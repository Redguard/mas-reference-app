import {Button, Image, Text, View} from 'react-native';
import React from 'react';
import styles from './style.tsx';

function HomeScreen({navigation}: any): React.JSX.Element {
  return (
    <View style={styles.homeStyle}>
      <View style={styles.container}>
        <Image
          style={styles.logo}
          source={require('../../assets/logo_circle.png')}
        />
      </View>
      <Text style={styles.title}>{'OWASP MAS Reference-App'}</Text>
      <Text>{'TBD: What does this Reference-App do?'}</Text>
      <Button
        onPress={() => navigation.navigate('Automation')}
        title="Go to Automation"
      />
    </View>
  );
}

export default HomeScreen;
