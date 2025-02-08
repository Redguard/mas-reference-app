import {StyleSheet} from 'react-native';

const styles = StyleSheet.create({
  categoryDescription: {
    flex: 1,
    margin: 20,
  },
  testDescription: {
    marginBottom: 15,
  },
  button: {
    width: '100%',
    alignItems: 'center',
    backgroundColor: '#DDDDDD',
    borderColor: '#70706f',
    borderWidth: 1,
    borderRadius: 10,
    padding: 10,
    marginTop: 5,
    marginBottom: 5,
    flex: 1,
  },
  buttonPressed: {
    alignItems: 'center',
    backgroundColor: '#5bc878',
    borderColor: '#377046',
    borderWidth: 1,
    borderRadius: 10,
    padding: 10,
    marginTop: 5,
    marginBottom: 5,
    flex: 1,
  },
  buttonRow: {
    flexDirection: 'row',
    alignItems: 'center',
  },
  circle: {
    width: 35,
    height: 35,
    borderRadius: 17.25,
    borderWidth: 1,
    borderColor: '#70706f',
    backgroundColor: '#DDDDDD',
    justifyContent: 'center',
    alignItems: 'center',
    marginRight: 10,
  },
  arrow: {
    position: 'absolute',
    marginLeft: 13,
    marginTop: 40,
    borderLeftWidth: 5,
    borderRightWidth: 5,
    borderBottomWidth: 10,
    borderStyle: 'solid',
    backgroundColor: 'transparent',
    borderLeftColor: 'transparent',
    borderRightColor: 'transparent',
    borderBottomColor: 'gray',
  },
  popupBox: {
    position: 'absolute',
    width: '100%',
    backgroundColor: 'white',
    marginTop: 50,
    padding: 10,
    borderRadius: 8,
    alignSelf: 'center', // This helps with centering
    borderWidth: 1,
    borderColor: '#70706f',
  },
});

export default styles;
