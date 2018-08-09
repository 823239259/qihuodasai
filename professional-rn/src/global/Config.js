const domainName = 'duokongtai';    // oldest: incentm, older: qhinom new:vs
const quoteDomainName = 'vs';
const tradeDomainName = 'duokongtai';
const apiDomainName = 'duokongtai';
export default {
    /********************** system **********************/
    mock: false,                // for Api/Store test
    theme: 'DarkBlue',          // Rhino, DarkBlue
    locale: 'zh-cn',
    // Logger
    logLevel: 99,                // 0 開起全部，99關閉
    logShowTime: true,
    // time
    waitingTime: 700,           // Navigation, Screen, Dialog, Toast在某些狀況下，不能夠連續render
    socketReconnectTime: 1500,  // socket reconnect time
    socketReconnectCount: 5,    // tradeSocket auto reconnect 次數
    stateRecoverTime: 2000,     // 只判斷一個state: 1.NetInfo 2.AppState
    
    keepAliveTime: 10000,       // 這個秒數必須大於 TradeSocket heatbeat時間 大約4秒，因此多設1秒
    backForHeartbeatTime: 5000, 

    allWaitingTime: 200,        // 全部撤單、全部平倉：為了可以一筆筆顯示訊息出來，小等一下時間
    heartBeatTime: 10000,
    throttleTime: 1000,
    chartHighlightTime: 3000,
    judgeShow: {
        appVersion: '1.1.6',
        // 用來統計使用者的iphone/android/型號
        // appChannelId: '5a01578f8f4a9d484900018d' // iOS
        appChannelId: '5a966705f43e48152b000143'    // android
    },
    /********************** url **********************/
    marketSocketUrl: 'ws://192.168.0.232:9102',//`ws://quote.${quoteDomainName}.com:9002`,                          //指向内部数据，由domainName 修改为quoteDomainName          by zihaoWang 18.6.8
    tradeSocket: {
        futinUrl:'',
        futoutUrl:'',
        isMock: 1,
        version: 6.1,
        source: 'xn_app' // app
    },
    api_domain: `http://test.api.${apiDomainName}.cn`,                                       //指向内部数据  old:`http://api.${domainName}.com`        by zihaoWang 18.6.8
    base_images_url: `http://test.manage.${domainName}.cn`, //图片地址                       //暂未指向新url  by zihaoWang 18.6.8
    customer_service_url: `http://api.${apiDomainName}.cn/consistentbeauty.html`,      //old:`http://api.${domainName}.com/consistentbeauty.html`  by zihaoWang 18.6.8
    api_recharge_url: `http://test.pay.${domainName}.cn`,                                  //暂未指向新url  by zihaoWang 18.6.8
    /********************** input **********************/
    maxStringLength: 18,   // 1.电话号码11位 2.银行卡16位 3.身份证18位
    maxFractionLength: 4,  // 小數點可以輸入幾位
    maxAccessMoneyFractionLength: 2,  // 充值提现小数点位数
    maxIntegerLength: 11,   // 整數可以輸入幾位
    /********************** QuotationDetail **********************/
    chartDataLimited: 40,  // 最多取多少資料來畫圖
    kChartAxisMaximum: 55, // 40(chartDataLimited) + 10格 留空的地方
    TimeChartPortion: 5,
    chartLastestLine: 15,
    observableArrLimited: 1000, // https://github.com/mobxjs/mobx/issues/381
    // OnRspQryHistory(歷史資料)發送之前的OnRtnQuote(最新資料)都放棄，邏輯是先有歷史資料，才會有歷史資料加上最新資料: 
    // xxxChartStore.add() 更新資料會有成交量暴漲的問題，先放棄前x筆OnRtnQuote
    countOnRtnQuote: 5,
    toastRoot: {
        position: {
            top: 50,
            bottom: -60,
            center: 0
        },
        durations: {
            long: 3500,
            short: 2000
        }
    }
};
