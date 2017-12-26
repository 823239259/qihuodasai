import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

//控制显示与否
var isshow = {
	state: {
		isconnected: false,
		//判断是第一次画图还是第二次
		isfensshow: false,
		islightshow: false,
		isklineshow: false,
		//判断是否画图
		isfens: false,
		iskline: false,
		islight: false,
		warningShow: false,
		warningType: '',
	}
};

//账户信息
var account = {
	state: {
		isRefresh: false,  //判断是否刷新
		isBack: false,   //判断是否退出交易账号并跳至首页
		loginStatus: false,  //交易账号登录状态	
		exitStatus: false,   //交易账号退出状态
		userName: '',   //平台账号
		currentNav: 0,  //当前导航索引
		currentUrlHead: 'http:', //http or https
	}
}

//行情交易数据
var market = {
	state: {
		quoteConfig:{
			url_real: "ws://192.168.0.232:9002",  //测试地址
//			url_real: "ws://quote.vs.com:9002",   //正式地址
			userName: "13677622344",
			passWord: "a123456"
		},
		tradeConfig:{
			version: "3.3",	// 版本
			url_real: '',
//			url_real: "ws://192.168.0.232:6102",   //测试地址
//			url_real: "ws://139.196.176.60:6101",  //正式地址
			model: "1", // 实盘：0；	模拟盘：1
			client_source: "N_WEB",	// 客户端渠道
//			username: "00004",		// 账号(新模拟盘——000008、直达实盘——000140、易盛模拟盘——Q517029969)
//			password: "YTEyMzQ1Ng==" 	// 密码：base64密文(明文：a123456——YTEyMzQ1Ng==     888888——ODg4ODg4	 74552102——NzQ1NTIxMDI=		123456=MTIzNDU2)
			username:'',
			password:''
		},
		//心跳信息
		HeartBeatTiming: null,   //心跳定时器
		HeartBeat:{
			lastHeartBeatTimestamp : 1,	// 行情最后心跳时间
			oldHeartBeatTimestamp : 0,	// 上一次心跳时间
			intervalCheckTime : 8000  // 间隔检查时间：8秒
		},
//		HeartBeat00:{
//			lastHeartBeatTimestamp : 1,	// 交易最后心跳时间
//			oldHeartBeatTimestamp : 0,	// 上一次心跳时间
//			intervalCheckTime : 8000  // 间隔检查时间：8秒
//		},
		quoteInitStatus: false,    //行情是否已经初始化
		quoteInitStep: '',      //判断行情Parameters是否已经初始化完
		CacheLastQuote: [],    //缓存最新一条行情数据
		volume: 0,           //缓存最新成交量
		subscribeIndex: 1,   //订阅推送次数统计
		quoteIndex: '',      //行情变化索引
		quoteColor: '',      //行情变化颜色
		
		//行情历史合约数据（分时）
		jsonData: {},
		//行情历史合约数据（K线）
		jsonDataKline: {
			"Method": "OnRspQryHistory",
			"Parameters": {
				"ColumNames": ["DateTimeStamp", "LastPrice", "OpenPrice", "LowPrice", "HighPrice", "Position", "Volume"],
				"CommodityNo": "CL",
				"ContractNo": "1708",
				"Count": 102,
				"Data": [
					["2017-06-26 09:31:00", 43.38, 43.35, 43.34, 43.39, 548303, 634,0],
				],
				"ExchangeNo": "NYMEX",
				"HisQuoteType": 0
			}
		},
		//行情数据格式
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
		chartHeight: '',
		//绘制分时的设置
		option1: {},   //成交量
		option2: {},   //价格
		//绘制K线的设置
		option3: {},   //价格
		option4: {},   //成交量
		//绘制闪电图的设置
		option5: {},   //价格
		//K线图（蜡烛图）数据
		tempArr: [], 
		//闪电图用到的数据
		lightChartTime: {
			"time": [],
			"price": []
		},
		//时间差（行情数据更新 画图）
		charttime: 0,
		charttimetime: 0,
		charttimems: 0,
		charttimetime2: 0,
		charttimems2: 0,
		
		//选择K线的类型
		selectTime: 1,
		//当前合约代码（CL）
		currentNo: '',
		//第一次所有合约列表
		markettemp: [],
		//订阅成功后查询品种列表
		orderTemplist:{},
		//存订阅成功后的行情信息
		templateList:{},
		//当前所有有效合约列表
		Parameters: [],
		//当前所有有效合约成交明细
		tradeParameters: [],
		//当前选中合约
		currentdetail: {},
		currentdetailDefault: {
			CommodityName: "国际原油",
			CommodityNo: "CL",
			ContractSize: 10,
			CurrencyNo: "USD",
			DotSize: 2,
			ExchangeNo: "NYMEX",
			Index: 1,
			IsDomestic: 0,
			IsUsed: 1,
			LastQuotation: {
				LastPrice: 55.81
			}
		},
		//当前选中合约的成交明细
		currentTradeDetails: [],
		
		//缓存账户信息
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
				RiskRate : 0.0,	// 风险率
				RiskDegree: 0.0,   //风险度
			}
		},
		//订单状态
		OrderType:{
			0: "订单已提交",
			1: "排队中",
			2: "部分成交",
			3: "完全成交",
			4: "已撤单",
			5: "下单失败",
			6: "未知"
		},
		forceLine: 0.00,    //强平线
		initBalance: 0.00,   //初始资金
		//存持仓列表
		qryHoldTotalArr: [],
		positionListCont: [],
		//委托列表页面数据
		OnRspOrderInsertEntrustCont: [],
		//挂单页面列表
		OnRspOrderInsertOrderListCont: [],
		orderListCont: [],
		//成交记录列表
		OnRspQryTradeDealListCont: [],
		//持仓合约浮盈处理
		CacheHoldFloatingProfit: {
			jHoldFloatingProfit: {},	// 持仓合约对应浮盈
			jCurrencyNoFloatingProfit: {}	// 币种对应浮盈
		},
		ifUpdateHoldProfit: false,       //是否使用最新行情更新持仓盈亏
		ifUpdateAccountProfit: false,   //是否可以更新账户盈亏标志：资金信息显示完毕就可以更新盈亏
		//成交历史列表
		queryHisList: [], 
		
		//切换后合约的名字
		jContractFloatingProfitVO:{
			currencyNo: '',
			floatingProfit: 0.0
		},
		
		//止损止盈
		stopLossList: [],   //未触发列表
		stopLossTriggeredList: [],    //已触发列表
		//条件单
		conditionList: [],   //未触发列表
		triggerConditionList: [],   //已触发列表
		
		//判断是否可以进行交易操作
		buyStatus: false,
		cancelStatus: false,
		stopStatus: false,
		conditionStatus: false,
	}
}

