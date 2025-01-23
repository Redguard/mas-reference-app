import React, {useEffect, useState} from 'react';
import {
  SafeAreaView,
  ScrollView,
  View,
  Text,
  TouchableOpacity,
  Linking,
  NativeModules,
} from 'react-native';
import styles from './style.tsx';
import FeatherIcon from 'react-native-vector-icons/Feather';
import DialogInput from 'react-native-dialog-input';
import EncryptedStorage from 'react-native-encrypted-storage';
import {MasSettings} from '../../../App.tsx';

const {MasSettingsSync} = NativeModules;

function SettingsScreen(): React.JSX.Element {
  const [isDialogVisible, setIsDialogVisible] = useState(false);
  const [selectedSetting, setSelectedSetting] = useState(null);
  const [settings, setSettings] = useState({
    testDomain: '',
    canaryToken: '',
    androidCloudProjectNumber: '',
  });

  useEffect(() => {
    const fetchSettings = async () => {
      try {
        const globalSettings: string | null = await EncryptedStorage.getItem(
          'MasReferenceAppSettings',
        );
        if (globalSettings) {
          setSettings(JSON.parse(globalSettings));
        }
      } catch (error) {
        console.error('Failed to retrieve global settings:', error);
      } finally {
      }
    };

    fetchSettings();
  }, []);

  const handleRowPress = (settingKey: any) => {
    setSelectedSetting(settingKey);
    setIsDialogVisible(true);
  };

  const handleDialogSubmit = (inputText: any) => {
    if (selectedSetting) {
      setSettings(prevSettings => {
        const updatedSettings = {
          ...prevSettings,
          [selectedSetting]: inputText,
        };
        updateGlobalSettings(updatedSettings);
        return updatedSettings;
      });
    }
    setIsDialogVisible(false);
  };

  const updateGlobalSettings = (updatedSettings: typeof settings) => {
    const {testDomain, canaryToken, androidCloudProjectNumber} =
      updatedSettings;

    const masSettings: MasSettings = {
      testDomain: testDomain,
      canaryToken: canaryToken,
      androidCloudProjectNumber: androidCloudProjectNumber,
    };
    try {
      EncryptedStorage.setItem(
        'MasReferenceAppSettings',
        JSON.stringify(masSettings),
      );
      // sync settings to native app
      MasSettingsSync.setSettings(JSON.stringify(masSettings));
    } catch (error) {
      console.log(error);
    }
  };

  const handleCancel = () => {
    setIsDialogVisible(false);
  };

  const getSettingLabel = (
    key: 'testDomain' | 'canaryToken' | 'androidCloudProjectNumber',
  ) => {
    const labels = {
      testDomain: 'Test Domain',
      canaryToken: 'Canary Token',
      androidCloudProjectNumber: 'Android Cloud Project Number',
    };
    return labels[key];
  };

  return (
    <SafeAreaView style={styles.safeArea}>
      <ScrollView contentContainerStyle={styles.content}>
        <View style={styles.section}>
          <Text style={styles.sectionTitle}>Preferences</Text>
          <View style={styles.sectionBody}>
            <View style={[styles.rowWrapper, styles.rowFirst]}>
              <TouchableOpacity
                onPress={() => {
                  handleRowPress('testDomain');
                }}
                style={styles.row}>
                <Text style={styles.rowLabel}>Test Domain</Text>
                <View style={styles.rowSpacer} />
                <Text
                  ellipsizeMode="tail"
                  numberOfLines={1}
                  style={styles.rowValue}>
                  {settings.testDomain}
                </Text>
                <FeatherIcon color="#bcbcbc" name="chevron-right" size={19} />
              </TouchableOpacity>
            </View>
            <View style={styles.rowWrapper}>
              <TouchableOpacity
                onPress={() => {
                  handleRowPress('canaryToken');
                }}
                style={styles.row}>
                <Text style={styles.rowLabel}>Canary Token</Text>
                <View style={styles.rowSpacer} />
                <Text
                  ellipsizeMode="tail"
                  numberOfLines={1}
                  style={styles.rowValue}>
                  {settings.canaryToken}
                </Text>
                <FeatherIcon color="#bcbcbc" name="chevron-right" size={19} />
              </TouchableOpacity>
            </View>
            <View style={[styles.rowWrapper, styles.rowLast]}>
              <TouchableOpacity
                onPress={() => {
                  handleRowPress('androidCloudProjectNumber');
                }}
                style={styles.row}>
                <Text style={styles.rowLabel}>
                  Android Cloud Project Number
                </Text>
                <View style={styles.rowSpacer} />
                <Text
                  ellipsizeMode="tail"
                  numberOfLines={1}
                  style={styles.rowValue}>
                  {settings.androidCloudProjectNumber}
                </Text>
                <FeatherIcon color="#bcbcbc" name="chevron-right" size={19} />
              </TouchableOpacity>
            </View>
          </View>
        </View>
        <View style={styles.section}>
          <Text style={styles.sectionTitle}>Resources</Text>
          <View style={styles.sectionBody}>
            <View style={[styles.rowWrapper, styles.rowFirst]}>
              <TouchableOpacity
                onPress={() => {
                  Linking.openURL('https://mas.owasp.org/');
                }}
                style={styles.row}>
                <Text style={styles.rowLabel}>MAS Website</Text>
                <View style={styles.rowSpacer} />
                <FeatherIcon color="#bcbcbc" name="chevron-right" size={19} />
              </TouchableOpacity>
            </View>
            <View style={styles.rowWrapper}>
              <TouchableOpacity
                onPress={() => {
                  Linking.openURL('https://mas.owasp.org/MASVS/');
                }}
                style={styles.row}>
                <Text style={styles.rowLabel}>MAS Verification Standard</Text>
                <View style={styles.rowSpacer} />
                <FeatherIcon color="#bcbcbc" name="chevron-right" size={19} />
              </TouchableOpacity>
            </View>
            <View style={styles.rowWrapper}>
              <TouchableOpacity
                onPress={() => {
                  Linking.openURL('https://mas.owasp.org/MASWE/');
                }}
                style={styles.row}>
                <Text style={styles.rowLabel}>MAS Weaktness Enumeration</Text>
                <View style={styles.rowSpacer} />
                <FeatherIcon color="#bcbcbc" name="chevron-right" size={19} />
              </TouchableOpacity>
            </View>
            <View style={[styles.rowWrapper, styles.rowLast]}>
              <TouchableOpacity
                onPress={() => {
                  Linking.openURL('https://mas.owasp.org/MASTG/');
                }}
                style={styles.row}>
                <Text style={styles.rowLabel}>MAS Testing Guide</Text>
                <View style={styles.rowSpacer} />
                <FeatherIcon color="#bcbcbc" name="chevron-right" size={19} />
              </TouchableOpacity>
            </View>
          </View>
        </View>
        <Text style={styles.contentFooter}>App Version 0.10</Text>

        {/* Dynamic DialogInput */}
        <DialogInput
          isDialogVisible={isDialogVisible}
          title={`Enter ${
            selectedSetting ? getSettingLabel(selectedSetting) : ''
          }`}
          hintInput={'Enter value'}
          initValueTextInput={selectedSetting ? settings[selectedSetting] : ''}
          submitInput={inputText => {
            const currentSettingValue = selectedSetting
              ? settings[selectedSetting]
              : '';
            if (inputText === '' && currentSettingValue !== '') {
              handleDialogSubmit(currentSettingValue);
            } else {
              handleDialogSubmit(inputText);
            }
          }}
          closeDialog={handleCancel}
        />
      </ScrollView>
    </SafeAreaView>
  );
  // );
}

export default SettingsScreen;
