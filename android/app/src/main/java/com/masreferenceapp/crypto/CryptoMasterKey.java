package com.masreferenceapp.crypto;

import androidx.annotation.NonNull;
import androidx.security.crypto.MasterKeys;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.masreferenceapp.ReturnStatus;

import java.io.IOException;
import java.security.GeneralSecurityException;


public class CryptoMasterKey extends ReactContextBaseJavaModule {
    ReactApplicationContext context;

    public CryptoMasterKey(ReactApplicationContext context) {
        super(context);
        this.context = context;
    }

    @NonNull
    @Override
    public String getName() {
        return "CryptoMasterKey";
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String createMasterKey()
    {
        String masterKeyAlias = null;
        try {
            masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC);
            ReturnStatus r = new ReturnStatus("OK", "MasterKey created. Alias is:" + masterKeyAlias);
            return r.toJsonString();
        } catch (Exception e) {
            ReturnStatus r = new ReturnStatus("FAIL", "Exception: " + e);
            return r.toJsonString();
        }
    }
}