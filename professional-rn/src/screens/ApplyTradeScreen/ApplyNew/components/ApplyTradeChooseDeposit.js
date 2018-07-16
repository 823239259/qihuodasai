import React, { Component } from 'react';
import { View } from 'react-native';
import { inject, observer } from 'mobx-react/native';
import { Layout } from '../../../../global';
import ToggleDeposit from './ToggleDeposit';

@inject('ApplyTradeStore') @observer
export default class ApplyTradeChooseDeposit extends Component {
    _renderContent() {
        const { ApplyTradeStore } = this.props;
        return ApplyTradeStore.depositParams.map((depositParam, index) => {
            let isToggle = false;
            if (depositParam.traderBond === ApplyTradeStore.chooseDeposit) {
                isToggle = true;
            }
            return <ToggleDeposit text={depositParam.traderBond} key={index} isToggle={isToggle} />;
        });
    }
    render() {
        return (
            <View style={{ flexDirection: 'row', flexWrap: 'wrap', padding: Layout.applyTradeChooseDepositMargin }}>
                { this._renderContent() }
            </View>
        );
    }
}
