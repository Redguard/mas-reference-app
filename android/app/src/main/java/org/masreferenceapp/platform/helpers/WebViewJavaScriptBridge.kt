package org.masreferenceapp.platform.helpers

import android.webkit.JavascriptInterface
import android.webkit.WebView
import com.facebook.react.bridge.Promise
import org.masreferenceapp.ReturnStatus

class WebViewJavaScriptBridge(
    private val promise: Promise,
    private val r: ReturnStatus
) {
    @JavascriptInterface
    fun customJsBridge(dataFromJavaScript: String?) {
        r.success("JavaScript called this native method and passed the following data: $dataFromJavaScript")
        promise.resolve(r.toJsonString())
    }
}
