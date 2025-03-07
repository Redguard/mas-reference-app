import * as React from 'react';
import styles from './styles.tsx';
import {
  Pressable,
  SafeAreaView,
  StatusBar,
  Text,
  useColorScheme,
  View,
  Alert,
  NativeModules
} from 'react-native';
import {Colors} from 'react-native/Libraries/NewAppScreen';
import {Color} from './style/Color';
import {Board} from './component/Board';
import {useCardSize} from './style/sizes';
import {game} from './game/Game';
import {observer} from 'mobx-react-lite';
import {WinOverlayTouch} from './component/WinOverlayTouch';
import {useIsPortrait} from './util/useIsPortrait';
import {InfoModal} from './component/InfoModal';
import {FeedbackModal} from './component/FeedbackModal';
import {HelpModal} from './component/HelpModal.tsx';
import LinearGradient from 'react-native-linear-gradient';
import DebugScreen from './debug/DebugScreen.tsx';

const { WelcomeCTF } = NativeModules;

const CtfScreen = observer(function CtfScreen(): React.JSX.Element {
  const isDarkMode = useColorScheme() === 'dark';
  const isPortrait = useIsPortrait();
  const {boardSize} = useCardSize();

  const [showInfoModal, setShowInfoModal] = React.useState(false);
  const [showHelpModal, setShowHelpModal] = React.useState(false);
  const [showFeedbackModal, setShowFeedbackModal] = React.useState(false);

  const backgroundStyle = {
    backgroundColor: isDarkMode ? Colors.darker : Colors.lighter,
  };

  const textStyleTop = {fontSize: isPortrait ? 22 : 18};
  const textStyleBottom = {fontSize: isPortrait ? 24 : 20};
  const row2Style = {
    marginTop: isPortrait ? 12 : 3,
    marginBottom: isPortrait ? 15 : 2,
  };

  /* Special flag shown only when the score is -1234 (this should never happen, right?) */
  let specialFlag = game.totalScore == -1234? WelcomeCTF.getSpecialFlag() : null;
  if (specialFlag) {
    Alert.alert ("wait a minute... 🤨", specialFlag);
  }

  return (
    <SafeAreaView style={[styles.fullHeight, backgroundStyle]}>
      <StatusBar barStyle={isDarkMode ? 'light-content' : 'dark-content'} />

      <LinearGradient
        colors={[Color.teal, Color.purple]}
        useAngle={true}
        angle={135}
        style={[styles.container]}>
        <View style={styles.spaceTop} />
        <View style={[styles.row1, { width: boardSize }]}>
          <Text style={[styles.title, textStyleTop]}>Memory Game</Text>
          <Pressable
            style={({ pressed }) => [
              styles.restartPressable,
              {
                backgroundColor: pressed ? Color.blue : Color.blueLight,
              },
            ]}
            onPress={() => game.startGame()}>
            <Text style={[styles.restartText, textStyleTop]}>restart</Text>
          </Pressable>
          <Pressable
            style={({ pressed }) => [
              styles.infoPressable,
              {
                backgroundColor: pressed ? Color.teal : Color.tealLight,
              },
            ]}
            onPress={() => {
              setShowHelpModal(true);
            } }>
            <Text style={[styles.infoText, textStyleTop]}>?</Text>
          </Pressable>
          <Pressable
            style={({ pressed }) => [
              styles.infoPressable,
              {
                backgroundColor: pressed ? Color.teal : Color.tealLight,
              },
            ]}
            onPress={() => {
              setShowInfoModal(true);
            } }>
            <Text style={[styles.infoText, textStyleTop]}>i</Text>
          </Pressable>
        </View>
        <View style={[styles.row2, row2Style, { width: boardSize }]}>
          <Text style={[styles.textBottom, textStyleBottom]}>
            {game.moves} moves
          </Text>
          <Text style={[styles.textBottom, textStyleBottom]}>
            {game.timer.seconds} s
          </Text>
        </View>
        <Board cards={game.cards} />
        <View style={[styles.row2, row2Style, { width: boardSize }]}>
          <Text style={[styles.textBottom, textStyleBottom]}>
            Total score: {game.totalScore}
          </Text>
        </View>
        <Pressable
          style={({ pressed }) => [
            styles.restartPressable,
            {
              backgroundColor: pressed ? Color.blue : Color.blueLight,
            },
          ]}
          onPress={() => {
            setShowFeedbackModal(true);
          } }>
          <Text style={[styles.restartText, textStyleTop]}>Give feedback</Text>
        </Pressable>
        <View style={styles.spaceBottom} />
      </LinearGradient>

      {game.isCompleted && (
        <WinOverlayTouch
          game={game}
          onClose={() => {
            game.startGame();
          } } />
      )}

      {showInfoModal && <InfoModal onClose={() => setShowInfoModal(false)} />}
      {showHelpModal && <HelpModal onClose={() => setShowHelpModal(false)} />}
      {showFeedbackModal && <FeedbackModal game={game} onClose={() => setShowFeedbackModal(false)} />}
      <DebugScreen />
    </SafeAreaView>
  );
});

export default CtfScreen;
