import React, { Component } from 'react';
import { View, Text, ScrollView } from 'react-native';
import { inject, observer } from 'mobx-react/native';
import PropTypes from 'prop-types';
import { Carousel, ButtonCommon } from '../../../components';
import { ApplyTradeChooseDeposit, ApplyTradeChooseDepositInfo, ApplyTradeContractList } from './components';
import { Colors, Layout, Variables, Enum, Config } from '../../../global';

@inject('ApplyTradeStore') @observer
export default class ApplyNew extends Component {
    static propTypes = {
        navigator: PropTypes.object,
    }
    constructor(props) {
        super(props);
        this.props.ApplyTradeStore.getBannerList();
        this.props.ApplyTradeStore.getTradeParams();
    }
    _toApplyConfirm() {
        this.props.ApplyTradeStore.getApplyTradeInfo();

        this.props.navigator.push({
            screen: 'applyTrade.ApplyConfirm',
            title: '支付确认',
            backButtonTitle: '',
            animationType: 'slide-horizontal'
        });
    }
    _toLogin() {
        const { navigator, ApplyTradeStore } = this.props;
        const { accountStore } = ApplyTradeStore;
        navigator.showModal({
          screen: 'account.LoginModal',
          title: '登录',
          navigatorButtons: {
            leftButtons: [
                {
                    icon: Variables.icon.closeLogin.source,
                    id: Variables.icon.closeLogin.id
                }
            ]
          }
        });
        accountStore.setInitModal(accountStore.ACCOUNT_MODAL.login);
        setTimeout(() => {
            navigator.switchToTab({ tabIndex: Enum.screen.tabBased.account.index });
        }, Config.waitingTime);
    }
    _getButtonText() {
        const { ApplyTradeStore } = this.props;
        if (ApplyTradeStore.accountStore.isLogged) {
            return `总计: ¥ ${ApplyTradeStore.chooseDeposit} - 提交`;
        }
        return '请先登录帐户';
    }
    render() {
        const { ApplyTradeStore } = this.props;
        return (
            <View style={{ flex: 1 }}>
                <ScrollView>
                    <Carousel imagesUri={ApplyTradeStore.bannerList} />
                    <View style={[{ marginTop: 10, marginLeft: 10 }]}>
                        <Text style={{ color: Colors.greyText }}>{'选择操盘保证金：金额越多，可持仓手数越多'}</Text>
                    </View>
                    <ApplyTradeChooseDeposit />
                    <ApplyTradeChooseDepositInfo />
                    <ApplyTradeContractList />
                </ScrollView>
                <ButtonCommon 
                    text={this._getButtonText()} 
                    color={Colors.white} 
                    style={{ flex: 0, height: Layout.buttonLargeHeight, backgroundColor: Colors.red, marginHorizontal: Layout.applyTradeChooseDepositInfoRowPadding }} 
                    textStyle={Layout.fontNormal} onPress={() => {
                        if (ApplyTradeStore.accountStore.isLogged) {
                            this._toApplyConfirm();
                        } else {
                            this._toLogin();
                        }
                    }} 
                />
            </View>
        );
    }
}
