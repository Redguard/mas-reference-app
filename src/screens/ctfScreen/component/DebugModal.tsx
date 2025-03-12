// DebugModal.js
import React, { useRef, useState } from 'react';
import { Buffer } from 'buffer';
import LottieView from 'lottie-react-native';
import { Modal, View, Text, StyleSheet, Pressable, NativeModules } from 'react-native';

import Clipboard from '@react-native-clipboard/clipboard';

const { WelcomeCTF } = NativeModules;

import { Game } from '../game/Game';

interface Props {
  game: Game;
  crashTheApp: Function;
}

export function DebugModal({game, crashTheApp}: Props) {
  const [, setTapSequence] = useState([]);
  const [isDebugVisible, setIsDebugVisible] = useState(false);

  // TODO: Change back once the challenge is finished
  const correctSequence = ['top-left', 'top-right', 'bottom-right', 'bottom-left'];
  // const correctSequence = ['top-left'];

  function otpDecode(base64String: string, hexKey: string): string {
    const encodedBytes: Buffer = Buffer.from(base64String, 'base64');
    const keyBytes: Buffer = Buffer.from(hexKey, 'hex');
      const decodedBytes: Buffer = Buffer.from(
      encodedBytes.map((byte, index) => {
        const keyByte = keyBytes[index % keyBytes.length];
        return byte ^ keyByte;
      })
    );
    return decodedBytes.toString('utf-8');
  }

  const handleTap = (corner: string) => {
    console.log(corner + ' tapped...')
    setTapSequence((prevSequence) => {
      const newSequence = [...prevSequence, corner];
      if (newSequence.join(',') === correctSequence.join(',')) {
        setIsDebugVisible(true);
        return [];
      }
      if (!correctSequence.slice(0, newSequence.length).every((v, i) => v === newSequence[i])) {
        return [];
      }
      return newSequence;
    });
  };

  const confettiRef = useRef<LottieView>(null);

  function triggerConfetti() {
    confettiRef.current?.reset();
    confettiRef.current?.play(1);
  }

  return (
    <View style={styles.overlay}>
      <Pressable
        style={[styles.corner, styles.topLeft]}
        onPressIn={() => handleTap('top-left')}
      />
      <Pressable
        style={[styles.corner, styles.topRight]}
        onPressIn={() => handleTap('top-right')}
      />
      <Pressable
        style={[styles.corner, styles.bottomRight]}
        onPressIn={() => handleTap('bottom-right')}
      />
      <Pressable
        style={[styles.corner, styles.bottomLeft]}
        onPressIn={() => handleTap('bottom-left')}
      />
      <Modal
        visible={isDebugVisible}
        transparent={true}
        animationType="slide"
      >

      <View style={styles.lottieWrapper} pointerEvents="none">
        <LottieView
          ref={confettiRef}
          source={require('./assets/confetti.json')}
          autoPlay={true}
          loop={false}
          style={styles.lottie}
          resizeMode="cover"
        />
      </View>
        <View style={styles.modalContainer}>
          <View style={styles.modalContent}>
            <Text style={styles.title}>Debug Screen</Text>
            <Text style={styles.text}>You deserve a flag:</Text>
            <Pressable onPress={() => Clipboard.setString(otpDecode('3KTlTD9mgaCBfdIXbzhdZaRRNKhVMi1JZh+4dYEvlWeNyNhV', 'edc0872a5b5fb692ac49b1245b15695dc06419ca60534f64522f8c44e218f057bcf8bc609e07f0ed9add6e4289c5e937aa39e86bd24aa0aa0f5570ab96265e9eda7decbeed34cbf5'))} >
                <Text style={styles.text}>{otpDecode('3KTlTD9mgaCBfdIXbzhdZaRRNKhVMi1JZh+4dYEvlWeNyNhV', 'edc0872a5b5fb692ac49b1245b15695dc06419ca60534f64522f8c44e218f057bcf8bc609e07f0ed9add6e4289c5e937aa39e86bd24aa0aa0f5570ab96265e9eda7decbeed34cbf5')}</Text>
              </Pressable>
            <Text style={styles.text}>{}</Text>

            <Pressable style={styles.button} onPress={function(){
              game.showAllCards();
              setIsDebugVisible(false);
            }}>
              <Text>New Game with a Head Start</Text>
            </Pressable>
            {/* <Pressable style={styles.button} onPress={function(){
              setIsDebugVisible(false);
            }}>
              <Text>Load Secret Game-State</Text>
            </Pressable> */}
            <Pressable style={styles.button} onPress={function(){
              WelcomeCTF.enableExperimentalGui();
              crashTheApp();
              setIsDebugVisible(false);
            }}>
              <Text>‼️ Enable Experimental GUI ‼️</Text>
            </Pressable>

            <Pressable style={styles.button} onPress={function(){
              triggerConfetti();
            }}>
            <Text>Confetti 🎉</Text>
            </Pressable>
            <Pressable style={styles.button} onPress={function(){
              setIsDebugVisible(false);
            }}>
              <Text>Close</Text>
            </Pressable>
          </View>
        </View>
      </Modal>
      </View>
  );
}

const styles = StyleSheet.create({
  overlay: {
    position: 'absolute',
    top: 0,
    left: 0,
    right: 0,
    bottom: 0,
    // backgroundColor: 'rgba(18, 171, 102, 0.27))',
    zIndex: 100, // Ensure it appears above all other content
  },
  corner: {
    position: 'absolute',
    width: 100,
    height: 100,
    // backgroundColor: 'rgba(171, 18, 18, 0.6))',
  },
  topLeft: {
    top: 0,
    left: 0,
  },
  topRight: {
    top: 0,
    right: 0,
  },
  bottomRight: {
    bottom: 0,
    right: 0,
  },
  bottomLeft: {
    bottom: 0,
    left: 0,
  },
  modalContainer: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: 'rgba(0, 0, 0, 0.5)',
  },
  modalContent: {
    width: '80%',
    padding: 20,
    backgroundColor: 'white',
    borderRadius: 10,
    alignItems: 'center',
  },
  title: {
    fontSize: 20,
    fontWeight: 'bold',
    marginBottom: 10,
  },
  text: {
    margin: 5,
  },
  button: {
    alignItems: 'center',
    backgroundColor: '#02b3e4',
    padding: 10,
    margin: 10,
    width: '100%',
    pointerEvents: 'box-only',
  },
  lottieWrapper: {
    position: 'absolute',
    top: 0,
    left: 0,
    right: 0,
    bottom: 0,
    zIndex: 1000,
    pointerEvents: 'none',
  },
  lottie: {
    flex: 1,
  },
});

