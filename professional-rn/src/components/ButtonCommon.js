/**
 *  Compare to Submit: Submit的用法是跟FieldProvider一起使用
 */
import React, { Component } from 'react';
import _ from 'lodash';
import { TouchableOpacity, Text, StyleSheet } from 'react-native';
import PropTypes from 'prop-types';
import { Layout, Colors, Config } from '../global';

export default class ButtonCommon extends Component {

    static propTypes = {
        param: PropTypes.object,

        style: PropTypes.object,
        
        text: PropTypes.string,
        color: PropTypes.string,
        textStyle: PropTypes.object,
        
        backgroundColor: PropTypes.string,
        isValid: PropTypes.bool,
        inActiveBackgroundColor: PropTypes.string,
        isBorderRadius: PropTypes.bool,
        onPress: PropTypes.func
    }
    static defaultProps = {
        isValid: true,
        inActiveBackgroundColor: Colors.submitBackgroundColor,
        color: Colors.white,
        isBorderRadius: true
    }
    constructor(props) {
        super(props);
        this._onPress = this._onPress.bind(this);
        this._onPressThrottled = _.throttle(this._onPress, Config.throttleTime);
    }
    _onPress() {
        if (this.props.onPress && this.props.isValid) {
            if (this.props.param) {
                this.props.onPress(this.props.param);
            } else {
                this.props.onPress();
            }
        }
    }
    _getContainerBackgroundColor() {
        if (this.props.isValid) {
            return { backgroundColor: this.props.backgroundColor && this.props.backgroundColor };
        }
        return { backgroundColor: this.props.inActiveBackgroundColor };
    }
    render() {
        return (
            <TouchableOpacity 
                style={[styles.container, { borderRadius: this.props.isBorderRadius ? 5 : 0 }, this._getContainerBackgroundColor(), this.props.style && this.props.style]}
                onPress={this._onPressThrottled}
                activeOpacity={this.props.isValid ? 0.2 : 1}
            >
                <Text style={[this.props.textStyle && this.props.textStyle, { color: this.props.color && this.props.color }]}>{this.props.text}</Text>
            </TouchableOpacity>
        );
    }
}
const styles = StyleSheet.create({
    container: {
        flex: 1,
        ...Layout.center
    }
});
