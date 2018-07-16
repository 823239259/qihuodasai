import React, { Component } from 'react';
import { View, Text, StyleSheet } from 'react-native';
import PropTypes from 'prop-types';
import { inject, observer } from 'mobx-react/native';
import moment from 'moment';
import { Enum, Colors } from '../../../global';
import { DateUtil } from '../../../utils';

@observer
export default class AccountFundRow extends Component {
    static propTypes = {
        fund: PropTypes.object
    };
    render() {
        const { fund } = this.props;
        const dateTimeArr = moment(fund.payTime * 1000).format('YYYY-MM-DD HH:mm').split(' ');
        let dateString = dateTimeArr[0];
        const todayString = DateUtil.getToday().split(' ')[0];
        const yesterdayString = DateUtil.getYesterday().split(' ')[0];
        if (dateString === todayString) {
            dateString = '今天';
        } else if (dateString === yesterdayString) {
            dateString = '昨天';
        }
        const timeString = dateTimeArr[1];
        let direction = Enum.direction[0];
        if (String(fund.money)[0] === '-') {
            direction = Enum.direction[1];
        }
    
        return (
            <View style={styles.container}>
                <View style={{ flex: 1 }}>
                    <Text style={{ color: Colors.white }}>{fund.remark}</Text>
                    <Text style={{ color: Colors.greyText }}>{`${dateString} ${timeString}`}</Text>
                </View>
                <View style={{ flexDirection: 'row', justifyContent: 'flex-end', alignItems: 'center', width: 80 }}>
                    <Text style={{ color: direction.color, fontSize: 16 }}>{fund.money}</Text>
                </View>
            </View>
        );
    }
}
const styles = StyleSheet.create({
    container: {
        flex: 1,
        height: 80,
        padding: 20,
        backgroundColor: Colors.fundRowBackgroundColor,
        borderBottomWidth: 1,
        borderColor: Colors.blackBorder,

        flexDirection: 'row', 
        justifyContent: 'space-between'
    }
});
