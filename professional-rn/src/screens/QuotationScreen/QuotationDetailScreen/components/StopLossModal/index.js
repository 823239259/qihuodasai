import React, { Component } from 'react';
import { View, Text } from 'react-native';
import PropTypes from 'prop-types';
import ScrollableTabView from 'react-native-scrollable-tab-view';
import { inject, observer } from 'mobx-react/native';
import { TabBarModal, TabBarDropdown, Dialog } from '../../../../../components';
import { Colors, Layout } from '../../../../../global';
import StopLossHeader from './StopLossHeader';
import StopLossTab from './StopLossTab';
import StopWinTab from './StopWinTab';

@inject('TradeHoldPositionStore') @observer
export default class StopLossModal extends Component {
    static propTypes = {
        isModal: PropTypes.bool
    }
    static defaultProps = {
        isModal: true
    }
    _renderTabs() {}
    render() {
        const { TradeHoldPositionStore } = this.props;
        return (
            <TabBarModal 
                visible={TradeHoldPositionStore.isStopLossVisible}
                onCancel={() => TradeHoldPositionStore.cancelStopLoss()}
                onConfirm={() => TradeHoldPositionStore.confirmStopLoss()}
                height={250}
                isModal={this.props.isModal}
            >
                <View style={{ flex: 1 }}>
                    <StopLossHeader />
                    <ScrollableTabView
                        style={{ flex: 1 }}
                        locked={true}
                        scrollWithoutAnimation={true}
                        onChangeTab={(currentTab) => TradeHoldPositionStore.setStopLossCurrentTab(currentTab.i)}
                        renderTabBar={() => <TabBarDropdown 
                                                backgroundColor={Colors.tabBarModalBackgroundColor}
                                                textStyle={Layout.fontNormal}
                                            />
                                    }
                    >
                        { TradeHoldPositionStore.isStopLossTabVisible ? <StopLossTab tabLabel={'止损'} /> : null }
                        { TradeHoldPositionStore.isStopWinTabVisible ? <StopWinTab tabLabel={'止盈'} /> : null }
                    </ScrollableTabView>
                </View>
                <Dialog 
                    visible={TradeHoldPositionStore.isStopLossDialogVisible}
                    header={'提示'}
                    content={`是否添加${TradeHoldPositionStore.stopLossText}`}
                    height={Layout.dialogHeight}
                    onConfirm={() => TradeHoldPositionStore.confirmStopLossDialog()}
                    onCancel={() => TradeHoldPositionStore.cancelStopLossDialog()}
                />
            </TabBarModal>
        );
    }
}
