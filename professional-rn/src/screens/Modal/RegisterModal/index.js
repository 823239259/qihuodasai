import React, { Component } from 'react';
import { View, Text, StyleSheet, TouchableOpacity } from 'react-native';
import { composeKeyboard } from '../../../hoc';
import { inject, observer } from 'mobx-react/native';
import { Submit, FieldProvider, InputFieldEnhanced, Dialog, ImageVerification } from '../../../components';
import { Variables, NavigatorStyle, Colors, Layout } from '../../../global';

@inject('AccountStore', 'RegisterStore') @observer
class RegisterModal extends Component {
  constructor(props) {
    super(props);
    this.store = this.props.RegisterStore;
    this.store.init(this.props.navigator);
    this.props.navigator.setOnNavigatorEvent(this.onNavigatorEvent.bind(this));
  }
  onNavigatorEvent(event) { // this is the onPress handler for the two buttons together
    const { AccountStore } = this.props;
    if (event.type === 'NavBarButtonPress') { // this is the event type for button presses
        if (event.id === Variables.icon.closeRegister.id) { // this is the same id field from the static navigatorButtons definition
          this.props.navigator.dismissModal();
          AccountStore.setInitModal(null);
        }
    }
  }
  _toLogin() {
    const { AccountStore } = this.props;
    if (AccountStore.initModal === AccountStore.ACCOUNT_MODAL.login) {
      this.props.navigator.pop();
    } else {
      this.props.navigator.push({
        screen: 'account.LoginModal',
        title: '登录',
        animationType: 'slide-horizontal',
        navigatorButtons: {
          leftButtons: [
              {
                  icon: Variables.icon.closeLogin.source,
                  id: Variables.icon.closeLogin.id
              }
          ]
        }
      });
    }
  }
  render() {
    return (
      <View style={{ flex: 1 }}>
        <FieldProvider form={this.store}>
          <View style={{ marginHorizontal: Layout.fieldHorizontalMargin }}>
            <InputFieldEnhanced name='mobile' type='int' label='手机' style={{ marginTop: Layout.fieldMargin }} />
            <InputFieldEnhanced 
              name='verification'
              type='stringNumber' 
              label='验证码' 
              isVerification={true}
              onVerificationPress={() => this.store.onVerificationPress()}
            />
            <Submit text={'下一步'} onSubmit={() => this.store.next()} />
            <TouchableOpacity style={[styles.centerText, { marginTop: Layout.fieldMargin }]} onPress={() => this._toLogin()}>
              <Text style={{ color: Colors.white }}>登录帐号</Text>
            </TouchableOpacity>
            <Dialog 
              visible={this.store.isRegisterImageDialogVisible}
              header={'请先输入图形验证码'}
              renderContent={() => <ImageVerification />}
              onConfirm={() => this.store.confirmImageVerification()}
              onCancel={() => this.store.setIsRegisterImageDialogVisible(false)}
            />
          </View>
        </FieldProvider>
      </View>
    );
  }
}
const composedRegisterModal = composeKeyboard(RegisterModal);
composedRegisterModal.navigatorStyle = NavigatorStyle.modalStyle;
export default composedRegisterModal;

const styles = StyleSheet.create({
    container: {
        flex: 1,
        padding: 20,
    },
    centerText: {
        flexDirection: 'row',
        justifyContent: 'center',
        alignItems: 'center'
    }
});
