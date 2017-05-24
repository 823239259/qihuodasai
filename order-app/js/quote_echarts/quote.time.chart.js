	//配置分时线
    function setOptionTime(data,positionValue) {
       var  option = {
       	backgroundColor: '#fff',
           tooltip : {
               show: true,
               transitionDuration:0,
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
                   var val   = params[0].value;
                   	time=time.split(" ")[1].split(":");
                   var html  = '时间:'+time[0]+":"+time[1]  + '<br/>' +
                           '价格: ' + val + '<br/>';
                   return html;
               },
           },
           toolbox: {
               show: false,
           },
           animation: false,
			 xAxis:[{
					type: 'category',
					show:false,
			        data: data.timeLabel,
			        axisLine: { lineStyle: { color: '#777777' } },
			        boundaryGap: true
			}],	
           yAxis:  [
               {
                   type: 'value',
                   scale: true,
                   position:"left",
                   axisTick:{
                   	show:false,
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
               }
           ],
           grid: {
               x: 70,
               y:20,
               x2:46,
               y2:5
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
                       color: "#3A4F5D"
                   }
               },
               itemStyle:{
               	 normal: {
                       color: "#3A4F5D"
                   }
               },
               symbolSize: 2,
                  markLine: {
                symbol: ['none', 'none'],
                clickable:false,
                               lineStyle: {
                   normal: {
                       width: 1,
                       color: "#3A4F5D"
                   }
               },
                data: [
	                 {name: '标线2起点', value: positionValue, xAxis: "1", yAxis: positionValue},     // 当xAxis或yAxis为数值轴时，不管传入是什么，都被理解为数值后做空间位置换算
        			{name: '标线2终点', xAxis: "2", yAxis: positionValue}
                ]
                },
               data:data.prices
           }
       }
        return option
    }
    //配置成交量
	function volumeChartSetOption(data) {
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
			 yAxis: [
			            {
	                type : 'value',
	                name : '成交量(万)',
	                 axisLine: { lineStyle: { color: '#777777' } },
		              axisTick:{
		               	show:false,
		              },
		              scale:true,
	                axisLabel: {
//	                    formatter: function (a) {
//	                    	   a = +a;
//	                    	    return isFinite(a)
//	                            ? echarts.format.addCommas(+a / 10000)
//	                            : '';
//	                    },
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
	function handleTimeChartData(json) {
    	if(json.Parameters.Data==null){
    		return;
    	}
        var Len=json.Parameters.Data.length;
        var TimeLength=timeData.timeLabel.length;
       	var Parameters=json.Parameters.Data;
       	var leng=timeData.time.length;
       	var VolumeLength=volumeChartData.time.length;
        for(var i=0;i<Len;i++){ 
			timeData.timeLabel[TimeLength+i]=Parameters[i][0];
        	timeData.prices[TimeLength+i]=fixedPriceByContract(Parameters[i][1],json.Parameters.CommodityNo);	
        	volumeChartData.time[VolumeLength+i]=Parameters[i][0];
            volumeChartData.volume[VolumeLength+i]=Parameters[i][6];
        }
    	var positionValue=getPositionValue();
//  	timeData.timeLabel=timeData.timeLabel.slice(-50);
//  	timeData.prices=timeData.prices.slice(-50);
//  	volumeChartData.time=volumeChartData.time.slice(-50);
//  	volumeChartData.volume=volumeChartData.volume.slice(-50);
    	var option = setOptionTime(timeData,positionValue);
        timeChart.setOption(option);
        timeChart.resize();
        timeChart.group="group1";
		var volumeChartOption=volumeChartSetOption(volumeChartData)
        volumeChart.setOption(volumeChartOption);
        volumeChart.resize();
       	volumeChart.group="group1";
    }
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
		                       color: "#777777"
		                   }
		               },
		                data: [
			                 {name: '标线2起点', value: x, xAxis: "1", yAxis: x},     // 当xAxis或yAxis为数值轴时，不管传入是什么，都被理解为数值后做空间位置换算
		       				 {name: '标线2终点', xAxis: "2", yAxis: x}
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
	                data: calculateMA(5,data),
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
	                data: calculateMA(10,data),
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
	                data: calculateMA(20,data),
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
	                data: calculateMA(30,data),
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