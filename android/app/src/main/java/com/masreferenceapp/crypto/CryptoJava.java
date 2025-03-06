package com.masreferenceapp.crypto;

import android.os.Build;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.masreferenceapp.ReturnStatus;

import java.security.KeyPairGenerator;
import java.security.Security;
import java.security.Signature;
import java.util.ArrayList;
import java.util.HexFormat;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;


public class CryptoJava extends ReactContextBaseJavaModule {
    ReactApplicationContext context;

    public CryptoJava(ReactApplicationContext context) {
        super(context);
        this.context = context;
    }

    @NonNull
    @Override
    public String getName() {
        return "CryptoJava";
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String initInsecureKeyGenerators() {

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
                r.addStatus("OK", "Initialized KeyGenerator for algorithm: " + algorithm);

            } catch (Exception e) {
                r.addStatus("FAIL", e.toString());
            }

        }
        return r.toJsonString();
    }


    @ReactMethod(isBlockingSynchronousMethod = true)
    public String initInsecureKeyPairGenerators() {

        ReturnStatus r = new ReturnStatus();
        try {
            KeyPairGenerator kg = KeyPairGenerator.getInstance("RSA");
            kg.initialize(512);
            r.addStatus("OK", "Initialized KeyPairGenerator with insecure key size (512 bit).");

        } catch (Exception e) {
            r.addStatus("FAIL", e.toString());
        }

        try {
            KeyPairGenerator kg = KeyPairGenerator.getInstance("RSA", "AndroidOpenSSL");
            r.addStatus("OK", "Initialized KeyPairGenerator with a security provider other than AndroidKeyStore.");

        } catch (Exception e) {
            r.addStatus("FAIL", e.toString());
        }
        return r.toJsonString();
    }


    @ReactMethod(isBlockingSynchronousMethod = true)
    public String initInsecureCiphers() {

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
                r.addStatus("OK", "Initialized Cipher for algorithm:  " + t);
            } catch (Exception e) {
                r.addStatus("FAIL", e.toString());
            }
        }

        return r.toJsonString();

    }


    @ReactMethod(isBlockingSynchronousMethod = true)
    public String initInsecureSignatures() {

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
                r.addStatus("OK", "Initialized Singnature for algorithm: " + s);
            } catch (Exception e) {
                r.addStatus("FAIL", e.toString());
            }

        }
        return r.toJsonString();
    }


    @ReactMethod(isBlockingSynchronousMethod = true)
    public String bouncyCastle() {
        ReturnStatus r = new ReturnStatus();

        StringBuilder status = new StringBuilder();
        StringBuilder message = new StringBuilder();
        try {
            Cipher c = Cipher.getInstance("AES/CBC/PKCS7PADDING", "BC");
            r.addStatus("OK", "Cipher instance with BouncyCastle as SecurityProvider created.");
        } catch (Exception e) {
            r.addStatus("Fail", e.toString());
        }

        try {
            Cipher c = Cipher.getInstance("AES/CBC/PKCS7PADDING", Security.getProvider("BC"));
            r.addStatus("OK", "Cipher instance with BouncyCastle as SecurityProvider created.");
        } catch (Exception e) {
            r.addStatus("Fail", e.toString());

        }
        return r.toJsonString();
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String pbeCipherZeroIV() {

        // more infos: https://developer.android.com/privacy-and-security/cryptography#pbe-without-iv

        ReturnStatus r = new ReturnStatus();

        try {

            // Create a PBE key
            char[] password = "password".toCharArray();
            byte[] salt = "12345678".getBytes(); // Example salt (should be securely generated)
            PBEKeySpec keySpec = new PBEKeySpec(password, salt, 10000, 256); // Iteration count and key size
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBEWITHSHA256AND256BITAES-CBC-BC");
            SecretKey secretKey = keyFactory.generateSecret(keySpec);

            // Init the cipher WITHOUT explicit IV
            Cipher cipher = Cipher.getInstance("PBEWITHSHA256AND256BITAES-CBC-BC");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);

            r.success("PBE Cipher with Zero IV created. ");

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                r.success("IV is : " + HexFormat.of().formatHex(cipher.getIV()));
            }
            return r.toJsonString();

        } catch (Exception e) {
            r.addStatus("FAIL", e.toString());
            return r.toJsonString();
        }
    }


    @ReactMethod(isBlockingSynchronousMethod = true)
    public String nullIv() {

        // Use a NULL IV (all zeros)
        byte[] nullIV = new byte[16]; // 16 bytes for AES block size
        IvParameterSpec ivSpec = new IvParameterSpec(nullIV);

        ReturnStatus r = new ReturnStatus();
        r.addStatus("OK", "Created a IV with all nulls.");

        return r.toJsonString();
    }
}