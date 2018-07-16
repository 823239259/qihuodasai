import React, { Component } from 'react';
import { View, Text, StyleSheet, TouchableOpacity, Platform } from 'react-native';
import { inject, observer } from 'mobx-react/native';
import Icon from 'react-native-vector-icons/FontAwesome';
import { Dialog } from '../../../components';
import { Colors, Layout, Variables } from '../../../global';
import Button from './Button';
import RootNavi from './../../../app/RootNavi';

@inject('DrawerTradeStore', 'WorkbenchDetailStore', 'ConnectionScreenStore') @observer
export default class DrawerTrade extends Component {
    constructor(props) {
        super(props);
        this.loggedTrueArr = ['切换帐号', '条件单', '止盈止損單', '历史成交', '开户'];
        this.loggedFalseArr = ['登录交易账户', '开户'];
    }
    _toggleDrawer() {
        this.props.navigator.toggleDrawer({
            side: 'right',
            animated: true
        });
    }
    _onButtonPress(text) {
        const { DrawerTradeStore, WorkbenchDetailStore } = this.props;
        if (text === '切换模拟帐号') {
            DrawerTradeStore.setIsSwitchDialogVisbile(true);
        } else if (text === '登录交易账户') {
            WorkbenchDetailStore.toTradeLogin();
        } else if (text === '登录交易账户') {
            WorkbenchDetailStore.toTradeLogin();
        } else if (text === '开户') {
            this._toAbout();
        } else if (text === '止盈止損單') {
            this._toStopLossList();
        } else if (text === '历史成交') {
            this._toHistoryTrade();
        } else if (text === '条件单') {
            this._toConditionList();
        } else {
            DrawerTradeStore.setIsNewFeatureDialogVisbile(true);
        }
        this._toggleDrawer();
    }
    _renderAccounInfo() {
        return (
            <View style={styles.accountInfo}>
                <Icon
                    name="user" 
                    size={Variables.icon.size} 
                    color={Colors.iconButtonColor}
                    style={{ width: Variables.icon.size, height: Variables.icon.size }} 
                />
                <Text style={[Layout.fontNormal, { color: Colors.lightTextColor }]}>{this.props.ConnectionScreenStore.tradeAccount}</Text>
            </View>
        );
    }
    _renderButtons() {
        let arr = this.loggedFalseArr;
        if (this.props.ConnectionScreenStore.isTradeSocketConnection) {
            arr = this.loggedTrueArr;
        }
        return arr.map((value, index) => {
            return <Button key={index} text={value} onPress={(text) => this._onButtonPress(text)} />;
        });
    }
    _renderContent() {
        if (this.props.ConnectionScreenStore.isTradeSocketConnection) {
            return (
                <View>
                    { this._renderAccounInfo() }
                    { this._renderButtons() }
                </View>
            );
        }
        return this._renderButtons();
    }
    _toAbout() {
        RootNavi.popToRoot();
        // this.props.navigator.showModal({
        //     screen: 'quotation.QuotationAboutModal',
        //     title: '关于',
        //     navigatorButtons: {
        //     leftButtons: [
        //         {
        //             icon: Variables.icon.closeAbout.source,
        //             id: Variables.icon.closeAbout.id
        //         }
        //     ]
        //     }
        // }); 
    }
    _toStopLossList() {
        this.props.navigator.showModal({
            screen: 'trade.StopLossListModal',
            title: '止盈止損單',
            navigatorButtons: {
            leftButtons: [
                {
                    icon: Variables.icon.closeStopLoss.source,
                    id: Variables.icon.closeStopLoss.id
                },
                {
                    icon: Variables.icon.exclamationStopLoss.source,
                    id: Variables.icon.exclamationStopLoss.id
                }
            ]
            }
        }); 
    }
    _toConditionList() {
        this.props.navigator.showModal({
            screen: 'trade.ConditionListModal',
            title: '条件单',
            navigatorButtons: {
            leftButtons: [
                {
                    icon: Variables.icon.closeCondition.source,
                    id: Variables.icon.closeCondition.id
                },
                {
                    icon: Variables.icon.exclamationCondition.source,
                    id: Variables.icon.exclamationCondition.id
                }
            ],
            rightButtons: [
                {
                    icon: Variables.icon.plusCondition.source,
                    id: Variables.icon.plusCondition.id
                },
            ]
            }
        }); 
    }
    _toHistoryTrade() {
        this.props.navigator.showModal({
            screen: 'trade.HistoryTradeModal',
            title: '历史成交',
            navigatorButtons: {
            leftButtons: [
                {
                    icon: Variables.icon.closeHistoryTrade.source,
                    id: Variables.icon.closeHistoryTrade.id
                },
                {
                    icon: Variables.icon.exclamationHistoryTrade.source,
                    id: Variables.icon.exclamationHistoryTrade.id
                }
            ]
            }
        });
    }
    render() {
        const { DrawerTradeStore } = this.props;
        return (
            <View style={styles.container}>
                <View style={[styles.header, { height: Layout.navBarHeight }]}>
                    <TouchableOpacity 
                        style={[Layout.center, { width: Layout.navBarHeight, height: Layout.navBarHeight }]}
                        onPress={() => this._toggleDrawer()}
                    >
                        <Icon
                            name="close" 
                            size={Variables.icon.size} 
                            color={Colors.iconButtonColor}
                            style={{ width: Variables.icon.size, height: Variables.icon.size }} 
                        />
                    </TouchableOpacity>
                </View>
                <View style={styles.content}>
                    { this._renderContent() }
                </View>
                <Dialog 
                    visible={DrawerTradeStore.isSwitchDialogVisible}
                    content={'确认退出帐号'}
                    onConfirm={() => DrawerTradeStore.confirmSwitchDialog()}
                    onCancel={() => DrawerTradeStore.cancelSwitchSubmitDialog()}
                />
                <Dialog 
                    visible={DrawerTradeStore.isNewFeatureDialogVisible}
                    content={'该功能内测中，暂未开放，敬请期待'}
                    onConfirm={() => DrawerTradeStore.confirmNewFeatureDialog()}
                    isCancel={false}
                />
            </View>
        );
    }
}
const styles = StyleSheet.create({
    container: {
        backgroundColor: Colors.drawerTradeBackgroundColor,
        flex: 1,
        width: (Platform.OS === 'ios') ? null : Layout.screenWidth * 0.67
    },
    header: {
        marginTop: (Platform.OS === 'ios') ? Layout.statusBarHeight : 0,
        flexDirection: 'row',
        justifyContent: 'flex-end',
    },
    content: {
        flex: 1,
    },
    accountInfo: {
        backgroundColor: Colors.bgDarker, 
        height: Layout.buttonLargeHeight, 
        marginBottom: 10, 
        flexDirection: 'row', 
        justifyContent: 'space-around',
        ...Layout.center
    }
});

