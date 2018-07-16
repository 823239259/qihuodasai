import React, { Component } from 'react';
import { View } from 'react-native';
import { inject, observer } from 'mobx-react/native';
import PropTypes from 'prop-types';
import RadioForm, { RadioButton, RadioButtonInput, RadioButtonLabel } from 'react-native-simple-radio-button';
import { Colors, Layout, Enum } from '../../../../global';
import { TradeTextInput } from '../../../../components';
import { Logger, I18n } from '../../../../utils';

@inject('TradeOptionStore') @observer
export default class TradeOptionSelect extends Component {

    static propTypes = {
        text: PropTypes.string,
        backgroundColor: PropTypes.string,
        color: PropTypes.string,
        onPress: PropTypes.func,
        showModal: PropTypes.func
    }

    static defaultProps = {
        backgroundColor: Colors.info,
        color: Colors.white
    }

    constructor(props) {
        super(props);
        this.logger = new Logger(TradeOptionSelect);
    }
    _renderTextInput() {
        const { TradeOptionStore } = this.props;
        return (
            <View style={{ height: Layout.buttonHeight, flexDirection: 'row', paddingHorizontal: Layout.radioMargin, marginBottom: Layout.radioMargin }}>
                <TradeTextInput
                    type={'int'}
                    label={'手数'} 
                    value={TradeOptionStore.num}
                    onChangeText={(text) => TradeOptionStore.onNumChange(text)} 
                    style={{ backgroundColor: Colors.tradeTextInputBackgroundColor }}
                />
                <TradeTextInput 
                    type={'float'}
                    label={'价格'} 
                    value={TradeOptionStore.price}
                    placeholder={TradeOptionStore.radioValue === Enum.priceType.market.value ? '市场价' : null}
                    onChangeText={(text) => TradeOptionStore.onPriceChange(text)} 
                    style={{ backgroundColor: Colors.tradeTextInputPriceBackgroundColor }}
                />
            </View>
        );
    }
    _renderRadio() {
        const { TradeOptionStore } = this.props;
        return (
            <RadioForm 
                formHorizontal={true} 
                style={{ justifyContent: 'space-around', marginVertical: Layout.radioMargin }}
            >
            {TradeOptionStore.radioOptions.map((obj, i) => {
                return (
                    <RadioButton 
                        labelHorizontal={true} 
                        key={i}
                        animation={true}
                    >
                        {/*  You can set RadioButtonLabel before RadioButtonInput */}
                        <RadioButtonInput
                        obj={obj}
                        index={i}
                        isSelected={TradeOptionStore.radioIndex === i}
                        onPress={(value, index) => TradeOptionStore.onRadioPress(value, index)}
                        
                        buttonInnerColor={Colors.radioActiveColor}
                        buttonOuterColor={TradeOptionStore.radioIndex === i ? Colors.radioActiveColor : Colors.radioNonActiveColor}
                        
                        buttonSize={10}
                        buttonOuterSize={20}

                        buttonStyle={{}}
                        buttonWrapStyle={{ marginLeft: Layout.radioMargin }}
                        />
                        <RadioButtonLabel
                        obj={obj}
                        index={i}
                        onPress={(value, index) => TradeOptionStore.onRadioPress(value, index)}
                        labelStyle={{ fontWeight: 'bold', color: Colors.greyText, marginLeft: 10, fontSize: Layout.radioLabelFontSize }}
                        labelWrapStyle={{}}
                        />
                    </RadioButton>
                );
            })}
            </RadioForm>
        );
    }
    render() {
        return (
            <View>
                { this._renderRadio() }
                { this._renderTextInput() }
            </View>
        );
    }
}

