/* eslint-disable react/no-unstable-nested-components */
/**
 * Sample React Native App
 * https://github.com/facebook/react-native
 *
 * @format
 */

import * as React from 'react';
import {NativeModules, StyleSheet, Text, View} from 'react-native';
import FastImage from 'react-native-fast-image';

import {createDrawerNavigator} from '@react-navigation/drawer';

import {NavigationContainer} from '@react-navigation/native';
import CategoryStackScreen from './src/screens/categoryScreen/categoryScreen.tsx';
import HomeScreen from './src/screens/homeScreen/homeScreen.tsx';
import SettingsScreen from './src/screens/settingsScreen/settingsScreen.tsx';
import CtfScreen from './src/screens/ctfScreen/ctfScreen.tsx';
import appContent from './src/appContent.tsx';
import HeaderBackground from './src/screens/header/headerBackground.tsx';
import EncryptedStorage from 'react-native-encrypted-storage';
import { MenuProvider } from 'react-native-popup-menu';
import LottieView from 'lottie-react-native';
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
const styles = StyleSheet.create({
  container: {
    flex: 1,
  },
  text: {
    fontSize: 24,
    fontWeight: 'bold',
    color: "rgb(61, 61, 61)",
  },
  lottieWrapper: {
    position: 'absolute',
    top: 0,
    left: 0,
    right: 0,
    bottom: 0,
    zIndex: 1000,
    pointerEvents: 'none',
    flexDirection: 'row',
    justifyContent: 'space-around',
    alignItems: 'center',
  },
  lottie: {
    width: 100,
    height: 100,
  },
});

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
            name="CTF Memory"
            component={CtfScreen}
            options={{
              drawerItemStyle: {backgroundColor: 'rgb(255,239,255'},
              headerBackground: () => <HeaderBackground />,
              drawerLabel: () => (
                <View style={styles.container}>
                <View>
                  <Text style={styles.text}>CTF Memory</Text>
                </View>
                <View style={styles.lottieWrapper} pointerEvents="none">
                  <LottieView
                    source={require('./src/assets/sparkle.json')}
                    autoPlay={true}
                    loop={true}
                    style={styles.lottie}
                    resizeMode="cover"
                  />
                 <LottieView
                    source={require('./src/assets/sparkle.json')}
                    autoPlay={true}
                    loop={true}
                    style={styles.lottie}
                    resizeMode="cover"
                  />
                 <LottieView
                    source={require('./src/assets/sparkle.json')}
                    autoPlay={true}
                    loop={true}
                    style={styles.lottie}
                    resizeMode="cover"
                  />
                   <LottieView
                    source={require('./src/assets/sparkle.json')}
                    autoPlay={true}
                    loop={true}
                    style={styles.lottie}
                    resizeMode="cover"
                  />
                </View>
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
