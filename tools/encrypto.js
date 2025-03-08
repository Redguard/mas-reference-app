import aesjs from 'aes-js';

// PKCS7 Padding (Ensures 16-byte blocks)
const pkcs7Pad = (textBytes, blockSize = 16) => {
  const paddingLength = blockSize - (textBytes.length % blockSize);
  const padding = new Uint8Array(paddingLength).fill(paddingLength);
  return new Uint8Array([...textBytes, ...padding]);
};

// PKCS7 Unpadding (Removes extra bytes)
const pkcs7Unpad = (paddedBytes) => {
  const paddingLength = paddedBytes[paddedBytes.length - 1];

  // Validate padding value (prevents corruption issues)
  if (paddingLength < 1 || paddingLength > 16) {
      throw new Error("Invalid padding");
  }

  return paddedBytes.slice(0, -paddingLength);
};

// Example AES encryption using aes-js with padding
const key = aesjs.utils.utf8.toBytes('5a9ae0bae7692e2063457011ff08c275'); // Must be 16, 24, or 32 bytes


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
  //below are the real tips
  'Have you looked at the logs?',
  'Sometimes the developers forget something important in the build.',
  'Sometimes it is nice to be a fly on the wall, or on the network.',
  'Obfuscation can bring you only so far.',
  'I heard CyberChef can cook very well with hard coded incidences.',
  'Ask the nice people at the Redguard booth for a flag. The code phrase is the 4 character geohash of our newest member.', // location of redguard office neuchatel
  'Memory is live.',
  'Live is memory',
  'If you are VERY lucky, you will find fortune in these tips.',
  'Your victory is right around the CORNER. Never give up.',
  'The sun goes up in north-west.',
  'Cherish your luck! There is only a 1:1000 change that you see this tip. Have your self a flag: 26b60c38-7d0c-43d5-9716-26281ab9ad9a',
];


for (var tip in tips) {
  // Convert text to bytes first
  var textBytes = aesjs.utils.utf8.toBytes(tips[tip]);

  // Apply padding only ONCE
  textBytes = pkcs7Pad(textBytes);

  // Encrypt using AES-ECB
  const aesEcb = new aesjs.ModeOfOperation.ecb(key);
  const encryptedBytes = aesEcb.encrypt(textBytes);

  // Convert encrypted data to hex string
  tips[tip] = aesjs.utils.hex.fromBytes(encryptedBytes);

  // Convert hex string back to bytes
  const encryptedBytesDecoded = aesjs.utils.hex.toBytes(tips[tip]);

  // Decrypt
  const decryptedBytes = aesEcb.decrypt(encryptedBytesDecoded);

  // Remove PKCS7 padding properly
  const unpaddedBytes = pkcs7Unpad(decryptedBytes);
  const decryptedText = aesjs.utils.utf8.fromBytes(unpaddedBytes);

  console.log('Decrypted:', decryptedText);
}


console.log(tips);


