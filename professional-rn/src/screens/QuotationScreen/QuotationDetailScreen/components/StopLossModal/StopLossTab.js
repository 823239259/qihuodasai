import React, { Component } from 'react';
import { View, Text } from 'react-native';
import { inject, observer } from 'mobx-react/native';
import { StopLossConditionStyle, Enum } from '../../../../../global';
import { FieldProvider, InputField, SelectField } from '../../../../../components';

@inject('TradeHoldPositionStore') @observer
export default class StopLossTab extends Component {
    render() {
        const { TradeHoldPositionStore } = this.props;
        return (
            <View style={StopLossConditionStyle.container}>
                <FieldProvider form={TradeHoldPositionStore.stopLossForm}>
                    <View style={StopLossConditionStyle.content}>
                        <View style={StopLossConditionStyle.row}>
                            <View style={StopLossConditionStyle.columnFirst}>
                                <Text style={StopLossConditionStyle.label}>{'方式'}</Text>
                            </View>
                            <View style={StopLossConditionStyle.column}>
                                <SelectField name={'stopLossType'} options={[Enum.stopLossType.stopLoss, Enum.stopLossType.stopLossDynamic]} />
                                </View>
                            <View style={StopLossConditionStyle.column}>
                                <InputField name={'stopLossPrice'} type={'float'} />
                            </View>
                            <View style={StopLossConditionStyle.column}>
                                <Text style={StopLossConditionStyle.label}>{`${TradeHoldPositionStore.lossPercentage}%`}</Text>
                            </View>
                        </View>
                        <View style={StopLossConditionStyle.row}>
                            <View style={StopLossConditionStyle.columnFirst}>
                                <Text style={StopLossConditionStyle.label}>{'手数'}</Text>
                            </View>
                            <View style={StopLossConditionStyle.column}>
                                <InputField name={'stopLossNum'} type={'int'} />
                            </View>
                            <View style={StopLossConditionStyle.column2}>
                                <Text style={StopLossConditionStyle.label}>{'止损委托价：市价'}</Text>
                            </View>
                        </View>
                    </View>
                </FieldProvider>
            </View>
        );
    }
}
