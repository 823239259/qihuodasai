import React, { Component } from 'react';
import { View, Text } from 'react-native';
import { observable, action } from 'mobx';
import validate from 'mobx-form-validate';
import { Submit, ToastRoot, FieldProvider, InputFieldEnhanced } from '../../components';
import { Layout, Enum, Variables, NavigatorStyle, Config } from '../../global';
import { composeKeyboard } from '../../hoc';
import { I18n, Api } from '../../utils';

class AccountLoginPwdStore {
    navigator = null;
    
    @observable
    @validate(Enum.validateReg.mobile, `${I18n.message('please_input_corret_phone_number')}`)
    mobile = '';

    @observable
    @validate(Enum.validateReg.required, `${I18n.message('required')}`)
    verification = '';

    @observable
    @validate(Enum.validateReg.password, `${I18n.message('please_input_correct_password')}`)
    newPwd = '';

    @observable
    @validate(Enum.validateReg.password, `${I18n.message('please_input_correct_password')}`)
    confirmPwd = '';

    constructor(navigator) {
        this.navigator = navigator;
    }
    @action onVerificationPress() {
        Api.sendSecuritySms(this.mobile, 1,() => this.sendSmsSuccess(), (result) => this.sendSmsFail(result));
    }
    @action.bound sendSmsSuccess() {
        // 開始倒數
        this.imageCodeCount = Enum.imageCodeCount;
        ToastRoot.show('发送成功');
    }
    @action.bound sendSmsFail(result) {
        if (result.code !== '1') {
            ToastRoot.show(result.message);
        }
    }
    @action submit() {
        if (this.newPwd !== this.confirmPwd) {
            ToastRoot.show('两次输入密码不一致');
            return;
        }
        Api.resetPassword(this.newPwd, this.verification, () => this.resetPasswordSuccess(), (result) => this.resetPasswordFail(result));
    }
    @action.bound resetPasswordFail(result) {
        if (result.code !== '1') {
            ToastRoot.show(result.message);
        }
    }
    @action.bound resetPasswordSuccess() {
        ToastRoot.show('密码重置成功，请重新登录');
        
        setTimeout(() => {
            this.toLogin();
        }, Config.waitingTime);
        setTimeout(() => {
            this.navigator.pop();
        }, Config.waitingTime * 2);  
    }
    toLogin() {
        this.navigator.showModal({
            screen: 'account.LoginModal',
            title: '登录',
            passProps: {
                mobileFromStore: this.mobile,
                pwdFromStore: this.newPwd,
            },
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

class AccountLoginPwd extends Component {
    constructor(props) {
        super(props);
        this.store = new AccountLoginPwdStore(this.props.navigator);
    }
    render() {
        return (
            <FieldProvider form={this.store}>
                <View style={{ marginHorizontal: Layout.fieldHorizontalMargin }}>
                    <InputFieldEnhanced name='mobile' type='int' label='手机号码' style={{ marginTop: Layout.fieldMargin }} />
                    <InputFieldEnhanced 
                        name='verification' 
                        type='stringNumber' 
                        label='验证码' 
                        isVerification={true}
                        style={{ marginBottom: Layout.fieldMargin }}
                        onVerificationPress={() => this.store.onVerificationPress()}
                    />
                    <InputFieldEnhanced name='newPwd' type='string' label='重置密码' secureTextEntry style={{ marginTop: Layout.fieldMargin }} />
                    <InputFieldEnhanced name='confirmPwd' type='string' label='确认密码' secureTextEntry />

                    <Submit text={'立即认证'} onSubmit={() => this.store.submit()} />
                </View>
            </FieldProvider>
        );
    }
}
const composedAccountLoginPwd = composeKeyboard(AccountLoginPwd);
composedAccountLoginPwd.navigatorStyle = NavigatorStyle.screenInnerStyle;
export default composedAccountLoginPwd;
