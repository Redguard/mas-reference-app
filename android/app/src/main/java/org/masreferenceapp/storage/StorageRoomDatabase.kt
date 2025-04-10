package org.masreferenceapp.storage

import androidx.room.Room.databaseBuilder
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import org.masreferenceapp.ReturnStatus
import org.masreferenceapp.SensitiveData
import org.masreferenceapp.storage.room.AppDatabase
import org.masreferenceapp.storage.room.User

class StorageRoomDatabase(var context: ReactApplicationContext) : ReactContextBaseJavaModule(
    context
) {
    override fun getName(): String {
        return "StorageRoomDatabase"
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    fun initRoomDb(): String {
        val db = databaseBuilder(
            context,
            org.masreferenceapp.storage.room.AppDatabase::class.java, "Mas_RoomDb_" + (0..1000000).random()
        ).build()
        db.close()
        return ReturnStatus("OK", "Room Database created.").toJsonString()
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    fun writeToRoomDb(): String {
        val db = databaseBuilder(
            context,
            org.masreferenceapp.storage.room.AppDatabase::class.java, "Mas_RoomDb_" + (0..1000000).random()
        ).build()

        val userDao = db.userDao()

        val user = org.masreferenceapp.storage.room.User()
        user.uid = 1
        user.firstName = "Maximilian"
        user.lastName = "Smith"
        user.password = SensitiveData.data
        userDao.insert(user)
        db.close()
        return ReturnStatus("OK", "Sensitive data saved to Room database.").toJsonString()
    }

}