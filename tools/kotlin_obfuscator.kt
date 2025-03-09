import java.util.Base64
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

class RC4Obfuscator {

    // Hardcoded hexadecimal string for the key
    private val keyHex = "8c00e2cc664423d42eb34496bde93456d6e2f9bdfc578c602f31ebbbd7c5b4b7"

    // Hardcoded hexadecimal string for the XOR mask
    private val xorMaskHex = "57dec105cdf828aab759713e0a7dbc9934aa14e13205bb058fdaec52262729d7"

    // Convert the hex strings into byte arrays
    private val xorMask: ByteArray = hexStringToByteArray(xorMaskHex)
    private val rawKey: ByteArray = hexStringToByteArray(keyHex)

    // Hardcoded obfuscated key (Base64 encoded XORed key)
    private val obfuscatedKey: String = generateObfuscatedKey(rawKey, xorMask)

    /**
     * De-obfuscates the key by reversing XOR and Base64 decoding.
     */
    private fun getDeobfuscatedKey(): ByteArray {
        val decodedKey = Base64.getDecoder().decode(obfuscatedKey)
        val deobfuscatedKey = ByteArray(decodedKey.size)

        for (i in decodedKey.indices) {
            deobfuscatedKey[i] = (decodedKey[i].toInt() xor xorMask[i % xorMask.size].toInt()).toByte()
        }

        return deobfuscatedKey
    }

    /**
     * Encrypts the given plaintext using RC4.
     *
     * @param plaintext The data to encrypt.
     * @return The encrypted data as a Base64 encoded string.
     */
    fun encrypt(plaintext: String): String {
        val key = SecretKeySpec(getDeobfuscatedKey(), "RC4")
        val cipher = Cipher.getInstance("RC4")
        cipher.init(Cipher.ENCRYPT_MODE, key)
        val encryptedBytes = cipher.doFinal(plaintext.toByteArray())
        return Base64.getEncoder().encodeToString(encryptedBytes)
    }

    /**
     * Decrypts the given ciphertext using RC4.
     *
     * @param ciphertext The Base64 encoded encrypted data.
     * @return The decrypted plaintext.
     */
    fun decrypt(ciphertext: String): String {
        val key = SecretKeySpec(getDeobfuscatedKey(), "RC4")
        val cipher = Cipher.getInstance("RC4")
        cipher.init(Cipher.DECRYPT_MODE, key)
        val encryptedBytes = Base64.getDecoder().decode(ciphertext)
        val decryptedBytes = cipher.doFinal(encryptedBytes)
        return String(decryptedBytes)
    }

    companion object {
        /**
         * Converts a hexadecimal string into a byte array.
         */
        fun hexStringToByteArray(hex: String): ByteArray {
            val result = ByteArray(hex.length / 2)
            for (i in hex.indices step 2) {
                result[i / 2] = ((hex[i].digitToInt(16) shl 4) + hex[i + 1].digitToInt(16)).toByte()
            }
            return result
        }

        /**
         * Utility function to generate an obfuscated key for initialization.
         * This is not used in production but can be used to generate the `obfuscatedKey`.
         */
        fun generateObfuscatedKey(rawKey: ByteArray, xorMask: ByteArray): String {
            val obfuscatedKey = ByteArray(rawKey.size)

            for (i in rawKey.indices) {
                obfuscatedKey[i] = (rawKey[i].toInt() xor xorMask[i % xorMask.size].toInt()).toByte()
            }

            return Base64.getEncoder().encodeToString(obfuscatedKey)
        }
    }
}

// Example usage
fun main() {
    val obfuscator = RC4Obfuscator()

    // Example plaintext
    var plaintext = "f5bee9ca-1e4d-40b5-be3c-7000d57ed8e5"

    // Encrypt the data
    var encrypted = obfuscator.encrypt(plaintext)
    println("Encrypted: $encrypted")

    // Decrypt the data
    var decrypted = obfuscator.decrypt(encrypted)
    println("Decrypted: $decrypted")


    plaintext = "512c2349-2db6-48d7-be6f-b543277dc946"

    // Encrypt the data
    encrypted = obfuscator.encrypt(plaintext)
    println("Encrypted: $encrypted")

    // Decrypt the data
    decrypted = obfuscator.decrypt(encrypted)
    println("Decrypted: $decrypted")
    
}
