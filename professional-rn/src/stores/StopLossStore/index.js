/**
 * 止损單
 */
import { observable, action, computed, autorun } from 'mobx';
import _ from 'lodash';
import StopLoss from './models/StopLoss';
import { Enum, Config } from '../../global';
import { ToastRoot } from '../../components';
import { Logger, ArrayUtil } from '../../utils';

export default class StopLossStore {
    quotationStore = null;
    tradeSend = null;
    tradeHoldPositionStore = null;

    constructor(quotationStore, tradeSend, tradeHoldPositionStore) {
        this.logger = new Logger(StopLossStore);
        this.quotationStore = quotationStore;
        this.tradeSend = tradeSend;
        this.tradeHoldPositionStore = tradeHoldPositionStore;
        // autorun(() => {
        //     this.logger.trace(`balanceText: ${this.balanceText}, canUseText: ${this.canUseText}, totalFloatProfit: ${this.totalFloatProfit} riskText: ${this.riskText}, deposit: ${this.deposit}`);
        // });
    }
    // observable
    @observable stopLossYets;
    @observable stopLossDones;

    @observable isDialogVisible = false;
    @observable dialogContent = '';
    operatingStopLoss = null;
    operationType = null;                   // 決定dialog按下做什麼樣的operation

    @action reset() {
        this.stopLossYets = [];
        this.stopLossDones = [];
        this.resetDialog();
    }
    @action manageStopLoss(param) {
        const stopLossNo = param.StopLossNo;
        const productName = `${param.CommodityNo}${param.ContractNo}`;
        const status = param.Status;
        const stopLoss = new StopLoss(stopLossNo, productName, this.getStopLossAttributes(param));
        if (status === 0 || status === 1) {
            this.stopLossYets.push(stopLoss);
        } else {
            this.stopLossDones.push(stopLoss);
        }
    }
    updateStopLoss(param) {
        const stopLossNo = param.StopLossNo;
        const productName = `${param.CommodityNo}${param.ContractNo}`;
        const status = param.Status;
        let stopLossType = param.StopLossType;

        if (stopLossType === 0 || stopLossType === 2) {
            stopLossType = Enum.stopLossType.stopLoss.name;
        } else {
            stopLossType = Enum.stopLossType.stopWin.name;
        }
        const statusText = Enum.triggerStatus[status];
        ToastRoot.show(`${stopLossType}单【${stopLossNo}】,${statusText}`);

        if (this.stopLossYets.length === 0) {
            return;
        }

        if (status === 2 || status === 3 || status === 4 || status === 5) {
            // 移除Yet
            ArrayUtil.remove(this.stopLossYets, (stopLoss) => {
                return stopLoss.stopLossNo === stopLossNo;
            });
            // 放入Done
            this.stopLossDones.push(new StopLoss(stopLossNo, productName, this.getStopLossAttributes(param)));
        } else {
            // update yet
            const stopLoss = this.stopLossYets.find((sl) => {
                return sl.stopLossNo === stopLossNo;
            });
            if (stopLoss) {
                stopLoss.update(this.getStopLossAttributes(param));
            }
        }
    }
    getStopLossAttributes(param) {
        const productName = `${param.CommodityNo}${param.ContractNo}`;
        const dotSize = this.quotationStore.getProduct(productName).dotSize;
        let stopLossPrice = param.StopLossPrice;
        stopLossPrice = _.toNumber(stopLossPrice.toFixed(dotSize));
        let stopLossDiff = param.StopLossDiff;
        stopLossDiff = _.toNumber(stopLossDiff.toFixed(dotSize));

        return {
            status: param.Status,
            direction: param.HoldDrection,
            stopLossType: param.StopLossType,
            num: param.Num,
            stopLossPrice,
            stopLossDiff,
            orderType: param.OrderType,
            insertTime: param.InsertDateTime
        };
    }
    toDialog(operationType, stopLoss) {
        this.operationType = operationType;
        this.operatingStopLoss = stopLoss;
        let stopLossTypeText = '';
        _.forOwn(Enum.stopLossType, (v, k) => {
            if (v.value === stopLoss.stopLossType) {
                stopLossTypeText = v.name;
            }
        });
        let stopLossOperationTypeText = '';
        _.forOwn(Enum.accordionItemButtonType, (v, k) => {
            if (v.value === operationType) {
                stopLossOperationTypeText = v.text;
            }
        });
        this.dialogContent = `是否${stopLossOperationTypeText}${stopLossTypeText}单`;
        this.setIsDialogVisbile(true);
    }
    confirmDialog() {
        // 修改 止損單 - 將止損止盈資料填上去
        if (this.operationType === Enum.accordionItemButtonType.modify.value) {
            if (this.operatingStopLoss.status === 0) { // Enum.triggerStatus[0] == 运行中
                this.setIsDialogVisbile(false);
                ToastRoot.show('运行中的止损/止盈单不能修改,请先暂停');
                return;
            }
            this.setIsDialogVisbile(false);
            setTimeout(() => {
                this.tradeHoldPositionStore.toStopLoss(this.operatingStopLoss);
            }, Config.waitingTime);
        // 暫停、啟動、刪除 止損單
        } else {
            this.tradeSend.modifyStopLoss(this.operationType, this.operatingStopLoss);
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
        this.operatingStopLoss = null;
        this.operationType = null; 
    }
    @action setIsDialogVisbile(isVisible) {
        this.isDialogVisible = isVisible;
    }
}

