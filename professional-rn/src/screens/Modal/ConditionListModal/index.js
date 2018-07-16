import React, { Component } from 'react';
import { View, Text } from 'react-native';
import { inject } from 'mobx-react/native';
import ScrollableTabView from 'react-native-scrollable-tab-view';
import { TabBarDropdown, Dialog } from '../../../components';
import { Variables, NavigatorStyle, Layout } from '../../../global';
import ConditionList from './ConditionList';
import ConditionEditModal from './components/ConditionEditModal';

@inject('ConditionStore')
export default class ConditionListModal extends Component {
    constructor(props) {
        super(props);
        this.props.navigator.setOnNavigatorEvent(this.onNavigatorEvent.bind(this));
        this.state = { isExclamationVisible: false };
    }
    onNavigatorEvent(event) { // this is the onPress handler for the two buttons together
        if (event.type === 'NavBarButtonPress') { // this is the event type for button presses
            if (event.id === Variables.icon.closeCondition.id) { // this is the same id field from the static navigatorButtons definition
                this.props.navigator.dismissModal();
            } else if (event.id === Variables.icon.exclamationCondition.id) {
                this._showExclamation();
            } else if (event.id === Variables.icon.plusCondition.id) {
                this._showEditModal();
            }
        }
    }
    _showExclamation() {
        this.setState({ isExclamationVisible: true });
    }
    _showEditModal() {
        this.props.ConditionStore.plusConditioModal();
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
                    <ConditionList tabLabel={'未触发列表'} conditionArr={this.props.ConditionStore.conditionYets} />
                    <ConditionList tabLabel={'已触发列表'} conditionArr={this.props.ConditionStore.conditionDones} isAccordionItemButton={false} />
                </ScrollableTabView>
                <Dialog 
                    visible={this.state.isExclamationVisible}
                    content={'1.云端条件单只保证触发不保证成交\n2.暂不支持显示套利条件单'}
                    isCancel={false}
                    onConfirm={() => this.setState({ isExclamationVisible: false })}
                />
                <ConditionEditModal />
            </View>
        );
    }
}
ConditionListModal.navigatorStyle = NavigatorStyle.modalStyle;
