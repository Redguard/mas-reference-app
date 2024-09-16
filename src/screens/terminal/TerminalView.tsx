import React, {forwardRef, useImperativeHandle, useRef, useState} from 'react';
import {ScrollView, Text, View} from 'react-native';
import styles from './style';

const TerminalView = forwardRef((props, ref) => {
  const [lines, setLines] = useState<{text: string; color: string}[]>([
    {text: '$ Welcome to the MAS Reference App Shell', color: '#00ff00'},
  ]);
  const scrollViewRef = useRef<ScrollView>(null); // Create a ref for the ScrollView

  // Function to add a new line to the terminal
  const addLine = (newLine: string, color: string) => {
    setLines(prevLines => [...prevLines, {text: newLine, color}]);
    if (scrollViewRef.current) {
      scrollViewRef.current.scrollToEnd({animated: true});
    }
  };

  const addSuccess = (message: string) => {
    addLine('$ [+] ' + message, '#00ff00');
  };

  const addFailure = (message: string) => {
    addLine('$ [!] ' + message, 'red');
  };

  useImperativeHandle(ref, () => ({
    addSuccess,
    addFailure,
  }));

  return (
    <View style={styles.container}>
      <ScrollView ref={scrollViewRef}>
        {lines.map((line, index) => (
          <Text key={index} style={[styles.lineText, {color: line.color}]}>
            {line.text}
          </Text>
        ))}
      </ScrollView>
    </View>
  );
});

export default TerminalView;
