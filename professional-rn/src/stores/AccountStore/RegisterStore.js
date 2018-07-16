import { observable, action } from 'mobx';
import validate from 'mobx-form-validate';
import Api from '../../utils/Api';
import { Config, Enum, Variables } from '../../global';
import { ToastRoot } from '../../components';
import { I18n, Logger } from '../../utils';

export default class RegisterStore {
    navigator = null;
    // form
    @observable
    @validate(Enum.validateReg.mobile, `${I18n.message('please_input_corret_phone_number')}`)
    mobile = '';
  
    @observable
    @validate(Enum.validateReg.required, `${I18n.message('required')}`)
    verification = '';
    // end form
    // @observable imageCode, imageCodeCount -> 由FieldWrapper加入進去
    password = null; //registSuccss -> 為了傳給toLogin

    @observable isRegisterImageDialogVisible = false;
    @observable isRegistSuccessDialogVisible = false;

    constructor() {
        this.logger = new Logger(RegisterStore);
    }
    init(navigator) {
        this.navigator = navigator;
        this.clear();
    }
    // 出現圖形驗證碼
    @action onVerificationPress() {
        this.imageCode = '';
        this.setIsRegisterImageDialogVisible(true);
    }
    // 輸入圖形驗證碼
    @action confirmImageVerification() {
        if (!this.imageCode) {
            ToastRoot.show('验证码不能为空', { position: Config.toastRoot.position.top });
            return;
        }
        Api.sendSms(this.mobile, 1, this.imageCode, () => this.sendSmsSuccess(), (result) => this.sendSmsFail(result));
        this.setIsRegisterImageDialogVisible(false);
    }
    // 圖形驗證碼成功
    @action.bound sendSmsSuccess() {
        this.startImageCount();
    }
    @action.bound sendSmsFail(result) {
        // 1是成功
        if (result.code !== '1') {
          ToastRoot.show(result.message);
        }
        // 再次輸入圖形驗證碼
        setTimeout(() => {
          // 只有輸入錯誤驗證碼，才會再次跳出圖形驗證碼Dialog
          if (result.code === '8') {
            this.setIsRegisterImageDialogVisible(true);
          }
        }, Config.waitingTime);
    }
    // 倒數計時簡訊驗證碼
    @action startImageCount() {
        this.imageCodeCount = Enum.imageCodeCount;
    }
    // 輸入簡訊驗證碼 -> 下一步
    next() {
        Api.validateVerification(this.mobile, this.verification, () => this.validateVerificationSuccss(), (result) => this.validateVerificationFail(result));
    }
    validateVerificationSuccss() {
        this.toRegisterConfirmPwd();
    }
    validateVerificationFail(result) {
        if (result.code === '2') {
            ToastRoot.show('注册失败');
        } else if (result.code === '3') {
            ToastRoot.show('手机号码已经存在');
        } else if (result.code === '4') {
            ToastRoot.show('验证码错误或为空');
        } else if (result.code === '5') {
            ToastRoot.show('验证码失效');
        } else if (result.code === '6') {
            ToastRoot.show('推广码错误');
        } else {
            ToastRoot.show(`系统繁忙，请稍后再试！"${result.message}`);
        }
    }
    toRegisterConfirmPwd() {
        this.navigator.push({
          screen: 'account.RegisterConfirmPwd',
          title: '设置密码',
          backButtonTitle: '',
          animationType: 'slide-horizontal'
      });
    }
    regist(password) {
        Api.regist(this.mobile, password, this.verification, () => this.registSuccss(password), (result) => this.registFail(result));
    }
    registSuccss(password) {
        this.password = password;
        this.setIsRegistSuccessDialogVisible(true);
    }
    confirmRegistSuccess() {
        this.setIsRegistSuccessDialogVisible(false);
        setTimeout(() => {
            this.navigator.dismissModal();
        }, Config.waitingTime);
        setTimeout(() => {
            this.toLogin();
        }, Config.waitingTime * 2);
    }
    toLogin() {
        this.navigator.showModal({
            screen: 'account.LoginModal',
            title: '登录',
            passProps: {
                mobileFromStore: this.mobile,
                pwdFromStore: this.password,
            },
            navigatorButtons: {
              leftButtons: [
                  {
                      icon: Variables.icon.closeLogin.source,
                      id: Variables.icon.closeLogin.id
                  }
              ]
            }
        });
        this.clear();
    }
    registFail(result) {
        if (result.code == 2) {
            ToastRoot.show('注册失败！');
            return;
        } else if (result.code == 3) {
            ToastRoot.show('手机号码已经存在！');
            return;
        } else if (result.code == 4) {
            ToastRoot.show('验证码错误或为空！');
        } else if (result.code == 5) {
            ToastRoot.show('验证码失效！');
        } else if (result.code == 6) {
            ToastRoot.show('推广码错误！');
        } else {
            ToastRoot.show(`系统繁忙，请稍后再试！${result.message}`);
        }
    }
    @action clear() {
        this.mobile = '';
        this.verification = '';
        this.password = null;
    }
    @action setIsRegisterImageDialogVisible(isRegisterImageDialogVisible) {
        this.isRegisterImageDialogVisible = isRegisterImageDialogVisible;
    }
    @action setIsRegistSuccessDialogVisible(isRegistSuccessDialogVisible) {
        this.isRegistSuccessDialogVisible = isRegistSuccessDialogVisible;
    }
}
