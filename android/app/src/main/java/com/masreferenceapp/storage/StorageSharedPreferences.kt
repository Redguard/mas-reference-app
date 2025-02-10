package com.masreferenceapp.storage

import android.content.Context
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.masreferenceapp.MasSettings
import com.masreferenceapp.ReturnStatus
import com.masreferenceapp.SensitiveData

class StorageSharedPreferences(var context: ReactApplicationContext) : ReactContextBaseJavaModule(
    context
) {
    override fun getName(): String {
        return "StorageSharedPreferences"
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    fun putCtString(): String {
        val sharedPref =
            context.getSharedPreferences("MasSharedPref_CT_String", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putString("CanaryTokenString", MasSettings.getCanaryToken())
        editor.apply()
        return ReturnStatus("OK", "Data written: " + MasSettings.getCanaryToken()).toJsonString()
    }


    @ReactMethod(isBlockingSynchronousMethod = true)
    fun putCtStringSet(): String {
        val sharedPref =
            context.getSharedPreferences("MasSharedPref_CT_StringSet", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        val stringSet: MutableSet<String> = HashSet()
        stringSet.add(MasSettings.getCanaryToken())
        editor.putStringSet("CanaryTokenStringSet", stringSet)
        editor.apply()
        return ReturnStatus("OK", "Data written: " + MasSettings.getCanaryToken()).toJsonString()
    }


    @ReactMethod(isBlockingSynchronousMethod = true)
    fun putSensitiveString(): String {
        val sharedPref =
            context.getSharedPreferences("MasSharedPref_Sensitve_String", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putString("SensitiveData", SensitiveData.data)
        editor.apply()
        return ReturnStatus("OK", "Data written: " + SensitiveData.data).toJsonString()
    }


    @ReactMethod(isBlockingSynchronousMethod = true)
    fun putSensitiveStringSet(): String {
        val sharedPref =
            context.getSharedPreferences("MasSharedPref_Sensitive_StringSet", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        val stringSet: MutableSet<String> = HashSet()
        stringSet.add(SensitiveData.data)
        editor.putStringSet("SensitiveDataStringSet", stringSet)
        editor.apply()
        return ReturnStatus("OK", "Data written: " + SensitiveData.data).toJsonString()
    }


    @ReactMethod(isBlockingSynchronousMethod = true)
    fun getWorldReadableInstance(): String {
        val r = ReturnStatus()
        try {
            val sharedPref =
                context.getSharedPreferences("MasSharedPrefREADABLE", Context.MODE_WORLD_READABLE)
            r.addStatus("OK", "WorldReadable Instance created.")
        } catch (e: Exception) {
            r.addStatus("FAIL", e.toString())
        }

        return r.toJsonString()
    }


    @ReactMethod(isBlockingSynchronousMethod = true)
    fun getWorldWritableInstance(): String {
        val r = ReturnStatus()
        try {
            val sharedPref =
                context.getSharedPreferences("MasSharedPrefREADABLE", Context.MODE_WORLD_WRITEABLE)
            r.addStatus("OK", "WorldWritable Instance created.")
        } catch (e: Exception) {
            r.addStatus("FAIL", e.toString())
        }

        return r.toJsonString()
    }

    //@method

}