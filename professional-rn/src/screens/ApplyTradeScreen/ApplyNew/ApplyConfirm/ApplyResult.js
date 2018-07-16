/**
 * 申请成功
 * futruePaySuc.html
 */
import React, { Component } from 'react';
import PropTypes from 'prop-types';
import Icon from 'react-native-vector-icons/FontAwesome';
import { View, Text, StyleSheet } from 'react-native';
import { ButtonCommon } from '../../../../components';
import { SafeAreaView } from '../../../../containments';
import { Enum, Colors, Layout, NavigatorStyle } from '../../../../global';
import { Logger } from '../../../../utils';

export default class ApplyResult extends Component {
    static propTypes = {
        businessType: PropTypes.number,
        vid: PropTypes.string,  // 操盤方案id
        stateType: PropTypes.number
    }
    constructor(props) {
        super(props);
        this.logger = new Logger(ApplyResult);
    }
    _toTradeDetail() {
        this.logger.trace(`申請成功後，進入操盤明細，可點擊立即操盤 - vid: ${this.props.vid}`);
        this.props.navigator.push({
            screen: 'applyTrade.ApplyDetail',
            title: '方案',
            backButtonTitle: '',
            animationType: 'slide-horizontal',
            passProps: {
                vid: this.props.vid //方案 id - ex: ff8080815f44e207015fbed702931caf
            }
        });
    }
    _renderTradePlay() {
        return (
            <View style={styles.container}>
                <View style={styles.row}>
                    <Icon
                        name={'check-circle'} 
                        size={80} 
                        color={Colors.green}
                    />
                </View>
                <View style={styles.row}>
                    <Text style={[Layout.fontBolder, { color: Colors.lightTextColor, marginBottom: 20 }]}>{'操盘方案申请成功！'}</Text>
                    <Text style={[Layout.fontBolder, { color: Colors.greyText }]}>{'交易账户已经分配！'}</Text>
                </View>
                <View style={styles.row} />
                <ButtonCommon 
                    text={'立即去操盘'} 
                    color={Colors.white} 
                    style={{ flex: 0, height: Layout.buttonLargeHeight, backgroundColor: Colors.red, marginHorizontal: Layout.inset }} 
                    textStyle={Layout.fontNormal} onPress={() => this._toTradeDetail()} 
                />
            </View>
        );
    }
    _renderTradeInfo() {
        return (
            <View style={styles.container}>
                <View style={styles.row}>
                    <Icon
                        name={'check-circle'} 
                        size={80} 
                        color={Colors.green}
                    />
                </View>
                <View style={[styles.row]}>
                    <Text style={[Layout.fontBolder, { color: Colors.lightTextColor, marginBottom: 20 }]}>{'操盘方案申请成功！'}</Text>
                    <Text style={[Layout.fontBolder, { color: Colors.greyText }]}>{'交易账户正在排队开户中...'}</Text>
                </View>
                <View style={styles.row}>
                    <View>
                        <Text style={{ color: Colors.greyText }}>{'交易时间：系统将在30分钟内处理'}</Text>
                        <Text style={{ color: Colors.greyText }}>{'非交易时间：系统将在下个交易日开盘前处理'}</Text>
                        <Text style={{ color: Colors.greyText }}>{'开户成功系统将短信通知您'}</Text>
                    </View>
                </View>
                <ButtonCommon 
                    text={'进入方案详情'} 
                    color={Colors.white} 
                    style={{ flex: 0, height: Layout.buttonLargeHeight, backgroundColor: Colors.red, marginHorizontal: Layout.inset }} 
                    textStyle={Layout.fontNormal} onPress={() => this._toTradeDetail()} 
                />
            </View>
        );
    }
    _renderContent() {
        if (this.props.stateType === Enum.applyTradeStateType.using.value) {
            return this._renderTradePlay();
        }
        return this._renderTradeInfo(); 
    }
    render() {
        return (
            <SafeAreaView>
                {this._renderContent()}
            </SafeAreaView>
        );
    }
}
ApplyResult.navigatorStyle = NavigatorStyle.screenInnerStyle;

const styles = StyleSheet.create({
    container: {
        flex: 1
    },
    row: {
       flex: 1,
       ...Layout.center 
    }
});
