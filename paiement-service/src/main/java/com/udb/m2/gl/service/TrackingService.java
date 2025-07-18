package com.udb.m2.gl.service;

import com.udb.m2.gl.model.Tracking;
import com.udb.m2.gl.repository.TrackingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;
@RequiredArgsConstructor
@Service
public class TrackingService implements ITracking{

    private final TrackingRepository trackingRepository;

    @Override
    public Tracking findById(UUID id) {
        return trackingRepository.findById(id)
                .orElse(null);
    }

    @Override
    public Tracking save(Tracking tracking) {
        return trackingRepository.save(tracking);
    }
}
