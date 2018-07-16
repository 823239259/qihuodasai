import React, { Component } from 'react';
import { View, Text, StyleSheet } from 'react-native';
import { inject, observer } from 'mobx-react/native';
import { FundHeaderText } from '../components';
import { Colors, Layout } from '../../../global';


@inject('AccountStore')
export default class AccountFundHeader extends Component {
    render() {
        const { AccountStore } = this.props;
        return (
            <View style={styles.container}>
                <FundHeaderText title={'总收入'} num={AccountStore.incomeNum} money={AccountStore.incomeMoney} moneyColor={Colors.red} />
                <FundHeaderText title={'总支出'} num={AccountStore.outlayNum} money={AccountStore.outlayMoney} moneyColor={Colors.green} />
            </View>
        );
    }
}
const styles = StyleSheet.create({
    container: {
        height: 100,
        paddingHorizontal: Layout.accountContainerPadding,
        flexDirection: 'row',
        ...Colors.accountBalance,
        ...Layout.center,
    }
});
