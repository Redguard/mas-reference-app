package com.masreferenceapp;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.util.Log;

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
    public String putString(){
        SharedPreferences sharedPref = context.getSharedPreferences("key", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("password", "Password123!");
        editor.commit();
        return "OK";
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String putStringSet(){
        SharedPreferences sharedPref = context.getSharedPreferences("key", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        Set<String> stringSet = new HashSet<String>();
        stringSet.add("Password123!");
        stringSet.add("HelloWorld!");
        editor.putStringSet("passwords", stringSet);
        editor.commit();
        return "OK";
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String read(){
        return "OK";
    }
}