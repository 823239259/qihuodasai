/**
 * 右側顯示資料
 */
import React, { Component } from 'react';
import { View } from 'react-native';
import { inject, observer } from 'mobx-react/native';
import { TradeInfoRow } from '../../../../components';
import { Colors } from '../../../../global';

@inject('TradeStore') @observer
export default class TradeAccountView extends Component {
    render() {
        const { TradeStore } = this.props;

        return (
            <View style={{ flex: 1 }}>
                <TradeInfoRow label={'总资产'} value={TradeStore.balanceText} valueTextColor={Colors.tradeViewInfoAccountTextColor} labelColumn={{ flex: 1 }} />
                <TradeInfoRow label={'余额'} value={TradeStore.canUseText} isLabelFilled={true} valueTextColor={Colors.tradeViewInfoAccountTextColor} labelColumn={{ flex: 1 }} />
                <TradeInfoRow label={'平仓线'} value={TradeStore.forceLine} valueTextColor={Colors.tradeViewInfoAccountTextColor} labelColumn={{ flex: 1 }} />
                <TradeInfoRow label={'风险'} value={TradeStore.riskText} isLabelFilled={true} valueTextColor={Colors.tradeViewInfoAccountTextColor} labelColumn={{ flex: 1 }} />
            </View>
        );
    }
}
