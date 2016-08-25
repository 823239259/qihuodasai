var aa=require.config({
        paths:{
            'echarts' :'echarts.min (2)',
            'echarts/chart/pie' :'echarts.min (2)',
        }
    });
    
var rawData = [];
    var	 myChart = null;
    var timeChart=null;
    var volumeChart=null;
    var volumeChartTime=[];
    var volumeChartPrices=[];
    var volumeChartData={
        "time":volumeChartTime,
        "prices":volumeChartPrices
    };
    var echarts;
    var time=[];
    var prices=[];
    var timeData={
        "time":time,
        "prices":prices
    };
    loadK();
    //生成一个K线图容器
    function loadK(){
        // 使用
        require(
                [
                    'echarts',
                    'echarts/chart/pie', // 使用柱状图就加载bar模块，按需加载
//                    'echarts/chart/line',
                ],
                function (ec) {

                    // 基于准备好的dom，初始化echarts图表
                    myChart = ec.init(document.getElementById('CandlestickChartDiv'));
                   
                    var option = setOption(rawData);

                    echarts=ec;
                    timeChart=ec.init(document.getElementById("timeChart"));
                    var option1=setOption1(timeData);
                    timeChart.setOption(option1);

                }
        );
		
    }
 var domnRange=document.getElementById("domnRange");
    var freshPrices=document.getElementById("freshPrices");
    var volumePricesNumber=document.getElementById("volumePricesNumber");
    var buyPrices=document.getElementById("buyPrices");
    var buyPricesNumber=document.getElementById("buyPricesNumber");
    var sellPrices=document.getElementById("sellPrices");
    var sellPricesNumber=document.getElementById("sellPricesNumber");
    function insertDATA(DATA){
    	buyPrices.innerHTML=DATA.Parameters.AskPrice1.toFixed(Transfer.name[4]);
    	buyPricesNumber.innerHTML=DATA.Parameters.AskQty1;
    	sellPrices.innerHTML=DATA.Parameters.BidPrice1.toFixed(Transfer.name[4]);
    	sellPricesNumber.innerHTML=DATA.Parameters.BidQty1;
    	volumePricesNumber.innerHTML=DATA.Parameters.LastVolume;
    	freshPrices.innerHTML=DATA.Parameters.LastPrice.toFixed(Transfer.name[4]);
    	domnRange.innerHTML=DATA.Parameters.ChangeValue.toFixed(Transfer.name[4])+" / "+DATA.Parameters.ChangeRate.toFixed(2)+"%";
    }
    function processingData(jsonData){
    	  var lent=rawData.length;
        var Len=jsonData.Parameters.length;    
        	for(var i=0;i<Len;i++){
            var openPrice = jsonData.Parameters[i].OpenPrice;
            var closePrice = jsonData.Parameters[i].LastPrice;
            var chaPrice = closePrice - openPrice;
            time1=jsonData.Parameters[i].DateTime;
            var sgData = [jsonData.Parameters[i].DateTimeStamp,openPrice,closePrice,chaPrice,"",jsonData.Parameters[i].LowPrice,jsonData.Parameters[i].HighPrice,"","","-"];
	         rawData[lent+i] = sgData;
        }; 
          time1=jsonData.Parameters[Len-1].DateTimeStamp;
        var option = setOption(rawData);
        if(myChart != null){
        	myChart.setOption(option);
        	document.getElementById("Candlestick").addEventListener("tap",function(){

			setTimeout(function(){
        			myChart.resize();	
        		},50)
       });
        }
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
    backgroundColor: '#2B2B2B',
    tooltip: {
        trigger: 'axis',
        axisPointer: {
            animation: false,
            lineStyle: {
                color: '#376df4',
                width: 2,
                opacity: 1
            }
        }
    },
    grid: {
               x: 10,
               y:20,
               x2:20,
               y2:20
           },
    xAxis: {
        type: 'category',
        data: dates,
        axisLine: { lineStyle: { color: '#8392A5' } }
    },
    yAxis: {
        scale: true,
        axisLine: { lineStyle: { color: '#8392A5' } },
        splitLine: { show: false },
        splitArea: {
                    show: false
                },
                axisLabel: {
                        inside: true,
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
    }
    function handleTime(json){
        var Len=json.Parameters.length;
        var TimeLength=timeData.time.length;
        for(var i=0;i<Len;i++){
            timeData.time[TimeLength+i]=json.Parameters[i].DateTimeStamp;
            timeData.prices[TimeLength+i]=json.Parameters[i].LastPrice;
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
       	backgroundColor: '#2B2B2B',
           tooltip : {
               show: true,
               trigger: 'axis',
               axisPointer : {
                   type : 'line'
               },
               formatter: function(params) {
                   var time  = params[0].name;
                   var val   = params[0].value;
                   var html  = time + '<br/>' +
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
		        data: data1.time,
		        axisLine: { lineStyle: { color: '#8392A5' } }
						}],	
           yAxis:  [
               {
                   type: 'value',
                   scale: true,
                   position:"left",
                    axisLine: { lineStyle: { color: '#8392A5' } },
                   splitArea: {
                       show: false
                   },
                    axisLabel: {
                        inside: true,
                        margin: 4
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
               x: 10,
               y:20,
               x2:20,
               y2:20
           },
           series: {type: 'line',
               label: {
                   normal: {
                       show: true,
                       position: 'inside'
                   },
               },
               lineStyle: {
                   normal: {
                       width: 1,
                       color: "#fda729"
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
        var VolumeLength=volumeChartData.time.length;
        for(var i=0;i<Len;i++){
            volumeChartData.time[VolumeLength+i]=json.Parameters[i].DateTimeStamp;
            volumeChartData.prices[VolumeLength+i]=json.Parameters[i].Volume;
        };
        var option =volumeChartSetOption(volumeChartData);
        if(volumeChart != null){
        }
    }
    function volumeChartSetOption(data) {
        var  dataVolume=volumeChartData;
      var  option = {
      	backgroundColor: '#2B2B2B',
          tooltip: {
              trigger: 'axis'
          },
          legend: {
              data:['最新成交价']
          },
            toolbox: {
                show: false,
            },
             animation: false,
			grid: {
               x: 50,
               y:30,
               x2:20,
               y2:30
           },

          xAxis:[
              {
                  type : 'category',
                  position:'bottom',
                  boundaryGap : true,
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
                axisLabel: {
                    formatter: function (a) {
                        a = +a;
                        return isFinite(a)
                            ? echarts.format.addCommas(+a / 1000)
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
                  data:dataVolume.prices
              }
          ]
      };
        return option
}