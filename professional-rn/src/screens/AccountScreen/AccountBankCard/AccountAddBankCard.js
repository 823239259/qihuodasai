import React, { Component } from 'react';
import { View, Text, StyleSheet } from 'react-native';
import { inject, observer } from 'mobx-react/native';
import { observable, action } from 'mobx';
import validate from 'mobx-form-validate';
import { composeKeyboard } from '../../../hoc';
import { FieldProvider, InputFieldEnhanced, SelectFieldEnhanced, Submit, DisplayUnderline, Dialog, ToastRoot } from '../../../components';
import { Layout, Enum, Config, NavigatorStyle } from '../../../global';
import { I18n, Api } from '../../../utils';

class AccountAddBankCardStore {
    navigator = null;
    accountStore = null;

    @observable bankOptions = [];
    @observable isDialogVisible = false;
    @observable dialogContent = '';

    constructor(navigator, accountStore) {
        this.navigator = navigator;
        this.accountStore = accountStore;
    }

    @observable
    @validate(Enum.validateReg.required, `${I18n.message('required')}`)
    bank = '';

    @observable
    @validate(Enum.validateReg.required, `${I18n.message('required')}`)
    address = '';

    provinceText = null;
    cityText = null;

    @observable
    @validate(Enum.validateReg.required, `${I18n.message('required')}`)
    addressDetail = '';

    @observable
    @validate(Enum.validateReg.required, `${I18n.message('required')}`)
    card = '';

    @action setDialogVisible(isDialogVisible) {
        this.isDialogVisible = isDialogVisible;
    }
    @action setDialogContent() {
        const username = this.accountStore.username;
        const bankOption = Enum.bankOptions.find((bank) => {
            return bank.value === this.bank;
        });
        const bank = bankOption.text;
        const addressArr = this.address.split(' ');
        const province = addressArr[0];
        const city = addressArr[1];
        const provinceObject = Enum.cityData.find((cd) => {
            return cd.value === province;
        });
        this.provinceText = provinceObject.text;
        const cityObject = provinceObject.children.find((cd) => {
            return cd.value === city;
        });
        this.cityText = cityObject.text;
        this.dialogContent = `开户姓名: ${username}\n开户地址: ${bank}\n开户地址: ${this.provinceText} ${this.cityText} ${this.addressDetail}\n银行卡号: ${this.card}`;
    }
    @action addBankCard() {
        Api.addBankCard(this.bank, this.provinceText, this.cityText, this.addressDetail, this.card, () => this.addBankCardSuccess(), (result) => this.addBankCardError(result));
    }
    @action.bound addBankCardSuccess() {
        this.setDialogVisible(false);
        ToastRoot.show('添加成功');
        this.accountStore.getBankList();
        setTimeout(() => {
            this.navigator.pop();
        }, Config.waitingTime);
    }
    @action.bound addBankCardError(result) {
        this.setDialogVisible(false);
        if (result.code == 2) {
            ToastRoot.show('添加失败！');
        } else if (result.code == 3) {
            ToastRoot.show('用户信息不存在');
        } else if (result.code == 4) {
            ToastRoot.show('银行卡卡号已经存在');
        } else if (result.code == 5) {
            ToastRoot.show('请先实名认证方可添加银行卡');
        } else {
            ToastRoot.show('系统繁忙，请稍后再试');
        }
    }
}

@inject('AccountStore') @observer
class AccountAddBankCard extends Component {
    constructor(props) {
        super(props);
        this.store = new AccountAddBankCardStore(this.props.navigator, this.props.AccountStore);
    }
    _setDialog() {
        this.store.setDialogContent();
        this.store.setDialogVisible(true);
    }
    render() {
        const { AccountStore } = this.props;
        
        return (
            <View style={styles.container}>
                <FieldProvider form={this.store}>
                    <View style={{ marginHorizontal: Layout.fieldHorizontalMargin, flex: 1 }}>
                        <DisplayUnderline label='开户姓名' text={AccountStore.username} style={{ marginTop: Layout.fieldMargin }} />
                        <SelectFieldEnhanced name='bank' type='normal' label='开户银行' skin='underline' options={Enum.bankOptions} isErrorMsg={false} />
                        <SelectFieldEnhanced name='address' type='address' label='开户地址' skin='underline' isErrorMsg={false} />
                        <InputFieldEnhanced name='addressDetail' type='string' label='详细地址' skin='underline' isErrorMsg={false} />
                        <InputFieldEnhanced name='card' type='stringNumber' label='银行卡号' skin='underline' isErrorMsg={false} />
                        
                        <Submit text={'添加银行卡'} onSubmit={() => this._setDialog()} />
                    </View>
                </FieldProvider>
                <Dialog 
                    visible={this.store.isDialogVisible}
                    height={Layout.dialogHeight}
                    header={'新增银行卡'}
                    content={this.store.dialogContent}
                    onConfirm={() => this.store.addBankCard()}
                    onCancel={() => this.store.setDialogVisible(false)}
                />
            </View>
        );
    }
}
const composedAccountAddBankCard = composeKeyboard(AccountAddBankCard);
composedAccountAddBankCard.navigatorStyle = NavigatorStyle.screenInnerStyle;
export default composedAccountAddBankCard;

const styles = StyleSheet.create({
    container: {
        flex: 1
    }
});
