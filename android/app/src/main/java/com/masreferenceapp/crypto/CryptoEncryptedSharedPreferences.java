package com.masreferenceapp.crypto;


import android.content.SharedPreferences;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.masreferenceapp.Status;

import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;

import java.util.HashSet;
import java.util.Set;

public class CryptoEncryptedSharedPreferences extends ReactContextBaseJavaModule {
    ReactApplicationContext context;

    public CryptoEncryptedSharedPreferences(ReactApplicationContext context) {
        super(context);
        this.context = context;
    }

    @NonNull
    @Override
    public String getName() {
        return "CryptoEncryptedSharedPreferences";
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String createEncryptedSharedPreferences(){
        SharedPreferences sharedPref;
        String message= "";
        try {
            // a the moment, this is the only way of creating encrypted preferences
            // a developer is not able to create an _insecure_ instances of EncryptedSharedPreferences

            String masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC);

            // the following method is in androidx-crypto 1.0.0, however it is deprecated in the new version alpha-7
            // which is not released at the date of development of this project
            SharedPreferences sharedPreferences = EncryptedSharedPreferences.create(
                    "masRefAppKey",
                    masterKeyAlias,
                    context,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            );
            message = sharedPreferences.toString();
        } catch (Exception e){
            message = e.toString();
        }

        return Status.status("OK", message);
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String putString(){
        SharedPreferences sharedPref;
        String message = "";
        try {
            // a the moment, this is the only way of creating encrypted preferences
            // a developer is not able to create an _insecure_ instances of EncryptedSharedPreferences

            String masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC);

            // the following method is in androidx-crypto 1.0.0, however it is deprecated in the new version alpha-7
            // which is not released at the date of development of this project
            SharedPreferences sharedPreferences = EncryptedSharedPreferences.create(
                    "masRefAppKey",
                    masterKeyAlias,
                    context,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            );
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("masRefAppKeyPassword", "Password123!");
            editor.commit();

            message = editor.toString();
        } catch (Exception e){
            message = e.toString();
        }
        return Status.status("OK", message);
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String putStringSet(){
        SharedPreferences sharedPref;
        String message = "";
        try {
            // a the moment, this is the only way of creating encrypted preferences
            // a developer is not able to create an _insecure_ instances of EncryptedSharedPreferences

            String masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC);

            // the following method is in androidx-crypto 1.0.0, however it is deprecated in the new version alpha-7
            // which is not released at the date of development of this project
            SharedPreferences sharedPreferences = EncryptedSharedPreferences.create(
                    "masRefAppKey",
                    masterKeyAlias,
                    context,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            );
            SharedPreferences.Editor editor = sharedPreferences.edit();
            Set<String> stringSet = new HashSet<String>();
            stringSet.add("Password123!");
            stringSet.add("HelloWorld!");
            editor.putStringSet("masRefAppKeyPasswords", stringSet);
            editor.commit();

            message = editor.toString();
        } catch (Exception e){
            message = e.toString();
        }
        return Status.status("OK", message);
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String readString(){
        SharedPreferences sharedPref;
        String message = "";
        try {
            // a the moment, this is the only way of creating encrypted preferences
            // a developer is not able to create an _insecure_ instances of EncryptedSharedPreferences

            String masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC);

            // the following method is in androidx-crypto 1.0.0, however it is deprecated in the new version alpha-7
            // which is not released at the date of development of this project
            SharedPreferences sharedPreferences = EncryptedSharedPreferences.create(
                    "masRefAppKey",
                    masterKeyAlias,
                    context,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            );
            String readValue = sharedPreferences.getString("masRefAppKeyPassword", "");

            message = readValue;
        } catch (Exception e){
            return Status.status("FAIL", e.toString());
        }
        return Status.status("OK", message);
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String readStringSet(){
        SharedPreferences sharedPref;
        String message = "";
        try {
            // a the moment, this is the only way of creating encrypted preferences
            // a developer is not able to create an _insecure_ instances of EncryptedSharedPreferences

            String masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC);

            // the following method is in androidx-crypto 1.0.0, however it is deprecated in the new version alpha-7
            // which is not released at the date of development of this project
            SharedPreferences sharedPreferences = EncryptedSharedPreferences.create(
                    "masRefAppKey",
                    masterKeyAlias,
                    context,
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            );
            Set<String> readValue = sharedPreferences.getStringSet("masRefAppKeyPasswords", new HashSet<>());
            message = readValue.toString();
        } catch (Exception e){
            return Status.status("FAIL", e.toString());
        }
        return Status.status("OK", message);
    }
}