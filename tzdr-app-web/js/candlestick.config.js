    var rawData = [];
    var CandlestickChartOption=null;
    var CandlestickVolumeChartOption=null;
    var CandlestickVolumeData={
    	time:[],
    	volume:[]
    }
    var newData={}; 
    function processingData(jsonData){
    	var dosizeL=$("#doSize").val();
    		var parameters = jsonData.Parameters.Data;
    		var Len=parameters.length;
    		if(jsonData == null)return;
    	    var lent=rawData.length;
    	    if(jsonData.Parameters.HisQuoteType==1440){
    	    	for(var i=0;i<Len;i++){
//      			var timeStr=parameters[i][DateTimeStampSubscript].split(" ")[0];
		            var sgData = [parameters[i][DateTimeStampSubscript],parameters[i][OpenPriceSubscript],parameters[i][LastPriceSubscript],parameters[i][LowPriceSubscript],parameters[i][HighPriceSubscript]];
			         rawData[lent+i] = sgData; 
	       		};
	        	for(var i=0;i<rawData.length-1;i++){
	        		if(rawData[i][0]==rawData[i+1][0]){
	        			rawData.splice(i,1);
	        		}
	        	}
    	    }else{
    	    	for(var i=0;i<Len;i++){
//      		var time2=parameters[i][DateTimeStampSubscript].split(" ");
//		        	var str1=time2[1].split(":");
//		        	var str2=str1[0]+":"+str1[1]
        			var sgData = [parameters[i][DateTimeStampSubscript],parameters[i][OpenPriceSubscript],parameters[i][LastPriceSubscript],parameters[i][LowPriceSubscript],parameters[i][HighPriceSubscript]];
			         rawData[lent+i] = sgData; 
	       		};
	        	for(var i=0;i<rawData.length-1;i++){
	        		if(rawData[i][0]==rawData[i+1][0]){
	        			rawData.splice(i,1);
	        		}
	        	}
    	    }
        	newData=splitData(rawData.slice(-60));
        	console.log(JSON.stringify(newData));
        	var x=0;
            var length=$("#positionList .position3").length;
        	var text=$("#CommodityNo").text();
            if(length!=0){
            	for(var i=0;i<length;i++){
            		var text1=$("#positionList .position0").eq(i).text();
            		if(text.indexOf(text1)>=0){
            			x=Number($("#positionList .position3").eq(i).text());
            		}
            	}
            }
	  		CandlestickChartOption = setOption(newData,x);
	  		myChart.setOption(CandlestickChartOption);
	  		myChart.resize();
	  		CandlestickVolumeChart.resize();	
		  	myChart.group="group2";
		  	
    }
	function splitData(rawData) {
	    var categoryData = [];
	    var values = []
	    for (var i = 0; i < rawData.length; i++) {
	        categoryData.push(rawData[i].splice(0, 1)[0]);
	        values.push(rawData[i])
	    }
	    return {
	        categoryData: categoryData,
	        values: values
	    };
	}
	
	function calculateMA(dayCount) {
	    var result = [];
	    if(newData.values!=undefined)
	    var len= newData.values.length;
	    for (var i = 0; i < len; i++) {
	        if (i < dayCount) {
	            result.push('-');
	            continue;
	        }
	        var sum = 0;
	        for (var j = 0; j < dayCount; j++) {
	            sum += newData.values[i - j][1];
	        }
	        result.push(sum / dayCount);
	    }
	    return result;
	}
    //设置数据参数（为画图做准备）
    function setOption(rawData,x){
    option = {
        title: {
            text: '上证指数',
            left: 0
        },
        tooltip: {
            trigger: 'axis',
            axisPointer: {
                type: 'line'
            }
        },
        legend: {
            data: ['日K', 'MA5', 'MA10', 'MA20', 'MA30']
        },
        grid: {
            left: '10%',
            right: '10%',
            bottom: '15%'
        },
        xAxis: {
            type: 'category',
            data: rawData.categoryData,
            scale: true,
            boundaryGap : false,
            axisLine: {onZero: false},
            splitLine: {show: false},
            splitNumber: 20,
            min: 'dataMin',
            max: 'dataMax'
        },
        yAxis: {
            scale: true,
            splitArea: {
                show: true
            }
        },
        dataZoom: [
            {
                type: 'inside',
                start: 50,
                end: 100
            },
            {
                show: true,
                type: 'slider',
                y: '90%',
                start: 50,
                end: 100
            }
        ],
        series: [
            {
                name: '日K',
                type: 'candlestick',
                data: rawData.values,
                markPoint: {
                    label: {
                        normal: {
                            formatter: function (param) {
                                return param != null ? Math.round(param.value) : '';
                            }
                        }
                    },
                    data: [
                        {
                            name: 'XX标点',
                            coord: ['2013/5/31', 2300],
                            value: 2300,
                            itemStyle: {
                                normal: {color: 'rgb(41,60,85)'}
                            }
                        },
                        {
                            name: 'highest value',
                            type: 'max',
                            valueDim: 'highest'
                        },
                        {
                            name: 'lowest value',
                            type: 'min',
                            valueDim: 'lowest'
                        },
                        {
                            name: 'average value on close',
                            type: 'average',
                            valueDim: 'close'
                        }
                    ],
                    tooltip: {
                        formatter: function (param) {
                            return param.name + '<br>' + (param.data.coord || '');
                        }
                    }
                },
                markLine: {
                    symbol: ['none', 'none'],
                    data: [
                        [
                            {
                                name: 'from lowest to highest',
                                type: 'min',
                                valueDim: 'lowest',
                                symbol: 'circle',
                                symbolSize: 10,
                                label: {
                                    normal: {show: false},
                                    emphasis: {show: false}
                                }
                            },
                            {
                                type: 'max',
                                valueDim: 'highest',
                                symbol: 'circle',
                                symbolSize: 10,
                                label: {
                                    normal: {show: false},
                                    emphasis: {show: false}
                                }
                            }
                        ],
                        {
                            name: 'min line on close',
                            type: 'min',
                            valueDim: 'close'
                        },
                        {
                            name: 'max line on close',
                            type: 'max',
                            valueDim: 'close'
                        }
                    ]
                }
            },
            {
                name: 'MA5',
                type: 'line',
                data: calculateMA(5),
                smooth: true,
                lineStyle: {
                    normal: {opacity: 0.5}
                }
            },
            {
                name: 'MA10',
                type: 'line',
                data: calculateMA(10),
                smooth: true,
                lineStyle: {
                    normal: {opacity: 0.5}
                }
            },
            {
                name: 'MA20',
                type: 'line',
                data: calculateMA(20),
                smooth: true,
                lineStyle: {
                    normal: {opacity: 0.5}
                }
            },
            {
                name: 'MA30',
                type: 'line',
                data: calculateMA(30),
                smooth: true,
                lineStyle: {
                    normal: {opacity: 0.5}
                }
            }

        ]
    };
        return option;
    };
    var firstTimeNum=0;
     var volumeTime=[];
    var volumeV=[];
    function processingCandlestickVolumeData(data){
    		var parameters = data.Parameters.Data;
    		var Len=parameters.length;
    		if(parameters == null)return;
    	    var lent=volumeV.length;
//  	    var lengt=volumeTimeH.length;
 			if(data.Parameters.HisQuoteType==1440){
	        	for(var i=0;i<Len;i++){
	        			var timeStr=parameters[i][DateTimeStampSubscript].split(" ")[0];
	        			volumeTime[lent+i]=timeStr;
	        			volumeV[lent+i]=parameters[i][VolumeSubscript];
	       		};
	        	for(var i=0;i<volumeTime.length-1;i++){
	        		if(volumeTime[i]==volumeTime[i+1]){
	        			volumeTime.splice(i,1);
	        			volumeV.splice(i,1);
	        		}
	        	}
       		 }else{
       		 	for(var i=0;i<Len;i++){
	        			var time2=parameters[i][DateTimeStampSubscript].split(" ");
			        	var str1=time2[1].split(":");
			        	var str2=str1[0]+":"+str1[1]
	        			volumeTime[lent+i]=str2;
	        			volumeV[lent+i]=parameters[i][VolumeSubscript];
	       		};
	        	for(var i=0;i<volumeTime.length-1;i++){
	        		if(volumeTime[i]==volumeTime[i+1]){
	        			volumeTime.splice(i,1);
	        			volumeV.splice(i,1);
	        		}
	        	}
       		 }
        	CandlestickVolumeData.time=volumeTime.slice(-60);
        	CandlestickVolumeData.volume=volumeV.slice(-60);
        	CandlestickVolumeChart.group="group2";
		  		var option1= CandlestickVolumeChartSetoption1(CandlestickVolumeData);
		  		CandlestickVolumeChart.resize();	
		  		CandlestickVolumeChart.setOption(option1);
		  		CandlestickVolumeChart.resize();	
		  	
    };
    function CandlestickVolumeChartSetoption1(data){
    	 var  CandlestickVolumeChartData=data;
	      var  option = {
	      	backgroundColor: '#2B2B2B',
	      	 color: ['#EDF274'],
	          tooltip: {
	              trigger: 'axis',
	              axisPointer : {
                   type : 'line',
                   animation: false,
		            lineStyle: {
		                color: '#ffffff',
		                width: 1,
		                opacity: 1
		            }
               },
	          },
	          legend: {
	              data:['']
	          },
	            toolbox: {
	                show: false,
	            },
	             animation: false,
				 grid: {
	               x: 40,
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
	                   axisLine: { lineStyle: { color: '#8392A5' } },
	                  data : CandlestickVolumeChartData.time
	              }
	          ],
			 yAxis: [
			            {
	                type : 'value',
	              name : '成交量(万)',
	                 axisLine: { lineStyle: { color: '#8392A5' } },
		              axisTick:{
		               	show:false,
		              },
		              scale:true,
	              axisLabel: {
	                  formatter: function (a) {
	                      a = +a;
	                      return isFinite(a)
	                          ? echarts.format.addCommas(+a / 10000)
	                          : '';
	                  }
	              },
	                splitLine: {
	                    show: true,
	                    lineStyle: {
	                        color: "#8392A5"
	                    }
	                }
	            }
	        ],
	          series : [
	              {
	                  name: '成交量',
	                  type: 'bar',
	                  data:CandlestickVolumeChartData.volume
	              },
	          ]
	      };
        return option
    }
