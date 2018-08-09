/**
 * 条件单
 */
import { observable, action, computed, autorun, observe } from 'mobx';
import moment from 'moment';
import _ from 'lodash';
import Condition from './models/Condition';
import { Enum, Config } from '../../global';
import { ToastRoot } from '../../components';
import { Logger, TradeUtil, ArrayUtil } from '../../utils';

export default class ConditionStore {
    quotationStore = null;
    tradeSend = null;

    constructor(quotationStore, tradeSend) {
        this.logger = new Logger(ConditionStore);
        this.quotationStore = quotationStore;
        this.tradeSend = tradeSend;
        // autorun(() => {
        //     this.logger.trace(`balanceText: ${this.balanceText}, canUseText: ${this.canUseText}, totalFloatProfit: ${this.totalFloatProfit} riskText: ${this.riskText}, deposit: ${this.deposit}`);
        // });
        observe(this.priceForm, (change) => {
            // 為了取得最新價
            if (change.name === 'productPrice') {
                const productValue = change.newValue;
                this.productPriceSelected = this.quotationStore.products.find((product) => {
                    return product.productName === productValue;
                });
                this.productPriceChanged();
            }
        });
        observe(this.timeForm, (change) => {
            if (change.name === 'productTime') {
                const productValue = change.newValue;
                this.productTimeSelected = this.quotationStore.products.find((product) => {
                    return product.productName === productValue;
                });
                this.productTimeChanged();
            }
        });
    }
    // observable
    @observable conditionYets;
    @observable conditionDones;

    @observable isInsert = true;   // true->insert, false->modify
    @observable isPriceTab = true; // true->priceTab, false->timeTab
    @observable conditionEditDialogContent = '';

    // old
    @observable isDialogVisible = false;
    @observable dialogContent = '';
    operatingCondition = null;

    operationType = null;                   // 決定dialog按下做什麼樣的operation
    // 

    // Edit
    @observable isConditionEditVisible = false;
    @observable isPriceConditionTabVisible = true;
    @observable isTimeConditionTabVisible = true;
    // priceForm
    optionProducts;
    optionDirections;
    @observable priceForm = { 
        productPrice: null, // 為了跟Picker 比對
        compareType: Enum.compareType.greater.value, 
        triggerPrice: '0',

        additionType: Enum.compareType.addition.value,
        openCloseType: Enum.openCloseType.open.value,
        additionPrice: '0',

        direction: Enum.direction[0].value,
        orderType: Enum.orderType.market.value,
        num: '1'
    };
    // observe product - > productPriceSelected -> productTimeLastPrice
    @observable productPriceSelected = null;
    @computed get productPriceLastPrice() {
        return this.productPriceSelected.lastPrice;
    }
    @observable productTimeSelected = null;
    @computed get productTimeLastPrice() {
        return this.productTimeSelected.lastPrice;
    }

    productPriceChanged() {
        this.priceForm.triggerPrice = this.productPriceSelected.lastPrice;
        this.priceForm.additionPrice = this.productPriceSelected.lastPrice;
    }
    productTimeChanged() {
        this.timeForm.additionPrice = this.productTimeSelected.lastPrice;
    }

    // timeForm
    @observable timeForm = {
        productTime: null, // 為了跟Picker 比對
        triggerTime: moment().format('YYYY-MM-DD HH:mm:ss'),
        openCloseType: Enum.openCloseType.open.value,
        additionType: Enum.compareType.addition.value,
        additionPrice: '0',

        direction: Enum.direction[0].value,
        orderType: Enum.orderType.market.value,
        num: '1'
    };

    // dialog
    @observable isConditionEditDialogVisible = false;


