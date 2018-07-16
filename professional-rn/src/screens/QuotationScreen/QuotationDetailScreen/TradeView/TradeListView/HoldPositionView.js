/**
 * 持仓
 */
import React, { Component } from 'react';
import { inject, observer } from 'mobx-react/native';
import { Accordion, AccordionItem } from '../../../../../components';
import { AccordionItemHoldPositionButton, AccordionFooterHoldPosition } from '../../components';
import { Layout, Enum } from '../../../../../global';
import Logger from '../../../../../utils/Logger';

@inject('TradeStore') @observer
export default class HoldPositionView extends Component {
    
    constructor(props) {
        super(props);
        this.logger = new Logger(HoldPositionView);
    }
    render() {
        const { TradeStore } = this.props;
        const padColumnSize = Layout.screenWidth / 5;
        const holdPositionArr = [];
        TradeStore.holdPositions.forEach((value, key, map) => {
            holdPositionArr.push(value);
        });
        return (
            <Accordion
                headers={[
                    { text: '合约', style: { width: Layout.deviceSize === Enum.deviceSize.pad ? padColumnSize : 80 } },   // style 設定個別column 寬度
                    { text: '多空', style: { width: Layout.deviceSize === Enum.deviceSize.pad ? padColumnSize : 60 } },
                    { text: '手数', style: { width: Layout.deviceSize === Enum.deviceSize.pad ? padColumnSize : 60 } },
                    { text: '持仓均价', style: { width: Layout.deviceSize === Enum.deviceSize.pad ? padColumnSize : 80 } },
                    { text: '浮动盈亏', style: { width: Layout.deviceSize === Enum.deviceSize.pad ? padColumnSize : 150 } } // 很長的： -390.00:HKD-HKFE 
                ]}
                count={holdPositionArr.length}
                renderFooter={() => <AccordionFooterHoldPosition />}
            >
                { holdPositionArr.map((holdPosition, index) => {
                    return (
                        <AccordionItem 
                            data={holdPosition}
                            key={index}
                            keys={[
                                { name: 'productName' }, 
                                { name: 'directionText', color: 'directionColor' },
                                { name: 'holdNum' },
                                { name: 'holdAvgPriceText' },
                                { name: 'floatProfitText', color: 'floatProfitColor' }
                            ]}
                        >
                            <AccordionItemHoldPositionButton holdPosition={holdPosition} />
                        </AccordionItem>
                    );
                })}
            </Accordion>
        );
    }
}
