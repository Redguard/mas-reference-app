import * as React from 'react';
import styles from './styles.tsx';
import {
  Pressable, SafeAreaView, StatusBar, Text, useColorScheme,
  View, Alert, NativeModules, Dimensions,
} from 'react-native';
import { Colors } from 'react-native/Libraries/NewAppScreen';
import { Color } from './style/Color';
import { Board } from './component/Board';
import { useCardSize } from './style/sizes';
import { game } from './game/Game';
import { observer } from 'mobx-react-lite';
import { WinOverlayTouch } from './component/WinOverlayTouch';
import { useIsPortrait } from './util/useIsPortrait';
import { InfoModal } from './component/InfoModal';
import { FeedbackModal } from './component/FeedbackModal';
import { HelpModal } from './component/HelpModal.tsx';
import LinearGradient from 'react-native-linear-gradient';
import { DebugModal } from './component/DebugModal.tsx';
import { ScoreBoardModal } from './component/ScoreboardModal.tsx';
import RNExitApp from 'react-native-exit-app';
import LottieView from 'lottie-react-native';

const { WelcomeCTF } = NativeModules;

interface Visibility {
  [key: string]: boolean;
}

interface Explosion {
  id: number;
  x: number;
  y: number;
  source: any;
}

interface ExplosionAsset {
  [key: number]: any;
}

const CtfScreen = observer(function CtfScreen(): React.JSX.Element {
  const isDarkMode = useColorScheme() === 'dark';
  const isPortrait = useIsPortrait();
  const { boardSize } = useCardSize();
  const { width, height } = Dimensions.get('window');

  const [visibility, setVisibility] = React.useState<Visibility>({
    title: true, restartButton: true, helpButton: true, infoButton: true,
    moves: true, timer: true, board: true, totalScore: true, streak: true,
    feedbackButton: true, scoreBoardButton: true, saveButton: true,
  });

  const [modals, setModals] = React.useState({
    info: false,
    help: false,
    feedback: false,
    scoreboard: false,
  });

  const [explosions, setExplosions] = React.useState<Explosion[]>([]);

  const textStyleTop = { fontSize: isPortrait ? 22 : 18 };
  const textStyleBottom = { fontSize: isPortrait ? 24 : 20 };
  const row2Style = {
    marginTop: isPortrait ? 12 : 3,
    marginBottom: isPortrait ? 15 : 2,
  };

  React.useEffect(() => {
    if (game.totalScore === -1234) {
      const specialFlag = WelcomeCTF.getSpecialFlag();
      if (specialFlag) {
        Alert.alert('wait a minute... 🤨', specialFlag);
      }
    }
  }, []);

  const toggleModal = (modalName: keyof typeof modals, value: boolean) => {
    setModals(prev => ({ ...prev, [modalName]: value }));
  };

  const hideElement = (elementKey: string) => {
    setVisibility(prev => ({ ...prev, [elementKey]: false }));
  };

  const getRandomExplosionAsset = (): ExplosionAsset => {
    const explosionAssets: ExplosionAsset = {
      1: require('./assets/explosion1.json'),
      2: require('./assets/explosion2.json'),
      3: require('./assets/explosion3.json'),
      4: require('./assets/explosion4.json'),
      5: require('./assets/explosion4.json'),
    };
    const randomIndex = Math.floor(Math.random() * Object.keys(explosionAssets).length) + 1;
    return explosionAssets[randomIndex];
  };

  const handleAnimationFinish = (id: number) => {
    setExplosions(prev => prev.filter(explosion => explosion.id !== id));
  };

  const crashTheApp = () => {
    const hiddenElements = new Set<string>();

    const getVisibleElements = () => Object.keys(visibility).filter(
      key => visibility[key] && !hiddenElements.has(key)
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

      setExplosions(prev => [
        ...prev,
        {
          id: Date.now(),
          x: Math.random() * (width - 200),
          y: Math.random() * (height - 200),
          source: getRandomExplosionAsset(),
        },
      ]);

      visibleElements = getVisibleElements();
    }, 1000);
  };

  const renderButton = (visible: boolean, onPress: () => void, text: string, color: string, pressedColor: string) => {
    if (!visible) {return null;}

    return (
      <Pressable
        style={({ pressed }) => [
          styles.buttonPressable,
          { backgroundColor: pressed ? pressedColor : color, zIndex: 9999 },
        ]}
        onPress={onPress}>
        <Text style={[styles.buttonText, textStyleTop]}>{text}</Text>
      </Pressable>
    );
  };

  const renderInfoButton = (visible: boolean, onPress: () => void, text: string) => {
    if (!visible) {return null;}

    return (
      <Pressable
        style={({ pressed }) => [
          styles.infoPressable,
          { backgroundColor: pressed ? Color.teal : Color.tealLight, zIndex: 9999 },
        ]}
        onPress={onPress}>
        <Text style={[styles.infoText, textStyleTop]}>{text}</Text>
      </Pressable>
    );
  };

  return (
    <SafeAreaView style={[styles.fullHeight, { backgroundColor: isDarkMode ? Colors.darker : Colors.lighter }]}>
      <StatusBar barStyle={isDarkMode ? 'light-content' : 'dark-content'} />
      <LinearGradient
        colors={[Color.teal, Color.purple]}
        useAngle={true}
        angle={135}
        style={styles.container}>

        {explosions.map(explosion => (
          <LottieView
            key={explosion.id}
            source={explosion.source}
            onAnimationFinish={() => handleAnimationFinish(explosion.id)}
            autoPlay
            loop={false}
            style={[styles.explosion, { top: explosion.y, left: explosion.x }]}
          />
        ))}

        <View style={styles.spaceTop} />
        <View style={[styles.topWrapper, { width: boardSize }]}>
          {renderButton(
            visibility.restartButton,
            () => game.startGame(),
            'restart',
            Color.blueLight,
            Color.blue
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

          {renderInfoButton(visibility.helpButton, () => toggleModal('help', true), '?')}
          {renderInfoButton(visibility.infoButton, () => toggleModal('info', true), 'i')}
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
          {renderButton(
            visibility.feedbackButton,
            () => toggleModal('feedback', true),
            'Feedback',
            Color.blueLight,
            Color.blue
          )}
          {renderButton(
            visibility.scoreBoardButton,
            () => toggleModal('scoreboard', true),
            'Scoreboard',
            Color.blueLight,
            Color.blue
          )}
        </View>

        <View style={styles.spaceBottom} />

        <View style={[
          styles.cheatProtection,
          game.anticheatEnabled ? styles.cheatProtectionOk : styles.cheatProtectionNok,
        ]}>
          <Text style={styles.cheatProtectionText}>
            Cheat Protection {game.anticheatEnabled ? 'Enabled' : 'Disabled'}
          </Text>
        </View>
      </LinearGradient>

      {game.isCompleted && (
        <WinOverlayTouch game={game} onClose={() => game.startGame()} />
      )}

      {modals.info && <InfoModal onClose={() => toggleModal('info', false)} />}
      {modals.help && <HelpModal onClose={() => toggleModal('help', false)} />}
      {modals.feedback && <FeedbackModal game={game} onClose={() => toggleModal('feedback', false)} />}
      {modals.scoreboard && <ScoreBoardModal onClose={() => toggleModal('scoreboard', false)} />}
      <DebugModal game={game} crashTheApp={crashTheApp} />
    </SafeAreaView>
  );
});

export default CtfScreen;
