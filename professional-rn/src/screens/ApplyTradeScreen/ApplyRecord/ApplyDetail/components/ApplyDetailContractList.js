import React, { Component } from 'react';
import { View, Text } from 'react-native';
import { inject, observer } from 'mobx-react/native';
import { Accordion, AccordionItem } from '../../../../../components';
import ApplyDetailEnd from './ApplyDetailEnd';
import { Colors, Layout, Enum } from '../../../../../global';

@inject('ApplyTradeStore') @observer
export default class ApplyDetailContractList extends Component {
    _renderAccoridon(outDiskVoList) {
        const columnWidth = (Layout.screenWidth / 4);
        return (
            <Accordion
                headers={[
                    { text: '期货产品', style: { width: columnWidth, backgroundColor: Colors.applyTradeAccordionColor, alignItems: 'flex-start', paddingLeft: 10 } },
                    { text: '主力合约', style: { width: columnWidth, backgroundColor: Colors.applyTradeAccordionColor } },
                ]}
                contentTextStyle={{ color: Colors.greyText }}
                count={outDiskVoList.length}
                scrollEnabled={false}
            >
                { outDiskVoList.map((contractWithParam, index) => {
                    return (
                        <AccordionItem
                            style={{ backgroundColor: Colors.bg }}
                            data={contractWithParam}
                            key={index}
                            keys={[
                                { name: 'commodityName' },
                                { name: 'mainContract' },
                            ]}
                        />
                    );
                })}
            </Accordion>
        );
    } 
    _renderLeft(leftOutDiskVoList) {
        return this._renderAccoridon(leftOutDiskVoList);
    }
    _renderRight(rightOutDiskVoList) {
        return this._renderAccoridon(rightOutDiskVoList);
    }
    _renderContent() {
        const { outDiskVoList } = this.props.ApplyTradeStore;
        const leftIndex = outDiskVoList.length / 2;
        const leftOutDiskVoList = outDiskVoList.slice(0, leftIndex);
        const rightOutDiskVoList = outDiskVoList.slice(leftIndex, outDiskVoList.length);
        return (
            <View>
                <View style={[{ margin: 10, marginTop: 20 }]}>
                    <Text style={{ color: Colors.titleTextColor }}>{'可交易期货商品'}</Text>
                </View>
                <View style={[{ flexDirection: 'row', justifyContent: 'center' }]}>
                    {this._renderLeft(leftOutDiskVoList)}
                    {this._renderRight(rightOutDiskVoList)}
                </View>
                <View style={[{ margin: 10 }]}>
                    <Text style={{ color: Colors.titleTextColor }}>{'*交易时间: 不同期货不同交易时间段'}</Text>
                </View>
            </View>
        );
    }
    render() {
        if (this.props.ApplyTradeStore.contractDetail.stateType === Enum.applyTradeStateType.end.value) {
            return <ApplyDetailEnd />;
        } 
        return this._renderContent();
    }
}
