import {StyleSheet} from 'react-native';

const styles = StyleSheet.create({
  categoryDescription: {
    flex: 1,
    margin: 20,
  },
  button: {
    alignItems: 'center',
    backgroundColor: '#DDDDDD',
    borderColor: '#70706f',
    borderWidth: 1,
    borderRadius: 10,
    padding: 10,
    marginTop: 10,
    flex: 1,
  },
  buttonPressed: {
    alignItems: 'center',
    backgroundColor: '#5bc878',
    borderColor: '#377046',
    borderWidth: 1,
    borderRadius: 10,
    padding: 10,
    marginTop: 10,
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
    marginTop: 10,
    verticalAlign: 'middle',
  },
  icon: {
    color: 'gary',
    fontSize: 20,
  },
});

export default styles;
