import aesjs from 'aes-js';

// PKCS7 Padding (Ensures 16-byte blocks)
class PKCS7 {
  static pad(data, blockSize) {
    const padLength = blockSize - (data.length % blockSize);
    const padding = new Uint8Array(padLength).fill(padLength);
    return new Uint8Array([...data, ...padding]);
  }

  static unpad(paddedData) {
    const padLength = paddedData[paddedData.length - 1];
    if (padLength < 1 || padLength > paddedData.length) {
      throw new Error("Invalid padding");
    }
    return paddedData.slice(0, -padLength);
  }
}

// Example AES encryption using aes-js with padding
const key = aesjs.utils.hex.toBytes('5a9ae0bae7692e2063457011ff08c275'); // Must be 16, 24, or 32 bytes


const tips = [
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
  'You\'re a diamond—pressure only makes you shine brighter.',
  'Success starts with a single step. Take it today!',
  'Be the reason someone smiles today.',
  'You\'ve got this! The best is yet to come.',
  'Mistakes are proof that you\'re trying.',
  'Hustle until your haters ask if you\'re hiring.',
  'Your vibe attracts your tribe—stay positive!',
  'The harder you work, the luckier you get.',
  'Wake up, kick butt, and repeat.',
  'Don\'t stop until you\'re proud.',
  'If opportunity doesn\'t knock, build a door.',
  'You\'re stronger than you think and braver than you feel.',
  'Success is a journey, not a destination—enjoy the ride!',
  'Keep going! You\'re closer than you think.',
  'Don\t dream it, be it.',
  //below are the real / hacker related tips
  'Have you looked at the logs?',
  'Sometimes the developers forget something important in the build.',
  'Sometimes it is nice to be a fly on the wall, or on the network.',
  'Obfuscation can bring you only so far.',
  'I heard CyberChef can cook very well with hard coded incidences.',
  'Ask the nice people at the Redguard booth for a flag. The code phrase is the 4 character geohash of our newest member.',
  'Memory is live.',
  'Live is memory',
  'Try harder...',
  'If you are VERY lucky, you will find fortune in these tips.',
  'Your victory is right around the CORNER. Never give up.',
  'The journey starts at north-west.',
  'Cherish your luck! There is only a 1:1000 change that you see this tip. Have your self a flag: 26b60c38-7d0c-43d5-9716-26281ab9ad9a',
];


for (var tip in tips) {
  var textBytes = aesjs.utils.utf8.toBytes(tips[tip]);
  textBytes = PKCS7.pad(textBytes, 16);
  const aesEcb = new aesjs.ModeOfOperation.ecb(key);
  const encryptedBytes = aesEcb.encrypt(textBytes);
  tips[tip] = aesjs.utils.hex.fromBytes(encryptedBytes);
  const encryptedBytesDecoded = aesjs.utils.hex.toBytes(tips[tip]);
  const decryptedBytes = aesEcb.decrypt(encryptedBytesDecoded);
  const unpaddedBytes =  PKCS7.unpad(decryptedBytes);
  const decryptedText = aesjs.utils.utf8.fromBytes(unpaddedBytes);
  console.log('Decrypted:', decryptedText);
}


console.log(tips);


