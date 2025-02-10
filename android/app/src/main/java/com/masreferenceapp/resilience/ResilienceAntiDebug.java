package com.masreferenceapp.resilience;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Debug;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.masreferenceapp.ReturnStatus;


public class ResilienceAntiDebug extends ReactContextBaseJavaModule {
    ReactApplicationContext context;

    public ResilienceAntiDebug(ReactApplicationContext context) {
        super(context);
        this.context = context;
    }

    @NonNull
    @Override
    public String getName() {
        return "ResilienceAntiDebug";
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String debuggable() {
        try {
            PackageManager pm = context.getPackageManager();
            ApplicationInfo appInfo = pm.getApplicationInfo(context.getPackageName(), 0);
            boolean debuggable = (0 != (appInfo.flags & ApplicationInfo.FLAG_DEBUGGABLE));
            return new ReturnStatus("OK", "Is app debuggable: " + debuggable).toJsonString();
        } catch (Exception e) {
            ReturnStatus r = new ReturnStatus("FAIL", "Exception: " + e);
            return r.toJsonString();
        }
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String debuggerConnected() {
        ReturnStatus r = new ReturnStatus("OK", "Is debugger connected:: " + Debug.isDebuggerConnected());
        return r.toJsonString();
    }

}