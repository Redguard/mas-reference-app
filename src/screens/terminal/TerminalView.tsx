import React, {forwardRef, useImperativeHandle, useRef, useState} from 'react';
import {ScrollView, Text, View} from 'react-native';
import styles from './style';

const TerminalView = forwardRef((props, ref) => {
  const [lines, setLines] = useState(['Welcome to the React Native Shell']);
  const scrollViewRef = useRef<ScrollView>(null); // Create a ref for the ScrollView

  // Function to add a new line to the terminal
  const addLine = (newLine: string) => {
    setLines(prevLines => [...prevLines, newLine]);
    if (scrollViewRef.current) {
      scrollViewRef.current.scrollToEnd({animated: true});
    }
  };

  useImperativeHandle(ref, () => ({
    addLine,
  }));

  return (
    <View style={styles.container}>
      <ScrollView ref={scrollViewRef}>
        {lines.map((line, index) => (
          <Text key={index} style={styles.lineText}>
            {line}
          </Text>
        ))}
      </ScrollView>
    </View>
  );
});

export default TerminalView;
