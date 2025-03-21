package org.masreferenceapp.resilience

import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import org.masreferenceapp.ReturnStatus
import com.scottyab.rootbeer.RootBeer


class ResilienceRootDetection(var context: ReactApplicationContext) : ReactContextBaseJavaModule(
    context
) {
    override fun getName(): String {
        return "ResilienceRootDetection"
    }


    @ReactMethod(isBlockingSynchronousMethod = true)
    fun rootBeer(): String {
        val r = ReturnStatus()
        try {
            val rootBeer = RootBeer(context)
            r.success("Used RoodBeer to test if device is rooted. Is it rooted: ${rootBeer.isRooted}")
            return r.toJsonString()
        } catch (e: Exception) {
            r.fail(e.toString())
        }
        return r.toJsonString()

    }

    //@method
}