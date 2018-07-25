/*
    盤口: HandicapView: handicap/five
    最新交易: TradeLastView

    為了保留後面的0，不轉成number
    _.toNumber(this.data.AskPrice1.toFixed(this.dotSize)); -> this.data.AskPrice1.toFixed(this.dotSize)
*/

import { observable, action, computed, comparer } from 'mobx';
import _ from 'lodash';
import { Colors, Variables, Enum } from '../../global';
import { Logger } from '../../utils';

let isActive = false;
export default class HandicapTradeStore {
    logger = null;
    
    constructor() {
        this.reset();
        this.logger = new Logger(HandicapTradeStore);
    }
    dotSize = null;
    preSettlePrice = null; //昨日結算價
    productName = null;    //判斷欄位設定寬度

    @observable data = {};

    setIsActive(active) {
        isActive = active;
    }
    @action reset() {
        this.data = {};
        this.dotSize = null;
        this.preSettlePrice = null;
        this.productName = null;
    }
    @action start(product) {
        this.data = product;
        this.productName = product.productName;
        this.dotSize = product.dotSize;
        this.preSettlePrice = product.pre_settle;
    }
    @action update(param, product) {
        this.data = param;
        this.preSettlePrice = param.PreSettlePrice;
        this.dotSize || (this.dotSize = product.dotSize);
        this.productName || (this.productName = product.productName);
    } 
    // TradeLastView
    // 卖
    @computed get buyPrices() {
        if (_.isEmpty(this.data) || isNaN(this.data.ask[0][0])) {
            return 0;
        }
        return this.data.ask[0][0].toFixed(this.dotSize);   
    }
    @computed get buyPricesNumber() {
        if (_.isEmpty(this.data)) {
            return 0;
        }
        return this.data.ask[0][1];
    }
    @computed get buyPricesColor() {
        return Colors.getColorText(this.data.ask[0][0] - this.data.pre_settle);
    }
    // 买
    @computed get sellPrices() {
        if (_.isEmpty(this.data) || isNaN(this.data.bid[0][0])) {
            return 0;
        }
        return this.data.bid[0][0].toFixed(this.dotSize);   // 為了保留後面的0，不轉成number
    }
    @computed get sellPricesNumber() {
        if (_.isEmpty(this.data)) {
            return 0;
        }
        return this.data.bid[0][1];
    }
    @computed get sellPricesColor() {
        return Colors.getColorText(this.data.bid[0][0] - this.data.pre_settle);
    }
    // 成交量
    @computed get volumePricesNumber() {
        if (_.isEmpty(this.data)) {
            return 0;
        }
        return this.data.volume;
    }
    // 右邊的大圖 - 最新價
    // fresh
    @computed get freshPrices() {
        if (_.isEmpty(this.data) || isNaN(this.data.last)) {
            return 0;
        }
        return this.data.last.toFixed(this.dotSize);
    }
    @computed get freshPricesColor() {
        return Colors.getColorText(this.data.last - this.data.pre_settle);
    }
    // change value
    @computed get changeValue() {
        let changeValue;
        if (_.isEmpty(this.data) || isNaN(this.data.change_value)) {
            changeValue = 0;
        } else {
            changeValue = this.data.change_value;
        }
        return {
            content: `${changeValue.toFixed(this.dotSize)}`,
            color: Colors.getColorText(changeValue)
        };
    }
    // change rate
    @computed get changeRate() {
        let changeRate;
        if (_.isEmpty(this.data) || isNaN(this.data.change_rate)) {
            changeRate = 0;
        } else {
            changeRate = this.data.change_rate;
        }
        return {
            content: `${changeRate.toFixed(2)}%`,
            color: Colors.getColorText(changeRate)
        };
    }
    // end TradeLastView

