package com.masreferenceapp.storage;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.masreferenceapp.Status;


public class StorageExternalStorage extends ReactContextBaseJavaModule {
    ReactApplicationContext context;

    public StorageExternalStorage(ReactApplicationContext context) {
        super(context);
        this.context = context;
    }

    @NonNull
    @Override
    public String getName() {
        return "StorageExternalStorage";
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String checkState(){
        return Status.status("OK", "Message");
    }
}