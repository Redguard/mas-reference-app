package com.masreferenceapp.network;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.masreferenceapp.Status;


public class NetworkTlsPinning extends ReactContextBaseJavaModule {
    ReactApplicationContext context;

    public NetworkTlsPinning(ReactApplicationContext context) {
        super(context);
        this.context = context;
    }

    @NonNull
    @Override
    public String getName() {
        return "NetworkTlsPinning";
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String truststore(){
        return Status.status("OK", "Message");
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String okHttpCertificatePinner(){
        return Status.status("OK", "Message");
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String webViewPinning(){
        return Status.status("OK", "Message");
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String programmaticallyVerify(){
        return Status.status("OK", "Message");
    }
}