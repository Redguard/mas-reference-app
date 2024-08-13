import {Image} from 'react-native';
import React from 'react';
import styles from './style';

function HeaderBackground(): React.JSX.Element {
  return (
    <Image
      style={styles.header}
      source={require('../../assets/owasp_background.png')}
      resizeMode="stretch"
    />
  );
}

export default HeaderBackground;
