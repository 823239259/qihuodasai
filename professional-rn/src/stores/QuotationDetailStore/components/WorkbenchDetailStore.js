import { observable, action } from 'mobx';
import _ from 'lodash';
import { Animated } from 'react-native';
import { Logger, I18n } from '../../../utils';
import { Config, Variables, Layout, Enum } from '../../../global';

export default class WorkbenchDetailStore {

    // view
    navigator = null;
    tabView = null;
    // enum
    // animations
    @observable keyboardHeight = new Animated.Value(0);
    @observable doneButtonOpacity = new Animated.Value(0);
    @observable doneButtonHeight = new Animated.Value(Layout.contentWithBottomHeight);
    // variables
    @observable isTradeLoginDialogVisible = false;
    @observable isTradeNumVisible = true;
    @observable isTradeLastVisible = true;
    // setTimeout
    timeoutIndex = {
        loginConfirm: null,
        loginSuccess: null,
        loginDismiss: null
    };
    logger = null;
    constructor() {
        this.logger = new Logger(WorkbenchDetailStore);
    }
    @action init(navigator, tabView) {
        this.navigator = navigator;
        this.tabView = tabView;
    }
    @action clear() {
        this.isTradeNumVisible = true;
        this.isTradeLastVisible = true;
        clearTimeout(this.timeoutIndex.loginSuccess);
        clearTimeout(this.timeoutIndex.loginDismiss);
    }
    @action toggleTradeNum(page) {
        if (page === Enum.detail.pageIndex.tradePage) {
            this.isTradeNumVisible = false;
        } else {
            this.isTradeNumVisible = true;
        }
    }
    @action toggleTradeLast(page) {
        if (page === Enum.detail.pageIndex.hanidcapPage || page === Enum.detail.pageIndex.tradePage) {
            this.isTradeLastVisible = false;
        } else {
            this.isTradeLastVisible = true;
        }
    }
    @action onCancelTradeLoginDialog() {
        this.isTradeLoginDialogVisible = false;
    }
    @action onConfirmTradeLoginDialog() {
        this.isTradeLoginDialogVisible = false;
        // 無法馬上關閉Dialog，又馬上轉頁，需要等一下..
        this.timeoutIndex.loginConfirm = setTimeout(() => {
            this.toTradeLogin();
        }, Config.waitingTime);
    }
    toTradeLogin() {
        this.navigator.showModal({
            screen: 'quotation.TradeLoginModal',
            title: '操盘登录',
            navigatorButtons: {
            leftButtons: [
                {
                    icon: Variables.icon.closeTradeLogin.source,
                    id: Variables.icon.closeTradeLogin.id
                }
            ]
            },
            passProps: {
                title: '',
                onTradeLoginSuccess: () => { this.tradeLoginSuccess(); }
            }
        });
        clearTimeout(this.timeoutIndex.loginConfirm);
    }
    @action showTradeLoginDialog() {
        this.isTradeLoginDialogVisible = true;
    }
    @action tradeLoginSuccess() {
        this.logger.trace('tradeLoginSuccess');
        this.toggleTradeNum(Enum.detail.pageIndex.tradePage);
        
        this.timeoutIndex.loginDismiss = setTimeout(() => {
            this.navigator.dismissModal();
        }, Config.waitingTime);

        this.timeoutIndex.loginSuccess = setTimeout(() => {
            this.goToPage(Enum.detail.pageIndex.tradePage);
        }, Config.waitingTime);
    }
    @action goToPage(page) {
        this.toggleTradeNum(page);
        this.toggleTradeLast(page);
        this.tabView && this.tabView.goToPage(page);
    }
    showKeyboard(e) {
        const keyboardHeight = e.endCoordinates.height;
        const iphoneXPadding = Layout.isIphoneX ? 34 : 0;
        Animated.parallel([
            // padding keyboard - 注意扣除 tabBar高度，保留doneButton位置
            Animated.timing(this.keyboardHeight, {
                duration: e.duration,
                toValue: ((keyboardHeight + Layout.doneButtonHeight) - Layout.buttonLargeHeight) - iphoneXPadding
            }),
            // doneButton - position: 'absolute': 計算方式用絕對位置推算
            Animated.timing(this.doneButtonOpacity, {
                duration: e.duration,
                toValue: 1,
            }),
            Animated.timing(this.doneButtonHeight, {
                duration: e.duration,
                toValue: Layout.contentWithBottomHeight - keyboardHeight - Layout.doneButtonHeight
            })
        ]).start();
    }
    hideKeyboard(e) {
        Animated.parallel([
            // padding keyboard - 注意扣除 tabBar高度，保留doneButton位置
            Animated.timing(this.keyboardHeight, {
                duration: e.duration,
                toValue: 0,
            }),
            // doneButton - position: 'absolute': 計算方式用絕對位置推算
            Animated.timing(this.doneButtonOpacity, {
                duration: e.duration,
                toValue: 0,
            }),
            Animated.timing(this.doneButtonHeight, {
                duration: e.duration,
                toValue: Layout.contentWithBottomHeight, // 藏到最底下就好了
            })
        ]).start();
    }
}

