package com.masreferenceapp.crypto

import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.masreferenceapp.ReturnStatus



class CryptoDeprecated(var context: ReactApplicationContext) : ReactContextBaseJavaModule(
    context
) {
    override fun getName(): String {
        return "CryptoDeprecated"
    }

        
    @ReactMethod(isBlockingSynchronousMethod = true)
    fun bouncyCastle(): String {
        val r = ReturnStatus("OK", "Android code stub.")
        return r.toJsonString()
    }

    //@method
}