package com.masreferenceapp.crypto;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.masreferenceapp.ReturnStatus;

import java.security.SecureRandom;
import java.util.Random;


public class CryptoRandomJava extends ReactContextBaseJavaModule {
    ReactApplicationContext context;

    public CryptoRandomJava(ReactApplicationContext context) {
        super(context);
        this.context = context;
    }

    @NonNull
    @Override
    public String getName() {
        return "CryptoRandomJava";
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String insecureRandom(){

        Random random = new Random();

        ReturnStatus r = new ReturnStatus("OK", "Weak random number generated using java.util.Random(): " + random.nextInt());
        return r.toJsonString();
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String insecureRandomSeed(){

        Random r1 = new Random(123456);
        Random r2 = new Random(123456);
        ReturnStatus r = new ReturnStatus("OK", "Two weak random number generated using java.util.Random() and a seed:  " + r1.nextInt() + ":" + r2.nextInt());
        return r.toJsonString();

    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String secureRandom(){
        SecureRandom random = new SecureRandom();

        ReturnStatus r = new ReturnStatus("OK", "Secure random number generated using java.util.SecureRandom(): " + random.nextInt());
        return r.toJsonString();

    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String secureRandomSeed() {

        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[20];
        random.nextBytes(bytes);

        SecureRandom r1 = new SecureRandom(bytes);
        SecureRandom r2 = new SecureRandom(bytes);

        ReturnStatus r = new ReturnStatus("OK", "Secure random number generated using java.util.SecureRandom() and a seed.");
        return r.toJsonString();
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String secureRandomInstance(){

        try {
            SecureRandom sr = SecureRandom.getInstance("SHA1PRNG", "Crypto");
            ReturnStatus r = new ReturnStatus("OK", "Created a SecureRandom Instance with 'SHA1PRNG'-Algorithm.");
            return r.toJsonString();
        }
        catch (Exception e) {
            ReturnStatus r = new ReturnStatus("FAIL", "Exception: " + e);
            return r.toJsonString();
        }

    }
}