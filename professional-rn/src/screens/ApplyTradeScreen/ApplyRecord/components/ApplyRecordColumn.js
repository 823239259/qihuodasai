import React, { Component } from 'react';
import { View, Text, StyleSheet } from 'react-native';
import PropTypes from 'prop-types';
import { Layout, Colors } from '../../../../global';

export default class ApplyRecordColumn extends Component {
    static propTypes = {
        title: PropTypes.string,
        text: PropTypes.oneOfType([PropTypes.number, PropTypes.string]),
        
        active: PropTypes.bool,

        style: PropTypes.object
    }
    render() {
        return (
            <View style={[styles.container, this.props.style && this.props.style]}>
                <Text style={[Layout.fontBold, { color: this.props.active ? Colors.white : Colors.grey }]}>{this.props.title}</Text>
                <Text style={[Layout.fontNormal, { color: this.props.active ? Colors.lightTextColor : Colors.grey }]}>{this.props.text}</Text>
            </View>
        );
    }
}
const styles = StyleSheet.create({
    container: {
        marginLeft: Layout.cardMargin,
        marginTop: Layout.cardMargin
    }
});
