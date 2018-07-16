import React, { Component } from 'react';
import { View, Text, StyleSheet, TouchableOpacity } from 'react-native';
import { observable } from 'mobx';
import validate from 'mobx-form-validate';
import { composeKeyboard } from '../../../hoc';
import { inject, observer } from 'mobx-react/native';
import { Submit, Dialog, FieldProvider, InputFieldEnhanced, DisplayUnderline, ImageVerification } from '../../../components';
import { Variables, NavigatorStyle, Colors, Layout, Enum } from '../../../global';
import { I18n, Logger } from '../../../utils';

class LoginStore {
  @observable
  @validate(Enum.validateReg.mobile, `${I18n.message('please_input_corret_phone_number')}`)
  mobile = '';

  @observable
  @validate(Enum.validateReg.password, `${I18n.message('please_input_correct_password')}`)
  pwd = '';

  @observable imageCode = '';
}

@inject('AccountStore') @observer
class LoginModal extends Component {
  constructor(props) {
    super(props);
    this.logger = new Logger(LoginModal);
    this.store = new LoginStore();
    this.setStoreData();
    this.props.navigator.setOnNavigatorEvent(this.onNavigatorEvent.bind(this));
  }
  onNavigatorEvent(event) { // this is the onPress handler for the two buttons together
    const { AccountStore } = this.props;
    if (event.type === 'NavBarButtonPress') { // this is the event type for button presses
        if (event.id === Variables.icon.closeLogin.id) { // this is the same id field from the static navigatorButtons definition
          this.props.navigator.dismissModal();
          AccountStore.setInitModal(null);
        }
    }
  }
  // mobileFromStore & pwdFromStore 都存在 表示是 1.註冊頁面(RegisterModal) 2.密碼重置成功(AccountLoginPwd) 跳轉過來
  // mobileFromStore 存在 表示 1.修改手機號(AccountCellphone) 跳轉過來
  setStoreData() {
    if (this.props.mobileFromStore) {
      this.store.mobile = this.props.mobileFromStore;
      if (this.props.pwdFromStore) {
        this.store.pwd = this.props.pwdFromStore;
        this.logger.info(`註冊成功 | 密碼重置成功 - mobile: ${this.props.mobileFromStore}, pwd: ${this.props.pwdFromStore}`);
        return;
      }
      this.logger.info(`修改手機號 - mobile: ${this.props.mobileFromStore}`);
    }
  }
  _toRegister = () => {
    const { AccountStore } = this.props;
    if (AccountStore.initModal === AccountStore.ACCOUNT_MODAL.register) {
      this.props.navigator.pop();
    } else {
      this.props.navigator.push({
        screen: 'account.RegisterModal',
        title: '注册',
        animationType: 'slide-horizontal',
        navigatorButtons: {
          leftButtons: [
              {
                  icon: Variables.icon.closeRegister.source,
                  id: Variables.icon.closeRegister.id
              }
          ]
        }
      });
    }
  }
  _onImageVerificationConfirm = () => {
    this.props.AccountStore.loginImageVerify(this.store.mobile, this.store.pwd, this.store.imageCode);
    this.store.imageCode = '';
  }
  _onImageVerificationCancel = () => {
    this.props.AccountStore.setIsLoginImageDialogVisible(false);
  }
  _toAccountLoginPwd= () => {
    this.props.navigator.push({
      screen: 'account.AccountLoginPwd',
      title: '忘记密码',
      backButtonTitle: '',
      animationType: 'slide-horizontal'
    });
  }
  render() {
    const { AccountStore } = this.props;

    return (
      <View style={{ flex: 1 }}>
        <FieldProvider form={this.store}>
          <View style={styles.container}>
            <InputFieldEnhanced name='mobile' type='stringNumber' label='手机' style={{ marginTop: Layout.fieldMargin }} />
            <InputFieldEnhanced name='pwd' type='string' label='密码' secureTextEntry />

            <DisplayUnderline label='' isButton={true} buttonText={'忘记密码'} isButtomBorder={false} isBottomMargin={false} onButtonPress={this._toAccountLoginPwd} />
            
            <Submit text={'登录'} onSubmit={() => AccountStore.login(this.store.mobile, this.store.pwd)} />
            
            <TouchableOpacity style={[styles.centerText, { marginTop: Layout.fieldMargin }]} onPress={this._toRegister}>
              <Text style={{ color: Colors.white }}>注册帐号</Text>
            </TouchableOpacity>
            <Dialog
              visible={AccountStore.isLoginImageDialogVisible}
              header={'请先输入图形验证码'}
              renderContent={() => <ImageVerification />}
              onConfirm={this._onImageVerificationConfirm}
              onCancel={this._onImageVerificationCancel}
            />
          </View>
        </FieldProvider>
      </View>
    );
  }
}
const composedLoginModal = composeKeyboard(LoginModal);
composedLoginModal.navigatorStyle = NavigatorStyle.modalStyle;

export default composedLoginModal;

const styles = StyleSheet.create({
    container: {
      marginHorizontal: Layout.fieldHorizontalMargin
    },
    centerText: {
      flexDirection: 'row',
      ...Layout.center
    }
});
