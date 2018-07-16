/**
 * 追蹤
 * 1.網路
 * 2.行情連接
 * 3.交易連接
 */
import { observable, action } from 'mobx';

export default class ConnectionScreenStore {
    @observable activeScreenName = null;    // quotation.QuotationScreen

    @observable isInternetConnection = false;
    @observable isQuotationSocketConnection = false;
    
    @observable isTradeSocketConnection = false;
    @observable tradeAccount = null;

    @action setIsInternetConnection(isInternetConnection) {
        this.isInternetConnection = isInternetConnection;
    }
    @action setIsQuotationSocketConnection(isQuotationSocketConnection) {
        this.isQuotationSocketConnection = isQuotationSocketConnection;
    }
    @action setIsTradeSocketConnection(isTradeSocketConnection) {
        this.isTradeSocketConnection = isTradeSocketConnection;
    }
    @action setTradeSocketAccount(tradeAccount) {
        this.tradeAccount = tradeAccount;
    }
    setActiveScreenName(screenName) {
        this.activeScreenName = screenName;
    }
}

