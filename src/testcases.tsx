import {NativeModules, Platform} from 'react-native';
import {TestCases} from './appContent';
const {
  StorageSharedPreferences,
  StorageLog,
  StorageDataStore,
  StorageDataStoreProto,
  StorageInternalStorage,
  StorageExternalStorage,
  StorageRoomDatabase,
  CryptoKeyStore,
  CryptoKeyAttestation,
  CryptoCipher,
  CryptoEncryptedFile,
  CryptoEncryptedSharedPreferences,
  PlatformWebView,
  ResilienceFileIntegrityManager,
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
    {
      title: 'Sensitive Date in Notifications',
      description: 'This test uses noitifications to display sensitive data.',
      testCases: [],
    },
    {
      title: 'Passwords in Cleartext Forms',
      description:
        "This test creates input fields which are meant for passwords but don't conciel them proerly.",
      testCases: [],
    },
  ],
  CRYPTO: [],
  AUTH: [
    {
      title: 'Biometry',
      description: '',
      testCases: [],
    },
  ],
  NETWORK: [
    {
      title: 'Unencrypted Connections',
      description: '',
      testCases: [],
    },
    {
      title: 'TLS Client Settings',
      description: '',
      testCases: [],
    },
    {
      title: 'TLS Pinning',
      description: '',
      testCases: [],
    },
  ],
  PLATFORM: [],
  CODE: [],
  RESILIENCE: [
    {
      title: 'Verify Signature',
      description:
        'Programmatically verify the signature of the package during runtime.',
      testCases: [],
    },
  ],
  PRIVACY: [
    {
      title: 'Access Phone Data',
      description: 'e.G. Calendar, Contacts,',
      testCases: [],
    },
    {
      title: 'Access Geolocation',
      description: '',
      testCases: [],
    },
    {
      title: 'SMS',
      description: '',
      testCases: [],
    },
  ],
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
      title: 'Internal Sandboxed Storage',
      description:
        'These test cases write data into the internal storage (FilesDir and CacheDir). These files are not encrypted by default.',
      testCases: [
        {
          title: 'Get internal location using getFilesDir',
          description:
            'Returns the absolute path to the directory on the filesystem where files created with openFileOutput(String, int) are stored.',
          nativeFunction: StorageInternalStorage.getFilesDir,
        },
        {
          title: 'Get cache location using getCacheDir',
          description:
            'Returns the absolute path to the application specific cache directory on the filesystem.',
          nativeFunction: StorageInternalStorage.getCacheDir,
        },
        {
          title: 'Write a internal text file',
          description:
            "Open a private file associated with this Context's application package for writing. Creates the file if it doesn't already exist. ",
          nativeFunction: StorageInternalStorage.writeFileOutput,
        },
        {
          title: 'Write an internal text file using different Modes',
          description:
            "Open a private file associated with this Context's application package for writing. Creates the file if it doesn't already exist. This test writes different modes such as MODE_WORLD_READABLE.",
          nativeFunction: StorageInternalStorage.writeFileOutputModes,
        },
        {
          title: 'Read a internal text file',
          description:
            'Open a private text file and reads the content using InputStreamReader and BufferedReader.',
          nativeFunction: StorageInternalStorage.readTextFile,
        },
        {
          title: 'Get a list of internal files',
          description:
            "Returns an array of strings naming the private files associated with this Context's application package.",
          nativeFunction: StorageInternalStorage.getInternalFileList,
        },
        {
          title: 'Create a cache file',
          description:
            'Creates an empty file in the default temporary-file directory, using the given prefix and suffix to generate its name. Invoking this method is equivalent to invoking createTempFile(prefix, suffix, null). ',
          nativeFunction: StorageInternalStorage.createCacheFile,
        },
      ],
    },
    {
      title: 'External Sandboxed Storage',
      description:
        'These test cases write data into the extternal storage (ExternalFilesDir and ExternalCacheDir). An app can use these locations, if the internal storage is full for exmaple. They are still sandboxed form other applications. However, an attacker may remove the extenral storage and have easy access to this data. These files are not encrypted by default.',
      testCases: [
        {
          title: 'Check state of external storage',
          description:
            'Returns the current state of the primary shared/external storage media.',
          nativeFunction: StorageExternalStorage.checkState,
        },
        {
          title: 'Get root location using getExternalFilesDir',
          description:
            'Returns the absolute path to the directory on the primary shared/external storage device where the application can place persistent files it owns. These files are internal to the applications, and not typically visible to the user as media. ',
          nativeFunction: StorageExternalStorage.getExternalFilesDirRoot,
        },
        {
          title: 'Get external cache location using getExternalCacheDir',
          description:
            'Returns absolute path to application-specific directory on the primary shared/external storage device where the application can place cache files it owns. These files are internal to the application, and not typically visible to the user as media. ',
          nativeFunction: StorageExternalStorage.getExternalCacheDir,
        },
        {
          title: 'Get different external file location',
          description:
            'Try to access different types of external locatios such as Environment.DIRECTORY_MUSIC or Environment.DIRECTORY_PICTURES',
          nativeFunction: StorageExternalStorage.getDifferentExternalDirs,
        },
        {
          title: 'Write a external text file',
          description:
            "Open a external file associated with this Context's application package for writing. Creates the file if it doesn't already exist. ",
          nativeFunction: StorageExternalStorage.writeFileOutput,
        },
        {
          title: 'Read a external text file',
          description:
            'Open a external text file and reads the content using InputStreamReader and BufferedReader.',
          nativeFunction: StorageExternalStorage.readTextFile,
        },
      ],
    },

    {
      title: 'Room API',
      description:
        'The Room persistence library provides an abstraction layer over SQLite to allow fluent database access while harnessing the full power of SQLite. The data ist not encrypted using this API.',
      testCases: [
        {
          title: 'Init Room Database',
          description:
            'Creates a RoomDatabase.Builder for a persistent database. Once a database is built, you should keep a reference to it and re-use it.',
          nativeFunction: StorageRoomDatabase.init,
        },
        {
          title: 'Write Data to Room Database',
          description: 'Writes a simple datastrucutre to the Room DB',
          nativeFunction: StorageRoomDatabase.writeToRoomDb,
        },
        {
          title: 'Read Data from Room Database',
          description: 'Reads a simple datastrucutre from the Room DB',
          nativeFunction: StorageRoomDatabase.readFromRoomDb,
        },
      ],
    },
    {
      title: 'MediaStore API',
      description:
        "To provide a more enriched user experience, many apps let users contribute and access media that's available on an external storage volume. The framework provides an optimized index into media collections, called the media store, that lets users retrieve and update these media files more easily. Even after your app is uninstalled, these files remain on the user's device. These files remain outside the app sandbox.",
      testCases: [],
    },

    // {
    //   title: 'java.io',
    //   description:
    //     'Theses tests will write and read data into the local sanbox using the java.io Classes.',
    //   testCases: [
    //     {
    //       title: 'Write writeBufferedOutputStream',
    //       description: 'Write data into the sandbox using BufferedOutputStream',
    //       nativeFunction: null,
    //     },
    //     {
    //       title: 'Write writeBufferedWriter',
    //       description: 'Write data into the sandbox using BufferedWriter',
    //       nativeFunction: null,
    //     },
    //     // 'writeBufferedOutputStream',
    //     // 'writeBufferedWriter',
    //     // 'writeByteArrayOutputStream',
    //     // 'writeCharArrayWriter',
    //     // 'writeConsole',
    //     // 'writeDataOutputStream',
    //     // 'writeFileOutputStream',
    //     // 'writeFilterOutputStream',
    //     // 'writeFilterWriter',
    //     // 'writeObjectOutputStream',
    //     // 'writeStringWriter',
    //   ],
    // },
  );
  testCases.CRYPTO.push(
    {
      title: 'KeyStore',
      description:
        'This class represents a storage facility for cryptographic keys and certificates. The Android Keystore system lets you store cryptographic keys in a container to make them more difficult to extract from the device. Once keys are in the keystore, you can use them for cryptographic operations, with the key material remaining non-exportable. ',
      testCases: [
        {
          title: 'Init KeyStores',
          description:
            'Intializes instances of the avialable KeyStores, they are: AndroidKeyStore, AndroidCAStore, BKS, BouncyCastle and PKCS12',
          nativeFunction: CryptoKeyStore.init,
        },
        {
          title: 'Get Aliases',
          description:
            'Retrieves all key aliases of the currently stored keys.',
          nativeFunction: CryptoKeyStore.getKeyAliases,
        },
        {
          title: 'Create Secure KeyGenParameterSpec',
          description:
            'Creates a RSA KeyGenParameterSpec which is considered as secure.',
          nativeFunction: CryptoKeyStore.createSecureKeyGenParameterSpec,
        },
        {
          title: "Create Insecure KeyGenParameterSpec's",
          description:
            'Creates a few KeyGenParameterSpec which are considered inseuce, such as small RSA keysize, insecure ciphers (RC4), or weak digest algorithms.',
          nativeFunction: CryptoKeyStore.createInsecureKeyGenParameterSpec,
        },
        {
          title: 'Create Asymmetric Key',
          description: 'Create RSA and EC keys using the KeyPairGenerator.',
          nativeFunction: CryptoKeyStore.generateAsymmetricKeys,
        },
        {
          title: 'Access Private Key',
          description:
            'Creates an asymmetric EC key pair and tries to access the private key.',
          nativeFunction: CryptoKeyStore.accessPrivateKey,
        },
        {
          title: 'Read KeyInfo',
          description:
            'Gets the KeyInfo object of a key and reads information about the key.',
          nativeFunction: CryptoKeyStore.readKeyInfo,
        },
      ],
    },
    {
      title: 'Key Attestation',
      description:
        " Key Attestation gives you more confidence that the keys you use in your app are stored in a device's hardware-backed keystore. The following sections describe how to verify the properties of hardware-backed keys and how to interpret the attestation certificates' extension data. ",
      testCases: [
        {
          title: 'Get Certificate Chain',
          description:
            "Use a KeyStore object's getCertificateChain() method to get a reference to the chain of X.509 certificates associated with the hardware-backed keystore.",
          nativeFunction: CryptoKeyAttestation.getCertificateChain,
        },
      ],
    },
    {
      title: 'KeyChain',
      description:
        'The KeyChain class provides access to private keys and their corresponding certificate chains in credential storage. ',
      testCases: [],
    },
    {
      title: 'Cipher',
      description:
        'These tests use the Cipher class for basic cryptographic operations such as encrypyt, or sign.',
      testCases: [
        {
          title: 'Init Insecure Ciphers',
          description: 'Initializes insecure ciphers, such as AES/ECB or RC4',
          nativeFunction: CryptoCipher.initInsecureCiphers,
        },
        {
          title: 'Init Insecure KeyGenerators',
          description:
            'Creates KeyGenerators for all available Algorithms, also insecure one such as RC4',
          nativeFunction: CryptoCipher.initInsecureKeyGenerators,
        },
        {
          title: 'Init Insecure Signatures',
          description: 'Initializes insecure Signatures.',
          nativeFunction: CryptoCipher.initInsecureSignatures,
        },
        {
          title: 'Deprecated Bouncy Castle',
          description: 'Initializes an Cipher object with deprecated BC',
          nativeFunction: CryptoCipher.bouncyCastle,
        },
        {
          title: 'Password-Based Ciphers',
          description: 'Initializes an Cipher with PBE',
          nativeFunction: CryptoCipher.pbeCipher,
        },
        {
          title: 'Password-Based Ciphers with Zero-IV',
          description:
            "Password-based encryption (PBE) ciphers that require an initialization vector (IV) can obtain it from the key, if it's suitably constructed, or from an explicitly passed IV. If you pass a PBE key that doesn't contain an IV and don't pass an explicit IV, the PBE ciphers on Android currently assume an IV of zero.",
          nativeFunction: CryptoCipher.pbeCipherZeroIV,
        },
        {
          title: 'Encrypt',
          description: 'Ueses AES in CBC mode to encrypt a Text',
          nativeFunction: CryptoCipher.encrypt,
        },
        {
          title: 'Decrypt',
          description: 'Ueses AES in CBC mode to encrypt a Text',
          nativeFunction: CryptoCipher.decrypt,
        },
        {
          title: 'Sign Document',
          description: 'Uses Cipher to sign a document.',
          nativeFunction: CryptoCipher.sign,
        },
        {
          title: 'Verify Document',
          description: 'Uses Cipher to verify a document.',
          nativeFunction: CryptoCipher.verify,
        },
      ],
    },
    {
      title: 'EncryptedSharedPreferences',
      description:
        'This testcase stores data using secure, encrypted SharedPreferences provided by androidx.security.crypto',
      testCases: [
        {
          title: 'Create EncryptedSharedPreferences Instance',
          description: 'Create EncryptedSharedPreferences.',
          nativeFunction:
            CryptoEncryptedSharedPreferences.createEncryptedSharedPreferences,
        },
        {
          title: 'Write String to EncryptedSharedPreferences',
          description:
            'Write string into the snadbox using putString method of EncryptedSharedPreferences.',
          nativeFunction: CryptoEncryptedSharedPreferences.putString,
        },
        {
          title: 'Write StringSet to EncryptedSharedPreferences',
          description:
            'Write stringSet into the snadbox using putStringSet method of EncryptedSharedPreferences.',
          nativeFunction: CryptoEncryptedSharedPreferences.putStringSet,
        },
        {
          title: 'Read String from EncryptedSharedPreferences',
          description:
            'Read a String from the unencrypted EncryptedSharedPreferences',
          nativeFunction: CryptoEncryptedSharedPreferences.readString,
        },
        {
          title: 'Read StringSet from EncryptedSharedPreferences',
          description:
            'Read a StringSet from the unencrypted EncryptedSharedPreferences',
          nativeFunction: CryptoEncryptedSharedPreferences.readStringSet,
        },
      ],
    },

    {
      title: 'EncryptedFile',
      description:
        'Class used to create and read encrypted files using androidx.security.crypto',
      testCases: [
        {
          title: 'Write Encrypted File',
          description:
            'Encrypts a file using the Builder class to configure EncryptedFile',
          nativeFunction: CryptoEncryptedFile.writeFile,
        },

        {
          title: 'Read Encrypted File',
          description:
            'Decrypts a file using the Builder class to configure EncryptedFile',
          nativeFunction: CryptoEncryptedFile.readFile,
        },
      ],
    },

    {
      title: 'MasterKey',
      description:
        "Wrapper for a master key used in the library. On Android M (API 23) and above, this is class references a key that's stored in the Android Keystore. On Android L (API 21, 22), there isn't a master key.",
      testCases: [],
    },
  );
  testCases.AUTH.push({
    title: 'Confirm Acccess To Keys',
    description:
      'These Tests create Keys setUserAuthenticationRequired set to true.',
    testCases: [],
  });
  testCases.NETWORK.push();
  testCases.PLATFORM.push({
    title: 'WebView',
    description:
      'This class represents a storage facility for cryptographic keys and certificates. The Android Keystore system lets you store cryptographic keys in a container to make them more difficult to extract from the device. Once keys are in the keystore, you can use them for cryptographic operations, with the key material remaining non-exportable. ',
    testCases: [
      {
        title: 'Load Local Resource',
        description: '',
        nativeFunction: PlatformWebView.loadLocalResource,
      },
      {
        title: 'Load Remote HTTP Resource',
        description: '',
        nativeFunction: PlatformWebView.loadRemoteHttpResource,
      },
      {
        title: 'Load Remote HTTPS Resource',
        description: '',
        nativeFunction: PlatformWebView.loadRemoteHttpsResource,
      },
      {
        title: 'Allow File Access',
        description: '',
        nativeFunction: PlatformWebView,
      },
      {
        title: 'Use JavascriptInterface',
        description: '',
        nativeFunction: PlatformWebView,
      },
      {
        title: 'Enable Geolocation',
        description: '',
        nativeFunction: PlatformWebView,
      },
      {
        title: 'Allow Mixed Content',
        description: '',
        nativeFunction: PlatformWebView,
      },
    ],
  });
  testCases.CODE.push();
  testCases.RESILIENCE.push({
    title: 'FileIntegrityManager',
    description:
      'This class provides access to file integrity related operations.',
    testCases: [
      {
        title: 'setupFsVerity',
        description:
          "Enables fs-verity to the owned file under the calling app's private directory. It always uses the common configuration, i.e. SHA-256 digest algorithm, 4K block size, and without salt. ",
        nativeFunction: ResilienceFileIntegrityManager.setupFsVerify,
      },
    ],
  });
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
        description: '',
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
