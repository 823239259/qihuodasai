import _ from 'lodash';
import { ToastRoot } from '../components';
import { I18n } from '../utils';
import { Config } from '../global';

class TradeUtil {
    // 手數
    validateNum(num) {
        if (!this.isPriceValid(num)) {
            ToastRoot.show(I18n.message('please_input_correct_num'), { position: Config.toastRoot.position.bottom });
            return false;
        }
        if (num <= 0) {
            ToastRoot.show(I18n.message('please_input_correct_num'), { position: Config.toastRoot.position.bottom });
            return false;
        }
        if (num > 10) {
            ToastRoot.show(I18n.message('maximun_num_10'), { position: Config.toastRoot.position.bottom });
            return false;
        }
        return true;
    }
    validatePrice(price, product, msg) {
        let msgPriceValid = I18n.message('please_input_correct_price');
        if (msg) {
            msgPriceValid = `${msg}: ${msgPriceValid}`;
        }
        if (!this.isPriceValid(price)) {
            ToastRoot.show(msgPriceValid, { position: Config.toastRoot.position.bottom });
            return false;
        }
        let msgMiniTikeSize = `${I18n.message('please_input_miniTikeSize')}: ${product.miniTikeSize}`;
        if (msg) {
            msgMiniTikeSize = `${msg}: ${msgMiniTikeSize}`;
        }
        if (!this.isMiniTikeSizeValid(price, product.miniTikeSize)) {
            ToastRoot.show(msgMiniTikeSize, { position: Config.toastRoot.position.bottom });
            return false;
        }
        let msgMaxMin = I18n.message('max_min_price');
        if (msg) {
            msgMaxMin = `${msg}: ${msgMaxMin}`;
        }
        if (!this.isMaxMinPrice(price, product.lastPrice)) {
            ToastRoot.show(msgMaxMin, { position: Config.toastRoot.position.bottom });
            return false;
        }
        return true;
    }
    getOrderStatusText(orderStatus) {
        let orderStatusText = '';
        if (orderStatus === 0) {
            orderStatusText = '已提交';
        } else if (orderStatus === 1) {
            orderStatusText = '排队中';
        } else if (orderStatus === 2) {
            orderStatusText = '部分提交';
        } else if (orderStatus === 3) {
            orderStatusText = '完全成交';
        } else if (orderStatus === 4) {
            orderStatusText = '已撤单';
        } else if (orderStatus === 5) {
            orderStatusText = '下单失败';
        } else if (orderStatus === 6) {
            orderStatusText = '未知';
        }
        return orderStatusText;
    }
    isPriceValid(price) {
        let isValid = true;
        if (price <= 0 || price === undefined || price === null || isNaN(price) || price.length === 0) {
            isValid = false;
        }
        return isValid;
    }
    // 涨跌幅限制 10%
    isMaxMinPrice(price, lastPrice) {
        const maxPrice = lastPrice * 1.1;
        const minPrice = lastPrice * 0.9;
        if (price > maxPrice) {
            return false;
        }
        if (price < minPrice) {
            return false;
        }
        return true;
    }
    isMiniTikeSizeValid(price, miniTikeSize) {
        const a = _.parseInt((price * 10000).toFixed());
        const b = _.parseInt((miniTikeSize * 10000).toFixed());
        if (a % b !== 0) {
            return false;
        }
        return true;
    }
    /**
     * 获取市场价格(doGetMarketPrice)
     * @param {Object} price 最新价格
     * @param {Object} miniTikeSize 最小变动单位
     * @param {Object} drection 买卖方向(0-买，1-卖)
     */
    getMarketPrice(lastPrice, miniTikeSize, drection, dotSize) {
        const price = Number(lastPrice);
        const base = 20;
        const priceRange = miniTikeSize * base;
        let newPrice = 0;
        if (drection === 0) {
            newPrice = price + priceRange;
        } else {
            newPrice = price - priceRange;
        }
        return Number(parseFloat(newPrice).toFixed(dotSize));
    }
    getOrderRef() {
        return new Date().getTime();
    }
    /** 
     * 将数值四舍五入(保留n位小数)后格式化成金额形式 
     * 
     * @param s 数值(Number或者String) 
     * @param n 保留小数位(Number或者String) 不传默认2位  整数可不传
     * @return 金额格式的字符串,如'1,234,567.45' 
     * @type String 
     */
    formatCurrency(s, n) {
        var num = s;
        s = Math.abs(s);
        n = n > 0 && n <= 20 ? n : 2;
        s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + "";
        var l = s.split(".")[0].split("").reverse(),
            r = s.split(".")[1];
        t = "";
        for(i = 0; i < l.length; i++) {
            t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "");
        }

        var result = num;
        if(n == 0 || num.toString().indexOf(".") == -1) {

            result = t.split("").reverse().join("");
        } else {
            result = t.split("").reverse().join("") + "." + r;
        }

        if(num < 0) {
            result = "-" + result;
        }
        return result;
    }
}
export default new TradeUtil();
