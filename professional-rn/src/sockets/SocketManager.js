import { NetInfo, AppState } from 'react-native';
import EventEmitter from 'EventEmitter';
import { Enum, Config, Variables } from '../global';
import { ToastRoot } from '../components';
import { Logger, I18n } from '../utils';

export default class SocketManager {
    eventEmitter = null;
    // socket & store
    tradeSocket = null;
    quotationSocket = null;
    quotationDetailStore = null;
    tradeLoginModalStore = null;
    connectionScreenStore = null;
    tradeSend = null;

    quotationHeartBeatTimeoutId = null;
    tradeHeartBeatTimeoutId = null;

    connectionInfo = null;
    
    constructor(quotationSocket, tradeSocket, quotationDetailStore, tradeLoginModalStore, connectionScreenStore, tradeSend) {
        this.logger = new Logger(SocketManager);
        this.quotationSocket = quotationSocket;
        this.tradeSocket = tradeSocket;
        this.quotationDetailStore = quotationDetailStore;
        this.tradeLoginModalStore = tradeLoginModalStore;
        this.connectionScreenStore = connectionScreenStore;
        this.tradeSend = tradeSend;
        // EventEmitter settgins
        this.eventEmitter = new EventEmitter();
        this.eventEmitter.addListener('quotationSocket', this.handleQuotationSocket.bind(this));
        this.eventEmitter.addListener('tradeSocket', this.handleTradeSocket.bind(this));
        this.eventEmitter.addListener('quotationDetailStore', this.handleQuotationDetailStore.bind(this));
        this.eventEmitter.addListener('tradeLoginModalStore', this.handleTradeLoginModalStore.bind(this));

        this.quotationSocket.setEventEmitter(this.eventEmitter);
        this.tradeSocket.setEventEmitter(this.eventEmitter);
        this.quotationDetailStore.setEventEmitter(this.eventEmitter);
        this.tradeLoginModalStore.setEventEmitter(this.eventEmitter);
        
        // connectionInfo: 1. wifi 2. cable 3.... 4.none(總之沒連線就傳none)
        NetInfo.addEventListener('connectionChange', (connectionInfo) => {
            this.connectionInfo = connectionInfo;
            this.logger.warn(`NetInfo: ${connectionInfo.type}`);
            if (quotationSocket.socket) {
                this.logger.warn(`NetInfo: ${connectionInfo.type}, quotationSocket socket state: ${Enum.socketState[quotationSocket.socket.readyState]}`);
            }
            if (tradeSocket.socket) {
                this.logger.warn(`NetInfo: ${connectionInfo.type}, trade socket state: ${Enum.socketState[tradeSocket.socket.readyState]}`);
            }
            if (connectionInfo.type === 'none') {
                Variables.isNetConnectd = false;
                this.connectionScreenStore.setIsInternetConnection(false);
                this.connectionScreenStore.setIsQuotationSocketConnection(false);
                this.connectionScreenStore.setIsTradeSocketConnection(false);
                this.logInternetProblem();
            } else {
                this.connectionScreenStore.setIsInternetConnection(true);
                this.backForHeartbeat();
            }
        });
        // AppState: 1.inactive 2.background 3.active
        // inactive時，App還保持著連線狀態
        // background是指 inactive到了一段時間後，會切換到background，這時候連線就中斷了 (The app is transitioning between foreground and background)
        AppState.addEventListener('change', (appState) => {
            this.logger.warn(`AppState: ${appState}`);
            if (quotationSocket.socket) {
                this.logger.warn(`AppState: ${appState}, quotationSocket socket state: ${Enum.socketState[quotationSocket.socket.readyState]}`);
            }
            if (tradeSocket.socket) {
                this.logger.warn(`AppState: ${appState}, trade socket state: ${Enum.socketState[tradeSocket.socket.readyState]}`);
            }
            if (appState === 'active') {
                this.backForHeartbeat();
            }
        });
        this.start();
    }
    backForHeartbeat() {
        this.quotationHeartbeat(true);
        this.tradeHeartbeat(true);
    }
    handleQuotationSocket() {
        this.logger.trace('handleQuotationSocket');
        this.startQuotationSocket();
    }
    // type == logout || error
    handleTradeSocket(e) {
        this.logger.trace('handleTradeSocket');
        // 停止tradeHeartbeat
        if (e.type === 'logout') {
            clearTimeout(this.tradeHeartBeatTimeoutId);
            this.connectionScreenStore.setIsTradeSocketConnection(false);
            return;
        }
        this.restartTradeSocket(Variables.trade.account.value, Variables.trade.password.value);
    }
    handleQuotationDetailStore() {
        this.logger.trace('handleQuotationDetailStore first start tradeSocket');
        if (Variables.trade.account.value) {
            this.startTradeSocket(Variables.trade.account.value, Variables.trade.password.value);
        }
    }
    // 1.一開始沒登入過
    // 2.已經登入過，但是被踢出，要再登入一次
    handleTradeLoginModalStore(e) {
        this.logger.trace('handleTradeLoginModalStore login tradeSocket');
        if (Variables.trade.isLogged) {
            this.restartTradeSocket(e.account, e.password, e.onTradeLoginSuccess);
        } else {
            this.startTradeSocket(e.account, e.password, e.onTradeLoginSuccess);
        }
    }
    //第一次進行socket連線
    start() {
        this.startQuotationSocket();
    }
    startQuotationSocket() {
        this.quotationSocket.connectSocket().then((msg) => {
            this.connectQuotationSocketSuccess(msg);
        }).catch((err) => {
            this.connectQuotationSocketError(err);
        });
    }
    startTradeSocket(account, password, onTradeLoginSuccess) {
        if (!account) {
            return;
        }
        this.tradeSocket.connectSocket(account, password, onTradeLoginSuccess).then((msgObj) => {
            this.connectTradeSocketSuccess(msgObj);
        }).catch((err) => {
            this.connectTradeSocketError(err);
        });
    }
    restartTradeSocket(account, password, onTradeLoginSuccess) {
        this.tradeSend.logout();
        this.tradeSocket.socket.close();
        if (!account) {
            return;
        }
        setTimeout(() => {
            this.tradeSocket.connectSocket(account, password, onTradeLoginSuccess).then((msgObj) => {
                this.connectTradeSocketSuccess(msgObj);
            }).catch((err) => {
                this.connectTradeSocketError(err);
            });
        }, Config.socketReconnectTime);
    }
    // --------------------- QuotationSocket -----------------------
    connectQuotationSocketSuccess(msg) {
        this.logger.trace(`connectQuotationSocketSuccess - ${msg}`);
        NetInfo.isConnected.fetch().then(isConnected => {
            this.connectionScreenStore.setIsInternetConnection(isConnected);
            if (isConnected) {
                this.connectionScreenStore.setIsQuotationSocketConnection(true);
            }
        });
        this.quotationHeartbeat();
    }
    // 失敗就再次重連
    connectQuotationSocketError(msg) {
        this.logger.error(`connectQuotationSocketError - ${msg}`);
        this.connectionScreenStore.setIsQuotationSocketConnection(false);
        if (this.connectionInfo.type === 'none') {
            this.logger.error('connectQuotationSocketError - 目前無網路連線 不重連');
            return;
        }
        setTimeout(() => {
            this.startQuotationSocket();
        }, Config.socketReconnectTime);
    }
    // 一call quotationHeartbeat會設this.quotationSocket.isHeartBeating = false
    // quotationSockert.OnRtnQuote 發動則會讓this.quotationSocket.isHeartBeating = true
    // 在keepAliveTime 是否接收到 OnRtnQuote
    // 否 -> 就會再次連線 -> quotationHeartbeat
    // 是 -> recursive一次heartbeat
    // ps: 週末時不會有新行情，因此一直收到OnRtnQuote，所以quotationSocket會一直重連，不過也沒問題
    quotationHeartbeat(isBack) {
        const time = isBack ? Config.backForHeartbeatTime : Config.keepAliveTime;
        // 表示socket重未連線過
        if (!this.quotationSocket.socket) {
            return;
        }
        this.logger.warn(`quotationHeartbeat - quotationSocket socket state: ${Enum.socketState[this.quotationSocket.socket.readyState]}`);
        
        this.quotationSocket.isHeartBeating = false;
        this.quotationSocket.sendQryCommodity(true);

        this.quotationHeartBeatTimeoutId && clearTimeout(this.quotationHeartBeatTimeoutId);

        this.quotationHeartBeatTimeoutId = setTimeout(() => {
            clearTimeout(this.quotationHeartBeatTimeoutId);
            if (this.quotationSocket.isHeartBeating) {
                NetInfo.isConnected.fetch().then(isConnected => {
                    this.connectionScreenStore.setIsInternetConnection(isConnected);
                    if (isConnected) {
                        this.connectionScreenStore.setIsQuotationSocketConnection(true);
                    }
                });
                this.quotationHeartbeat();
            } else {
                this.connectionScreenStore.setIsQuotationSocketConnection(false);
                this.startQuotationSocket();
            }
        }, time);
    }
    // --------------------- TradeSocket -----------------------
    connectTradeSocketSuccess(o) {
        this.logger.trace(`connectTradeSocketSuccess - ${o.msg}`);
        NetInfo.isConnected.fetch().then(isConnected => {
            this.connectionScreenStore.setIsInternetConnection(isConnected);
            if (isConnected) {
                this.connectionScreenStore.setIsTradeSocketConnection(true);
            }
        });
        this.connectionScreenStore.setTradeSocketAccount(o.param.account);
        this.tradeHeartbeat();
    }
    connectTradeSocketError(o) {
        this.logger.trace(`connectTradeSocketError - ${o.msg}`);
        this.connectionScreenStore.setIsTradeSocketConnection(false);
        if (this.connectionInfo.type === 'none') {
            this.logger.error('connectTradeSocketError - 目前無網路連線 不重連');
            return;
        } else if (o.msg === Enum.tradeSocketConnectionMode.loginError) {
            this.logger.error(`connectTradeSocketError - ${o.msg}`);
            return;
        }
        this.restartTradeSocket(Variables.trade.account.value, Variables.trade.password.value);
    }
    tradeHeartbeat(isBack) {
        const time = isBack ? Config.backForHeartbeatTime : Config.keepAliveTime;
        // socket重未連線過
        if (!this.tradeSocket.socket) {
            return;
        }
        this.logger.warn(`tradeHeartbeat - trade socket state: ${Enum.socketState[this.tradeSocket.socket.readyState]}`);
        this.tradeSocket.isHeartBeating = false;

        this.tradeHeartBeatTimeoutId && clearTimeout(this.tradeHeartBeatTimeoutId);

        this.tradeHeartBeatTimeoutId = setTimeout(() => {
            clearTimeout(this.tradeHeartBeatTimeoutId);
            if (this.tradeSocket.isHeartBeating && (this.tradeSocket.socket.readyState === 0 || this.tradeSocket.socket.readyState === 1)) {
                NetInfo.isConnected.fetch().then(isConnected => {
                    this.connectionScreenStore.setIsInternetConnection(isConnected);
                    if (isConnected) {
                        this.connectionScreenStore.setIsTradeSocketConnection(true);
                    }
                });
                this.tradeHeartbeat();
            } else {
                this.connectionScreenStore.setIsTradeSocketConnection(false);
                this.restartTradeSocket(Variables.trade.account.value, Variables.trade.password.value);
            }
        }, time);
    }
    logInternetProblem() {
        setTimeout(() => {
            this.logger.error(I18n.message('internet_Problem'));
        }, Config.waitingTime);
    }
}
