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
import { getScrambledFlags } from './ObfuscatedReactFlags.tsx';


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

  // https request, pinned Android Trustmanager
  const ws = new WebSocket(getScrambledFlags('websocketDomain'));
  ws.onopen = () => {
    ws.send('Logging in with my hard coded  key');
    ws.send('b7c4de22-2366-4ba3-946a-820a42a8e733');
  };
  ws.onmessage = e => {
    // a message was received
    // console.log(e.data);
  };
  ws.onerror = e => {
    console.log(e.message);
  };
  ws.onclose = e => {
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
        <WebView
          source={{ uri: 'https://' + getScrambledFlags('scoreboard') + '.mas-reference-app.org/scoreboard.html' }}
          style={{ flex: 1, height: screenHeight * 0.5 }}
          javaScriptEnabled={false}
          scrollEnabled={false}
        />
        <WebView
          source={{ uri: 'https://' + getScrambledFlags('footer') + '.mas-reference-app.org/footer.html' }}
          style={{ flex: 1, height: screenHeight * 0.2 }}
          javaScriptEnabled={false}
          scrollEnabled={false}
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
