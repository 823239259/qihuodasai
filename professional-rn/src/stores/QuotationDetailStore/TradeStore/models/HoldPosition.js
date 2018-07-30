/**
 *  持倉
 */
import { observable, computed, action } from 'mobx';
import _ from 'lodash';
import { Colors, Enum } from '../../../../global';

export default class HoldPosition {

    @observable product;
    @observable rtnParam;
    /*************/
    // productName;               //合約名稱
    // @observable direction;      //多空
    // @observable holdNum;        //手數
    // @observable holdAvgPrice;   //持倉均價
    // @observable floatProfit;    //浮動盈虧
    // //不顯示
    // dotSize;
    // currencyNo;                    
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

    constructor(product, param) {
        this.product = product;
        this.rtnParam = param;
    }

    @action update(product, param) {
        this.product = product;
        this.rtnParam = param;
    }

    @computed get productName() {
        return this.product.productName;
    }

    @computed get direction() {
        return Enum.direction[this.rtnParam.Drection];
    }

    @computed get holdNum() {
        return this.rtnParam.HoldNum;
    }

    @computed get holdAvgPrice() {
        return _.toNumber(this.rtnParam.HoldAvgPrice.toFixed(this.product.dotSize));
    }

    @computed get dotSize() {
        return this.product.dotSize;
    }

    @computed get currencyNo() {
        return this.product.currencyNo;
    }

    @computed get floatProfit() {
        return this.getFloatProfit(this.product.lastPrice, this.holdAvgPrice, this.product.contractSize, this.product.miniTikeSize, this.holdNum, this.direction, this.currencyNo);
    }

    getFloatProfit(lastPrice, holdAvgPrice, contractSize, miniTikeSize, holdNum, direction, currencyNo) {
        let price = 0;
        if (direction.value === 0) {
            price = lastPrice - holdAvgPrice;
        } else {
            price = holdAvgPrice - lastPrice;
        }
        const value = price * contractSize * (holdNum / miniTikeSize);
        return {
            value,
            color: Colors.getColorText(value),
            text: `${value.toFixed(2)}:${currencyNo}`
        };
    }
}
