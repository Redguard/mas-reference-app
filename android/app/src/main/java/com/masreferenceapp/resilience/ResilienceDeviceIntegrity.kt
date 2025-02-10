package com.masreferenceapp.resilience

import android.util.Base64
import com.facebook.react.bridge.Promise
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.google.android.gms.safetynet.SafetyNet
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.play.core.integrity.IntegrityManager
import com.google.android.play.core.integrity.IntegrityManagerFactory
import com.google.android.play.core.integrity.IntegrityTokenRequest
import com.masreferenceapp.MasSettings
import com.masreferenceapp.ReturnStatus
import java.security.SecureRandom


class ResilienceDeviceIntegrity(var context: ReactApplicationContext) : ReactContextBaseJavaModule(
    context
) {
    override fun getName(): String {
        return "ResilienceDeviceIntegrity"
    }

        
    @ReactMethod(isBlockingSynchronousMethod = true)
    fun playIntegrity(): String {
        val r = ReturnStatus()
        try{
            SafetyNet.getClient(context).attest(ByteArray(0), "DUMMY-API-KEY")
            r.success("SafetyNet attestation function hast been called. Since it is deprecated, a dummy API key was taken.")
        }
        catch(e:Exception){
            r.fail(e.toString())
        }
        return r.toJsonString()
    }

        
    @ReactMethod(isBlockingSynchronousMethod = false)
    fun safetyNet(promise: Promise) {
        val r = ReturnStatus()
        try{
            val integrityManager: IntegrityManager = IntegrityManagerFactory.create(context)

            val nonceBytes = ByteArray(50)
            SecureRandom().nextBytes(nonceBytes)
            val nonceBase64: String = Base64.encodeToString(nonceBytes, Base64.NO_WRAP or Base64.URL_SAFE)

            val request = IntegrityTokenRequest.builder()
                .setNonce(nonceBase64)
                .setCloudProjectNumber(MasSettings.getAndroidCloudProjectNumber())
                .build()

            integrityManager.requestIntegrityToken(request).addOnCompleteListener(OnCompleteListener { task ->
                if (task.isSuccessful) {
                    val integrityToken = task.result.token()
                    r.success("Used PlayIntegrity to ask for an Integrity Token. Token is: $integrityToken")
                } else {
                    r.fail(task.exception.toString())
                }
                promise.resolve(r.toJsonString())
            })
        }
        catch(e:Exception){
            r.fail(e.toString())
            promise.resolve(r.toJsonString())
        }
    }


    @ReactMethod(isBlockingSynchronousMethod = true)
    fun safetyNet(): String {
        val r = ReturnStatus("OK", "Android code stub.")
        return r.toJsonString()
    }

    //@method
}