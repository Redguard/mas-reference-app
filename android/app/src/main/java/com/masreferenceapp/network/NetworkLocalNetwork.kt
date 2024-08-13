package com.masreferenceapp.network

import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.masreferenceapp.Status

class NetworkLocalNetwork(var context: ReactApplicationContext) : ReactContextBaseJavaModule(
    context
) {
    override fun getName(): String {
        return "NetworkLocalNetwork"
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    fun putString(): String {
        return Status.status("OK", "Message")
    }
}