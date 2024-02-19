
package com.masreferenceapp.auth;

import androidx.biometric.BiometricManager;
import androidx.annotation.NonNull;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.masreferenceapp.Status;


public class AuthBiometricManager extends ReactContextBaseJavaModule {
    ReactApplicationContext context;

    public AuthBiometricManager(ReactApplicationContext context) {
        super(context);
        this.context = context;
    }

    @NonNull
    @Override
    public String getName() {
        return "AuthBiometricManager";
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String testStrongAuth(){
        BiometricManager bm = BiometricManager.from(context);
        int canAuth = bm.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG);

        return Status.status("OK", String.valueOf(canAuth));
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String testWeakAuth(){
        BiometricManager bm = BiometricManager.from(context);
        int canAuth = bm.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_WEAK);

        return Status.status("OK", String.valueOf(canAuth));
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String testDeviceCredentialsAuth(){
        BiometricManager bm = BiometricManager.from(context);
        int canAuth = bm.canAuthenticate(BiometricManager.Authenticators.DEVICE_CREDENTIAL);

        return Status.status("OK", String.valueOf(canAuth));
    }

}