import React, { Component } from 'react';
import _ from 'lodash';
import { View, Text } from 'react-native';
import PropTypes from 'prop-types';
import { inject, observer } from 'mobx-react/native';
import { Layout, Colors } from '../../../../global';
import { AccordionItemButton, Dialog } from '../../../../components';
import { DesignateModification } from '../components';

@inject('TradeDesignateStore') @observer
export default class AccordionItemDesignateButton extends Component {
    static propTypes = {
        designate: PropTypes.object
    }
    _renderCancelDesignateDialogContent() {
        const { productName, orderNum } = this.props.designate;
        return (
            <View style={{ alignItems: 'center' }}>
                <Text>{`确认撤单合约【 ${productName} 】`}</Text>
                <Text>{`手数【 ${orderNum} 】`}</Text>
            </View>
        );
    }
    _renderModifyDialogHeader() {
        if (this.props.TradeDesignateStore.isModifyFinal) {
            return '确认改单';
        }
        return '改单';
    }
    _renderModifyDialogRenderContent() {
        if (this.props.TradeDesignateStore.isModifyFinal) {
            return null;
        }
        return () => <DesignateModification designate={this.props.designate} />;
    }
    _renderModifyDialogContent() {
        const { TradeDesignateStore, designate } = this.props;
        if (TradeDesignateStore.isModifyFinal) {
            return `确认将原合约【${designate.productName}】，价格【${designate.orderPrice}】，手数【${designate.orderNum}】改为：价格【${TradeDesignateStore.orderPrice}】，手数【${TradeDesignateStore.orderNum}】`;
        }
        return null;
    }
    render() {
        const { TradeDesignateStore, designate } = this.props;
        return (
            <View style={{ flexDirection: 'row', justifyContent: 'flex-end', width: Layout.screenWidth }}>
                <AccordionItemButton title={'撤单'} onPress={() => TradeDesignateStore.toCancel()} />
                <AccordionItemButton title={'改单'} onPress={() => TradeDesignateStore.toModify()} />
                <Dialog 
                    visible={TradeDesignateStore.isCancelDesignateDialogVisible}
                    header={'确认撤单'}
                    height={Layout.dialogHeight}
                    renderContent={() => this._renderCancelDesignateDialogContent()}
                    onConfirm={() => TradeDesignateStore.confirmCancelDesignateDialog(designate)}
                    onCancel={() => TradeDesignateStore.cancelCancelDesignateDialog()}
                />
                <Dialog 
                    visible={TradeDesignateStore.isModifyDesignateDialogVisbile}
                    header={this._renderModifyDialogHeader()}
                    height={Layout.dialogHeight}
                    renderContent={this._renderModifyDialogRenderContent()}
                    content={this._renderModifyDialogContent()}
                    onConfirm={() => TradeDesignateStore.confirmModifyDesignateDialog(designate)}
                    onCancel={() => TradeDesignateStore.cancelModifyDesignateDialog()}
                />
            </View>
        );
    }
}
