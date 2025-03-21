package org.masreferenceapp.storage

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import org.masreferenceapp.MasSettings
import org.masreferenceapp.ReturnStatus
import org.masreferenceapp.SensitiveData
import org.masreferenceapp.storage.proto.User
import org.masreferenceapp.storage.proto.UserSerializer
import org.masreferenceapp.UserPreference
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch


class StorageDataStoreProto(context: ReactApplicationContext) :
    ReactContextBaseJavaModule(context) {
    override fun getName() = "StorageDataStoreProto"

    private val Context.userProtoDataStore: DataStore<UserPreference> by dataStore(
        fileName = "user.pb",
        serializer = UserSerializer
    )

    private suspend fun saveUserToProtoStore(user: User) {
        reactApplicationContext.userProtoDataStore.updateData { currentUserData ->
            currentUserData.toBuilder()
                .setFirstName(user.firstName)
                .setLastName(user.lastName)
                .setPassword(user.password)
                .build()
        }
    }

    val myPluginScope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    @ReactMethod(isBlockingSynchronousMethod = true)
    fun writeProtoDataStore(): String {
        val user = User(firstName = "Hans", lastName = "Meier", password = SensitiveData.data)

        myPluginScope.launch {
            saveUserToProtoStore(user)
        }
        return ReturnStatus(
            "OK",
            "Sensitive Data stored in Proto DataStore: " + SensitiveData.data
        ).toJsonString()
    }


//    @ReactMethod(isBlockingSynchronousMethod = true)
//    fun readProtoDataStore(): String {
//        val user = User(firstName = "Barbara", lastName = "Meier", password = "Passw0rd!")
//
//        myPluginScope.launch {
//            saveUserToProtoStore(user)
//            reactApplicationContext.userProtoDataStore.data.map { user ->
//                User(
//                    firstName = user.firstName,
//                    lastName = user.lastName,
//                    password = user.password
//                )
//            }
//        }
//        return ReturnStatus("OK", "Read User Object in Proto DataStore.").toJsonString()
//    }


    @ReactMethod(isBlockingSynchronousMethod = true)
    fun writeCanaryTokenProtoDataStore(): String {
        val user =
            User(firstName = "Canary", lastName = "Token", password = MasSettings.getCanaryToken())

        myPluginScope.launch {
            saveUserToProtoStore(user)
        }
        return ReturnStatus(
            "OK",
            "Sensitive Data stored in Proto DataStore: " + MasSettings.getCanaryToken()
        ).toJsonString()

    }

    //@method

}




