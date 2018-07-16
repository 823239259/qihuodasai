import React, { Component } from 'react';
import { View, Text, TouchableOpacity } from 'react-native';

export default class TestFatArrow extends Component {
    render() {
        return (
            <View>
                <TouchableOpacity onPress={() => this.onAddAsync2()}>
                    <Text>TestFatArrow</Text>
                </TouchableOpacity>
            </View>
        );
    }
}
