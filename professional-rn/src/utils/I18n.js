import { Config } from '../global';
import Term_zh_cn from '../global/i18n/Term_zh_cn';
import Message_zh_cn from '../global/i18n/Message_zh_cn';

class I18n {
    locale = null;
    currentTerm = null;
    currentMessage = null;
    
    constructor() {
        this.setLocale(Config.locale);
    }
    setLocale(locale) {
        this.locale = locale;
        if (this.locale === 'zh-cn') {
            this.currentTerm = Term_zh_cn;
            this.currentMessage = Message_zh_cn;
        }
    }
    // 訊息類
    message(key, ...args) {
        if (args.length === 0) {
            return this.currentMessage[key];
        }
        const closure = this.currentMessage[key];

        const params = args.map((arg) => {
            return arg;
        });
        return closure(...params);
    }
    // 顯示在畫面的文字
    term(key, ...args) {
        if (args.length === 0) {
            return this.currentTerm[key];
        }
        const closure = this.currentTerm[key];

        const params = args.map((arg) => {
            return arg;
        });
        return closure(...params);
    }
}
export default new I18n();
