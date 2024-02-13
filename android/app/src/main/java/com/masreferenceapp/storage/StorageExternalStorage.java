package com.masreferenceapp.storage;

import android.content.Context;
import android.os.Environment;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.masreferenceapp.Status;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;


public class StorageExternalStorage extends ReactContextBaseJavaModule {
    ReactApplicationContext context;

    public StorageExternalStorage(ReactApplicationContext context) {
        super(context);
        this.context = context;
    }

    @NonNull
    @Override
    public String getName() {
        return "StorageExternalStorage";
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String checkState(){
        return Status.status("OK", Environment.getExternalStorageState());
    }


    @ReactMethod(isBlockingSynchronousMethod = true)
    public String getExternalFilesDirRoot(){
        String message = "";
        File externalFile = context.getExternalFilesDir(null);
        if (externalFile == null){
            message = "";
        }else{
            message = externalFile.toString();
        }
        return Status.status("OK", message);
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String getExternalCacheDir(){
        String message = "";
        File externalFile = context.getExternalCacheDir();
        if (externalFile == null){
            message = "";
        }else{
            message = externalFile.toString();
        }
        return Status.status("OK", message);
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String getDifferentExternalDirs(){

        List<String> types = new ArrayList<>();
        types.add( Environment.DIRECTORY_MUSIC);
        types.add( Environment.DIRECTORY_PODCASTS);
        types.add( Environment.DIRECTORY_RINGTONES);
        types.add( Environment.DIRECTORY_ALARMS);
        types.add( Environment.DIRECTORY_NOTIFICATIONS);
        types.add( Environment.DIRECTORY_PICTURES);
        types.add( Environment.DIRECTORY_MOVIES);

        StringBuilder status = new StringBuilder();
        StringBuilder message = new StringBuilder();;

        for (String type : types) {
            File externalFile = context.getExternalFilesDir(type);
            if (externalFile == null){
                status.append("[FAIL]");
            }else{
                status.append("[OK]");
                message.append("[" + externalFile.toString() + "]");
            }
        }

        return Status.status(status.toString(), message.toString());
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String writeFileOutput(){

        String[] returnValues = new String[2];
        String message = "";

        String fileContents = "Some secret Message in the external sandbox!";

        try {
            String file = "masExternalTestfile";
            File appSpecificExternalFile = new File(context.getExternalFilesDir(null), file);
            FileOutputStream fos = new FileOutputStream(appSpecificExternalFile);
            fos.write(fileContents.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            returnValues[0] = "FAIL";
            returnValues[1] = e.toString();
        }
        returnValues[0] = "OK";
        returnValues[1] = "File successfully written";

        return Status.status(returnValues[0], returnValues[1]);
    }



    @ReactMethod(isBlockingSynchronousMethod = true)
    public String readTextFile(){
        String[] returnValues = new String[2];
        returnValues[0] = "OK";
        returnValues[1] = "";

        // make sure a file exists
        this.writeFileOutput();

        try {
            String file = "masExternalTestfile";
            File appSpecificExternalFile = new File(context.getExternalFilesDir(null), file);
            FileInputStream fis = new FileInputStream(appSpecificExternalFile);
            InputStreamReader inputStreamReader = new InputStreamReader(fis, StandardCharsets.UTF_8);
            StringBuilder stringBuilder = new StringBuilder();
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
                while (line != null) {
                    stringBuilder.append(line).append('\n');
                    line = reader.readLine();}

            returnValues[1] = stringBuilder.toString();
        } catch (Exception e){
            returnValues[0] = "FAIL";
            returnValues[1] = e.toString();
        }

        return Status.status(returnValues[0], returnValues[1]);

    }
}