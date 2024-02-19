package com.masreferenceapp.auth;

import static androidx.core.content.ContextCompat.getSystemService;

import android.app.KeyguardManager;
import android.content.ContentResolver;
import android.provider.Settings;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.masreferenceapp.Status;


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
    public String checkKeyguardState(){
        KeyguardManager kgm = (KeyguardManager) getSystemService(context, KeyguardManager.class);

        boolean isKeyguardSecure = kgm.isKeyguardSecure();
        boolean isKeyguardLocked = kgm.isKeyguardLocked();

        return Status.status("OK", "Message: "+ isKeyguardSecure +", " +  isKeyguardLocked);
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String checkDeviceState(){
        KeyguardManager kgm = (KeyguardManager) getSystemService(context, KeyguardManager.class);

        boolean isDeviceSecure = kgm.isDeviceSecure();
        boolean isDeviceLocked = kgm.isDeviceLocked();

        return Status.status("OK", "Message: " +  isDeviceSecure+", " +  isDeviceLocked);
    }


    @ReactMethod(isBlockingSynchronousMethod = true)
    public String checkPattern(){

        try{
            int patternEnabled = Settings.System.getInt(context.getContentResolver(), Settings.Secure.LOCK_PATTERN_ENABLED , 0);
            return Status.status("OK", "Pattern Enabled?: "+ patternEnabled );

        }catch (Exception e){
            return Status.status("FAIL",  e.toString());
        }
    }


    @ReactMethod(isBlockingSynchronousMethod = true)
    public String disableKeyguardLock(){

        KeyguardManager kgm = (KeyguardManager) getSystemService(context, KeyguardManager.class);
        KeyguardManager.KeyguardLock keyguardLock = kgm.newKeyguardLock("Tag");
        keyguardLock.disableKeyguard();

        return Status.status("OK", "Message");
    }


    @ReactMethod(isBlockingSynchronousMethod = true)
    public String requestDismissKeyguard(){

        KeyguardManager kgm = (KeyguardManager) getSystemService(context, KeyguardManager.class);

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

        return Status.status("OK", kgm.toString());
    }

}