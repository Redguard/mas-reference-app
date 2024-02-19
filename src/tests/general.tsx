import {NativeModules} from 'react-native';
import {TestCases} from '../appContent';
const {StorageLog} = NativeModules;

interface Dictionary<Type> {
  [key: string]: Type;
}

export var generalTestCases: Dictionary<TestCases[]> = {
  STORAGE: [
    {
      title: 'Sensitive Data in Log',
      description:
        'This test cases will write the following sensitive data to the application log:\n\n- Different Password-Identifiere\n- Valid Access-Token\n- ...\n',
      testCases: [
        {
          title: 'Log Name',
          nativeFunction: StorageLog.logName,
        },
        {
          title: 'Log Password',
          nativeFunction: StorageLog.logPassword,
        },
        {
          title: 'Log Secrets',
          nativeFunction: StorageLog.logSecrets,
        },
        {
          title: 'Log PEM Headers',
          nativeFunction: StorageLog.logPEM,
        },
        {
          title: 'Log Phone Number',
          nativeFunction: StorageLog.logPhoneNumber,
        },
        {
          title: 'Log E-Mail Address',
          nativeFunction: StorageLog.logEmail,
        },
        {
          title: 'Log Financial Data',
          nativeFunction: StorageLog.logFinData,
        },
        {
          title: 'Log Location',
          nativeFunction: StorageLog.logLocation,
        },
        {
          title: 'Log Access Token',
          nativeFunction: StorageLog.locAccessToken,
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
    {
      title: 'SQLite Database',
      description: '',
      testCases: [],
    },
    // Define your general storage test cases here
  ],
  CRYPTO: [
    // Define your general crypto test cases here
  ],
  AUTH: [
    // Define your general authentication test cases here
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
    // Define your general network test cases here
  ],
  PLATFORM: [
    // Define your general platform-related test cases here
  ],
  CODE: [
    // Define your general code-related test cases here
  ],
  RESILIENCE: [
    {
      title: 'Verify Signature',
      description:
        'Programmatically verify the signature of the package during runtime.',
      testCases: [],
    },
    // Define your general resilience test cases here
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
    // Define your general privacy-related test cases here
  ],
};

export default generalTestCases;
