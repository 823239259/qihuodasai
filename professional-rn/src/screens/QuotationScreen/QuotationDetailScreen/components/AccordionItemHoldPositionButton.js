import React, { Component } from 'react';
import { View, Text } from 'react-native';
import PropTypes from 'prop-types';
import { inject, observer } from 'mobx-react/native';
import { Layout } from '../../../../global';
import { AccordionItemButton, Dialog } from '../../../../components';
import StopLossModal from './StopLossModal';

@inject('TradeHoldPositionStore') @observer
export default class AccordionItemHoldPositionButton extends Component {
    static propTypes = {
        holdPosition: PropTypes.object,
    }
    _renderCloseHoldDialogContent() {
        const { productName, holdNum } = this.props.holdPosition;
        return (
            <View style={{ alignItems: 'center' }}>
                <Text>{`确认平仓合约【 ${productName} 】`}</Text>
                <Text>{'价格【市价】'}</Text>
                <Text>{`手数【 ${holdNum} 】`}</Text>
            </View>
        );
    }
    render() {
        const { TradeHoldPositionStore } = this.props;
        return (
            <View style={{ flexDirection: 'row', justifyContent: 'flex-end', width: Layout.screenWidth }}>
                <AccordionItemButton title={'平仓'} onPress={() => TradeHoldPositionStore.toClose()} />
                <AccordionItemButton title={'反手'} onPress={() => TradeHoldPositionStore.toReverse()} />
                <AccordionItemButton title={'止损止盈'} onPress={() => TradeHoldPositionStore.toStopLoss(this.props.holdPosition)} />
                <Dialog 
                    visible={TradeHoldPositionStore.isCloseHoldDialogVisible}
                    header={'确认平仓'}
                    height={Layout.dialogHeight}
                    renderContent={() => this._renderCloseHoldDialogContent()}
                    onConfirm={() => TradeHoldPositionStore.confirmCloseHoldDialog(this.props.holdPosition)}
                    onCancel={() => TradeHoldPositionStore.cancelCloseHoldDialog()}
                />
                <Dialog 
                    visible={TradeHoldPositionStore.isReverseHoldDialogVisible}
                    header={'确认反手'}
                    content={`确认反手合约【${this.props.holdPosition.productName}】`}
                    height={Layout.dialogHeight}
                    onConfirm={() => TradeHoldPositionStore.confirmReverseHoldDialog(this.props.holdPosition)}
                    onCancel={() => TradeHoldPositionStore.cancelReverseHoldDialog()}
                />
                <StopLossModal />
            </View>
        );
    }
}
