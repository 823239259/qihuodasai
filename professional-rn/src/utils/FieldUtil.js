import _ from 'lodash';
import { Config } from '../global';

export default {
    getFloat(text) {
        if (text.charAt(0) === '0'&& text.charAt(1) !== '.' && text.length >1) {
            return text.charAt(1)
        }
        // 狀況：2.00.0
        if (isNaN(text)) {
            return text.slice(0, -1);
        }
        // 小數最多輸入4位數
        const textArr = text.split('.');
        if (textArr.length === 2) {
            if (textArr[1].length > Config.maxFractionLength) {
                return text.slice(0, -1);
            }
        }
        // 整數最多輸入maxIntegerLength位數
        if (textArr[0].length > Config.maxIntegerLength) {
            return text.slice(0, -1);
        }
        
        return text;
    },
    getInt(text) {
        if (text.length === 0 || text === '.') {
            return '';
        }
        // 狀況: copy/paste string
        if (isNaN(text)) {
            return text.slice(0, -1);
        }
        // 不能輸入小數點
        if (text.charAt(text.length - 1) === '.') {
            return text.slice(0, -1);
        }
        // 整數最多輸入maxIntegerLength位數
        if (text.length > Config.maxIntegerLength) {
            return text.slice(0, -1);
        }
        return _.toString(_.parseInt(text));
    },
    getStringNumber(text) {
        // 不能輸入小數點
        if (text.charAt(text.length - 1) === '.') {
            return text.slice(0, -1);
        }
        if (text.length > Config.maxStringLength) {
            return text.slice(0, -1);
        }
        return text;
    },
    getString(text) {
        if (text.length > Config.maxStringLength) {
            return text.slice(0, -1);
        }
        return text;
    },
    // 验证手机号是否符合格式要求 
    validateMobile(mobile) {
		const mobilePattern = /^(((13[0-9])|(14[5-7])|(15[0-9])|(17[0-9])|(18[0-9]))+\d{8})$/;
		if (mobilePattern.test(mobile)) {
			return true;
		}
		return false;
    },
    // 验证密码是否符合格式要求
    validatePassword(password) {
		const passwordPattern = /^(?![0-9]+$)(?![a-zA-Z]+$)[a-zA-Z\d]{6,16}$/;
		if (passwordPattern.test(password)) {
			return true;
		}
		return false;
	}
};
