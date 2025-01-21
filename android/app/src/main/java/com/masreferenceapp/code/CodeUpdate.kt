package com.masreferenceapp.code

import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability
import com.masreferenceapp.ReturnStatus



class CodeUpdate(var context: ReactApplicationContext) : ReactContextBaseJavaModule(
    context
) {
    override fun getName(): String {
        return "CodeUpdate"
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    fun checkUpdateAvailable(): String {

        val appUpdateManager = AppUpdateManagerFactory.create(context)

        val appUpdateInfoTask = appUpdateManager.appUpdateInfo

        // Checks that the platform will allow the specified type of update.
        appUpdateInfoTask.addOnSuccessListener { appUpdateInfo ->
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                // This example applies an immediate update. To apply a flexible update
                // instead, pass in AppUpdateType.FLEXIBLE
                && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)
            ) {
                // Request the update.
                print("Update available.")
            }
            else{
                print("No update available.")
            }
        }

        val r = ReturnStatus("OK", "Queried App Update Manager for In-app update.")
        return r.toJsonString()
    }

        
    @ReactMethod(isBlockingSynchronousMethod = true)
    fun checkOs(): String {

        val sdkInt = android.os.Build.VERSION.SDK_INT
        val sdkString = android.os.Build.VERSION.SDK
        val securityPatch = android.os.Build.VERSION.SECURITY_PATCH
        val codeName = android.os.Build.VERSION.CODENAME
        val release = android.os.Build.VERSION.RELEASE

        val r = ReturnStatus("OK", "Android Release is $release. Android code name is $codeName. Android SDK INT is $sdkInt. Android SDK String is $sdkString. Android Security Patch is $securityPatch.")
        return r.toJsonString()
    }

    //@method
}