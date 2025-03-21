package org.masreferenceapp.auth;

import static androidx.core.content.ContextCompat.getSystemService;

import android.app.KeyguardManager;
import android.provider.Settings;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import org.masreferenceapp.ReturnStatus;


public class AuthKeyguardManager extends ReactContextBaseJavaModule {
    ReactApplicationContext context;

    public AuthKeyguardManager(ReactApplicationContext context) {
        super(context);
        this.context = context;
    }

    @NonNull
    @Override
    public String getName() {
        return "AuthKeyguardManager";
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String checkKeyguardState() {
        KeyguardManager kgm = getSystemService(context, KeyguardManager.class);
        ReturnStatus r = new ReturnStatus();

        boolean isKeyguardSecure = kgm.isKeyguardSecure();
        r.success("Does the device have a secure lock screen or is there a locked SIM card: " + isKeyguardSecure);
        boolean isKeyguardLocked = kgm.isKeyguardLocked();
        r.success("Is lock screen (also known as KeyGuard) shown at the moment:  " + isKeyguardLocked);

        return r.toJsonString();
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String checkDeviceState() {

        ReturnStatus r = new ReturnStatus();

        KeyguardManager kgm = getSystemService(context, KeyguardManager.class);

        boolean isDeviceSecure = kgm.isDeviceSecure();
        r.success("Does the device have a secure lock screen: " + isDeviceSecure);

        boolean isDeviceLocked = kgm.isDeviceLocked();
        r.success("Is the device locked at the moment: " + isDeviceLocked);

        return r.toJsonString();
    }


    @ReactMethod(isBlockingSynchronousMethod = true)
    public String checkPattern() {

        try {
            int patternEnabled = Settings.System.getInt(context.getContentResolver(), Settings.Secure.LOCK_PATTERN_ENABLED, 0);
            ReturnStatus r = new ReturnStatus("OK", "Does the lock screen require a patter to unlock: " + patternEnabled);
            return r.toJsonString();

        } catch (Exception e) {
            ReturnStatus r = new ReturnStatus("FAIL", e.toString());
            return r.toJsonString();
        }
    }


    @ReactMethod(isBlockingSynchronousMethod = true)
    public String disableKeyguardLock() {

        KeyguardManager kgm = getSystemService(context, KeyguardManager.class);
        KeyguardManager.KeyguardLock keyguardLock = kgm.newKeyguardLock("Tag");
        keyguardLock.disableKeyguard();

        ReturnStatus r = new ReturnStatus("OK", "KeyboardLock disabled.");
        return r.toJsonString();
    }


    @ReactMethod(isBlockingSynchronousMethod = true)
    public String requestDismissKeyguard() {

        KeyguardManager kgm = getSystemService(context, KeyguardManager.class);

        KeyguardManager.KeyguardDismissCallback callback = new KeyguardManager.KeyguardDismissCallback() {
            @Override
            public void onDismissError() {
                super.onDismissError();
            }

            @Override
            public void onDismissSucceeded() {
                super.onDismissSucceeded();
            }

            @Override
            public void onDismissCancelled() {
                super.onDismissCancelled();
            }
        };

        kgm.requestDismissKeyguard(context.getCurrentActivity(), callback);


        ReturnStatus r = new ReturnStatus("OK", "KeyguardManager request dismissed.");
        return r.toJsonString();
    }

}