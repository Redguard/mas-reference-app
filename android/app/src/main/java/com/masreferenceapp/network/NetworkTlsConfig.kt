package com.masreferenceapp.network

import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.masreferenceapp.MasSettings
import com.masreferenceapp.ReturnStatus
import javax.net.ssl.SSLParameters
import javax.net.ssl.SSLSocket
import javax.net.ssl.SSLSocketFactory


class NetworkTlsConfig(var context: ReactApplicationContext) : ReactContextBaseJavaModule(context) {
    override fun getName(): String {
        return "NetworkTlsConfig"
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    fun oldTlsConfig(): String {
        val testDomain = MasSettings.getData("testDomain")

        val status = StringBuffer()
        val factory = SSLSocketFactory.getDefault() as SSLSocketFactory
        var socket: SSLSocket? = null
        val r = ReturnStatus()

        try {
            socket = factory.createSocket(testDomain, 443) as SSLSocket
            try {
                socket.enabledProtocols = arrayOf("TLSv1")
            } catch (e: Exception) {
                r.addStatus("FAIL", e.toString())
            }
            try {
                socket = factory.createSocket(testDomain, 443) as SSLSocket
                // second way to set older protocols
                val params = SSLParameters()
                params.protocols = arrayOf("TLSv1")
                socket.sslParameters = params
            } catch (e: Exception) {
                r.addStatus("FAIL", e.toString())
            }
            socket!!.startHandshake()
            socket.close()
            r.addStatus("[OK]", "TLS socket with TLSv1 created.")
        } catch (e: Exception) {
            r.addStatus("FAIL", e.toString())
        }
        return r.toJsonString()
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    fun insecureCipherSuties(): String {
        val testDomain = MasSettings.getData("testDomain")

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
        val factory = SSLSocketFactory.getDefault() as SSLSocketFactory
        var socket: SSLSocket? = null
        val r = ReturnStatus()
        try {
            socket = factory.createSocket(testDomain, 443) as SSLSocket
            try {
                socket.enabledCipherSuites = ciphers
            } catch (e: Exception) {
                r.addStatus("FAIL", e.toString())
            }
            try {
                socket = factory.createSocket(testDomain, 443) as SSLSocket
                // second way to set older protocols
                val params = SSLParameters(ciphers, null)
                socket.sslParameters = params
            } catch (e: Exception) {
                r.addStatus("FAIL", e.toString())
            }
            socket!!.startHandshake()
            socket.close()

            r.addStatus("[OK]", "TLS socket with insecure ciphersuite created.")

            status.append("[OK]")
        } catch (e: Exception) {
            r.addStatus("FAIL", e.toString())
        }
        return r.toJsonString()
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    fun clientCertificate(): String {

        // load cert to keystore/keychain
        // configure TLS client to use it
        val r = ReturnStatus("OK", "Android code stub")
        return r.toJsonString()
    }

        
    @ReactMethod(isBlockingSynchronousMethod = true)
    fun acceptBadTLS(): String {
        val r = ReturnStatus("OK", "Android code stub.")
        return r.toJsonString()
    }

    //@method

}