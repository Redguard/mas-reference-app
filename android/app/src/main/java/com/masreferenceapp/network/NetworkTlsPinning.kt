package com.masreferenceapp.network

import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.masreferenceapp.MasSettings
import com.masreferenceapp.ReturnStatus
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.Request;
import java.net.HttpURLConnection
import java.net.URL


class NetworkTlsPinning(var context: ReactApplicationContext) : ReactContextBaseJavaModule(
    context
) {
    override fun getName(): String {
        return "NetworkTlsPinning"
    }



    @ReactMethod(isBlockingSynchronousMethod = true)
    fun androidPinning(): String {
        val testDomain = MasSettings.getData("testDomain")

        val r = ReturnStatus()

        try{
            val connection = URL("https://$testDomain").openConnection() as HttpURLConnection
            val data = connection.inputStream.bufferedReader().readText()
            r.success("Pin verification successful.")
        }
        catch (e: Exception){
            r.success(e.toString())
        }
        return r.toJsonString()
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    fun androidPinningInvalid(): String {
        val testDomain = MasSettings.getData("testDomain")

        val r = ReturnStatus()

        try{
            val connection = URL("https://invalidpin.$testDomain").openConnection() as HttpURLConnection
            val data = connection.inputStream.bufferedReader().readText()
            r.success("Pin verification successful.")
        }
        catch (e: Exception){
            r.success(e.toString())
        }
        return r.toJsonString()
    }


    @ReactMethod(isBlockingSynchronousMethod = true)
    fun okHttpCertificatePinner(): String {
        val r = ReturnStatus()

        val testDomain = MasSettings.getData("testDomain") as String

        val certificatePinner = CertificatePinner.Builder()
            // These are the valid pins of the two Let's Encrypt root ca's
            .add(testDomain, "sha256/C5+lpZ7tcVwmwQIMcRtPbsQtWLABXhQzejna0wHFr8M=")
            .add(testDomain, "sha256/diGVwiVYbubAI3RW4hB9xU8e/CH2GnkuvVFZE8zmgzI=")
            .build()

        val okHttpClient = OkHttpClient.Builder()
            .certificatePinner(certificatePinner)
            .build()

        try{
            val request = Request.Builder()
                .url("https://$testDomain")
                .build()
            val response = okHttpClient.newCall(request).execute()
            r.success("Pin verification successful.")
        }
        catch (e: Exception){
            r.success(e.toString())
        }
        return r.toJsonString()
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    fun okHttpCertificatePinnerInvalid(): String {
        val r = ReturnStatus()

        val testDomain = MasSettings.getData("testDomain") as String

        val certificatePinner = CertificatePinner.Builder()
            //This is the invalid pin of the SwissSign ca
            .add(testDomain, "sha256/NDirQI6weuLiefh9EFjP0Rg8F7iLvBQE7fdD2e+j5r8=")
            .build()

        val okHttpClient = OkHttpClient.Builder()
            .certificatePinner(certificatePinner)
            .build()

        try{
            val request = Request.Builder()
                .url("https://invalidpin.$testDomain")
                .build()
            val response = okHttpClient.newCall(request).execute()
            r.success("Pin verification successful.")
        }
        catch (e: Exception){
            r.success(e.toString())
        }
        return r.toJsonString()
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    fun x509TrustManager(): String {
        val r = ReturnStatus("OK", "Android code stub.")
        return r.toJsonString()
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    fun webViewPinning(): String {
        val r = ReturnStatus("OK", "Android code stub.")
        return r.toJsonString()
    }

    //@method
}