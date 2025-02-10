package com.masreferenceapp.crypto

import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.masreferenceapp.ReturnStatus
import kotlin.random.Random

class CryptoRandomKotlin(var context: ReactApplicationContext) : ReactContextBaseJavaModule(
    context
) {
    override fun getName(): String {
        return "CryptoRandomKotlin"
    }


    @ReactMethod(isBlockingSynchronousMethod = true)
    fun insecureRandom(): String {

        val random = Random.Default

        val r = ReturnStatus()
        r.success(
            "Weak random number generated using kotlin.random.Random(): " + random.nextInt()
        )
        return r.toJsonString()
    }


    @ReactMethod(isBlockingSynchronousMethod = true)
    fun insecureSeed(): String {

        val r1 = Random(seed = 42)
        val r2 = Random(seed = 42)

        val r = ReturnStatus()
        r.success(
            "Two weak random number generated using kotlin.random.Random() and a seed:  " + r1.nextInt() + ":" + r2.nextInt()
        )
        return r.toJsonString()
    }

    //@method
}