import React, { Component } from 'react';
import { View, Text } from 'react-native';
import { inject, observer } from 'mobx-react/native';
import { StopLossConditionStyle } from '../../../../../global';

@inject('TradeHoldPositionStore') @observer
export default class StopLossHeader extends Component {
    render() {
        const { TradeHoldPositionStore } = this.props;
        return (
            <View style={StopLossConditionStyle.header}>
                <View style={[StopLossConditionStyle.column2]}>
                    <Text style={[StopLossConditionStyle.headerText]}>{TradeHoldPositionStore.stopHoldPosition.productName}</Text>
                </View>
                <View style={StopLossConditionStyle.column}>
                    <Text style={[StopLossConditionStyle.headerText, { color: TradeHoldPositionStore.stopHoldPosition.directionColor }]}>{TradeHoldPositionStore.stopHoldPosition.directionText}</Text>
                </View>
                <View style={[StopLossConditionStyle.column2]}>
                    <Text style={[StopLossConditionStyle.headerText]}>{`最新:${TradeHoldPositionStore.lastPrice}`}</Text>
                </View>
            </View>
        );
    }
}
