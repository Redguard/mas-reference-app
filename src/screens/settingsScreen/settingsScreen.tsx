// @ts-nocheck
import React, {useState} from 'react';
import {
  SafeAreaView,
  ScrollView,
  View,
  Text,
  TouchableOpacity,
  Linking,
} from 'react-native';
import styles from './style.tsx';
import FeatherIcon from 'react-native-vector-icons/Feather';
import DialogInput from 'react-native-dialog-input';
import { GlobalSettingsManager } from '../../globalSettingsManager.tsx';


function SettingsScreen(): React.JSX.Element {
  const [isDialogVisible, setIsDialogVisible] = useState(false);
  const [selectedSetting, setSelectedSetting] = useState(null);
  const [settings, setSettings] = useState(() => {
    return GlobalSettingsManager.getInstance().getSettings();
  });

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
    GlobalSettingsManager.getInstance().updateSettings({
      testDomain: testDomain,
      canaryToken: canaryToken,
      androidCloudProjectNumber: androidCloudProjectNumber,
    });
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
                  Linking.openURL('https://github.com/Redguard/mas-reference-app');
                }}
                style={styles.row}>
                <Text style={styles.rowLabel}>Source Code</Text>
                <View style={styles.rowSpacer} />
                <FeatherIcon color="#bcbcbc" name="chevron-right" size={19} />
              </TouchableOpacity>
            </View>
            <View style={styles.rowWrapper}>
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
                <Text style={styles.rowLabel}>MAS Weakness Enumeration</Text>
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
        <Text style={styles.contentFooter}>App Version 0.50</Text>

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
