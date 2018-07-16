import React, { PureComponent } from 'react';
import {
  View,
  Text,
  StyleSheet,
  Dimensions
} from 'react-native';
import RefreshableFlatList from 'react-native-refreshable-flatlist';
import { IndicatorPullToRefresh } from '../../src/components';

const { width } = Dimensions.get('window');
const styles = StyleSheet.create({
  container: {
    flex: 1
  },
  topBar: {
    backgroundColor: '#F7F7F8',
    height: 64,
    zIndex: 10,
  },
  row: {
    padding: 10,
    height: 125,
    width,
    backgroundColor: '#ffffff',
    borderTopWidth: 1,
    marginBottom: -1,
    borderBottomColor: '#E5EDF5',
    borderTopColor: '#E5EDF5',
    borderBottomWidth: 1,
    justifyContent: 'center'
  },
  text: {
    textAlign: 'center',
    color: '#6da3d0'
  },
  navText: {
    color: '#6da3d0',
    fontSize: 20,
    fontWeight: '700',
    textAlign: 'center',
    paddingTop: 30
  }
});

export default class TestRefreshableFlatList extends PureComponent {
  state = {
    data: new Array(3).fill(1).map((x, i) => ({ id: i, text: `Item No. ${i}` })),
  }

  render() {
    return (
      <View style={styles.container}>
        <View style={styles.topBar}><Text style={styles.navText}>RefreshableFlatList</Text></View>
        <RefreshableFlatList
          showTopIndicator={false}
          bottomIndicatorComponent={IndicatorPullToRefresh}
          data={this.state.data}
          renderItem={({ item }) => (
            <View key={item.id} style={styles.row}>
              <Text style={styles.text}>{item.text}</Text>
            </View>
          )}
          ref={(ref) => { this.flatList = ref; }}
          onRefreshing={() => new Promise((r) => {
            setTimeout(() => {
              r();
            }, 3000);
          })}
          onLoadMore={() => new Promise((r) => {
            setTimeout(() => {
              const no = this.state.data.length;
              const newArr = new Array(4).fill(1).map((x, i) => ({ id: i + no, text: `Item No. ${i + no}` }));
              this.setState({ data: this.state.data.concat(newArr) });
              r();
            }, 2500);
          })}
          keyExtractor={item => item.id}
          styles={{ prompt: { color: 'gray' } }}
        />
      </View>
    );
  }
}