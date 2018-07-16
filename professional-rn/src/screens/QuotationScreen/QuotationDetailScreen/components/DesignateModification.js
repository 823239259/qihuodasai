import React, { Component } from 'react';
import { View, StyleSheet } from 'react-native';
import _ from 'lodash';
import PropTypes from 'prop-types';
import { inject, observer } from 'mobx-react/native';
import { Layout, Colors } from '../../../../global';
import { TradeTextInput } from '../../../../components';

@inject('TradeDesignateStore') @observer
export default class DesignateModification extends Component {
    static propTypes = {
        designate: PropTypes.object
    }
    constructor(props) {
        super(props);
        const { TradeDesignateStore, designate } = this.props;
        TradeDesignateStore.orderPrice = _.toString(designate.orderPrice);
        TradeDesignateStore.orderNum = _.toString(designate.orderNum);
    }
    render() {
        const { TradeDesignateStore } = this.props;
        return (
            <View style={{ flex: 1 }}>
                <TradeTextInput
                    type={'float'}
                    style={styles.tradeTextInputStyle}
                    valueColor={Colors.black}
                    label={'委託價'} 
                    value={TradeDesignateStore.orderPrice} 
                    onChangeText={(text) => TradeDesignateStore.onOrderPriceChange(text)} 
                />
                <TradeTextInput
                    type={'int'}
                    style={styles.tradeTextInputStyle}
                    valueColor={Colors.black}
                    label={'委託量'} 
                    value={TradeDesignateStore.orderNum} 
                    onChangeText={(text) => TradeDesignateStore.onOrderNumPrice(text)} 
                />
            </View>
        );
    }
}
const styles = StyleSheet.create({
    tradeTextInputStyle: {
        width: Layout.dialogWidth - (Layout.dialogContentPadding * 2), 
        marginVertical: 5,
        // borderBottomWidth: StyleSheet.hairlineWidth,
        // borderColor: Colors.black
    }
});
  