    @action reset() {
        this.conditionYets = [];
        this.optionProducts = this.quotationStore.products.map((product) => {
            return { value: product.productName, text: product.commodityName };
        });
        this.optionDirections = Enum.direction.map((d) => {
            return { value: d.value, text: d.display };
        });
        const productInit = this.quotationStore.products[0];
        this.productPriceSelected = productInit;
        this.priceForm.productPrice = this.optionProducts[0].value;

        this.productTimeSelected = productInit;
        this.timeForm.productTime = this.optionProducts[0].value;
        
        this.conditionDones = [];
        this.resetDialog();
    }
    @action manageCondition(param) {
        const productName = `${param.CommodityNo}${param.ContractNo}`;
        const status = param.Status;
        const condition = new Condition(productName, this.getConditionAttributes(param));
        if (status === 0 || status === 1) {
            this.conditionYets.push(condition);
        } else {
            this.conditionDones.push(condition);
        }
    }
    @action updateCondition(param) {
        const productName = `${param.CommodityNo}${param.ContractNo}`;
        const conditionNo = param.ConditionNo;
        const status = param.Status;
        const statusText = Enum.triggerStatus[status];
        ToastRoot.show(`【${productName}】条件单【${conditionNo}】,${statusText}`);

        if (this.conditionYets.length === 0) {
            return;
        }

        if (status === 2 || status === 3 || status === 4 || status === 5) {
            // 移除Yet
            ArrayUtil.removeOne(this.conditionYets, (condition) => {
                return condition.conditionNo === conditionNo;
            });
            // 放入Done
            this.conditionDones.push(new Condition(productName, this.getConditionAttributes(param)));
        } else {
            // update yet
            const condition = this.conditionYets.find((cy) => {
                return cy.conditionNo === conditionNo;
            });
            if (condition) {
                condition.update(this.getConditionAttributes(param));
            }
        }
    }
    getConditionAttributes(param) {
        const status = param.Status;
        let insertTime = param.InsertDateTime;
        if (status >= 2) {
            insertTime = param.TriggedTime;
        }
        return {
            conditionNo: param.ConditionNo,
            status,
            conditionType: param.ConditionType,
            
            compareType: param.CompareType,
            triggerPrice: param.PriceTriggerPonit,
            triggerTime: param.TimeTriggerPoint,

            additionFlag: param.AdditionFlag,
            additionType: param.AdditionType,
            additionPrice: param.AdditionPrice,

            direction: param.Drection,
            openCloseType: param.OpenCloseType,
            orderType: param.OrderType,
            num: param.Num,

            insertTime,
            statusMsg: param.StatusMsg
        };
    }
    plusConditioModal() {
        this.setIsConditionEditVisible(true);
        this.isInsert = true;
        this.isPriceConditionTabVisible = true;
        this.isTimeConditionTabVisible = true;
    }
    setIsPriceTab(index) {
        if (this.isInsert) {
            if (index === 0) {
                this.isPriceTab = true;
                this.priceForm.triggerPrice = this.productPriceSelected.lastPrice;
            } else {
                this.isPriceTab = false;
                this.timeForm.triggerTime = moment().format('YYYY-MM-DD HH:mm:ss');
            }
        }
    }
    // ConditionEdit
    @action confirmConditionEdit() {
        if (!this.validate(this.isPriceTab)) {
            return;
        }
        if (this.isPriceTab) {
            this.conditionEditDialogContent = `是否提交【${this.productPriceSelected.productName}】条件单`;
        } else {
            this.conditionEditDialogContent = `是否提交【${this.productTimeSelected.productName}】条件单`;
        }
        this.setIsConditionEditDialogVisible(true);
    }
    @action cancelConditionEdit() {
        this.setIsConditionEditVisible(false);
    }
    @action setIsConditionEditVisible(isVisible) {
        this.isConditionEditVisible = isVisible;
    }
    // ConditionEditDialog
    @action confirmConditionEditDialog() {
        if (this.isInsert) {
            if (this.isPriceTab) {
                this.insertPriceCondition();
            } else {
                this.insertTimeCondition();
            }
        } else {
            if (this.isPriceTab) {
                this.modifyPriceCondition();
            } else {
                this.modifyTimeCondition();
            }
        }
        this.setIsConditionEditDialogVisible(false);
        setTimeout(() => {
            this.setIsConditionEditVisible(false);
        }, Config.waitingTime);
    }
    @action cancelConditionEditDialog() {
        this.setIsConditionEditDialogVisible(false);
    }
    @action setIsConditionEditDialogVisible(isVisible) {
        this.isConditionEditDialogVisible = isVisible;
    }
    insertPriceCondition() {
        const exchangeNo = this.productPriceSelected.exchangeNo;
        const commodityNo = this.productPriceSelected.commodityNo;
        const contractNo = this.productPriceSelected.contractNo;
        const { num, triggerPrice, compareType, orderType, direction, additionType, openCloseType, additionPrice } = this.priceForm;
        const conditionType = Enum.conditionType.price.value;
        this.tradeSend.insertCondition(exchangeNo, commodityNo, contractNo, num, conditionType, triggerPrice, compareType, '', 0, 0, orderType, direction, 0, 0, 0, this.getFlag(additionType), additionType, additionPrice, openCloseType);
    }
    insertTimeCondition() {
        const exchangeNo = this.productTimeSelected.exchangeNo;
        const commodityNo = this.productTimeSelected.commodityNo;
        const contractNo = this.productTimeSelected.contractNo;
        const { num, orderType, direction, additionType, additionPrice, triggerTime, openCloseType } = this.timeForm;
        const conditionType = Enum.conditionType.time.value;

        this.tradeSend.insertCondition(exchangeNo, commodityNo, contractNo, num, conditionType, 0, 0, triggerTime, 0, 0, orderType, direction, 0, 0, 0, this.getFlag(additionType), additionType, additionPrice, openCloseType);
    }
    modifyPriceCondition() {
        const { conditionNo, conditionType } = this.operatingCondition;
        const { num, triggerPrice, compareType, orderType, direction, additionType, openCloseType, additionPrice } = this.priceForm;
        this.tradeSend.modifyCondition(Enum.accordionItemButtonType.modify.value, conditionNo, num, conditionType, triggerPrice, compareType, '', 0, 0, orderType, direction, 0, 0, 0, this.getFlag(additionType), additionType, additionPrice, openCloseType);
    }
    modifyTimeCondition() {
        const { conditionNo, conditionType } = this.operatingCondition;
        const { num, triggerTime, orderType, direction, additionType, additionPrice, openCloseType } = this.timeForm;
        this.tradeSend.modifyCondition(Enum.accordionItemButtonType.modify.value, conditionNo, num, conditionType, 0, 0, triggerTime, 0, 0, orderType, direction, 0, 0, 0, this.getFlag(additionType), additionType, additionPrice, openCloseType);
    }
    //1-有附加条件，0-没有附加条件
    getFlag(additionType) {
        let flag = 1;
        if (additionType === Enum.compareType.addition.value) {
            flag = 0;
        }
        return flag;
    }
    validate() {
        if (this.isPriceTab) {
            const { triggerPrice, additionPrice, additionType, num } = this.priceForm;

            if (!TradeUtil.validateNum(num)) {
                return false;
            }
            if (!TradeUtil.validatePrice(triggerPrice, this.productPriceSelected, '触发价格')) {
                return false;
            }
            if (additionType !== Enum.compareType.addition.value) {
                if (!TradeUtil.validatePrice(additionPrice, this.productPriceSelected, '附加触发价格')) {
                    return false;
                }
            }
            return this.validatePriceAddition();
        } else {
            const { additionPrice, additionType, num } = this.timeForm;
            if (additionType !== Enum.compareType.addition.value) {
                if (!TradeUtil.validatePrice(additionPrice, this.productTimeSelected, '附加触发价格')) {
                    return false;
                }
            }
            if (!TradeUtil.validateNum(num)) {
                return false;
            }
            if (moment().isAfter(this.timeForm.triggerTime)) {
                this.showToastRootCondition('条件时间必须晚于当前时间');
                return false;
            }
        }
        return true;
    }
    validatePriceAddition() {
        if (this.priceForm.additionType === Enum.compareType.addition.value) {
            return true;
        }
        if (this.priceForm.compareType === this.priceForm.additionType) {
            this.showToastRootCondition('初始条件和附加条件不能一致');
            return false;
        }
        if (this.priceForm.triggerPrice === this.priceForm.additionPrice) {
            this.showToastRootCondition('初始价格和附加价格不能一致');
            return false;
        }
        const diffPrice = this.priceForm.triggerPrice - this.priceForm.additionPrice;
        let triggerPriceDirection = 0;
        if (diffPrice > 0) {
            triggerPriceDirection = 1;
        }

        const areaMsg = '两个价格必须形成一个区间';
        if (triggerPriceDirection === 1) {
            if (this.priceForm.compareType === Enum.compareType.greater.value || this.priceForm.compareType === Enum.compareType.greaterThanEqual.value) {
                this.showToastRootCondition(areaMsg);
                return false;
            }
        } else {
            if (this.priceForm.compareType === Enum.compareType.less.value || this.priceForm.compareType === Enum.compareType.lessThanEqual.value) {
                this.showToastRootCondition(areaMsg);
                return false;
            }
        }
        return true;
    }
    toDialog(operationType, condition) {
        this.operationType = operationType;
        this.operatingCondition = condition;
        let operationTypeText = '';
        _.forOwn(Enum.accordionItemButtonType, (v, k) => {
            if (v.value === operationType) {
                operationTypeText = v.text;
            }
        });
        this.dialogContent = `是否${operationTypeText}条件单`;
        this.setIsDialogVisbile(true);
    }
    confirmDialog() {
        // 修改 止損單 - 將止損止盈資料填上去
        if (this.operationType === Enum.accordionItemButtonType.modify.value) {
            this.setIsDialogVisbile(false);
            setTimeout(() => {
                this.editCondition();
            }, Config.waitingTime);
        // 暫停、啟動、刪除 止損單
        } else {
            this.tradeSend.modifyCondition(this.operationType, this.operatingCondition.conditionNo, 0, 0, 0, 0, '', 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
            this.setIsDialogVisbile(false);
        }
    }
    cancelDialog() {
        this.setIsDialogVisbile(false);
        this.resetDialog();
    }
    @action resetDialog() {
        this.isDialogVisible = false;
        this.dialogContent = '';
        this.operatingCondition = null;
        this.operationType = null; 
    }
    @action setIsDialogVisbile(isVisible) {
        this.isDialogVisible = isVisible;
    }
    @action editCondition() {
        this.isInsert = false;
        if (this.operatingCondition.conditionType === Enum.conditionType.price.value) {
            this.isPriceTab = true;
            this.isPriceConditionTabVisible = true;
            this.isTimeConditionTabVisible = false;

            this.priceForm.productPrice = this.operatingCondition.productName;
            this.priceForm.compareType = this.operatingCondition.compareType;
            this.priceForm.triggerPrice = String(this.operatingCondition.triggerPrice);
            this.priceForm.additionType = this.operatingCondition.additionType;
            this.priceForm.additionPrice = String(this.operatingCondition.additionPrice);
            this.priceForm.direction = this.operatingCondition.direction;
            this.priceForm.orderType = this.operatingCondition.orderType;
            this.priceForm.num = String(this.operatingCondition.num);
        } else {
            this.isPriceTab = false;
            this.isPriceConditionTabVisible = false;
            this.isTimeConditionTabVisible = true;

            this.timeForm.productTime = this.operatingCondition.productName;
            this.timeForm.triggerTime = this.operatingCondition.triggerTime;
            this.timeForm.additionType = this.operatingCondition.additionType;
            this.timeForm.additionPrice = String(this.operatingCondition.additionPrice);
            this.timeForm.direction = this.operatingCondition.direction;
            this.timeForm.orderType = this.operatingCondition.orderType;
            this.timeForm.num = String(this.operatingCondition.num);

        }
        this.setIsConditionEditVisible(true);
    }
    showToastRootCondition(msg) {
        ToastRoot.show(msg, { position: Config.toastRoot.position.bottom });
    }
}

