import {NativeModules} from 'react-native';
import {TestGroup} from '../appContent';
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
  CryptoJava,
  CryptoRandomJava,
  CryptoRandomKotlin,
  CryptoKeyGenParameterSpec,
  CryptoSecretKeyFactory,
  CryptoDeprecated,
  CryptoKeyInfo,
  CryptoEncryptedSharedPreferences,

  AuthBiometricManager,
  AuthBiometricPrompt,
  AuthFingerprintManager,
  AuthKeyguardManager,
  AuthProtectedConfirmation,
  AuthHttpBasicAuth,

  NetworkTlsPinning,
  NetworkLocalNetwork,
  NetworkTlsClientConfig,

  PlatformWebView,
  PlatformIpc,
  PlatformUiDisclosure,

  CodeUpdate,
  CodeDependencies,
  CodeInsecureSoftware,

  ResilienceAntiDebug,
  ResilienceAntiVm,
  ResilienceObfuscation,
  ResilienceDeviceIntegrity,
  ResilienceRootDetection,

  PrivacyMarketingUUID,

} = NativeModules;

interface Dictionary<Type> {
  [key: string]: Type;
}

export var androidTestCases: Dictionary<TestGroup[]> = {
  STORAGE: [
    {
      title: 'SharedPreferences',
      maswe: '0006',
      description:
        'These testcase stores sensitive data using unencrypted SharedPreferences.',
      testCases: [
        {
          title: 'Init WORLD_READABLE',
          description: 'Initializes a world readable SharedPreferenceInstance. This mode is deprecated on modern versions of Android.',
          nativeFunction: StorageSharedPreferences.getWorldReadableInstance,
        },
        {
          title: 'Init WORLD_WRITABLE',
          description: 'Initializes an world writable SharedPreferenceInstance. This mode is deprecated on modern versions of Android.',
          nativeFunction: StorageSharedPreferences.getWorldWritableInstance,
        },
        {
          title: 'Write String',
          description:
          'Write sensitive data as String into the sandbox using SharedPreferences.',
          nativeFunction: StorageSharedPreferences.putSensitiveString,
        },
        {
          title: 'Write StringSet',
          description:
          'Write sensitive data as StringSet into the sandbox using SharedPreferences.',
          nativeFunction: StorageSharedPreferences.putSensitiveStringSet,
        },
      ],
    },
    {
      title: 'DataStore',
      maswe: '0006',
      description:
        'This testcase stores sensitive data using the DataStore. Data stored this way is not encrypted in the sandbox. An attacker may therefore be able to retrieve the data.',
      testCases: [
        {
          title: 'Write String to Preferences DataStore',
          description:
            'Write sensitive data as String using Preferences DataStore.',
          nativeFunction: StorageDataStore.writeStringPreferenceDataStore,
        },
        {
          title: 'Write StringSet to Preferences DataStore',
          description:
            'Write sensitive data as StringSet using Preferences DataStore.',
          nativeFunction: StorageDataStore.writeStringSetPreferenceDataStore,
        },
        {
          title: 'Write Proto DataStore',
          description:
            'Write sensitive data using Proto DataStore. The data is a simple User-Object with some String data.',
          nativeFunction: StorageDataStoreProto.writeProtoDataStore,
        },
      ],
    },
    {
      title: 'Java File API',
      description:
        'Developers can choose to use the java.io.File API directly. Using this class, it is possible to store data in within the sandbox, or the sandboxed external storage. However, these files are not encrypted by default. Developers should keep that in mind, wen using the Java File API directly.',
      testCases: [
        {
          title: 'Write MODE_PRIVATE',
          maswe: '006',
          description:
            'Creates a file within the sandbox using the java.io.File API. The file is MODE_PRIVATE which limits the accessibility only to the app. This file is not accessible by other apps, but can be exported as access to the external storage is possible by the USB interface for example.',
          nativeFunction: StorageJavaFileIo.writeSensitiveFileSandbox,
        },
        {
          title: 'Write MODE_WORLD_READABLE',
          maswe: '002',
          description: 'Creates a file within the sandbox using the java.io.File API. The file is MODE_WORLD_READABLE. This mode is deprecated on newer versions of Android. ',
          nativeFunction: StorageJavaFileIo.writeSandboxWorldReadable,
        },
        {
          title: 'Write MODE_WORLD_WRITABLE',
          maswe: '002',
          description: 'Creates a file within the sandbox using the java.io.File API. The file is MODE_WORLD_WRITABLE. This mode is deprecated on newer versions of Android. ',
          nativeFunction: StorageJavaFileIo.writeSandboxWorldWritable,
        },
        {
          title: 'Write to External App Storage',
          maswe: '002',
          description: 'Tries to write sensitive data into a file outside of the sandbox. This file is not accessible by other apps, but it can be exported using the USB interface or by physically removing the external SD-Card.',
          nativeFunction: StorageJavaFileIo.writeExternalAppContext,
        },
      ],
    },
    {
      title: 'Querying External Storage',
      maswe: '0002',
      description:
        'These test cases write data into the external storage (ExternalFilesDir and ExternalCacheDir). An app can use these locations, if the internal storage is full for example. They are still sandboxed form other applications. However, they could be accessed, using USB or by removing a physical SD-Card. These files are not encrypted by default.',
      testCases: [
        {
          title: 'Check State of External Storage',
          description:
            'Returns the current state of the primary shared/external storage media.',
          nativeFunction: StorageExternalStorage.checkState,
        },
        {
          title: 'Get External Root Location',
          description:
            'Uses getExternalFilesDir. The function returns the absolute path to the directory on the primary shared/external storage device where the application can place persistent files it owns.',
          nativeFunction: StorageExternalStorage.getExternalFilesDirRoot,
        },
        {
          title: 'Get External File Locations',
          description:
            'Uses getExternalFilesDir in order to get different types of external locations such as Environment.DIRECTORY_MUSIC,  Environment.DIRECTORY_PODCASTS or Environment.DIRECTORY_PICTURES.',
          nativeFunction: StorageExternalStorage.getDifferentExternalDirs,
        },
        {
          title: 'Get External Cache Location',
          description:
            'Uses getExternalCacheDir. The function returns absolute path to application-specific directory on the primary shared/external storage device where the application can place cache files it owns.',
          nativeFunction: StorageExternalStorage.getExternalCacheDir,
        },
      ],
    },
    {
      title: 'SQLite Database',
      maswe: '006',
      description:
        'SQLite Database is a simple, local SQL-Database. The data is stored in plain text within the sandbox by default. Hence, the developer must take care of protecting sensitive data in necessary.',
      testCases: [
        {
          title: 'Create SQLite DB',
          description: 'Creates a plain text SQLite DB within the app sandbox.',
          nativeFunction: StorageSQLite.createSQLiteDB,
        },
        {
          title: 'Use execSQL',
          description: 'Writes sensitive data to an existing SQLite database using execSQL() with a prepared statement.',
          nativeFunction: StorageSQLite.execSensitiveSQLStoredProcedures,
        },
        {
          title: 'Use insert',
          description: 'Inserts sensitive data into an existing SQLite database using insert().',
          nativeFunction: StorageSQLite.insertSensitive,
        },
        {
          title: 'Use replace',
          description: 'Replaces data with sensitive data of an existing SQLite database using replace().',
          nativeFunction: StorageSQLite.replaceSensitive,
        },
        {
          title: 'Use update',
          description: 'Updates data with sensitive data of an existing SQLite database using update().',
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
          title: 'Initialize Room Database',
          description: 'Initialize a Room database which stores data unencrypted within the sandbox.',
          nativeFunction: StorageRoomDatabase.initRoomDb,
        },
        {
          title: 'Write Data to Room Database',
          description: 'Writes sensitive data in the form of a simple User-DAO data structure to the Room database.',
          nativeFunction: StorageRoomDatabase.writeToRoomDb,
        },
      ],
    },
    {
      title: 'MediaStore API',
      maswe: '0007',
      description:
        'To provide a more enriched user experience, many apps let users contribute and access media that\'s available on an external storage volume. These files remain outside the apps sandbox, are world read- and writable and may therefore be accessed from other apps.',
      testCases: [
        {
          title: 'Write Data to Public Storage',
          description: 'Writes text file to the document folder in the world writable external storage.',
          nativeFunction: StorageMediaStoreAPI.writeDocument,
        },
        {
          title: 'Read Data from Public Storage',
          description: 'First writes a file into the world readable external storage. Then tries to access it again.',
          nativeFunction: StorageMediaStoreAPI.readDownload,
        },
      ],
    },
  ],
  CRYPTO: [
    {
      title: 'KeyGenParameterSpec',
      description:
        'This class represents properties for cryptographic keys. It is used when creating keys within the Android KeyStore for example. Since the security of the cryptography is also depending on secure key properties, a misconfigured KeyGenParameterSpec may be the source of a it security risk.',
      testCases: [
        {
          title: 'Set Attestation Challenge',
          description:
            'Sets whether an attestation certificate will be generated for this key pair, and what challenge value will be placed in the certificate.',
          nativeFunction: CryptoKeyGenParameterSpec.setAttestationChallenge,
        },
        {
          title: 'Set Insecure Block Modes',
          description:
            'Sets insecure block modes with which the key can be used when encrypting/decrypting.',
          nativeFunction: CryptoKeyGenParameterSpec.setInsecureBlockMode,
        },
        {
          title: 'Set Insecure Digests',
          description:
            'Sets insecure digests algorithms with which the key can be used.',
          nativeFunction: CryptoKeyGenParameterSpec.setInsecureDigest,
        },
        {
          title: 'Set Insecure Encryption Padding',
          description:
            'Sets insecure padding schemes (e.g., PKCS7Padding, OAEPPadding, PKCS1Padding, NoPadding) with which the key can be used when encrypting/decrypting.',
          nativeFunction: CryptoKeyGenParameterSpec.setInsecureEncryptionPadding,
        },
        {
          title: 'Set Insecure Signature Padding',
          description:
            'Sets insecure padding schemes (e.g., PKCS7Padding, OAEPPadding, PKCS1Padding, NoPadding) with which the key can be used when signing.',
          nativeFunction: CryptoKeyGenParameterSpec.setInsecureSignaturePadding,
        },
        {
          title: 'Set Weak Key Size',
          maswe: '0009',
          description:
            'Sets a weak size (in bits) of the key to be generated. ',
          nativeFunction: CryptoKeyGenParameterSpec.setWeakKey,
        },
        {
          title: 'Set Long Certificate Validity',
          description:
            'Sets a long end of the validity period for the certificate of the generated key pair.',
          nativeFunction: CryptoKeyGenParameterSpec.setLongCertValidity,
        },
        {
          title: 'Set Long Key Validity',
          description:
            'Sets a long end of the validity period for the key.',
          nativeFunction: CryptoKeyGenParameterSpec.setLongKeyValidity,
        },
        {
          title: 'Don\'t Invalidate at Biometric Enrollment ',
          description:
            'By default, the key is invalidated when a new biometric pattern is enrolled. If this key property is set to false, the key remains valid in this case.',
          nativeFunction: CryptoKeyGenParameterSpec.dontInvalidateBioEnrollment,
        },
        {
          title: 'Set to Exportable',
          maswe: '0014',
          description:
            'Forces the key not to be stored in the StrongBox HSM using setIsStrongBoxBacked(false). Such a key can now be accessed form within the application.',
          nativeFunction: CryptoKeyGenParameterSpec.setExportable,
        },
        {
          title: 'Don\'t Require Randomized Encryption',
          description:
            'Sets whether encryption using this key must be sufficiently randomized to produce different cipher texts for the same plaintext every time to false.',
          nativeFunction: CryptoKeyGenParameterSpec.setRandomizedEncryptionRequiredFalse,
        },
        {
          title: 'Require Unlocked Device',
          description:
            'By default, the setUnlockedDeviceRequired is set to false. By changing it to true, the key can only be accessed if the device is unlocked.',
          nativeFunction: CryptoKeyGenParameterSpec.setUnlockedDeviceRequiredTrue,
        },
        {
          title: 'Require User Authentication',
          maswe: '0018',
          description:
            'By default, the setUserAuthenticationRequired is set to false. By changing it to true, the user is authenticated by Android before the key can be accessed.',
          nativeFunction: CryptoKeyGenParameterSpec.requireUserAuthentication,
        },
        {
          title: 'Configure User Authorization',
          maswe: '0018',
          description:
            'By default, there is no timeout on how long a key can be accessed if user authentication is required. This use case configures this using setUserAuthenticationParameters (int timeout, int type).',
          nativeFunction: CryptoKeyGenParameterSpec.configureUserAuth,
        },
        {
          title: 'Configure User Authorization (legacy)',
          maswe: '0018',
          description:
            'By default, there is no timeout on how long a key can be accessed if user authentication is required. This use case configures this using setUserAuthenticationValidityDurationSeconds (int seconds).',
          nativeFunction: CryptoKeyGenParameterSpec.configureUserAuthLegacy,
        },
      ],
    },
    {
      title: 'KeyInfo',
      description:
        'Android devices can be very different in features with an impact on the security. The can for example have a Trusted Execution Environment' +
        '(TEE), a StrongBox HSM-Chip or none of them. This KeyInfo can be used to verify the properties of the key. Developers should use them in order to be informed about the security of the key material.',
      testCases: [
        {
          title: 'Get Security Level',
          description:
            'Depending on the device, the key can be stored in software, the TEE or the StrongBox HSM. This use case gets information about where a Key is stored using getSecurityLevel().',
          nativeFunction: CryptoKeyInfo.getSecurityLevel,
        },
        {
          title: 'Verify if Key is in Secure Hardware (deprecated)',
          description:
            'Depending on the device, the key can be stored in software, the TEE or the StrongBox HSM. This use case gets information about where a Key is stored using isInsideSecureHardware().',
          nativeFunction: CryptoKeyInfo.isInsideSecureHardware,
        },
      ],
    },
    {
      title: 'SecretKeyFactory',
      description:
        'SecretKeyFactory is commonly used to derive a cryptographic key from a user-provided password using algorithms like PBKDF2 (Password-Based Key Derivation Function 2) or when using custom cryptography which does not rely on the Android KeyStore System. This by itself can be a risk, as this key material may be stored more insecurely compared to keys within the KeyStore as they can be stored in dedicated hardware (TEE or StrongBox HSM).',
      testCases: [
        {
          title: 'Weak Algorithms',
          description: 'Create a SecretKeyFactory for weak algorithms such as DES, RC4, SHA1 etc.',
          nativeFunction: CryptoSecretKeyFactory.weakSecretKeyFactoryAlgorithms,
        },
        {
          title: 'Use Low-Iteration PBKDF2',
          maswe: '0010',
          description: 'Creates a key which is stretched using PBKDF2 with an low amount of iterations.',
          nativeFunction: CryptoSecretKeyFactory.lowIterationPBKDF2,
        },
      ],
    },
    {
      title: 'Key Attestation',
      description:
        'Key attestation gives you more confidence that the keys you use in your app are stored in a device\'s hardware-backed keystore.',
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
      title: 'Java Cryptography API',
      description:
        'Android can access cryptographic libraries provided by the Java Runtime Environment. They are for example javax.crypto.Cipher or java.security.Signature. These classes can be used to encrypt, and sign data. When using Java libraries, it is important to use secure parameters.',
      testCases: [
        {
          title: 'Init Weak KeyGenerators',
          description:
            'Creates KeyGenerators for weak Algorithms such as AES/ECB or RC4.',
          nativeFunction: CryptoJava.initInsecureKeyGenerators,
        },
        {
          title: 'Init Weak KeyPairGenerators',
          description:
            'Creates KeyPairGenerators with insecure properties such as weak key sizes.',
          nativeFunction: CryptoJava.initInsecureKeyPairGenerators,
        },
        {
          title: 'Init Weak Ciphers',
          description: 'Initializes insecure ciphers, such as AES/ECB or RC4.',
          nativeFunction: CryptoJava.initInsecureCiphers,
        },
        {
          title: 'Init Weak Signatures',
          description: 'Initializes insecure Signatures.',
          nativeFunction: CryptoJava.initInsecureSignatures,
        },
        {
          title: 'Deprecated Bouncy Castle Security Provider',
          description: 'Initializes an Cipher object with deprecated Bouncy Castle Security Provider.',
          nativeFunction: CryptoJava.bouncyCastle,
        },
        {
          title: 'Password-Based Ciphers with Zero-IV',
          description:
            'Password-based encryption (PBE) ciphers that require an initialization vector (IV) can obtain it from the key, if it\'s suitably constructed, or from an explicitly passed IV. This use case creates a Cipher object for a PBE cipher which does not explicity state the IV. This means, a null IV is used implicitly.',
          nativeFunction: CryptoJava.pbeCipherZeroIV,
        },
        {
          title: 'Null-IV',
          maswe:'0022',
          description:
            'The use of predictable IVs (hardcoded, null, reused) in a security sensitive context can weaken data encryption strength and potentially compromise confidentiality.',
          nativeFunction: CryptoJava.nullIv,
        },
      ],
    },
    {
      title: 'Random Numbers',
      description:
        'The quality of random numbers are essential when using them in cryptographic operations. These test use weak algorithms to create such numbers.',
      testCases: [
        {
          title: 'Weak Java Random',
          description: 'Creates a random number using the non-blocking java.util.Random class. These numbers should not be used for cryptographic operations.',
          nativeFunction: CryptoRandomJava.insecureRandom,
        },
        {
          title: 'Weak Java Random with Seed',
          description:
            'Creates a random number using the non-blocking java.util.Random class and a seed. These numbers should not be used for cryptographic operations.',
          nativeFunction: CryptoRandomJava.insecureRandomSeed,
        },
        {
          title: 'Secure Java Random',
          description:
            'Creates a random number using the blocking java.util.SecureRandom class and a seed. Two generators with the same seed will therefore not produce the same numbers.',
          nativeFunction: CryptoRandomJava.secureRandom,
        },
        {
          title: 'Secure Java Random with Seed',
          description:
            'Creates a random number using the blocking java.util.SecureRandom class and a seed. The seed is used to add entropy to the generator. Two generators with the same seed will therefore not produce the same numbers.',
          nativeFunction: CryptoRandomJava.secureRandomSeed,
        },
        {
          title: 'Weak Kotlin Random',
          description: 'Creates a random number using the non-blocking kotlin.random.Random class. This class uses the java.util.Random class internally. These numbers should not be used for cryptographic operations.',
          nativeFunction: CryptoRandomKotlin.insecureRandom,
        },
        {
          title: 'Weak Kotlin Random with Seed',
          description:
            'Creates a random number using the non-blocking kotlin.random.Random class and a seed. This class uses the java.util.Random class internally. These numbers should not be used for cryptographic operations.',
          nativeFunction: CryptoRandomKotlin.insecureSeed,
        },
        {
          title: 'Deprecated SecureRandom',
          description:
            'Constructs a secure random number generator using a getInstance().',
          nativeFunction: CryptoRandomJava.secureRandomInstance,
        },
      ],
    },
    {
      title: 'Usage of Deprecated Cryptography',
      description:
        'Android provides a fair amount of freedom when choosing cryptographic primitives and their implementation. These use cases implement the usage of BouncyCastle as one of the more common cryptographic library which should not be used anymore.',
      testCases: [
        {
          title: 'Enumerate SecurityProvider',
          description:
            'This use case enumerates the available SecurityProviders. This in it self is not a risk, but it can be a hint that the app does not use the recommended default SecurityProvider.',
          nativeFunction: CryptoDeprecated.enumerateSecurityProviders,
        },
        {
          title: 'Load BouncyCastle as SecurityProvider',
          description:
            'This test adds BouncyCastle as SecurityProvider. While this is not an issue in any case, it is not recommended to use it aside form specific purposes.',
          nativeFunction: CryptoDeprecated.bouncyCastleProvider,
        },
        {
          title: 'Use BouncyCastle Directly',
          description:
            'In this use case, BouncyCastle is used directly in order to encrypt data. While this is not an issue in any case, it is not recommended to use it aside form specific purposes.',
          nativeFunction: CryptoDeprecated.bouncyCastleDirect,
        },
      ],
    },
    {
      title: 'Encrypted Shared Preferences',
      description:
        'Encrypted Shared Preferences are Shared Preferences which are encrypted using the Android KeyStore.',
      testCases: [
        {
          title: 'Write String',
          description:
          'Write sensitive data as String into the sandbox using EncryptedSharedPreferences.',
          nativeFunction: CryptoEncryptedSharedPreferences.putString,
        },
        {
          title: 'Write StringSet',
          description:
          'Write sensitive data as StringSet into the sandbox using EncryptedSharedPreferences.',
          nativeFunction: CryptoEncryptedSharedPreferences.putStringSet,
        },
      ],
    },
  ],
  AUTH: [
    {
      title: 'BiometricManager',
      description:
        'A class that provides system information related to biometrics (e.g. fingerprint, biometry, etc.). ',
      testCases: [
        {
          title: 'Query Strong Biometry Authentication',
          description: 'Uses canAuthenticate() to query if the device can authenticate using the Class 3 method (formerly Strong), as defined by the Android CDD.',
          nativeFunction: AuthBiometricManager.testStrongAuth,
        },
        {
          title: 'Query Weak Biometry  Authentication',
          description: 'Uses canAuthenticate() to query if the device can authenticate using the Class method 2 (formerly Weak), as defined by the Android CDD.',
          nativeFunction: AuthBiometricManager.testWeakAuth,
        },
        {
          title: 'Query Non-Biometry Authentication',
          description: 'Uses canAuthenticate() to query if the device can authenticate using passwords, pins or patterns.',
          nativeFunction: AuthBiometricManager.testDeviceCredentialsAuth,
        },
      ],
    },
    {
      title: 'BiometricPrompt',
      description:
        'A class that manages a system-provided biometric prompt. The prompt can be used for a simple true/false authentication. This is considered insecure, as it can be easily bypassed using static or dynamic attacks. However, BiometricPrompt can also be used to unlock cryptographic keys. These test cases implement these use cases.',
      testCases: [
        {
          title: 'Biometric BiometricPrompt',
          description: 'Opens a prompt which requires biometric authentication. This method of authentication can usually be bypassed easily.',
          nativeFunction: AuthBiometricPrompt.simplePrompt,
        },
        {
          title: 'Device Credential BiometricPrompt',
          description: 'Opens a prompt which requires device credentials. This method of authentication is considered weak and can usually be bypassed easily.',
          nativeFunction: AuthBiometricPrompt.devicePinOnlyPrompt,
        },
        {
          title: 'BiometricPrompt Unlocking a CryptoObject',
          description: 'Opens a prompt which provides access to a CryptoObject.',
          nativeFunction: AuthBiometricPrompt.cryptoOperationPrompt,
        },
      ],
    },
    {
      title: 'FingerprintManager',
      description:
        'FingerprintManager is a class that coordinates access to the fingerprint hardware. However, it is deprecated and should not be used anymore. The replacement is BiometricManger and BiometricPrompt.',
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
          title: 'FingerprintManager Prompt',
          description: 'Opens a prompt which requires biometric authentication. This method of authentication can usually be bypassed easily.',
          nativeFunction: AuthFingerprintManager.authenticateSimple,
        },
        {
          title: 'FingerprintManager Unlocking a CryptoObject',
          description: 'Opens a prompt which provides access to a CryptoObject.',
          nativeFunction: AuthFingerprintManager.authenticateCryptoObject,
        },
      ],
    },
    {
      title: 'KeyguardManager',
      description:
        'The KeyguardManager class in Android is used to manage the KeyGuard (lock screen) functionality. It provides methods for determining the state of the KeyGuard, requesting its dismissal, ',
      testCases: [
        {
          title: 'Check Keyguard State',
          description: 'Checks if the KeyGuard is enabled (isKeyguardSecure()) and if it is currently visible (isKeyguardLocked()).',
          nativeFunction: AuthKeyguardManager.checkKeyguardState,
        },
        {
          title: 'Check Device State',
          description: 'Checks if the device has a secure lock screen (isDeviceSecure()) and if it is locked at the moment (isDeviceLocked()).',
          nativeFunction: AuthKeyguardManager.checkDeviceState,
        },
        {
          title: 'Check Pattern',
          description: 'Check if the lock screen uses a pattern or not.',
          nativeFunction: AuthKeyguardManager.checkPattern,
        },
        {
          title: 'Disable KeyguardLock',
          description: 'Disable the KeyGuard from showing. If the keyguard is currently showing, hide it.',
          nativeFunction: AuthKeyguardManager.disableKeyguardLock,
        },
        {
          title: 'Request Dismiss Keyguard',
          description:
            'Tries to dismiss the KeyGuard. If the Keyguard is not secure or the device is currently in a trusted state, calling this method will immediately dismiss the Keyguard without any user interaction.',
          nativeFunction: AuthKeyguardManager.requestDismissKeyguard,
        },
      ],
    },
    {
      title: 'Android Protected Confirmation',
      description:
        'Android Protected Confirmation is a security feature available on devices running Android 9 (API level 28) or higher. It uses a hardware-protected user interface, known as Trusted UI, to ensure that users explicitly confirm critical transactions, such as payments or sensitive actions. However, sensitive data displayed in the confirmation prompt is not guaranteed to remain confidential beyond the standard Android platform protections.',
      testCases: [
        {
          title: 'Create Protected Confirmation',
          description: 'Creates a protected confirmation prompt, which can be used to confirm sensitive actions.',
          nativeFunction: AuthProtectedConfirmation.create,
        },
      ],
    },
    {
      title: 'HTTP Basic Authentication',
      description:
        'The usage of HTTP Basic Authentication is not recommended anymore, since it comes with some inherit security flaws. For example, there is no mechanism for session management in place, passwords are not sufficiently protected against brute force attacks, and the passwords is sent to the server in every request.',
      testCases: [
        {
          title: 'Java Network API',
          description: 'This use case uses java.net API with HTTP Basic Authentication. You can set the target URL in the apps settings.',
          nativeFunction: AuthHttpBasicAuth.javaNet,
        },
        {
          title: 'WebView',
          description: 'This use case creates a Android WebView API which loads a page with HTTP Basic Authentication. You can set the target URL in the apps settings.',
          nativeFunction: AuthHttpBasicAuth.webView,
        },
      ],
    },
  ],
  NETWORK: [
    {
      title: 'TLS Client Configuration',
      description:
        'These tests change the default TLS client configuration. They can result in insecure settings.',
      testCases: [
        {
          title: 'Use Old TLS-Protocol',
          description:
            'Configures the client to use outdated TLS protocols, which are considered insecure and vulnerable to attacks.',
          nativeFunction: NetworkTlsClientConfig.oldTlsConfig,
        },
        {
          title: 'Use Insecure Cipher Suites',
          description:
            'These tests configure the client to use insecure cipher suites, such as ones with insecure algorithms or disabled forward secrecy-property.',
          nativeFunction: NetworkTlsClientConfig.insecureCipherSuites,
        },
        // {
        //   title: 'Usage of TLS Client Certificates',
        //   description:
        //     'Uses TLS with client authentication. This means, that the client must store a private key. Developers often hardcode client keys which is an issue.',
        //   nativeFunction: NetworkTlsClientConfig.clientCertificate,
        // },
        {
          title: 'Accept Bad TLS Servers',
          description:
            'Use BadSSL as TLS Server. The Client should not accept the connections.',
          nativeFunction: NetworkTlsClientConfig.acceptBadTLS,
        },
        {
          title: 'Android User TrustStore',
          description:
            'By default, the app uses the system trust store. Users cannot add new certificate authorities to this trust store. However, developers may weaken this setting by explicitly allowing the app to verify against the user trust store.',
          nativeFunction: NetworkTlsClientConfig.userTrustStore,
        },
      ],
    },
    {
      title: 'TLS Pinning',
      description:
        'By hardcoding or \'pinning\' specific TLS certificates or public keys within the application\'s code, TLS pinning ensures that only trusted servers are communicated with, preventing attackers from intercepting and tampering with sensitive data exchanged between the mobile app and the server. These test implement TLS pinning.',
      testCases: [
        {
          title: 'No Pinning',
          description: 'This test case uses the Java network API in order to connect to a TLS server who\'s certificate is not pinned using the Android manifest.',
          nativeFunction: NetworkTlsPinning.androidNoPinning,
        },
        {
          title: 'Android Pinning',
          description: 'This test case uses the Java network API in order to connect to a TLS server who\'s certificate is pinned using the Android manifest. There the Let\'s Encrypt root certificate is pinned.',
          nativeFunction: NetworkTlsPinning.androidPinning,
        },
        {
          title: 'Invalid Android Pinning',
          description: 'This test case uses the Java network API in order to connect to a TLS server who\'s certificate is NOT pinned.',
          nativeFunction: NetworkTlsPinning.androidPinningInvalid,
        },
        {
          title: 'OKHttp CertificatePinner',
          description: 'This test case uses the OKHttp CertificatePinner in order to verify the to a TLS server certificate. There the Let\'s Encrypt root certificate is pinned.',
          nativeFunction: NetworkTlsPinning.okHttpCertificatePinner,
        },
        {
          title: 'Invalid OKHttp CertificatePinner',
          description: 'This test case uses the OKHttp to connect to a TLS server who\'s certificate is NOT pinned.',
          nativeFunction: NetworkTlsPinning.okHttpCertificatePinnerInvalid,
        },
        {
          title: 'Java X509TrustManager',
          description: 'This test case verifies the TLS server certificate by implementing its own TrustManager in code. There the Let\'s Encrypt root certificate is pinned.',
          nativeFunction: NetworkTlsPinning.x509TrustManager,
        },
        {
          title: 'Invalid Java X509TrustManager',
          description: 'This test case verifies the TLS server certificate by implementing its own TrustManager in code. As no valid certificate is pinned, the client should not establish connection.',
          nativeFunction: NetworkTlsPinning.x509TrustManagerInvalid,
        },
        {
          title: 'WebView Workaround',
          description: 'WebView does not natively support server certificate pinning. In addition, it does not abide the the network security configuration form the manifest. One workaround is to intercept every request using shouldInterceptRequest() and then manually establish a connection using methods which support TLS pinning in order to do certificate pinning. This test implements such a WebView using OKHttp to pin the certificate.',
          nativeFunction: NetworkTlsPinning.webViewPinning,
        },
        {
          title: 'Invalid WebView Workaround',
          description: 'WebView does not natively support server certificate pinning. In addition, it does not abide the the network security configuration form the manifest. One workaround is to intercept every request using shouldInterceptRequest() and then manually establish a connection using methods which support TLS pinning in order to do certificate pinning. This test implements such a WebView using OKHttp to pin the certificate. The connection should fail, as the certificate is not properly pinned.',
          nativeFunction: NetworkTlsPinning.webViewPinningInvalid,
        },
      ],
    },
    // TODO: Will be implemented in the next release. The code stub in android exists already.
    // {
    //   title: 'Local Network',
    //   description:
    //     'Aside from connection servers on the public internet, apps may also try to access hosts on the local network. This can be be a violation of privacy in case an app scans and accesses local hosts excessively. Also it is possible to open ports on the phone. This adds additional attack surface which goes against the least-privilege principle if not necessary.',
    //   testCases: [
    //     {
    //       title: 'Access Local Network',
    //       description: 'This use case uses ICMP and TCP in order to check if a host in the private networks (10.0.0.0/8, 172.16.0.0/12 and 192.168.0.0/16) are up and if a service is running on the common port 80.',
    //       nativeFunction: NetworkLocalNetwork.access,
    //     },
    //     {
    //       title: 'Host HTTP Server',
    //       description: 'This use case hosts a HTTP server on port 80 and exposes it to the local network.',
    //       nativeFunction: NetworkLocalNetwork.hostHttpServer,
    //     },
    //     {
    //       title: 'Host TCP Server',
    //       description: 'This use case hosts a TCP server on port 2001 and exposes it to the local network.',
    //       nativeFunction: NetworkLocalNetwork.hostTcpServer,
    //     },
    //     {
    //       title: 'Host UDP Server',
    //       description: 'This use case hosts a UDP server on port 3001 and exposes it to the local network.',
    //       nativeFunction: NetworkLocalNetwork.hostUDPServer,
    //     },
    //   ],
    // },
  ],
  PLATFORM: [
    {
      title: 'Inter Process Communication',
      description:
        'These tests will simulate how information can be sent through IPC and potentially cause harm, given the input is not properly validated.',
      testCases: [
        {
          title: 'Exported Activity',
          description: 'Simulates an activity that is exported and accessible by other apps, potentially allowing malicious apps to send harmful data or commands.',
          nativeFunction: PlatformIpc.exportedActivity,
        },
        {
          title: 'Service',
          description: 'Tests an exported service that can be accessed by other apps, which may lead to unauthorized actions or data leakage.',
          nativeFunction: PlatformIpc.service,
        },
        // {
        //   title: 'Message Service',
        //   description: '',
        //   nativeFunction: PlatformIpc.serviceMessenger,
        // },
        {
          title: 'Content Provider',
          description: 'Simulates a content provider that is accessible by other apps, potentially exposing sensitive data if permissions are not properly configured.',
          nativeFunction: PlatformIpc.provider,
        },
        {
          title: 'Broadcast Receiver',
          description: 'Tests a broadcast receiver that listens for system or app broadcasts, which can be exploited if not properly secured.',
          nativeFunction: PlatformIpc.broadcastReceiver,
        },
        {
          title: 'Deep Links',
          description: 'Simulates deep link handling in the app, which could be exploited to inject malicious data or commands.',
          nativeFunction: PlatformIpc.deepLinks,
        },
        {
          title: 'Listen on Localhost',
          description: 'Tests a service that listens on localhost, which could be exploited by malicious apps or users on the same device.',
          nativeFunction: PlatformIpc.listenLocalhost,
        },
        {
          title: 'Send Data to Localhost',
          description: 'Simulates sending data to a service running on localhost, which could expose sensitive information if not properly secured.',
          nativeFunction: PlatformIpc.sendLocalhost,
        },
        {
          title: 'Write to Clipboard',
          description: 'Data can also be shared with services outside the app sandbox using the system clipboard. This test writes data to the clipboard.',
          nativeFunction: PlatformIpc.writeClipboard,
        },
        {
          title: 'Read from Clipboard',
          description: 'Data can also be shared with services outside the app sandbox using the system clipboard. This test reads data from the clipboard.',
          nativeFunction: PlatformIpc.readClipboard,
        },
      ],
    },
    {
      title: 'WebView',
      description:
        'This class represents a storage facility for cryptographic keys and certificates. The Android Keystore system lets you store cryptographic keys in a container to make them more difficult to extract from the device. Once keys are in the keystore, you can use them for cryptographic operations, with the key material remaining non-exportable.',
      testCases: [
        // {
        //   title: 'URL Allow List',
        //   description: 'Tests the implementation of a URL allow list to restrict WebView to load only specific URLs.',
        //   nativeFunction: PlatformWebView.urlAllowList,
        // },
        {
          title: 'Load Local Resource',
          description: 'Tests loading a local resource in the WebView, which could expose sensitive files if not properly configured.',
          nativeFunction: PlatformWebView.loadLocalResource,
        },
        {
          title: 'Load Remote HTTP Resource',
          description: 'Simulates loading a remote HTTP resource in the WebView, which could expose the app to man-in-the-middle attacks.',
          nativeFunction: PlatformWebView.loadRemoteHttpResource,
        },
        {
          title: 'Load Remote HTTPS Resource',
          description: 'Tests loading a remote HTTPS resource in the WebView, ensuring secure communication is used.',
          nativeFunction: PlatformWebView.loadRemoteHttpsResource,
        },
        {
          title: 'Allow File Access',
          description: 'Simulates enabling file access in the WebView, which could expose sensitive files to unauthorized access.',
          nativeFunction: PlatformWebView.allowFileAccess,
        },
        {
          title: 'Execute JavaScript From Host',
          description: 'Tests executing JavaScript in the WebView from the host application, which could lead to injection vulnerabilities.',
          nativeFunction: PlatformWebView.sendDataToJsSandbox,
        },
        {
          title: 'Call Native Function From JavaScript',
          description: 'Simulates calling native functions from JavaScript in the WebView, which could be exploited if not properly secured.',
          nativeFunction: PlatformWebView.readDataFromJsSandbox,
        },
        {
          title: 'Enable Geolocation',
          description: 'Tests enabling geolocation in the WebView, which could expose user location data to unauthorized parties.',
          nativeFunction: PlatformWebView.enableGeolocation,
        },
        {
          title: 'Allow Mixed Content',
          description: 'Simulates allowing mixed content (HTTP and HTTPS) in the WebView, which could expose the app to security risks.',
          nativeFunction: PlatformWebView.allowMixedContent,
        },
        {
          title: 'Enable Remote Web Content Debugging',
          description: 'Tests enabling remote debugging for WebView content, which could expose sensitive data during development.',
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
          description: 'Simulates displaying passwords in plaintext in text fields, which could expose sensitive information to shoulder-surfing or screenshots.',
          nativeFunction: PlatformUiDisclosure.passwordPlaintextUi,
        },
        {
          title: 'Sensitive Data in Notifications',
          description:
            'This test uses notifications to display sensitive data.',
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
  ],
  CODE: [
    {
      title: 'Current Software Version',
      description:
        'Check if the app enforces updates e.g. via AppUpdateManager on Android. However, the backend should be enforcing this and not only the app locally.',
      testCases: [
        {
          title: 'Check for App Updates',
          description: 'Use AppUpdateManager.getAppUpdateInfo to query, if there is a new version available.',
          nativeFunction: CodeUpdate.checkUpdateAvailable,
        },
        {
          title: 'Check OS Version',
          description: 'Checks the OS SDK Version in order to spot an insecure platform.',
          nativeFunction: CodeUpdate.checkOs,
        },
      ],
    },
    // TODO: Will be implemented in the next release. The code stub in android exists already.
    // {
    //   title: 'Insecure Dependency',
    //   description:
    //     'Insecure dependencies can introduce vulnerabilities into the app.',
    //   testCases: [
    //     {
    //       title: 'Vulnerable Native SDK',
    //       description: 'Test Case which uses insecure native SKD function.',
    //       nativeFunction: CodeDependencies.vulnerableNativeSDK,
    //     },
    //     {
    //       title: 'Vulnerable Web SDK',
    //       description: 'Test Case which uses insecure web SKD function.',
    //       nativeFunction: CodeDependencies.vulnerableWebSDK,
    //     },
    //   ],
    // },
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
          description: 'It is recommended to harden the DocumentBuilder used when parsing XML in order to protect against possible XEE.',
          nativeFunction: CodeInsecureSoftware.XmlExternalEntity,
        },
        {
          title: 'Java Deserialization',
          description: 'In this use case, a Java Object is serialized and the deserialized. If an attacker is able to control data which is deserialized, it is possible that the attacker can inject malicious code if the deserialization is successful. ',
          nativeFunction: CodeInsecureSoftware.deserialization,
        },
      ],
    },
  ],
  RESILIENCE: [
    // TODO: Will be implemented in the next release. The code stub in android exists already.
    // {
    //   title: 'Obfuscation',
    //   description:
    //     'Obfuscation can increase the amount of work attackers have to put into reverse-engineering or attacking the app. It therefore may be used alongside other secure development techniques in order to better protect sensitive parts of the app.',
    //   testCases: [
    //     {
    //       title: 'Obfuscated Android Class',
    //       description: 'Tests the use of obfuscated Android classes to make reverse engineering more difficult.',
    //       nativeFunction: ResilienceObfuscation.obfuscatedAndroidClass,
    //     },
    //     {
    //       title: 'Native Library whit Debug Symbols',
    //       description: 'Tests the inclusion of native libraries with debug symbols, which could expose sensitive implementation details.',
    //       nativeFunction: ResilienceObfuscation.nativeDebugSymbols,
    //     },
    //   ],
    // },
    {
      title: 'Anti-Debug',
      description:
        'The app tries to find out if it is debuggable, or of a debugger is currently attached.',
      testCases: [
        {
          title: 'Verify if App is Debuggable',
          description: 'Checks if the app is marked as debuggable, which could allow attackers to attach a debugger.',
          nativeFunction: ResilienceAntiDebug.debuggable,
        },
        {
          title: 'Verify if Debugger is Attached',
          description: 'Tests if a debugger is currently attached to the app, which could indicate an ongoing attack.',
          nativeFunction: ResilienceAntiDebug.debuggerConnected,
        },
      ],
    },
    {
      title: 'Anti-VM',
      description:
        'The app tries to find out if it runs in a virtual environment or on real hardware.',
      testCases: [
        {
          title: 'Get IMSI',
          description: 'Checks the IMSI to determine if the app is running on a virtual machine.',
          nativeFunction: ResilienceAntiVm.getImsi,
        },
        {
          title: 'Get Build String',
          description: 'Analyzes the build string to detect characteristics of virtual environments.',
          nativeFunction: ResilienceAntiVm.getBuild,
        },
        {
          title: 'Get Network Interface',
          description: 'Examines network interfaces to identify virtualized environments.',
          nativeFunction: ResilienceAntiVm.getNetworkInterface,
        },
        {
          title: 'Get Installer Package Name',
          description: 'Checks the installer package name to detect if the app is running in a virtual environment.',
          nativeFunction: ResilienceAntiVm.getInstallerPackageName,
        },
        {
          title: 'Get Sensor',
          description: 'Tests the availability of sensors to determine if the app is running on a physical device or a virtual machine.',
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
            'Use the RootBeer Library to check if the the app is running on a rooted device. This check can usually be bypassed by static or dynamic attacks.',
          nativeFunction: ResilienceRootDetection.rootBeer,
        },
      ],
    },
    // {
    //   title: 'Simple Dynamic Analysis Tools Detection',
    //   description:
    //     'The app uses common Dynamic Analysis Tools Detection detection libraries in order to do a simple check if tools are available.',
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
        'The app tries to validate the integrity of the platform using security features provided by Android. Compared to a in-app root check, these checks are more secure, as the attacker must compromise the OS itself and the developer can verify the result on the server site.',
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
      title: 'Advertisement',
      description: 'User tracking in mobile applications involves collecting and analyzing data to monitor user behavior, preferences, and movements. This enables companies to recognize and follow users over time and across different apps, devices, and services. Such tracking often occurs without the user\'s explicit knowledge or consent, leading to significant privacy concerns.',
      testCases: [
        {
          title: 'Get AAID',
          description: 'Tests the retrieval of the Advertising ID (AAID), which can be used for tracking users across apps.',
          nativeFunction: PrivacyMarketingUUID.getAAID,
        },
        {
          title: 'Get Android ID',
          description: 'Simulates accessing the Android ID, which can be used as a unique identifier for tracking purposes.',
          nativeFunction: PrivacyMarketingUUID.getAndroidID,
        },
        {
          title: 'Get IMEI',
          description: 'Tests accessing the IMEI, which can be used to uniquely identify a device and track users.',
          nativeFunction: PrivacyMarketingUUID.getImei,
        },
        {
          title: 'Get MAC Address',
          description: 'Simulates retrieving the MAC address, which can be used for tracking devices on a network.',
          nativeFunction: PrivacyMarketingUUID.getMAC,
        },
      ],
    },
  ],
};

export default androidTestCases;
