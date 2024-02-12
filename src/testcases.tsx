import {NativeModules, Platform} from 'react-native';
import {TestCases} from './appContent';
const {
  StorageSharedPreferences,
  StorageEncryptedSharedPreferences,
  StorageLog,
  StorageDataStore,
  StorageDataStoreProto,
} = NativeModules;

interface Dictionary<Type> {
  [key: string]: Type;
}

/*
  General Use-Cases
*/
var testCases: Dictionary<TestCases[]> = {
  STORAGE: [
    {
      title: 'Sensitive Data in Log',
      description:
        'This test cases will write the following sensitive data to the application log:\n\n- Different Password-Identifiere\n- Valid Access-Token\n- ...\n',
      testCases: [
        {
          title: 'Write Password into Log',
          description:
            'Write Keywords related to Passwords into the Application LOG.',
          nativeFunction: StorageLog.writeSensitiveData,
        },
        {
          title: 'Write Access Token into Log',
          description: 'Write a valid Access Token into the Application LOG.',
          nativeFunction: StorageLog.writeSensitiveData,
        },
      ],
    },
  ],
  CRYPTO: [],
  AUTH: [],
  NETWORK: [],
  PLATFORM: [],
  CODE: [],
  RESILIENCE: [],
};

// Android Specific TestCases

if (Platform.OS === 'android') {
  /*
    Android Use-Cases
  */
  testCases.STORAGE.push(
    {
      title: 'SharedPreferences',
      description:
        'This testcase stores data using unencrypted SharedPreferences.\n',
      testCases: [
        {
          title: 'Get insecure SharedPreferences Instance',
          description: 'Get an world read/writable SharedPreferenceInstance',
          nativeFunction: StorageSharedPreferences.getInsecureSharedPreferences,
        },
        {
          title: 'Write String to SharedPreferences',
          description:
            'Write string into the snadbox using putString method of SharedPreferences.',
          nativeFunction: StorageSharedPreferences.putString,
        },
        {
          title: 'Write StringSet to SharedPreferences',
          description:
            'Write stringSet into the snadbox using putStringSet method of SharedPreferences.',
          nativeFunction: StorageSharedPreferences.putStringSet,
        },
        {
          title: 'Read String from SharedPreferences',
          description: 'Read a String from the unencrypted SharedPreferences',
          nativeFunction: StorageSharedPreferences.readString,
        },
        {
          title: 'Read StringSet from SharedPreferences',
          description:
            'Read a StringSet from the unencrypted SharedPreferences',
          nativeFunction: StorageSharedPreferences.readStringSet,
        },
      ],
    },
    {
      title: 'EncryptedSharedPreferences',
      description:
        'This testcase stores data using secure, encrypted SharedPreferences.\n',
      testCases: [
        {
          title: 'Create EncryptedSharedPreferences Instance',
          description: 'Create EncryptedSharedPreferences.',
          nativeFunction:
            StorageEncryptedSharedPreferences.createEncryptedSharedPreferences,
        },
        {
          title: 'Write String to EncryptedSharedPreferences',
          description:
            'Write string into the snadbox using putString method of EncryptedSharedPreferences.',
          nativeFunction: StorageEncryptedSharedPreferences.putString,
        },
        {
          title: 'Write StringSet to EncryptedSharedPreferences',
          description:
            'Write stringSet into the snadbox using putStringSet method of EncryptedSharedPreferences.',
          nativeFunction: StorageEncryptedSharedPreferences.putStringSet,
        },
        {
          title: 'Read String from EncryptedSharedPreferences',
          description:
            'Read a String from the unencrypted EncryptedSharedPreferences',
          nativeFunction: StorageEncryptedSharedPreferences.readString,
        },
        {
          title: 'Read StringSet from EncryptedSharedPreferences',
          description:
            'Read a StringSet from the unencrypted EncryptedSharedPreferences',
          nativeFunction: StorageEncryptedSharedPreferences.readStringSet,
        },
      ],
    },
    {
      title: 'DataStore',
      description:
        'This testcase stores data using the DataStore. Data stored this way is not encrypted in the sandbox. An attacker may therefore be able to retrieve the data.',
      testCases: [
        {
          title: 'Init Preferences DataStore (RxDataStoreBuilder)',
          description:
            'Uses RxDataStoreBuilder to create a Preferences DataStore',
          nativeFunction: StorageDataStore.initPreferenceDataStore,
        },
        {
          title: 'Write String to Preferences DataStore',
          description:
            'Write String into the snadbox using Preferences DataStore',
          nativeFunction: StorageDataStore.writeStringPreferenceDataStore,
        },
        {
          title: 'Write StringSet to Preferences DataStore',
          description:
            'Write StringSet into the snadbox using Preferences DataStore',
          nativeFunction: StorageDataStore.writeStringSetPreferenceDataStore,
        },
        {
          title: 'Write Data to Proto DataStore',
          description:
            'Write Data into the snadbox using Preferences DataStore',
          nativeFunction: StorageDataStoreProto.writeProtoDataStore,
        },
        {
          title: 'Read String from Preferences DataStore',
          description:
            'Read String from the snadbox using Preferences DataStore',
          nativeFunction: StorageDataStore.readStringPreferenceDataStore,
        },
        {
          title: 'Read StringSet from Preferences DataStore',
          description:
            'Read StringSet from the snadbox using Preferences DataStore',
          nativeFunction: StorageDataStore.readStringSetPreferenceDataStore,
        },
        {
          title: 'Read Data from Proto DataStore',
          description:
            'Read structured Data into the snadbox using Preferences DataStore',
          nativeFunction: StorageDataStoreProto.readProtoDataStore,
        },
      ],
    },
    {
      title: 'java.io',
      description:
        'Theses tests will write and read data into the local sanbox using the java.io Classes.',
      testCases: [
        {
          title: 'Write writeBufferedOutputStream',
          description: 'Write data into the sandbox using BufferedOutputStream',
          nativeFunction: null,
        },
        {
          title: 'Write writeBufferedWriter',
          description: 'Write data into the sandbox using BufferedWriter',
          nativeFunction: null,
        },
        // 'writeBufferedOutputStream',
        // 'writeBufferedWriter',
        // 'writeByteArrayOutputStream',
        // 'writeCharArrayWriter',
        // 'writeConsole',
        // 'writeDataOutputStream',
        // 'writeFileOutputStream',
        // 'writeFilterOutputStream',
        // 'writeFilterWriter',
        // 'writeObjectOutputStream',
        // 'writeStringWriter',
      ],
    },
  );
  testCases.CRYPTO.push();
  testCases.AUTH.push();
  testCases.NETWORK.push();
  testCases.PLATFORM.push();
  testCases.CODE.push();
  testCases.RESILIENCE.push();
} else if (Platform.OS === 'ios') {
  /*
    iOS Use-Cases
  */
  testCases.STORAGE.push({
    title: 'Sensitive Data in Keychain',
    description:
      'This test cases will read and write sensitive Data to the iOS Keychain.',
    testCases: [
      {
        title: 'Write Data into the Keychain',
        description: 'Write unencrypted data sandbox Keychain.',
        nativeFunction: null,
      },
      {
        title: 'Read Data from Keychain',
        description: 'Write data into the sandbox using writeBufferedWriter',
        nativeFunction: null,
      },
      //'writeDataFromKeychain', 'readDataToKeychain'
    ],
  });
  testCases.CRYPTO.push();
  testCases.AUTH.push();
  testCases.NETWORK.push();
  testCases.PLATFORM.push();
  testCases.CODE.push();
  testCases.RESILIENCE.push();
}

export default testCases;
