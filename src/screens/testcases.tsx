import {NativeModules, Platform} from 'react-native';
const {
  StorageSharedPreferences,
  StorageEncryptedSharedPreferences,
  StorageLog,
} = NativeModules;

type TestCase = {
  id: string;
  title: string;
  description: string;
  nativeFunction: any;
};

type TestCases = {
  id: string;
  title: string;
  description: string;
  testCases: TestCase[];
};

type MASCategory = {
  key: string;
  color: string;
  description: string;
  tests: TestCases[];
};

var appContent: MASCategory[] = [
  {
    key: 'STORAGE',
    color: '#df5c8c',
    description:
      'This test case focuses on identifying potentially sensitive data stored by an application and verifying if it is securely stored.',
    tests: [],
  },
  {
    key: 'CRYPTO',
    color: '#f65928',
    description:
      "This category also focuses on the management of cryptographic keys throughout their lifecycle, including key generation, storage, and protection. Poor key management can compromise even the strongest cryptography, so it is crucial for developers to follow the recommended best practices to ensure the security of their users' sensitive data.",
    tests: [],
  },
  {
    key: 'AUTH',
    color: '#f09236',
    description:
      'Mobile apps often use different forms of authentication, such as biometrics, PIN, or multi-factor authentication code generators, to validate user identity. These mechanisms must be implemented correctly to ensure their effectiveness in preventing unauthorized access.',
    tests: [],
  },
  {
    key: 'NETWORK',
    color: '#f2c200',
    description:
      'This category is designed to ensure that the mobile app sets up secure connections under any circumstances. Specifically, it focuses on verifying that the app establishes a secure, encrypted channel for network communication. Additionally, this category covers situations where a developer may choose to trust only specific Certificate Authorities (CAs), which is commonly referred to as certificate pinning or public key pinning.',
    tests: [],
  },
  {
    key: 'PLATFORM',
    color: '#4fb990',
    description:
      "This category comprises controls that ensure the app's interactions with the mobile platform occur securely. These controls cover the secure use of platform-provided IPC mechanisms, WebView configurations to prevent sensitive data leakage and functionality exposure, and secure display of sensitive data in the app's user interface. By implementing these controls, mobile app developers can safeguard sensitive user information and prevent unauthorized access by attackers.",
    tests: [],
  },
  {
    key: 'CODE',
    color: '#5facd3',
    description:
      'This category covers coding vulnerabilities that arise from external sources such as app data entry points, the OS, and third-party software components. Developers should verify and sanitize all incoming data to prevent injection attacks and bypass of security checks. They should also enforce app updates and ensure that the app runs up-to-date platforms to protect users from known vulnerabilities.',
    tests: [],
  },
  {
    key: 'RESILIENCE',
    color: '#317bc0',
    description:
      "The controls in this category aim to ensure that the app is running on a trusted platform, prevent tampering at runtime and ensure the integrity of the app's intended functionality. Additionally, the controls impede comprehension by making it difficult to figure out how the app works using static analysis and prevent dynamic analysis and instrumentation that could allow an attacker to modify the code at runtime.",
    tests: [],
  },
  // {
  //   key: 'PRIVACY',
  //   color: '#8b5f9e',
  //   desciption: `test`,
  //   general: [],
  //   android: [],
  //   ios: [],
  // },
];

type testCaseCollection = {
  general: TestCases[];
  android: TestCases[];
  ios: TestCases[];
};

interface Dictionary<Type> {
  [key: string]: Type;
}

var tests: Dictionary<testCaseCollection> = {
  STORAGE: {
    general: [
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
    android: [
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
            description:
              'Write data into the sandbox using BufferedOutputStream',
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
    ],
    ios: [
      {
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
            description:
              'Write data into the sandbox using writeBufferedWriter',
            nativeFunction: null,
          },
          //'writeDataFromKeychain', 'readDataToKeychain'
        ],
      },
    ],
  },
  CRYPTO: {
    general: [],
    android: [],
    ios: [],
  },
  AUTH: {
    general: [],
    android: [],
    ios: [],
  },
  NETWORK: {
    general: [],
    android: [],
    ios: [],
  },
  PLATFORM: {
    general: [],
    android: [],
    ios: [],
  },
  CODE: {
    general: [],
    android: [],
    ios: [],
  },
  RESILIENCE: {
    general: [],
    android: [],
    ios: [],
  },
};

appContent.forEach((category: MASCategory) => {
  console.log(tests[category.key].general);
  category.tests = category.tests.concat(tests[category.key].general);
  if (Platform.OS === 'android') {
    category.tests = category.tests.concat(tests[category.key].android);
  } else if (Platform.OS === 'ios') {
    category.tests = category.tests.concat(tests[category.key].ios);
  }
});

export default appContent;
export type {TestCases};
