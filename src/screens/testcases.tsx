import {Text, View} from 'react-native';

function TestCaseScreen(): React.JSX.Element {
  return (
    <View
      style={{
        flex: 1,
        margin: 10,
        alignItems: 'center',
        justifyContent: 'center',
      }}>
      <Text>Storage Test-Cases:</Text>
    </View>
  );
}

function CryptoScreen(): React.JSX.Element {
  return (
    <View
      style={{
        flex: 1,
        margin: 10,
        alignItems: 'center',
        justifyContent: 'center',
      }}>
      <Text>Crypto Test-Cases:</Text>
    </View>
  );
}

function NetworkScreen(): React.JSX.Element {
  return (
    <View
      style={{
        flex: 1,
        margin: 10,
        alignItems: 'center',
        justifyContent: 'center',
      }}>
      <Text>Network Test-Cases:</Text>
    </View>
  );
}

const TestCases = [
  {
    key: 'STORAGE',
    screenName: TestCaseScreen,
    color: '#df5c8c',
    description:
      'This category is designed to help developers ensure that any sensitive data intentionally stored by the app is properly protected, regardless of the target location. It also covers unintentional leaks that can occur due to improper use of APIs or system capabilities.',
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
    screenName: CryptoScreen,
    color: '#f65928',
    general: [],
    android: [],
    ios: [],
  },
  {
    key: 'AUTH',
    screenName: NetworkScreen,
    color: '#f09236',
    general: [],
    android: [],
    ios: [],
  },
  {
    key: 'NETWORK',
    screenName: NetworkScreen,
    color: '#f2c200',
    general: [],
    android: [],
    ios: [],
  },
  {
    key: 'PLATFORM',
    screenName: NetworkScreen,
    color: '#4fb990',
    general: [],
    android: [],
    ios: [],
  },
  {
    key: 'CODE',
    screenName: NetworkScreen,
    color: '#5facd3',
    general: [],
    android: [],
    ios: [],
  },
  {
    key: 'RESILIENCE',
    screenName: NetworkScreen,
    color: '#317bc0',
    general: [],
    android: [],
    ios: [],
  },
  {
    key: 'PRIVACY',
    screenName: NetworkScreen,
    color: '#8b5f9e',
    general: [],
    android: [],
    ios: [],
  },
];

export default TestCases;
