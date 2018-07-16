import React, { Component } from 'react';
import { View, Text } from 'react-native';
import { inject, observer } from 'mobx-react/native';
import { StopLossConditionStyle, Enum } from '../../../../../global';
import { FieldProvider, InputField, SelectField } from '../../../../../components';

@inject('ConditionStore') @observer
export default class PriceConditionTab extends Component {
    _renderAdditionPrice() {
        if (this.props.ConditionStore.priceForm.additionType !== Enum.compareType.addition.value) {
            return <InputField name={'additionPrice'} type={'float'} />;
        }
    }
    render() {
        const { ConditionStore } = this.props;
        return (
            <View style={StopLossConditionStyle.container}>
                <FieldProvider form={ConditionStore.priceForm}>
                    <View style={StopLossConditionStyle.content}>
                        <View style={StopLossConditionStyle.row}>
                            <View style={StopLossConditionStyle.columnFirst}>
                                <Text style={StopLossConditionStyle.label}>{'合約'}</Text>
                            </View>
                            <View style={StopLossConditionStyle.column}>
                                <SelectField name={'productPrice'} options={ConditionStore.optionProducts} disable={!ConditionStore.isInsert} />
                                </View>
                            <View style={StopLossConditionStyle.column}>
                                <Text style={StopLossConditionStyle.headerText}>{`最新: ${ConditionStore.productPriceLastPrice}`}</Text>
                            </View>
                        </View>
                        <View style={StopLossConditionStyle.row}>
                            <View style={StopLossConditionStyle.columnFirst}>
                                <Text style={StopLossConditionStyle.label}>{'方式'}</Text>
                            </View>
                            <View style={StopLossConditionStyle.column}>
                                <SelectField name={'compareType'} options={[Enum.compareType.greater, Enum.compareType.less, Enum.compareType.greaterThanEqual, Enum.compareType.lessThanEqual]} />
                            </View>
                            <View style={StopLossConditionStyle.column}>
                                <InputField name={'triggerPrice'} type={'float'} />
                            </View>
                            <View style={StopLossConditionStyle.column}>
                                <SelectField name={'additionType'} options={[Enum.compareType.addition, Enum.compareType.greater, Enum.compareType.less, Enum.compareType.greaterThanEqual, Enum.compareType.lessThanEqual]} />
                            </View>
                            <View style={StopLossConditionStyle.column}>
                                { this._renderAdditionPrice() }
                            </View>
                        </View>
                        <View style={StopLossConditionStyle.row}>
                            <View style={StopLossConditionStyle.columnFirst}>
                                <Text style={StopLossConditionStyle.label}>{'操作'}</Text>
                            </View>
                            <View style={StopLossConditionStyle.column}>
                                <SelectField name={'direction'} options={ConditionStore.optionDirections} />
                            </View>
                            <View style={StopLossConditionStyle.column}>
                                <SelectField name={'orderType'} options={[Enum.orderType.market, Enum.orderType.opponent]} />
                            </View>
                            <View style={StopLossConditionStyle.column}>
                                <Text style={StopLossConditionStyle.label}>{'手数'}</Text>
                            </View>
                            <View style={StopLossConditionStyle.column}>
                                <InputField name={'num'} type={'int'} />
                            </View>
                        </View>
                        <View style={StopLossConditionStyle.row}>
                            <View style={StopLossConditionStyle.columnFirst}>
                                <Text style={StopLossConditionStyle.label}>{'有效'}</Text>
                            </View>
                            <View style={StopLossConditionStyle.column}>
                                <Text style={StopLossConditionStyle.label}>{Enum.stopLossExpiration}</Text>
                            </View>
                        </View>
                    </View>
                </FieldProvider>
            </View>
        );
    }
}
