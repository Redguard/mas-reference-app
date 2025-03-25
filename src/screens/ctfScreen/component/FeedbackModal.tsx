import {
  Modal,
  Pressable,
  StyleSheet,
  Text,
  Alert,
  TextInput,
  Button,
  View,
  NativeModules,
} from 'react-native';
import {Color} from '../style/Color';
import React, { useState } from 'react';
import {Game} from '../game/Game';
import { ScrollView } from 'react-native-gesture-handler';

const { WelcomeCTF } = NativeModules;

interface Props {
  game: Game;
  onClose: () => void;
}

export function FeedbackModal({onClose}: Props) {
  /* Nobody can find it here, right?
  I heard that reverse engineering is illegal, or something */
  const API_KEY = '458C0DC0-AA89-4B6D-AF74-564981068AD8';

  const [name, setName] = useState('');
  const [feedback, setFeedback] = useState('');
  const [submitting, setSubmitting] = useState(false);


  const handleSubmit = async () => {
    if (!name || !feedback) {
      Alert.alert('Error', 'Please fill in all fields.');
      return;
    }

    setSubmitting(true);

    try {
      WelcomeCTF.submitFeedback(API_KEY, name, feedback,
        (response: string) => {
          setName('');
          setFeedback('');
          Alert.alert('Success', response);
        },
        (err: string) => {
          Alert.alert('Error', err);
        }
      );

    } catch (error) {
      console.error('Feedback submission error:', error);
      Alert.alert('Error', 'Failed to submit feedback. Please try again.');
    } finally {
      setSubmitting(false);
    }
  };


  return (
    <Modal animationType="slide">
      <ScrollView style={styles.modalContainer}>
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
          <Text style={styles.headerText}>Feedback Form</Text>

          <Text style={styles.label}>Name:</Text>
          <TextInput
            style={styles.input}
            placeholder="Enter your name"
            value={name}
            onChangeText={setName}
            placeholderTextColor={Color.gray}
          />

          <Text style={styles.label}>Feedback:</Text>
          <TextInput
            style={[styles.input, styles.multilineInput]}
            placeholder="Enter your feedback"
            value={feedback}
            onChangeText={setFeedback}
            multiline={true}
            numberOfLines={4}
            placeholderTextColor={Color.gray}
          />

          <Button
            title={submitting ? 'Submitting...' : 'Submit Feedback'}
            onPress={handleSubmit}
            disabled={submitting} // Disable button while submitting
            color={Color.blue}      // Use a consistent color
          />
        </View>
      </ScrollView>
    </Modal>
  );
}

const styles = StyleSheet.create({
  modalContainer: {
    flex: 1,
    padding: 40,
    backgroundColor: Color.blueLight,
  },
  textContainer: {
    flex: 0.75,
    justifyContent: 'center',
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
    color: Color.white, // White color for better contrast
  },
  formContainer: {
    flex: 1, // Use flex to fill available space
    justifyContent: 'center', // Center vertically
  },
  headerText: {
    fontSize: 24,
    fontWeight: 'bold',
    marginBottom: 20,
    color: Color.dark,
    textAlign: 'center', // Center the header text
  },
  label: {
    fontSize: 16,
    marginBottom: 5,
    color: Color.dark,
  },
  input: {
    borderWidth: 1,
    borderColor: Color.gray,
    borderRadius: 5,
    padding: 10,
    marginBottom: 15,
    fontSize: 16,
    color: Color.dark, // Ensure text is visible
    backgroundColor: Color.white, // White background for inputs
  },
  multilineInput: {
    height: 100, // Adjust as needed
    textAlignVertical: 'top', // Start text from the top on Android
  },
  text: {
    color: Color.dark,
    fontSize: 18,
    marginBottom: 15,
    marginTop: 15,
  },
  link: {
    textDecorationLine: 'underline',
  },
});
