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
import com.masreferenceapp.auth.AuthKeyguardManager
import com.masreferenceapp.auth.AuthProtectedConfirmation
import com.masreferenceapp.crypto.CryptoCipher
import com.masreferenceapp.crypto.CryptoEncryptedFile
import com.masreferenceapp.crypto.CryptoKeyAttestation
import com.masreferenceapp.crypto.CryptoRandom
import com.masreferenceapp.platform.PlatformWebView
import com.masreferenceapp.resilience.ResilienceFileIntegrityManager
import com.masreferenceapp.storage.StorageInternalStorage
import com.masreferenceapp.storage.StorageDataStore
import com.masreferenceapp.storage.StorageDataStoreProto
import com.masreferenceapp.crypto.CryptoEncryptedSharedPreferences
import com.masreferenceapp.crypto.CryptoMasterKey
import com.masreferenceapp.network.NetworkLocalNetwork
import com.masreferenceapp.network.NetworkTlsConfig
import com.masreferenceapp.network.NetworkTlsPinning
import com.masreferenceapp.network.NetworkUnencrypted
import com.masreferenceapp.platform.PlatformIpc
import com.masreferenceapp.resilience.ResilienceAntiDebug
import com.masreferenceapp.resilience.ResilienceAntiVm
import com.masreferenceapp.resilience.ResilienceVerifySignature
import com.masreferenceapp.storage.StorageExternalStorage
import com.masreferenceapp.storage.StorageLog
import com.masreferenceapp.storage.StorageRoomDatabase
import com.masreferenceapp.storage.StorageSQLite
import com.masreferenceapp.storage.StorageSharedPreferences
import com.masreferenceapp.storage.StorageHardcodedApiKey

class MyAppPackage : ReactPackage {

    override fun createViewManagers(
        reactContext: ReactApplicationContext
    ): MutableList<ViewManager<View, ReactShadowNode<*>>> = mutableListOf()

    override fun createNativeModules(
        reactContext: ReactApplicationContext
    ): List<NativeModule> {
        val modules = mutableListOf<NativeModule>()

        modules.add(MasSettingsSync(reactContext));

        modules.add(StorageSharedPreferences(reactContext))
        modules.add(StorageLog(reactContext))
        modules.add(StorageDataStore(reactContext))
        modules.add(StorageDataStoreProto(reactContext))
        modules.add(StorageInternalStorage(reactContext))
        modules.add(StorageExternalStorage(reactContext))
        modules.add(StorageRoomDatabase(reactContext))
        modules.add(StorageSQLite(reactContext))
        modules.add(StorageHardcodedApiKey(reactContext))

        modules.add(AuthBiometricManager(reactContext))
        modules.add(AuthBiometricPrompt(reactContext))
        modules.add(AuthFingerprintManager(reactContext))
        modules.add(AuthKeyguardManager(reactContext))
        modules.add(AuthProtectedConfirmation(reactContext))

        modules.add(CryptoKeyAttestation(reactContext))
        modules.add(CryptoCipher(reactContext))
        modules.add(CryptoRandom(reactContext))
        modules.add(CryptoEncryptedSharedPreferences(reactContext))
        modules.add(CryptoEncryptedFile(reactContext))
        modules.add(CryptoMasterKey(reactContext))

        modules.add(NetworkUnencrypted(reactContext))
        modules.add(NetworkTlsConfig(reactContext))
        modules.add(NetworkTlsPinning(reactContext))
        modules.add(NetworkLocalNetwork(reactContext))

        modules.add(PlatformIpc(reactContext))
        modules.add(PlatformWebView(reactContext))

        modules.add(ResilienceFileIntegrityManager(reactContext))
        modules.add(ResilienceVerifySignature(reactContext))
        modules.add(ResilienceAntiDebug(reactContext))
        modules.add(ResilienceAntiVm(reactContext))

        modules.add(com.masreferenceapp.privacy.PrivacyAccessData(reactContext))
        modules.add(com.masreferenceapp.crypto.CryptoKeyChain(reactContext))
        modules.add(com.masreferenceapp.platform.PlatformUiDisclosure(reactContext))
        modules.add(com.masreferenceapp.storage.StorageJavaFileIo(reactContext))
        modules.add(com.masreferenceapp.storage.StorageMediaStoreAPI(reactContext))
        modules.add(com.masreferenceapp.crypto.CryptoKeyGenParameterSpec(reactContext))
        modules.add(com.masreferenceapp.crypto.CryptoSecretKeyFactory(reactContext))
        modules.add(com.masreferenceapp.crypto.CryptoHardcodedSecret(reactContext))
        modules.add(com.masreferenceapp.crypto.CryptoDeprecated(reactContext))
        modules.add(com.masreferenceapp.auth.AuthHttpBasicAuth(reactContext))

        modules.add(com.masreferenceapp.code.CodeUpdate(reactContext))
        modules.add(com.masreferenceapp.code.CodeDependencies(reactContext))
        modules.add(com.masreferenceapp.code.CodeInsecureSoftware(reactContext))
        modules.add(com.masreferenceapp.resilience.ResiliencObfuscation(reactContext))
        modules.add(com.masreferenceapp.resilience.ResilienceDynamicAnalysisDetechion(reactContext))
        modules.add(com.masreferenceapp.resilience.ResilienceRootDetection(reactContext))
        modules.add(com.masreferenceapp.privacy.PrivacyAccessGeolocation(reactContext))
        modules.add(com.masreferenceapp.privacy.PrivacySMS(reactContext))
        modules.add(com.masreferenceapp.privacy.PrivacyMarketingUUID(reactContext))
        modules.add(com.masreferenceapp.crypto.CryptoKeyInfo(reactContext))
        //@modules

        return modules
    }

}