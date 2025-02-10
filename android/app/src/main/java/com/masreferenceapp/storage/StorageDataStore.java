package com.masreferenceapp.storage;

import androidx.annotation.NonNull;
import androidx.datastore.preferences.core.MutablePreferences;
import androidx.datastore.preferences.core.Preferences;
import androidx.datastore.preferences.core.PreferencesKeys;
import androidx.datastore.preferences.rxjava3.RxPreferenceDataStoreBuilder;
import androidx.datastore.rxjava3.RxDataStore;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.masreferenceapp.MasSettings;
import com.masreferenceapp.ReturnStatus;
import com.masreferenceapp.SensitiveData;

import java.util.HashSet;
import java.util.Set;

import io.reactivex.rxjava3.core.Single;


public class StorageDataStore extends ReactContextBaseJavaModule {

    ReactApplicationContext context;
    RxDataStore<Preferences> prefDataStore;


    public StorageDataStore(ReactApplicationContext context) {
        super(context);
        this.context = context;
        this.prefDataStore = new RxPreferenceDataStoreBuilder(context, /*name=*/ "masDataStoreSettings").build();
    }

    @NonNull
    @Override
    public String getName() {
        return "StorageDataStore";
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String initPreferenceDataStore() {

        return new ReturnStatus("OK", "DataStore initialized.").toJsonString();
    }


    @ReactMethod(isBlockingSynchronousMethod = true)
    public String writeStringPreferenceDataStore() {

        Preferences.Key<String> EXAMPLE_STRING = PreferencesKeys.stringKey("sensitive_data_STR");

        Single<Preferences> updateResult = prefDataStore.updateDataAsync(prefsIn -> {
            MutablePreferences mutablePreferences = prefsIn.toMutablePreferences();
            mutablePreferences.set(EXAMPLE_STRING, SensitiveData.INSTANCE.getData());
            return Single.just(mutablePreferences);

        });
        return new ReturnStatus("OK", "Sensitive String written to DataStore: " + SensitiveData.INSTANCE.getData()).toJsonString();
    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String writeCanaryTokenStringPreferenceDataStore() {

        Preferences.Key<String> EXAMPLE_STRING = PreferencesKeys.stringKey("sensitive_data_STR");

        Single<Preferences> updateResult = prefDataStore.updateDataAsync(prefsIn -> {
            MutablePreferences mutablePreferences = prefsIn.toMutablePreferences();
            mutablePreferences.set(EXAMPLE_STRING, MasSettings.getCanaryToken());
            return Single.just(mutablePreferences);

        });
        return new ReturnStatus("OK", "Canary Token written to DataStore: " + MasSettings.getCanaryToken()).toJsonString();
    }


    @ReactMethod(isBlockingSynchronousMethod = true)
    public String writeStringSetPreferenceDataStore() {

        Preferences.Key<Set<String>> EXAMPLE_STRING_SET = PreferencesKeys.stringSetKey("sensitive_data_STRSET");


        Set<String> stringSet = new HashSet<String>();
        stringSet.add(SensitiveData.INSTANCE.getData());

        Single<Preferences> updateResult = prefDataStore.updateDataAsync(prefsIn -> {
            MutablePreferences mutablePreferences = prefsIn.toMutablePreferences();
            mutablePreferences.set(EXAMPLE_STRING_SET, stringSet);
            return Single.just(mutablePreferences);
        });

        return new ReturnStatus("OK", "Sensitive StringSet written to Datastore:" + SensitiveData.INSTANCE.getData()).toJsonString();

    }


    @ReactMethod(isBlockingSynchronousMethod = true)
    public String writeCanaryTokenStringSetPreferenceDataStore() {

        Preferences.Key<Set<String>> EXAMPLE_STRING_SET = PreferencesKeys.stringSetKey("sensitive_data_STRSET");


        Set<String> stringSet = new HashSet<String>();
        stringSet.add(MasSettings.getCanaryToken());

        Single<Preferences> updateResult = prefDataStore.updateDataAsync(prefsIn -> {
            MutablePreferences mutablePreferences = prefsIn.toMutablePreferences();
            mutablePreferences.set(EXAMPLE_STRING_SET, stringSet);
            return Single.just(mutablePreferences);
        });

        return new ReturnStatus("OK", "Canary Token written to Datastore:" + MasSettings.getCanaryToken()).toJsonString();

    }


    @ReactMethod(isBlockingSynchronousMethod = true)
    public String readStringPreferenceDataStore() {

        this.writeStringPreferenceDataStore();

        Preferences.Key<String> EXAMPLE_STRING = PreferencesKeys.stringKey("example_string");

        Single<String> value = prefDataStore.data().firstOrError().map(prefs -> prefs.get(EXAMPLE_STRING)).onErrorReturnItem("null");

        String storedString = value.blockingGet();

        return new ReturnStatus("OK", "String read: " + storedString).toJsonString();

    }

    @ReactMethod(isBlockingSynchronousMethod = true)
    public String readStringSetPreferenceDataStore() {

        this.writeStringSetPreferenceDataStore();

        Preferences.Key<Set<String>> EXAMPLE_STRING_SET = PreferencesKeys.stringSetKey("example_string_set");

        Single<Set<String>> value = prefDataStore.data().firstOrError().map(prefs -> prefs.get(EXAMPLE_STRING_SET)).onErrorReturnItem(new HashSet<>());

        Set<String> storedStringSet = value.blockingGet();

        return new ReturnStatus("OK", "StringSet read: " + storedStringSet.toString()).toJsonString();

    }

    //@method

}