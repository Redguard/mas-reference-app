package com.masreferenceapp.platform

import android.os.Handler
import android.os.Looper
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import com.facebook.react.bridge.Promise
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.masreferenceapp.MasSettings
import com.masreferenceapp.ReturnStatus
import com.masreferenceapp.platform.helpers.WebViewJavaScriptBridge

class PlatformWebView(var context: ReactApplicationContext) : ReactContextBaseJavaModule(context) {
    private val localWebViewDomain = "file:///android_asset/masTestPage.html"
    private val localWebViewJavaScriptBridge = "file:///android_asset/masJavaScriptBridge.html"

    override fun getName(): String {
        return "PlatformWebView"
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    fun loadLocalResource(): String {
        Handler(Looper.getMainLooper()).post {
            val wv = WebView(context)
            val headers: MutableMap<String, String> = HashMap()
            headers["X-a"] = "someHeader"
            headers["X-b"] = "anotherHeader"
            wv.loadUrl(localWebViewDomain, headers)
            wv.loadUrl(localWebViewDomain)
            wv.destroy()
        }
        return ReturnStatus(
            "OK",
            "Resource loaded. URL of the WebView: $localWebViewDomain"
        ).toJsonString()
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    fun loadRemoteHttpResource(): String {
        val testDomain = MasSettings.getTestDomain()
        val url = "http://$testDomain"
        Handler(Looper.getMainLooper()).post {
            val wv = WebView(context)
            val headers: MutableMap<String, String> = HashMap()
            headers["X-a"] = "someHeader"
            headers["X-b"] = "anotherHeader"
            wv.loadUrl(url, headers)
            wv.loadUrl(url)
            wv.destroy()
        }
        return ReturnStatus("OK", "Resource loaded. URL of the WebView: $url").toJsonString()
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    fun loadRemoteHttpsResource(): String {
        val testDomain = MasSettings.getTestDomain()
        val url = "https://$testDomain"
        Handler(Looper.getMainLooper()).post {
            val wv = WebView(context)
            val headers: MutableMap<String, String> = HashMap()
            headers["X-a"] = "someHeader"
            headers["X-b"] = "anotherHeader"
            wv.loadUrl(url, headers)
            wv.loadUrl(url)
            wv.destroy()
        }
        return ReturnStatus("OK", "Resource loaded. URL of the WebView: $url").toJsonString()
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    fun allowFileAccess(): String {
        Handler(Looper.getMainLooper()).post {
            val wv = WebView(context)
            wv.loadUrl(localWebViewDomain)
            wv.settings.allowFileAccess = true
            wv.destroy()
        }
        return ReturnStatus(
            "OK",
            "AllowFileAccess set. URL of the WebView: $localWebViewDomain"
        ).toJsonString()
    }

    @ReactMethod
    fun sendDataToJsSandbox(promise: Promise) {
        val r = ReturnStatus()
        Handler(Looper.getMainLooper()).post {
            val wv = WebView(context)
            wv.settings.javaScriptEnabled = true
            wv.loadUrl(localWebViewJavaScriptBridge)
            wv.webViewClient = object : WebViewClient() {
                override fun onPageFinished(view: WebView, url: String) {
                    view.evaluateJavascript("receiveCallFromAndroid()") { value ->
                        println(value)
                        r.success("Native method called JavaScript function. It returned the following value: $value")
                        promise.resolve(r.toJsonString())
                    }
                }
            }
        }
    }

    @ReactMethod
    fun readDataFromJsSandbox(promise: Promise) {
        Handler(Looper.getMainLooper()).post {
            val r = ReturnStatus()
            val wv = WebView(context)
            wv.settings.javaScriptEnabled = true
            wv.addJavascriptInterface(WebViewJavaScriptBridge(promise, r), "javascriptBridge")
            wv.loadUrl(localWebViewJavaScriptBridge)
        }
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    fun enableGeolocation(): String {
        Handler(Looper.getMainLooper()).post {
            val wv = WebView(context)
            wv.loadUrl(localWebViewDomain)
            wv.settings.setGeolocationEnabled(true)
            wv.destroy()
        }
        return ReturnStatus(
            "OK",
            "Geolocation enabled. URL of the WebView: $localWebViewDomain"
        ).toJsonString()
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    fun allowMixedContent(): String {
        Handler(Looper.getMainLooper()).post {
            val wv = WebView(context)
            wv.loadUrl(localWebViewDomain)
            wv.settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
            wv.destroy()
        }
        return ReturnStatus(
            "OK",
            "MixedContent allowed. URL of the WebView: $localWebViewDomain"
        ).toJsonString()
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    fun remoteDebugging(): String {
        WebView.setWebContentsDebuggingEnabled(true)
        val r = ReturnStatus("OK", "WebView set to debuggable.")
        return r.toJsonString()
    }

    //@method

}