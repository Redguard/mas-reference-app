package com.masreferenceapp.platform.helpers;

import android.content.Context;
import android.webkit.JavascriptInterface;

public class WebViewJavaScriptBridge {
    Context mContext;

    public WebViewJavaScriptBridge(Context c) {
        mContext = c;
    }

    @JavascriptInterface
    public void customJsBridge(String dataFromJavaScript) {
        System.out.println(dataFromJavaScript);
    }
}
