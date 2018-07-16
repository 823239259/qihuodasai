import React, { Component } from 'react';
import { View, StyleSheet } from 'react-native';
import { inject, observer } from 'mobx-react/native';
import PropTypes from 'prop-types';
import { FieldProvider, InputFieldEnhanced, Submit } from '../../components';
import { Variables, Layout, NavigatorStyle } from '../../global';

@inject('TradeLoginModalStore') @observer
export default class TradeLoginModal extends Component {
  static propTypes = {
    // 點擊登入 登入成功後 執行的function - 
    // 1.WorkbenchDetailStore.tradeLoginSuccess() : 隱藏modal + tab轉到交易中心
    // 2.ApplyDetailTradeAccount._tradeLoginSuccess() : 轉到 行情 tab + 隱藏modal
    onTradeLoginSuccess: PropTypes.func
  }

  static defaultProps = {

  }  
  constructor(props) {
    super(props);
    this.store = this.props.TradeLoginModalStore;
    this.store.onTradeLoginSuccess = this.props.onTradeLoginSuccess;
    this.props.navigator.setOnNavigatorEvent(this.onNavigatorEvent.bind(this));
  }
  componentWillMount() {
    // 操盤申請 -> 方案明細 -> 立即操盤
    if (this.props.account) {
      this.store.account = this.props.account;
      this.store.pwd = this.props.pwd;
      return;
    }
    // 從AsyncStorage取得
    if (Variables.trade.account.value) {
      this.store.account = Variables.trade.account.value;
      this.store.pwd = Variables.trade.password.value;
    }
  }
  onNavigatorEvent(event) { // this is the onPress handler for the two buttons together
    if (event.type === 'NavBarButtonPress') { // this is the event type for button presses
        if (event.id === Variables.icon.closeTradeLogin.id) { // this is the same id field from the static navigatorButtons definition
          this.props.navigator.dismissModal();
        }
    }
  }
  _submit() {
    this.store.setIsSubmitted(true);
    this.store.loginTrade();
  }

  render() {
    const labelAccount = '操盘账户';
    const labelPwd = '操盘密码';
    const textSubmit = '登录账号';
    
    return (
      <View style={{ flex: 1 }}>
        <FieldProvider form={this.store}>
          <View style={styles.container}>
            <InputFieldEnhanced name='account' type='string' label={labelAccount} style={{ marginTop: Layout.fieldMargin }} />
            <InputFieldEnhanced name='pwd' type='string' label={labelPwd} secureTextEntry />
            <Submit text={textSubmit} submitted={this.store.isSubmitted} onSubmit={() => this._submit()} />
          </View>
        </FieldProvider>
      </View>
    );
  }
}
TradeLoginModal.navigatorStyle = NavigatorStyle.modalStyle;

const styles = StyleSheet.create({
    container: {
      marginHorizontal: Layout.fieldHorizontalMargin
    },
    centerText: {
      flexDirection: 'row',
      ...Layout.center
    }
});
