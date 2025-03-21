package org.masreferenceapp.storage;

import android.content.Context;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import org.masreferenceapp.ReturnStatus;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
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
    public String getFilesDir() {
        return new ReturnStatus("OK", "Files directory: " + context.getFilesDir().toString()).toJsonString();
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String getCacheDir() {
        return new ReturnStatus("OK", "Cache directory: " + context.getCacheDir().toString()).toJsonString();
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String writeFileOutput() {

        ReturnStatus r = new ReturnStatus();

        writeFileOutputMODE(Context.MODE_PRIVATE, r);

        return r.toJsonString();
    }


    @ReactMethod(isBlockingSynchronousMethod = true)
    public String writeFileOutputModes() {

        List<Number> modes = new ArrayList<>();
        modes.add(Context.MODE_PRIVATE);
        modes.add(Context.MODE_APPEND);
        modes.add(Context.MODE_WORLD_READABLE);
        modes.add(Context.MODE_WORLD_WRITEABLE);

        StringBuilder status = new StringBuilder();
        StringBuilder message = new StringBuilder();

        ReturnStatus r = new ReturnStatus();
        for (Number mode : modes) {
            writeFileOutputMODE((int) mode, r);
        }
        return r.toJsonString();
    }


    @ReactMethod(isBlockingSynchronousMethod = true)
    public String readTextFile() {
        String[] returnValues = new String[2];
        returnValues[0] = "OK";
        returnValues[1] = "";

        // make sure a file exists
        this.writeFileOutput();

        ReturnStatus r = new ReturnStatus();

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
            r.addStatus("OK", "File with following content read: " + stringBuilder);

        } catch (Exception e) {
            r.addStatus("FAIL", r.toJsonString());
        }

        return r.toJsonString();
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String getInternalFileList() {
        String[] file = context.fileList();
        return new ReturnStatus("OK", "Internal File List: " + Arrays.toString(file)).toJsonString();
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String createCacheFile() {
        String statuscode = "OK";
        String message = "";
        File file;
        try {
            file = File.createTempFile("someCacheFileName", null, context.getCacheDir());
            message = file.toString();
        } catch (Exception e) {
            message = e.toString();
            statuscode = "FAIL";
        }

        return new ReturnStatus(statuscode, "Cache file created: " + message).toJsonString();
    }


    private void writeFileOutputMODE(int mode, ReturnStatus r) {
        String filename = "masTestFile";
        String fileContents = "Some secret Message in the internal sandbox!";
        try (FileOutputStream fos = context.openFileOutput(filename, mode)) {
            fos.write(fileContents.getBytes(StandardCharsets.UTF_8));
            r.addStatus("OK", "File successfully written. ");

        } catch (Exception e) {
            r.addStatus("FAIL", e.toString());
        }
    }

}