	//配置分时线
    function setOptionTime(data,positionValue){
       var  option = {
       	backgroundColor: '#1f1f1f',
           tooltip : {
               show: true,
               transitionDuration:0,
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
               formatter: function(params) {
               	  var time  = params[0].name;
                   var val   = params[0].value;
                   var html  = '时间:'+time + '<br/>' +
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
			        axisLine: { lineStyle: { color: '#8392A5' } },
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
                    axisLine: { lineStyle: { color: '#8392A5' } },
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
               }
           ],
           grid: {
               x: 40,
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
                       color: "#ffffff"
                   }
               },
               itemStyle:{
               	 normal: {
                       color: "#ffffff"
                   }
               },
               symbolSize: 2,
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
	      	backgroundColor: '#1f1f1f',
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
	                  axisLabel:{
	                  	textStyle:{
	                  		fontSize:10,
	                  	}
	                  },
	                   axisLine: { lineStyle: { color: '#8392A5' } },
	                  data : data.time
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
	                    },
	                    textStyle:{
	                  		fontSize:10,
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
	                  data:data.volume
	              }
	          ]
	      };
	        return option
	}
	var timeData={
		"timeLabel":[],
		"prices":[],
		"time":[]
	}
	var volumeChartData={
		"time":[],
		"volume":[]
	}
	function handleTimeChartData(json){
    	var dosizeL=$("#doSize").val();
        var Len=json.Parameters.Data.length;
        var TimeLength=timeData.timeLabel.length;
       	var Parameters=json.Parameters.Data;
       	var leng=timeData.time.length;
       	var VolumeLength=volumeChartData.time.length;
//     	if(jsonData.Parameters.HisQuoteType==1440){
//	    	for(var i=0;i<Len;i++){
//			var timeStr=parameters[i][DateTimeStampSubscript].split(" ")[0];
//				var openPrice = (parameters[i][OpenPriceSubscript]).toFixed(dosizeL);
//	            var closePrice = (parameters[i][LastPriceSubscript]).toFixed(dosizeL);
//	            var sgData = [timeStr,openPrice,closePrice,(parameters[i][LowPriceSubscript]).toFixed(dosizeL),(parameters[i][HighPriceSubscript]).toFixed(dosizeL),parameters[i][OpenPriceSubscript]];
//		         rawData[lent+i] = sgData; 
//	   		};
//	    }else{
//	    	for(var i=0;i<Len;i++){
//			var time2=parameters[i][DateTimeStampSubscript].split(" ");
//	        	var str1=time2[1].split(":");
//	        	var str2=str1[0]+":"+str1[1]
//				var openPrice = (parameters[i][OpenPriceSubscript]).toFixed(dosizeL);
//	            var closePrice = (parameters[i][LastPriceSubscript]).toFixed(dosizeL);
//	            var sgData = [str2,openPrice,closePrice,(parameters[i][LowPriceSubscript]).toFixed(dosizeL),(parameters[i][HighPriceSubscript]).toFixed(dosizeL),parameters[i][DateTimeStampSubscript]];
//		         rawData[lent+i] = sgData; 
//	   		};
//	   		
//	    }
        for(var i=0;i<Len;i++){ 
        	var time2=Parameters[i][DateTimeStampSubscript].split(" ");
        	var str1=time2[1].split(":");
        	var str2=str1[0]+":"+str1[1];
			timeData.timeLabel[TimeLength+i]=str2;
        	timeData.prices[TimeLength+i]=(Parameters[i][LastPriceSubscript]).toFixed(dosizeL);	
        	timeData.time[TimeLength+i]=Parameters[i][DateTimeStampSubscript];
        	volumeChartData.time[VolumeLength+i]=str2;
            volumeChartData.volume[VolumeLength+i]=Parameters[i][VolumeSubscript];
        }
        if(timeChart != null){
        	var positionValue=getPositionValue();
        	console.log(positionValue);
             var option = setOptionTime(timeData,positionValue);
             var volumeChartOption=volumeChartSetOption(volumeChartData)
            volumeChart.setOption(volumeChartOption);
            volumeChart.resize();
           	volumeChart.group="group1";
            timeChart.setOption(option);
            timeChart.resize();
            timeChart.group="group1";
           
        }

    }