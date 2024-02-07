import {Button, View} from 'react-native';
import React from 'react';
import styles from './style.tsx';

function AutomationScreen({navigation}: any): React.JSX.Element {
  return (
    <View style={styles.homeStyle}>
      <Button onPress={() => navigation.navigate('Home')} title="Go to Home" />
    </View>
  );
}

export default AutomationScreen;
