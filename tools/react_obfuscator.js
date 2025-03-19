import aesjs from 'aes-js';

class PKCS7 {
  static pad(data, blockSize) {
    const padLength = blockSize - (data.length % blockSize);
    const padding = new Uint8Array(padLength).fill(padLength);
    return new Uint8Array([...data, ...padding]);
  }

  static unpad(paddedData) {
    const padLength = paddedData[paddedData.length - 1];
    if (padLength < 1 || padLength > paddedData.length) {
      throw new Error('Invalid padding');
    }
    return paddedData.slice(0, -padLength);
  }
}

const key = aesjs.utils.hex.toBytes('5a9ae0bae7692e2063457011ff08c275');

const tips = [
  // cheesy motivational platitudes
  'Live, Love, Laugh.',
  'Stay hydrated throughout the day.',
  'Take short breaks while working.',
  'Exercise regularly for a healthy mind and body.',
  'Read a book to expand your knowledge.',
  'Practice gratitude daily.',
  'Believe in yourself, and the world will believe in you too!',
  'Every day is a second chance to sparkle.',
  'Dream big, work hard, and stay humble.',
  'You\'re one step closer to greatness with every small win.',
  'The only limit is the one you set for yourself.',
  "Turn your can'ts into cans and your dreams into plans.",
  'Success starts with a single step. Take it today!',
  'Be the reason someone smiles today.',
  'You\'ve got this! The best is yet to come.',
  'Your vibe attracts your tribe—stay positive!',
  'Wake up, kick butt, and repeat.',
  'Don\'t stop until you\'re proud.',
  'If opportunity doesn\'t knock, build a door.',
  'You\'re stronger than you think and braver than you feel.',
  'Success is a journey, not a destination—enjoy the ride!',
  'Keep going! You\'re closer than you think.',
  'Don\'t dream it, be it.',
  // below are the non-generic tips
  'Have you looked at the logs?',
  'Sometimes the developers forget something important in the build.',
  'Sometimes it is nice to be a fly on the wall, or on the network.',
  'Obfuscation can bring you only so far.',
  'I heard CyberChef can cook very well with hard coded incidences.',
  'Memory is live.',
  'Live is memory',
  'If you are VERY lucky, you will find fortune in these tips.',
  'Fortune is right around the CORNER.',
  'The journey starts at north-west.',
  'Cherish your luck! There is only a 1:1000 change that you see this tip. Have your self a flag: 26b60c38-7d0c-43d5-9716-26281ab9ad9a',
];


for (var t in tips) {
  var textBytes = aesjs.utils.utf8.toBytes(tips[t]);
  textBytes = PKCS7.pad(textBytes, 16);
  const aesEcb = new aesjs.ModeOfOperation.ecb(key);
  const encryptedBytes = aesEcb.encrypt(textBytes);
  tips[t] = aesjs.utils.hex.fromBytes(encryptedBytes);
  const encryptedBytesDecoded = aesjs.utils.hex.toBytes(tips[t]);
  const decryptedBytes = aesEcb.decrypt(encryptedBytesDecoded);
  const unpaddedBytes =  PKCS7.unpad(decryptedBytes);
  const decryptedText = aesjs.utils.utf8.fromBytes(unpaddedBytes);
  console.log('Decrypted:', decryptedText);
}


console.log(tips);

