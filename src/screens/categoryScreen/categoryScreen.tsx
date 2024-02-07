import {ScrollView, Text, TouchableOpacity, Platform} from 'react-native';
import styles from './styles.tsx';
import {TestCases} from '../testcases.tsx';
import TestScreen from '../testScreen/testScreen.tsx';

import React from 'react';
import {createNativeStackNavigator} from '@react-navigation/native-stack';

function CategoryScreen({route, navigation}: any): React.JSX.Element {
  var generalTestCases: TestCases[] = route.params.testCases[0];
  const androidTestCases: TestCases[] = route.params.testCases[1];
  const iosTestCases: TestCases[] = route.params.testCases[2];

  var platformTestCases: TestCases[] = generalTestCases;

  if (Platform.OS === 'android') {
    platformTestCases = platformTestCases.concat(androidTestCases);
  } else if (Platform.OS === 'ios') {
    platformTestCases = platformTestCases.concat(iosTestCases);
  }

  return (
    <ScrollView style={styles.categoryDescription}>
      <Text>{route.params.description}</Text>
      {platformTestCases.map(testCase => {
        return (
          <TouchableOpacity
            key={testCase.id}
            style={styles.button}
            onPress={() =>
              navigation.navigate('Tests', {
                testCase: testCase,
              })
            }>
            <Text>{testCase.title}</Text>
          </TouchableOpacity>
        );
      })}
    </ScrollView>
  );
}

const CategoryStack = createNativeStackNavigator();

function CategoryStackScreen({route}: {route: any}): React.JSX.Element {
  return (
    <CategoryStack.Navigator>
      <CategoryStack.Screen
        name=" "
        component={CategoryScreen}
        initialParams={route.params}
      />
      <CategoryStack.Screen
        name="Tests"
        component={TestScreen}
        initialParams={route.params}
      />
    </CategoryStack.Navigator>
  );
}

export default CategoryStackScreen;
