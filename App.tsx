/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 */

import * as React from 'react';
import {createDrawerNavigator} from '@react-navigation/drawer';
import {NavigationContainer} from '@react-navigation/native';
import TestCases from './src/screens/testcases.tsx';
import CategoryScreen from './src/screens/categoryScreen/categoryScreen.tsx';
import HomeScreen from './src/screens/homeScreen/homeScreen.tsx';
import AutomationScreen from './src/screens/automationScreen/automationScreen.tsx';

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
              component={CategoryScreen}
              initialParams={{description: item.description}}
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
