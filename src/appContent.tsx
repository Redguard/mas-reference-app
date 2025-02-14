import testCases from './testcases.tsx';

type TestCase = {
  title: string;
  description?: string;
  nativeFunction: any;
  canaryToken?: string;
  maswe?: string;
};

type TestCases = {
  title: string;
  description: string;
  testCases: TestCase[];
  maswe?: string;
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
      'Mobile apps often store sensitive data. This can be done in various ways. For example, the location of the datastorage can have an impact on the security of the data. Also, sensitive data can escape the app\'s sandbox using insecure backups or misconfigured logging.',
    tests: [],
  },
  {
    key: 'CRYPTO',
    color: '#f65928',
    description:
      'This category focuses on the management of cryptographic material throughout their lifecycle, including key properties, their storage, and protection against unauthorized access during the runtime. But also the usage of insecure cryptographic primitives, such as insecurely configured ciphers are part of these use cases.',
    tests: [],
  },
  {
    key: 'AUTH',
    color: '#f09236',
    description:
      'Local authentication, such as biometrics, PIN, or passwords, is important for mobile app security. However, there are different ways of implementing them. Some of them more, some of them less secure. This category enumerates the different risks associated with them.',
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
      "This category comprises tests that could lead to insecure interactions with the mobile platform. The cover the use of platform-provided IPC mechanisms, insecure WebView configurations which may lead to sensitive data leakage and functionality exposure, and the display of sensitive data in the app's user interface.",
    tests: [],
  },
  {
    key: 'CODE',
    color: '#5facd3',
    description:
      'This category covers coding vulnerabilities that arise from external sources such as app data entry points, the OS, and third-party software components. The use cases also cover the enforcement of app updates and checks, that the app runs up-to-date platforms to protect users from known vulnerabilities.',
    tests: [],
  },
  {
    key: 'RESILIENCE',
    color: '#317bc0',
    description:
      "The tests in this category aim to ensure that the app is running on a trusted platform, prevent tampering at runtime and ensure the integrity of the app's intended functionality. Due to the nature of the category, the absence of these controls in an application is the risk.",
    tests: [],
  },
  {
    key: 'PRIVACY',
    color: '#8b5f9e',
    description:
      'Mobile apps can access a variety of personal data under certain circumstances. These tests simulate apps which try to access these information.',
    tests: [],
  },
];

var tests = testCases;

appContent.forEach((category: MASCategory) => {
  category.tests = tests[category.key];
});

export default appContent;
export type {TestCases};
