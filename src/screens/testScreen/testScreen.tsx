import {ScrollView, Text, TouchableOpacity} from 'react-native';
import {TestCases} from '../testcases.tsx';
import styles from './styles.tsx';
import React, {useEffect} from 'react';

function TestScreen({route, navigation}: any): React.JSX.Element {
  var testCases: TestCases = route.params.testCase;
  // Rerender after headerTitle change
  useEffect(() => {
    navigation.setOptions({
      title: testCases.title,
    });
  }, [testCases.title, navigation]);

  return (
    <ScrollView style={styles.categoryDescription}>
      <Text>{route.params.description}</Text>
      {testCases.testCases.map(testCase => {
        return (
          <TouchableOpacity
            key={testCase.id}
            style={styles.button}
            onPress={() => testCase.nativeModule()}>
            <Text>{testCase.title}</Text>
          </TouchableOpacity>
        );
      })}
    </ScrollView>
  );
}

export default TestScreen;
