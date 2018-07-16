import { observable, action } from 'mobx';
import validate from 'mobx-form-validate';
import { Enum } from '../../global';
import { I18n } from '../../utils';

export default class TradeLoginModalStore {
    eventEmitter = null;
    onTradeLoginSuccess = null;

    setEventEmitter(eventEmitter) {
        this.eventEmitter = eventEmitter;
    }

    @observable
    @validate(Enum.validateReg.required, `${I18n.message('please_input_any_account')}`)
    account = '';
  
    @observable
    @validate(Enum.validateReg.required, `${I18n.message('please_input_any_password')}`)
    pwd = '';

    @observable isSubmitted = false;

    @action setIsSubmitted(isSubmitted) {
        this.isSubmitted = isSubmitted;
    }

    @action loginTrade() {
        const accountWithoutSpace = this.account.replace(/\s+/g, '');
        this.eventEmitter.emit('tradeLoginModalStore', { account: accountWithoutSpace, password: this.pwd, onTradeLoginSuccess: this.onTradeLoginSuccess });
    }
}
