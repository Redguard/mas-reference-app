package com.masreferenceapp.platform;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.masreferenceapp.Status;


public class PlatformUiDisclosure extends ReactContextBaseJavaModule {
    ReactApplicationContext context;

    public PlatformUiDisclosure(ReactApplicationContext context) {
        super(context);
        this.context = context;
    }

    @NonNull
    @Override
    public String getName() {
        return "PlatformUiDisclosure";
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String putString(){
        return Status.status("OK", "Message");
    }
}