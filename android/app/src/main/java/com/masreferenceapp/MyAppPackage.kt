package com.masreferenceapp

import android.view.View
import com.facebook.react.ReactPackage
import com.facebook.react.bridge.NativeModule
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.uimanager.ReactShadowNode
import com.facebook.react.uimanager.ViewManager
import com.masreferenceapp.auth.AuthBiometricManager
import com.masreferenceapp.auth.AuthBiometricPrompt
import com.masreferenceapp.auth.AuthFingerprintManager
import com.masreferenceapp.auth.AuthKeyAccess
import com.masreferenceapp.auth.AuthKeyguardManager
import com.masreferenceapp.crypto.CryptoCipher
import com.masreferenceapp.crypto.CryptoEncryptedFile
import com.masreferenceapp.crypto.CryptoKeyAttestation
import com.masreferenceapp.crypto.CryptoKeyChain
import com.masreferenceapp.crypto.CryptoKeyStore
import com.masreferenceapp.crypto.CryptoRandom
import com.masreferenceapp.platform.PlatformWebView
import com.masreferenceapp.resilience.ResilienceFileIntegrityManager
import com.masreferenceapp.storage.StorageInternalStorage
import com.masreferenceapp.storage.StorageDataStore
import com.masreferenceapp.storage.StorageDataStoreProto
import com.masreferenceapp.crypto.CryptoEncryptedSharedPreferences
import com.masreferenceapp.crypto.CryptoMasterKey
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
        modules.add(StorageLog(reactContext))
        modules.add(StorageDataStore(reactContext))
        modules.add(StorageDataStoreProto(reactContext))
        modules.add(StorageInternalStorage(reactContext))
        modules.add(StorageExternalStorage(reactContext))
        modules.add(StorageRoomDatabase(reactContext))

        modules.add(AuthBiometricManager(reactContext))
        modules.add(AuthBiometricPrompt(reactContext))
        modules.add(AuthKeyAccess(reactContext))
        modules.add(AuthFingerprintManager(reactContext))
        modules.add(AuthKeyguardManager(reactContext))

        modules.add(CryptoKeyStore(reactContext))
        modules.add(CryptoKeyAttestation(reactContext))
        modules.add(CryptoCipher(reactContext))
        modules.add(CryptoRandom(reactContext))
        modules.add(CryptoKeyChain(reactContext))
        modules.add(CryptoEncryptedSharedPreferences(reactContext))
        modules.add(CryptoEncryptedFile(reactContext))
        modules.add(CryptoMasterKey(reactContext))

        modules.add(PlatformWebView(reactContext))

        modules.add(ResilienceFileIntegrityManager(reactContext))

        return modules
    }

}