package org.masreferenceapp.resilience;

import android.content.pm.PackageManager;
import android.content.pm.Signature;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import org.masreferenceapp.ReturnStatus;


public class ResilienceVerifySignature extends ReactContextBaseJavaModule {
    ReactApplicationContext context;

    public ResilienceVerifySignature(ReactApplicationContext context) {
        super(context);
        this.context = context;
    }

    @NonNull
    @Override
    public String getName() {
        return "ResilienceVerifySignature";
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String getPackageSignatures() {

        StringBuilder message = new StringBuilder();
        try {
            Signature[] sigs = new Signature[0];
            sigs = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES).signatures;
            for (Signature sig : sigs) {
                message.append(sig.hashCode() + "\n");
            }
            return new ReturnStatus("OK", "Package signatures: " + message).toJsonString();

        } catch (Exception e) {
            return new ReturnStatus("FAIL", e.toString()).toJsonString();

        }
    }
}