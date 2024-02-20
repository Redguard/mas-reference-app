package com.masreferenceapp.network;
import javax.net.ssl.*;

import java.io.IOException;
import java.security.*;
import java.util.Arrays;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.masreferenceapp.Constants;
import com.masreferenceapp.Status;


public class NetworkTlsConfig extends ReactContextBaseJavaModule {
    ReactApplicationContext context;

    public NetworkTlsConfig(ReactApplicationContext context) {
        super(context);
        this.context = context;
    }

    @NonNull
    @Override
    public String getName() {
        return "NetworkTlsConfig";
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String oldTlsConfig(){
        StringBuffer status = new StringBuffer();
        StringBuffer message = new StringBuffer();

        SSLSocketFactory factory = (SSLSocketFactory)SSLSocketFactory.getDefault();

        SSLSocket  socket = null;
        try {
            socket = (SSLSocket)factory.createSocket(Constants.remoteWebViewHttpsDomain, 443);
            try {
                socket.setEnabledProtocols(new String[] {"TLSv1"});

            } catch (Exception e) {
                status.append("[FAIL]");
                message.append(e.toString() + ", ");
            }
            try {
                socket = (SSLSocket)factory.createSocket(Constants.remoteWebViewHttpsDomain, 443);
                // second way to set older protocols
                SSLParameters params = new SSLParameters();
                params.setProtocols(new String[] {"TLSv1"});
                socket.setSSLParameters(params);

            } catch (Exception e) {
                status.append("[FAIL]");
                message.append(e.toString() + ", ");
            }

            socket.startHandshake();
            socket.close();
            message.append(socket.toString());
            status.append("[OK]");

        } catch (Exception e) {
            status.append("[FAIL]");
            message.append(e.toString() + ", ");
        }
        return Status.status(status.toString(), message.toString());
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String insecureCipherSuties(){

        String[] ciphers = new String[] {
                "TLS_RSA_WITH_AES_128_CBC_SHA",
                "SSL_DHE_DSS_EXPORT_WITH_DES40_CBC_SHA",
                "SSL_DHE_RSA_WITH_3DES_EDE_CBC_SHA",
                "TLS_DH_anon_WITH_AES_128_GCM_SHA256",
                "TLS_ECDHE_ECDSA_WITH_RC4_128_SHA",
                "TLS_ECDHE_RSA_WITH_RC4_128_SHA",
                "SSL_RSA_EXPORT_WITH_RC2_CBC_40_MD5",
        };

        StringBuffer status = new StringBuffer();
        StringBuffer message = new StringBuffer();

        SSLSocketFactory factory = (SSLSocketFactory)SSLSocketFactory.getDefault();

        SSLSocket  socket = null;
        try {
            socket = (SSLSocket)factory.createSocket(Constants.remoteWebViewHttpsDomain, 443);
            try {
                socket.setEnabledCipherSuites(ciphers);

            } catch (Exception e) {
                status.append("[FAIL]");
                message.append(e.toString() + ", ");
            }
            try {
                socket = (SSLSocket)factory.createSocket(Constants.remoteWebViewHttpsDomain, 443);
                // second way to set older protocols
                SSLParameters params = new SSLParameters(ciphers, null);
                socket.setSSLParameters(params);

            } catch (Exception e) {
                status.append("[FAIL]");
                message.append(e.toString() + ", ");
            }

            socket.startHandshake();
            socket.close();
            message.append(socket.toString());
            status.append("[OK]");

        } catch (Exception e) {
            status.append("[FAIL]");
            message.append(e.toString() + ", ");
        }
        return Status.status(status.toString(), message.toString());
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String clientCertificate(){

        // load cert to keystore/keychain
        // configure TLS client to use it

        return Status.status("OK", "Message");
    }
}