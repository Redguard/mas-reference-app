import {StyleSheet} from 'react-native';

const styles = StyleSheet.create({
  container: {
    justifyContent: 'center',
    alignItems: 'center',
  },
  logo: {
    width: 250,
    height: 250,
    margin: '5%',
  },
  homeStyle: {
    flex: 1,
    alignItems: 'center',
  },
  title: {
    fontSize: 30,
    fontWeight: 'bold',
    textAlign: 'center',
  },
  text: {
    margin: '5%',
    textAlign: 'justify',
    fontSize: 15,
  },
  statsContainer: {
    width: '80%',
    position: 'relative',
    flex: 1,
    padding: 5,
  },
  statsText: {
    margin: 5,
    textAlign: 'center',
    alignItems: 'center',
  },
  statsTextHeader: {
    fontWeight: 'bold',
  },
});

export default styles;
