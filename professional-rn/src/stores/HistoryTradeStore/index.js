/**
 * 历史成交
 */
import { observable, action, computed, autorun } from 'mobx';
import _ from 'lodash';
import { Enum, Config, Variables } from '../../global';
import HistoryTrade from './models/HistoryTrade';
import { Logger, DateUtil } from '../../utils';

export default class HistoryTradeStore {
    tradeSend = null;

    constructor(tradeSend) {
        this.logger = new Logger(HistoryTradeStore);
        this.tradeSend = tradeSend;
        this.reset();
        this.resetRadio();
        // autorun(() => {
        //     this.logger.trace(`balanceText: ${this.balanceText}, canUseText: ${this.canUseText}, totalFloatProfit: ${this.totalFloatProfit} riskText: ${this.riskText}, deposit: ${this.deposit}`);
        // });
    }
    radioOptions = [
        { label: Enum.historyTradeType.day.text, value: Enum.historyTradeType.day.value },
        { label: Enum.historyTradeType.week.text, value: Enum.historyTradeType.week.value },
        { label: Enum.historyTradeType.month.text, value: Enum.historyTradeType.month.value }
    ];
    @observable radioValue;
    @observable radioIndex;
    @observable historyData;

    @observable isDialogVisible = false;

    @action reset() {
        this.historyData = [];
    }
    @action resetRadio() {
        this.radioValue = Enum.historyTradeType.day.value;
        this.radioIndex = 0;
    }
    @action search() {
        this.reset();
        if (this.radioValue === Enum.historyTradeType.day.value) {
            this.tradeSend.qryHistoryTrade(Variables.trade.account.value, DateUtil.getYesterday(), DateUtil.getToday());
        } else if (this.radioValue === Enum.historyTradeType.week.value) {
            this.tradeSend.qryHistoryTrade(Variables.trade.account.value, DateUtil.getLastWeek(), DateUtil.getToday());
        } else if (this.radioValue === Enum.historyTradeType.month.value) {
            this.tradeSend.qryHistoryTrade(Variables.trade.account.value, DateUtil.getLastMonth(), DateUtil.getToday());
        } 
    }
    // 其實應該全部的資料都抓到 才放在observer上render畫面，問題是這是用socket回傳 而且還是一比比回傳，我沒辦法知道要傳多久...有多少筆...
    @action manageHistoryTrade(param) {
        const productName = param.ContractCode;
        const exchangeNo = param.ExchangeNo;
        const currencyNo = param.CurrencyNo;
        const direction = param.Drection;
        const tradePrice = param.TradePrice;
        const tradeNum = param.TradeNum;
        const tradeFee = param.TradeFee;
        const tradeDateTime = param.TradeDateTime;
        const openCloseType = param.OpenCloseType;
        const historyTrade = new HistoryTrade(productName, exchangeNo, currencyNo, direction, tradePrice, tradeNum, tradeFee, tradeDateTime, openCloseType);
        
        historyTrade.setSerialNo(this.historyData.length + 1);
        this.historyData.push(historyTrade);
    }
    onRadioPress(value, index) {
        this.radioValue = value;
        this.radioIndex = index;
    }
}

