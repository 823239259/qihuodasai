/*
    交易帳號有登入過，就連接Trade Websocket
    登出的時候才關閉Trade WebSocket
*/
import { AsyncStorage } from 'react-native';
import base64 from 'base-64';
import { Config, Variables, Enum } from '../global';
import { ToastRoot } from '../components';
import { Logger, I18n } from '../utils';

export default class TradeSocket {
    socket = null;
    logger = null;
    eventEmitter = null;

    isHeartBeating = false;
    // store
    tradeStore = null;
    tradeSend = null;
    workbenchDetailStore = null;
    quotationDetailStore = null;
    stopLossStore = null;
    historyTradeStore= null;
    conditionStore = null;
    tradeLoginModalStore = null;
    futureTypeStore = null;

    constructor(tradeStore, tradeSend, workbenchDetailStore, quotationDetailStore, stopLossStore, historyTradeStore, conditionStore, tradeLoginModalStore, futureTypeStore) {
        this.logger = new Logger(TradeSocket);
        this.tradeStore = tradeStore;
        this.tradeSend = tradeSend;
        this.workbenchDetailStore = workbenchDetailStore;
        this.quotationDetailStore = quotationDetailStore;
        this.stopLossStore = stopLossStore;
        this.historyTradeStore = historyTradeStore;
        this.conditionStore = conditionStore;
        this.tradeLoginModalStore = tradeLoginModalStore;
        this.futureTypeStore = futureTypeStore;
    }
    setEventEmitter(eventEmitter) {
        this.eventEmitter = eventEmitter;
    }
    connectSocket(account, password, onTradeLoginSuccess) {
        return new Promise((resolve, reject) => {
            this.socket = new WebSocket(this.futureTypeStore.isFutIn ? Config.tradeSocket.futinUrl :Config.tradeSocket.futoutUrl);
            this.tradeSend.setSocket(this.socket);

            this.socket.onopen = () => {
                this.logger.trace('onopen');
                this.login(account, password);
            };
            this.socket.onclose = (e) => {
                this.logger.error(`onclose - code: ${e.code}, reason: ${e.reason}, readyState: ${Enum.socketState[this.socket.readyState]}`);
                const reason = e.reason ? e.reason : Enum.tradeSocketConnectionMode.close;
                reject({ msg: reason });
                
                // this.emit(); // 強制一直再次連線，先留著
            };
            this.socket.onmessage = (evt) => {
                const dataString = evt.data;
                const data = JSON.parse(dataString);
                const method = data.Method;
                const parameters = data.Parameters;
                if (parameters) {
                    if (method === 'OnRspLogin') {
                        const code = parameters.Code;
                        if (code === 0) {
                            this.onLoginSuccess(account, password, parameters, onTradeLoginSuccess);
                            resolve({ msg: Enum.tradeSocketConnectionMode.loginSuccess, param: { account } });
                        } else {
                            this.onLoginError(account, password, parameters.Message);
                            reject({ msg: Enum.tradeSocketConnectionMode.loginError });
                        }
                    } else if (method === 'OnRspLogout') {
                        this.logger.warn(`OnRspLogout - code: ${parameters.Code}`);
                        // 1.別人擠掉自己時，會接收到onRspLogout, 2.自己發出logout, tradeSend.logout()
                        this.logoutSuccess();
                    } else if (method === 'OnRspQryOrder') {    // qryOrder
                        this.tradeStore.addDesignateAndOrder(parameters, false);
                    } else if (method === 'OnRspQryTrade') {    // qryTrade
                        this.tradeStore.addDeal(parameters,false);
                    } else if (method === 'OnRspQryHisTrade') {
                        this.historyTradeStore.manageHistoryTrade(parameters);
                    } else if (method === 'OnRspQryAccount') {  // qryAccount
                        // todayBalance, todayCanUse, openRiskDegree
                        this.tradeStore.updateAccountMoney(parameters);
                    } else if (method === 'OnRspQryHoldTotal') { // qryHold 查询持仓信息回复
                        this.tradeStore.manageHold(parameters);
                    } else if (method === 'OnRspOrderInsert') {  // insertOrder
                        this.tradeStore.addDesignateAndOrder(parameters, true);
                        this.tradeStore.addDesignateAndOrderMessage(parameters);
                    } else if (method === 'OnRtnOrderTraded') {  // 订单成交通知
                        this.tradeStore.addDeal(parameters,true);
                        const {dotSize} = this.tradeStore.product
                        //parameters.TradePrice.toFixed(2) 保留两位小数
                        ToastRoot.show(I18n.message('trade_success_info', parameters.ContractCode, parameters.TradeNum, parameters.TradePrice.toFixed(dotSize)));
                    } else if (method === 'OnRtnOrderState') {   // 订单状态通知(處理掛單)
                        this.tradeStore.manageDesignateAndOrder(parameters);
                    } else if (method === 'OnRtnMoney') {        // 资金变化通知
                        this.tradeStore.updateAccountMoney(parameters);
                        // FundsDetails資金明細，可能是不需要的程式，根本query不到#account_gdt1
                    } else if (method === 'OnRtnHoldTotal') {    // 更新持仓
                        this.tradeStore.manageHold(parameters);
                    //录入止损止盈请求返回
                    } else if (method === 'OnRspInsertStopLoss') {
                        let msg = '';
                        if (parameters.Status === 4) {
                            msg = '添加止损单失败';
                        } else {
                            msg = `提交成功,单号:【 ${parameters.StopLossNo} 】`;
                        }
                        ToastRoot.show(msg);
                        this.stopLossStore.manageStopLoss(parameters);
                    //查询止损止盈返回
                    } else if (method === 'OnRspQryStopLoss') {
                        this.stopLossStore.manageStopLoss(parameters);
                    //止损止盈状态返回   
                    } else if (method === 'OnRtnStopLossState') {
                        this.stopLossStore.updateStopLoss(parameters);
                    //查询条件单返回
                    } else if (method === 'OnRspQryCondition') {
                        this.conditionStore.manageCondition(parameters);
                    //录入条件单请求返回
                    } else if (method === 'OnRspInsertCondition') {
                        this.conditionStore.manageCondition(parameters);
                    //止损止盈状态返回   
                    } else if (method === 'OnRtnConditionState') {
                        this.conditionStore.updateCondition(parameters);
                    } else if (method === 'OnError') {
                        // 掛單-改單時 出現餘額不足的狀況
                        // todo...  后续码表优化
                        console.log(parameters);
                        if (parameters.Method === 'ModifyOrder') {
                            ToastRoot.show(parameters.Message);
                            return;
                        }
                        this.logger.error(`OnError: ${parameters.Message}`);
                        this.emit('error');
                    } else if (method === 'OnRtnHeartBeat') {
                        this.isHeartBeating = true;
                        this.logger.info(`OnRtnHeartBeat - isHeartBeating: ${this.isHeartBeating}`);
                    }
                }
            };   
        });  
    }
    onLoginSuccess(account, password, parameters, onTradeLoginSuccess) {
        this.setIsLogged(true);
        // 顯示 操盤登入成功 但是目前重新登入 會一直logout...
        ToastRoot.show(I18n.message('trade_login_success'));
        this.tradeLoginModalStore.setIsSubmitted(false);
        this.logger.info(`Trade login Success: isLogged: ${Variables.trade.isLogged}`);
        this.loginSuccessSave(account, password, onTradeLoginSuccess);
        this.initData(account, parameters);
    }
    onLoginError(account, password, loginMessage) {
        ToastRoot.show(loginMessage);
        this.tradeLoginModalStore.setIsSubmitted(false);
        this.logger.error(`login Failed: ${loginMessage}, account: ${account}, password: ${password}`); //登入失敗 - ex: 您的账户已被冻结，如有疑问请拨打在线客服. 表示此帳戶無法登入了
        this.setIsLogged(false);
        // 清除暫存登入資訊，因為登入錯誤，表示此時帳號密碼無效了
        this.clearLogged();
    }
    loginSuccessSave(account, password, onTradeLoginSuccess) {
        this.logger.info(`Trade loginSuccess account: ${account}, password: ${password}`);
        Variables.trade.account.value = account;
        Variables.trade.password.value = password;
        onTradeLoginSuccess && onTradeLoginSuccess();  // 關掉loginmodal & 移動tab到交易中心, ps: 開啟debugger會卡住

        AsyncStorage.setItem(Variables.trade.account.key, account);
        AsyncStorage.setItem(Variables.trade.password.key, password);
    }
    // 原本linearlyLoadData做的事
    initData(account, param) {
        // reset
        this.tradeStore.reset();

        this.stopLossStore.reset();
        this.conditionStore.reset();
        // init
        this.tradeStore.initData(param);
        this.tradeSend.qryAccount(account);
        this.tradeSend.qryOrder(account);
        this.tradeSend.qryTrade(account);
        this.tradeSend.qryHold(account);

        this.tradeSend.qryStopLoss(account);
        this.tradeSend.qryCondition(account);
    }
    // 1. 自動登入 2. LoginTradeModal
    login(account, password) {
        this.logger.trace(`login - account: ${account}, password: ${password}`);
        this.tradeSend.login(account, base64.encode(password));
    }
    logoutSuccess() {
        this.logger.trace('logout');
        // 儲存登出狀態
        this.setIsLogged(false);
        this.tradeLeave();
        // 顯示操盤退出 再顯示操盤登入...
        ToastRoot.show(I18n.message('trade_logout_success'));
        this.emit('logout');
        this.clearLogged(); //邏輯上清除這樣比較正確，但是使用者使用上並沒有比較方變
    }
    // 清除暫存登入資訊
    clearLogged() {
        Variables.trade.account.value = null;
        Variables.trade.password.value = null;
        AsyncStorage.removeItem(Variables.trade.account.key);
        AsyncStorage.removeItem(Variables.trade.password.key);
    }
    // trade logout success -> 滑到 Time Chart View
    tradeLeave() {
        if (this.quotationDetailStore.isDetailMounted) {
            this.workbenchDetailStore.goToPage(Enum.detail.pageIndex.timePage);
        }
    }
    // 儲存登出/入狀態
    setIsLogged(isLogged) {
        Variables.trade.isLogged = isLogged;
    }
    emit(type) {
        this.eventEmitter.emit('tradeSocket', { type });
    }
}
