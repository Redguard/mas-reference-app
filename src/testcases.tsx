import {Platform} from 'react-native';
import {generalTestCases} from './tests/general';
import {androidTestCases} from './tests/android';

/*
  General Use-Cases
*/
var testCases = generalTestCases;

if (Platform.OS === 'android') {
  /*
    Android Use-Cases
  */
  testCases.STORAGE = testCases.STORAGE.concat(androidTestCases.STORAGE);
  testCases.CRYPTO = testCases.CRYPTO.concat(androidTestCases.CRYPTO);
  testCases.AUTH = testCases.AUTH.concat(androidTestCases.AUTH);
  testCases.NETWORK = testCases.NETWORK.concat(androidTestCases.NETWORK);
  testCases.PLATFORM = testCases.PLATFORM.concat(androidTestCases.PLATFORM);
  testCases.CODE = testCases.CODE.concat(androidTestCases.CODE);
  testCases.RESILIENCE = testCases.RESILIENCE.concat(
    androidTestCases.RESILIENCE,
  );
  testCases.PRIVACY = testCases.PRIVACY.concat(androidTestCases.PRIVACY);
} else if (Platform.OS === 'ios') {
  /*
    iOS Use-Cases
  */

  testCases.STORAGE.push({
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
      //'writeDataFromKeychain', 'readDataToKeychain'
    ],
  });
  testCases.CRYPTO.push();
  testCases.AUTH.push();
  testCases.NETWORK.push();
  testCases.PLATFORM.push();
  testCases.CODE.push();
  testCases.RESILIENCE.push();
}

export default testCases;
