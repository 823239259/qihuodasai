/**
 *  持倉
 */
import { observable, computed, action } from 'mobx';

export default class HoldPosition {
    productName;               //合約名稱
    @observable direction;      //多空
    @observable holdNum;        //手數
    @observable holdAvgPrice;   //持倉均價
    @observable floatProfit;    //浮動盈虧
    //不顯示
    dotSize;
    currencyNo;                    
    // 根據該品種dotSize，來決定toFixed();
    // 原本的holdAvgPrice是number: 不可能呈現5.00的數字，再toFixed()一次，會變成string，這時候可以顯示5.00
    @computed get holdAvgPriceText() {
        return this.holdAvgPrice.toFixed(this.dotSize);
    }
    @computed get directionText() {
        return this.direction.text;
    }
    @computed get directionColor() {
        return this.direction.color;
    }
    @computed get floatProfitText() {
        return this.floatProfit.text;
    }
    @computed get floatProfitColor() {
        return this.floatProfit.color;
    }

    constructor({ productName, direction, holdNum, holdAvgPrice, floatProfit, dotSize, currencyNo }) {
        this.productName = productName;
        this.direction = direction;
        this.holdNum = holdNum;
        this.holdAvgPrice = holdAvgPrice;
        this.floatProfit = floatProfit;

        this.dotSize = dotSize;
        this.currencyNo = currencyNo;
    }
    @action update({ productName, direction, holdNum, holdAvgPrice, floatProfit, dotSize, currencyNo }) {
        this.productName = productName;
        this.direction = direction;
        this.holdNum = holdNum;
        this.holdAvgPrice = holdAvgPrice;
        this.floatProfit = floatProfit;

        this.dotSize = dotSize;
        this.currencyNo = currencyNo;
    }
}
