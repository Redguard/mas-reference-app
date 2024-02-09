import {ScrollView, Text, TouchableOpacity} from 'react-native';
import styles from './styles.tsx';
import {TestCases} from '../testcases.tsx';
import TestScreen from '../testScreen/testScreen.tsx';

import React from 'react';
import {createNativeStackNavigator} from '@react-navigation/native-stack';

function CategoryScreen({route, navigation}: any): React.JSX.Element {
  var platformTestCases: TestCases[] = route.params.tests;

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
