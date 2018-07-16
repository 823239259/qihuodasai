/**
 * Cancel Designate - 撤单
 * Modify Designate - 改单
 */
import { observable, action } from 'mobx';
import _ from 'lodash';
import { ToastRoot } from '../../../components';
import { Logger, TradeUtil, I18n } from '../../../utils';
import { Config } from '../../../global';

export default class TradeDesignateStore {
    
    // store
    quotationStore = null;
    tradeStore = null;
    tradeSend = null;

    @observable isCancelDesignateDialogVisible = false;
    @observable isAllCancelDesignateDialogVisible = false;

    @observable isModifyDesignateDialogVisbile = false;
    @observable orderPrice = '0'; //委託價
    @observable orderNum = '0';   //委託量
    @observable isModifyFinal = false;
    
    constructor(quotationStore, tradeStore, tradeSend) {
        this.logger = new Logger(TradeDesignateStore);
        this.quotationStore = quotationStore;
        this.tradeStore = tradeStore;
        this.tradeSend = tradeSend;
    }
    toCancel() {
        this.setIsCancelDesignateDialogVisbile(true);
    }
    toAllCancel() {
        this.setIsAllCancelDesignateDialogVisbile(true);
    }
    toModify() {
        this.setIsModifyDesignateDialogVisbile(true);
    }
    /**
     * Cancel Designate - 撤单
     */
    confirmCancelDesignateDialog(designate) {
        const product = this.quotationStore.getProduct(designate.productName);
        this.tradeSend.cancelOrder(designate.orderSysID, designate.orderId, product.exchangeNo, product.commodityNo, product.contractNo, designate.orderNum, designate.direction.value, designate.orderPrice);
        this.cancelCancelDesignateDialog();
    }
    cancelCancelDesignateDialog() {
        this.setIsCancelDesignateDialogVisbile(false);
    }
    @action setIsCancelDesignateDialogVisbile(isVisible) {
        this.isCancelDesignateDialogVisible = isVisible;
    }
    /**
     * All Cancel Designate - 全部撤单
     */
    confirmAllCancelDesignateDialog() {
        const isFirst = true;
        for (const designate of this.tradeStore.designates) {
            if (isFirst) {
                this.confirmCancelDesignateDialog(designate);
            } else {
                setTimeout(() => {
                    this.confirmCancelDesignateDialog(designate);
                }, Config.allWaitingTime);
            }
        }
        this.cancelAllCancelDesignateDialog();
    }
    cancelAllCancelDesignateDialog() {
        this.setIsAllCancelDesignateDialogVisbile(false);
    }
    @action setIsAllCancelDesignateDialogVisbile(isVisible) {
        this.isAllCancelDesignateDialogVisible = isVisible;
    }
    /**
     * Modify Designate - 改单
     */
    @action confirmModifyDesignateDialog(designate) {
        if (!TradeUtil.validateNum(this.orderNum)) {
            return;
        }
        const product = this.quotationStore.getProduct(designate.productName);
        if (!TradeUtil.validatePrice(this.orderPrice, product)) {
            return;
        }
        if (!this.isModifyFinal) {
            this.isModifyFinal = true;
            return;
        }
        // 因為是物件，所以這個紅色訊息沒有問題
        designate.isUpdate = true;
        
        this.tradeSend.modifyOrder(designate.orderSysID, designate.orderId, product.exchangeNo, product.commodityNo, product.contractNo, this.orderNum, designate.direction.value, this.orderPrice, designate.triggerPrice);
        this.cancelModifyDesignateDialog();
    }
    cancelModifyDesignateDialog() {
        this.setIsModifyDesignateDialogVisbile(false);
        this.isModifyFinal = false;
    }
    @action setIsModifyDesignateDialogVisbile(isVisible) {
        this.isModifyDesignateDialogVisbile = isVisible;
    }
    @action onOrderPriceChange(orderPrice) {
        this.orderPrice = orderPrice;
    }
    @action onOrderNumPrice(orderNum) {
        this.orderNum = orderNum;
    }
}

