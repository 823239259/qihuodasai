import React, { Component } from 'react';
import { View, Text } from 'react-native';
import { inject, observer } from 'mobx-react/native';
import { StopLossConditionStyle } from '../../../../../global';
import { FieldProvider, InputField, SelectField } from '../../../../../components';

@inject('TradeHoldPositionStore') @observer
export default class StopWinTab extends Component {
    render() {
        const { TradeHoldPositionStore } = this.props;
        return (
            <View style={StopLossConditionStyle.container}>
                <FieldProvider form={TradeHoldPositionStore.stopWinForm}>
                    <View style={StopLossConditionStyle.content}>
                        <View style={StopLossConditionStyle.row}>
                            <View style={StopLossConditionStyle.columnFirst}>
                                <Text style={StopLossConditionStyle.label}>{'止盈价'}</Text>
                            </View>
                            <View style={StopLossConditionStyle.column}>
                                <InputField name={'stopWinPrice'} type={'float'} />
                            </View>
                            <View style={StopLossConditionStyle.column2}>
                                <Text style={StopLossConditionStyle.label}>{`${TradeHoldPositionStore.winPercentage}%`}</Text>
                            </View>
                        </View>
                        <View style={StopLossConditionStyle.row}>
                            <View style={StopLossConditionStyle.columnFirst}>
                                <Text style={StopLossConditionStyle.label}>{'手数'}</Text>
                            </View>
                            <View style={StopLossConditionStyle.column}>
                                <InputField name={'stopWinNum'} type={'int'} />
                            </View>
                            <View style={StopLossConditionStyle.column2}>
                                <Text style={StopLossConditionStyle.label}>{'止盈委托价：市价'}</Text>
                            </View>
                        </View>
                    </View>
                </FieldProvider>
            </View>
        );
    }
}
