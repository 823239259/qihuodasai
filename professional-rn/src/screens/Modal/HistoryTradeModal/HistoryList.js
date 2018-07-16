import React, { Component } from 'react';
import { inject, observer } from 'mobx-react/native';
import PropTypes from 'prop-types';
import { Accordion, AccordionItem } from '../../../components';
import { Enum, Layout } from '../../../global';
import Logger from '../../../utils/Logger';

@inject('HistoryTradeStore') @observer
export default class HistoryList extends Component {
    static propTypes = {
        type: PropTypes.number
    }
    static defaultProps = {
    }
    constructor(props) {
        super(props);
        this.logger = new Logger(HistoryList);
    }
    render() {
        const { HistoryTradeStore } = this.props;
        const columnRemainingWidth = (Layout.screenWidth - (40 + (60 * 3) + 80 + 150)) / 3;
        return (
            <Accordion
                headers={[
                    { text: '序号', style: { width: 40 } },
                    { text: '合约代码', style: { width: Layout.deviceSize === Enum.deviceSize.pad ? columnRemainingWidth : 70 } },
                    { text: '交易所', style: { width: Layout.deviceSize === Enum.deviceSize.pad ? columnRemainingWidth : 70 } },
                    { text: '币种', style: { width: Layout.deviceSize === Enum.deviceSize.pad ? columnRemainingWidth : 70 } },
                    { text: '买卖', style: { width: 60 } },
                    { text: '成交价', style: { width: 80 } },
                    { text: '成交量', style: { width: 60 } },
                    { text: '手续费', style: { width: 60 } },
                    { text: '成交时间', style: { width: 150 } }
                ]}
                count={HistoryTradeStore.historyData.length}
            >
                { HistoryTradeStore.historyData.map((data, index) => {
                    return (
                        <AccordionItem 
                            data={data}
                            key={index}
                            keys={[
                                { name: 'serialNo' },
                                { name: 'productName' },
                                { name: 'exchangeNo' },
                                { name: 'currencyNo' },
                                { name: 'directionText', color: 'directionColor' },
                                { name: 'tradePrice' },
                                { name: 'tradeNum' },
                                { name: 'tradeFee' },
                                { name: 'tradeDateTime' }
                            ]}
                        />
                    );
                })}
            </Accordion>
        );
    }
}
