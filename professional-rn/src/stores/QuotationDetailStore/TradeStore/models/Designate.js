/**
 *  掛單 Designate -> 子集 -> 委託 Order
 */
import { observable, computed, action } from 'mobx';

export default class Designate {
    productName;   //合約名稱
    @observable direction;      //買賣
    @observable orderPrice;     //委託價 - 選擇市價時，只會顯示市價，因為還沒成交
    @observable orderNum;       //委託量
    @observable designateNum;   //掛單量
    insertDateTime; //下單時間
    openCloseType;
    // 不顯示
    orderId;        //需要OrderId的原因是，委託、掛單可能會出現重複的productName
    orderSysID;     //都傳""，可能是以前留著要擴充功能之類的
    triggerPrice;
    isUpdate;       //作為一個Tag，改單 -> isUpdate = true, 顯示改單訊息後 -> isUpdate = false

    @computed get directionText() {
        return this.direction.display;
    }
    @computed get directionColor() {
        return this.direction.color;
    }

    constructor(productName, direction, orderPrice, orderNum, designateNum, insertDateTime, orderId, orderSysID, triggerPrice, openCloseType) {
        this.productName = productName;
        this.direction = direction;
        this.orderPrice = orderPrice;
        this.orderNum = orderNum;
        this.designateNum = designateNum;
        this.insertDateTime = insertDateTime;
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
        this.orderSysID = orderSysID;
        this.triggerPrice = triggerPrice;
        this.isUpdate = false;
    }
    @action update(orderPrice, orderNum, designateNum) {
        this.orderPrice = orderPrice;
        this.orderNum = orderNum;
        this.designateNum = designateNum;
    }
}
