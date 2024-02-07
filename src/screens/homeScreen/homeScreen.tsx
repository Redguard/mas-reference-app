import {Button, View} from 'react-native';
import React from 'react';
import styles from './style.tsx';

function HomeScreen({navigation}: any): React.JSX.Element {
  return (
    <View style={styles.homeStyle}>
      <Button
        onPress={() => navigation.navigate('Automation')}
        title="Go to Automation"
      />
    </View>
  );
}

export default HomeScreen;
