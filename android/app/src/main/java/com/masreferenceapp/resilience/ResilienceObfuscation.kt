package com.masreferenceapp.resilience

import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.masreferenceapp.ReturnStatus


class ResilienceObfuscation(var context: ReactApplicationContext) : ReactContextBaseJavaModule(
    context
) {
    override fun getName(): String {
        return "ResilienceObfuscation"
    }


    @ReactMethod(isBlockingSynchronousMethod = true)
    fun obfuscatedAndroidClass(): String {
        val r = ReturnStatus("OK", "Android code stub.")
        return r.toJsonString()
    }


    @ReactMethod(isBlockingSynchronousMethod = true)
    fun nativeDebugSymbols(): String {
        val r = ReturnStatus("OK", "Android code stub.")
        return r.toJsonString()
    }

    //@method
}