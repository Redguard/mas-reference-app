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

const STORAGE_COLOR = '#df5c8c';
const CRYPTO_COLOR = '#f65928';
const AUTH_COLOR = '#f09236';
const NETWORK_COLOR = '#f2c200';
const PLATFORM_COLOR = '#4fb990';
const CODE_COLOR = '#5facd3';
const RESILIENCE_COLOR = '#317bc0';
const PRIVACY_COLOR = '#8b5f9e';

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

function NotificationsScreen({navigation}): React.JSX.Element {
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
        <Drawer.Screen name="Automation" component={NotificationsScreen} />
        <Drawer.Screen
          name="MASVS-STORAGE"
          component={NotificationsScreen}
          options={{
            headerStyle: {backgroundColor: STORAGE_COLOR},
            drawerItemStyle: {backgroundColor: STORAGE_COLOR},
            drawerLabelStyle: {color: 'white'},
            headerTitleStyle: {color: 'white'},
          }}
        />
        <Drawer.Screen
          name="MASVS-CRYPYO"
          component={NotificationsScreen}
          options={{
            headerStyle: {backgroundColor: CRYPTO_COLOR},
            drawerItemStyle: {backgroundColor: CRYPTO_COLOR},
            drawerLabelStyle: {color: 'white'},
            headerTitleStyle: {color: 'white'},
          }}
        />
        <Drawer.Screen
          name="MASVS-AUTH"
          component={NotificationsScreen}
          options={{
            headerStyle: {backgroundColor: AUTH_COLOR},
            drawerItemStyle: {backgroundColor: AUTH_COLOR},
            drawerLabelStyle: {color: 'white'},
            headerTitleStyle: {color: 'white'},
          }}
        />
        <Drawer.Screen
          name="MASVS-NETWORK"
          component={NotificationsScreen}
          options={{
            headerStyle: {backgroundColor: NETWORK_COLOR},
            drawerItemStyle: {backgroundColor: NETWORK_COLOR},
            drawerLabelStyle: {color: 'white'},
            headerTitleStyle: {color: 'white'},
          }}
        />
        <Drawer.Screen
          name="MASVS-PLATFORM"
          component={NotificationsScreen}
          options={{
            headerStyle: {backgroundColor: PLATFORM_COLOR},
            drawerItemStyle: {backgroundColor: PLATFORM_COLOR},
            drawerLabelStyle: {color: 'white'},
            headerTitleStyle: {color: 'white'},
          }}
        />
        <Drawer.Screen
          name="MASVS-CODE"
          component={NotificationsScreen}
          options={{
            headerStyle: {backgroundColor: CODE_COLOR},
            drawerItemStyle: {backgroundColor: CODE_COLOR},
            drawerLabelStyle: {color: 'white'},
            headerTitleStyle: {color: 'white'},
          }}
        />
        <Drawer.Screen
          name="MASVS-RESILIENCE"
          component={NotificationsScreen}
          options={{
            headerStyle: {backgroundColor: RESILIENCE_COLOR},
            drawerItemStyle: {backgroundColor: RESILIENCE_COLOR},
            drawerLabelStyle: {color: 'white'},
            headerTitleStyle: {color: 'white'},
          }}
        />
        <Drawer.Screen
          name="MASVS-PRIVACY"
          component={NotificationsScreen}
          options={{
            headerStyle: {backgroundColor: PRIVACY_COLOR},
            drawerItemStyle: {backgroundColor: PRIVACY_COLOR},
            drawerLabelStyle: {color: 'white'},
            headerTitleStyle: {color: 'white'},
          }}
        />
      </Drawer.Navigator>
    </NavigationContainer>
  );
}

export default App;
