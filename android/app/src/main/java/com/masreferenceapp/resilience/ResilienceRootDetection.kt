package com.masreferenceapp.resilience

import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.masreferenceapp.ReturnStatus



class ResilienceRootDetection(var context: ReactApplicationContext) : ReactContextBaseJavaModule(
    context
) {
    override fun getName(): String {
        return "ResilienceRootDetection"
    }

        
    @ReactMethod(isBlockingSynchronousMethod = true)
    fun rootBeer(): String {
        val r = ReturnStatus("OK", "Android code stub.")
        return r.toJsonString()
    }

    //@method
}