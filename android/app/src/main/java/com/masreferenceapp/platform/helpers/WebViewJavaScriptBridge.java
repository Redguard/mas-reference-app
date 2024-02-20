package com.masreferenceapp.platform.helpers;

import android.content.Context;
import android.webkit.JavascriptInterface;

public class WebViewJavaScriptBridge {
    Context mContext;

    /** Instantiate the interface and set the context. */
    public WebViewJavaScriptBridge(Context c) {
        mContext = c;
    }

    /** Show a toast from the web page. */
    @JavascriptInterface
    public void customJsBridge(String dataFromJavaScript) {
        System.out.println(dataFromJavaScript);
    }
}
