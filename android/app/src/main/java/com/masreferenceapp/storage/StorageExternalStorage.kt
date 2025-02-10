package com.masreferenceapp.storage

import android.os.Environment
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.masreferenceapp.ReturnStatus


class StorageExternalStorage(private val context: ReactApplicationContext) :
    ReactContextBaseJavaModule(context) {
    override fun getName(): String {
        return "StorageExternalStorage"
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    fun checkState(): String {
        return ReturnStatus("OK", "State: " + Environment.getExternalStorageState()).toJsonString()
    }


//    @ReactMethod(isBlockingSynchronousMethod = true)
//    fun writeFileOutput(): String {
//        val fileContents = "Some secret Message in the external sandbox!"
//        val r = ReturnStatus()
//
//        try {
//            val file = "masExternalTestfile"
//            val appSpecificExternalFile = File(context.getExternalFilesDir(null), file)
//            FileOutputStream(appSpecificExternalFile).use { fos ->
//                fos.write(fileContents.toByteArray(StandardCharsets.UTF_8))
//            }
//            r.addStatus("OK", "File successfully written.")
//        } catch (e: Exception) {
//            r.addStatus("FAIL", e.toString())
//
//        }
//        return r.toJsonString()
//    }

//    @ReactMethod(isBlockingSynchronousMethod = true)
//    fun readTextFile(): String {
//        val r = ReturnStatus()
//
//
//
//        // Ensure the file exists by writing it first
//        writeFileOutput()
//
//        try {
//            val file = "masExternalTestfile"
//            val appSpecificExternalFile = File(context.getExternalFilesDir(null), file)
//            FileInputStream(appSpecificExternalFile).use { fis ->
//                InputStreamReader(fis, StandardCharsets.UTF_8).use { inputStreamReader ->
//                    BufferedReader(inputStreamReader).use { reader ->
//                        val text = StringBuilder()
//                        text.append(reader.readText())
//                        r.addStatus("OK", "File successfully read. Content: $text")
//
//                    }
//                }
//            }
//        } catch (e: Exception) {
//            r.addStatus("FAIL", e.toString())
//
//        }
//
//        return r.toJsonString()
//    }


    @ReactMethod(isBlockingSynchronousMethod = true)
    fun getExternalFilesDirRoot(): String {
        val externalFile = context.getExternalFilesDir(null)
        val state = externalFile?.toString() ?: ""
        return ReturnStatus("OK", "External files dir is : $state").toJsonString()
    }


    @ReactMethod(isBlockingSynchronousMethod = true)
    fun getExternalCacheDir(): String {
        val externalFile = context.externalCacheDir
        val message = externalFile?.toString() ?: ""
        return ReturnStatus("OK", "External files dir is : $message").toJsonString()
    }


    @ReactMethod(isBlockingSynchronousMethod = true)
    fun getDifferentExternalDirs(): String {
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
        val r = ReturnStatus()
        for (type in types) {
            val externalFile = context.getExternalFilesDir(type)
            if (externalFile == null) {
                status.append("[FAIL]")
                r.addStatus("FAIL", "No external Dir.")
            } else {
                status.append("[OK]")
                r.addStatus("OK", "External dir is: $externalFile")

            }
        }
        return r.toJsonString()
    }

    //@method


}
