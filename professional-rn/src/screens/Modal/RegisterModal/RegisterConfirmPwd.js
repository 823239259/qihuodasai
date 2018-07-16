import React, { Component } from 'react';
import { inject, observer } from 'mobx-react/native';
import validate from 'mobx-form-validate';
import { observable } from 'mobx';
import { View } from 'react-native';
import { Submit, FieldProvider, InputFieldEnhanced, Dialog, ToastRoot } from '../../../components';
import { Layout, Enum, NavigatorStyle } from '../../../global';
import { I18n } from '../../../utils';

class RegisterConfirmPwdStore {
    @observable
    @validate(Enum.validateReg.password, `${I18n.message('please_input_correct_password')}`)
    newPwd = '';
  
    @observable
    @validate(Enum.validateReg.password, `${I18n.message('please_input_correct_password')}`)
    confirmPwd = '';
}

@inject('RegisterStore') @observer
export default class RegisterConfirmPwd extends Component {
    constructor(props) {
        super(props);
        this.store = new RegisterConfirmPwdStore();
    }
    _regist() {
        if (this.store.newPwd !== this.store.confirmPwd) {
            ToastRoot.show('两次输入密码不一致');
            return;
        }
        this.props.RegisterStore.regist(this.store.newPwd);
    }
    render() {
        const { RegisterStore } = this.props;
        return (
            <View style={{ flex: 1 }}>
                <FieldProvider form={this.store}>
                    <View style={{ marginHorizontal: Layout.fieldHorizontalMargin }}>
                        <InputFieldEnhanced name='newPwd' secureTextEntry type='string' label='设置密码' style={{ marginTop: Layout.fieldMargin }} />
                        <InputFieldEnhanced name='confirmPwd' secureTextEntry type='string' label='确认密码' />
                        <Submit text={'立即注册'} onSubmit={() => this._regist()} />
                    </View>
                </FieldProvider>
                <Dialog 
                    visible={RegisterStore.isRegistSuccessDialogVisible}
                    content={'您已注册成功赶紧去登录吧'}
                    height={Layout.dialogHeight}
                    isCancel={false}
                    onConfirm={() => RegisterStore.confirmRegistSuccess()}
                />
            </View>
        );
    }
}
RegisterConfirmPwd.navigatorStyle = NavigatorStyle.navigatorStyle;
