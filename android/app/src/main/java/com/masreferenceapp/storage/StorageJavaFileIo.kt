package com.masreferenceapp.storage

import android.content.Context
import android.os.Environment
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.masreferenceapp.ReturnStatus
import com.masreferenceapp.SensitiveData
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.nio.charset.StandardCharsets


class StorageJavaFileIo(var context: ReactApplicationContext) : ReactContextBaseJavaModule(
    context
) {
    override fun getName(): String {
        return "StorageJavaFileIo"
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    fun writeSensitiveFileSandbox(): String {
        val r = ReturnStatus()
        writeSandboxFile(Context.MODE_PRIVATE, r, SensitiveData.data)
        return r.toJsonString()
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    fun writeSandboxWorldReadable(): String {
        val r = ReturnStatus()
        writeSandboxFile(Context.MODE_WORLD_READABLE, r, SensitiveData.data)
        return r.toJsonString()
    }


    @ReactMethod(isBlockingSynchronousMethod = true)
    fun writeSandboxWorldWritable(): String {
        val r = ReturnStatus()
        writeSandboxFile(Context.MODE_WORLD_WRITEABLE, r, SensitiveData.data)
        return r.toJsonString()
    }


    @ReactMethod(isBlockingSynchronousMethod = true)
    fun writeExternalAppContext(): String {
        val r = ReturnStatus()
        writeExternalFile(
            r,
            context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS),
            SensitiveData.data
        )
        return r.toJsonString()
    }


    private fun writeSandboxFile(mode: Int, r: ReturnStatus, content: String) {
        val random = (0..1000000).random()
        val filename = "Internal_MAS_Test_FILE_$random"

        try {
            val sandboxRootPath: String = context.filesDir.absolutePath

            context.openFileOutput(filename, mode).use { fos ->
                fos.write(content.toByteArray(StandardCharsets.UTF_8))
                r.addStatus(
                    "OK",
                    "File successfully written: ${File(sandboxRootPath, filename).absoluteFile}"
                )
            }
        } catch (e: Exception) {
            r.addStatus("FAIL", e.toString())
        }
    }

    private fun writeExternalFile(r: ReturnStatus, f: File?, content: String) {
        val random = (0..1000000).random()
        val filename = "External_MAS_Test_FILE_$random"

        try {
            val externalRootPath: File? = f
            val file = File(externalRootPath, filename)

            try {
                FileOutputStream(file).use { fos ->
                    fos.write(content.toByteArray())
                    fos.flush()
                    r.addStatus("OK", "File successfully written: ${file.absoluteFile}")
                }
            } catch (e: IOException) {
                r.addStatus("FAIL", e.toString())
            }

        } catch (e: Exception) {
            r.addStatus("FAIL", e.toString())
        }
    }


    //@method
}