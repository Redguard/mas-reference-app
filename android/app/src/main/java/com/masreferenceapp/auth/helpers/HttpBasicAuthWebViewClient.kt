package com.masreferenceapp.auth.helpers

import android.webkit.HttpAuthHandler
import android.webkit.WebView
import android.webkit.WebViewClient

class HttpBasicAuthWebView: WebViewClient(){
    override fun onReceivedHttpAuthRequest(
        view: WebView?,
        handler: HttpAuthHandler?,
        host: String?,
        realm: String?
    ) {
        // Provide the credentials
        val username = "mas"
        val password = "Passw0rd"

        // Use the handler to proceed with authentication
        handler?.proceed(username, password)

    }
}