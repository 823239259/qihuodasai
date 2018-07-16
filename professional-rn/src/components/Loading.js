/**
 * just show small ActivityIndicator
 */
import React, { Component } from 'react';
import { TouchableOpacity, StyleSheet, ActivityIndicator } from 'react-native';
import PropTypes from 'prop-types';
import { Colors } from '../global';

export default class Loading extends Component {

    static propTypes = {
        size: PropTypes.string
    }

    static defaultProps = {
        size: 'small'
    }

    render() {
        return (
            <TouchableOpacity style={[styles.container]}>
                <ActivityIndicator size={this.props.size} />
            </TouchableOpacity>
        );
    }
}
const styles = StyleSheet.create({
    container: {
        flex: 1,
        justifyContent: 'center',
        alignItems: 'center'
    }
});
