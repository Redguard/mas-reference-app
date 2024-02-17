package com.masreferenceapp.crypto;

import android.os.Environment;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.masreferenceapp.Status;

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
        StringBuilder message = new StringBuilder();;

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
        for (String t : transformation) {
            try {
                Cipher cipher = Cipher.getInstance(t);
                status.append("[OK]");
                message.append("["+cipher.toString()+"]");
            } catch (Exception e) {
                status.append("[FAIL]");
                message.append("["+e.toString()+"]");
            }

        }

        return Status.status(status.toString(), message.toString());

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
        StringBuilder message = new StringBuilder();;

        for (String algorithm : algorithms) {
            try {
                KeyGenerator kg = KeyGenerator.getInstance(algorithm);
                status.append("[OK]");
                message.append("["+kg.toString()+"]");
            } catch (Exception e) {
                status.append("[FAIL]");
                message.append("["+e.toString()+"]");
            }

        }
        return Status.status(status.toString(), message.toString());
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
        StringBuilder message = new StringBuilder();;

        for (String s : signatures) {
            try {
                Signature sig = Signature.getInstance(s);
                status.append("[OK]");
                message.append("["+sig.toString()+"]");
            } catch (Exception e) {
                status.append("[FAIL]");
                message.append("["+e.toString()+"]");
            }

        }
        return Status.status(status.toString(), message.toString());
    }



    @ReactMethod(isBlockingSynchronousMethod = true)
    public String bouncyCastle(){

        StringBuilder status = new StringBuilder();
        StringBuilder message = new StringBuilder();;
            try {
                Cipher c = Cipher.getInstance("AES/CBC/PKCS7PADDING", "BC");
                status.append("[OK]");
                message.append("["+c.toString()+"]");
            } catch (Exception e) {
                status.append("[FAIL]");
                message.append("["+e.toString()+"]");
            }

        try {
            Cipher c = Cipher.getInstance("AES/CBC/PKCS7PADDING", Security.getProvider("BC"));
            status.append("[OK]");
            message.append("["+c.toString()+"]");
        } catch (Exception e) {
            status.append("[FAIL]");
            message.append("["+e.toString()+"]");
        }
        return Status.status(status.toString(), message.toString());

    }


    @ReactMethod(isBlockingSynchronousMethod = true)
    public String pbeCipher(){

        StringBuilder status = new StringBuilder();
        StringBuilder message = new StringBuilder();;


        String password  = "password";
        int iterationCount = 1;
        int keyLength = 256;
        int saltLength = keyLength / 8; // same size as key output

        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[saltLength];
        random.nextBytes(salt);

        KeySpec keySpec = new PBEKeySpec(password.toCharArray(), salt, iterationCount, keyLength);
        return Status.status("OK",  keySpec.toString());

    }


    @ReactMethod(isBlockingSynchronousMethod = true)
    public String pbeCipherZeroIV(){

        // more infos: https://developer.android.com/privacy-and-security/cryptography#pbe-without-iv

        try {

            char[] password = "MySecretPassword".toCharArray();

            // Generate PBE key without an IV
            byte[] salt = new byte[] { 0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07 };
            PBEKeySpec spec = new PBEKeySpec(password, salt, 1, 256);

            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            SecretKey pbeKey = factory.generateSecret(spec);

            Cipher pbecipher = Cipher.getInstance("PBEWITHSHA256AND256BITAES-CBC-BC");
            pbecipher.init(Cipher.ENCRYPT_MODE, pbeKey);

            return Status.status("OK",  pbecipher.toString());
        } catch (Exception e) {
            return Status.status("FAIL",  e.toString());
        }

    }



    @ReactMethod(isBlockingSynchronousMethod = true)
    public String encrypt(){
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
            byte[] iv = cipher.getIV();


            return Status.status("OK", "Ciphertext: " +  bytesToHex(ciphertext));
        }
        catch (Exception e) {
            return Status.status("FAIL", e.toString());
        }
    }


    @ReactMethod(isBlockingSynchronousMethod = true)
    public String decrypt(){
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
            String plainText = new String( decrypt.doFinal(ciphertext), StandardCharsets.UTF_8);;

            return Status.status("OK", "Plaintext: " +  plainText);




        }
        catch (Exception e) {
            return Status.status("FAIL", e.toString());
        }
    }


    @ReactMethod(isBlockingSynchronousMethod = true)
    public String sign(){
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
            return Status.status("OK", "Signature: " +  bytesToHex(signature));
        }
        catch (Exception e) {
            return Status.status("FAIL", e.toString());
        }
    }


    @ReactMethod(isBlockingSynchronousMethod = true)
    public String verify(){
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



            return Status.status("OK", "Verify successful? : " +  valid);
        }
        catch (Exception e) {
            return Status.status("FAIL", e.toString());
        }
    }

}