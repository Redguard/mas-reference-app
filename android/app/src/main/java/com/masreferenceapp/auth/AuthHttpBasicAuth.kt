package com.masreferenceapp.auth

import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.masreferenceapp.ReturnStatus



class AuthHttpBasicAuth(var context: ReactApplicationContext) : ReactContextBaseJavaModule(
    context
) {
    override fun getName(): String {
        return "AuthHttpBasicAuth"
    }

        
    @ReactMethod(isBlockingSynchronousMethod = true)
    fun javaNet(): String {
        val r = ReturnStatus("OK", "Android code stub.")
        return r.toJsonString()
    }

        
    @ReactMethod(isBlockingSynchronousMethod = true)
    fun webView(): String {
        val r = ReturnStatus("OK", "Android code stub.")
        return r.toJsonString()
    }

    //@method
}