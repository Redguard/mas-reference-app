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
  StorageMediaStoreAPI,

  CryptoKeyAttestation,
  CryptoCipher,
  CryptoRandom,
  CryptoKeyGenParameterSpec,
  CryptoSecretKeyFactory,
  CryptoDeprecated,
  CryptoKeyInfo,

  AuthBiometricManager,
  AuthBiometricPrompt,
  AuthFingerprintManager,
  AuthKeyguardManager,
  AuthProtectedConfirmation,
  AuthHttpBasicAuth,

  NetworkTlsPinning,
  NetworkLocalNetwork,

  PlatformWebView,
  PlatformIpc,
  PlatformUiDisclosure,

  CodeUpdate,
  CodeDependencies,
  CodeInsecureSoftware,

  ResilienceAntiDebug,
  ResilienceAntiVm,
  ResiliencObfuscation,
  ResilienceDeviceIntegrity,
  ResilienceRootDetection,

  PrivacyMarketingUUID,

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
          title: 'Init WORLD_READABLE SharedPreferences',
          description: 'Get an world readable SharedPreferenceInstance',
          nativeFunction: StorageSharedPreferences.getWorldReadableInstance,
        },
        {
          title: 'Init WORLD_WRITABLE SharedPreferences',
          description: 'Get an world writable SharedPreferenceInstance',
          nativeFunction: StorageSharedPreferences.getWorldWritableInstance,
        },
        {
          title: 'Write sensitivie String',
          description:
          'Write  senstive data into the sandbox using SharedPreferences.',
          nativeFunction: StorageSharedPreferences.putSensitiveString,
        },
        {
          title: 'Write sensitivie StringSet',
          description:
          'Write a stringset of senstive data into the sandbox using SharedPreferences.',
          nativeFunction: StorageSharedPreferences.putSensitiveStringSet,
        },
      ],
    },
    {
      title: 'DataStore',
      maswe: '0006',
      description:
        'This testcase stores sensitivie data using the DataStore. Data stored this way is not encrypted in the sandbox. An attacker may therefore be able to retrieve the data.',
      testCases: [
        {
          title: 'Write sensitive String to Preferences DataStore',
          description:
            'Write sensitive String into the sandbox using Preferences DataStore',
          nativeFunction: StorageDataStore.writeStringPreferenceDataStore,
        },
        {
          title: 'Write sensitive StringSet to Preferences DataStore',
          description:
            'Write StringSet into the sandbox using Preferences DataStore',
          nativeFunction: StorageDataStore.writeStringSetPreferenceDataStore,
        },
        {
          title: 'Write sensitivie Data to Proto DataStore',
          description:
            'Write sensitive data into the sandbox using Proto DataStore',
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
          title: 'Write sensitive Data into Sandbox (MODE_PRIVATE)',
          maswe: '006',
          description:
            'Open a private file associated with this Context\'s application package for writing. Creates the file if it doesn\'t already exist. ',
          nativeFunction: StorageJavaFileIo.writeSensitiveFileSandbox,
        },
        {
          title: 'Write sensitive, Data into Sandbox (MODE_WORLD_READABLE)',
          maswe: '002',
          description: 'Tries to write senstive, world readable data into the sandbox.',
          nativeFunction: StorageJavaFileIo.writeSandboxWorldReadable,
        },
        {
          title: 'Write sensitive, Data into Sandbox (MODE_WORLD_WRITABLE)',
          maswe: '002',
          description: 'Tries to write senstive, world writable data into the sandbox.',
          nativeFunction: StorageJavaFileIo.writeSandboxWorldWritable,
        },
        {
          title: 'Write sensitive Data into external App Storage',
          maswe: '002',
          description: 'Tries to write sensitive data into a file outside of the sandbox. This file is not accessible by other apps, but can be exported as access to the external storage is possible by USB for example.',
          nativeFunction: StorageJavaFileIo.writeExternalAppContext,
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
          description: 'Creates a plain text SQLite DB within the app sandbox.',
          nativeFunction: StorageSQLite.createSQLiteDB,
        },
        {
          title: 'Store sensitive Data using execSQL and stored procedures',
          description: 'Writes sensitve data to the SQLite DB using execSQL with a prepared statement.',
          nativeFunction: StorageSQLite.execSensitiveSQLStoredProcedures,
        },
        {
          title: 'Store sensitive Data using insert',
          nativeFunction: StorageSQLite.insertSensitive,
        },
        {
          title: 'Store sensitive Data using replace',
          nativeFunction: StorageSQLite.replaceSensitive,
        },
        {
          title: 'Store sensitive Data using update',
          nativeFunction: StorageSQLite.updateSensitive,
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
          title: 'Initialise Room Database',
          description: 'Initialise a Room Database which stores data unencrypted within the sandbox.',
          nativeFunction: StorageRoomDatabase.initRoomDb,
        },
        {
          title: 'Write sensitivie Data to Room Database',
          description: 'Writes sensitive data as a simple datastrucutre to the Room DB',
          nativeFunction: StorageRoomDatabase.writeToRoomDb,
        },
      ],
    },
    {
      title: 'MediaStore API',
      description:
        'To provide a more enriched user experience, many apps let users contribute and access media that\'s available on an external storage volume. These files remain outside the apps sandbox and may therefore be accessed from other apps.',
      testCases: [
        {
          title: 'Write sensitivie Data to external Document Storage',
          description: 'Writes a Document to the Document Folder in the external storage',
          nativeFunction: StorageMediaStoreAPI.writeDocument,
        },
      ],
    },
  ],
  CRYPTO: [
    {
      title: 'KeyGenParameterSpec',
      description:
        'This class represents properties for cryptographic keys. The are used when creating keys within the Android KeyStore for example.',
      testCases: [
        {
          title: 'Set Attesation Challenge',
          description:
            'Sets whether an attestation certificate will be generated for this key pair, and what challenge value will be placed in the certificate. The attestation certificate chain can be retrieved with with KeyStore.getCertificateChain(String)',
          nativeFunction: CryptoKeyGenParameterSpec.setAttestationChallenge,
        },
        {
          title: 'Set insecure Block Modes',
          description:
            'Sets insecure block modes with which the key can be used when encrypting/decrypting.',
          nativeFunction: CryptoKeyGenParameterSpec.setInsecureBlockMode,
        },
        {
          title: 'Set insecure Digests',
          description:
            'Sets insecure digests algorithms with which the key can be used.',
          nativeFunction: CryptoKeyGenParameterSpec.setInsecureDigest,
        },
        {
          title: 'Set insecure Encryptoion Padding',
          description:
            'Sets insecure padding schemes (e.g., PKCS7Padding, OAEPPadding, PKCS1Padding, NoPadding) with which the key can be used when encrypting/decrypting.',
          nativeFunction: CryptoKeyGenParameterSpec.setInsecureEncryptionPadding,
        },
        {
          title: 'Set insecure Signature Padding',
          description:
            'Sets insecure padding schemes (e.g., PKCS7Padding, OAEPPadding, PKCS1Padding, NoPadding) with which the key can be used when signing.',
          nativeFunction: CryptoKeyGenParameterSpec.setInsecureSignaturePadding,
        },
        {
          title: 'Set weak Key Size',
          description:
            'Sets a weak size (in bits) of the key to be generated. ',
          nativeFunction: CryptoKeyGenParameterSpec.setWeakKey,
        },
        {
          title: 'Set long Certificate Validity',
          description:
            'Sets a long end of the validity period for the certificate of the generated key pair.',
          nativeFunction: CryptoKeyGenParameterSpec.setLongCertValidity,
        },
        {
          title: 'Set long Key Validity',
          description:
            'Sets a long end of the validity period for the keyr.',
          nativeFunction: CryptoKeyGenParameterSpec.setLongKeyValidity,
        },
        {
          title: 'Don\'t invalidate at Biometric Enrollment ',
          description:
            'By default, the key is invalidated when a new biometric pattern is enrolled, this settings bypasses this.',
          nativeFunction: CryptoKeyGenParameterSpec.dontInvalidateBioEnrollment,
        },
        {
          title: 'Set to Exportable',
          description:
            'Sets whether this key should be protected by a StrongBox security chip to False.',
          nativeFunction: CryptoKeyGenParameterSpec.setExportable,
        },
        {
          title: 'Don\'t require randomized encryption',
          description:
            'Sets whether encryption using this key must be sufficiently randomized to produce different ciphertexts for the same plaintext every time to False.',
          nativeFunction: CryptoKeyGenParameterSpec.setRandomizedEncryptionRequiredFalse,
        },
        {
          title: 'Don\'t require unlocked device',
          description:
            'Sets this key is authorized to be used only while the device is unlocked to false',
          nativeFunction: CryptoKeyGenParameterSpec.setUnlockedDeviceRequiredFalse,
        },
        {
          title: 'Don\'t require user authentication',
          description:
            'Sets this key is authorized to be used only if the user has been authenticated to false',
          nativeFunction: CryptoKeyGenParameterSpec.setUserAuthenticationRequiredFalse,
        },
      ],
    },
    {
      title: 'KeyInfo',
      description:
        'KeyInfo Object can be used to verify key attributes. ',
      testCases: [
        {
          title: 'Get Security Level',
          description:
            'Get information about where a Key is stored.',
          nativeFunction: CryptoKeyInfo.getSecurityLevel,
        },
        {
          title: 'Use isInsideSecureHardware (deprecated)',
          description:
            'Get information about where the key is stored.',
          nativeFunction: CryptoKeyInfo.isInsideSecureHardware,
        },
      ],
    },   
    {
      title: 'SecretKeyFactory',
      description:
        'Key factories are used to convert keys (opaque cryptographic keys of type Key) into key specifications (transparent representations of the underlying key material), and vice versa.',
      testCases: [
        {
          title: 'Use Insecure SecretKeyFactory Algorithms',
          description: 'Use DES, DESede and PBEWithMD5AndDES as Algorithm.',
          nativeFunction: CryptoSecretKeyFactory.insecureSecretKeyFactoryAlgorithms,
        },
        {
          title: 'Use Low-Iteration PBKDF2',
          maswe: '0010',
          description: 'Use insecure number of iterations when deriving a password using PBKDF2.',
          nativeFunction: CryptoSecretKeyFactory.lowIterationPBKDF2,
        },
      ],
    },
    {
      title: 'Key Attestation',
      description:
        'Key Attestation gives you more confidence that the keys you use in your app are stored in a device\'s hardware-backed keystore. The following sections describe how to verify the properties of hardware-backed keys and how to interpret the attestation certificates\' extension data.',
      testCases: [
        {
          title: 'Get Certificate Chain',
          description:
            'Use a KeyStore object\'s getCertificateChain() method to get a reference to the chain of X.509 certificates associated with the hardware-backed keystore.',
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
          title: 'Password-Based Cipher with Low Iteration Count',
          description:
            'Initializes an Cipher with PBE with 1 iteration when creating the Cipher Spec.',
          nativeFunction: CryptoCipher.pbeCipherLowIteration,
        },
        {
          title: 'Password-Based Ciphers with Zero-IV',
          description:
            'Password-based encryption (PBE) ciphers that require an initialization vector (IV) can obtain it from the key, if it\'s suitably constructed, or from an explicitly passed IV. If you pass a PBE key that doesn\'t contain an IV and don\'t pass an explicit IV, the PBE ciphers on Android currently assume an IV of zero.',
          nativeFunction: CryptoCipher.pbeCipherZeroIV,
        },
        {
          title: 'Null-IV',
          maswe:'0022',
          description:
            'The use of predictable IVs (hardcoded, null, reused) in a security sensitive context can weaken data encryption strength and potentially compromise confidentiality.',
          nativeFunction: CryptoCipher.nullIv,
        },
      ],
    },
    {
      title: 'Random Numbers',
      description:
        'Identify all the instances of random number generators and look for either custom or well-known insecure classes. These tests generate random number in different ways.',
      testCases: [
        {
          title: 'Create Insecure Java Random',
          description: 'Creates a new random number generator.',
          nativeFunction: CryptoRandom.insecureRandom,
        },
        {
          title: 'Create Insecure Kotlin Random',
          description: 'Creates a new random number generator.',
          nativeFunction: CryptoRandom.insecureRandom,
        },
        {
          title: 'Create Insecure Java Random with Seed',
          description:
            'Creates a new random number generator using a single long seed.',
          nativeFunction: CryptoRandom.insecureRandomSeed,
        },
        {
          title: 'Create Insecure Kotlin Random with Seed',
          description:
            'Creates a new random number generator using a single long seed.',
          nativeFunction: CryptoRandom.insecureRandomSeed,
        },
        {
          title: 'Create Deprecated SecureRandom.',
          description:
            'Constructs a secure random number generator using a getInstance().',
          nativeFunction: CryptoRandom.secureRandomInstance,
        },
      ],
    },
    {
      title: 'Deprected Implementation',
      description:
        'The usage of deprecated cryptographic implementation can intoduce risks.',
      testCases: [
        {
          title: 'Load BouncyCastle using SecurityProvider',
          description:
            'BouncyCastle (or SpongyCastle) is deprecated and shoudl be avoided.',
          nativeFunction: CryptoDeprecated.bouncyCastleProvider,
        },
        {
          title: 'Direct usage of BouncyCastle',
          description:
            'BouncyCastle (or SpongyCastle) is deprecated and shoudl be avoided.',
          nativeFunction: CryptoDeprecated.bouncyCastleDirect,
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
        'A class that manages a system-provided biometric prompt. On devices running Android 9.0 (API 28) and above, this will show a system-provided authentication prompt, using one of the device\'s supported biometric modalities (fingerprint, iris, face, etc). Prior to Android 9.0, this will instead show a custom fingerprint authentication dialog. The prompt will persist across configuration changes unless explicitly canceled. For security reasons, the prompt will be dismissed when the client application is no longer in the foreground.',
      testCases: [
        {
          title: 'Create a Simple Biometry Prompt',
          description: 'Simply requires biometric authentication. This can method of authentcation can usually be bypassed easily.',
          nativeFunction: AuthBiometricPrompt.simplePrompt,
        },
        {
          title: 'Create a Biometry Prompt to access a CryptoObject',
          description: 'Access a crypto object using biometry.',
          nativeFunction: AuthBiometricPrompt.cryptoOperationPrompt,
        },
        {
          title: 'Create Device PIN Only Prompt',
          description: 'Create a Prompt only for PINs. This can method of authentcation can usually be bypassed easily.',
          nativeFunction: AuthBiometricPrompt.devicePinOnlyPrompt,
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
          description: 'Ask the device if fingerprint hardware is available.',
          nativeFunction: AuthFingerprintManager.isHardwareDetected,
        },
        {
          title: 'Query Fingerprints',
          description: 'Ask the device if fingerprints are enrolled.',
          nativeFunction: AuthFingerprintManager.hasEnrolledFingerprints,
        },
        {
          title: 'Simple Authenticate',
          description: 'Does a simple fingerpint auht. Not considered secure because it is easily bypassable. Better use biomentry protected CryptoObjects. ',
          nativeFunction: AuthFingerprintManager.authenticateSimple,
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
          description: 'Tries to disable Keyguard Lock.',
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
        'Android Protected Confirmation doesn\'t provide a secure information channel for the user. Data shown here should not be more sensitivie than shown in other places within the app.',
      testCases: [
        {
          title: 'Create Protected Confirmation',
          description: '',
          nativeFunction: AuthProtectedConfirmation.create,
        },
      ],
    },
    {
      title: 'HTTP Basic Authentication',
      description:
        'The usage of HTTP Basic Authentication is not recommended anymore, since it comes with some inherint security flaws.',
      testCases: [
        {
          title: 'java.net',
          description: 'Using java.net with HTTP Basic Authentication.',
          nativeFunction: AuthHttpBasicAuth.javaNet,
        },
        {
          title: 'WebView',
          description: 'Using WebView with HTTP Basic Authentication.',
          nativeFunction: AuthHttpBasicAuth.webView,
        },
      ],
    },
  ],
  NETWORK: [
    {
      title: 'TLS Pinning',
      description:
        'By hardcoding or \'pinning\' specific TLS certificates or public keys within the application\'s code, TLS pinning ensures that only trusted servers are communicated with, preventing attackers from intercepting and tampering with sensitive data exchanged between the mobile app and the server.  These tests implement this.',
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
        'By hardcoding or \'pinning\' specific TLS certificates or public keys within the application\'s code, TLS pinning ensures that only trusted servers are communicated with, preventing attackers from intercepting and tampering with sensitive data exchanged between the mobile app and the server.  These tests implement this.',
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
        {
          title: 'Listen on Localhost',
          description: '',
          nativeFunction: PlatformIpc.listenLocalhost,
        },
        {
          title: 'Send Data to Localhost',
          description: '',
          nativeFunction: PlatformIpc.sendLocalhost,
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
        {
          title: 'Enabel Remote Web Content Debugging',
          description: '',
          nativeFunction: PlatformWebView.remoteDebugging,
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
          nativeFunction: PlatformUiDisclosure.passwordPlaintextUi,
        },
        {
          title: 'Sensitivie Data in Notifications',
          description:
            'This test uses noitifications to display sensitive data.',
          nativeFunction: PlatformUiDisclosure.sensitiveDataNotifications,
        },
        {
          title: 'Prohibit Screenshot',
          description:
            'This test creates an activity of which the user cannot take a screenshot.',
          nativeFunction: PlatformUiDisclosure.prohibitScreenshot,
        },
      ],
    },
    // Define your Android-specific platform-related test cases here
  ],
  CODE: [
    // Define your Android-specific code-related test cases here
    {
      title: 'Current Software Version',
      description:
        'Check if the app enforces updates e.g. via AppUpdateManager on Android. However, the backend would be enforcing this and not only the app locally.',
      testCases: [
        {
          title: 'Check if App Update is availabe',
          description: 'Use AppUpdateManager.getAppUpdateInfo to query, if there is a new version available.',
          nativeFunction: CodeUpdate.checkUpdateAvailable,
        },
        {
          title: 'Check OS Version',
          description: 'Checks the OS SDK Version in order to spot an insecure plattform.',
          nativeFunction: CodeUpdate.checkOs,
        },
      ],
    },
    {
      title: 'Insecure Dependency',
      description:
        'Insecure dependencies can introduce vulnerabilities into the app.',
      testCases: [
        {
          title: 'Vulnerable Native SDK',
          description: 'Test Case which uses insecure native SKD function.',
          nativeFunction: CodeDependencies.vulnerableNativeSDK,
        },
        {
          title: 'Vulnerable Web SDK',
          description: 'Test Case which uses insecure web SKD function.',
          nativeFunction: CodeDependencies.vulnerableWebSDK,
        },
      ],
    },
    {
      title: 'Insecure Software',
      description:
        'These use case implement common software vulnerabilities which put the app at risk.',
      testCases: [
        {
          title: 'SQL Injection',
          description: 'This uses case implements an insecure SQL query with an injection point.',
          nativeFunction: CodeInsecureSoftware.sqlInjection,
        },
        {
          title: 'Protect against XEE ',
          description: 'It is recommended to harden the DocumentBuilder used when parsing XML in order to protect agians possible XEE.',
          nativeFunction: CodeInsecureSoftware.XmlExternalEntity,
        },
        {
          title: 'Insecure Java Deserialisation',
          description: 'This uses case implements an insecure Java Deserialisation.',
          nativeFunction: CodeInsecureSoftware.insecureDeserialisation,
        },
      ],
    },
  ],
  RESILIENCE: [
    {
      title: 'Obfuscation',
      description:
        'Obfuscation can increase the amount of work attackers have to put into reverse-engineering or attacking the app. It therefore may be used alongside other seucre development techniques in order to better protect sensitve parts of the app.',
      testCases: [
        {
          title: 'Obfuscated Android Class',
          nativeFunction: ResiliencObfuscation.obfuscatedAndroidClass,
        },
        {
          title: 'Native Library whit Debug Symbols',
          nativeFunction: ResiliencObfuscation.nativeDebugSymbols,
        },
      ],
    },
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
          title: 'Get Sensor',
          nativeFunction: ResilienceAntiVm.getSensor,
        },
      ],
    },
    {
      title: 'Simple Root Detection',
      description:
        'The app uses common root detection libraries in order to do a simple root detection check.',
      testCases: [
        {
          title: 'RootBeer Check',
          description:
            'Use the RootBeer Library to do the check.',
          nativeFunction: ResilienceRootDetection.rootBeer,
        },
      ],
    },
    // {
    //   title: 'Simple Dynamic Analysis Tools Detection',
    //   description:
    //     'The app uses common Dynamic Analysis Tools Detection detection libraries in order to do a simple check if tools are avilable.',
    //   testCases: [
    //     {
    //       title: 'DetectFrida Check',
    //       description:
    //         'Use the DetectFrida Library to do the check.',
    //       nativeFunction: ResilienceDynamicAnalysisDetechion.detectFrida,
    //     },
    //   ],
    // },
    {
      title: 'Device Integrity Checks',
      description:
        'The app tries to validate the integrity of the platform using OS-API',
      testCases: [
        {
          title: 'Play Integrity API',
          description: 'The app tries to validate the integrity of the platform using Play Integrity API',
          nativeFunction: ResilienceDeviceIntegrity.playIntegrity,
        },
        {
          title: 'SafetyNet API',
          description:
            'The app tries to validate the integrity of the platform using the deprecated SafetyNet API',
            nativeFunction: ResilienceDeviceIntegrity.safetyNet,
        },
      ],
    },
    // {
    //   title: 'FileIntegrityManager',
    //   description:
    //     'This class provides access to file integrity related operations.',
    //   testCases: [
    //     {
    //       title: 'setupFsVerity',
    //       description:
    //         'Enables fs-verity to the owned file under the calling app\'s private directory. It always uses the common configuration, i.e. SHA-256 digest algorithm, 4K block size, and without salt. ',
    //       nativeFunction: ResilienceFileIntegrityManager.setupFsVerify,
    //     },
    //   ],
    // },
  ],
  PRIVACY: [    
    {
      title: 'Advertisemnt',
      description: 'User tracking in mobile applications involves collecting and analyzing data to monitor user behavior, preferences, and movements. This enables companies to recognize and follow users over time and across different apps, devices, and services. Such tracking often occurs without the user\'s explicit knowledge or consent, leading to significant privacy concerns.',
      testCases: [
        {
          title: 'Get AAID',
          nativeFunction: PrivacyMarketingUUID.getAAID,
        },
        {
          title: 'Get Android ID',
          nativeFunction: PrivacyMarketingUUID.getAndroidID,
        },
        {
          title: 'Get IMEI',
          nativeFunction: PrivacyMarketingUUID.getImei,
        },
        {
          title: 'Get MAC Address',
          nativeFunction: PrivacyMarketingUUID.getMAC,
        },
      ],
    },
  ],
};

export default androidTestCases;
