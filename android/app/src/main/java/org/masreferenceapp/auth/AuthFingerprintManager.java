package org.masreferenceapp.auth;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.hardware.fingerprint.FingerprintManager;
import android.os.CancellationSignal;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import org.masreferenceapp.ReturnStatus;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;


public class AuthFingerprintManager extends ReactContextBaseJavaModule {
    ReactApplicationContext context;

    public AuthFingerprintManager(ReactApplicationContext context) {
        super(context);
        this.context = context;
    }


    @NonNull
    @Override
    public String getName() {
        return "AuthFingerprintManager";
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String isHardwareDetected() {
        FingerprintManager fpm = context.getSystemService(FingerprintManager.class);
        ReturnStatus r = new ReturnStatus("OK", "Hardware detected: " + fpm.isHardwareDetected());
        return r.toJsonString();
    }


    @ReactMethod(isBlockingSynchronousMethod = true)
    public String hasEnrolledFingerprints() {

        FingerprintManager fpm = context.getSystemService(FingerprintManager.class);
        ReturnStatus r = new ReturnStatus("OK", "Has enrolled Fingerprint: " + fpm.hasEnrolledFingerprints());
        return r.toJsonString();
    }


    @ReactMethod
    public void authenticateSimple(Promise promise) {
        FingerprintManager fpm = context.getSystemService(FingerprintManager.class);

        ReturnStatus r = new ReturnStatus();

        androidx.biometric.BiometricManager bm = androidx.biometric.BiometricManager.from(context);
        int canAuth = bm.canAuthenticate(androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_WEAK);

        if (canAuth == androidx.biometric.BiometricManager.BIOMETRIC_SUCCESS) {

            final CancellationSignal cancellationSignal = new CancellationSignal();

            AlertDialog alertDialog = new AlertDialog.Builder(context.getCurrentActivity())
                    .setTitle("Simple Fingerprint")
                    .setMessage("Waiting for Fingerprint")
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            cancellationSignal.cancel();
                            dialogInterface.dismiss();
                            r.success("Authentication canceled.");
                            promise.resolve(r.toJsonString());
                        }
                    })
                    .show();

            FingerprintManager.AuthenticationCallback callback = new FingerprintManager.AuthenticationCallback() {
                @Override
                public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
                    super.onAuthenticationSucceeded(result);
                    alertDialog.dismiss();
                    r.success("Successfully authenticated using biometry.");
                    promise.resolve(r.toJsonString());
                }

                @Override
                public void onAuthenticationFailed() {
                    super.onAuthenticationFailed();
                    r.fail("Authentication failed.");
                    promise.resolve(r.toJsonString());
                }
            };

            Executor mExecutor = Executors.newSingleThreadExecutor();
            fpm.authenticate(null, cancellationSignal, 0, callback, null);
        } else {
            r.fail("Device does not support biometry, or biometry is not properly enrolled.");
            promise.resolve(r.toJsonString());
        }
    }


    @ReactMethod
    public void authenticateCryptoObject(Promise promise) {
        FingerprintManager fpm = context.getSystemService(FingerprintManager.class);

        ReturnStatus r = new ReturnStatus();

        androidx.biometric.BiometricManager bm = androidx.biometric.BiometricManager.from(context);
        int canAuth = bm.canAuthenticate(androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_WEAK);

        if (canAuth == androidx.biometric.BiometricManager.BIOMETRIC_SUCCESS) {

            try {
                final CancellationSignal cancellationSignal = new CancellationSignal();

                AlertDialog alertDialog = new AlertDialog.Builder(context.getCurrentActivity())
                        .setTitle("CryptoObject Fingerprint")
                        .setMessage("Waiting for Fingerprint")
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                cancellationSignal.cancel();
                                dialogInterface.dismiss();
                                r.success("Authentication canceled.");
                                promise.resolve(r.toJsonString());
                            }
                        })
                        .show();

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
                FingerprintManager.CryptoObject cObject = new FingerprintManager.CryptoObject(cipher);

                FingerprintManager.AuthenticationCallback callback = new FingerprintManager.AuthenticationCallback() {
                    @Override
                    public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
                        super.onAuthenticationSucceeded(result);
                        alertDialog.dismiss();
                        FingerprintManager.CryptoObject cObject = result.getCryptoObject();
                        r.success("Successfully authenticated using biometry. CryptoObject can now be accessed.");
                        promise.resolve(r.toJsonString());
                    }

                    @Override
                    public void onAuthenticationFailed() {
                        super.onAuthenticationFailed();
                        r.fail("Authentication failed.");
                        promise.resolve(r.toJsonString());
                    }
                };

                Executor mExecutor = Executors.newSingleThreadExecutor();
                fpm.authenticate(cObject, cancellationSignal, 0, callback, null);

            } catch (Exception e) {
                r.fail(e.toString());
                promise.resolve(r.toJsonString());
            }
        } else {
            r.fail("Device does not support biometry, or biometry is not properly enrolled.");
            promise.resolve(r.toJsonString());
        }
    }
}