package com.masreferenceapp.network

import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.masreferenceapp.Constants
import com.masreferenceapp.Status
import javax.net.ssl.SSLParameters
import javax.net.ssl.SSLSocket
import javax.net.ssl.SSLSocketFactory

class NetworkTlsConfig(var context: ReactApplicationContext) : ReactContextBaseJavaModule(context) {
    override fun getName(): String {
        return "NetworkTlsConfig"
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    fun oldTlsConfig(): String {
        val status = StringBuffer()
        val message = StringBuffer()
        val factory = SSLSocketFactory.getDefault() as SSLSocketFactory
        var socket: SSLSocket? = null
        try {
            socket = factory.createSocket(Constants.remoteWebViewHttpsDomain, 443) as SSLSocket
            try {
                socket!!.enabledProtocols = arrayOf("TLSv1")
            } catch (e: Exception) {
                status.append("[FAIL]")
                message.append("$e, ")
            }
            try {
                socket = factory.createSocket(Constants.remoteWebViewHttpsDomain, 443) as SSLSocket
                // second way to set older protocols
                val params = SSLParameters()
                params.protocols = arrayOf("TLSv1")
                socket!!.sslParameters = params
            } catch (e: Exception) {
                status.append("[FAIL]")
                message.append("$e, ")
            }
            socket!!.startHandshake()
            socket.close()
            message.append(socket.toString())
            status.append("[OK]")
        } catch (e: Exception) {
            status.append("[FAIL]")
            message.append("$e, ")
        }
        return Status.status(status.toString(), message.toString())
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    fun insecureCipherSuties(): String {
        val ciphers = arrayOf(
            "TLS_RSA_WITH_AES_128_CBC_SHA",
            "SSL_DHE_DSS_EXPORT_WITH_DES40_CBC_SHA",
            "SSL_DHE_RSA_WITH_3DES_EDE_CBC_SHA",
            "TLS_DH_anon_WITH_AES_128_GCM_SHA256",
            "TLS_ECDHE_ECDSA_WITH_RC4_128_SHA",
            "TLS_ECDHE_RSA_WITH_RC4_128_SHA",
            "SSL_RSA_EXPORT_WITH_RC2_CBC_40_MD5"
        )
        val status = StringBuffer()
        val message = StringBuffer()
        val factory = SSLSocketFactory.getDefault() as SSLSocketFactory
        var socket: SSLSocket? = null
        try {
            socket = factory.createSocket(Constants.remoteWebViewHttpsDomain, 443) as SSLSocket
            try {
                socket!!.enabledCipherSuites = ciphers
            } catch (e: Exception) {
                status.append("[FAIL]")
                message.append("$e, ")
            }
            try {
                socket = factory.createSocket(Constants.remoteWebViewHttpsDomain, 443) as SSLSocket
                // second way to set older protocols
                val params = SSLParameters(ciphers, null)
                socket!!.sslParameters = params
            } catch (e: Exception) {
                status.append("[FAIL]")
                message.append("$e, ")
            }
            socket!!.startHandshake()
            socket.close()
            message.append(socket.toString())
            status.append("[OK]")
        } catch (e: Exception) {
            status.append("[FAIL]")
            message.append("$e, ")
        }
        return Status.status(status.toString(), message.toString())
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    fun clientCertificate(): String {

        // load cert to keystore/keychain
        // configure TLS client to use it
        return Status.status("OK", "Message")
    }
}