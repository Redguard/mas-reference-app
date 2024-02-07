const TestCases = [
  {
    key: 'STORAGE',
    color: '#df5c8c',
    description:
      'This test case focuses on identifying potentially sensitive data stored by an application and verifying if it is securely stored.',
    general: [
      {
        log: [
          {
            title: 'Sensitive Data in Application-Log',
            description:
              'This test cases will write the following sensitive data to the application log:\n- Different Password-Identifiere\n- Valid Access-Token\n- ...\n',
            testCases: ['writeLog'],
          },
        ],
      },
    ],
    android: [
      {
        sharedPreferences: [
          {
            title: 'SharedPreferences',
            description:
              'This testcase stores data using unencrypted SharedPreferences.\n',
            testCases: ['writeSharedPreferences', 'readSharedPreferences'],
          },
        ],
        encryptedSharedPreferences: [
          {
            title: 'Encrypted SharedPreferences',
            description:
              'This testcase stores data using secure, encrypted SharedPreferences.\n',
            testCases: [
              'writeEncryptedSharedPreferences',
              'readEncryptedSharedPreferences',
              {},
            ],
          },
        ],
        'java.io': [
          {
            title: 'java.io',
            description:
              'Theses tests will write and read data into the local sanbox using the java.io Classes.',
            testCases: [
              'writeBufferedOutputStream',
              'writeBufferedWriter',
              'writeByteArrayOutputStream',
              'writeCharArrayWriter',
              'writeConsole',
              'writeDataOutputStream',
              'writeFileOutputStream',
              'writeFilterOutputStream',
              'writeFilterWriter',
              'writeObjectOutputStream',
              'writeStringWriter',
            ],
          },
        ],
      },
    ],
    ios: [],
  },
  {
    key: 'CRYPTO',
    color: '#f65928',
    description:
      "This category also focuses on the management of cryptographic keys throughout their lifecycle, including key generation, storage, and protection. Poor key management can compromise even the strongest cryptography, so it is crucial for developers to follow the recommended best practices to ensure the security of their users' sensitive data.",
    general: [],
    android: [],
    ios: [],
  },
  {
    key: 'AUTH',
    color: '#f09236',
    description:
      'Mobile apps often use different forms of authentication, such as biometrics, PIN, or multi-factor authentication code generators, to validate user identity. These mechanisms must be implemented correctly to ensure their effectiveness in preventing unauthorized access.',
    general: [],
    android: [],
    ios: [],
  },
  {
    key: 'NETWORK',
    color: '#f2c200',
    description:
      'This category is designed to ensure that the mobile app sets up secure connections under any circumstances. Specifically, it focuses on verifying that the app establishes a secure, encrypted channel for network communication. Additionally, this category covers situations where a developer may choose to trust only specific Certificate Authorities (CAs), which is commonly referred to as certificate pinning or public key pinning.',
    general: [],
    android: [],
    ios: [],
  },
  {
    key: 'PLATFORM',
    color: '#4fb990',
    description:
      "This category comprises controls that ensure the app's interactions with the mobile platform occur securely. These controls cover the secure use of platform-provided IPC mechanisms, WebView configurations to prevent sensitive data leakage and functionality exposure, and secure display of sensitive data in the app's user interface. By implementing these controls, mobile app developers can safeguard sensitive user information and prevent unauthorized access by attackers.",
    general: [],
    android: [],
    ios: [],
  },
  {
    key: 'CODE',
    color: '#5facd3',
    description:
      'This category covers coding vulnerabilities that arise from external sources such as app data entry points, the OS, and third-party software components. Developers should verify and sanitize all incoming data to prevent injection attacks and bypass of security checks. They should also enforce app updates and ensure that the app runs up-to-date platforms to protect users from known vulnerabilities.',
    general: [],
    android: [],
    ios: [],
  },
  {
    key: 'RESILIENCE',
    color: '#317bc0',
    description:
      "The controls in this category aim to ensure that the app is running on a trusted platform, prevent tampering at runtime and ensure the integrity of the app's intended functionality. Additionally, the controls impede comprehension by making it difficult to figure out how the app works using static analysis and prevent dynamic analysis and instrumentation that could allow an attacker to modify the code at runtime.",
    general: [],
    android: [],
    ios: [],
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

export default TestCases;
