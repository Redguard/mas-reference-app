package com.masreferenceapp.auth;

import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.masreferenceapp.Status;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;


public class AuthKeyAccess extends ReactContextBaseJavaModule {
    ReactApplicationContext context;

    public AuthKeyAccess(ReactApplicationContext context) {
        super(context);
        this.context = context;
    }

    @NonNull
    @Override
    public String getName() {
        return "AuthKeyAccess";
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String accessKeyWithoutPrompt(){

        try {
            KeyGenerator keygen = KeyGenerator.getInstance("AES");
            KeyGenParameterSpec spec = new KeyGenParameterSpec.Builder(
                    "masAccessTests",
                    KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT).
                    setUserAuthenticationRequired(true).
                    setBlockModes(KeyProperties.BLOCK_MODE_CBC).
                    setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7).
                    setKeySize(256).
                    build();

            keygen.init(spec);
            SecretKey key = keygen.generateKey();

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] encrypted = cipher.doFinal("someSecret Plaintext".getBytes());

            return Status.status("OK", key.toString());

        } catch (Exception e) {
            return Status.status("FAIL", e.toString());
        }

    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String longValidity(){
        try {
            KeyGenerator keygen = KeyGenerator.getInstance("AES");
            KeyGenParameterSpec spec = new KeyGenParameterSpec.Builder(
                    "masAccessTests",
                    KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT).
                    setUserAuthenticationRequired(true).
                    setUserAuthenticationParameters(2000000, KeyProperties.AUTH_BIOMETRIC_STRONG).
                    setBlockModes(KeyProperties.BLOCK_MODE_CBC).
                    setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7).
                    setKeySize(256).
                    build();

            keygen.init(spec);
            SecretKey key = keygen.generateKey();

            return Status.status("OK", key.toString());

        } catch (Exception e) {
            return Status.status("FAIL", e.toString());
        }
    }
}