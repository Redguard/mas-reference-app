package com.masreferenceapp.network;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.masreferenceapp.Status;


public class NetworkLocalNetwork extends ReactContextBaseJavaModule {
    ReactApplicationContext context;

    public NetworkLocalNetwork(ReactApplicationContext context) {
        super(context);
        this.context = context;
    }

    @NonNull
    @Override
    public String getName() {
        return "NetworkLocalNetwork";
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String putString(){
        return Status.status("OK", "Message");
    }
}