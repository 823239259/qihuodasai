import React, { Component } from 'react';
import _ from 'lodash';
import { View, Text, TextInput, StyleSheet, ViewPropTypes } from 'react-native';
import PropTypes from 'prop-types';
import { Colors } from '../global';
import { FieldUtil } from '../utils';

export default class TradeTextInput extends Component {
    static propTypes = {
        label: PropTypes.string,
        value: PropTypes.string,    // 因為TextInput..
        placeholder: PropTypes.string,
        type: PropTypes.oneOf(['int', 'float']).isRequired,
        onChangeText: PropTypes.func,
        // style
        style: ViewPropTypes.style,

        labelBackgroundColor: PropTypes.string,
        labelColor: PropTypes.string,
        labelFontSize: PropTypes.number,

        valueColor: PropTypes.string,
    }

    static defaultProps = {
        label: '手数',
        value: '',
        placeholder: '',
        type: 'int',
        // style
        labelColor: Colors.black,
        labelFontWeight: '200',
        labelFontSize: 16,
        valueColor: Colors.white
    }
    _onChangeText(text) {
        if (this.props.type === 'int') {
            return FieldUtil.getInt(text);
        }
        return FieldUtil.getFloat(text);
    }
    render() {
        let editable = true;
        if (this.props.value === null) {
            editable = false;
        }
        return (
            <View style={[styles.container, this.props.style && this.props.style]}>
                <View style={{ width: 50 }}>
                    <Text style={{ color: this.props.labelColor, fontSize: this.props.labelFontSize, fontWeight: this.props.labelFontWeight, textAlign: 'center' }}>{this.props.label}</Text>
                </View>
                <TextInput
                    keyboardType={'numeric'}
                    underlineColorAndroid={'transparent'}
                    style={{ padding: 0, flex: 1, textAlign: 'center', color: this.props.valueColor }}
                    placeholder={this.props.placeholder}
                    placeholderTextColor={Colors.white}
                    value={this.props.value}
                    editable={editable}
                    onChangeText={(text) => {
                            const newText = this._onChangeText(text);
                            if (newText !== null) {
                                this.props.onChangeText(newText);
                            }
                        }
                    }
                />
            </View>
        );
    }
}
const styles = StyleSheet.create({
    container: {
        flex: 1, 
        flexDirection: 'row', 
        justifyContent: 'space-between', 
        alignItems: 'center', 
        marginHorizontal: 2
    }
});
