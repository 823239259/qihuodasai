/**
 * Close Hold - 平倉
 * Reserve Hold - 反手
 * Stop Loss - 止损止盈
 */
import { observable, action, computed, observe } from 'mobx';
import _ from 'lodash';
import HoldPosition from '../TradeStore/models/HoldPosition';
import StopLoss from '../../StopLossStore/models/StopLoss';
import { ToastRoot } from '../../../components';
import { Config, Enum, Variables } from '../../../global';
import { Logger, TradeUtil, I18n } from '../../../utils';

export default class TradeHoldPositionStore {
    type = true; //to fix ... true 内盘 false 外盘
    navigator = null;
    // store
    quotationStore = null;
    tradeStore = null;
    tradeSend = null;
    
    constructor(quotationStore, tradeStore, tradeSend) {
        this.logger = new Logger(TradeHoldPositionStore);
        this.quotationStore = quotationStore;
        this.tradeStore = tradeStore;
        this.tradeSend = tradeSend;
        
        observe(this.stopLossForm, (change) => {
            if (change.name === 'stopLossType') {
                this.stopLossType = change.newValue;
                if (change.newValue === Enum.stopLossType.stopLoss.value) {
                    this.stopLossForm.stopLossPrice = this.lastPrice;
                } else if (change.newValue === Enum.stopLossType.stopLossDynamic.value) {
                    this.stopLossForm.stopLossPrice = '0';
                }
            }
        });
    }
    
    @observable isStopLossTabVisible = true;
    @observable isStopWinTabVisible = true;

    @observable isCloseHoldDialogVisible = false;
    @observable isAllCloseHoldDialogVisible = false;
    @observable isReverseHoldDialogVisible = false;
    @observable isStopLossVisible = false;
    @observable isStopLossDialogVisible = false;
    // 止损止盈
    @observable stopHoldPosition = null;
    @observable stopLossType = 0;
    stopLossDiff = 0;
    stopPrice = 0;
    stopNumber = 0;
    stopLossTabIndex = 0;
    // true: 在列表中執行 - modify，false：在交易中心執行 - insert
    isStopLossList = false;
    stopLossSelected = null;    // 在列表中執行時需要抓到原本的StopLossNo
    // form
    @observable stopLossForm = { stopLossType: Enum.stopLossType.stopLoss.value, stopLossPrice: '1', stopLossNum: '1' };
    @observable stopWinForm = { stopWinPrice: '1', stopWinNum: '1' };

    @computed get lastPrice() {
        return this.quotationStore.getProduct(this.stopHoldPosition.productName).lastPrice;
    }
    @computed get lossPercentage() {
        if (!this.stopLossForm.stopLossPrice) {
            return 0;
        }
        const holdAvgPrice = this.stopHoldPosition.holdAvgPrice;
        const stopLossPriceFloat = parseFloat(this.stopLossForm.stopLossPrice);
        let scale = 0;
        if (this.stopLossForm.stopLossType === Enum.stopLossType.stopLossDynamic.value) {
            scale = (stopLossPriceFloat / this.lastPrice) * 100;
        } else if (this.stopLossForm.stopLossType === Enum.stopLossType.stopLoss.value) {
            scale = ((stopLossPriceFloat - holdAvgPrice) / holdAvgPrice) * 100;
        }
        return Math.abs(scale).toFixed(2);
    }
    @computed get winPercentage() {
        if (!this.stopWinForm.stopWinPrice) {
            return 0;
        }
        const holdAvgPrice = this.stopHoldPosition.holdAvgPrice;
        const stopWinPriceFloat = parseFloat(this.stopWinForm.stopWinPrice);
        const scale = ((stopWinPriceFloat - holdAvgPrice) / holdAvgPrice) * 100;
        return Math.abs(scale).toFixed(2);
    }
    @computed get stopLossText() {
        let stopLossText = '';
        _.forOwn(Enum.stopLossType, (v) => {
            if (v.value === this.stopLossType) {
                stopLossText = v.display;
            }
        });
        return stopLossText;
    }

