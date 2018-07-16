import React, { Component } from 'react';
import { View, Text, TouchableWithoutFeedback, FlatList } from 'react-native';
import Swipeout from 'react-native-swipeout';
import { observable, computed } from 'mobx';
import { observer } from 'mobx-react/native';
import { Colors } from '../../src/global';
import styles from '../../src/screens/QuotationScreen/Styles';
import { InputFieldEnhanced, FieldProvider } from '../../src/components';

const jsonString0 = '{"commodityName":"恒指期货","commodityNo":"HSI","contractNo":"1802","productName":"HSI1802","dotSize":0,"exchangeNo":"HKEX","miniTikeSize":1,"contractSize":50,"currencyNo":"HKD-HKFE","lastPrice":"30777","lastPriceIsUp":true,"totalVolume":177995,"changeRate":"-3.94","changeRateIsUp":true,"AskPrice1":30779,"AskPrice2":30780,"AskPrice3":30781,"AskPrice4":30782,"AskPrice5":30783,"AskQty1":1,"AskQty2":1,"AskQty3":2,"AskQty4":5,"AskQty5":2,"AveragePrice":0,"BidPrice1":30776,"BidPrice2":30775,"BidPrice3":30774,"BidPrice4":30773,"BidPrice5":30772,"BidQty1":2,"BidQty2":4,"BidQty3":2,"BidQty4":4,"BidQty5":3,"ChangeRate":-3.94494553852876,"ChangeValue":-1264,"ClosingPrice":0,"CommodityNo":"HSI","ContractNo":"1802","DateTimeStamp":"2018-02-06 11:33:30","ExchangeNo":"HKEX","HighPrice":32204,"LastPrice":30777,"LastVolume":1,"LimitDownPrice":0,"LimitUpPrice":0,"LowPrice":30712,"OpenPrice":32066,"Position":167884,"PreClosingPrice":0,"PrePosition":0,"PreSettlePrice":32041,"SettlePrice":0,"TotalAskQty":0,"TotalBidQty":0,"TotalTurnover":0,"TotalVolume":177995}';
const jsonString1 = '{"commodityName":"迷你标准普尔","commodityNo":"ES","contractNo":"1803","productName":"ES1803","dotSize":2,"exchangeNo":"CME","miniTikeSize":0.25,"contractSize":12.5,"currencyNo":"USD","lastPrice":"2558.00","lastPriceIsUp":true,"totalVolume":371481,"changeRate":"-1.91","changeRateIsUp":true,"AskPrice1":2558,"AskPrice2":2558.25,"AskPrice3":2558.5,"AskPrice4":2558.75,"AskPrice5":2559,"AskQty1":2,"AskQty2":28,"AskQty3":9,"AskQty4":6,"AskQty5":15,"AveragePrice":0,"BidPrice1":2557.75,"BidPrice2":2557.5,"BidPrice3":2557.25,"BidPrice4":2557,"BidPrice5":2556.75,"BidQty1":1,"BidQty2":7,"BidQty3":6,"BidQty4":11,"BidQty5":13,"ChangeRate":-1.905894082908303,"ChangeValue":-49.69999999999982,"ClosingPrice":0,"CommodityNo":"ES","ContractNo":"1803","DateTimeStamp":"2018-02-06 11:33:30","ExchangeNo":"CME","HighPrice":2634.75,"LastPrice":2558,"LastVolume":1,"LimitDownPrice":0,"LimitUpPrice":0,"LowPrice":2542,"OpenPrice":2594,"Position":3297660,"PreClosingPrice":0,"PrePosition":0,"PreSettlePrice":2607.7,"SettlePrice":0,"TotalAskQty":0,"TotalBidQty":0,"TotalTurnover":0,"TotalVolume":371481}';
const jsonString2 = '{"commodityName":"天然气","commodityNo":"NG","contractNo":"1803","productName":"NG1803","dotSize":3,"exchangeNo":"NYMEX","miniTikeSize":0.001,"contractSize":10,"currencyNo":"USD","lastPrice":"2.767","lastPriceIsUp":false,"totalVolume":4457,"changeRate":"0.73","changeRateIsUp":false,"AskPrice1":2.768,"AskPrice2":2.769,"AskPrice3":2.77,"AskPrice4":2.771,"AskPrice5":2.772,"AskQty1":4,"AskQty2":9,"AskQty3":5,"AskQty4":5,"AskQty5":5,"AveragePrice":0,"BidPrice1":2.767,"BidPrice2":2.766,"BidPrice3":2.765,"BidPrice4":2.764,"BidPrice5":2.763,"BidQty1":2,"BidQty2":8,"BidQty3":6,"BidQty4":5,"BidQty5":5,"ChangeRate":0.7280669821623597,"ChangeValue":0.02000000000000002,"ClosingPrice":0,"CommodityNo":"NG","ContractNo":"1803","DateTimeStamp":"2018-02-06 11:33:30","ExchangeNo":"NYMEX","HighPrice":2.782,"LastPrice":2.767,"LastVolume":1,"LimitDownPrice":0,"LimitUpPrice":0,"LowPrice":2.761,"OpenPrice":2.767,"Position":305066,"PreClosingPrice":0,"PrePosition":0,"PreSettlePrice":2.747,"SettlePrice":0,"TotalAskQty":0,"TotalBidQty":0,"TotalTurnover":0,"TotalVolume":4457}';
const { containerStyle, textStyle, centerStyle, centerVerticalStyle, borderStyle } = styles;

