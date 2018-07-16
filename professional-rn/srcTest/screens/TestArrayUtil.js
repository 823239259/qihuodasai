import React, { Component } from 'react';
import { View, Text, ScrollView } from 'react-native';
import { ArrayUtil } from '../../src/utils';
import Row from './components/Row';

export default class TestArrayUtil extends Component {
    constructor(props) {
        super(props);
        this.arr = [5, 12, 8, 130, 44, 25];
        // COND_20180126105042030, COND_20180126122723134, COND_20180126123406137
        this.arrObject = JSON.parse('[{"productName":"CL1803","conditionNo":"COND_20180126105042030","status":0,"conditionType":0,"compareType":0,"triggerPrice":67,"triggerTime":"0000-00-00 00:00:00","additionFlag":0,"additionType":99,"additionPrice":65.4,"direction":0,"orderType":1,"num":1,"insertTime":"2018-01-26 10:50:42","statusMsg":"运行中","expiration":"当日有效"},{"productName":"CL1803","conditionNo":"COND_20180126122723134","status":0,"conditionType":0,"compareType":0,"triggerPrice":70,"triggerTime":"0000-00-00 00:00:00","additionFlag":0,"additionType":99,"additionPrice":65.4,"direction":0,"orderType":1,"num":1,"insertTime":"2018-01-26 12:27:22","statusMsg":"运行中","expiration":"当日有效"},{"productName":"CL1803","conditionNo":"COND_20180126123406137","status":0,"conditionType":0,"compareType":0,"triggerPrice":69,"triggerTime":"","additionFlag":0,"additionType":99,"additionPrice":65.41,"direction":0,"orderType":1,"num":1,"insertTime":"2018-01-26 12:34:06","statusMsg":"运行中","expiration":"当日有效"}]');
        console.log(this.arrObject);
    }
    remove = () => {
        ArrayUtil.remove(this.arr, (el) => { return el > 13; });
        console.log(this.arr);
    }
    removeOne = () => {
        ArrayUtil.removeOne(this.arr, (el) => { return el > 13; });
        console.log(this.arr);
    }
    removeObject = () => {
        ArrayUtil.remove(this.arrObject, (o) => { return o.conditionNo === 'COND_20180126122723134'; });
        console.log(this.arrObject);
    }
    removeOneObject = () => {
        ArrayUtil.removeOne(this.arrObject, (o) => { return o.conditionNo === 'COND_20180126122723134'; });
        console.log(this.arrObject);
    }
    render() {
        return (
            <View style={{ flex: 1 }}>
                <ScrollView>
                    <Row title={'primitive remove'} onPress={this.remove} />
                    <Row title={'primitive removeOne'} onPress={this.removeOne} />
                    <Row title={'object remove'} onPress={this.removeObject} />
                    <Row title={'object removeOne'} onPress={this.removeOneObject} />
                </ScrollView>
            </View>
        );
    }
}
