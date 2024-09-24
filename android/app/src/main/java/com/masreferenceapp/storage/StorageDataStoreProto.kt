package com.masreferenceapp.storage
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.masreferenceapp.ReturnStatus
import com.masreferenceapp.storage.proto.User
import com.masreferenceapp.storage.proto.UserSerializer
import com.msareferenceapp.UserPreference
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch


class StorageDataStoreProto(context: ReactApplicationContext) : ReactContextBaseJavaModule(context){
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
        val user = User(firstName = "Hans", lastName = "Meier", password = "Passw0rd!")

        myPluginScope.launch {
            saveUserToProtoStore(user)
        }
        return ReturnStatus("OK", "Stored User Object in Proto DataStore.").toJsonString()
    }


    @ReactMethod(isBlockingSynchronousMethod = true)
    fun readProtoDataStore(): String {
        val user = User(firstName = "Barbara", lastName = "Meier", password = "Passw0rd!")

        myPluginScope.launch {
            saveUserToProtoStore(user)
            reactApplicationContext.userProtoDataStore.data.map { user ->
                User(
                    firstName = user.firstName,
                    lastName = user.lastName,
                    password = user.password
                )
            }
        }
        return ReturnStatus("OK", "Read User Object in Proto DataStore.").toJsonString()
    }

}




