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
        this.preSettlePrice = product.PreSettlePrice;
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
        if (_.isEmpty(this.data) || isNaN(this.data.AskPrice1)) {
            return 0;
        }
        return this.data.AskPrice1.toFixed(this.dotSize);   
    }
    @computed get buyPricesNumber() {
        if (_.isEmpty(this.data)) {
            return 0;
        }
        return this.data.AskQty1;
    }
    @computed get buyPricesColor() {
        return Colors.getColorText(this.data.AskPrice1 - this.data.PreSettlePrice);
    }
    // 买
    @computed get sellPrices() {
        if (_.isEmpty(this.data) || isNaN(this.data.BidPrice1)) {
            return 0;
        }
        return this.data.BidPrice1.toFixed(this.dotSize);   // 為了保留後面的0，不轉成number
    }
    @computed get sellPricesNumber() {
        if (_.isEmpty(this.data)) {
            return 0;
        }
        return this.data.BidQty1;
    }
    @computed get sellPricesColor() {
        return Colors.getColorText(this.data.BidPrice1 - this.data.PreSettlePrice);
    }
    // 成交量
    @computed get volumePricesNumber() {
        if (_.isEmpty(this.data)) {
            return 0;
        }
        return this.data.TotalVolume;
    }
    // 右邊的大圖 - 最新價
    // fresh
    @computed get freshPrices() {
        if (_.isEmpty(this.data) || isNaN(this.data.LastPrice)) {
            return 0;
        }
        return this.data.LastPrice.toFixed(this.dotSize);
    }
    @computed get freshPricesColor() {
        return Colors.getColorText(this.data.LastPrice - this.data.PreSettlePrice);
    }
    // change value
    @computed get changeValue() {
        let changeValue;
        if (_.isEmpty(this.data) || isNaN(this.data.ChangeValue)) {
            changeValue = 0;
        } else {
            changeValue = this.data.ChangeValue;
        }
        return {
            content: `${changeValue.toFixed(this.dotSize)}`,
            color: Colors.getColorText(changeValue)
        };
    }
    // change rate
    @computed get changeRate() {
        let changeRate;
        if (_.isEmpty(this.data) || isNaN(this.data.ChangeRate)) {
            changeRate = 0;
        } else {
            changeRate = this.data.ChangeRate;
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
        return this.getContentColor('LastPrice');//最新价
    }
    // 開盤
    @computed.equals((old, new_) => {
        if (!isActive) {
            return true;
        }
        return comparer.default(old, new_);
    }) get pkOpenPrice() {
        return this.getContentColor('OpenPrice');//开仓价
    }
    // 最高
    @computed.equals((old, new_) => {
        if (!isActive) {
            return true;
        }
        return comparer.default(old, new_);
    }) get pkHightPrice() {
        return this.getContentColor('HighPrice');//最高价
    }
    // 最低
    @computed.equals((old, new_) => {
        if (!isActive) {
            return true;
        }
        return comparer.default(old, new_);
    }) get pkLowPrice() {
        return this.getContentColor('LowPrice');//最低价
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
        if (_.isEmpty(this.data) || isNaN(this.data.ChangeValue) || isNaN(this.data.ChangeRate)) {
            pkzd = 0;
            content = '';
        } else {
            pkzd = this.data.ChangeValue;
            content = `${this.data.ChangeValue.toFixed(this.dotSize)}/${this.data.ChangeRate.toFixed(2)}%`;
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
        return this.data.TotalVolume;
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
        return this.data.Position;
    }
    // 昨結
    @computed.equals((old, new_) => {
        if (!isActive) {
            return true;
        }
        return comparer.default(old, new_);
    }) get pkzj() {
        if (_.isEmpty(this.data) || isNaN(this.data.PreSettlePrice)) {
            return 0;
        }
        return this.data.PreSettlePrice.toFixed(this.dotSize);
    }
    // end handicap

    // five market
    @computed.equals((old, new_) => {
        if (!isActive) {
            return true;
        }
        return comparer.default(old, new_);
    }) get bid1() {
        return this.getFiveMarket('BidPrice1', 'BidQty1');
    }
    @computed.equals((old, new_) => {
        if (!isActive) {
            return true;
        }
        return comparer.default(old, new_);
    }) get bid2() {
        return this.getFiveMarket('BidPrice2', 'BidQty2');
    }
    @computed.equals((old, new_) => {
        if (!isActive) {
            return true;
        }
        return comparer.default(old, new_);
    }) get bid3() {
        return this.getFiveMarket('BidPrice3', 'BidQty3');
    }
    @computed.equals((old, new_) => {
        if (!isActive) {
            return true;
        }
        return comparer.default(old, new_);
    }) get bid4() {
        return this.getFiveMarket('BidPrice4', 'BidQty4');
    }
    @computed.equals((old, new_) => {
        if (!isActive) {
            return true;
        }
        return comparer.default(old, new_);
    }) get bid5() {
        return this.getFiveMarket('BidPrice5', 'BidQty5');
    }
    @computed.equals((old, new_) => {
        if (!isActive) {
            return true;
        }
        return comparer.default(old, new_);
    }) get ask1() {
        return this.getFiveMarket('AskPrice1', 'AskQty1');
    }
    @computed.equals((old, new_) => {
        if (!isActive) {
            return true;
        }
        return comparer.default(old, new_);
    }) get ask2() {
        return this.getFiveMarket('AskPrice2', 'AskQty2');
    }
    @computed.equals((old, new_) => {
        if (!isActive) {
            return true;
        }
        return comparer.default(old, new_);
    }) get ask3() {
        return this.getFiveMarket('AskPrice3', 'AskQty3');
    }
    @computed.equals((old, new_) => {
        if (!isActive) {
            return true;
        }
        return comparer.default(old, new_);
    }) get ask4() {
        return this.getFiveMarket('AskPrice4', 'AskQty4');
    }
    @computed.equals((old, new_) => {
        if (!isActive) {
            return true;
        }
        return comparer.default(old, new_);
    }) get ask5() {
        return this.getFiveMarket('AskPrice5', 'AskQty5');
    }
    getFiveMarket(price, qty) {
        let tempPrice;
        if (_.isEmpty(this.data)) {
            tempPrice = 0;
        } else {
            tempPrice = this.data[price];
        }
        return {
            price: tempPrice.toFixed(this.dotSize),
            color: Colors.getColorText(tempPrice - this.preSettlePrice),
            qty: this.data[qty]
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
            color: Colors.getColorText(temp - this.data.PreSettlePrice)
        };
    }
}

