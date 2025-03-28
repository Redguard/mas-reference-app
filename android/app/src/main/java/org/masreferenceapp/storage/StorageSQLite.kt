package org.masreferenceapp.storage

import android.content.ContentValues
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import org.masreferenceapp.ReturnStatus
import org.masreferenceapp.SensitiveData


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

    override fun getName(): String {
        return "StorageSQLite"
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    fun createSQLiteDB(): String {
        val dbpath = context.getDatabasePath("MasSqlite_" + (0..1000000).random() + ".db")
        val plainTextDb = SQLiteDatabase.openOrCreateDatabase(dbpath, null)
        initDb(plainTextDb)
        return ReturnStatus("OK", "Database created at: $dbpath").toJsonString()
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    fun execSensitiveSQLStoredProcedures(): String {
        val dbpath = context.getDatabasePath("MasSqlite_" + (0..1000000).random() + ".db")
        val plainTextDb = SQLiteDatabase.openOrCreateDatabase(dbpath, null)
        initDb(plainTextDb)

        val insertQuery = "INSERT INTO myTable (password) VALUES (?)"

        plainTextDb.execSQL(insertQuery, arrayOf(SensitiveData.data))

        plainTextDb.close()

        val r = ReturnStatus(
            "OK",
            "Using execSQL() to insert sensitive data into DB stored at: $dbpath"
        )
        return r.toJsonString()
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    fun insertSensitive(): String {
        val dbpath = context.getDatabasePath("MasSqlite_" + (0..1000000).random() + ".db")
        val plainTextDb = SQLiteDatabase.openOrCreateDatabase(dbpath, null)
        initDb(plainTextDb)

        val values = ContentValues()
        values.put("password", SensitiveData.data)

        plainTextDb.insert("myTable", null, values)

        plainTextDb.close()

        val r =
            ReturnStatus("OK", "Using insert() to insert sensitive data into DB stored at: $dbpath")
        return r.toJsonString()
    }


    @ReactMethod(isBlockingSynchronousMethod = true)
    fun replaceSensitive(): String {
        val dbpath = context.getDatabasePath("MasSqlite_" + (0..1000000).random() + ".db")
        val plainTextDb = SQLiteDatabase.openOrCreateDatabase(dbpath, null)
        initDb(plainTextDb)

        plainTextDb.execSQL("INSERT INTO myTable (password) VALUES (\"DummyData\")")
        val newValues = ContentValues()
        newValues.put("password", SensitiveData.data)

        plainTextDb.replace("myTable", null, newValues)

        plainTextDb.close()

        val r = ReturnStatus(
            "OK",
            "Using replace() to insert sensitive data into DB stored at: $dbpath"
        )
        return r.toJsonString()
    }


    @ReactMethod(isBlockingSynchronousMethod = true)
    fun updateSensitive(): String {
        val dbpath = context.getDatabasePath("MasSqlite_" + (0..1000000).random() + ".db")
        val plainTextDb = SQLiteDatabase.openOrCreateDatabase(dbpath, null)
        initDb(plainTextDb)

        plainTextDb.execSQL("INSERT INTO myTable (password) VALUES (\"DummyData\")")

        val newValues = ContentValues()
        newValues.put("password", SensitiveData.data)

        val selection: String = "id = ?"
        val selectionArgs: Array<String> = arrayOf("1")

        plainTextDb.update("myTable", newValues, selection, selectionArgs)

        plainTextDb.close()

        val r =
            ReturnStatus("OK", "Using update() to insert sensitive data into DB stored at: $dbpath")
        return r.toJsonString()
    }


    //@method
}