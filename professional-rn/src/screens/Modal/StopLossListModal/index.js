import React, { Component } from 'react';
import { View, Text } from 'react-native';
import { inject } from 'mobx-react/native';
import ScrollableTabView from 'react-native-scrollable-tab-view';
import { TabBarDropdown, Dialog } from '../../../components';
import { Variables, Colors, Layout, NavigatorStyle } from '../../../global';
import StopLossList from './StopLossList';

@inject('StopLossStore')
export default class StopLossListModal extends Component {
    constructor(props) {
        super(props);
        this.props.navigator.setOnNavigatorEvent(this.onNavigatorEvent.bind(this));
        this.state = { isExclamationVisible: false };
    }
    onNavigatorEvent(event) { // this is the onPress handler for the two buttons together
        if (event.type === 'NavBarButtonPress') { // this is the event type for button presses
            if (event.id === Variables.icon.closeStopLoss.id) { // this is the same id field from the static navigatorButtons definition
                this.props.navigator.dismissModal();
            } else if (event.id === Variables.icon.exclamationStopLoss.id) {
                this._showExclamation();
            }
        }
    }
    _showExclamation() {
        this.setState({ isExclamationVisible: true });
    }
    render() {
        return (
            <View style={{ flex: 1 }}>
                <ScrollableTabView
                    style={{ flex: 1 }}
                    locked={true}
                    scrollWithoutAnimation={true}
                    renderTabBar={() => <TabBarDropdown textStyle={Layout.fontBold} />}
                >
                    <StopLossList tabLabel={'未触发列表'} stopLossArr={this.props.StopLossStore.stopLossYets} />
                    <StopLossList tabLabel={'已触发列表'} stopLossArr={this.props.StopLossStore.stopLossDones} isAccordionItemButton={false} />
                </ScrollableTabView>
                <Dialog 
                    visible={this.state.isExclamationVisible}
                    content={'1.云端止损单只保证触发不保证成交\n2.暂不支持显示套利止损单'}
                    isCancel={false}
                    onConfirm={() => this.setState({ isExclamationVisible: false })}
                />
            </View>
        );
    }
}
StopLossListModal.navigatorStyle = NavigatorStyle.modalStyle;
