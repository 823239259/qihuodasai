/*
  資訊直播
*/
import React, { Component } from 'react';
import { View, TextInput, TouchableOpacity, Text, FlatList, ScrollView } from 'react-native';
import Icon from 'react-native-vector-icons/FontAwesome';
import { inject, observer } from 'mobx-react/native';
import { Navigation } from 'react-native-navigation';

@inject('TodoStore') @observer
export default class InformationScreen extends Component { 
  render() {
    const TodoStore = this.props.TodoStore;

    return (
      <View style={{ flex: 1 }}>
        {/* filter */}
        <View style={{ backgroundColor: 'rgba(188, 188, 188, 0.5)', borderRadius: 25, height: 35, alignItems: 'center', flexDirection: 'row', marginTop: 10 }}>
          <Icon 
            name="search" 
            size={25} 
            color={'black'} 
            style={{ marginLeft: 10, flex: 1 }} 
          />
          <TextInput 
            onChangeText={(text) => {
              TodoStore.filter = text;
            }}
            underlineColorAndroid={'transparent'}
            autoCapitalize={'none'}
            value={TodoStore.filter}
            style={{ padding: 0, flex: 5 }}
          />
        </View>
        {/* list */}
        {/*<MyFlatList todos={TodoStore.todos} />*/}
        <ScrollView style={{ flex: 1, marginTop: 10 }}>
          {TodoStore.filteredTodos.map((todo) => {
            return (
              <View
                key={todo.id}
                style={{ height: 50, borderWidth: 1, borderColor: 'rgba(180, 180, 180, 0.5)', flex: 1, flexDirection: 'row', alignItems: 'center' }}
              >
                <Text style={{ flex: 1, textAlign: 'center', color: 'rgb(255, 255, 255)' }}>{todo.value}</Text>
                <View style={{ flex: 1, alignItems: 'center' }}>
                  <TouchableOpacity onPress={() => TodoStore.toggleTodo(todo.id)}>
                    {
                      todo.complete  
                      ? <Icon name="check-square-o" size={25} color={'rgb(1, 116, 0)'} />   
                      : <Icon name="square-o" size={25} color={'rgb(1, 116, 0)'} />   
                    }
                
                  </TouchableOpacity>
                </View>
              </View>              
            );
          })}
        </ScrollView>
        {/* buttons */}
        <View style={{ flexDirection: 'row' }}>
          <TouchableOpacity 
            style={[styles.bigBtn, { backgroundColor: 'rgb(255, 168, 166)' }]}
            onPress={() => {
              TodoStore.clearComplete();
            }}
          >
            <Text style={{ color: 'white' }}>Clear Completed</Text>
          </TouchableOpacity>
          <TouchableOpacity 
            style={[styles.bigBtn, { backgroundColor: 'rgb(144, 195, 212)' }]}
            onPress={() => {
              Navigation.showModal({
                screen: 'todoScreen.TodoAddScreen',
                title: 'Add Todo',
                animationType: 'slide-up'
              });       
            }}
          >
            <Text style={{ color: 'white' }}>Add</Text>
          </TouchableOpacity>
        </View>
      </View>
    );
  }
}
// Can not be observed, FlatList is a PureComponent
// @observer
// class MyFlatList extends Component {
//   render() {
//     return (
//         <FlatList
//           style={{ flex: 1, marginTop: 10 }}
//           data={this.props.todos}
//           renderItem={({ item }) => (
//             <View
//               style={{ height: 50, borderWidth: 1, borderColor: 'rgba(180, 180, 180, 0.5)', flexDirection: 'row', alignItems: 'center' }}
//             >
//               <View style={{ backgroundColor: 'white' }}>
//                 <Text>{item.value}</Text>
//               </View>
//             </View>
//           )}
//           keyExtractor={item => item.id}  
//         />      
//     );
//   }
// }
const styles = {
  bigBtn: {
    flex: 1, 
    flexDirection: 'row',
    justifyContent: 'center',
    alignItems: 'center',
    height: 40
  }
};

