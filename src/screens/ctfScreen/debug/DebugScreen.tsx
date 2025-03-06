// DebugScreen.js
import React, { useState } from 'react';
import { Modal, View, Text, Button, StyleSheet, TouchableOpacity } from 'react-native';

const DebugScreen = () => {
  const [, setTapSequence] = useState([]);
  const [isDebugVisible, setIsDebugVisible] = useState(false);

  // Define the correct tap sequence (top-left, top-right, bottom-right, bottom-left)
  const correctSequence = ['top-left', 'top-right', 'bottom-right', 'bottom-left'];

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
      {/* Top-left corner */}
      <TouchableOpacity
        style={[styles.corner, styles.topLeft]}
        onPress={() => handleTap('top-left')}
      />

      {/* Top-right corner */}
      <TouchableOpacity
        style={[styles.corner, styles.topRight]}
        onPress={() => handleTap('top-right')}
      />

      {/* Bottom-right corner */}
      <TouchableOpacity
        style={[styles.corner, styles.bottomRight]}
        onPress={() => handleTap('bottom-right')}
      />

      {/* Bottom-left corner */}
      <TouchableOpacity
        style={[styles.corner, styles.bottomLeft]}
        onPress={() => handleTap('bottom-left')}
      />

      {/* Debug Screen Modal */}
      <Modal
        visible={isDebugVisible}
        transparent={true}
        animationType="slide"
      >
        <View style={styles.modalContainer}>
          <View style={styles.modalContent}>
            <Text style={styles.title}>Debug Screen</Text>
            <Text>This is the hidden debug view!\n</Text>
            <Text>You deserve a flag: </Text>
            <Button title="Close" onPress={() => setIsDebugVisible(false)} />
            <Button title="Load encrypted state" onPress={() => setIsDebugVisible(false)} />
            <Button title="Some Bla" onPress={() => setIsDebugVisible(false)} />

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
});

export default DebugScreen;
