package com.masreferenceapp.crypto;

import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyInfo;
import android.security.keystore.KeyProperties;

import androidx.annotation.NonNull;
import androidx.constraintlayout.motion.widget.KeyPosition;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.masreferenceapp.Status;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
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
        StringBuffer message = new StringBuffer();

        try{
            KeyStore ks = KeyStore.getInstance("AndroidKeyStore");
            status.append("[OK]");
            message.append("["+ ks.toString() +"]");
        }
        catch (Exception e){
            status.append("[FAIL]");
            message.append("["+ e.toString() +"]");
        }
        try{
            KeyStore ks = KeyStore.getInstance("AndroidCAStore");
            status.append("[OK]");
            message.append("["+ ks.toString() +"]");
        }
        catch (Exception e){
            status.append("[FAIL]");
            message.append("["+ e.toString() +"]");
        }
        try{
            KeyStore ks = KeyStore.getInstance("BKS");
            status.append("[OK]");
            message.append("["+ ks.toString() +"]");
        }
        catch (Exception e){
            status.append("[FAIL]");
            message.append("["+ e.toString() +"]");
        }
        try{
            KeyStore ks = KeyStore.getInstance("BouncyCastle");
            status.append("[OK]");
            message.append("["+ ks.toString() +"]");
        }
        catch (Exception e){
            status.append("[FAIL]");
            message.append("["+ e.toString() +"]");
        }
        try{
            KeyStore ks = KeyStore.getInstance("PKCS12");

            status.append("[OK]");
            message.append("["+ ks.toString() +"]");
        }
        catch (Exception e){
            status.append("[FAIL]");
            message.append("["+ e.toString() +"]");
        }

        return Status.status(status.toString(), message.toString());
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

            StringBuffer r = new StringBuffer();

            Collections.list(aliases).forEach(o -> {
                r.append("[").append(o).append("]");
            });

            return Status.status("OK", r.toString());
        } catch (Exception e) {
            return Status.status("FAIL", e.toString());
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

        return Status.status("OK", b.toString());
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

        return Status.status("OK", b.toString());
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
            return Status.status("OK", kpg.toString());
        } catch (Exception e) {
            return Status.status("FAIL", e.toString());
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

            return Status.status("OK", pk.toString());

        } catch (Exception e) {
            return Status.status("FAIL", e.toString());
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
                    .setDigests(KeyProperties.DIGEST_SHA256, KeyProperties.DIGEST_SHA512)
                    .build());
            KeyPair kp = kpg.generateKeyPair();


            PrivateKey pk = kp.getPrivate();
            KeyFactory factory = KeyFactory.getInstance(pk.getAlgorithm(), "AndroidKeyStore");
            KeyInfo keyInfo = (KeyInfo) factory.getKeySpec(pk, KeyInfo.class);

            StringBuffer r = new StringBuffer();

            r.append(keyInfo.getKeySize() + ", ");
            r.append(Arrays.toString(keyInfo.getDigests()) + ", ");
            r.append(keyInfo.getPurposes() + ", ");
            r.append(keyInfo.getKeyValidityStart() + ", ");
            r.append(Arrays.toString(keyInfo.getBlockModes()) + ", ");
            r.append(keyInfo.getSecurityLevel() + ", ");
            r.append(keyInfo.getUserAuthenticationType() + ", ");
            r.append(keyInfo.isTrustedUserPresenceRequired() + ", ");
            r.append(keyInfo.isInsideSecureHardware() + ", ");
            r.append(Arrays.toString(keyInfo.getEncryptionPaddings()) + ", ");


            return Status.status("OK", r.toString());

        } catch (Exception e) {
            return Status.status("FAIL", e.toString());
        }


    }

}