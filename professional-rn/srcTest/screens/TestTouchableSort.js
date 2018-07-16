import React, { Component } from 'react';
import { View, Text } from 'react-native';
import { Layout } from '../../src/global';
import { TouchableSort } from '../../src/components';

export default class TestTouchableSort extends Component {
    _onPress(o) {
        console.log(`text: ${o.text}, count: ${o.count}`);
    }
    render() {
        return (
            <View style={[{ flex: 1, flexDirection: 'row', }, Layout.center]}>
                <TouchableSort 
                    text={'Sort'}
                    textStyle={{ color: 'red', fontSize: 16 }}
                    onPress={this._onPress}
                />
            </View>
        );
    }
}
