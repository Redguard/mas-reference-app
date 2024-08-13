package com.masreferenceapp.storage

import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferences.Key
import androidx.datastore.preferences.rxjava3.RxPreferenceDataStoreBuilder
import androidx.datastore.rxjava3.RxDataStore
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReactContextBaseJavaModule
import com.facebook.react.bridge.ReactMethod
import com.masreferenceapp.Status
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.functions.Function

class StorageDataStore(var context: ReactApplicationContext) : ReactContextBaseJavaModule(context) {
    var prefDataStore: RxDataStore<Preferences>

    init {
        prefDataStore =
            RxPreferenceDataStoreBuilder(context,  /*name=*/"masDataStoreSettings").build()
    }

    override fun getName(): String {
        return "StorageDataStore"
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    fun initPreferenceDataStore(): String {
        return Status.status("OK", prefDataStore.toString())
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    fun writeStringPreferenceDataStore(): String {
        val EXAMPLE_STRING: Key<String> = stringPreferencesKey("example_string")
        val updateResult = prefDataStore.updateDataAsync { prefsIn: Preferences ->
            val mutablePreferences = prefsIn.toMutablePreferences()
            mutablePreferences.set(EXAMPLE_STRING, "Password: Passw0rd!")
            Single.just<Preferences>(mutablePreferences)
        }
        return Status.status("OK", prefDataStore.toString())
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    fun writeStringSetPreferenceDataStore(): String {
        val EXAMPLE_STRING_SET: Key<Set<String>> = stringSetPreferencesKey("example_string_set")
        val stringSet: MutableSet<String> = HashSet()
        stringSet.add("Password123!")
        stringSet.add("HelloWorld!")
        val updateResult = prefDataStore.updateDataAsync { prefsIn: Preferences ->
            val mutablePreferences = prefsIn.toMutablePreferences()
            mutablePreferences.set<Set<String>>(EXAMPLE_STRING_SET, stringSet)
            Single.just<Preferences>(mutablePreferences)
        }
        return Status.status("OK", prefDataStore.toString())
    }

    //    @ReactMethod(isBlockingSynchronousMethod = true)
    //    public String writeStringSetPreferenceDataStore(){
    //
    //
    //    }
    @ReactMethod(isBlockingSynchronousMethod = true)
    fun readStringPreferenceDataStore(): String {
        writeStringPreferenceDataStore()
        val EXAMPLE_STRING: Key<String> = stringPreferencesKey("example_string")
        val value = prefDataStore.data().firstOrError().map(
            Function<Preferences, String?> { prefs: Preferences -> prefs.get(EXAMPLE_STRING) })
            .onErrorReturnItem("null")
        val storedString = value.blockingGet()
        return Status.status("OK", storedString)
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    fun readStringSetPreferenceDataStore(): String {
        writeStringSetPreferenceDataStore()
        val EXAMPLE_STRING_SET: Key<Set<String>> = stringSetPreferencesKey("example_string_set")
        val value = prefDataStore.data().firstOrError()
            .map { prefs: Preferences -> prefs.get<Set<String>>(EXAMPLE_STRING_SET)!! }
            .onErrorReturnItem(HashSet())
        val storedStringSet = value.blockingGet()
        return Status.status("OK", storedStringSet.toString())
    }
}