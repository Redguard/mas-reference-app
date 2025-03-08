// DebugScreen.js
import React, { useState } from 'react';
import { Buffer } from 'buffer';
import { Modal, View, Text, Button, StyleSheet, TouchableOpacity } from 'react-native';

const DebugScreen = () => {
  const [, setTapSequence] = useState([]);
  const [isDebugVisible, setIsDebugVisible] = useState(false);

  // const correctSequence = ['top-left', 'top-right', 'bottom-right', 'bottom-left'];
  const correctSequence = ['top-left'];


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

      // Check if the sequence matches the correct sequence
      if (newSequence.join(',') === correctSequence.join(',')) {
        setIsDebugVisible(true); // Show the debug screen
        return []; // Reset the sequence
      }

      // If the sequence is incorrect or incomplete, keep tracking
      if (!correctSequence.slice(0, newSequence.length).every((v, i) => v === newSequence[i])) {
        return []; // Reset the sequence if it doesn't match
      }

      return newSequence;
    });
  };

  return (
    <View style={styles.overlay}>
      <TouchableOpacity
        style={[styles.corner, styles.topLeft]}
        onPress={() => handleTap('top-left')}
      />
      <TouchableOpacity
        style={[styles.corner, styles.topRight]}
        onPress={() => handleTap('top-right')}
      />
      <TouchableOpacity
        style={[styles.corner, styles.bottomRight]}
        onPress={() => handleTap('bottom-right')}
      />
      <TouchableOpacity
        style={[styles.corner, styles.bottomLeft]}
        onPress={() => handleTap('bottom-left')}
      />
      <Modal
        visible={isDebugVisible}
        transparent={true}
        animationType="slide"
      >
        <View style={styles.modalContainer}>
          <View style={styles.modalContent}>
            <Text style={styles.title}>Debug Screen</Text>
            <Text style={styles.text}>You deserve a flag:</Text>
            <Text style={styles.text}>{otpDecode('3KTlTD9mgaCBfdIXbzhdZaRRNKhVMi1JZh+4dYEvlWeNyNhV', 'edc0872a5b5fb692ac49b1245b15695dc06419ca60534f64522f8c44e218f057bcf8bc609e07f0ed9add6e4289c5e937aa39e86bd24aa0aa0f5570ab96265e9eda7decbeed34cbf5')}</Text>

            <TouchableOpacity style={styles.button} onPress={function(){
              setIsDebugVisible(false);
            }}>
              <Text>Show Cards</Text>
            </TouchableOpacity>

            <TouchableOpacity style={styles.button} onPress={function(){
              setIsDebugVisible(false);
            }}>
              <Text>Load Secret Game-State</Text>
            </TouchableOpacity>

            <TouchableOpacity style={styles.button} onPress={function(){
              setIsDebugVisible(false);
            }}>
              <Text>Confetti 🎉</Text>
            </TouchableOpacity>

            {/* <Button style={styles.button} title="Load Golden Deck" onPress={() => setIsDebugVisible(false)} />
            <Button title="Show Deck" onPress={() => setIsDebugVisible(false)} />
            <Button title="Close" onPress={() => setIsDebugVisible(false)} /> */}

          </View>
        </View>
      </Modal>
    </View>
  );
};

const styles = StyleSheet.create({
  overlay: {
    pointerEvents: 'box-none',
    position: 'absolute',
    top: 0,
    left: 0,
    right: 0,
    bottom: 0,
    zIndex: 9999, // Ensure it appears above all other content
  },
  corner: {
    position: 'absolute',
    width: 100,
    height: 100,
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
  },
});

export default DebugScreen;
