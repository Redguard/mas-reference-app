/* eslint-disable prettier/prettier */
import * as fs from 'fs';
const JSON5 = require('json5');

/*

This tool takes the OWASP MAS use cases form the following files and creates Android/iOS-Subb-Files:

- <MAS-Root>/src/tests/general.tsx
- <MAS-Root>/src/tests/android.tsx
- <MAS-Root>/src/tests/ios.tsx

Run this tool, after you added a new test-case in order to generate the according native code

*/

type TestCase = {
  title: string;
  description?: string;
  nativeFunction: string;
  maswe?: string;
};

type TestCases = {
  title: string;
  description: string;
  testCases: TestCase[];
  maswe?: string;
};


function getBasePath(){
  const arr:string[] = __filename.split('/');
  arr.splice(-2);
  const basePath = arr.join('/');
  return basePath;
}


function loadJson(path: fs.PathOrFileDescriptor) {
  const testRaw = fs.readFileSync(path, 'utf-8');
  var tests = testRaw
    .split('Dictionary<TestCases[]> = ')[1]
    .split('export default')[0]
    .replace(/;/g, '')
    .replace(/STORAGE/g, '"STORAGE"')
    .replace(/CRYPTO/g, '"CRYPTO"')
    .replace(/AUTH/g, '"AUTH"')
    .replace(/NETWORK/g, '"NETWORK"')
    .replace(/PLATFORM/g, '"PLATFORM"')
    .replace(/CODE/g, '"CODE"')
    .replace(/RESILIENCE/g, '"RESILIENCE"')
    .replace(/PRIVACY/g, '"PRIVACY"')
    .replace(/title/g, '"title"')
    .replace(/description/g, '"description"')
    .replace(/testCases/g, '"testCases"')
    .replace(/maswe/g, '"maswe"')
    .replace(/nativeFunction: (.*),/g, '"nativeFunction": "$1"');
  return JSON5.parse(tests) as Array<TestCases>;
}

function createMethod(masDomain:string, classname:string, methodName:string, target:string){
  const basePath = getBasePath();
  if (target === 'android'){
    const kotlinClass = basePath + '/android/app/src/main/java/com/masreferenceapp/' + masDomain.toLowerCase() + '/' + classname + '.kt';
    const javaClass = basePath + '/android/app/src/main/java/com/masreferenceapp/' + masDomain.toLowerCase() + '/' + classname + '.java';

    if ( fs.existsSync(kotlinClass)) {

      const existingClass = fs.readFileSync(kotlinClass, 'utf-8');

      if (!existingClass.includes('fun ' + methodName + '(): String {')){
        // method does not exist yet
        const methodContent = fs.readFileSync(basePath + '/tools/TemplateMethodKotlin.txt', 'utf-8').replace(/@methodName/gm, methodName);
        const classContent = fs.readFileSync(kotlinClass, 'utf-8').replace(/\/\/@method/gm, methodContent);
        fs.writeFileSync(kotlinClass, classContent);
        console.log('[+] Created new Kotlin Method ' + methodName + '() in Class: ' + kotlinClass);
      }

    }
    else if (fs.existsSync(javaClass)){
      // console.log('Java Class Exists')
    }
  }
  }


function createClass(masDomain:string, classname:string, target:string){

  const basePath = getBasePath();

  const kotlinClass = basePath + '/android/app/src/main/java/com/masreferenceapp/' + masDomain.toLowerCase() + '/' + classname + '.kt';
  const javaClass = basePath + '/android/app/src/main/java/com/masreferenceapp/' + masDomain.toLowerCase() + '/' + classname + '.java';
  const myAppPackageClass = basePath + '/android/app/src/main/java/com/masreferenceapp/MyAppPackage.kt';

  if (target === 'android'){
    if ( !(fs.existsSync(javaClass) || fs.existsSync(kotlinClass) )
    ) {
      // we only create kotlin stubs at the moment
      const classContent = fs.readFileSync(basePath + '/tools/TemplateClassKotlin.txt', 'utf-8').replace(/@classname/gm, classname).replace(/@masdomain/gm, masDomain.toLowerCase());
      fs.writeFileSync(kotlinClass, classContent);

      // update MyAppPackacke
      // TODO: verify if duplicate
      const myAppPackageContent = fs.readFileSync(myAppPackageClass, 'utf-8').replace(/\/\/@modules/gm, 'modules.add(com.masreferenceapp.' + masDomain.toLowerCase() + '.' + classname + '(reactContext))\n        //@modules');
      fs.writeFileSync(myAppPackageClass, myAppPackageContent);
      console.log('[+] Created new Kotlin Class: ' + kotlinClass);
    }
  }


}


function createStub(jsonObect:any, target:string) {
  for (const key in jsonObect) {
    const masDomain:string = key;

    var testCases:TestCases[] = jsonObect[key] as unknown as TestCases[];

    for (const test of testCases) {
      for (const testCase of test.testCases) {
        const className:string = testCase.nativeFunction.split('.')[0];
        const methodName:string = testCase.nativeFunction.split('.')[1];
        createClass(masDomain, className, target);
        createMethod(masDomain, className, methodName, target);
      }
    }
  }
}


const basePath = getBasePath();

const generalJson = loadJson(basePath + '/src/tests/general.tsx');
const androidJson = loadJson(basePath +  '/src/tests/android.tsx');
const iosJson = loadJson(basePath + '/src/tests/ios.tsx');


createStub(generalJson, 'android');
createStub(generalJson, 'ios');

createStub(androidJson, 'android');
createStub(iosJson, 'ios');

