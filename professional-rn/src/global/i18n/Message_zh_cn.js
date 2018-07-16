import template from './Template';

export default {
    login_success: '行情连接成功',
    logout_success: '行情退出',
    
    trade_login_success: '操盘连接成功',
    trade_logout_success: '操盘退出',

    wait_quotation_reconnect: '行情系统更新中，请耐心耐心',
    wait_trade_reconnect: '交易更新中，请耐心耐心',

    internet_Problem: '当前网络不给力',

    submit_order_success: '提交成功,等待交易',
    trade_success_info: template`交易成功：合约【${0}】,交易手数:【${1}】,交易价格:【${2}】`,
    trade_error: '交易错误',
    please_input_correct_num: '请输入正确的手数',
    please_input_correct_price: '请输入正确的价格',
    maximun_num_10: '最多只能输入10手',
    please_input_miniTikeSize: '不符合最小变动价,请重新输入,最小变动价为',
    max_min_price: '已超过涨跌幅限制',

    please_input_any_account: '请输入帐户',
    please_input_corret_phone_number: '请输入正确手机号码',
    please_input_any_password: '请输入密码',
    please_input_correct_password: '密码由6-16位数字和字母组成',
    please_input_real_name: '真实姓名有误',
    please_input_real_id: '身份证号格式有误',

    required: '必填'
};
