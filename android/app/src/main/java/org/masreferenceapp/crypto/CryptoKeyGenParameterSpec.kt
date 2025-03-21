package org.masreferenceapp.crypto

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import org.masreferenceapp.ReturnStatus
import java.time.LocalDate
import java.time.ZoneId
import java.util.Date
import kotlin.random.Random


class CryptoKeyGenParameterSpec(var context: ReactApplicationContext) : ReactContextBaseJavaModule(
    context
) {
    override fun getName(): String {
        return "CryptoKeyGenParameterSpec"
    }


    @ReactMethod(isBlockingSynchronousMethod = true)
    fun setAttestationChallenge(): String {
        val b = KeyGenParameterSpec.Builder(
            "testKeyGenParameter",
            KeyProperties.PURPOSE_ENCRYPT
        )
        b.setAttestationChallenge(Random.nextBytes(16))
        return ReturnStatus("OK", "Attestation challenge set.").toJsonString()
    }


    @ReactMethod(isBlockingSynchronousMethod = true)
    fun setInsecureBlockMode(): String {
        val b = KeyGenParameterSpec.Builder(
            "testKeyGenParameter",
            KeyProperties.PURPOSE_ENCRYPT
        )
        b.setBlockModes(KeyProperties.BLOCK_MODE_ECB)
        return ReturnStatus("OK", "Block mode ECB set.").toJsonString()
    }


    @ReactMethod(isBlockingSynchronousMethod = true)
    fun setInsecureDigest(): String {
        val b = KeyGenParameterSpec.Builder(
            "testKeyGenParameter",
            KeyProperties.PURPOSE_ENCRYPT
        )

        val r = ReturnStatus()


        b.setDigests(KeyProperties.DIGEST_MD5)
        r.addStatus("OK", "Digest MD5 set.")
        b.setDigests(KeyProperties.DIGEST_SHA1)
        r.addStatus("OK", "Digest SHA-1 set.")
        b.setDigests(KeyProperties.DIGEST_NONE)
        r.addStatus("OK", "Digest NONE set.")

        return r.toJsonString()
    }


    @ReactMethod(isBlockingSynchronousMethod = true)
    fun setInsecureEncryptionPadding(): String {
        val b = KeyGenParameterSpec.Builder(
            "testKeyGenParameter",
            KeyProperties.PURPOSE_ENCRYPT
        )

        val r = ReturnStatus()

        b.setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
        r.addStatus("OK", "Encryption padding NONE set.")
        b.setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_RSA_PKCS1)
        r.addStatus("OK", "Encryption padding PKCS1 set.")

        return r.toJsonString()
    }


    @ReactMethod(isBlockingSynchronousMethod = true)
    fun setInsecureSignaturePadding(): String {
        val b = KeyGenParameterSpec.Builder(
            "testKeyGenParameter",
            KeyProperties.PURPOSE_ENCRYPT
        )

        val r = ReturnStatus()

        b.setSignaturePaddings(KeyProperties.SIGNATURE_PADDING_RSA_PKCS1)
        r.addStatus("OK", "Signature padding PKCS1 set.")

        return r.toJsonString()
    }


    @ReactMethod(isBlockingSynchronousMethod = true)
    fun setWeakKey(): String {
        val b = KeyGenParameterSpec.Builder(
            "testKeyGenParameter",
            KeyProperties.PURPOSE_ENCRYPT
        )

        val r = ReturnStatus()

        b.setKeySize(56)
        r.addStatus("OK", "Key size set to 56 bit (DES)")

        b.setKeySize(64)
        r.addStatus("OK", "Key size set to 64 bit (3DES)")

        b.setKeySize(80)
        r.addStatus("OK", "Key size set to 80 bit (legacy encryption)")

        b.setKeySize(512)
        r.addStatus("OK", "Key size set to 512 bit (RSA)")

        b.setKeySize(1024)
        r.addStatus("OK", "Key size set to 1024 bit (RSA)")

        return r.toJsonString()
    }


    @ReactMethod(isBlockingSynchronousMethod = true)
    fun setLongCertValidity(): String {
        val b = KeyGenParameterSpec.Builder(
            "testKeyGenParameter",
            KeyProperties.PURPOSE_ENCRYPT
        )
        val localDate = LocalDate.of(2100, 10, 10)
        val date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant())

        b.setCertificateNotAfter(date)
        return ReturnStatus("OK", "Set cert validity to not valid after 2100-10-10.").toJsonString()
    }


    @ReactMethod(isBlockingSynchronousMethod = true)
    fun setLongKeyValidity(): String {
        val b = KeyGenParameterSpec.Builder(
            "testKeyGenParameter",
            KeyProperties.PURPOSE_ENCRYPT
        )
        val localDate = LocalDate.of(2100, 10, 10)
        val date = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant())

        b.setKeyValidityEnd(date)
        return ReturnStatus("OK", "Set key validity to not valid after 2100-10-10.").toJsonString()
    }


    @ReactMethod(isBlockingSynchronousMethod = true)
    fun dontInvalidateBioEnrollment(): String {
        val b = KeyGenParameterSpec.Builder(
            "testKeyGenParameter",
            KeyProperties.PURPOSE_ENCRYPT
        )
        b.setInvalidatedByBiometricEnrollment(false)
        return ReturnStatus(
            "OK",
            "Key will not be invalidated if new biometric is enrolled."
        ).toJsonString()
    }


    @ReactMethod(isBlockingSynchronousMethod = true)
    fun setExportable(): String {
        val b = KeyGenParameterSpec.Builder(
            "testKeyGenParameter",
            KeyProperties.PURPOSE_ENCRYPT
        )
        b.setIsStrongBoxBacked(false)
        return ReturnStatus(
            "OK",
            "Key can be exported, as it is not backed by StrongBox."
        ).toJsonString()
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    fun setRandomizedEncryptionRequiredFalse(): String {
        val b = KeyGenParameterSpec.Builder(
            "testKeyGenParameter",
            KeyProperties.PURPOSE_ENCRYPT
        )
        b.setRandomizedEncryptionRequired(false)
        return ReturnStatus("OK", "Randomized encryption required set to false.").toJsonString()
    }


    @ReactMethod(isBlockingSynchronousMethod = true)
    fun setUnlockedDeviceRequiredTrue(): String {
        val b = KeyGenParameterSpec.Builder(
            "testKeyGenParameter",
            KeyProperties.PURPOSE_ENCRYPT
        )
        b.setUnlockedDeviceRequired(true)
        return ReturnStatus("OK", "Unlocked device required set to true.").toJsonString()
    }


    @ReactMethod(isBlockingSynchronousMethod = true)
    fun requireUserAuthentication(): String {
        val b = KeyGenParameterSpec.Builder(
            "testKeyGenParameter",
            KeyProperties.PURPOSE_ENCRYPT
        )
        b.setUserAuthenticationRequired(true)
        return ReturnStatus("OK", "Authentication required set to true.").toJsonString()
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    fun configureUserAuth(): String {
        val b = KeyGenParameterSpec.Builder(
            "testKeyGenParameter",
            KeyProperties.PURPOSE_ENCRYPT
        )
        val r = ReturnStatus()
        b.setUserAuthenticationParameters(0, KeyProperties.AUTH_BIOMETRIC_STRONG)
        r.success("Configured that user must authenticate each time before accessing the key and STRONG BIOMETRY is required.")

        b.setUserAuthenticationParameters(0, KeyProperties.AUTH_DEVICE_CREDENTIAL)
        r.success("Configured that user must authenticate each time before accessing the key and DEVICE CREDENTIAL is required.")
        return r.toJsonString()
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    fun configureUserAuthLegacy(): String {
        val b = KeyGenParameterSpec.Builder(
            "testKeyGenParameter",
            KeyProperties.PURPOSE_ENCRYPT
        )
        b.setUserAuthenticationValidityDurationSeconds(-1)
        return ReturnStatus(
            "OK",
            "Configured that user must authenticate each time before accessing the key."
        ).toJsonString()
    }

    //@method
}