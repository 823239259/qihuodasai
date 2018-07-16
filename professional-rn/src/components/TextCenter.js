import React, { Component } from 'react';
import { View, Text } from 'react-native';
import PropTypes from 'prop-types';
import { Colors } from '../global';

export default class TextCenter extends Component {
    static propTypes = {
        text: PropTypes.string.isRequired
    }
    render() {
        return (
            <View style={{ flex: 1, justifyContent: 'center', alignItems: 'center' }}>
                <Text style={{ textAlign: 'center', color: Colors.white }}>{this.props.text}</Text>
            </View>
        );
    }
}
