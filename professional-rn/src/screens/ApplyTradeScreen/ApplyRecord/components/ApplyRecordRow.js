import React, { Component } from 'react';
import { View, TouchableOpacity, Text, Image, StyleSheet } from 'react-native';
import PropTypes from 'prop-types';
import { inject, observer } from 'mobx-react/native';
import { Layout, Colors, Enum } from '../../../../global';
import ApplyRecordColumn from './ApplyRecordColumn';

@inject('ApplyTradeStore') @observer
export default class ApplyRecordRow extends Component {
    static propTypes = {
        data: PropTypes.object,
        navigator: PropTypes.object
    }
    constructor(props) {
        super(props);
        this.state = { imgHeight: null, imgWidth: null };
    }
    _onPress() {
        this.props.navigator.push({
            screen: 'applyTrade.ApplyDetail',
            title: '方案',
            backButtonTitle: '',
            animationType: 'slide-horizontal',
            passProps: {
                vid: this.props.data.id //方案 id - ex: ff8080815f44e207015fbed702931caf
            }
        });
    }
    calSize = e => {
        const size = e.nativeEvent.layout.height / 2;
        this.setState({ imgHeight: size, imgWidth: size });
    }
    render() {
        const data = this.props.data;
        let active = true;
        // 操盤中 | 已結算
        if (data.stateType === Enum.applyTradeStateType.end.value) {
            active = false;
        }
        return (
            <TouchableOpacity style={styles.container} onPress={() => this._onPress()}>
                <View style={{ flex: 4 }}>
                    <View style={{ flex: 1, marginLeft: Layout.cardMargin, marginTop: Layout.cardMargin, }}>
                        <Text style={{ color: Colors.greyText }}>{data.appTime}</Text>
                    </View>
                    <View style={{ flex: 9, flexDirection: 'row' }}>
                        <View style={{ flex: 5 }}>
                            <ApplyRecordColumn active={active} title={'交易品种'} text={data.businessTypeStr} />
                            <ApplyRecordColumn active={active} title={'操盘帐户'} text={data.tranAccount} style={{ marginBottom: Layout.cardMargin }} />
                        </View>
                        <View style={{ flex: 4 }}>
                            <ApplyRecordColumn active={active} title={'总操盘资金'} text={data.traderTotal} />
                            <ApplyRecordColumn active={active} title={'亏损平仓线'} text={data.lineLoss} style={{ marginBottom: Layout.cardMargin }} />
                        </View>
                    </View>
                </View>
                <View 
                    style={{ flex: 1, flexDirection: 'row', justifyContent: 'flex-end' }}
                    onLayout={this.calSize}
                >
                    <Image
                        style={{ width: this.state.imgWidth, height: this.state.imgHeight }}
                        source={active ? require('../../../../../img/applyTrade/trading.png') : require('../../../../../img/applyTrade/settle.png')}
                        resizeMode={'contain'}
                    />
                </View>
            </TouchableOpacity>
        );
    }
}
const styles = StyleSheet.create({
    container: {
        borderRadius: 5,
        backgroundColor: Colors.cardBackgroundColor,
        flexDirection: 'row',
        marginTop: Layout.cardMargin,
        marginHorizontal: Layout.cardMargin    
    }
});
