/* 
  帳戶
  
  localstorage 
  有token: button  - 退出
  無token: buttons - 登錄/註冊

  Modal:
  1.LoginModal
  2.RegisterModal - 註冊: -> 發送驗證碼
  3.註冊密碼：ConfirmPasswordModal
*/
import React, { Component } from 'react';
import { View, StyleSheet, ScrollView } from 'react-native';
import { inject, observer } from 'mobx-react/native';
import { Variables, Colors, Layout } from '../../global';
import { ButtonCommon } from '../../components';
import { ContentRow, CardHeader, CardBalance } from './components';

@inject('AccountStore') @observer
export default class AccountScreen extends Component {
  constructor(props) {
    super(props);
    this.init();
  }
  componentWillMount() {
    this.props.navigator.setButtons({
      rightButtons: [{ id: Variables.icon.accountCustomerService.id, icon: Variables.icon.accountCustomerService.source }]
    });
  }
  componentDidMount() {
    this.props.navigator.setOnNavigatorEvent(this.onNavigatorEvent.bind(this));
  }
  onNavigatorEvent(event) { // this is the onPress handler for the two buttons together
    if (event.type === 'NavBarButtonPress') { // this is the event type for button presses
      if (event.id === Variables.icon.accountCustomerService.id) { // this is the same id field from the static navigatorButtons definition
        this._toCustomerService();
      } 
    }
  }
  _toCustomerService() {
    this.props.navigator.showModal({
      screen: 'account.CustomerServiceModal',
      title: '在线客服',
      navigatorButtons: {
        leftButtons: [
            {
                icon: Variables.icon.closeAccountCustomerService.source,
                id: Variables.icon.closeAccountCustomerService.id
            }
        ]
      }
    }); 
  }
  init() {
    this.props.AccountStore.init(this.props.navigator);
  }
  _toLogin() {
    const { AccountStore } = this.props;

    this.props.navigator.showModal({
      screen: 'account.LoginModal',
      title: '登录',
      navigatorButtons: {
        leftButtons: [
            {
                icon: Variables.icon.closeLogin.source,
                id: Variables.icon.closeLogin.id
            }
        ]
      }
    });
    AccountStore.setInitModal(AccountStore.ACCOUNT_MODAL.login);
  }
  _toRegister() {
    const { AccountStore } = this.props;

    this.props.navigator.showModal({
      screen: 'account.RegisterModal',
      title: '注册',
      navigatorButtons: {
        leftButtons: [
            {
                icon: Variables.icon.closeRegister.source,
                id: Variables.icon.closeRegister.id
            }
        ]
      }
    });

    AccountStore.setInitModal(AccountStore.ACCOUNT_MODAL.register);
  }
  _renderHeader() {
    return <CardHeader />;
  }
  _renderBalanceOrButtons() {
    if (this.props.AccountStore.isLogged) {
      return <CardBalance />;
    }
    return (
      <View style={styles.balanceOrButtonsContainer}>
        <View style={styles.buttonContainer}>
          <ButtonCommon text={'登录'} style={{ height: Layout.buttonLargeHeight, marginRight: Layout.accountContainerPadding }} isBorderRadius={false} backgroundColor={Colors.red} color={Colors.white} onPress={() => this._toLogin()} />
          <ButtonCommon text={'注册'} color={Colors.red} style={{ height: Layout.buttonLargeHeight, borderWidth: 1, borderColor: Colors.red }} isBorderRadius={false} onPress={() => this._toRegister()} />
        </View>
      </View>
    );
  }
  _renderContent() {
    if (this.props.AccountStore.isLogged) {
      return (
        <ScrollView>
          { 
            this.props.AccountStore.contentRows.map((data, index) => {
              return <ContentRow key={`contentRow${index}`} icon={data.icon} title={data.title} text={data.text} screen={data.screen} navigator={this.props.navigator} />;
            })
          }
        </ScrollView>
      );
    }
  }
  render() {
    return (
      <View style={styles.container}>
          { this._renderHeader() }
          { this._renderBalanceOrButtons() }
          { this._renderContent() }
      </View>
    );
  }
}
const styles = StyleSheet.create({
  container: {
    flex: 1
  },
  balanceOrButtonsContainer: {
    height: 100,
    alignItems: 'center',
    ...Colors.accountBalance
  },
  buttonContainer: {
    flex: 1,
    flexDirection: 'row',
    marginHorizontal: Layout.accountContainerPadding,
    alignItems: 'center'
  },
  bottomButtonStyle: {
    height: 50,
    flexDirection: 'row'
  }
});
