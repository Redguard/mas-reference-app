/* eslint-disable react/no-unstable-nested-components */
import * as React from 'react';
import { Text, View} from 'react-native';

import {createDrawerNavigator} from '@react-navigation/drawer';

import {NavigationContainer} from '@react-navigation/native';
import CategoryStackScreen from './src/screens/categoryScreen/categoryScreen.tsx';
import HomeScreen from './src/screens/homeScreen/homeScreen.tsx';
import SettingsScreen from './src/screens/settingsScreen/settingsScreen.tsx';
import CtfScreen from './src/screens/ctfScreen/ctfScreen.tsx';
import appContent from './src/appContent.tsx';
import HeaderBackground from './src/screens/header/headerBackground.tsx';
import { MenuProvider } from 'react-native-popup-menu';
import { GlobalSettingsManager } from './src/globalSettingsManager.tsx';


const Drawer = createDrawerNavigator();

function App(): React.JSX.Element {
  GlobalSettingsManager.getInstance().initialize();

  return (
    <MenuProvider>
      <NavigationContainer>
        <Drawer.Navigator initialRouteName="Home">
          <Drawer.Screen
            name="Home"
            component={HomeScreen}
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
          <Drawer.Screen
            name="CTF Game"
            component={CtfScreen}
            options={{
              drawerItemStyle: {backgroundColor: 'rgb(225,239,255)'},
              headerBackground: () => <HeaderBackground />,
              drawerLabel: () => (
                <View>
                  <Text>CTF Game</Text>
              </View>
              ),
            }}/>
          <Drawer.Screen
            name="Settings"
            component={SettingsScreen}
            options={{
              headerBackground: () => <HeaderBackground />,
            }}/>
        </Drawer.Navigator>
      </NavigationContainer>
    </MenuProvider>
  );
}

export default App;
