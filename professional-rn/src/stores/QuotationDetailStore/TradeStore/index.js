/*
    部分資料混合了兩個WebSocket的資料，並且需要進行計算

    命名原則
    http://nettavern.mysinablog.com/index.php?op=ViewArticle&articleId=2309214
    https://www.hkex.com.hk/chi/global/documents/glossary_c.pdf

    倉：position
    持倉: holdPositions,

    平倉: closed position
    市價掛單 = market order 
    定價掛單 = limit order

    class: 1.HoldPosition 2.Deignate 3.Order 4.Deal

    currency: 應該是System Level的設定，放在Config. (又不會整天轉換你要顯示的匯率)
    幣別(CurrencyNo)，匯率轉換(cr - currentRate)

    toFixed() 會將 Number 轉成 string -> 平時的資料意義是數字，只有顯示時是string -> 用@computed，但不影響計算的就直接toFixed吧
*/

import { observable, action, computed, toJS, autorun } from 'mobx';
import _ from 'lodash';
import { Colors, Variables, Config, Enum } from '../../../global';
import { HoldPosition, Designate, Order, Deal, Cache } from './models';
import { ToastRoot } from '../../../components';
import { Logger, I18n, TradeUtil, ArrayUtil } from '../../../utils';

export default class TradeStore {
    type = true; //to fix ... true 内盘 false 外盘
    logger = null;
    // store
    quotationStore = null;
    tradeNumStore = null;
    tradeOptionStore = null;
    
    product = null; // 當前處理的current product

    constructor(quotationStore, tradeNumStore, tradeOptionStore) {
        this.logger = new Logger(TradeStore);
        this.quotationStore = quotationStore;
        this.tradeNumStore = tradeNumStore;
        this.tradeOptionStore = tradeOptionStore;
        // 用來計算updateAccountMoney(updateBalance)
        this.cache = new Map(); // key" 'USU || HKD-HKFE || EUR || JPY || CNY', value: Cache

        // autorun(() => {
        //     this.logger.trace(`balanceText: ${this.balanceText}, canUseText: ${this.canUseText}, totalFloatProfit: ${this.totalFloatProfit} riskText: ${this.riskText}, deposit: ${this.deposit}`);
        // });
    }
    // TradeQuotationView
    @observable preSettlePrice;     // 昨日結算價 - 也就是 - 今日開盤價

    @observable lastPrice;          // 新
    @computed get lastPriceColor() {
        return Colors.getColorText(this.lastPrice - this.preSettlePrice);
    }
    @observable totalVolumn;
    
    @observable changeValue;
    @observable changeRate;
    @computed get changeRateColor() {
        return Colors.getColorText(this.changeValue);
    }

    @observable sellLastPrice;      // 卖
    @computed get sellLastPriceColor() {
        return Colors.getColorText(this.sellLastPrice - this.preSettlePrice);
    }
    @observable askQty;

    @observable buyLastPrice;       // 买
    @computed get buyLastPriceColor() {
        return Colors.getColorText(this.buyLastPrice - this.preSettlePrice);
    }
    @observable bidQty;
    // TradeAccountView
    cache = null;
    @observable balance;            // 总资产(USD)
    @observable canUse;             // 余额(USD)
    deposit;                        // 保證金
    @observable forceLine;          // 平仓线 - 原名字 loss-Open-line
    @observable initBalance;        // 初始資產 - 用來計算風險度
    @observable totalFloatProfit;   // 總和浮動盈虧 - 用來計算風險度
    // TradeListView
    // 持倉
    @observable holdPositions;  // 持倉, Map - https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/Map
    // 掛單
    @observable designates;
    // 委託
    @observable orders;
    // 成交
    @observable deals;

