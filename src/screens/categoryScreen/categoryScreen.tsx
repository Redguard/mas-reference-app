import {ScrollView, Text, TouchableOpacity, View} from 'react-native';
import styles from './styles.tsx';
import TestScreen from '../testScreen/testScreen.tsx';
import {TestGroup} from '../../appContent.tsx';

import React, {useRef} from 'react';
import {createNativeStackNavigator} from '@react-navigation/native-stack';
import TerminalView from '../terminal/TerminalView.tsx';

function CategoryScreen({route, navigation}: any): React.JSX.Element {
  var platformTestCases: TestGroup[] = route.params.tests;

  return (
    <ScrollView style={styles.categoryDescription}>
      <Text>{route.params.description}</Text>
      {platformTestCases.map(testCase => {
        return (
          <TouchableOpacity
            key={testCase.title.replace(' ', '_')}
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
  const terminalRef = useRef(null);

  return (
    <View style={styles.container}>
      <View style={styles.content}>
        <CategoryStack.Navigator screenOptions={{headerShown: false}}>
          <CategoryStack.Screen
            name=" "
            component={CategoryScreen}
            initialParams={route.params}
          />
          <CategoryStack.Screen
            name="Tests"
            component={TestScreen}
            initialParams={{...route.params, terminalRef}}
          />
        </CategoryStack.Navigator>
      </View>
      <TerminalView ref={terminalRef} />
    </View>
  );
}

export default CategoryStackScreen;
