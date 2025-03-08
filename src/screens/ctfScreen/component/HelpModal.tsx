import { Modal, Pressable, StyleSheet, Text, TouchableWithoutFeedback, View } from 'react-native';
import { Color } from '../style/Color';
import React, { useState, useEffect } from 'react';
import aesjs from 'aes-js';
import Clipboard from '@react-native-clipboard/clipboard';


interface Props {
  /** Invoked when the modal automatically closes or is dismissed by a click. */
  onClose: () => void;
}

const tips = [
  '2b0fd92bee2f33eb98374c37937e674f1705b35eb5d1a33dabd712d6da3716f7',
  '74d54e1862c0a87fe8823c6a25250884fb2973dde3a383dbb03529fa3a11c5deb83df994602897e8fed2fe791a3a369a',
  'c928cd1e72881412f6dc0c47274c93d8e4cd3cecf2fd7af5c5355dc70438ecc7c613e4c8506c1722a182f2b08b81f40d',
  '1582a944e7d61ccb5dcae98c659d239b5a60d072d8e463fcdcd98bbb97a6078fda6e2e74539ad1867e45c49f6bf74394',
  '4cf5af7ea09354d4f152e2092ff29ea03366af42426514420ae3c7a8afd313578cfac73437b91da170e680becf8ab92f',
  '8da69effeba3c381f0ab32dcc72ca308d889f648abeabc8ef3974e43b60154f1',
  '38cb90102dc815e74603970e0bace999f3f4a4a11ee43653c06753698e477d69b90d705955d3ea8a3f64d6d6e914ca4e2b35d07bff84089f66a4d75ff1b52e6c',
  '40dbca3afe90bbf4724bf9f3cb3b330fd2941ed33c4e098432a5da86855bd86932c7ff40fd4d3422c066b4f11d85f042',
  'de463933ffe6f6e477a144563cf843e7718074f0cbcc54fd61bf7e49415c6763b3d73b3c785a72cfb8b210e59da866fe',
  'f63df819db396b80878786e62df6ae3abc5240f8850ac636d2f0c088b0bdd59ea8d4e9fc389a637b3dd65d923714189094c29d88c890da231ed47091e6918a88',
  '149d9f138f28a4911dde0774966e3fae709c387428a12bd97663996543430bcaa7899ce349496c02ad2d41a67ed0fdb8',
  '8dd4218f7e6d7bf2a1cd31632c2ab3be521c786d5f8db88b896b75edab6ce23483d8b2b4bb371b47c6f2a38333c055133e9e530043a26a2a11f42c9b97ebc622',
  'b5b8e6fa5645c631785f893de0fb3219d0bcb2ddec850d5b51b48e6cede547e8bc77fac47981f942c014037659695b563030ef29e8a37d8104ff60318b5c221a',
  '8b377ac516146d7fc408f5b8dd46be5f6ce36d88552a95ce854c81867bd35e4d146102546195d0aa80784696244f8ffbb9b94ad1ba5fd3a425a56e7f35beb4ff',
  '1e36dde8aaa925c39e508211256696a3ead77a121d0ef5d1251eb66d0a9c85c83811bc43f3d27b971a135f380fb411fa',
  'fdc1980997f66769b808a33cfcaa2b178ce9f5f9dc45698cd05a729eee779ac49b59601787d2e4594d5bfa9bb7195d09',
  '68062aa4cda1f560e3e6193454864d2de324e88f8c21eb4c02767bb6f68f4562ea596db8cf4ccf445e747b54a3f3902a',
  'd92501a37fdaf802a014b0650d065502609d7500c390e1d4560711fbe9d2c629c290eaad782e8ec8dd217dd444580cef',
  '29b0c659b9a26cff8f97da224a93b88575b31f154890b1926821e337962b3d4475ea10e6fd69d532697cca6db5051360',
  'f179b79d47a6159b55380ea4d1d35c47ec86469c938a406d2ff5614635592dba050f1592c7f347ebe860113592b4a506',
  'c077380ef8a1cdd7300d1ed57114a4b051922ac708ddfe97603821bb28c3061d',
  '1e3ae8919e158f9475e7f58f244d2f3824cf118ebe7e24eb8af8c83f234e62a8',
  'b9e211f7e017e872395524de0ce3562bb3337a6943dc0c82aa2ee38de3cb0606126230a88c214721f86aefb0a8945efa',
  'af6c2b4678582fe0f1e5b87d86569fc705c18f6abe9024caaa6ac81c30646ef4f7d043f856febaa6baf127a24fa56daa7f1bd8bf15f59e66555ff616614d43f0',
  '563e3f456322d784a6af2715c2caa4dc29215ee695698d34bc0b46b9647d4e935742a98367ee7454ba3d57eb2313b0e92d8bfb222f42455a09b97af0daea03b6',
  '26acca5ed5ce46980729248fabb75df3b54aec8c486b6d21f10eeb5ed2d9f90d1636535edc3d811a6464e0c3e93dbea9',
  '9a2f3d755d9e605e3addbe888897617b5cac1c2d7564238d0fe78b0f85ee023a',
  'b583eefbf322ea1a6668a3f3f37d4700332035523c5ed90d523975a9a60ec324',
  '7af717d31b7d7782e646404e96d6ad1b388a05ace9ac129c2f66400ca1336e76c53eae50346c52cc4a83db4d9b0b55884c88d144b9cdc82932cc7a1fb7e13086b83df994602897e8fed2fe791a3a369a',
  '8419ae133ac7c0cbeedf54e94a51919109f9cefbc4159a1eee7d150a79a6d86fdd5801a8b4ce064d8b4e2e37ce63a914b07bd9b85b906b506cfc3b701d482e4dc613e4c8506c1722a182f2b08b81f40d',
  'a56fd3db666e2ee72a2a298b483d23fcf6d3bf3e253866754ffe68cf719fd9e68e0f64d1305b06e3b74115d72edbf07b',
  '48e415ad0363f6771f0a13c58221a1e2d770dd2973bbe20f1fa46cff46af1ffcdedbd5bc5fccd762750a13f9713ae2ca4277ea5271c8b5a5addc2975b0daf16ac613e4c8506c1722a182f2b08b81f40d',
  '9c3786d37b669e5455c4cb41d0d028c96d9458d18c3a431231e343e5524cdf9cabbf506702dff1c8640cc0f400e1757bc9a3582d972a30bc5667f11530b8b8ae08f41b60670d3a0142783749b0bbaf3bd11dc45b00a38caba405c6cc93984857c9c6394d52aff44b03c848e9f60d5a2350eaaba40ddd88fe0abdbe1753b9abd3',
  '91d04a006f7155021f13ff2944886edd',
  '54c1169f541edc0dbafa6175c5b41e97',
  '56ca5a2047bb5ceafbaf554bdbd70bf62748e1480f57bce63302182c74659e7cf10892b8e6c0458816f80140c5fd1a4846c6539649e8e9a6ed33b0a3f71cb93b'
];

