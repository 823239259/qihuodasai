/*
  操盤申請 -> 申請交易帳號 -> 才能進入交易中心 - TradeView
*/
import React, { Component } from 'react';
import { View, StyleSheet } from 'react-native';
import ScrollableTabView from 'react-native-scrollable-tab-view';
import { inject, observer } from 'mobx-react/native';
import { TabBarDropdown } from '../../components';
import { Layout, Enum } from '../../global';
import ApplyNew from './ApplyNew';
import ApplyRecord from './ApplyRecord';
import { Logger } from '../../utils';

@inject('ApplyTradeStore') @observer
export default class ApplyTradeScreen extends Component {
  constructor(props) {
    super(props);
    this.logger = new Logger(ApplyTradeScreen);

    this.tabView = null;
  }
  componentDidMount() {
    this.props.ApplyTradeStore.init(this.tabView);
  }
  render() {
    const { ApplyTradeStore } = this.props;
    return (
      <View style={styles.container}>
        <ScrollableTabView
          style={{ flex: 1 }}
          locked={true}
          scrollWithoutAnimation={true}
          renderTabBar={() => <TabBarDropdown textStyle={Layout.fontBold} />}
          ref={(tabView) => { this.tabView = tabView; }}
          onChangeTab={(currentTab) => {
            if (currentTab.i === Enum.applyTradeTabView.applyRecord.index) {
              ApplyTradeStore.getTradeAccountList();
            }
          }}
        >
          <ApplyNew tabLabel={Enum.applyTradeTabView.applyNew.text} navigator={this.props.navigator} />
          <ApplyRecord tabLabel={Enum.applyTradeTabView.applyRecord.text} navigator={this.props.navigator} />
        </ScrollableTabView>
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1
  },
});