    init(navigator) {
        this.navigator = navigator;
    }
    toClose() {
        this.setIsCloseHoldDialogVisbile(true);
    }
    toAllClose() {
        this.setIsAllCloseHoldDialogVisbile(true);
    }
    toReverse() {
        this.setIsReverseHoldDialogVisbile(true);
    }
    // 1. AccordionItemHoldPositionButton - data: HoldPosition
    // 2. StopLossStore - data: StopLoss
    @action toStopLoss(data) {
        this.stopHoldPosition = null;
        // 止損單Modal 修改時 - 只是把值填上去
        if (data instanceof StopLoss) {
            this.isStopLossList = true;
            this.stopLossSelected = data;
            
            this.stopLossType = this.stopLossSelected.stopLossType;
            this.stopLossDiff = this.stopLossSelected.stopLossDiff;

            this.tradeStore.holdPositions.forEach((value, key, map) => {
                if (value.productName === this.stopLossSelected.productName) {
                    this.stopHoldPosition = value;
                }
            });
            if (this.stopHoldPosition === null) {
                ToastRoot.show('无效的合约');
                return;
            }
            // 只顯示止盈tab
            if (this.stopLossSelected.stopLossType === Enum.stopLossType.stopWin.value) {
                this.isStopLossTabVisible = false;
                this.isStopWinTabVisible = true;

                this.stopWinForm.stopWinPrice = String(this.stopLossSelected.stopLossPrice);
                this.stopWinForm.stopWinNum = String(this.stopLossSelected.num);
            // 只顯示止損tab
            } else {
                this.isStopLossTabVisible = true;
                this.isStopWinTabVisible = false;


                this.stopLossForm.stopLossType = this.stopLossSelected.stopLossType;
                this.stopLossForm.stopLossNum = String(this.stopLossSelected.num);
                if (this.stopLossSelected.stopLossType === Enum.stopLossType.stopLossDynamic.value) {
                    this.stopLossForm.stopLossPrice = String(this.stopLossSelected.stopLossDiff);
                } else {
                    this.stopLossForm.stopLossPrice = String(this.stopLossSelected.stopLossPrice);
                }
            }
        // 2. 交易中心 點選止損止盈時
        } else if (data instanceof HoldPosition) {
            this.isStopLossList = false;
            this.stopHoldPosition = data;
            this.isStopLossTabVisible = true;
            this.isStopWinTabVisible = true;

            const lastPrice = this.quotationStore.getProduct(data.productName).lastPrice;
            
            this.stopLossType = Enum.stopLossType.stopLoss.value;
            this.stopLossDiff = 0;
            this.stopLossForm.stopLossType = Enum.stopLossType.stopLoss.value;
            this.stopLossForm.stopLossPrice = lastPrice; // lastPrice - string
            this.stopLossForm.stopLossNum = '1';
            
            this.stopWinForm.stopWinPrice = lastPrice;
            this.stopWinForm.stopWinNum = '1';
        }
        
        this.setIsStopLossVisbile(true);
        this.stopLossTabIndex = 0;
    }
    setStopLossCurrentTab(index) {
        this.stopLossTabIndex = index;
        // 交易中心時，才做
        if (!this.isStopLossList) {
            if (index === 1) {
                this.stopLossType = Enum.stopLossType.stopWin.value;
            } else {
                this.stopLossType = this.stopLossForm.stopLossType;
            }
        }
    }
    /**
     * Close Hold - 平倉
     *   
     * direction(Drection) -> 相反
     * LimitPrice -> 根據最新Product計算出
     * priceType = 1 -> 市價
     */
    confirmCloseHoldDialog(holdPosition) {
        const product = this.quotationStore.getProduct(holdPosition.productName);
        const price = TradeUtil.getMarketPrice(product.lastPrice, product.miniTikeSize, holdPosition.direction.value, product.dotSize);
        this.tradeSend.insertOrder(product.exchangeNo, product.commodityNo, product.contractNo, holdPosition.holdNum, this.getReverseDirection(holdPosition.direction), Enum.priceType.market.value, price, 0, TradeUtil.getOrderRef(),2);
        this.setIsCloseHoldDialogVisbile(false);
    }
    cancelCloseHoldDialog() {
        this.setIsCloseHoldDialogVisbile(false);
    }
    @action setIsCloseHoldDialogVisbile(isVisible) {
        this.isCloseHoldDialogVisible = isVisible;
    }
    // All Close Hold - 全部平倉
    confirmAllCLoseHoldDialog() {
        const isFirst = true;
        this.tradeStore.holdPositions.forEach((value, key, map) => {
            if (isFirst) {
                this.confirmCloseHoldDialog(value);
            } else {
                setTimeout(() => {
                    this.confirmCloseHoldDialog(value);
                }, Config.allWaitingTime);
            }
        });
        this.setIsAllCloseHoldDialogVisbile(false);
    }
    cancelAllCloseHoldDialog() {
        this.setIsAllCloseHoldDialogVisbile(false);
    }
    @action setIsAllCloseHoldDialogVisbile(isVisible) {
        this.isAllCloseHoldDialogVisible = isVisible;
    }
    /**
     * Reserve Hold - 反手
     * 
     * direction(Drection) -> 一樣是相反
     * LimitPrice -> 根據最新Product計算出
     * priceType = 1 -> 市价
     * orderNum -> 原本orderNum * 2
     */
    confirmReverseHoldDialog(holdPosition) {
        const product = this.quotationStore.getProduct(holdPosition.productName);
        const price = TradeUtil.getMarketPrice(product.lastPrice, product.miniTikeSize, holdPosition.direction.value, product.dotSize);
        if(this.type){
        this.tradeSend.insertOrder(product.exchangeNo, product.commodityNo, product.contractNo, holdPosition.holdNum, this.getReverseDirection(holdPosition.direction), Enum.priceType.market.value, price, 0, TradeUtil.getOrderRef(),2);
        this.tradeSend.insertOrder(product.exchangeNo, product.commodityNo, product.contractNo, holdPosition.holdNum, this.getReverseDirection(holdPosition.direction), Enum.priceType.market.value, price, 0, TradeUtil.getOrderRef(),1);
        }else{
        this.tradeSend.insertOrder(product.exchangeNo, product.commodityNo, product.contractNo, holdPosition.holdNum * 2, this.getReverseDirection(holdPosition.direction), Enum.priceType.market.value, price, 0, TradeUtil.getOrderRef());
        }
        this.setIsReverseHoldDialogVisbile(false);
    }
    cancelReverseHoldDialog() {
        this.setIsReverseHoldDialogVisbile(false);
    }
    @action setIsReverseHoldDialogVisbile(isVisible) {
        this.isReverseHoldDialogVisible = isVisible;
    }
    /**
     * 止损止盈
     */
    confirmStopLoss() {
        // 止盈
        if (this.stopLossType === Enum.stopLossType.stopWin.value) {
            this.stopPrice = _.toNumber(this.stopWinForm.stopWinPrice);
            this.stopNumber = _.toNumber(this.stopWinForm.stopWinNum);
        // 止损
        } else {
            this.stopPrice = _.toNumber(this.stopLossForm.stopLossPrice);
            this.stopNumber = _.toNumber(this.stopLossForm.stopLossNum);
        }
        // validation
        const stopProduct = this.quotationStore.getProduct(this.stopHoldPosition.productName);
        if (!stopProduct) {
            this.showToastRootStopLoss('无效的合约');
            return;
        }
        if (!TradeUtil.isPriceValid(this.stopPrice)) {
            this.showToastRootStopLoss('请输入正确的回撤价');
            return;
        }
        if (!TradeUtil.validateNum(this.stopNumber)) {
            return false;
        }

        const { lastPrice } = stopProduct;
        const direction = this.stopHoldPosition.direction.value;
        // 止损
        if (this.stopLossType === 0 || this.stopLossType === 2) { // stopLossType.stopLoss
            // 止损价
            if (this.stopLossForm.stopLossType === Enum.stopLossType.stopLoss.value) {
                if (!TradeUtil.validatePrice(this.stopPrice, stopProduct)) {
                    return false;
                }
                // 买
                if (direction === 0) {
                    if (lastPrice <= this.stopPrice) {
                        this.showToastRootStopLoss('输入价格应小于最新价');
                        return;
                    }
                // 卖
                } else {
                    if (lastPrice >= this.stopPrice) {
                        this.showToastRootStopLoss('输入价格应大于最新价');
                        return;
                    }
                }
                this.stopLossDiff = Math.abs(lastPrice - this.stopPrice);
            // 动态价
            } else {
                this.stopLossDiff = this.stopPrice;
            }

        // 止盈
        } else if (this.stopLossType === 1) {   // topLossType.stopWin
            if (!TradeUtil.validatePrice(this.stopPrice, stopProduct)) {
                return false;
            }
            // 买
            if (direction === 0) {
                if (lastPrice >= this.stopPrice) {
                    this.showToastRootStopLoss('输入价格应大于最新价');
                    return;
                }
            // 卖
            } else {
                if (lastPrice <= this.stopPrice) {
                    this.showToastRootStopLoss('输入价格应小于最新价');
                    return;
                }
            }
            this.stopLossDiff = Math.abs(this.stopPrice - lastPrice);
        }

        if (this.stopLossDiff === 0) {
            this.showToastRootStopLoss('此价差会导致立即触发,请重新设置');
            return;
        }
        this.setIsStopLossDialogVisbile(true);
    }
    cancelStopLoss() {
        this.setIsStopLossVisbile(false);
    }
    @action setIsStopLossVisbile(isVisible) {
        this.isStopLossVisible = isVisible;
    }
    showToastRootStopLoss(msg) {
        ToastRoot.show(msg, { position: Config.toastRoot.position.bottom });
    }
    /**
     * 確認
     * 止损止盈
     */
    @action confirmStopLossDialog() {
        const product = this.quotationStore.getProduct(this.stopHoldPosition.productName);
        
        if (this.isStopLossList) {
            this.stopLossSelected.stopLossType = this.stopLossType;
            this.stopLossSelected.num = this.stopNumber;
            this.stopLossSelected.stopLossDiff = this.stopLossDiff;
            this.stopLossSelected.stopLossPrice = this.stopPrice;

            this.tradeSend.modifyStopLoss(Enum.accordionItemButtonType.modify.value, this.stopLossSelected);
            
            this.stopLossSelected = null;
            this.cancelStopLossDialog();
            setTimeout(() => {
                this.cancelStopLoss();
            }, Config.waitingTime);
        } else {
            this.tradeSend.insertStopLoss(product.exchangeNo, product.commodityNo, product.contractNo, this.stopNumber, this.stopLossType, this.stopLossDiff, this.stopHoldPosition.holdAvgPrice, this.stopHoldPosition.direction.value, 1, this.stopPrice);

            this.cancelStopLossDialog();
            setTimeout(() => {
                this.cancelStopLoss();
            }, Config.waitingTime);
            setTimeout(() => {
                this._toStopLossList();
            }, Config.waitingTime * 2);
        }
    }
    cancelStopLossDialog() {
        this.setIsStopLossDialogVisbile(false);
    }
    @action setIsStopLossDialogVisbile(isVisible) {
        this.isStopLossDialogVisible = isVisible;
    }
    _toStopLossList() {
        this.navigator.showModal({
            screen: 'trade.StopLossListModal',
            title: '止损单',
            navigatorButtons: {
            leftButtons: [
                {
                    icon: Variables.icon.closeStopLoss.source,
                    id: Variables.icon.closeStopLoss.id
                },
                {
                    icon: Variables.icon.exclamationStopLoss.source,
                    id: Variables.icon.exclamationStopLoss.id
                }
            ]
            }
        }); 
    }
    getReverseDirection(direction) {
        if (direction.value === 0) {
            return 1;
        }
        return 0;
    }
}

