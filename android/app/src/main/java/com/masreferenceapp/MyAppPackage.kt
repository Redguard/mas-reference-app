package com.masreferenceapp

import android.view.View
import com.facebook.react.ReactPackage
import com.facebook.react.bridge.NativeModule
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.uimanager.ReactShadowNode
import com.facebook.react.uimanager.ViewManager
import com.masreferenceapp.crypto.CryptoCipher
import com.masreferenceapp.crypto.CryptoKeyAttestation
import com.masreferenceapp.crypto.CryptoKeyStore
import com.masreferenceapp.platform.PlatformWebView
import com.masreferenceapp.resilience.ResilienceFileIntegrityManager
import com.masreferenceapp.storage.StorageInternalStorage
import com.masreferenceapp.storage.StorageDataStore
import com.masreferenceapp.storage.StorageDataStoreProto
import com.masreferenceapp.storage.StorageEncryptedSharedPreferences
import com.masreferenceapp.storage.StorageExternalStorage
import com.masreferenceapp.storage.StorageLog
import com.masreferenceapp.storage.StorageRoomDatabase
import com.masreferenceapp.storage.StorageSharedPreferences


class MyAppPackage : ReactPackage {

    override fun createViewManagers(
        reactContext: ReactApplicationContext
    ): MutableList<ViewManager<View, ReactShadowNode<*>>> = mutableListOf()

    override fun createNativeModules(
        reactContext: ReactApplicationContext
    ): List<NativeModule> {
        val modules = mutableListOf<NativeModule>()

        modules.add(StorageSharedPreferences(reactContext))
        modules.add(StorageEncryptedSharedPreferences(reactContext))
        modules.add(StorageLog(reactContext))
        modules.add(StorageDataStore(reactContext))
        modules.add(StorageDataStoreProto(reactContext))
        modules.add(StorageInternalStorage(reactContext))
        modules.add(StorageExternalStorage(reactContext))
        modules.add(StorageRoomDatabase(reactContext))

        modules.add(CryptoKeyStore(reactContext))
        modules.add(CryptoKeyAttestation(reactContext))
        modules.add(CryptoCipher(reactContext))


        modules.add(PlatformWebView(reactContext))

        modules.add(ResilienceFileIntegrityManager(reactContext))


        return modules
    }

}