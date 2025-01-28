/* eslint-disable react/no-unstable-nested-components */
/* eslint-disable prettier/prettier */
/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 */

import * as React from 'react';
import {NativeModules} from 'react-native';

import {createDrawerNavigator} from '@react-navigation/drawer';

import {NavigationContainer} from '@react-navigation/native';
import CategoryStackScreen from './src/screens/categoryScreen/categoryScreen.tsx';
import HomeScreen from './src/screens/homeScreen/homeScreen.tsx';
import SettingsScreen from './src/screens/settingsScreen/settingsScreen.tsx';
import appContent from './src/appContent.tsx';
import HeaderBackground from './src/screens/header/headerBackground.tsx';
import EncryptedStorage from 'react-native-encrypted-storage';
import { MenuProvider } from 'react-native-popup-menu';
const {MasSettingsSync} = NativeModules;

export type MasSettings = {
  testDomain: string;
  canaryToken: string;
  androidCloudProjectNumber: string;
};

const Drawer = createDrawerNavigator();

async function syncGlobalSettingsNativeApp(){
  try {
    var settings = await EncryptedStorage.getItem('MasReferenceAppSettings');
    console.log(MasSettingsSync.setSettings(settings));
    } catch (error) {
      console.log(error);
  }
}


async function initGlobalSettings(){
  try {
    var settings = await EncryptedStorage.getItem('MasReferenceAppSettings');
    if (settings === null) {
      const masSettings: MasSettings = {
        testDomain: 'mas-reference-app.org',
        canaryToken: '__MAS.OWASP.MAS__',
        androidCloudProjectNumber: '0',
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
      syncGlobalSettingsNativeApp();
    } catch (error) {
    console.log(error);
  }
}

function App(): React.JSX.Element {

  initGlobalSettings();

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
