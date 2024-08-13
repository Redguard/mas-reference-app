import React, {useState} from 'react';
import {ScrollView, Text, View} from 'react-native';
import styles from './style';

const TerminalView = () => {
  const [lines] = useState(['Welcome to the React Native Shell']);
  // const scrollViewRef = useRef();

  return (
    <View style={styles.container}>
      <ScrollView>
        {lines.map((line, index) => (
          <Text key={index} style={styles.lineText}>
            {line}
          </Text>
        ))}
      </ScrollView>
    </View>
  );
};

export default TerminalView;
