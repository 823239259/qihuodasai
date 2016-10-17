    var rawDataFive = [];
    var CandlestickVolumeDataFive={
    	time:[],
    	volume:[]
    }
    var newDataFive=[]; 
    function processingDataFive(jsonData){
    	var dosizeL=$("#doSize").val();
    		var parameters = jsonData.Parameters.Data;
    		var Len=parameters.length;
    		if(parameters == null)return;
    	    var lent=rawDataFive.length;
        	for(var i=0;i<Len;i++){
        		var time2=parameters[i][DateTimeStampSubscript].split(" ");
		        	var str1=time2[1].split(":");
		        	var str2=str1[0]+":"+str1[1]
        			var openPrice = (parameters[i][OpenPriceSubscript]).toFixed(dosizeL);
		            var closePrice = (parameters[i][LastPriceSubscript]).toFixed(dosizeL);
		            var chaPrice = (closePrice - openPrice).toFixed(dosizeL);
		            var sgData = [str2,openPrice,closePrice,chaPrice,"",(parameters[i][LowPriceSubscript]).toFixed(dosizeL),(parameters[i][HighPriceSubscript]).toFixed(dosizeL),"","","-"];
			         rawDataFive[lent+i] = sgData; 
       		};
        	for(var i=0;i<rawDataFive.length-1;i++){
        		if(rawDataFive[i][0]==rawDataFive[i+1][0]){
        			rawDataFive.splice(i,1);
        		}
        	}
        	newDataFive=rawDataFive.slice(-60);
		  		CandlestickChartOption = setOptionFive(newDataFive);
		  		fiveCandlestickChartDiv.setOption(CandlestickChartOption);
		  		fiveCandlestickChartDiv.group="group4";
		  	
    }
    document.getElementById("Five").addEventListener("tap",function(){
    			$("#dayCandlestickChart").css("opacity","0");
    				$("#TimeChart1").css("opacity","0");
				 if(fiveCandlestickChartDiv != null){
					document.getElementsByClassName("buttomFix")[0].style.display="block";
						var option = setOptionFive(newDataFive);
						 var option2=CandlestickVolumeChartSetoptionFive(CandlestickVolumeData);
						setTimeout(function(){
							fiveCandlestickChartDiv.resize();
							fiveCandlestickChartDiv.setOption(option);
		        			fiveCandlestickChartDiv.resize();	
		        			fiveCandlestickVolumeChart.resize();	
							fiveCandlestickVolumeChart.setOption(option2);
		        			fiveCandlestickVolumeChart.resize();	
		        		},10);
		        		setTimeout(function(){
		        			$("#fiveCandlestickChart").css("opacity","1");
		        		},100);
			    } 
		});
    //设置数据参数（为画图做准备）
    function setOptionFive(rawData){
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
    function processingCandlestickVolumeDataFive(data){
    		var parameters = data.Parameters.Data;
    		var Len=parameters.length;
    		if(parameters == null)return;
    	    var lent=CandlestickVolumeDataFive.time;
        	for(var i=0;i<Len;i++){
        			var time2=parameters[i][DateTimeStampSubscript].split(" ");
		        	var str1=time2[1].split(":");
		        	var str2=str1[0]+":"+str1[1]
        			CandlestickVolumeDataFive.time[lent+i]=str2;
        			CandlestickVolumeDataFive.volume[lent+i]=parameters[i][VolumeSubscript];
       		};
        	for(var i=0;i<CandlestickVolumeDataFive.time.length-1;i++){
        		if(CandlestickVolumeDataFive.time[i]==CandlestickVolumeDataFive.time[i+1]){
        			CandlestickVolumeDataFive.time.splice(i,1);
        			CandlestickVolumeDataFive.volume.splice(i,1);
        		}
        	}
        	CandlestickVolumeDataFive.time=CandlestickVolumeDataFive.time.slice(-60);
        	CandlestickVolumeDataFive.volume=CandlestickVolumeDataFive.volume.slice(-60);
        	fiveCandlestickVolumeChart.group="group4";
		  		var option1= CandlestickVolumeChartSetoptionFive(CandlestickVolumeData);
		  		fiveCandlestickVolumeChart.resize();	
		  		fiveCandlestickVolumeChart.setOption(option1);
		  		fiveCandlestickVolumeChart.resize();	
		  	
    };
    function CandlestickVolumeChartSetoptionFive(data){
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