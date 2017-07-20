import Vue from 'vue'
import Vuex from 'vuex'

//import QuoteMethod from '../assets/n_quote_vo'
//import Quote from '../assets/QuoteUtils'

//this.$http.post(
//	'/api/user/security/validatecard', {
//		emulateJSON: true
//	}, {
//		headers: {
//			"secret": "b45efe40116ba803048613d0b163a96d",
//			"token": "ZmY4MDgwODE1YzliYzY0ZjAxNWNhNTkxN2Q1YTAyNTc="
//		}
//	}, {
//		params: {
//			"name": "黄俊",
//			"card": "510107198801302311"
//		}
//	}
//
//).then(function(e) {
//	console.log(e.body)
//}, function() {});

//var format = function(time, format) {
//				var t = new Date(time);
//				var tf = function(i) {
//					return(i < 10 ? '0' : '') + i
//				};
//				return format.replace(/yyyy|MM|dd|HH|mm|ss/g, function(a) {
//					switch(a) {
//						case 'yyyy':
//							return tf(t.getFullYear());
//							break;
//						case 'MM':
//							return tf(t.getMonth() + 1);
//							break;
//						case 'mm':
//							return tf(t.getMinutes());
//							break;
//						case 'dd':
//							return tf(t.getDate());
//							break;
//						case 'HH':
//							return tf(t.getHours());
//							break;
//						case 'ss':
//							return tf(t.getSeconds());
//							break;
//					};
//				});
//			};
//			alert(format(1498029506000, 'yyyy-MM-dd HH:mm:ss'));

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
		guideshow: false
	}
};

//控制个人数据
var account = {
	state: {
		islogin: false,     //是否登录
		phone: '',        //账户
		password: '',     //密码 
		token: '',
		secret: '',
		isCertification: false,    //是否实名认证
		username: '',      //实名
		balance: 0.00,      //余额
		operateMoney: 0.00,//免提现手续费额度
		bankList: [] ,      //已绑定银行卡信息
		//存不知道有用没的数据
		tempList:[],
		//存合约列表
		programList:[]
	}
}