    @computed get balanceText() {
        return (this.balance + this.totalFloatProfit).toFixed(2);
    }
    @computed get canUseText() {
        return (this.canUse + this.totalFloatProfit).toFixed(2);
    }
    @computed get riskText() {
        // 风险度 - 根據每一筆持倉的浮動盈虧 來計算你的風險度
        const balanceWithFloatProfit = this.balance + this.totalFloatProfit;
        const diffBalance = this.initBalance - balanceWithFloatProfit;
        const diffBalanceWithForce = this.initBalance - this.forceLine;
        let riskText = '0.00%';
        
        if (!isNaN(diffBalance / diffBalanceWithForce)) {    
            if (diffBalance / diffBalanceWithForce > 0) {
                riskText = `${((diffBalance * 100) / diffBalanceWithForce).toFixed(2)}%`;
            }
        }
        return riskText;
    }
    // function名不叫start的原因是，其實交易中心是獨立的
    setCurrentProduct(productName) {
        this.product = this.quotationStore.getProduct(productName);
        this.tradeNumStore.reset(this.product);
        this.tradeOptionStore.reset(this.product);
        // 第一次 由TradeStore自己發出，因為OnRtnQuote不一定隨時馬上更新
        this.updateTradeQuotation(this.product);
    }
    @action initData(param) {
        param.ForceLine && (this.forceLine = param.ForceLine);
        param.InitBalance && (this.initBalance = param.InitBalance);
    }
    // 只有自己在initData之前call reset()
    // TradeStore 跟 QuotationDetailStore是分開的資料
    // TradeStore只有部分跟Product有關，也用setCurrentProduct() -> updateTradeQuotation() 解決了
    @action reset() {
        if (!Variables.trade.isLogged) {
            return;
        }
        this.balance = 0;
        this.canUse = 0;
        this.deposit = 0;
        this.risk = 0;
        this.forceLine = 0;
        this.initBalance = 0;
        this.totalFloatProfit = 0;
        this.holdPositions = new Map();
        this.designates = [];
        this.orders = [];
        this.deals = [];
    }
    // 原本updateBalance
    // 持仓：原油1手，亏损30美金  恒指1手，盈利50港币    德指1手， 亏损12.5欧元  美金对人民币，盈利 10人民币 ， 日经225 1手，亏损10000日元
    // 因此是：30+50*港币汇率-12.5*欧元对美金汇率+10人民币汇率-10000日元汇率
    @action updateAccountMoney(param) {
        const currencyNo = param.CurrencyNo;  // 幣別 : USD, EUR, JPY, CNY, HKD-HKFE
        const currencyRate = param.CurrencyRate;  // 匯率
        const todayAmount = param.TodayAmount;
        const deposit = param.Deposit;
        const frozenMoney = param.FrozenMoney;

        const balance = todayAmount;
        const canUse = balance - deposit - frozenMoney;
        // 有時候後端傳回來的資料會有undefined, null...，這時候放棄此筆資料
        // this.logger.warn(`updateAccountMoney - balance: ${balance}, canUse: ${canUse}, deposit: ${deposit}`);
        if (isNaN(balance) || isNaN(canUse) || isNaN(deposit)) {
            return;
        }
        // 只有第一次updateAccountMoney會傳currentRate，之後沒傳
        if (this.cache.has(currencyNo)) {
            const existCache = this.cache.get(currencyNo);
            existCache.balance = balance;
            existCache.canUse = canUse;
            existCache.deposit = deposit;
            this.cache.set(currencyNo, existCache);
        } else {
            this.cache.set(currencyNo, new Cache(currencyRate, balance, canUse, deposit));
        }
        // 每一次都重新匯總
        this.balance = 0;
        this.canUse = 0;
        this.deposit = 0;
        // iterate c: [key, value]
        for (const c of this.cache) {
            const value = c[1];
            this.balance = this.balance + (value.balance * value.currencyRate);
            this.canUse = this.canUse + (value.canUse * value.currencyRate);
            this.deposit = this.deposit + (value.deposit * value.currencyRate);
        }
        // 因為有多國貨幣的選擇(但現在只有USD)，所以原本要考慮currenecy rate(cr)
    }
    // --------------------- 持仓 HoldPosition -----------------------
    manageHold(param) {
        // 合约, key for the holdPositions(Map)
        const product = this.getProduct(param);
        this.logger.warn(`manageHold - productName: ${product.productName}`);
        // 手數
        const holdNum = param.HoldNum;
        const productName = this.type ? `${product.productName}${param.Drection}`:product.productName;
        if (holdNum === 0) {
            this.deleteHold(productName);
            return;
        }
        if (this.holdPositions.has(productName)) {
            this.logger.debug(`has: productName: ${product.productName}`);
            this.updateHold(product, param);
        } else {
            this.logger.debug(`no has: productName: ${product.productName}`);
            this.addHold(product, param);
        }
    }
    setHoldPosition(product, param) {
        const holdNum = param.HoldNum;
        // 持仓方向
        const direction = param.Drection;
        const directionObj = Enum.direction[param.Drection];
        const holdAvgPrice = _.toNumber(param.HoldAvgPrice.toFixed(product.dotSize));
        // 浮动盈亏
        const floatProfit = this.getFloatProfit(product.lastPrice, holdAvgPrice, product.contractSize, product.miniTikeSize, holdNum, direction, product.currencyNo);

        return {
            productName: product.productName,
            direction: directionObj,
            holdNum,
            holdAvgPrice,
            floatProfit,
            //不顯示
            dotSize: product.dotSize,
            currencyNo: product.currencyNo
        };
    }
    getHoldPosition(param) {
        return this.holdPositions.get(this.getProductName(param));
    }
    @action updateHold(product, param) {
        const productName = this.type ? `${product.productName}${param.Drection}`:product.productName;
        const holdPosition = this.holdPositions.get(productName);
        holdPosition.update(this.setHoldPosition(product, param));
    }
    @action addHold(product, param) {
        const productName = this.type ? `${product.productName}${param.Drection}`:product.productName;
        this.holdPositions.set(productName, new HoldPosition(this.setHoldPosition(product, param)));
    }
    @action deleteHold(productName) {
        this.holdPositions.delete(productName);
    }
    // --------------------- 掛單 Designate -----------------------
    // OnRspQryOrder, OnRspOrderInsert
    addDesignateAndOrder(param, isInsert) {
        const orderStatus = param.OrderStatus;
        this.addOrder(param, isInsert);
        if (orderStatus < 3) {
            this.addDesignate(param, isInsert);
        }
    }
    // OnRspOrderInsert
    addDesignateAndOrderMessage(param) {
        const orderStatus = param.OrderStatus;
        if (orderStatus === 5) {
            if(param.ContractCode == undefined){
                ToastRoot.show(`交易失败:原因【${param.StatusMsg}】`);
            }else{
                ToastRoot.show(`交易失败:合约【${param.ContractCode}】,原因【${param.StatusMsg}】`);
            }
        } else {
            ToastRoot.show(I18n.message('submit_order_success'));
        }
    }
    manageDesignateAndOrder(param) {
        const orderStatus = param.OrderStatus;
        const orderNum = param.OrderNum;          // 委託量
        const tradeNum = param.TradeNum;          // 已成交
        const designateNum = orderNum - tradeNum; // 掛單量
        const orderId = param.OrderID;
        // this.logger.warn(`manageDesignate - orderStatus: ${orderStatus}, orderNum: ${orderNum}, tradeNum: ${tradeNum}, designateNum: ${designateNum}, orderId: ${orderId}`);
        this.updateOrder(param);
        if (orderStatus === 0 || orderStatus === 1 || orderStatus === 2) {
            if (designateNum === 0) {
                this.deleteDesignate(param);
            } else {
                this.updateDesignate(param);
            }
        } else if (orderStatus === 3) {
            this.deleteDesignate(orderId);
        } else if (orderStatus === 4) {
            this.deleteDesignate(orderId);
            ToastRoot.show(`撤单成功:合约【${param.ContractCode}】,订单号【${orderId}】`);
        } else if (orderStatus === 5) {
            this.deleteDesignate(orderId);
            ToastRoot.show(`交易失败:合约【${param.ContractCode}】,原因【${param.StatusMsg}】`);
        }
    }
    @action deleteDesignate(param) {
        const product = this.getProduct(param);
        if (this.designates.length === 0) {
            return;
        }
        // orderId
        if (typeof param === 'string') {
            ArrayUtil.remove(this.designates, (designate) => {
                return designate.orderId === param;
            });
            return;
        }
        // typeof param === 'object'
        ArrayUtil.remove(this.designates, (designate) => {
            return designate.productName === product.productName;
        });
    }
    // updateDesignatesDom
    @action updateDesignate(param) {
        const product = this.getProduct(param);
        const orderPriceText = this.getOrderPriceText(param, product.dotSize);
        const orderId = param.OrderID;
        const orderNum = param.OrderNum;          // 委託量
        const tradeNum = param.TradeNum;          // 已成交
        const designateNum = orderNum - tradeNum; // 掛單量
        // this.logger.warn(`updateDesignate - orderPriceText: ${orderPriceText}, orderNum: ${orderNum}, tradeNum: ${tradeNum}, designateNum: ${designateNum}`);

        const designate = this.designates.find((d) => {
            return d.orderId === orderId;
        });
        designate.update(orderPriceText, orderNum, designateNum);
        if (designate.isUpdate) {
            ToastRoot.show(`改单成功:合约【${designate.productName}】,委托价【${designate.orderPrice}】,委托量【${designate.orderNum}】`);
            designate.isUpdate = false;
        }
    }
    // appendDesignates
    @action addDesignate(param, isInsert) {
        const product = this.getProduct(param);
        const directionObj = Enum.direction[param.Drection];
        const orderPriceText = this.getOrderPriceText(param, product.dotSize);
        const orderNum = param.OrderNum;          // 委託量
        const tradeNum = param.TradeNum;          // 已成交
        const designateNum = orderNum - tradeNum; // 掛單量
        if (isInsert) {
            this.designates.unshift(new Designate(product.productName, directionObj, orderPriceText, orderNum, designateNum, param.InsertDateTime, param.OrderID, param.OrderSysID, param.TriggerPrice));
        } else {
            this.designates.push(new Designate(product.productName, directionObj, orderPriceText, orderNum, designateNum, param.InsertDateTime, param.OrderID, param.OrderSysID, param.TriggerPrice));
        }
    }
    // --------------------- 委託 Order -----------------------
    // appendOrder
    @action addOrder(param, isInsert) {
        const product = this.getProduct(param);
        if (!product) {
            return;
        }
        const directionObj = Enum.direction[param.Drection];
        const orderPriceText = this.getOrderPriceText(param, product.dotSize);
        const orderStatus = param.OrderStatus;
        const ordreStatusText = TradeUtil.getOrderStatusText(orderStatus);
        const orderNum = param.OrderNum;    // 委託量
        const tradeNum = param.TradeNum;    // 已成交
        let cdNum = 0; //撤單
        if (orderStatus === 4) {
            cdNum = orderNum - tradeNum;
        }
        if (isInsert) {
            this.orders.unshift(new Order(product.productName, ordreStatusText, directionObj, orderPriceText, orderNum, tradeNum, cdNum, param.InsertDateTime, param.OrderID));
        } else {
            this.orders.push(new Order(product.productName, ordreStatusText, directionObj, orderPriceText, orderNum, tradeNum, cdNum, param.InsertDateTime, param.OrderID));
        }
    }
    @action updateOrder(param) {
        const orderPrice = param.OrderPrice;
        const orderStatus = param.OrderStatus;
        const tradeNum = param.TradeNum;
        const orderNum = param.OrderNum;

        const product = this.getProduct(param);
        const orderId = param.OrderID;
        // this.logger.warn(`updateOrder - orderId: ${orderId}, orderStatus: ${orderStatus}`);
        const order = this.orders.find((o) => {
            return o.orderId === orderId;
        });
        // if(order == undefined){
        //     return;//to fix ...条件单也会触发order？？？
        // }
        if (order.orderPrice !== Enum.priceType.market.text) {
            order.orderPrice = orderPrice.toFixed(product.dotSize);
        }
        order.orderStatus = TradeUtil.getOrderStatusText(orderStatus);
        if (orderStatus === 4) {
            order.cdNum = orderNum - tradeNum;
        }
        order.orderNum = orderNum;
        order.tradeNum = tradeNum;
    }
    // --------------------- 成交 Deal -----------------------
    // appendTradeSuccess
    @action addDeal(param) {
        const product = this.getProduct(param);
        const directionObj = Enum.direction[param.Drection];
        let tradePrice = param.TradePrice;
        tradePrice = tradePrice.toFixed(product.dotSize);
        this.deals.push(new Deal(param.ContractCode, directionObj, tradePrice, param.TradeNum, param.TradeDateTime, param.OrderID));
    }
    getOrderPriceText(param, dotSize) {
        let orderPriceText = param.OrderPrice.toFixed(dotSize);
        const orderPriceType = param.OrderPriceType;
        if (orderPriceType === 1) {
            orderPriceText = Enum.priceType.market.text;
        }
        return orderPriceText;
    }
    // 原本的localCommodity
    getProduct(param) {
        // 行情有記錄下所有的品種，
        return this.quotationStore.getProduct(this.getProductName(param));
    }
    getProductName(param) {
        return `${param.CommodityNo}${param.ContractNo}`;
    }
    // 計算浮动盈亏, lastPrice 最新价, holdAvgPrice 均价, contractSize 每手乘数, miniTikeSize 最小变动单位, holdNum 订单量(手數), drection 买卖方向（0-买，1-买), currencyNo 幣別
    getFloatProfit(lastPrice, holdAvgPrice, contractSize, miniTikeSize, holdNum, direction, currencyNo) {
        let price = 0;
        if (direction === 0) {
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
    // 從QuotationSocket的OnRtnQuote來, 更新每一筆筆持倉的浮动盈亏
    // 計算方式：得到的最新價，再去計算浮動盈虧，更新該筆持倉-浮动盈亏
    // 原本的 sumListfloatingProfit, updateAccountBalance
    @action updateFloatProfitAndRisk(param) {
        // 更新每一筆浮動盈虧
        const holdPosition = this.getHoldPosition(param);
        if (holdPosition !== undefined) {
            const product = this.quotationStore.getProduct(holdPosition.productName);
            holdPosition.floatProfit = this.getFloatProfit(param.LastPrice, holdPosition.holdAvgPrice, product.contractSize, product.miniTikeSize, holdPosition.holdNum, holdPosition.direction.value, product.currencyNo);
        }
        // 每一筆的持倉 根據不同貨幣，計算出USD總和
        let tempTotalFloatProfit = 0;
        // iterate h: [key, value]
        for (const h of this.holdPositions) {
            if (h[1]) {
                try {
                    const currencyRate = this.cache.get(h[1].currencyNo).currencyRate;
                    tempTotalFloatProfit += h[1].floatProfit.value * currencyRate;
                } catch (e) {
                    this.logger.error(`updateFloatProfitAndRisk - h[1]: ${h[1]}, cache: ${this.cache.get(h[1].currencyNo)}`);
                }
            }
        }
        this.totalFloatProfit = _.toNumber(tempTotalFloatProfit.toFixed(2));
    }
    // 第一次 由TradeStore自己發出，因為OnRtnQuote不一定隨時馬上更新
    @action updateTradeQuotation(param) {
        //to do ....
        // if (!param || !this.product || isNaN(param.last) || isNaN(param.ask) || isNaN(param.bid)) {
        //     return;
        // }
        const dotSize = this.product.dotSize;
        this.preSettlePrice = param.pre_settle;

        this.lastPrice = param.last.toFixed(dotSize);
        this.totalVolumn = param.volume;
        
        this.changeValue = param.change_value;
        this.changeRate = `${param.change_rate.toFixed(2)}%`;

        this.sellLastPrice = param.ask[0][0].toFixed(dotSize);
        this.askQty = param.ask[0][1];

        this.buyLastPrice = param.bid[0][0].toFixed(dotSize);
        this.bidQty = param.bid[0][1];


    }
}

