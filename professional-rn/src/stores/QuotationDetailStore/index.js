/*
    Master Store
    Children Stores inside
*/
import { observable, computed, action } from 'mobx';
import { Config, Variables, Enum } from '../../global';
import Logger from '../../utils/Logger';

export default class QuotationDetailStore {
    logger = null;
    eventEmitter = null;
    
    isFirst = true;
    isDetailMounted = false;
    detailCurrentTab = Enum.detail.startPageIndex;

    marketSocketUrl = Config.marketSocketUrl;
    // parent store
    quotationStore = null;
    // child stores
    timeStore = null;
    kStore = null;
    lightningStore = null;
    handicapTradeStore = null;

    isChartDataConfig = false;  // 確定欄位資料的index

    constructor(quotationStore, timeStore, kStore, lightningStore, handicapTradeStore) {
        this.logger = new Logger(QuotationDetailStore);
        this.quotationStore = quotationStore;
        this.timeStore = timeStore;
        this.kStore = kStore;
        this.lightningStore = lightningStore;
        this.handicapTradeStore = handicapTradeStore;
    }
    setEventEmitter(eventEmitter) {
        this.eventEmitter = eventEmitter;
    }
    @observable product = null;         // 進入detail頁面時，處理的current Product
    @observable isPickerVisible = false;
    
    @computed get productName() {
        let productName = '';
        this.product && (productName = this.product.productName);
        return productName;
    }
    // 這樣處理的原因是：盤口，閃電圖，一開始可能沒資料，因為OnRtnQuote都沒回應
    // 取得最新的該品種資料
    start(productName) {
        this.isDetailMounted = true;
        this.product = this.quotationStore.getProduct(productName);
        if (this.product) {
            this.handicapTradeStore.start(this.product);// 盤口(handicap)) & TradeLast
            this.lightningStore.start(this.product);// 閃電圖
        }
        if (this.isFirst && !Variables.trade.isLogged) {
            this.eventEmitter.emit('quotationDetailStore');
            this.isFirst = false;
        }
    }
    reset() {
        this.isDetailMounted = false;
        this.product = null;

        this.timeStore.reset();
        this.kStore.reset();
        this.lightningStore.reset();
        this.handicapTradeStore.reset();
    }
    @action setDetailCurrentTab(tabIndex) {
        this.detailCurrentTab = tabIndex;
        this.setActiveFalse();
        switch (tabIndex) {
            case Enum.detail.pageIndex.lightningPage:
                this.lightningStore.setIsActive(true);
                break;
            case Enum.detail.pageIndex.hanidcapPage:
                this.handicapTradeStore.setIsActive(true);
                break;
            case Enum.detail.pageIndex.timePage:
                this.timeStore.setIsActive(true);
                break;
            case Enum.detail.pageIndex.kPage:
                this.kStore.setIsActive(true);
                break;
            default:
                break;
        }
    }
    setActiveFalse() {
        this.lightningStore.setIsActive(false);
        this.handicapTradeStore.setIsActive(false);
        this.timeStore.setIsActive(false);
        this.kStore.setIsActive(false);
    }
    @action toggleIsPickerVisible() {
        this.isPickerVisible = !this.isPickerVisible;
    }
    updateChart(param) {
        if (this.isChartDataConfig) {
            this.handicapTradeStore.update(param, this.product);// 盤口(handicap)) & TradeLast 
            this.lightningStore.add(param, this.product);       // 閃電圖
            this.timeStore.add(param);// 更新分時圖
            this.kStore.add(param);//更新K線圖
        }
    }
    setChartHistory(jsonData) {
        this.setChartDataConfig(['time_flag','open','close','high','low','volume']);//to do ...约定好的
        let dotSize = 2;
        if (this.product) {
            dotSize = this.product.dotSize;
        }
        if (jsonData.data.period === "TIME_SHARING") {
            //分時圖
            this.timeStore.start(jsonData, dotSize);
        } else {
            //K線圖
            this.kStore.start(jsonData, dotSize);
        }
    }
    // getSubscript
    setChartDataConfig(data) {
        if (this.isChartDataConfig) {
            return;
        }
        for (let i = 0; i <= data.length - 1; i++) {
            if (data[i] === 'time_flag') {
                Enum.chartDataColumns.DateTimeStampIndex = i;
            } else if (data[i] === 'close') {
                Enum.chartDataColumns.LastPriceIndex = i;
            } else if (data[i] === 'open') {
                Enum.chartDataColumns.OpenPriceIndex = i;
            } else if (data[i] === 'low') {
                Enum.chartDataColumns.LowPriceIndex = i;
            } else if (data[i] === 'high') {
                Enum.chartDataColumns.HighPriceIndex = i;
            } else if (data[i] === 'volume') {
                Enum.chartDataColumns.VolumeIndex = i;
            }
        }
        this.isChartDataConfig = true;    
    }
}


