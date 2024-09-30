import {NativeModules} from 'react-native';
import {TestCases} from '../appContent';
const {
  StorageLog,
  StorageHardcodedSecret,

  NetworkUnencrypted,

  ResilienceVerifySignature,

  PrivacyAccessData,
} = NativeModules;

interface Dictionary<Type> {
  [key: string]: Type;
}

export var generalTestCases: Dictionary<TestCases[]> = {
  STORAGE: [
    {
      title: 'Sensitive Data in Log',
      maswe: '0001',
      description:
        'This test cases will write the following sensitive data to the application log.',
      testCases: [
        {
          title: 'Log Sensitive Data',
          nativeFunction: StorageLog.logSensitiveData,
        },
        {
          title: 'Log Canary Token',
          nativeFunction: StorageLog.logCanaryToken,
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
      ],
    },

    {
      title: 'Hardcoded Secrets',
      maswe: '0005',
      description:
        'Sensitive data, including cryptographic keys and authentication material, hardcoded in the app package, source code, or compiled binaries, poses significant security risks, as attackers can easily extract this data through reverse engineering. These test simulate sensitve date within code and stored within the binary as embedded files.',
      testCases: [
        {
          title: 'Hardcoded Local Private Key',
          nativeFunction: StorageHardcodedSecret.privateLocalKeys,
        },
        {
          title: 'Hardcoded Embedded Private Key',
          nativeFunction: StorageHardcodedSecret.privateEmbeddedKeys,
        },
        {
          title: 'Hardcoded API Keys',
          nativeFunction: StorageHardcodedSecret.apiKeys,
        },
        {
          title: 'Hardcoded Passwords',
          nativeFunction: StorageHardcodedSecret.passwords,
        },
      ],
    },

    ////////////////////REFACTOR///////////////////////
    ////////////////////REFACTOR///////////////////////
    ////////////////////REFACTOR///////////////////////
    ////////////////////REFACTOR///////////////////////
    ////////////////////REFACTOR///////////////////////
    ////////////////////REFACTOR///////////////////////
    ////////////////////REFACTOR///////////////////////
    ////////////////////REFACTOR///////////////////////
    ////////////////////REFACTOR///////////////////////
    ////////////////////REFACTOR///////////////////////
    ////////////////////REFACTOR///////////////////////

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
      title: 'Cleartext Traffic',
      maswe: '0050',
      description:
        'Unencrypted connections are an issue because they expose sensitive data to potential interception and tampering by malicious actors.',
      testCases: [
        {
          title: 'Resolve Domainname',
          description: 'Resolve domainname using DNS',
          nativeFunction: NetworkUnencrypted.resolveDns,
        },
        {
          title: 'Open Standard HTTP Connection',
          description: 'Opens a standard HTTP connection.',
          nativeFunction: NetworkUnencrypted.openHTTP,
        },
        {
          title: 'Send sensitve Data using HTTP',
          nativeFunction: NetworkUnencrypted.sendHTTP,
        },
        {
          title: 'Send sensitve Data usign TCP',
          nativeFunction: NetworkUnencrypted.rawTcp,
        },
        {
          title: 'Send sensitve Data using UDP',
          nativeFunction: NetworkUnencrypted.rawUdp,
        },
      ],
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
      testCases: [
        {
          title: 'Get Package Signatures',
          nativeFunction: ResilienceVerifySignature.getPackageSignatures,
        },
      ],
    },
    // Define your general resilience test cases here
  ],
  PRIVACY: [
    {
      title: 'Access Phone Data',
      description: 'e.G. Calendar, Contacts,',
      testCases: [
        {
          title: 'Get Contacts',
          nativeFunction: PrivacyAccessData.getContacts,
        },
        {
          title: 'Store senstive Date in Contacts',
          nativeFunction: PrivacyAccessData.writeContacts,
        },
        {
          title: 'Get Calendar Event',
          nativeFunction: PrivacyAccessData.getCalendarEvent,
        },
        {
          title: 'Store senstive Date in Calendar Event',
          nativeFunction: PrivacyAccessData.writeCalendarEvent,
        },
        // {
        //   title: 'Get WIFI',
        //   nativeFunction: PrivacyAccessData.getWifi,
        // },
      ],
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
