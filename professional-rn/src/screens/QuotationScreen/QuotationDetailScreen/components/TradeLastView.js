import React, { Component } from 'react';
import { View, Text, StyleSheet } from 'react-native';
import { inject, observer } from 'mobx-react/native';
import { TextStyle, Colors, Layout } from '../../../../global';
import { I18n } from '../../../../utils';

@inject('HandicapTradeStore') @observer
export default class TradeLastView extends Component {

    render() {
        const { HandicapTradeStore } = this.props;

        return (
            <View style={styles.container}>
                <View style={styles.content}>
                    <View style={styles.contentLeft}>
                        <View style={styles.contentLeftInner}>
                            <View style={styles.row}>
                                <Text style={[Layout.tradeLastTextStyle, { color: Colors.tradeLastViewContentTextColor }]}>{I18n.term('sell')}</Text>
                                <Text style={[Layout.tradeLastTextStyle, { color: Colors.tradeLastViewContentTextColor }, { color: HandicapTradeStore.buyPricesColor }]}>{HandicapTradeStore.buyPrices}</Text>
                                <View style={{ width: 30 }}>
                                    <Text style={[Layout.tradeLastTextStyle, { textAlign: 'right' }, { color: Colors.tradeLastViewContentValueTextColor }]}>{HandicapTradeStore.buyPricesNumber}</Text>
                                </View>
                            </View>
                            <View style={styles.row}>
                                <Text style={[Layout.tradeLastTextStyle, { color: Colors.tradeLastViewContentTextColor }]}>{I18n.term('buy')}</Text>
                                <Text style={[Layout.tradeLastTextStyle, { color: Colors.tradeLastViewContentTextColor }, { color: HandicapTradeStore.sellPricesColor }]}>{HandicapTradeStore.sellPrices}</Text>
                                <View style={{ width: 30 }}>
                                    <Text style={[Layout.tradeLastTextStyle, { textAlign: 'right' }, { color: Colors.tradeLastViewContentValueTextColor }]}>{HandicapTradeStore.sellPricesNumber}</Text>
                                </View>
                            </View>
                            <View style={styles.row}>
                                <Text style={[Layout.tradeLastTextStyle, { color: Colors.tradeLastViewContentTextColor }]}>{I18n.term('deal_total')}</Text>
                                <Text style={[Layout.tradeLastTextStyle, { color: Colors.tradeLastViewContentValueTextColor }]}>{HandicapTradeStore.volumePricesNumber}</Text>
                            </View>
                        </View>
                    </View>
                    <View style={styles.contentRight}>
                            <Text style={[Layout.tradeLastRightHeaderStyle, { color: HandicapTradeStore.freshPricesColor }]}>{HandicapTradeStore.freshPrices}</Text>
                            <View style={[{ flexDirection: 'row' }]}>
                                <Text style={[Layout.tradeLastTextStyle, { color: Colors.tradeLastViewContentTextColor }, { color: HandicapTradeStore.changeValue.color }]}>{`${HandicapTradeStore.changeValue.content} `}</Text>
                                <Text style={[Layout.tradeLastTextStyle, { color: Colors.tradeLastViewContentTextColor }, { color: HandicapTradeStore.changeValue.color }]}>{'/'}</Text>
                                <Text style={[Layout.tradeLastTextStyle, { color: Colors.tradeLastViewContentTextColor }, { color: HandicapTradeStore.changeRate.color }]}>{` ${HandicapTradeStore.changeRate.content}`}</Text>
                            </View>
                    </View>
                </View>
            </View>
        );
    }
}
const styles = StyleSheet.create({
    container: {
        height: Layout.tradeLastHeight,
    },
    content: {
        flex: 1,
        flexDirection: 'row'
    },
    contentLeft: {
        flex: 1,
    },
    contentLeftInner: {
        flex: 1,
        borderRightWidth: 1,
        borderColor: Colors.black,
        padding: Layout.inset
    },
    contentRight: {
        flex: 1,
        ...Layout.center,
        borderLeftWidth: 1,
        borderColor: Colors.black
    },
    // separator: {
    //     marginBottom: 5,
    //     marginTop: 10, // 因為chart的label算是浮起來的 推不過去，因此得比marginBottom多
    //     backgroundColor: Colors.separatorBackgroundColor,
    //     height: 8,
    //     borderBottomWidth: 1,
    //     borderTopWidth: 1,
    //     borderColor: Colors.separatorBorderColor
    // },
    row: {
        flexDirection: 'row',
        justifyContent: 'space-between',
        alignItems: 'center',
        flex: 1
    },
});
