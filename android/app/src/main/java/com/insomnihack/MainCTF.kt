package com.insomnihack

import android.util.Log

class MainCTF {

    init {
        System.loadLibrary("rust_jni")
        Log.i("CTF", "rust_jni loaded (?)")
    }
}