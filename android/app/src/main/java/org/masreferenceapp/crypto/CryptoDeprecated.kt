package org.masreferenceapp.crypto

import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import org.masreferenceapp.ReturnStatus
import org.spongycastle.crypto.engines.AESEngine
import org.spongycastle.crypto.modes.CBCBlockCipher
import org.spongycastle.crypto.paddings.PKCS7Padding
import org.spongycastle.crypto.paddings.PaddedBufferedBlockCipher
import org.spongycastle.crypto.params.KeyParameter
import org.spongycastle.crypto.params.ParametersWithIV
import org.spongycastle.jce.provider.BouncyCastleProvider
import java.security.SecureRandom
import java.security.Security
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey


class CryptoDeprecated(var context: ReactApplicationContext) : ReactContextBaseJavaModule(
    context
) {
    override fun getName(): String {
        return "CryptoDeprecated"
    }


    @ReactMethod(isBlockingSynchronousMethod = true)
    fun enumerateSecurityProviders(): String {
        val p = Security.getProviders()

        val r = ReturnStatus()

        for (item in p) {
            r.success("SecurityProvider found: $item")
        }
        return r.toJsonString()
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    fun bouncyCastleProvider(): String {

        Security.addProvider(BouncyCastleProvider())

        val r = ReturnStatus("OK", "Loaded Bouncy Castle as Security Provider.")
        return r.toJsonString()

    }


    @ReactMethod(isBlockingSynchronousMethod = true)
    fun bouncyCastleDirect(): String {

        val pbbc = PaddedBufferedBlockCipher(CBCBlockCipher(AESEngine()), PKCS7Padding())

        val sr = SecureRandom()


        var newParamIV: ParametersWithIV? = null
        val IV = ByteArray(16)
        val kg: KeyGenerator = KeyGenerator.getInstance("AES")
        kg.init(256)
        val sk: SecretKey = kg.generateKey()
        sr.nextBytes(IV)
        newParamIV = ParametersWithIV(KeyParameter(sk.encoded), IV)

        val inputString = "Hello World"
        val inputBytes = inputString.toByteArray(charset("UTF-8"))

        pbbc.init(true, newParamIV)
        val output = ByteArray(pbbc.getOutputSize(inputBytes.size))
        val bytesWrittenOut = pbbc.processBytes(
            inputBytes, 0, inputBytes.size, output, 0
        )
        pbbc.doFinal(output, bytesWrittenOut)


        val r = ReturnStatus(
            "OK",
            "Encrypted a text using the BouncyCastle Implementation of AES/CBC directly."
        )
        return r.toJsonString()
    }

    //@method
}