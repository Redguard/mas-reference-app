// https://obfuscator.io/#code
// don't string array rotate/shuffle

// export function getObfuscatedFlag() {
//   const seed = "You made it this far, now go further."
//   let hash = 0
//   for (let i = 0; i < seed.length; i++) {
//     hash = (hash * 31 + seed.charCodeAt(i)) % 0xffffffff
//   }

//   let hex = ""
//   for (let i = 0; i < 8; i++) {
//     const part = (hash >> (i * 4)) & 0xf
//     hex += part.toString(16)
//   }

//   while (hex.length < 32) {
//     hex += hex
//   }
//   hex = hex.substring(0, 32)

//   return [
//     hex.substring(0, 8),
//     hex.substring(8, 12),
//     `4${hex.substring(13, 16)}`,
//     `${((parseInt(hex.substring(16, 17), 16) & 0x3) | 0x8).toString(
//       16
//     )}${hex.substring(17, 20)}`,
//     hex.substring(20, 32)
//   ].join("-")
// }


function _0x1914(_0x3a7e63,_0x19141e){const _0x3c92bf=_0x3a7e();return _0x1914=function(_0x29e8d4,_0x59fd0d){_0x29e8d4=_0x29e8d4-0x0;let _0x32d026=_0x3c92bf[_0x29e8d4];if(_0x1914['kOLDip']===undefined){var _0x180f31=function(_0x4d8db0){const _0x4f9daa='abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789+/=';let _0x2fb216='',_0x2b7ceb='';for(let _0x2dec26=0x0,_0x4015c9,_0x261332,_0x1ce30e=0x0;_0x261332=_0x4d8db0['charAt'](_0x1ce30e++);~_0x261332&&(_0x4015c9=_0x2dec26%0x4?_0x4015c9*0x40+_0x261332:_0x261332,_0x2dec26++%0x4)?_0x2fb216+=String['fromCharCode'](0xff&_0x4015c9>>(-0x2*_0x2dec26&0x6)):0x0){_0x261332=_0x4f9daa['indexOf'](_0x261332);}for(let _0x15c1a1=0x0,_0x10278d=_0x2fb216['length'];_0x15c1a1<_0x10278d;_0x15c1a1++){_0x2b7ceb+='%'+('00'+_0x2fb216['charCodeAt'](_0x15c1a1)['toString'](0x10))['slice'](-0x2);}return decodeURIComponent(_0x2b7ceb);};const _0x19f290=function(_0x6c455f,_0x5e1f37){let _0x36cea7=[],_0x478d8e=0x0,_0x1ac067,_0x7f6ec4='';_0x6c455f=_0x180f31(_0x6c455f);let _0x3aa09b;for(_0x3aa09b=0x0;_0x3aa09b<0x100;_0x3aa09b++){_0x36cea7[_0x3aa09b]=_0x3aa09b;}for(_0x3aa09b=0x0;_0x3aa09b<0x100;_0x3aa09b++){_0x478d8e=(_0x478d8e+_0x36cea7[_0x3aa09b]+_0x5e1f37['charCodeAt'](_0x3aa09b%_0x5e1f37['length']))%0x100,_0x1ac067=_0x36cea7[_0x3aa09b],_0x36cea7[_0x3aa09b]=_0x36cea7[_0x478d8e],_0x36cea7[_0x478d8e]=_0x1ac067;}_0x3aa09b=0x0,_0x478d8e=0x0;for(let _0x2b83da=0x0;_0x2b83da<_0x6c455f['length'];_0x2b83da++){_0x3aa09b=(_0x3aa09b+0x1)%0x100,_0x478d8e=(_0x478d8e+_0x36cea7[_0x3aa09b])%0x100,_0x1ac067=_0x36cea7[_0x3aa09b],_0x36cea7[_0x3aa09b]=_0x36cea7[_0x478d8e],_0x36cea7[_0x478d8e]=_0x1ac067,_0x7f6ec4+=String['fromCharCode'](_0x6c455f['charCodeAt'](_0x2b83da)^_0x36cea7[(_0x36cea7[_0x3aa09b]+_0x36cea7[_0x478d8e])%0x100]);}return _0x7f6ec4;};_0x1914['MicXXD']=_0x19f290,_0x3a7e63=arguments,_0x1914['kOLDip']=!![];}const _0x32e26e=_0x3c92bf[0x0],_0x332f4c=_0x29e8d4+_0x32e26e,_0x1ed6ba=_0x3a7e63[_0x332f4c];return!_0x1ed6ba?(_0x1914['ftojBP']===undefined&&(_0x1914['ftojBP']=!![]),_0x32d026=_0x1914['MicXXD'](_0x32d026,_0x59fd0d),_0x3a7e63[_0x332f4c]=_0x32d026):_0x32d026=_0x1ed6ba,_0x32d026;},_0x1914(_0x3a7e63,_0x19141e);}function _0x3a7e(){const _0x3d49ac=['bgfdW5NdJsJcOmklW53dVhGXW7PQWPFcSSoNWOBcOSkxWRpcMYNdTSk9WOnpW4zdW4BcSNdcGsLRpSod','W6HznbyW','zIRdN399','cSohWOPfemoH','p8kiWR8eWOO','W4jmp2LG','wdvHaSkO','cCk1ESkJWRNdQuRcMryB','W7z8c2PG','eaiGyNG','m1Cav8oRWR1KW74','W6m+xSo6W6O','mwTyWP7dLce','BCoNp8k2DCkEdMhcOW','qWf0k8kQW58vB3W','WR1xWQVcV0W','WQeFgrRcHMlcVmod'];_0x3a7e=function(){return _0x3d49ac;};return _0x3a7e();}export function getObfuscatedFlag(){const _0x5bcb16=_0x1914,_0xce97a6={'idZDN':_0x5bcb16(0x0,'vFH^'),'fnYUH':function(_0x4a118a,_0x150bb2){return _0x4a118a<_0x150bb2;},'ZQMEw':function(_0x2290ef,_0x315be3){return _0x2290ef%_0x315be3;},'RCSQL':function(_0x205226,_0x4f99c4){return _0x205226+_0x4f99c4;},'hAwZv':function(_0x1169b6,_0x8e1174){return _0x1169b6*_0x8e1174;},'zqcXZ':function(_0x31caa3,_0x200dbd){return _0x31caa3<_0x200dbd;},'fsgRL':function(_0x7c9709,_0x385346){return _0x7c9709&_0x385346;},'vtDfd':function(_0x4318b2,_0x299001){return _0x4318b2>>_0x299001;},'SuVIO':function(_0xc306f1,_0x1e631f){return _0xc306f1*_0x1e631f;},'NXWoa':function(_0x431fd5,_0x9dbc9f,_0x225231){return _0x431fd5(_0x9dbc9f,_0x225231);}},_0x81ae37=_0xce97a6[_0x5bcb16(0x1,'lPas')];let _0x31c8c1=0x0;for(let _0x55ca0b=0x0;_0xce97a6[_0x5bcb16(0x2,'7PrI')](_0x55ca0b,_0x81ae37[_0x5bcb16(0x3,']FHM')]);_0x55ca0b++){_0x31c8c1=_0xce97a6[_0x5bcb16(0x4,'[UD#')](_0xce97a6[_0x5bcb16(0x5,'(YoP')](_0xce97a6[_0x5bcb16(0x6,'8VXD')](_0x31c8c1,0x1f),_0x81ae37[_0x5bcb16(0x7,'!KOh')](_0x55ca0b)),0xffffffff);}let _0x4560c2='';for(let _0x1e8ad0=0x0;_0xce97a6['zqcXZ'](_0x1e8ad0,0x8);_0x1e8ad0++){const _0x1189a5=_0xce97a6[_0x5bcb16(0x8,'(YoP')](_0xce97a6[_0x5bcb16(0x9,'YT5N')](_0x31c8c1,_0xce97a6['SuVIO'](_0x1e8ad0,0x4)),0xf);_0x4560c2+=_0x1189a5[_0x5bcb16(0xa,'mm5]')](0x10);}while(_0xce97a6[_0x5bcb16(0xb,'rW*x')](_0x4560c2[_0x5bcb16(0xc,'vFH^')],0x20)){_0x4560c2+=_0x4560c2;}return _0x4560c2=_0x4560c2['substring'](0x0,0x20),[_0x4560c2[_0x5bcb16(0xd,'LCox')](0x0,0x8),_0x4560c2['substring'](0x8,0xc),'4'+_0x4560c2[_0x5bcb16(0xe,'8VXD')](0xd,0x10),''+(_0xce97a6['fsgRL'](_0xce97a6[_0x5bcb16(0xf,'WxOk')](parseInt,_0x4560c2[_0x5bcb16(0xd,'LCox')](0x10,0x11),0x10),0x3)|0x8)[_0x5bcb16(0x10,'(9T^')](0x10)+_0x4560c2['substring'](0x11,0x14),_0x4560c2['substring'](0x14,0x20)]['join']('-');}


