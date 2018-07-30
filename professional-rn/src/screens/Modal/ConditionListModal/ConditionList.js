import React, { Component } from 'react';
import { observer } from 'mobx-react/native';
import RnPropTypes from 'prop-types';
import { PropTypes } from 'mobx-react';
import { Accordion, AccordionItem } from '../../../components';
import AccordionItemConditionButton from './components/AccordionItemConditionButton';
import Logger from '../../../utils/Logger';

@observer
export default class ConditionList extends Component {
    static propTypes = {
        conditionArr: PropTypes.observableArray,
        isAccordionItemButton: RnPropTypes.bool
    }
    static defaultProps = {
        isAccordionItemButton: true
    }
    constructor(props) {
        super(props);
        this.logger = new Logger(ConditionList);
    }
    _renderAccordionItemButton(condition) {
        if (this.props.isAccordionItemButton) {
            return <AccordionItemConditionButton condition={condition} />;
        }
    }
    render() {
        const { conditionArr } = this.props;
        return (
            <Accordion
                headers={[
                    { text: '合約', style: { width: 80 } },
                    { text: '状态', style: { width: 60 } },
                    { text: '类型', style: { width: 80 } },
                    { text: '条件', style: { width: 150 } },
                    { text: '多空', style: { width: 60 } },
                    { text: '开平', style: { width: 60 } },// to do ... 
                    { text: '下单', style: { width: 120 } },
                    { text: '有效期', style: { width: 80 } },
                    { text: '触发时间', style: { width: 150 } } 
                ]}
                count={conditionArr.length}
            >
                { conditionArr.map((condition, index) => {
                    return (
                        <AccordionItem 
                            data={condition}
                            key={index}
                            keys={[
                                { name: 'productName' },
                                { name: 'statusText' },
                                { name: 'conditionTypeText' },
                                { name: 'compareTypeText' },
                                { name: 'directionText', color: 'directionColor' },
                                { name: 'openCloseType' },// to do ... 
                                { name: 'insertOrderText' },
                                { name: 'expiration' },
                                { name: 'insertTimeText' }
                            ]}
                        >
                            { this._renderAccordionItemButton(condition) }
                        </AccordionItem>
                    );
                })}
            </Accordion>
        );
    }
}
