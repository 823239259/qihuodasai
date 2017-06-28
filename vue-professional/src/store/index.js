import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)
//控制显示与否的模块
var isshow = {
	state: {
		navBarShow: true,
		isconnected: false,
		bottomshow: false,
		pshow: false,
		sshow: false,
		fshow: true,
		kshow: false
	}
};
//控制行情数据
var market = {
	state: {
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
		option1:{
			
		},
		option2:{
			
		},
		option3:{
			
		},
		option4:{
			
		}
	}
}

export default new Vuex.Store({
	modules: {
		isshow,
		market
	},
	mutations: {
		drawfens:function(state,x){
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
			fens.setOption(state.market.option1);
			volume.setOption(state.market.option2);
		},
		drawfenssecond:function(state,x){
			var echarts = require('echarts/lib/echarts');
			var volume = echarts.getInstanceByDom(document.getElementById(x.id1));
			var fens = echarts.getInstanceByDom(document.getElementById(x.id2));
			fens.setOption(state.market.option1);
			volume.setOption(state.market.option2);
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
			state.market.option1={
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
			state.market.option2={
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
		setklineoption:function(state){
			var echarts = require('echarts/lib/echarts');
			var vol = [],
				price = [],
				time = [];
			state.market.jsonData.Parameters.Data.forEach(function(e) {
				vol.push(e[6]);
				time.push(e[0].split(' ')[1].split(':')[0] + ':' + e[0].split(' ')[1].split(':')[1]);
				price.push(e[1]);
			})
			//成交量设置
			state.market.option4={
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
		},
		drawkline:function(state,x){
			// 引入 ECharts 主模块
			var echarts = require('echarts/lib/echarts');
//			console.log(echarts);
			// 引入柱状图
			require('echarts/lib/chart/bar');
			require('echarts/lib/chart/line');
			require('echarts/lib/component/tooltip');
			
			var volume = echarts.init(document.getElementById(x.id2));
			volume.group = 'group1';
			// 基于准备好的dom，初始化echarts实例
//			var kline = echarts.init(document.getElementById(x.id1));
//			fens.group = 'group1';
//			echarts.connect("group1");
//			kline.setOption(state.market.option1);
			volume.setOption(state.market.option4);
		}
	}
})