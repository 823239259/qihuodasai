import { action } from 'mobx';
import { Config, Variables, Enum } from '../global';
import { Logger, I18n } from '../utils';
import { ToastRoot } from '../components';

export default class QuotationSocket {
    socket = null;
    logger = null;
    eventEmitter = null;

    isHeartBeating = false;
    // stores
    quotationStore = null;
    quotationDetailStore = null;
    tradeStore = null;
    
    constructor(quotationStore, quotationDetailStore, tradeStore) {
        this.logger = new Logger(QuotationSocket);
        
        this.quotationStore = quotationStore;
        this.quotationDetailStore = quotationDetailStore;
        this.tradeStore = tradeStore;
    }
    setEventEmitter(eventEmitter) {
        this.eventEmitter = eventEmitter;
    }
    connectSocket() {
        return new Promise((resolve, reject) => {
            this.socket = new WebSocket(Config.marketSocketUrl);
            this.socket.onopen = () => {
                // this.logger.info('onopen');
                // 登入接口 Login
                this.sendLogin();
            };
            // websocket中斷的話 就再連一次
            this.socket.onclose = (e) => {
                // this.logger.error(`onclose - code: ${e.code}, reason: ${e.reason}, readyState: ${Enum.socketState[this.socket.readyState]}`);
                const reason = e.reason ? e.reason : 'QuotationSocket Login Fail';
                reject(reason);
                // this.emitClose(); // 強制一直再次連線，先留著
            };
            this.socket.onmessage = (evt) => {
                const data = evt.data;
                const jsonData = JSON.parse(data);
                if (!jsonData.Parameters) {
                    return;
                }
                const method = jsonData.Method;
                // this.logger.info(`onmessage - method ${method}`);

                if (method === 'OnRspLogin') {
                    this.sendQryCommodity(false);
                    this.reconnectStartDetail();
                    resolve('QuotationSocket Login Success');
                } else if (method === 'OnRspQryCommodity') { // 返回品種
                    if (this.isQryCommodityForheartBeat) {
                        this.isQryCommodityForheartBeat = false;
                        this.isHeartBeating = true;
                        return;
                    }
                    // 訂閱合約
                    this.onRspQryCommodity(jsonData);
                } else if (method === 'OnRspSubscribe') {
                    // quotation
                    const moreData = JSON.parse(evt.data).Parameters.LastQuotation;
                    this.quotationStore.insertData(moreData);
                } else if (method === 'OnRspQryHistory') {
                    // detail
                    this.quotationDetailStore.setChartHistory(jsonData);
                } else if (method === 'OnRtnQuote') { // 行情推送接口
                    this.isHeartBeating = true;
                    // this.logger.info(`OnRtnQuote - isHeartBeating: ${this.isHeartBeating}`);
                    this.quotationStore.insertData(jsonData.Parameters);
                    
                    if (this.quotationDetailStore.isDetailMounted) {
                        const param = jsonData.Parameters;
                        // 更新每一筆筆持倉的浮动盈亏
                        Variables.trade.isLogged && this.tradeStore.updateFloatProfitAndRisk(param);
                        if (this.isQuotationEqual(param)) {
                            this.quotationDetailStore.updateChart(param);
                            // 更新選擇到的合約最新資料
                            Variables.trade.isLogged && this.tradeStore.updateTradeQuotation(param);
                        }
                    }
                }
            };
        });
    }
    /**
     * 1. 手動進入Detail頁面時
     * 2. Detail Picker 更換 product時
     * 3. reconnect 而 又在已經在detail頁面時，但這時quotationDetailStore已經存有product，不需要再次start
     */
    startDetail(commodityNo, contractNo) {
        if (commodityNo) {
            const productName = `${commodityNo}${contractNo}`;
            this.quotationDetailStore.start(productName);
            this.tradeStore.setCurrentProduct(productName);
        }
        // 查詢TimeChart data，因為分時圖只要查一次，那就讓他一開始查就好了
        this.sendHistoryMessage(0);
        // 但K線有很多種，只判斷當前是KPage，才去查特定ktype data
        if (this.quotationDetailStore.detailCurrentTab === Enum.detail.pageIndex.kPage) {
            // 查詢當前選擇的kType
            this.sendHistoryMessage(this.quotationDetailStore.currentKType);
        }
    }
    resetDetail() {
        this.quotationDetailStore.reset();
    }
    // Picker selected product 
    @action changeDetail(productName) {
        const selectedProduct = this.quotationStore.getProduct(productName);
        // 表示當前選擇的合約，抓到的資料會有問題
        if (!selectedProduct.LastPrice && !selectedProduct.AskPrice1 && !selectedProduct.BidPrice1) {
            ToastRoot.show(`${selectedProduct.productName}無行情`);
            return;
        }
        this.resetDetail();
        this.startDetail(selectedProduct.commodityNo, selectedProduct.contractNo);
    }
    // reconnect時，若是已經在detail頁面，就得query history message
    reconnectStartDetail() {
        if (this.quotationDetailStore.isDetailMounted) {
            this.startDetail();
        }
    }
    // 返回品種
    onRspQryCommodity(jsonData) {
        const commoditys = jsonData.Parameters;
        
        for (let i = 0; i < commoditys.length; i++) {
            if (commoditys[i].IsUsed !== 0) {
                const commodity = commoditys[i];
                this.quotationStore.addProduct(commodity);                
                // 訂閱合約 - 告訴WebSocket server 我要訂閱Subscribe -> server response -> OnRspSubscribe
                this.sendSubscribe(commodity);
            }
        }
        this.quotationStore.setLoading(false);
    }
    // 是否為當前選取的合約
    isQuotationEqual(data) {
        let isQuotationEqual = false;
        const { commodityNo, contractNo } = this.quotationDetailStore.product;
        if (data.CommodityNo === commodityNo && data.ContractNo === contractNo) {
            isQuotationEqual = true;
        }
        return isQuotationEqual;
    }
    // --------------------- send -----------------------
    /* 
        sendHistoryMessage(0)         —> 取得分時圖資料
        sendHistoryMessage(1)         —> 取得K線資料 - 1分
        sendHistoryMessage(5)         —> 取得K線資料 - 5分
        sendHistoryMessage(15)        —> 取得K線資料 - 15分
        sendHistoryMessage(30)        —> 取得K線資料 - 30分
        sendHistoryMessage(1440)      —> 取得K線資料 - 日
    */
    sendHistoryMessage(num) {
        if (!this.quotationDetailStore.product) {
            return;
        }
        const { exchangeNo, commodityNo, contractNo } = this.quotationDetailStore.product;
        const qryHistoryParam = `{"ExchangeNo":"${exchangeNo}","CommodityNo":"${commodityNo}","ContractNo":"${contractNo}","HisQuoteType":${num} }`;
		this.sendMessage('QryHistory', qryHistoryParam);
    }
    sendSubscribe(commodity) {
        this.sendMessage('Subscribe', `{"ExchangeNo":"${commodity.ExchangeNo}","CommodityNo":"${commodity.CommodityNo}","ContractNo":"${commodity.MainContract}"}`);
    }
    // 其實行情傳入的UserName & PassWord都是''
    sendLogin() {
        this.sendMessage('Login', `{"UserName":"${Variables.user}", "PassWord":" ${Variables.pwd}"}`);
    }
    // 查詢品種接口 QryCommodity 並且用來測試heartbeat，因為OnRtnQuote可能頻率不高
    sendQryCommodity(isQryCommodityForheartBeat) {
        this.isQryCommodityForheartBeat = isQryCommodityForheartBeat;
        this.sendMessage('QryCommodity', null);
    }
    sendMessage(method, parameters) {
        try {
            this.socket.send(`{"Method":"${method}","Parameters":${parameters}}`);
        } catch (err) {
            this.logger.error(`quotationSocket sendMessage: ${err}`);
        }
    }
    emitClose() {
        this.eventEmitter.emit('quotationSocket', { type: 'close' });
    }
}
