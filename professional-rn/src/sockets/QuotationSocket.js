import { action,autorun } from 'mobx';
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
    futureTypeStore = null;
    constructor(quotationStore, quotationDetailStore, tradeStore, futureTypeStore) {
        this.logger = new Logger(QuotationSocket);
        
        this.quotationStore = quotationStore;
        this.quotationDetailStore = quotationDetailStore;
        this.tradeStore = tradeStore;
        this.futureTypeStore = futureTypeStore
        

    }
    
    //ss

    ss () {
        this.socket.close()
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
                console.log('登录成功');
                
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
                if (!jsonData.data) {
                    return;
                }
                const method = jsonData.method;
                // this.logger.info(`onmessage - method ${method}`);
                //console.log(this.futureTypeStore);
                if (method === 'on_rsp_login') {
                    this.sendQryCommodity(false);
                    this.reconnectStartDetail();
                    resolve('QuotationSocket Login Success');
                } else if (method === 'on_rsp_commodity_list') { // 返回品種
                    //console.log(12313);
                    //console.log(jsonData);
                    
                    
                    if (this.isQryCommodityForheartBeat) {
                        this.isQryCommodityForheartBeat = false;
                        this.isHeartBeating = true;
                        return;
                    }
                    // 訂閱合約
                    this.onRspQryCommodity(jsonData);
                } else if (method === 'on_rsp_subscribe') {
                    //订阅成功
                    // quotation
                    //const moreData = JSON.parse(evt.data).Parameters.LastQuotation;
                    //this.quotationStore.insertData(jsonData.data);
                } else if (method === 'on_rsp_history_data') {//查询k线
                    // detail
                    this.quotationDetailStore.setChartHistory(jsonData);
                } else if (method === 'on_rtn_quote') { // 行情推送接口
                    // console.log(jsonData);
                    this.isHeartBeating = true;
                    // this.logger.info(`OnRtnQuote - isHeartBeating: ${this.isHeartBeating}`);
                    this.quotationStore.insertData(jsonData.data);
                    
                    if (this.quotationDetailStore.isDetailMounted) {
                        const param = jsonData.data;
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
        console.log(this.quotationStore.products);
        
        console.log(selectedProduct);
        
        // 表示當前選擇的合約，抓到的資料會有問題
        //if (!selectedProduct.LastPrice && !selectedProduct.AskPrice1 && !selectedProduct.BidPrice1) {
        if (!selectedProduct.LastPrice && !selectedProduct.ask[0] && !selectedProduct.bid[0]) {
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
        
        const commoditys = jsonData.data.commodity_list;
        //console.log(commoditys);
        
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
        if (data.contract_info.commodity_no === commodityNo && data.contract_info.contract_no === contractNo) {
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
        // to do ...
        let type = '';
        switch(num){
            case 0: type = 'TIME_SHARING';
            break;
            case 1: type = 'KLINE_1MIN';
            break;
            case 5: type = 'KLINE_5MIN';
            break;
            case 15: type = 'KLINE_15MIN';
            break;
            case 30: type = 'KLINE_30MIN';
            break;
            case 1440: type = 'KLINE_1DAY';
            break;
        }
        const { exchangeNo, commodityNo, contractNo } = this.quotationDetailStore.product;
        const qryHistoryParamSharing = `{"contract_info":{"security_type":"${this.futureTypeStore.Futstring}","exchange_no":"${exchangeNo}","commodity_no":"${commodityNo}","contract_no":"${contractNo}"},"period":"${type}"}`;//`{"ExchangeNo":"${exchangeNo}","CommodityNo":"${commodityNo}","ContractNo":"${contractNo}","HisQuoteType":${num} }`;
        const qryHistoryParamKline = `{"contract_info":{"security_type":"${this.futureTypeStore.Futstring}","exchange_no":"${exchangeNo}","commodity_no":"${commodityNo}","contract_no":"${contractNo}"},"period":"${type}","count":40}`;//`{"ExchangeNo":"${exchangeNo}","CommodityNo":"${commodityNo}","ContractNo":"${contractNo}","HisQuoteType":${num} }`;
        
        
        const qryHistoryParam = type == 'TIME_SHARING' ? qryHistoryParamSharing : qryHistoryParamKline ;
        this.sendMessage('req_history_data', qryHistoryParam);
    }
    sendSubscribe(commodity) {
        let mainContract = null;
        let contractList = commodity.contract_no_list;
        for(key in contractList){
          if(contractList[key].flags == 1){
            mainContract = contractList[key].contract_no;
            break;
          }
        }
        this.sendMessage('req_subscribe', `{"security_type":"${this.futureTypeStore.Futstring}","exchange_no":"${commodity.exchange_no}","commodity_no":"${commodity.commodity_no}","contract_no":"${mainContract}"}`);
        //this.sendMessage('req_subscribe', `{"security_type":"FUT_IN","exchange_no":"${commodity.exchange_no}","commodity_no":"${commodity.commodity_no}","contract_no":"${mainContract}"}`);
    }
    // 其實行情傳入的UserName & PassWord都是''
    sendLogin() {
        this.sendMessage('req_login', `{"user_name":"${Variables.user}", "password":" ${Variables.pwd}", "protoc_version":"6.2"}`);
    }
    // 查詢品種接口 QryCommodity 並且用來測試heartbeat，因為OnRtnQuote可能頻率不高
    sendQryCommodity(isQryCommodityForheartBeat) {
        this.isQryCommodityForheartBeat = isQryCommodityForheartBeat;
        //this.sendMessage('req_commodity_list', `{}`);
        this.sendMessage('req_commodity_list', `{"security_type":"${this.futureTypeStore.Futstring}"}`);
        //this.sendMessage('req_commodity_list', '{"security_type":"FUT_IN"}');
    }
    sendMessage(method, parameters) {
        // console.log(parameters);
        
        try {
            this.socket.send(`{"method":"${method}","data":${parameters}}`);
        } catch (err) {
            this.logger.error(`quotationSocket sendMessage: ${err}`);
        }
    }
    emitClose() {
        this.eventEmitter.emit('quotationSocket', { type: 'close' });
    }
}
