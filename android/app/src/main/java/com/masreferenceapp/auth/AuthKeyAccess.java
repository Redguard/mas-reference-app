package com.masreferenceapp.auth;

import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.masreferenceapp.ReturnStatus;

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


//    @ReactMethod(isBlockingSynchronousMethod = true)
//    public String accessKeyWithoutPrompt(){
//
//        try {
//            KeyGenerator keygen = KeyGenerator.getInstance("AES");
//            KeyGenParameterSpec spec = new KeyGenParameterSpec.Builder(
//                    "masAccessTests",
//                    KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT).
//                    setUserAuthenticationRequired(true).
//                    setBlockModes(KeyProperties.BLOCK_MODE_CBC).
//                    setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7).
//                    setKeySize(256).
//                    build();
//
//            keygen.init(spec);
//            SecretKey key = keygen.generateKey();
//
//            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7PADDING");
//            cipher.init(Cipher.ENCRYPT_MODE, key);
//            byte[] encrypted = cipher.doFinal("someSecret Plaintext".getBytes());
//
//
//            ReturnStatus r = new ReturnStatus("OK", "Accessed key: " +  key.toString());
//            return r.toJsonString();
//
//        } catch (Exception e) {
//            ReturnStatus r = new ReturnStatus("FAIL", "Exception: " + e.toString());
//            return r.toJsonString();        }
//
//    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String deviceCredentialsRequired(){
        try {
            KeyGenerator keygen = KeyGenerator.getInstance("AES");
            KeyGenParameterSpec spec = new KeyGenParameterSpec.Builder(
                    "masAccessTests",
                    KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT).
                    setUserAuthenticationRequired(true).
                    setUserAuthenticationParameters(60, KeyProperties.AUTH_DEVICE_CREDENTIAL).
                    setBlockModes(KeyProperties.BLOCK_MODE_CBC).
                    setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7).
                    setKeySize(256).
                    build();

            keygen.init(spec);
            SecretKey key = keygen.generateKey();

            ReturnStatus r = new ReturnStatus("OK", "Accessed a key which required a device auth (Pin/Password/Pattern).");
            return r.toJsonString();

        } catch (Exception e) {
            ReturnStatus r = new ReturnStatus("FAIL", "Exception: " + e);
            return r.toJsonString();
        }
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String biometryRequired(){
        try {
            KeyGenerator keygen = KeyGenerator.getInstance("AES");
            KeyGenParameterSpec spec = new KeyGenParameterSpec.Builder(
                    "masAccessTests",
                    KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT).
                    setUserAuthenticationRequired(true).
                    setUserAuthenticationParameters(60, KeyProperties.AUTH_BIOMETRIC_STRONG).
                    setBlockModes(KeyProperties.BLOCK_MODE_CBC).
                    setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7).
                    setKeySize(256).
                    build();

            keygen.init(spec);
            SecretKey key = keygen.generateKey();

            ReturnStatus r = new ReturnStatus("OK", "Accessed a key which required strong biometric auth.");
            return r.toJsonString();

        } catch (Exception e) {
            ReturnStatus r = new ReturnStatus("FAIL", "Exception: " + e);
            return r.toJsonString();
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
                    setUserAuthenticationParameters(31536000, KeyProperties.AUTH_DEVICE_CREDENTIAL).
                    setBlockModes(KeyProperties.BLOCK_MODE_CBC).
                    setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7).
                    setKeySize(256).
                    build();

            keygen.init(spec);
            SecretKey key = keygen.generateKey();

            ReturnStatus r = new ReturnStatus("OK", "Accessed a key with validity timeout of a year.");
            return r.toJsonString();

        } catch (Exception e) {
            ReturnStatus r = new ReturnStatus("FAIL", "Exception: " + e);
            return r.toJsonString();
        }
    }
}