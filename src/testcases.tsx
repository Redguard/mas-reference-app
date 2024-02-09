import {NativeModules, Platform} from 'react-native';
import {TestCases} from './appContent';
const {
  StorageSharedPreferences,
  StorageEncryptedSharedPreferences,
  StorageLog,
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
      id: 'log',
      title: 'Sensitive Data in Log',
      description:
        'This test cases will write the following sensitive data to the application log:\n\n- Different Password-Identifiere\n- Valid Access-Token\n- ...\n',
      testCases: [
        {
          id: 'writePasswordsToLog',
          title: 'Write Password into Log',
          description:
            'Write Keywords related to Passwords into the Application LOG.',
          nativeFunction: StorageLog.writeSensitiveData,
        },
        {
          id: 'writeAccessTokenToLog',
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
      id: 'sharedPreferences',
      title: 'SharedPreferences',
      description:
        'This testcase stores data using unencrypted SharedPreferences.\n',
      testCases: [
        {
          id: 'writeSharedPreferences.string',
          title: 'Write SharedPreferences.string',
          description:
            'Write string into the snadbox using putString method of SharedPreferences.',
          nativeFunction: StorageSharedPreferences.putString,
        },
        {
          id: 'writeSharedPreferences.stringSet',
          title: 'Write SharedPreferences.stringSet',
          description:
            'Write stringSet into the snadbox using putStringSet method of SharedPreferences.',
          nativeFunction: StorageSharedPreferences.putStringSet,
        },
        {
          id: 'readSharedPreferences',
          title: 'Read SharedPreferences',
          description:
            'Read data into the snadbox using unencrypted SharedPreferences',
          nativeFunction: StorageSharedPreferences.read,
        },
      ],
    },
    {
      id: 'encryptedSharedPreferences',
      title: 'Encrypted SharedPreferences',
      description:
        'This testcase stores data using secure, encrypted SharedPreferences.\n',
      testCases: [
        {
          id: 'writeEncryptedSharedPreferences',
          title: 'Write SharedPreferences',
          description:
            'Write data into the snadbox using encrypted EncryptedSharedPreferences',
          nativeFunction: StorageEncryptedSharedPreferences.write,
        },
        {
          id: 'readEncryptedSharedPreferences',
          title: 'Write Access Token into Log',
          description:
            'Read data into the snadbox using encrypted EncryptedSharedPreferences',
          nativeFunction: StorageEncryptedSharedPreferences.read,
        },
      ],
    },
    {
      id: 'java.io',
      title: 'java.io',
      description:
        'Theses tests will write and read data into the local sanbox using the java.io Classes.',
      testCases: [
        {
          id: 'writeBufferedOutputStream',
          title: 'Write writeBufferedOutputStream',
          description: 'Write data into the sandbox using BufferedOutputStream',
          nativeFunction: null,
        },
        {
          id: 'writeBufferedWriter',
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
    id: 'keychain',
    title: 'Sensitive Data in Keychain',
    description:
      'This test cases will read and write sensitive Data to the iOS Keychain.',
    testCases: [
      {
        id: 'writeDataFromKeychain',
        title: 'Write Data into the Keychain',
        description: 'Write unencrypted data sandbox Keychain.',
        nativeFunction: null,
      },
      {
        id: 'readDataToKeychain',
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
