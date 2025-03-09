package com.insomnihack.utils
import java.util.Base64
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

class NetworkHelpers {

    // We deliberately use hard coded secrets and outdated javas crypto to de-obfuscate strings
    private val keyHex = "8c00e2cc664423d42eb34496bde93456d6e2f9bdfc578c602f31ebbbd7c5b4b7"
    private val xorMaskHex = "57dec105cdf828aab759713e0a7dbc9934aa14e13205bb058fdaec52262729d7"
    private val xorMask: ByteArray = hexStringToByteArray(xorMaskHex)
    private val rawKey: ByteArray = hexStringToByteArray(keyHex)
    private val obfuscatedKey: String = generateObfuscatedKey(rawKey, xorMask)

    private fun getDeobfuscatedKey(): ByteArray {
        val decodedKey = Base64.getDecoder().decode(obfuscatedKey)
        val deobfuscatedKey = ByteArray(decodedKey.size)

        for (i in decodedKey.indices) {
            deobfuscatedKey[i] = (decodedKey[i].toInt() xor xorMask[i % xorMask.size].toInt()).toByte()
        }
        return deobfuscatedKey
    }

    fun decode(ciphertext: String): String {
        val key = SecretKeySpec(getDeobfuscatedKey(), "RC4")
        val cipher = Cipher.getInstance("RC4")
        cipher.init(Cipher.DECRYPT_MODE, key)
        val encryptedBytes = Base64.getDecoder().decode(ciphertext)
        val decryptedBytes = cipher.doFinal(encryptedBytes)
        return String(decryptedBytes)
    }

    companion object {
        fun hexStringToByteArray(hex: String): ByteArray {
            val result = ByteArray(hex.length / 2)
            for (i in hex.indices step 2) {
                result[i / 2] = ((hex[i].digitToInt(16) shl 4) + hex[i + 1].digitToInt(16)).toByte()
            }
            return result
        }
        fun generateObfuscatedKey(rawKey: ByteArray, xorMask: ByteArray): String {
            val obfuscatedKey = ByteArray(rawKey.size)

            for (i in rawKey.indices) {
                obfuscatedKey[i] = (rawKey[i].toInt() xor xorMask[i % xorMask.size].toInt()).toByte()
            }
            return Base64.getEncoder().encodeToString(obfuscatedKey)
        }
    }
}