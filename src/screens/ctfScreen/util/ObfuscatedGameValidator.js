// this code validates on the server
// this code will be obfuscated in the release build
import * as secp from '@noble/secp256k1';
import { secp256k1 } from '@noble/curves/secp256k1';
import { sha256 } from 'react-native-sha256';

async function verifySignature(payload, signature) {
  try {
    const hashHex = await sha256(JSON.stringify(payload));
    const hashBytes = secp.etc.hexToBytes(hashHex);

    const signatureFromDer = secp256k1.Signature.fromDER(signature);

    return secp.verify(signatureFromDer, hashBytes, secp.etc.hexToBytes('046fc4d3181ffd1d561efa1c7878ecf89dc0235973399b19c68dbd7a2dee238c5a721d01abf001fe9519595b96d994ada8672ad54fe9d257eb66ddcd26a480208f'));
  } catch (error) {
    console.error('Error verifying signature:', error);
    return false;
  }
}

export default verifySignature;
