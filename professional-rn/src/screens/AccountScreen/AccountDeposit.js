/**
 * 我要充值
 */
import React, { Component } from 'react';
import { View, Text, StyleSheet } from 'react-native';
import { observable, computed } from 'mobx';
import validate from 'mobx-form-validate';
import { inject, observer } from 'mobx-react/native';
import { Submit, FieldProvider, InputFieldEnhanced, ToastRoot } from '../../components';
import { Layout, Colors, Variables, Enum, NavigatorStyle } from '../../global';
import { CardBalance } from './components';
import { composeKeyboard } from './../../hoc';
import { I18n } from '../../utils';

class AccountDepositStore {
    balance;
    constructor(balance) {
        this.balance = balance;
    }
    @observable
    @validate(Enum.validateReg.required, `${I18n.message('required')}`)
    deposit = '';

    @computed get afterDeposit() {
        if (this.deposit) {
            return (parseFloat(this.balance) + parseFloat(this.deposit)).toFixed(2);
        }
        return this.balance;
    }
}
@inject('AccountStore') @observer
class AccountDeposit extends Component {
    constructor(props) {
        super(props);
        const balance = this.props.AccountStore.balance;
        this.store = new AccountDepositStore(balance);
        // 1.帳戶 -> 我要充值
        // 2.操盤申請 -> 立即充值 ApplyConfirm
        if (this.props.chooseDeposit) {
            this.store.deposit = (this.props.chooseDeposit - balance).toFixed(2);
        }
        // 3.操盤申請 -> 開戶紀錄 -> 立即追加 AppendTraderBondModalStore
        this.props.navigator.setOnNavigatorEvent(this.onNavigatorEvent.bind(this));
    }
    onNavigatorEvent(event) {
        if (event.type === 'NavBarButtonPress') {
            if (event.id === Variables.icon.closeAccountDeposit.id) {
                this.props.navigator.dismissModal();
            }
        }
    }
    _toDepositWebView() {
        const biggestMoney = 9999999;
        if (this.store.deposit > biggestMoney) {
            ToastRoot.show(`充值金额不高于${biggestMoney}元`);
            return;
        } else if (this.store.deposit < 10) {
            ToastRoot.show('充值金额应大于等于10元');
            return;
        }
        this.props.navigator.push({
            screen: 'account.AccountDepositWebView',
            title: '充值方式',
            backButtonTitle: '',
            animationType: 'slide-horizontal',
            passProps: {
                money: this.store.deposit
            }
        });
    }
    render() {
        return (
            <View style={styles.container}>
                <CardBalance />
                <FieldProvider form={this.store}>
                    <View style={{ marginHorizontal: Layout.fieldHorizontalMargin, flex: 1 }}>
                        <InputFieldEnhanced name='deposit' type='float' label='充值金额' skin='underline' isErrorMsg={false} style={{ marginTop: Layout.fieldMargin }} />
                        <Submit text={'立即充值'} onSubmit={() => this._toDepositWebView()} />

                        <View style={{ flexDirection: 'row', flex: 1, justifyContent: 'flex-end', marginTop: Layout.fieldMargin }}>
                            <Text style={[Layout.fontNormal, { color: Colors.grey }]}>{'充值后的余额  '}</Text>
                            <Text style={[Layout.fontNormal, { color: Colors.lightTextColor }]}>{this.store.afterDeposit}</Text>
                            <Text style={[Layout.fontNormal, { color: Colors.grey }]}>{'  元'}</Text>
                        </View>
                    </View>
                </FieldProvider>
            </View>
        );
    }
}
const composedAccountDeposit = composeKeyboard(AccountDeposit);
composedAccountDeposit.navigatorStyle = NavigatorStyle.screenInnerStyle;
export default composedAccountDeposit;

const styles = StyleSheet.create({
    container: {
        flex: 1
    }
});
