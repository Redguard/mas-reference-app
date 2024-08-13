package com.masreferenceapp.storage

import android.os.Environment
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.masreferenceapp.Status
import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets

class StorageExternalStorage(private val context: ReactApplicationContext) : ReactContextBaseJavaModule(context) {
    override fun getName(): String {
        return "StorageExternalStorage"
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    fun checkState(): String {
        return Status.status("OK", Environment.getExternalStorageState())
    }

    @get:ReactMethod(isBlockingSynchronousMethod = true)
    val externalFilesDirRoot: String
        get() {
            val externalFile = context.getExternalFilesDir(null)
            val message = externalFile?.toString() ?: ""
            return Status.status("OK", message)
        }

    @get:ReactMethod(isBlockingSynchronousMethod = true)
    val externalCacheDir: String
        get() {
            val externalFile = context.externalCacheDir
            val message = externalFile?.toString() ?: ""
            return Status.status("OK", message)
        }

    @get:ReactMethod(isBlockingSynchronousMethod = true)
    val differentExternalDirs: String
        get() {
            val types = listOf(
                Environment.DIRECTORY_MUSIC,
                Environment.DIRECTORY_PODCASTS,
                Environment.DIRECTORY_RINGTONES,
                Environment.DIRECTORY_ALARMS,
                Environment.DIRECTORY_NOTIFICATIONS,
                Environment.DIRECTORY_PICTURES,
                Environment.DIRECTORY_MOVIES
            )
            val status = StringBuilder()
            val message = StringBuilder()
            for (type in types) {
                val externalFile = context.getExternalFilesDir(type)
                if (externalFile == null) {
                    status.append("[FAIL]")
                } else {
                    status.append("[OK]")
                    message.append("[$externalFile]")
                }
            }
            return Status.status(status.toString(), message.toString())
        }

    @ReactMethod(isBlockingSynchronousMethod = true)
    fun writeFileOutput(): String {
        val returnValues = arrayOfNulls<String>(2)
        val fileContents = "Some secret Message in the external sandbox!"
        try {
            val file = "masExternalTestfile"
            val appSpecificExternalFile = File(context.getExternalFilesDir(null), file)
            FileOutputStream(appSpecificExternalFile).use { fos ->
                fos.write(fileContents.toByteArray(StandardCharsets.UTF_8))
            }
            returnValues[0] = "OK"
            returnValues[1] = "File successfully written"
        } catch (e: Exception) {
            returnValues[0] = "FAIL"
            returnValues[1] = e.toString()
        }
        return Status.status(returnValues[0], returnValues[1])
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    fun readTextFile(): String {
        val returnValues = arrayOfNulls<String>(2)
        returnValues[0] = "OK"
        returnValues[1] = ""

        // Ensure the file exists by writing it first
        writeFileOutput()

        try {
            val file = "masExternalTestfile"
            val appSpecificExternalFile = File(context.getExternalFilesDir(null), file)
            FileInputStream(appSpecificExternalFile).use { fis ->
                InputStreamReader(fis, StandardCharsets.UTF_8).use { inputStreamReader ->
                    BufferedReader(inputStreamReader).use { reader ->
                        val stringBuilder = StringBuilder()
                        stringBuilder.append(reader.readText())
                        returnValues[1] = stringBuilder.toString()
                    }
                }
            }
        } catch (e: Exception) {
            returnValues[0] = "FAIL"
            returnValues[1] = e.toString()
        }

        return Status.status(returnValues[0], returnValues[1])
    }
}
