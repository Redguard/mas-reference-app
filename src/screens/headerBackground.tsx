import {Image} from 'react-native';
import React from 'react';

function HeaderBackground(): React.JSX.Element {
  return (
    <Image
      style={{width: '100%', height: '100%'}}
      source={require('../assets/owasp_background.png')}
      resizeMode="stretch"
    />
  );
}

export default HeaderBackground;
