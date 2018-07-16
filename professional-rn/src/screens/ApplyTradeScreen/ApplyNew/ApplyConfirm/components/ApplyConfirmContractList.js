import React, { Component } from 'react';
import { View, Text } from 'react-native';
import { inject, observer } from 'mobx-react/native';
import { Accordion, AccordionItem } from '../../../../../components';
import { Colors, Layout } from '../../../../../global';

@inject('ApplyTradeStore') @observer
export default class ApplyConfirmContractList extends Component {
    _renderAccoridon(contractListWithParam) {
        const firstColumnWidth = (Layout.screenWidth / 3) * 0.67;
        const secondColumnWidth = (Layout.screenWidth / 3) * 0.33;
        return (
            <Accordion
                headers={[
                    { text: '期货产品', style: { width: firstColumnWidth, backgroundColor: Colors.applyTradeAccordionColor, alignItems: 'flex-start', paddingLeft: 10 } },
                    { text: '手数', style: { width: secondColumnWidth, backgroundColor: Colors.applyTradeAccordionColor } },
                ]}
                contentTextStyle={{ color: Colors.greyText }}
                count={contractListWithParam.length}
                scrollEnabled={false}
            >
                { contractListWithParam.map((contractWithParam, index) => {
                    return (
                        <AccordionItem
                            style={{ backgroundColor: Colors.bg }}
                            data={contractWithParam}
                            key={index}
                            keys={[
                                { name: 'commodityName' },
                                { name: 'tradeNum' },
                            ]}
                        />
                    );
                })}
            </Accordion>
        );
    }   
    _renderLeft(leftContractListWithParam) {
        return this._renderAccoridon(leftContractListWithParam);
    }
    _renderMiddle(middleContractListWithParam) {
        return this._renderAccoridon(middleContractListWithParam);
    }
    _renderRight(rightContractListWithParam) {
        return this._renderAccoridon(rightContractListWithParam);
    }
    render() {
        const { contractListWithParam } = this.props.ApplyTradeStore;
        if (!contractListWithParam) {
            return <View />;
        }
        const leftIndex = Math.floor(contractListWithParam.length / 3);
        const leftContractListWithParam = contractListWithParam.slice(0, leftIndex);
        const middleIndex = leftIndex + Math.floor((contractListWithParam.length - leftIndex) / 2);
        const middleContractListWithParam = contractListWithParam.slice(leftIndex, middleIndex);
        const rightContractListWithParam = contractListWithParam.slice(middleIndex, contractListWithParam.length);

        return (
            // <View style={{ height: Layout.screenHeight / 2 }}>
            <View>
                <View style={[{ margin: 10, marginTop: 20 }]}>
                    <Text style={{ color: Colors.titleTextColor }}>{'可交易17种期货商品'}</Text>
                </View>
                <View style={[{ flexDirection: 'row', justifyContent: 'center' }]}>
                    {this._renderLeft(leftContractListWithParam)}
                    {this._renderMiddle(middleContractListWithParam)}
                    {this._renderRight(rightContractListWithParam)}
                </View>
            </View>
        );
    }
}
