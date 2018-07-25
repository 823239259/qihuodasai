import { AsyncStorage, Platform } from 'react-native';
import { Navigation } from 'react-native-navigation';
import SplashScreen from 'react-native-splash-screen';
import Icon from 'react-native-vector-icons/FontAwesome';
import { registerScreens, registerScreenVisibilityListener } from '../screens';
import { registerBugsnag } from '../tools/Bugsnag';
import Provider from '../utils/MobxRnnProvider';
import StoreManager from '../stores';
import { Variables, NavigatorStyle, Config, Layout, Enum } from '../global';
import { Logger, Api, PromiseTestUtil } from '../utils';
import AppSetting from './AppSetting';

export default class App {
  logger = null;
  constructor() {
    this.logger = new Logger(App);
    this.promiseTestUtil = new PromiseTestUtil();

    this.start().then(() => {
      // SplashScreen的處理 ios & android不同
      if (Platform.OS === 'ios') {
        SplashScreen.hide();
      }
      this.startApp();
    });
  }
  start() {
    return new Promise(resolve => {
      Promise.all([
        registerBugsnag(),
        this.getIsJudgeShow(),
        this.getHotline(),
        this.getTradeSocketInfo(),
        this.initTradeInfo(),
        this.populateIcons(),
        this.configureStoreAndRegisterScreen(),
        // this.promiseTestUtil.resolveAfter3Seconds(0) // 等三秒
      ]).then(() => {
        resolve(true);
      });
    });  
  }
  // 控制是否只顯示部分App功能
  // Config.appVersion 設定一個不存在的version會直接 顯示正常功能
  // 1.開啟 result.data = true -> 顯示馬甲包
  // 2.屏蔽 result.data = false -> 顯示正常功能
  getIsJudgeShow() {
    return new Promise((resolve) => {
      Api.getJudgeShow((result) => {
        if (result.success) {
          if (result.data === 'true') {
            Variables.isJudgeShow = true;
          }
        }
        resolve(true);
      });
    });
  }
  getHotline() {
    return new Promise((resolve) => {
      Api.getHotline((result) => {
        Variables.hotline = result.data.hotline;  // 400-120-9061
        resolve(true);
      });
    });
  }
  getTradeSocketInfo() {
    return new Promise((resolve) => {
      
      Api.getTradeSocketInfo((result) => {
        Config.tradeSocket.url = result.data.socketUrl;//to fix...改为静态url // ws://trade.qhinom.com:6102 有可能會更改掉
        resolve(true);
      });
    });
  }
  // App一開始啟動時會去抓Trade socket相關資訊，若是有交易登入資訊存在localstorage就直接登入，並連接Trade Websocket
  async initTradeInfo() {
    Variables.trade.account.value = await AsyncStorage.getItem(Variables.trade.account.key);
    Variables.trade.password.value = await AsyncStorage.getItem(Variables.trade.password.key);
  }
  async configureStoreAndRegisterScreen() {
    const stores = await StoreManager.configureStore();
    await registerScreens(stores, Provider);
    registerScreenVisibilityListener();
    return true;
  }
  populateIcons() {
    return new Promise((resolve, reject) => {
      Promise.all(
        [
          Icon.getImageSource(Variables.icon.quotation.name, Variables.icon.size),
          Icon.getImageSource(Variables.icon.trade.name, Variables.icon.size),
          Icon.getImageSource(Variables.icon.information.name, Variables.icon.size),
          Icon.getImageSource(Variables.icon.account.name, Variables.icon.size),
          Icon.getImageSource(Variables.icon.closeAbout.name, Variables.icon.size),
          Icon.getImageSource(Variables.icon.bars.name, Variables.icon.size),
          Icon.getImageSource(Variables.icon.list.name, Variables.icon.size),
          Icon.getImageSource(Variables.icon.grid.name, Variables.icon.size),
          Icon.getImageSource(Variables.icon.question.name, Variables.icon.size),
          Icon.getImageSource(Variables.icon.exclamationStopLoss.name, Variables.icon.size),
          Icon.getImageSource(Variables.icon.plusCondition.name, Variables.icon.size),
          Icon.getImageSource(Variables.icon.accountCustomerService.name, Variables.icon.size),
          Icon.getImageSource(Variables.icon.indicatorArrowUp.name, Variables.icon.size),
          Icon.getImageSource(Variables.icon.indicatorArrowDown.name, Variables.icon.size),
          Icon.getImageSource(Variables.icon.search.name, Variables.icon.size),
        ]
      ).then((values) => {
        Variables.icon.quotation.source = values[0];
        Variables.icon.trade.source = values[1];
        Variables.icon.information.source = values[2];
        Variables.icon.account.source = values[3];
        Variables.icon.closeAbout.source = values[4];
        Variables.icon.closeLogin.source = values[4];
        Variables.icon.closeRegister.source = values[4];
        Variables.icon.closeStopLoss.source = values[4];
        Variables.icon.closeTradeLogin.source = values[4];
        Variables.icon.closeCondition.source = values[4];
        Variables.icon.closeHistoryTrade.source = values[4];
        Variables.icon.closeAccountCustomerService.source = values[4];
        Variables.icon.closeAccountDeposit.source = values[4];
        Variables.icon.closeAppendTraderBond.source = values[4];
        Variables.icon.bars.source = values[5];
        Variables.icon.list.source = values[6];
        Variables.icon.grid.source = values[7];
        Variables.icon.question.source = values[8];
        Variables.icon.exclamationStopLoss.source = values[9];
        Variables.icon.exclamationCondition.source = values[9];
        Variables.icon.exclamationHistoryTrade.source = values[9];
        Variables.icon.exclamationAccountWithDraw.source = values[9];
        Variables.icon.plusCondition.source = values[10];
        Variables.icon.accountCustomerService.source = values[11];
        Variables.icon.indicatorArrowUp.source = values[12];
        Variables.icon.indicatorArrowDown.source = values[13];
        Variables.icon.search.source = values[14];
        resolve(true);
      }).catch((error) => {
        console.log(error);
        reject(error);
      }).done();
    });
  }
  // https://wix.github.io/react-native-navigation/#/styling-the-navigator?id=styling-the-statusbar
  startTabBased() {
    let tabs = [
      {
        label: Enum.screen.tabBased.quotation.title,
        screen: 'quotation.QuotationScreen', // this is a registered name for a screen
        icon: Variables.icon.quotation.source,
        title: Enum.screen.tabBased.quotation.title,
        navigatorStyle: NavigatorStyle.tabBasedStyle
      },
      {
        label: Enum.screen.tabBased.applyTrade.title,
        screen: 'applyTrade.ApplyTradeScreen',
        icon: Variables.icon.trade.source,
        title: Enum.screen.tabBased.applyTrade.title,
        navigatorStyle: NavigatorStyle.tabBasedStyle
      },
      {
        label: Enum.screen.tabBased.information.title,
        screen: 'information.InformationScreen',
        icon: Variables.icon.information.source,
        title: Enum.screen.tabBased.information.title,
        navigatorStyle: NavigatorStyle.tabBasedStyle
      },
      {
        label: Enum.screen.tabBased.account.title,
        screen: 'account.AccountScreen',
        icon: Variables.icon.account.source,
        title: Enum.screen.tabBased.account.title,
        navigatorStyle: NavigatorStyle.tabBasedStyle
      }
    ];
    Navigation.startTabBasedApp({
      appStyle: AppSetting.appStyle,
      tabs,
      tabsStyle: AppSetting.tabsStyle,
      animationType: 'fade',
      drawer: {
        disableOpenGesture: 'true',
        right: {
          screen: 'trade.DrawerTrade'
        }
      }
    });
  }
  startSingle(title, screenName) {
    Layout.tabBarHeight = 0;
    Navigation.startSingleScreenApp({
      screen: {
        screen: screenName,
        title, // title of the screen as appears in the nav bar (optional)
        navigatorStyle: NavigatorStyle.navigatorStyle // https://wix.github.io/react-native-navigation/#/styling-the-navigator
      },
      animationType: 'fade',
    });
  }
  
  startApp() {
    // if (Variables.isJudgeShow) {
    //   this.startSingle(Enum.screen.tabBased.quotation.title, 'quotation.QuotationScreen');
    //   return;
    // }
    // this.startSingle();
    this.startTabBased();
  }
}

