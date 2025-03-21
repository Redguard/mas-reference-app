package org.masreferenceapp.network

import PinningWebViewClient
import android.webkit.WebView
import com.facebook.react.bridge.Promise
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import org.masreferenceapp.MasSettings
import org.masreferenceapp.R
import org.masreferenceapp.ReturnStatus
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.Request
import java.net.HttpURLConnection
import java.net.URL
import java.security.KeyStore
import java.security.SecureRandom
import java.security.cert.CertificateFactory
import java.security.cert.X509Certificate
import javax.net.ssl.HttpsURLConnection
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManagerFactory


class NetworkTlsPinning(var context: ReactApplicationContext) : ReactContextBaseJavaModule(
    context
) {
    override fun getName(): String {
        return "NetworkTlsPinning"
    }


    @ReactMethod(isBlockingSynchronousMethod = true)
    fun androidNoPinning(): String {
        val testDomain = "nopin." + MasSettings.getTestDomain()

        val r = ReturnStatus()

        try {
            val connection = URL("https://$testDomain").openConnection() as HttpURLConnection
            val data = connection.inputStream.bufferedReader().readText()
            r.success("Pin verification successful. Connection to https://$testDomain established. Response code was: ${connection.responseCode}.")
        } catch (e: Exception) {
            r.fail("Connection to https://$testDomain could not be established.")
            r.fail(e.toString())
        }
        return r.toJsonString()
    }


    @ReactMethod(isBlockingSynchronousMethod = true)
    fun androidPinning(): String {
        val testDomain = MasSettings.getTestDomain()

        val r = ReturnStatus()

        try {
            val connection = URL("https://$testDomain").openConnection() as HttpURLConnection
            val data = connection.inputStream.bufferedReader().readText()
            r.success("Pin verification successful. Connection to https://$testDomain established. Response code was: ${connection.responseCode}.")
        } catch (e: Exception) {
            r.fail("Connection to https://$testDomain could not be established.")
            r.fail(e.toString())
        }
        return r.toJsonString()
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    fun androidPinningInvalid(): String {
        val testDomain = "invalidpin." + MasSettings.getTestDomain()

        val r = ReturnStatus()

        try {
            val connection = URL("https://$testDomain").openConnection() as HttpURLConnection
            val data = connection.inputStream.bufferedReader().readText()
            r.success("Pin verification successful. Connection to https://$testDomain established. Response code was: ${connection.responseCode}.")
        } catch (e: Exception) {
            r.fail("Connection to https://$testDomain could not be established.")
            r.fail(e.toString())
        }
        return r.toJsonString()
    }


    @ReactMethod(isBlockingSynchronousMethod = true)
    fun okHttpCertificatePinner(): String {
        val r = ReturnStatus()

        val testDomain = MasSettings.getTestDomain()

        val certificatePinner = CertificatePinner.Builder()
            // These are the valid pins of the two Let's Encrypt root ca's
            .add(testDomain, "sha256/C5+lpZ7tcVwmwQIMcRtPbsQtWLABXhQzejna0wHFr8M=")
            .add(testDomain, "sha256/diGVwiVYbubAI3RW4hB9xU8e/CH2GnkuvVFZE8zmgzI=")
            .build()

        val okHttpClient = OkHttpClient.Builder()
            .certificatePinner(certificatePinner)
            .build()

        try {
            val request = Request.Builder()
                .url("https://$testDomain")
                .build()
            val response = okHttpClient.newCall(request).execute()
            r.success("Pin verification successful. Connection to https://$testDomain established. Response code was: ${response.code}.")
        } catch (e: Exception) {
            r.fail("Connection to https://$testDomain could not be established.")
            r.fail(e.toString())
        }
        return r.toJsonString()
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    fun okHttpCertificatePinnerInvalid(): String {
        val r = ReturnStatus()

        val testDomain = "invalidpin." + MasSettings.getTestDomain()

        val certificatePinner = CertificatePinner.Builder()
            //This is the invalid pin of the SwissSign ca
            .add(testDomain, "sha256/NDirQI6weuLiefh9EFjP0Rg8F7iLvBQE7fdD2e+j5r8=")
            .build()

        val okHttpClient = OkHttpClient.Builder()
            .certificatePinner(certificatePinner)
            .build()

        try {
            val request = Request.Builder()
                .url("https://$testDomain")
                .build()
            val response = okHttpClient.newCall(request).execute()
            r.success("Pin verification successful. Connection to https://$testDomain established. Response code was: ${response.code}.")
        } catch (e: Exception) {
            r.fail("Connection to https://$testDomain could not be established.")
            r.fail(e.toString())
        }
        return r.toJsonString()
    }


    private fun loadKeyStore(ks: KeyStore, fileId: Int) {
        context.resources.openRawResource(fileId).use { keyInput ->
            val certificateFactory = CertificateFactory.getInstance("X.509")
            val certificate = certificateFactory.generateCertificate(keyInput) as X509Certificate
            ks.setCertificateEntry("cert_${fileId}", certificate)
        }
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    fun x509TrustManager(): String {
        val r = ReturnStatus()
        val testDomain = MasSettings.getTestDomain()

        try {
            val keyStore = KeyStore.getInstance(KeyStore.getDefaultType())
            keyStore.load(null, null) // Create an empty KeyStore

            loadKeyStore(keyStore, R.raw.isrgrootx1)
            loadKeyStore(keyStore, R.raw.isrgrootx2)

            val trustManagerFactory =
                TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
            trustManagerFactory.init(keyStore)

            val sslContext = SSLContext.getInstance("TLS")
            sslContext.init(null, trustManagerFactory.trustManagers, SecureRandom())

            val sslSocketFactory = sslContext.socketFactory

            val url = URL("https://${testDomain}")
            val connection = url.openConnection() as HttpsURLConnection
            connection.sslSocketFactory = sslSocketFactory

            connection.requestMethod = "GET"
            connection.setRequestProperty("User-Agent", "MAS-Reference-App")
            connection.connect()

            val inputStream = connection.inputStream
            val response = inputStream.bufferedReader().use { it.readText() }
            connection.disconnect()

            r.success("Pin verification successful. Connection to https://$testDomain established. Response code was: ${connection.responseCode}.")
        } catch (e: Exception) {
            r.fail("Connection to https://$testDomain could not be established.")
            r.fail(e.toString())
        }
        return r.toJsonString()
    }


    @ReactMethod(isBlockingSynchronousMethod = true)
    fun x509TrustManagerInvalid(): String {
        val r = ReturnStatus()
        val testDomain = "invalidpin.${MasSettings.getTestDomain()}"

        try {
            val keyStore = KeyStore.getInstance(KeyStore.getDefaultType())
            keyStore.load(null, null)

            // leave the KeyStore empty on purpose

            val trustManagerFactory =
                TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
            trustManagerFactory.init(keyStore)

            val sslContext = SSLContext.getInstance("TLS")
            sslContext.init(null, trustManagerFactory.trustManagers, SecureRandom())

            val sslSocketFactory = sslContext.socketFactory

            val url = URL("https://${testDomain}")
            val connection = url.openConnection() as HttpsURLConnection
            connection.sslSocketFactory = sslSocketFactory
            connection.connect()
            connection.disconnect()
            r.success("Pin verification successful. Connection to https://$testDomain established. Response code was: ${connection.responseCode}.")
        } catch (e: Exception) {
            r.fail("Connection to https://$testDomain could not be established.")
            r.fail(e.toString())
        }
        return r.toJsonString()
    }

    @ReactMethod
    fun webViewPinning(promise: Promise) {
        val r = ReturnStatus()
        val testDomain = MasSettings.getTestDomain()

        // These are the valid pins of the two Let's Encrypt root ca's
        val pins = arrayOf(
            "sha256/diGVwiVYbubAI3RW4hB9xU8e/CH2GnkuvVFZE8zmgzI=",
            "sha256/C5+lpZ7tcVwmwQIMcRtPbsQtWLABXhQzejna0wHFr8M="
        )
        val wv = WebView(context)
        wv.webViewClient = PinningWebViewClient(testDomain, pins, promise, r)
        wv.loadUrl("https://$testDomain")
    }

    @ReactMethod
    fun webViewPinningInvalid(promise: Promise) {
        val r = ReturnStatus()
        val testDomain = MasSettings.getTestDomain()

        //This is the invalid pin of the SwissSign ca
        val pins = arrayOf(
            "sha256/NDirQI6weuLiefh9EFjP0Rg8F7iLvBQE7fdD2e+j5r8="
        )
        val wv = WebView(context)
        wv.webViewClient = PinningWebViewClient(testDomain, pins, promise, r)
        wv.loadUrl("https://invalid.$testDomain")
    }


    //@method
}