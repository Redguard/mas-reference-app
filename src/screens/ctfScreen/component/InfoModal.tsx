import {Linking, Modal, Pressable, StyleSheet, Text, View} from 'react-native';
import {Color} from '../style/Color';
import React from 'react';

interface Props {
  /** Invoked when the close button is pressed. */
  onClose: () => void;
}

export function InfoModal({onClose}: Props) {
  return (
    <Modal animationType="slide">
      <View style={styles.modalContainer}>
        <Pressable
          style={({pressed}) => [
            styles.closePressable,
            {
              backgroundColor: pressed ? Color.redLight : Color.red,
            },
          ]}
          onPress={onClose}>
          <Text style={styles.closeText} accessibilityHint="Close">
            X
          </Text>
        </Pressable>
        <View style={styles.textContainer}>
          <Text style={styles.text}>Hi!</Text>
          <Text style={styles.text}>
            The CTF was built on top of a game developed by Albert Vila Calvo.
          </Text>
          <Text style={styles.text}>
            He gave us permission to use his App as a baseline for our CTF and we want to thank him for that ❤️
          </Text>
          <Text style={styles.text}>
            The original source code is available at{' '}
            <Text
              style={styles.link}
              onPress={() =>
                Linking.openURL(
                  'https://github.com/AlbertVilaCalvo/React-Native-Memory-Game',
                )
              }>
              https://github.com/AlbertVilaCalvo/React-Native-Memory-Game
            </Text>
          </Text>
        </View>
      </View>
    </Modal>
  );
}

const styles = StyleSheet.create({
  modalContainer: {
    zIndex: 9999,
    flex: 1,
    padding: 40,
    backgroundColor: Color.blue,
  },
  closePressable: {
    backgroundColor: Color.red,
    alignSelf: 'flex-end',
    width: 50,
    height: 50,
    borderRadius: 40,
    justifyContent: 'center',
    alignItems: 'center',
  },
  closeText: {
    fontSize: 20,
  },
  textContainer: {
    flex: 0.75,
    justifyContent: 'center',
  },
  text: {
    color: Color.dark,
    fontSize: 18,
    marginBottom: 15,
  },
  link: {
    textDecorationLine: 'underline',
  },
});
