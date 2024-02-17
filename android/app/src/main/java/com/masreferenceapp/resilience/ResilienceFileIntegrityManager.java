package com.masreferenceapp.resilience;

import android.security.FileIntegrityManager;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.masreferenceapp.Status;

import java.io.File;


public class ResilienceFileIntegrityManager extends ReactContextBaseJavaModule {
    ReactApplicationContext context;

    public ResilienceFileIntegrityManager(ReactApplicationContext context) {
        super(context);
        this.context = context;
    }

    @NonNull
    @Override
    public String getName() {
        return "ResilienceFileIntegrityManager";
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String setupFsVerify(){

        File appSpecificExternalFile = new File(context.getExternalFilesDir(null), "masFileIntegrityCheck");

        // will be added in Android 15



        return Status.status("OK", "Message");

    }
}