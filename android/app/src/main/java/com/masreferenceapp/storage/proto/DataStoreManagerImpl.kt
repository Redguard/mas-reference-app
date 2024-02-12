package cstorage.proto

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.masreferenceapp.storage.proto.DataStoreManager
import com.masreferenceapp.storage.proto.User
import com.msareferenceapp.UserPreference
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStoreManagerImpl @Inject constructor(
//    private val userPreferencesDataStore: DataStore<Preferences>,
    private val userProtoDataStore: DataStore<UserPreference>
) : DataStoreManager {

//    private val USER_FIRST_NAME = stringPreferencesKey("user_first_name")
//    private val USER_LAST_NAME = stringPreferencesKey("user_last_name")
//    private val USER_PASSWORD = stringPreferencesKey("user_password")

//    override suspend fun saveUserToPreferencesStore(user: User) {
//        userPreferencesDataStore.edit { preferences ->
//            preferences[USER_FIRST_NAME] = user.firstName
//            preferences[USER_LAST_NAME] = user.lastName
//            preferences[USER_PASSWORD] = user.password
//        }
//    }
//
//    override fun getUserFromPreferencesStore(): Flow<User> = userPreferencesDataStore.data
//        .map { preferences ->
//            User(
//                firstName = preferences[USER_FIRST_NAME] ?: "",
//                lastName = preferences[USER_LAST_NAME] ?: "",
//                password = preferences[USER_PASSWORD] ?: "",
//            )
//        }

    override suspend fun saveUserToProtoStore(user: User) {
        userProtoDataStore.updateData { currentUserData ->
            currentUserData.toBuilder()
                .setFirstName(user.firstName)
                .setLastName(user.lastName)
                .setPassword(user.password)
                .build()
        }
    }

    override fun getUserFromProtoStore(): Flow<User> =
        userProtoDataStore.data.map { user ->
            User(
                firstName = user.firstName,
                lastName = user.lastName,
                password = user.password
            )
        }
}
