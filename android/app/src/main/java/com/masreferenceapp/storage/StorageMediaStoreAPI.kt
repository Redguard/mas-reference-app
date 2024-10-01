package com.masreferenceapp.storage

import android.content.ContentValues
import android.os.Environment
import android.provider.MediaStore
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import java.io.OutputStream

import com.masreferenceapp.ReturnStatus
import com.masreferenceapp.SensitiveData


class StorageMediaStoreAPI(var context: ReactApplicationContext) : ReactContextBaseJavaModule(
    context
) {
    override fun getName(): String {
        return "StorageMediaStoreAPI"
    }

        
    @ReactMethod(isBlockingSynchronousMethod = true)
    fun writeDocument(): String {

        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, "MAS_Test_File_" + (0..1000000).random() + ".txt")
            put(MediaStore.MediaColumns.MIME_TYPE, "text/plain")
            put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOCUMENTS)
        }

        val contentResolver = context.contentResolver

        // This line indicates if the file is stored internally or externally
        val uri = contentResolver.insert(MediaStore.Files.getContentUri("external"), contentValues)

        val r = ReturnStatus()

        uri?.let {
            var outputStream: OutputStream? = null
            try {
                outputStream = contentResolver.openOutputStream(uri)
                outputStream?.write(SensitiveData.data.toByteArray())
                outputStream?.close()

            } catch (e: Exception) {
                r.addStatus("FAIL", e.toString())
            } finally {
                outputStream?.close()
            }
        }
        r.addStatus("OK", "Data written to External Public Document. URI-Path is: " + (uri?.path
            ?: ""))

        return r.toJsonString()
    }

    //@method
}