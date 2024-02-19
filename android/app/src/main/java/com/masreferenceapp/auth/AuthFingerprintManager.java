package com.masreferenceapp.auth;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.hardware.fingerprint.FingerprintManager;
import android.os.CancellationSignal;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.masreferenceapp.Status;

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
    public String isHardwareDetected(){
        FingerprintManager fpm = context.getSystemService(FingerprintManager.class);
        return Status.status("OK", "HW detected: " + fpm.isHardwareDetected());
    }


    @ReactMethod(isBlockingSynchronousMethod = true)
    public String hasEnrolledFingerprints(){

        FingerprintManager fpm = context.getSystemService(FingerprintManager.class);
        return Status.status("OK", "HW detected: " + fpm.hasEnrolledFingerprints());
    }


    @ReactMethod(isBlockingSynchronousMethod = true)
    public String authenticateSimple(){
        FingerprintManager fpm = context.getSystemService(FingerprintManager.class);

        final CancellationSignal cancellationSignal = new CancellationSignal();

        AlertDialog alertDialog = new AlertDialog.Builder(context.getCurrentActivity())

                .setTitle("Simple Fingerprint")
                .setMessage("Waiting for Fingerprint")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        cancellationSignal.cancel();
                        dialogInterface.dismiss(); // Dismiss the dialog
                    }
                })
                .show();

        FingerprintManager.AuthenticationCallback callback = new FingerprintManager.AuthenticationCallback() {
            @Override
            public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                alertDialog.dismiss();
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Toast t = Toast.makeText(context, "Wrong Fingerprint", Toast.LENGTH_SHORT);
                t.show();
                alertDialog.dismiss();
            }
        };
        Executor mExecutor = Executors.newSingleThreadExecutor();
        fpm.authenticate(null, cancellationSignal, 0, callback, null);

        return Status.status("OK", "FPM Auth executed.");

    }


    @ReactMethod(isBlockingSynchronousMethod = true)
    public String authenticateCryptoObject(){

        try {
            final CancellationSignal cancellationSignal = new CancellationSignal();

            AlertDialog alertDialog = new AlertDialog.Builder(context.getCurrentActivity())
                    .setTitle("Crypto Object Fingerprint")
                    .setMessage("Waiting for Fingerprint")
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            cancellationSignal.cancel();
                            dialogInterface.dismiss(); // Dismiss the dialog
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


            FingerprintManager fpm = context.getSystemService(FingerprintManager.class);


            FingerprintManager.AuthenticationCallback callback = new FingerprintManager.AuthenticationCallback() {
                @Override
                public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
                    super.onAuthenticationSucceeded(result);
                    System.out.println(result.getCryptoObject().toString());
                    alertDialog.dismiss();
                }

                @Override
                public void onAuthenticationFailed() {
                    super.onAuthenticationFailed();
                    Toast t = Toast.makeText(context, "Wrong Fingerprint", Toast.LENGTH_SHORT);
                    t.show();
                    alertDialog.dismiss();
                }
            };

            Executor mExecutor = Executors.newSingleThreadExecutor();

            fpm.authenticate(cObject, cancellationSignal, 0, callback, null);


            return Status.status("OK", cipher.toString());

        } catch (Exception e) {
            return Status.status("FAIL", e.toString());
        }
    }

}