/**
 * 掛單
 */
import React, { Component } from 'react';
import { inject, observer } from 'mobx-react/native';
import { Accordion, AccordionItem } from '../../../../../components';
import { AccordionItemDesignateButton, AccordionFooterDesignate } from '../../components';
import { Layout, Enum } from '../../../../../global';
import Logger from '../../../../../utils/Logger';

@inject('TradeStore', 'FutureTypeStore') @observer
export default class DesignateView extends Component {
    
    constructor(props) {
        super(props);
        this.logger = new Logger(DesignateView);
    }
    // header -> { text: '挂单时间', style: { flex: 1 } }
    // accordionItem ->  { name: 'insertDateTime' }
    render() {
        const { TradeStore } = this.props;
        const padColumnSize = Layout.screenWidth / 6;
        const padTimeColumnSize = Layout.screenWidth - ((padColumnSize * 2) + (60 * 3));
        const headerArr = this.props.FutureTypeStore.isFutIn ? [
            { text: '合约', style: { width: Layout.deviceSize === Enum.deviceSize.pad ? padColumnSize : 80 } },   // style 設定個別column 寬度
            { text: '买卖', style: { width: 60 } },
            { text: '开平', style: { width: 60 } },
            { text: '委托价', style: { width: Layout.deviceSize === Enum.deviceSize.pad ? padColumnSize : 60 } },
            { text: '委托量', style: { width: 60 } },
            { text: '挂单量', style: { width: 60 } },
            { text: '挂单时间', style: { width: Layout.deviceSize === Enum.deviceSize.pad ? padTimeColumnSize : 150 } }
        ] : [
            { text: '合约', style: { width: Layout.deviceSize === Enum.deviceSize.pad ? padColumnSize : 80 } },   // style 設定個別column 寬度
            { text: '买卖', style: { width: 60 } },
            { text: '委托价', style: { width: Layout.deviceSize === Enum.deviceSize.pad ? padColumnSize : 60 } },
            { text: '委托量', style: { width: 60 } },
            { text: '挂单量', style: { width: 60 } },
            { text: '挂单时间', style: { width: Layout.deviceSize === Enum.deviceSize.pad ? padTimeColumnSize : 150 } }
        ];
        const keyArr = this.props.FutureTypeStore.isFutIn ? [
            { name: 'productName' }, 
            { name: 'directionText', color: 'directionColor' },
            { name: 'openCloseType' },
            { name: 'orderPrice' },
            { name: 'orderNum' },
            { name: 'designateNum' },
            { name: 'insertDateTime' }
        ] : [
            { name: 'productName' }, 
            { name: 'directionText', color: 'directionColor' },
            { name: 'orderPrice' },
            { name: 'orderNum' },
            { name: 'designateNum' },
            { name: 'insertDateTime' }
        ];
        return (
            <Accordion
                headers={headerArr}
                count={TradeStore.designates.length}
                renderFooter={() => <AccordionFooterDesignate />}
            >
                { TradeStore.designates.map((designate, index) => {
                    return (
                        <AccordionItem 
                            data={designate}
                            key={index}
                            keys={keyArr}
                        >
                            <AccordionItemDesignateButton designate={designate} />
                        </AccordionItem>
                    );
                })}
            </Accordion>
        );
    }
}
