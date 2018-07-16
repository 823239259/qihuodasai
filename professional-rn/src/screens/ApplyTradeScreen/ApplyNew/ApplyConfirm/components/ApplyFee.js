import React, { Component } from 'react';
import { View, Text, StyleSheet } from 'react-native';
import Icon from 'react-native-vector-icons/FontAwesome';
import { inject, observer } from 'mobx-react/native';
import { Colors, Layout, Variables } from '../../../../../global';


@inject('ApplyTradeStore') @observer
export default class ApplyFee extends Component {
    _renderCompare() {
        let iconName = 'times-circle';
        let color = Colors.red;
        let text = ' 余额不足';
        if (this.props.ApplyTradeStore.compared) {
            iconName = 'check-circle';
            color = Colors.green;
            text = ' 余额充足';
        }
        return (
            <View style={[Layout.center, { flexDirection: 'row' }]}>
                <Icon
                    name={iconName} 
                    size={Variables.icon.size} 
                    color={color}
                />
                <Text style={{ color }}>{text}</Text>
            </View>
        );
    }
    render() {
        const { ApplyTradeStore } = this.props;
        return (
            <View style={styles.container}>
                <View style={[{ margin: Layout.applyTradeChooseDepositInfoRowPadding }]}>
                    <Text style={{ color: Colors.titleTextColor }}>{'操盘费用'}</Text>
                </View>
                <View style={[styles.row, { justifyContent: 'space-between' }]}>
                    <View style={{ flexDirection: 'row' }}>
                        <Text style={[Layout.fontSize, { color: Colors.greyText }]}>{'账户余额  '}</Text>
                        <Text style={[Layout.fontSize, { color: Colors.lightBlue }]}>{`¥ ${ApplyTradeStore.balance}`}</Text>
                    </View>
                    {this._renderCompare()}
                </View>
                <View style={[styles.row, { justifyContent: 'space-between' }]}>
                    <View style={{ flexDirection: 'row' }}>
                        <Text style={[Layout.fontSize, { color: Colors.greyText }]}>{'支付金额  '}</Text>
                        <Text style={[Layout.fontSize, { color: Colors.lightBlue }]}>{`¥ ${ApplyTradeStore.chooseDeposit}`}</Text>
                    </View>
                    <Text style={{ color: Colors.greyText }}>{'操盘保证金'}</Text>
                </View>
            </View>
        );
    }
}
const styles = StyleSheet.create({
    container: {
        marginTop: Layout.applyTradeChooseDepositInfoRowPadding,
        marginBottom: Layout.applyTradeChooseDepositInfoRowPadding * 2
    },
    row: {
        flexDirection: 'row',
        borderBottomWidth: StyleSheet.hairlineWidth,
        borderColor: Colors.grey,
        paddingVertical: Layout.applyTradeChooseDepositInfoRowPadding,
        marginHorizontal: Layout.applyTradeChooseDepositInfoRowPadding
    }
});
