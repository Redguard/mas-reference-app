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
import SettingsScreen from './src/screens/settingsScreen/settingsScreen.tsx';
import appContent from './src/appContent.tsx';
import HeaderBackground from './src/screens/header/headerBackground.tsx';
import EncryptedStorage from 'react-native-encrypted-storage';
import { Settings } from 'react-native-feather';

export type MasSettings = {
  httpTestDomain: string;
  httpsTestDomain: string;
  canaryToken: string;
  androidApiKey: string;
};


const Drawer = createDrawerNavigator();

async function initGlobalSettings(){
  try {
    const settings = await EncryptedStorage.getItem('MasReferenceAppSettings');
    if (settings === null) {

      console.log('First start, storing default settings.');

      const masSettings: MasSettings = {
        httpTestDomain: 'redguard.ch',
        httpsTestDomain: 'redguard.ch',
        canaryToken: '__MAS.SECRET.eL4$wV__',
        androidApiKey: '',
      };
      try {
        await EncryptedStorage.setItem(
            'MasReferenceAppSettings',
            JSON.stringify(masSettings)
        );
        } catch (error) {
          console.log(error);
    }
    }

    } catch (error) {
      console.log(error);
    }
}

function App(): React.JSX.Element {

  initGlobalSettings();

  return (
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
          name="Settings"
          component={SettingsScreen}
          options={{
            headerBackground: () => <HeaderBackground />,
          }}/>
      </Drawer.Navigator>
    </NavigationContainer>
  );
}

export default App;
