/**
 *  成交
 */
import { observable, computed } from 'mobx';

export default class Deal {
    productName;   //合約名稱
    @observable direction;      //買賣
    @observable tradePrice;     //成交價
    @observable tradeNum;       //成交量
    tradeTime;      //成交時間
    // 不顯示
    orderId;        //

    @computed get directionText() {
        return this.direction.display;
    }
    @computed get directionColor() {
        return this.direction.color;
    }

    constructor(productName, direction, tradePrice, tradeNum, tradeTime, orderId) {
        this.productName = productName;
        this.direction = direction;
        this.tradePrice = tradePrice;
        this.tradeNum = tradeNum;
        this.tradeTime = tradeTime;

        this.orderId = orderId;
    }
}
