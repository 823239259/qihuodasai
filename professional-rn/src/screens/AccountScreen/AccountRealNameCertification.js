import React, { Component } from 'react';
import { View, Text } from 'react-native';
import { observable, action } from 'mobx';
import { inject, observer } from 'mobx-react/native';
import validate from 'mobx-form-validate';
import { Submit, ToastRoot, FieldProvider, InputFieldEnhanced } from '../../components';
import { Layout, Enum, NavigatorStyle } from '../../global';
import { I18n, Api } from '../../utils';

class AccountRealNameCertificationStore {
    
    accountStore = null;

    constructor(accountStore) {
        this.accountStore = accountStore;
    }
    @observable
    @validate(Enum.validateReg.cnName, `${I18n.message('please_input_real_name')}`)
    name = '';

    @observable
    @validate(Enum.validateReg.cnId, `${I18n.message('please_input_real_id')}`)
    id = '';

    @action validateRealName() {
        Api.validateRealName(this.name, this.id, () => this.validateRealName(), (result) => this.validateRealNameFail(result));
    }
    @action.bound validateRealNameSuccess() {
        // 更新帳戶資訊
        this.accountStore.getBalance();
        ToastRoot.show('认证成功');
    }
    @action.bound validateRealNameFail(result) {
        if (result.code == 2) {
            ToastRoot.show('实名认证失败');
        } else if (result.code == 3) {
            ToastRoot.show('身份证号格式有误');
        } else if (result.code == 4) {
            ToastRoot.show('该身份证已被认证过');
        } else if (result.code == 5) {
            ToastRoot.show('实名不能为空');
        } else if (result.code == 6) {
            ToastRoot.show('身份证号码不能为空');
        } else if (result.code == 7) {
            ToastRoot.show('已经实名认证过');
        } else if (result.code == 8) {
            ToastRoot.show('实名失败次数已超过最高次数');
        } else {
            ToastRoot.show('系统繁忙，请稍后再试');
        }
    }
}
@inject('AccountStore') @observer
export default class AccountRealNameCertification extends Component {
    constructor(props) {
        super(props);
        this.store = new AccountRealNameCertificationStore(this.props.AccountStore);
    }
    render() {
        return (
            <FieldProvider form={this.store}>
                <View style={{ marginHorizontal: Layout.fieldHorizontalMargin }}>
                    <InputFieldEnhanced name='name' type='string' label='真实姓名' style={{ marginTop: Layout.fieldMargin }} />
                    <InputFieldEnhanced name='id' type='string' label='身份证号' />
                    <Submit text={'立即认证'} onSubmit={() => this.store.validateRealName()} />
                </View>
            </FieldProvider>
        );
    }
}
AccountRealNameCertification.navigatorStyle = NavigatorStyle.screenInnerStyle;
