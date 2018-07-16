import React, { Component } from 'react';
import { View, Text } from 'react-native';
import { inject, observer } from 'mobx-react/native';
import PropTypes from 'prop-types';
import { DisplayWithButtonApply } from '../../../../components';

@inject('ApplyTradeStore') @observer
export default class ApplyTradeChooseDepositInfo extends Component {
    static propTypes = {
        isTraderBond: PropTypes.bool
    }
    static defaultProps = {
        isTraderBond: true,
    }
    render() {
        const depositInfo = this.props.ApplyTradeStore.depositInfo;
        if (!depositInfo) {
            return <View />;
        }
        return (
            <View>
                { this.props.isTraderBond && <DisplayWithButtonApply label={'操盘保证金  '} text={`¥ ${depositInfo.traderBond}`} />}
                <DisplayWithButtonApply label={'总操盘资金  '} text={`$ ${depositInfo.traderTotal}`} />
                <DisplayWithButtonApply label={'亏损平仓线  '} text={`$ ${depositInfo.lineLoss}`} />
            </View>
        );
    }
}
