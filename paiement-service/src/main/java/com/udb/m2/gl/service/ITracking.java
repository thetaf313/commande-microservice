package com.udb.m2.gl.service;

import com.udb.m2.gl.model.Tracking;

import java.util.UUID;

public interface ITracking {

    public Tracking findById(UUID id);
    public Tracking save(Tracking tracking);
}
