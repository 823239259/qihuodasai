//配置分时线
var CommodityName1 = null;
var CommodityName2 = null;
var SettlePrice1 = 0;
var SettlePrice2 = 0;

function setOptionTime(data, data1, positionValue,CommodityName1,CommodityName2) {
	var option = {
		backgroundColor: '#fff',
		tooltip: {
			show: true,
			transitionDuration: 0,
			trigger: 'axis',
			axisPointer: {
				type: 'line',
				animation: false,
				lineStyle: {
					color: '#AAAAAA',
					width: 1,
					opacity: 1
				}
			},
			formatter: function(params) {
				var time = params[0].name;
				var val = params[0].value;
				var val1 = params[1].value;
				var val3=params[1].value;
				var val4=0;
				if(val1!=undefined ){
					if(val1.length==0 ){
						val1=0;
					}
					val4 = ((val1 - SettlePrice1) * SettlePrice2) / SettlePrice1 + SettlePrice2;
					val4=fixedPriceByContract(val4, $("#commodityNo").val());
					val1 = fixedPriceByContract(val1, $("#commodityNo1").val());
				}else{
					val1=0;
					val3=0;
				}
				if(val==""){
					val=0;
				}
				var time1 = params[1].name;
				var name = params[0].seriesName;
				var name1 = params[1].seriesName;
				time=time.split(" ")[1].split(":");
				var html = time[0]+":"+time[1] + '<br/>' +
					"基差 "+ Number(val4-val).toFixed(2)+ '<br/>' +
					name + '<br/>' +
					'<span style="display:inline-block;margin-right:5px;border-radius:10px;width:9px;height:9px;background-color:#384d5b""></span>' + val + '<br/>' +
					name1 + '<br/>' +
					'<span style="display:inline-block;margin-right:5px;border-radius:10px;width:9px;height:9px;background-color:#e6c72e"></span>' + val4;
				return html;
			},
		},
		toolbox: {
			show: false,
		},
		animation: false,
		xAxis: [{
				type: 'category',
				show: true,
				data: data.timeLabel,
				axisLine: { lineStyle: { color: '#777777' } },
				boundaryGap: true
			}
			//		, {
			//			type: 'category',
			//			show: true,
			//			axisTick: {
			//				alignWithLabel: true
			//			},
			//			data: data1.timeLabel,
			//			axisLine: { onZero: false, lineStyle: { color: '#777777' } },
			//			boundaryGap: true
			//		}
		],
		yAxis: [{
			type: 'value',
			scale: true,
			position: "left",
			axisTick: {
				show: false,
			},
			axisLine: { lineStyle: { color: '#777777' } },
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
					color: "#CCCCCC"
				}
			}
		}],
		grid: {
			x: 70,
			y: 20,
			x2: 46,
			y2: 5
		},
		series: [{
			type: 'line',
			name: CommodityName1,
			label: {
				normal: {
					show: false,
					position: 'inside'
				},
			},
			lineStyle: {
				normal: {
					width: 1,
					color: "#384d5b"
				}
			},
			itemStyle: {
				normal: {
					color: "#384d5b"
				}
			},
			symbolSize: 2,
			markLine: {
				symbol: ['none', 'none'],
				clickable: false,
				lineStyle: {
					normal: {
						width: 1,
						color: "#384d5b"
					}
				},
				data: [
					{ name: '标线2起点', value: positionValue, xAxis: "1", yAxis: positionValue }, // 当xAxis或yAxis为数值轴时，不管传入是什么，都被理解为数值后做空间位置换算
					{ name: '标线2终点', xAxis: "2", yAxis: positionValue }
				]
			},
			data: data.prices
		}, {
			type: 'line',
			name: CommodityName2,
			label: {
				normal: {
					show: false,
					position: 'inside'
				},
			},
			lineStyle: {
				normal: {
					width: 1,
					color: "#e6c72e"
				}
			},
			itemStyle: {
				normal: {
					color: "#e6c72e"
				}
			},
			symbolSize: 2,
			markLine: {
				symbol: ['none', 'none'],
				clickable: false,
				lineStyle: {
					normal: {
						width: 1,
						color: "#e6c72e"
					}
				},
				data: [
					{ name: '标线2起点', value: positionValue, xAxis: "1", yAxis: positionValue }, // 当xAxis或yAxis为数值轴时，不管传入是什么，都被理解为数值后做空间位置换算
					{ name: '标线2终点', xAxis: "2", yAxis: positionValue }
				]
			},
			data: data1.prices
		}]
	}
	return option
}
//配置成交量
function volumeChartSetOption(data) {
	var option = {
		backgroundColor: '#fff',
		color: ['#CCCCCC'],
		tooltip: {
			trigger: 'axis',
			axisPointer: {
				type: 'line',
				animation: false,
				lineStyle: {
					color: '#AAAAAA',
					width: 1,
					opacity: 1
				}
			},
		},
		toolbox: {
			show: false,
		},
		animation: false,
		grid: {
			x: 70,
			y: 30,
			x2: 46,
			y2: 20
		},
		xAxis: [{
			type: 'category',
			position: 'bottom',
			boundaryGap: true,
			axisTick: { onGap: false },
			splitLine: { show: false },
			axisLabel: {
				textStyle: {
					fontSize: 10,
				}
			},
			axisLine: { lineStyle: { color: '#777777' } },
			data: data.time
		}],
		yAxis: [{
			type: 'value',
			name: '成交量(万)',
			axisLine: { lineStyle: { color: '#777777' } },
			axisTick: {
				show: false,
			},
			scale: true,
			axisLabel: {
				//	                    formatter: function (a) {
				//	                    	   a = +a;
				//	                    	    return isFinite(a)
				//	                            ? echarts.format.addCommas(+a / 10000)
				//	                            : '';
				//	                    },
				textStyle: {
					fontSize: 10,
				}
			},
			splitLine: {
				show: true,
				lineStyle: {
					color: "#CCCCCC"
				}
			}
		}],
		series: [{
			name: '成交量',
			type: 'bar',
			data: data.volume,
			itemStyle: {
				normal: {
					color: '#7fbe9e'
				},
				emphasis: {
					color: '#140'
				}
			},
		}]
	};
	return option
}

