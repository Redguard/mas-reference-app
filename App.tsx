/* eslint-disable prettier/prettier */
/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 */

import * as React from 'react';
import {createDrawerNavigator} from '@react-navigation/drawer';
import {NavigationContainer} from '@react-navigation/native';
import CategoryStackScreen from './src/screens/categoryScreen/categoryScreen.tsx';
import HomeScreen from './src/screens/homeScreen/homeScreen.tsx';
import AutomationScreen from './src/screens/automationScreen/automationScreen.tsx';
import appContent from './src/appContent.tsx';
import HeaderBackground from './src/screens/header/headerBackground.tsx';

const Drawer = createDrawerNavigator();



function App(): React.JSX.Element {
  return (
    <NavigationContainer>
      <Drawer.Navigator initialRouteName="Home">
        <Drawer.Screen
          name="Home"
          component={HomeScreen}
          options={{
            headerBackground: () => <HeaderBackground />,
          }}/>
        <Drawer.Screen
          name="Automation"
          component={AutomationScreen}
          options={{
            headerBackground: () => <HeaderBackground />,
          }}/>
        {appContent.map(item => {
          return (
            <Drawer.Screen
              key={item.key}
              name={'MASVS-' + item.key}
              component={CategoryStackScreen}
              initialParams={{
                description: item.description,
                tests: item.tests,
              }}
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
