import React, { Component } from 'react';
import { View, Text, ScrollView } from 'react-native';
import PropTypes from 'prop-types';
import { inject, observer } from 'mobx-react/native';
import { Layout } from '../../../../global';
import { AccordionItemButton, AccordionFooter, Dialog } from '../../../../components';

@inject('TradeDesignateStore') @observer
export default class AccordionFooterDesignate extends Component {
    static propTypes = {
        count: PropTypes.number,
        scrollX: PropTypes.number
    }
    static defaultProps = {
        count: 0,
    }
    componentWillReceiveProps(nextProps) {
        if (!nextProps) {
            return;
        }
        if (nextProps.scrollX) {
            this._scrollView.scrollTo({ x: -nextProps.scrollX, animated: false });
        }
    }
    render() {
        const { TradeDesignateStore } = this.props;
        return (
            <View>
                <AccordionFooter count={this.props.count} />
                <ScrollView 
                    contentContainerStyle={{ width: Layout.screenWidth }}
                    horizontal={true}
                    directionalLockEnabled={false}
                    scrollEventThrottle={16}
                    ref={(scrollView) => this._scrollView = scrollView}
                >
                    <View style={{ flexDirection: 'row', justifyContent: 'flex-end', width: Layout.screenWidth }}>
                        <AccordionItemButton title={'全部撤单'} onPress={() => TradeDesignateStore.toAllCancel()} />
                    </View>
                </ScrollView>
                <Dialog 
                    visible={TradeDesignateStore.isAllCancelDesignateDialogVisible}
                    header={'确认全部撤单'}
                    content={'此操作将撤销挂单中所有合约，请您慎重选择。是否确认将所有合约全部撤销？'}
                    height={Layout.dialogHeight}
                    onConfirm={() => TradeDesignateStore.confirmAllCancelDesignateDialog()}
                    onCancel={() => TradeDesignateStore.cancelAllCancelDesignateDialog()}
                />
            </View>
        );
    }
}