export function getScrambledPayload(target: String){
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
  else if (target === 'noDebugWinningStreak'){
    return '7a1f77fb-5aac-4bb0-b0bc-a8929ec91319';
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

// function _0x3b83(){var _0x4e0186=['wmkiWPRdSrW','WP/cUfGFDG','lSkmcMhdTa','WPxcLd1kjG','W6BdLvRcRuFcQu0','W5pcNSkvle0Aba','WRdcMYC','W7dcOCkeWRu','zSkxWPhdNq','WR52u8oMsq','WPGdbe7dV1/dQSobWR0','o8kDgghdQa','W5dcRMHktG','W4ddOmkVv8oqta','W7xdIfVcQKFcSvfoWP4','WR7cNs5r','BSoRW5RdT3XqnSoXoq','FCkMCW0','xcv2WQRcJ2SnW6S','W5lcMmkvoW','WQlcUvyPuCkqwu48WRG','W53dVHDDW5pcM3CpW5OFWQ9JjwhdH1BcSSooamojz8owgeO6W4tcM3y5t8kgWRJdGSoGfSov','xY9hWQ3cKMeiW6LPWPhdHmkBsmoQW4a','W5e+CeWvCCoxWRerA3pdK8k0WP4MWOfw','WPVdUWWACG','FSkaWQpdLtq','WQJcUZX2x8kKW6RdRmoVW7BdH8oYW5G2ofFcV0lcR8omqWRdMCkfBCkrW6BdKmkyW4WbWO7cSCkSW5q3','WQhdQmotrvG6WQ7cNCoplKtcLmkBfSohWQhcHsbUW7tdLghcN1FdN1zzl8ofWPrsfW','WPGumMVcVGNcGW','WO7dJMJdMXjuW58','WOyTpa','t8kGovG','W4hcPgPmw0XKWOddTW','WQ9Lq8oLxG','yuzxj2lcMa','W5pcNSkvlfyefcSFwcC','rmkNmvm','W4VcSqmA','E8kwWQtdHGtdLY/dRa','fY5pWP54BIxcSG','WOVcP0LC','ucCrW7X1xtldNSk0W6mVmuVdIcH3WR5RW6tdLq','fdL0WRPY','qJG1nmow','W4xcRhLfuG','W4RcS2JdQv3dUt3dKSoOWPf3rgGVfwddMCkdqhVcHSktWQtcGqSpv8knWQiBW44unaDHpa','WR7dKSkuh8oWWReu','W6RcLInuWRdcImkxtftcRMNcMIy+W7uHWOj3AmoDWR0lqHNcKJzoWRVcJmkaWQ7dLYNdGM3cMa','WOSGpZr8xY8dgs3dH8ohWPVcGSkKF8ojm8kPWPq8W5pcJHW9W5ZcUxmZevPJW78ksSkH','asb/WOf/D3G','W73cJcreEq','DCkSDCkcW5LLoCk3wmo/gmo/wNL+WQZcOmkSW4RdLbBcUmoYaSoPWO/cGCk5bCkSW4SWe8kiiX8','FSkTEv7dIhOeDgmSWOGQW5awjgvsWQ1rC1DKkCkbuNVdM8oRFCkccX/cMSourCkv','BrHQWOW'];_0x3b83=function(){return _0x4e0186;};return _0x3b83();}function _0x1fb5(_0x53bc0b,_0x5eb872){var _0x28e030=_0x3b83();return _0x1fb5=function(_0xe8cc06,_0x2e8318){_0xe8cc06=_0xe8cc06-0xde;var _0x3b838e=_0x28e030[_0xe8cc06];if(_0x1fb5['KcIJlD']===undefined){var _0x1fb503=function(_0x3aad6b){var _0x2bbae3='abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789+/=';var _0x2a5f39='',_0x2ac45f='';for(var _0x28ff36=0x0,_0x2770a7,_0x210fae,_0x1c8bc0=0x0;_0x210fae=_0x3aad6b['charAt'](_0x1c8bc0++);~_0x210fae&&(_0x2770a7=_0x28ff36%0x4?_0x2770a7*0x40+_0x210fae:_0x210fae,_0x28ff36++%0x4)?_0x2a5f39+=String['fromCharCode'](0xff&_0x2770a7>>(-0x2*_0x28ff36&0x6)):0x0){_0x210fae=_0x2bbae3['indexOf'](_0x210fae);}for(var _0x1fc782=0x0,_0x3a7b8b=_0x2a5f39['length'];_0x1fc782<_0x3a7b8b;_0x1fc782++){_0x2ac45f+='%'+('00'+_0x2a5f39['charCodeAt'](_0x1fc782)['toString'](0x10))['slice'](-0x2);}return decodeURIComponent(_0x2ac45f);};var _0x34b367=function(_0x55e2d7,_0x4dd498){var _0x57c437=[],_0x1f361d=0x0,_0x5ac63f,_0x1247b7='';_0x55e2d7=_0x1fb503(_0x55e2d7);var _0x563925;for(_0x563925=0x0;_0x563925<0x100;_0x563925++){_0x57c437[_0x563925]=_0x563925;}for(_0x563925=0x0;_0x563925<0x100;_0x563925++){_0x1f361d=(_0x1f361d+_0x57c437[_0x563925]+_0x4dd498['charCodeAt'](_0x563925%_0x4dd498['length']))%0x100,_0x5ac63f=_0x57c437[_0x563925],_0x57c437[_0x563925]=_0x57c437[_0x1f361d],_0x57c437[_0x1f361d]=_0x5ac63f;}_0x563925=0x0,_0x1f361d=0x0;for(var _0x42ba02=0x0;_0x42ba02<_0x55e2d7['length'];_0x42ba02++){_0x563925=(_0x563925+0x1)%0x100,_0x1f361d=(_0x1f361d+_0x57c437[_0x563925])%0x100,_0x5ac63f=_0x57c437[_0x563925],_0x57c437[_0x563925]=_0x57c437[_0x1f361d],_0x57c437[_0x1f361d]=_0x5ac63f,_0x1247b7+=String['fromCharCode'](_0x55e2d7['charCodeAt'](_0x42ba02)^_0x57c437[(_0x57c437[_0x563925]+_0x57c437[_0x1f361d])%0x100]);}return _0x1247b7;};_0x1fb5['JKoXSl']=_0x34b367,_0x53bc0b=arguments,_0x1fb5['KcIJlD']=!![];}var _0x1f2344=_0x28e030[0x0],_0x33b86d=_0xe8cc06+_0x1f2344,_0x283478=_0x53bc0b[_0x33b86d];return!_0x283478?(_0x1fb5['cnFUSm']===undefined&&(_0x1fb5['cnFUSm']=!![]),_0x3b838e=_0x1fb5['JKoXSl'](_0x3b838e,_0x2e8318),_0x53bc0b[_0x33b86d]=_0x3b838e):_0x3b838e=_0x283478,_0x3b838e;},_0x1fb5(_0x53bc0b,_0x5eb872);}var _0xe8cc06=(function(){var _0x4950a0=!![];return function(_0x325254,_0x33cf0f){var _0x31930d=_0x4950a0?function(){var _0x27f825=_0x1fb5;if(_0x33cf0f){if(_0x27f825(0xde,'vYFS')!==_0x27f825(0xdf,'$5[]')){var _0x3ddbbe=_0x33cf0f[_0x27f825(0xe0,'nGaw')](_0x325254,arguments);return _0x33cf0f=null,_0x3ddbbe;}else{if(_0x296fdc){var _0x47bfcd=_0xa1c0ed[_0x27f825(0xe1,'*@oK')](_0x3c4e68,arguments);return _0x5240af=null,_0x47bfcd;}}}}:function(){};return _0x4950a0=![],_0x31930d;};}()),_0x28e030=_0xe8cc06(this,function(){var _0x546845=_0x1fb5,_0x267ef0;try{var _0x148bbc=Function('return\x20(function()\x20'+'{}.constructor(\x22return\x20this\x22)(\x20)'+');');_0x267ef0=_0x148bbc();}catch(_0x5c5b1f){_0x267ef0=window;}var _0x99b9b6=_0x267ef0[_0x546845(0xe2,'f1rb')]=_0x267ef0[_0x546845(0xe3,'Ee&I')]||{},_0x4b7c44=[_0x546845(0xe4,'LIjZ'),_0x546845(0xe5,'!r9s'),_0x546845(0xe6,'vYFS'),_0x546845(0xe7,'Qz%a'),_0x546845(0xe8,'7Xls'),_0x546845(0xe9,'nGaw'),_0x546845(0xea,'x#E7')];for(var _0x43396b=0x0;_0x43396b<_0x4b7c44[_0x546845(0xeb,'MQEs')];_0x43396b++){var _0x17e4df=_0xe8cc06['constructor'][_0x546845(0xec,'f1rb')][_0x546845(0xed,'LIjZ')](_0xe8cc06),_0x33e9e2=_0x4b7c44[_0x43396b],_0x435254=_0x99b9b6[_0x33e9e2]||_0x17e4df;_0x17e4df[_0x546845(0xee,'#L#Z')]=_0xe8cc06[_0x546845(0xef,'ruvg')](_0xe8cc06),_0x17e4df[_0x546845(0xf0,'2RR8')]=_0x435254['toString'][_0x546845(0xf1,'Ee&I')](_0x435254),_0x99b9b6[_0x33e9e2]=_0x17e4df;}});_0x28e030();export function getScrambledFlags(_0x53914b){var _0x46fcef=_0x1fb5;if(_0x53914b===_0x46fcef(0xf2,'$5[]'))return _0x46fcef(0xf3,'QS)P');else{if(_0x53914b==='footer')return'63b29c64-033c-44fd-bf95-d93b87c609da';else{if(_0x53914b===_0x46fcef(0xf4,'2RR8'))return'wss://f5bee9ca-1e4d-40b5-be3c-7000d57ed8e5.mas-reference-app.org:6001';else{if(_0x53914b===_0x46fcef(0xf5,'merW')){if(_0x46fcef(0xf6,'hFla')!==_0x46fcef(0xf7,'vYFS'))return _0x46fcef(0xf8,'hFla');else{var _0x2766af;try{var _0x575c7c=_0x1a3f57('return\x20(function()\x20'+_0x46fcef(0xf9,'J&7M')+');');_0x2766af=_0x575c7c();}catch(_0x58d33c){_0x2766af=_0x4a93d8;}var _0x1e1bb0=_0x2766af[_0x46fcef(0xfa,'@K$2')]=_0x2766af[_0x46fcef(0xfb,'O(]4')]||{},_0x16fe8c=[_0x46fcef(0xfc,'Z[jG'),'warn',_0x46fcef(0xfd,'GB0N'),'error',_0x46fcef(0xfe,'x#E7'),_0x46fcef(0xff,'Qz%a'),'trace'];for(var _0x132ad3=0x0;_0x132ad3<_0x16fe8c[_0x46fcef(0x100,'R(LR')];_0x132ad3++){var _0x2f1acf=_0x5a9be0[_0x46fcef(0x101,'Ee&I')]['prototype'][_0x46fcef(0x102,'GB0N')](_0x8d2bab),_0x4abac3=_0x16fe8c[_0x132ad3],_0x3a86b4=_0x1e1bb0[_0x4abac3]||_0x2f1acf;_0x2f1acf['__proto__']=_0x476869[_0x46fcef(0x103,'lI43')](_0xc6e448),_0x2f1acf[_0x46fcef(0x104,'vYFS')]=_0x3a86b4[_0x46fcef(0x105,'toQi')][_0x46fcef(0x106,'QS)P')](_0x3a86b4),_0x1e1bb0[_0x4abac3]=_0x2f1acf;}}}else{if(_0x53914b===_0x46fcef(0x107,'u3jI')){if(_0x46fcef(0x108,'toQi')===_0x46fcef(0x109,'fA[N')){var _0x36bb6e=_0x1fc782?function(){var _0x2b076b=_0x46fcef;if(_0x5ac63f){var _0x47fd7d=_0x1b7d44[_0x2b076b(0x10a,'x#E7')](_0x5e7424,arguments);return _0x26a9e8=null,_0x47fd7d;}}:function(){};return _0x1f361d=![],_0x36bb6e;}else return _0x46fcef(0x10b,'9(Ni');}else{if(_0x53914b===_0x46fcef(0x10c,'Z)hT'))return _0x46fcef(0x10d,'LIjZ');else{if(_0x53914b==='backup2')return _0x46fcef(0x10e,'Z[jG');else return _0x53914b===_0x46fcef(0x10f,'toQi')?_0x46fcef(0x110,'OBoy')==='Nanjw'?_0x46fcef(0x111,'&v!N'):_0x46fcef(0x112,'ruvg'):_0x46fcef(0x113,'2RR8');}}}}}}}
