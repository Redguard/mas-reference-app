package org.masreferenceapp

import android.view.View
import com.facebook.react.ReactPackage
import com.facebook.react.bridge.NativeModule
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.uimanager.ReactShadowNode
import com.facebook.react.uimanager.ViewManager
import org.masreferenceapp.crypto.CryptoKeyAttestation
import org.masreferenceapp.network.NetworkLocalNetwork
import org.masreferenceapp.network.NetworkTlsClientConfig
import org.masreferenceapp.network.NetworkTlsPinning
import org.masreferenceapp.network.NetworkUnencrypted
import org.masreferenceapp.platform.PlatformIpc
import org.masreferenceapp.platform.PlatformWebView
import org.masreferenceapp.storage.StorageDataStoreProto
import org.masreferenceapp.storage.StorageExternalStorage
import org.masreferenceapp.storage.StorageHardcodedApiKey
import org.masreferenceapp.storage.StorageLog
import org.masreferenceapp.storage.StorageRoomDatabase
import org.masreferenceapp.storage.StorageSQLite
import org.masreferenceapp.storage.StorageSharedPreferences

class MyAppPackage : ReactPackage {

    override fun createViewManagers(
        reactContext: ReactApplicationContext
    ): MutableList<ViewManager<View, ReactShadowNode<*>>> = mutableListOf()

    override fun createNativeModules(
        reactContext: ReactApplicationContext
    ): List<NativeModule> {
        val modules = mutableListOf<NativeModule>()

        modules.add(MasSettingsSync(reactContext))

        modules.add(StorageSharedPreferences(reactContext))
        modules.add(StorageLog(reactContext))
        modules.add(org.masreferenceapp.storage.StorageDataStore(reactContext))
        modules.add(StorageDataStoreProto(reactContext))
        modules.add(org.masreferenceapp.storage.StorageInternalStorage(reactContext))
        modules.add(StorageExternalStorage(reactContext))
        modules.add(StorageRoomDatabase(reactContext))
        modules.add(StorageSQLite(reactContext))
        modules.add(StorageHardcodedApiKey(reactContext))

        modules.add(org.masreferenceapp.auth.AuthBiometricManager(reactContext))
        modules.add(org.masreferenceapp.auth.AuthBiometricPrompt(reactContext))
        modules.add(org.masreferenceapp.auth.AuthFingerprintManager(reactContext))
        modules.add(org.masreferenceapp.auth.AuthKeyguardManager(reactContext))
        modules.add(org.masreferenceapp.auth.AuthProtectedConfirmation(reactContext))

        modules.add(CryptoKeyAttestation(reactContext))
        modules.add(org.masreferenceapp.crypto.CryptoJava(reactContext))
        modules.add(org.masreferenceapp.crypto.CryptoRandomJava(reactContext))
        modules.add(org.masreferenceapp.crypto.CryptoEncryptedSharedPreferences(reactContext))
        modules.add(org.masreferenceapp.crypto.CryptoEncryptedFile(reactContext))
        modules.add(org.masreferenceapp.crypto.CryptoMasterKey(reactContext))

        modules.add(NetworkUnencrypted(reactContext))
        modules.add(NetworkTlsClientConfig(reactContext))
        modules.add(NetworkTlsPinning(reactContext))
        modules.add(NetworkLocalNetwork(reactContext))

        modules.add(PlatformIpc(reactContext))
        modules.add(PlatformWebView(reactContext))

        modules.add(org.masreferenceapp.resilience.ResilienceFileIntegrityManager(reactContext))
        modules.add(org.masreferenceapp.resilience.ResilienceVerifySignature(reactContext))
        modules.add(org.masreferenceapp.resilience.ResilienceAntiDebug(reactContext))
        modules.add(org.masreferenceapp.resilience.ResilienceAntiVm(reactContext))

        modules.add(org.masreferenceapp.privacy.PrivacyAccessData(reactContext))
        modules.add(org.masreferenceapp.crypto.CryptoKeyChain(reactContext))
        modules.add(org.masreferenceapp.platform.PlatformUiDisclosure(reactContext))
        modules.add(org.masreferenceapp.storage.StorageJavaFileIo(reactContext))
        modules.add(org.masreferenceapp.storage.StorageMediaStoreAPI(reactContext))
        modules.add(org.masreferenceapp.crypto.CryptoKeyGenParameterSpec(reactContext))
        modules.add(org.masreferenceapp.crypto.CryptoSecretKeyFactory(reactContext))
        modules.add(org.masreferenceapp.crypto.CryptoHardcodedSecret(reactContext))
        modules.add(org.masreferenceapp.crypto.CryptoDeprecated(reactContext))
        modules.add(org.masreferenceapp.auth.AuthHttpBasicAuth(reactContext))

        modules.add(org.masreferenceapp.code.CodeUpdate(reactContext))
        modules.add(org.masreferenceapp.code.CodeDependencies(reactContext))
        modules.add(org.masreferenceapp.code.CodeInsecureSoftware(reactContext))
        modules.add(org.masreferenceapp.resilience.ResilienceObfuscation(reactContext))
        modules.add(org.masreferenceapp.resilience.ResilienceRootDetection(reactContext))
        modules.add(org.masreferenceapp.privacy.PrivacyMarketingUUID(reactContext))
        modules.add(org.masreferenceapp.crypto.CryptoKeyInfo(reactContext))
        modules.add(org.masreferenceapp.resilience.ResilienceDeviceIntegrity(reactContext))
        modules.add(org.masreferenceapp.crypto.CryptoRandomKotlin(reactContext))
        //@modules

        return modules
    }

}