package com.masreferenceapp.auth;

import static androidx.core.content.ContextCompat.getSystemService;

import android.app.KeyguardManager;
import android.content.ContentResolver;
import android.provider.Settings;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.masreferenceapp.ReturnStatus;
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

        ReturnStatus r = new ReturnStatus("OK", "isKeyguardSecure: "+ isKeyguardSecure +", isKeyguardLocked:" +  isKeyguardLocked);
        return r.toJsonString();
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String checkDeviceState(){
        KeyguardManager kgm = (KeyguardManager) getSystemService(context, KeyguardManager.class);

        boolean isDeviceSecure = kgm.isDeviceSecure();
        boolean isDeviceLocked = kgm.isDeviceLocked();

        ReturnStatus r = new ReturnStatus("OK", "isDeviceSecure: " +  isDeviceSecure+", isDeviceLocked:" +  isDeviceLocked);
        return r.toJsonString();
    }


    @ReactMethod(isBlockingSynchronousMethod = true)
    public String checkPattern(){

        try{
            int patternEnabled = Settings.System.getInt(context.getContentResolver(), Settings.Secure.LOCK_PATTERN_ENABLED , 0);
            ReturnStatus r = new ReturnStatus("OK", "Pattern Enabled: "+ patternEnabled);
            return r.toJsonString();

        }catch (Exception e){
            ReturnStatus r = new ReturnStatus("FAIL", "Exception: " + e.toString());
            return r.toJsonString();
        }
    }


    @ReactMethod(isBlockingSynchronousMethod = true)
    public String disableKeyguardLock(){

        KeyguardManager kgm = (KeyguardManager) getSystemService(context, KeyguardManager.class);
        KeyguardManager.KeyguardLock keyguardLock = kgm.newKeyguardLock("Tag");
        keyguardLock.disableKeyguard();

        ReturnStatus r = new ReturnStatus("OK", "KeyboardLock disabled.");
        return r.toJsonString();
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


        ReturnStatus r = new ReturnStatus("OK", "KeyguardManager Request dismissed.");
        return r.toJsonString();
    }

}