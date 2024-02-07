import {ScrollView, Text} from 'react-native';
import styles from './style.tsx';

function CategoryScreen({route}: {route: any}): React.JSX.Element {
  return (
    <ScrollView style={styles.categoryDescription}>
      <Text>{route.params.description}</Text>
    </ScrollView>
  );
}

export default CategoryScreen;
