import React, { Component } from 'react';
import { View, Text } from 'react-native';
import { inject, observer } from 'mobx-react/native';
import { Accordion, AccordionItem } from '../../../../components';
import { Colors, Layout, Enum } from '../../../../global';

@inject('ApplyTradeStore') @observer
export default class ApplyTradeContractList extends Component {
    render() {
        const { contractListWithParam } = this.props.ApplyTradeStore;
        const padColumnSize = Layout.screenWidth / 5;
        if (!contractListWithParam) {
            return <View />;
        }
        return (
            <View style={{ height: Layout.screenHeight / 2 }}>
                <View style={[{ margin: 10, marginTop: 20 }]}>
                    <Text style={{ color: Colors.titleTextColor }}>{'可交易17种期货商品'}</Text>
                </View>
                <Accordion
                    headers={[
                        { text: '期货产品', style: { width: Layout.deviceSize === Enum.deviceSize.pad ? padColumnSize : 100, backgroundColor: Colors.applyTradeAccordionColor, alignItems: 'flex-start', paddingLeft: 10 } },
                        { text: '主力合约', style: { width: Layout.deviceSize === Enum.deviceSize.pad ? padColumnSize : 100, backgroundColor: Colors.applyTradeAccordionColor, alignItems: 'flex-start', paddingLeft: 20 } },
                        { text: '手续费', style: { width: Layout.deviceSize === Enum.deviceSize.pad ? 80 : 80, backgroundColor: Colors.applyTradeAccordionColor }, alignItems: 'flex-end' },
                        { text: '最大持仓', style: { width: Layout.deviceSize === Enum.deviceSize.pad ? 80 : 80, backgroundColor: Colors.applyTradeAccordionColor } },
                        { text: '交易时间', style: { width: Layout.deviceSize === Enum.deviceSize.pad ? (Layout.screenWidth - (padColumnSize * 2) - (80 * 2)) : 250, backgroundColor: Colors.applyTradeAccordionColor } },
                    ]}
                    contentTextStyle={{ color: Colors.greyText }}
                    count={contractListWithParam.length}
                >
                    { contractListWithParam.map((contractWithParam, index) => {
                        return (
                            <AccordionItem
                                style={{ backgroundColor: Colors.bg }}
                                data={contractWithParam}
                                key={index}
                                keys={[
                                    { name: 'commodityName' },
                                    { name: 'mainContract' },
                                    { name: 'price' },
                                    { name: 'tradeNum' },
                                    { name: 'tradTime' }
                                ]}
                            />
                        );
                    })}
                </Accordion>
            </View>
        );
    }
}