export default new Vuex.Store({
	modules: {
		isshow,
		account,
		market,
	},
	state: {
		wsjsondata: {},
		//行情websocket
		quoteSocket: {},
		//交易websocket
		tradeSocket: {},
		//test 测试环境，online 正式环境
		environment: 'test',
		//打包的时候，值为 build ，开发的时候，值为 dev
		setting: 'dev',
	},
	getters: {
		PATH: function(state) {
			if(state.setting == 'dev') {
				return state.account.currentUrlHead + '//test.api.duokongtai.cn';
			} else if(state.setting == 'build'){
				if(state.environment == 'test'){
					return state.account.currentUrlHead + '//test.api.duokongtai.cn';
				}else{
					return state.account.currentUrlHead + '//api.duokongtai.cn';
				}
			} else if(state.setting == 'nat') {
				return '/nat/vs-api';
			}
		}
	},
	mutations: {
		//画闪电图
		drawlight: function(state, e) {
			// 引入 ECharts 主模块
			var echarts = require('echarts/lib/echarts');
			// 引入柱状图
			require('echarts/lib/chart/bar');
			// 基于准备好的dom，初始化echarts图表
			var lightChart;
			if(state.isshow.islightshow == false) {
				lightChart = echarts.init(document.getElementById(e));
				state.isshow.islightshow = true;
			} else {
				
				if(document.getElementById(e) != null){
					lightChart = echarts.getInstanceByDom(document.getElementById(e));
				}
			}
			lightChart.setOption(state.market.option5);
		},
		//设置闪电图数据
		setlightDate: function(state) {
			var TimeLength = state.market.lightChartTime.time.length;
			state.market.lightChartTime.price.push(state.market.jsonTow.Parameters.LastPrice.toFixed(state.market.currentdetail.DotSize));
			state.market.lightChartTime.time.push((state.market.jsonTow.Parameters.DateTimeStamp).split(" ")[1]);
			state.market.lightChartTime.time = state.market.lightChartTime.time.slice(-50);
			state.market.lightChartTime.price = state.market.lightChartTime.price.slice(-50);
			state.market.option5 = {
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
//				backgroundColor: '#2d3040',
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
						margin: 3,
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
		//设置K线图数据
		setklineoption: function(state) {
			// 引入 ECharts 主模块
			var echarts = require('echarts/lib/echarts');
			// 引入柱状图
			require('echarts/lib/chart/bar');
			require('echarts/lib/chart/line');
			require('echarts/lib/component/tooltip');
			require('echarts/lib/chart/candlestick');

			var dosizeL = state.market.currentdetail.DotSize;
			var rawData = [];
			var parameters = state.market.jsonDataKline.Parameters.Data;
			var Len = parameters.length;
			var lent = rawData.length;
			if(state.market.jsonDataKline.Parameters.HisQuoteType == 1440) {
				for(var i = 0; i < Len; i++) {
					var timeStr = parameters[i][0].split(" ")[0];
					var openPrice = parseFloat(parameters[i][2]).toFixed(dosizeL);
					var closePrice = parseFloat(parameters[i][1]).toFixed(dosizeL);
					var sgData = [timeStr, openPrice, closePrice, parseFloat(parameters[i][3]).toFixed(dosizeL), parseFloat(parameters[i][4]).toFixed(dosizeL), parameters[i][2]];
					rawData[lent + i] = sgData;
				};
			} else {
				for(var i = 0; i < Len; i++) {
					var time2 = parameters[i][0].split(" ");
					var str1 = time2[1].split(":");
					var str2 = str1[0] + ":" + str1[1];
					var openPrice = parseFloat(parameters[i][2]).toFixed(dosizeL);
					var closePrice = parseFloat(parameters[i][1]).toFixed(dosizeL);
					var sgData = [str2, openPrice, closePrice, parseFloat(parameters[i][3]).toFixed(dosizeL),parseFloat(parameters[i][4]).toFixed(dosizeL), parameters[i][0]];
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
					result.push(Number(sum / dayCount).toFixed(dosizeL));
				}
				return result;
			}
			state.market.option3 = {
				grid: {
					x: 43,
					y: 20,
					x2: 30,
					y2: 5
				},
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
						var rate = (kd[2] - kd[1]) / kd[1] * 100;
						rate = parseFloat(rate).toFixed(2);
						var res = "时间:" + params[0].name + '  涨跌幅: ' + rate+'%';
						res += '<br/>  开盘 : ' + parseFloat(kd[1]).toFixed(dosizeL) + '  最高 : ' + parseFloat(kd[4]).toFixed(dosizeL);
						res += '<br/>  收盘 : ' + parseFloat(kd[2]).toFixed(dosizeL) + ' 最低 : ' + parseFloat(kd[3]).toFixed(dosizeL);
						res += '<br/> <span style="display:inline-block;margin-right:5px;border-radius:10px;width:9px;height:9px;background-color:#3689B3"></span> MA5 : ' + ma5 + '  <span style="display:inline-block;margin-right:5px;border-radius:10px;width:9px;height:9px;background-color:#B236B3"></span> MA10 : ' + ma10;
						res += '<br/><span style="display:inline-block;margin-right:5px;border-radius:10px;width:9px;height:9px;background-color:#B37436"></span> MA20 : ' + ma20 + '  <span style="display:inline-block;margin-right:5px;border-radius:10px;width:9px;height:9px;background-color:#B2B336"></span> MA30 : ' + ma30;
						return res;
					}
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
						margin: 3,
						fontSize: 10,
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
							}
						}
					}

				]
			}
			var vol = [],
				price = [],
				time = [];
			var Ktime;
			state.market.jsonDataKline.Parameters.Data.slice(-40).forEach(function(e) {
				vol.push(e[6]);
				Ktime = e[0].split(' ')[1].split(':')[0] + ':' + e[0].split(' ')[1].split(':')[1];
				if(Ktime == '00:00'){
					time.push(e[0].split(' ')[0]);
				}else{
					time.push(e[0].split(' ')[1].split(':')[0] + ':' + e[0].split(' ')[1].split(':')[1]);
				}
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
						margin: 3,
						formatter: function(a) {
							a = +a;
							return isFinite(a) ? echarts.format.addCommas(+a / 10000) : '';
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
			var h = window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight;
			if(h < 950){
				state.market.option4.yAxis[0].axisLabel.show = false
			}
		},
		//画K线图
		drawkline: function(state, x) {
			// 引入 ECharts 主模块
			var echarts = require('echarts/lib/echarts');
			// 引入柱状图
			require('echarts/lib/chart/bar');
			require('echarts/lib/chart/line');
			require('echarts/lib/component/tooltip');
			require('echarts/lib/chart/candlestick');
			var kline, volume;
			if(state.isshow.isklineshow == false) {
				kline = echarts.init(document.getElementById(x.id1));
				volume = echarts.init(document.getElementById(x.id2));
				volume.group = 'group2';
				kline.group = 'group2';
				// 基于准备好的dom，初始化echarts实例
				echarts.connect("group2");
				state.isshow.isklineshow = true;
			} else {
				if(document.getElementById(x.id1) != null){
					kline = echarts.getInstanceByDom(document.getElementById(x.id1));
				}
				if(document.getElementById(x.id2) != null){
					volume = echarts.getInstanceByDom(document.getElementById(x.id2));
				}
			}
			kline.setOption(state.market.option3);
			volume.setOption(state.market.option4);
		},
		//画分时图
		drawfens: function(state, x) {
			// 引入 ECharts 主模块
			var echarts = require('echarts/lib/echarts');
			// 引入柱状图
			require('echarts/lib/chart/bar');
			require('echarts/lib/chart/line');
			require('echarts/lib/component/tooltip');
			var fens, volume;
			if(state.isshow.isfensshow == false) {
				volume = echarts.init(document.getElementById(x.id1));
				volume.group = 'group1';
				// 基于准备好的dom，初始化echarts实例
				fens = echarts.init(document.getElementById(x.id2));
				fens.group = 'group1';
				echarts.connect("group1");
				state.isshow.isfensshow = true;
			} else {
				if(document.getElementById(x.id1) != null){
					volume = echarts.getInstanceByDom(document.getElementById(x.id1));
				}
				if(document.getElementById(x.id2) != null){
					fens = echarts.getInstanceByDom(document.getElementById(x.id2));
				}
			}
			fens.setOption(state.market.option1);
			volume.setOption(state.market.option2);
			fens.resize();
			volume.resize();
		},
		//设置分时图数据
		setfensoption: function(state) {
			var echarts = require('echarts/lib/echarts');
			var vol = [],
				price = [],
				time = [];
			var dosizeL = state.market.currentdetail.DotSize;
			state.market.jsonData.Parameters.Data.forEach(function(e) {
				vol.push(e[6]);
				time.push(e[0].split(' ')[1].split(':')[0] + ':' + e[0].split(' ')[1].split(':')[1]);
				price.push(e[1]);
			});
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
						margin: 3,
						formatter: function(a) {
							a = +a;
							return isFinite(a) ?
								echarts.format.addCommas(+a / 10000) :
								'';
						},
						textStyle: {
							fontSize: 10
						},
						showMinLabel: null
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
			var h = window.innerHeight || document.documentElement.clientHeight || document.body.clientHeight;
			if(h < 950){
				state.market.option1.yAxis[0].axisLabel.show = false
			}
			state.market.option2 = {
				grid: {
					x: 50,
					y: 20,
					x2: 30,
					y2: 5
				},
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
						var val = parseFloat(params[0].value).toFixed(dosizeL);
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
						margin: 3,
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
				series: {
					type: 'line',
					data: price,
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
								value: 45.36,
								xAxis: "1",
								yAxis: -1
							}, // 当xAxis或yAxis为数值轴时，不管传入是什么，都被理解为数值后做空间位置换算
							{
								name: '标线2终点',
								xAxis: "2",
								yAxis: 100
							}
						]
					},
				}
			};
		},
	},
	actions: {
		//处理交易数据
		handleTradeMessage: function(context,evt){
			var data = JSON.parse(evt.data);
			var parameters = data.Parameters;
			switch (data.Method){
				case 'OnRtnHeartBeat':
					context.state.market.HeartBeat.lastHeartBeatTimestamp = parameters.Ref; // 更新心跳最新时间戳
					break;
				case 'OnRspLogin':
					if(context.state.market.tradeConfig.username == '' && localStorage.tradeUser != '' && localStorage.tradeUser != undefined){
						context.state.market.tradeConfig.username = JSON.parse(localStorage.tradeUser).username;
					}
					//回复
					if(parameters.Code == 0){
						layer.msg('交易服务器连接成功',{time: 1000});
						sessionStorage.tradeStatus = true;
						context.state.account.loginStatus = true;
						context.state.account.exitStatus = false;
						//设置强平线
						context.state.market.forceLine = parameters.ForceLine;
						//初始资金
						context.state.market.initBalance = parameters.InitBalance;
						//查询持仓合计 
						context.state.tradeSocket.send('{"Method":"QryHoldTotal","Parameters":{"ClientNo":"'+context.state.market.tradeConfig.username+'"}}');
						//查询订单 
						context.state.tradeSocket.send('{"Method":"QryOrder","Parameters":{"ClientNo":"'+context.state.market.tradeConfig.username+'"}}');
						//查询成交记录
						context.state.tradeSocket.send('{"Method":"QryTrade","Parameters":{"ClientNo":"'+context.state.market.tradeConfig.username+'"}}');
						//查询账户信息 
						context.state.tradeSocket.send('{"Method":"QryAccount","Parameters":{"ClientNo":"'+context.state.market.tradeConfig.username+'"}}');
						//查询止损单
						context.state.tradeSocket.send('{"Method":"QryStopLoss","Parameters":{"ClientNo":"'+context.state.market.tradeConfig.username+'"}}');
						//查询条件单
						context.state.tradeSocket.send('{"Method":"QryCondition","Parameters":{"ClientNo":"'+context.state.market.tradeConfig.username+'"}}');
						//查询历史成交
						context.dispatch('qryHisTrade');
						//启动交易心跳定时检查
						context.dispatch('HeartBeatTimingCheck');
					}else{
						layer.msg(parameters.Message,{time: 1000});
						context.state.account.loginStatus = false;
						context.state.tradeSocket.close();
						context.state.tradeSocket = null;
						localStorage.removeItem('tradeUser');
					}
					break;
				case 'OnRspLogout': //登出回复
					if(parameters.Code == 0){
//						console.log('登出成功');
						layer.msg('退出成功', {time: 1000});
						context.state.account.exitStatus = true;
					}else{
						console.log('登出失败');
						layer.msg(parameters.Message, {time: 1000});
						context.state.account.loginStatus = false;
						setTimeout(function(){
							context.state.account.isBack = true;
						}, 1000);
					}
					break;
				case 'OnRspQryHoldTotal': //查询持仓合计回复
//					console.log('查询持仓合计回复');	
					if (parameters == null || typeof(parameters) == "undefined" || parameters.length == 0){
						context.state.market.ifUpdateHoldProfit = true;//可以使用最新行情更新持仓盈亏
					}else{
						//数据加载到页面
						context.state.market.qryHoldTotalArr.push(parameters);
						//初始化持仓列表中的浮动盈亏
						context.dispatch('updateHoldFloatingProfit',parameters);
					}
					break;
				case 'OnRspQryOrder': //查询订单信息回复
//					console.log('查询订单信息回复');
					if(parameters!=null){
						context.dispatch('appendOrder',parameters);
						context.dispatch('appendApply',parameters);
					}
					break;
				case 'OnRspQryTrade'://查询成交记录回复
//					console.log('查询成交记录回复');
					if(parameters!=null){
						context.state.market.OnRspQryTradeDealListCont.push(parameters);
					}
					break;
				case 'OnRspQryAccount'://查询账户信息回复
//					console.log('查询账户信息回复');
					if(parameters == null || typeof(parameters) == "undefined" || parameters.length == 0){
						context.dispatch('updateTotalAccount',parameters);
						context.state.market.ifUpdateAccountProfit = true;
					}else{
						context.state.market.CacheAccount.moneyDetail.push(parameters);
						context.dispatch('initCacheAccount',parameters);
					}
					break;
				case 'OnRspQryHisTrade'://查询历史成交记录回复
					if(parameters!=null){
						context.state.market.queryHisList.push(parameters);
					}
					break;
				case 'OnRtnMoney':
//					console.log('资金变化通知');
					// 更新资金账户信息
					context.dispatch('updateCacheAccount',parameters);
					// 更新资金汇总信息
					context.dispatch('updateTotalAccount',parameters);
					break;	
				case 'OnRtnOrderState'://订单状态改变通知
//					console.log('订单状态改变通知');
					//更新委托表
					context.dispatch('updateOrder',parameters);
					//更新挂单表
					context.dispatch('updateApply',parameters);
					break;
				case 'OnRspOrderInsert':
//					console.log('报单请求回复');
					context.dispatch('layerMessage',parameters);
					//添加到委托表
					context.dispatch('appendOrder00',parameters);
					//排队中委托单放入挂单列表
					context.dispatch('appendApply',parameters);
					break;
				case 'OnRtnHoldTotal':
					context.dispatch('updateHold',parameters);
					break;
				case 'OnRtnOrderTraded':
					if(parameters != null){
						context.state.market.OnRspQryTradeDealListCont.unshift(parameters);
					}
					context.dispatch('layerOnRtnOrderTraded',parameters);
					break;
				case 'OnRspQryStopLoss':
					if(parameters != null){
						if(parameters.Status == 0 || parameters.Status == 1){
							context.state.market.stopLossList.push(parameters);
//						}else if(parameters.Status == 2 || parameters.Status == 3 || parameters.Status == 4 || parameters.Status == 5){
						}else if(parameters.Status >= 2){
							context.state.market.stopLossTriggeredList.push(parameters);
						}
					}
					break;
				case 'OnRtnStopLossState':
					context.dispatch('updateStopLoss',parameters);
					break;
				case 'OnRspInsertStopLoss':
					context.dispatch('layerOnRspInsertStopLoss',parameters);
					break;
				case 'OnRspQryCondition':
					if(parameters != null){
						if(parameters.Status < 2){
							context.state.market.conditionList.push(parameters);
						}else{
							context.state.market.triggerConditionList.push(parameters);
						}
					}
					break;
				case "OnRspInsertCondition":
					if(parameters.Status == 0){
						layer.msg('设置条件单成功', {time: 1000});
					}else{
						layer.msg('设置条件单失败，原因:【'+parameters.StatusMsg+'】', {time: 1000});
					}
					setTimeout(function(){
						context.state.market.conditionStatus = false;
					}, 1000);
					context.dispatch('dealWithOnRspInsertCondition',parameters);
					break;
				case 'OnRtnConditionState':
					if(parameters.Status == 0){
						layer.msg('【'+parameters.CommodityNo+parameters.ContractNo+'】条件单【'+ parameters.ConditionNo+'】,改单后已运行', {time: 1000});
					}else if(parameters.Status == 1){
						layer.msg('【'+parameters.CommodityNo+parameters.ContractNo+'】条件单【'+ parameters.ConditionNo+'】,已暂停', {time: 1000});
					}else if(parameters.Status == 2){
						layer.msg('【'+parameters.CommodityNo+parameters.ContractNo+'】条件单【'+ parameters.ConditionNo+'】,已触发', {time: 1000});
					}else if(parameters.Status == 3){
						layer.msg('【'+parameters.CommodityNo+parameters.ContractNo+'】条件单【'+ parameters.ConditionNo+'】,已取消', {time: 1000});
					}else if(parameters.Status == 4){
						layer.msg('【'+parameters.CommodityNo+parameters.ContractNo+'】条件单【'+ parameters.ConditionNo+'】,插入失败', {time: 1000});
					}else if(parameters.Status == 5){
						layer.msg('【'+parameters.CommodityNo+parameters.ContractNo+'】条件单【'+ parameters.ConditionNo+'】,触发失败', {time: 1000});
					}
					setTimeout(function(){
						context.state.market.conditionStatus = false;
					}, 1000);
					context.dispatch('dealWithOnRtnConditionState',parameters);
					break;	
				case 'OnError':
					if(parameters!=null){
						layer.msg(parameters.Message,{time: 1000});
						setTimeout(function(){
							context.state.market.cancelStatus = false;
						}, 1000);
						setTimeout(function(){
							context.state.market.buyStatus = false;
						}, 1000);
					}
					break;
				default:
					break;
			}
			//退出交易账号清除websocket
			if(context.state.account.loginStatus == false){
				context.state.tradeSocket.close();
				context.state.tradeSocket = null;
			}
		},
		//条件单处理
		dealWithOnRtnConditionState: function(context,parameters){
			context.state.market.conditionList.forEach(function(e,i){
				if(parameters.ConditionNo==e.ConditionNo){
					let e0 = parameters;
					let b={};
					
					b.AB_BuyPoint = e0.AB_BuyPoint;
					b.AB_SellPoint = e0.AB_SellPoint;
					b.AdditionFlag=e0.AdditionFlag;
					b.AdditionPrice = e0.AdditionPrice;
					b.AdditionType = e0.AdditionType;
					b.CommodityNo = e0.CommodityNo;
					b.CompareType = e0.CompareType;
					b.ConditionNo = e0.ConditionNo;
					b.ConditionType = e0.ConditionType;
					b.ContractNo = e0.ContractNo;
					b.Drection = e0.Drection;
					b.ExchangeNo = e0.ExchangeNo;
					b.InsertDateTime = e0.InsertDateTime;
					b.Num = e0.Num;
					b.OrderType = e0.OrderType;
					b.PriceTriggerPonit = e0.PriceTriggerPonit;
					b.Status = e0.Status;
					b.StatusMsg = e0.StatusMsg;
					b.StopLossDiff = e0.StopLossDiff;
					b.StopLossType = e0.StopLossType;
					b.StopLossWin = e0.StopLossWin;
					b.TimeTriggerPoint = e0.TimeTriggerPoint;
					b.TriggedTime = e0.TriggedTime;
					
					b.name=e0.CommodityNo+e0.ContractNo;
					b.status00 = (function(){
							if(e0.Status==0){
								return '运行中';
							}else if(e0.Status==1){
								return '暂停';
							}else if(e0.Status==2){
								return '已触发';
							}else if(e0.Status==3){
								return '已取消';
							}else if(e0.Status==4){
								return '插入失败';
							}else if(e0.Status==5){
								return '触发失败';
							}
						})();
					b.type = (function(){
							if(e0.ConditionType==0){
								return '价格条件';
							}else if(e0.ConditionType==1){
								return '时间条件';
							}else if(e0.ConditionType==2){
								return 'AB单';
							}
						})();	
					b.conditions = (function(){
							
							if(e0.AdditionFlag==0){ //没有附件条件
								if(e0.CompareType==0){
									return '>'+e0.PriceTriggerPonit;
								}else if(e0.CompareType==1){
									return '<'+e0.PriceTriggerPonit;
								}else if(e0.CompareType==2){
									return '>='+e0.PriceTriggerPonit;
								}else if(e0.CompareType==3){
									return '<='+e0.PriceTriggerPonit;
								}else{
									let s = e0.TimeTriggerPoint.split(' ');
									if(e0.AdditionType==0){
										return s[1]+' >'+e0.AdditionPrice;
									}else if(e0.AdditionType==1){
										return s[1]+' <'+e0.AdditionPrice;
									}else if(e0.AdditionType==2){
										return s[1]+' >='+e0.AdditionPrice;
									}else if(e0.AdditionType==3){
										return s[1]+' <='+e0.AdditionPrice;
									}else{
										return s[1];
									}
									
								}
							}else{ //有附加条件
								if(e0.CompareType==0){
									if(e0.AdditionType==0){
										return '>'+e0.PriceTriggerPonit+' >'+e0.AdditionPrice;
									}else if(e0.AdditionType==1){
										return '>'+e0.PriceTriggerPonit+' <'+e0.AdditionPrice;
									}else if(e0.AdditionType==2){
										return '>'+e0.PriceTriggerPonit+' >='+e0.AdditionPrice;
									}else if(e0.AdditionType==3){
										return '>'+e0.PriceTriggerPonit+' <='+e0.AdditionPrice;
									}
								}else if(e0.CompareType==1){
									if(e0.AdditionType==0){
										return '<'+e0.PriceTriggerPonit+' >'+e0.AdditionPrice;
									}else if(e0.AdditionType==1){
										return '<'+e0.PriceTriggerPonit+' <'+e0.AdditionPrice;
									}else if(e0.AdditionType==2){
										return '<'+e0.PriceTriggerPonit+' >='+e0.AdditionPrice;
									}else if(e0.AdditionType==3){
										return '<'+e0.PriceTriggerPonit+' <='+e0.AdditionPrice;
									}
								}else if(e0.CompareType==2){
									if(e0.AdditionType==0){
										return '>='+e0.PriceTriggerPonit+' >'+e0.AdditionPrice;
									}else if(e0.AdditionType==1){
										return '>='+e0.PriceTriggerPonit+' <'+e0.AdditionPrice;
									}else if(e0.AdditionType==2){
										return '>='+e0.PriceTriggerPonit+' >='+e0.AdditionPrice;
									}else if(e0.AdditionType==3){
										return '>='+e0.PriceTriggerPonit+' <='+e0.AdditionPrice;
									}
								}else if(e0.CompareType==3){
									if(e0.AdditionType==0){
										return '<='+e0.PriceTriggerPonit+' >'+e0.AdditionPrice;
									}else if(e0.AdditionType==1){
										return '<='+e0.PriceTriggerPonit+' <'+e0.AdditionPrice;
									}else if(e0.AdditionType==2){
										return '<='+e0.PriceTriggerPonit+' >='+e0.AdditionPrice;
									}else if(e0.AdditionType==3){
										return '<='+e0.PriceTriggerPonit+' <='+e0.AdditionPrice;
									}
								}else{
									let s = e0.TimeTriggerPoint.split(' ');
									if(e0.AdditionType==0){
										return s[1]+' >'+e0.AdditionPrice;
									}else if(e0.AdditionType==1){
										return s[1]+' <'+e0.AdditionPrice;
									}else if(e0.AdditionType==2){
										return s[1]+' >='+e0.AdditionPrice;
									}else if(e0.AdditionType==3){
										return s[1]+' <='+e0.AdditionPrice;
									}else{
										return s[1];
									}
									
								}
							}
						})();	
					b.order = (function(){
						if(e0.Drection == 0){ //买
							if(e0.OrderType==1){
								return '买,市价,'+e0.Num+'手'
							}else{
								return '买,对手价,'+e0.Num+'手'
							}
						} else if(e0.Drection == 1){//卖
							if(e0.OrderType==1){
								return '卖,市价,'+e0.Num+'手'
							}else{
								return '卖,对手价,'+e0.Num+'手'
							}
						}
					})();
					b.term = '当日有效';
					b.time = e0.InsertDateTime;	
					if(parameters.Status < 2){
						context.state.market.conditionList.splice(i,1,parameters);
					}else{
						context.state.market.conditionList.splice(i,1);
						context.state.market.triggerConditionList.push(parameters);
					}
					
				}
			});
		},
		dealWithOnRspInsertCondition:function(context,parameters){
			
			let e0 = parameters;
			let b={};
			
			b.AB_BuyPoint = e0.AB_BuyPoint;
			b.AB_SellPoint = e0.AB_SellPoint;
			b.AdditionFlag=e0.AdditionFlag;
			b.AdditionPrice = e0.AdditionPrice;
			b.AdditionType = e0.AdditionType;
			b.CommodityNo = e0.CommodityNo;
			b.CompareType = e0.CompareType;
			b.ConditionNo = e0.ConditionNo;
			b.ConditionType = e0.ConditionType;
			b.ContractNo = e0.ContractNo;
			b.Drection = e0.Drection;
			b.ExchangeNo = e0.ExchangeNo;
			b.InsertDateTime = e0.InsertDateTime;
			b.Num = e0.Num;
			b.OrderType = e0.OrderType;
			b.PriceTriggerPonit = e0.PriceTriggerPonit;
			b.Status = e0.Status;
			b.StatusMsg = e0.StatusMsg;
			b.StopLossDiff = e0.StopLossDiff;
			b.StopLossType = e0.StopLossType;
			b.StopLossWin = e0.StopLossWin;
			b.TimeTriggerPoint = e0.TimeTriggerPoint;
			b.TriggedTime = e0.TriggedTime;
			
			
			b.name=e0.CommodityNo+e0.ContractNo;
			b.status00 = (function(){
					if(e0.Status==0){
						return '运行中';
					}else if(e0.Status==1){
						return '暂停';
					}else if(e0.Status==2){
						return '已触发';
					}else if(e0.Status==3){
						return '已取消';
					}else if(e0.Status==4){
						return '插入失败';
					}else if(e0.Status==5){
						return '触发失败';
					}
				})();
			b.type = (function(){
					if(e0.ConditionType==0){
						return '价格条件';
					}else if(e0.ConditionType==1){
						return '时间条件';
					}else if(e0.ConditionType==2){
						return 'AB单';
					}
				})();	
			b.conditions = (function(){
					
					if(e0.AdditionFlag==0){ //没有附件条件
						if(e0.CompareType==0){
							return '>'+e0.PriceTriggerPonit;
						}else if(e0.CompareType==1){
							return '<'+e0.PriceTriggerPonit;
						}else if(e0.CompareType==2){
							return '>='+e0.PriceTriggerPonit;
						}else if(e0.CompareType==3){
							return '<='+e0.PriceTriggerPonit;
						}else{
							let s = e0.TimeTriggerPoint.split(' ');
							if(e0.AdditionType==0){
								return s[1]+' >'+e0.AdditionPrice;
							}else if(e0.AdditionType==1){
								return s[1]+' <'+e0.AdditionPrice;
							}else if(e0.AdditionType==2){
								return s[1]+' >='+e0.AdditionPrice;
							}else if(e0.AdditionType==3){
								return s[1]+' <='+e0.AdditionPrice;
							}else{
								return s[1];
							}
						}
					}else{ //有附加条件
						if(e0.CompareType==0){
							if(e0.AdditionType==0){
								return '>'+e0.PriceTriggerPonit+' >'+e0.AdditionPrice;
							}else if(e0.AdditionType==1){
								return '>'+e0.PriceTriggerPonit+' <'+e0.AdditionPrice;
							}else if(e0.AdditionType==2){
								return '>'+e0.PriceTriggerPonit+' >='+e0.AdditionPrice;
							}else if(e0.AdditionType==3){
								return '>'+e0.PriceTriggerPonit+' <='+e0.AdditionPrice;
							}
						}else if(e0.CompareType==1){
							if(e0.AdditionType==0){
								return '<'+e0.PriceTriggerPonit+' >'+e0.AdditionPrice;
							}else if(e0.AdditionType==1){
								return '<'+e0.PriceTriggerPonit+' <'+e0.AdditionPrice;
							}else if(e0.AdditionType==2){
								return '<'+e0.PriceTriggerPonit+' >='+e0.AdditionPrice;
							}else if(e0.AdditionType==3){
								return '<'+e0.PriceTriggerPonit+' <='+e0.AdditionPrice;
							}
						}else if(e0.CompareType==2){
							if(e0.AdditionType==0){
								return '>='+e0.PriceTriggerPonit+' >'+e0.AdditionPrice;
							}else if(e0.AdditionType==1){
								return '>='+e0.PriceTriggerPonit+' <'+e0.AdditionPrice;
							}else if(e0.AdditionType==2){
								return '>='+e0.PriceTriggerPonit+' >='+e0.AdditionPrice;
							}else if(e0.AdditionType==3){
								return '>='+e0.PriceTriggerPonit+' <='+e0.AdditionPrice;
							}
						}else if(e0.CompareType==3){
							if(e0.AdditionType==0){
								return '<='+e0.PriceTriggerPonit+' >'+e0.AdditionPrice;
							}else if(e0.AdditionType==1){
								return '<='+e0.PriceTriggerPonit+' <'+e0.AdditionPrice;
							}else if(e0.AdditionType==2){
								return '<='+e0.PriceTriggerPonit+' >='+e0.AdditionPrice;
							}else if(e0.AdditionType==3){
								return '<='+e0.PriceTriggerPonit+' <='+e0.AdditionPrice;
							}
						}else{
							let s = e0.TimeTriggerPoint.split(' ');
							if(e0.AdditionType==0){
								return s[1]+' >'+e0.AdditionPrice;
							}else if(e0.AdditionType==1){
								return s[1]+' <'+e0.AdditionPrice;
							}else if(e0.AdditionType==2){
								return s[1]+' >='+e0.AdditionPrice;
							}else if(e0.AdditionType==3){
								return s[1]+' <='+e0.AdditionPrice;
							}else{
								return s[1];
							}
						}
					}
				})();	
			b.order = (function(){
				if(e0.Drection == 0){ //买
					if(e0.OrderType==1){
						return '买,市价,'+e0.Num+'手'
					}else{
						return '买,对手价,'+e0.Num+'手'
					}
				} else if(e0.Drection == 1){//卖
					if(e0.OrderType==1){
						return '卖,市价,'+e0.Num+'手'
					}else{
						return '卖,对手价,'+e0.Num+'手'
					}
				}
				
				
			})();
			b.term = '当日有效';
			b.time = e0.InsertDateTime;	
			if(e0.Status<2){
				context.state.market.conditionList.push(parameters);
			}else{
				context.state.market.triggerConditionList.push(parameters);
			}
		},
		updateStopLoss:function(context,parameters){
			layer.msg('单号【'+ parameters.StopLossNo +'】,更新成功', {time: 1000});
			if(parameters.Status>2){
				context.state.market.stopLossTriggeredList.push(parameters);
				context.state.market.stopLossList.forEach(function(o, i){
					if(o.StopLossNo == parameters.StopLossNo){
						context.state.market.stopLossList.splice(i,1);
					}
				});
			}else if(parameters.Status==2){
				context.state.market.stopLossList.forEach(function(e,i){
					if(e.StopLossNo == parameters.StopLossNo){
						context.state.market.stopLossList.splice(i,1);
					}
				});
				context.state.market.stopLossTriggeredList.push(parameters);
			}else{
				let hasExist = false;
				context.state.market.stopLossList.forEach(function(e,i){
					if(e.StopLossNo==parameters.StopLossNo){
						hasExist = true;
						e.HoldDrection = parameters.HoldDrection;
						e.Num = parameters.Num;
						e.OrderType = parameters.OrderType;
						e.Status = parameters.Status;
						e.StopLossDiff = parameters.StopLossDiff;
						e.StopLossPrice = parameters.StopLossPrice;
						e.StopLossType = parameters.StopLossType;
						context.state.market.stopLossList.splice(i,1,e);
					}
				});
				if(hasExist == false){
					context.state.market.stopLossList.push(parameters);
				}
			}
		},
		layerOnRspInsertStopLoss:function(context,parameters){
			if(parameters.Status == 0){
				layer.msg('提交成功,单号【'+ parameters.StopLossNo +'】', {time: 2000});
			}else{
				layer.msg('提交失败,原因:【'+parameters.StatusMsg+'】', {time: 2000});
			}
			setTimeout(function(){
				context.state.market.stopStatus = false;
			}, 1000);
		},
		qryHisTrade:function(context){
			var date = new Date(); 
    		date.setDate(date.getDate()-1);
    		var year = date.getFullYear();
    		var day = date.getDate() > 9 ? date.getDate() : "0" + date.getDate();
    		var month = (date.getMonth() + 1) > 9 ? (date.getMonth() + 1) : "0"+ (date.getMonth() + 1);
    		var beginTime = year + '/' + month + '/' + day+' 00:00:00';
    		
    		var date00 = new Date(); 
    		date00.setDate(date00.getDate());
    		var year00 = date00.getFullYear();
    		var day00 = date00.getDate() > 9 ? date00.getDate() : "0" + date00.getDate();
    		var month00 = (date00.getMonth() + 1) > 9 ? (date00.getMonth() + 1) : "0"+ (date00.getMonth() + 1);
    		var endTime= year00 + '/' + month00 + '/' + day00+' 00:00:00';
//			context.state.tradeSocket.send('{"Method":"QryHisTrade","Parameters":{"ClientNo":"'+context.state.market.tradeConfig.username+'","BeginTime":"'+beginTime+'","EndTime":"'+endTime+'"}}');
			context.state.tradeSocket.send('{"Method":"QryHisTrade","Parameters":{"ClientNo":"'+ JSON.parse(localStorage.tradeUser).username +'","BeginTime":"'+beginTime+'","EndTime":"'+endTime+'"}}');
		},
		layerOnRtnOrderTraded:function(context,parameters){
			if(parameters!=null){
				var CommodityName =context.state.market.orderTemplist[parameters.CommodityNo].CommodityName;
				var DirectionStr;
				if(parameters.Drection==0){
					DirectionStr='买';
				}
				if(parameters.Drection==1){
					DirectionStr='卖';
				}
				var TradeNum = parameters.TradeNum;
				var TradeNo = parameters.TradeNo;
				var TradePrice = parseFloat(parameters.TradePrice).toFixed(context.state.market.orderTemplist[parameters.CommodityNo].DotSize);
				layer.msg("成交（"+CommodityName+",价格:"+TradePrice+","+DirectionStr+TradeNum+"手）", {time: 1000});
			}
		},
		layerMessage:function(context,parameters){
			if(parameters!=null){
				if(parameters.OrderStatus == 5){
					layer.msg(parameters.StatusMsg, {time: 1000});
					setTimeout(function(){
						context.state.market.buyStatus = false;
					}, 1000);
					return;
				}
				var CommodityName = context.state.market.orderTemplist[parameters.CommodityNo].CommodityName;
				var DirectionStr;
				if(parameters.Drection == 0){
					DirectionStr='买';
				}
				if(parameters.Drection == 1){
					DirectionStr='卖';
				}
				var price;
				if(parameters.OrderPriceType == 1){
					price = '市价';
				}
				if(parameters.OrderPriceType == 0){
					price = parseFloat(parameters.OrderPrice).toFixed(context.state.market.orderTemplist[parameters.CommodityNo].DotSize);
				}
				var OrderNum = parameters.OrderNum;
				var OrderID = parameters.OrderID;
				
				if(parameters.OrderStatus < 4){
					layer.msg('委托成功（'+CommodityName+','+price+','+DirectionStr+OrderNum+'手,委托号:'+OrderID+'）', {time: 1000});
				}else{
					layer.msg('委托失败（'+CommodityName+','+price+','+DirectionStr+OrderNum+'手,失败原因:'+parameters.StatusMsg+'）', {time: 1000});
				}
				setTimeout(function(){
					context.state.market.buyStatus = false;
				}, 1000);
				
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
			if(parameters.OrderStatus < 3 ){
//				context.state.market.OnRspOrderInsertOrderListCont.push(parameters);
				if(isExist==true){
					currentObj.delegatePrice = parameters.OrderPrice;
					currentObj.delegateNum = parameters.OrderNum;
					currentObj.ApplyOrderNum = parameters.OrderNum- parameters.TradeNum;
					context.state.market.orderListCont.splice(index,1,currentObj);
					context.state.market.OnRspOrderInsertOrderListCont[context.state.market.OnRspOrderInsertOrderListCont.length-index-1].OrderPrice
						= parameters.OrderPrice;
					context.state.market.OnRspOrderInsertOrderListCont[context.state.market.OnRspOrderInsertOrderListCont.length-index-1].OrderNum
						= parameters.OrderNum;
					layer.msg(parameters.StatusMsg + ':合约【'+ parameters.ContractCode +'】,订单号:【'+ parameters.OrderID +'】', {time: 1000});
					setTimeout(function(){
						context.state.market.cancelStatus = false;
					}, 1000);
				}
			}else if(parameters.OrderStatus == 6){
				return true;
			}else{
//				if(isExist==true){
					context.state.market.orderListCont.splice(index,1);
					context.state.market.OnRspOrderInsertOrderListCont.splice(context.state.market.OnRspOrderInsertOrderListCont.length-index-1,1);
					layer.msg(parameters.StatusMsg + ':合约【'+ parameters.ContractCode +'】,订单号:【'+ parameters.OrderID +'】', {time: 1000});
					setTimeout(function(){
						context.state.market.cancelStatus = false;
					}, 1000);
//				}
			}
		},
		appendApply:function(context,parameters){
			if(parameters.OrderStatus < 3 ) { // 订单已提交、排队中、部分成交 显示到挂单列表
				context.state.market.OnRspOrderInsertOrderListCont.push(parameters);
			}
		},
		appendOrder00:function(context,parameters){
			context.state.market.OnRspOrderInsertEntrustCont.unshift(parameters);
		},
		appendOrder:function(context,parameters){
			context.state.market.OnRspOrderInsertEntrustCont.push(parameters);
			
		},
		updateOrder:function(context,parameters){
			context.state.market.OnRspOrderInsertEntrustCont.forEach(function(e,i){
				if(e.OrderID == parameters.OrderID){
					parameters['Drection'] = e.Drection;	
					parameters['OrderPriceType']=e.OrderPriceType;
					context.state.market.OnRspOrderInsertEntrustCont.splice(i,1,parameters);
				}
			});
		},
		//更新持仓
		updateHold:function(context,parameters){
			var isExist = false;
			var positionListContCurrent = null;
			var positionListContCurrentIndex = 0;
			context.state.market.positionListCont.forEach(function(e,i){
				if(e.commodityNocontractNo==parameters.ContractCode){
					positionListContCurrent = e;
					positionListContCurrentIndex = i;
					isExist = true;
				}
			});
			if(isExist==false){
				var obj={};
				obj.CommodityName=context.state.market.orderTemplist[parameters.CommodityNo].CommodityName;
				obj.type=function(){
					if(parameters.Drection==0){
						return '多'
					}else{
						return '空'
					}
				}();
				obj.HoldNum = parameters.HoldNum;
				obj.price = parameters.HoldAvgPrice.toFixed(context.state.market.orderTemplist[parameters.CommodityNo].DotSize);
				obj.total = 0;
				obj.showbar = false;
				obj.type_color=function(){
					if(parameters.Drection==0){
						return 'red'
					}else{
						return 'green'
					}
				}();
				obj.total_color='green';
				obj.commodityNocontractNo = context.state.market.orderTemplist[parameters.CommodityNo].LastQuotation.CommodityNo
											+ context.state.market.orderTemplist[parameters.CommodityNo].LastQuotation.ContractNo;
				if(parameters.HoldNum!=0){
					context.state.market.positionListCont.unshift(obj);	
					context.state.market.qryHoldTotalArr.push(parameters);
				}
			}
			if(isExist==true){
				if(parameters.HoldNum != 0){
					positionListContCurrent.HoldNum=parameters.HoldNum;
					if(parameters.Drection==0){
						 positionListContCurrent.type ='多';
						 positionListContCurrent.type_color='red';
					}
					if(parameters.Drection==1){
						 positionListContCurrent.type='空';
						  positionListContCurrent.type_color='green';
					}
					positionListContCurrent.price = parseFloat(parameters.OpenAvgPrice).toFixed(context.state.market.orderTemplist[parameters.CommodityNo].DotSize);
					context.state.market.positionListCont.splice(positionListContCurrentIndex,1,positionListContCurrent);
					context.state.market.qryHoldTotalArr[context.state.market.qryHoldTotalArr.length-1-positionListContCurrentIndex] = parameters;
				}else{
					context.state.market.positionListCont.splice(positionListContCurrentIndex,1);
					context.state.market.qryHoldTotalArr.splice(context.state.market.qryHoldTotalArr.length-1-positionListContCurrentIndex,1);
					//如果没有持仓 持仓盈亏归0
					if(context.state.market.positionListCont.length == 0){
						let jCacheAccount = context.state.market.CacheAccount.jCacheAccount;
						for(let i in jCacheAccount){
							jCacheAccount[i].FloatingProfit = 0;
						}
					}
					context.state.market.CacheHoldFloatingProfit.jHoldFloatingProfit[parameters.CommodityNo+parameters.ContractNo] 
							= {"currencyNo" : context.state.market.orderTemplist[parameters.CommodityNo].CurrencyNo, "floatingProfit": 0};
				}
			}
		},
		updateAccountFloatingProfit:function(context,parameters){
			if(context.state.market.ifUpdateAccountProfit){
				//遍历持仓浮盈，根据币种合并逐笔浮盈
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
								= parseFloat(tmp1+tmp2).toFixed(2);
						}
					}
				}
				// 根据币种更新资金账户：逐笔浮盈、逐笔浮盈率、当前权益、可用资金
				for (var currencyNo in context.state.market.CacheHoldFloatingProfit.jCurrencyNoFloatingProfit){
					var floatingProfit = parseFloat(context.state.market.CacheHoldFloatingProfit.jCurrencyNoFloatingProfit[currencyNo]);
					var todayAmount = parseFloat(context.state.market.CacheAccount.jCacheAccount[currencyNo].TodayAmount);
					// 当前权益 = 当前结存 + 逐笔浮盈
					var todayBalance = todayAmount + floatingProfit;
					// 可用资金 = 当前权益 - 保证金 - 冻结资金
					var deposit =parseFloat(context.state.market.CacheAccount.jCacheAccount[currencyNo].Deposit);
					var frozenMoney =parseFloat(context.state.market.CacheAccount.jCacheAccount[currencyNo].FrozenMoney)
					var todayCanUse = todayBalance - deposit - frozenMoney;
					context.state.market.CacheAccount.jCacheAccount[currencyNo].FloatingProfit=floatingProfit;
					context.state.market.CacheAccount.jCacheAccount[currencyNo].TodayBalance=todayBalance;
					context.state.market.CacheAccount.jCacheAccount[currencyNo].TodayCanUse=todayCanUse;
					context.state.market.CacheAccount.moneyDetail.forEach(function(e,i){
						if(e.AccountNo == currencyNo){
							e.FloatingProfit = floatingProfit;
							e.TodayBalance = todayBalance;
							e.TodayCanUse = todayCanUse;
							context.state.market.CacheAccount.moneyDetail.splice(i,1,e);
						}
					});
				}
				//清空币种盈亏
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
			if(parameters != null){
				context.state.market.CacheAccount.jCacheAccount[parameters.AccountNo].FloatingProfit = parameters.floatingProfit;
				context.state.market.CacheAccount.moneyDetail.forEach(function(o, i){
					if(o.AccountNo == parameters.AccountNo){
						context.state.market.CacheAccount.floating = true;
						o.FloatingProfit = null;
						o.FloatingProfit = parameters.floatingProfit;
						context.state.market.CacheAccount.moneyDetail.splice(i,1,o);
					}
				});
//				context.state.market.CacheAccount.moneyDetail[parameters.AccountNo].FloatingProfit = parameters.floatingProfit;
			}
			var obj = context.state.market.CacheAccount.jCacheAccount;
			if (obj == null || typeof(obj) == "undefined" || obj.length == 0) {
				return;
			}
//			var jCacheAccount = context.state.market.CacheAccount.jCacheAccount;
			context.state.market.CacheAccount.jCacheTotalAccount.FloatingProfit=0.0;
			context.state.market.CacheAccount.jCacheTotalAccount.TodayBalance=0.0;
			context.state.market.CacheAccount.jCacheTotalAccount.TodayCanUse=0.0;
			context.state.market.CacheAccount.jCacheTotalAccount.CloseProfit=0.0;
			context.state.market.CacheAccount.jCacheTotalAccount.FrozenMoney=0.0;
			context.state.market.CacheAccount.jCacheTotalAccount.Deposit=0.0;
			context.state.market.CacheAccount.jCacheTotalAccount.CounterFee=0.0;
			context.state.market.CacheAccount.jCacheTotalAccount.RiskRate =0.0;
			for(var e in obj){
				// 逐笔浮盈：直接从当前资金账户获取
				var floatingProfit = obj[e].FloatingProfit;
				context.state.market.CacheAccount.jCacheTotalAccount.FloatingProfit += floatingProfit * obj[e].CurrencyRate;
				// 今权益 = 今结存 + 浮盈
				var tmpTodayBalance = obj[e].TodayAmount + floatingProfit;
				context.state.market.CacheAccount.jCacheTotalAccount.TodayBalance += tmpTodayBalance * obj[e].CurrencyRate;
				// 今可用=今权益-冻结资金-保证金
				context.state.market.CacheAccount.jCacheTotalAccount.TodayCanUse += (tmpTodayBalance - obj[e].FrozenMoney - obj[e].Deposit) * obj[e].CurrencyRate;
				// 平仓盈亏
				context.state.market.CacheAccount.jCacheTotalAccount.CloseProfit += obj[e].CloseProfit * obj[e].CurrencyRate;
				// 冻结资金
				context.state.market.CacheAccount.jCacheTotalAccount.FrozenMoney += obj[e].FrozenMoney * obj[e].CurrencyRate;
				// 保证金
				context.state.market.CacheAccount.jCacheTotalAccount.Deposit += obj[e].Deposit * obj[e].CurrencyRate;
				// 手续费
				context.state.market.CacheAccount.jCacheTotalAccount.CounterFee += obj[e].CounterFee * obj[e].CurrencyRate;
			}
			// 风险率 = 保证金 / 今权益 * 100%
			context.state.market.CacheAccount.jCacheTotalAccount.RiskRate 
				= context.state.market.CacheAccount.jCacheTotalAccount.Deposit / context.state.market.CacheAccount.jCacheTotalAccount.TodayBalance / 100;
			//风险度 =（初始资金 - 今权益）/（初始资金 - 强平线）
			context.state.market.CacheAccount.jCacheTotalAccount.RiskDegree =  (context.state.market.initBalance - context.state.market.CacheAccount.jCacheTotalAccount.TodayBalance) / (context.state.market.initBalance - context.state.market.forceLine) * 100;
			if(context.state.market.CacheAccount.jCacheTotalAccount.RiskDegree < 0){
				context.state.market.CacheAccount.jCacheTotalAccount.RiskDegree = 0;
			}
		},
		initCacheAccount:function(context,parameters){
			if(parameters!=null){
				context.state.market.CacheAccount.jCacheAccount[parameters.CurrencyNo] = parameters;
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
						var	tmpFloatingProfit = parseFloat(diff * mult * currentPositionListContObj.HoldNum).toFixed(2);
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
//			console.log('根据订阅行情初始化持仓盈亏');
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
			var currentPositionListContObj = null;
			var currentPositionListContObjIndex = 0;
			var isExist00 = false;
			context.state.market.positionListCont.forEach(function(e,i){
				if(e.name==CommodityName){
					isExist00 = true;
					currentPositionListContObj = e;
					currentPositionListContObjIndex=i;
				}
			});
			if(isExist00 == true){
				currentPositionListContObj.total = floatingProfit;
				if(tmpFloatingProfit >= 0){
					currentPositionListContObj.total_color = 'red';
				}else{
					currentPositionListContObj.total_color = 'green';
				}
				context.state.market.positionListCont.splice(currentPositionListContObjIndex,1,currentPositionListContObj);
			}
		},
		HeartBeatTimingCheck: function(context){
			context.state.market.HeartBeatTiming = setInterval(heartBeatUpdate,15000);	
			function heartBeatUpdate(){
				if(context.state.market.HeartBeat.lastHeartBeatTimestamp == context.state.market.HeartBeat.oldHeartBeatTimestamp){
					if(localStorage.tradeUser == undefined || localStorage.tradeUser == ''){
						clearInterval(context.state.market.HeartBeatTiming);
						return;
					}
					if(context.state.isshow.warningShow == true) return;
					context.state.isshow.warningType = 2;
					context.state.isshow.warningShow = true;
					sessionStorage.tradeStatus = false;
					console.log('交易服务器断开，正在重连');
				}else{
					context.state.market.HeartBeat.oldHeartBeatTimestamp = context.state.market.HeartBeat.lastHeartBeatTimestamp; // 更新上次心跳时间
				}
			}
//			heartBeatUpdate();
		},
		//初始化交易
		initTrade: function(context){
			if(localStorage.tradeUser != '' && localStorage.tradeUser != undefined || context.state.market.tradeConfig.username != '' && context.state.market.tradeConfig.username != undefined){
				context.state.tradeSocket = new WebSocket(context.state.market.tradeConfig.url_real);
				context.state.tradeSocket.onopen = function(evt){
					//登录
//					console.log(JSON.parse(localStorage.tradeUser).password);
					if(context.state.tradeSocket.readyState == 1){ //连接已建立，可以进行通信。
						if(localStorage.tradeUser != '' && localStorage.tradeUser != undefined){
							context.state.tradeSocket.send('{"Method":"Login","Parameters":{"ClientNo":"'+JSON.parse(localStorage.tradeUser).username+'","PassWord":"'+JSON.parse(localStorage.tradeUser).password+'","IsMock":'+context.state.market.tradeConfig.model+',"Version":"'+context.state.market.tradeConfig.version+'","Source":"'+context.state.market.tradeConfig.client_source+'"}}');
						}else{
							context.state.tradeSocket.send('{"Method":"Login","Parameters":{"ClientNo":"'+context.state.market.tradeConfig.username+'","PassWord":"'+context.state.market.tradeConfig.password+'","IsMock":'+context.state.market.tradeConfig.model+',"Version":"'+context.state.market.tradeConfig.version+'","Source":"'+context.state.market.tradeConfig.client_source+'"}}');
						}
					}
				};
				context.state.tradeSocket.onclose = function(evt) {
					console.log('tradeClose:');
					clearInterval(context.state.market.HeartBeatTiming);
					if(context.state.account.loginStatus == true){
						context.state.isshow.warningType = 2;
						context.state.isshow.warningShow = true;
						sessionStorage.tradeStatus = false;
					}else{
						if(sessionStorage.tradeStatus == 'false'){
							layer.msg('交易连接失败', {time: 1500});
							localStorage.removeItem('tradeUser');
							context.state.market.tradeConfig.username = '';
							context.state.market.tradeConfig.password = '';
						}
					}
				};
				context.state.tradeSocket.onerror = function(evt) {
					console.log('tradeError');
				};
				context.state.tradeSocket.onmessage = function(evt) {
					context.dispatch('handleTradeMessage',evt);
				};
			}
		},
		//初始化行情
		initQuoteClient: function(context) {
			context.state.quoteSocket = new WebSocket(context.state.market.quoteConfig.url_real);
			context.state.quoteSocket.onopen = function(evt) {
//				console.log('open');
				context.state.quoteSocket.send('{"Method":"Login","Parameters":{"UserName":"'+context.state.market.quoteConfig.userName+'","PassWord":"'+context.state.market.quoteConfig.passWord+'"}}');
			};
			context.state.quoteSocket.onclose = function(evt) {
				console.log('close');
				context.state.isshow.warningType = 1;
				context.state.isshow.warningShow = true;
				sessionStorage.quoteStatus = false;
			};
			context.state.quoteSocket.onerror = function(evt) {
//				console.log('error');
			};
			context.state.quoteSocket.onmessage = function(evt) {
//				console.log('message');
				context.state.wsjsondata = JSON.parse(evt.data);
				if(context.state.wsjsondata.Method == "OnRspLogin") { // 登录行情服务器
					layer.msg('行情服务器连接成功',{time: 1000});
					sessionStorage.quoteStatus = true;
					// 查询服务器支持品种用于订阅
					context.state.quoteSocket.send('{"Method":"QryCommodity","Parameters":{' + null + '}}');
				} else if(context.state.wsjsondata.Method == "OnRspQryCommodity") { // 行情服务器支持的品种
					// 行情服务器支持的品种
					context.state.market.markettemp = JSON.parse(evt.data).Parameters;
					context.state.market.markettemp.forEach(function(e) {
						var key = e.CommodityNo;
						context.state.market.orderTemplist[key] = e;
						if(e.IsUsed != 0) {
							context.state.quoteSocket.send('{"Method":"Subscribe","Parameters":{"ExchangeNo":"' + e.ExchangeNo + '","CommodityNo":"' + e.CommodityNo + '","ContractNo":"' + e.MainContract + '"}}');
						}
					});
				} else if(context.state.wsjsondata.Method == "OnRspSubscribe") { // 订阅成功信息
					var key = JSON.parse(evt.data).Parameters.CommodityNo;
					context.state.market.templateList[key]=JSON.parse(evt.data).Parameters;
					var dealDetails = {CommodityNo: '', data: []}, _dealDetails = {};
					context.state.market.markettemp.forEach(function(e) {
//						if(e.IsUsed != 0){
							if(e.CommodityNo == key) {
								e.LastQuotation = JSON.parse(evt.data).Parameters.LastQuotation;
								context.state.market.Parameters.push(e);
								//订阅成功  成交信息
								_dealDetails['time'] = e.LastQuotation.DateTimeStamp.split(' ')[1];
								_dealDetails['price'] = e.LastQuotation.LastPrice;
								_dealDetails['volume'] = e.LastQuotation.LastVolume;
								_dealDetails['_price'] = e.LastQuotation.PreSettlePrice;
								_dealDetails['dotSize'] = e.DotSize;
								dealDetails.CommodityNo = e.CommodityNo;
								dealDetails.data.push(_dealDetails);
								context.state.market.tradeParameters.push(dealDetails); 
							}
//						}
					});
					context.state.market.quoteInitStep = true;
					if(context.state.market.subscribeIndex == 1){
						//初始化交易
						context.dispatch('initTrade');
					}
					context.state.market.subscribeIndex++;
				} else if(context.state.wsjsondata.Method == "OnRtnQuote") { // 最新行情
					var val = JSON.parse(evt.data).Parameters;
					var key = JSON.parse(evt.data).Parameters.CommodityNo;
					context.state.market.Parameters.forEach(function(a, r) {
						if(a.CommodityNo == key){
							if(JSON.parse(evt.data).Parameters.LastPrice > a.LastQuotation.LastPrice){
								context.state.market.quoteIndex = r;   //行情变颜色
								context.state.market.quoteColor = 'red';
							}else if(JSON.parse(evt.data).Parameters.LastPrice < a.LastQuotation.LastPrice){
								context.state.market.quoteIndex = r;   //行情变颜色
								context.state.market.quoteColor = 'green';
							}
						}
					});
					//合约成交明细
					var _dealDetails00 = {};
					context.state.market.tradeParameters.forEach(function(o, i){
						if(key == o.CommodityNo){
							_dealDetails00['time'] = val.DateTimeStamp.split(' ')[1];
							_dealDetails00['price'] = val.LastPrice;
							_dealDetails00['volume'] = val.LastVolume;
							_dealDetails00['_price'] = val.PreSettlePrice;
							_dealDetails00['dotSize'] = context.state.market.orderTemplist[key].DotSize;
							if(o.data.length >= 10){
								o.data.shift();
							}
							o.data.push(_dealDetails00);
						}
					});
					//更新数据
					context.state.market.templateList[key] = JSON.parse(evt.data).Parameters;
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
								context.state.market.CacheLastQuote.push(JSON.parse(evt.data).Parameters);
								if(context.state.market.CacheLastQuote.length > 2){
									context.state.market.CacheLastQuote.shift();
								}
								//更新分时图
								if(context.state.isshow.isfensshow == true && context.state.isshow.isfens == true) {
									var arr = [], arr1, arr2, arr3, arr4;
									context.state.market.charttimetime = new Date();
									context.state.market.charttimems = context.state.market.charttimetime.getTime();
									context.state.market.charttime = context.state.market.charttimems - context.state.market.charttimems2;
									if(context.state.market.charttime >= 1000 || context.state.market.charttimetemp >= 1000) {
										arr = [];
										arr[0] = JSON.parse(evt.data).Parameters.DateTimeStamp;
										arr[1] = JSON.parse(evt.data).Parameters.LastPrice;
										arr[2] = JSON.parse(evt.data).Parameters.OpenPrice;
										arr[3] = JSON.parse(evt.data).Parameters.LowPrice;
										arr[4] = JSON.parse(evt.data).Parameters.HighPrice;
										arr[5] = JSON.parse(evt.data).Parameters.Position;
										arr[6] = JSON.parse(evt.data).Parameters.LastVolume;
										arr1 = JSON.parse(evt.data).Parameters.DateTimeStamp.split(' ');
										arr2 = arr1[1].split(':'); //最新时间
										arr3 = context.state.market.jsonData.Parameters.Data[context.state.market.jsonData.Parameters.Data.length - 1][0].split(' ');
										arr4 = arr3[1].split(':'); //历史时间
										if(arr2[1] == arr4[1]) {
											var time = context.state.market.jsonData.Parameters.Data[context.state.market.jsonData.Parameters.Data.length - 1][0];
											var vol = parseInt(context.state.market.jsonData.Parameters.Data[context.state.market.jsonData.Parameters.Data.length - 1][6]) + parseInt(arr[6]);
											context.state.market.jsonData.Parameters.Data[context.state.market.jsonData.Parameters.Data.length - 1] = arr;
											context.state.market.jsonData.Parameters.Data[context.state.market.jsonData.Parameters.Data.length - 1][0] = time;
											context.state.market.jsonData.Parameters.Data[context.state.market.jsonData.Parameters.Data.length - 1][6] = vol;
										}else{
//											context.state.market.jsonData.Parameters.Data.shift();
											context.state.market.jsonData.Parameters.Data.push(arr);
										}
										context.commit('setfensoption');
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
								//更新闪电图
								if(context.state.isshow.islightshow == true && context.state.isshow.islight == true) {
									context.state.market.jsonTow = JSON.parse(evt.data);
									context.commit('setlightDate');
									context.commit('drawlight', 'light');
								}
								//更新K线图
								if(context.state.isshow.isklineshow == true && context.state.isshow.iskline == true) {
									if(context.state.market.CacheLastQuote[1].TotalVolume <= context.state.market.CacheLastQuote[0].TotalVolume){
										return;
									}
									var arr = [];
									arr[0] = JSON.parse(evt.data).Parameters.DateTimeStamp;
									arr[1] = JSON.parse(evt.data).Parameters.LastPrice;
									arr[2] = JSON.parse(evt.data).Parameters.OpenPrice;
									arr[3] = JSON.parse(evt.data).Parameters.LowPrice;
									arr[4] = JSON.parse(evt.data).Parameters.HighPrice;
									arr[5] = JSON.parse(evt.data).Parameters.Position;
									context.state.market.volume+=JSON.parse(evt.data).Parameters.LastVolume;
									arr[6] = context.state.market.volume;
									
									var arr1 = JSON.parse(evt.data).Parameters.DateTimeStamp.split(' '); //得到的时间
									//["20", "47", "38"]
									var arr2 = arr1[1].split(':'); //得到的数据
									var arr3 = context.state.market.jsonDataKline.Parameters.Data[context.state.market.jsonDataKline.Parameters.Data.length - 1][0].split(' ');
									//["20", "48", "00"]
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
											arr[6] = context.state.market.volume;
											var length = context.state.market.jsonDataKline.Parameters.Data.length;
											context.state.market.jsonDataKline.Parameters.Data.splice(length-1,1,arr);
										} else{
											var arrTemp = [];
											context.state.market.jsonDataKline.Parameters.Data.shift();
											context.state.market.volume = 0;
											arrTemp[0] = arr[0].substring(0, arr[0].length - 2) + '00';
											arrTemp[1] = arr[1];
											arrTemp[2] = arr[1];
											arrTemp[3] = arr[1];
											arrTemp[4] = arr[1];
											arrTemp[5] = arr[5];
											arrTemp[6] = arr[6];
											arr = arrTemp;
											context.state.market.jsonDataKline.Parameters.Data.push(arrTemp);
										}
									}else if(context.state.market.selectTime == 5){
										if(arr2[1]%5 != 0) {
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
											//arr[6] = context.state.market.jsonDataKline.Parameters.Data[context.state.market.jsonDataKline.Parameters.Data.length - 1][6];
											arr[6] = context.state.market.volume;
											var length = context.state.market.jsonDataKline.Parameters.Data.length;
											context.state.market.jsonDataKline.Parameters.Data.splice(length-1,1,arr);
											//context.state.market.jsonDataKline.Parameters.Data[context.state.market.jsonDataKline.Parameters.Data.length - 1] = arr;
										} else if(arr2[1]%5 == 0 && arr2[2]=='00'){
											var arrTemp = [];
											context.state.market.jsonDataKline.Parameters.Data.shift();
											context.state.market.volume=0;
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
										if(arr2[1]%15 != 0) {
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
											//arr[6] = context.state.market.jsonDataKline.Parameters.Data[context.state.market.jsonDataKline.Parameters.Data.length - 1][6];
											//context.state.market.jsonDataKline.Parameters.Data[context.state.market.jsonDataKline.Parameters.Data.length - 1] = arr;
											arr[6] = context.state.market.volume;
											var length = context.state.market.jsonDataKline.Parameters.Data.length;
											context.state.market.jsonDataKline.Parameters.Data.splice(length-1,1,arr);
										} else if(arr2[1]%15 == 0 && arr2[2]=='00'){
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
											context.state.market.volume=0;
											context.state.market.jsonDataKline.Parameters.Data.push(arr);
										}
									}else if(context.state.market.selectTime == 30){
										if(arr2[1]%30 != 0) {
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
											//arr[6] = context.state.market.jsonDataKline.Parameters.Data[context.state.market.jsonDataKline.Parameters.Data.length - 1][6];
											//context.state.market.jsonDataKline.Parameters.Data[context.state.market.jsonDataKline.Parameters.Data.length - 1] = arr;
											arr[6] = context.state.market.volume;
											var length = context.state.market.jsonDataKline.Parameters.Data.length;
											context.state.market.jsonDataKline.Parameters.Data.splice(length-1,1,arr);
										} else if(arr2[1]%30 == 0 && arr2[2]=='00'){
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
											context.state.market.volume = 0;
											context.state.market.jsonDataKline.Parameters.Data.push(arr);
										}
									}else if(context.state.market.selectTime == 1440){
										if(arr2[1]=='00' && arr2[2]=='00') {
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
											//arr[6] = context.state.market.jsonDataKline.Parameters.Data[context.state.market.jsonDataKline.Parameters.Data.length - 1][6];
											//context.state.market.jsonDataKline.Parameters.Data[context.state.market.jsonDataKline.Parameters.Data.length - 1] = arr;
											arr[6] = context.state.market.volume;
											var length = context.state.market.jsonDataKline.Parameters.Data.length;
											context.state.market.jsonDataKline.Parameters.Data.splice(length-1,1,arr);
										} else if(arr2[0]=='00' && arr2[1]=='00' && arr2[2]=='00'){
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
											context.state.market.volume = 0;
											context.state.market.jsonDataKline.Parameters.Data.push(arr);
										}
									}
									context.commit('setklineoption');
									context.commit('drawkline', {
										id1: 'kliness',
										id2: 'kliness_volume'
									});
								}
							}
						}
					});
					//更新持仓盈亏
					context.dispatch('UpdateHoldProfit',JSON.parse(evt.data).Parameters);
				} else if(context.state.wsjsondata.Method == "OnRspQryHistory") { // 历史行情
					let data = JSON.parse(evt.data);
					if(data.Parameters.HisQuoteType == 0){
						context.state.market.jsonData = data;
						if(context.state.isshow.isfens == true){
							context.commit('setfensoption');
							context.commit('drawfens', {
								id1: 'fens',
								id2: 'volume'
							});
						}
					}else{
						context.state.market.jsonDataKline = data;
						if(context.state.isshow.iskline == true){
							context.commit('setklineoption');
							context.commit('drawkline', {
								id1: 'kliness',
								id2: 'kliness_volume'
							});
						}
					}
				}
			}
		},
	}
})