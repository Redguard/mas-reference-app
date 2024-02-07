import {ScrollView, Text, TouchableOpacity} from 'react-native';
import styles from './style.tsx';
import React from 'react';

function CategoryScreen({route}: {route: any}): React.JSX.Element {
  return (
    <ScrollView style={styles.categoryDescription}>
      <Text>{route.params.description}</Text>
      <TouchableOpacity style={styles.button}>
        <Text>Press Here</Text>
      </TouchableOpacity>
      <TouchableOpacity style={styles.button}>
        <Text>Press Here</Text>
      </TouchableOpacity>
      <TouchableOpacity style={styles.button}>
        <Text>Press Here</Text>
      </TouchableOpacity>
      <TouchableOpacity style={styles.button}>
        <Text>Press Here</Text>
      </TouchableOpacity>
      <TouchableOpacity style={styles.button}>
        <Text>Press Here</Text>
      </TouchableOpacity>
      <TouchableOpacity style={styles.button}>
        <Text>Press Here</Text>
      </TouchableOpacity>
      <TouchableOpacity style={styles.button}>
        <Text>Press Here</Text>
      </TouchableOpacity>
    </ScrollView>
  );
}

export default CategoryScreen;
