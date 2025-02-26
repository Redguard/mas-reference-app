package com.insomnihack;

import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.util.Base64;
import android.util.Log;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.Key;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.UnrecoverableEntryException;
import java.security.cert.CertificateException;

import javax.crypto.Cipher;

/**
 * This entire class was (mostly) generated by Gemini. I spent exactly 0 seconds checking whether
 * the code is actually good.
 *
 * The prompt was simply "Write me some code for Android to encrypt, decrypt, sign and verify a
 * signature. All operations use RSA and the device's Secure Enclave"
 */
public class RsaThingies {

    private static final String TAG = "CTF";
    private static final String KEY_ALIAS = "CtfKey";
    private static final String PROVIDER = "AndroidKeyStore";
    private static final String RSA_MODE = "RSA/ECB/PKCS1Padding";
    private static final String SIGNATURE_ALGORITHM = "SHA256withRSA";

    private final KeyStore keyStore;

    public RsaThingies() throws KeyStoreException, CertificateException, IOException, NoSuchAlgorithmException {
        keyStore = KeyStore.getInstance(PROVIDER);
        keyStore.load(null);
    }

    /**
     * Generates an RSA key pair within the Android Keystore, utilizing the Secure Enclave if available.
     *
     * @throws NoSuchProviderException        If the specified provider ("AndroidKeyStore") isn't found.
     * @throws NoSuchAlgorithmException      If RSA isn't supported (highly unlikely).
     * @throws InvalidAlgorithmParameterException If the KeyGenParameterSpec or KeyPairGeneratorSpec is invalid.
     */
    public void generateKeyPair () throws NoSuchProviderException, NoSuchAlgorithmException, InvalidAlgorithmParameterException, IOException, CertificateException, KeyStoreException {

        if (!keyStore.containsAlias(KEY_ALIAS)) {

            KeyGenParameterSpec keyGenParameterSpec = new KeyGenParameterSpec.Builder(
                    KEY_ALIAS,
                    KeyProperties.PURPOSE_ENCRYPT | KeyProperties.PURPOSE_DECRYPT | KeyProperties.PURPOSE_SIGN | KeyProperties.PURPOSE_VERIFY)
                    .setDigests(KeyProperties.DIGEST_SHA256, KeyProperties.DIGEST_SHA512)
                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_RSA_PKCS1)
                    .setSignaturePaddings(KeyProperties.SIGNATURE_PADDING_RSA_PKCS1)
                    .setKeySize(2048)
                    .build();

            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(
                    KeyProperties.KEY_ALGORITHM_RSA, PROVIDER);
            keyPairGenerator.initialize(keyGenParameterSpec);
            keyPairGenerator.generateKeyPair();
        }
    }

    /**
     * Encrypts data using RSA/ECB/OAEPWithSHA-256AndMGF1Padding with the public key.
     *
     * @param plainText The data to encrypt.
     * @return The encrypted data as a Base64 encoded string.
     * @throws Exception if any encryption-related errors occur.
     */
    public String encryptData(byte[] plainText) throws Exception {

        PublicKey publicKey = getPublicKey();
        if (publicKey == null) {
            throw new Exception("Public key not available.  Has the key been generated?");
        }

        Cipher cipher = Cipher.getInstance(RSA_MODE);
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] encryptedBytes = cipher.doFinal(plainText);
        return Base64.encodeToString(encryptedBytes, Base64.DEFAULT);
    }


    /**
     * Decrypts data using RSA/ECB/OAEPWithSHA-256AndMGF1Padding with the private key.
     *
     * @param encryptedData The Base64 encoded encrypted data.
     * @return The decrypted data as a byte array.
     * @throws Exception if any decryption-related errors occur.
     */
    public byte[] decryptData(String encryptedData) throws Exception {

        PrivateKey privateKey = getPrivateKey();
        if (privateKey == null) {
            throw new Exception("Private key not available. Has the key been generated?");
        }

        Cipher cipher = Cipher.getInstance(RSA_MODE);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] encryptedBytes = Base64.decode(encryptedData, Base64.DEFAULT);
        return cipher.doFinal(encryptedBytes);
    }

    /**
     * Signs data using SHA256withRSA.
     *
     * @param dataToSign The data to sign.
     * @return The signature as a Base64 encoded string.
     * @throws Exception If any signing-related errors occur.
     */
    public String signData(byte[] dataToSign) throws Exception {
        PrivateKey privateKey = getPrivateKey();
        if (privateKey == null) {
            throw new Exception("Private key not available for signing.");
        }

        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(privateKey);
        signature.update(dataToSign);
        byte[] signatureBytes = signature.sign();
        return Base64.encodeToString(signatureBytes, Base64.DEFAULT);
    }

    /**
     * Verifies a signature using SHA256withRSA.
     *
     * @param data          The original data that was signed.
     * @param signatureData The Base64 encoded signature.
     * @return True if the signature is valid, false otherwise.
     * @throws Exception If any verification-related errors occur.
     */
    public boolean verifySignature(byte[] data, String signatureData) throws Exception {
        PublicKey publicKey = getPublicKey();
        if (publicKey == null) {
            throw new Exception("Public key not available for signature verification.");
        }

        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initVerify(publicKey);
        signature.update(data);
        byte[] signatureBytes = Base64.decode(signatureData, Base64.DEFAULT);
        return signature.verify(signatureBytes);
    }


    /**
     * Retrieves the private key from the Android KeyStore.
     */
    private PrivateKey getPrivateKey() throws UnrecoverableEntryException, NoSuchAlgorithmException, KeyStoreException {
        KeyStore.Entry entry = keyStore.getEntry(KEY_ALIAS, null);
        if (!(entry instanceof KeyStore.PrivateKeyEntry)) {
            Log.w(TAG, "Not an instance of a PrivateKeyEntry");
            return null;
        }
        return ((KeyStore.PrivateKeyEntry) entry).getPrivateKey();
    }

    /**
     * Retrieves the public key from the Android KeyStore.
     */
    private PublicKey getPublicKey() throws KeyStoreException {
        return keyStore.getCertificate(KEY_ALIAS).getPublicKey();
    }

    /**
     * Deletes the key pair from the Android Keystore.
     */
    public void deleteKeyPair() throws KeyStoreException {
        if (keyStore.containsAlias(KEY_ALIAS)) {
            keyStore.deleteEntry(KEY_ALIAS);
        }
    }

    /**
     * Checks if StrongBox (dedicated hardware security module) is backing the key.
     * This method requires API level 28 (Android 9.0 - Pie) or higher.  It will return false on lower API levels.
     * @return true if StrongBox is used, false otherwise or if the API level is too low.
     */
    public boolean isStrongBoxBacked() {
        try {
            KeyStore.Entry entry = keyStore.getEntry(KEY_ALIAS, null);
            if (entry instanceof KeyStore.PrivateKeyEntry) {
                Key key = ((KeyStore.PrivateKeyEntry) entry).getPrivateKey();
                if (key.getClass().getName().equals("android.security.keystore.AndroidKeyStoreRSAPrivateKey")) {
                    //Best effort to check if it is a StrongBox Key
                    return ((java.security.interfaces.RSAKey) key).getModulus().bitLength() >= 512 &&  // Check for a reasonable key size
                            key.toString().contains("algorithm=RSA, keyStore=AndroidKeyStore, alias=MyRSAKeyAlias, parameters=java.security.spec.KeySpec, isStrongBoxBacked=true"); //Key is strongBox Backed

                }

            }
            return false;
        } catch (Exception e) {
            Log.e(TAG, "Error checking StrongBox status", e);
            return false;
        }
    }
}