package com.masreferenceapp.storage.proto

import kotlinx.coroutines.flow.Flow

interface DataStoreManager {
    //    suspend fun saveUserToPreferencesStore(user: User)
//    fun getUserFromPreferencesStore(): Flow<User>
    suspend fun saveUserToProtoStore(user: User)
    fun getUserFromProtoStore(): Flow<User>
}
