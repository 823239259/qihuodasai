/**
 * 支付确认
 * paycp.html
 */
import React, { Component } from 'react';
import { View, Text, ScrollView } from 'react-native';
import ApplyConfirmContractList from './components/ApplyConfirmContractList';
import ApplyFee from './components/ApplyFee';
import ApplyTradeChooseDepositInfo from '../components/ApplyTradeChooseDepositInfo';
import { inject, observer } from 'mobx-react/native';
import { ButtonCommon, ToastRoot } from '../../../../components';
import { Colors, Layout, Variables, NavigatorStyle } from '../../../../global';
import { SafeAreaView } from '../../../../containments';
import { Api, Logger } from '../../../../utils';

@inject('ApplyTradeStore') @observer
export default class ApplyConfirm extends Component {
    constructor(props) {
        super(props);
        this.logger = new Logger(ApplyConfirm);
    }
    _onPress() {
        if (this.props.ApplyTradeStore.compared) {
            this._pay();
        } else {
            this._toAccountDeposit();
        }
    }
    _pay() {
        Api.payApplyTrade(this.props.ApplyTradeStore.chooseDeposit, this._paySuccess.bind(this), this._payError.bind(this));
    }
    _paySuccess(result) {
        this.logger.trace(`專案申請成功 vid: ${result.data.id}, stateType: ${result.data.stateType}`);
        this._toApplyResult(result);
        this.props.ApplyTradeStore.refreshAccountData();
    }
    _payError(error) {
        this.logger.trace(`專案申請失敗 error.code = ${error.code}`);  
        if (error.code === 2) {
            ToastRoot.show('支付确认失败');
        } else if (error.code === 3) {
            ToastRoot.show('余额不足');
        }
    }
    _toApplyResult(result) {
        this.props.navigator.push({
            screen: 'applyTrade.ApplyResult',
            title: '申请成功',
            backButtonTitle: '',
            animationType: 'slide-horizontal',
            passProps: {
                businessType: 8,
                vid: result.data.id,
                stateType: result.data.stateType
            }
        });
    }
    _toAccountDeposit() {
        this.props.navigator.showModal({
            screen: 'account.AccountDeposit',
            title: '我要充值',
            passProps: {
                chooseDeposit: this.props.ApplyTradeStore.chooseDeposit, // 3000 / 6000
            },
            navigatorButtons: {
              leftButtons: [
                  {
                      icon: Variables.icon.closeAccountDeposit.source,
                      id: Variables.icon.closeAccountDeposit.id
                  }
              ]
            }
          }); 
    }
    render() {
        return (
            <SafeAreaView>
                <ScrollView>
                    <ApplyConfirmContractList />
                    <View style={[{ margin: 10 }]}>
                        <Text style={{ color: Colors.greyText }}>{'*以上手数为只交易该品种时，初始最大可持仓手数'}</Text>
                    </View>
                    <ApplyTradeChooseDepositInfo isTraderBond={false} />
                    <ApplyFee />
                </ScrollView>
                <ButtonCommon 
                    text={this.props.ApplyTradeStore.compared ? '确认支付' : '立即充值'} 
                    color={Colors.white} 
                    style={{ flex: 0, height: Layout.buttonLargeHeight, backgroundColor: Colors.red, marginHorizontal: Layout.applyTradeChooseDepositInfoRowPadding }} 
                    textStyle={Layout.fontNormal} onPress={() => this._onPress()} 
                />
            </SafeAreaView>
        );
    }
}
ApplyConfirm.navigatorStyle = NavigatorStyle.screenInnerStyle;
