package com.masreferenceapp;

import android.util.Log;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

public class StorageEncryptedSharedPreferences extends ReactContextBaseJavaModule {
    StorageEncryptedSharedPreferences(ReactApplicationContext context) {
        super(context);
    }

    @NonNull
    @Override
    public String getName() {
        return "StorageEncryptedSharedPreferences";
    }

    @ReactMethod
    public String write(){
        Log.i("WRITE ENCRYPTED", "sUPER SECRET PASSSWD");
        return "Writing SharedPreferences";
    }

    @ReactMethod
    public String read(){
        Log.i("REAADD ENCRYPTED", "sUPER SECRET PASSSWD");
        return "Reading SharedPreferences";
    }
}