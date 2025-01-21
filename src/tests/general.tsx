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
  AUTH: [],
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
        {
          title: 'Send plaintext Data using RTP',
          nativeFunction: NetworkUnencrypted.rtp,
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
        {
          title: 'Accept Bad TLS Servers',
          description:
            'Use BadSSL as TLS Server. The Client should not accept the connections.',
          nativeFunction: NetworkTlsConfig.acceptBadTLS,
        },
      ],
    },
  ],
  PLATFORM: [],
  CODE: [],
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
  ],
  PRIVACY: [
    {
      title: 'Access Personal Data',
      description:
        'Access to private data may raise concerns about app privay. Furhtermore, if the app is given the permission to access private data, SDK may also be able to access them in bulk. It is therefore vital to understand what data the app access it during runtime.',
      testCases: [
        {
          title: 'Access Contacts',
          nativeFunction: PrivacyAccessData.getContacts,
        },
        {
          title: 'Access Calendar',
          nativeFunction: PrivacyAccessData.getCalendarEvent,
        },
        {
          title: 'Access Location',
          nativeFunction: PrivacyAccessData.getLocation,
        },
        {
          title: 'Access SMS',
          nativeFunction: PrivacyAccessData.getSMS,
        },
        {
          title: 'Attempt so send SMS',
          nativeFunction: PrivacyAccessData.sendSMS,
        },
      ],
    },
  ],
};

export default generalTestCases;
