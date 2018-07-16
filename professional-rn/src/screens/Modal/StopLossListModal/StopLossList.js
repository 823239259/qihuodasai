import React, { Component } from 'react';
import { observer } from 'mobx-react/native';
import RnPropTypes from 'prop-types';
import { PropTypes } from 'mobx-react';
import { Accordion, AccordionItem } from '../../../components';
import AccordionItemStopLossButton from './components/AccordionItemStopLossButton';
import { Layout, Enum } from '../../../global';
import Logger from '../../../utils/Logger';

@observer
export default class TriggerYet extends Component {
    static propTypes = {
        stopLossArr: PropTypes.observableArray,
        isAccordionItemButton: RnPropTypes.bool
    }
    static defaultProps = {
        isAccordionItemButton: true
    }
    constructor(props) {
        super(props);
        this.logger = new Logger(TriggerYet);
    }
    _renderAccordionItemButton(stopLoss) {
        if (this.props.isAccordionItemButton) {
            return <AccordionItemStopLossButton stopLoss={stopLoss} />;
        }
    }
    render() {
        const { stopLossArr } = this.props;
        const columnRemainingWidth = Layout.screenWidth - ((80 * 2) + (60 * 5) + 150);
        return (
            <Accordion
                headers={[
                    { text: '合約', style: { width: 80 } },   // style 設定個別column 寬度
                    { text: '状态', style: { width: 60 } },
                    { text: '多空', style: { width: 60 } },
                    { text: '类别', style: { width: 60 } },
                    { text: '手数', style: { width: 60 } },
                    { text: '触发条件', style: { width: Layout.deviceSize === Enum.deviceSize.pad ? columnRemainingWidth : 120 } },
                    { text: '委托价', style: { width: 60 } },
                    { text: '有效期', style: { width: 80 } },
                    { text: '下单时间', style: { width: 150 } } // 很長的： -390.00:HKD-HKFE 
                ]}
                count={stopLossArr.length}
            >
                { stopLossArr.map((stopLoss, index) => {
                    return (
                        <AccordionItem 
                            data={stopLoss}
                            key={index}
                            keys={[
                                { name: 'productName' },
                                { name: 'statusText' },
                                { name: 'directionText', color: 'directionColor' },
                                { name: 'stopLossTypeText' },
                                { name: 'num' },
                                { name: 'stopLossPriceText' },
                                { name: 'orderTypeText' },
                                { name: 'expiration' },
                                { name: 'insertTime' }
                            ]}
                        >
                            { this._renderAccordionItemButton(stopLoss) }
                        </AccordionItem>
                    );
                })}
            </Accordion>
        );
    }
}
