package com.masreferenceapp.platform;

import static android.webkit.WebSettings.MIXED_CONTENT_ALWAYS_ALLOW;
import static com.masreferenceapp.Constants.localWebViewDomain;
import static com.masreferenceapp.Constants.localWebViewJavaScriptBridge;
import static com.masreferenceapp.Constants.remoteWebViewHttpDomain;

import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.masreferenceapp.Status;
import com.masreferenceapp.platform.helpers.WebViewJavaScriptBridge;

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

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String loadRemoteHttpResource(){

        WebView wv = new WebView(context);

        Map<String, String> headers =  new HashMap<>();
        headers.put("X-a", "someHeader");
        headers.put("X-b", "anotherHeader");

        wv.loadUrl("http://"+remoteWebViewHttpDomain, headers);

        wv.loadUrl("http://"+remoteWebViewHttpDomain);

        return Status.status("OK", wv.toString());

    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String loadRemoteHttpsResource(){

        WebView wv = new WebView(context);

        Map<String, String> headers =  new HashMap<>();
        headers.put("X-a", "someHeader");
        headers.put("X-b", "anotherHeader");

        wv.loadUrl("https://"+remoteWebViewHttpDomain, headers);

        wv.loadUrl("https://"+remoteWebViewHttpDomain);

        return Status.status("OK", wv.toString());

    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String allowFileAccess(){

        WebView wv = new WebView(context);
        wv.loadUrl(localWebViewDomain);
        wv.getSettings().setAllowFileAccess(true);

        return Status.status("OK", wv.toString());

    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String sendDataToJsSandbox(){

        WebView wv = new WebView(context);

        wv.getSettings().setJavaScriptEnabled(true);

        wv.loadUrl(localWebViewJavaScriptBridge);

        wv.setWebViewClient(new WebViewClient() {
            public void onPageFinished(WebView view, String url) {
                view.evaluateJavascript("receiveCallFromAndroid()", new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String value) {
                        System.out.println(value);
                    }
                });
            }
        });

        return Status.status("OK", wv.toString());
    }


    @ReactMethod(isBlockingSynchronousMethod = true)
    public String readDataFromJsSandbox(){

        WebView wv = new WebView(context);

        wv.getSettings().setJavaScriptEnabled(true);
        wv.addJavascriptInterface(new WebViewJavaScriptBridge(context), "javascriptBridge");
        wv.loadUrl(localWebViewJavaScriptBridge);

        return Status.status("OK", wv.toString());
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String enableGeolocation(){

        WebView wv = new WebView(context);
        wv.loadUrl(localWebViewDomain);
        wv.getSettings().setGeolocationEnabled(true);

        return Status.status("OK", wv.toString());

    }


    @ReactMethod(isBlockingSynchronousMethod = true)
    public String allowMixedContent(){

        WebView wv = new WebView(context);
        wv.loadUrl(localWebViewDomain);
        wv.getSettings().setMixedContentMode(MIXED_CONTENT_ALWAYS_ALLOW);

        return Status.status("OK", wv.toString());

    }
}