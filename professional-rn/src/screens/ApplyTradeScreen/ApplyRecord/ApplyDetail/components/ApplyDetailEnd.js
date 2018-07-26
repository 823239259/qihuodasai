import React, { Component } from 'react';
import { View, Text } from 'react-native';
import { inject, observer } from 'mobx-react/native';
import { Accordion, AccordionItem, DisplayWithButtonApply } from '../../../../../components';
import { Colors, Layout, Enum } from '../../../../../global';

@inject('ApplyTradeStore','FutureTypeStore') @observer
export default class ApplyDetailEnd extends Component {
    _renderAccoridon() {
        const headerWidth = Layout.screenWidth / 3;
        const { contractEnd } = this.props.ApplyTradeStore;
        const tradeProduct = [];
        Enum.productTradeType.forEach(productTrade => {
            const key = productTrade.actual;
            if (contractEnd[key]) {
                tradeProduct.push({ commodityName: productTrade.commodityName, productName: productTrade.productName, num: contractEnd[key] });
            }
        });
        return (
            <Accordion
                headers={[
                    { text: '期货产品', style: { width: headerWidth, backgroundColor: Colors.applyTradeAccordionColor, alignItems: 'flex-start', paddingLeft: 10 } },
                    { text: '主力合约', style: { width: headerWidth, backgroundColor: Colors.applyTradeAccordionColor } },
                    { text: '交易手数', style: { width: headerWidth, backgroundColor: Colors.applyTradeAccordionColor }, textStyle: { color: Colors.lightTextColor } },
                ]}
                contentTextStyle={{ color: Colors.greyText }}
                count={tradeProduct.length}
                scrollEnabled={false}
            >
                { tradeProduct.map((product, index) => {
                    return (
                        <AccordionItem
                            style={{ backgroundColor: Colors.bg }}
                            data={product}
                            key={index}
                            keys={[
                                { name: 'commodityName' },
                                { name: 'productName' },
                                { name: 'num', textStyle: { color: Colors.lightTextColor } }
                            ]}
                        />
                    );
                })}
            </Accordion>
        );
    } 
    _renderTradeProduct() {
        return (
            <View>
                <View style={[{ margin: 10, marginTop: 20 }]}>
                    <Text style={{ color: Colors.titleTextColor }}>{'投资标的'}</Text>
                </View>
                <View style={[{ flexDirection: 'row', justifyContent: 'center' }]}>
                    {this._renderAccoridon()}
                </View>
            </View>
        );
    }
    _renderResult() {
        const { ApplyTradeStore } = this.props;
        return (
            <View>
                <View style={[{ margin: 10, marginTop: 20 }]}>
                    <Text style={{ color: Colors.titleTextColor }}>{'方案结算'}</Text>
                </View>
                <DisplayWithButtonApply label={'方案结算时间  '} text={ApplyTradeStore.endTimeString} />
                {
                	!this.props.FutureTypeStore.business_Type && <DisplayWithButtonApply label={'美元结算汇率  '} text={ApplyTradeStore.parity} />
                }
                <DisplayWithButtonApply label={'交易盈亏  '} text={ApplyTradeStore.profitLossString} textStyle={{ color: (ApplyTradeStore.profitLossString >= 0) ? Colors.red : Colors.green }} />
            </View>
        );
    }
    _renderResultMoney() {
        const { contractEnd } = this.props.ApplyTradeStore;
        return (
            <View>
                <View style={[{ margin: 10, marginTop: 20 }]}>
                    <Text style={{ color: Colors.titleTextColor }}>{'结算金额=操盘保证金+追加保证金+交易盈亏-交易手续费'}</Text>
                </View>
                <DisplayWithButtonApply label={'交易手续费  '} text={contractEnd.tranFeesTotal} />
                <DisplayWithButtonApply label={'结算金额  '} text={contractEnd.endAmount} />
            </View>
        );
    }
    render() {
        return (
            <View>
                {this._renderTradeProduct()}
                {this._renderResult()}
                {this._renderResultMoney()}
            </View>
        );
    }
}
