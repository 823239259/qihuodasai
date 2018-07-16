/*
  行情列表
*/
import { observable, action, computed, autorun } from 'mobx';
import _ from 'lodash';
import { Enum } from '../global';
import Logger from '../utils/Logger';
/**
 * 一個品種裡面，有非常多合約，但只有一個主力合約
 * commodity - 品種(国际原油)，commodityNo: MCH
 * contract  - 主力合約，contractNo: 1710
 * 
 * 1.因為不要把名字混淆，稱之為Product
 * 2.不直接稱呼name，是因為name太常見，很難搜尋
 * productName = commodityNo + contractNo = MCH1710
 */
class Product {
  productName;                //commodityNo+contractNo - MCH1710 - 常用key

  commodityName;              //品種名稱: 国际原油
  commodityNo;                //品種代碼 CommodityNo: MCH
  contractNo;                 //該品種最熱門：1710
  dotSize;                    //小數位數
  exchangeNo;                 //交易所: HKEX
  miniTikeSize;               //最小變動單位
  contractSize;               //每手乘數
  currencyNo;                 //幣別: USD, EUR, JPY, CNY, HKD-HKFE
  // for Swipeout
  sectionID;
  rowID;
  
  @observable lastPrice;      //最新價
  @observable lastPriceIsUp;

  @observable totalVolume;    //當日總成交量

  @observable changeRate;     //漲幅
  @observable changeRateIsUp;
  
  constructor({ CommodityName, CommodityNo, MainContract, DotSize, ExchangeNo, MiniTikeSize, ContractSize, CurrencyNo }, sectionID, rowID) {
    this.commodityName = CommodityName;
    this.commodityNo = CommodityNo;  // MCH
    this.contractNo = MainContract; // 1710
    this.productName = this.commodityNo + this.contractNo;  // MCH1710

    this.dotSize = DotSize;
    this.exchangeNo = ExchangeNo;
    this.miniTikeSize = MiniTikeSize;
    this.contractSize = ContractSize;
    this.currencyNo = CurrencyNo;

    this.sectionID = sectionID;
    this.rowID = rowID;
  }
  setLastPrice(LastPrice, LastPriceIsUp) {
    this.lastPrice = LastPrice;
    this.lastPriceIsUp = LastPriceIsUp;
  }
  setTotalVolume(TotalVolume) {
    this.totalVolume = TotalVolume;
  }
  setChangeRate(ChangeRate, ChangeRateIsUp) {
    this.changeRate = ChangeRate;
    this.changeRateIsUp = ChangeRateIsUp;
  }
}
class SearchStore {
  @observable search = '';
}
export default class QuotationStore {
  logger = null;
  searchStore = null;
  id = 0;
  // product是將commodity的部分資料抽出來，並塞入dotSize
  @observable products = [];
  @observable isLoading = true;

  @observable sortKey = null; // lastPrice, totalVolume, changeRate
  @observable sortType = Enum.sort.init;
  // for swipeout
  @observable sectionID = 's1';
  @observable rowID = 0; 

  @observable focusedProductNames = []; //'CL1804', 'HSI1802', 'GC1804'
  
  @computed get sortedProducts() {
    // 只有register, 還沒有insert 進入data時
    const productReady = this.products.filter(product => {
      return product.changeRate !== undefined;
    });
    // 不需要去做sort, filter
    if (productReady.length === 0) {
      return productReady;
    }
    if (!this.sortKey) {
      return this.filterProducts(productReady);
    }
    if (this.sortType === Enum.sort.init) {
      return this.filterProducts(productReady);
    }
    const sortedProducts = productReady.sort((a, b) => {
      let aValue = 0;
      let bValue = 0;
      if (this.sortKey === Enum.quotationHeader.latest) {
        aValue = parseFloat(a[this.sortKey]) * (a.lastPriceIsUp ? 1 : -1);
        bValue = parseFloat(b[this.sortKey]) * (b.lastPriceIsUp ? 1 : -1);
      } else {
        aValue = parseFloat(a[this.sortKey]);
        bValue = parseFloat(b[this.sortKey]);
      }
      return this.sortType === Enum.sort.ascending ? (bValue - aValue) : (aValue - bValue);
    });
    return this.filterProducts(sortedProducts);
  }
  filterProducts(products) {
    return products.filter(d => {
        return `${d.commodityName}${d.productName}`.indexOf(this.searchStore.search) > -1;
    });
  }
  @computed get focusedProducts() {
    let focusedProducts = [];
    this.focusedProductNames.forEach((productName) => {
      const findedProduct = this.products.find((product) => {
        return product.productName === productName;
      });
      if (findedProduct) {
        focusedProducts = focusedProducts.concat(findedProduct);
      }
    });
    return focusedProducts;
  }
  getId() {
    return this.id++;
  }

  constructor() {
    this.logger = new Logger(QuotationStore);
    this.searchStore = new SearchStore();
    // autorun(() => {
    //   this.logger.trace(`autorun: ${JSON.stringify(this.products[this.products.length - 1])}`);
    // });
  }
  @action setLoading(isLoading) {
    this.isLoading = isLoading;
  }
  // commodity起始資料，有DotSize, 為 盤口，閃電圖的 起始 最新資料，
  @action addProduct(commodity) {
    const productName = commodity.CommodityNo + commodity.MainContract;
    let isExist = false;
    for (const product of this.products) {
        if (product.productName === productName) {
            isExist = true;
        }
    }
    if (!isExist) {
      // sectionID, rowID for Swipeout
      this.products.push(new Product(commodity, 'section1', this.products.length + 1));
      // set initial focusedProductNames
      if (this.focusedProductNames.length < 3) {
          this.focusedProductNames.push(productName);
      }
    }
  }
  // TotalVolume:當日總成交量
  // ChangeRate: 漲幅
  // LastPrice: 最新價
  // CommodityNo: 品種代碼
  @action insertData(MoreData) {
    const products = this.products;
    const moreData = MoreData;
    const { CommodityNo, LastPrice, TotalVolume, ChangeRate, PreSettlePrice } = moreData;
    for (let i = 0; i < products.length; i++) {
      const product = products[i];
      if (CommodityNo === product.commodityNo) {
        _.assign(product, moreData);  // 3支sockets 減少為2支Socket後，讓product 保持拿到最新資料
        product.setLastPrice(LastPrice.toFixed(product.dotSize), Number(LastPrice) - Number(PreSettlePrice) < 0);
        product.setTotalVolume(TotalVolume);
        product.setChangeRate(ChangeRate.toFixed(2), Number(ChangeRate) < 0);
        break;
      }
    }
  }
  @action clearData() {
    this.products.clear();
  }
  @action sort(sortKey, sortType) {
    this.sortKey = sortKey;
    this.sortType = sortType;
  }
  @action swipeout(sectionID, rowID) {
    this.sectionID = sectionID;
    this.rowID = rowID;
  }
  @action focusProduct(productName) {
    this.focusedProductNames.shift();
    this.focusedProductNames.push(productName);
  }
  getProduct(name) {
    return this.products.find((p) => {
        return name === p.productName;
    });
  }
}
