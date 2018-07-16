import React, { Component } from 'react';
import { View, AsyncStorage } from 'react-native';
import { observable, action, observe, computed } from 'mobx';
import validate from 'mobx-form-validate';
import { observer } from 'mobx-react/native';
import { Submit, ToastRoot, FieldProvider, InputFieldEnhanced } from '../../components';
import { Layout, Enum, Config, Variables, NavigatorStyle } from '../../global';
import { composeKeyboard } from '../../hoc';
import { I18n, Api } from '../../utils';

function sendSmsSuccess() {
    this.imageCodeCount = Enum.imageCodeCount;
    ToastRoot.show('发送成功');
}
function sendSmsFail(result) {
    if (result.code == 2) {
        ToastRoot.show('短信验证码发送失败！');
    } else if (result.code == 3) {
        ToastRoot.show('手机号码已经注册！');
    } else if (result.code == 4) {
        ToastRoot.show('手机号码不存在！');
    } else if (result.code == 5) {
        ToastRoot.show('操作过于频繁，请稍后再试！');
    }
}
class NewCellphoneStore {
    @observable
    @validate(Enum.validateReg.mobile, `${I18n.message('please_input_corret_phone_number')}`)
    mobile = '';

    @observable
    @validate(Enum.validateReg.required, `${I18n.message('required')}`)
    verification = '';

    @action onVerificationPress() {
        Api.sendSecuritySms(this.mobile, 1, sendSmsSuccess.bind(this), sendSmsFail.bind(this));
    }
}
class OldCellphoneStore {
    @observable
    @validate(Enum.validateReg.mobile, `${I18n.message('please_input_corret_phone_number')}`)
    mobile = '';

    @observable
    @validate(Enum.validateReg.required, `${I18n.message('required')}`)
    verification = '';

    @action onVerificationPress() {
        Api.sendSecuritySms(this.mobile, 2, sendSmsSuccess.bind(this), sendSmsFail.bind(this));
    }
}
class AccountCellphoneStore {

    newCellphoneStore = null;
    oldCellphoneStore = null;

    @observable newMobileValid = false;
    @observable newVerificationValid = false;

    @observable oldMobileValid = false;
    @observable oldVerificationValid = false;

    @computed get isValid() {
        if (this.newMobileValid && this.newVerificationValid && this.oldMobileValid && this.oldVerificationValid) {
            return true;
        }
        return false;
    }

    constructor(newCellphoneStore, oldCellphoneStore) {
        this.newCellphoneStore = newCellphoneStore;
        this.oldCellphoneStore = oldCellphoneStore;

        observe(this.newCellphoneStore, (change) => {
            if (change.name === 'mobile') {
                if (Enum.validateReg.mobile.test(change.newValue)) {
                    this.newMobileValid = true;
                } else {
                    this.newMobileValid = false;
                }
            } else if (change.name === 'verification') {
                if (change.newValue.length > 0) {
                    this.newVerificationValid = true;
                } else {
                    this.newVerificationValid = false;
                }
            }
        });
        observe(this.oldCellphoneStore, (change) => {
            if (change.name === 'mobile') {
                if (Enum.validateReg.mobile.test(change.newValue)) {
                    this.oldMobileValid = true;
                } else {
                    this.oldMobileValid = false;
                }
            } else if (change.name === 'verification') {
                if (change.newValue.length > 0) {
                    this.oldVerificationValid = true;
                } else {
                    this.oldVerificationValid = false;
                }
            }
        });
    }
}
@observer
class AccountCellphone extends Component {
    constructor(props) {
        super(props);
        this.newCellphoneStore = new NewCellphoneStore();
        this.oldCellphoneStore = new OldCellphoneStore();
        this.store = new AccountCellphoneStore(this.newCellphoneStore, this.oldCellphoneStore);
    }

    @action _submit() {
        Api.upPhone(this.newCellphoneStore.mobile, this.newCellphoneStore.verification, this.oldCellphoneStore.verification, () => this._upPhoneSuccess(), (result) => this._upPhoneError(result));
    }
    @action.bound _upPhoneSuccess() {
        ToastRoot.show('修改成功！请使用新号码登录');
        this.clearLogged();
        setTimeout(() => {
            this.toLogin();
            this.props.navigator.pop();
        }, Config.waitingTime * 2);
    }
    @action.bound _upPhoneError(result) {
        if (result.code == 2) {
            ToastRoot.show('手机号码已经注册！');
        } else if (result.code == 3) {
            ToastRoot.show('原验证码超时！');
        } else if (result.code == 4) {
            ToastRoot.show('原验证码错误！');
        } else if (result.code == 5) {
            ToastRoot.show('新验证码超时！');
        } else if (result.code == 6) {
            ToastRoot.show('新验证码错误！');
        } else if (result.code == 7) {
            ToastRoot.show('原验证码不能为空！');
        } else if (result.code == 8) {
            ToastRoot.show('新验证码不能为空！');
        } else if (result.code == 9) {
            ToastRoot.show('新手机号码不能为空！');
        } else {
            ToastRoot.show('新手机号码格式错误！');
        }
    }
    toLogin() {
        this.props.navigator.showModal({
            screen: 'account.LoginModal',
            title: '登录',
            passProps: {
                mobileFromStore: this.newCellphoneStore.mobile,
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
    // 清除暫存登入資訊
    clearLogged() {
        Variables.trade.account.value = null;
        Variables.trade.password.value = null;
        AsyncStorage.removeItem(Variables.trade.account.key);
        AsyncStorage.removeItem(Variables.trade.password.key);
    }
    render() {
        return (
            <View style={{ marginHorizontal: Layout.fieldHorizontalMargin }}>
                <FieldProvider form={this.oldCellphoneStore}>
                    <View>
                        <InputFieldEnhanced name='mobile' type='int' label='原号码' style={{ marginTop: Layout.fieldMargin }} />
                        <InputFieldEnhanced 
                            name='verification' 
                            type='stringNumber' 
                            label='验证码' 
                            isVerification={true} 
                            style={{ marginBottom: Layout.fieldMargin }}
                            onVerificationPress={() => this.oldCellphoneStore.onVerificationPress()}
                        />
                    </View>
                </FieldProvider>
                <FieldProvider form={this.newCellphoneStore}>
                    <View>
                        <InputFieldEnhanced name='mobile' type='int' label='新号码' style={{ marginTop: Layout.fieldMargin }} />
                        <InputFieldEnhanced 
                            name='verification' 
                            type='stringNumber' 
                            label='验证码' 
                            isVerification={true} 
                            onVerificationPress={() => this.newCellphoneStore.onVerificationPress()}
                        />
                    </View>
                </FieldProvider>
                <Submit text={'立即修改'} form={this.store} onSubmit={() => this._submit()} />
            </View>
        );
    }
}
const composedAccountCellphone = composeKeyboard(AccountCellphone);
composedAccountCellphone.navigatorStyle = NavigatorStyle.screenInnerStyle;
export default composedAccountCellphone;
