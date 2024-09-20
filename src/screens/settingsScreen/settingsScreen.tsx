import React, {useEffect, useState} from 'react';
import {
  SafeAreaView,
  ScrollView,
  View,
  Text,
  TouchableOpacity,
  TextInput,
  Linking,
} from 'react-native';
import styles from './style.tsx';
import FeatherIcon from 'react-native-vector-icons/Feather';
import DialogInput from 'react-native-dialog-input';
import EncryptedStorage from 'react-native-encrypted-storage';
import {MasSettings} from '../../../App.tsx';

function SettingsScreen(): React.JSX.Element {
  const [isDialogVisible, setIsDialogVisible] = useState(false);
  const [selectedSetting, setSelectedSetting] = useState(null);
  const [settings, setSettings] = useState({
    httpTestDomain: '',
    httpsTestDomain: '',
    canaryToken: '',
    androidApiKey: '',
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

  const handleDialogSubmit = (inputText: string) => {
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
    const {httpTestDomain, httpsTestDomain, canaryToken, androidApiKey} =
      updatedSettings;

    const masSettings: MasSettings = {
      httpTestDomain: httpTestDomain,
      httpsTestDomain: httpsTestDomain,
      canaryToken: canaryToken,
      androidApiKey: androidApiKey,
    };

    try {
      EncryptedStorage.setItem(
        'MasReferenceAppSettings',
        JSON.stringify(masSettings),
      );
    } catch (error) {
      console.log(error);
    }
  };

  const handleCancel = () => {
    setIsDialogVisible(false);
  };

  const getSettingLabel = (
    key: 'httpTestDomain' | 'httpsTestDomain' | 'canaryToken' | 'androidApiKey',
  ) => {
    const labels = {
      httpTestDomain: 'HTTP Test Domain',
      httpsTestDomain: 'HTTPS Test Domain',
      canaryToken: 'Canary Token',
      androidApiKey: 'Android API Key',
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
                  handleRowPress('httpTestDomain');
                }}
                style={styles.row}>
                <Text style={styles.rowLabel}>HTTP Test Domain</Text>
                <View style={styles.rowSpacer} />
                <Text numberOfLines={1} style={styles.rowValue}>
                  {settings.httpTestDomain}
                </Text>
                <FeatherIcon color="#bcbcbc" name="chevron-right" size={19} />
              </TouchableOpacity>
            </View>
            <View style={[styles.rowWrapper]}>
              <TouchableOpacity
                onPress={() => {
                  handleRowPress('httpsTestDomain');
                }}
                style={styles.row}>
                <Text style={styles.rowLabel}>HTTPS Test Domain</Text>
                <View style={styles.rowSpacer} />
                <Text numberOfLines={1} style={styles.rowValue}>
                  {settings.httpsTestDomain}
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
                <Text numberOfLines={1} style={styles.rowValue}>
                  {settings.canaryToken}
                </Text>
                <FeatherIcon color="#bcbcbc" name="chevron-right" size={19} />
              </TouchableOpacity>
            </View>
            <View style={[styles.rowWrapper, styles.rowLast]}>
              <TouchableOpacity
                onPress={() => {
                  handleRowPress('androidApiKey');
                }}
                style={styles.row}>
                <Text style={styles.rowLabel}>Android API-Key</Text>
                <View style={styles.rowSpacer} />
                <TextInput
                  editable={false}
                  secureTextEntry={true}
                  numberOfLines={1}
                  style={styles.rowValue}>
                  {settings.androidApiKey}
                </TextInput>
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
        {/* <View style={styles.section}>
          <View style={styles.sectionBody}>
            <View
              style={[
                styles.rowWrapper,
                styles.rowFirst,
                styles.rowLast,
                {alignItems: 'center'},
              ]}>
              <TouchableOpacity
                onPress={() => {
                  // handle onPress
                }}
                style={styles.row}>
                <Text style={[styles.rowLabel, styles.rowLabelLogout]}>
                  Log Out
                </Text>
              </TouchableOpacity>
            </View>
          </View>
        </View> */}
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
