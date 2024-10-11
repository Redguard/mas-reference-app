package com.masreferenceapp.crypto;

import static java.lang.Character.LINE_SEPARATOR;

import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.masreferenceapp.ReturnStatus;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.util.Base64;


public class CryptoKeyAttestation extends ReactContextBaseJavaModule {
    ReactApplicationContext context;

    public CryptoKeyAttestation(ReactApplicationContext context) {
        super(context);
        this.context = context;
    }

    public static final String BEGIN_CERT = "-----BEGIN CERTIFICATE-----";
    public static final String END_CERT = "-----END CERTIFICATE-----";
    public final static String LINE_SEPARATOR = System.getProperty("line.separator");


    public static String formatCrtFileContents(final Certificate certificate) throws CertificateEncodingException {
        final Base64.Encoder encoder = Base64.getMimeEncoder(64, LINE_SEPARATOR.getBytes());

        final byte[] rawCrtText = certificate.getEncoded();
        final String encodedCertText = new String(encoder.encode(rawCrtText));
        final String prettified_cert = BEGIN_CERT + LINE_SEPARATOR + encodedCertText + LINE_SEPARATOR + END_CERT;
        return prettified_cert;
    }

    @NonNull
    @Override
    public String getName() {
        return "CryptoKeyAttestation";
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String getCertificateChain(){

        // create new keypair
        KeyPairGenerator kpg = null;
        try {
            kpg = KeyPairGenerator.getInstance(
                    KeyProperties.KEY_ALGORITHM_EC, "AndroidKeyStore");
            kpg.initialize(new KeyGenParameterSpec.Builder(
                    "masAttestationTestKey",
                    KeyProperties.PURPOSE_SIGN | KeyProperties.PURPOSE_VERIFY)
                    .setDigests(KeyProperties.DIGEST_SHA256,
                            KeyProperties.DIGEST_SHA512)
                    .build());
            KeyPair kp = kpg.generateKeyPair();

            KeyStore ks = KeyStore.getInstance("AndroidKeyStore");
            ks.load(null);
            Certificate[] attestationCerts = ks.getCertificateChain("masAttestationTestKey");

            // get certificates
            StringBuilder chain = new StringBuilder();


            for (Certificate attestationCert : attestationCerts) {

                chain.append(formatCrtFileContents(attestationCert)).append("\n");
            }

            ReturnStatus r = new ReturnStatus("OK", "Certificate Chain: " + chain.substring(0, 100) + "...");
            return r.toJsonString();

        } catch (Exception e) {
            ReturnStatus r = new ReturnStatus("FAIL", "Exception: " + e);
            return r.toJsonString();
        }

    }
}