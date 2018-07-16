import React, { Component } from 'react';
import { Text, View, StyleSheet } from 'react-native';
import { inject, observer } from 'mobx-react/native';
import PropTypes from 'prop-types';
import { Colors, Config, Layout, Enum } from '../../../../global';
import { ButtonCommon, Dialog } from '../../../../components';
import { Logger, I18n } from '../../../../utils';

@inject('TradeOptionStore') @observer
export default class TradeOptionButton extends Component {

    static propTypes = {

    }

    static defaultProps = {

    }

    constructor(props) {
        super(props);
        this.logger = new Logger(TradeOptionButton);
    }
    // 買賣的意思是：買漲/跌．差別只在direction
    _onBuyPress(direction) {
        this.props.TradeOptionStore.toBuy(direction);
    }
    _renderTradeSubmitDialogContent() {
        const { product, num, direction, radioValue, price } = this.props.TradeOptionStore;
        let priceMsg = '价格【市价】';
        if (radioValue === Enum.priceType.limit.value) {
            priceMsg = `价格【限价】【${price}】`;
        }
        return (
            <View style={{ alignItems: 'center' }}>
                <Text>{`【 ${product.productName} 】`}</Text>
                <Text>{priceMsg}</Text>
                <Text>{`手数【 ${num} 】`}</Text>
                <Text>{`方向【 ${direction ? direction.display : ''} 】`}</Text>
            </View>
        );
    }
    render() {
        const { TradeOptionStore } = this.props;
        return (
                <View style={styles.bottomButtonStyle}>
                    <ButtonCommon text={'买入'} param={Enum.direction[0]} isBorderRadius={false} backgroundColor={Colors.red} onPress={(direction) => this._onBuyPress(direction)} />
                    <ButtonCommon text={'卖出'} param={Enum.direction[1]} isBorderRadius={false} backgroundColor={Colors.green} onPress={(direction) => this._onBuyPress(direction)} />
                    <Dialog 
                        visible={TradeOptionStore.isTradeSubmitDialogVisible}
                        header={'确认提交订单'}
                        height={200}
                        renderContent={() => this._renderTradeSubmitDialogContent()}
                        onConfirm={() => TradeOptionStore.confirmTradeSubmitDialog()}
                        onCancel={() => TradeOptionStore.cancelTradeSubmitDialog()}
                    />
                </View>
        );
    }
}
const styles = StyleSheet.create({
    bottomButtonStyle: {
        height: Layout.buttonLargeHeight,
        flexDirection: 'row'
    }
});
