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
  'c688d67590d8f64b7d72e58c17db38252cc1e2593f37f83b0bc1d797e5daee2d',
  '7f0c9de4b737fd148fd7835aca4cedea33aae1facef0afbacd61718de0d9e088f2e27ca7e01a700f187c7dde43833ca5',
  '666afdffc5b9258b41e907c52d09fd46482b28e1c5f3b42354550af950277be0a995ea60ed6c191b5433e4491f40769f',
  '2a18350163b0a3b0314002da6eeb539914d2abb57b453d1997abc901842c7556c880e63adc0a6fd549e883e35d9537ed',
  '4f175adc317da97496f0b74459d495f2f71aff325122582c81351ebf585f37840a8e191ab0426c3e0792e518ddbc501e',
  'c8538bd1b037e9da95758345f4f341900eaf1c8efedc2b007ac91afdcad06fd8',
  '493b8e75c69500a3a6941a0df7b4a97943ff2a8329e43ad68edd9c9a7ed493814ec6c89cd11539cf84dbee450fcbb05b3f5a091515c3525194e9fb223946cd7a',
  '11d15c41d1ee89ebabd6ce55f2e60922c8417ce190ce7647b84f5d394a5218ed307e0e88bf9c8ed842c60acf0bb6354f',
  '82614c2a24ddfaf17cb07ee0003aa482e29e9cc0ed4461168dbbb053f03bd3cfa4e35abda91f6d93f835097e11387fdc',
  '6de7aa9a9198b5d318fdc9fee19fcdc0cc48d7d20f0d283cbed992f129f043293b00e3fabbb02fe69e3b1ef7e55814df1aa0610ed1abaebdfa5ad13df402320b',
  '309a30696e2bb6ead9ea778ca3c20ad3d04c3ac7c010e0b7065e8d04e57560e5f5d6d8ba754b3527d3cb62b99467d79a',
  '63571e8ba33b87ee63b6df4d4dec94823119a3ef144ecbcf8ccaedf87e446498793e1ee06a208d65c17b223bdeab49925838330d72ce4645224f3abe19be1869',
  'fe87f94b29426ded0bd80e8b271bd6778d41b3fb75a4e6a76328e64f9bb6dc60555415341d33cbfefb92331ee672ee7295b488c9b7a894de9b428aacca6a6764',
  'dcbd353f14c127ccc775c37e0afa07a5fb5f15e762b072e90c7ed49e2edf0cc8ce836285664b8bf3286245955b5f15dc',
  'c09f7a1e58a57a778acf460320b932498f38b57fd47d199eaa21219c2a524e28a2a4f83280dcf109b9b8d177cd8d737d',
  '067c9f3de712c092753d2f8f2bf9aaf38b56e01b15db7655a1a4a95744ad784c10248776bdb039fa67609eb2c6708079',
  'c84be51725787169d8efdbbd1a77705ad65ab6305592a3135f95f5c12603a99e',
  'd98b12ffa94aabf52882d47740529de7f8a97ed33a731afda8d37eca09d22cf7',
  '0deb4863d88f41b30169781fc19a49b1ed559c6626c4429461f32cba10dc1881039d5bf49e43323c3cc08a4aa0561ac4',
  '409d2e64b88d69f971c30f6e5d857f3879ba8c075007f08143b08e54c31e0201db5c185f0c5d19a8cfecf2e7bb4a9ccb5a55bb3da2a1838a06d24864d389ed2b',
  'c92d9ca849674f7626ae9cb886110e5d7e93e31211a99b2d1577f18a0ab18696795b82e1bde09c0e262a1137bf09838bb17409c7bf623aa88f3744c9f5c61e33',
  '9cde1c6aacde2f62976b2359e956bd190cca3397c4d88bf56e16634c984411c3822a54a0d44665dda9dcbefe6f485b30',
  'd10cdfee91c99bc4d93fe9e016a28b6a6fa02daa80bbe09944f84490ead641ff',
  'bb363151995a2a9406f05d49de1f70f1ea9d8af6ac6768333648d8c41c45c127',
  '52642218ac5c91c750027ec3ffd7f8952151c486038a5b3e71736676a4c6c6c673f852a993dc9318e2af63e3f74c4ea8878d47ae1c0b87ceb040eda995425980f2e27ca7e01a700f187c7dde43833ca5',
  '93cc1d4580ab10d50f2fcae990d83f7fa4d6bce4098c2c24d1c8787e0274059d011b2affae64d5bdcbf2445c8003d0d8461b5fb618983a708b48c38dde5c4db4a995ea60ed6c191b5433e4491f40769f',
  'e22959fa9a23d5dd97354b032b0aab318532612757d0e379fc020c5e7ed371ef32099ccd6247e435cda220bf4e73e3d9',
  '92979ec54b53cc469355b8c3ba7bc8b9036cc6c4c3a1bcc798e47b8b4ce8bfe4c39119252bd112e47c8d416513a7870dbcff04cb41847043ced6c5033270aca1a995ea60ed6c191b5433e4491f40769f',
  'c0a2b133e98eb8f68bd27ba338019d6b',
  '66782b911313313de6787f489bfeca85',
  '55dc2af1625e5b457117e8ed6be046330668da57b343f5991a33b3a4365c25b87a3a8075075ee61a833286d19b5a73d5b764a191c64ff4ca3cb71f417041af74',
  'ac829179336ca5531d9b57307db832399dcfbece46b974bd3827c183eb1d417854deddd45d49a2240025832e13c20f0f',
  '32254279e80d2fd67ee89378ddb34a3cfa5682461d05a847242e0b1d1be0e0ecf2e27ca7e01a700f187c7dde43833ca5',
];

