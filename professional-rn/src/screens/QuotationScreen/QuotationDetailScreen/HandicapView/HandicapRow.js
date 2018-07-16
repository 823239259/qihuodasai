import React, { Component } from 'react';
import { View, Text, StyleSheet } from 'react-native';
import PropTypes from 'prop-types';
import { Layout, Colors } from '../../../../global';
import { Logger } from '../../../../utils';

export default class HandicapRow extends Component {
    static propTypes = {
        data: PropTypes.object.isRequired,
        productName: PropTypes.string,
        index: PropTypes.number,
        lastIndex: PropTypes.number
    }
    constructor(props) {
        super(props);
        this.logger = new Logger(HandicapRow);
    }
    // 1.為了排好 兩/三個字，讓兩個字中間空白
    // 2.只有美銅HG1712 顯示到資料會太長
    _renderCaption() {
        const { data } = this.props;
        const stringArr = data.text.split('');
        const captionStyle = [{ flexDirection: 'row', marginLeft: Layout.handicapRowMarginLeft }];
        captionStyle.push({ flex: this.props.productName === 'HG1712' ? 1 : 2 });
        if (data.text.length === 3) {
            return (
                <View style={captionStyle}>
                    <Text style={[Layout.handicapContentTextStyle, { color: Colors.handicapTextColor }]}>{data.text}</Text>
                </View>
            );
        }
        return (
            <View style={captionStyle}>
                <Text style={[Layout.handicapContentTextStyle, { color: Colors.handicapTextColor }]}>{stringArr[0]}</Text>
                <Text style={[Layout.handicapContentTextStyle, { color: Colors.bg }]}>{'一'}</Text>
                <Text style={[Layout.handicapContentTextStyle, { color: Colors.handicapTextColor }]}>{stringArr[1]}</Text>
            </View>
        );
    }
    _getRowStyle() {
        const handicapRowStyle = [Layout.handicapRowStyle, { borderColor: Colors.handicapRowBottomColor }];
        if (this.props.lastIndex === this.props.index) {
            handicapRowStyle.push({ ...Layout.handicapRowBottomStyle });
        }
        return handicapRowStyle;
    }
    // 只有美銅HG1712 顯示到資料會太長
    render() {
        const { data } = this.props;
        return (
            <View key={data.text} style={this._getRowStyle()}>
                { this._renderCaption() }
                <Text style={[Layout.handicapContentTextStyle, { flex: this.props.productName === 'HG1712' ? 2 : 3 }, { color: data.color || Colors.greyText }]}>{data.value}</Text>
            </View>
        );
    }
}
