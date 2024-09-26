package com.masreferenceapp.crypto;

import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyInfo;
import android.security.keystore.KeyProperties;

import androidx.annotation.NonNull;
import androidx.constraintlayout.motion.widget.KeyPosition;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.masreferenceapp.ReturnStatus;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.RSAKeyGenParameterSpec;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;

import android.security.keystore.KeyGenParameterSpec.Builder;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.RC2ParameterSpec;


public class CryptoKeyStore extends ReactContextBaseJavaModule {
    ReactApplicationContext context;

    public CryptoKeyStore(ReactApplicationContext context) {
        super(context);
        this.context = context;
    }

    @NonNull
    @Override
    public String getName() {
        return "CryptoKeyStore";
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String init(){

        StringBuffer status = new StringBuffer();

        ReturnStatus r = new ReturnStatus();


        try{
            KeyStore ks = KeyStore.getInstance("AndroidKeyStore");
            r.addStatus("OK", "AndroidKeyStore initialized.");
        }
        catch (Exception e){
            r.addStatus("FAIL", e.toString());
        }
        try{
            KeyStore ks = KeyStore.getInstance("AndroidCAStore");
            r.addStatus("OK", "AndroidCAStore initialized.");

        }
        catch (Exception e){
            r.addStatus("FAIL", e.toString());

        }
        try{
            KeyStore ks = KeyStore.getInstance("BKS");
            r.addStatus("OK", "BKS initialized.");

        }
        catch (Exception e){
            r.addStatus("FAIL", e.toString());

        }
        try{
            KeyStore ks = KeyStore.getInstance("BouncyCastle");
            r.addStatus("OK", "BouncyCastle initialized.");

        }
        catch (Exception e){
            r.addStatus("FAIL", e.toString());

        }
        try{
            KeyStore ks = KeyStore.getInstance("PKCS12");
            r.addStatus("OK", "PKCS12 initialized.");

        }
        catch (Exception e){
            r.addStatus("FAIL", e.toString());

        }


        return r.toJsonString();
    }


    @ReactMethod(isBlockingSynchronousMethod = true)
    public String getKeyAliases(){

        // create two entries

        this.accessPrivateKey();
        this.generateAsymmetricKeys();

        /*
         * Load the Android KeyStore instance using the
         * AndroidKeyStore provider to list the currently stored entries.
         */
        try{
            KeyStore ks = KeyStore.getInstance("AndroidKeyStore");
            ks.load(null);
            Enumeration<String> aliases = ks.aliases();

            StringBuffer al = new StringBuffer();

            Collections.list(aliases).forEach(o -> {
                al.append("[").append(o).append("]");
            });

            ReturnStatus r = new ReturnStatus("OK", "Aliases: " + al.toString());
            return r.toJsonString();
        } catch (Exception e) {
            ReturnStatus r = new ReturnStatus("FAIL", "Exception: " + e);
            return r.toJsonString();
        }
    }


    @ReactMethod(isBlockingSynchronousMethod = true)
    public String createSecureKeyGenParameterSpec(){

        KeyGenParameterSpec b = new KeyGenParameterSpec.Builder(
                "nonsenseKeySpec",
                KeyProperties.PURPOSE_DECRYPT)
                .setDigests(KeyProperties.DIGEST_SHA512)
                .setKeySize(4096)
                .setCertificateNotAfter(new Date())
                .setBlockModes(KeyProperties.BLOCK_MODE_GCM)
                .setDigests(KeyProperties.DIGEST_SHA256, KeyProperties.DIGEST_SHA512)
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_RSA_OAEP)
                .setAlgorithmParameterSpec(new ECGenParameterSpec("secp256r1"))
                .setUserAuthenticationRequired(true)
                .setUserAuthenticationParameters(100,KeyProperties.AUTH_BIOMETRIC_STRONG)
                .build();
        ReturnStatus r = new ReturnStatus("OK", "Parameter Spec: " + b.toString());
        return r.toJsonString();
    }


    @ReactMethod(isBlockingSynchronousMethod = true)
    public String createInsecureKeyGenParameterSpec(){

        KeyGenParameterSpec b = new KeyGenParameterSpec.Builder(
                "nonsenseKeySpec",
                KeyProperties.PURPOSE_ENCRYPT)
                .setDigests(KeyProperties.DIGEST_MD5)
                .setKeySize(56)
                .setCertificateNotAfter(new Date())
                .setBlockModes(KeyProperties.BLOCK_MODE_ECB)
                .setDigests(KeyProperties.DIGEST_MD5, KeyProperties.DIGEST_NONE)
                .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_NONE)
                .setIsStrongBoxBacked(false)
                .setAlgorithmParameterSpec(new RSAKeyGenParameterSpec(512, new BigInteger("12345")))
                .build();

