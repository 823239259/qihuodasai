import React, { Component } from 'react';
import { WebView, View } from 'react-native';
import { PropTypes } from 'prop-types';
import { Variables, Config, NavigatorStyle, Layout } from '../../global';
import { Logger } from '../../utils';

export default class AccountDepositWebView extends Component {
  static propTypes = {
    money: PropTypes.string
  }
  constructor(props) {
    super(props);
    this.logger = new Logger(AccountDepositWebView);
  }
  render() {
    const uri = `${Config.api_recharge_url}/app/appPayinfo?mobile=${Variables.account.mobile.value}&money=${this.props.money}`;
    this.logger.info(`充值 - mobile: ${Variables.account.mobile.value}, money: ${this.props.money}`);
    return (
        <WebView
          source={{ uri }}
          style={{ marginBottom: Layout.isIphoneX ? Layout.iphoneXPaddingButton : 0 }}
          automaticallyAdjustContentInsets={true}
          scalesPageToFit={true}
          mixedContentMode={'always'}
          javaScriptEnabled={true}
          domStorageEnabled={true}
          startInLoadingState={true}
        />
    );
  }
}
AccountDepositWebView.navigatorStyle = NavigatorStyle.screenInnerStyle;
