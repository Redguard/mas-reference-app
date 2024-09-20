import {Platform} from 'react-native';
import {generalTestCases} from './tests/general';
import {androidTestCases} from './tests/android';
import {iosTestCases} from './tests/ios';

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

  testCases.STORAGE = testCases.STORAGE.concat(iosTestCases.STORAGE);
  testCases.CRYPTO = testCases.CRYPTO.concat(iosTestCases.CRYPTO);
  testCases.AUTH = testCases.AUTH.concat(iosTestCases.AUTH);
  testCases.NETWORK = testCases.NETWORK.concat(iosTestCases.NETWORK);
  testCases.PLATFORM = testCases.PLATFORM.concat(iosTestCases.PLATFORM);
  testCases.CODE = testCases.CODE.concat(iosTestCases.CODE);
  testCases.RESILIENCE = testCases.RESILIENCE.concat(iosTestCases.RESILIENCE);
  testCases.PRIVACY = testCases.PRIVACY.concat(iosTestCases.PRIVACY);
}

export default testCases;
