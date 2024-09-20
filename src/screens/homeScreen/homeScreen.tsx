import {Image, Text, View} from 'react-native';
import React from 'react';
import styles from './style.tsx';

function HomeScreen({}: any): React.JSX.Element {
  return (
    <View style={styles.homeStyle}>
      <View style={styles.container}>
        <Image
          style={styles.logo}
          source={require('../../assets/logo_circle.png')}
        />
      </View>
      <Text style={styles.title}>{'OWASP MAS Reference-App'}</Text>
      <Text style={styles.text}>
        {
          'The OWASP MAS Refernce App implemnts different vulnerabilities and security-relvant features such as anti-tampering measures. The app can be used to train testers in assessing the security of mobile apps. In addition it can be used to develop and test security scanners both static and dynamic ones.'
        }
      </Text>
    </View>
  );
}

export default HomeScreen;
