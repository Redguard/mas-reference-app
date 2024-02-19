package com.masreferenceapp.storage;

import android.os.Environment;
import android.util.Log;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.masreferenceapp.Status;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class StorageLog extends ReactContextBaseJavaModule {
    ReactApplicationContext context;

    public StorageLog(ReactApplicationContext context) {
        super(context);
        this.context = context;
    }


    private void logToAll(String message){
        String tag = "MasReferenceAppTestLog";
        Log.v(tag, message);
        Log.d(tag, message);
        Log.i(tag, message);
        Log.w(tag, message);
        Log.e(tag, message);
    }

    @NonNull
    @Override
    public String getName() {
        return "StorageLog";
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String logName(){

        List<String> messages = new ArrayList<>();
        messages.add("Name: test");
        messages.add("Name:test");
        messages.add("name: test");
        messages.add("name:   test");
        messages.add("First Name: test");
        messages.add("Last Name: test");
        messages.add("Vorname: test");
        messages.add("Vorname:test");
        messages.add("Nachname: test");

        StringBuilder status = new StringBuilder();
        StringBuilder message = new StringBuilder();;

        for (String m : messages) {
            logToAll(m);
        }
        return Status.status("OK", "Data Logged to all Logs");
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String logPassword(){
        List<String> messages = new ArrayList<>();
        messages.add("Password: test");
        messages.add("password: test");
        messages.add("Passwd: test");
        messages.add("passwd: test");
        messages.add("pwd: test");
        messages.add("Pwd: test");
        messages.add("PWD: test");
        messages.add("Secret: test");
        messages.add("secret: test");
        messages.add("PIN: test");
        messages.add("pin: test");
        messages.add("Passphrase: test");
        messages.add("passphrase: test");
        messages.add("passphrase:   test");
        messages.add("PassPhrase: test");
        messages.add("Pass Phrase: test");
        messages.add("Pass Phrase:test");
        messages.add("Private Key: test");
        messages.add("PrivateKey: test");
        messages.add("Secret Key: test");
        messages.add("SecretKey: test");

        StringBuilder status = new StringBuilder();
        StringBuilder message = new StringBuilder();;

        for (String m : messages) {
            logToAll(m);
        }
        return Status.status("OK", "Data Logged to all Logs");
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String logSecrete(){
        List<String> messages = new ArrayList<>();
        messages.add("Private Key: test");
        messages.add("PrivateKey: test");
        messages.add("Secret Key: test");
        messages.add("SecretKey: test");

        StringBuilder status = new StringBuilder();
        StringBuilder message = new StringBuilder();;

        for (String m : messages) {
            logToAll(m);
        }
        return Status.status("OK", "Data Logged to all Logs");
    }


    @ReactMethod(isBlockingSynchronousMethod = true)
    public String logPEM(){
        List<String> messages = new ArrayList<>();
        messages.add("-----BEGIN CERTIFICATE-----");
        messages.add("-----BEGIN RSA PRIVATE KEY-----");
        messages.add("-----BEGIN RSA PRIVATE KEY-----");
        messages.add("-----BEGIN DSA PRIVATE KEY-----");
        messages.add("-----BEGIN DSA PUBLIC KEY-----");
        messages.add("-----BEGIN EC PRIVATE KEY-----");
        messages.add("-----BEGIN EC PUBLIC KEY-----");
        messages.add("-----BEGIN DH PARAMETERS-----");
        messages.add("-----BEGIN PRIVATE KEY-----");
        messages.add("-----BEGIN PUBLIC KEY-----");
        messages.add("-----BEGIN EC PRIVATE KEY-----");
        messages.add("-----BEGIN ENCRYPTED PRIVATE KEY-----");
        messages.add("-----END RSA PRIVATE KEY-----");
        messages.add("-----END EC PRIVATE KEY-----");
        messages.add("Proc-Type: 4,ENCRYPTED");
        messages.add("DEK-Info: AES-256-CBC,F6F1F37584D8189C97F23F9DCD431B42");

        StringBuilder status = new StringBuilder();
        StringBuilder message = new StringBuilder();;

        for (String m : messages) {
            logToAll(m);
        }
        return Status.status("OK", "Data Logged to all Logs");
    }


    @ReactMethod(isBlockingSynchronousMethod = true)
    public String logPhoneNumber(){
        List<String> messages = new ArrayList<>();
        messages.add("PhoneNumber: +41763452345");
        messages.add("phoneNumber: +41 (076) 3452345");
        messages.add("phoneNumber:+41 (076) 3452345");
        messages.add("phone: +41 (076) 3452345");
        messages.add("phone number: +41 (076) 3452345");
        messages.add("Phone Number: +41 (076) 3452345");

        StringBuilder status = new StringBuilder();
        StringBuilder message = new StringBuilder();;

        for (String m : messages) {
            logToAll(m);
        }
        return Status.status("OK", "Data Logged to all Logs");
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String logEmail(){

        List<String> messages = new ArrayList<>();
        messages.add("eMail: fakeuser1@gmail.com");
        messages.add("e-Mail: fakeuser1@gmail.com");
        messages.add("e Mail: fakeuser1@gmail.com");
        messages.add("e mail: fakeuser1@gmail.com");
        messages.add("EMail: fakeuser1@gmail.com");
        messages.add("EMAIL:fakeuser1@gmail.com");
        messages.add("EMAIL:    fakeuser1@gmail.com");
        messages.add("EMAIL: fakeuser1@gmail.com");
        messages.add("fakeuser2@yahoo.com");
        messages.add("fakeuser4@hotmail.com");
        messages.add("fakeuser_11@bluewin.ch");
        messages.add("fakeuser16@protonmail.ch");

        StringBuilder status = new StringBuilder();
        StringBuilder message = new StringBuilder();;

        for (String m : messages) {
            logToAll(m);
        }
        return Status.status("OK", "Data Logged to all Logs");

    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String logFinData(){
        return Status.status("OK", "Data Logged to all Logs");
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String logLocation(){
        return Status.status("OK", "Data Logged to all Logs");
    }
    @ReactMethod(isBlockingSynchronousMethod = true)
    public String locAccessToken(){
        return Status.status("OK", "Data Logged to all Logs");
    }
}