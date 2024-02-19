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
import com.masreferenceapp.Status;

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

        return Status.status("OK", prompt.toString());
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
                .setSubtitle("Place your finger on the sensor.")
                .setAllowedAuthenticators(BiometricManager.Authenticators.DEVICE_CREDENTIAL)
                .build();

        prompt.authenticate(mcancellationSignal, mExecutor, mAuthenticationCallback );

        return Status.status("OK", prompt.toString());
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String cryptoOperationPrompt(){

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

            BiometricPrompt.CryptoObject cObject = new BiometricPrompt.CryptoObject(cipher);
            Executor mExecutor = Executors.newSingleThreadExecutor();
            CancellationSignal mcancellationSignal = new CancellationSignal();
            BiometricPrompt.AuthenticationCallback mAuthenticationCallback = new BiometricPrompt.AuthenticationCallback() {
                @Override
                public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                    super.onAuthenticationSucceeded(result);
                    BiometricPrompt.CryptoObject cObject =  result.getCryptoObject();
                    Cipher c = cObject.getCipher();

                    try {
                        String inputString = "Let's encrypt this S3CR3T!";
                        byte[] plaintext = inputString.getBytes();
                        byte[] ciphertext = c.doFinal(plaintext);
                    } catch (Exception e) {
                        System.out.println(e.toString());
                    }
                }

                @Override
                public void onAuthenticationFailed() {
                    super.onAuthenticationFailed();
                }
            };
            BiometricPrompt prompt = new BiometricPrompt.Builder(context.getApplicationContext())
                    .setTitle("Crypto Authentication")
                    .setSubtitle("Place your finger on the sensor.")
                    .setNegativeButton("Cancel", mExecutor, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Handle negative button click (e.g., user cancels authentication)
                        }
                    })
                    .build();

            prompt.authenticate(cObject, mcancellationSignal, mExecutor, mAuthenticationCallback);

            return Status.status("OK", cipher.toString());

        } catch (Exception e) {
            return Status.status("FAIL", e.toString());
        }

    }
}