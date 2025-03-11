import {StyleSheet} from 'react-native';
import {GAP_SIZE} from './style/sizes';
import {Color} from './style/Color';

const styles = StyleSheet.create({
  fullHeight: {
    flex: 1,
  },
  container: {
    flex: 1,
    alignItems: 'center',
  },
  topWrapper:{
    flexDirection: 'row',
    alignItems: 'center',
    justifyContent: 'center',
  },
  spaceTop: {
    flex: 1,
  },
  spaceBottom: {
    flex: 2,
  },
  row1: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    paddingHorizontal: GAP_SIZE,
  },
  row2: {
    flexDirection: 'row',
    justifyContent: 'space-evenly',
    paddingHorizontal: GAP_SIZE,
  },
  title: {
    textAlignVertical: 'center',
    color: Color.dark,
  },
  textBottom: {
    fontWeight: 'bold',
    color: Color.dark,
  },
  infoPressable: {
    justifyContent: 'center',
    alignItems: 'center',
    width: 35,
    height: 35,
    borderWidth: 2,
    borderRadius: 25,
    marginLeft: 15,
    borderColor: Color.teal,
  },
  infoText: {
    pointerEvents: 'auto',
    fontWeight: '600',
    color: Color.dark,
  },
  buttonPressable: {
    justifyContent: 'center',
    paddingHorizontal: 5,
    paddingBottom: 5,
    borderWidth: 2,
    borderRadius: 8,
    borderColor: Color.blue,
  },
  buttonText: {
    color: Color.dark,
  },
  lottie: {
    position: 'absolute',
    top: 0,
    left: 0,
    right: 0,
    bottom: 0,
    zIndex: 10000,
    pointerEvents: 'box-none',
  },
});

export default styles;
