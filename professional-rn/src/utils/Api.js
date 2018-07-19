import axios from 'axios';
import { toJS, action } from 'mobx';
import { ToastRoot } from '../components';
import { Config, Variables, Enum } from '../global';
import Logger from '../utils/Logger';

const business_Type = 99;
class Api {
    logger = null;
    // for mock
    bankCards = null;

    constructor() {
        this.logger = new Logger('Api');
    }
    _onError(url, e) {
        // ToastRoot.show(e.message);
        this.logger.error(`onError - url: ${url}, message: ${e.message}`);
    }
    _onCatch(url, e) {
        // ToastRoot.show('当前网络不给力，请稍后再试');
        this.logger.error(`onCatch - url: ${url}, message: ${e.message}`);
    }
    request(url, params, onSuccess, onError, onCatch) {
        let success = false;
        axios({
            method: 'post',
            url: `${Config.api_domain}/${url}`,
            params,
            headers: {
                token: Variables.account.token.value,
                secret: Variables.account.secret.value,
                version: '1.1'
            }
        }).then((response) => {
            const result = response.data;
            success = result.success;
            if (result.success) {
                try {
                    onSuccess(result);
                } catch (error) {
                    this.logger.error('request onSuccess catch error');
                    console.log(error);
                }
            } else {
                onError ? onError(result) : this._onError(url, result);
            }
        }).catch((error) => {
            if (!success) {
                onCatch ? onCatch(error) : this._onCatch(url, error);
            }
        });
    }
    test(store) {
        console.log(JSON.stringify(toJS(store)));
        axios.get('https://facebook.github.io/react-native/movies.json')
        .then(response => { 
            console.log(response);
            console.log(response.data);
        })
        .catch(error => console.log(error));
    }
    login(loginName, password, onSuccess, onError, onCatch, imageCode) {
        const params = {
            loginName,
            password
        };
        imageCode && (params.code = imageCode);
        this.logger.info(`login 使用者登入 - loginName: ${loginName}, password: ${password}, imageCode: ${imageCode}`);
        
        this.request('login', params, onSuccess, onError, onCatch);
    }
    logout() {
        
    }
    // 获取用户信息
    // 1. AccountStore - businessType: 4
    // 2. AppendTraderBondModal - businessType: 1              => 獲得匯率 -> 此時是公司要將 人民幣->美金 換給使用者 ˙¥ 7.1 = $ 1
    // 3. ApplyEnd - businessType: 2, couponBusinessType: 8    => 獲得匯率 -> 此時是公司要將 美金->人民幣 換給使用者  $ 1   = ¥ 6.9
    getBalanceRate(businessType, couponBusinessType, onSuccess) {
        const param = { businessType };
        if (couponBusinessType) {
            param.couponBusinessType = couponBusinessType;
        }
        this.logger.info(`getBalanceRate - businessType: ${param.businessType}, couponBusinessType: ${param.couponBusinessType}`);
        this.request('user/getbalancerate', param, onSuccess);
    }
    // 獲取資金明細
    getFund(onSuccess) {
        this.request('user/fund/list', {}, onSuccess);
    }
    // 獲取交易帳號
    getTradeAccountList(onSuccess) {//开户记录
        this.request('user/ftrade/list', {businessType:business_Type}, onSuccess);
    }
    // 获取当前方案信息 方案id
    getTradeAccountDetail(id, onSuccess) {
        this.request('user/ftrade/details', { id }, onSuccess);
    }
    // 获取历史成交明细 方案id
    getFstTradeDetail(id, onSuccess) {
        this.request('user/ftrade/getFstTradeDetail', { id }, onSuccess);
    }
    // 獲取圖
    getBannerList(onSuccess) {
        this.request('banner/list', { type: 9 }, onSuccess);
    }
    // 獲取 開戶申請 資料 
    getTradeParams(onSuccess) {//获取期货操盘参数
        this.request('ftrade/params', { businessType: business_Type }, onSuccess);
    }
    // 获取支付申请数据
    getApplyTradeInfo(traderBond, onSuccess) {//申请操盘
        this.request('/user/ftrade/handle', { traderBond, businessType: business_Type, tranLever: 0 }, onSuccess);
    }
    endApplyTrade(id, cId, onSuccess, onError) {
        if (Config.mock) {
            onSuccess();
            return;
        }
        this.request('user/ftrade/endtrade', { id, cId, businessType: business_Type }, onSuccess, onError);
    }
    getLiveInformation(pageIndex, onSuccess) {
        this.request('crawler/getCrawler', { size: 10, pageIndex }, onSuccess);
    }
    // 支付
    payApplyTrade(traderBond, onSuccess, onError) {
        if (Config.mock) {
            onSuccess({ data: { id: 'ff8080816063f3e1016068e00a6e00d1', stateType: 4 } });
            return;
        }//支付确认
        this.request('user/ftrade/handle', { traderBond, vid: -1, businessType: business_Type, tranLever: 0 }, onSuccess, onError);
    }
    // 追加保证金
    addBond(vid, appendFund, onSuccess, onError) {
        if (Config.mock) {
            onSuccess();
            return;
        }
        this.request('user/ftrade/addbond', { id: vid, addBond: appendFund }, onSuccess, onError);
    }
    // 發送驗證碼
    // 只有註冊時RegisterStore需要 yzm(圖形驗證碼) : 這時候type = 1 
    sendSms(mobile, type, yzm, onSuccess, onError) {
        const param = {
            mobile,
            type
        };
        if (yzm) {
            param.yzm = yzm;
        }
        this.logger.info(`sendSms 等待獲取簡訊驗證碼 - mobile: ${mobile}, type: ${type}, yzm: ${yzm}`);
        if (Config.mock) {
            onSuccess();
            return;
        }
        this.request('sms', param, onSuccess, onError);
    }
    // 發送驗證碼
    sendSecuritySms(mobile, type, onSuccess, onError) {
        const param = {
            mobile,
            type
        };
        this.logger.info(`sendSecuritySms 等待獲取簡訊驗證碼 - mobile: ${mobile}, type: ${type}`);
        if (Config.mock) {
            onSuccess();
            return;
        }
        this.request('user/security/send_sms', param, onSuccess, onError);
    }
    // 簡訊驗證碼
    validateVerification(mobile, verification, onSuccess, onError) {
        this.logger.info(`validate/sms 確認簡訊驗證碼是否正確 - mobile: ${mobile}, verification: ${verification}`);
        if (Config.mock) {
            onSuccess();
            return;
        }
        this.request('validate/sms', { mobile, code: verification }, onSuccess, onError);
    }
    // 修改号码
    upPhone(mobile, newCode, oldCode, onSuccess, onError) {
        this.logger.info(`user/security/upphone 修改号码 - mobile: ${mobile}, newCode: ${newCode}, oldCode: ${oldCode}`);
        if (Config.mock) {
            onSuccess();
            return;
        }
        this.request('user/security/upphone', { mobile, newCode, oldCode }, onSuccess, onError);
    }
    // 设置提现密码
    setWithdrawPwd(code, password, onSuccess, onError) {
        this.logger.info(`user/security/set_withdraw_pwd 设置提现密码 - code: ${code}, password: ${password}`);
        if (Config.mock) {
            onSuccess();
            return;
        }
        this.request('user/security/set_withdraw_pwd', { code, password }, onSuccess, onError);
    }
    // verification：驗證碼
    regist(mobile, password, verification, onSuccess, onError) {
        this.logger.info(`regist 註冊 - mobile: ${mobile}, password: ${password}, verification: ${verification}`);
        if (Config.mock) {
            onSuccess();
            return;
        }
        this.request('regist', { parentGeneralizeId: ' ', channel: '57b2d65067e58e601d00258a', mobile, password, code: verification }, onSuccess, onError);
    }
    resetPassword(mobile, password, verification, onSuccess, onError) {
        this.request('reset_password', { mobile, password, code: verification }, onSuccess, onError);
    }
    validateRealName(name, card, onSuccess, onError) {
        if (Config.mock) {
            onSuccess();
            return;
        }
        this.request('user/security/validatecard', { name, card }, onSuccess, onError);
    }
    // 取得該使用者銀行卡
    getBankList(onSuccess) {
        if (Config.mock) {
            if (!this.bankCards) {
                this.bankCards = { data: [
                        { bankId: 'ff8080816063f3e101606858fc5c0086', bankName: '中国工商银行', card: '55555555555', uid: 'ff8080815d123c9a015d1311b01c0002', abbreviation: 'icbc', default: true },
                        { bankId: 'ff8080816063f3e101606876be0c0090', bankName: '中国工商银行', card: '11111111111', uid: 'ff8080815d123c9a015d1311b01c0002', abbreviation: 'icbc', default: false },
                    ] 
                };
            }
            onSuccess(this.bankCards);
            return;
        }
        this.request('user/withdraw/bank_list', {}, onSuccess);
    }
    addBankCard(bank, province, city, address, card, onSuccess, onError) {
        this.logger.info(`addBankCard 增加銀行卡 - bank: ${bank}, province: ${province}, city: ${city}, address: ${address}, card: ${card}`);
        if (Config.mock) {
            const bankName = Enum.bankOptions.find((bo) => {
                return bo.value === bank;
            }).text;
            this.bankCards.data.push({
                bankId: String(Math.random()).split('.')[1],
                bankName,
                card,
                uid: String(Math.random()).split('.')[1],
                abbreviation: bank,
                default: false
            });
            onSuccess();
            return;
        }
        this.request('user/withdraw/add_bank', { bank, prov: province, city, address, card }, onSuccess, onError);
    }
    setBankCardDefault(bankId, onSuccess, onError) {
        this.logger.info(`setBankCardDefault 設定當前使用銀行卡 - bankId: ${bankId}`);
        if (Config.mock) {
            const originalDefault = this.bankCards.data.find((o) => {
                return o.default === true;
            });
            originalDefault.default = false;
            const newDefault = this.bankCards.data.find((o) => {
                return o.bankId === bankId;
            });
            newDefault.default = true;
            onSuccess();
            return;
        }
        this.request('user/withdraw/set_default_bank', { bankId }, onSuccess, onError);
    }
    deleteBankCard(bankId, onSuccess) {
        this.logger.info(`deleteBankCard 刪除銀行卡 - bankId: ${bankId}`);
        if (Config.mock) {
            const bankCardsFilterd = this.bankCards.data.filter((o) => {
                return o.bankId !== bankId;
            });
            this.bankCards.data = bankCardsFilterd;
            onSuccess();
            return;
        }
        this.request('user/withdraw/del_bank', { bankId }, onSuccess);
    }
    // 取得銀行選項
    getBankCardOptions(onSuccess) {
        this.request('user/withdraw/support_banks', {}, onSuccess);
    }
    // 提现
    withdraw(bankAbbreviation, card, money, withdrawPwd, onSuccess, onError) {
        this.logger.info(`withdraw 提现 - bankAbbreviation: ${bankAbbreviation}, card: ${card}, money: ${money}, withdrawPwd: ${withdrawPwd}`);
        if (Config.mock) {
            onSuccess();
            return;
        }
        this.request('user/withdraw/handle', {}, onSuccess, onError);
    }
    // 判断是否显示马甲包
    getJudgeShow(onSuccess) {
        this.request('judgeShow', { appVersion: Config.judgeShow.appVersion, parms: Config.judgeShow.appChannelId }, onSuccess);
    }
    // 取得客服電話
    getHotline(onSuccess) {
        this.request('hotline', {}, onSuccess);
    }
    getTradeSocketInfo(onSuccess) {
        this.request('socket/config/getVersions', { appVersions: Config.tradeSocket.version }, onSuccess);
    }
    getNewsDetail() {
        axios({
            method: 'post',
            url: `${Config.api_domain}/news/list`,
            data: {
                loginName: '13880667347',
                password: 'a123456'
            },
            headers: {
                token: 'user_token',
                secret: 'user_secret'
            }
        })
        .then((response) => {
            console.log('onSuccess'); 
            console.log(response); 
        }).catch((error) => {
            console.log('onError'); 
            console.log(error); 
        });
    }
    getCrawlerByChannelLiveContent() {
        axios({
            method: 'post',    
            url: 'http://api.incentm.com/crawler/getCrawlerByChannelLiveContent',
            params: {
                size: 30,
                channelset: 1,
                pageIndex: 0
            }
        })
        .then((response) => {
            console.log(response);
        })
        .catch((error) => {
            console.log(error);
        });
    }
}
export default new Api();
