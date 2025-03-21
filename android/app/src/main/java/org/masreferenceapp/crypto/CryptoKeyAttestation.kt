package org.masreferenceapp.crypto

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import org.masreferenceapp.ReturnStatus
import java.security.KeyPairGenerator
import java.security.KeyStore
import java.security.cert.X509Certificate


class CryptoKeyAttestation(var context: ReactApplicationContext) : ReactContextBaseJavaModule(
    context
) {
    override fun getName(): String {
        return "CryptoKeyAttestation"
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    fun getCertificateChain(): String {
        var kpg: KeyPairGenerator? = null
        val r = ReturnStatus()

        try {
            kpg = KeyPairGenerator.getInstance(
                KeyProperties.KEY_ALGORITHM_EC, "AndroidKeyStore"
            )
            kpg.initialize(
                KeyGenParameterSpec.Builder(
                    "masAttestationTestKey",
                    KeyProperties.PURPOSE_SIGN
                ).setAttestationChallenge("randomAttestationChallenge".toByteArray())
                    .build()
            )
            val kp = kpg.generateKeyPair()

            val ks = KeyStore.getInstance("AndroidKeyStore")
            ks.load(null)
            val attestationCerts = ks.getCertificateChain("masAttestationTestKey")

            r.success("Certificate chain for key retrieved.")

            for (attestationCert in attestationCerts) {
                val x509cert: X509Certificate = attestationCert as X509Certificate
                r.success("Current certificate in chain is $x509cert")
            }

            return r.toJsonString()
        } catch (e: Exception) {
            r.fail(e.toString())
            return r.toJsonString()
        }
    }
}