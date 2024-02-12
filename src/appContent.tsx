import testCases from './testcases.tsx';

type TestCase = {
  title: string;
  description: string;
  nativeFunction: any;
};

type TestCases = {
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

var tests = testCases;

appContent.forEach((category: MASCategory) => {
  category.tests = tests[category.key];
});

export default appContent;
export type {TestCases};
