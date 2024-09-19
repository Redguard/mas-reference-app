package com.masreferenceapp.platform;

import static android.webkit.WebSettings.MIXED_CONTENT_ALWAYS_ALLOW;
import static com.masreferenceapp.Constants.localWebViewDomain;
import static com.masreferenceapp.Constants.localWebViewJavaScriptBridge;
import static com.masreferenceapp.Constants.remoteHttpDomain;

import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.masreferenceapp.ReturnStatus;
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
        return new ReturnStatus("OK", "Resource loaded. URL of the WebView: " + wv.getUrl()).toJsonString();
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String loadRemoteHttpResource(){

        WebView wv = new WebView(context);

        Map<String, String> headers =  new HashMap<>();
        headers.put("X-a", "someHeader");
        headers.put("X-b", "anotherHeader");

        wv.loadUrl("http://"+ remoteHttpDomain, headers);

        wv.loadUrl("http://"+ remoteHttpDomain);

        return new ReturnStatus("OK", "Resource loaded. URL of the WebView: " + wv.getUrl()).toJsonString();

    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String loadRemoteHttpsResource(){

        WebView wv = new WebView(context);

        Map<String, String> headers =  new HashMap<>();
        headers.put("X-a", "someHeader");
        headers.put("X-b", "anotherHeader");

        wv.loadUrl("https://"+ remoteHttpDomain, headers);

        wv.loadUrl("https://"+ remoteHttpDomain);

        return new ReturnStatus("OK", "Resource loaded. URL of the WebView: " + wv.getUrl()).toJsonString();

    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String allowFileAccess(){

        WebView wv = new WebView(context);
        wv.loadUrl(localWebViewDomain);
        wv.getSettings().setAllowFileAccess(true);

        return new ReturnStatus("OK", "AllowFileAccess set. URL of the WebView: " + wv.getUrl()).toJsonString();

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

        return new ReturnStatus("OK", "Data to JS sandobx sent. URL of the WebView: " + wv.getUrl()).toJsonString();
    }


    @ReactMethod(isBlockingSynchronousMethod = true)
    public String readDataFromJsSandbox(){

        WebView wv = new WebView(context);

        wv.getSettings().setJavaScriptEnabled(true);
        wv.addJavascriptInterface(new WebViewJavaScriptBridge(context), "javascriptBridge");
        wv.loadUrl(localWebViewJavaScriptBridge);

        return new ReturnStatus("OK", "Data from JS sandobx read. URL of the WebView: " + wv.getUrl()).toJsonString();

    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String enableGeolocation(){

        WebView wv = new WebView(context);
        wv.loadUrl(localWebViewDomain);
        wv.getSettings().setGeolocationEnabled(true);

        return new ReturnStatus("OK", "Geolocation enabled. URL of the WebView: " + wv.getUrl()).toJsonString();

    }


    @ReactMethod(isBlockingSynchronousMethod = true)
    public String allowMixedContent(){

        WebView wv = new WebView(context);
        wv.loadUrl(localWebViewDomain);
        wv.getSettings().setMixedContentMode(MIXED_CONTENT_ALWAYS_ALLOW);

        return new ReturnStatus("OK", "MixedContent allowed. URL of the WebView: " + wv.getUrl()).toJsonString();

    }
}