import { Platform } from 'react-native';
import { Navigation } from 'react-native-navigation';
import SplashScreen from 'react-native-splash-screen';
import Provider from '../../src/utils/MobxRnnProvider';
import { registerScreens, registerScreenVisibilityListener } from '../screens';
import StoreManager from '../stores';
import { NavigatorStyle, Layout } from '../../src/global';
import { Logger, PromiseTestUtil } from '../../src/utils';

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
        this.configureStoreAndRegisterScreen(),
        // this.promiseTestUtil.resolveAfter3Seconds(0) // 等三秒
      ]).then(() => {
        resolve(true);
      });
    });  
  }
  async configureStoreAndRegisterScreen() {
    const stores = await StoreManager.configureStore();
    await registerScreens(stores, Provider);
    registerScreenVisibilityListener();
    return true;
  }  
  startApp() {
    Layout.tabBarHeight = 0;
    Navigation.startSingleScreenApp({
      screen: {
        screen: 'test.TestScreen',
        title: 'TEST',
        navigatorStyle: NavigatorStyle.navigatorStyle // https://wix.github.io/react-native-navigation/#/styling-the-navigator
      },
      animationType: 'fade',
    });
  }
}
