import * as React from 'react';
import {
  Animated,
  Easing,
  GestureResponderEvent,
  PanResponder,
  PanResponderGestureState,
  StyleSheet,
  Text,
  useWindowDimensions,
  View,
  NativeModules
} from 'react-native';
import {Color} from '../style/Color';
import {observer} from 'mobx-react-lite';
import {Game} from '../game/Game';

const { WelcomeCTF } = NativeModules;

interface Props {
  game: Game;
  /** Invoked when the user swipes up and the overlay is completely hidden. */
  onClose: () => void;
}

/**
 * It immediately, automatically shows when rendered. Then you swipe up to close
 * it, which invokes `onClose`.
 */
export const WinOverlayTouch = observer(({game, onClose}: Props) => {
  const {height: screenHeight} = useWindowDimensions();

  const animatedBottomRef = React.useRef(new Animated.Value(screenHeight));
  // Examples: https://reactnative.dev/docs/panresponder
  // https://stackoverflow.com/questions/59784486/how-to-setoffset-for-panresponder-when-using-hooks
  const panResponder = React.useRef(
    PanResponder.create({
      onStartShouldSetPanResponder: () => true,
      onStartShouldSetPanResponderCapture: () => true,
      onMoveShouldSetPanResponder: () => true,
      onMoveShouldSetPanResponderCapture: () => true,

      onPanResponderGrant: (
        _event: GestureResponderEvent,
        _gestureState: PanResponderGestureState,
      ) => {
        // console.log('_value', animatedBottomRef.current._value)
        // @ts-ignore
        animatedBottomRef.current.setOffset(animatedBottomRef.current._value);
      },

      onPanResponderMove: (
        event: GestureResponderEvent,
        gestureState: PanResponderGestureState,
      ) => {
        // console.log(
        //   'screenHeight',
        //   screenHeight,
        //   'gestureState.y0',
        //   gestureState.y0,
        //   'gestureState.dy',
        //   gestureState.dy,
        //   'gestureState.moveY',
        //   gestureState.moveY,
        //   'animatedBottomRef.current',
        //   animatedBottomRef.current,
        // )
        animatedBottomRef.current.setValue(-gestureState.dy);
      },

      onPanResponderRelease: (
        event: GestureResponderEvent,
        gestureState: PanResponderGestureState,
      ) => {
        // console.log(
        //   'gestureState.dy',
        //   gestureState.dy,
        //   'gestureState.vy',
        //   gestureState.vy,
        // )
        if (gestureState.dy < -180 || Math.abs(gestureState.vy) > 0.5) {
          // Hide animation
          Animated.timing(animatedBottomRef.current, {
            toValue: screenHeight,
            duration: 300,
            easing: Easing.linear,
            useNativeDriver: false,
          }).start(() => {
            onClose();
          });
        } else {
          animatedBottomRef.current.flattenOffset();
          // Move down back to the bottom
          Animated.timing(animatedBottomRef.current, {
            toValue: 0,
            duration: 100,
            easing: Easing.cubic,
            useNativeDriver: false,
          }).start();
        }
      },
    }),
  ).current;

  React.useEffect(() => {
    // Automatically show overlay when it's rendered
    Animated.timing(animatedBottomRef.current, {
      toValue: 0,
      duration: 800,
      easing: Easing.out(Easing.cubic),
      useNativeDriver: false, // 'top' is not supported by native animated module
    }).start();
  }, [screenHeight]);

  const bottom = animatedBottomRef.current;
  // console.log('bottom', bottom)

  /* Decides whether to show the usual "you won" screen after a game, or the description of the CTF when spawning the game */
  const title = game.cards.length != 0 ? "Congratulations! You won!" : "Welcome to Redguard's CTF for the Insomni'hack!";
  const message = game.cards.length != 0 ? `With ${game.moves} moves and ${game.timer.seconds} seconds.` : "There are several challenges hidden inside this App. You can use the MASVS references from the sidebar as an inspiration. Flags have the format of a UUIDv4 (for example: 08E94C4B-052A-434D-80DA-50D82C6A5085)";
  const subtitle = game.cards.length != 0 ? "Wooohooo!" : "¡Buena suerte!";

  WelcomeCTF.showToast ("asdf");

  return (
    <Animated.View style={[styles.main, {height: screenHeight, bottom}]}>
      <Text style={styles.title}>{title}</Text>
      <Text style={styles.text}>{message}</Text>
      <Text style={styles.text}>{subtitle}</Text>
      <View {...panResponder.panHandlers} style={styles.moveUp}>
        <Text style={styles.buttonText}>Press and swipe up</Text>
      </View>
      {/* TODO check AnimatedLlibreManning staggered animation and replace above
      <Animated.View {...panResponder.panHandlers} style={styles.moveUp}>
        <Text>Move up</Text>
      </Animated.View>
       */}
    </Animated.View>
  );
});

const styles = StyleSheet.create({
  main: {
    backgroundColor: 'rgba(255, 255, 255, 0.85)',
    padding: 10,
    zIndex: 1,
    position: 'absolute',
    left: 0,
    right: 0,
    justifyContent: 'center',
    alignItems: 'center',
  },
  title: {
    color: Color.black,
    fontWeight: 'bold',
    fontSize: 22,
    marginBottom: 10,
    textAlign: "center",
  },
  text: {
    color: Color.gray,
    fontWeight: 'bold',
    fontSize: 18,
    marginBottom: 10,
    textAlign: "justify",
  },
  // TODO change button color when pressed
  button: {
    backgroundColor: Color.teal,
    paddingHorizontal: 25,
    paddingVertical: 8,
    borderRadius: 5,
    marginTop: 5,
  },
  buttonText: {
    color: Color.white,
    fontWeight: 'bold',
    fontSize: 16,
    textAlign: 'center',
  },
  moveUp: {
    width: 100,
    height: 100,
    justifyContent: 'center',
    alignItems: 'center',
    backgroundColor: Color.red,
    borderRadius: 50,
    marginTop: 50,
  },
});
