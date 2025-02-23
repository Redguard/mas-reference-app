package com.insomnihack

import android.widget.Toast
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod

class Welcome(reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext) {

    override fun getName() = "WelcomeCTF"

    external fun test(a: Int, b: Int): Int

    @ReactMethod
    fun showToast(message: String) {
        Toast.makeText(reactApplicationContext, "message ${test(1, 3)}", Toast.LENGTH_SHORT).show()
    }
}
