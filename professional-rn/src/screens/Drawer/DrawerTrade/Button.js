import React, { Component } from 'react';
import { TouchableOpacity, Text, StyleSheet } from 'react-native';
import PropTypes from 'prop-types';
import { Colors, Layout } from '../../../global';

export default class Button extends Component {
    static propTypes = {
        text: PropTypes.string,
        onPress: PropTypes.func
    }

    static defaultProps = {

    }
    render() {
        return (
            <TouchableOpacity 
                style={styles.container}
                onPress={() => this.props.onPress(this.props.text)}
            >
                <Text style={[Layout.fontNormal, { color: Colors.white }]}>{this.props.text}</Text>
            </TouchableOpacity>
        );
    }
}
const styles = StyleSheet.create({
    container: {
        backgroundColor: Colors.drawButtonColor,
        height: Layout.buttonLargeHeight,
        marginBottom: 10,
        ...Layout.center
    }
});