export function HelpModal({ onClose }: Props) {
  const [tip, setTip] = useState('');

  const pkcs7Unpad = (paddedBytes: Uint8Array): Uint8Array => {
    const paddingLength = paddedBytes[paddedBytes.length - 1];
    if (paddingLength < 1 || paddingLength > 16) {
        throw new Error('Invalid padding');
    }
    return paddedBytes.slice(0, -paddingLength);
  };

  useEffect(() => {
    var randomTip = tips[Math.floor(Math.random() * tips.length)];

    if ( Math.random() < 1 / 1000){
      randomTip = '78e0646d85bfec41cca6724bb84a20b18e7d5e49a49b826fb3a9bb77ee6be0d02f5689c013e3416687afc8ae989a49908303c4e97631ce4fa70636e83720f4e7182ca0e970aa6b5d68d90036702d8c7d8ab2f841f60b98cebd243b18de9003f6e777bb7091e150c5203e76344bd698f3335ca2bed4fff9812ec37cb17a2dff51c6731684b3cf350fbaa180fe1e3c44a5';
    }

    const aesEcb = new aesjs.ModeOfOperation.ecb(aesjs.utils.utf8.toBytes('5a9ae0bae7692e2063457011ff08c275'));
    const encryptedBytes: Uint8Array = aesjs.utils.hex.toBytes(randomTip);
    const encryptedBytesDecoded: Uint8Array = aesEcb.decrypt(encryptedBytes);
    const unpaddedBytes: Uint8Array = pkcs7Unpad(encryptedBytesDecoded);
    const plainTip: string = aesjs.utils.utf8.fromBytes(unpaddedBytes);
    setTip(plainTip);
    // Automatically close the modal after 5 seconds
    const timer = setTimeout(() => {
      onClose();
    }, 5000);

    // Cleanup the timer on unmount
    return () => clearTimeout(timer);
  }, [onClose]);

  return (
    <Modal animationType="slide" transparent={true} onRequestClose={onClose}>
      <TouchableWithoutFeedback onPress={onClose}>
        <View style={styles.modalContainer}>
          <View style={styles.textContainer}>
            <Text style={styles.text}>Tip of the moment is:</Text>
              <Pressable onPress={() => Clipboard.setString(tip)} >
                <Text style={styles.tipText}>{tip}</Text>
              </Pressable>
          </View>
        </View>
      </TouchableWithoutFeedback>
    </Modal>
  );
}

const styles = StyleSheet.create({
  modalContainer: {
    zIndex: 9999,
    flex: 1,
    justifyContent: 'flex-end', // Align the modal content at the bottom
    backgroundColor: 'transparent', // Ensure the rest of the app is visible
  },
  textContainer: {
    height: '20%', // Make the modal content only 10% of the screen height
    backgroundColor: Color.blue, // Background for the bottom section
    padding: 20,
    borderTopLeftRadius: 20,
    borderTopRightRadius: 20,
    justifyContent: 'center', // Center the text vertically
  },
  text: {
    color: Color.dark,
    fontSize: 18,
    marginBottom: 10,
  },
  tipText: {
    color: Color.dark,
    fontSize: 16,
    fontStyle: 'italic',
  },
});
