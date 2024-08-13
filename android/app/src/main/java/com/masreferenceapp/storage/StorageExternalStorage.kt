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

class StorageExternalStorage(var context: ReactApplicationContext) : ReactContextBaseJavaModule(
    context
) {
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
            var message = ""
            val externalFile = context.getExternalFilesDir(null)
            message = externalFile?.toString() ?: ""
            return Status.status("OK", message)
        }

    @get:ReactMethod(isBlockingSynchronousMethod = true)
    val externalCacheDir: String
        get() {
            var message = ""
            val externalFile = context.externalCacheDir
            message = externalFile?.toString() ?: ""
            return Status.status("OK", message)
        }

    @get:ReactMethod(isBlockingSynchronousMethod = true)
    val differentExternalDirs: String
        get() {
            val types: MutableList<String> = ArrayList()
            types.add(Environment.DIRECTORY_MUSIC)
            types.add(Environment.DIRECTORY_PODCASTS)
            types.add(Environment.DIRECTORY_RINGTONES)
            types.add(Environment.DIRECTORY_ALARMS)
            types.add(Environment.DIRECTORY_NOTIFICATIONS)
            types.add(Environment.DIRECTORY_PICTURES)
            types.add(Environment.DIRECTORY_MOVIES)
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
        val message = ""
        val fileContents = "Some secret Message in the external sandbox!"
        try {
            val file = "masExternalTestfile"
            val appSpecificExternalFile = File(context.getExternalFilesDir(null), file)
            val fos = FileOutputStream(appSpecificExternalFile)
            fos.write(fileContents.toByteArray(StandardCharsets.UTF_8))
        } catch (e: Exception) {
            returnValues[0] = "FAIL"
            returnValues[1] = e.toString()
        }
        returnValues[0] = "OK"
        returnValues[1] = "File successfully written"
        return Status.status(returnValues[0], returnValues[1])
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    fun readTextFile(): String {
        val returnValues = arrayOfNulls<String>(2)
        returnValues[0] = "OK"
        returnValues[1] = ""

        // make sure a file exists
        writeFileOutput()
        try {
            val file = "masExternalTestfile"
            val appSpecificExternalFile = File(context.getExternalFilesDir(null), file)
            val fis = FileInputStream(appSpecificExternalFile)
            val inputStreamReader = InputStreamReader(fis, StandardCharsets.UTF_8)
            val stringBuilder = StringBuilder()
            val reader = BufferedReader(inputStreamReader)
            var line = reader.readLine()
            while (line != null) {
                stringBuilder.append(line).append('\n')
                line = reader.readLine()
            }
            returnValues[1] = stringBuilder.toString()
        } catch (e: Exception) {
            returnValues[0] = "FAIL"
            returnValues[1] = e.toString()
        }
        return Status.status(returnValues[0], returnValues[1])
    }
}