/**
 * 追加保证金
 */
import React, { Component } from 'react';
import { View, Text, StyleSheet } from 'react-native';
import _ from 'lodash';
import { observable, computed, action } from 'mobx';
import { inject, observer } from 'mobx-react/native';
import validate from 'mobx-form-validate';
import { CardBalance } from '../AccountScreen/components';
import { FieldProvider, DisplayUnderline, InputFieldEnhanced, Submit, ToastRoot, Dialog } from '../../components';
import { composeKeyboard } from '../../hoc';
import { Variables, Layout, NavigatorStyle, Enum, Config } from '../../global';
import { I18n, Api } from '../../utils';

class AppendTraderBondModalStore {
    applyTradeStore = null;
    navigator = null;
    // 這個匯率 是我們要換人民幣到美金 給使用者換的匯率 => 7.1
    balance = 0;
    @observable rate = 1;
    @observable isDialogVisable = false;

    @observable
    @validate(Enum.validateReg.required, `${I18n.message('required')}`)
    appendFund = '';

    constructor(applyTradeStore, navigator) {
        this.applyTradeStore = applyTradeStore;
        this.navigator = navigator;
        this.initData();
    }
    @action initData() {
        Api.getBalanceRate(1, null, (result) => this.getBalanceRateSuccess(result)); 
    }
    @action.bound getBalanceRateSuccess(result) {
        this.rate = result.data.rate;
        this.balance = result.data.balance;
    }
    @computed get toDollar() {
        if (this.appendFund.length === 0) {
            return '0.00';
        }
        return (_.parseInt(this.appendFund) / this.rate).toFixed(2);
    }
    @action setDialogVisible(isVisible) {
        this.isDialogVisable = isVisible;
    }
    @action submit() {
        if (_.parseInt(this.appendFund) < 500) {
            ToastRoot.show('追加金额最低500元');
        } else if (_.parseInt(this.appendFund) > this.balance) {
            ToastRoot.show('余额不足，请立即充值');
        } else {
            this.setDialogVisible(true);
        }
    }
    @action confirmAddBond() {
        Api.addBond(this.applyTradeStore.vid, _.parseInt(this.appendFund), () => this.addBondSuccess(), (result) => this.addBondError(result));
        this.setDialogVisible(false);
    }
    @action.bound addBondSuccess() {
        ToastRoot.show('申请成功，系统会尽快处理');
        // 更新當前方案明細
        this.applyTradeStore.getTradeAccountDetail(this.applyTradeStore.vid);
        this.applyTradeStore.refreshAccountData();
        setTimeout(() => {
            this.navigator.dismissModal();
        }, Config.waitingTime * 2);
    }
    @action.bound addBondError(result) {
        if (result.code == 2) {
            ToastRoot.show('余额不足,无法追加保证金！');
        } else if (result.code == 3 || result.code == 6) {
            ToastRoot.show('未找到该方案，无法追加保证金！');
        } else if (result.code == 4) {
            ToastRoot.show('该方案已完结，无法追加保证金！');
        } else if (result.code == 5) {
            ToastRoot.show('追加金额低于默认最小金额！');
        } else if (result.code == 7) {
            ToastRoot.show('追加金额不能为空！');
        } else {
            ToastRoot.show('系统繁忙，请稍后再试！');
        }
    }
}

@inject('ApplyTradeStore','FutureTypeStore') @observer
class AppendTraderBondModal extends Component {
    constructor(props) {
        super(props);
        const { ApplyTradeStore, navigator } = this.props;
        this.store = new AppendTraderBondModalStore(ApplyTradeStore, navigator);
        navigator.setOnNavigatorEvent(this.onNavigatorEvent.bind(this));
    }
    onNavigatorEvent(event) {
        if (event.type === 'NavBarButtonPress') {
            if (event.id === Variables.icon.closeAppendTraderBond.id) {
                this.props.navigator.dismissModal();
            }
        }
    }
    _toAccountDeposit() {
        this.props.navigator.push({
            screen: 'account.AccountDeposit',
            title: '我要充值',
            backButtonTitle: '',
            animationType: 'slide-horizontal'
          }); 
    }
    render() {
        return (
            <View style={styles.container}>
                <CardBalance />
                <FieldProvider form={this.store}>
                    <View style={{ marginHorizontal: Layout.fieldHorizontalMargin, flex: 1 }}>
                        <DisplayUnderline label='' isButton={true} buttonText={'去充值'} isButtomBorder={false} isBottomMargin={false} onButtonPress={() => this._toAccountDeposit()} />
                        <InputFieldEnhanced name='appendFund' type='int' label='追加金额' skin='underline' isErrorMsg={false} />                                            
                             {!this.props.FutureTypeStore.business_Type && <DisplayUnderline label='换算汇率' text={`¥ ${this.store.rate} = $ 1`} />}
                             {!this.props.FutureTypeStore.business_Type && <DisplayUnderline label='换算美元' text={`$ ${this.store.toDollar}`} />}
                        <Submit text={'立即追加'} onSubmit={() => this.store.submit()} />
                    </View>
                </FieldProvider>
                <Dialog 
                    visible={this.store.isDialogVisable}
                    content={`是否向该方案追加资金 ¥ ${this.store.appendFund}`}
                    onConfirm={() => this.store.confirmAddBond()}
                    onCancel={() => this.store.setDialogVisible(false)}
                />
            </View>
        );
    }
}
const conposedAppendTraderBondModal = composeKeyboard(AppendTraderBondModal);
conposedAppendTraderBondModal.navigatorStyle = NavigatorStyle.modalStyle;
export default conposedAppendTraderBondModal;

const styles = StyleSheet.create({
    container: {
        flex: 1
    }
});
