package com.masreferenceapp.storage;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;


public class StorageLog extends ReactContextBaseJavaModule {
    ReactApplicationContext context;

    public StorageLog(ReactApplicationContext context) {
        super(context);
        this.context = context;
    }

    @NonNull
    @Override
    public String getName() {
        return "StorageLog";
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String writeSensitiveData(){
        return "OK";
    }
}