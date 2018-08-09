/**
 * 委託
 */
import React, { Component } from 'react';
import { inject, observer } from 'mobx-react/native';
import { Accordion, AccordionItem } from '../../../../../components';
import { Layout, Enum } from '../../../../../global';
import Logger from '../../../../../utils/Logger';

@inject('TradeStore', 'FutureTypeStore') @observer
export default class OrderView extends Component {
    
    constructor(props) {
        super(props);
        this.logger = new Logger(OrderView);
    }
    // headers -> { text: '下单时间', style: { flex: 1 } },
    // AccordionItem -> { name: 'insertDateTime' }
    render() {
        const { TradeStore } = this.props;
        const padColumnSize = Layout.screenWidth / 8;
        const padTimeColumnSize = Layout.screenWidth - ((padColumnSize * 3) + (60 * 4));
        const headerArr = this.props.FutureTypeStore.isFutIn ? [
            { text: '合约', style: { width: Layout.deviceSize === Enum.deviceSize.pad ? padColumnSize : 80 } },   // style 設定個別column 寬度
            { text: '状态', style: { width: Layout.deviceSize === Enum.deviceSize.pad ? padColumnSize : 80 } },
            { text: '买卖', style: { width: 60 } },
            { text: '开平', style: { width: 60 } },
            { text: '委托价', style: { width: Layout.deviceSize === Enum.deviceSize.pad ? padColumnSize : 80 } },
            { text: '委托量', style: { width: 60 } },
            { text: '已成交', style: { width: 60 } },
            { text: '已撤单', style: { width: 60 } },
            { text: '下单时间', style: { width: Layout.deviceSize === Enum.deviceSize.pad ? padTimeColumnSize : 150 } }
        ] : [
            { text: '合约', style: { width: Layout.deviceSize === Enum.deviceSize.pad ? padColumnSize : 80 } },   // style 設定個別column 寬度
            { text: '状态', style: { width: Layout.deviceSize === Enum.deviceSize.pad ? padColumnSize : 80 } },
            { text: '买卖', style: { width: 60 } },
            { text: '委托价', style: { width: Layout.deviceSize === Enum.deviceSize.pad ? padColumnSize : 80 } },
            { text: '委托量', style: { width: 60 } },
            { text: '已成交', style: { width: 60 } },
            { text: '已撤单', style: { width: 60 } },
            { text: '下单时间', style: { width: Layout.deviceSize === Enum.deviceSize.pad ? padTimeColumnSize : 150 } }
        ];
        const keyArr = this.props.FutureTypeStore.isFutIn ? [
            { name: 'productName' }, 
            { name: 'orderStatus' },
            { name: 'directionText', color: 'directionColor' },
            { name: 'openCloseType' },
            { name: 'orderPrice' },
            { name: 'orderNum' },
            { name: 'tradeNum' },
            { name: 'cdNum' },
            { name: 'insertDateTime' }
        ] : [
            { name: 'productName' }, 
            { name: 'orderStatus' },
            { name: 'directionText', color: 'directionColor' },
            { name: 'orderPrice' },
            { name: 'orderNum' },
            { name: 'tradeNum' },
            { name: 'cdNum' },
            { name: 'insertDateTime' }
        ];
        
        return (
            <Accordion
                headers={headerArr}
            >
                { TradeStore.orders.map((order, index) => {
                    return (
                        <AccordionItem 
                            data={order}
                            key={index}
                            keys={keyArr}
                        />
                    );
                })}
            </Accordion>
        );
    }
}
