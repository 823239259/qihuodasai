import React, { Component } from 'react';
import { WebView, View } from 'react-native';
import { Variables, Config, NavigatorStyle, Layout } from '../../global';

export default class CustomerServiceModal extends Component {
  constructor(props) {
      super(props);
      this.props.navigator.setOnNavigatorEvent(this.onNavigatorEvent.bind(this));
  }
  onNavigatorEvent(event) { // this is the onPress handler for the two buttons together
    if (event.type === 'NavBarButtonPress') { // this is the event type for button presses
        if (event.id === Variables.icon.closeAccountCustomerService.id) { // this is the same id field from the static navigatorButtons definition
        this.props.navigator.dismissModal();
        }
    }
  }
  render() {
    const uri = `${Config.customer_service_url}?phone=${Variables.account.mobile.value}&userName=${Variables.account.name.value}`;
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
CustomerServiceModal.navigatorStyle = NavigatorStyle.modalStyle;
