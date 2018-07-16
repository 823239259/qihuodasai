import { Navigation, ScreenVisibilityListener } from 'react-native-navigation';

import QuotationScreen from './QuotationScreen';
import QuotationDetailScreen from './QuotationScreen/QuotationDetailScreen';
import QuotationDetailHeader from './QuotationScreen/QuotationDetailScreen/components/QuotationDetailHeader';
//import QuotationAboutModal from './Modal/QuotationAboutModal';
import TradeLoginModal from './Modal/TradeLoginModal';

import DrawerTrade from './Drawer/DrawerTrade';
import StopLossListModal from './Modal/StopLossListModal';
import ConditionListModal from './Modal/ConditionListModal';
import HistoryTradeModal from './Modal/HistoryTradeModal';

import ApplyTradeScreen from './ApplyTradeScreen';
import ApplyDetail from './ApplyTradeScreen/ApplyRecord/ApplyDetail';
import ApplyEnd from './ApplyTradeScreen/ApplyRecord/ApplyDetail/ApplyEnd';
import ApplyConfirm from './ApplyTradeScreen/ApplyNew/ApplyConfirm';
import ApplyResult from './ApplyTradeScreen/ApplyNew/ApplyConfirm/ApplyResult';
import AppendTraderBondModal from './ApplyTradeScreen/AppendTraderBondModal';

import InformationScreen from './InformationScreen';
import AccountScreen from './AccountScreen';
import AccountDeposit from './AccountScreen/AccountDeposit';
import AccountDepositWebView from './AccountScreen/AccountDepositWebView';
import AccountWithdraw from './AccountScreen/AccountWithdraw';
import AccountWithdrawPwd from './AccountScreen/AccountWithdraw/AccountWithdrawPwd';
import AccountFund from './AccountScreen/AccountFund';
import AccountRealNameCertification from './AccountScreen/AccountRealNameCertification';
import AccountBankCard from './AccountScreen/AccountBankCard';
import AccountAddBankCard from './AccountScreen/AccountBankCard/AccountAddBankCard';
import AccountCellphone from './AccountScreen/AccountCellphone';
import AccountLoginPwd from './AccountScreen/AccountLoginPwd';

import LoginModal from './Modal/LoginModal';
import RegisterModal from './Modal/RegisterModal';
import RegisterConfirmPwd from './Modal/RegisterModal/RegisterConfirmPwd';
import CustomerServiceModal from './Modal/CustomerServiceModal';

import { Logger } from '../utils';

// register all screens of the app (including internal ones)
export function registerScreens(store: {}, Provider: {}) {
  return new Promise(resolve => {
      Navigation.registerComponent('quotation.QuotationScreen', () => QuotationScreen, store, Provider);
      
      Navigation.registerComponent('quotation.QuotationDetailScreen', () => QuotationDetailScreen, store, Provider);
      Navigation.registerComponent('quotation.QuotationDetailHeader', () => QuotationDetailHeader, store, Provider);
    
      //Navigation.registerComponent('quotation.QuotationAboutModal', () => QuotationAboutModal, store, Provider);            关于页 暂时移除
      Navigation.registerComponent('quotation.TradeLoginModal', () => TradeLoginModal, store, Provider);
    
      Navigation.registerComponent('trade.DrawerTrade', () => DrawerTrade, store, Provider);
      Navigation.registerComponent('trade.StopLossListModal', () => StopLossListModal, store, Provider);
      Navigation.registerComponent('trade.ConditionListModal', () => ConditionListModal, store, Provider);
      Navigation.registerComponent('trade.HistoryTradeModal', () => HistoryTradeModal, store, Provider);
    
      Navigation.registerComponent('applyTrade.ApplyTradeScreen', () => ApplyTradeScreen, store, Provider);
      Navigation.registerComponent('applyTrade.ApplyDetail', () => ApplyDetail, store, Provider);
      Navigation.registerComponent('applyTrade.ApplyEnd', () => ApplyEnd, store, Provider);
      Navigation.registerComponent('applyTrade.ApplyConfirm', () => ApplyConfirm, store, Provider);
      Navigation.registerComponent('applyTrade.ApplyResult', () => ApplyResult, store, Provider);
      Navigation.registerComponent('applyTrade.AppendTraderBondModal', () => AppendTraderBondModal, store, Provider);
    
      Navigation.registerComponent('information.InformationScreen', () => InformationScreen, store, Provider);
    
      Navigation.registerComponent('account.AccountScreen', () => AccountScreen, store, Provider);
      Navigation.registerComponent('account.AccountDeposit', () => AccountDeposit, store, Provider);
      Navigation.registerComponent('account.AccountDepositWebView', () => AccountDepositWebView, store, Provider);
      Navigation.registerComponent('account.AccountWithdraw', () => AccountWithdraw, store, Provider);
      Navigation.registerComponent('account.AccountWithdrawPwd', () => AccountWithdrawPwd, store, Provider);
      Navigation.registerComponent('account.AccountFund', () => AccountFund, store, Provider);
      Navigation.registerComponent('account.AccountRealNameCertification', () => AccountRealNameCertification, store, Provider);
      Navigation.registerComponent('account.AccountBankCard', () => AccountBankCard, store, Provider);
      Navigation.registerComponent('account.AccountAddBankCard', () => AccountAddBankCard, store, Provider);
      Navigation.registerComponent('account.AccountCellphone', () => AccountCellphone, store, Provider);
      Navigation.registerComponent('account.AccountLoginPwd', () => AccountLoginPwd, store, Provider);
      
      Navigation.registerComponent('account.LoginModal', () => LoginModal, store, Provider);
      Navigation.registerComponent('account.RegisterModal', () => RegisterModal, store, Provider);
      Navigation.registerComponent('account.RegisterConfirmPwd', () => RegisterConfirmPwd, store, Provider);
      Navigation.registerComponent('account.CustomerServiceModal', () => CustomerServiceModal, store, Provider);

    resolve(true);
  });
}
export function registerScreenVisibilityListener() {
  return new Promise(resolve => {
      const logger = new Logger('RegisterScreens');
      new ScreenVisibilityListener({
        willAppear: ({screen}) => logger.trace(`Displaying screen ${screen}`),
        didAppear: ({screen, startTime, endTime, commandType}) => logger.trace('screenVisibility', `Screen ${screen} displayed in ${endTime - startTime} millis [${commandType}]`),
        willDisappear: ({screen}) => logger.trace(`Screen will disappear ${screen}`),
        didDisappear: ({screen}) => logger.trace(`Screen disappeared ${screen}`)
      }).register();
    resolve(true);
  });
}
