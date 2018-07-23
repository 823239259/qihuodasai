import { observable, action } from 'mobx';
import _ from 'lodash';
import { ToastRoot } from '../../../components';
import { Logger, TradeUtil, I18n, FieldUtil } from '../../../utils';
import { Enum } from '../../../global';

export default class TradeNumStore {
    
    tradeSend = null;
    
    product = null;
    buyOrderPrice = null;
    @observable num = null;             // 手数
    @observable direction = null;
    @observable isTradeSubmitDialogVisible = false;
    
   constructor(tradeSend) {
        this.logger = new Logger(TradeNumStore);
        this.tradeSend = tradeSend;
   }
   reset(product) {
        this.num = '1';
        this.product = product;
   }
   @action onNumChange(text) {
        this.num = FieldUtil.getInt(text);
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
        this.buyOrderPrice = TradeUtil.getMarketPrice(this.product.lastPrice, this.product.miniTikeSize, direction.value, this.product.dotSize);

        this.setIsTradeSubmitDialogVisbile(true);
   }
   confirmTradeSubmitDialog() {
        this.tradeSend.insertOrder(this.product.exchangeNo, this.product.commodityNo, this.product.contractNo, this.num, this.direction.value, Enum.priceType.market.value, this.buyOrderPrice, 0, TradeUtil.getOrderRef(),1);// to fix ... 1改为变量
        this.setIsTradeSubmitDialogVisbile(false);
   }
   cancelTradeSubmitDialog() {
        this.setIsTradeSubmitDialogVisbile(false);
   }
   @action setIsTradeSubmitDialogVisbile(isVisible) {
        this.isTradeSubmitDialogVisible = isVisible;
   }
}

