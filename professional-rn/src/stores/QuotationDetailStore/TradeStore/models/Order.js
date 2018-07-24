/**
 *  委託 Order -> 母集 -> 掛單 Designate
 */
import { observable, computed } from 'mobx';

export default class Order {
    productName;    //合約名稱
    @observable orderStatus;    //狀態
    @observable direction;      //買賣
    @observable orderPrice;     //委託價 - 選擇市價時，只會顯示市價，因為還沒成交
    @observable orderNum;       //委託量
    @observable tradeNum;       //已成交
    @observable cdNum;          //已撤單
    insertDateTime; //下單時間
    // 不顯示
    orderId;        //需要OrderId的原因是，委託、掛單可能會出現重複的productName

    @computed get directionText() {
        return this.direction.display;
    }
    @computed get directionColor() {
        return this.direction.color;
    }
    
    constructor(productName, orderStatus, direction, orderPrice, orderNum, tradeNum, cdNum, insertDateTime, orderId) {
        this.productName = productName;
        this.orderStatus = orderStatus;
        this.direction = direction;
        this.orderPrice = orderPrice;
        this.orderNum = orderNum;
        this.tradeNum = tradeNum;
        this.cdNum = cdNum;
        this.insertDateTime = insertDateTime;

        this.orderId = orderId;
    }
}