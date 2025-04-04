package org.masreferenceapp.network

import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import org.masreferenceapp.MasSettings
import org.masreferenceapp.ReturnStatus
import java.net.HttpURLConnection
import java.net.URL
import javax.net.ssl.SSLParameters
import javax.net.ssl.SSLSocket
import javax.net.ssl.SSLSocketFactory


class NetworkTlsClientConfig(var context: ReactApplicationContext) :
    ReactContextBaseJavaModule(context) {
    override fun getName(): String {
        return "NetworkTlsClientConfig"
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    fun oldTlsConfig(): String {
        val testDomain = MasSettings.getTestDomain()

        val status = StringBuffer()
        val factory = SSLSocketFactory.getDefault() as SSLSocketFactory
        var socket: SSLSocket? = null
        val r = ReturnStatus()

        try {
            try {
                socket = factory.createSocket(testDomain, 443) as SSLSocket
                socket.enabledProtocols = arrayOf("TLSv1", "SSLv3", "TLSv1.1")
                socket.startHandshake()
                socket.close()
                r.success("TLS socket with outdated protocol versions created.")
            } catch (e: Exception) {
                r.fail(e.toString())
            }
            try {
                socket = factory.createSocket(testDomain, 443) as SSLSocket
                // second way to set older protocols
                val params = SSLParameters()
                params.protocols = arrayOf("TLSv1", "SSLv3", "TLSv1.1")
                socket.sslParameters = params
                socket.startHandshake()
                socket.close()
                r.success("TLS socket with outdated protocol versions created.")
            } catch (e: Exception) {
                r.fail(e.toString())
            }
        } catch (e: Exception) {
            r.fail(e.toString())
        }
        return r.toJsonString()
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    fun insecureCipherSuites(): String {
        val testDomain = MasSettings.getTestDomain()

        val ciphers = arrayOf(
            "TLS_RSA_WITH_AES_128_CBC_SHA",
            "SSL_DHE_DSS_EXPORT_WITH_DES40_CBC_SHA",
            "SSL_DHE_RSA_WITH_3DES_EDE_CBC_SHA",
            "TLS_DH_anon_WITH_AES_128_GCM_SHA256",
            "TLS_ECDHE_ECDSA_WITH_RC4_128_SHA",
            "TLS_ECDHE_RSA_WITH_RC4_128_SHA",
            "SSL_RSA_EXPORT_WITH_RC2_CBC_40_MD5"
        )
        val factory = SSLSocketFactory.getDefault() as SSLSocketFactory
        var socket: SSLSocket? = null
        val r = ReturnStatus()
        try {
            socket = factory.createSocket(testDomain, 443) as SSLSocket
            try {
                socket.enabledCipherSuites = ciphers
                socket.startHandshake()
                socket.close()
                r.success("TLS socket with insecure cipher suites created.")
            } catch (e: Exception) {
                r.fail(e.toString())
            }
            try {
                socket = factory.createSocket(testDomain, 443) as SSLSocket
                // second way to set older protocols
                val params = SSLParameters(ciphers, null)
                socket.sslParameters = params
                socket.startHandshake()
                socket.close()
                r.success("TLS socket with insecure cipher suites created.")

            } catch (e: Exception) {
                r.fail(e.toString())
            }
        } catch (e: Exception) {
            r.fail(e.toString())
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

        val badDomains = arrayOf(
            "https://expired.badssl.com/",
            "https://wrong.host.badssl.com/",
            "https://self-signed.badssl.com/",
            "https://untrusted-root.badssl.com/",
            "https://revoked.badssl.com/",
            "https://pinning-test.badssl.com/",
            "https://dh480.badssl.com/",
            "https://dh512.badssl.com/",
            "https://dh1024.badssl.com/",
            "https://dh-small-subgroup.badssl.com/",
            "https://dh-composite.badssl.com/",
            "https://rc4-md5.badssl.com/",
            "https://rc4.badssl.com/",
            "https://3des.badssl.com/",
            "https://null.badssl.com/",
            "https://sha1-2017.badssl.com/",
            "https://sha1-intermediate.badssl.com/",
            "https://invalid-expected-sct.badssl.com/"
        )

        val r = ReturnStatus()

        for (domain in badDomains) {
            try {
                val url = URL(domain)
                val connection = url.openConnection() as HttpURLConnection
                val responseCode = connection.responseCode
                r.success("App established connection to $domain . Response Code is $responseCode")

            } catch (e: Exception) {
                r.fail(e.toString())
            }
        }
        return r.toJsonString()
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    fun userTrustStore(): String {
        val r = ReturnStatus()
        r.success("This app also trusts the CAs from the user trust store. This is configured in the app's manifest (network_security_config.xml)")
        return r.toJsonString()
    }

    //@method

}