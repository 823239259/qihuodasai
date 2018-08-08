import Vue from 'vue'
import Vuex from 'vuex'
import Isshow from './isshowStore'
import Account from './accountStore'
import Market from './MarketStore'
// 引入 ECharts 主模块
var echarts = require('echarts/lib/echarts');
// 引入柱状图
require('echarts/lib/chart/bar');
require('echarts/lib/chart/line');
require('echarts/lib/component/tooltip');
require('echarts/lib/chart/candlestick');
Vue.use(Vuex)

const isshow = new Isshow();
const account = new Account();
const market = new Market();

const mTimeExg = /[' '|'.']/g
export default new Vuex.Store({
	modules: {
		isshow,
		market,
		account
	},
	state: {
		//test 测试环境，online 正式环境
		environment: 'online',
		//打包的时候，值为 build ，开发的时候，值为 dev
		setting: 'build',
		//请求的操盘参数数据
		tempTradeapply: {},
		quoteSocket: {},
		tradeSocket: null,
		webuser: {
			username: '',
			password: ''
		},
		wsjsondata: {},
		//连接提示语
		wsmsg: '',
		version: {}
		
	},
	getters: {
		PATH: function(state) {
			if(state.setting == 'dev') {
				return '/api';
			} else if(state.setting == 'build') {
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
			let { lightChartTime,jsonTow, currentdetail } = state.market;
			
			lightChartTime.price.push(jsonTow.last.toFixed(currentdetail.dot_size));
			lightChartTime.time.push((jsonTow.time_flag).split(mTimeExg)[1]);
			lightChartTime.time = lightChartTime.time.slice(-50);
			lightChartTime.price = lightChartTime.price.slice(-50)
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
				xAxis: [{
					type: 'category',
					show: true,
					//data: state.market.lightChartTime.time,
					data: lightChartTime.time,
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
				series: [{
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
					"data": lightChartTime.price
				}]
			}
		},
		setklineoption: function(state) {
			//			console.time('e');
			let {jsonDataKline:{Parameters}, currentdetail} = state.market
			var rawData;
			var dosizeL = currentdetail.DotSize;
			var parameters = Parameters.Data;
			//HisQuoteType 1440 为日K 划线
			if(Parameters.HisQuoteType == 1440) {
				rawData = parameters.reduce((arr1,parameter,index) => {
					let timeStr = parameter[0].split(" ")[0];
					let closePrice = parameter[1].toFixed(dosizeL);
					let openPrice = parameter[2].toFixed(dosizeL);
					let lowPrice =  parameter[3].toFixed(dosizeL);
					let highPrice = parameter[4].toFixed(dosizeL);
					let sgData = [timeStr, openPrice, closePrice, lowPrice, highPrice, parameter[2]] //[时间, 开, 关, 低, 高, ?]
					arr1.push(sgData)
					return arr1
				},[])
			} else {
				rawData = parameters.reduce((arr1,parameter,index) => {
					let time2 = parameter[0].split(" ");
					let str1 = time2[1].split(":");
					let str2 = str1[0] + ":" + str1[1];
					let closePrice = parameter[1].toFixed(dosizeL);
					let openPrice = parameter[2].toFixed(dosizeL);
					let lowPrice =  parameter[3].toFixed(dosizeL);
					let highPrice = parameter[4].toFixed(dosizeL);
					let sgData = [str2, openPrice, closePrice, lowPrice, highPrice, parameter[0]] //[时间, 开, 关, 低, 高, ?]
					arr1.push(sgData)
					return arr1
				},[])
			}
			let categoryData = [];
			let values = [];
			let time1 = [];
			let lastFortyArr = rawData.slice(-40);
			//处理最后40根数据
			lastFortyArr.forEach(lastFortyItem => {
				categoryData.push(lastFortyItem[0])
				values.push([lastFortyItem[1], lastFortyItem[2], lastFortyItem[3], lastFortyItem[4]])
				time1.push(lastFortyItem[5])

			})
			let chartDataC = {
				categoryData,
				values,
				time1
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
						var rate = (kd[2] - kd[1]) / kd[1] * 100;
						rate = parseFloat(rate).toFixed(2);
						var style = "display:inline-block;margin-right:5px;border-radius:10px;width:9px;height:9px;background-color:";
						let res = `时间:${params[0].name} 涨跌幅: ${rate}%
						<br/>开盘 : ${parseFloat(kd[1]).toFixed(dosizeL)} 最高 : ${parseFloat(kd[4]).toFixed(dosizeL)}
						<br/>收盘 : ${parseFloat(kd[2]).toFixed(dosizeL)} 最低 : ${parseFloat(kd[3]).toFixed(dosizeL)}
						<br/><span style="${style}#3689B3"></span> MA5 : ${ma5} <span style="${style}#B236B3"></span> MA10 : ${ma10}
						<br/><span style="${style}#B37436"></span> MA20 : ${ma20} <span style="${style}#B2B336"></span> MA30 : ${ma30}
						`
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
			var Ktime;
			let barData = Parameters.Data.slice(-40);
			// 取条形图的数据
			barData.forEach((parameter) =>{
				vol.push(parameter[6]);
				let timeArr = parameter[0].split(' ');
				let SecondTime = timeArr[1].split(':');
				Ktime = SecondTime[0] + ':'+SecondTime[1];
				if(Ktime == '00:00'){
					time.push(timeArr[0]);
				}else{
					time.push(Ktime);
				}
				price.push(parameter[1]);
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
			
			var kline, volume;
			if(state.isshow.isklineshow == false) {
				kline = echarts.init(document.getElementById(x.id1));
				volume = echarts.init(document.getElementById(x.id2));
				volume.group = 'group1';
				kline.group = 'group1';
				// 基于准备好的dom，初始化echarts实例
				echarts.connect("group1");
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
		drawfens: function(state, x) {
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
		},
		setfensoption: function(state) {
			let vol = [],
				price = [],
				time = [];
				
			let {jsonData, currentdetail} = state.market;	
			var dosizeL = currentdetail.dot_size;	
			if (!jsonData.data) return;
			jsonData.data.Lines.forEach(function(parameter) {
				let timeArr = parameter[0].split(mTimeExg)[1].split(':');
				vol.push(parameter[5]);
				time.push(timeArr[0] + ':' + timeArr[1]);
				price.push(parameter[1]);
				
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
					data: price
				}
			};
		},
		//保存真实姓名
		setRealName (state,payload) {
			state.account.realName = payload.realName
			state.account.userVerified = payload.userVerified
		},
		sendMessage (state, message) {
			let messageStr = typeof message === 'object'? JSON.stringify(message):message
			state.quoteSocket.send(messageStr)
		},
		sendTradeMessage (state, message) {
			let messageStr = typeof message === 'object'? JSON.stringify(message):message
			state.tradeSocket.send(messageStr)
		},
		updateQryHoldTotalArr(state, payload) {
			state.market.qryHoldTotalArr.push(payload)
		}
	},
	actions: {
		handleTradeMessage:function(context,evt){
			var data = JSON.parse(evt.data);
			var parameters = data.Parameters;
			let {state} = context
			let {market, tradeSocket} = state
			let {HeartBeat, tradeConfig} = market
			switch (data.method){
				case 'OnRtnHeartBeat': //处理行情心跳
					HeartBeat.lastHeartBeatTimestamp = parameters.Ref; // 更新心跳最新时间戳
//					console.log('lastHeartBeatTimestamp:'+context.state.market.HeartBeat.lastHeartBeatTimestamp);
					break;
				case 'OnRspLogin'://登录回复
					if(parameters.Code==0){ //code为0 代表成功
//						console.log('交易服务器连接成功');
						market.tradeLoginSuccessMsg='交易服务器连接成功';
						market.forceLine = parameters.ForceLine;
						// 查询持仓合计 查询订单 查询成交记录 查询账户信息 查询止损单 查询条件单 查询历史成交 QryHoldTotal
						const methodArr = ['QryHoldTotal', 'QryOrder', 'QryTrade', 'QryAccount', 'QryStopLoss', 'QryCondition']
						// todo 初始化的时候tradeConfig没有username
						methodArr.forEach(method => {
							tradeSocket.send(`{"method":"${method}","Parameters":{"ClientNo":"${JSON.parse(localStorage.tradeUser).username}"}}`);
							//tradeSocket.send(`{"method":"${method}","Parameters":{"ClientNo":"${tradeConfig.username}"}}`);
						})
						// 查询历史成交
						context.dispatch('qryHisTrade');
						//启动交易心跳定时检查
						context.dispatch('HeartBeatTimingCheck');
						
						
					}else{
//						console.log('登录失败');
						market.tradeLoginSuccessMsg=parameters.Message;
						tradeSocket.close();
						//清空本地交易登录信息
						localStorage.tradeUser = null;
					}
					break;
				case 'OnRspLogout': //登出回复
					if(parameters.Code==0){
//						console.log('登出成功');
						market.layer='登出成功'+Math.floor(Math.random()*10);
					}else{
//						console.log('登出失败');
						market.layer = parameters.Message+Math.floor(Math.random()*10);
						market.errorMsg = parameters.Message + Math.floor(Math.random()*10);
						localStorage.removeItem('tradeUser');
					}
					break;
				case 'OnRspQryHoldTotal': //查询持仓合计回复 数据是一条一条回推的
//					console.log('查询持仓合计回复');		
					if (parameters == null || typeof(parameters) == "undefined" || parameters.length == 0){
						market.ifUpdateHoldProfit=true;//可以使用最新行情更新持仓盈亏
					}else{
						//更新数据 qryHoldTotalArr 
						context.commit('updateQryHoldTotalArr',parameters) 
						//market.qryHoldTotalArr.push(parameters);
						market.qryHoldTotalKV[parameters.CommodityNo] = parameters;
						var qryItem = market.orderTemplist[parameters.CommodityNo];
						var positionListContItem = {
							name: qryItem.CommodityName,
							type: !parameters.Drection?'多':'空',
							num: parameters.HoldNum,
							price: parameters.HoldAvgPrice.toFixed(qryItem.DotSize),
							showbar: false,
							type_color: !parameters.Drection?'red':'green',
							commodityNocontractNo: qryItem.LastQuotation.CommodityNo + qryItem.LastQuotation.ContractNo,

						}
						market.positionListCont.unshift(positionListContItem);
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
					// 排队中委托单放入挂单列表
					context.dispatch('appendApply',parameters);
					break;
				case 'OnRtnHoldTotal':
//					console.log('持仓合计变化推送通知');
					context.dispatch('updateHold',parameters);
					break;
				case 'OnRtnOrderTraded':
//					console.log('成交单通知');
					if(parameters!=null){
//						context.state.market.OnRspQryTradeDealListCont.push(parameters);
						context.state.market.OnRspQryTradeDealListCont.unshift(parameters);
					}
					context.dispatch('layerOnRtnOrderTraded',parameters);
					break;
				
				case 'OnRspQryStopLoss':
					if(parameters!=null){
						if(parameters.Status==0||parameters.Status==1){
							context.state.market.stopLossList.push(parameters);
						}else if(parameters.Status==2||parameters.Status==3||parameters.Status==4||parameters.Status==5){
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
					if(parameters!=null){
						if(parameters.Status<2){
							context.state.market.conditionList.push(parameters);
						}else{
							context.state.market.triggerConditionList.push(parameters);
						}
					}
					break;
				case "OnRspInsertCondition":
//					console.log(parameters);
					if(parameters.Status==0){
						context.state.market.layer='设置条件单成功' + Math.floor(Math.random()*10);
					}else{
						context.state.market.layer='设置条件单失败，原因:【'+parameters.StatusMsg+'】' + Math.floor(Math.random()*10);
					}
					context.dispatch('dealWithOnRspInsertCondition',parameters);
					break;
				case 'OnRtnConditionState':
//					console.log(parameters);
					if(parameters.Status==0){
						context.state.market.layer='【'+parameters.CommodityNo+parameters.ContractNo+'】条件单【'+ parameters.ConditionNo+'】,改单后已运行' + Math.floor(Math.random()*10);
					}else if(parameters.Status==1){
						context.state.market.layer='【'+parameters.CommodityNo+parameters.ContractNo+'】条件单【'+ parameters.ConditionNo+'】,已暂停' + Math.floor(Math.random()*10);
					}else if(parameters.Status==2){
						context.state.market.layer='【'+parameters.CommodityNo+parameters.ContractNo+'】条件单【'+ parameters.ConditionNo+'】,已触发' + Math.floor(Math.random()*10);
					}else if(parameters.Status==3){
						context.state.market.layer='【'+parameters.CommodityNo+parameters.ContractNo+'】条件单【'+ parameters.ConditionNo+'】,已取消' + Math.floor(Math.random()*10);
					}else if(parameters.Status==4){
						context.state.market.layer='【'+parameters.CommodityNo+parameters.ContractNo+'】条件单【'+ parameters.ConditionNo+'】,插入失败' + Math.floor(Math.random()*10);
					}else if(parameters.Status==5){
						context.state.market.layer='【'+parameters.CommodityNo+parameters.ContractNo+'】条件单【'+ parameters.ConditionNo+'】,触发失败';
					}
					context.dispatch('dealWithOnRtnConditionState',parameters);
					
					break;	
				case 'OnError':
					if(parameters!=null){
						context.state.market.layer=parameters.Message + Math.floor(Math.random()*10);
					}
					break;
				default:
					break;
			}
		},
		dealWithOnRtnConditionState:function(context,parameters){
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
							
							if(parameters.Status<2){
//								context.state.market.noListCont.splice(i,1,b);
								context.state.market.conditionList.splice(i,1,parameters);
							}else{
								context.state.market.conditionList.splice(i,1);
								context.state.market.triggerConditionList.push(parameters);
//								context.state.market.yesListCont.push(b);
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
//				context.state.market.noListCont.push(b);
			}else{
				context.state.market.triggerConditionList.push(parameters);
//				context.state.market.yesListCont.push(b);
			}
		},
		updateStopLoss:function(context,parameters){
			context.state.market.layer='单号【'+ parameters.StopLossNo+'】,更新成功' + Math.floor(Math.random()*10);
			if(parameters.Status>2){
				context.state.market.stopLossTriggeredList.push(parameters);
				context.state.market.hasYesstopLossList.push(parameters);
				context.state.market.hasNostopLossList.forEach(function(e,i){
					if(e.StopLossNo==parameters.StopLossNo){
						context.state.market.hasNostopLossList.splice(i,1);
						context.state.market.stopLossList.splice(i,1);
					}
				});
			}else if(parameters.Status==2){
				context.state.market.hasNostopLossList.forEach(function(e,i){
					if(e.StopLossNo==parameters.StopLossNo){
						context.state.market.hasNostopLossList.splice(i,1);
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
						context.state.market.hasNostopLossList.splice(i,1,e);
					}
				});
				
				if(hasExist==false){
					context.state.market.stopLossList.push(parameters);
				}
				
			}
		},
		layerOnRspInsertStopLoss:function(context,parameters){
			if(parameters.Status==0){
				context.state.market.layer='提交成功,单号【'+ parameters.StopLossNo+'】'+Math.floor(Math.random()*10);
			}else{
				context.state.market.layer='提交失败,原因:【'+parameters.StatusMsg+'】'+Math.floor(Math.random()*10);
			}
		},
		qryHisTrade:function(context){ //查询历史交易
			const {tradeSocket} = context.state
			function _getDayString (date) { // '2018/03/01 00:00:00'
				var year = date.getFullYear();
				var day = date.getDate().toString().padStart(2,'0')
				var month = (date.getMonth() + 1).toString().padStart(2,'0')
				var timeString = year + '/' + month + '/' + day+' 00:00:00';
				return timeString
			}
			var date = new Date(); 
			var endTime = _getDayString(date);
			date.setDate(date.getDate()-1);
			var beginTime = _getDayString(date);
//			context.state.tradeSocket.send('{"method":"QryHisTrade","Parameters":{"ClientNo":"'+context.state.market.tradeConfig.username+'","BeginTime":"'+beginTime+'","EndTime":"'+endTime+'"}}');
			tradeSocket.send(`{"method":"QryHisTrade","Parameters":{"ClientNo":"${JSON.parse(localStorage.tradeUser).username}","BeginTime":"${beginTime}","EndTime":"${endTime}"}}`);
		
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
				context.state.market.layerOnRtnOrder = "成交（"+CommodityName+",价格:"+TradePrice+","+DirectionStr+TradeNum+"手）" + Math.floor(Math.random()*10);
			}
		},
		layerMessage:function(context,parameters){
			if(parameters!=null){
				if(parameters.OrderStatus==5){
					context.state.market.layer = parameters.StatusMsg+Math.floor(Math.random()*10);
					return;
				}
				
				var CommodityName =context.state.market.orderTemplist[parameters.CommodityNo].CommodityName;
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
					price = parseFloat(parameters.OrderPrice).toFixed(context.state.market.orderTemplist[parameters.CommodityNo].DotSize);
				}
				var OrderNum = parameters.OrderNum;
				var OrderID = parameters.OrderID;
				
				if(parameters.OrderStatus<4){
					context.state.market.layer='委托成功（'+CommodityName+','+price+','+DirectionStr+OrderNum+'手,委托号:'+OrderID+'）'+Math.floor(Math.random()*10);
				}else{
					context.state.market.layer='委托失败（'+CommodityName+','+price+','+DirectionStr+OrderNum+'手,失败原因:'+parameters.StatusMsg+'）'+Math.floor(Math.random()*10);
				}
				
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
						=parameters.OrderPrice;
					context.state.market.OnRspOrderInsertOrderListCont[context.state.market.OnRspOrderInsertOrderListCont.length-index-1].OrderNum
						=parameters.OrderNum;
					context.state.market.layer = parameters.StatusMsg + ':合约【'+ parameters.ContractCode +'】,订单号:【'+ parameters.OrderID +'】' + Math.floor(Math.random()*10);
					
				}
			}else if(parameters.OrderStatus == 6){
				return true;
			}else{
				if(isExist==true){
					context.state.market.orderListCont.splice(index,1);
					context.state.market.OnRspOrderInsertOrderListCont.splice(context.state.market.OnRspOrderInsertOrderListCont.length-index-1,1);
					context.state.market.layer = parameters.StatusMsg + ':合约【'+ parameters.ContractCode +'】,订单号:【'+ parameters.OrderID +'】' + + Math.floor(Math.random()*10);
				}
			}
		},
		appendApply:function(context,parameters){
			if( parameters.OrderStatus < 3 ) { // 订单已提交、排队中、部分成交 显示到挂单列表
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
							 positionListContCurrent.type_color='red';
						}
						if(parameters.Drection==1){
							 positionListContCurrent.type='空';
							  positionListContCurrent.type_color='green';
						}
						
						positionListContCurrent.price = parseFloat(parameters.OpenAvgPrice)
															.toFixed(context.state.market.orderTemplist[parameters.CommodityNo].DotSize);
															
						context.state.market.positionListCont.splice(positionListContCurrentIndex,1,positionListContCurrent);
						
//						context.state.market.qryHoldTotalArr[context.state.market.qryHoldTotalArr.length-1-positionListContCurrentIndex].HoldNum = parameters.HoldNum;
//						context.state.market.qryHoldTotalArr[context.state.market.qryHoldTotalArr.length-1-positionListContCurrentIndex].Drection = parameters.Drection;
//						context.state.market.qryHoldTotalArr[context.state.market.qryHoldTotalArr.length-1-positionListContCurrentIndex].OpenAvgPrice = parameters.OpenAvgPrice;
						context.state.market.qryHoldTotalArr[context.state.market.qryHoldTotalArr.length-1-positionListContCurrentIndex] = parameters;
					
					}else{
						context.state.market.positionListCont.splice(positionListContCurrentIndex,1);
						context.state.market.qryHoldTotalArr.splice(context.state.market.qryHoldTotalArr.length-1-positionListContCurrentIndex,1);
					}
			}
			
					
		},
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
//			console.log('根据订阅行情初始化持仓盈亏');
//			console.log(context.state.market.orderTemplist[parameters.CommodityNo]);
			let {market} = context.state
			var CommodityNo = context.state.market.orderTemplist[parameters.CommodityNo]
			var lastPrice = CommodityNo.LastQuotation.last;
			var currentObj = null;
			currentObj = market.qryHoldTotalArr.find((e)=>e.ContractCode==parameters.ContractCode)
			// 价差 = 最新价-开仓价
			var diff = lastPrice - currentObj.OpenAvgPrice;
			// 合约乘数 = 最小变动价格 / 最小变动点数
			var mult = CommodityNo.ContractSize/CommodityNo.MiniTikeSize;
			// 浮动盈亏 = (价差/最小变动) * (合约乘数 * 最小变动) * 手数 = 价差 * 合约乘数(最小变动价格 / 最小变动点数) * 手数
			var tmpFloatingProfit = parseFloat(diff * mult * currentObj.HoldNum).toFixed(2);
			if(currentObj.Drection === 1) { // 空反向
				tmpFloatingProfit = -tmpFloatingProfit;
			}
			
			var floatingProfit=tmpFloatingProfit+':'+CommodityNo.CurrencyNo;
			
			var CommodityName = CommodityNo.CommodityName;
			
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
			console.log(currentObj);
			
			console.log(currentPositionListContObj)
			if(isExist00==true){
				currentPositionListContObj.total = floatingProfit;
				if(tmpFloatingProfit>=0){
					currentPositionListContObj.total_color = 'red';
				}else{
					currentPositionListContObj.total_color = 'green';
				}
				
				market.positionListCont.splice(currentPositionListContObjIndex,1,currentPositionListContObj);
				
			}
			
			
		},
		
		HeartBeatTimingCheck:function(context){
			const {market} =context.state
			const {HeartBeat} = market
			setInterval(
				heartBeatUpdate,HeartBeat.intervalCheckTime
			);	
			function heartBeatUpdate(){
				if(HeartBeat.lastHeartBeatTimestamp == HeartBeat.oldHeartBeatTimestamp){
//					console.log('交易服务器断开，正在重连');
					market.tradeConnectedMsg='交易服务器断开，正在重连'+Math.ceil(Math.random()*10);
				}else{
					HeartBeat.oldHeartBeatTimestamp = HeartBeat.lastHeartBeatTimestamp; // 更新上次心跳时间
//					console.log(context.state.market.HeartBeat.oldHeartBeatTimestamp);
				}
			}
			heartBeatUpdate();
		},
		initTrade:function(context){
			const {state} = context
			const {market, isshow} = state
			let {tradeConfig} = market
			state.tradeSocket = new WebSocket(tradeConfig.url_real);
			state.tradeSocket.onopen = function(evt){
				//登录
				if(state.tradeSocket.readyState==1){ //连接已建立，可以进行通信。
					let tradeUser = JSON.parse(localStorage.getItem('tradeUser'))||{}
					const tradeMessage = {
						method: 'Login',
						Parameters: {
							ClientNo: tradeUser.username,
							PassWord: tradeUser.password,
							IsMock: tradeConfig.model,
							Version: tradeConfig.version,
							Source: tradeConfig.client_source
						}
					}
					if(tradeUser){
						context.commit('sendTradeMessage',tradeMessage)
					}else{
						if(tradeConfig.username!=''){
							tradeMessage.Parameters.ClientNo = tradeConfig.username
							tradeMessage.Parameters.PassWord = tradeConfig.password
							context.commit('sendTradeMessage',tradeMessage)
						}	
					}
				}
			};
			context.state.tradeSocket.onclose = function(evt) {
//				console.log('tradeClose:');
//				console.log(evt);
				state.tradeSocket=null;
			};
			context.state.tradeSocket.onerror = function(evt) {
//				console.log('tradeError:');
//				console.log(evt);
			};
			context.state.tradeSocket.onmessage = function(evt) {
				context.dispatch('handleTradeMessage',evt);
			};
			
		},
		initQuoteClient: function(context) {
			const {state} = context
			const {market, isshow} = state
			let {quoteConfig} = market
			state.quoteSocket = new WebSocket(quoteConfig.url_real);
			state.quoteSocket.onopen = function(evt) {
//				console.log('open');
				const data = {
					method: 'req_login',
					data: {
						user_name: quoteConfig.userName,
						password: quoteConfig.passWord,
						//protoc_version: '6.1'
					}
				}
				context.commit('sendMessage',data)
			};
			
			state.quoteSocket.onclose = function(evt) {
//				console.log('close');
			};
			state.quoteSocket.onerror = function(evt) {
//				console.log('error');
			};
			state.quoteSocket.onmessage = function(evt) {
				let wsData = JSON.parse(evt.data);
				state.wsjsondata = wsData;
				const {method} = state.wsjsondata;
				switch (method) {
					case 'on_rsp_login': //登录服务器
						market.quoteConnectedMsg='行情服务器连接成功' + Math.floor(Math.random()*10);
						// 查询服务器支持品种用于订阅
						var data = {
							method: 'req_commodity_list',
							data: {
								security_type: 'FUT_OUT',
							}
						}
						context.commit('sendMessage',data)
						break;
					case 'on_rsp_commodity_list': //行情服务器支持的品种
						//加入主力合约字段
						wsData.data.commodity_list.forEach(item => {
							var contract = item.contract_no_list.find( contract => contract.flags === 1)
							item.mainContract = contract?contract.contract_no:'';
							var key=item.commodity_no;
						    market.orderTemplist[key]=item;
						})
						market.markettemp = wsData.data.commodity_list;
						// 查询服务器支持品种用于订阅
						var paramsArr = market.markettemp.reduce((arr,obj) =>{
								var sendParams = {
									security_type: obj.security_type,
									exchange_no: obj.exchange_no,
									commodity_no: obj.commodity_no,
									contract_no: obj.mainContract							
								}
								arr.push(sendParams);
							
							return arr
						},[])
						var subscribeParams = {
							method: 'req_subscribe',
							data: paramsArr
						}

						context.commit('sendMessage',subscribeParams)
						break;
					case 'on_rsp_subscribe': //订阅成功信息
						//console.log(market.markettemp)
						var  {succ_list} = wsData.data;
						
						market.templateList = succ_list.reduce((templateList,item) =>{
							templateList[item.commodity_no] = item;
							return templateList
						},market.templateList)
						market.Parameters = succ_list
						// console.log(market.templateList)
						// console.log(market.Parameters)

						// templateList {key:Parameters}
						//market.templateList[key] = Parameters;
						//更新订阅合约列表Parameters 加入LastQuotation
						// let OnRspMarkettempItem = market.markettemp.find(item => item.CommodityNo == Parameters.CommodityNo)
						// OnRspMarkettempItem.LastQuotation = Parameters.LastQuotation;
						// market.Parameters.push(OnRspMarkettempItem);
						//初始化交易
						if(market.subscribeIndex==1) context.dispatch('initTrade');
						market.subscribeIndex++;
						break;
					case 'on_rtn_quote': // 最新行情
						var val = wsData.data;
						
						var key = val.contract_info.commodity_no;
						//
						//找到当前的回推的合约及index
						//console.log(market.Parameters)
						var RtnParameters = market.Parameters.find((a) => a.commodity_no == key);
						var RtnParametersIndex = market.Parameters.findIndex((a) => a.commodity_no == key);
						if (!RtnParameters) return;
						//处理变色 
						if (RtnParameters.LastQuotation && val.last > RtnParameters.LastQuotation.last) {
							market.quoteIndex = RtnParametersIndex;   //行情变颜色
							market.quoteColor = 'red';
						}else if(RtnParameters.LastQuotation && val.last < RtnParameters.LastQuotation.last) {
							market.quoteIndex = RtnParametersIndex;   //行情变颜色
							market.quoteColor = 'green';
						}
						//重新给templateList 赋最新值
						market.templateList[key] = val;

						// 找到 对应的markettemp 赋最新价
						var RtnMarkettemp = market.markettemp.find((a) => a.commodity_no == key);
						if (!RtnMarkettemp) return;

						RtnMarkettemp.LastQuotation = val;
						//market.Parameters[RtnParametersIndex] = RtnMarkettemp
						market.Parameters.splice(RtnParametersIndex, 1, RtnMarkettemp);
						//debugger;
						//更新当前合约
						if (market.currentNo == RtnMarkettemp.commodity_no) {
							console.log(123)
							market.CacheLastQuote.push(val);
							if (market.CacheLastQuote.length > 2){
								market.CacheLastQuote.shift();
							}
							market.jsonTow = wsData.data;
							let RtnTime = val.time_flag.split(mTimeExg); //得到回推的时间  '2018-08-07 16:49:45.147'=> ['2018-08-07', '16:49:45', '147'] [日期,时间,毫秒数]
							let RtnSecondArr = RtnTime[1].split(':'); //回推的时间 ['时', '分', '秒']
							//  当前成交量 = 本次总成交量 - 上次成交量  回推的行情的volume可能是0 需处理
							if(val.volume){
								market.volume = market.lastTotalVolume?market.volume + (val.volume - market.lastTotalVolume):market.volume
								// console.log(val.volume,'val.volume')
								// console.log(market.volume,'market.volume')
								// console.log(market.lastTotalVolume,'market.lastTotalVolume')
								market.lastTotalVolume = val.volume
								
							}
							//构造成历史数据结构
							//var arr = [val.time_flag, val.last, val.open, val.low, val.high, val.position, market.volume];
							var arr = [val.time_flag, val.last, val.open, val.low, val.high, market.volume];
							//更新分时图 
							if (isshow.fshow == true) {
								if(isshow.isfensshow == true) {
									let {jsonData} = market;
									let lastData = jsonData.data.Lines;
									let historyTime =lastData[lastData.length - 1][0].split(mTimeExg);
									let historySecondArr = historyTime[1].split(':'); //历史时间 ["20", "48", "00"]
									// 根据总成交量来计算当前成交量
									if(RtnSecondArr[1] == historySecondArr[1]) { // 1分钟内更新
										var time = lastData[lastData.length - 1][0];
										lastData[lastData.length - 1] = arr;
										lastData[lastData.length - 1][0] = time;
									} else{ //加一分钟 更新
										market.volume = 0;
										lastData.push(arr);
									}
									context.commit('setfensoption');
								}else{
									context.commit('drawfens', {
										id1: 'fens',
										id2: 'volume'
									});	
								}
								
							}
							//更新闪电图 
							if(isshow.islightshow == true) {
								context.commit('setlightDate');
								context.commit('drawlight', 'lightcharts');
							}
							//更新K线图 todo
							if(isshow.isklineshow == true) {
								console.log(market.CacheLastQuote)
								if(market.CacheLastQuote[1].TotalVolume <= market.CacheLastQuote[0].TotalVolume) return;
								let {jsonDataKline} = market;
								let historyTime =jsonDataKline.Parameters.Data[jsonDataKline.Parameters.Data.length - 1][0].split(' ');
								let historySecondArr = historyTime[1].split(':'); //历史时间 ["20", "48", "00"]
								const _updateData = function () {
									let newParameter = jsonDataKline.Parameters.Data;
									let newParameterLast = newParameter[newParameter.length - 1];
									arr[0] = newParameterLast[0]; //更新时间
									if(arr[1] <newParameterLast[3]) { //更新最新价
										arr[3] = arr[1];
									} else {
										arr[3] = newParameterLast[3]
									}
									if(arr[1] > newParameterLast[4]) { //更新最高价
										arr[4] = arr[1];
									} else {
										arr[4] = newParameterLast[4]
									}
									arr[1] = arr[1];
									arr[2] = newParameterLast[2];
									arr[5] = newParameterLast[5];
									arr[6] = market.volume;
									var length = newParameter.length;
									newParameter.splice(length-1,1,arr);
								};
								const _addData = function () {
									var arrTemp = [];
									jsonDataKline.Parameters.Data.shift();
									market.volume = 0;
									arrTemp[0] = arr[0].substring(0, arr[0].length - 2) + '00';
									arrTemp[1] = arr[1];
									arrTemp[2] = arr[1];
									arrTemp[3] = arr[1];
									arrTemp[4] = arr[1];
									arrTemp[5] = arr[5];
									arrTemp[6] = arr[6];
									arr = arrTemp;
									jsonDataKline.Parameters.Data.push(arrTemp);
								}
								const switchList = {
									'5': 'switch5min',
									'15': 'switch15min',
									'30': 'switch30min'
								}
								var selectTime = market.selectTime;
								if (selectTime == 1) { //1分钟k
									if(RtnSecondArr[1] == historySecondArr[1]) { // 1分钟内更新
										_updateData()
									} else{ //加一分钟 更新
										_addData()
									}
								}else if (selectTime == 1440) {//日k
									if (RtnSecondArr[0]=='00' && RtnSecondArr[1]=='00' && RtnSecondArr[2]=='00') {
										_addData()
									}else{
										_updateData()
									}
								}else { // 5 15 30K
									var switchItem = switchList[selectTime]
									if(RtnSecondArr[1]%selectTime != 0||!market[switchItem]) {
										if(RtnSecondArr[1]%selectTime != 0){
											market[switchItem] = true
										}
										_updateData()
									}else if(RtnSecondArr[1]%selectTime == 0 && market[switchItem]) {
										market[switchItem] = false
										_addData()
									}
								}
								context.commit('setklineoption');
								context.commit('drawkline', {
									id1: 'kliness',
									id2: 'volume'
								});
							}
						}
					/**
					 * 更新持仓盈亏
					 */
					context.dispatch('UpdateHoldProfit',JSON.parse(evt.data).Parameters);
						break;

					case 'on_rsp_history_data': //历史查询
						market.jsonData = wsData;
						//console.log(market.jsonData)
						if(isshow.fshow == true) {
							//赋值当前成交量
							market.volume = market.jsonData.data.Lines[market.jsonData.data.Lines.length - 1][5]
							context.commit('setfensoption');
							context.commit('drawfens', {
								id1: 'fens',
								id2: 'volume'
							});
						} else if(isshow.kshow == true) {
							market.jsonDataKline = wsData;
							//赋值当前成交量
							market.volume = market.jsonDataKline.data.Lines[market.jsonDataKline.data.Lines.length - 1][5]
							context.commit('setklineoption');
							context.commit('drawkline', {
								id1: 'kliness',
								id2: 'volume'
							});
						}
						break;	
					default:
						break;
				}
			}
		},
	}
})