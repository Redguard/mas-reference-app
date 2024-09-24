package com.masreferenceapp.crypto;

import android.os.Environment;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.masreferenceapp.ReturnStatus;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.Security;
import java.security.Signature;
import java.security.interfaces.ECPrivateKey;
import java.security.spec.KeySpec;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import javax.crypto.spec.SecretKeySpec;


public class CryptoCipher extends ReactContextBaseJavaModule {
    ReactApplicationContext context;

    public CryptoCipher(ReactApplicationContext context) {
        super(context);
        this.context = context;
    }

    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();
    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }

    @NonNull
    @Override
    public String getName() {
        return "CryptoCipher";
    }


    @ReactMethod(isBlockingSynchronousMethod = true)
    public String initInsecureCiphers(){

        // more infos: https://developer.android.com/reference/kotlin/javax/crypto/Cipher

        StringBuilder status = new StringBuilder();
        StringBuilder message = new StringBuilder();

        List<String> transformation = new ArrayList<>();
        transformation.add("AES_128/ECB/PKCS5Padding");
        transformation.add("AES_128/CBC/PKCS5Padding");
        transformation.add("DES/ECB");
        transformation.add("DESede/OFB");
        transformation.add("RC4");
        transformation.add("ARC4/ECB");
        transformation.add("RC2");
        transformation.add("RSA/ECB/PKCS1Padding");
        transformation.add("RSA/CBC/PKCS1Padding");
        transformation.add("AES/ECB/PKCS5Padding");
        transformation.add("AES/CBC/PKCS5Padding");
        transformation.add("DES/ECB/PKCS5Padding");


        ReturnStatus r = new ReturnStatus();


        for (String t : transformation) {
            try {
                Cipher cipher = Cipher.getInstance(t);
                r.addStatus("OK", "Cipher init with tranformation: " + t);
            } catch (Exception e) {
                r.addStatus("FAIL", e.toString());
            }
        }

        return r.toJsonString();

    }


    @ReactMethod(isBlockingSynchronousMethod = true)
    public String initInsecureKeyGenerators(){

        List<String> algorithms = new ArrayList<>();
        algorithms.add("ARC4");
        algorithms.add("DES");
        algorithms.add("DESede");
        algorithms.add("DESedeWRAP");
        algorithms.add("HmacMD5");
        algorithms.add("HmacSHA1");
        algorithms.add("RC4");

        StringBuilder status = new StringBuilder();
        StringBuilder message = new StringBuilder();


        ReturnStatus r = new ReturnStatus();

        for (String algorithm : algorithms) {
            try {
                KeyGenerator kg = KeyGenerator.getInstance(algorithm);
                r.addStatus("OK", "Cipher init with algorithm: " + algorithm);

            } catch (Exception e) {
                r.addStatus("FAIL", e.toString());
            }

        }
        return r.toJsonString();
    }


    @ReactMethod(isBlockingSynchronousMethod = true)
    public String initInsecureSignatures(){

        // more infos: https://docs.oracle.com/javase/8/docs/technotes/guides/security/StandardNames.html#Signature

        List<String> signatures = new ArrayList<>();
        signatures.add("NONEwithRSA");
        signatures.add("MD2withRSA");
        signatures.add("MD5withRSA");
        signatures.add("SHA1withRSA");
        signatures.add("NONEwithDSA");
        signatures.add("SHA1withDSA");
        signatures.add("NONEwithECDSA");
        signatures.add("SHA1withECDSA");

        StringBuilder status = new StringBuilder();
        StringBuilder message = new StringBuilder();

        ReturnStatus r = new ReturnStatus();

        for (String s : signatures) {
            try {
                Signature sig = Signature.getInstance(s);
                r.addStatus("OK", "Signature init with algorithm: " + s);
            } catch (Exception e) {
                r.addStatus("FAIL", e.toString());
            }

        }
        return r.toJsonString();
    }



    @ReactMethod(isBlockingSynchronousMethod = true)
    public String bouncyCastle(){
        ReturnStatus r = new ReturnStatus();

        StringBuilder status = new StringBuilder();
        StringBuilder message = new StringBuilder();
        try {
            Cipher c = Cipher.getInstance("AES/CBC/PKCS7PADDING", "BC");
            r.addStatus("OK", "BouncyCastle instance created.");
        } catch (Exception e) {
            r.addStatus("Fail", e.toString());
        }

        try {
            Cipher c = Cipher.getInstance("AES/CBC/PKCS7PADDING", Security.getProvider("BC"));
            r.addStatus("OK", "BouncyCastle instance created.");
        } catch (Exception e) {
            r.addStatus("Fail", e.toString());

        }
        return r.toJsonString();

    }


    @ReactMethod(isBlockingSynchronousMethod = true)
    public String pbeCipher(){

        StringBuilder status = new StringBuilder();
        StringBuilder message = new StringBuilder();


        char[] password = "MySecretPassword".toCharArray();
        int iterationCount = 1000000;
        int keyLength = 256;
        int saltLength = keyLength / 8; // same size as key output

        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[saltLength];
        random.nextBytes(salt);

        KeySpec keySpec = new PBEKeySpec(password, salt, iterationCount, keyLength);

        ReturnStatus r = new ReturnStatus("OK", "Password Based Encryption Cipher instance created.");
        return r.toJsonString();

    }


    @ReactMethod(isBlockingSynchronousMethod = true)
    public String pbeCipherLowIteration(){

        StringBuilder status = new StringBuilder();
        StringBuilder message = new StringBuilder();


        char[] password = "MySecretPassword".toCharArray();
        int iterationCount = 1;
        int keyLength = 256;
        int saltLength = keyLength / 8; // same size as key output

        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[saltLength];
        random.nextBytes(salt);

        KeySpec keySpec = new PBEKeySpec(password, salt, iterationCount, keyLength);

        ReturnStatus r = new ReturnStatus("OK", "Password Based Encryption Cipher instance with 1 iteration of PBEKeySpec created.");
        return r.toJsonString();

    }


    @ReactMethod(isBlockingSynchronousMethod = true)
    public String pbeCipherZeroIV(){

        // more infos: https://developer.android.com/privacy-and-security/cryptography#pbe-without-iv

        ReturnStatus r = new ReturnStatus();

        try {
            char[] password = "MySecretPassword".toCharArray();

            // Generate PBE key without an IV
            byte[] salt = new byte[] { 0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07 };
            PBEKeySpec spec = new PBEKeySpec(password, salt, 1, 256);

            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            SecretKey pbeKey = factory.generateSecret(spec);

            // The code uses AES in CBC mode (PBEWITHSHA256AND256BITAES-CBC-BC) for encryption but does not explicitly specify an Initialization Vector (IV) during the cipher initialization (pbecipher.init(Cipher.ENCRYPT_MODE, pbeKey);).
            Cipher pbecipher = Cipher.getInstance("PBEWITHSHA256AND256BITAES-CBC-BC");
            pbecipher.init(Cipher.ENCRYPT_MODE, pbeKey);

            r.addStatus("OK", "PBE Cipher with Zero IV created.");
            return r.toJsonString();

        } catch (Exception e) {
            r.addStatus("FAIL", e.toString());
            return r.toJsonString();
        }
    }



    @ReactMethod(isBlockingSynchronousMethod = true)
    public String encrypt(){
        ReturnStatus r = new ReturnStatus();

        try {
            String partOne = "Fist secret Text: Passw0rd! \n";
            byte[] plaintextOne = partOne.getBytes();
            String inputString = "Let's encrypt this S3CR3T!";
            byte[] plaintext = inputString.getBytes();

            KeyGenerator keygen = KeyGenerator.getInstance("AES");
            keygen.init(256);
            SecretKey key = keygen.generateKey();
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            cipher.update(plaintextOne);
            byte[] ciphertext = cipher.doFinal(plaintext);


            r.addStatus("OK", "Encryption done. Ciphertext: " +  bytesToHex(ciphertext));
            return r.toJsonString();
        }
        catch (Exception e) {
            r.addStatus("FAIL", e.toString());
            return r.toJsonString();
        }
    }


    @ReactMethod(isBlockingSynchronousMethod = true)
    public String decrypt(){
        ReturnStatus r = new ReturnStatus();

        try {

            String inputString = "Let's DECRYPT this S3CR3T!";
            byte[] plaintext = inputString.getBytes();

            KeyGenerator keygen = KeyGenerator.getInstance("AES");
            keygen.init(256);
            SecretKey key = keygen.generateKey();
            Cipher encryptor = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            encryptor.init(Cipher.ENCRYPT_MODE, key);
            byte[] ciphertext = encryptor.doFinal(plaintext);
            byte[] iv = encryptor.getIV();


            // decrypt
            Cipher decrypt = Cipher.getInstance("AES/CBC/PKCS5PADDING");
            IvParameterSpec iVector = new IvParameterSpec(iv);
            decrypt.init(Cipher.DECRYPT_MODE, key, iVector);
            String plainText = new String( decrypt.doFinal(ciphertext), StandardCharsets.UTF_8);

            r.addStatus("OK", "Encryption done. Plaintext: " + plainText);
            return r.toJsonString();

        }
        catch (Exception e) {
            r.addStatus("FAIL", e.toString());
            return r.toJsonString();
        }
    }


    @ReactMethod(isBlockingSynchronousMethod = true)
    public String sign(){
        ReturnStatus r = new ReturnStatus();

        try {
            String inputString = "Document to sign.";
            byte[] doc = inputString.getBytes();

            KeyPairGenerator kpg = KeyPairGenerator.getInstance(KeyProperties.KEY_ALGORITHM_RSA);
            kpg.initialize(1024);
            KeyPair kp = kpg.generateKeyPair();

            Signature s = Signature.getInstance("SHA512withRSA");
            s.initSign(kp.getPrivate());
            s.update(doc);
            byte[] signature = s.sign();
            r.addStatus("OK", "Document signed.");
            return r.toJsonString();
        }
        catch (Exception e) {
            r.addStatus("FAIL", e.toString());
            return r.toJsonString();
        }
    }


    @ReactMethod(isBlockingSynchronousMethod = true)
    public String verify(){
        ReturnStatus r = new ReturnStatus();

        try {
            String inputString = "Document to sign.";
            byte[] doc = inputString.getBytes();

            KeyPairGenerator kpg = KeyPairGenerator.getInstance(KeyProperties.KEY_ALGORITHM_RSA);
            kpg.initialize(1024);
            KeyPair kp = kpg.generateKeyPair();

            Signature s = Signature.getInstance("SHA512withRSA");
            s.initSign(kp.getPrivate());
            s.update(doc);
            byte[] signature = s.sign();

            Signature v = Signature.getInstance("SHA512withRSA");
            v.initVerify(kp.getPublic());
            v.update(doc);
            boolean valid = v.verify(signature);

            r.addStatus("OK", "Document validity:  " + valid);
            return r.toJsonString();
        }
        catch (Exception e) {
            r.addStatus("FAIL", e.toString());
            return r.toJsonString();
        }
    }

}