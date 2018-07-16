import React, { Component } from 'react';
import { View } from 'react-native';
import { inject, observer } from 'mobx-react/native';
import { TradeInfoRow } from '../../../../components';
import { Colors } from '../../../../global';

@inject('TradeStore') @observer
export default class TradeQuotationView extends Component {
    render() {
        const { TradeStore } = this.props;
        return (
            <View style={{ flex: 1 }}>
                <TradeInfoRow label={'最新价'} value={TradeStore.lastPrice} valueTextColor={TradeStore.lastPriceColor} num={TradeStore.changeRate} numTextColor={TradeStore.changeRateColor} />
                <TradeInfoRow label={'成交量'} value={TradeStore.totalVolumn} num={'一'} />
                <TradeInfoRow label={'卖价'} value={TradeStore.sellLastPrice} valueTextColor={TradeStore.sellLastPriceColor} num={TradeStore.askQty} isLabelFilled={true} />
                <TradeInfoRow label={'买价'} value={TradeStore.buyLastPrice} valueTextColor={TradeStore.buyLastPriceColor} num={TradeStore.bidQty} isLabelFilled={true} />
            </View>
        );
    }
}
