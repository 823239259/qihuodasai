import React, { Component } from 'react';
import { View, TextInput, Text, TouchableOpacity } from 'react-native';
import { inject, observer } from 'mobx-react/native';
import { Navigation } from 'react-native-navigation';

@inject('TodoStore') @observer
export default class TodoAddScreen extends Component {
  constructor(props) {
    super(props);

    this.state = {
      todo: ''
    };
  }
  _addTodo(TodoStore) {
    TodoStore.addTodo(this.state.todo);
  }
  _dismiss() {
    Navigation.dismissModal({
      animationType: 'slide-down'
    });
  }
  render() {
    const TodoStore = this.props.TodoStore;

    return (
      <View style={{ flex: 1, justifyContent: 'space-between' }}>
        <View style={{ height: 50, justifyContent: 'center' }}>
          <TextInput
            value={this.state.todo}
            onChangeText={(text) => { this.setState({ todo: text }); }}
            placeholder={'something'}
            underlineColorAndroid={'transparent'}
            style={{ padding: 0, textAlign: 'center' }}
            autoCapitalize={'none'}
          />
        </View>
        <View style={{ height: 100 }}>
          <TouchableOpacity 
            style={[styles.Btn, { backgroundColor: 'rgb(27, 198, 98)' }]}
            onPress={() => {
              this._addTodo(TodoStore);
              this._dismiss();
            }}            
          >
            <Text style={{ color: 'white' }}>Save</Text>
          </TouchableOpacity>
          <TouchableOpacity 
            style={[styles.Btn, { backgroundColor: 'rgb(233, 139, 60)' }]}
            onPress={() => {
              this._dismiss();
            }}
          >
            <Text style={{ color: 'white' }}>Cancel</Text>
          </TouchableOpacity>
        </View>
      </View>
    );
  }
}
const styles = {
  Btn: {
    flex: 1, 
    flexDirection: 'row',
    justifyContent: 'center',
    alignItems: 'center',
    // height: 40
  }
};
