import java.util.Base64
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

class RC4Obfuscator {

    // Hardcoded hexadecimal string for the key

    // this is the plain text key

    private val keyHex = "8c00e2cc664423d42eb34496bde93456d6e2f9bdfc578c602f31ebbbd7c5b4b7"


    // Convert the hex strings into byte arrays
    private val rawKey: ByteArray = hexStringToByteArray(keyHex)


    fun encrypt(plaintext: String): String {
        val key = SecretKeySpec(rawKey, "RC4")
        val cipher = Cipher.getInstance("RC4")
        cipher.init(Cipher.ENCRYPT_MODE, key)
        val encryptedBytes = cipher.doFinal(plaintext.toByteArray())
        return Base64.getEncoder().encodeToString(encryptedBytes)
    }

    fun decrypt(ciphertext: String): String {
        val key = SecretKeySpec(rawKey, "RC4")
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

fun main() {
    val obfuscator = RC4Obfuscator()

    var plaintext = "f5bee9ca-1e4d-40b5-be3c-7000d57ed8e5"
    var encrypted = obfuscator.encrypt(plaintext)
    println("Encrypted: $encrypted")
    var decrypted = obfuscator.decrypt(encrypted)
    println("Decrypted: $decrypted")


    plaintext = "512c2349-2db6-48d7-be6f-b543277dc946"
    encrypted = obfuscator.encrypt(plaintext)
    println("Encrypted: $encrypted")
    decrypted = obfuscator.decrypt(encrypted)
    println("Decrypted: $decrypted")
    
}
