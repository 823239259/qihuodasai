/**
 * 方案明細
 */
import React, { Component } from 'react';
import { View, ScrollView } from 'react-native';
import { ApplyDetailContractList, ApplyDetailTradeAccount, ApplyDetailContractInfo, ApplyDetailButton } from './components';
import { SafeAreaView } from '../../../../containments';
//
export default class ApplyDetailCurrent extends Component {
    render() {
        return (
            <SafeAreaView>
                <ScrollView>
                    <ApplyDetailTradeAccount />
                    <ApplyDetailContractInfo />
                    <ApplyDetailContractList />
                </ScrollView>
                <ApplyDetailButton />
            </SafeAreaView>
        );
    }
}
