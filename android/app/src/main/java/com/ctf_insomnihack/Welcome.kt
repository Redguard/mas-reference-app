package com.ctf_insomnihack

import android.widget.Toast
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod

class Welcome(reactContext: ReactApplicationContext) : ReactContextBaseJavaModule(reactContext) {

    // The name of the module that will be used in JS
    override fun getName() = "WelcomeCTF"

    // Method to show the toast
    @ReactMethod
    fun showToast(message: String) {
        Toast.makeText(reactApplicationContext, message, Toast.LENGTH_SHORT).show()
    }

    // Optionally, you can add methods to handle more complex functionality
}
