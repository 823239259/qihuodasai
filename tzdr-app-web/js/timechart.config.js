    var time=[];
    var prices=[];
    var timeLabel=[]
    var timeData={
        "time":time,
        "prices":prices,
        "timeLabel":timeLabel
    };
    var volumeChartTime=[];
    var volumeChartPrices=[];
    var volumeChartData={
        "time":volumeChartTime,
        "volume":volumeChartPrices
    };
    var timePrice=[];
     var timeLabel=[];
    function handleTime(json){
        var Len=json.Parameters.length;
        var TimeLength=timeData.timeLabel.length;
       	var Parameters=json.Parameters;
       	var leng=timePrice.length;
        for(var i=0;i<Len;i++){
        	var time2=Parameters[i].DateTimeStamp.split(" ");
        	var str1=time2[1].split(":");
        	var str2=str1[0]+":"+str1[1];
			timeData.timeLabel[TimeLength+i]=str2;
        	timeData.prices[TimeLength+i]=parseFloat(Parameters[i].LastPrice).toFixed(doSize); 	
        }
		for(var i=0;i<timeData.timeLabel.length-1;i++){
			if(timeData.timeLabel[i]==timeData.timeLabel[i+1]){
				timeData.timeLabel.splice(i,1);
				timeData.prices.splice(i,1);
			}else{
				
			}
		}
        var option = setOption1();
        if(timeChart != null){
            timeChart.setOption(option);
            timeChart.group="group1";
        }
    }
    function setOption1(){
        var  data1=timeData;
       var  option = {
       	backgroundColor: 'rgba(43, 43, 43, 0)',
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
			        boundaryGap: false
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
               x2:20,
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
               data:data1.prices
           }
       }
        return option
    }
    function handleVolumeChartData(json){
        var Len=json.Parameters.length;
        var Parameters=json.Parameters;
        var VolumeLength=volumeChartData.time.length;
        for(var i=0;i<Len;i++){
        	var time2=Parameters[i].DateTimeStamp.split(" ");
        	var str1=time2[1].split(":");
        	var str2=str1[0]+":"+str1[1];
            volumeChartData.time[VolumeLength+i]=str2;
            volumeChartData.volume[VolumeLength+i]=Parameters[i].Volume;
        };
        var TimeLength= volumeChartData.time.length;
		for(var i=0;i<volumeChartData.time.length-1;i++){
			if(volumeChartData.time[i]==volumeChartData.time[i+1]){
				volumeChartData.time.splice(i,1);
				volumeChartData.volume.splice(i,1);
			}
		}
        var option =volumeChartSetOption(volumeChartData);
        if(volumeChart != null){
            volumeChart.setOption(option);
           	volumeChart.group="group1";
        }
    }
    function volumeChartSetOption(data) {
        var  dataVolume=volumeChartData;
      var  option = {
      	backgroundColor: '#2B2B2B',
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
              data:['最新成交价']
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
                 boundaryGap: false,
                  axisTick: {onGap:false},
                  splitLine: {show:false},
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
//                 markLine: {
//		                data: [
//		                    {type: 'average', name: '平均值'},
//		                    [{
//		                        symbol: 'none',
//		                        x: '90%',
//		                        yAxis: 'max'
//		                    }, {
//		                        symbol: 'circle',
//		                        label: {
//		                            normal: {
//		                                position: 'start',
//		                                formatter: '最大值'
//		                            }
//		                        },
//		                        type: 'max',
//		                        name: '最高点'
//		                    }]
//		                ]
//		            },
                  data:dataVolume.volume
              }
          ]
      };
        return option
}