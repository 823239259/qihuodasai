/**
 *  成交
 */
import { observable, computed } from 'mobx';

export default class Deal {
    productName;   //合約名稱
    @observable direction;      //買賣
    @observable tradePrice;     //成交價
    @observable tradeNum;       //成交量
    openCloseType;
    tradeTime;      //成交時間
    // 不顯示
    orderId;        //

    @computed get directionText() {
        return this.direction.display;
    }
    @computed get directionColor() {
        return this.direction.color;
    }

    constructor(productName, direction, tradePrice, tradeNum, tradeTime, orderId, openCloseType) {
        this.productName = productName;
        this.direction = direction;
        this.tradePrice = tradePrice;
        this.tradeNum = tradeNum;
        this.tradeTime = tradeTime;

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

        this.orderId = orderId;
    }
}
