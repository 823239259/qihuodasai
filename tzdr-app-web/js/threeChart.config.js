    var rawDataThree = [];
    var CandlestickVolumeDataThree ={
    	time:[],
    	volume:[]
    }
    var newDataThree =[]; 
    function processingDataThree(jsonData){
    	var dosizeL=$("#doSize").val();
    		var parameters = jsonData.Parameters.Data;
    		var Len=parameters.length;
    		if(parameters == null)return;
    	    var lent=rawDataThree.length;
        	for(var i=0;i<Len;i++){
        		var time2=parameters[i][DateTimeStampSubscript].split(" ");
		        	var str1=time2[1].split(":");
		        	var str2=str1[0]+":"+str1[1]
        			var openPrice = (parameters[i][OpenPriceSubscript]).toFixed(dosizeL);
		            var closePrice = (parameters[i][LastPriceSubscript]).toFixed(dosizeL);
		            var chaPrice = (closePrice - openPrice).toFixed(dosizeL);
		            var sgData = [str2,openPrice,closePrice,chaPrice,"",(parameters[i][LowPriceSubscript]).toFixed(dosizeL),(parameters[i][HighPriceSubscript]).toFixed(dosizeL),"","","-"];
			         rawDataThree[lent+i] = sgData; 
       		};
        	for(var i=0;i<rawDataThree.length-1;i++){
        		if(rawDataThree[i][0]==rawDataThree[i+1][0]){
        			rawDataThree.splice(i,1);
        		}
        	}
        	newDataThree=rawDataThree.slice(-60);
		  		var CandlestickChartOptionThree = setOptionThree(newDataThree);
		  		threeCandlestickChartDiv.setOption(CandlestickChartOptionThree);
		  		threeCandlestickChartDiv.group="group6";
		  	
    }
//  document.getElementById("Three").addEventListener("tap",function(){
//  			$("#dayCandlestickChart").css("opacity","0");
//  				$("#TimeChart1").css("opacity","0");
//				 if(threeCandlestickChartDiv != null){
//					document.getElementsByClassName("buttomFix")[0].style.display="block";
//						var option = setOptionThree(newDataThree);
//						 var option2=CandlestickVolumeChartSetoptionThree(CandlestickVolumeDataThree);
//						setTimeout(function(){
//							threeCandlestickChartDiv.resize();
//							threeCandlestickChartDiv.setOption(option);
//		        			threeCandlestickChartDiv.resize();	
//		        			threeCandlestickVolumeChart.resize();	
//							threeCandlestickVolumeChart.setOption(option2);
//		        			threeCandlestickVolumeChart.resize();	
//		        		},10);
//		        		setTimeout(function(){
//		        			$("#threeCandlestickChart").css("opacity","1");
//		        		},100);
//			    } 
//		});
    //设置数据参数（为画图做准备）
    function setOptionThree(rawData){
        var dates = rawData.map(function (item) {
            return item[0];
        });
        var data = rawData.map(function (item) {
            return [+item[1], +item[2], +item[5], +item[6]];
        });
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
		         formatter: function (params) {
		            var res = "时间:"+params[0].name;
		            res += '<br/>  开盘 : ' + params[0].value[0] + '<br/>  最高 : ' + params[0].value[3];
		            res += '<br/>  收盘 : ' + params[0].value[1] + '<br/>  最低 : ' + params[0].value[2];
		            return res;
		        }
		    },
		    grid: {
		               x: 43,
		               y:20,
		               x2:20,
		               y2:5
		           },
		    xAxis: {
		        type: 'category',
		        data: dates,
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
		            data: data,
		            itemStyle: {
		                normal: {
		                    color: '#FD1050',
		                    color0: '#0CF49B',
		                    borderColor: '#FD1050',
		                    borderColor0: '#0CF49B'
		                }
		            }
		        }
		    ]
		}
        return option;
    };
    function processingCandlestickVolumeDataThree(data){
    		var parameters = data.Parameters.Data;
    		var Len=parameters.length;
    		if(parameters == null)return;
    	    var lent=CandlestickVolumeDataThree.time;
        	for(var i=0;i<Len;i++){
        			var time2=parameters[i][DateTimeStampSubscript].split(" ");
		        	var str1=time2[1].split(":");
		        	var str2=str1[0]+":"+str1[1];
        			CandlestickVolumeDataThree.time.push(str2);
        			CandlestickVolumeDataThree.volume.push(parameters[i][VolumeSubscript]);
       		};
        	for(var i=0;i<CandlestickVolumeDataThree.time.length-1;i++){
        		if(CandlestickVolumeDataThree.time[i]==CandlestickVolumeDataThree.time[i+1]){
        			CandlestickVolumeDataThree.time.splice(i,1);
        			CandlestickVolumeDataThree.volume.splice(i,1);
        		}
        	}
        	CandlestickVolumeDataThree.time=CandlestickVolumeDataThree.time.slice(-60);
        	CandlestickVolumeDataThree.volume=CandlestickVolumeDataThree.volume.slice(-60);
        	threeCandlestickVolumeChart.group="group6";
		  		var option1= CandlestickVolumeChartSetoptionThree(CandlestickVolumeDataThree);
		  		threeCandlestickVolumeChart.resize();	
		  		threeCandlestickVolumeChart.setOption(option1);
		  		threeCandlestickVolumeChart.resize();	
		  	
    };
    function CandlestickVolumeChartSetoptionThree(data){
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
	               x2:20,
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