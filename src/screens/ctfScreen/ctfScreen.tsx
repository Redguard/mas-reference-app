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
  Dimensions,
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
import LottieView from 'lottie-react-native';

const { WelcomeCTF } = NativeModules;

interface ExplosionAssets {
  [key: number]: any;
}

interface Visibility {
  [key: string]: boolean;
}

interface Explosion {
  id: number;
  x: number;
  y: number;
  source: any;
}

const CtfScreen = observer(function CtfScreen(): React.JSX.Element {
  const isDarkMode = useColorScheme() === 'dark';
  const isPortrait = useIsPortrait();
  const {boardSize} = useCardSize();

  const [visibility, setVisibility] = React.useState<Visibility>({
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
  const [explosions, setExplosions] = React.useState<Explosion[]>([]);

  const { width, height } = Dimensions.get('window');

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
  let specialFlag = game.totalScore === -1234 ? WelcomeCTF.getSpecialFlag() : null;
  if (specialFlag) {
    Alert.alert('wait a minute... 🤨', specialFlag);
  }

  // Function to hide/show an element
  const hideElement = (elementKey: string) => {
    setVisibility((prev) => ({
      ...prev,
      [elementKey]: false, // Set the element's visibility to false
    }));
  };

  const crashTheApp = () => {
    const hiddenElements = new Set<string>();

    const getVisibleElements = () => Object.keys(visibility).filter(
      (key) => visibility[key] && !hiddenElements.has(key)
    );

    let visibleElements = getVisibleElements();

    if (visibleElements.length === 0) {
      RNExitApp.exitApp();
      return;
    }

    const interval = setInterval(() => {
      if (visibleElements.length === 0) {
        clearInterval(interval);
        RNExitApp.exitApp();
        return;
      }

      const randomIndex = Math.floor(Math.random() * visibleElements.length);
      const randomElement = visibleElements[randomIndex];

      hideElement(randomElement);
      hiddenElements.add(randomElement);

      const randomX = Math.random() * (width - 200);
      const randomY = Math.random() * (height - 200);
      setExplosions((prevExplosions) => [
        ...prevExplosions,
        {
          id: Date.now(),
          x: randomX,
          y: randomY,
          source: getRandomExplosion(),
        },
      ]);

      visibleElements = getVisibleElements();
    }, 1000);
  };


  const explosionAssets: ExplosionAssets = {
    1: require('./assets/explosion1.json'),
    2: require('./assets/explosion2.json'),
    3: require('./assets/explosion3.json'),
    4: require('./assets/explosion4.json'),
    5: require('./assets/explosion4.json'),
  };

  const getRandomExplosion = (): any => {
    const randomIndex = Math.floor(Math.random() * Object.keys(explosionAssets).length) + 1; // Random number between 1 and 4
    return explosionAssets[randomIndex];
  };

  const handleAnimationFinish = (id: Number) => {
    // Remove the explosion from the array after the animation finishes
    setExplosions((prevExplosions) =>
      prevExplosions.filter((explosion) => explosion.id !== id)
    );
  };

  return (
    <SafeAreaView style={[styles.fullHeight, backgroundStyle]}>
      <StatusBar barStyle={isDarkMode ? 'light-content' : 'dark-content'} />
      <LinearGradient
        colors={[Color.teal, Color.purple]}
        useAngle={true}
        angle={135}
        style={[styles.container]}>

        {explosions.map((explosion) => (
                <LottieView
                  key={explosion.id}
                  source={explosion.source}
                  onAnimationFinish={() => handleAnimationFinish(explosion.id)}
                  autoPlay
                  loop={false}
                  style={[
                    styles.explosion,
                    { top: explosion.y, left: explosion.x },
                  ]}
                />
              ))}

        <View style={styles.spaceTop} />
        <View style={[styles.topWrapper, { width: boardSize}]}>
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
        </View>

        <View style={styles.spaceBottom} />

        {game.anticheatEnabled && (
          <View style={[styles.cheatProtection,styles.cheatProtectionOk]}>
              <Text style={[styles.cheatProtectionText]}>Cheat Protection Enabled</Text>
          </View>
        )}
        {!game.anticheatEnabled && (
          <View style={[styles.cheatProtection,styles.cheatProtectionNok]}>
              <Text style={[styles.cheatProtectionText]}>Cheat Protection Disabled</Text>
          </View>
        )}

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
