import React, { Component } from 'react';
import { inject, observer } from 'mobx-react/native';
import { View, Text } from 'react-native';
import PropTypes from 'prop-types';
import { DisplayWithButtonApply } from '../../../../../components';
import { Colors, Enum, NavigatorStyle, Variables } from '../../../../../global';

@inject('ApplyTradeStore') @observer
export default class ApplyDetailContractInfo extends Component {
    static contextTypes = {
        navigator: PropTypes.object
    };
    _addTraderBond() {
        this.context.navigator.showModal({
            screen: 'applyTrade.AppendTraderBondModal',
            title: '追加保证金',
            navigatorButtons: {
              leftButtons: [
                  {
                      icon: Variables.icon.closeAppendTraderBond.source,
                      id: Variables.icon.closeAppendTraderBond.id
                  }
              ]
            }
          }); 
    }
    _renderAppendTraderBond() {
        const { contractDetail } = this.props.ApplyTradeStore;
        const stateType = contractDetail.stateType;
        if (stateType === Enum.applyTradeStateType.using.value) {
            return (
                <DisplayWithButtonApply 
                    label={'追加保证金  '} 
                    text={`${contractDetail.appendTraderBond}`}
                    buttonText={'立即追加'}
                    onButtonPress={() => this._addTraderBond()}
                />
            );
        }
        return <DisplayWithButtonApply label={'追加保证金  '} text={`${contractDetail.appendTraderBond}`} />;
    }
    render() {
        const { ApplyTradeStore } = this.props;
        const { contractDetail } = ApplyTradeStore;
        return (
            <View>
                <View style={[{ margin: 10, marginTop: 20 }]}>
                    <Text style={{ color: Colors.titleTextColor }}>{'方案申请'}</Text>
                </View>
                <DisplayWithButtonApply label={'申请时间  '} text={ApplyTradeStore.appTimeString} />
                <DisplayWithButtonApply label={'最大持仓  '} text={'参考初始可持仓手数'} />
                <DisplayWithButtonApply label={'操盘保证金  '} text={`${contractDetail.traderBond}`} />
                {this._renderAppendTraderBond()}
                <DisplayWithButtonApply label={'总操盘资金  '} text={`${contractDetail.traderTotal}`} />
                <DisplayWithButtonApply label={'亏损平仓线  '} text={`${contractDetail.lineLoss}`} />
                <DisplayWithButtonApply label={'账户管理费  '} text={'免费'} />
            </View>
        );
    }
}
ApplyDetailContractInfo.navigatorStyle = NavigatorStyle.screenInnerStyle;
