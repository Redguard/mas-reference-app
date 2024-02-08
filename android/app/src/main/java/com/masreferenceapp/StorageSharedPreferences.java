package com.masreferenceapp;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.util.Log;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;


public class StorageSharedPreferences extends ReactContextBaseJavaModule {
    ReactApplicationContext context;

    StorageSharedPreferences(ReactApplicationContext context) {
        super(context);
        this.context = context;
    }

    @NonNull
    @Override
    public String getName() {
        return "StorageSharedPreferences";
    }

    @ReactMethod
    public String putString(){
        SharedPreferences sharedPref = context.getSharedPreferences("key", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("username", "administrator");
        editor.putString("password", "supersecret");
        editor.commit();
        return "Writing SharedPreferences";
    }

    @ReactMethod
    public String putStringSet(){
        SharedPreferences sharedPref = context.getSharedPreferences("key", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        // editor.putString("username", "administrator");
        // editor.putString("password", "supersecret");
        // editor.commit();
        return "Writing SharedPreferences";
    }

    @ReactMethod
    public String read(){
        Log.i("REAADD", "sUPER SECRET PASSSWD");
        return "Reading SharedPreferences";
    }
}