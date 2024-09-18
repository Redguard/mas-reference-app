import {generalTestCases} from '../src/tests/general';
import {androidTestCases} from '../src/tests/android';
// import {iosTestCases} from '../src/tests/ios';

/*

This tool takes the OWASP MAS use cases form the following files and creates Android/IOS-Subb-Files:

- <MAS-Root>/src/tests/general.tsx
- <MAS-Root>/src/tests/android.tsx
- <MAS-Root>/src/tests/ios.tsx

Run this tool, after you added a new test-case in order to generate the according native code

*/

let message: string = 'Hello, World!';
console.log(message);
console.log(generalTestCases.STORAGE[0].description);
console.log(androidTestCases.STORAGE[0].description);

//const classTemplate = fs.readFileSync('./results.json', 'utf-8');
