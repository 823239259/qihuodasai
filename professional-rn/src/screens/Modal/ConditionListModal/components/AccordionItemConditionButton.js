import React, { Component } from 'react';
import { observe } from 'mobx';
import { View, Text } from 'react-native';
import PropTypes from 'prop-types';
import { inject, observer } from 'mobx-react/native';
import { Layout, Enum } from '../../../../global';
import { AccordionItemButton, Dialog } from '../../../../components';
import { Logger } from '../../../../utils';

@inject('ConditionStore') @observer
export default class AccordionItemConditionButton extends Component {
    static propTypes = {
        condition: PropTypes.object,
    }
    static defaultProps = {
    };
    constructor(props) {
        super(props);
        this.logger = new Logger(AccordionItemConditionButton);
    }
    _renderPauseOrStartButton() {
        const { ConditionStore, condition } = this.props;
        if (condition.status === 0) { // Enum.triggerStatus - 运行中
            return <AccordionItemButton title={Enum.accordionItemButtonType.pause.text} onPress={() => ConditionStore.toDialog(Enum.accordionItemButtonType.pause.value, condition)} />;
        } else if (condition.status === 1) { // Enum.triggerStatus - 暂停
            return <AccordionItemButton title={Enum.accordionItemButtonType.start.text} onPress={() => ConditionStore.toDialog(Enum.accordionItemButtonType.start.value, condition)} />;
        }        
    }
    render() {
        const { ConditionStore, condition } = this.props;
        return (
            <View style={{ flexDirection: 'row', justifyContent: 'flex-end', width: Layout.screenWidth }}>
                { this._renderPauseOrStartButton() }
                <AccordionItemButton title={Enum.accordionItemButtonType.modify.text} onPress={() => ConditionStore.toDialog(Enum.accordionItemButtonType.modify.value, condition)} />
                <AccordionItemButton title={Enum.accordionItemButtonType.delete.text} onPress={() => ConditionStore.toDialog(Enum.accordionItemButtonType.delete.value, condition)} />
                <Dialog 
                    visible={ConditionStore.isDialogVisible}
                    content={ConditionStore.dialogContent}
                    height={Layout.dialogHeight}
                    onConfirm={() => ConditionStore.confirmDialog()}
                    onCancel={() => ConditionStore.cancelDialog()}
                />
            </View>
        );
    }
}
