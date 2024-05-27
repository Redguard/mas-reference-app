package com.masreferenceapp.storage;

import static android.content.Context.MODE_PRIVATE;
import static android.database.sqlite.SQLiteDatabase.openOrCreateDatabase;

import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.masreferenceapp.Status;


public class StorageSQLite extends ReactContextBaseJavaModule {
    ReactApplicationContext context;

    public StorageSQLite(ReactApplicationContext context) {
        super(context);
        this.context = context;
    }

    @NonNull
    @Override
    public String getName() {

        return "StorageSQLite";

    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String createSQLiteDB(){

        SQLiteDatabase plainTextDb = openOrCreateDatabase("/insecureSQLite.db",null);

        return Status.status("OK", "Message");
    }
}