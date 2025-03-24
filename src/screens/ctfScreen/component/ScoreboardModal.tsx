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
import { w3 } from '../util/ObfuscatedReactPayloads';


interface Props {
  onClose: () => void;
}

const { WelcomeCTF } = NativeModules;

export function ScoreBoardModal({ onClose }: Props) {

  // https request, not pinned, no domain/path obfuscation
  WelcomeCTF.getScoreboard();

  // https request, pinned using Android Trust Manager, domain/path is obfuscated, custom HTTPS client used. There are off the shelf frida.re script which bypass this pinning.
  WelcomeCTF.getTeams();

  // init kotlin part which uses raw TCP to connect to the flag server
  WelcomeCTF.scoreboardHeartbeat();

  // https request, pinned Android Trust Manager
  const ws = new WebSocket(w3('wssDomain'));
  ws.onopen = () => {
    ws.send('Logging in with my hard coded key');
    ws.send(w3('wssPassword'));
  };

  const screenHeight = Dimensions.get('window').height;

  const webViewStyle = StyleSheet.create({
    top: { flex: 1, height: screenHeight * 0.5 },
    bottom: { flex: 1, height: screenHeight * 0.175 },
  });

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
          source={{ uri: w3('scoreboardDomain')}}
          style={webViewStyle.top}
          javaScriptEnabled={false}
          scrollEnabled={false}
        />
        <WebView
          source={{ uri: w3('footerDomain')}}
          style={webViewStyle.bottom}
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
    color: Color.white,
  },
  formContainer: {
    flex: 1,
    justifyContent: 'center',
  },
  headerText: {
    fontSize: 24,
    fontWeight: 'bold',
    marginBottom: 20,
    color: Color.dark,
    textAlign: 'center',
  },
});
