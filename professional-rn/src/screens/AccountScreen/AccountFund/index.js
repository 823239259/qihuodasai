import React, { Component } from 'react';
import { View, Text, ScrollView } from 'react-native';
import { inject, observer } from 'mobx-react/native';
import { NavigatorStyle } from '../../../global';
import AccountFundHeader from './AccountFundHeader';
import AccountFundRow from './AccountFundRow';

@inject('AccountStore') @observer
export default class AccountFund extends Component {
    _renderContent() {
        return this.props.AccountStore.funds.map((fund, index) => {
            return <AccountFundRow key={`fund${index}`} fund={fund} />;
        });
    }
    render() {
        return (
            <View style={{ flex: 1 }}>
                <AccountFundHeader />
                <ScrollView style={{ marginTop: 10 }}>
                    { this._renderContent() }
                </ScrollView>
            </View>
        );
    }
}
AccountFund.navigatorStyle = NavigatorStyle.screenInnerStyle;
