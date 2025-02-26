package com.insomnihack

class MainCTF {
    init {
        System.loadLibrary("rust_jni")

        /* Create a new key pair (if not yet existing) in the KeyStore */
        RsaThingies ().generateKeyPair()
    }
}