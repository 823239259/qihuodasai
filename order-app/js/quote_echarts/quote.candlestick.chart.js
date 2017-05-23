    /*
 	 获取持仓均线的值
 	 */
    function getPositionValue() {
	//  	var text=$("#commodity_title").text();
	//		var length=$(".tab_content .position0").length;
			var positionValue=0
	//		if(length!=0){
	//			for(var i=0;i<length;i++){
	//				var text1=$(".tab_content .position0").eq(i).text();
	//				if(text.indexOf(text1)>=0){
	//					positionValue=$(".tab_content .position3").eq(i).text();
	//				}
	//			}
	//		}
		return positionValue;
    }
    
    //开盘(open)，收盘(close)，最低(lowest)，最高(highest)

        function processingData(jsonData) {
    		if(jsonData.Parameters.Data==null){
	    		return
	    	}
    		var parameters = jsonData.Parameters.Data;
    		var Len=parameters.length;
    		if(jsonData == null)return;
    	    	for(var i=0;i<Len;i++){
					var openPrice =	fixedPriceByContract(parameters[i][2],jsonData.Parameters.CommodityNo);
		            var closePrice = fixedPriceByContract(parameters[i][1],jsonData.Parameters.CommodityNo);
		            var sgData = [parameters[i][0],openPrice,closePrice,fixedPriceByContract(parameters[i][3],jsonData.Parameters.CommodityNo),fixedPriceByContract(parameters[i][4],jsonData.Parameters.CommodityNo),parameters[i][0]];
			        rawData.push(sgData);
			        CandlestickVolumeData.time.push(parameters[i][0]);
	        		CandlestickVolumeData.volume.push(parameters[i][6]);
	       		};
	       	var positionValue=getPositionValue();
	       	rawData=rawData.slice(-40);
	       	CandlestickVolumeData.time=CandlestickVolumeData.time.slice(-40);
	        CandlestickVolumeData.volume=CandlestickVolumeData.volume.slice(-40);
	       	splitData(rawData);
	       	if(CandlestickVolumeData.volume==null){
	       		return
	       	}
	       	if(CandlestickData.categoryData==null){
	       		return
	       	}
			var option=setOptionCandlestick(CandlestickData,positionValue);
			var option1=volumeChartCandlestickSetOption(CandlestickVolumeData);
			CandlestickChart.setOption(option);
	  		CandlestickChart.resize();
		  	CandlestickChart.group="group2";
		  	CandlestickVolumeChart.setOption(option1);
	  		CandlestickVolumeChart.resize();
		  	CandlestickVolumeChart.group="group2";
		  	
    }
    //设置数据参数（为画图做准备）
    function setOptionCandlestick(data,x) {
        var option = {
		    backgroundColor: '#fff',
		    tooltip: {
		        trigger: 'axis',
		        axisPointer : {
                   type : 'line',
                   animation: false,
		            lineStyle: {
		                color: '#AAAAAA',
		                width: 1,
		                opacity: 1
		            }
             	 },
               	formatter: function(params) {
                  var time  = params[0].name;
                  var kd    = params[0].data;
                  var ma5 = params[1].data;
                  var ma10 = params[2].data;
                  var ma20 = params[3].data;
                   var ma30 = params[4].data;
                  var rate = (kd[1]-kd[0])/kd[0]*100;
                  rate = rate > 0 ?( '+'+rate.toFixed(2)):rate.toFixed(2);
                   var res = "时间:"+params[0].name+ '  涨跌 : ' + rate;
		            res += '<br/>  开盘 : ' + kd[0] + '  最高 : ' + kd[3];
		            res += '<br/>  收盘 : ' + kd[1] + ' 最低 : ' + kd[2];
		              res += '<br/> <span style="display:inline-block;margin-right:5px;border-radius:10px;width:9px;height:9px;background-color:#3689B3"></span> MA5 : ' + ma5 + '  <span style="display:inline-block;margin-right:5px;border-radius:10px;width:9px;height:9px;background-color:#B236B3"></span> MA10 : ' + ma10;
		              res += '<br/><span style="display:inline-block;margin-right:5px;border-radius:10px;width:9px;height:9px;background-color:#B37436"></span> MA20 : ' + ma15 + '  <span style="display:inline-block;margin-right:5px;border-radius:10px;width:9px;height:9px;background-color:#B2B336"></span> MA30 : ' + ma30;
		            return res;
              }
		    },
		    grid: {
		               x: 70,
		               y:20,
		               x2:46,
		               y2:5
		           },
		    xAxis: {
		        type: 'category',
		        data: data.categoryData,
		        show:false,
		        axisLine: { lineStyle: { color: '#777777' } }
		    },
//		    dataZoom: [{
//		        textStyle: {
//		            color: '#8392A5'
//		        },
//		        show:false,
//		        handleIcon: 'M10.7,11.9v-1.3H9.3v1.3c-4.9,0.3-8.8,4.4-8.8,9.4c0,5,3.9,9.1,8.8,9.4v1.3h1.3v-1.3c4.9-0.3,8.8-4.4,8.8-9.4C19.5,16.3,15.6,12.2,10.7,11.9z M13.3,24.4H6.7V23h6.6V24.4z M13.3,19.6H6.7v-1.4h6.6V19.6z',
//		        handleSize: '80%',
//		        dataBackground: {
//		            areaStyle: {
//		                color: '#8392A5'
//		            },
//		            lineStyle: {
//		                opacity: 0.8,
//		                color: '#8392A5'
//		            }
//		        },
//		        handleStyle: {
//		            color: '#fff',
//		            shadowBlur: 3,
//		            shadowColor: 'rgba(0, 0, 0, 0.6)',
//		            shadowOffsetX: 2,
//		            shadowOffsetY: 2
//		        }
//		    }, {
//		        type: 'inside',
//		    }],
		    yAxis: {
		        scale: true,
		        axisLine: { lineStyle: { color: '#777777' } },
		        splitLine: { show: false },
		        axisTick:{
		                   	show:false,
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
		    series: [
		        {
		            type: 'candlestick',
		            name: '',
		            data: data.values,
		              markLine: {
                		symbol: ['none', 'none'],
                		clickable:false,
                       lineStyle: {
		                   normal: {
		                       width: 1,
		                       color: "#000"
		                   }
		               },
		                data: [
			                 {name: '标线2起点', value: x, xAxis: "1", yAxis: x},     // 当xAxis或yAxis为数值轴时，不管传入是什么，都被理解为数值后做空间位置换算
		       				 {name: '标线2终点', xAxis: "2", yAxis: x}
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
	                data: calculateMA(5,data),
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
	                data: calculateMA(10,data),
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
	                data: calculateMA(20,data),
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
	                data: calculateMA(30,data),
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
        return option;
    };
    function splitData(data0) {
        for (var i = 0; i < data0.length; i++) {
            CandlestickData.categoryData.push(data0[i][0]);
            CandlestickData.values.push([data0[i][1],data0[i][2],data0[i][3],data0[i][4]]);
        }
        return
    }
	//计算均线的数据
    function calculateMA(dayCount,data) {
    	var commodityNo=$("#commodityNo").val();
    	if(commodityNo==""){
    		commodityNo="CL";
    	};
        var result = [];
        for (var i = 0, len = data.values.length; i < len; i++) {
            if (i < dayCount) {
                result.push('-');
                continue;
            }
            var sum = 0;
            for (var j = 0; j < dayCount; j++) {
                sum += Number(data.values[i - j][1]);
            }
            result.push(fixedPriceByContract(sum / dayCount,commodityNo));
        }
        return result;
    }
        //配置成交量
	function volumeChartCandlestickSetOption(data) {
	      var  option = {
	      	backgroundColor: '#fff',
	      	 color: ['#CCCCCC'],
	          tooltip: {
	              trigger: 'axis',
	              axisPointer : {
	                   type : 'line',
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
	               y:30,
	               x2:46,
	               y2:20
	           },
	          xAxis:[
	              {
	                  type : 'category',
	                  position:'bottom',
	                 boundaryGap: true,
	                  axisTick: {onGap:false},
	                  splitLine: {show:false},
	                  axisLabel:{
	                  	textStyle:{
	                  		fontSize:10,
	                  	}
	                  },
	                   axisLine: { lineStyle: { color: '#777777' } },
	                  data : data.time
	              }
	          ],
//			     dataZoom: [{
//		        textStyle: {
//		            color: '#8392A5'
//		        },
//		        handleIcon: 'M10.7,11.9v-1.3H9.3v1.3c-4.9,0.3-8.8,4.4-8.8,9.4c0,5,3.9,9.1,8.8,9.4v1.3h1.3v-1.3c4.9-0.3,8.8-4.4,8.8-9.4C19.5,16.3,15.6,12.2,10.7,11.9z M13.3,24.4H6.7V23h6.6V24.4z M13.3,19.6H6.7v-1.4h6.6V19.6z',
//		        handleSize: '80%',
//		        dataBackground: {
//		            areaStyle: {
//		                color: '#8392A5'
//		            },
//		            lineStyle: {
//		                opacity: 0.8,
//		                color: '#8392A5'
//		            }
//		        },
//		        handleStyle: {
//		            color: '#fff',
//		            shadowBlur: 3,
//		            shadowColor: 'rgba(0, 0, 0, 0.6)',
//		            shadowOffsetX: 2,
//		            shadowOffsetY: 2
//		        }
//		    }, {
//		        type: 'inside',
//              handleSize: 8
//		    }],
			 yAxis: [
			            {
	                type : 'value',
	                name : '成交量',
	                 axisLine: { lineStyle: { color: '#777777' } },
		              axisTick:{
		               	show:false,
		              },
		              scale:true,
	                axisLabel: {
	                    textStyle:{
	                  		fontSize:10,
	                  	}
	                },
	                splitLine: {
	                    show: true,
	                    lineStyle: {
	                        color: "#CCCCCC"
	                    }
	                }
	            }
	        ],
	          series : [
	              {
	                  name: '成交量',
	                  type: 'bar',
	                  data:data.volume,
	                  itemStyle: {
			            normal: {
			                color: '#7fbe9e'
			            },
			            emphasis: {
			                color: '#140'
			            }
			        },
	              }
	          ]
	      };
	        return option
	}