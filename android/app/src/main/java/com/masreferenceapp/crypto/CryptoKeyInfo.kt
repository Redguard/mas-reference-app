package com.masreferenceapp.crypto

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyInfo
import android.security.keystore.KeyProperties
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.masreferenceapp.ReturnStatus
import java.security.KeyStore
import javax.crypto.KeyGenerator
import javax.crypto.SecretKeyFactory


class CryptoKeyInfo(var context: ReactApplicationContext) : ReactContextBaseJavaModule(
    context
) {
    override fun getName(): String {
        return "CryptoKeyInfo"
    }

    private fun createKey(): KeyInfo {
        val keyAlias = "testAesKey"
        val keyStore = KeyStore.getInstance("AndroidKeyStore").apply {
            load(null)
        }
        val keyGenerator =
            KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore")
        val keyGenParameterSpec = KeyGenParameterSpec.Builder(
            keyAlias,
            KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
        ).apply {
            setBlockModes(KeyProperties.BLOCK_MODE_GCM)
            setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
            setKeySize(256)
        }.build()
        keyGenerator.init(keyGenParameterSpec)
        keyGenerator.generateKey()

        val keyEntry = keyStore.getEntry(keyAlias, null) as KeyStore.SecretKeyEntry
        val secretKey = keyEntry.secretKey
        val factory = SecretKeyFactory.getInstance(secretKey.algorithm, "AndroidKeyStore")

        return factory.getKeySpec(secretKey, KeyInfo::class.java) as KeyInfo
    }

    private fun deleteKey() {
        val keyAlias = "testAesKey"
        val keyStore = KeyStore.getInstance("AndroidKeyStore").apply {
            load(null)
        }
        keyStore.deleteEntry(keyAlias)
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    fun getSecurityLevel(): String {
        val ki = createKey()

        val securityLevel = ki.securityLevel

        deleteKey()

        val r = ReturnStatus("OK", "SecurityLevel of key is: $securityLevel")
        return r.toJsonString()
    }


    @ReactMethod(isBlockingSynchronousMethod = true)
    fun isInsideSecureHardware(): String {
        val ki = createKey()

        val securityLevel = ki.isInsideSecureHardware

        deleteKey()

        val r = ReturnStatus("OK", "Is key in secure hardware: $securityLevel")
        return r.toJsonString()
    }

    //@method
}