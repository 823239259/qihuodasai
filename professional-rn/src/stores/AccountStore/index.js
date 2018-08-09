import { AsyncStorage } from 'react-native';
import { observable, action, computed, observe } from 'mobx';
import { Config, Variables, Enum } from '../../global';
import { ToastRoot } from '../../components';
import { Logger, Api } from '../../utils';
import BankCard from './models/BankCard';

export default class AccountStore {
    
    // view
    navigator = null;
    eventEmitterBetweenApplyTradeStore = null;

    ACCOUNT_MODAL = { login: 'login', register: 'register' };
    initModal = null;   // 紀錄哪個modal先被開啟 - 之後好計算 - push/pop stacks
    
    @observable isLogged = false;
    @observable isLoginImageDialogVisible = false;
    // account information
    @observable contentRows = [
        { icon: require('../../../img/account/deposit.png'), title: '我要充值', screen: 'account.AccountDeposit' },
        { icon: require('../../../img/account/withdraw.png'), title: '我要提现', screen: 'account.AccountWithdraw' },
        { icon: require('../../../img/account/moneyDetail.png'), title: '资金明细', screen: 'account.AccountFund' },
        { icon: require('../../../img/account/realNameCertification.png'), title: '实名认证', text: '未认证', screen: 'account.AccountRealNameCertification' },
        { icon: require('../../../img/account/creditCard.png'), title: '绑定银行卡', screen: 'account.AccountBankCard' },
        { icon: require('../../../img/account/cellphone.png'), title: '手机绑定', screen: 'account.AccountCellphone' },
        { icon: require('../../../img/account/loginSecurity.png'), title: '登录密码', screen: 'account.AccountLoginPwd' },
        { icon: require('../../../img/account/tradeAccount.png'), title: '交易账号', text: 'down', screen: 'applyRecord' },
    ];
    // BalanceRate 使用者明細
    @observable username;           // 實名認證 名字
    @observable balance;            // 餘額
    @observable isSetWithdrawPwd;   // 是否设置了提现密码
    @observable withdrawHandleFee;  // 提現手續費 - 非固定
    @observable operateMoney;       // 免提現手續費
    @observable isCertification;    // 是否已實名認證
    @computed get isCertificationText() {
        if (this.isCertification) {
            return '已认证';
        }
        return '未认证';
    }
    // fund 資金明細
    @observable funds;
    @observable incomeMoney;
    @observable incomeNum;
    @observable outlayMoney;
    @observable outlayNum;
    // bankCard 銀行卡
    @observable bankCards = [];
    // fTrade 開戶申請
  
