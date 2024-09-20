package com.masreferenceapp.storage;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.masreferenceapp.ReturnStatus;
import com.masreferenceapp.Status;

import java.util.HashSet;
import java.util.Set;


public class StorageSharedPreferences extends ReactContextBaseJavaModule {
    ReactApplicationContext context;

    public StorageSharedPreferences(ReactApplicationContext context) {
        super(context);
        this.context = context;
    }

    @NonNull
    @Override
    public String getName() {
        return "StorageSharedPreferences";
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String getInsecureSharedPreferences(){
        SharedPreferences sharedPref;
        ReturnStatus r = new ReturnStatus();

        try {
            sharedPref = context.getSharedPreferences("masRefAppKey", Context.MODE_WORLD_READABLE);
            r.addStatus("OK", "SharedPreferences initialized.");

        } catch (Exception e){
            r.addStatus("FAIL", e.toString());
        }
        try {
            sharedPref = context.getSharedPreferences("masRefAppKey", Context.MODE_WORLD_WRITEABLE);
            r.addStatus("OK", "SharedPreferences initialized.");
        } catch (Exception e) {
            r.addStatus("FAIL", e.toString());
        }

        return r.toJsonString();

    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String putString(){
        SharedPreferences sharedPref = context.getSharedPreferences("masRefAppKey", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("masRefAppKeyPassword", "Password123!");
        editor.apply();
        return new ReturnStatus("OK", "Data written.").toJsonString();
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String putStringSet(){
        SharedPreferences sharedPref = context.getSharedPreferences("masRefAppKey", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
            Set<String> stringSet = new HashSet<String>();
            stringSet.add("Password123!");
            stringSet.add("HelloWorld!");
        editor.putStringSet("masRefAppKeyPasswords", stringSet);
        editor.apply();
        return new ReturnStatus("OK", "Data written.").toJsonString();
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String readString(){
        this.putString();

        SharedPreferences sharedPref = context.getSharedPreferences("masRefAppKey", MODE_PRIVATE);
        String readValue = sharedPref.getString("masRefAppKeyPassword", "");

        return new ReturnStatus("OK", "Data read: " + readValue).toJsonString();

    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String readStringSet(){
        this.putStringSet();

        SharedPreferences sharedPref = context.getSharedPreferences("masRefAppKey", MODE_PRIVATE);
        Set<String> readValue = sharedPref.getStringSet("masRefAppKeyPasswords", new HashSet<>());

        return new ReturnStatus("OK", "Data read: " + readValue).toJsonString();
    }
}