const pkcs7Unpad = (paddedBytes: Uint8Array): Uint8Array => {
  const paddingLength = paddedBytes[paddedBytes.length - 1];
  if (paddingLength < 1 || paddingLength > 16) {
      throw new Error('Invalid padding');
  }
  return paddedBytes.slice(0, -paddingLength);
};

const decryptFlag = (ciphertext: string, key: string): string => {
  const aesEcb = new aesjs.ModeOfOperation.ecb(aesjs.utils.hex.toBytes(key));
  const encryptedBytes: Uint8Array = aesjs.utils.hex.toBytes(ciphertext);
  const encryptedBytesDecoded: Uint8Array = aesEcb.decrypt(encryptedBytes);
  const unpaddedBytes: Uint8Array = pkcs7Unpad(encryptedBytesDecoded);
  const plainTip: string = aesjs.utils.utf8.fromBytes(unpaddedBytes);
  return plainTip;
};

export function HelpModal({ onClose }: Props) {
  const [tip, setTip] = useState('');

  // this code is not perfect on purpose. We want, that it is easier to find in the JS bytecode disassembly
  const title: string =  'Tip of the moment is:';

  useEffect(() => {
    var randomTip = tips[Math.floor(Math.random() * tips.length)];

    if ( Math.random() < 1 / 1000){
      randomTip = 'dab208345db23b4b076402141ba425013e2ff030f2e4dae3cffec49d353e32c795953a990b9178a0ee2e5eea905089faddd0e7ca8cd9427a9ecc0ccd5a737b775dead4c30c13559722c64af38902cd1ec41db2deac7cca224b2d3d031141a2f576e4537672915ecfc3eca1c3e238782cfd6fb05b6c4a0bfa7c409cc834c3fa99ba2a4b9e3f4b876d2950b141ba4371fb';
    }

    setTip(decryptFlag(randomTip, '5a9ae0bae7692e2063457011ff08c275'));

    const timer = setTimeout(() => {
      onClose();
    }, 5000);

    return () => clearTimeout(timer);
  }, [onClose]);

  return (
    <Modal animationType="slide" transparent={true} onRequestClose={onClose}>
      <TouchableWithoutFeedback onPress={onClose}>
        <View style={styles.modalContainer}>
          <View style={styles.textContainer}>
            <Text style={styles.text}>{title}</Text>
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
