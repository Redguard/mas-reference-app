import {NativeModules} from 'react-native';
import {TestCases} from '../appContent';
const {
  StorageLog,
  StorageHardcodedApiKey,

  NetworkUnencrypted,

  ResilienceVerifySignature,

  NetworkTlsConfig,

  CryptoHardcodedSecret,

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
          title: 'Log sensitive Data',
          nativeFunction: StorageLog.logSensitiveData,
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
      title: 'Hardcoded API Keys',
      maswe: '0005',
      description:
        'API keys hardcoded in the app package, source code, or compiled binaries, can be easily extracted through reverse engineering.',
      testCases: [
        {
          title: 'Hardcoded API Keys',
          nativeFunction: StorageHardcodedApiKey.apiKeys,
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
    {
      title: 'Hardcoded Crytographic Keys',
      maswe: '0014',
      description:
        'This weakness involves storing cryptographic keys in insecure locations, such as unencrypted SharedPreferences, unprotected files, hardcoding them within the application code, or including them in source control and versioning systems which may end in the final application package in production.',
      testCases: [
        {
          title: 'Hardcoded Local Asymmetric Private Key',
          nativeFunction: CryptoHardcodedSecret.privateLocalKeys,
        },
        {
          title: 'Hardcoded Embedded Asymmetric Private Key',
          nativeFunction: CryptoHardcodedSecret.privateEmbeddedKeys,
        },
        {
          title: 'Hardcoded Symmetric Key',
          nativeFunction: CryptoHardcodedSecret.passwords,
        },
      ],
    },
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
          description: 'Resolve domainname using plaintext DNS',
          nativeFunction: NetworkUnencrypted.resolveDns,
        },
        {
          title: 'Send sensitive Data using HTTP',
          nativeFunction: NetworkUnencrypted.sendHTTP,
        },
        {
          title: 'Send plaintext Data using raw TCP',
          nativeFunction: NetworkUnencrypted.rawTcp,
        },
        {
          title: 'Send plaintext Data using raw UDP',
          nativeFunction: NetworkUnencrypted.rawUdp,
        },
      ],
    },
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
          title: 'Usage of TLS Client Certifitates',
          description:
            'The fact, that a TLS client certificate are used, may mean, that the app contians the private keys hard coded.',
          nativeFunction: NetworkTlsConfig.clientCertificate,
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
