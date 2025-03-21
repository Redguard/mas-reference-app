package org.masreferenceapp.resilience

import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import org.masreferenceapp.ReturnStatus


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
    fun nonObfuscatedAndroidClass(): String {
        val r = ReturnStatus("OK", "Android code stub.")
        return r.toJsonString()
    }


    @ReactMethod(isBlockingSynchronousMethod = true)
    fun nativeWithDebugSymbols(): String {
        val r = ReturnStatus("OK", "Android code stub.")
        return r.toJsonString()
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    fun nativeWithoutDebugSymbols(): String {
        val r = ReturnStatus("OK", "Android code stub.")
        return r.toJsonString()
    }

    //@method
}