function handleDepTimeChartData(json) {
	timeChart=echarts.init(document.getElementById("timeChart"));
	if(json.Parameters.Data == null) {
		return;
	}
	if(json.Parameters.CommodityNo != $("#commodityNo").val()) {
		return
	}
	var Len = json.Parameters.Data.length;
	var TimeLength = timeData.timeLabel.length;
	var Parameters = json.Parameters.Data;
	var leng = timeData.time.length;
	var VolumeLength = volumeChartData.time.length;
	for(var i = 0; i < Len; i++) {
		timeData.timeLabel[TimeLength + i] = Parameters[i][0];
		timeData.prices[TimeLength + i] = fixedPriceByContract(Parameters[i][1], json.Parameters.CommodityNo);
		volumeChartData.time[VolumeLength + i] = Parameters[i][0];
		volumeChartData.volume[VolumeLength + i] = Parameters[i][6];
	}
	var positionValue = getPositionValue();
//	timeData.timeLabel = timeData.timeLabel.slice(-50);
//	timeData.prices = timeData.prices.slice(-50);
//	volumeChartData.time = volumeChartData.time.slice(-50);
//	volumeChartData.volume = volumeChartData.volume.slice(-50);
	var option = setOptionTime(timeData, deptimeDataCommodity1, positionValue,CommodityName1,CommodityName2);
	timeChart.setOption(option);
	timeChart.resize();
	timeChart.group = "group1";
	var volumeChartOption = volumeChartSetOption(volumeChartData)
	volumeChart.setOption(volumeChartOption);
	volumeChart.resize();
	volumeChart.group = "group1";
}

