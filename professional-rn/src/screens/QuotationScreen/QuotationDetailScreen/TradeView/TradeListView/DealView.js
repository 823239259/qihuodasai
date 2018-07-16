/**
 * 成交
 */
import React, { Component } from 'react';
import { inject, observer } from 'mobx-react/native';
import { Accordion, AccordionItem } from '../../../../../components';
import { Layout, Enum } from '../../../../../global';
import Logger from '../../../../../utils/Logger';

@inject('TradeStore') @observer
export default class DealView extends Component {
    
    constructor(props) {
        super(props);
        this.logger = new Logger(DealView);
    }
    // headers -> { text: '成交时间', style: { flex: 1 } }
    // keys -> { name: 'tradeTime' }
    render() {
        const { TradeStore } = this.props;
        const padColumnSize = Layout.screenWidth / 5;
        return (
            <Accordion
                headers={[
                    { text: '合约', style: { width: Layout.deviceSize === Enum.deviceSize.pad ? padColumnSize : 80 } },   // style 設定個別column 寬度
                    { text: '买卖', style: { width: Layout.deviceSize === Enum.deviceSize.pad ? padColumnSize : 60 } },
                    { text: '成交价', style: { width: Layout.deviceSize === Enum.deviceSize.pad ? padColumnSize : 60 } },
                    { text: '成交量', style: { width: Layout.deviceSize === Enum.deviceSize.pad ? padColumnSize : 60 } },
                    { text: '成交时间', style: { width: Layout.deviceSize === Enum.deviceSize.pad ? padColumnSize : 150 } }
                ]}
            >
                { TradeStore.deals.map((deal, index) => {
                    return (
                        <AccordionItem 
                            data={deal}
                            key={index}
                            keys={[
                                { name: 'productName' }, 
                                { name: 'directionText', color: 'directionColor' },
                                { name: 'tradePrice' },
                                { name: 'tradeNum' },
                                { name: 'tradeTime' }
                            ]}
                        />
                    );
                })}
            </Accordion>
        );
    }
}
