package com.masreferenceapp;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.datastore.core.DataStore;
import androidx.datastore.core.DataStoreFactory;
import androidx.datastore.preferences.core.MutablePreferences;
import androidx.datastore.preferences.core.Preferences;
import androidx.datastore.preferences.core.PreferencesKeys;
import androidx.datastore.preferences.rxjava3.RxPreferenceDataStoreBuilder;
import androidx.datastore.rxjava3.RxDataStore;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import java.util.HashSet;
import java.util.Set;

import io.reactivex.rxjava3.core.Single;


public class StorageDataStore extends ReactContextBaseJavaModule {
    ReactApplicationContext context;
    RxDataStore<Preferences> dataStore;

    StorageDataStore(ReactApplicationContext context) {
        super(context);
        this.context = context;
        this.dataStore =  new RxPreferenceDataStoreBuilder(context, /*name=*/ "masDataStoreSettings").build();

    }

    @NonNull
    @Override
    public String getName() {
        return "StorageDataStore";
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String initPreferenceDataStore(){

        return Status.status("OK", dataStore.toString());
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String writeStringPreferenceDataStore(){

        Preferences.Key<String> EXAMPLE_STRING = PreferencesKeys.stringKey("example_string");

        Single<Preferences> updateResult =  dataStore.updateDataAsync(prefsIn -> {
            MutablePreferences mutablePreferences = prefsIn.toMutablePreferences();
            mutablePreferences.set(EXAMPLE_STRING, "Password: Passw0rd!");
            return Single.just(mutablePreferences);
        });

        return Status.status("OK", dataStore.toString());

    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String writeStringSetPreferenceDataStore(){

        Preferences.Key<Set<String>> EXAMPLE_STRING_SET = PreferencesKeys.stringSetKey ("example_string_set");


        Set<String> stringSet = new HashSet<String>();
        stringSet.add("Password123!");
        stringSet.add("HelloWorld!");

        Single<Preferences> updateResult =  dataStore.updateDataAsync(prefsIn -> {
            MutablePreferences mutablePreferences = prefsIn.toMutablePreferences();
            mutablePreferences.set(EXAMPLE_STRING_SET, stringSet);
            return Single.just(mutablePreferences);
        });

        return Status.status("OK", dataStore.toString());
    }


    @ReactMethod(isBlockingSynchronousMethod = true)
    public String readStringPreferenceDataStore(){

        this.writeStringPreferenceDataStore();

        Preferences.Key<String> EXAMPLE_STRING = PreferencesKeys.stringKey("example_string");

        Single<String> value = dataStore.data().firstOrError().map(prefs -> prefs.get(EXAMPLE_STRING)).onErrorReturnItem("null");

        String storedString = value.blockingGet();

        return Status.status("OK", storedString);
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String readStringSetPreferenceDataStore(){

        this.writeStringSetPreferenceDataStore();

        Preferences.Key<Set<String>> EXAMPLE_STRING_SET = PreferencesKeys.stringSetKey("example_string_set");

        Single<Set<String>> value = dataStore.data().firstOrError().map(prefs -> prefs.get(EXAMPLE_STRING_SET)).onErrorReturnItem(new HashSet<>());

        Set<String> storedStringSet = value.blockingGet();

        return Status.status("OK", storedStringSet.toString());
    }

}