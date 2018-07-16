import React, { Component } from 'react';
import { View, Text } from 'react-native';
import { inject } from 'mobx-react/native';
import styles from './Styles';
import { TouchableSort } from '../../components';
import { Enum } from '../../global';
import { I18n } from '../../utils';

@inject('QuotationStore')
export default class HeaderSort extends Component {
    constructor(props) {
        super(props);
        this.state = { activeName: null };
    }
    _onPress = (o) => {
        this.setState({ activeName: o.name });
        this.props.QuotationStore.sort(o.name, o.count);
    }
    render() {
        const { containerHeaderStyle, headerFirstTextStyle, headerTextStyle, centerStyle, borderStyle, centerVerticalStyle } = styles;
        return (
            <View style={[containerHeaderStyle, borderStyle]}>
            <View style={centerVerticalStyle}>
                <Text style={headerFirstTextStyle}>{I18n.term('contract_name')}</Text>
            </View>
            <TouchableSort 
                style={centerStyle} 
                textStyle={headerTextStyle}
                name={Enum.quotationHeader.latest}
                text={I18n.term('latest_price')}
                onPress={this._onPress}
                activeName={this.state.activeName}
            />
            <TouchableSort 
                style={centerStyle} 
                textStyle={headerTextStyle} 
                name={Enum.quotationHeader.total}
                text={I18n.term('deal_total')}
                onPress={this._onPress}
                activeName={this.state.activeName}
            />
            <TouchableSort 
                style={centerStyle} 
                textStyle={headerTextStyle} 
                name={Enum.quotationHeader.raise}
                text={I18n.term('raise_fall_num')}
                onPress={this._onPress}
                activeName={this.state.activeName}
            />
            </View>
      );
    }
}
