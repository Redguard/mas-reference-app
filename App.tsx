/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 */

import * as React from 'react';
import {createDrawerNavigator} from '@react-navigation/drawer';
import {View, Button} from 'react-native';
import {NavigationContainer} from '@react-navigation/native';
import TestCases from './src/screens/testcases.tsx';

function HomeScreen({navigation}): React.JSX.Element {
  return (
    <View style={{flex: 1, alignItems: 'center', justifyContent: 'center'}}>
      <Button
        onPress={() => navigation.navigate('Notification')}
        title="Go to notifications"
      />
    </View>
  );
}

function AutomationScreen({navigation}): React.JSX.Element {
  return (
    <View style={{flex: 1, alignItems: 'center', justifyContent: 'center'}}>
      <Button onPress={() => navigation.goBack()} title="Go back home" />
    </View>
  );
}

const Drawer = createDrawerNavigator();

function App(): React.JSX.Element {
  return (
    <NavigationContainer>
      <Drawer.Navigator initialRouteName="Home">
        <Drawer.Screen name="Home" component={HomeScreen} />
        <Drawer.Screen name="Automation" component={AutomationScreen} />

        {TestCases.map(item => {
          return (
            <Drawer.Screen
              key={item.key}
              name={'MASVS-' + item.key}
              component={item.screenName}
              options={{
                headerStyle: {backgroundColor: item.color},
                drawerItemStyle: {backgroundColor: item.color},
                drawerLabelStyle: {color: 'white'},
                headerTitleStyle: {color: 'white'},
              }}
            />
          );
        })}
      </Drawer.Navigator>
    </NavigationContainer>
  );
}

export default App;
