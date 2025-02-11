package com.masreferenceapp.network.helpers

import android.webkit.JavascriptInterface
import com.facebook.react.bridge.Promise
import com.masreferenceapp.ReturnStatus

class WebViewJavaScriptBridge (
    private val testDomain: String,
    private val promise: Promise,
    private val r: ReturnStatus
){
    @JavascriptInterface
    fun wsResponseReceived(dataFromJavaScript: String?) {
        r.success("Connection to $testDomain established. Received data: ${dataFromJavaScript}.")
        promise.resolve(r.toJsonString())
    }

    @JavascriptInterface
    fun wsErrorOccurred(dataFromJavaScript: String?) {
        r.fail("There was an error: ${dataFromJavaScript}.")
        promise.resolve(r.toJsonString())
    }
}
