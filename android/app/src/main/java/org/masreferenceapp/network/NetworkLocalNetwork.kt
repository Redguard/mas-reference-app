package org.masreferenceapp.network

import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import org.masreferenceapp.ReturnStatus

class NetworkLocalNetwork(var context: ReactApplicationContext) : ReactContextBaseJavaModule(
    context
) {
    override fun getName(): String {
        return "NetworkLocalNetwork"
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    fun putString(): String {
        val r = ReturnStatus("OK", "Android code stub.")
        return r.toJsonString()
    }


    @ReactMethod(isBlockingSynchronousMethod = true)
    fun access(): String {
        val r = ReturnStatus("OK", "Android code stub.")
        return r.toJsonString()
    }


    @ReactMethod(isBlockingSynchronousMethod = true)
    fun hostHttpServer(): String {
        val r = ReturnStatus("OK", "Android code stub.")
        return r.toJsonString()
    }


    @ReactMethod(isBlockingSynchronousMethod = true)
    fun hostTcpServer(): String {
        val r = ReturnStatus("OK", "Android code stub.")
        return r.toJsonString()
    }


    @ReactMethod(isBlockingSynchronousMethod = true)
    fun hostUDPServer(): String {
        val r = ReturnStatus("OK", "Android code stub.")
        return r.toJsonString()
    }

    //@method

}