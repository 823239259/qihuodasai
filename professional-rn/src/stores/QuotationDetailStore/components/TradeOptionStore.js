import { observable, action } from 'mobx';
import _ from 'lodash';
import { ToastRoot } from '../../../components';
import { Logger, TradeUtil, I18n } from '../../../utils';
import { Enum } from '../../../global';

export default class TradeOptionStore {
    
    tradeSend = null;

    product = null;
    buyOrderPrice = null;
    @observable num = null;                     // 手数
    @observable direction = null;

    @observable price = null;                   // 市场价
    priceType = Enum.priceType.market.value;  // 价格类型:限价0，市价1，止损2

    radioOptions = [
        { label: Enum.priceType.market.text, value: Enum.priceType.market.value },
        { label: Enum.priceType.limit.text, value: Enum.priceType.limit.value }
    ];
    @observable radioValue = Enum.priceType.market.value;
    @observable radioIndex = 0;

    @observable isTradeSubmitDialogVisible = false;
    
   constructor(tradeSend) {
        this.logger = new Logger(TradeOptionStore);
        this.tradeSend = tradeSend;
   }
   reset(product) {
        this.product = product;
        this.num = '1';
        this.price = null;
        this.priceType = Enum.priceType.market.value;
        this.radioValue = Enum.priceType.market.value;
        this.radioIndex = 0;
   }
   @action onRadioPress(value, index) {
        this.radioValue = value;
        this.radioIndex = index;
        if (this.radioValue === Enum.priceType.market.value) {
            this.price = null;
            this.priceType = Enum.priceType.market.value;
        } else {
            this.price = this.product.lastPrice;
            this.priceType = Enum.priceType.limit.value;
        }
   }
   @action onNumChange(text) {
        this.num = text;
   }
   @action onPriceChange(text) {
        this.price = text;
   }
   toBuy(direction) {
        this.direction = direction;
        if (!TradeUtil.validateNum(this.num)) {
            return;
        }
        // validate lastPrice
        if (!TradeUtil.isPriceValid(this.product.lastPrice)) {
            ToastRoot.show(I18n.message('trade_error'));
            return;
        }
        if (this.priceType === Enum.priceType.market.value) {
            this.buyOrderPrice = TradeUtil.getMarketPrice(this.product.lastPrice, this.product.miniTikeSize, direction.value, this.product.dotSize);
        } else {
            this.buyOrderPrice = this.price;
        }
        if (!TradeUtil.validatePrice(this.buyOrderPrice, this.product)) {
            return;
        }

        this.setIsTradeSubmitDialogVisbile(true);
   }
   confirmTradeSubmitDialog() {
    this.tradeSend.insertOrder(this.product.exchangeNo, this.product.commodityNo, this.product.contractNo, this.num, this.direction.value, this.priceType, this.buyOrderPrice, 0, TradeUtil.getOrderRef());
    this.setIsTradeSubmitDialogVisbile(false);
   }
   cancelTradeSubmitDialog() {
        this.setIsTradeSubmitDialogVisbile(false);
   }
   @action setIsTradeSubmitDialogVisbile(isVisible) {
        this.isTradeSubmitDialogVisible = isVisible;
   }
}

