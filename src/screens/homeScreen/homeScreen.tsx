import {Image, ScrollView, Text, View} from 'react-native';
import React from 'react';
import styles from './style.tsx';
import testCases from '../../testcases.tsx';

function Stats({}: any): React.JSX.Element {
  console.log(testCases);

  return (
    <View style={styles.stats}>
      <Text>Stats</Text>
    </View>
  );
}

function HomeScreen({}: any): React.JSX.Element {
  return (
    <ScrollView>
      <View style={styles.homeStyle}>
        <View style={styles.container}>
          <Image
            style={styles.logo}
            source={require('../../assets/logo_circle.png')}
          />
        </View>
        <Text style={styles.title}>{'OWASP MAS Reference App'}</Text>
        <Text style={styles.text}>
          {
            'The OWASP MAS Reference App implements different vulnerabilities and security-relevant features such as anti-tampering measures. \n\nThe app can be used to train testers in assessing the security of mobile apps. In addition it can be used to develop and test security scanners both static and dynamic ones.'
          }
        </Text>
        <Stats />
      </View>
    </ScrollView>
  );
}

export default HomeScreen;
