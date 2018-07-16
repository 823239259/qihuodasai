import React, { Component } from 'react';
import { View, Text } from 'react-native';
import PropTypes from 'prop-types';
import { observer } from 'mobx-react/native';
import { Colors, Layout } from '../global';

@observer
export default class TradeInfoRow extends Component {
    static propTypes = {
        isLabelFilled: PropTypes.bool,
        label: PropTypes.string,
        labelColumn: PropTypes.object,

        value: PropTypes.oneOfType([PropTypes.number, PropTypes.string]),
        valueColumn: PropTypes.object,
        valueTextColor: PropTypes.string,

        num: PropTypes.oneOfType([PropTypes.number, PropTypes.string]),
        numColumn: PropTypes.object,
        numTextColor: PropTypes.string,
    }
    static defaultProps = {
        labelColumn: { flex: 1 },
        
        valueColumn: { flex: 1 },
        valueTextColor: Colors.greyText,
        
        num: undefined,
        numColumn: { flex: 1 },
        
        numTextColor: Colors.greyText,
        isLabelFilled: false
    }
    _renderLabel() {
        // 為了排好 兩/三個字，讓兩個字中間空白
        if (this.props.isLabelFilled) {
            const labelArr = this.props.label.split('');
            return (
                <View style={[{ flexDirection: 'row', justifyContent: 'center' }, this.props.labelColumn]}>
                    <Text style={{ fontSize: Layout.tradeViewFontSize, color: Colors.greyText }}>{labelArr[0]}</Text>
                    <Text style={{ fontSize: Layout.tradeViewFontSize, color: Colors.tradeViewInfoBoxBackgroundColor }}>{'一'}</Text>
                    <Text style={{ fontSize: Layout.tradeViewFontSize, color: Colors.greyText }}>{labelArr[1]}</Text>
                </View>
            );
        }
        return (
            <View style={this.props.labelColumn}>
                <Text style={{ fontSize: Layout.tradeViewFontSize, color: Colors.greyText, textAlign: 'center' }}>{this.props.label}</Text>
            </View>
        );
    }
    _renderNum() {
        if (this.props.num !== undefined) {
            return (
                <View style={this.props.numColumn}>
                    <Text style={{ fontSize: Layout.tradeViewFontSize, color: this.props.num === '一' ? Colors.tradeViewInfoBoxBackgroundColor : this.props.numTextColor, textAlign: 'center' }}>{this.props.num}</Text>
                </View>
            );
        }
    }
    render() {
        return (
            <View style={[Layout.center, Layout.tradeViewRow]}>
                { this._renderLabel() }
                <View style={this.props.valueColumn}>
                    <Text style={{ fontSize: Layout.tradeViewFontSize, color: this.props.valueTextColor ? this.props.valueTextColor : this.props.valueTextColor, textAlign: 'center' }}>{this.props.value}</Text>
                </View>
                { this._renderNum() }
            </View>
        );
    }
}