//控制行情数据
var market = {
	state: {
		markettemp: [],
		Parameters: [],
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
		setting: 'build',
		//请求的操盘参数数据
		tempTradeapply: {},
		quoteSocket: {},
		webuser: {
			username: '13677622344',
			password: 'a123456'
		},
		wsjsondata: {},
		//连接提示语
		wsmsg: '',
		currentdetail:{},
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
			}
		}
	},
	mutations: {
		initQuoteClient: function(state) {
			state.quoteSocket = new WebSocket('ws://192.168.0.213:9002');
			state.quoteSocket.onopen = function(evt) {
				console.log('open');
				state.quoteSocket.send('{"Method":"Login","Parameters":{"UserName":"13677622344","PassWord":"a123456"}}');

			};
			state.quoteSocket.onclose = function(evt) {
				console.log('close');
			};
			state.quoteSocket.onerror = function(evt) {
				console.log('error');
			};
			state.quoteSocket.onmessage = function(evt) {
				state.wsjsondata = JSON.parse(evt.data);
				if(state.wsjsondata.Method == "OnRspLogin") { // 登录行情服务器
					state.wsmsg = '行情连接成功';
					// 查询服务器支持品种用于订阅
					state.quoteSocket.send('{"Method":"QryCommodity","Parameters":{' + null + '}}');
				} else if(state.wsjsondata.Method == "OnRspQryCommodity") { // 行情服务器支持的品种
					// 行情服务器支持的品种
					state.market.markettemp = JSON.parse(evt.data).Parameters;
					//							console.log(JSON.stringify(state.market.markettemp));
					state.market.markettemp.forEach(function(e) {
						if(e.IsUsed != 0) {
							state.quoteSocket.send('{"Method":"Subscribe","Parameters":{"ExchangeNo":"' + e.ExchangeNo + '","CommodityNo":"' + e.CommodityNo + '","ContractNo":"' + e.MainContract + '"}}');
						}
					});
				} else if(state.wsjsondata.Method == "OnRspSubscribe") { // 订阅成功信息
					state.market.markettemp.forEach(function(e) {
						if(e.CommodityNo == JSON.parse(evt.data).Parameters.CommodityNo) {
							e.LastQuotation = JSON.parse(evt.data).Parameters.LastQuotation;
							state.market.Parameters.push(e);
						}
					})
				} else if(state.wsjsondata.Method == "OnRtnQuote") { // 最新行情

				} else if(state.wsjsondata.Method == "OnRspQryHistory") { // 历史行情
//					//k线图
//					jsonDataTwo = jsonData;
//					if(is_k) {
//						processingData(jsonData);
//					}
//
//					//分时图
//					if(is_fenshi) {
//						handleTimeChartData(jsonData);
//					}
				}
			}
		},
		drawlight: function(state, e) {
			// 引入 ECharts 主模块
			var echarts = require('echarts/lib/echarts');
			// 引入柱状图
			require('echarts/lib/chart/bar');
			// 基于准备好的dom，初始化echarts图表
			var lightChart = echarts.init(document.getElementById(e));
			lightChart.setOption(state.market.option5);
		},
		drawlightsec: function(state, e) {
			// 引入 ECharts 主模块
			var echarts = require('echarts/lib/echarts');
			// 引入柱状图
			require('echarts/lib/chart/bar');
			// 基于准备好的dom，初始化echarts图表
			var lightChart = echarts.getInstanceByDom(document.getElementById(e));
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
		processingData: function(state) {
			console.time('e');
			// 引入 ECharts 主模块
			var echarts = require('echarts/lib/echarts');
			//			console.log(echarts);
			// 引入柱状图
			require('echarts/lib/chart/bar');
			require('echarts/lib/chart/line');
			require('echarts/lib/component/tooltip');
			require('echarts/lib/chart/candlestick');
			var dosizeL = 2;
			var parameters = state.market.jsonData.Parameters.Data;
			var Len = parameters.length;
			//			if(state.market.jsonData == null) return;
			var lent = state.market.rawData.length;

			if(state.market.jsonData.Parameters.HisQuoteType == 1440) {
				for(var i = 0; i < Len; i++) {
					var timeStr = parameters[i][0].split(" ")[0];
					var openPrice = (parameters[i][2]).toFixed(dosizeL);
					var closePrice = (parameters[i][1]).toFixed(dosizeL);
					var sgData = [timeStr, openPrice, closePrice, (parameters[i][3]).toFixed(dosizeL), (parameters[i][4]).toFixed(dosizeL), parameters[i][2]];
					state.market.rawData[lent + i] = sgData;

				};
			} else {
				for(var i = 0; i < Len; i++) {
					var time2 = parameters[i][0].split(" ");
					var str1 = time2[1].split(":");
					var str2 = str1[0] + ":" + str1[1]
					var openPrice = (parameters[i][2]).toFixed(dosizeL);
					var closePrice = (parameters[i][1]).toFixed(dosizeL);
					var sgData = [str2, openPrice, closePrice, (parameters[i][3]).toFixed(dosizeL), (parameters[i][4]).toFixed(dosizeL), parameters[i][0]];
					state.market.rawData[lent + i] = sgData;
				};
				//				console.log(state.market.rawData);
			}

			var categoryData = [];
			var values = [];
			var time = []
			for(var i = 0; i < state.market.rawData.slice(-40).length; i++) {
				categoryData.push(state.market.rawData.slice(-40)[i][0]);
				//				console.log(state.market.rawData.slice(-40)[0][0]);
				values.push([state.market.rawData.slice(-40)[i][1], state.market.rawData.slice(-40)[i][2], state.market.rawData.slice(-40)[i][3], state.market.rawData.slice(-40)[i][4]]);
				time.push(state.market.rawData.slice(-40)[i][5])
			}

			state.market.chartDataC = {
				categoryData: categoryData,
				values: values,
				time: time
			};

			/*MA5 10 20 30*/
			function calculateMA(dayCount) {
				var result = [];
				for(var i = 0, len = state.market.chartDataC.values.length; i < len; i++) {
					if(i < dayCount) {
						result.push('-');
						continue;
					}
					var sum = 0;
					for(var j = 0; j < dayCount; j++) {
						sum += Number(state.market.chartDataC.values[i - j][1]);
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
					data: state.market.chartDataC.categoryData,
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
						data: state.market.chartDataC.values,
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
			console.timeEnd('e');
		},

	},
	actions: {

		drawfens: function(context, x) {
			// 引入 ECharts 主模块
			var echarts = require('echarts/lib/echarts');
			//			console.log(echarts);
			// 引入柱状图
			require('echarts/lib/chart/bar');
			require('echarts/lib/chart/line');
			require('echarts/lib/component/tooltip');

			var volume = echarts.init(document.getElementById(x.id1));
			volume.group = 'group1';
			// 基于准备好的dom，初始化echarts实例
			var fens = echarts.init(document.getElementById(x.id2));
			//			console.log(x.op1.option1);
			fens.group = 'group1';
			echarts.connect("group1");
			fens.setOption(context.state.market.option1);
			volume.setOption(context.state.market.option2);
		},
		drawfenssecond: function(context, x) {
			var echarts = require('echarts/lib/echarts');
			var volume = echarts.getInstanceByDom(document.getElementById(x.id1));
			var fens = echarts.getInstanceByDom(document.getElementById(x.id2));
			console.log(fens);
			fens.setOption(context.state.market.option1);
			volume.setOption(context.state.market.option2);
		},
		setfensoption: function(context) {
			var echarts = require('echarts/lib/echarts');
			var vol = [],
				price = [],
				time = [];
			context.state.market.jsonData.Parameters.Data.forEach(function(e) {
				vol.push(e[6]);
				time.push(e[0].split(' ')[1].split(':')[0] + ':' + e[0].split(' ')[1].split(':')[1]);
				price.push(e[1]);
			})
			context.state.market.option1 = {
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
			context.state.market.option2 = {
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

		setklineoption: function(context) {
			var echarts = require('echarts/lib/echarts');
			var vol = [],
				price = [],
				time = [];
			context.state.market.jsonData.Parameters.Data.slice(-40).forEach(function(e) {
				vol.push(e[6]);
				time.push(e[0].split(' ')[1].split(':')[0] + ':' + e[0].split(' ')[1].split(':')[1]);
				price.push(e[1]);
			});

			//成交量设置
			context.state.market.option4 = {
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
		drawkline: function(context, x) {
			// 引入 ECharts 主模块
			var echarts = require('echarts/lib/echarts');
			//			console.log(echarts);
			// 引入柱状图
			require('echarts/lib/chart/bar');
			require('echarts/lib/chart/line');
			require('echarts/lib/component/tooltip');
			require('echarts/lib/chart/candlestick');
			var kline = echarts.init(document.getElementById(x.id1));
			var volume = echarts.init(document.getElementById(x.id2));
			volume.group = 'group1';
			kline.group = 'group1';
			// 基于准备好的dom，初始化echarts实例

			echarts.connect("group1");

			kline.setOption(context.state.market.option3);
			volume.setOption(context.state.market.option4);
		},
		drawklinesec: function(context, x) {
			// 引入 ECharts 主模块
			var echarts = require('echarts/lib/echarts');
			//			console.log(echarts);
			// 引入柱状图
			require('echarts/lib/chart/bar');
			require('echarts/lib/chart/line');
			require('echarts/lib/component/tooltip');
			require('echarts/lib/chart/candlestick');
			var kline = echarts.getInstanceByDom(document.getElementById(x.id1));
			var volume = echarts.getInstanceByDom(document.getElementById(x.id2));

			kline.setOption(context.state.market.option3);

			volume.setOption(context.state.market.option4);
		}
	}
})