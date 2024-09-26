/* eslint-disable no-trailing-spaces */
/* eslint-disable prettier/prettier */
import {NativeModules} from 'react-native';
import {TestCases} from '../appContent';
const {
  StorageSharedPreferences,
  StorageDataStore,
  StorageDataStoreProto,
  StorageExternalStorage,
  StorageJavaFileIo,
  StorageRoomDatabase,
  StorageSQLite,
  
  CryptoKeyStore,
  CryptoKeyAttestation,
  CryptoKeyChain,
  CryptoCipher,
  CryptoEncryptedFile,
  CryptoEncryptedSharedPreferences,
  CryptoMasterKey,
  CryptoRandom,

  AuthBiometricManager,
  AuthBiometricPrompt,
  AuthKeyAccess,
  AuthFingerprintManager,
  AuthKeyguardManager,
  AuthProtectedConfirmation,

  NetworkTlsConfig,
  NetworkTlsPinning,
  NetworkLocalNetwork,

  PlatformWebView,
  PlatformIpc,
  PlatformUiDisclosure,

  ResilienceFileIntegrityManager,
  ResilienceAntiDebug,
  ResilienceAntiVm,
} = NativeModules;

interface Dictionary<Type> {
  [key: string]: Type;
}

export var androidTestCases: Dictionary<TestCases[]> = {
  STORAGE: [
    {
      title: 'SharedPreferences',
      maswe: '0006',
      description:
        'This testcase stores sensitive data using unencrypted SharedPreferences.',
      testCases: [
        {
          title: 'Init SharedPreferences',
          description: 'Get an world read/writable SharedPreferenceInstance',
          nativeFunction: StorageSharedPreferences.getInsecureSharedPreferences,
        },

        {
          title: 'Write sensitve String',
          description:
          'Write canary token into the sandbox using SharedPreferences.',
          nativeFunction: StorageSharedPreferences.putString,
        },
        {
          title: 'Write sensitve StringSet',
          description:
          'Write a stringset of canary tokens into the sandbox using SharedPreferences.',
          nativeFunction: StorageSharedPreferences.putStringSet,
        },
      ],
    },

    {
      title: 'DataStore',
      maswe: '0006',
      description:
        'This testcase stores sensitve data using the DataStore. Data stored this way is not encrypted in the sandbox. An attacker may therefore be able to retrieve the data.',
      testCases: [
        {
          title: 'Init Preferences DataStore (RxDataStoreBuilder)',
          description:
            'Uses RxDataStoreBuilder to create a Preferences DataStore',
          nativeFunction: StorageDataStore.initPreferenceDataStore,
        },
        {
          title: 'Write sensitive String to Preferences DataStore',
          description:
            'Write String into the snadbox using Preferences DataStore',
          nativeFunction: StorageDataStore.writeStringPreferenceDataStore,
        },
        {
          title: 'Write sensitive StringSet to Preferences DataStore',
          description:
            'Write StringSet into the snadbox using Preferences DataStore',
          nativeFunction: StorageDataStore.writeStringSetPreferenceDataStore,
        },
        {
          title: 'Write sensitve Data to Proto DataStore',
          description:
            'Write Data into the snadbox using Preferences DataStore',
          nativeFunction: StorageDataStoreProto.writeProtoDataStore,
        },
      ],
    },


    {
      title: 'java.file.io',
      description:
        'These test cases write data into the internal storage (FilesDir and CacheDir) using java.io.File. These files are not encrypted by default.',
      testCases: [
        {
          title: 'Write senstive to internal sandbox',
          maswe: '006',
          description:
            "Open a private file associated with this Context's application package for writing. Creates the file if it doesn't already exist. ",
          nativeFunction: StorageJavaFileIo.writeFileSandbox,
        },
        {
          title: 'Write senstive Data to external Storage',
          maswe: '002',
          description: 'Tries to write sensitive data into a file outside of the sandbox.',
          nativeFunction: StorageJavaFileIo.writeExternal,
        },

        {
          title: 'Write senstive, MODE_WORLD_WRITEABLE Data to external Storage',
          maswe: '002',
          description: 'Tries to write sensitive data into a file outside of the sandbox.',
          nativeFunction: StorageJavaFileIo.writeExternalWorldWritable,
        },

        {
          title: 'Write senstive, MODE_WORLD_READABLE Data to external Storage',
          maswe: '002',
          description: 'Tries to write sensitive data into a file outside of the sandbox.',
          nativeFunction: StorageJavaFileIo.writeExternalWorldReadable,
        },
      ],
    },


    {
      title: 'Querying External Storage',
      maswe: '0002',
      description:
        'These test cases write data into the external storage (ExternalFilesDir and ExternalCacheDir). An app can use these locations, if the internal storage is full for exmaple. They are still sandboxed form other applications. However, an attacker may remove the extenral storage and have easy access to this data. These files are not encrypted by default.',
      testCases: [
        {
          title: 'Check state of external Storage',
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
      ],
    },

    {
      title: 'SQLite Database',
      maswe: '006',
      description:
        'SQLite Database is a simple, local SQL-Database. The data is stored in plain text within the sandbox by default. Hence, the developer must take care of protecting sensitive data in nessecary.',
      testCases: [
        {
          title: 'Create SQLite DB',
          nativeFunction: StorageSQLite.createSQLiteDB,
        },

        {
          title: 'Store sensitve Data using execSQL',
          nativeFunction: StorageSQLite.execSQL,
        },
        {
          title: 'Store sensitve Data using insert',
          nativeFunction: StorageSQLite.insert,
        },
        {
          title: 'Store sensitve Data using replace',
          nativeFunction: StorageSQLite.replace,
        },
        {
          title: 'Store sensitve Data using update',
          nativeFunction: StorageSQLite.update,
        },
      ],
    },

    {
      title: 'Room API',
      maswe: '006',
      description:
        'The Room persistence library provides an abstraction layer over SQLite to allow fluent database access while harnessing the full power of SQLite. The data ist not encrypted using this API.',
      testCases: [
        {
          title: 'Write sensitve Data to Room Database',
          description: 'Writes a simple datastrucutre to the Room DB',
          nativeFunction: StorageRoomDatabase.writeToRoomDb,
        },
      ],
    },
    {
      title: 'MediaStore API',
      description:
        "To provide a more enriched user experience, many apps let users contribute and access media that's available on an external storage volume. The framework provides an optimized index into media collections, called the media store, that lets users retrieve and update these media files more easily. Even after your app is uninstalled, these files remain on the user's device. These files remain outside the app sandbox.",
      testCases: [],
    },
    // Define your Android-specific storage test cases here
  ],

  CRYPTO: [




    {
      title: 'KeyStore',
      description:
        'This class represents a storage facility for cryptographic keys and certificates. The Android Keystore system lets you store cryptographic keys in a container to make them more difficult to extract from the device. Once keys are in the keystore, you can use them for cryptographic operations, with the key material remaining non-exportable. ',
      testCases: [
        // {
        //   title: 'Init KeyStores',
        //   description:
        //     'Intializes instances of the avialable KeyStores, they are: AndroidKeyStore, AndroidCAStore, BKS, BouncyCastle and PKCS12',
        //   nativeFunction: CryptoKeyStore.init,
        // },
        // {
        //   title: 'Get Aliases',
        //   description:
        //     'Retrieves all key aliases of the currently stored keys.',
        //   nativeFunction: CryptoKeyStore.getKeyAliases,
        // },
        // {
        //   title: 'Create Secure KeyGenParameterSpec',
        //   description:
        //     'Creates a RSA KeyGenParameterSpec which is considered as secure.',
        //   nativeFunction: CryptoKeyStore.createSecureKeyGenParameterSpec,
        // },

        {
          title: 'Create KeyGenParameterSpec',
          description:
            'Creates a KeyGenParameterSpec which are considered inseuce, such as small RSA keysize, insecure ciphers (RC4), or weak digest algorithms.',
          nativeFunction: CryptoKeyStore.createInsecureKeyGenParameterSpec,
        },



        
        {
          title: 'Create Insecure KeyGenParameterSpec',
          description:
            'Creates a KeyGenParameterSpec which are considered inseuce, such as small RSA keysize, insecure ciphers (RC4), or weak digest algorithms.',
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
          title: 'Password-Based Cipher with Low Iteration Count',
          description:
            'Initializes an Cipher with PBE with 1 iteration when creating the Cipher Spec.',
          nativeFunction: CryptoCipher.pbeCipherLowIteration,
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
        'This testcase stores data using secure, encrypted SharedPreferences provided by androidx.security.crypto. Currently, there are only secure ways of using this function.',
      testCases: [
        {
          title: 'Create EncryptedSharedPreferences Instance',
          description: 'Create EncryptedSharedPreferences.',
          nativeFunction: CryptoEncryptedSharedPreferences.createEncryptedSharedPreferences,
        },
        // {
        //   title: 'Write String to EncryptedSharedPreferences',
        //   description:
        //     'Write string into the snadbox using putString method of EncryptedSharedPreferences.',
        //   nativeFunction: CryptoEncryptedSharedPreferences.putString,
        // },
        // {
        //   title: 'Write StringSet to EncryptedSharedPreferences',
        //   description:
        //     'Write stringSet into the snadbox using putStringSet method of EncryptedSharedPreferences.',
        //   nativeFunction: CryptoEncryptedSharedPreferences.putStringSet,
        // },
        // {
        //   title: 'Read String from EncryptedSharedPreferences',
        //   description:
        //     'Read a String from the unencrypted EncryptedSharedPreferences',
        //   nativeFunction: CryptoEncryptedSharedPreferences.readString,
        // },
        // {
        //   title: 'Read StringSet from EncryptedSharedPreferences',
        //   description:
        //     'Read a StringSet from the unencrypted EncryptedSharedPreferences',
        //   nativeFunction: CryptoEncryptedSharedPreferences.readStringSet,
        // },
      ],
    },

    
    {
      title: 'EncryptedFile',
      description:
        'Class used to create and read encrypted files using androidx.security.crypto. Currently, there are only secure ways of using this function. If an application does not use this to store files, this may be an option.',
      testCases: [
        {
          title: 'Write Encrypted File',
          description:
            'Encrypts a file using the Builder class to configure EncryptedFile.',
          nativeFunction: CryptoEncryptedFile.writeFile,
        },

        // {
        //   title: 'Read Encrypted File',
        //   description:
        //     'Decrypts a file using the Builder class to configure EncryptedFile',
        //   nativeFunction: CryptoEncryptedFile.readFile,
        // },
      ],
    },


    {
      title: 'MasterKey',
      description:
        "Wrapper for a master key used in the library. On Android M (API 23) and above, this is class references a key that's stored in the Android Keystore. On Android L (API 21, 22), there isn't a master key.",
      testCases: [
        {
          title: 'Create a MasterKey',
          description:
            'Decrypts a file using the Builder class to configure EncryptedFile',
          nativeFunction: CryptoMasterKey.createMasterKey,
        },
      ],
    },


    {
      title: 'KeyChain',
      description:
        'The KeyChain class provides access to private keys and their corresponding certificate chains in credential storage.',
      testCases: [
        {
          title: 'Create a KeyChain',
          description: 'Creates a KeyChain',
          nativeFunction: CryptoKeyChain.createKeyChain,
        },
      ],
    },


    {
      title: 'Random Numbers',
      description:
        'Identify all the instances of random number generators and look for either custom or well-known insecure classes. These tests generate random number in different ways.',
      testCases: [
        {
          title: 'Create Insecure Random',
          description: 'Creates a new random number generator.',
          nativeFunction: CryptoRandom.insecureRandom,
        },
        {
          title: 'Create Insecure Random with Seed',
          description:
            'Creates a new random number generator using a single long seed.',
          nativeFunction: CryptoRandom.insecureRandomSeed,
        },
        // {
        //   title: 'Create SecureRandom',
        //   description:
        //     'Constructs a secure random number generator (RNG) implementing the default random number algorithm.',
        //   nativeFunction: CryptoRandom.secureRandom,
        // },
        {
          title: 'Create Deprecated SecureRandom.',
          description:
            'Constructs a secure random number generator using a getInstance().',
          nativeFunction: CryptoRandom.secureRandomInstance,
        },
      ],
    },
    // Define your Android-specific crypto test cases here
  ],
  AUTH: [

    {
      title: 'BiometricManager',
      description:
        'A class that provides system information related to biometrics (e.g. fingerprint, face, etc.). ',
      testCases: [
        {
          title: 'Can Device STRONG Authenticate?',
          description: '',
          nativeFunction: AuthBiometricManager.testStrongAuth,
        },
        {
          title: 'Can Device WEAK Authenticate?',
          description: '',
          nativeFunction: AuthBiometricManager.testWeakAuth,
        },
        {
          title: 'Can Device DEVICE_CREDENTIAL Authenticate?',
          description: '',
          nativeFunction: AuthBiometricManager.testDeviceCredentialsAuth,
        },
      ],
    },

    {
      title: 'BiometricPrompt',
      description:
        "A class that manages a system-provided biometric prompt. On devices running Android 9.0 (API 28) and above, this will show a system-provided authentication prompt, using one of the device's supported biometric modalities (fingerprint, iris, face, etc). Prior to Android 9.0, this will instead show a custom fingerprint authentication dialog. The prompt will persist across configuration changes unless explicitly canceled. For security reasons, the prompt will be dismissed when the client application is no longer in the foreground. ",
      testCases: [
        {
          title: 'Create a Simple Prompt',
          description: 'Simply requires biometric authentication. This can method of authentcation can usually be bypassed easily.',
          nativeFunction: AuthBiometricPrompt.simplePrompt,
        },
        {
          title: 'Create Device PIN Only Prompt',
          description: 'Create a Prompt only for PINs.',
          nativeFunction: AuthBiometricPrompt.devicePinOnlyPrompt,
        },
        // {
        //   title: 'Create a Crypto Object Prompt',
        //   description: 'Unlocks a key which requires biometric authentication',
        //   nativeFunction: AuthBiometricPrompt.cryptoOperationPrompt,
        // },
      ],
    },




    {
      title: 'Acccess To Keys',
      description:
        'These Tests create Keys setUserAuthenticationRequired set to true.',
      testCases: [
        {
          title: 'Device Auth Required',
          description:
            'Creates a new key wich requires a device authentication such as PIN, Password or Pattern.',
          nativeFunction: AuthKeyAccess.deviceCredentialsRequired,
        },
        {
          title: 'Biometric Auth Required',
          description:
            'Creates a new key wich requires a strong biometric authentication.',
          nativeFunction: AuthKeyAccess.biometryRequired,
        },
        {
          title: 'Long Key Validity',
          description:
            'Creates a new key wich setUserAuthenticationRequired is set to true, but it has a very long validity.',
          nativeFunction: AuthKeyAccess.longValidity,
        },
      ],
    },

    {
      title: 'FingerprintManager',
      description:
        'FingerprintManager is deprectatd and should not be used anymore. The replacement is BiometryManger and BiometryPrompt.',
      testCases: [
        {
          title: 'Query Fingerprint Hardware',
          nativeFunction: AuthFingerprintManager.isHardwareDetected,
        },
        {
          title: 'Query Fingerprints',
          nativeFunction: AuthFingerprintManager.hasEnrolledFingerprints,
        },
        {
          title: 'Simple Authenticate',
          nativeFunction: AuthFingerprintManager.authenticateSimple,
        },
        {
          title: 'Crypto Object Authenticate',
          nativeFunction: AuthFingerprintManager.authenticateCryptoObject,
        },
      ],
    },

    {
      title: 'KeyguardManager',
      description:
        'The KeyguardManager class in Android is used to manage the keyguard (lock screen) functionality. It provides methods for determining the state of the keyguard, requesting its dismissal, and handling various events related to the keyguard.',
      testCases: [
        {
          title: 'Check Keyguard State',
          description: 'Verifies the state of Keyguard (Secure/Locked?)',
          nativeFunction: AuthKeyguardManager.checkKeyguardState,
        },
        {
          title: 'Check Device State',
          description: 'Verifies the state of the device (Secure/Locked?)',
          nativeFunction: AuthKeyguardManager.checkDeviceState,
        },
        {
          title: 'Check if Pattern is used',
          description: 'Verifies if the lockscreen uses a pattern or not.',
          nativeFunction: AuthKeyguardManager.checkPattern,
        },
        {
          title: 'Disable KeyguardLock',
          nativeFunction: AuthKeyguardManager.disableKeyguardLock,
        },
        {
          title: 'Request Dismiss Keyguard',
          nativeFunction: AuthKeyguardManager.requestDismissKeyguard,
        },
      ],
    },

    {
      title: 'Android Protected Confirmation',
      description:
        "Android Protected Confirmation doesn't provide a secure information channel for the user. Data shown here should not be more sensitve than shown in other places within the app.",
      testCases: [
        {
          title: 'Create Protected Confirmation',
          description: '',
          nativeFunction: AuthProtectedConfirmation.create,
        },
      ],
    },

    // Define your Android-specific authentication test cases here
  ],
  NETWORK: [
    {
      title: 'TLS Client Settings',
      description:
        'These tests change the default TLS client configuration. They can result in insecure settings.',
      testCases: [
        {
          title: 'Use Old TLS-Protocol',
          nativeFunction: NetworkTlsConfig.oldTlsConfig,
        },
        {
          title: 'Use Insecure Cipher-Suites',
          description:
            'These tests configure the client to use insecure cipher suites, such as ones with insecure algorithms or disabled forward secrecy-property.',
          nativeFunction: NetworkTlsConfig.insecureCipherSuties,
        },
        {
          title: 'TLS Client-Certifitates',
          nativeFunction: NetworkTlsConfig.clientCertificate,
        },
      ],
    },

    {
      title: 'TLS Pinning',
      description:
        "By hardcoding or 'pinning' specific TLS certificates or public keys within the application's code, TLS pinning ensures that only trusted servers are communicated with, preventing attackers from intercepting and tampering with sensitive data exchanged between the mobile app and the server.  These tests implement this.",
      testCases: [
        {
          title: 'Use Custom TrustStore',
          nativeFunction: NetworkTlsPinning.customTruststore,
        },
        {
          title: 'Use OKHttp CertificatePinner',
          nativeFunction: NetworkTlsPinning.okHttpCertificatePinner,
        },
        {
          title: 'Use WebView CertificatePinner',
          nativeFunction: NetworkTlsPinning.webViewPinning,
        },
        {
          title: 'Programmatically verify',
          nativeFunction: NetworkTlsPinning.programmaticallyVerify,
        },
      ],
    },
    {
      title: 'Local Network',
      description:
        "By hardcoding or 'pinning' specific TLS certificates or public keys within the application's code, TLS pinning ensures that only trusted servers are communicated with, preventing attackers from intercepting and tampering with sensitive data exchanged between the mobile app and the server.  These tests implement this.",
      testCases: [
        {
          title: 'Access Local Network',
          nativeFunction: NetworkLocalNetwork.access,
        },
        {
          title: 'Host HTTP Server',
          nativeFunction: NetworkLocalNetwork.hostHttpServer,
        },
        {
          title: 'Host TCP Server',
          nativeFunction: NetworkLocalNetwork.hostTcpServer,
        },
        {
          title: 'Host UDP Server',
          nativeFunction: NetworkLocalNetwork.hostUDPServer,
        },
      ],
    },
    // Define your Android-specific network test cases here
  ],
  PLATFORM: [
    {
      title: 'Inter Process Communication',
      description:
        'These tests will simulate how information can be sent trough IPC and potentially cause harm, given the input is not properly validated.',
      testCases: [
        {
          title: 'Exported Activity',
          description: '',
          nativeFunction: PlatformIpc.exportedActivity,
        },
        {
          title: 'Service',
          description: '',
          nativeFunction: PlatformIpc.service,
        },
        // {
        //   title: 'Message Service',
        //   description: '',
        //   nativeFunction: PlatformIpc.serviceMessenger,
        // },
        {
          title: 'Content Provider',
          description: '',
          nativeFunction: PlatformIpc.provider,
        },
        {
          title: 'Broadcast Receiver',
          description: '',
          nativeFunction: PlatformIpc.broadcastReceiver,
        },
        {
          title: 'Deep Links',
          description: '',
          nativeFunction: PlatformIpc.deepLinks,
        },
      ],
    },
    {
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
          nativeFunction: PlatformWebView.allowFileAccess,
        },
        {
          title: 'Execute JavaScript From Host',
          description: '',
          nativeFunction: PlatformWebView.sendDataToJsSandbox,
        },
        {
          title: 'Call Native Function From JavaScript',
          description: '',
          nativeFunction: PlatformWebView.readDataFromJsSandbox,
        },
        {
          title: 'Enable Geolocation',
          description: '',
          nativeFunction: PlatformWebView.enableGeolocation,
        },
        {
          title: 'Allow Mixed Content',
          description: '',
          nativeFunction: PlatformWebView.allowMixedContent,
        },
      ],
    },
    {
      title: 'UI Data Disclosure',
      description:
        'UI components that either show such information or take it as input can be a source of data disclosure. Tests in this category simulate them.',
      testCases: [
        {
          title: 'Passwords in Text-Fields',
          description: '',
          nativeFunction: PlatformUiDisclosure.loadLocalResource,
        },
        {
          title: 'Sensitve Data in Notifications',
          description:
            'This test uses noitifications to display sensitive data.',
          nativeFunction: PlatformUiDisclosure.sensitveDataNotifications,
        },
      ],
    },
    // Define your Android-specific platform-related test cases here
  ],
  CODE: [
    // Define your Android-specific code-related test cases here
  ],
  RESILIENCE: [
    {
      title: 'Anti-Debug',
      description:
        'The app tries to find out if it is debuggable, or of a degubber is currently attached.',
      testCases: [
        {
          title: 'Verify if App is Debuggable',
          nativeFunction: ResilienceAntiDebug.debuggable,
        },
        {
          title: 'Verify if Debugger is Attached',
          nativeFunction: ResilienceAntiDebug.debuggerConnected,
        },
      ],
    },
    {
      title: 'Anti-VM',
      description:
        'The app tries to find out if it runs in a virual environment or on real hardware.',
      testCases: [
        {
          title: 'Get IMSI',
          nativeFunction: ResilienceAntiVm.getImsi,
        },
        {
          title: 'Get Build String',
          nativeFunction: ResilienceAntiVm.getBuild,
        },
        {
          title: 'Get Networkinterface',
          nativeFunction: ResilienceAntiVm.getNetworkInterface,
        },
        {
          title: 'Get Installer Package Name',
          nativeFunction: ResilienceAntiVm.getInstallerPackageName,
        },
        {
          title: 'Get Sendor',
          nativeFunction: ResilienceAntiVm.getSensor,
        },
      ],
    },
    {
      title: 'Play Integrity API',
      description:
        'The app tries to validate the integrity of the platform using Play Integrity API',
      testCases: [
        // {
        //   title: 'setupFsVerity',
        //   description:
        //     "Enables fs-verity to the owned file under the calling app's private directory. It always uses the common configuration, i.e. SHA-256 digest algorithm, 4K block size, and without salt. ",
        //   nativeFunction: ResilienceFileIntegrityManager.setupFsVerify,
        // },
      ],
    },
    {
      title: 'SafetyNet API',
      description:
        'The app tries to validate the integrity of the platform using the deprecated SafetyNet API',
      testCases: [
        // {
        //   title: 'setupFsVerity',
        //   description:
        //     "Enables fs-verity to the owned file under the calling app's private directory. It always uses the common configuration, i.e. SHA-256 digest algorithm, 4K block size, and without salt. ",
        //   nativeFunction: ResilienceFileIntegrityManager.setupFsVerify,
        // },
      ],
    },
    {
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
    },
    // Define your Android-specific resilience test cases here
  ],
  PRIVACY: [],
};

export default androidTestCases;
