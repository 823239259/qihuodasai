	//配置分时线
    function setOption(x){
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
			        data: data1.timeLabel,
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
	                 {name: '标线2起点', value: x, xAxis: "1", yAxis: x},     // 当xAxis或yAxis为数值轴时，不管传入是什么，都被理解为数值后做空间位置换算
        			{name: '标线2终点', xAxis: "2", yAxis: x}
                ]
                },
               data:data1.prices
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
	                  data : dataVolume.time
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
	                  data:dataVolume.volume
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
	function handleTimeChartData(json){
    	var dosizeL=$("#doSize").val();
        var Len=json.Parameters.Data.length;
        var TimeLength=timeData.timeLabel.length;
       	var Parameters=json.Parameters.Data;
       	var leng=timePrice.length;
        for(var i=0;i<Len;i++){ 
        	var time2=Parameters[i][DateTimeStampSubscript].split(" ");
        	var str1=time2[1].split(":");
        	var str2=str1[0]+":"+str1[1];
			timeData.timeLabel[TimeLength+i]=str2;
        	timeData.prices[TimeLength+i]=(Parameters[i][LastPriceSubscript]).toFixed(dosizeL);	
        	timeData.time[TimeLength+i]=Parameters[i][DateTimeStampSubscript]
        }
        if(timeChart != null){
        	var positionValue=getPositionValue();
             var option = setOption1(positionValue);
            timeChart.setOption(option);
            timeChart.resize();
            timeChart.group="group1";
           
        }

    }