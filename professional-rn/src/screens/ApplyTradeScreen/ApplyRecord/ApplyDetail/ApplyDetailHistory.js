import React, { Component } from 'react';
import { inject, observer } from 'mobx-react/native';
import { Accordion, AccordionItem } from '../../../../components';
import { Colors } from '../../../../global';

@inject('ApplyTradeStore', 'FutureTypeStore') @observer
export default class ApplyDetailDetail extends Component {
    render() {
        const { contractHistorys } = this.props.ApplyTradeStore;
        const headerArr = this.props.FutureTypeStore.isFutIn ? [
            { text: '序号', style: { width: 50 } },
            { text: '成交日期', style: { width: 150 } },
            { text: '客户号', style: { width: 80 } },
            { text: '币种', style: { width: 100 } },
            { text: '交易所', style: { width: 50 } },
            { text: '品种', style: { width: 80 } },
            { text: '开平', style: { width: 60 } },
            { text: '买', style: { width: 50 } },
            { text: '卖', style: { width: 50 } },
            { text: '成交价', style: { width: 100 } },
            { text: '手续费', style: { width: 50 } },
        ] : [
            { text: '序号', style: { width: 50 } },
            { text: '成交日期', style: { width: 150 } },
            { text: '客户号', style: { width: 80 } },
            { text: '币种', style: { width: 100 } },
            { text: '交易所', style: { width: 50 } },
            { text: '品种', style: { width: 80 } },
            { text: '买', style: { width: 50 } },
            { text: '卖', style: { width: 50 } },
            { text: '成交价', style: { width: 100 } },
            { text: '手续费', style: { width: 50 } },
        ];

        const keyArr = this.props.FutureTypeStore.isFutIn ? [
            { name: 'index' },
            { name: 'tradeDate' },
            { name: 'userNo' },
            { name: 'currencyNo' },
            { name: 'exchangeNo' },
            { name: 'commodityNo' },
            { name: 'openCloseType' },
            { name: 'buyNum' },
            { name: 'sellNum' },
            { name: 'tradePrice' },
            { name: 'free' },
        ] : [
            { name: 'index' },
            { name: 'tradeDate' },
            { name: 'userNo' },
            { name: 'currencyNo' },
            { name: 'exchangeNo' },
            { name: 'commodityNo' },
            { name: 'buyNum' },
            { name: 'sellNum' },
            { name: 'tradePrice' },
            { name: 'free' },
        ];
        return (
            <Accordion
                headers={headerArr}
                contentTextStyle={{ color: Colors.greyText }}
                count={contractHistorys.length}
            >
                { contractHistorys.map((history, index) => {
                    return (
                        <AccordionItem
                            style={{ backgroundColor: Colors.bg }}
                            data={history}
                            key={index}
                            keys={keyArr}
                        />
                    );
                })}
            </Accordion>
        );
    }
}

