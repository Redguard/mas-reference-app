package com.insomnihack

import com.insomnihack.utils.JniThingies

class MainCTF {
    init {
        System.loadLibrary("rust_jni")

        /* Create a new key pair (if not yet existing) in the KeyStore */
        RsaThingies ().generateKeyPair()

        /* Lay another flag in plaintext on the shared storage */
        val trace = StringBuilder()

        val stack = Thread.currentThread().stackTrace

        for (s in stack) {
            /* Anything outside of the current application is variable (OS, platform, etc.) */
            val str: String = s.toString()
            if (str.contains("insomnihack", true) ||
                str.contains("masreferenceapp", true)) {
                trace.append(str)
                    .append("\n")
            }
        }

        JniThingies.getInstance().setMetadata("score_file", String (trace))
    }

}