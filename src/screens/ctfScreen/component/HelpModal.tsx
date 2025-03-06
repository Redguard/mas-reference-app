import { Modal, StyleSheet, Text, TouchableWithoutFeedback, View } from 'react-native';
import { Color } from '../style/Color';
import React, { useState, useEffect } from 'react';

interface Props {
  /** Invoked when the modal automatically closes or is dismissed by a click. */
  onClose: () => void;
}

// Array of tips
const tips = [
  'Live, Love, Laugh.',
  'Stay hydrated throughout the day.',
  'Take short breaks while working.',
  'Exercise regularly for a healthy mind and body.',
  'Read a book to expand your knowledge.',
  'Practice gratitude daily.',
  'Believe in yourself, and the world will believe in you too!',
  'Every day is a second chance to sparkle.',
  'Dream big, work hard, and stay humble.',
  'You’re one step closer to greatness with every small win.',
  'The only limit is the one you set for yourself.',
  "Turn your can'ts into cans and your dreams into plans.",
  'You’re a diamond—pressure only makes you shine brighter.',
  'Success starts with a single step. Take it today!',
  'Be the reason someone smiles today.',
  'You’ve got this! The best is yet to come.',
  'Mistakes are proof that you’re trying.',
  'Hustle until your haters ask if you’re hiring.',
  'Your vibe attracts your tribe—stay positive!',
  'The harder you work, the luckier you get.',
  'Wake up, kick butt, and repeat.',
  'Don’t stop until you’re proud.',
  'If opportunity doesn’t knock, build a door.',
  'You’re stronger than you think and braver than you feel.',
  'Success is a journey, not a destination—enjoy the ride!',
  'Keep going! You’re closer than you think.',
  //below are the real tips
  'Have you looked at the logs?',
  'Sometimes the developers forget something important in the build.',
  'Sometimes it is nice to be a fly on the wall, or on the network.',
  'Obfuscation can bring you only so far.',
  'I heard CyberChef can cook very well with hard coded incidences.',
  'Ask the nice people at the Redguard booth for a flag, the code phrase is the geohash of a very special special location.', // location of redguard office neuchatel
   '...TODO: some more hints...',
];

export function HelpModal({ onClose }: Props) {
  const [tip, setTip] = useState('');

  useEffect(() => {
    // Select a random tip when the component mounts
    const randomTip = tips[Math.floor(Math.random() * tips.length)];
    setTip(randomTip);

    // Automatically close the modal after 5 seconds
    const timer = setTimeout(() => {
      onClose();
    }, 3000);

    // Cleanup the timer on unmount
    return () => clearTimeout(timer);
  }, [onClose]);

  return (
    <Modal animationType="slide" transparent={true} onRequestClose={onClose}>
      <TouchableWithoutFeedback onPress={onClose}>
        <View style={styles.modalContainer}>
          <View style={styles.textContainer}>
            <Text style={styles.text}>Tip of the moment is:</Text>
            <Text style={styles.tipText}>{tip}</Text>
          </View>
        </View>
      </TouchableWithoutFeedback>
    </Modal>
  );
}

const styles = StyleSheet.create({
  modalContainer: {
    flex: 1,
    justifyContent: 'flex-end', // Align the modal content at the bottom
    backgroundColor: 'transparent', // Ensure the rest of the app is visible
  },
  textContainer: {
    height: '10%', // Make the modal content only 10% of the screen height
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
