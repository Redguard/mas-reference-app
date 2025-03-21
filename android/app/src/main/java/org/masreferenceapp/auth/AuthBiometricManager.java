package org.masreferenceapp.auth;

import androidx.annotation.NonNull;
import androidx.biometric.BiometricManager;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import org.masreferenceapp.ReturnStatus;


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
    public String testStrongAuth() {
        BiometricManager bm = BiometricManager.from(context);
        int canAuth = bm.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG);

        String test = "False";
        if (canAuth == 0) {
            test = "True";
        }

        ReturnStatus r = new ReturnStatus("OK", "Can device strong authenticate (Class 3)?: " + test);
        return r.toJsonString();
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String testWeakAuth() {
        BiometricManager bm = BiometricManager.from(context);
        int canAuth = bm.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_WEAK);

        String test = "False";
        if (canAuth == 0) {
            test = "True";
        }

        ReturnStatus r = new ReturnStatus("OK", "Can device weak authenticate (Class 2)?:  " + test);
        return r.toJsonString();
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String testDeviceCredentialsAuth() {
        BiometricManager bm = BiometricManager.from(context);
        int canAuth = bm.canAuthenticate(BiometricManager.Authenticators.DEVICE_CREDENTIAL);

        String test = "False";
        if (canAuth == 0) {
            test = "True";
        }

        ReturnStatus r = new ReturnStatus("OK", "Can non-biometric credential be used to secure the device?: " + test);
        return r.toJsonString();
    }
}