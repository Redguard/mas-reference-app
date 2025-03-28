package org.masreferenceapp

import android.app.Application
import android.content.Context
import android.content.IntentFilter
import com.facebook.react.PackageList
import com.facebook.react.ReactApplication
import com.facebook.react.ReactHost
import com.facebook.react.ReactNativeHost
import com.facebook.react.ReactPackage
import com.facebook.react.defaults.DefaultNewArchitectureEntryPoint.load
import com.facebook.react.defaults.DefaultReactHost.getDefaultReactHost
import com.facebook.react.defaults.DefaultReactNativeHost
import com.facebook.react.soloader.OpenSourceMergedSoMapping
import com.facebook.soloader.SoLoader
import org.insomnihack.CTFPackage
import org.insomnihack.MainCTF
import org.masreferenceapp.platform.helpers.IpcExportedBroadcastReceiver

class MainApplication : Application(), ReactApplication {

    override val reactNativeHost: ReactNativeHost =
        object : DefaultReactNativeHost(this) {
            override fun getPackages(): List<ReactPackage> =
                PackageList(this).packages.apply {
                    // Packages that cannot be autolinked yet can be added manually here, for example:
                    add(MyAppPackage())
                    add(CTFPackage())
                }

            override fun getJSMainModuleName(): String = "index"

            override fun getUseDeveloperSupport(): Boolean = BuildConfig.DEBUG

            override val isNewArchEnabled: Boolean = BuildConfig.IS_NEW_ARCHITECTURE_ENABLED
            override val isHermesEnabled: Boolean = BuildConfig.IS_HERMES_ENABLED
        }

    override val reactHost: ReactHost
        get() = getDefaultReactHost(this.applicationContext, reactNativeHost)

    override fun onCreate() {
        super.onCreate()

        // register broadcast

        // Initialize the BroadcastReceiver
        val ipcReceiver = IpcExportedBroadcastReceiver()

        // Define the intent filter for the broadcast
        val intentFilter = IntentFilter().apply {
            addAction("org.masreferenceapp.DO_SOMETHING")
        }

        // Register the receiver dynamically with the appropriate flag
        registerReceiver(ipcReceiver, intentFilter, Context.RECEIVER_EXPORTED)


        SoLoader.init(this, OpenSourceMergedSoMapping)
        if (BuildConfig.IS_NEW_ARCHITECTURE_ENABLED) {
            // If you opted-in for the New Architecture, we load the native entry point for this app.
            load()
        }
        // Initialise the CTF
        MainCTF()
    }
}
