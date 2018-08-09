import SocketManager from '../sockets/SocketManager';
import QuotationSocket from '../sockets/QuotationSocket';
import TradeSocket from '../sockets/TradeSocket';
import TradeSend from '../sockets/TradeSend';
import ConnectionScreenStore from './ConnectionScreenStore';

import QuotationStore from './QuotationStore';

import QuotationDetailStore from './QuotationDetailStore';
import TimeStore from './QuotationDetailStore/TimeStore';
import KStore from './QuotationDetailStore/KStore';
import LightningStore from './QuotationDetailStore/LightningStore';
import HandicapTradeStore from './QuotationDetailStore/HandicapTradeStore';

import TradeStore from './QuotationDetailStore/TradeStore';

import WorkbenchDetailStore from './QuotationDetailStore/components/WorkbenchDetailStore';
import TradeNumStore from './QuotationDetailStore/components/TradeNumStore';
import TradeOptionStore from './QuotationDetailStore/components/TradeOptionStore';
import TradeHoldPositionStore from './QuotationDetailStore/components/TradeHoldPositionStore';
import TradeDesignateStore from './QuotationDetailStore/components/TradeDesignateStore';

import AccountStore from './AccountStore';
import RegisterStore from './AccountStore/RegisterStore';
import AccountWithdrawPwdStore from './AccountStore/AccountWithdrawPwdStore';
import StopLossStore from './StopLossStore';
import HistoryTradeStore from './HistoryTradeStore';
import ConditionStore from './ConditionStore';
import TradeLoginModalStore from './ModalStore/TradeLoginModalStore';
import InformationStore from './InformationStore';

import ApplyTradeStore from './ApplyTradeStore';
import DrawerTradeStore from './DrawerStore/DrawerTradeStore';

import futureTypeStore from './FutureTypeStore';
import { Logger, PromiseTestUtil } from '../utils';

class StoreManager {
  logger = null;
  promiseTestUtil = null;
  quotationStoreProductResolve = null;

  constructor() {
    this.logger = new Logger(StoreManager);
    this.promiseTestUtil = new PromiseTestUtil();
    // this.promiseTestUtil.printSecond();
  }
  configureStore() {
    return new Promise((resolve, reject) => {
      let stores = null;
      Promise.all([
          this.createStores(),
          // this.quotationStoreProductAdded(),
      ]).then(values => {
          stores = values[0];
          resolve(stores);
      }).catch(error => {
          console.log(error);
          reject(error);
      });
    });
  }
  createStores() {
    return new Promise(resolve => {
        // quotation
        const quotationStore = new QuotationStore();
        
        const timeStore = new TimeStore();
        const kStore = new KStore();
        const lightningStore = new LightningStore();
        const handicapTradeStore = new HandicapTradeStore();
        const quotationDetailStore = new QuotationDetailStore(quotationStore, timeStore, kStore, lightningStore, handicapTradeStore);
  
  
        const tradeSend = new TradeSend();
        // trade
        const workbenchDetailStore = new WorkbenchDetailStore();
        const tradeNumStore = new TradeNumStore(tradeSend);
        const tradeOptionStore = new TradeOptionStore(tradeSend);
        const tradeStore = new TradeStore(quotationStore, tradeNumStore, tradeOptionStore);
        const tradeHoldPositionStore = new TradeHoldPositionStore(quotationStore, tradeStore, tradeSend);
        const tradeDesignateStore = new TradeDesignateStore(quotationStore, tradeStore, tradeSend);

        const drawerTradeStore = new DrawerTradeStore(tradeSend, workbenchDetailStore);
  
        const registerStore = new RegisterStore();
        const stopLossStore = new StopLossStore(quotationStore, tradeSend, tradeHoldPositionStore);
        const historyTradeStore = new HistoryTradeStore(tradeSend);
        const conditionStore = new ConditionStore(quotationStore, tradeSend);

        const informationStore = new InformationStore();
  
        const accountStore = new AccountStore();
        const applyTradeStore = new ApplyTradeStore(accountStore);
        const accountWithdrawPwdStore = new AccountWithdrawPwdStore(accountStore);

        const tradeLoginModalStore = new TradeLoginModalStore();

        // futureTypeStore内外盘标识
        const quotationSocket = new QuotationSocket(quotationStore, quotationDetailStore, tradeStore, futureTypeStore);
        const tradeSocket = new TradeSocket(tradeStore, tradeSend, workbenchDetailStore, quotationDetailStore, stopLossStore, historyTradeStore, conditionStore, tradeLoginModalStore, futureTypeStore);
        const connectionScreenStore = new ConnectionScreenStore();
        const socketManager = new SocketManager(quotationSocket, tradeSocket, quotationDetailStore, tradeLoginModalStore, connectionScreenStore, tradeSend);


        resolve({
          QuotationStore: quotationStore,
  
          TimeStore: timeStore,
          KStore: kStore,
          LightningStore: lightningStore,
          HandicapTradeStore: handicapTradeStore,
          QuotationDetailStore: quotationDetailStore,
  
          TradeSend: tradeSend,
  
          TradeStore: tradeStore,
  
          WorkbenchDetailStore: workbenchDetailStore,
          TradeNumStore: tradeNumStore,
          TradeOptionStore: tradeOptionStore,
          TradeHoldPositionStore: tradeHoldPositionStore,
          TradeDesignateStore: tradeDesignateStore,
  
          AccountStore: accountStore,
          RegisterStore: registerStore,
          AccountWithdrawPwdStore: accountWithdrawPwdStore,
          StopLossStore: stopLossStore,
          HistoryTradeStore: historyTradeStore,
          ConditionStore: conditionStore,
  
          InformationStore: informationStore,

          ApplyTradeStore: applyTradeStore,

          TradeLoginModalStore: tradeLoginModalStore,
          ConnectionScreenStore: connectionScreenStore,
          SocketManager: socketManager,
          QuotationSocket: quotationSocket,
          TradeSocket: tradeSocket,

          DrawerTradeStore: drawerTradeStore,
          //内外盘store
          FutureTypeStore: futureTypeStore
        });
    });
  }
}
export default new StoreManager();
