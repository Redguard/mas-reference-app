package com.masreferenceapp.auth.helpers

import android.webkit.HttpAuthHandler
import android.webkit.WebView
import android.webkit.WebViewClient

class HttpBasicAuthWebViewClient: WebViewClient(){
    override fun onReceivedHttpAuthRequest(
        view: WebView?,
        handler: HttpAuthHandler?,
        host: String?,
        realm: String?
    ) {
        val username = "mas"
        val password = "Passw0rd"
        handler?.proceed(username, password)
    }
}