package com.mgabor.datastoresampleapp.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import org.masreferenceapp.storage.proto.UserSerializer
import org.masreferenceapp.UserPreference
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataStoreServiceModule {

    private val Context.userPreferencesDataStore: DataStore<Preferences> by preferencesDataStore(
        name = "user"
    )

    private val Context.userProtoDataStore: DataStore<org.masreferenceapp.UserPreference> by dataStore(
        fileName = "user.pb",
        serializer = UserSerializer
    )

    @Singleton
    @Provides
    fun provideUserPreferencesDataStore(
        @ApplicationContext app: Context
    ): DataStore<Preferences> = app.userPreferencesDataStore

    @Singleton
    @Provides
    fun provideUserProtoDataStore(
        @ApplicationContext app: Context
    ): DataStore<org.masreferenceapp.UserPreference> = app.userProtoDataStore
}
