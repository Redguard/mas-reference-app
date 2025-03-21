package org.masreferenceapp.crypto

import android.os.Build
import androidx.annotation.RequiresApi
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import org.masreferenceapp.ReturnStatus
import java.security.SecureRandom
import java.util.HexFormat
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec


class CryptoSecretKeyFactory(var context: ReactApplicationContext) : ReactContextBaseJavaModule(
    context
) {
    override fun getName(): String {
        return "CryptoSecretKeyFactory"
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    fun weakSecretKeyFactoryAlgorithms(): String {
        val r = ReturnStatus()

        val algorithms = arrayOf(
            "DES",
            "DESede",
            "HmacSHA1",
            "PBEwithHmacSHA1",
            "PBEwithMD5AND128BITAES-CBC-OPENSSL",
            "PBEwithMD5AND192BITAES-CBC-OPENSSL",
            "PBEwithMD5AND256BITAES-CBC-OPENSSL",
            "PBEwithMD5ANDDES",
            "PBEwithMD5ANDRC2",
            "PBEwithSHA1ANDDES",
            "PBEwithSHA1ANDRC2",
            "PBEwithSHAAND128BITRC2-CBC",
            "PBEwithSHAAND128BITRC4",
            "PBEwithSHAAND40BITRC2-CBC",
            "PBEwithSHAAND40BITRC4",
            "PBEwithSHAANDTWOFISH-CBC",
        )

        for (a in algorithms) {
            try {
                val secretKeyFactory = SecretKeyFactory.getInstance(a)
                r.success("Created SecretKeyFactory:  " + secretKeyFactory.algorithm)
            } catch (e: Exception) {
                r.fail(e.toString())
            }
        }
        return r.toJsonString()
    }


    @RequiresApi(Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    @ReactMethod(isBlockingSynchronousMethod = true)
    fun lowIterationPBKDF2(): String {

        val random = SecureRandom.getInstance("SHA1PRNG")
        val salt = ByteArray(16)
        random.nextBytes(salt)
        val password = "Passw0rd".toCharArray()
        val iterations = 1
        val keyLength = 256

        val factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256")
        val spec = PBEKeySpec(password, salt, iterations, keyLength)

        val key = HexFormat.of().formatHex(factory.generateSecret(spec).encoded)

        val r = ReturnStatus()
        r.success("Created a PBKDF2 stretched key with 1 iteration. Key is : ${key}")
        return r.toJsonString()
    }

    //@method
}