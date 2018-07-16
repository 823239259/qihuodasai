import React, { Component } from 'react';
import { View, Text, StyleSheet } from 'react-native';
import PropTypes from 'prop-types';
import { ButtonCommon } from '../components';
import { Layout, Colors, FieldUnderlineStyle } from '../global';

export default class DisplayWithButtonApply extends Component {
    static propTypes = {
        label: PropTypes.string,
        text: PropTypes.oneOfType([PropTypes.number, PropTypes.string]),

        buttonText: PropTypes.string,
        onButtonPress: PropTypes.func,

        style: PropTypes.object,
        labelStyle: PropTypes.object,
        textStyle: PropTypes.object,
    }
    static defaultPropTypes = {

    }
    _renderButton() {
        const { buttonText, onButtonPress } = this.props;
        return (
            <View style={{ flex: 1, flexDirection: 'row', justifyContent: 'flex-end' }}>
                <ButtonCommon 
                    text={buttonText}
                    backgroundColor={Colors.buttonCommonTinyOnTheRightBackgroundColor}
                    color={Colors.buttonCommonTinyOnTheRightTextColor} 
                    style={{ flex: 0, padding: 10 }} 
                    onPress={onButtonPress.bind(this)}
                />
            </View>
        );
    }
    render() {
        return (
            <View style={[styles.container, this.props.style && this.props.style]}>
                <View style={FieldUnderlineStyle.labelContainer}>
                    <Text style={[styles.labelStyle, this.props.labelStyle && this.props.labelStyle]}>{this.props.label}</Text>
                </View>
                <Text style={[styles.textStyle, this.props.textStyle && this.props.textStyle]}>{this.props.text}</Text>
                {this.props.buttonText && this._renderButton()}
            </View>
        );
    }
}
const styles = StyleSheet.create({
    container: {
        flexDirection: 'row',
        borderBottomWidth: StyleSheet.hairlineWidth,
        borderColor: Colors.grey,
        height: 50,
        alignItems: 'center',
        marginHorizontal: Layout.applyTradeChooseDepositInfoRowPadding
    },
    labelStyle: {
        color: Colors.greyText,
    },
    textStyle: {
        color: Colors.lightBlue,
    }
});