    // handicap
    // 最新
    @computed.equals((old, new_) => {
        if (!isActive) {
            return true;
        }
        return comparer.default(old, new_);
    }) get pkLastPrice() {
        return this.getContentColor('last');//最新价
    }
    // 開盤
    @computed.equals((old, new_) => {
        if (!isActive) {
            return true;
        }
        return comparer.default(old, new_);
    }) get pkOpenPrice() {
        return this.getContentColor('open');//开仓价
    }
    // 最高
    @computed.equals((old, new_) => {
        if (!isActive) {
            return true;
        }
        return comparer.default(old, new_);
    }) get pkHightPrice() {
        return this.getContentColor('high');//最高价
    }
    // 最低
    @computed.equals((old, new_) => {
        if (!isActive) {
            return true;
        }
        return comparer.default(old, new_);
    }) get pkLowPrice() {
        return this.getContentColor('low');//最低价
    }
    // 漲跌
    @computed.equals((old, new_) => {
        if (!isActive) {
            return true;
        }
        return comparer.default(old, new_);
    }) get pkzd() {
        let pkzd;
        let content;
        if (_.isEmpty(this.data) || isNaN(this.data.change_value) || isNaN(this.data.change_rate)) {
            pkzd = 0;
            content = '';
        } else {
            pkzd = this.data.change_value;
            content = `${this.data.change_value.toFixed(this.dotSize)}/${this.data.change_rate.toFixed(2)}%`;
        }
        return {
            content,
            color: Colors.getColorText(pkzd)
        };
    }
    // 成交量
    @computed.equals((old, new_) => {
        if (!isActive) {
            return true;
        }
        return comparer.default(old, new_);
    }) get pktrademl() {
        if (_.isEmpty(this.data)) {
            return 0;
        }
        return this.data.volume;
    }
    // 持倉量
    @computed.equals((old, new_) => {
        if (!isActive) {
            return true;
        }
        return comparer.default(old, new_);
    }) get pkccml() {
        if (_.isEmpty(this.data)) {
            return 0;
        }
        return this.data.position;
    }
    // 昨結
    @computed.equals((old, new_) => {
        if (!isActive) {
            return true;
        }
        return comparer.default(old, new_);
    }) get pkzj() {
        if (_.isEmpty(this.data) || isNaN(this.data.pre_settle)) {
            return 0;
        }
        return this.data.pre_settle.toFixed(this.dotSize);
    }
    // end handicap

    // five market
    @computed.equals((old, new_) => {
        if (!isActive) {
            return true;
        }
        return comparer.default(old, new_);
    }) get bid1() {
        return this.getFiveMarket('bid', 0);
    }
    @computed.equals((old, new_) => {
        if (!isActive) {
            return true;
        }
        return comparer.default(old, new_);
    }) get bid2() {
        return this.getFiveMarket('bid', 1);
    }
    @computed.equals((old, new_) => {
        if (!isActive) {
            return true;
        }
        return comparer.default(old, new_);
    }) get bid3() {
        return this.getFiveMarket('bid', 2);
    }
    @computed.equals((old, new_) => {
        if (!isActive) {
            return true;
        }
        return comparer.default(old, new_);
    }) get bid4() {
        return this.getFiveMarket('bid', 3);
    }
    @computed.equals((old, new_) => {
        if (!isActive) {
            return true;
        }
        return comparer.default(old, new_);
    }) get bid5() {
        return this.getFiveMarket('bid', 4);
    }
    @computed.equals((old, new_) => {
        if (!isActive) {
            return true;
        }
        return comparer.default(old, new_);
    }) get ask1() {
        return this.getFiveMarket('ask', 0);
    }
    @computed.equals((old, new_) => {
        if (!isActive) {
            return true;
        }
        return comparer.default(old, new_);
    }) get ask2() {
        return this.getFiveMarket('ask', 1);
    }
    @computed.equals((old, new_) => {
        if (!isActive) {
            return true;
        }
        return comparer.default(old, new_);
    }) get ask3() {
        return this.getFiveMarket('ask', 2);
    }
    @computed.equals((old, new_) => {
        if (!isActive) {
            return true;
        }
        return comparer.default(old, new_);
    }) get ask4() {
        return this.getFiveMarket('ask', 3);
    }
    @computed.equals((old, new_) => {
        if (!isActive) {
            return true;
        }
        return comparer.default(old, new_);
    }) get ask5() {
        return this.getFiveMarket('ask', 4);
    }
    getFiveMarket(AskBid, level) {
        
        let body;
        let tempPrice;
        let qty;
        if (_.isEmpty(this.data)) {
            tempPrice = 0;
        } else {
            switch(AskBid){
                case 'ask':
                body = this.data.ask;
                break;
                case 'bid':
                body = this.data.bid;
            }
            if (body[level]){
                tempPrice = body[level][0];
                qty = body[level][1];
            }else{
                tempPrice = body[0][0] // to do ...国际五档数据显示 , 如果(回推5挡不全时) 待处理
                qty = body[0][1]
            }
            // console.log(body);
            // console.log(qty);
        }
        return {
            price: tempPrice.toFixed(this.dotSize),
            color: Colors.getColorText(tempPrice - this.preSettlePrice),
            qty: qty
        };
    }
    getContentColor(name) {
        let temp;
        if (_.isEmpty(this.data)) {
            temp = 0;
        } else {
            temp = this.data[name];
        }
        return {
            content: temp.toFixed(this.dotSize),
            color: Colors.getColorText(temp - this.data.pre_settle)
        };
    }
}

