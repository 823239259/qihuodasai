import React, { Component } from 'react';
import { View, Text, StyleSheet } from 'react-native';
import PropTypes from 'prop-types';
import { Layout, Colors } from '../../../../global';

export default class FiveMarketRow extends Component {
    static propTypes = {
        data: PropTypes.object.isRequired,
        index: PropTypes.number,
        lastIndex: PropTypes.number
    }
    _getRowStyle() {
        const handicapRowStyle = [Layout.handicapRowStyle, { borderColor: Colors.handicapRowBottomColor }];
        if (this.props.lastIndex === this.props.index) {
            handicapRowStyle.push({ ...Layout.handicapRowBottomStyle });
        }
        return handicapRowStyle;
    }
    render() {
        const { data } = this.props;
        return (
            <View style={this._getRowStyle()}>
                <Text style={[Layout.handicapContentTextStyle, { color: Colors.handicapTextColor }, { flex: 2, textAlign: 'right' }]}>{data.text}</Text>
                <Text style={[Layout.handicapContentTextStyle, { color: Colors.handicapTextColor }, { flex: 5, textAlign: 'center', color: data.color }]}>{data.price}</Text>
                <Text style={[Layout.handicapContentTextStyle, { color: Colors.handicapTextColor }, { flex: 2 }]}>{data.qty}</Text>
            </View>
        );
    }
}
