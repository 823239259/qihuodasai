import React, { Component } from 'react';
import { View, Text } from 'react-native';
import PropTypes from 'prop-types';
import { inject, observer } from 'mobx-react/native';
import { DisplayWithButtonApply } from '../../../../../components';
import { Variables, Config, Enum } from '../../../../../global';
import { Logger } from '../../../../../utils';

@inject('ApplyTradeStore') @observer
export default class ApplyDetailTradeAccount extends Component {
    static contextTypes = {
        navigator: PropTypes.object
    };
    constructor(props) {
        super(props);
        this.logger = new Logger(ApplyDetailTradeAccount);
        this._onImmediately = this._onImmediately.bind(this);
        this._tradeLoginSuccess = this._tradeLoginSuccess.bind(this);
    }
    _tradeLoginSuccess() {
        this.logger.trace('操盤登入成功: 1.移動tab到行情 2.關閉tradeLoginModal 3.navigator.popToRoot');
        // 轉到 行情 tab
        this.context.navigator.switchToTab({ tabIndex: Enum.screen.tabBased.quotation.index });
        setTimeout(() => {
            this.context.navigator.dismissModal();
        }, Config.waitingTime);
        setTimeout(() => {
            this.context.navigator.popToRoot({ animated: false });
        }, Config.waitingTime);
    }
    _onImmediately() {
        const { contractDetail } = this.props.ApplyTradeStore;

        this.context.navigator.showModal({
            screen: Enum.screen.TradeLoginModal,
            title: Enum.screen.tabBased.applyTrade.title,
            navigatorButtons: {
            leftButtons: [
                {
                    icon: Variables.icon.closeTradeLogin.source,
                    id: Variables.icon.closeTradeLogin.id
                }
            ]
            },
            passProps: {
                account: contractDetail.tranAccount,
                pwd: contractDetail.tranPassword,
                onTradeLoginSuccess: this._tradeLoginSuccess
            }
        });
    }
    render() {
        const { contractDetail } = this.props.ApplyTradeStore;
        const stateType = contractDetail.stateType;
        if (stateType) {
            if (stateType === Enum.applyTradeStateType.using.value) {
                return (
                    <View>
                        <DisplayWithButtonApply 
                            label={'操盘帐户  '} 
                            text={`${contractDetail.tranAccount}`} 
                            buttonText={'立即操盘'}
                            onButtonPress={this._onImmediately}
                        />
                        <DisplayWithButtonApply 
                            label={'操盘密码  '} 
                            text={`${contractDetail.tranPassword}`} 
                        />
                    </View>
                );
            }
        }
        return (
            <View />
        );
    }
}

