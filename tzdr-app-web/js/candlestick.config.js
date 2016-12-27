    var rawData = [];
    var CandlestickChartOption=null;
    var CandlestickVolumeChartOption=null;
    var CandlestickVolumeData={
    	time:[],
    	volume:[]
    }
    var newData=[]; 
    var    data0;
    function processingData(jsonData){
    	var dosizeL=$("#doSize").val();
    		var parameters = jsonData.Parameters.Data;
    		var Len=parameters.length;
    		if(jsonData == null)return;
    	    var lent=rawData.length;
    	    if(jsonData.Parameters.HisQuoteType==1440){
    	    	for(var i=0;i<Len;i++){
        		var timeStr=parameters[i][DateTimeStampSubscript].split(" ")[0];
        			var openPrice = (parameters[i][OpenPriceSubscript]).toFixed(dosizeL);
		            var closePrice = (parameters[i][LastPriceSubscript]).toFixed(dosizeL);
		            var sgData = [timeStr,openPrice,closePrice,(parameters[i][LowPriceSubscript]).toFixed(dosizeL),(parameters[i][HighPriceSubscript]).toFixed(dosizeL)];
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
        			var openPrice = (parameters[i][OpenPriceSubscript]).toFixed(dosizeL);
		            var closePrice = (parameters[i][LastPriceSubscript]).toFixed(dosizeL);
		            var sgData = [str2,openPrice,closePrice,(parameters[i][LowPriceSubscript]).toFixed(dosizeL),(parameters[i][HighPriceSubscript]).toFixed(dosizeL)];
			         rawData[lent+i] = sgData; 
	       		};
	        	for(var i=0;i<rawData.length-1;i++){
	        		if(rawData[i][0]==rawData[i+1][0]){
	        			rawData.splice(i,1);
	        		}
	        	}
    	    }
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
		   
	  		myChart.resize();
	  		CandlestickVolumeChart.resize();	
		  	myChart.group="group2";
		  	
    }
	    function splitData(data0) {
	        var categoryData = [];
	        var values = [];
	        for (var i = 0; i < data0.length; i++) {
	            categoryData.push(data0[i][0]);
	            values.push([data0[i][1],data0[i][2],data0[i][3],data0[i][4]]);
	        }
	        return {
	            categoryData: categoryData,
	            values: values
	        };
	    }
	
	    function calculateMA(dayCount,data) {
	        var result = [];
	        console.log(data.values);
	        for (var i = 0, len = data.values.length; i < len; i++) {
	            if (i < dayCount) {
	                result.push('-');
	                continue;
	            }
	            var sum = 0;
	            for (var j = 0; j < dayCount; j++) {
	                sum += data.values[i - j][1];
	            }
	            result.push(sum / dayCount);
	        }
	        return result;
	    }
    //设置数据参数（为画图做准备）
    function setOption(data,x){
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
		    },
		    grid: {
		               x: 43,
		               y:20,
		               x2:46,
		               y2:5
		           },
		    xAxis: {
		        type: 'category',
		        data: data.categoryData,
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
		            data: data.values,
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
	                data: calculateMA(5,data),
	                smooth: true,
	                lineStyle: {
	                    normal: {opacity: 0.5}
	                }
	            },
	            {
	                name: 'MA10',
	                type: 'line',
	                data: calculateMA(10,data),
	                smooth: true,
	                lineStyle: {
	                    normal: {opacity: 0.5}
	                }
	            },
	            {
	                name: 'MA20',
	                type: 'line',
	                data: calculateMA(20,data),
	                smooth: true,
	                lineStyle: {
	                    normal: {opacity: 0.5}
	                }
	            },
	            {
	                name: 'MA30',
	                type: 'line',
	                data: calculateMA(30,data),
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
        	CandlestickVolumeData.time=volumeTime.slice(-40);
        	CandlestickVolumeData.volume=volumeV.slice(-40);
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