import React, { Component } from 'react';
import { TextInput, Text, View, StyleSheet } from 'react-native';
import { inject, observer } from 'mobx-react/native';
import PropTypes from 'prop-types';
import { Colors, Config, Variables, Layout, Enum } from '../../../../global';
import { ButtonCommon, Dialog } from '../../../../components';
import { Logger, I18n } from '../../../../utils';

@inject('TradeNumStore') @observer
export default class TradeNum extends Component {

    static propTypes = {
        text: PropTypes.string,
        backgroundColor: PropTypes.string,
        color: PropTypes.string,
        onPress: PropTypes.func,
        showModal: PropTypes.func
    }

    static defaultProps = {
        backgroundColor: Colors.info,
        color: Colors.white
    }

    constructor(props) {
        super(props);
        this.logger = new Logger(TradeNum);
    }
    // 買賣的意思是：買漲/跌．差別只在direction
    _onBuyPress(direction) {
        if (!Variables.trade.isLogged) {
            this.props.showModal();
            return;
        }
        this.props.TradeNumStore.toBuy(direction);
    }
    _renderTradeSubmitDialogContent() {
        const { product, num, direction } = this.props.TradeNumStore;
        let title = '合约';
        if (product) {
            title = product.productName;
        }
        return (
            <View style={{ alignItems: 'center' }}>
                <Text>{`【 ${title} 】`}</Text>
                <Text>{'价格【市价】'}</Text>
                <Text>{`手数【 ${num} 】`}</Text>
                <Text>{`方向【 ${direction ? direction.display : ''} 】`}</Text>
            </View>
        );
    }
    render() {
        const { TradeNumStore } = this.props;
        return (
            <View style={styles.container}>
                <View style={{ flex: 1, flexDirection: 'row', paddingHorizontal: 10, paddingBottom: 10 }}>
                    <View style={{ flex: 1, flexDirection: 'row', backgroundColor: Colors.black, paddingHorizontal: 20, justifyContent: 'space-between', alignItems: 'center', borderRadius: 10 }}>
                        <View style={{ width: Layout.tradeNumLabelWidth }}>
                            <Text style={{ color: Colors.tradeNumLabelColor }}>{'手数'}</Text>
                        </View>
                        <TextInput
                            keyboardType={'numeric'}
                            underlineColorAndroid={'transparent'}
                            style={{ padding: 0, flex: 1, textAlign: 'center', color: Colors.white }}
                            placeholder={'输入手数'}
                            placeholderTextColor={Colors.tradeNumLabelColor}
                            value={TradeNumStore.num}
                            onChangeText={(text) => TradeNumStore.onNumChange(text)}
                        />
                        <View style={{ width: Layout.tradeNumLabelWidth, alignItems: 'flex-end' }}>
                            <Text style={{ color: Colors.tradeNumLabelColor }}>{'(价格: 市价)'}</Text>
                        </View>
                    </View>
                </View>
                <View style={styles.bottomButtonStyle}>
                    <ButtonCommon text={'买入'} param={Enum.direction[0]} isBorderRadius={false} backgroundColor={Colors.red} onPress={(direction) => this._onBuyPress(direction)} />
                    <ButtonCommon text={'卖出'} param={Enum.direction[1]} isBorderRadius={false} backgroundColor={Colors.green} onPress={(direction) => this._onBuyPress(direction)} />
                </View>
                <Dialog 
                    visible={TradeNumStore.isTradeSubmitDialogVisible}
                    header={'确认提交订单'}
                    height={Layout.dialogHeight}
                    renderContent={() => this._renderTradeSubmitDialogContent()}
                    onConfirm={() => TradeNumStore.confirmTradeSubmitDialog()}
                    onCancel={() => TradeNumStore.cancelTradeSubmitDialog()}
                />
            </View>
        );
    }
}
const styles = StyleSheet.create({
    container: {
        height: 100,
        justifyContent: 'center',
        alignItems: 'center'
    },
    bottomButtonStyle: {
        height: Layout.buttonLargeHeight,
        flexDirection: 'row'
    }
});
