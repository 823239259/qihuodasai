import React, { Component } from 'react';
import PropTypes from 'prop-types';
import { View, Text, StyleSheet } from 'react-native';
import { Colors, Layout } from '../../../global';
import { inject, observer } from 'mobx-react/native';

@observer
export default class FundHeaderText extends Component {
    static propTypes = {
        title: PropTypes.string,
        num: PropTypes.number,
        money: PropTypes.string,    // toFixed(2)
        moneyColor: PropTypes.string
    };
    constructor(props) {
        super(props);
        this.state = { first: true, containerWidth: Layout.buttonLargeHeight };
    }
    render() {
        return (
            <View style={[styles.container]}>
                <View style={{ flexDirection: 'row' }}>
                    <Text style={{ color: Colors.greyText, fontSize: 20 }}>{this.props.title}</Text>
                    <Text style={{ color: Colors.greyText, lineHeight: 26 }}>{' - '}</Text>
                    <Text style={{ color: Colors.greyText, lineHeight: 26 }}>{`${this.props.num} 笔`}</Text>
                </View>
                <Text style={{ color: this.props.moneyColor, fontSize: 24, fontWeight: '600' }} >{this.props.money}</Text>
            </View>
        );
    }
}
const styles = StyleSheet.create({
    container: {
        flex: 1,
        height: Layout.buttonLargeHeight,
        ...Layout.center
    },
});
