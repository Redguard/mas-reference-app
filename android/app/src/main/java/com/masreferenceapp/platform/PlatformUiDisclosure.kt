package com.masreferenceapp.platform

import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.masreferenceapp.ReturnStatus



class PlatformUiDisclosure(var context: ReactApplicationContext) : ReactContextBaseJavaModule(
    context
) {
    override fun getName(): String {
        return "PlatformUiDisclosure"
    }

        
    @ReactMethod(isBlockingSynchronousMethod = true)
    fun loadLocalResource(): String {
        val r = ReturnStatus("OK", "Android code stub.")
        return r.toJsonString()
    }

        
    @ReactMethod(isBlockingSynchronousMethod = true)
    fun sensitveDataNotifications(): String {
        val r = ReturnStatus("OK", "Android code stub.")
        return r.toJsonString()
    }

    //@method
}