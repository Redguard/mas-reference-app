package com.masreferenceapp.crypto;

import androidx.annotation.NonNull;
import androidx.security.crypto.EncryptedFile;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.masreferenceapp.Status;
import androidx.security.crypto.MasterKeys;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;


public class CryptoEncryptedFile extends ReactContextBaseJavaModule {
    ReactApplicationContext context;

    public CryptoEncryptedFile(ReactApplicationContext context) {
        super(context);
        this.context = context;
    }



    @NonNull
    @Override
    public String getName() {
        return "CryptoEncryptedFile";
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String writeFile(){
        try {
            String masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC);

            File file = new File(context.getFilesDir(), "masReferenceApp_secret_data");

            if (file.exists()){
               file.delete();
            }

            EncryptedFile encryptedFile = new EncryptedFile.Builder(
                    file,
                    context,
                    masterKeyAlias,
                    EncryptedFile.FileEncryptionScheme.AES256_GCM_HKDF_4KB).build();

            // write to the encrypted file
            FileOutputStream encryptedOutputStream = encryptedFile.openFileOutput();
            encryptedOutputStream.write("Some s3cr3t text".getBytes());
            encryptedOutputStream.close();



            StringBuilder hexString = new StringBuilder();
            byte[] buffer = new byte[16];
            int bytesRead;
            FileInputStream fis = new FileInputStream(context.getFilesDir() + "/masReferenceApp_secret_data");
            while ((bytesRead = fis.read(buffer)) != -1) {
                for (int i = 0; i < bytesRead; i++) {
                    String hexByte = Integer.toHexString(buffer[i] & 0xFF);
                    if (hexByte.length() == 1) {
                        hexString.append("0");
                    }
                    hexString.append(hexByte);
                }
            }

            return Status.status("OK", "Encrypted file content: " + hexString);

        } catch (Exception e) {
            return Status.status("FAIL", e.toString());
        }

    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String readFile(){
        try {
            String masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC);

            File file = new File(context.getFilesDir(), "masReferenceApp_read_secret");

            if (file.exists()){
                file.delete();
            }

            EncryptedFile encryptedFile = new EncryptedFile.Builder(
                    file,
                    context,
                    masterKeyAlias,
                    EncryptedFile.FileEncryptionScheme.AES256_GCM_HKDF_4KB).build();

            // write to the encrypted file
            FileOutputStream encryptedOutputStream = encryptedFile.openFileOutput();
            encryptedOutputStream.write("Some s3cr3t text to read".getBytes());
            encryptedOutputStream.close();



            FileInputStream encryptedInputStream = encryptedFile.openFileInput();
            StringBuilder outString = new StringBuilder();

            int character;
            // read character by character by default
            // read() function return int between 0 and 255.

            while ((character = encryptedInputStream.read()) != -1) {
                outString.append((char)character);
            }

            return Status.status("OK", "Decrypted file content: "+ outString);

        } catch (Exception e) {
            return Status.status("FAIL", e.toString());
        }
    }

}