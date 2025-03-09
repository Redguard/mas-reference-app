package com.insomnihack.utils
import java.util.Base64
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

class NetworkHelpers {

    // We deliberately use hard coded secrets and outdated javas crypto to de-obfuscate strings
    private val xorMaskHex = "57dec105cdf828aab759713e0a7dbc9934aa14e13205bb058fdaec52262729d7"
    private val obfuscatedKey: String = "dbde23c9abbc0b7e99ea35a8b79488cfe248ed5cce523765a0eb07e9f1e29d60"

    fun decode(ciphertext: String): String {
        val xorMask = hexStringToByteArray(xorMaskHex)
        val obfuscatedKeyBytes = hexStringToByteArray(obfuscatedKey)
        val key = SecretKeySpec(xorByteArrays(xorMask, obfuscatedKeyBytes), "RC4")
        val cipher = Cipher.getInstance("RC4")
        cipher.init(Cipher.DECRYPT_MODE, key)
        val encryptedBytes = Base64.getDecoder().decode(ciphertext)
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