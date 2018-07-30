/**
 *  条件单
 *  ~价格条件 - compareType: 1. >=   2. >=
 *  ~时间条件 - compareType: 1. time 2. >=
 */
import { observable, computed, action } from 'mobx';
import moment from 'moment';
import _ from 'lodash';
import { Enum, Colors } from '../../../global';

export default class Condition {
    conditionNo;
    productName;                //合約
    @observable status;         //状态

    @observable conditionType;  //类型 - 价格条件: 0, 时间条件: 1
    
    @observable compareType;    //条件
    @observable triggerPrice;
    @observable triggerTime;
    
    @observable additionFlag;
    @observable additionType;   // additional compareType, 第二個額外條件
    @observable additionPrice;

    @observable direction;      //下单
    @observable orderType;
    @observable num;
    openCloseType;

    expiration;                 //有效期
    
    @observable insertTime;     //下单时间
    @observable statusMsg;      
  

    @computed get statusText() {
        return Enum.triggerStatus[this.status];
    }
    @computed get directionText() {
        return Enum.direction[this.direction].text;
    }
    @computed get directionColor() {
        return Enum.direction[this.direction].color;
    }
    @computed get conditionTypeText() {
        let conditionTypeText = '';
        _.forOwn(Enum.conditionType, (v) => {
            if (v.value === this.conditionType) {
                conditionTypeText = v.text;
            }
        });
        return conditionTypeText;
    }
    @computed get compareTypeText() {
        const compareTypeText = this.getCompareTypeText();
        let compareTypeFullText = '';
        // > 13870 < 13900
        if (this.conditionType === Enum.conditionType.price.value) {
            compareTypeFullText = `${compareTypeText} ${this.triggerPrice}`;
        // 11:30:30 > 13970
        } else {
            compareTypeFullText = moment(this.triggerTime).format('HH:mm:ss');
        }
        let additionTypeText = '';
        if (this.additionFlag === 1) {
            additionTypeText = ` ${this.getAdditionalTypeText()}  ${this.additionPrice}`;
        }
        compareTypeFullText += additionTypeText;
        return compareTypeFullText;
    }
    @computed get insertOrderText() {
        let orderTypeText = '';
        _.forOwn(Enum.orderType, (v) => {
            if (v.value === this.orderType) {
                orderTypeText = v.text;
            }
        });

        return `${orderTypeText}, ${this.num}手`;
    }
    @computed get insertTimeText() {
        return this.insertTime ? this.insertTime : this.statusMsg;
    }
    getCompareTypeText() {
        let compareText = '';
        _.forOwn(Enum.compareType, (v) => {
            if (v.value === this.compareType) {
                compareText = v.text;
            }
        });
        return compareText;
    }
    getAdditionalTypeText() {
        let additionTypeText = '';
        _.forOwn(Enum.compareType, (v) => {
            if (v.value === this.additionType) {
                additionTypeText = v.text;
            }
        });
        return additionTypeText;
    }
    constructor(productName, { conditionNo, conditionType, status, compareType, triggerPrice, triggerTime, additionFlag, additionType, additionPrice, direction, openCloseType, orderType, num, insertTime, statusMsg }) {    
        this.productName = productName;
        this.conditionNo = conditionNo;
        this.conditionType = conditionType;
        this.status = status;
        this.compareType = compareType;
        this.triggerPrice = triggerPrice;
        this.triggerTime = triggerTime;
        this.additionFlag = additionFlag;
        this.additionType = additionType;
        this.additionPrice = additionPrice;
        this.direction = direction;
        this.orderType = orderType;
        this.num = num;
        this.insertTime = insertTime;
        this.statusMsg = statusMsg;
        this.expiration = Enum.stopLossExpiration;
        
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
    @action update({ status, direction, openCloseType, conditionType, num, orderType, compareType, triggerPrice, triggerTime, additionFlag, additionType, additionPrice, insertTime, statusMsg }) {
        this.status = status;
        this.direction = direction;
        this.conditionType = conditionType;
        this.num = num;
        this.orderType = orderType;
        this.compareType = compareType;
        this.triggerPrice = triggerPrice;
        this.triggerTime = triggerTime;

        this.additionFlag = additionFlag;
        this.additionType = additionType;
        this.additionPrice = additionPrice;

        this.insertTime = insertTime;
        this.statusMsg = statusMsg;

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
