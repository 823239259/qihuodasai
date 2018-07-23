import axios from 'axios';
import { toJS } from 'mobx';
import _ from 'lodash';
import { Config } from '../global';
import Logger from '../utils/Logger';

export default class TradeSend {
    logger = null;
    constructor() {
        this.logger = new Logger(TradeSend);
    }
    setSocket(socket) {
        this.socket = socket;
    }
    sendMessage(method, parameters) {
        try {
            this.socket.send(`{"Method":"${method}","Parameters":${parameters}}`);
        } catch (err) {
            this.logger.error(err);
        }
    }
    login(username, password) {
        const loginSend = `{"ClientNo":"${username}","PassWord":"${password}","IsMock": ${Config.tradeSocket.isMock},"Version":"${Config.tradeSocket.version}","Source":"${Config.tradeSocket.source}"`;
        this.sendMessage('Login', loginSend);
    }
    logout(username) {
        const logoutSend = `{"ClientNo": "${username}"}`;
        this.sendMessage('Logout', logoutSend);
    }
    // 获取个人账户信息
    qryAccount(username) {
        const qryAccountSend = `{"ClientNo": "${username}"}`;
        this.sendMessage('QryAccount', qryAccountSend);
    }
    // 查询持仓信息
    qryHold(username) {
        const qryHoldSend = `{"ClientNo": "${username}"}`;
        this.sendMessage('QryHoldTotal', qryHoldSend);
    }
    // 获取订单信息
    qryOrder(username) {
        const qryOrder = `{"ClientNo": "${username}"}`;
        this.sendMessage('QryOrder', qryOrder);
    }
    //  查询成交记录信息
    qryTrade(username) {
        const qryTrade = `{"ClientNo": "${username}"}`;
        this.sendMessage('QryTrade', qryTrade);
    }
    /**
     * 报单录入请求
     * @param {Object} exchangeNo 交易所代码
     * @param {Object} commodeityNo 品种代码
     * @param {Object} contractNo 合约代码
     * @param {Object} orderNum 订单数量
     * @param {Object} drection 买卖方向（0：买，1：卖）
     * @param {Object} priceType 价格类型:限价0，市价1，止损2
     * @param {Object} limitPrice 订单价格
     * @param {Object} triggerPrice 触发价(止损单需要)
     * @param {Object} orderRef 报单引用，用户自己生成
     * 
     * -> OnRspOrderInsert
     */ 
    insertOrder(exchangeNo, commodeityNo, contractNo, orderNum, drection, priceType, limitPrice, triggerPrice, orderRef, openCloseType) {
        const param = `{"ExchangeNo":"${exchangeNo}", "CommodityNo":"${commodeityNo}", "ContractNo":"${contractNo}", "OrderNum":${_.toNumber(orderNum)},`
                    + ` "Drection":${drection}, "PriceType":${priceType}, "LimitPrice":${_.toNumber(limitPrice)}, "TriggerPrice":${triggerPrice}, "OrderRef":"${orderRef}", "OpenCloseType":${openCloseType}`;                 
        this.sendMessage('InsertOrder', param);
    }
    /**
     * 撤单请求
     * @param {Object} orderSysId 系统编号
     * @param {Object} orderId 订单号
     * @param {Object} exchangeNo 交易所代码
     * @param {Object} commodityNo 品种代码
     * @param {Object} contractNo 合约代码
     * @param {Object} orderNum 订单数量
     * @param {Object} drection 买卖方向（0：买，1：卖）
     * @param {Object} orderPrice 订单价格
     */
    cancelOrder(orderSysId, orderId, exchangeNo, commodityNo, contractNo, orderNum, drection, orderPrice) {
        const param = `{"OrderSysID":"${orderSysId}", "OrderID":"${orderId}", "ExchangeNo":"${exchangeNo}", "CommodityNo":"${commodityNo}",`
        + ` "ContractNo":"${contractNo}", "OrderNum":${orderNum}, "Drection":${drection}, "OrderPrice":${orderPrice}}`;
        this.sendMessage('CancelOrder', param);
    }
    /**
     * 改单请求
     * @param {Object} orderSysId 系统编号
     * @param {Object} orderId 订单号
     * @param {Object} exchangeNo 交易所代码
     * @param {Object} commodityNo 品种代码
     * @param {Object} contractNo 合约代码
     * @param {Object} orderNum 订单数量
     * @param {Object} drection 买卖方向（0：买，1：卖）
     * @param {Object} orderPrice 订单价格
     * @param {Object} triggerPrice 触发价格
     */
    modifyOrder(orderSysId, orderId, exchangeNo, commodityNo, contractNo, orderNum, drection, orderPrice, triggerPrice) {
        const param = `{"OrderSysID":"${orderSysId}", "OrderID":"${orderId}", "ExchangeNo":"${exchangeNo}", "CommodityNo":"${commodityNo}",`
        + ` "ContractNo":"${contractNo}", "OrderNum":${orderNum}, "Drection":${drection}, "OrderPrice":${orderPrice}, "TriggerPrice":${triggerPrice}}`;
        this.sendMessage('ModifyOrder', param);
    }
    /* --------------------------- 止损单 ----------------------*/
    // 获取止损单请求
    qryStopLoss(username) {
        const qryStopLoss = `{"ClientNo": "${username}"}`;
        this.sendMessage('QryStopLoss', qryStopLoss);
    }
    // 增加止损单
    insertStopLoss(exchangeNo, commodityNo, contractNo, num, stopLossType, stopLossDiff, holdAvgPrice, holdDrection, orderType, stopLossPrice) {
        const param = `{"ExchangeNo":"${exchangeNo}", "CommodityNo":"${commodityNo}", "ContractNo":"${contractNo}", "Num":${num},`
        + ` "StopLossType":${stopLossType}, "StopLossDiff":${stopLossDiff}, "HoldAvgPrice":${holdAvgPrice}, "HoldDrection":${holdDrection}, "OrderType":${orderType}, "StopLossPrice":${stopLossPrice}}`;
        this.sendMessage('InsertStopLoss', param);
        // this.logger.warn(param);
    }
    modifyStopLoss(modifyFlag, { stopLossNo, num, stopLossType, orderType, stopLossDiff, stopLossPrice }) {
        const param = `{"ModifyFlag":${modifyFlag}, "StopLossNo":"${stopLossNo}", "Num":${num}, "StopLossType":${stopLossType},`
        + ` "OrderType":${orderType}, "StopLossDiff":${stopLossDiff}, "StopLossPrice":${stopLossPrice}}`;
        this.sendMessage('ModifyStopLoss', param);
        // this.logger.warn(param);
    }
    /* --------------------------- 条件单 ----------------------*/
    // 条件单查询
    qryCondition(username) {
        const qryCondition = `{"ClientNo": "${username}"}`;
        this.sendMessage('QryCondition', qryCondition);
    }
    insertCondition(exchangeNo, commodityNo, contractNo, num, conditionType, triggerPrice, compareType, triggerTime, abBuyPoint, abSellPoint, 
                    orderType, direction, stopLossType, stopLossDiff, stopWinDiff, additionFlag, additionType, additionPrice) {
        const param = `{"ExchangeNo":"${exchangeNo}", "CommodityNo":"${commodityNo}", "ContractNo":"${contractNo}", "Num":${num},`
        + ` "ConditionType":${conditionType}, "PriceTriggerPonit":${triggerPrice}, "CompareType":${compareType}, "TimeTriggerPoint":"${triggerTime}", "AB_BuyPoint":${abBuyPoint}, "AB_SellPoint":${abSellPoint},`
        + ` "OrderType":${orderType}, "Drection":${direction}, "StopLossType":${stopLossType}, "StopLossDiff":${stopLossDiff}, "StopWinDiff":${stopWinDiff}, "AdditionFlag":${additionFlag}, "AdditionType": ${additionType}, "AdditionPrice": ${additionPrice}}`;
        this.sendMessage('InsertCondition', param);
    }
    modifyCondition(modifyFlag, conditionNo, num, conditionType, triggerPrice, compareType, triggerTime, abBuyPoint, abSellPoint, 
                    orderType, direction, stopLossType, stopLossDiff, stopWinDiff, additionFlag, additionType, additionPrice) {
        const param = `{"ConditionNo":"${conditionNo}", "ModifyFlag":${modifyFlag}, "Num":${num},`
        + ` "ConditionType":${conditionType}, "PriceTriggerPonit":${triggerPrice}, "CompareType":${compareType}, "TimeTriggerPoint":"${triggerTime}", "AB_BuyPoint":${abBuyPoint}, "AB_SellPoint":${abSellPoint},`
        + ` "OrderType":${orderType}, "Drection":${direction}, "StopLossType":${stopLossType}, "StopLossDiff":${stopLossDiff}, "StopWinDiff":${stopWinDiff}, "AdditionFlag":${additionFlag}, "AdditionType": ${additionType}, "AdditionPrice": ${additionPrice}}`;
        this.sendMessage('ModifyCondition', param);
    }
    // 查询历史成交记录
    qryHistoryTrade(clientNo, beginTime, endTime) {
        const param = `{"ClientNo":"${clientNo}", "BeginTime":"${beginTime}", "EndTime":"${endTime}"}`;
        this.logger.warn(param);
        this.sendMessage('QryHisTrade', param);
    }
    // OnRtnOrderTraded

}

