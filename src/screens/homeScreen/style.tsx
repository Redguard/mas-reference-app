import {StyleSheet} from 'react-native';

const styles = StyleSheet.create({
  logoContainer: {
    justifyContent: 'center',
    alignItems: 'center',
  },
  logo: {
    width: 200,
    height: 200,
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
    alignItems: 'center',
    marginHorizontal: '5%',
    marginBottom: '2%',
    marginTop: '2%',
    textAlign: 'justify',
    fontSize: 15,
  },
  bulletPoint: {
    marginHorizontal: '10%',
  },
  statsContainer: {
    justifyContent: 'center',
    width: '80%',
    position: 'relative',
    flex: 1,
    padding: 5,
  },
  statsText: {
    margin: 5,
    textAlign: 'center',
    alignItems: 'center',
    justifyContent: 'center',
  },
  statsTextHeader: {
    fontWeight: 'bold',
  },
});

export default styles;
