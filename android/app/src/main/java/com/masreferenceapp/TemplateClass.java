package com.masreferenceapp;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;


public class TemplateClass extends ReactContextBaseJavaModule {
    ReactApplicationContext context;

    TemplateClass(ReactApplicationContext context) {
        super(context);
        this.context = context;
    }

    @NonNull
    @Override
    public String getName() {
        return "TemplateClass";
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String putString(){
        return "OK";
    }
}