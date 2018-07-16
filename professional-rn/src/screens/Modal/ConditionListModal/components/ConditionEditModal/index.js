import React, { Component } from 'react';
import { View, Text } from 'react-native';
import PropTypes from 'prop-types';
import ScrollableTabView from 'react-native-scrollable-tab-view';
import { inject, observer } from 'mobx-react/native';
import { TabBarModal, TabBarDropdown, Dialog } from '../../../../../components';
import { Colors, Layout } from '../../../../../global';
import PriceConditionTab from './PriceConditionTab';
import TimeConditionTab from './TimeConditionTab';

@inject('ConditionStore') @observer
export default class ConditionEditModal extends Component {
    static propTypes = {
    }
    static defaultProps = {
    }
    render() {
        const { ConditionStore } = this.props;
        return (
            <TabBarModal 
                visible={ConditionStore.isConditionEditVisible}
                onCancel={() => ConditionStore.cancelConditionEdit()}
                onConfirm={() => ConditionStore.confirmConditionEdit()}
                height={350}
                isModal={false}
            >
                <View style={{ flex: 1 }}>
                    <ScrollableTabView
                        style={{ flex: 1 }}
                        locked={true}
                        scrollWithoutAnimation={true}
                        onChangeTab={(currentTab) => ConditionStore.setIsPriceTab(currentTab.i)}
                        renderTabBar={() => <TabBarDropdown 
                                                backgroundColor={Colors.tabBarModalBackgroundColor}
                                                textStyle={Layout.fontNormal}
                                            />
                                    }
                    >
                        { ConditionStore.isPriceConditionTabVisible ? <PriceConditionTab tabLabel={'价格条件'} /> : null }
                        { ConditionStore.isTimeConditionTabVisible ? <TimeConditionTab tabLabel={'时间条件'} /> : null }
                    </ScrollableTabView>
                </View>
                <Dialog 
                    visible={ConditionStore.isConditionEditDialogVisible}
                    content={ConditionStore.conditionEditDialogContent}
                    height={Layout.dialogHeight}
                    onConfirm={() => ConditionStore.confirmConditionEditDialog()}
                    onCancel={() => ConditionStore.cancelConditionEditDialog()}
                />
            </TabBarModal>
        );
    }
}
