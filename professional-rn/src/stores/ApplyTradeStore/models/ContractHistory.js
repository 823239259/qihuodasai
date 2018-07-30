import { observable } from 'mobx';
import _ from 'lodash';

export default class ContractHistory {
    @observable index;      // 序号
    @observable tradeDate;  // 成交日期
    @observable userNo;     // 客户号
    @observable currencyNo; // 币种
    @observable exchangeNo; // 交易所
    @observable commodityNo;// 品种
    @observable buyNum;     // 买
    @observable sellNum;    // 卖
    @observable tradePrice; // 成交价
    @observable free;       // 手续费
    @observable openCloseType;

    constructor({ tradeDate, userNo, currencyNo, exchangeNo, commodityNo, buyNum, sellNum, tradePrice, free, openCloseType }, index) {
        this.index = index;
        this.tradeDate = tradeDate;
        this.userNo = userNo;
        this.currencyNo = currencyNo;
        this.exchangeNo = exchangeNo;
        this.commodityNo = commodityNo;
        this.buyNum = buyNum;
        this.sellNum = sellNum;
        this.tradePrice = _.toNumber((parseFloat(tradePrice).toFixed(2)));
        this.free = _.toNumber((parseFloat(free).toFixed(2)));

        switch(openCloseType){
            case 0 :
                this.openCloseType = 'N/A';
                break;
            case 1 :
                this.openCloseType = '开仓';
                break;
            case 2 :
                this.openCloseType = '平仓';
                break;
            case 3 :
                this.openCloseType = '平今';
                break;
            default:
                this.openCloseType = 'N/A';
        }
    }
}
