package com.masreferenceapp.storage

import android.content.Context
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.masreferenceapp.ReturnStatus

class StorageSharedPreferences(var context: ReactApplicationContext) : ReactContextBaseJavaModule(
    context
) {
    override fun getName(): String {
        return "StorageSharedPreferences"
    }

    //    @ReactMethod(isBlockingSynchronousMethod = true)
    //    public String getInsecureSharedPreferences(){
    //        SharedPreferences sharedPref;
    //        ReturnStatus r = new ReturnStatus();
    //
    //        try {
    //            sharedPref = context.getSharedPreferences("masRefAppKey", Context.MODE_WORLD_READABLE);
    //            r.addStatus("OK", "SharedPreferences initialized.");
    //
    //        } catch (Exception e){
    //            r.addStatus("FAIL", e.toString());
    //        }
    //        try {
    //            sharedPref = context.getSharedPreferences("masRefAppKey", Context.MODE_WORLD_WRITEABLE);
    //            r.addStatus("OK", "SharedPreferences initialized.");
    //        } catch (Exception e) {
    //            r.addStatus("FAIL", e.toString());
    //        }
    //
    //        return r.toJsonString();
    //
    //    }
    @ReactMethod(isBlockingSynchronousMethod = true)
    fun putString(): String {
        val sharedPref = context.getSharedPreferences("masRefAppKey", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putString("masRefAppKeyPassword", "Password123!")
        editor.apply()
        return ReturnStatus("OK", "Data written.").toJsonString()
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    fun putStringSet(): String {
        val sharedPref = context.getSharedPreferences("masRefAppKey", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        val stringSet: MutableSet<String> = HashSet()
        stringSet.add("Password123!")
        stringSet.add("HelloWorld!")
        editor.putStringSet("masRefAppKeyPasswords", stringSet)
        editor.apply()
        return ReturnStatus("OK", "Data written.").toJsonString()
    }


    @ReactMethod(isBlockingSynchronousMethod = true)
    fun putBoolean(): String {
        val r = ReturnStatus("OK", "Android code stub.")
        return r.toJsonString()
    }


    @ReactMethod(isBlockingSynchronousMethod = true)
    fun putFloat(): String {
        val r = ReturnStatus("OK", "Android code stub.")
        return r.toJsonString()
    }

        
    @ReactMethod(isBlockingSynchronousMethod = true)
    fun putInt(): String {
        val r = ReturnStatus("OK", "Android code stub.")
        return r.toJsonString()
    }

    //@method

    
    //    @ReactMethod(isBlockingSynchronousMethod = true)
    //    public String readString(){
    //        this.putString();
    //
    //        SharedPreferences sharedPref = context.getSharedPreferences("masRefAppKey", MODE_PRIVATE);
    //        String readValue = sharedPref.getString("masRefAppKeyPassword", "");
    //
    //        return new ReturnStatus("OK", "Data read: " + readValue).toJsonString();
    //
    //    }
    //
    //    @ReactMethod(isBlockingSynchronousMethod = true)
    //    public String readStringSet(){
    //        this.putStringSet();
    //
    //        SharedPreferences sharedPref = context.getSharedPreferences("masRefAppKey", MODE_PRIVATE);
    //        Set<String> readValue = sharedPref.getStringSet("masRefAppKeyPasswords", new HashSet<>());
    //
    //        return new ReturnStatus("OK", "Data read: " + readValue).toJsonString();
    //    }
}