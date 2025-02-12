import {Image, ScrollView, Text, View} from 'react-native';
import React from 'react';
import styles from './style.tsx';
import testCases from '../../testcases.tsx';
import appContent from '../../appContent.tsx';
import {Table, Rows} from 'react-native-reanimated-table';

function Stats({}: any): React.JSX.Element {
  const stats: {[id: string]: number} = {};

  for (const c in appContent) {
    const category = appContent[c].key;
    var i = 0;
    for (const tc in testCases[category]) {
      i += Object.keys(testCases[category][tc].testCases).length;
    }
    stats[category] = i;
  }

  return (
    <View style={styles.statsContainer}>
      <View>
        <Table>
          <Rows
            data={[['MAS Category', 'Number of Tests']]}
            textStyle={[styles.statsText, styles.statsTextHeader]}
          />
          <Rows
            data={[['Storage', stats.STORAGE]]}
            textStyle={styles.statsText}
          />
          <Rows
            data={[['Crypto', stats.CRYPTO]]}
            textStyle={styles.statsText}
          />
          <Rows data={[['Auth', stats.AUTH]]} textStyle={styles.statsText} />
          <Rows
            data={[['Network', stats.NETWORK]]}
            textStyle={styles.statsText}
          />
          <Rows
            data={[['Platform', stats.PLATFORM]]}
            textStyle={styles.statsText}
          />
          <Rows
            data={[['Resilience', stats.RESILIENCE]]}
            textStyle={styles.statsText}
          />
          <Rows
            data={[['Privacy', stats.PRIVACY]]}
            textStyle={styles.statsText}
          />
        </Table>
      </View>
    </View>
  );
}

function HomeScreen({}: any): React.JSX.Element {
  return (
    <ScrollView>
      <View style={styles.homeStyle}>
        <View style={styles.logoContainer}>
          <Image
            style={styles.logo}
            source={require('../../assets/logo_circle.png')}
          />
        </View>
        <Text style={styles.title}>{'OWASP MAS\n Reference App'}</Text>
        <Text style={styles.text}>
          This app implements common security and privacy weaknesses specific to mobile apps. Use it for the following purposes:
        </Text>
      </View>
        <Text style={styles.bulletPoint}>
          - Lean about mobile security and OWASP MAS
        </Text>
        <Text style={styles.bulletPoint}>
          - Test tools used to assess mobile app security
        </Text>
        <Text style={styles.bulletPoint}>
          - Educate security testers about testing apps
        </Text>
        <Text style={styles.bulletPoint}>
          - Educate developers about secure app development
        </Text>
      <View style={styles.homeStyle}>
        <Text style={styles.text}>
          For more information got the settings and follow the links.
        </Text>
        <Text style={styles.text}>
          Here are some statistics:
        </Text>
        <Stats />
      </View>
    </ScrollView>
  );
}

export default HomeScreen;
