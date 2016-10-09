    var firstTimeNumber=0;
    var rawData = [];
    var CandlestickChartOption=null;
    var CandlestickVolumeChartOption=null;
//  var CandlestickVolumeChartTime=[];
//  var CandlestickVolumeChartVolume=[];
    var CandlestickVolumeData={
    	time:[],
    	volume:[]
    }
    function processingData(jsonData){
    		var parameters = jsonData.Parameters.Data;
    		var Len=parameters.length;
    		if(parameters == null)return;
    	    var lent=rawData.length;
        	for(var i=0;i<Len;i++){
        		var time2=parameters[i][DateTimeStampSubscript].split(" ");
		        	var str1=time2[1].split(":");
		        	var str2=str1[0]+":"+str1[1]
        			var openPrice = parameters[i][OpenPriceSubscript];
		            var closePrice = parameters[i][LastPriceSubscript];
		            var chaPrice = closePrice - openPrice;
		            var sgData = [str2,openPrice,closePrice,chaPrice,"",parameters[i][LowPriceSubscript],parameters[i][HighPriceSubscript],"","","-"];
			         rawData[lent+i] = sgData; 
       		};
        	for(var i=0;i<rawData.length-1;i++){
        		if(rawData[i][0]==rawData[i+1][0]){
        			rawData.splice(i,1);
        		}
        	}
        	var newData=rawData.slice(-60);
        		CandlestickChartOption = setOption(newData);
        	if(firstTimeNumber==0){
		  			
		  	}else{
		  		myChart.setOption(CandlestickChartOption);
		  	};
		  	myChart.group="group2";
		  	document.getElementById("Candlestick").addEventListener("tap",function(){
				 if(myChart != null){
				 	setTimeout(function(){
				 		muiSpinner[1].style.display="none";
				 	},100)
					document.getElementsByClassName("buttomFix")[0].style.display="block";
						setTimeout(function(){
						 	myChart.resize();	
							myChart.setOption(CandlestickChartOption);
		        			myChart.resize();	
		        			firstTimeNumber++;
		        		},10);
			    }
		});
    }
    //设置数据参数（为画图做准备）
    function setOption(rawData){
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
    var firstTimeNum=0;
     var volumeTime=[];
    var volumeV=[];
//  var volumeTimeH=[];
//  var volumeVH=[];
    function processingCandlestickVolumeData(data){
    		var parameters = data.Parameters.Data;
    		var Len=parameters.length;
    		if(parameters == null)return;
    	    var lent=volumeV.length;
//  	    var lengt=volumeTimeH.length;
        	for(var i=0;i<Len;i++){
        			var time2=parameters[i][DateTimeStampSubscript].split(" ");
		        	var str1=time2[1].split(":");
		        	var str2=str1[0]+":"+str1[1]
//      		if(Number(parameters[i][OpenPriceSubscript])>Number(parameters[i][LastPriceSubscript])){
        			volumeTime[lent+i]=str2;
        			volumeV[lent+i]=parameters[i][VolumeSubscript];
//      		}else{
//      			volumeTimeH[lengt+i]=str2;
//      			volumeVH[lengt+i]=parameters[i][VolumeSubscript];
//      		}
        		
        			
        			
       		};
        	for(var i=0;i<volumeTime.length-1;i++){
        		if(volumeTime[i]==volumeTime[i+1]){
        			volumeTime.splice(i,1);
        			volumeV.splice(i,1);
        		}
        	}
        	CandlestickVolumeData.time=volumeTime.slice(-60);
        	CandlestickVolumeData.volume=volumeV.slice(-60);
//      	console.log("最大值"+Math.max.apply(null, CandlestickVolumeData.volume));//最大值
//			console.log("最小值"+Math.min.apply(null, CandlestickVolumeData.volume));//最小值
//			console.log("最后一条数据"+CandlestickVolumeData.volume[CandlestickVolumeData.volume.length-1]);
        	var option1= CandlestickVolumeChartSetoption1(CandlestickVolumeData);
        	CandlestickVolumeChart.group="group2";
        	if(firstTimeNum==0){
		  			
		  	}else{
		  		CandlestickVolumeChart.setOption(option1);
		  	};
		  	document.getElementById("Candlestick").addEventListener("tap",function(){
				 if(CandlestickVolumeChart != null){
						setTimeout(function(){
						 	CandlestickVolumeChart.resize();	
							CandlestickVolumeChart.setOption(option1);
		        			CandlestickVolumeChart.resize();	
		        			firstTimeNum++;
		        		},10);
			    }
		});
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
//	              {
//	                  name: '成交量',
//	                  type: 'bar',
//	                  data:volumeVH
//	              }
	          ]
	      };
        return option
    }