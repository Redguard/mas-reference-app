package com.masreferenceapp.network

import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.masreferenceapp.Status

class NetworkTlsPinning(var context: ReactApplicationContext) :
    ReactContextBaseJavaModule(context) {
    override fun getName(): String {
        return "NetworkTlsPinning"
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    fun truststore(): String {
        return Status.status("OK", "Message")
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    fun okHttpCertificatePinner(): String {
        return Status.status("OK", "Message")
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    fun webViewPinning(): String {
        return Status.status("OK", "Message")
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    fun programmaticallyVerify(): String {
        return Status.status("OK", "Message")
    }
}