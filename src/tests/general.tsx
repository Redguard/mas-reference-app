import {NativeModules} from 'react-native';
import {TestGroup} from '../appContent';
const {
  StorageLog,
  StorageHardcodedApiKey,

  NetworkUnencrypted,

  ResilienceVerifySignature,

  CryptoHardcodedSecret,

  PrivacyAccessData,
} = NativeModules;

interface Dictionary<Type> {
  [key: string]: Type;
}

export var generalTestCases: Dictionary<TestGroup[]> = {
  STORAGE: [
    {
      title: 'Sensitive Data in Log',
      maswe: '0001',
      description:
        'Mobile apps may write sensitive data to logs. This may include sensitive user data, such as passwords, credit card numbers, or other personally identifiable information (PII), as well as sensitive system data, such as cryptographic keys, session tokens, or other sensitive information.',
      testCases: [
        {
          title: 'Log Sensitive Data',
          description:
            'This use case logs sensitive data to the. Among other, they contain the canary token, private keys and various numbers of api keys.',
          nativeFunction: StorageLog.logSensitiveData,
        },
        {
          title: 'Log Phone Numbers',
          description:
            'Logs phone number in different forms. An example is +41 (076) 1234567.',
          nativeFunction: StorageLog.logPhoneNumber,
        },
        {
          title: 'Log E-Mail Addresses',
          description:
            'Logs e-mail addresses in different forms. An example is someuser1@gmail.com.',
          nativeFunction: StorageLog.logEmail,
        },
        {
          title: 'Log Financial Data',
          description:
            'Logs financial data, such as IBAN and credit card numbers, in different forms. An example is DE89 3704 0044 0532 0130 00',
          nativeFunction: StorageLog.logFinData,
        },
        {
          title: 'Log Location',
          description:
            'Logs location data in different forms. An example is 37°46\'29.64"N, 122°25\'9.84"W',
          nativeFunction: StorageLog.logLocation,
        },
      ],
    },
    {
      title: 'Hardcoded API Keys',
      maswe: '0005',
      description:
        'API keys hardcoded in the app package, source code, or compiled binaries, can be easily extracted through reverse engineering. If the key is only required after a user is authenticated, the key should not be hardcoded but rather be stored on the server side.',
      testCases: [
        {
          title: 'Hardcoded API Keys',
          description: 'This class contains hard coded, well known API keys.',
          nativeFunction: StorageHardcodedApiKey.apiKeys,
        },
      ],
    },
  ],
  CRYPTO: [
    {
      title: 'Hardcoded Cryptographic Keys',
      maswe: '0014',
      description:
        'This weakness involves storing cryptographic keys in insecure locations, such as unprotected files, hardcoding them within the application code, or including them in source control and versioning systems which may end in the final application package in production.',
      testCases: [
        {
          title: 'Asymmetric Private Key as File',
          description:
            'This use case loads asymmetric private keys from files stored in the application.',
          nativeFunction: CryptoHardcodedSecret.privateLocalKeys,
        },
        {
          title: 'Asymmetric Private Key within Code',
          description:
            'This use case contains asymmetric private keys which are embedded within the code directly.',
          nativeFunction: CryptoHardcodedSecret.privateEmbeddedKeys,
        },
        {
          title: 'Symmetric Key within Code',
          description:
            'This use case contains symmetric keys which are embedded within the code directly. They are stored in variables which are named password or within db connection string such as "jdbc:mysql://localhost/db?user=root&password=badpwd"',
          nativeFunction: CryptoHardcodedSecret.hardcodedSymmetricKeys,
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
        'Unencrypted connections are an issue because they expose sensitive data to potential interception and tampering by malicious actors. Protocols such as HTTP should never be used as they are always unencrypted. Further, if a raw TCP or UDP socket is opened from the apps namespace, this can be a this may be an indication that the developer uses custom application layer protocols. This could be a source for a risk.',
      testCases: [
        {
          title: 'HTTP',
          description:
            'Opens a HTTP connection using the default HTTP stack. This is always a risk as the traffic is not encrypted.',
          nativeFunction: NetworkUnencrypted.openHTTP,
        },
        {
          title: 'HTTP on Non-Default-Port',
          description:
            'Opens a HTTP connection using the default HTTP stack and on a non-standard port (7001). This is always a risk as the traffic is not encrypted.',
          nativeFunction: NetworkUnencrypted.nonStandardHTTP,
        },
        {
          title: 'WebSocket within WebView',
          description:
            'Opens a WebView which opens a plaintext WebSocket connection. This is always a risk as the traffic is not encrypted.',
          nativeFunction: NetworkUnencrypted.webSocket,
        },
        {
          title: 'Raw TCP Socket',
          description:
            'Opens a raw TCP socket on port 80. Then the app sends a crafted HTTP request. Usually, the app must not directly open a raw socket. Should the app transmit plain text data over this channel, this may therefore introduce a risk.',
          nativeFunction: NetworkUnencrypted.rawTcp,
        },
        {
          title: 'Raw UDP Socket',
          description:
            'Opens a raw UDP socket on port 4001. If wou use the default test domain, a UDP echo server will echo the request on this port. Usually, the app must not directly open a raw socket. Should the app transmit plain text data over this channel, this may therefore introduce a risk. ',
          nativeFunction: NetworkUnencrypted.rawUdp,
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
          description:
            'Retrieves the package signatures to verify the integrity and authenticity of the application.',
          nativeFunction: ResilienceVerifySignature.getPackageSignatures,
        },
      ],
    },
  ],
  PRIVACY: [
    {
      title: 'Access Personal Data',
      description:
        'Access to private data may raise concerns about app privacy. Furthermore, if the app is given the permission to access private data, SDK may also be able to access them in bulk. It is therefore vital to understand what data the app access it during runtime.',
      testCases: [
        {
          title: 'Access Contacts',
          description:
            "Attempts to access the user's contact list, which may contain sensitive personal information. The test does not really access the data, but only count the number of records retrieved.",
          nativeFunction: PrivacyAccessData.getContacts,
        },
        {
          title: 'Access Calendar',
          description:
            "Attempts to access the user's calendar events, which may reveal private information. The test does not really access the data, but only count the number of records retrieved.",
          nativeFunction: PrivacyAccessData.getCalendarEvent,
        },
        {
          title: 'Access Location',
          description:
            "Attempts to access the user's location data, which may expose sensitive geographic information.",
          nativeFunction: PrivacyAccessData.getLocation,
        },
        {
          title: 'Access SMS',
          description:
            "Attempts to access the user's SMS messages, which may contain private communication data. The test does not really access the data, but only count the number of records retrieved.",
          nativeFunction: PrivacyAccessData.getSMS,
        },
        // TODO: Not where to put this risk. 
        // {
        //   title: 'Attempt so send SMS',
        //   description:
        //     'This test will send a SMS to the number 000000000. As the number is invalid, the SMS will not be sent to anyone, but it will show up in the messaging app.',
        //   nativeFunction: PrivacyAccessData.sendSMS,
        // },
      ],
    },
  ],
};

export default generalTestCases;
