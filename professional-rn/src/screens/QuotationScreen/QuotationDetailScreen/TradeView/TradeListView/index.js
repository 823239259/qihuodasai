/*
    Tab: 持仓(HoldPosition), 挂单(Designate), 委托(Order), 成交(Deal)
*/
import React, { Component } from 'react';
import { View, Text } from 'react-native';
import ScrollableTabView from 'react-native-scrollable-tab-view';
import { Colors, Layout } from '../../../../../global';
import { TabBarTrade } from '../../../../../components';
import HoldPositionView from './HoldPositionView';
import DesignateView from './DesignateView';
import OrderView from './OrderView';
import DealView from './DealView';

export default class TradeListView extends Component {
    render() {
        return (
            <View style={{ flex: 1, backgroundColor: Colors.bg }}>
                <ScrollableTabView
                    tabBarUnderlineStyle={{ backgroundColor: 'transparent' }}
                    locked={true}
                    scrollWithoutAnimation={true}
                    renderTabBar={() => <TabBarTrade
                                            backgroundColor={Colors.tabBarTradeBackgroundColor}
                                            activeTextColor={Colors.tabBarTradeActiveTextColor}
                                            inactiveTextColor={Colors.tabBarTradeInactiveTextColor}
                                            borderStyle={{ borderWidth: 1, borderColor: Colors.black }}
                                            textStyle={{ fontSize: Layout.tabBarTradeFontSize }}
                                        />
                    }
                >
                    <HoldPositionView tabLabel='持仓' />
                    <DesignateView tabLabel='挂单' />
                    <OrderView tabLabel='委托' />
                    <DealView tabLabel='成交' />
                </ScrollableTabView>
            </View>
        );
    }
}
