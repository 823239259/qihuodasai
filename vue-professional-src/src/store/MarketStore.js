 //控制显示与否的模块
export default class Market {
    constructor () {
        this.state = {
            positionListCont:[], //存持仓列表
            //心跳信息
            HeartBeat:{
                lastHeartBeatTimestamp : 1,	// 最后心跳时间
                oldHeartBeatTimestamp : 0,	// 上一次心跳时间
                intervalCheckTime : 15000  // 间隔检查时间：8秒
            },
            HeartBeat00:{
                lastHeartBeatTimestamp : 1,	// 最后心跳时间
                oldHeartBeatTimestamp : 0,	// 上一次心跳时间
                intervalCheckTime : 15000  // 间隔检查时间：8秒
            },
            quoteConfig:{
    //			url_real: "ws://192.168.0.232:19002",  //测试地址
                url_real: "ws://quote.vs.com:9102",   //正式地址
                userName:"fut_game_webapp",
                passWord:"a123456"
            },
            tradeConfig:{
                version : "3.3",	// 版本
    //			url_real : "ws://192.168.0.232:6102",   //测试地址
                url_real : "ws://139.196.176.60:6101",  //正式地址
                model : "1", // 实盘：0；	模拟盘：1
                client_source : "N_WEB",	// 客户端渠道
    //			username : "000031",		// 账号(新模拟盘——000008、直达实盘——000140、易盛模拟盘——Q517029969)
    //			password : "YTEyMzQ1Ng==" 	// 密码：base64密文(明文：a123456——YTEyMzQ1Ng==     888888——ODg4ODg4	 74552102——NzQ1NTIxMDI=		123456=MTIzNDU2)
    //			username:JSON.parse(localStorage.getItem('tradeUser')).username,
    //			password:JSON.parse(localStorage.getItem('tradeUser')).password
                username:'',
                password:''
            },
            ifUpdateHoldProfit:false, //是否使用最新行情更新持仓盈亏
            ifUpdateAccountProfit:false,//// 是否可以更新账户盈亏标志：资金信息显示完毕就可以更新盈亏
            qryHoldTotalArr:[],//持仓合计回复数组
            qryHoldTotalKV:{},
            quoteIndex: '',
            quoteColor: '',
            /**
             * 缓存账户信息
             */
            CacheAccount:{
                moneyDetail:[],
                jCacheAccount : {},	// key 为CurrencyNo
                jCacheTotalAccount:{
                    TodayBalance : 0.0,	// 今权益
                    TodayCanUse : 0.0,	// 今可用
                    FloatingProfit : 0.0,	// 浮动盈亏
                    CloseProfit : 0.0,	// 平仓盈亏
                    FrozenMoney : 0.0,	// 冻结资金
                    Deposit : 0.0,	// 保证金
                    CounterFee : 0.0,	// 手续费
                    RiskRate : 0.0	// 风险率
                }
            },
            //切换后合约的名字
            selectId: '',
            //订阅推送次数统计
            subscribeIndex:1,
            
            //持仓合约浮盈处理
            CacheHoldFloatingProfit:{
                jHoldFloatingProfit : {},	// 持仓合约对应浮盈
                jCurrencyNoFloatingProfit : {}	// 币种对应浮盈
            },
            
            jContractFloatingProfitVO:{
                currencyNo:'',
                floatingProfit:0.0
            },
            
            //委托列表页面数据
            entrustCont:[],
            OnRspOrderInsertEntrustCont:[],
            
            //挂单页面列表
            orderListCont:[],
            OnRspOrderInsertOrderListCont:[],
            
            //成交记录列表
            dealListCont:[],
            OnRspQryTradeDealListCont:[],
            
            // 订单状态
            OrderType:{
                0: "订单已提交",
                1: "排队中",
                2: "部分成交",
                3: "完全成交",
                4: "已撤单",
                5: "下单失败",
                6: "未知"
            },
            
            openChangealertCurrentObj:null,
            
            layer:null, //用户登出消息文本
            errorMsg: '', //交易错误消息文本
            
            queryHisList:[],
            
            forceLine:0.00, //用户强平线
            
            toast:'',
            
            quoteConnectedMsg:'',
            
            tradeConnectedMsg:'',
            
            tradeLoginSuccessMsg:'', //登录成功消息
            
            tradeLoginfailMsg:'',
            
            layerOnRtnOrder: '',     //买入成功提示
            
    //		appendOrderMsg: '',     //委托提示
            
            
            //止损止盈---------------------------------
            stopLossList:[],
            hasNostopLossList:[],
            
            stopLossTriggeredList:[],//已触发列表
            hasYesstopLossList:[],
            
            stopLossListSelectOneObj:{},
            
            //条件单--------------------------------
            conditionList:[],//条件单未触发列表
            conditionTriggeredList:[],//条件单已触发列表
            noObj:'',
    //		noListCont:[],
            
            triggerConditionList:[],
    //		yesListCont:[],
            
            
            //选择K线时候的值
            selectTime: 1,
            //存进入详情页的No
            currentNo: '',
            //订阅成功后查询品种列表
            orderTemplist:{},
            templateList:{}, //订阅后的最新行情列表
            //缓存数组，用于存最新行情的数据
            tempArr: [],
            jsonDatatemp: {},
            currentdetail: {},
            markettemp: [], //可查询合约列表
            Parameters: [], //已订阅的合约列表
            CacheLastQuote:[],
            volume:0,
            lastTotalVolume: 0,
            jsonDataKline: {
                // "Method": "OnRspQryHistory",
                // "Parameters": {
                //     "ColumNames": ["DateTimeStamp", "LastPrice", "OpenPrice", "LowPrice", "HighPrice", "Position", "Volume"],
                //     "CommodityNo": "CL",
                //     "ContractNo": "1708",
                //     "Count": 102,
                //     "Data": [
                //         ["2017-06-26 09:31:00", 43.38, 43.35, 43.34, 43.39, 548303, 634,0],
                        
                //     ],
                //     "ExchangeNo": "NYMEX",
                //     "HisQuoteType": 0
                // }
            },
    
            //用于存放分时图的历史数据
            jsonData: {
                // "Method": "OnRspQryHistory",
                // "Parameters": {
                //     "ColumNames": ["DateTimeStamp", "LastPrice", "OpenPrice", "LowPrice", "HighPrice", "Position", "Volume"],
                //     "CommodityNo": "CL",
                //     "ContractNo": "1708",
                //     "Count": 102,
                //     "Data": [
                //         ["2017-06-26 09:31:00", 43.38, 43.35, 43.34, 43.39, 548303, 634,0], // [时间, 收盘价, 开盘价, 最低价, 最高价,]
                        
                //     ],
                //     "ExchangeNo": "NYMEX",
                //     "HisQuoteType": 0
                // }
            },
            
            //用于存放从后台抓取的合约数据
            jsonTow: {
                "Method": "OnRtnQuote",
                "Parameters": {
                    "AskPrice1": 0,
                    "AskPrice2": 0,
                    "AskPrice3": 0,
                    "AskPrice4": 0,
                    "AskPrice5": 0,
                    "AskQty1": 0,
                    "AskQty2": 0,
                    "AskQty3": 0,
                    "AskQty4": 0,
                    "AskQty5": 0,
                    "AveragePrice": 0,
                    "BidPrice1": 0,
                    "BidPrice2": 0,
                    "BidPrice3": 0,
                    "BidPrice4": 0,
                    "BidPrice5": 0,
                    "BidQty1": 0,
                    "BidQty2": 0,
                    "BidQty3": 0,
                    "BidQty4": 0,
                    "BidQty5": 0,
                    "ChangeRate": 0,
                    "ChangeValue": 0,
                    "ClosingPrice": 0,
                    "CommodityNo": "CL",
                    "ContractNo": "1708",
                    "DateTimeStamp": "2017-06-29 11:40:36",
                    "ExchangeNo": "NYMEX",
                    "HighPrice": 0,
                    "LastPrice": 0,
                    "LastVolume": 0,
                    "LimitDownPrice": 0,
                    "LimitUpPrice": 0,
                    "LowPrice": 0,
                    "OpenPrice": 0,
                    "Position": 0,
                    "PreClosingPrice": 0,
                    "PrePosition": 0,
                    "PreSettlePrice": 0,
                    "SettlePrice": 0,
                    "TotalAskQty": 0,
                    "TotalBidQty": 0,
                    "TotalTurnover": 0,
                    "TotalVolume": 0
                }
            },
            //绘制分时的设置 条形图
            option1: {},
            //绘制分时的设置 折线图
            option2: {},
            //绘制K线的设置 k线图
            option3: {},
            //绘制K线的设置 条形图
            option4: {},
            //绘制闪电图的设置 
            option5: {},
            //K线用到的数据
            rawData: [],
            //K线用到的数据
            chartDataC: null,
            lightChartTime: { //闪电图
                "time": [],
                "price": []
            },
            switch5min:true, //5分钟k线 划线or更新 更新标识符
            switch15min:true, //5分钟k线 划线or更新 标识符
            switch30min:true, //5分钟k线 划线or更新 更新标识符
        }
    }
   
 }
