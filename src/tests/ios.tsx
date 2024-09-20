/* eslint-disable no-trailing-spaces */
/* eslint-disable prettier/prettier */
import {NativeModules} from 'react-native';
import {TestCases} from '../appContent';
const {} = NativeModules;

interface Dictionary<Type> {
  [key: string]: Type;
}

export var iosTestCases: Dictionary<TestCases[]> = {
  STORAGE: [
    {
    title: 'Sensitive Data in Keychain',
    description:
      'This test cases will read and write sensitive Data to the iOS Keychain.',
    testCases: [
      {
        title: 'Write Data into the Keychain',
        description: 'Write unencrypted data sandbox Keychain.',
        nativeFunction: null,
      },
      {
        title: 'Read Data from Keychain',
        description: '',
        nativeFunction: null,
      },
    ],
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
    // Define your general network test cases here
  ],
  PLATFORM: [
    // Define your general platform-related test cases here
  ],
  CODE: [
    // Define your general code-related test cases here
  ],
  RESILIENCE: [
    // Define your general resilience test cases here
  ],
  PRIVACY: [
    
    // Define your general privacy-related test cases here
  ],
};

export default iosTestCases;
