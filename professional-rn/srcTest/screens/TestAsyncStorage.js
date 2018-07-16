import React, { Component } from 'react';
import { View, Text, TouchableOpacity, AsyncStorage } from 'react-native';
import { Variables } from '../../src/global';
import { PromiseTestUtil } from '../../src/utils';

export default class TestAsyncStorage extends Component {
    constructor(props) {
        super(props);
        this.promiseTestUtil = new PromiseTestUtil();
    }
    onGetData = () => {
        // alert(this.getData());
    }
    onSetData = () => {
        this.setData();
    }
    async getData() {
        // try {
            const value = this.promiseTestUtil.resolveAfter3Seconds(3);
            return value;
        //     alert(`getData: ${value}`);
        //     if (value !== null) {
        //         // We have data!!
        //         alert(`getData: ${value}`);
        //     }
        // } catch (error) {
        //     // Error retrieving data
        //     console.log(error);
        // }
    }
    async setData() {
        try {
            await AsyncStorage.setItem('test', 123);
            alert('setData');
        } catch (error) {
            // Error saving data
        }
    }
    render() {
        return (
            <View>
                <TouchableOpacity onPress={this.onGetData}>
                    <Text>onGetData</Text>
                </TouchableOpacity>
                <TouchableOpacity onPress={this.onSetData}>
                    <Text>onSetData</Text>
                </TouchableOpacity>
            </View>
        );
    }
}
