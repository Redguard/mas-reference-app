package com.masreferenceapp;

import static android.content.Context.CONTEXT_RESTRICTED;
import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import java.util.HashSet;
import java.util.Set;


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

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String getInsecureSharedPreferences(){
        SharedPreferences sharedPref;
        String retunCode = "OK";
        try {
            sharedPref = context.getSharedPreferences("masRefAppKey", Context.MODE_WORLD_READABLE);
        } catch (Exception e){
            retunCode = e.toString();
        }
        try {
            sharedPref = context.getSharedPreferences("masRefAppKey", Context.MODE_WORLD_WRITEABLE);
        } catch (Exception e) {
            retunCode = e.toString();
        }
        return retunCode;
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String putString(){
        SharedPreferences sharedPref = context.getSharedPreferences("masRefAppKey", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("masRefAppKeyPassword", "Password123!");
        editor.commit();
        return "OK";
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String putStringSet(){
        SharedPreferences sharedPref = context.getSharedPreferences("masRefAppKey", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
            Set<String> stringSet = new HashSet<String>();
            stringSet.add("Password123!");
            stringSet.add("HelloWorld!");
        editor.putStringSet("masRefAppKeyPasswords", stringSet);
        editor.commit();
        return "OK";
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String readString(){
        this.putString();

        SharedPreferences sharedPref = context.getSharedPreferences("masRefAppKey", MODE_PRIVATE);
        String readValue = sharedPref.getString("masRefAppKeyPassword", "");

        return "OK";
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String readStringSet(){
        this.putStringSet();

        SharedPreferences sharedPref = context.getSharedPreferences("masRefAppKey", MODE_PRIVATE);
        Set<String> readValue = sharedPref.getStringSet("masRefAppKeyPasswords", new HashSet<>());

        return "OK";
    }
}