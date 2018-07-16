/**
 * Android 在 debug模式下 setTimout(); 無效
 */
import React, { Component } from 'react';
import { View, Text, TouchableOpacity } from 'react-native';
import { PromiseTestUtil } from '../../src/utils';

export default class TestPromise extends Component {
    constructor(props) {
        super(props);
        this.promiseTestUtil = new PromiseTestUtil();
        // this.promiseTestUtil.printSecond();
    }
    onAddAsync = () => {
        this.promiseTestUtil.addAsync(5).then((total) => {
            alert(total);
        });
    }
    async onAddAsync2() {
        const value = await this.promiseTestUtil.addAsync(5);
        alert(value);
    }
    onAddPromise = () => {
        this.promiseTestUtil.addPromise(5).then((total) => {
            alert(total);
        });
    }
    render() {
        return (
            <View>
                <TouchableOpacity onPress={() => this.onAddAsync2()}>
                    <Text>addAsync</Text>
                </TouchableOpacity>
                <TouchableOpacity onPress={this.onAddPromise}>
                    <Text>addPromise</Text>
                </TouchableOpacity>
            </View>
        );
    }
}
