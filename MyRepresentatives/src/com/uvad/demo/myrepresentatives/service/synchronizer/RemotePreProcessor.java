package com.uvad.demo.myrepresentatives.service.synchronizer;

import java.util.List;

import com.uvad.demo.myrepresentatives.service.RemoteObject;

public abstract class RemotePreProcessor<T extends RemoteObject> {

    public abstract void preProcessRemoteRecords(List<T> records);

}
