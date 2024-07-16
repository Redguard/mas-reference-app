package com.masreferenceapp.storage;

import static android.content.Context.MODE_PRIVATE;
import static android.database.sqlite.SQLiteDatabase.openOrCreateDatabase;

import android.content.ContentValues;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.masreferenceapp.Status;

import java.io.File;


public class StorageSQLite extends ReactContextBaseJavaModule {
    ReactApplicationContext context;

    public StorageSQLite(ReactApplicationContext context) {
        super(context);
        this.context = context;
    }

    private void initDb(SQLiteDatabase db){
        // Table name and column names
        String TABLE_NAME = "myTable";
        String COLUMN_ID = "id";
        String COLUMN_NAME = "password";
        String CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME + "(" +
                        COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                        COLUMN_NAME + " TEXT" +
                        ")";

        try {
            db.execSQL(CREATE_TABLE);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void deleteDb(){
        File dbpath = context.getDatabasePath("insecureSQLite.db");
        context.deleteDatabase(dbpath.getAbsolutePath());
    }

    @NonNull
    @Override
    public String getName() {

        return "StorageSQLite";

    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String createSQLiteDB(){

        File dbpath = context.getDatabasePath("insecureSQLite.db");
        SQLiteDatabase plainTextDb = openOrCreateDatabase(dbpath,null);
        this.initDb(plainTextDb);
        this.deleteDb();

        return Status.status("OK", "Message");
    }


    @ReactMethod(isBlockingSynchronousMethod = true)
    public String insertData(){

        File dbpath = context.getDatabasePath("insecureSQLite.db");
        SQLiteDatabase plainTextDb = openOrCreateDatabase(dbpath,null);
        this.initDb(plainTextDb);

        ContentValues values = new ContentValues();
        values.put("password", "SuperSecret");
        plainTextDb.insert("myTable", null, values);
        plainTextDb.close();

        this.deleteDb();

        return Status.status("OK", "Message");
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String updateData(){

        File dbpath = context.getDatabasePath("insecureSQLite.db");
        SQLiteDatabase plainTextDb = openOrCreateDatabase(dbpath,null);
        this.initDb(plainTextDb);

        ContentValues values = new ContentValues();
        values.put("password", "SuperSecret");
        long id = plainTextDb.insert("myTable", null, values);

        values.put("password", "newSecretPW");
        plainTextDb.update("myTable", values, "id" + " = ?", new String[]{String.valueOf(id)});

        this.deleteDb();

        return Status.status("OK", "Message");
    }


    @ReactMethod(isBlockingSynchronousMethod = true)
    public String deleteData(){

        File dbpath = context.getDatabasePath("insecureSQLite.db");
        SQLiteDatabase plainTextDb = openOrCreateDatabase(dbpath,null);
        this.initDb(plainTextDb);

        ContentValues values = new ContentValues();
        values.put("password", "SuperSecret");
        long id = plainTextDb.insert("myTable", null, values);


        plainTextDb.delete("myTable", "id" + " = ?",  new String[]{String.valueOf(id)});

        plainTextDb.close();
        this.deleteDb();

        return Status.status("OK", "Message");
    }

}