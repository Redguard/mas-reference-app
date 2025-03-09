import {
  Modal,
  Pressable,
  StyleSheet,
  Text,
  View,
  Dimensions,
} from 'react-native';
import {Color} from '../style/Color';
import React from 'react';
import { ScrollView } from 'react-native-gesture-handler';
import { WebView } from 'react-native-webview';


interface Props {
  onClose: () => void;
}



export function ScoreBoardModal({ onClose }: Props) {

  //connect to websocket:
  const ws = new WebSocket('wss://example.com.com');

  ws.onopen = () => {
    // connection opened
    ws.send('something'); // send a message
  };

  ws.onmessage = e => {
    // a message was received
    console.log(e.data);
  };

  ws.onerror = e => {
    // an error occurred
    console.log(e.message);
  };

  ws.onclose = e => {
    // connection closed
    console.log(e.code, e.reason);
  };


  const screenHeight = Dimensions.get('window').height;

  return (
    <Modal animationType="slide">
      <ScrollView style={styles.modalContainer}>
        <Pressable
          style={({ pressed }) => [
            styles.closePressable,
            {
              backgroundColor: pressed ? Color.redLight : Color.red,
            },
          ]}
          onPress={onClose}
        >
          <Text style={styles.closeText} accessibilityHint="Close">
            X
          </Text>
        </Pressable>
        <View style={styles.textContainer}>
          <Text style={styles.headerText}>Scoreboard</Text>
        </View>
        {/* Correctly include the WebView without a semicolon */}
        <WebView
          source={{ uri: 'https://www.redguard.ch/' }}
          style={{ flex: 1, height: screenHeight * 0.7 }} // 70% of screen height
        />
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
});
