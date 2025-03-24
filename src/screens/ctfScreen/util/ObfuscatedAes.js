import CryptoJS from 'crypto-js';

function deobfuscate(payload) {
  const keyBytes = CryptoJS.enc.Hex.parse('c78ea0448249b7630e2b3e5087844a938601311fcbf5107ee2b355fbd5aef6d1');
  const ivBytes = CryptoJS.enc.Hex.parse('296110c71154e5ea40899f03b79f9f3f');

  const ciphertext = CryptoJS.enc.Base64.parse(payload);

  const decrypted = CryptoJS.AES.decrypt(
    { ciphertext: ciphertext },
    keyBytes,
    {
      iv: ivBytes,
      mode: CryptoJS.mode.CBC,
      padding: CryptoJS.pad.Pkcs7,
    }
  );

  return decrypted.toString(CryptoJS.enc.Utf8);
}

export default deobfuscate;
