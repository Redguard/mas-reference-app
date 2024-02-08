package com.masreferenceapp
import com.facebook.react.bridge.NativeModule
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
class DemoTestModule(reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext) {
    // add to CalendarModule.kt
    override fun getName() = "DemoTestModule"
}

