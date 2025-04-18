package org.masreferenceapp.auth

import android.os.Handler
import android.os.Looper
import android.webkit.WebView
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import org.masreferenceapp.MasSettings
import org.masreferenceapp.ReturnStatus
import org.masreferenceapp.auth.helpers.HttpBasicAuthWebViewClient
import java.net.Authenticator
import java.net.HttpURLConnection
import java.net.PasswordAuthentication
import java.net.URL

open class AuthHttpBasicAuth(var context: ReactApplicationContext) : ReactContextBaseJavaModule(
    context
) {
    override fun getName(): String {
        return "AuthHttpBasicAuth"
    }


    @ReactMethod(isBlockingSynchronousMethod = true)
    fun javaNet(): String {
        try {
            val url = URL("https://" + MasSettings.getTestDomain() + "/basicAuth.html")

            Authenticator.setDefault(object : Authenticator() {
                override fun getPasswordAuthentication(): PasswordAuthentication {
                    return PasswordAuthentication("mas", "Passw0rd".toCharArray())
                }
            })

            val c = url.openConnection() as HttpURLConnection
            c.connect()

            val r = ReturnStatus(
                "OK",
                "Successfully connected to the URL $url. Response code was: ${c.responseCode}"
            )
            return r.toJsonString()
        } catch (e: Exception) {
            val r = ReturnStatus("FAIL", "Exception: $e")
            return r.toJsonString()
        }
    }


    @ReactMethod(isBlockingSynchronousMethod = true)
    fun webView(): String {

        val testDomain = MasSettings.getTestDomain()

        Handler(Looper.getMainLooper()).post {
            val wv = WebView(context)
            wv.webViewClient = HttpBasicAuthWebViewClient()
            wv.loadUrl("https://$testDomain/basicAuth.html")
            wv.destroy()
        }

        return ReturnStatus(
            "OK",
            "Used WebView to connect to $testDomain using HTTP Basic Access Authentication."
        ).toJsonString()

    }

    //@method
}