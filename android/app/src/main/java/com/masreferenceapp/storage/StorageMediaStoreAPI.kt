package com.masreferenceapp.storage

import android.content.ContentValues
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.masreferenceapp.ReturnStatus
import com.masreferenceapp.SensitiveData
import java.io.InputStream
import java.io.OutputStream


class StorageMediaStoreAPI(var context: ReactApplicationContext) : ReactContextBaseJavaModule(
    context
) {
    override fun getName(): String {
        return "StorageMediaStoreAPI"
    }

    private fun writeFile(location: String, r: ReturnStatus): Uri? {

        val fileName = "MAS_Test_File_" + (0..1000000).random() + ".txt"
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
            put(MediaStore.MediaColumns.MIME_TYPE, "text/plain")
            put(MediaStore.MediaColumns.RELATIVE_PATH, location)
        }

        val contentResolver = context.contentResolver

        val uri = contentResolver.insert(MediaStore.Files.getContentUri("external"), contentValues)

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
        r.success(
            "Data written to external, public folder ($location). URI-Path is: " + (uri?.path ?: "")
        )

        return uri
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    fun writeDocument(): String {
        val r = ReturnStatus()

        val publicDirs = arrayOf(
            Environment.DIRECTORY_DOCUMENTS,
            Environment.DIRECTORY_DOWNLOADS,
            Environment.DIRECTORY_PICTURES,
            Environment.DIRECTORY_MUSIC,
            Environment.DIRECTORY_MOVIES,
            Environment.DIRECTORY_DCIM,
            Environment.DIRECTORY_ALARMS,
            Environment.DIRECTORY_NOTIFICATIONS,
            Environment.DIRECTORY_PODCASTS,
            Environment.DIRECTORY_RINGTONES
        )

        for (directory in publicDirs) {
            try {
                writeFile(directory, r)
            } catch (e: Exception) {
                r.fail(e.toString())
            }
        }
        return r.toJsonString()
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    fun readDownload(): String {

        val r = ReturnStatus()

        val uri = writeFile(Environment.DIRECTORY_DOWNLOADS, r)

        val contentResolver = context.contentResolver

        uri?.let {
            var inputStream: InputStream? = null
            try {
                inputStream = contentResolver.openInputStream(uri)
                val inputAsString = inputStream?.reader()?.readText()
                r.success("File successfully read. Content: $inputAsString")
                inputStream?.close()

            } catch (e: Exception) {
                r.fail(e.toString())
            } finally {
                inputStream?.close()
            }
        }
        return r.toJsonString()
    }

    //@method
}