import React, { Component } from 'react';
import { inject, observer } from 'mobx-react/native';
import { View, Text } from 'react-native';
import RadioForm, { RadioButton, RadioButtonInput, RadioButtonLabel } from 'react-native-simple-radio-button';
import { Dialog, ButtonCommon } from '../../../components';
import { Variables, Layout, NavigatorStyle, Colors } from '../../../global';
import HistoryList from './HistoryList';
import { Logger } from '../../../utils';
import { SafeAreaView } from '../../../containments';

@inject('HistoryTradeStore') @observer 
class HistoryTradeModal extends Component {
    constructor(props) {
        super(props);
        this.props.navigator.setOnNavigatorEvent(this.onNavigatorEvent.bind(this));
        this.state = { isExclamationVisible: false };
        this.logger = new Logger(HistoryTradeModal);
    }
    onNavigatorEvent(event) { // this is the onPress handler for the two buttons together
        if (event.type === 'NavBarButtonPress') { // this is the event type for button presses
            if (event.id === Variables.icon.closeHistoryTrade.id) { // this is the same id field from the static navigatorButtons definition
                this._closeModal();
            } else if (event.id === Variables.icon.exclamationHistoryTrade.id) {
                this._showExclamation();
            }
        }
    }
    _showExclamation() {
        this.setState({ isExclamationVisible: true });
    }
    _closeModal() {
        const { HistoryTradeStore, navigator } = this.props;
        navigator.dismissModal();
        HistoryTradeStore.reset();
        HistoryTradeStore.resetRadio();
    }
    _renderOptionButton() {
        const { HistoryTradeStore } = this.props;
        return (
            <View style={{ flexDirection: 'row', height: Layout.buttonLargeHeight }}>
                <RadioForm 
                    formHorizontal={true}
                    style={[{ flex: 1, backgroundColor: Colors.historyTrade.radioBackgroundColor }, Layout.center]}
                >
                {HistoryTradeStore.radioOptions.map((obj, i) => {
                    return (
                        <RadioButton 
                            labelHorizontal={false} 
                            key={i}
                            animation={true}
                            wrapStyle={[{ flex: 1, marginTop: 10 }, Layout.center]}
                        >
                            {/*  You can set RadioButtonLabel before RadioButtonInput */}
                            <RadioButtonInput
                            obj={obj}
                            index={i}
                            isSelected={HistoryTradeStore.radioIndex === i}
                            onPress={(value, index) => HistoryTradeStore.onRadioPress(value, index)}
                            
                            buttonInnerColor={Colors.radioActiveColor}
                            buttonOuterColor={HistoryTradeStore.radioIndex === i ? Colors.radioActiveColor : Colors.radioNonActiveColor}
                            
                            buttonSize={10}
                            buttonOuterSize={20}

                            buttonStyle={{}}
                            buttonWrapStyle={{}}
                            />
                            <RadioButtonLabel
                            obj={obj}
                            index={i}
                            onPress={(value, index) => HistoryTradeStore.onRadioPress(value, index)}
                            labelStyle={{ color: Colors.greyText, fontSize: Layout.historyTradeRadioFontSize }}
                            labelWrapStyle={{}}
                            />
                        </RadioButton>
                    );
                })}
                </RadioForm>
                <ButtonCommon 
                    text={'查询'} 
                    color={Colors.white} 
                    style={{ flex: 1, height: Layout.buttonLargeHeight, backgroundColor: Colors.red }} 
                    textStyle={Layout.fontNormal} onPress={() => HistoryTradeStore.search()} 
                />
            </View>
        );
    }
    render() {
        return (
            <SafeAreaView>
                <HistoryList />
                { this._renderOptionButton() }
                <Dialog 
                    visible={this.state.isExclamationVisible}
                    content={'1.历史成交明细只可查询当日凌晨5点之前的数据\n2.当日的成交明细可到交易中心-》成交中心查询'}
                    isCancel={false}
                    height={180}
                    width={Layout.screenWidth * 0.85}
                    onConfirm={() => this.setState({ isExclamationVisible: false })}
                />
            </SafeAreaView>
        );
    }
}
HistoryTradeModal.navigatorStyle = NavigatorStyle.modalStyle;
export default HistoryTradeModal;
