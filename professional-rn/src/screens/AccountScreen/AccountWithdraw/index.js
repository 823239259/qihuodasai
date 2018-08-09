/**
 * 我要充值
 */
import React, { Component } from 'react';
import { View, ScrollView, StyleSheet } from 'react-native';
import _ from 'lodash';
import { observable, computed, action } from 'mobx';
import validate from 'mobx-form-validate';
import { inject, observer } from 'mobx-react/native';
import { Submit, FieldProvider, InputFieldEnhanced, DisplayUnderline, Dialog, ToastRoot } from '../../../components';
import { Layout, Variables, Enum, NavigatorStyle } from '../../../global';
import { CardBalance } from './../components';
import CardBankCard from '../AccountBankCard/components/CardBankCard';
import { composeKeyboard } from './../../../hoc';
import { I18n } from '../../../utils';

class AccountWithdrawStore {
    balance;            //餘額
    // withdrawHandleFee;  //提現手續費 - 非固定
    operateMoney;       //免提現手續費

    constructor(balance, operateMoney) {
        this.balance = balance;
        // this.withdrawHandleFee = withdrawHandleFee;
        this.operateMoney = operateMoney;
    }
    @observable
    @validate(Enum.validateReg.required, `${I18n.message('required')}`)
    withdraw = '';

    @observable
    @validate(Enum.validateReg.required, `${I18n.message('required')}`)
    withdrawPwd = '';
    /**
     * (提現金額 - 累計免手續費) * 1%
     */
    @computed get real() {
        let handleFee = 0;
        let realMoney = 0;

        if (this.withdraw > this.balance) {
            ToastRoot.show('帐户余额不足');
            return {
                handleFee,
                realMoney
            };
        }

        if (this.withdraw <= this.operateMoney) {
            handleFee = 0;
            realMoney = this.withdraw;
        } else {
            handleFee = _.toNumber(((this.withdraw - this.operateMoney) / 100).toFixed(0));
            realMoney = this.withdraw - handleFee;
        }

        return {
            handleFee, 
            realMoney
        };
    }
}
const withdrawPwdText = '提现密码';
@inject('AccountStore') @observer
class AccountWithdraw extends Component {
    constructor(props) {
        super(props);

        this.props.navigator.setOnNavigatorEvent(this.onNavigatorEvent.bind(this));
        this.state = { isExclamationVisible: false };

        const { AccountStore } = this.props;
        this.store = new AccountWithdrawStore(AccountStore.balance, AccountStore.operateMoney);
    }
    onNavigatorEvent(event) { // this is the onPress handler for the two buttons together
        if (event.type === 'NavBarButtonPress') { // this is the event type for button presses
            if (event.id === Variables.icon.exclamationAccountWithDraw.id) {
                this._showExclamation();
            }
        }
    }
    _showExclamation() {
        this.setState({ isExclamationVisible: true });
    }
    _toSetWithdrawPwd() {
        this.props.navigator.push({
            screen: 'account.AccountWithdrawPwd',
            title: withdrawPwdText,
            backButtonTitle: '', //返回
            animationType: 'slide-horizontal'
        });   
    }
    _toBandCard() {
        this.props.navigator.push({
            screen: 'account.AccountBankCard',
            title: '绑定银行卡',
            backButtonTitle: '', //返回
            animationType: 'slide-horizontal'
        });   
    }
    _toRealName() {
        this.props.navigator.push({
            screen: 'account.AccountRealNameCertification',
            title: '实名认证',
            backButtonTitle: '', //返回
            animationType: 'slide-horizontal'
        });   
    }
    _renderPwd() {
        if (this.props.AccountStore.isSetWithdrawPwd) {
            return (
                <InputFieldEnhanced 
                    name='withdrawPwd' 
                    type='string' 
                    label={withdrawPwdText} 
                    skin='underline' 
                    isErrorMsg={false}
                    isErrorMsgSpace={false}
                />
            );
        }
        return (
            <DisplayUnderline 
                label={withdrawPwdText} 
                isButton={true}
                buttonText={'未设置'} 
                onButtonPress={() => this._toSetWithdrawPwd()}
                isBottomMargin={false}
            />
        );
    }
    _onBankPress() {
        if (this.props.AccountStore.isCertification) {
            this._toBandCard();
            return;
        }
        this._toRealName();
    }
    _renderCardBand() {
        const { bankCards, isCertification } = this.props.AccountStore;
        if (bankCards.length === 0) {
            return (
                <DisplayUnderline
                    label={'银行卡'} 
                    isButton={true}
                    buttonText={isCertification ? '未设置' : '请进行实名认证'} 
                    onButtonPress={() => this._onBankPress()} 
                    style={{ marginTop: Layout.fieldMargin }}
                />
            );
        }
        const selectedBankCard = bankCards.find((bc) => {
            return bc.selected === true;
        });
        return <CardBankCard bankCard={selectedBankCard} editable={false} toRight={() => this._toBandCard()} />;
    }
    _submit() {
        const { bankCards, isCertification } = this.props.AccountStore;
        if (bankCards.length === 0) {
            ToastRoot.show('请绑定银行卡');
            return;
        } else if (!isCertification) {
            ToastRoot.show('请进行实名认证');
            return;
        }
        const selectedBankCard = bankCards.find((bc) => {
            return bc.selected === true;
        });
        this.props.AccountStore.withdraw(selectedBankCard.abbreviation, selectedBankCard.card, this.store.withdraw, this.store.withdrawPwd);
    }
    render() {
        return (
            <View style={styles.container}>
                <CardBalance />
                <ScrollView style={{ flex: 1 }}>
                    <FieldProvider form={this.store}>
                        <View style={{ marginHorizontal: Layout.fieldHorizontalMargin, flex: 1 }}>
                            { this._renderCardBand() }
                            <InputFieldEnhanced name='withdraw' type='float' label='提现金额' skin='underline' isErrorMsg={false} isErrorMsgSpace={false}/>
                            { this._renderPwd() }
                            <DisplayUnderline label='手续费' text={`${this.store.real.handleFee} 元`} isBottomMargin={false}/>
                            <DisplayUnderline label='累计免手续费' text={`${this.store.operateMoney} 元`} isBottomMargin={false}/>
                            <DisplayUnderline label='实际到账' text={`${this.store.real.realMoney} 元`} isBottomMargin={false}/>
                            <Submit text={'确认提现'} onSubmit={() => this._submit()} />
                        </View>
                    </FieldProvider>
                </ScrollView>
                <Dialog 
                    visible={this.state.isExclamationVisible}
                    content={'1.到账时间：工作日09:00-16:30申请，24小时内到账，最快5分钟到账。其余时间段申请，将在下一个工作日到账。\n2. 提现手续费：1%（适用于充值后，未实际操盘金额）0元（适用于操盘用户提现)。\n3. 提现处理时间：每个工作日固定时间进行提现处理，具体为10:00  14:00  16:00  17:30 。'}
                    height={300}
                    isCancel={false}
                    onConfirm={() => this.setState({ isExclamationVisible: false })}
                />
            </View>
        );
    }
}
const composedAccountWithdraw = composeKeyboard(AccountWithdraw);
composedAccountWithdraw.navigatorStyle = NavigatorStyle.screenInnerStyle;
export default composedAccountWithdraw;

const styles = StyleSheet.create({
    container: {
        flex: 1
    }
});
