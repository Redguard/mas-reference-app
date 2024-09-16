package com.masreferenceapp.crypto;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.masreferenceapp.ReturnStatus;
import com.masreferenceapp.Status;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.util.Random;


public class CryptoRandom extends ReactContextBaseJavaModule {
    ReactApplicationContext context;

    public CryptoRandom(ReactApplicationContext context) {
        super(context);
        this.context = context;
    }

    @NonNull
    @Override
    public String getName() {
        return "CryptoRandom";
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String insecureRandom(){

        Random r = new Random();

        return Status.status("OK", "Number: " + r.nextInt());
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String insecureRandomSeed(){

        Random r1 = new Random(123456);
        Random r2 = new Random(123456);

        return Status.status("OK", "Numbers: " + r1.nextInt() +":" + r2.nextInt());

    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String secureRandom(){
        SecureRandom r = new SecureRandom();

        return Status.status("OK", "Number: " + r.nextInt());
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String secureRandomSeed(){

        SecureRandom random = new SecureRandom();
        byte bytes[] = new byte[20];
        random.nextBytes(bytes);

        SecureRandom r1 = new SecureRandom(bytes);
        SecureRandom r2 = new SecureRandom(bytes);

        return Status.status("OK", "Numbers: " + r1.nextInt() +":" + r2.nextInt());
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String secureRandomInstance(){

        try {
            SecureRandom sr = SecureRandom.getInstance("SHA1PRNG", "Crypto");
            return Status.status("OK", "Number: " + sr.nextInt());
        }
        catch (Exception e) {
            ReturnStatus r = new ReturnStatus("FAIL", "Exception: " + e.toString());
            return r.toJsonString();
        }

    }
}