package com.masreferenceapp.storage

import android.content.ContentValues
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.masreferenceapp.Status

class StorageSQLite(var context: ReactApplicationContext) : ReactContextBaseJavaModule(context) {
    private fun initDb(db: SQLiteDatabase) {
        // Table name and column names
        val TABLE_NAME = "myTable"
        val COLUMN_ID = "id"
        val COLUMN_NAME = "password"
        val CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_NAME + " TEXT" +
                ")"
        try {
            db.execSQL(CREATE_TABLE)
        } catch (e: SQLException) {
            e.printStackTrace()
        }
    }

    private fun deleteDb() {
        val dbpath = context.getDatabasePath("insecureSQLite.db")
        context.deleteDatabase(dbpath.absolutePath)
    }

    override fun getName(): String {
        return "StorageSQLite"
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    fun createSQLiteDB(): String {
        val dbpath = context.getDatabasePath("insecureSQLite.db")
        val plainTextDb = SQLiteDatabase.openOrCreateDatabase(dbpath, null)
        initDb(plainTextDb)
        deleteDb()
        return Status.status("OK", "Message")
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    fun insertData(): String {
        val dbpath = context.getDatabasePath("insecureSQLite.db")
        val plainTextDb = SQLiteDatabase.openOrCreateDatabase(dbpath, null)
        initDb(plainTextDb)
        val values = ContentValues()
        values.put("password", "SuperSecret")
        plainTextDb.insert("myTable", null, values)
        plainTextDb.close()
        deleteDb()
        return Status.status("OK", "Message")
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    fun updateData(): String {
        val dbpath = context.getDatabasePath("insecureSQLite.db")
        val plainTextDb = SQLiteDatabase.openOrCreateDatabase(dbpath, null)
        initDb(plainTextDb)
        val values = ContentValues()
        values.put("password", "SuperSecret")
        val id = plainTextDb.insert("myTable", null, values)
        values.put("password", "newSecretPW")
        plainTextDb.update("myTable", values, "id" + " = ?", arrayOf(id.toString()))
        deleteDb()
        return Status.status("OK", "Message")
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    fun deleteData(): String {
        val dbpath = context.getDatabasePath("insecureSQLite.db")
        val plainTextDb = SQLiteDatabase.openOrCreateDatabase(dbpath, null)
        initDb(plainTextDb)
        val values = ContentValues()
        values.put("password", "SuperSecret")
        val id = plainTextDb.insert("myTable", null, values)
        plainTextDb.delete("myTable", "id" + " = ?", arrayOf(id.toString()))
        plainTextDb.close()
        deleteDb()
        return Status.status("OK", "Message")
    }
}