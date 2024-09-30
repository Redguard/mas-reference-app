package com.masreferenceapp.storage

import android.content.Context
import android.os.Environment
import androidx.core.content.ContextCompat
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.masreferenceapp.MasSettings
import com.masreferenceapp.ReturnStatus
import com.masreferenceapp.SensitiveData
import java.io.File
import java.nio.charset.StandardCharsets


class StorageJavaFileIo(var context: ReactApplicationContext) : ReactContextBaseJavaModule(
    context
) {
    override fun getName(): String {
        return "StorageJavaFileIo"
    }

        
    @ReactMethod(isBlockingSynchronousMethod = true)
    fun writeExternal(): String {
        val r = ReturnStatus()
        writeFileOutputMODE(Context.MODE_PRIVATE, r, MasSettings.getCanaryToken())
        return r.toJsonString()
    }


    @ReactMethod(isBlockingSynchronousMethod = true)
    fun writeExternalWorldWritable(): String {
        val r = ReturnStatus()
        writeExternalFileOutputMODE(Context.MODE_WORLD_WRITEABLE, r, SensitiveData.data)
        return r.toJsonString()
    }


    @ReactMethod(isBlockingSynchronousMethod = true)
    fun writeExternalWorldReadable(): String {
        val r = ReturnStatus()
        writeExternalFileOutputMODE(Context.MODE_WORLD_READABLE, r, SensitiveData.data)
        return r.toJsonString()
    }


    @ReactMethod(isBlockingSynchronousMethod = true)
    fun writeSandboxWorldReadable(): String {
        val r = ReturnStatus()
        writeFileOutputMODE(Context.MODE_WORLD_READABLE, r, SensitiveData.data)
        return r.toJsonString()
    }


    @ReactMethod(isBlockingSynchronousMethod = true)
    fun writeSandboxWorldWritable(): String {
        val r = ReturnStatus()
        writeFileOutputMODE(Context.MODE_WORLD_WRITEABLE, r, SensitiveData.data)
        return r.toJsonString()
    }


        
    @ReactMethod(isBlockingSynchronousMethod = true)
    fun writeCtFileSandbox(): String {
        val r = ReturnStatus()
        writeExternalFileOutputMODE(Context.MODE_PRIVATE, r, MasSettings.getCanaryToken())
        return r.toJsonString()
    }

        
    @ReactMethod(isBlockingSynchronousMethod = true)
    fun writeSensitiveFileSandbox(): String {
        val r = ReturnStatus()
        writeFileOutputMODE(Context.MODE_PRIVATE, r, SensitiveData.data)
        return r.toJsonString()
    }

    private fun writeFileOutputMODE(mode: Int, r: ReturnStatus, content: String) {
        val random = (0..1000).random()
        val filename = "Internal_MAS_Test_FILE_$random"

        try {
            val file = File(context.filesDir, filename)
            context.openFileOutput(filename, mode).use { fos ->
                fos.write(content.toByteArray(StandardCharsets.UTF_8))
                r.addStatus("OK", "File successfully written: ${file.absolutePath}")
            }
        } catch (e: Exception) {
            r.addStatus("FAIL", e.toString())
        }
    }

    private fun writeExternalFileOutputMODE(mode: Int, r: ReturnStatus, content: String) {
        val random = (0..1000).random()
        val filename = "External_MAS_Test_FILE_$random"

        try {
            val externalStorageDir = context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)
            val file = File(externalStorageDir, filename)

            context.openFileOutput(filename, mode).use { fos ->
                fos.write(content.toByteArray(StandardCharsets.UTF_8))
                r.addStatus("OK", "File successfully written: ${file.absoluteFile}")
            }
        } catch (e: Exception) {
            r.addStatus("FAIL", e.toString())
        }
    }


    //@method
}