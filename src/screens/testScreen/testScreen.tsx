import {ScrollView, Text, TouchableOpacity} from 'react-native';
import styles from './styles.tsx';
import React, {Component, useLayoutEffect} from 'react';
import {TestCases} from '../../appContent.tsx';

class ExecuteTestButton extends Component<any, any> {
  nativeFunction: any;

  constructor(props: any) {
    super(props);
    this.nativeFunction = props.nativeFunction;
    this.state = {successful: false};
  }

  onPress() {
    // execute the native function
    const ret: string = this.nativeFunction();
    console.log(ret);
    this.setState({successful: true});
  }

  render() {
    return (
      <TouchableOpacity
        style={
          this.state.successful === true ? styles.buttonPressed : styles.button
        }
        onPress={() => this.onPress()}>
        <Text>{this.props.title}</Text>
      </TouchableOpacity>
    );
  }
}

function TestScreen({route, navigation}: any): React.JSX.Element {
  var testCases: TestCases = route.params.testCase;
  useLayoutEffect(() => {
    navigation.setOptions({
      title: testCases.title,
      headerShown: true,
    });
  }, [testCases.title, navigation]);

  return (
    <ScrollView style={styles.categoryDescription}>
      <Text>{route.params.description}</Text>
      {testCases.testCases.map(testCase => {
        return (
          <ExecuteTestButton
            key={testCase.title.replace(' ', '_')}
            nativeFunction={testCase.nativeFunction}
            title={testCase.title}
          />
        );
      })}
    </ScrollView>
  );
}

export default TestScreen;
