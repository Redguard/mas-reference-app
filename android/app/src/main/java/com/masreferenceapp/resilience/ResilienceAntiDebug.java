package com.masreferenceapp.resilience;

import android.accessibilityservice.AccessibilityServiceInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Debug;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.masreferenceapp.Status;


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
    public String debuggable(){
        try{
            PackageManager pm = context.getPackageManager();
            ApplicationInfo appInfo = pm.getApplicationInfo(context.getPackageName(), 0);
            boolean debuggable = (0 != (appInfo.flags & ApplicationInfo.FLAG_DEBUGGABLE));
            return Status.status("OK", "Debuggable?: " + debuggable);
        }
        catch (Exception e){
            return Status.status("FAIL", e.toString());
        }
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String debuggerConnected(){
        return Status.status("OK", "Debugger Connected?: " + Debug.isDebuggerConnected());
    }

}