package com.masreferenceapp.storage;

import android.content.Context;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.masreferenceapp.Status;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class StorageInternalStorage extends ReactContextBaseJavaModule {
    ReactApplicationContext context;

    public StorageInternalStorage(ReactApplicationContext context) {
        super(context);
        this.context = context;
    }

    @NonNull
    @Override
    public String getName() {
        return "StorageInternalStorage";
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String getFilesDir(){
        return Status.status("OK", context.getFilesDir().toString());
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String getCacheDir(){
        return Status.status("OK", context.getCacheDir().toString());
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String writeFileOutput(){

        String[] returnValues = writeFileOutputMODE(Context.MODE_PRIVATE);

        return Status.status(returnValues[0], returnValues[1]);
    }


    @ReactMethod(isBlockingSynchronousMethod = true)
    public String writeFileOutputModes(){

        List<Number> modes = new ArrayList<>();
        modes.add(Context.MODE_PRIVATE);
        modes.add(Context.MODE_APPEND);
        modes.add(Context.MODE_WORLD_READABLE);
        modes.add(Context.MODE_WORLD_WRITEABLE);

        StringBuilder status = new StringBuilder();
        StringBuilder message = new StringBuilder();;

        for (Number mode : modes) {
            String[] returnValues = writeFileOutputMODE((int)mode);
            status = status.append("[" + returnValues[0] + "]");
            message = message.append("["+ returnValues[1] + "]");
        }
        return Status.status(status.toString(), message.toString());
    }



    private String[] writeFileOutputMODE(int mode){
        String[] returnValues = new String[2];
        String message = "";
        String filename = "masTestFile";
        String fileContents = "Some secret Message in the internal sandbox!";
        try (FileOutputStream fos = context.openFileOutput(filename, mode)) {
            fos.write(fileContents.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            returnValues[0] = "FAIL";
            returnValues[1] = e.toString();
        }
        returnValues[0] = "OK";
        returnValues[1] = "File successfully written";

        return returnValues;
    }



    @ReactMethod(isBlockingSynchronousMethod = true)
    public String readTextFile(){
        String[] returnValues = new String[2];
        returnValues[0] = "OK";
        returnValues[1] = "";

        // make sure a file exists
        this.writeFileOutput();

        try {
            FileInputStream fis = context.openFileInput("masTestFile");

            InputStreamReader inputStreamReader = new InputStreamReader(fis, StandardCharsets.UTF_8);
            StringBuilder stringBuilder = new StringBuilder();
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                stringBuilder.append(line).append('\n');
                line = reader.readLine();
            }
            returnValues[1] = stringBuilder.toString();

        } catch (Exception e){
            returnValues[0] = "FAIL";
            returnValues[1] = e.toString();
        }

        return Status.status(returnValues[0], returnValues[1]);
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String getInternalFileList(){
        String[] file =  context.fileList();
        return Status.status("OK",  Arrays.toString(file));
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String createCacheFile(){
        String statuscode = "OK";
        String message = "";
        File file;
        try {
            file = File.createTempFile("someCacheFileName", null, context.getCacheDir());
            message = file.toString();
        } catch (Exception e) {
            message = e.toString();
            statuscode = "FAIL";
        };

        return Status.status(statuscode,  message);
    }
}