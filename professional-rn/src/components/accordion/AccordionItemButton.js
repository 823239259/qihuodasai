import React, { Component } from 'react';
import { View, Text, TouchableOpacity, StyleSheet } from 'react-native';
import PropTypes from 'prop-types';
import { Layout, Colors } from '../../global';

export default class AccordionItemButton extends Component {
    static propTypes = {
        productName: PropTypes.string,
        title: PropTypes.string,
        onPress: PropTypes.func
    }
    static defaultProps = {
        title: 'title',
    }
    render() {
        return (
            <TouchableOpacity style={styles.container} onPress={() => this.props.onPress()}>
                <Text style={{ color: Colors.white }}>{this.props.title}</Text>
            </TouchableOpacity>
        );
    }
}
const styles = StyleSheet.create({
    container: {
        margin: 5, 
        width: Layout.accordionButtonWidth,
        height: Layout.accordionButtonHeight, 
        backgroundColor: Colors.accordionButtonColor,
        ...Layout.center
    }
});
