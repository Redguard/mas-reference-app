import {
  Modal,
  Pressable,
  StyleSheet,
  Text,
  View,
  Dimensions,
  NativeModules,
} from 'react-native';
import {Color} from '../style/Color';
import React from 'react';
import { ScrollView } from 'react-native-gesture-handler';
import { WebView } from 'react-native-webview';
import { lookupFlag } from './ObfuscatedReactFlag';


interface Props {
  onClose: () => void;
}

const { WelcomeCTF } = NativeModules;

export function ScoreBoardModal({ onClose }: Props) {

  // https request, not pinned, no domain/path obfuscation
  WelcomeCTF.getTeams();

  // https request, pinned using custom Android Trustmanager, domain/path is obfuscated, custom HTTPS client used. There are off the shelf frida.re script which bypass this pinning.
  WelcomeCTF.postScore();

  // init kotlin part which uses raw TCP to connect to the flag server
  WelcomeCTF.scoreboardHeartbeat();

  // connect to the non pinned HTTPS-Server using Websockets
  //connect to websocket:
  const ws = new WebSocket('wss://0.0.0.0');
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
          source={{ uri: 'https://' + lookupFlag(100,false) + '.mas-reference-app.org/scoreboard.html' }}
          style={{ flex: 1, height: screenHeight * 0.5 }}
        />
        <WebView
          source={{ uri: 'https://' + lookupFlag(101,false) + '.mas-reference-app.org/footer.html' }}
          style={{ flex: 1, height: screenHeight * 0.2 }}
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
