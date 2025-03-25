const {getDefaultConfig, mergeConfig} = require('@react-native/metro-config');
const defaultConfig = getDefaultConfig(__dirname);

const jsoMetroPlugin = require('@bernhste/obfuscator-io-metro-plugin')(
  {
    compact: true,
    controlFlowFlattening: true,
    controlFlowFlatteningThreshold: 0.75, // Slightly reduced to avoid potential issues
    deadCodeInjection: true,
    deadCodeInjectionThreshold: 0.4,
    debugProtection: false, // Disabled for better React Native compatibility
    disableConsoleOutput: false, // Keep console for React Native debugging
    identifierNamesGenerator: 'hexadecimal',
    numbersToExpressions: true,
    renameGlobals: false, // Safer option for React Native
    rotateStringArray: true,
    selfDefending: false, // Disabled for better React Native compatibility
    shuffleStringArray: true,
    simplify: true,
    splitStrings: true,
    splitStringsChunkLength: 5,
    stringArray: true,
    stringArrayEncoding: ['rc4'],
    stringArrayThreshold: 0.75,
    transformObjectKeys: true,
    unicodeEscapeSequence: false, // Can cause issues with some React Native features
    target: 'browser', // Better for React Native than 'node'
  },
  {
    runInDev: false,
    logObfuscatedFiles: true, // Enable this to debug
    exclude: [/^\.\//],  // exclude all
    include: [/^src\/screens\/ctfScreen\/util\//, /^src\/screens\/ctfScreen\/game\/Game.js/],
  }
);

// Create a properly merged configuration
module.exports = mergeConfig(
  mergeConfig(defaultConfig, {
    transformer: {
      getTransformOptions: async () => ({
        transform: {
          experimentalImportSupport: false,
          inlineRequires: false,
        },
      }),
    },
  }),
  jsoMetroPlugin
);
