/* eslint-disable no-trailing-spaces */
import {NativeModules} from 'react-native';
import {TestGroup} from '../appContent';
const {} = NativeModules;
  
interface Dictionary<Type> {
  [key: string]: Type;
}

export var iosTestCases: Dictionary<TestGroup[]> = {
  STORAGE: [
    // Define your ios storage test cases here
  ],
  CRYPTO: [
    // Define your ios crypto test cases here
  ],
  AUTH: [
    // Define your ios authentication test cases here
  ],
  NETWORK: [
    // Define your ios network test cases here
  ],
  PLATFORM: [
    // Define your ios platform-related test cases here
  ],
  CODE: [
    // Define your ios code-related test cases here
  ],
  RESILIENCE: [
    // Define your ios resilience test cases here
  ],
  PRIVACY: [
    
    // Define your ios privacy-related test cases here
  ],
};

export default iosTestCases;
