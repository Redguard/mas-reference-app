import {ScrollView, Text, TouchableOpacity, View} from 'react-native';
import styles from './styles.tsx';
import React, {Component, useLayoutEffect} from 'react';
import {TestCases} from '../../appContent.tsx';
import {Menu, MenuOptions, MenuTrigger} from 'react-native-popup-menu';

class ExecuteTestButton extends Component<any, any> {
  nativeFunction: any;
  terminalRef: any;

  constructor(props: any) {
    super(props);
    this.nativeFunction = props.nativeFunction;
    this.state = {successful: false};
    this.terminalRef = props.terminalRef;
  }

  async onPress() {
    // execute the native function
    const ret: string = await this.nativeFunction();

    const retJson = JSON.parse(ret);

    // Send result to terminal
    if (this.terminalRef && this.terminalRef.current) {
      retJson.map((item: any, _: any) => {
        console.log(item);
        if (item.statusCode === 'OK') {
          this.terminalRef.current.addSuccess(item.message);
        } else if (item.statusCode === 'FAIL') {
          this.terminalRef.current.addFailure(item.message);
        }
      });
    }

    console.log(ret);
    this.setState({successful: true});
  }

  render() {
    return (
      <View style={styles.buttonRow}>
        <Menu>
          <MenuTrigger>
            <View style={styles.circle}>
              <Text>?</Text>
            </View>
          </MenuTrigger>
          <MenuOptions>
            <View style={styles.arrow} />
            <View style={styles.popupBox}>
              <Text>{this.props.description}</Text>
            </View>
          </MenuOptions>
        </Menu>

        <TouchableOpacity
          style={
            this.state.successful === true
              ? styles.buttonPressed
              : styles.button
          }
          onPress={() => this.onPress()}>
          <Text>{this.props.title}</Text>
        </TouchableOpacity>
      </View>
    );
  }
}

function TestScreen({route, navigation}: any): React.JSX.Element {
  var testCases: TestCases = route.params.testCase;
  const terminalRef = route.params.terminalRef;

  useLayoutEffect(() => {
    navigation.setOptions({
      title: testCases.title,
      headerShown: true,
    });
  }, [testCases.title, navigation]);

  return (
    <ScrollView style={styles.categoryDescription}>
      <Text>{testCases.description}</Text>
      {testCases.testCases.map(testCase => {
        var title = testCase.title;

        if (testCases.maswe) {
          title += ' (MASWE-' + testCases.maswe + ')';
        } else if (testCase.maswe) {
          title += ' (MASWE-' + testCase.maswe + ')';
        }

        return (
          <ExecuteTestButton
            key={testCase.title.replace(' ', '_')}
            nativeFunction={testCase.nativeFunction}
            title={title}
            description={testCase.description}
            terminalRef={terminalRef}
          />
        );
      })}
    </ScrollView>
  );
}

export default TestScreen;
