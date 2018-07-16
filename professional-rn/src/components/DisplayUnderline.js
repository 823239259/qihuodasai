/**
 * 配合Inputfield + FieldWrapper(skin: underline)
 */
import React, { Component } from 'react';
import PropTypes from 'prop-types';
import { View, Text } from 'react-native';
import { Colors, FieldUnderlineStyle, Layout } from '../global';
import { ButtonCommon } from '../components';

export default class DisplayUnderline extends Component {
    static propTypes = {
        label: PropTypes.string,
        text: PropTypes.oneOfType([PropTypes.number, PropTypes.string]),
        
        isButton: PropTypes.bool,
        isButtomBorder: PropTypes.bool,
        isBottomMargin: PropTypes.bool,
        buttonText: PropTypes.string,
        onButtonPress: PropTypes.func,

        style: PropTypes.object,
        wrapperHeight: PropTypes.number,
    }
    static defaultProps = {
        isButton: false,
        isButtomBorder: true,
        isBottomMargin: true,
        wrapperHeight: Layout.buttonLargeHeight,
    }
    _renderButton() {
        return (
            <ButtonCommon 
                text={this.props.buttonText} 
                color={Colors.darkBlue} 
                style={{ flex: 0 }} 
                textStyle={Layout.fontNormal} 
                onPress={() => this.props.onButtonPress()}
            />
        );
    }
    _renderText() {
        return <Text style={{ color: Colors.greyText, fontSize: 16 }} textAlign={'right'}>{this.props.text}</Text>;
    }
    render() {
        return (
            <View style={this.props.style}>
                <View style={[FieldUnderlineStyle.containerUnderline, { flexDirection: 'row', height: this.props.wrapperHeight }, !this.props.isButtomBorder && { borderBottomWidth: 0 }]}>
                    <View style={[FieldUnderlineStyle.inputWrapper, { height: this.props.wrapperHeight }]}>
                        <View style={FieldUnderlineStyle.labelDisplayContainer}>
                            <Text style={FieldUnderlineStyle.labelStyle}>{this.props.label}</Text>
                        </View>
                    </View>
                    <View style={{ flex: 1, flexDirection: 'row', justifyContent: 'flex-end' }}>
                        { this.props.isButton ? this._renderButton() : this._renderText() }
                    </View>
                </View>
                {/* 為了跟FieldWrapper有一樣的空格 */}
                { this.props.isBottomMargin && <View style={{ ...Layout.center, height: Layout.fieldErrorHeight }} /> }
            </View>
        );
    }
}
