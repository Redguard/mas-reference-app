import {ScrollView, Text, TouchableOpacity, Platform} from 'react-native';
import styles from './style.tsx';
import {TestCases} from '../testcases.tsx';
import React from 'react';

function CategoryScreen({route}: {route: any}): React.JSX.Element {
  const generalTestCases: TestCases[] = route.params.testCases[0];
  const androidTestCases: TestCases[] = route.params.testCases[1];
  const iosTestCases: TestCases[] = route.params.testCases[2];

  return (
    <ScrollView style={styles.categoryDescription}>
      <Text>{route.params.description}</Text>
      {generalTestCases.map(testCase => {
        return (
          <TouchableOpacity key={testCase.id} style={styles.button}>
            <Text>{testCase.title}</Text>
          </TouchableOpacity>
        );
      })}
      {androidTestCases.map(testCase => {
        if (Platform.OS === 'android') {
          return (
            <TouchableOpacity key={testCase.id} style={styles.button}>
              <Text>{testCase.title}</Text>
            </TouchableOpacity>
          );
        }
      })}
      {iosTestCases.map(testCase => {
        if (Platform.OS === 'ios') {
          return (
            <TouchableOpacity key={testCase.id} style={styles.button}>
              <Text>{testCase.title}</Text>
            </TouchableOpacity>
          );
        }
      })}
    </ScrollView>
  );
}

export default CategoryScreen;