        ReturnStatus r = new ReturnStatus("OK", "Parameter Spec: " + b.toString());
        return r.toJsonString();
    }


    @ReactMethod(isBlockingSynchronousMethod = true)
    public String generateAsymmetricKeys(){

        KeyPairGenerator kpg = null;
        try {
            kpg = KeyPairGenerator.getInstance(
                    KeyProperties.KEY_ALGORITHM_EC, "AndroidKeyStore");
            kpg.initialize(new KeyGenParameterSpec.Builder(
                    "masReferenceAppAsymmetricEcKey",
                    KeyProperties.PURPOSE_SIGN | KeyProperties.PURPOSE_VERIFY)
                    .setDigests(KeyProperties.DIGEST_SHA256,
                            KeyProperties.DIGEST_SHA512)
                    .build());
            KeyPair kp = kpg.generateKeyPair();

            ReturnStatus r = new ReturnStatus("OK", "Asymmetric Key generated.");
            return r.toJsonString();

        } catch (Exception e) {
            ReturnStatus r = new ReturnStatus("FAIL", "Exception: " + e);
            return r.toJsonString();
        }
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String accessPrivateKey(){

        KeyPairGenerator kpg = null;
        try {
            kpg = KeyPairGenerator.getInstance(
                    KeyProperties.KEY_ALGORITHM_RSA, "AndroidKeyStore");
            kpg.initialize(new KeyGenParameterSpec.Builder(
                    "masReferenceAppAsymmetricRSA",
                    KeyProperties.PURPOSE_SIGN | KeyProperties.PURPOSE_VERIFY)
                    .setKeySize(512) // for performance :)
                    .setDigests(KeyProperties.DIGEST_SHA256, KeyProperties.DIGEST_SHA512)
                    .build());
            KeyPair kp = kpg.generateKeyPair();


            PrivateKey pk = kp.getPrivate();

            ReturnStatus r = new ReturnStatus("OK", "Private Key accessed.");
            return r.toJsonString();

        } catch (Exception e) {
            ReturnStatus r = new ReturnStatus("FAIL", "Exception: " + e);
            return r.toJsonString();
        }
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String readKeyInfo(){

        KeyPairGenerator kpg = null;
        try {
            kpg = KeyPairGenerator.getInstance(
                    KeyProperties.KEY_ALGORITHM_RSA, "AndroidKeyStore");
            kpg.initialize(new KeyGenParameterSpec.Builder(
                    "masReferenceAppAsymmetricRSA",
                    KeyProperties.PURPOSE_SIGN | KeyProperties.PURPOSE_VERIFY)
                    .setKeySize(512) // for performance :)
                    .setBlockModes(KeyProperties.BLOCK_MODE_ECB, KeyProperties.BLOCK_MODE_CBC)
                    .setDigests(KeyProperties.DIGEST_SHA256, KeyProperties.DIGEST_SHA512)
                    .build());
            KeyPair kp = kpg.generateKeyPair();


            PrivateKey pk = kp.getPrivate();
            KeyFactory factory = KeyFactory.getInstance(pk.getAlgorithm(), "AndroidKeyStore");
            KeyInfo keyInfo = factory.getKeySpec(pk, KeyInfo.class);

            String info = keyInfo.getKeySize() + ", " +
                    Arrays.toString(keyInfo.getDigests()) + ", " +
                    keyInfo.getPurposes() + ", " +
                    keyInfo.getKeyValidityStart() + ", " +
                    Arrays.toString(keyInfo.getBlockModes()) + ", " +
                    keyInfo.getSecurityLevel() + ", " +
                    keyInfo.getUserAuthenticationType() + ", " +
                    keyInfo.isTrustedUserPresenceRequired() + ", " +
                    keyInfo.isInsideSecureHardware() + ", " +
                    Arrays.toString(keyInfo.getEncryptionPaddings()) + ", ";


            ReturnStatus r = new ReturnStatus("OK", "Key Info: " +info);
            return r.toJsonString();

        } catch (Exception e) {
            ReturnStatus r = new ReturnStatus("FAIL", "Exception: " + e);
            return r.toJsonString();
        }
    }
}