    constructor() {
        this.logger = new Logger(AccountStore);
        this.reset();
        this.start().then(() => this.initData());
    }
    // 判斷是否已經有登入，有登入表示有得到token，有token就
    async start() {
        Variables.account.token.value = await AsyncStorage.getItem(Variables.account.token.key);
        if (Variables.account.token.value) {
            Variables.account.secret.value = await AsyncStorage.getItem(Variables.account.secret.key);
            Variables.account.password.value = await AsyncStorage.getItem(Variables.account.password.key);
            Variables.account.mobile.value = await AsyncStorage.getItem(Variables.account.mobile.key);
            this.setIsLogged(true);
            this.logger.info(`已登入過，存有AsynsStorage - mobile: ${Variables.account.mobile.value}, password: ${Variables.account.password.value}, secret: ${Variables.account.secret.value}, token: ${Variables.account.token.value}`);
        }
    }
    // for only one time setting
    init(navigator) {
        this.navigator = navigator;
    }
    @action initData() {
        if (this.isLogged) {
            this.getBalance();
            this.getFund();
            this.getBankCardOptions();
            this.getBankList();
        }
    }
    @action reset() {
        this.resetInfo();
        this.resetFund();
        this.resetBankCard();
    }
    @action resetInfo() {
        this.username = '请您先登录'; // 未实名认证
        this.balance = '0.00';
        this.isCertification = false;
    }
    @action resetFund() {
        this.funds = [];
    }
    @action resetBankCard() {
        this.bankCards = [];
    }
    @action setIsLogged(isLogged) {
        this.isLogged = isLogged;
        Variables.account.isLogged = isLogged;
    }
    setInitModal(modalName) {
        this.initModal = modalName;
    }
    login(loginName, password) {
        Variables.account.mobile.value = loginName; // 圖形驗證碼要用
        Variables.account.password.value = password;
        Api.login(loginName, password, this.loginSuccess.bind(this), this.loginError.bind(this), this.onCatch.bind(this));
    }
    // 其實只是清掉local token
    @action logout() {
        Variables.account.token.value = null;
        Variables.account.secret.value = null;
        Variables.account.password.value = null;
        Variables.account.mobile.value = null;
        AsyncStorage.removeItem(Variables.account.token.key);
        AsyncStorage.removeItem(Variables.account.secret.key);
        AsyncStorage.removeItem(Variables.account.password.key);
        AsyncStorage.removeItem(Variables.account.mobile.key);

        this.setIsLogged(false);
        this.reset();
        // 操盤申請 -> 移動到 -> 開戶申請，因為我要點擊開戶紀錄的時候，再次去查一次
        // 因為是在不顯示畫面的時候作切換，所以不需要setTimeout()
        this.eventEmitterBetweenApplyTradeStore.emit('accountStore', { type: 'logoutSuccess' });
        ToastRoot.show('退出成功');
    }
    @action loginSuccess(result) {
        this.logger.info(`使用者帳號登入成功 - mobile: ${Variables.account.mobile.value}, password: ${Variables.account.password.value}, secret: ${result.data.secret}, token: ${result.data.token}`);
        AsyncStorage.setItem(Variables.account.token.key, result.data.token);
        AsyncStorage.setItem(Variables.account.secret.key, result.data.secret);
        AsyncStorage.setItem(Variables.account.password.key, Variables.account.password.value);
        AsyncStorage.setItem(Variables.account.mobile.key, Variables.account.mobile.value);
        // Api request使用
        Variables.account.token.value = result.data.token;
        Variables.account.secret.value = result.data.secret;

        this.setIsLogged(true);
        this.initData();
        // 無法馬上關閉Dialog，又馬上rerender AccountScreen，必須等一下
        this.setIsLoginImageDialogVisible(false);
        setTimeout(() => {
            this.navigator.dismissModal();
        }, Config.waitingTime);
        // 操盤申請 -> 移動到 -> 開戶申請，因為我要點擊開戶紀錄的時候，再次去查一次
        // 因為是在不顯示畫面的時候作切換，所以不需要setTimeout()
        this.eventEmitterBetweenApplyTradeStore.emit('accountStore', { type: 'loginSuccess' });
        ToastRoot.show('登录成功');
    }
    loginError(result) {
        this.logger.warn(`loginError - message: ${result.message}`);
        const data = result.data;
        // 必須輸入圖形驗證碼
        if (data.num > 2) {
            this.isLoginImageDialogVisible = true;
        }

        this.setIsLogged(false);
        ToastRoot.show(result.message);
    }
    // 原本sourceCode有可能出現錯誤的就是網路問題..
    onCatch(error) {
        this.setIsLogged(false);
        ToastRoot.show('当前网络不给力，请稍后再试');
    }
    // ImageVerification - 圖形驗證碼
    loginImageVerify(loginName, password, imageCode) {
        Api.login(loginName, password, this.loginSuccess.bind(this), this.loginErrorImageVerify.bind(this), this.onCatch.bind(this), imageCode);
    }
    loginErrorImageVerify(result) {
        this.setIsLoginImageDialogVisible(false);
        const code = result.code;
        if (code == 6) {
            let restTime = ((result.data.date) / 3600).toString();
            restTime = restTime.substring(0, 3);
            
            if (restTime == 3) {
                ToastRoot.show(`密码输错已超过5次,账户将被被冻结3小时\n距解冻还有：${restTime} 小时`);
            } else {
                const restTimes = restTime.split('.', 2);
                ToastRoot.show(`密码输错已超过5次,账户将被被冻结3小时\n距解冻还有：${restTimes[0]} 小时 ${restTimes[1] * 6} 分钟`);
            }
        } else if (code == 2 || code == 4) {
            ToastRoot.show(result.message);
        }
    }
    // 获取账户余额
    @action getBalance(onSuccess) {
        Api.getBalanceRate(4, null, onSuccess ? onSuccess : this.getBalanceRateSuccess, () => this.getBalanceRateFail());
    }
    @action.bound getBalanceRateSuccess(result) {
        const { data } = result;
        this.username = data.username ? data.username : `${this.getPhoneSecret(Variables.account.mobile.value)}`;
        this.balance = data.balance; // 0.00元
        this.isSetWithdrawPwd = data.isSetDrawPwd;
        this.withdrawHandleFee = data.drawHandleFee;
        this.operateMoney = data.operateMoney;
        this.isCertification = data.isCertification;
        this.logger.info(`getBalanceRateSuccess 取得使用者資料 - username: ${this.username}, balance: ${this.balance}, isSetWithdrawPwd: ${this.isSetWithdrawPwd}, withdrawHandleFee: ${this.withdrawHandleFee}, operateMoney: ${this.operateMoney}, isCertification: ${this.isCertification}`);
    }
    @action.bound getBalanceRateFail() {
        this.setIsLogged(false);
        this.reset();
    }
    // 更新資金明細
    @action getFund() {
        Api.getFund(result => this.getFundSuccess(result), () => this.getFundFail());
    }
    @action.bound getFundSuccess(result) {
        const data = result.data;
        this.funds = data.fundList;
        this.incomeMoney = data.incomeMoney.toFixed(2);
        this.incomeNum = data.incomeNum;
        this.outlayMoney = data.outlayMoney.toFixed(2);
        this.outlayNum = data.outlayNum;
    }
    @action.bound getFundFail() {
        this.setIsLogged(false);
        this.reset();
    }
    // 取得銀行選項
    @action getBankCardOptions() {
        // 有了就不需要查一次
        if (Enum.bankOptions.length !== 0) {
            return;
        }
        Api.getBankCardOptions((result) => {
            Enum.bankOptions = result.data.map((bank) => {
                return { value: bank.abbreviation, text: bank.bankName };
            });
        });
    }
    // 取得該使用者銀行卡
    @action getBankList() {
        Api.getBankList(result => {
            this.bankCards = result.data.map((bank) => {
                return new BankCard(bank.bankId, bank.bankName, bank.card, bank.uid, bank.abbreviation, bank.default);
            });
        });
    }
    // 設定當前使用銀行卡
    @action setBankCardDefault(bankId) {
        Api.setBankCardDefault(bankId, () => this.setBankCardDefaultSuccess(), (result) => this.setBankCardDefaultError(result));
    }
    @action.bound setBankCardDefaultSuccess() {
        ToastRoot.show('设置成功');
        this.getBankList();
    }
    @action.bound setBankCardDefaultError(result) {
        if (result.code == 3) {
            ToastRoot.show('银行卡不存在');
            return;
        }
        ToastRoot.show('设置失败');
    }
    // 刪除銀行卡
    @action deleteBankCard(bankId) {
        Api.deleteBankCard(bankId, () => this.deleteBankCardSuccess());
    }
    @action.bound deleteBankCardSuccess() {
        ToastRoot.show('删除成功');
        this.getBankList();
    }
    // 提现
    @action withdraw(bankAbbreviation, card, money, withdrawPwd) {
        Api.withdraw(bankAbbreviation, card, money, withdrawPwd, () => this.withdrawSuccess(), (result) => this.withdrawError(result));
    }
    @action.bound withdrawSuccess() {
        ToastRoot.show('提交成功，系统会尽快处理');
    }
    @action.bound withdrawError(result) {
        if (result.code == 0) {
            ToastRoot.show('token失效');
        } else if (result.code == 2) {
            ToastRoot.show('提现失败');
        } else if (result.code == 3) {
            ToastRoot.show('用户信息不存在');
        } else if (result.code == 4) {
            ToastRoot.show('银行卡卡号不存在');
        } else if (result.code == 5) {
            ToastRoot.show(`你的账户已被限制提现，具体原因为${result.message}, 请联系客服解除限制！`);
        } else if (result.code == 6) {
            ToastRoot.show('系统升级期间无法提现');
        } else if (result.code == 7) {
            ToastRoot.show('余额不足不能提现');
        } else if (result.code == 8) {
            ToastRoot.show('当天取款次数不能超过5次');
        } else if (result.code == 9) {
            ToastRoot.show('每次提现金额不能小于10元');
        } else if (result.code == 10) {
            ToastRoot.show('提现密码错误');
        } else if (result.code == 11) {
            ToastRoot.show('网银平台暂不支持此银行提现，请重新添加银行！');
        } else if (result.code == 12) {
            ToastRoot.show('网银限制，单笔提现金额不能超过50000元，每天可提现5笔。');
        } else if (result.code == 13) {
            ToastRoot.show('未实名认证');
        }
    }
    getPhoneSecret(phone) {
        return `${phone.substring(0, 3)}****${phone.substring(7, 11)}`;
    }
    @action setIsLoginImageDialogVisible(isLoginImageDialogVisible) {
        this.isLoginImageDialogVisible = isLoginImageDialogVisible;
    }
    setEventEmitterbetweenApplyTradeStore(eventEmitter) {
        this.eventEmitterBetweenApplyTradeStore = eventEmitter;
    }
}

