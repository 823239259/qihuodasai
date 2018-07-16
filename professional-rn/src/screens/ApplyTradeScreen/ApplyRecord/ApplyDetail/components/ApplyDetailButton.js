import React, { Component } from 'react';
import { View, Text } from 'react-native';
import { inject, observer } from 'mobx-react/native';
import PropTypes from 'prop-types';
import { ButtonCommon } from '../../../../../components';
import { Layout, Colors, Enum } from '../../../../../global';

@inject('ApplyTradeStore') @observer
export default class ApplyDetailButton extends Component {
    static contextTypes = {
        navigator: PropTypes.object
    };
    _toApplyEnd() {
        this.context.navigator.push({
            screen: 'applyTrade.ApplyEnd',
            title: '终结方案',
            backButtonTitle: '',
            animationType: 'slide-horizontal'
        });
    }
    render() {
        const { contractDetail } = this.props.ApplyTradeStore;
        const stateType = contractDetail.stateType;
        let buttonText = Enum.applyTradeStateType.using.userText;
        let isButtonValid = true;
        if (stateType === Enum.applyTradeStateType.opening.value) {
            buttonText = Enum.applyTradeStateType.opening.userText;
            isButtonValid = false;
        } else if (stateType === Enum.applyTradeStateType.applying.value || stateType === Enum.applyTradeStateType.waiting.value) {
            buttonText = Enum.applyTradeStateType.applying.userText;
            isButtonValid = false;
        } else if (stateType === Enum.applyTradeStateType.fail.value) {
            buttonText = Enum.applyTradeStateType.fail.userText;
            isButtonValid = false;
        } else if (stateType === Enum.applyTradeStateType.end.value) {
            buttonText = Enum.applyTradeStateType.end.userText;
            isButtonValid = false;
        }

        return (
            <View>
                <ButtonCommon
                    isValid={isButtonValid}
                    text={buttonText} 
                    color={Colors.white} 
                    style={{ flex: 0, height: Layout.buttonLargeHeight, marginHorizontal: Layout.applyTradeChooseDepositInfoRowPadding }}
                    backgroundColor={Colors.submitActiveBackgroundCoor}
                    textStyle={Layout.fontNormal} onPress={() => this._toApplyEnd()} 
                />
            </View>
        );
    }
}
