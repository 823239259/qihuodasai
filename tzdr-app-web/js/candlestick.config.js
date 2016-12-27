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
        			var timeStr=parameters[i][DateTimeStampSubscript].split(" ")[0];
		            var sgData = [timeStr,parameters[i][OpenPriceSubscript],parameters[i][LastPriceSubscript],parameters[i][LowPriceSubscript],parameters[i][HighPriceSubscript]];
			         rawData[lent+i] = sgData; 
	       		};
	        	for(var i=0;i<rawData.length-1;i++){
	        		if(rawData[i][0]==rawData[i+1][0]){
	        			rawData.splice(i,1);
	        		}
	        	}
    	    }else{
    	    	for(var i=0;i<Len;i++){
        		var time2=parameters[i][DateTimeStampSubscript].split(" ");
		        	var str1=time2[1].split(":");
		        	var str2=str1[0]+":"+str1[1]
        			var sgData = [str2,parameters[i][OpenPriceSubscript],parameters[i][LastPriceSubscript],parameters[i][LowPriceSubscript],parameters[i][HighPriceSubscript]];
			         rawData[lent+i] = sgData; 
	       		};
	       		rawData=rawData.slice(-40)
	        	for(var i=0;i<rawData.length-1;i++){
	        		if(rawData[i][0]==rawData[i+1][0]){
	        			rawData.splice(i,1);
	        		}
	        	}
    	    }
    	    console.log(rawData)
        	newData=splitData(rawData);
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
       var option = {
		    backgroundColor: 'rgba(43, 43, 43, 0)',
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
//		         formatter: function (params) {
//		            var res = "时间:"+params[0].name;
//		            res += '<br/>  开盘 : ' + params[0].value[0] + '<br/>  最高 : ' + params[0].value[3];
//		            res += '<br/>  收盘 : ' + params[0].value[1] + '<br/>  最低 : ' + params[0].value[2];
//		            return res;
//		        }
		    },
		    grid: {
		               x: 43,
		               y:20,
		               x2:46,
		               y2:5
		           },
		    xAxis: {
		        type: 'category',
		        data: rawData.categoryData,
		        show:false,
		        axisLine: { lineStyle: { color: '#8392A5' } }
		    },
		    yAxis: {
		        scale: true,
		        axisLine: { lineStyle: { color: '#8392A5' } },
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
		                        color: "#8392A5"
		                    }
		                }
		    },
		    animation: false,
		    series: [
		        {
		            type: 'candlestick',
		            name: '',
		            data: rawData.values,
		              markLine: {
                		symbol: ['none', 'none'],
                		clickable:false,
                       lineStyle: {
		                   normal: {
		                       width: 1,
		                       color: "#ffffff"
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
		}
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
