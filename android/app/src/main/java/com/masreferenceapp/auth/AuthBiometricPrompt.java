package com.masreferenceapp.auth;

import android.content.DialogInterface;
import android.hardware.biometrics.BiometricManager;
import android.hardware.biometrics.BiometricPrompt;
import android.os.CancellationSignal;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.Promise;
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

    @ReactMethod
    public void simplePrompt(Promise promise) {

        ReturnStatus r = new ReturnStatus();

        Executor mExecutor = Executors.newSingleThreadExecutor();
        BiometricPrompt.AuthenticationCallback mAuthenticationCallback = new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                r.success("Successfully authenticated using biometry.");
                promise.resolve(r.toJsonString());
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                r.fail("Authenticated using biometry failed.");
                promise.resolve(r.toJsonString());
            }
        };

        BiometricPrompt prompt = new BiometricPrompt.Builder(context.getApplicationContext())
                .setTitle("Simple Authentication")
                .setSubtitle("Place your finger on the sensor.")
                .setNegativeButton("Cancel", mExecutor, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        r.success("Authentication canceled.");
                        promise.resolve(r.toJsonString());
                    }
                }).build();

        androidx.biometric.BiometricManager bm = androidx.biometric.BiometricManager.from(context);
        int canAuth = bm.canAuthenticate(androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_WEAK);

        if (canAuth == androidx.biometric.BiometricManager.BIOMETRIC_SUCCESS){
            CancellationSignal mcancellationSignal = new CancellationSignal();
            prompt.authenticate(mcancellationSignal, mExecutor, mAuthenticationCallback);

        }else{
            r.fail("Device does not support biometry, or biometry is not properly enrolled.");
            promise.resolve(r.toJsonString());
        }
    }

    @ReactMethod
    public void devicePinOnlyPrompt(Promise promise) {
        ReturnStatus r = new ReturnStatus();

        Executor mExecutor = Executors.newSingleThreadExecutor();
        CancellationSignal mcancellationSignal = new CancellationSignal();
        BiometricPrompt.AuthenticationCallback mAuthenticationCallback = new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationSucceeded(@NonNull BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                r.success("Successfully authenticated using local device credential.");
                promise.resolve(r.toJsonString());
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                r.fail("Authenticated using local device credential failed.");
                promise.resolve(r.toJsonString());
            }
        };
        BiometricPrompt prompt = new BiometricPrompt.Builder(context.getApplicationContext())
                .setTitle("Simple Authentication")
                .setSubtitle("Please enter your PIN.")
                .setAllowedAuthenticators(BiometricManager.Authenticators.DEVICE_CREDENTIAL)
                .build();


        androidx.biometric.BiometricManager bm = androidx.biometric.BiometricManager.from(context);
        int canAuth = bm.canAuthenticate(androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL);

        if (canAuth == androidx.biometric.BiometricManager.BIOMETRIC_SUCCESS){
            prompt.authenticate(mcancellationSignal, mExecutor, mAuthenticationCallback);
        }else{
            r.fail("Device does not support device credentials. Set up local authentication using PIN, password or pattern first.");
            promise.resolve(r.toJsonString());
        }

    }


    @ReactMethod
    public void cryptoOperationPrompt(Promise promise) {
        ReturnStatus r = new ReturnStatus();

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
                    BiometricPrompt.CryptoObject cObject = result.getCryptoObject();
                    Cipher c = cObject.getCipher();

                    try {
                        String inputString = "Let's encrypt this S3CR3T!";
                        byte[] plaintext = inputString.getBytes();
                        byte[] ciphertext = c.doFinal(plaintext);
                        r.success("CryptoObject successfully accessed.");
                        promise.resolve(r.toJsonString());
                    } catch (Exception e) {
                        r.fail(e.toString());
                        promise.resolve(r.toJsonString());
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
                            r.success("Authentication canceled.");
                            promise.resolve(r.toJsonString());                        }
                    })
                    .build();

            androidx.biometric.BiometricManager bm = androidx.biometric.BiometricManager.from(context);
            int canAuth = bm.canAuthenticate(androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_WEAK);

            if (canAuth == androidx.biometric.BiometricManager.BIOMETRIC_SUCCESS){
                prompt.authenticate(cObject, mcancellationSignal, mExecutor, mAuthenticationCallback);
            }else{
                r.fail("Device does not support biometry, or biometry is not properly enrolled.");
                promise.resolve(r.toJsonString());
            }

        } catch (Exception e) {
            r.fail(e.toString());
            promise.resolve(r.toJsonString());
        }
    }

}