class TestSwipeoutStore {
    @observable data = [];
    constructor(data) {
        this.data = data;
    }
    @observable search = '';
    
    @computed get filterData() {
        return this.data.filter(d => {
            return `${d.commodityName}${d.productName}`.indexOf(this.search) > -1;
        });
    }
}
@observer
export default class TestSwipeout extends Component {
    constructor(props) {
        super(props);
        const o0 = JSON.parse(jsonString0);
        o0.sectionID = 's1';
        o0.rowID = 0;

        const o1 = JSON.parse(jsonString1);
        o1.sectionID = 's1';
        o1.rowID = 1;

        const o2 = JSON.parse(jsonString2);
        o2.sectionID = 's1';
        o2.rowID = 2;
        this.store = new TestSwipeoutStore([o0, o1, o2]);
        this.state = { sectionID: 's1', rowID: 0 };
    }
    _onPress = (item) => {
        console.log(item.productName);
    }
    _renderItem = ({ item }) => (
        <Swipeout
            close={!(this.state.sectionID === item.sectionID && this.state.rowID === item.rowID)}
            left={[
                {
                    text: '关注',
                    onPress: () => { alert('button pressed'); },
                    type: 'primary',
                }
            ]
            }
            rowID={item.rowID}
            sectionID={item.sectionID}
            autoClose={true}
            backgroundColor={Colors.quotationContentBackgroundColor}
            onOpen={(sectionID, rowID) => {
            this.setState({
                sectionID,
                rowID,
            });
            }}
        >
            <TouchableWithoutFeedback onPress={this._onPress.bind(this, item)}>
                <View style={[containerStyle, borderStyle]}>
                    <View style={centerVerticalStyle}>
                        <Text style={[textStyle]}>{item.commodityName}</Text>
                        <Text style={[textStyle, { fontSize: 11, color: Colors.quotationGreyColor }]}>{item.productName}</Text>
                    </View>
                    <View style={centerStyle}>
                        <Text style={[textStyle, { color: item.lastPriceIsUp ? Colors.green : Colors.red }]}>{item.lastPrice}</Text>
                    </View>
                    <View style={centerStyle}>
                        <Text style={[textStyle, { color: Colors.quotationGreyColor }]}>{item.totalVolume}</Text>
                    </View>
                    <View style={[centerStyle, { flex: 1, borderRadius: 5, backgroundColor: item.changeRateIsUp ? Colors.green : Colors.red, marginVertical: 10 }]}>
                        <Text style={textStyle}>{`${item.changeRate}%`}</Text>
                    </View>
                </View>
            </TouchableWithoutFeedback>
        </Swipeout>
    );
    _renderSearchInput() {
        return (
            <FieldProvider form={this.store}>
                <View style={styles.container}>
                    <InputFieldEnhanced 
                        name='search' 
                        type='string' 
                        icon='search'
                        isErrorMsgSpace={false}
                        wrapperHeight={30}
                        placeholder={'合约名称/代码'}
                    />
                </View>
            </FieldProvider>
        );
    }
    render() {
        return (
            <View style={{ flex: 1 }}>
                { this._renderSearchInput() }
                <FlatList
                    style={{ flex: 1 }}
                    keyExtractor={item => item.productName}
                    data={this.store.filterData}
                    renderItem={this._renderItem}
                />
            </View>
        );
    }
}
