export default {
    isJudgeShow: false,                              //true則只顯示行情功能
    hotline: null,                                   //客服 400-120-9061
    isNetConnectd: false,                            //是有網路連線
    //is_appstore: false,                            //区分appstore，如:false=否，true=是,默认值为false
    //ipa_download_url: '',                          //企业ipa下载地址
    
    // 帳戶 Account
    user: 'zihao',
    pwd: '12345',
    // AsyncStorage - 平台登入
    account: {
        isLogged: false,
        token: { key: 'user_token', value: null },       //token
        secret: { key: 'user_secret', value: null },     //加密過的密碼 -> 密钥
        password: { key: 'user_password', value: null }, //密碼
        mobile: { key: 'user_mobile', value: null },     //用户手机号
        name: { key: 'user_name', value: null }          //使用者名稱
    },
    // AsyncStorage - 交易(操盤)登入
    trade: {
        isLogged: false,
        account: { key: 'trade_account', value: null },  //交易帳號
        password: { key: 'trade_password', value: null } //交易密碼
    },
    icon: {
        size: 24,
        // main tab
        quotation: { source: null, name: 'line-chart' },
        trade: { source: null, name: 'file-text-o' },
        information: { source: null, name: 'dot-circle-o' },
        account: { source: null, name: 'user-o' },
        // 行情
        question: { source: null, name: 'question-circle-o', id: 'question' },
        list: { source: null, name: 'list', id: 'list' },
        grid: { source: null, name: 'th', id: 'grid' },
        search: { source: null, name: 'search', id: 'search' },
        closeAbout: { source: null, name: 'close', id: 'closeAbout' },
        closeLogin: { source: null, name: 'close', id: 'closeLogin' },
        closeRegister: { source: null, name: 'close', id: 'closeRegister' },
        closeStopLoss: { source: null, name: 'close', id: 'closeStopLoss' },
        closeCondition: { source: null, name: 'close', id: 'closeCondition' },
        closeHistoryTrade: { source: null, name: 'close', id: 'closeHistoryTrade' },
        closeAccountCustomerService: { source: null, name: 'close', id: 'closeAccountCustomerService' },
        closeAccountDeposit: { source: null, name: 'close', id: 'closeAccountDeposit' },
        closeAppendTraderBond: { source: null, name: 'close', id: 'closeAppendTraderBond' },
        // 交易
        closeTradeLogin: { source: null, name: 'close', id: 'closeTradeLogin' },
        // 行情圖表
        bars: { source: null, name: 'bars', id: 'bars' },
        // 止損單
        exclamationStopLoss: { source: null, name: 'exclamation-circle', id: 'exclamationStopLoss' },
        // 條件單
        exclamationCondition: { source: null, name: 'exclamation-circle', id: 'exclamationCondition' },
        plusCondition: { source: null, name: 'plus', id: 'plusCondition' },
        // 歷史成交
        exclamationHistoryTrade: { source: null, name: 'exclamation-circle', id: 'exclamationHistoryTrade' },
        // 我要提現
        exclamationAccountWithDraw: { source: null, name: 'exclamation-circle', id: 'exclamationAccountWithDraw' },
        // 帳戶
        accountCustomerService: { source: null, name: 'users', id: 'accountCustomerService' },
        // IndicatorPullToRefresh
        indicatorArrowUp: { source: null, name: 'arrow-up', id: 'arrowUp' },
        indicatorArrowDown: { source: null, name: 'arrow-down', id: 'arrowDown' },
    }
};
