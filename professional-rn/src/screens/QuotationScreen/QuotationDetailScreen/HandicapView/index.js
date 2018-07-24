/*
    盤口
    盤口/五檔行情
*/
import React, { Component } from 'react';
import { View, Text } from 'react-native';
import { inject, observer } from 'mobx-react/native';
import HandicapRow from './HandicapRow';
import FiveMarketRow from './FiveMarketRow';
import { Layout, Colors } from '../../../../global';
import { I18n } from '../../../../utils';

@inject('HandicapTradeStore','FutureTypeStore') @observer
export default class HandicapView extends Component {
    _renderHeader(text) {
        return (
            <View style={{ justifyContent: 'center', alignItems: 'center' }}>
                <Text style={[Layout.handicapHeaderTextStyle, { color: Colors.handicapTextColor }]}>{text}</Text>
            </View>            
        );
    }
    _renderHandicapRow() {
        const { HandicapTradeStore } = this.props;

        const handicapData = [ 
            { text: I18n.term('last_price'), value: HandicapTradeStore.pkLastPrice.content, color: HandicapTradeStore.pkLastPrice.color },
            { text: I18n.term('open_price'), value: HandicapTradeStore.pkOpenPrice.content, color: HandicapTradeStore.pkOpenPrice.color },
            { text: I18n.term('high_price'), value: HandicapTradeStore.pkHightPrice.content, color: HandicapTradeStore.pkHightPrice.color },
            { text: I18n.term('low_price'), value: HandicapTradeStore.pkLowPrice.content, color: HandicapTradeStore.pkLowPrice.color },
            { text: I18n.term('raise_fall'), value: HandicapTradeStore.pkzd.content, color: HandicapTradeStore.pkzd.color },
            { text: I18n.term('deal_total'), value: HandicapTradeStore.pktrademl, },
            { text: I18n.term('holdPosition_total'), value: HandicapTradeStore.pkccml, },
            { text: I18n.term('close_yesterday'), value: HandicapTradeStore.pkzj, },
        ];
        return handicapData.map((data, index) => {
            return (
                <HandicapRow key={`handicapRow${index}`} data={data} productName={HandicapTradeStore.productName} index={index} lastIndex={handicapData.length - 1} />
            );
        });
    }
    _renderFiveMarketRow() {
        const { HandicapTradeStore,FutureTypeStore } = this.props;
        const typeIn = [
            { text: I18n.term('ask1'), ...HandicapTradeStore.ask1 },
            { text: I18n.term('bid1'), ...HandicapTradeStore.bid1 },
        ];
        const typeOut = [
            { text: I18n.term('ask5'), ...HandicapTradeStore.ask5 },
            { text: I18n.term('ask4'), ...HandicapTradeStore.ask4 },
            { text: I18n.term('ask3'), ...HandicapTradeStore.ask3 },
            { text: I18n.term('ask2'), ...HandicapTradeStore.ask2 },
            { text: I18n.term('ask1'), ...HandicapTradeStore.ask1 },
            { text: I18n.term('bid1'), ...HandicapTradeStore.bid1 },
            { text: I18n.term('bid2'), ...HandicapTradeStore.bid2 },
            { text: I18n.term('bid3'), ...HandicapTradeStore.bid3 },
            { text: I18n.term('bid4'), ...HandicapTradeStore.bid4 },
            { text: I18n.term('bid5'), ...HandicapTradeStore.bid5 },
        ]
        const fiveMarketData = FutureTypeStore.isFutIn ? typeIn : typeOut;
        return fiveMarketData.map((data, index) => {
            return (
                <FiveMarketRow key={`fiveMarket${index}`} data={data} index={index} lastIndex={fiveMarketData.length - 1} />
            );
        });
    }
    render() {
        return (
            <View style={{ flex: 1, flexDirection: 'row' }}>
                <View style={{ flex: 1 }}>
                    {this._renderHeader(I18n.term('handicap'))}
                    {this._renderHandicapRow()}
                </View>
                <View style={{ flex: 1 }}>
                    {this._renderHeader(I18n.term('five_market'))}
                    {this._renderFiveMarketRow()}
                </View>
            </View>
        );
    }
}
