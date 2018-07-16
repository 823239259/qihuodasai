import React, { Component } from 'react';
import { View, Text, TouchableOpacity, StyleSheet } from 'react-native';
import PropTypes from 'prop-types';
import { inject, observer } from 'mobx-react/native';
import { Layout, Colors } from '../../../../global';

@inject('ApplyTradeStore') @observer
export default class ToggleDeposit extends Component {
    static propTypes = {
        text: PropTypes.number,
        width: PropTypes.number,
        height: PropTypes.number,
        onPress: PropTypes.func,
        isToggle: PropTypes.bool
    }
    static defaultProps = {
        // (Layout.screenWidth - 做外層的padding) / 4 - (自己的margin)
        width: ((Layout.screenWidth - (Layout.applyTradeChooseDepositMargin * 2)) / 4) - (Layout.applyTradeChooseDepositMargin * 2),
        height: Layout.buttonLargeHeight
    }
    _onPress() {
        this.props.ApplyTradeStore.chooseDeposit = this.props.text;
    }
    render() {
        return (
            <TouchableOpacity 
                style={[styles.container, { width: this.props.width, height: this.props.height, backgroundColor: this.props.isToggle ? Colors.boxActiveBlue : Colors.boxBlue }]}
                onPress={() => this._onPress()}
            >
                <Text style={{ color: Colors.white }}>{`¥ ${this.props.text}`}</Text>
            </TouchableOpacity>
        );
    }
}
const styles = StyleSheet.create({
    container: {
        ...Layout.center,
        margin: Layout.applyTradeChooseDepositMargin, 
        borderRadius: 5
    }
});
