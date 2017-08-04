import Vue from 'vue'
import Vuex from 'vuex'


Vue.use(Vuex)

//控制显示与否的模块
var isshow = {
	state: {
		navBarShow: true,
		isconnected: true,
		bottomshow: false,
		pshow: false,
		sshow: false,
		fshow: true,
		kshow: false,
		guideshow: false,
		//是否进入过分时
		isfensshow: false,
		//判断是否是直接画图
		isfenssec: false,
		islightshow: false,
		isklineshow: false
	}
};

//控制个人数据
var account = {
	state: {
		islogin: false, //是否登录
		phone: '', //账户
		password: '', //密码 
		token: '',
		secret: '',
		isCertification: false, //是否实名认证
		username: '', //实名
		balance: 0.00, //余额
		operateMoney: 0.00, //免提现手续费额度
		bankList: [], //已绑定银行卡信息
		//存不知道有用没的数据
		tempList: [],
		//存合约列表
		programList: []
	}
}

//控制行情数据
var market = {
	state: {
		//存持仓列表
		positionListCont:[],
		//心跳信息
		HeartBeat:{
			lastHeartBeatTimestamp : 1,	// 最后心跳时间
			oldHeartBeatTimestamp : 0,	// 上一次心跳时间
			intervalCheckTime : 8000  // 间隔检查时间：8秒
		},
		tradeConfig:{
			version : "3.3",	// 版本
			url_real : "ws://192.168.0.213:6102", // 实盘地址
			model : "1", // 实盘：0；	模拟盘：1
			client_source : "N_WEB",	// 客户端渠道
			username : "000031",		// 账号(新模拟盘——000008、直达实盘——000140、易盛模拟盘——Q517029969)
			password : "YTEyMzQ1Ng==" 	// 密码：base64密文(明文：a123456——YTEyMzQ1Ng==     888888——ODg4ODg4	 74552102——NzQ1NTIxMDI=		123456=MTIzNDU2)
		},
		ifUpdateHoldProfit:false, //是否使用最新行情更新持仓盈亏
		ifUpdateAccountProfit:false,//// 是否可以更新账户盈亏标志：资金信息显示完毕就可以更新盈亏
		qryHoldTotalArr:[],//持仓合计回复数组
		/**
		 * 缓存账户信息
		 */
		CacheAccount:{
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
		
		
		
		
		
		//选择K线时候的值
		selectTime: 1,
		//存进入详情页的No
		currentNo: '',
		//订阅成功后查询品种列表
		orderTemplist:{},
		//存订阅成功后的行情信息
		templateList:{},
		//缓存数组，用于存最新行情的数据
		tempArr: [],
		jsonDatatemp: {},
		currentdetail: {},
		markettemp: [],
		Parameters: [],
		//		时间差
		charttime: 0,
		charttimetime: 0,
		charttimems: 0,
		charttimetime2: 0,
		charttimems2: 0,
		jsonDataKline: {
			"Method": "OnRspQryHistory",
			"Parameters": {
				"ColumNames": ["DateTimeStamp", "LastPrice", "OpenPrice", "LowPrice", "HighPrice", "Position", "Volume"],
				"CommodityNo": "CL",
				"ContractNo": "1708",
				"Count": 102,
				"Data": [
					["2017-06-26 09:31:00", 43.38, 43.35, 43.34, 43.39, 548303, 634],
					["2017-06-26 09:32:00", 43.36, 43.38, 43.35, 43.38, 548303, 319],
					["2017-06-26 09:33:00", 43.36, 43.36, 43.36, 43.37, 548303, 68],
					["2017-06-26 09:34:00", 43.34, 43.36, 43.34, 43.36, 548303, 125],
					["2017-06-26 09:35:00", 43.34, 43.34, 43.34, 43.35, 548303, 75],
					["2017-06-26 09:36:00", 43.35, 43.34, 43.34, 43.36, 548303, 75],
					["2017-06-26 09:37:00", 43.35, 43.35, 43.34, 43.35, 548303, 49],
					["2017-06-26 09:38:00", 43.36, 43.35, 43.35, 43.37, 548303, 90],
					["2017-06-26 09:39:00", 43.37, 43.36, 43.36, 43.37, 548303, 99],
					["2017-06-26 09:40:00", 43.38, 43.37, 43.37, 43.38, 548303, 85],
					["2017-06-26 09:41:00", 43.36, 43.38, 43.36, 43.38, 548303, 148],
					["2017-06-26 09:42:00", 43.43, 43.37, 43.36, 43.44, 548303, 758],
					["2017-06-26 09:43:00", 43.41, 43.42, 43.4, 43.43, 548303, 317],
					["2017-06-26 09:44:00", 43.39, 43.4, 43.39, 43.41, 548303, 137],
					["2017-06-26 09:45:00", 43.39, 43.4, 43.39, 43.4, 548303, 78],
					["2017-06-26 09:46:00", 43.39, 43.39, 43.38, 43.39, 548303, 121],
					["2017-06-26 09:47:00", 43.39, 43.39, 43.38, 43.4, 548303, 63],
					["2017-06-26 09:48:00", 43.4, 43.39, 43.37, 43.4, 548303, 142],
					["2017-06-26 09:49:00", 43.41, 43.4, 43.4, 43.41, 548303, 35],
					["2017-06-26 09:50:00", 43.41, 43.4, 43.4, 43.42, 548303, 147],
					["2017-06-26 09:51:00", 43.42, 43.41, 43.41, 43.44, 548303, 282],
					["2017-06-26 09:52:00", 43.43, 43.42, 43.42, 43.44, 548303, 92],
					["2017-06-26 09:53:00", 43.43, 43.44, 43.42, 43.44, 548303, 159],
					["2017-06-26 09:58:00", 43.46, 43.46, 43.45, 43.46, 548303, 40],
					["2017-06-26 09:59:00", 43.48, 43.46, 43.45, 43.49, 548303, 360],
					["2017-06-26 10:00:00", 43.53, 43.47, 43.47, 43.59, 548303, 2138],
					["2017-06-26 10:01:00", 43.53, 43.53, 43.53, 43.55, 548303, 207],
					["2017-06-26 10:02:00", 43.51, 43.53, 43.5, 43.53, 548303, 270],
					["2017-06-26 10:03:00", 43.5, 43.51, 43.5, 43.52, 548303, 112],
					["2017-06-26 10:04:00", 43.49, 43.49, 43.48, 43.49, 548303, 349],
					["2017-06-26 10:05:00", 43.5, 43.49, 43.48, 43.5, 548303, 125],
					["2017-06-26 10:06:00", 43.48, 43.49, 43.48, 43.5, 548303, 98],
					["2017-06-26 10:07:00", 43.49, 43.49, 43.48, 43.49, 548303, 89],
					["2017-06-26 10:08:00", 43.48, 43.49, 43.48, 43.5, 548303, 55],
					["2017-06-26 10:09:00", 43.48, 43.48, 43.48, 43.49, 548303, 75],
					["2017-06-26 10:10:00", 43.48, 43.48, 43.48, 43.49, 548303, 119],
					["2017-06-26 10:11:00", 43.49, 43.48, 43.48, 43.49, 548303, 82],
					["2017-06-26 10:12:00", 43.47, 43.49, 43.46, 43.49, 548303, 359],
					["2017-06-26 10:13:00", 43.48, 43.47, 43.45, 43.49, 548303, 542],
					["2017-06-26 10:14:00", 43.51, 43.48, 43.47, 43.51, 548303, 219],
					["2017-06-26 10:15:00", 43.47, 43.51, 43.47, 43.51, 548303, 104],
					["2017-06-26 10:16:00", 43.48, 43.47, 43.46, 43.48, 548303, 262],
					["2017-06-26 10:17:00", 43.44, 43.48, 43.44, 43.48, 548303, 266],
					["2017-06-26 10:18:00", 43.45, 43.44, 43.44, 43.46, 548303, 140],
					["2017-06-26 10:19:00", 43.49, 43.45, 43.44, 43.49, 548303, 239],
					["2017-06-26 10:20:00", 43.48, 43.48, 43.48, 43.5, 548303, 135],
					["2017-06-26 10:21:00", 43.48, 43.48, 43.47, 43.49, 548303, 142],
					["2017-06-26 10:22:00", 43.51, 43.48, 43.48, 43.51, 548303, 146],
					["2017-06-26 10:23:00", 43.51, 43.5, 43.5, 43.51, 548303, 23],
					["2017-06-26 10:24:00", 43.51, 43.5, 43.5, 43.51, 548303, 122],
					["2017-06-26 10:25:00", 43.5, 43.51, 43.5, 43.51, 548303, 80],
					["2017-06-26 10:26:00", 43.5, 43.5, 43.5, 43.51, 548303, 41],
					["2017-06-26 10:27:00", 43.49, 43.5, 43.48, 43.5, 548303, 64],
					["2017-06-26 10:28:00", 43.47, 43.48, 43.47, 43.49, 548303, 46],
					["2017-06-26 10:29:00", 43.47, 43.47, 43.46, 43.48, 548303, 104],
					["2017-06-26 10:30:00", 43.48, 43.46, 43.46, 43.49, 548303, 226],
					["2017-06-26 10:31:00", 43.49, 43.47, 43.46, 43.49, 548303, 72],
					["2017-06-26 10:32:00", 43.44, 43.48, 43.44, 43.48, 548303, 316],
					["2017-06-26 10:33:00", 43.44, 43.44, 43.43, 43.45, 548303, 161],
					["2017-06-26 10:34:00", 43.47, 43.45, 43.45, 43.47, 548303, 95],
					["2017-06-26 10:35:00", 43.48, 43.47, 43.46, 43.5, 548303, 265],
					["2017-06-26 10:36:00", 43.47, 43.48, 43.46, 43.48, 548303, 118],
					["2017-06-26 10:37:00", 43.46, 43.47, 43.46, 43.47, 548303, 32],
					["2017-06-26 10:38:00", 43.46, 43.47, 43.45, 43.47, 548303, 53],
					["2017-06-26 10:39:00", 43.46, 43.45, 43.45, 43.46, 548303, 32],
					["2017-06-26 10:40:00", 43.47, 43.46, 43.46, 43.47, 548303, 17],
					["2017-06-26 10:41:00", 43.47, 43.47, 43.46, 43.47, 548303, 25],
					["2017-06-26 10:42:00", 43.49, 43.46, 43.46, 43.49, 548303, 91],
					["2017-06-26 10:43:00", 43.48, 43.49, 43.48, 43.49, 548303, 48],
					["2017-06-26 10:44:00", 43.47, 43.48, 43.47, 43.48, 548303, 44],
					["2017-06-26 10:45:00", 43.47, 43.47, 43.46, 43.47, 548303, 35],
					["2017-06-26 10:46:00", 43.47, 43.47, 43.46, 43.47, 548303, 19],
					["2017-06-26 10:47:00", 43.47, 43.47, 43.47, 43.48, 548303, 28],
					["2017-06-26 10:48:00", 43.47, 43.47, 43.47, 43.48, 548303, 40],
					["2017-06-26 10:49:00", 43.47, 43.46, 43.46, 43.47, 548303, 10],
					["2017-06-26 10:50:00", 43.45, 43.47, 43.44, 43.47, 548303, 155],
					["2017-06-26 10:51:00", 43.45, 43.45, 43.44, 43.45, 548303, 116],
					["2017-06-26 10:52:00", 43.45, 43.45, 43.44, 43.46, 548303, 37],
					["2017-06-26 10:53:00", 43.45, 43.45, 43.45, 43.46, 548303, 14],
					["2017-06-26 10:54:00", 43.44, 43.46, 43.44, 43.46, 548303, 114],
					["2017-06-26 10:55:00", 43.44, 43.44, 43.43, 43.45, 548303, 147],
					["2017-06-26 10:56:00", 43.44, 43.43, 43.43, 43.44, 548303, 46],
					["2017-06-26 10:57:00", 43.45, 43.44, 43.43, 43.46, 548303, 167],
					["2017-06-26 10:58:00", 43.45, 43.45, 43.44, 43.46, 548303, 43],
					["2017-06-26 10:59:00", 43.45, 43.45, 43.45, 43.46, 548303, 7],
					["2017-06-26 11:00:00", 43.45, 43.45, 43.45, 43.46, 548303, 16],
					["2017-06-26 11:01:00", 43.45, 43.45, 43.45, 43.45, 548303, 30],
					["2017-06-26 11:02:00", 43.44, 43.45, 43.44, 43.45, 548303, 18],
					["2017-06-26 11:03:00", 43.47, 43.45, 43.44, 43.48, 548303, 102],
					["2017-06-26 11:04:00", 43.47, 43.47, 43.46, 43.47, 548303, 98],
					["2017-06-26 11:05:00", 43.46, 43.47, 43.46, 43.47, 548303, 30],
					["2017-06-26 11:06:00", 43.46, 43.46, 43.45, 43.47, 548303, 47],
					["2017-06-26 11:07:00", 43.46, 43.46, 43.46, 43.47, 548303, 29],
					["2017-06-26 11:08:00", 43.45, 43.46, 43.45, 43.46, 548303, 15],
					["2017-06-26 11:09:00", 43.44, 43.45, 43.44, 43.46, 548303, 46],
					["2017-06-26 11:10:00", 43.45, 43.45, 43.44, 43.46, 548303, 34],
					["2017-06-26 11:11:00", 43.47, 43.46, 43.45, 43.47, 548303, 116],
					["2017-06-26 11:12:00", 43.47, 43.47, 43.46, 43.47, 548303, 53],
					["2017-06-26 11:13:00", 43.47, 43.47, 43.47, 43.48, 548303, 20],
					["2017-06-26 11:14:00", 43.47, 43.47, 43.47, 43.47, 548303, 25],
					["2017-06-26 11:15:00", 43.46, 43.46, 43.46, 43.47, 548303, 31],
					["2017-06-26 11:16:00", 43.46, 43.46, 43.46, 43.46, 548303, 2]
				],
				"ExchangeNo": "NYMEX",
				"HisQuoteType": 0
			}
		},

		//用于存放从后台抓取的历史合约数据
		jsonData: {
			"Method": "OnRspQryHistory",
			"Parameters": {
				"ColumNames": ["DateTimeStamp", "LastPrice", "OpenPrice", "LowPrice", "HighPrice", "Position", "Volume"],
				"CommodityNo": "CL",
				"ContractNo": "1708",
				"Count": 102,
				"Data": [
					["2017-06-26 09:31:00", 43.38, 43.35, 43.34, 43.39, 548303, 634],
					["2017-06-26 09:32:00", 43.36, 43.38, 43.35, 43.38, 548303, 319],
					["2017-06-26 09:33:00", 43.36, 43.36, 43.36, 43.37, 548303, 68],
					["2017-06-26 09:34:00", 43.34, 43.36, 43.34, 43.36, 548303, 125],
					["2017-06-26 09:35:00", 43.34, 43.34, 43.34, 43.35, 548303, 75],
					["2017-06-26 09:36:00", 43.35, 43.34, 43.34, 43.36, 548303, 75],
					["2017-06-26 09:37:00", 43.35, 43.35, 43.34, 43.35, 548303, 49],
					["2017-06-26 09:38:00", 43.36, 43.35, 43.35, 43.37, 548303, 90],
					["2017-06-26 09:39:00", 43.37, 43.36, 43.36, 43.37, 548303, 99],
					["2017-06-26 09:40:00", 43.38, 43.37, 43.37, 43.38, 548303, 85],
					["2017-06-26 09:41:00", 43.36, 43.38, 43.36, 43.38, 548303, 148],
					["2017-06-26 09:42:00", 43.43, 43.37, 43.36, 43.44, 548303, 758],
					["2017-06-26 09:43:00", 43.41, 43.42, 43.4, 43.43, 548303, 317],
					["2017-06-26 09:44:00", 43.39, 43.4, 43.39, 43.41, 548303, 137],
					["2017-06-26 09:45:00", 43.39, 43.4, 43.39, 43.4, 548303, 78],
					["2017-06-26 09:46:00", 43.39, 43.39, 43.38, 43.39, 548303, 121],
					["2017-06-26 09:47:00", 43.39, 43.39, 43.38, 43.4, 548303, 63],
					["2017-06-26 09:48:00", 43.4, 43.39, 43.37, 43.4, 548303, 142],
					["2017-06-26 09:49:00", 43.41, 43.4, 43.4, 43.41, 548303, 35],
					["2017-06-26 09:50:00", 43.41, 43.4, 43.4, 43.42, 548303, 147],
					["2017-06-26 09:51:00", 43.42, 43.41, 43.41, 43.44, 548303, 282],
					["2017-06-26 09:52:00", 43.43, 43.42, 43.42, 43.44, 548303, 92],
					["2017-06-26 09:53:00", 43.43, 43.44, 43.42, 43.44, 548303, 159],
					["2017-06-26 09:58:00", 43.46, 43.46, 43.45, 43.46, 548303, 40],
					["2017-06-26 09:59:00", 43.48, 43.46, 43.45, 43.49, 548303, 360],
					["2017-06-26 10:00:00", 43.53, 43.47, 43.47, 43.59, 548303, 2138],
					["2017-06-26 10:01:00", 43.53, 43.53, 43.53, 43.55, 548303, 207],
					["2017-06-26 10:02:00", 43.51, 43.53, 43.5, 43.53, 548303, 270],
					["2017-06-26 10:03:00", 43.5, 43.51, 43.5, 43.52, 548303, 112],
					["2017-06-26 10:04:00", 43.49, 43.49, 43.48, 43.49, 548303, 349],
					["2017-06-26 10:05:00", 43.5, 43.49, 43.48, 43.5, 548303, 125],
					["2017-06-26 10:06:00", 43.48, 43.49, 43.48, 43.5, 548303, 98],
					["2017-06-26 10:07:00", 43.49, 43.49, 43.48, 43.49, 548303, 89],
					["2017-06-26 10:08:00", 43.48, 43.49, 43.48, 43.5, 548303, 55],
					["2017-06-26 10:09:00", 43.48, 43.48, 43.48, 43.49, 548303, 75],
					["2017-06-26 10:10:00", 43.48, 43.48, 43.48, 43.49, 548303, 119],
					["2017-06-26 10:11:00", 43.49, 43.48, 43.48, 43.49, 548303, 82],
					["2017-06-26 10:12:00", 43.47, 43.49, 43.46, 43.49, 548303, 359],
					["2017-06-26 10:13:00", 43.48, 43.47, 43.45, 43.49, 548303, 542],
					["2017-06-26 10:14:00", 43.51, 43.48, 43.47, 43.51, 548303, 219],
					["2017-06-26 10:15:00", 43.47, 43.51, 43.47, 43.51, 548303, 104],
					["2017-06-26 10:16:00", 43.48, 43.47, 43.46, 43.48, 548303, 262],
					["2017-06-26 10:17:00", 43.44, 43.48, 43.44, 43.48, 548303, 266],
					["2017-06-26 10:18:00", 43.45, 43.44, 43.44, 43.46, 548303, 140],
					["2017-06-26 10:19:00", 43.49, 43.45, 43.44, 43.49, 548303, 239],
					["2017-06-26 10:20:00", 43.48, 43.48, 43.48, 43.5, 548303, 135],
					["2017-06-26 10:21:00", 43.48, 43.48, 43.47, 43.49, 548303, 142],
					["2017-06-26 10:22:00", 43.51, 43.48, 43.48, 43.51, 548303, 146],
					["2017-06-26 10:23:00", 43.51, 43.5, 43.5, 43.51, 548303, 23],
					["2017-06-26 10:24:00", 43.51, 43.5, 43.5, 43.51, 548303, 122],
					["2017-06-26 10:25:00", 43.5, 43.51, 43.5, 43.51, 548303, 80],
					["2017-06-26 10:26:00", 43.5, 43.5, 43.5, 43.51, 548303, 41],
					["2017-06-26 10:27:00", 43.49, 43.5, 43.48, 43.5, 548303, 64],
					["2017-06-26 10:28:00", 43.47, 43.48, 43.47, 43.49, 548303, 46],
					["2017-06-26 10:29:00", 43.47, 43.47, 43.46, 43.48, 548303, 104],
					["2017-06-26 10:30:00", 43.48, 43.46, 43.46, 43.49, 548303, 226],
					["2017-06-26 10:31:00", 43.49, 43.47, 43.46, 43.49, 548303, 72],
					["2017-06-26 10:32:00", 43.44, 43.48, 43.44, 43.48, 548303, 316],
					["2017-06-26 10:33:00", 43.44, 43.44, 43.43, 43.45, 548303, 161],
					["2017-06-26 10:34:00", 43.47, 43.45, 43.45, 43.47, 548303, 95],
					["2017-06-26 10:35:00", 43.48, 43.47, 43.46, 43.5, 548303, 265],
					["2017-06-26 10:36:00", 43.47, 43.48, 43.46, 43.48, 548303, 118],
					["2017-06-26 10:37:00", 43.46, 43.47, 43.46, 43.47, 548303, 32],
					["2017-06-26 10:38:00", 43.46, 43.47, 43.45, 43.47, 548303, 53],
					["2017-06-26 10:39:00", 43.46, 43.45, 43.45, 43.46, 548303, 32],
					["2017-06-26 10:40:00", 43.47, 43.46, 43.46, 43.47, 548303, 17],
					["2017-06-26 10:41:00", 43.47, 43.47, 43.46, 43.47, 548303, 25],
					["2017-06-26 10:42:00", 43.49, 43.46, 43.46, 43.49, 548303, 91],
					["2017-06-26 10:43:00", 43.48, 43.49, 43.48, 43.49, 548303, 48],
					["2017-06-26 10:44:00", 43.47, 43.48, 43.47, 43.48, 548303, 44],
					["2017-06-26 10:45:00", 43.47, 43.47, 43.46, 43.47, 548303, 35],
					["2017-06-26 10:46:00", 43.47, 43.47, 43.46, 43.47, 548303, 19],
					["2017-06-26 10:47:00", 43.47, 43.47, 43.47, 43.48, 548303, 28],
					["2017-06-26 10:48:00", 43.47, 43.47, 43.47, 43.48, 548303, 40],
					["2017-06-26 10:49:00", 43.47, 43.46, 43.46, 43.47, 548303, 10],
					["2017-06-26 10:50:00", 43.45, 43.47, 43.44, 43.47, 548303, 155],
					["2017-06-26 10:51:00", 43.45, 43.45, 43.44, 43.45, 548303, 116],
					["2017-06-26 10:52:00", 43.45, 43.45, 43.44, 43.46, 548303, 37],
					["2017-06-26 10:53:00", 43.45, 43.45, 43.45, 43.46, 548303, 14],
					["2017-06-26 10:54:00", 43.44, 43.46, 43.44, 43.46, 548303, 114],
					["2017-06-26 10:55:00", 43.44, 43.44, 43.43, 43.45, 548303, 147],
					["2017-06-26 10:56:00", 43.44, 43.43, 43.43, 43.44, 548303, 46],
					["2017-06-26 10:57:00", 43.45, 43.44, 43.43, 43.46, 548303, 167],
					["2017-06-26 10:58:00", 43.45, 43.45, 43.44, 43.46, 548303, 43],
					["2017-06-26 10:59:00", 43.45, 43.45, 43.45, 43.46, 548303, 7],
					["2017-06-26 11:00:00", 43.45, 43.45, 43.45, 43.46, 548303, 16],
					["2017-06-26 11:01:00", 43.45, 43.45, 43.45, 43.45, 548303, 30],
					["2017-06-26 11:02:00", 43.44, 43.45, 43.44, 43.45, 548303, 18],
					["2017-06-26 11:03:00", 43.47, 43.45, 43.44, 43.48, 548303, 102],
					["2017-06-26 11:04:00", 43.47, 43.47, 43.46, 43.47, 548303, 98],
					["2017-06-26 11:05:00", 43.46, 43.47, 43.46, 43.47, 548303, 30],
					["2017-06-26 11:06:00", 43.46, 43.46, 43.45, 43.47, 548303, 47],
					["2017-06-26 11:07:00", 43.46, 43.46, 43.46, 43.47, 548303, 29],
					["2017-06-26 11:08:00", 43.45, 43.46, 43.45, 43.46, 548303, 15],
					["2017-06-26 11:09:00", 43.44, 43.45, 43.44, 43.46, 548303, 46],
					["2017-06-26 11:10:00", 43.45, 43.45, 43.44, 43.46, 548303, 34],
					["2017-06-26 11:11:00", 43.47, 43.46, 43.45, 43.47, 548303, 116],
					["2017-06-26 11:12:00", 43.47, 43.47, 43.46, 43.47, 548303, 53],
					["2017-06-26 11:13:00", 43.47, 43.47, 43.47, 43.48, 548303, 20],
					["2017-06-26 11:14:00", 43.47, 43.47, 43.47, 43.47, 548303, 25],
					["2017-06-26 11:15:00", 43.46, 43.46, 43.46, 43.47, 548303, 31],
					["2017-06-26 11:16:00", 43.46, 43.46, 43.46, 43.46, 548303, 2]
				],
				"ExchangeNo": "NYMEX",
				"HisQuoteType": 0
			}
		},
		//用于存放从后台抓取的合约数据
		jsonTow: {
			"Method": "OnRtnQuote",
			"Parameters": {
				"AskPrice1": 44.92,
				"AskPrice2": 44.93,
				"AskPrice3": 44.94,
				"AskPrice4": 44.95,
				"AskPrice5": 44.96,
				"AskQty1": 15,
				"AskQty2": 37,
				"AskQty3": 35,
				"AskQty4": 33,
				"AskQty5": 61,
				"AveragePrice": 0,
				"BidPrice1": 44.91,
				"BidPrice2": 44.9,
				"BidPrice3": 44.89,
				"BidPrice4": 44.88,
				"BidPrice5": 44.87,
				"BidQty1": 28,
				"BidQty2": 47,
				"BidQty3": 38,
				"BidQty4": 90,
				"BidQty5": 33,
				"ChangeRate": 0.402324541797049,
				"ChangeValue": 0.1799999999999997,
				"ClosingPrice": 0,
				"CommodityNo": "CL",
				"ContractNo": "1708",
				"DateTimeStamp": "2017-06-29 11:40:36",
				"ExchangeNo": "NYMEX",
				"HighPrice": 45.03,
				"LastPrice": 44.92,
				"LastVolume": 1,
				"LimitDownPrice": 0,
				"LimitUpPrice": 0,
				"LowPrice": 44.75,
				"OpenPrice": 44.89,
				"Position": 541143,
				"PreClosingPrice": 0,
				"PrePosition": 0,
				"PreSettlePrice": 44.74,
				"SettlePrice": 0,
				"TotalAskQty": 0,
				"TotalBidQty": 0,
				"TotalTurnover": 0,
				"TotalVolume": 26287
			}
		},
		//绘制分时的设置
		option1: {

		},
		//绘制分时的设置
		option2: {

		},
		//绘制K线的设置
		option3: {

		},
		//绘制K线的设置
		option4: {

		},
		//绘制闪电图的设置
		option5: {

		},
		//K线用到的数据
		rawData: [],
		//K线用到的数据
		chartDataC: null,
		lightChartTime: {
			"time": [],
			"price": []
		}
	}
}

export default new Vuex.Store({
	modules: {
		isshow,
		market,
		account
	},
	state: {

		//打包的时候，值为 build ，开发的时候，值为 dev
		setting: 'dev',
		//请求的操盘参数数据
		tempTradeapply: {},
		quoteSocket: {},
		tradeSocket:{},
		webuser: {
			username: '13677622344',
			password: 'a123456'
		},
		wsjsondata: {},
		//连接提示语
		wsmsg: '',
		QuoteMethod: {
			/**
			 * 登录Method
			 */
			LoginMethod: "Login",
			/**
			 * 登出Method
			 */
			LogoutMethod: "Logout",
			/**
			 * 查询品种Method
			 */
			QryCommodityMethod: "QryCommodity",
			/**
			 * 查询合约Method
			 */
			QryContractMethod: "QryContract",
			/**
			 * 订阅Method
			 */
			SubscribeMethod: "Subscribe",
			/**
			 * 取消订阅Method
			 */
			UnSubscribeMethod: "UnSubscribe",
			/**
			 * 查询历史数据Method
			 */
			QryHistoryMethod: "QryHistory",
			/**
			 * 查询深度行情组
			 */
			QryDepthQuoteGroupMethod: "QryDepthQuoteGroup"
		}
	},
	getters: {
		PATH: function(state) {
			if(state.setting == 'dev') {
				return '/api'
			} else if(state.setting == 'build') {
				return 'http://test.api.dktai.cn'
			} else if(state.setting == 'nat') {
				return '/nat/vs-api'
			}
		}
	},
	mutations: {
	
		drawlight: function(state, e) {
			// 引入 ECharts 主模块
			var echarts = require('echarts/lib/echarts');
			// 引入柱状图
			require('echarts/lib/chart/bar');
			// 基于准备好的dom，初始化echarts图表
			if(state.isshow.islightshow == false) {
				var lightChart = echarts.init(document.getElementById(e));
				state.isshow.islightshow = true;
			} else {
				var lightChart = echarts.getInstanceByDom(document.getElementById(e));
			}
			lightChart.setOption(state.market.option5);
		},
		setlightDate: function(state) {
			var TimeLength = state.market.lightChartTime.time.length;
			state.market.lightChartTime.price.push(state.market.jsonTow.Parameters.LastPrice);
			state.market.lightChartTime.time.push((state.market.jsonTow.Parameters.DateTimeStamp).split(" ")[1]);
			state.market.lightChartTime.time = state.market.lightChartTime.time.slice(-50);
			state.market.lightChartTime.price = state.market.lightChartTime.price.slice(-50);
			state.market.option5 = {
				backgroundColor: "#1f1f1f",
				"tooltip": {
					"show": false,
				},
				animation: false,
				grid: {
					x: 50,
					y: 40,
					x2: 46,
					y2: 20
				},
				xAxis: [{
					type: 'category',
					show: true,
					data: state.market.lightChartTime.time,
					axisLine: {
						lineStyle: {
							color: '#8392A5'
						}
					},
					boundaryGap: true
				}],
				yAxis: [{
					type: 'value',
					scale: true,
					position: "left",
					axisTick: {
						show: false,
					},
					axisLine: {
						lineStyle: {
							color: '#8392A5'
						}
					},
					splitArea: {
						show: false
					},
					axisLabel: {
						inside: false,
						margin: 4,
					},
					splitLine: {
						show: true,
						lineStyle: {
							color: "#8392A5"
						}
					}
				}],
				"series": [{
					"name": "总数",
					"type": "line",
					"stack": "总量",
					symbolSize: 10,
					symbol: 'circle',
					"itemStyle": {
						"normal": {
							"color": "#8392A5",
							"barBorderRadius": 0,
							"label": {
								"show": true,
								"position": "top",
								formatter: function(p) {
									return p.value > 0 ? (p.value) : '';
								}
							}
						}
					},
					"data": state.market.lightChartTime.price
				}]
			}
		},

		setklineoption: function(state) {
			//			console.time('e');
			// 引入 ECharts 主模块
			var echarts = require('echarts/lib/echarts');
			// 引入柱状图
			require('echarts/lib/chart/bar');
			require('echarts/lib/chart/line');
			require('echarts/lib/component/tooltip');
			require('echarts/lib/chart/candlestick');

			var dosizeL = 2;
			var rawData = [];
			var parameters = state.market.jsonDataKline.Parameters.Data;
			var Len = parameters.length;
			var lent = rawData.length;
			if(state.market.jsonDataKline.Parameters.HisQuoteType == 1440) {
				for(var i = 0; i < Len; i++) {
					var timeStr = parameters[i][0].split(" ")[0];
					var openPrice = (parameters[i][2]).toFixed(dosizeL);
					var closePrice = (parameters[i][1]).toFixed(dosizeL);
					var sgData = [timeStr, openPrice, closePrice, (parameters[i][3]).toFixed(dosizeL), (parameters[i][4]).toFixed(dosizeL), parameters[i][2]];
					rawData[lent + i] = sgData;
				};
			} else {
				for(var i = 0; i < Len; i++) {
					var time2 = parameters[i][0].split(" ");
					var str1 = time2[1].split(":");
					var str2 = str1[0] + ":" + str1[1]
					var openPrice = (parameters[i][2]).toFixed(dosizeL);
					var closePrice = (parameters[i][1]).toFixed(dosizeL);
					var sgData = [str2, openPrice, closePrice, (parameters[i][3]).toFixed(dosizeL), (parameters[i][4]).toFixed(dosizeL), parameters[i][0]];
					rawData[lent + i] = sgData;
				};

			}
			var categoryData = [];
			var values = [];
			var time = [];
			for(var i = 0; i < rawData.slice(-40).length; i++) {
				categoryData.push(rawData.slice(-40)[i][0]);
				values.push([rawData.slice(-40)[i][1], rawData.slice(-40)[i][2], rawData.slice(-40)[i][3], rawData.slice(-40)[i][4]]);
				time.push(rawData.slice(-40)[i][5])
			}

			var chartDataC = {
				categoryData: categoryData,
				values: values,
				time: time
			};

			/*MA5 10 20 30*/
			function calculateMA(dayCount) {
				var result = [];
				for(var i = 0, len = chartDataC.values.length; i < len; i++) {
					if(i < dayCount) {
						result.push('-');
						continue;
					}
					var sum = 0;
					for(var j = 0; j < dayCount; j++) {
						sum += Number(chartDataC.values[i - j][1]);
					}
					result.push(Number(sum / dayCount).toFixed(2));
				}
				return result;
			}

			state.market.option3 = {
				backgroundColor: 'transparent',
				tooltip: {
					trigger: 'axis',
					axisPointer: {
						type: 'line',
						animation: false,
						lineStyle: {
							color: '#ffffff',
							width: 1,
							opacity: 1
						}
					},
					formatter: function(params) {
						var time = params[0].name;
						if(time == null || time == "") {
							return
						}
						var kd = params[0].data;
						var ma5 = params[1].data;
						var ma10 = params[2].data;
						var ma20 = params[3].data;
						var ma30 = params[4].data;
						var rate = (kd[1] - kd[0]) / kd[0] * 100;
						rate = rate > 0 ? ('+' + rate.toFixed(2)) : rate.toFixed(2);
						var res = "时间:" + params[0].name + '  涨跌 : ' + rate;
						res += '<br/>  开盘 : ' + kd[0] + '  最高 : ' + kd[3];
						res += '<br/>  收盘 : ' + kd[1] + ' 最低 : ' + kd[2];
						res += '<br/> <span style="display:inline-block;margin-right:5px;border-radius:10px;width:9px;height:9px;background-color:#3689B3"></span> MA5 : ' + ma5 + '  <span style="display:inline-block;margin-right:5px;border-radius:10px;width:9px;height:9px;background-color:#B236B3"></span> MA10 : ' + ma10;
						res += '<br/><span style="display:inline-block;margin-right:5px;border-radius:10px;width:9px;height:9px;background-color:#B37436"></span> MA20 : ' + ma20 + '  <span style="display:inline-block;margin-right:5px;border-radius:10px;width:9px;height:9px;background-color:#B2B336"></span> MA30 : ' + ma30;
						return res;
					}
				},
				grid: {
					x: 43,
					y: 20,
					x2: 30,
					y2: 5
				},
				xAxis: {
					type: 'category',
					data: chartDataC.categoryData,
					show: false,
					axisLine: {
						lineStyle: {
							color: '#8392A5'
						}
					}
				},
				yAxis: {
					scale: true,
					axisLine: {
						lineStyle: {
							color: '#8392A5'
						}
					},
					splitLine: {
						show: false
					},
					axisTick: {
						show: false,
					},
					splitArea: {
						show: false
					},
					axisLabel: {
						inside: false,
						margin: 4
					},
					splitLine: {
						show: true,
						lineStyle: {
							color: "#8392A5"
						}
					}
				},
				animation: false,
				series: [{
						type: 'candlestick',
						name: '',
						data: chartDataC.values,
						markLine: {
							symbol: ['none', 'none'],
							clickable: false,
							lineStyle: {
								normal: {
									width: 1,
									color: "#ffffff"
								}
							},
							data: [{
									name: '标线2起点',
									value: 0,
									xAxis: "1",
									yAxis: 0
								}, // 当xAxis或yAxis为数值轴时，不管传入是什么，都被理解为数值后做空间位置换算
								{
									name: '标线2终点',
									xAxis: "2",
									yAxis: 0
								}
							]
						},
						itemStyle: {
							normal: {
								color: '#FD1050',
								color0: '#0CF49B',
								borderColor: '#FD1050',
								borderColor0: '#0CF49B'
							}
						}
					},
					{
						name: 'MA5',
						type: 'line',
						data: calculateMA(5),
						smooth: true,
						showSymbol: false,
						lineStyle: {
							normal: {
								color: '#3689B3',
								width: 1,
								//	                    	opacity: 0.5
							}
						}
					},
					{
						name: 'MA10',
						type: 'line',
						showSymbol: false,
						data: calculateMA(10),
						smooth: true,
						lineStyle: {
							normal: {
								color: '#B236B3',
								width: 1,
								//	                    	opacity: 0.5
							}
						}
					},
					{
						name: 'MA20',
						type: 'line',
						showSymbol: false,
						data: calculateMA(20),
						smooth: true,
						lineStyle: {
							normal: {
								color: '#B37436',
								width: 1,
								//	                    	opacity: 0.5
							}
						}
					},
					{
						name: 'MA30',
						type: 'line',
						showSymbol: false,
						data: calculateMA(30),
						smooth: true,
						lineStyle: {
							normal: {
								color: '#B2B336',
								width: 1,
								//	                    	opacity: 0.5
							}
						}
					}

				]
			}
			//			console.timeEnd('e');

			var vol = [],
				price = [],
				time = [];
			state.market.jsonDataKline.Parameters.Data.slice(-40).forEach(function(e) {
				vol.push(e[6]);
				time.push(e[0].split(' ')[1].split(':')[0] + ':' + e[0].split(' ')[1].split(':')[1]);
				price.push(e[1]);
			});

			//成交量设置
			state.market.option4 = {
				grid: {
					x: 43,
					y: 30,
					x2: 30,
					y2: 20
				},
				color: ['#edf07c'],
				tooltip: {},
				xAxis: [{
					type: 'category',
					position: 'bottom',
					boundaryGap: true,
					axisTick: {
						onGap: false
					},
					splitLine: {
						show: false
					},
					axisLabel: {
						textStyle: {
							fontSize: 10,
						}
					},
					axisLine: {
						lineStyle: {
							color: '#8392A5'
						}
					},
					data: time
				}],
				yAxis: [{
					type: 'value',
					name: '成交量(万)',
					axisLine: {
						lineStyle: {
							color: '#8392A5'
						}
					},
					axisTick: {
						show: false,
					},
					scale: true,
					axisLabel: {
						formatter: function(a) {
							a = +a;
							return isFinite(a) ?
								echarts.format.addCommas(+a / 10000) :
								'';
						},
						textStyle: {
							fontSize: 10
						}
					},
					splitLine: {
						show: true,
						lineStyle: {
							color: "#8392A5"
						}
					}
				}],
				tooltip: {
					trigger: 'axis',
					axisPointer: {
						type: 'line',
						animation: false,
						lineStyle: {
							color: '#ffffff',
							width: 1,
							opacity: 1
						}
					},
					triggerOn: 'mousemove|click'
				},
				series: [{
					name: '成交量',
					type: 'bar',
					data: vol
				}]
			};
		},
		drawkline: function(state, x) {
			// 引入 ECharts 主模块
			var echarts = require('echarts/lib/echarts');
			// 引入柱状图
			require('echarts/lib/chart/bar');
			require('echarts/lib/chart/line');
			require('echarts/lib/component/tooltip');
			require('echarts/lib/chart/candlestick');
			if(state.isshow.isklineshow == false) {
				var kline = echarts.init(document.getElementById(x.id1));
				var volume = echarts.init(document.getElementById(x.id2));
				volume.group = 'group1';
				kline.group = 'group1';
				// 基于准备好的dom，初始化echarts实例
				echarts.connect("group1");
				state.isshow.isklineshow = true;
			} else {
				var kline = echarts.getInstanceByDom(document.getElementById(x.id1));
				var volume = echarts.getInstanceByDom(document.getElementById(x.id2));
			}
			kline.setOption(state.market.option3);
			volume.setOption(state.market.option4);
		},
		drawfens: function(state, x) {
			// 引入 ECharts 主模块
			var echarts = require('echarts/lib/echarts');
			// 引入柱状图
			require('echarts/lib/chart/bar');
			require('echarts/lib/chart/line');
			require('echarts/lib/component/tooltip');
			if(state.isshow.isfensshow == false) {
				var volume = echarts.init(document.getElementById(x.id1));
				volume.group = 'group1';
				// 基于准备好的dom，初始化echarts实例
				var fens = echarts.init(document.getElementById(x.id2));
				fens.group = 'group1';
				echarts.connect("group1");
				state.isshow.isfensshow = true;
			} else {
				var volume = echarts.getInstanceByDom(document.getElementById(x.id1));
				var fens = echarts.getInstanceByDom(document.getElementById(x.id2));
			}

			fens.setOption(state.market.option1);
			volume.setOption(state.market.option2);
		},
		//		drawfenssecond: function(state, x) {
		//			fens.setOption(state.market.option1);
		//			volume.setOption(state.market.option2);
		//		},
		setfensoptionsecond: function(state) {
			var echarts = require('echarts/lib/echarts');
			var vol = [],
				price = [],
				time = [];

			state.market.jsonData.Parameters.Data.forEach(function(e) {
				vol.push(e[6]);
				time.push(e[0].split(' ')[1].split(':')[0] + ':' + e[0].split(' ')[1].split(':')[1]);
				price.push(e[1]);
			})
			state.market.option1 = {
				grid: {
					x: 50,
					y: 30,
					x2: 30,
					y2: 20
				},
				color: ['#edf07c'],
				tooltip: {},
				xAxis: [{
					type: 'category',
					position: 'bottom',
					boundaryGap: true,
					axisTick: {
						onGap: false
					},
					splitLine: {
						show: false
					},
					axisLabel: {
						textStyle: {
							fontSize: 10,
						}
					},
					axisLine: {
						lineStyle: {
							color: '#8392A5'
						}
					},
					data: time
				}],
				yAxis: [{
					type: 'value',
					name: '成交量(万)',
					axisLine: {
						lineStyle: {
							color: '#8392A5'
						}
					},
					axisTick: {
						show: false,
					},
					scale: true,
					axisLabel: {
						formatter: function(a) {
							a = +a;
							return isFinite(a) ?
								echarts.format.addCommas(+a / 10000) :
								'';
						},
						textStyle: {
							fontSize: 10
						}
					},
					splitLine: {
						show: true,
						lineStyle: {
							color: "#8392A5"
						}
					}
				}],
				tooltip: {
					trigger: 'axis',
					axisPointer: {
						type: 'line',
						animation: false,
						lineStyle: {
							color: '#ffffff',
							width: 1,
							opacity: 1
						}
					},
					triggerOn: 'mousemove|click'
				},
				series: [{
					name: '成交量',
					type: 'bar',
					data: vol
				}]
			};
			state.market.option2 = {
				backgroundColor: 'transparent',
				tooltip: {
					show: true,
					transitionDuration: 0,
					trigger: 'axis',
					axisPointer: {
						type: 'line',
						animation: false,
						lineStyle: {
							color: '#ffffff',
							width: 1,
							opacity: 1
						}
					},
					formatter: function(params) {
						var time = params[0].name;
						var val = params[0].value;
						if(time == null || time == "") {
							return
						}
						var html = '时间:' + time + '<br/>' +
							'价格: ' + val + '<br/>';
						return html;
					},
				},
				toolbox: {
					show: false,
				},
				animation: false,
				xAxis: [{
					type: 'category',
					show: false,
					data: time,
					axisLine: {
						lineStyle: {
							color: '#8392A5'
						}
					},
					boundaryGap: true
				}],
				yAxis: [{
					type: 'value',
					scale: true,
					position: "left",
					axisTick: {
						show: false,
					},
					axisLine: {
						lineStyle: {
							color: '#8392A5'
						}
					},
					splitArea: {
						show: false
					},
					axisLabel: {
						inside: false,
						margin: 4,
					},
					splitLine: {
						show: true,
						lineStyle: {
							color: "#8392A5"
						}
					}
				}],
				grid: {
					x: 50,
					y: 20,
					x2: 30,
					y2: 5
				},
				series: {
					type: 'line',
					label: {
						normal: {
							show: false,
							position: 'inside'
						},
					},
					lineStyle: {
						normal: {
							width: 1,
							color: "#ffffff"
						}
					},
					itemStyle: {
						normal: {
							color: "#ffffff"
						}
					},
					symbolSize: 2,
					markLine: {
						symbol: ['none', 'none'],
						clickable: false,
						lineStyle: {
							normal: {
								width: 1,
								color: "#ffffff"
							}
						},
						data: [{
								name: '标线2起点',
								value: 0,
								xAxis: "1",
								yAxis: 0
							}, // 当xAxis或yAxis为数值轴时，不管传入是什么，都被理解为数值后做空间位置换算
							{
								name: '标线2终点',
								xAxis: "2",
								yAxis: 0
							}
						]
					},
					data: price
				}
			};
		},

		setfensoption: function(state) {
			var echarts = require('echarts/lib/echarts');
			var vol = [],
				price = [],
				time = [];
			state.market.jsonData.Parameters.Data.forEach(function(e) {
				vol.push(e[6]);
				time.push(e[0].split(' ')[1].split(':')[0] + ':' + e[0].split(' ')[1].split(':')[1]);
				price.push(e[1]);
			})
			state.market.option1 = {
				grid: {
					x: 50,
					y: 30,
					x2: 30,
					y2: 20
				},
				color: ['#edf07c'],
				tooltip: {},
				xAxis: [{
					type: 'category',
					position: 'bottom',
					boundaryGap: true,
					axisTick: {
						onGap: false
					},
					splitLine: {
						show: false
					},
					axisLabel: {
						textStyle: {
							fontSize: 10,
						}
					},
					axisLine: {
						lineStyle: {
							color: '#8392A5'
						}
					},
					data: time
				}],
				yAxis: [{
					type: 'value',
					name: '成交量(万)',
					axisLine: {
						lineStyle: {
							color: '#8392A5'
						}
					},
					axisTick: {
						show: false,
					},
					scale: true,
					axisLabel: {
						formatter: function(a) {
							a = +a;
							return isFinite(a) ?
								echarts.format.addCommas(+a / 10000) :
								'';
						},
						textStyle: {
							fontSize: 10
						}
					},
					splitLine: {
						show: true,
						lineStyle: {
							color: "#8392A5"
						}
					}
				}],
				tooltip: {
					trigger: 'axis',
					axisPointer: {
						type: 'line',
						animation: false,
						lineStyle: {
							color: '#ffffff',
							width: 1,
							opacity: 1
						}
					},
					triggerOn: 'mousemove|click'
				},
				series: [{
					name: '成交量',
					type: 'bar',
					data: vol
				}]
			};
			state.market.option2 = {
				backgroundColor: 'transparent',
				tooltip: {
					show: true,
					transitionDuration: 0,
					trigger: 'axis',
					axisPointer: {
						type: 'line',
						animation: false,
						lineStyle: {
							color: '#ffffff',
							width: 1,
							opacity: 1
						}
					},
					formatter: function(params) {
						var time = params[0].name;
						var val = params[0].value;
						if(time == null || time == "") {
							return
						}
						var html = '时间:' + time + '<br/>' +
							'价格: ' + val + '<br/>';
						return html;
					},
				},
				toolbox: {
					show: false,
				},
				animation: false,
				xAxis: [{
					type: 'category',
					show: false,
					data: time,
					axisLine: {
						lineStyle: {
							color: '#8392A5'
						}
					},
					boundaryGap: true
				}],
				yAxis: [{
					type: 'value',
					scale: true,
					position: "left",
					axisTick: {
						show: false,
					},
					axisLine: {
						lineStyle: {
							color: '#8392A5'
						}
					},
					splitArea: {
						show: false
					},
					axisLabel: {
						inside: false,
						margin: 4,
					},
					splitLine: {
						show: true,
						lineStyle: {
							color: "#8392A5"
						}
					}
				}],
				grid: {
					x: 50,
					y: 20,
					x2: 30,
					y2: 5
				},
				series: {
					type: 'line',
					label: {
						normal: {
							show: false,
							position: 'inside'
						},
					},
					lineStyle: {
						normal: {
							width: 1,
							color: "#ffffff"
						}
					},
					itemStyle: {
						normal: {
							color: "#ffffff"
						}
					},
					symbolSize: 2,
					markLine: {
						symbol: ['none', 'none'],
						clickable: false,
						lineStyle: {
							normal: {
								width: 1,
								color: "#ffffff"
							}
						},
						data: [{
								name: '标线2起点',
								value: 0,
								xAxis: "1",
								yAxis: 0
							}, // 当xAxis或yAxis为数值轴时，不管传入是什么，都被理解为数值后做空间位置换算
							{
								name: '标线2终点',
								xAxis: "2",
								yAxis: 0
							}
						]
					},
					data: price
				}
			};
		},
		updateTempdata: function(state, obj) {
			state.market.markettemp.forEach(function(e) {
				if(e.CommodityNo == obj) {
					state.market.tempArr[0] = e.LastQuotation.DateTimeStamp;
					state.market.tempArr[1] = e.LastQuotation.LastPrice;
					state.market.tempArr[2] = e.LastQuotation.OpenPrice;
					state.market.tempArr[3] = e.LastQuotation.LowPrice;
					state.market.tempArr[4] = e.LastQuotation.HighPrice;
					state.market.tempArr[5] = e.LastQuotation.Position;
					state.market.tempArr[6] = e.LastQuotation.LastVolume;
				}

			});
			//			console.time('ssss');
			//			历史数据
			var arr1 = state.market.jsonData.Parameters.Data[state.market.jsonData.Parameters.Data.length - 1][0].split(' ');
			var arr2 = arr1[1].split(':');
			//新抓取的数据
			var arr3 = state.market.tempArr[0].split(' ');
			var arr4 = arr3[1].split(':');
			if(arr2[1] == arr4[1]) {
				var time = state.market.jsonData.Parameters.Data[state.market.jsonData.Parameters.Data.length - 1][0];

				var vol = parseInt(state.market.jsonData.Parameters.Data[state.market.jsonData.Parameters.Data.length - 1][6]) + parseInt(state.market.tempArr[6]);
				state.market.jsonData.Parameters.Data[state.market.jsonData.Parameters.Data.length - 1] = state.market.tempArr;
				state.market.jsonData.Parameters.Data[state.market.jsonData.Parameters.Data.length - 1][0] = time;
				state.market.jsonData.Parameters.Data[state.market.jsonData.Parameters.Data.length - 1][6] = vol;
			} else {
				state.market.jsonData.Parameters.Data.shift();
				state.market.jsonData.Parameters.Data.push(state.market.tempArr);
				var time = state.market.tempArr[0].split(' ');
				time = time[0] + ' ' + arr4[0] + ':00:00';
				state.market.jsonData.Parameters.Data[state.market.jsonData.Parameters.Data.length - 1][0] = time;
			}
		}
	},
	actions: {
		handleTradeMessage:function(context,evt){
			var data = JSON.parse(evt.data);
			var parameters = data.Parameters;
			switch (data.Method){
				case 'OnRtnHeartBeat':
					context.state.market.HeartBeat.lastHeartBeatTimestamp = parameters.Ref; // 更新心跳最新时间戳
					break;
				case 'OnRspLogin'://登录回复
					if(parameters.Code==0){
						console.log('交易连接成功');
						// 查询持仓合计 QryHoldTotal
						context.state.tradeSocket.send('{"Method":"QryHoldTotal","Parameters":{"ClientNo":"'+context.state.market.tradeConfig.username+'"}}');
						// 查询订单 QryOrder
						context.state.tradeSocket.send('{"Method":"QryOrder","Parameters":{"ClientNo":"'+context.state.market.tradeConfig.username+'"}}');
						// 查询成交记录
						context.state.tradeSocket.send('{"Method":"QryTrade","Parameters":{"ClientNo":"'+context.state.market.tradeConfig.username+'"}}');
						// 查询账户信息 QryAccount
						context.state.tradeSocket.send('{"Method":"QryAccount","Parameters":{"ClientNo":"'+context.state.market.tradeConfig.username+'"}}');
						// 查询历史成交
//						context.state.tradeSocket.send('{"Method":"QryHisTrade","Parameters":{"ClientNo":"'+context.state.market.tradeConfig.username+'","BeginTime":"","EndTime":""}}');
					}else{
						console.log('交易连接失败');
					}
					break;
				case 'OnRspLogout': //登出回复
					if(parameters.Code==0){
						console.log('登出成功');
					}else{
						console.log('登出失败');
					}
					break;
				case 'OnRspQryHoldTotal': //查询持仓合计回复
					console.log('查询持仓合计回复');		
					if (parameters == null || typeof(parameters) == "undefined" || parameters.length == 0){
						context.state.market.ifUpdateHoldProfit=true;//可以使用最新行情更新持仓盈亏
					}else{
						//数据加载到页面
						context.state.market.qryHoldTotalArr.push(parameters);
						//初始化持仓列表中的浮动盈亏
//						console.log(context.state.market.qryHoldTotalArr);
						console.log(context.state.market.positionListCont);
						context.dispatch('updateHoldFloatingProfit',parameters);
						
					}
					
					break;
				case 'OnRspQryOrder': //查询订单信息回复
					console.log('查询订单信息回复');
//					console.log(context.state.market.orderTemplist[parameters.CommodityNo].CommodityName);
					if(parameters!=null){
						context.dispatch('appendOrder',parameters);
						context.dispatch('appendApply',parameters);
					}
					break;
				case 'OnRspQryTrade'://查询成交记录回复
					console.log('查询成交记录回复');
					if(parameters!=null){
						context.state.market.OnRspQryTradeDealListCont.push(parameters);
					}
					break;
				case 'OnRspQryAccount'://查询账户信息回复
					console.log('查询账户信息回复');
					if(parameters == null || typeof(parameters) == "undefined" || parameters.length == 0){
						context.dispatch('updateTotalAccount',parameters);
						context.state.market.ifUpdateAccountProfit = true;
					}else{
						context.dispatch('initCacheAccount',parameters);
					}
					break;
				case 'OnRspQryHisTrade'://查询历史成交记录回复
					console.log('查询历史成交记录回复');
					break;
				case 'OnRtnMoney':
					console.log('资金变化通知');
					// 更新资金账户信息
					context.dispatch('updateCacheAccount',parameters);
					// 更新资金汇总信息
					context.dispatch('updateTotalAccount',parameters);
					break;	
				case 'OnRtnOrderState'://订单状态改变通知
					console.log('订单状态改变通知');
					//更新委托表
					context.dispatch('updateOrder',parameters);
					//更新挂单表
					context.dispatch('updateApply',parameters);
					break;
				case 'OnRspOrderInsert':
					console.log('报单请求回复');
					//添加到委托表
					context.dispatch('appendOrder',parameters);
					// 排队中委托单放入挂单列表
					context.dispatch('appendApply',parameters);
					break;
				case 'OnRtnHoldTotal':
					console.log('持仓合计变化推送通知');
					context.dispatch('updateHold',parameters);
					break;
				case 'OnError':
					console.log('OnError');
					console.log(parameters);
				default:
					break;
			}
		},
		updateApply:function(context,parameters){
			// 2 排队中 状态，新增/更新挂单列表
			// 2.2 部分成交、完全成交、已撤单、下单失败、未知 状态，需处理挂单
			var isExist = false;
			var index=0;
			var currentObj=null;
			context.state.market.orderListCont.forEach(function(e,i){
				if(e.OrderID == parameters.OrderID){
					isExist = true;
					index = i;
					currentObj = e;
				}
			});
			console.log(parameters);
			if(parameters.OrderStatus < 3 ){
//				context.state.market.OnRspOrderInsertOrderListCont.push(parameters);
				if(isExist==true){
					currentObj.delegatePrice = parameters.OrderPrice;
					currentObj.delegateNum = parameters.OrderNum;
					currentObj.ApplyOrderNum = parameters.OrderNum- parameters.TradeNum;
					context.state.market.orderListCont.splice(index,1,currentObj);
					
					context.state.market.OnRspOrderInsertOrderListCont[context.state.market.OnRspOrderInsertOrderListCont.length-index-1].OrderPrice
						=parameters.OrderPrice;
					context.state.market.OnRspOrderInsertOrderListCont[context.state.market.OnRspOrderInsertOrderListCont.length-index-1].OrderNum
						=parameters.OrderNum;	
						
				}
			}else{
				if(isExist==true){
					context.state.market.orderListCont.splice(index,1);
					context.state.market.OnRspOrderInsertOrderListCont.splice(context.state.market.OnRspOrderInsertOrderListCont.length-index-1,1);
				}
			}
		},
		appendApply:function(context,parameters){
			if( parameters.OrderStatus < 3 ) { // 订单已提交、排队中、部分成交 显示到挂单列表
				context.state.market.OnRspOrderInsertOrderListCont.push(parameters);
			}
		},
		appendOrder:function(context,parameters){
			context.state.market.OnRspOrderInsertEntrustCont.push(parameters);
			var obj={};
			obj.commodityName = context.state.market.orderTemplist[parameters.CommodityNo].CommodityName;
			obj.commodityStatus=context.state.market.OrderType[parameters.OrderStatus];
			obj.buyOrSell = function(){
				if(parameters.Drection==0){
					return '买';
				}else{
					return '卖';
				}
			}();
			obj.delegatePrice = function(){
				if(parameters.OrderPrice==0){
					return '市价';
				}else{
					return parseFloat(parameters.OrderPrice).toFixed(context.state.market.orderTemplist[parameters.CommodityNo].DotSize);
				}
			}();
			obj.delegateNum = parameters.OrderNum;
			obj.TradeNum = parameters.TradeNum;
			obj.RevokeNum=function(){
				if(parameters.OrderStatus==4){
					return parameters.OrderNum - parameters.TradeNum;
				}else{
					return 0;
				}
			}();
			obj.InsertDateTime = parameters.InsertDateTime;
			obj.ContractCode = parameters.ContractCode;
			obj.OrderID = parameters.OrderID;
			context.state.market.entrustCont.unshift(obj);		
			
		},
		updateOrder:function(context,parameters){
			context.state.market.entrustCont.forEach(function(e,i){
				
				if(e.OrderID==parameters.OrderID){
					e.commodityStatus = context.state.market.OrderType[parameters.OrderStatus];
					e.delegatePrice=e.delegatePrice=='市价'?'市价':parameters.OrderPrice;
					e.delegateNum = parameters.OrderNum;
					e.TradeNum = parameters.TradeNum;
					e.RevokeNum=parameters.OrderNum-parameters.TradeNum;
					context.state.market.entrustCont.splice(i,1,e);
				}
			});
		},
		//更新持仓
		updateHold:function(context,parameters){
			console.log(parameters);
			console.log(context.state.market.qryHoldTotalArr);
			console.log('3333333');
			console.log(context.state.market.positionListCont);
			var isExist = false;
			var positionListContCurrent = null;
			var positionListContCurrentIndex=0;
			context.state.market.positionListCont.forEach(function(e,i){
				if(e.commodityNocontractNo==parameters.ContractCode){
					positionListContCurrent = e;
					positionListContCurrentIndex = i;
					isExist = true;
				}
			});
			if(isExist==false){
				var obj={};
				obj.name=context.state.market.orderTemplist[parameters.CommodityNo].CommodityName;
				obj.type=function(){
					if(parameters.Drection==0){
						return '多'
					}else{
						return '空'
					}
				}();
				obj.num=parameters.HoldNum;
				obj.price=parameters.HoldAvgPrice.toFixed(context.state.market.orderTemplist[parameters.CommodityNo].DotSize);
				obj.total=0;
				obj.showbar=false;
				obj.type_color=function(){
					if(parameters.Drection==0){
						return 'red'
					}else{
						return 'green'
					}
				}();
				obj.total_color='green';
				obj.commodityNocontractNo = context.state.market.orderTemplist[parameters.CommodityNo].LastQuotation.CommodityNo
											+context.state.market.orderTemplist[parameters.CommodityNo].LastQuotation.ContractNo;
				
				if(parameters.HoldNum!=0){
					context.state.market.positionListCont.unshift(obj);	
					context.state.market.qryHoldTotalArr.push(parameters);
				}
				
			}
			
			if(isExist==true){
					if(parameters.HoldNum!=0){
						positionListContCurrent.num=parameters.HoldNum;
						if(parameters.Drection==0){
							 positionListContCurrent.type ='多';
						}
						if(parameters.Drection==1){
							 positionListContCurrent.type='空';
						}
						
						positionListContCurrent.price = parseFloat(parameters.OpenAvgPrice)
															.toFixed(context.state.market.orderTemplist[parameters.CommodityNo].DotSize);
						context.state.market.positionListCont.splice(positionListContCurrentIndex,1,positionListContCurrent);
						
						context.state.market.qryHoldTotalArr[context.state.market.qryHoldTotalArr.length-1-positionListContCurrentIndex].HoldNum = parameters.HoldNum;
						context.state.market.qryHoldTotalArr[context.state.market.qryHoldTotalArr.length-1-positionListContCurrentIndex].Drection = parameters.Drection;
						context.state.market.qryHoldTotalArr[context.state.market.qryHoldTotalArr.length-1-positionListContCurrentIndex].OpenAvgPrice = parameters.OpenAvgPrice;
					}else{
						context.state.market.positionListCont.splice(positionListContCurrentIndex,1);
						context.state.market.qryHoldTotalArr.splice(context.state.market.qryHoldTotalArr.length-1-positionListContCurrentIndex,1);
					}
			}
			
					
		},
//		setHoldFloatingProfit:function(context){
//			
//		},
		updateAccountFloatingProfit:function(context,parameters){
			if(context.state.market.ifUpdateAccountProfit){
				// 遍历持仓浮盈，根据币种合并逐笔浮盈
				for(var contract in context.state.market.CacheHoldFloatingProfit.jHoldFloatingProfit){
					
					var contractFloatingProfit =context.state.market.CacheHoldFloatingProfit.jHoldFloatingProfit[contract];
					var obj = context.state.market.CacheHoldFloatingProfit.jCurrencyNoFloatingProfit[contractFloatingProfit.currencyNo];
					if (obj == null || typeof(obj) == "undefined" || obj.length == 0){
						if(isNaN(contractFloatingProfit.floatingProfit) || !contractFloatingProfit.floatingProfit){
							context.state.market.CacheHoldFloatingProfit.jCurrencyNoFloatingProfit[contractFloatingProfit.currencyNo] =0.0;
						}else{
							context.state.market.CacheHoldFloatingProfit.jCurrencyNoFloatingProfit[contractFloatingProfit.currencyNo] 
								= parseFloat(contractFloatingProfit.floatingProfit).toFixed(2);
						}
					}
					
					else{
//						context.state.market.CacheHoldFloatingProfit.jCurrencyNoFloatingProfit[contractFloatingProfit.currencyNo]=0.0;
						var tmp1 = parseFloat(context.state.market.CacheHoldFloatingProfit.jCurrencyNoFloatingProfit[contractFloatingProfit.currencyNo]);
						var tmp2 = parseFloat(contractFloatingProfit.floatingProfit);
						if (isNaN(tmp1+tmp2) || !(tmp1+tmp2)){
							context.state.market.CacheHoldFloatingProfit.jCurrencyNoFloatingProfit[contractFloatingProfit.currencyNo]=0.0;
						}else{
							context.state.market.CacheHoldFloatingProfit.jCurrencyNoFloatingProfit[contractFloatingProfit.currencyNo] 
								=parseFloat(tmp1+tmp2).toFixed(2);
						
						}
						
						
					}
					
					
				}
				// 根据币种更新资金账户：逐笔浮盈、逐笔浮盈率、当前权益、可用资金
				for (var currencyNo in context.state.market.CacheHoldFloatingProfit.jCurrencyNoFloatingProfit){
					
					var floatingProfit = parseFloat(context.state.market.CacheHoldFloatingProfit.jCurrencyNoFloatingProfit[currencyNo]);
					var todayAmount = parseFloat(context.state.market.CacheAccount.jCacheAccount[currencyNo].TodayAmount);
					// 当前权益 = 当前结存 + 逐笔浮盈
					var todayBalance = todayAmount + floatingProfit;
					var deposit =parseFloat(context.state.market.CacheAccount.jCacheAccount[currencyNo].Deposit);
					var frozenMoney =parseFloat(context.state.market.CacheAccount.jCacheAccount[currencyNo].FrozenMoney)
					var todayCanUse = todayBalance - deposit - frozenMoney;
					context.state.market.CacheAccount.jCacheAccount[currencyNo].FloatingProfit=floatingProfit;
					context.state.market.CacheAccount.jCacheAccount[currencyNo].TodayBalance=todayBalance;
					context.state.market.CacheAccount.jCacheAccount[currencyNo].TodayCanUse=todayCanUse;
					
				}
				// 清空币种盈亏
				context.state.market.CacheHoldFloatingProfit.jCurrencyNoFloatingProfit = {};
				//更新账户盈亏
				context.dispatch('updateTotalAccount');
				
			}
		},
		updateCacheAccount:function(context,parameters){
			
			
			// 入金
			context.state.market.CacheAccount.jCacheAccount[parameters.CurrencyNo].InMoney = parameters.InMoney;
			// 出金
			context.state.market.CacheAccount.jCacheAccount[parameters.CurrencyNo].OutMoney = parameters.OutMoney;
			// 手续费
			context.state.market.CacheAccount.jCacheAccount[parameters.CurrencyNo].CounterFee = parameters.Fee;
			// 平仓盈亏
			context.state.market.CacheAccount.jCacheAccount[parameters.CurrencyNo].CloseProfit = parameters.CloseProfit;
			// 今结存
			context.state.market.CacheAccount.jCacheAccount[parameters.CurrencyNo].TodayAmount = parameters.TodayAmount;
			// 保证金
			context.state.market.CacheAccount.jCacheAccount[parameters.CurrencyNo].Deposit = parameters.Deposit;
			// 挂单保证金
			context.state.market.CacheAccount.jCacheAccount[parameters.CurrencyNo].FrozenMoney = parameters.FrozenMoney;
			
		},
		updateTotalAccount:function(context,parameters){
			var obj = context.state.market.CacheAccount.jCacheAccount;
			if (obj == null || typeof(obj) == "undefined" || obj.length == 0) {
				return 0;
			}
			var jCacheAccount = context.state.market.CacheAccount.jCacheAccount;
			context.state.market.CacheAccount.jCacheTotalAccount.FloatingProfit=0.0;
			context.state.market.CacheAccount.jCacheTotalAccount.TodayBalance=0.0;
			context.state.market.CacheAccount.jCacheTotalAccount.TodayCanUse=0.0;
			context.state.market.CacheAccount.jCacheTotalAccount.CloseProfit=0.0;
			context.state.market.CacheAccount.jCacheTotalAccount.FrozenMoney=0.0;
			context.state.market.CacheAccount.jCacheTotalAccount.Deposit=0.0;
			context.state.market.CacheAccount.jCacheTotalAccount.CounterFee=0.0;
			context.state.market.CacheAccount.jCacheTotalAccount.RiskRate =0.0;
			for( var e in jCacheAccount){
				// 逐笔浮盈：直接从当前资金账户获取
				var floatingProfit = jCacheAccount[e].FloatingProfit;
				context.state.market.CacheAccount.jCacheTotalAccount.FloatingProfit  += floatingProfit * jCacheAccount[e].CurrencyRate;
				
				// 今权益 = 今结存 + 浮盈
				var tmpTodayBalance = jCacheAccount[e].TodayAmount + floatingProfit;
				context.state.market.CacheAccount.jCacheTotalAccount.TodayBalance += tmpTodayBalance * jCacheAccount[e].CurrencyRate;
				
				// 今可用=今权益-冻结资金-保证金
				context.state.market.CacheAccount.jCacheTotalAccount.TodayCanUse += (tmpTodayBalance - jCacheAccount[e].FrozenMoney - jCacheAccount[e].Deposit) * jCacheAccount[e].CurrencyRate;
				// 平仓盈亏
				context.state.market.CacheAccount.jCacheTotalAccount.CloseProfit += jCacheAccount[e].CloseProfit * jCacheAccount[e].CurrencyRate;
				// 冻结资金
				context.state.market.CacheAccount.jCacheTotalAccount.FrozenMoney += jCacheAccount[e].FrozenMoney * jCacheAccount[e].CurrencyRate;
				// 保证金
				context.state.market.CacheAccount.jCacheTotalAccount.Deposit += jCacheAccount[e].Deposit * jCacheAccount[e].CurrencyRate;
				// 手续费
				context.state.market.CacheAccount.jCacheTotalAccount.CounterFee += jCacheAccount[e].CounterFee * jCacheAccount[e].CurrencyRate;
			}
			// 风险率 = 保证金 / 今权益 * 100%
			context.state.market.CacheAccount.jCacheTotalAccount.RiskRate 
			= context.state.market.CacheAccount.jCacheTotalAccount.Deposit / context.state.market.CacheAccount.jCacheTotalAccount.TodayBalance / 100;
			
		},
		updateAccount:function(context,parameters){
			console.log(parameters);
			// 入金
			context.state.market.CacheAccount.jCacheAccount[parameters.CurrencyNo].InMoney = parameters.InMoney;
			//出金
			context.state.market.CacheAccount.jCacheAccount[parameters.CurrencyNo].OutMoney = parameters.OutMoney;
			// 手续费
			context.state.market.CacheAccount.jCacheAccount[parameters.CurrencyNo].CounterFee = parameters.Fee;
			// 平仓盈亏
			context.state.market.CacheAccount.jCacheAccount[parameters.CurrencyNo].CloseProfit = parameters.CloseProfit;
			// 今结存
			context.state.market.CacheAccount.jCacheAccount[parameters.CurrencyNo].TodayAmount = parameters.TodayAmount;
			// 保证金
			context.state.market.CacheAccount.jCacheAccount[parameters.CurrencyNo].Deposit = parameters.Deposit;
			// 挂单保证金
			context.state.market.CacheAccount.jCacheAccount[parameters.CurrencyNo].FrozenMoney = parameters.FrozenMoney;
			console.log(context.state.market.CacheAccount.jCacheAccount[parameters.CurrencyNo]);
		},
		initCacheAccount:function(context,parameters){
			if(parameters!=null){
				context.state.market.CacheAccount.jCacheAccount[parameters.CurrencyNo] = parameters;
			}
		},
		LayerOnRspOrderInsert:function(context,parameters){
			console.log(parameters);
			console.log(context.state.market.orderTemplist[parameters.CommodityNo]);
			var CommodityName=context.state.market.orderTemplist[parameters.CommodityNo].CommodityName;
			var DirectionStr;
			if(parameters.Drection==0){
				DirectionStr='买';
			}
			if(parameters.Drection==1){
				DirectionStr='卖';
			}
			
			var price;
			if(parameters.OrderPriceType==1){
				price = '市价';
			}
			if(parameters.OrderPriceType==0){
				var DotSize = context.state.market.orderTemplist[parameters.CommodityNo].DotSize;
				if (DotSize == null || typeof(DotSize) == "undefined" || DotSize.length == 0){
					DotSize=0; 
				}
				price=parseFloat(parameters.OrderPrice).toFixed(DotSize);
			}
			
			var OrderNum = parameters.OrderNum;
			var OrderID = parameters.OrderID;
			if(parameters.OrderStatus<4){
				console.log("委托成功（"+CommodityName+","+price+","+DirectionStr+OrderNum+"手,委托号:"+OrderID+"）");
			}else{
				console.log("委托失败（"+CommodityName+","+price+","+DirectionStr+OrderNum+"手,失败原因:"+parameters.StatusMsg+"）");
			}
			
			
		},
		UpdateHoldProfit:function(context,parameters){
			if(context.state.market.ifUpdateHoldProfit){
				if(parameters!=null){
					var currentPositionListContObjIndex = 0;
					var currentPositionListContObj;
					context.state.market.positionListCont.forEach(function(e,i){
							if(e.commodityNocontractNo==(parameters.CommodityNo+parameters.ContractNo)){
								currentPositionListContObj=e;
								currentPositionListContObjIndex=i;
							}
					});
					if(currentPositionListContObj!=undefined){
						// 价差 = 最新价-开仓价
						var diff = parameters.LastPrice - currentPositionListContObj.price;
						// 合约乘数 = 最小变动价格 / 最小变动点数
						var mult = context.state.market.orderTemplist[parameters.CommodityNo].ContractSize/context.state.market.orderTemplist[parameters.CommodityNo].MiniTikeSize;
						// 浮动盈亏 = (价差/最小变动) * (合约乘数 * 最小变动) * 手数 = 价差 * 合约乘数(最小变动价格 / 最小变动点数) * 手数
						var tmpFloatingProfit = parseFloat(diff * mult * currentPositionListContObj.num).toFixed(2);
						if(currentPositionListContObj.type == '空') { // 空反向
									tmpFloatingProfit = -tmpFloatingProfit;
						}	
						if(tmpFloatingProfit>=0){
							currentPositionListContObj.total_color = 'red';
						}else{
							currentPositionListContObj.total_color = 'green';
						}
						var floatingProfit=tmpFloatingProfit+':'+context.state.market.orderTemplist[parameters.CommodityNo].CurrencyNo;
						currentPositionListContObj.total = floatingProfit;
						context.state.market.positionListCont.splice(currentPositionListContObjIndex,1,currentPositionListContObj);
						
						context.state.market.CacheHoldFloatingProfit.jHoldFloatingProfit[parameters.CommodityNo+parameters.ContractNo] 
							= {"currencyNo" : context.state.market.orderTemplist[parameters.CommodityNo].CurrencyNo, "floatingProfit" : tmpFloatingProfit};
						
						//更新账户资金盈亏
						context.dispatch('updateAccountFloatingProfit',parameters);
						
					}
					
				}
				
			}
			
		},
		updateHoldFloatingProfit:function(context,parameters){
			console.log('根据订阅行情初始化持仓盈亏');
			console.log(parameters);
			console.log(context.state.market.orderTemplist[parameters.CommodityNo]);
			var lastPrice = context.state.market.orderTemplist[parameters.CommodityNo].LastQuotation.LastPrice;
			var contract=parameters.ContractCode;
			var CommodityNo = context.state.market.orderTemplist[parameters.CommodityNo]
			var isExist = false;
			var currentObj = null;
			var currentObjIndex=0;
			context.state.market.qryHoldTotalArr.forEach(function(e,i){
				if(e.ContractCode==parameters.ContractCode){
					isExist = true;	
					currentObj = e;
					currentObjIndex=i;
				}
			});
			
			// 价差 = 最新价-开仓价
			var diff = lastPrice - currentObj.OpenAvgPrice;
			// 合约乘数 = 最小变动价格 / 最小变动点数
			var mult = context.state.market.orderTemplist[parameters.CommodityNo].ContractSize/context.state.market.orderTemplist[parameters.CommodityNo].MiniTikeSize;
			// 浮动盈亏 = (价差/最小变动) * (合约乘数 * 最小变动) * 手数 = 价差 * 合约乘数(最小变动价格 / 最小变动点数) * 手数
			var tmpFloatingProfit = parseFloat(diff * mult * currentObj.HoldNum).toFixed(2);
			if(currentObj.Drection === 1) { // 空反向
				tmpFloatingProfit = -tmpFloatingProfit;
			}
			
			var floatingProfit=tmpFloatingProfit+':'+context.state.market.orderTemplist[parameters.CommodityNo].CurrencyNo;
			
			var CommodityName = context.state.market.orderTemplist[parameters.CommodityNo].CommodityName;
			
			var currentPositionListContObj=null;
			var currentPositionListContObjIndex=0;
			var isExist00 = false;
			
			context.state.market.positionListCont.forEach(function(e,i){
				if(e.name==CommodityName){
					isExist00 = true;
					currentPositionListContObj = e;
					currentPositionListContObjIndex=i;
				}
			});
			if(isExist00==true){
				currentPositionListContObj.total = floatingProfit;
				if(tmpFloatingProfit>=0){
					currentPositionListContObj.total_color = 'red';
				}else{
					currentPositionListContObj.total_color = 'green';
				}
				
				context.state.market.positionListCont.splice(currentPositionListContObjIndex,1,currentPositionListContObj);
				
			}
			
			
		},
		
		HeartBeatTimingCheck:function(context){
			setInterval(context.setTim, 8000);	// 间隔8秒检查一次
		},
		setTim:function(context){
				if (context.state.HeartBeat.lastHeartBeatTimestamp == context.state.HeartBeat.oldHeartBeatTimestamp){
					console.log('交易服务器断开，正在重连');
				}else{
					context.state.HeartBeat.oldHeartBeatTimestamp = context.state.HeartBeat.lastHeartBeatTimestamp; // 更新上次心跳时间
				}
		},
		initTrade:function(context){
			
			context.state.tradeSocket = new WebSocket('ws://192.168.0.213:6102');
			context.state.tradeSocket.onopen = function(evt){
				//登录
				if(context.state.tradeSocket.readyState==1){ //连接已建立，可以进行通信。
					context.state.tradeSocket.send('{"Method":"Login","Parameters":{"ClientNo":"'+context.state.market.tradeConfig.username+'","PassWord":"'+context.state.market.tradeConfig.password+'","IsMock":'+context.state.market.tradeConfig.model+',"Version":"'+context.state.market.tradeConfig.version+'","Source":"'+context.state.market.tradeConfig.client_source+'"}}'
							);
																				
				}																
			};
			context.state.tradeSocket.onclose = function(evt) {
				console.log('tradeClose');
			};
			context.state.tradeSocket.onerror = function(evt) {
				console.log('tradeError');
			};
			context.state.tradeSocket.onmessage = function(evt) {
				context.dispatch('handleTradeMessage',evt);
			};
			
		},
		initQuoteClient: function(context) {
			context.state.quoteSocket = new WebSocket('ws://192.168.0.213:9002');
			context.state.quoteSocket.onopen = function(evt) {
				console.log('open');
				context.state.quoteSocket.send('{"Method":"Login","Parameters":{"UserName":"13677622344","PassWord":"a123456"}}');

			};
			context.state.quoteSocket.onclose = function(evt) {
				console.log('close');
			};
			context.state.quoteSocket.onerror = function(evt) {
				console.log('error');
			};
			context.state.quoteSocket.onmessage = function(evt) {
				context.state.wsjsondata = JSON.parse(evt.data);
				if(context.state.wsjsondata.Method == "OnRspLogin") { // 登录行情服务器
					context.state.wsmsg = '行情连接成功';
					// 查询服务器支持品种用于订阅
					context.state.quoteSocket.send('{"Method":"QryCommodity","Parameters":{' + null + '}}');
				} else if(context.state.wsjsondata.Method == "OnRspQryCommodity") { // 行情服务器支持的品种
					// 行情服务器支持的品种
					context.state.market.markettemp = JSON.parse(evt.data).Parameters;
					
					context.state.market.markettemp.forEach(function(e) {
						var key=e.CommodityNo;
						context.state.market.orderTemplist[key]=e;
						if(e.IsUsed != 0) {
							context.state.quoteSocket.send('{"Method":"Subscribe","Parameters":{"ExchangeNo":"' + e.ExchangeNo + '","CommodityNo":"' + e.CommodityNo + '","ContractNo":"' + e.MainContract + '"}}');
						}
					});
				} else if(context.state.wsjsondata.Method == "OnRspSubscribe") { // 订阅成功信息
					var key=JSON.parse(evt.data).Parameters.CommodityNo;
					context.state.market.templateList[key]=JSON.parse(evt.data).Parameters;
					context.state.market.markettemp.forEach(function(e) {
						if(e.CommodityNo == JSON.parse(evt.data).Parameters.CommodityNo) {
							e.LastQuotation = JSON.parse(evt.data).Parameters.LastQuotation;
							context.state.market.Parameters.push(e);
						}
					});
					if(context.state.market.subscribeIndex==1){
						//初始化交易
						context.dispatch('initTrade');
						//启动交易心跳定时检查
						context.dispatch('HeartBeatTimingCheck');
					}
					
					context.state.market.subscribeIndex++;
				} else if(context.state.wsjsondata.Method == "OnRtnQuote") { // 最新行情

					context.state.market.markettemp.forEach(function(e, i) {
						//如果拿到的数据的CommodityNo与缓存的数据的CommodityNo相等
						if(JSON.parse(evt.data).Parameters.CommodityNo == e.CommodityNo) {
							//就把拿到数据存入缓存中
							e.LastQuotation = JSON.parse(evt.data).Parameters;
							//将显示数据进行更新
							context.state.market.Parameters.forEach(function(a, r) {
								if(a.CommodityNo == e.CommodityNo) {
									context.state.market.Parameters.splice(r, 1, e);
								}
							});
							if(context.state.market.currentNo == e.CommodityNo) {
								context.commit('updateTempdata', context.state.market.currentNo);
								context.commit('setfensoptionsecond');
								context.state.market.jsonTow = JSON.parse(evt.data);
								context.commit('setlightDate');
								if(context.state.isshow.isfensshow == true) {
									context.state.market.charttimetime = new Date();
									context.state.market.charttimems = context.state.market.charttimetime.getTime();
									context.state.market.charttime = context.state.market.charttimems - context.state.market.charttimems2;

									if(context.state.market.charttime >= 1000 || context.state.market.charttimetemp >= 1000) {
										context.commit('drawfens', {
											id1: 'fens',
											id2: 'volume'
										});
										context.state.market.charttimetemp = 0;
									} else {
										context.state.market.charttimetemp += context.state.market.charttime;
									}
									context.state.market.charttimetime2 = new Date();
									context.state.market.charttimems2 = context.state.market.charttimetime2.getTime();
								}
								if(context.state.isshow.islightshow == true) {
									context.commit('drawlight', 'lightcharts');
								}
								if(context.state.isshow.isklineshow == true) {
									//									"ColumNames": ["DateTimeStamp", "LastPrice", "OpenPrice", "LowPrice", "HighPrice", "Position", "Volume"],
									
									//									
									var arr = [];
									arr[0] = JSON.parse(evt.data).Parameters.DateTimeStamp;
									arr[1] = JSON.parse(evt.data).Parameters.LastPrice;
									arr[2] = JSON.parse(evt.data).Parameters.OpenPrice;
									arr[3] = JSON.parse(evt.data).Parameters.LowPrice;
									arr[4] = JSON.parse(evt.data).Parameters.HighPrice;
									arr[5] = JSON.parse(evt.data).Parameters.Position;
									arr[6] = JSON.parse(evt.data).Parameters.LastVolume;

									//									
									var arr1 = JSON.parse(evt.data).Parameters.DateTimeStamp.split(' '); //得到的数据
									var arr2 = arr1[1].split(':'); //得到的数据
									var arr3 = context.state.market.jsonDataKline.Parameters.Data[context.state.market.jsonDataKline.Parameters.Data.length - 1][0].split(' ');
									var arr4 = arr3[1].split(':'); //历史

									if(context.state.market.selectTime == 1) {
										if(arr2[1] == arr4[1]) {
											arr[0] = context.state.market.jsonDataKline.Parameters.Data[context.state.market.jsonDataKline.Parameters.Data.length - 1][0];
											if(arr[1] < context.state.market.jsonDataKline.Parameters.Data[context.state.market.jsonDataKline.Parameters.Data.length - 1][3]) {
												arr[3] = arr[1];
											} else {
												arr[3] = context.state.market.jsonDataKline.Parameters.Data[context.state.market.jsonDataKline.Parameters.Data.length - 1][3]
											}
											if(arr[1] > context.state.market.jsonDataKline.Parameters.Data[context.state.market.jsonDataKline.Parameters.Data.length - 1][4]) {
												arr[4] = arr[1];
											} else {
												arr[4] = context.state.market.jsonDataKline.Parameters.Data[context.state.market.jsonDataKline.Parameters.Data.length - 1][4]
											}
											arr[1] = arr[1];
											arr[2] = context.state.market.jsonDataKline.Parameters.Data[context.state.market.jsonDataKline.Parameters.Data.length - 1][2];
											arr[5] = context.state.market.jsonDataKline.Parameters.Data[context.state.market.jsonDataKline.Parameters.Data.length - 1][5];
											arr[6] += context.state.market.jsonDataKline.Parameters.Data[context.state.market.jsonDataKline.Parameters.Data.length - 1][6];
											context.state.market.jsonDataKline.Parameters.Data[context.state.market.jsonDataKline.Parameters.Data.length - 1] = arr;
										} else if(arr2[1] > arr4[1]) {
											var arrTemp = [];
											context.state.market.jsonDataKline.Parameters.Data.shift();
											arrTemp[0] = arr[0].substring(0, arr[0].length - 2) + '00';
											arrTemp[1] = arr[1];
											arrTemp[2] = arr[1];
											arrTemp[3] = arr[1];
											arrTemp[4] = arr[1];
											arrTemp[5] = arr[5];
											arrTemp[6] = arr[6];
											arr = arrTemp;
											context.state.market.jsonDataKline.Parameters.Data.push(arr);
										}
									}else if(context.state.market.selectTime == 5){
										var timeDifference=arr2[1]-arr4[1]; 
										if(timeDifference%5 != 0) {
											arr[0] = context.state.market.jsonDataKline.Parameters.Data[context.state.market.jsonDataKline.Parameters.Data.length - 1][0];
											if(arr[1] < context.state.market.jsonDataKline.Parameters.Data[context.state.market.jsonDataKline.Parameters.Data.length - 1][3]) {
												arr[3] = arr[1];
											} else {
												arr[3] = context.state.market.jsonDataKline.Parameters.Data[context.state.market.jsonDataKline.Parameters.Data.length - 1][3]
											}
											if(arr[1] > context.state.market.jsonDataKline.Parameters.Data[context.state.market.jsonDataKline.Parameters.Data.length - 1][4]) {
												arr[4] = arr[1];
											} else {
												arr[4] = context.state.market.jsonDataKline.Parameters.Data[context.state.market.jsonDataKline.Parameters.Data.length - 1][4]
											}
											arr[1] = arr[1];
											arr[2] = context.state.market.jsonDataKline.Parameters.Data[context.state.market.jsonDataKline.Parameters.Data.length - 1][2];
											arr[5] = context.state.market.jsonDataKline.Parameters.Data[context.state.market.jsonDataKline.Parameters.Data.length - 1][5];
											arr[6] += context.state.market.jsonDataKline.Parameters.Data[context.state.market.jsonDataKline.Parameters.Data.length - 1][6];
											context.state.market.jsonDataKline.Parameters.Data[context.state.market.jsonDataKline.Parameters.Data.length - 1] = arr;
										} else{
											var arrTemp = [];
											context.state.market.jsonDataKline.Parameters.Data.shift();
											arrTemp[0] = arr[0].substring(0, arr[0].length - 2) + '00';
											arrTemp[1] = arr[1];
											arrTemp[2] = arr[1];
											arrTemp[3] = arr[1];
											arrTemp[4] = arr[1];
											arrTemp[5] = arr[5];
											arrTemp[6] = arr[6];
											arr = arrTemp;
											context.state.market.jsonDataKline.Parameters.Data.push(arr);
										}
									}else if(context.state.market.selectTime == 15){
										var timeDifference=arr2[1]-arr4[1]; 
										if(timeDifference%15 != 0) {
											arr[0] = context.state.market.jsonDataKline.Parameters.Data[context.state.market.jsonDataKline.Parameters.Data.length - 1][0];
											if(arr[1] < context.state.market.jsonDataKline.Parameters.Data[context.state.market.jsonDataKline.Parameters.Data.length - 1][3]) {
												arr[3] = arr[1];
											} else {
												arr[3] = context.state.market.jsonDataKline.Parameters.Data[context.state.market.jsonDataKline.Parameters.Data.length - 1][3]
											}
											if(arr[1] > context.state.market.jsonDataKline.Parameters.Data[context.state.market.jsonDataKline.Parameters.Data.length - 1][4]) {
												arr[4] = arr[1];
											} else {
												arr[4] = context.state.market.jsonDataKline.Parameters.Data[context.state.market.jsonDataKline.Parameters.Data.length - 1][4]
											}
											arr[1] = arr[1];
											arr[2] = context.state.market.jsonDataKline.Parameters.Data[context.state.market.jsonDataKline.Parameters.Data.length - 1][2];
											arr[5] = context.state.market.jsonDataKline.Parameters.Data[context.state.market.jsonDataKline.Parameters.Data.length - 1][5];
											arr[6] += context.state.market.jsonDataKline.Parameters.Data[context.state.market.jsonDataKline.Parameters.Data.length - 1][6];
											context.state.market.jsonDataKline.Parameters.Data[context.state.market.jsonDataKline.Parameters.Data.length - 1] = arr;
										} else{
											var arrTemp = [];
											context.state.market.jsonDataKline.Parameters.Data.shift();
											arrTemp[0] = arr[0].substring(0, arr[0].length - 2) + '00';
											arrTemp[1] = arr[1];
											arrTemp[2] = arr[1];
											arrTemp[3] = arr[1];
											arrTemp[4] = arr[1];
											arrTemp[5] = arr[5];
											arrTemp[6] = arr[6];
											arr = arrTemp;
											context.state.market.jsonDataKline.Parameters.Data.push(arr);
										}
									}else if(context.state.market.selectTime == 30){
										var timeDifference=arr2[1]-arr4[1]; 
										if(timeDifference%30 != 0) {
											console.log(1)
											arr[0] = context.state.market.jsonDataKline.Parameters.Data[context.state.market.jsonDataKline.Parameters.Data.length - 1][0];
											if(arr[1] < context.state.market.jsonDataKline.Parameters.Data[context.state.market.jsonDataKline.Parameters.Data.length - 1][3]) {
												arr[3] = arr[1];
											} else {
												arr[3] = context.state.market.jsonDataKline.Parameters.Data[context.state.market.jsonDataKline.Parameters.Data.length - 1][3]
											}
											if(arr[1] > context.state.market.jsonDataKline.Parameters.Data[context.state.market.jsonDataKline.Parameters.Data.length - 1][4]) {
												arr[4] = arr[1];
											} else {
												arr[4] = context.state.market.jsonDataKline.Parameters.Data[context.state.market.jsonDataKline.Parameters.Data.length - 1][4]
											}
											arr[1] = arr[1];
											arr[2] = context.state.market.jsonDataKline.Parameters.Data[context.state.market.jsonDataKline.Parameters.Data.length - 1][2];
											arr[5] = context.state.market.jsonDataKline.Parameters.Data[context.state.market.jsonDataKline.Parameters.Data.length - 1][5];
											arr[6] += context.state.market.jsonDataKline.Parameters.Data[context.state.market.jsonDataKline.Parameters.Data.length - 1][6];
											context.state.market.jsonDataKline.Parameters.Data[context.state.market.jsonDataKline.Parameters.Data.length - 1] = arr;
										} else{
											var arrTemp = [];
											context.state.market.jsonDataKline.Parameters.Data.shift();
											arrTemp[0] = arr[0].substring(0, arr[0].length - 2) + '00';
											arrTemp[1] = arr[1];
											arrTemp[2] = arr[1];
											arrTemp[3] = arr[1];
											arrTemp[4] = arr[1];
											arrTemp[5] = arr[5];
											arrTemp[6] = arr[6];
											arr = arrTemp;
											context.state.market.jsonDataKline.Parameters.Data.push(arr);
										}
									}else if(context.state.market.selectTime == 1440){
										var timeDifference=arr2[1]-arr4[1]; 
										if(timeDifference%1440 != 0) {
											arr[0] = context.state.market.jsonDataKline.Parameters.Data[context.state.market.jsonDataKline.Parameters.Data.length - 1][0];
											if(arr[1] < context.state.market.jsonDataKline.Parameters.Data[context.state.market.jsonDataKline.Parameters.Data.length - 1][3]) {
												arr[3] = arr[1];
											} else {
												arr[3] = context.state.market.jsonDataKline.Parameters.Data[context.state.market.jsonDataKline.Parameters.Data.length - 1][3]
											}
											if(arr[1] > context.state.market.jsonDataKline.Parameters.Data[context.state.market.jsonDataKline.Parameters.Data.length - 1][4]) {
												arr[4] = arr[1];
											} else {
												arr[4] = context.state.market.jsonDataKline.Parameters.Data[context.state.market.jsonDataKline.Parameters.Data.length - 1][4]
											}
											arr[1] = arr[1];
											arr[2] = context.state.market.jsonDataKline.Parameters.Data[context.state.market.jsonDataKline.Parameters.Data.length - 1][2];
											arr[5] = context.state.market.jsonDataKline.Parameters.Data[context.state.market.jsonDataKline.Parameters.Data.length - 1][5];
											arr[6] += context.state.market.jsonDataKline.Parameters.Data[context.state.market.jsonDataKline.Parameters.Data.length - 1][6];
											context.state.market.jsonDataKline.Parameters.Data[context.state.market.jsonDataKline.Parameters.Data.length - 1] = arr;
										} else{
											var arrTemp = [];
											context.state.market.jsonDataKline.Parameters.Data.shift();
											arrTemp[0] = arr[0].substring(0, arr[0].length - 2) + '00';
											arrTemp[1] = arr[1];
											arrTemp[2] = arr[1];
											arrTemp[3] = arr[1];
											arrTemp[4] = arr[1];
											arrTemp[5] = arr[5];
											arrTemp[6] = arr[6];
											arr = arrTemp;
											context.state.market.jsonDataKline.Parameters.Data.push(arr);
										}
									}
									context.commit('setklineoption');
									context.commit('drawkline', {
										id1: 'kliness',
										id2: 'volume'
									});
								}
							}
						}
					});
					/**
					 * 更新持仓盈亏
					 */
					context.dispatch('UpdateHoldProfit',JSON.parse(evt.data).Parameters);
				} else if(context.state.wsjsondata.Method == "OnRspQryHistory") { // 历史行情
					context.state.market.jsonData = JSON.parse(evt.data);
					if(context.state.isshow.fshow == true) {
						context.commit('setfensoption');
						context.commit('drawfens', {
							id1: 'fens',
							id2: 'volume'
						});
					} else if(context.state.isshow.kshow == true) {
						context.state.market.jsonDataKline = JSON.parse(evt.data);
						context.commit('setklineoption');
						context.commit('drawkline', {
							id1: 'kliness',
							id2: 'volume'
						});
					}
				}
			}
		},
	}
})