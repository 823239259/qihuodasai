import { observable, action } from 'mobx';
import validate from 'mobx-form-validate';
import { ToastRoot } from '../../components';
import { Enum, Config } from '../../global';
import { I18n, Api } from '../../utils';

export default class AccountWithdrawPwdStore {

    accountStore = null;
    navigator = null;

    @observable
    @validate(Enum.validateReg.mobile, `${I18n.message('please_input_corret_phone_number')}`)
    mobile = '';

    @observable
    @validate(Enum.validateReg.required, `${I18n.message('required')}`)
    verification = '';

    @observable
    @validate(Enum.validateReg.password, `${I18n.message('please_input_correct_password')}`)
    newPwd = '';

    @observable
    @validate(Enum.validateReg.password, `${I18n.message('please_input_correct_password')}`)
    confrimPwd = '';

    constructor(accountStore) {
        this.accountStore = accountStore;
    }
    init(navigator) {
        this.navigator = navigator;
        this.reset();
    }
    reset() {
        this.mobile = '';
        this.verification = '';
        this.newPwd = '';
        this.confrimPwd = '';
    }

    @action onVerificationPress() {
        Api.sendSecuritySms(this.mobile, 2, () => this.sendSmsSuccess(), (result) => this.sendSmsFail(result));
    }
    @action.bound sendSmsSuccess() {
        // 開始倒數
        this.imageCodeCount = Enum.imageCodeCount;
        ToastRoot.show('发送成功');
    }
    @action.bound sendSmsFail(result) {
        if (result.code == 2) {
            ToastRoot.show('短信验证码发送失败');	
        } else if (result.code == 3) {
            ToastRoot.show('手机号码已经存在');	
        } else if (result.code == 4) {
            ToastRoot.show('手机号码不存在');	
        } else if (result.code == 5) {
            ToastRoot.show('操作过于频繁，请稍候再试');	
        } else {
            ToastRoot.show('系统繁忙，请稍候再试');	
        }
    }
    @action setWithdrawPwd() {
        if (this.newPwd !== this.confrimPwd) {
            ToastRoot.show('两次密码输入不一致');
            return;
        }
        Api.setWithdrawPwd(this.verification, this.newPwd, () => this.setWithdrawPwdSuccess(), (result) => this.setWithdrawPwdError(result));
    }
    @action.bound setWithdrawPwdSuccess() {
        ToastRoot.show('设置成功');
        // 重新刷新資料 - isSetWithdrawPwd
        this.accountStore.initData();
        setTimeout(() => {
            this.navigator.pop();
        }, Config.waitingTime);
    }
    @action.bound setWithdrawPwdError(result) {
        if (result.code == 2) {
            ToastRoot.show('设置失败');
        } else if (result.code == 3) {
            ToastRoot.show('用户信息不存在');
        } else if (result.code == 4) {
            ToastRoot.show('提款密码不能与登录密码相同');
        } else if (result.code == 5) {
            ToastRoot.show('密码格式有误');
        } else if (result.code == 6) {
            ToastRoot.show('验证码错误或失效');
        }
    }
} 