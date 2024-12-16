package com.masreferenceapp.auth;
import androidx.annotation.NonNull;

import android.content.DialogInterface;
import android.hardware.biometrics.BiometricManager;
import android.hardware.biometrics.BiometricPrompt;
import android.os.CancellationSignal;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;


import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.masreferenceapp.ReturnStatus;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;


public class AuthBiometricPrompt extends ReactContextBaseJavaModule {
    ReactApplicationContext context;

    public AuthBiometricPrompt(ReactApplicationContext context) {
        super(context);
        this.context = context;
    }

    @NonNull
    @Override
    public String getName() {
        return "AuthBiometricPrompt";
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String simplePrompt() {

        Executor mExecutor = Executors.newSingleThreadExecutor();
        BiometricPrompt.AuthenticationCallback mAuthenticationCallback = new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
            }
        };

        BiometricPrompt prompt = new BiometricPrompt.Builder(context.getApplicationContext())
                .setTitle("Simple Authentication")
                .setSubtitle("Place your finger on the sensor.")
                .setNegativeButton("Cancel", mExecutor, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Handle negative button click (e.g., user cancels authentication)
                    }
                }).build();

        CancellationSignal mcancellationSignal = new CancellationSignal();

        prompt.authenticate(mcancellationSignal, mExecutor, mAuthenticationCallback );

        ReturnStatus r = new ReturnStatus("OK", "Simple biometry prompt executed.");
        return r.toJsonString();
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String devicePinOnlyPrompt() {

        Executor mExecutor = Executors.newSingleThreadExecutor();
        CancellationSignal mcancellationSignal = new CancellationSignal();
        BiometricPrompt.AuthenticationCallback mAuthenticationCallback = new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
            }
        };
        BiometricPrompt prompt = new BiometricPrompt.Builder(context.getApplicationContext())
                .setTitle("Simple Authentication")
                .setSubtitle("Please enter your PIN.")
                .setAllowedAuthenticators(BiometricManager.Authenticators.DEVICE_CREDENTIAL)
                .build();

        prompt.authenticate(mcancellationSignal, mExecutor, mAuthenticationCallback );

        ReturnStatus r = new ReturnStatus("OK", "Simple device PIN prompt executed.");
        return r.toJsonString();
    }

}