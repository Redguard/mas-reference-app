package com.masreferenceapp

import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod

class TemplateClassKotlin(var context: ReactApplicationContext) : ReactContextBaseJavaModule(
    context
) {
    override fun getName(): String {
        return "TemplateClass"
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    fun firstMethod(): String {
        val r = ReturnStatus("OK", "Android code stub for: ")
        return r.toJsonString()
    }
}