import EncryptedStorage from 'react-native-encrypted-storage';
import { NativeModules } from 'react-native';
const { MasSettingsSync } = NativeModules;

export type MasSettings = {
  testDomain: string;
  canaryToken: string;
  androidCloudProjectNumber: string;
};

export class GlobalSettingsManager {
  private static instance: GlobalSettingsManager;
  private settings: MasSettings | null = null;
  private readonly storageKey = 'MasReferenceAppSettings';
  private readonly defaultSettings: MasSettings = {
    testDomain: 'mas-reference-app.org',
    canaryToken: '__MAS.OWASP.MAS__',
    androidCloudProjectNumber: '0',
  };

  private constructor() {}

  public static getInstance(): GlobalSettingsManager {
    if (!GlobalSettingsManager.instance) {
      GlobalSettingsManager.instance = new GlobalSettingsManager();
    }
    return GlobalSettingsManager.instance;
  }

  public async initialize(): Promise<void> {
    try {
      const storedSettings = await EncryptedStorage.getItem(this.storageKey);

      if (storedSettings === null) {
        await this.saveSettings(this.defaultSettings);
      } else {
        this.settings = JSON.parse(storedSettings);
      }

      await this.syncToNative();
    } catch (error) {
      console.error('Failed to initialize settings:', error);
    }
  }

  public getSettings(): MasSettings {
    console.log('getting settings');
    if (!this.settings) {
      return this.defaultSettings;
    }
    return this.settings;
  }


  public async updateSettings(newSettings: Partial<MasSettings>): Promise<boolean> {
    try {
      const currentSettings = this.getSettings();
      const updatedSettings = { ...currentSettings, ...newSettings };
      await this.saveSettings(updatedSettings);
      await this.syncToNative();
      return true;
    } catch (error) {
      console.error('Failed to update settings:', error);
      return false;
    }
  }

  public async resetToDefaults(): Promise<boolean> {
    try {
      await this.saveSettings(this.defaultSettings);
      await this.syncToNative();
      return true;
    } catch (error) {
      console.error('Failed to reset settings:', error);
      return false;
    }
  }

  private async saveSettings(settings: MasSettings): Promise<void> {
    try {
      await EncryptedStorage.setItem(
        this.storageKey,
        JSON.stringify(settings)
      );
      this.settings = settings;
    } catch (error) {
      console.error('Failed to save settings:', error);
      throw error;
    }
  }

  private async syncToNative(): Promise<void> {
    try {
      if (MasSettingsSync && this.settings) {
        const result = await MasSettingsSync.setSettings(
          JSON.stringify(this.settings)
        );
        console.log('Settings synced to native:', result);
      }
    } catch (error) {
      console.error('Failed to sync settings to native:', error);
    }
  }
}
