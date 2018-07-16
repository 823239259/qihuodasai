/**
 *  止损
 */
import { observable, computed, action } from 'mobx';
import _ from 'lodash';
import { Enum, Colors } from '../../../global';
import { Logger } from '../../../utils';

export default class StopLoss {
    stopLossNo;                 
    productName;                //合約
    @observable status;         //状态
    @observable direction;      //多空
    @observable stopLossType;   //类别
    num;                        //手数
    @observable stopLossPrice;  //触发条件
    @observable orderType;      //委托价
    expiration;                 //有效期
    insertTime;                 //下单时间
    @observable stopLossDiff;               

    @computed get statusText() {
        return Enum.triggerStatus[this.status];
    }
    @computed get directionText() {
        return Enum.direction[this.direction].text;
    }
    @computed get directionColor() {
        return Enum.direction[this.direction].color;
    }
    @computed get stopLossTypeText() {
        let stopLossTypeText = '';
        _.forOwn(Enum.stopLossType, (v) => {
            if (v.value === this.stopLossType) {
                stopLossTypeText = v.display;
            }
        });
        return stopLossTypeText;
    }
    @computed get stopLossPriceText() {
        if (this.stopLossType === 2) {
            return `${Enum.stopLossPriceType.dynamic}: ${this.stopLossDiff}`;
        }
        return `${Enum.stopLossPriceType.trigger}: ${this.stopLossPrice}`;
    }
    @computed get orderTypeText() {
        let orderTypeText = '';
        _.forOwn(Enum.orderType, (v) => {
            if (v.value === this.orderType) {
                orderTypeText = v.text;
            }
        });
        return orderTypeText;
    }
    constructor(stopLossNo, productName, { status, direction, stopLossType, num, stopLossPrice, stopLossDiff, orderType, insertTime }) {
        this.logger = new Logger(StopLoss);
    
        this.stopLossNo = stopLossNo;
        this.productName = productName;
        this.status = status;
        this.direction = direction;
        this.stopLossType = stopLossType;
        this.num = num;
        this.stopLossPrice = stopLossPrice;
        this.stopLossDiff = stopLossDiff;
        this.orderType = orderType;
        this.expiration = Enum.stopLossExpiration;
        this.insertTime = insertTime;
    }
    @action update({ status, direction, stopLossType, num, stopLossPrice, stopLossDiff, orderType, insertTime }) {
        this.status = status;
        this.direction = direction;
        this.stopLossType = stopLossType;
        this.num = num;
        this.stopLossPrice = stopLossPrice;
        this.stopLossDiff = stopLossDiff;
        this.orderType = orderType;
        this.insertTime = insertTime;
    }
}
