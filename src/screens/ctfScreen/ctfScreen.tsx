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
  NativeModules,
  Linking
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
import {DebugModal} from './component/DebugModal.tsx';
import {ScoreBoardModal} from './component/ScoreboardModal.tsx';
import RNExitApp from 'react-native-exit-app';


const { WelcomeCTF } = NativeModules;

const CtfScreen = observer(function CtfScreen(): React.JSX.Element {
  const isDarkMode = useColorScheme() === 'dark';
  const isPortrait = useIsPortrait();
  const {boardSize} = useCardSize();

  const [visibility, setVisibility] = React.useState({
    title: true,
    restartButton: true,
    helpButton: true,
    infoButton: true,
    moves: true,
    timer: true,
    board: true,
    totalScore: true,
    streak: true,
    feedbackButton: true,
    scoreBoardButton: true,
    saveButton: true,
  });

  const [showInfoModal, setShowInfoModal] = React.useState(false);
  const [showHelpModal, setShowHelpModal] = React.useState(false);
  const [showFeedbackModal, setShowFeedbackModal] = React.useState(false);
  const [showScoreboardModal, setShowScoreboardModal] = React.useState(false);


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

  // Function to hide/show an element
  const hideElement = (elementKey: string) => {
    setVisibility((prev) => ({
      ...prev,
      [elementKey]: false, // Set the element's visibility to false
    }));
  };

  const crashTheApp = () => {
    const visibleElements = Object.keys(visibility).filter(
      (key) => visibility[key]
    );

    if (visibleElements.length === 0) {
      RNExitApp.exitApp();
      return;
    }

    const hiddenElements = new Set(); // Use a Set to track hidden elements
    const interval = setInterval(() => {
      const visibleElements = Object.keys(visibility).filter(
        (key) => visibility[key] && !hiddenElements.has(key) // Exclude already hidden elements
      );

      if (visibleElements.length === 0) {
        clearInterval(interval);
        RNExitApp.exitApp();
        return;
      }
      const randomIndex = Math.floor(Math.random() * visibleElements.length);
      const randomElement = visibleElements[randomIndex];
      hideElement(randomElement);
      hiddenElements.add(randomElement);
    }, 1000);
  };

  return (
    <SafeAreaView style={[styles.fullHeight, backgroundStyle]}>
      <StatusBar barStyle={isDarkMode ? 'light-content' : 'dark-content'} />
      <LinearGradient
        colors={[Color.teal, Color.purple]}
        useAngle={true}
        angle={135}
        style={[styles.container]}>
        <View style={styles.spaceTop} />
        <View style={[styles.topWrapper, { width: boardSize}]}>
          {/* {visibility.title && (
            <Text style={[styles.title, textStyleTop]}>Memory Game</Text>
          )} */}
          {visibility.restartButton && (
            <Pressable
              style={({ pressed }) => [
                styles.buttonPressable,
                {
                  backgroundColor: pressed ? Color.blue : Color.blueLight,
                }, { zIndex: 9999 }]}
              onPress={() => game.startGame()}>
              <Text style={[styles.buttonText, textStyleTop]}>restart</Text>
            </Pressable>
          )}
          <View style={[styles.row2, row2Style, { width: boardSize / 2 }]}>
            {visibility.moves && (
              <Text style={[styles.textBottom, textStyleBottom]}>
                {game.moves} moves
              </Text>
            )}
            {visibility.timer && (
              <Text style={[styles.textBottom, textStyleBottom]}>
                {game.timer.seconds} s
              </Text>
            )}
          </View>
          {visibility.helpButton && (
            <Pressable
              style={({ pressed }) => [
                styles.infoPressable,
                {
                  backgroundColor: pressed ? Color.teal : Color.tealLight,
                }, { zIndex: 9999 }]}
              onPress={() => {
                setShowHelpModal(true);
              }}>
              <Text style={[styles.infoText, textStyleTop]}>?</Text>
            </Pressable>
          )}
          {visibility.infoButton && (
            <Pressable
              style={({ pressed }) => [
                styles.infoPressable,
                {
                  backgroundColor: pressed ? Color.teal : Color.tealLight,
                }, { zIndex: 9999 }]}
              onPress={() => {
                setShowInfoModal(true);
              }}>
              <Text style={[styles.infoText, textStyleTop]}>i</Text>
            </Pressable>
          )}
        </View>
        {visibility.board && <Board cards={game.cards} />}
        <View style={[styles.row2, row2Style, { width: boardSize }]}>
          {visibility.totalScore && (
            <Text style={[styles.textBottom, textStyleBottom]}>
              Total score: {game.totalScore}
            </Text>
          )}
          {visibility.streak && (
            <Text style={[styles.textBottom, textStyleBottom]}>
              Streak: {game.streak}
            </Text>
          )}
        </View>
        <View style={[styles.row2, row2Style, { width: boardSize }]}>
        {visibility.feedbackButton && (
          <Pressable
            style={({ pressed }) => [
              styles.buttonPressable,
              {
                backgroundColor: pressed ? Color.blue : Color.blueLight,
              }, { zIndex: 9999 }]}
            onPress={() => {
              setShowFeedbackModal(true);
            }}>
            <Text style={[styles.buttonText, textStyleTop]}>Feedback</Text>
          </Pressable>
        )}
        {visibility.scoreBoardButton && (
          <Pressable
            style={({ pressed }) => [
              styles.buttonPressable,
              {
                backgroundColor: pressed ? Color.blue : Color.blueLight,
              }, { zIndex: 9999 }]}
            onPress={() => {
              setShowScoreboardModal(true);
            }}>
            <Text style={[styles.buttonText, textStyleTop]}>Scoreboard</Text>
          </Pressable>
        )}
        {/*visibility.saveButton && (
          <Pressable
          style={({ pressed }) => [
            styles.buttonPressable,
            {
              backgroundColor: pressed ? Color.blue : Color.blueLight,
            },{zIndex: 9999}]}
            onPress={() => {
              Linking.openURL(
                `ctf://game-controller?token=${WelcomeCTF.getIpcToken()}&command=save_session&score=${game.totalScore}&streak=${game.streak}&moves=${game.moves}&time=${game.timer.seconds}&data=Tm90aGluZyB0byBzZWUgaGVyZS4gR28gYXdheSEh`,
              )
            } }>
          <Text style={[styles.buttonText, textStyleTop]}>Save Game</Text>
        </Pressable>
          )*/}
        </View>

        <View style={styles.spaceBottom} />
      </LinearGradient>

      {game.isCompleted && (
        <WinOverlayTouch
          game={game}
          onClose={() => {
            game.startGame();
          }} />
      )}

      {showInfoModal && (
        <InfoModal onClose={() => setShowInfoModal(false)} />
      )}
      {showHelpModal && (
        <HelpModal onClose={() => setShowHelpModal(false)} />
      )}
      {showFeedbackModal && (
        <FeedbackModal game={game} onClose={() => setShowFeedbackModal(false)} />
      )}
      {showScoreboardModal && (
        <ScoreBoardModal onClose={() => setShowScoreboardModal(false)} />
      )}
      {(
        <DebugModal game={game} crashTheApp={crashTheApp} />
      )}
    </SafeAreaView>
  );
});

export default CtfScreen;
