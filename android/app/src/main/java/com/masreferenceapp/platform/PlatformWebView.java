package com.masreferenceapp.platform;

import static com.masreferenceapp.Constants.localWebViewDomain;

import android.webkit.WebView;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.masreferenceapp.Status;

import java.util.HashMap;
import java.util.Map;


public class PlatformWebView extends ReactContextBaseJavaModule {
    ReactApplicationContext context;

    public PlatformWebView(ReactApplicationContext context) {
        super(context);
        this.context = context;
    }

    @NonNull
    @Override
    public String getName() {
        return "PlatformWebView";
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String loadLocalResource(){

        WebView wv = new WebView(context);

        Map<String, String> headers =  new HashMap<>();
        headers.put("X-a", "someHeader");
        headers.put("X-b", "anotherHeader");

        wv.loadUrl(localWebViewDomain, headers);

        wv.loadUrl(localWebViewDomain);

        return Status.status("OK", wv.toString());

    }
}