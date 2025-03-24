/* eslint-disable eslint-comments/no-unlimited-disable */
/* eslint-disable */

// https://obfuscator.io/#code
// don't string array rotate/shuffle

export function getWinnerFlag() {
  const seed = "You made it this far, now go further."
  let hash = 0
  for (let i = 0; i < seed.length; i++) {
    hash = (hash * 31 + seed.charCodeAt(i)) % 0xffffffff
  }

  let hex = ""
  for (let i = 0; i < 8; i++) {
    const part = (hash >> (i * 4)) & 0xf
    hex += part.toString(16)
  }

  while (hex.length < 32) {
    hex += hex
  }
  hex = hex.substring(0, 32)

  return [
    hex.substring(0, 8),
    hex.substring(8, 12),
    `4${hex.substring(13, 16)}`,
    `${((parseInt(hex.substring(16, 17), 16) & 0x3) | 0x8).toString(
      16
    )}${hex.substring(17, 20)}`,
    hex.substring(20, 32)
  ].join("-")
}


export function w2() {
  const seed = "This is really not the flag you are looking for: 642e7f41-1fa9-48b7-8a8b-106d02713298";
  const bytes = new Uint8Array(16);

  for (let i = 0; i < 16; i++) {
    let h = 0;
    for (let j = 0; j < seed.length; j++) {
      h = Math.imul(31, h) + seed.charCodeAt(j) + i;
    }
    for (let r = 0; r < 3; r++) {
      h ^= h >>> 11;
      h = Math.imul(h, 0x5bd1e995);
      h ^= h >>> 15;
    }
    bytes[i] = h & 0xff;
  }
  return [
    bytes.slice(0, 4).map(b => b.toString(16).padStart(2, '0')).join(''),
    bytes.slice(4, 6).map(b => b.toString(16).padStart(2, '0')).join(''),
    (bytes[6] & 0x0f | 0x40).toString(16).padStart(2, '0') + 
      bytes[7].toString(16).padStart(2, '0'),
    (bytes[8] & 0x3f | 0x80).toString(16).padStart(2, '0') + 
      bytes[9].toString(16).padStart(2, '0'),
    bytes.slice(10, 16).map(b => b.toString(16).padStart(2, '0')).join('')
  ].join('-');
}


export function w3(target){
  if(target === 'scoreboardDomain'){
    return 'https://scoreboard.mas-reference-app.org/scoreboard.html';
  }
  else if (target === 'footerDomain'){
    return 'https://teams.mas-reference-app.org/footer.html';
  }
  else if (target === 'wssPassword'){
    return 'b7c4de22-2366-4ba3-946a-820a42a8e733';
  }
  else if (target === 'wssDomain'){
    return 'wss://update.mas-reference-app.org:6001';
  }
  else if (target === 'websocketPassword'){
    return 'b7c4de22-2366-4ba3-946a-820a42a8e733';
  }
  else if (target === 'noDebugPerfectGame'){
    return '7a1f77fb-5aac-4bb0-b0bc-a8929ec91319';
  }
  else if (target === 'serverValidatedWin'){
    return 'd23eac76-aa29-4348-9136-4f5704ab870a ';
  }
  else if (target === 'backup1'){
    return '6bcaf14d-295a-4f8f-ae72-2fccb4a8c1d3';
  }
  else if (target === 'backup2'){
    return 'abd7fc63-9aca-4ce5-93af-f771b14983fb';
  }
  else if (target === 'backup3'){
    return '0472a3ed-4270-425e-a949-c3c77a30eaf2';
  }
  else{
    return 'EROR';
  }
}
