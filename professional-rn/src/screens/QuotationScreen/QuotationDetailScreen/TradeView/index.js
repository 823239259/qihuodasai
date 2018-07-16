/*
    TradeQuotationView  - 顯示合約最新價
    TradeAccountView    - 顯示使用者資金
    TradeListView       - tab - 持仓, 挂单, 委托, 成交
*/
import React, { Component } from 'react';
import { View, Text, StyleSheet } from 'react-native';
import PropTypes from 'prop-types';
import { inject, observer } from 'mobx-react/native';
import { Layout, Colors } from '../../../../global';
import TradeListView from './TradeListView';
import TradeAccountView from './TradeAccountView';
import TradeQuotationView from './TradeQuotationView';
import { TradeOptionSelect, TradeOptionButton } from '../components';

@inject('TradeSocket') @observer
export default class TradeView extends Component {
    _renderInfo() {
        return (
            <View style={[Colors.tradeViewInfoBox, Layout.tradeViewInfoBox]}>
                <TradeQuotationView />
                <TradeAccountView />
            </View>
        );
    }
    render() {
        return (
            <View style={{ flex: 1 }}>
                { this._renderInfo() }
                <TradeOptionSelect />
                <TradeListView />
                <TradeOptionButton />
            </View>
        );
    }
}

