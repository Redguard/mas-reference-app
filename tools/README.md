# Intro

We use custom code/secret obfuscation for the CTF game. 

For kotlin we use RC4 encrypted strings and for react we use AES.ECB encrypted strings.

These tools generate the strings, which can then be used in the CTF game.


## Build kotlin strings


1. Compile the code: `kotlinc ./kotlin_obfuscator.kt -include-runtime -d kotlin_obfuscator.jar`
2. Execute the jar: `java -jar kotlin_obfuscator.jar`


## Build react native strings

1. Execute the js: `node react_obfuscator.js`
