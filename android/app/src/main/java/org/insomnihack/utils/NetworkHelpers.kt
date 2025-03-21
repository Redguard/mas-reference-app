package org.insomnihack.utils
import android.util.Log
import java.security.cert.CertificateException
import java.util.Base64
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager
import java.security.cert.X509Certificate
import javax.net.ssl.SSLSocket

class NetworkHelpers {

    // We deliberately use hard coded secrets and outdated javas crypto to de-obfuscate strings
    // Key is obfuscated with the XOR mask m
    private val m = "57dec105cdf828aab759713e0a7dbc9934aa14e13205bb058fdaec52262729d7"
    private val key: String = "dbde23c9abbc0b7e99ea35a8b79488cfe248ed5cce523765a0eb07e9f1e29d60"

    fun decode(c: String): String {
        val xorMask = hexStringToByteArray(m)
        val obfuscatedKeyBytes = hexStringToByteArray(key)
        val key = SecretKeySpec(xorByteArrays(xorMask, obfuscatedKeyBytes), "RC4")
        val cipher = Cipher.getInstance("RC4")
        cipher.init(Cipher.DECRYPT_MODE, key)
        val encryptedBytes = Base64.getDecoder().decode(c)
        val decryptedBytes = cipher.doFinal(encryptedBytes)
        return String(decryptedBytes)
    }

    companion object {
        fun xorByteArrays(array1: ByteArray, array2: ByteArray): ByteArray {
            val result = ByteArray(array1.size)
            for (i in array1.indices) {
                result[i] = (array1[i].toInt() xor array2[i % array2.size].toInt()).toByte()
            }
            return result
        }

        fun hexStringToByteArray(hex: String): ByteArray {
            val result = ByteArray(hex.length / 2)
            for (i in hex.indices step 2) {
                result[i / 2] = ((hex[i].digitToInt(16) shl 4) + hex[i + 1].digitToInt(16)).toByte()
            }
            return result
        }
    }
}

class CustomTrustManager : X509TrustManager {
    override fun checkClientTrusted(chain: Array<out X509Certificate>?, authType: String?) {
    }
    val validIssuers = listOf(
        "CN=E5,O=Let's Encrypt,C=US",
        "CN=ISRG Root X1,O=Internet Security Research Group,C=US"
    )
    override fun checkServerTrusted(chain: Array<out X509Certificate>?, authType: String?) {
        if (chain == null || chain.isEmpty()) {
            throw CertificateException("Certificate chain is empty or null")
        }
        chain.forEach { cert ->
            if (cert.issuerDN.name in validIssuers) {
                Log.i("CTF", "Certificate is trusted: ${cert.issuerDN}")
                return
            }
        }
        throw CertificateException("Certificate issuer is not trusted. Your custom cert validator works.")
    }
    override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
}

fun createTLSSocket(host: String, port: Int): SSLSocket {
    val trustManagers = arrayOf<TrustManager>(CustomTrustManager())

    val sslContext = SSLContext.getInstance("TLS")
    sslContext.init(null, trustManagers, null)

    val sslSocketFactory = sslContext.socketFactory

    val socket = sslSocketFactory.createSocket(host, port) as SSLSocket
    socket.startHandshake()

    return socket
}