function handleDepTimeChartData1(json) {
	if(json.Parameters.Data == null) {
		return;
	}
	if($("#commodityNo1").val() != json.Parameters.CommodityNo) {
		return
	}
	var Len = json.Parameters.Data.length;
	var TimeLength = deptimeDataC.timeLabel.length;
	var Parameters = json.Parameters.Data;
	var leng = deptimeDataC.time.length;
	for(var i = 0; i < Len; i++) {
		deptimeDataC.timeLabel[TimeLength + i] = Parameters[i][0];
		deptimeDataC.prices[TimeLength + i] = fixedPriceByContract(Parameters[i][1], json.Parameters.CommodityNo);
	}
	for(var j = 0; j < SettlePrice.CommodityNo.length; j++) {
		if(SettlePrice.CommodityNo[j] == $("#commodityNo").val()) {
			SettlePrice1 = SettlePrice.SettlePrice[j];
			CommodityName1 = CacheQuoteBase.getCacheContractAttribute($("#commodityNo").val(), "CommodityName");
		}
		if(SettlePrice.CommodityNo[j] == $("#commodityNo1").val()) {
			SettlePrice2 = SettlePrice.SettlePrice[j];
			CommodityName2 = CacheQuoteBase.getCacheContractAttribute($("#commodityNo1").val(), "CommodityName");
		}
	}
		generateTemp();
	for(var i = 0; i < deptimeDataTemp.timeLabel.length; i++) {
		deptimeDataCommodity1.timeLabel.push(deptimeDataTemp.timeLabel[i]);
		if(deptimeDataTemp.prices[i]==""){
			deptimeDataCommodity1.prices.push(deptimeDataTemp.prices[i]);
		}else{
			var prices = Number(deptimeDataTemp.prices[i] - SettlePrice2) / SettlePrice2 * SettlePrice1 + SettlePrice1;
//			prices = fixedPriceByContract(prices, $("#commodityNo1").val())
			deptimeDataCommodity1.prices.push(prices);
		}
		
	}
	var positionValue = getPositionValue();
//	deptimeDataCommodity1.timeLabel = deptimeDataCommodity1.timeLabel.slice(-50);
//	deptimeDataCommodity1.prices = deptimeDataCommodity1.prices.slice(-50);
//	volumeChartData.time = volumeChartData.time.slice(-50);
//	volumeChartData.volume = volumeChartData.volume.slice(-50);
	var option = setOptionTime(timeData, deptimeDataCommodity1, positionValue,CommodityName1,CommodityName2);
	timeChart.setOption(option);
	timeChart.resize();
	timeChart.group = "group1";
	var volumeChartOption = volumeChartSetOption(volumeChartData)
	volumeChart.setOption(volumeChartOption);
	volumeChart.resize();
	volumeChart.group = "group1";
}
var deptimeDataTemp={"timeLabel":[],"prices":[]};
function generateTemp(){
	for(var t=0;t<timeData.timeLabel.length;t++){
		var valuetemp = "";
		for(var i=0;i<deptimeDataC.timeLabel.length;i++){
			if(timeData.timeLabel[t]==deptimeDataC.timeLabel[i]){
				valuetemp = deptimeDataC.prices[i];
				break;
			}
		}
		deptimeDataTemp.timeLabel.push(timeData.timeLabel[t]);
		deptimeDataTemp.prices.push(valuetemp);
	}
}
function setOptionCandlestick(data, x) {
	var option = {
		backgroundColor: '#fff',
		tooltip: {
			trigger: 'axis',
			axisPointer: {
				type: 'line',
				animation: false,
				lineStyle: {
					color: '#AAAAAA',
					width: 1,
					opacity: 1
				}
			},
			formatter: function(params) {
				var time = params[0].name;
				var kd = params[0].data;
				var ma5 = params[1].data;
				var ma10 = params[2].data;
				var ma20 = params[3].data;
				var ma30 = params[4].data;
				var rate = (kd[1] - kd[0]) / kd[0] * 100;
				time=time.split(" ")[1].split(":");
                  rate = rate > 0 ?( '+'+rate.toFixed(2)):rate.toFixed(2);
                   var res = "时间:"+time[0]+":"+time[1] + '  涨跌 : ' + rate;
				res += '<br/>  开盘 : ' + kd[0] + '  最高 : ' + kd[3];
				res += '<br/>  收盘 : ' + kd[1] + ' 最低 : ' + kd[2];
				res += '<br/> <span style="display:inline-block;margin-right:5px;border-radius:10px;width:9px;height:9px;background-color:#3689B3"></span> MA5 : ' + ma5 + '  <span style="display:inline-block;margin-right:5px;border-radius:10px;width:9px;height:9px;background-color:#B236B3"></span> MA10 : ' + ma10;
				res += '<br/><span style="display:inline-block;margin-right:5px;border-radius:10px;width:9px;height:9px;background-color:#B37436"></span> MA20 : ' + ma20 + '  <span style="display:inline-block;margin-right:5px;border-radius:10px;width:9px;height:9px;background-color:#B2B336"></span> MA30 : ' + ma30;
				return res;
			}
		},
		grid: {
			x: 70,
			y: 20,
			x2: 46,
			y2: 5
		},
		xAxis: {
			type: 'category',
			data: data.categoryData,
			show: false,
			axisLine: { lineStyle: { color: '#777777' } }
		},
		yAxis: {
			scale: true,
			axisLine: { lineStyle: { color: '#777777' } },
			splitLine: { show: false },
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
					color: "#CCCCCC"
				}
			}
		},
		animation: false,
		series: [{
				type: 'candlestick',
				name: '',
				data: data.values,
				markLine: {
					symbol: ['none', 'none'],
					clickable: false,
					lineStyle: {
						normal: {
							width: 1,
							color: "#777777"
						}
					},
					data: [
						{ name: '标线2起点', value: x, xAxis: "1", yAxis: x }, // 当xAxis或yAxis为数值轴时，不管传入是什么，都被理解为数值后做空间位置换算
						{ name: '标线2终点', xAxis: "2", yAxis: x }
					]
				},
				itemStyle: {
					normal: {
						color: '#ef232a',
						color0: '#14b143',
						borderColor: '#ef232a',
						borderColor0: '#14b143'
					}
				}
			},
			{
				name: 'MA5',
				type: 'line',
				data: calculateMA(5, data),
				smooth: true,
				showSymbol: false,
				lineStyle: {
					normal: {
						color: '#C43E3A',
						width: 1,
					}
				}
			},
			{
				name: 'MA10',
				type: 'line',
				showSymbol: false,
				data: calculateMA(10, data),
				smooth: true,
				lineStyle: {
					normal: {
						color: '#586A76',
						width: 1,
					}
				}
			},
			{
				name: 'MA20',
				type: 'line',
				showSymbol: false,
				data: calculateMA(20, data),
				smooth: true,
				lineStyle: {
					normal: {
						color: '#6EA8AF',
						width: 1,
					}
				}
			},
			{
				name: 'MA30',
				type: 'line',
				showSymbol: false,
				data: calculateMA(30, data),
				smooth: true,
				lineStyle: {
					normal: {
						color: '#E6BDAE',
						width: 1,
					}
				}
			}

		]
	}
	return option;
};