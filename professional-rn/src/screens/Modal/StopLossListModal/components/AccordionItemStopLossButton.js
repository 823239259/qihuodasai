import React, { Component } from 'react';
import { observe } from 'mobx';
import { View, Text } from 'react-native';
import PropTypes from 'prop-types';
import { inject, observer } from 'mobx-react/native';
import { Layout, Enum } from '../../../../global';
import { AccordionItemButton, Dialog } from '../../../../components';
import { Logger } from '../../../../utils';
import StopLossModal
    from './../../../QuotationScreen/QuotationDetailScreen/components/StopLossModal';

@inject('StopLossStore') @observer
export default class AccordionItemStopLossButton extends Component {
    static propTypes = {
        stopLoss: PropTypes.object,
    }
    static defaultProps = {
    };
    constructor(props) {
        super(props);
        this.logger = new Logger(AccordionItemStopLossButton);
    }
    _renderPauseOrStartButton() {
        const { StopLossStore, stopLoss } = this.props;
        if (stopLoss.status === 0) { // Enum.triggerStatus - 运行中
            return <AccordionItemButton title={Enum.accordionItemButtonType.pause.text} onPress={() => StopLossStore.toDialog(Enum.accordionItemButtonType.pause.value, stopLoss)} />;
        } else if (stopLoss.status === 1) { // Enum.triggerStatus - 暂停
            return <AccordionItemButton title={Enum.accordionItemButtonType.start.text} onPress={() => StopLossStore.toDialog(Enum.accordionItemButtonType.start.value, stopLoss)} />;
        }        
    }
    render() {
        const { StopLossStore, stopLoss } = this.props;
        return (
            <View style={{ flexDirection: 'row', justifyContent: 'flex-end', width: Layout.screenWidth }}>
                { this._renderPauseOrStartButton() }
                <AccordionItemButton title={Enum.accordionItemButtonType.modify.text} onPress={() => StopLossStore.toDialog(Enum.accordionItemButtonType.modify.value, stopLoss)} />
                <AccordionItemButton title={Enum.accordionItemButtonType.delete.text} onPress={() => StopLossStore.toDialog(Enum.accordionItemButtonType.delete.value, stopLoss)} />
                <Dialog 
                    visible={StopLossStore.isDialogVisible}
                    content={StopLossStore.dialogContent}
                    height={Layout.dialogHeight}
                    onConfirm={() => StopLossStore.confirmDialog()}
                    onCancel={() => StopLossStore.cancelDialog()}
                />
                <StopLossModal isModal={false} />
            </View>
        );
    }
}
