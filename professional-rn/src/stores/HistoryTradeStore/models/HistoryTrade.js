/**
 *  历史成交记录
 *  不會一直更新，都不用@observable
 */
import { observable, computed, action } from 'mobx';
import _ from 'lodash';
import { Enum, Colors } from '../../../global';
import { Logger } from '../../../utils';

export default class HistoryTrade {
    serialNo;                    // 序号
    productName;                 // 合约代码
    exchangeNo;                  // 交易所
    currencyNo;                  // 币种
    @observable direction;       // 买卖
    tradePrice;                  // 成交价
    tradeNum;                    // 成交量
    tradeFee;                    // 手续费
    tradeDateTime;               // 成交时间
    openCloseType;

    @computed get directionText() {
        return Enum.direction[this.direction].text;
    }
    @computed get directionColor() {
        return Enum.direction[this.direction].color;
    }

    constructor(productName, exchangeNo, currencyNo, direction, tradePrice, tradeNum, tradeFee, tradeDateTime, openCloseType) {
        this.logger = new Logger(HistoryTrade);
    
        this.productName = productName;
        this.exchangeNo = exchangeNo;
        this.currencyNo = currencyNo;
        this.direction = direction;
        this.tradePrice = tradePrice;
        this.tradeNum = tradeNum;
        this.tradeFee = tradeFee;
        this.tradeDateTime = tradeDateTime;
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
    setSerialNo(serialNo) {
        this.serialNo = serialNo;
    }
}
