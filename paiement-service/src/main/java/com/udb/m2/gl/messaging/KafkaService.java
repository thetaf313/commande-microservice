package com.udb.m2.gl.messaging;


import com.udb.m2.gl.ConfigData;
import com.udb.m2.gl.common.service.event.KafkaEvent;
import com.udb.m2.gl.kafka.avro.model.CustomerCreateRequestAvroModel;
import com.udb.m2.gl.service.MessageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class KafkaService {

    private final MessageHelper<String, CustomerCreateRequestAvroModel> messageHelper;

    private final ConfigData configData;


    public KafkaService(MessageHelper<String, CustomerCreateRequestAvroModel> messageHelper, ConfigData configData) {
        this.messageHelper = messageHelper;
        this.configData = configData;
    }

    public void createCustumer(KafkaEvent<CustomerCreateRequestAvroModel> kafkaEvent){
        //create customer
        messageHelper.send(configData.getCustomerCreateTopicRequestName(), kafkaEvent.getEventId().toString(),
                kafkaEvent.getData());
    }

}
