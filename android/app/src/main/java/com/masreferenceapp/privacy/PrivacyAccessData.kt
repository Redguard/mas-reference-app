package com.masreferenceapp.privacy

import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.masreferenceapp.ReturnStatus



class PrivacyAccessData(var context: ReactApplicationContext) : ReactContextBaseJavaModule(
    context
) {
    override fun getName(): String {
        return "PrivacyAccessData"
    }

        
    @ReactMethod(isBlockingSynchronousMethod = true)
    fun getContacts(): String {
        val r = ReturnStatus("OK", "Android code stub.")
        return r.toJsonString()
    }

        
    @ReactMethod(isBlockingSynchronousMethod = true)
    fun writeContacts(): String {
        val r = ReturnStatus("OK", "Android code stub.")
        return r.toJsonString()
    }

        
    @ReactMethod(isBlockingSynchronousMethod = true)
    fun getCalendarEvent(): String {
        val r = ReturnStatus("OK", "Android code stub.")
        return r.toJsonString()
    }

        
    @ReactMethod(isBlockingSynchronousMethod = true)
    fun writeCalendarEvent(): String {
        val r = ReturnStatus("OK", "Android code stub.")
        return r.toJsonString()
    }

    //@method
}