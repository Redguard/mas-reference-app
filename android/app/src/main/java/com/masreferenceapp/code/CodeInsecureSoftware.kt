package com.masreferenceapp.code

import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.masreferenceapp.ReturnStatus
import com.masreferenceapp.SensitiveData
import org.xmlpull.v1.XmlPullParserFactory
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.io.Serializable


class CodeInsecureSoftware(var context: ReactApplicationContext) : ReactContextBaseJavaModule(
    context
) {
    override fun getName(): String {
        return "CodeInsecureSoftware"
    }

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


    @ReactMethod(isBlockingSynchronousMethod = true)
    fun sqlInjection(): String {
        val r = ReturnStatus()
        try {
            val dbpath = context.getDatabasePath("MasSqlite_" + (0..1000000).random() + ".db")
            val plainTextDb = SQLiteDatabase.openOrCreateDatabase(dbpath, null)
            initDb(plainTextDb)

            plainTextDb.execSQL("INSERT INTO myTable (password) VALUES (\"" + SensitiveData.data + "\")")

            plainTextDb.close()

            r.success("Using execSQL(sql: String) to insert sensitive data into DB stored at: $dbpath")
        } catch (e: Exception) {
            r.fail(e.toString())
        }
        return r.toJsonString()
    }


    @ReactMethod(isBlockingSynchronousMethod = true)
    fun XmlExternalEntity(): String {
        val r = ReturnStatus()
        try {
            val factory = XmlPullParserFactory.newInstance()
            factory.isNamespaceAware = true
            factory.setFeature("http://xml.org/sax/features/external-general-entities", false)
            factory.setFeature("http://xml.org/sax/features/external-parameter-entities", false)
            factory.setFeature(
                "http://apache.org/xml/features/nonvalidating/load-external-dtd",
                false
            )

            r.success("XML PullParser hardened to make it resilient against XEE attacks.")
        } catch (e: Exception) {
            r.fail(e.toString())
        }
        return r.toJsonString()
    }

    data class User(val id: Int, val name: String, val email: String) : Serializable

    @ReactMethod(isBlockingSynchronousMethod = true)
    fun insecureDeserialisation(): String {
        val r = ReturnStatus()
        try {
            var user = User(id = 1, name = "John Doe", email = "john.doe@mas.owasp.org")

            val byteArrayOutputStream = ByteArrayOutputStream()
            ObjectOutputStream(byteArrayOutputStream).use { it.writeObject(user) }

            val b = byteArrayOutputStream.toByteArray()

            val byteArrayInputStream = ByteArrayInputStream(b)

            user = ObjectInputStream(byteArrayInputStream).use { it.readObject() as User }

            r.success("Serialized and deserialized an object using ObjectInputStream")
        } catch (e: Exception) {
            r.fail(e.toString())
        }
        return r.toJsonString()
    }

    //@method
}