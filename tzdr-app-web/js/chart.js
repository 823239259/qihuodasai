//var href = window.location.href;
    // 路径配置
    mui.plusReady(function(){
    	var bb=plus.webview.currentWebview();
				var CommodityNo=document.getElementById("CommodityNo");
				var mainTitleFirst=document.getElementsByClassName("mainTitleFirst")[0];
				mainTitleFirst.innerHTML=bb.name[0];
				CommodityNo.innerHTML=bb.name[2]+bb.name[1];
//				console.log(bb.name[3])
				document.getElementById("TradingCenter").addEventListener("tap",function(){
					mui.openWindow({
						url:"trade.html",
						id:"trade.html",
					});
				});
    var aa=require.config({
        paths:{
            'echarts' :'../../js/echarts.min (2)',
            'echarts/chart/pie' :'../../js/echarts.min (2)',
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
                    /* document.getElementById('main').innerHTML = ""; */
                    // 基于准备好的dom，初始化echarts图表
                    myChart = ec.init(document.getElementById('CandlestickChartDiv'));
                   
                    var option = setOption(rawData);
                    // 为echarts对象加载数据
//                  setTimeout(function(){
//                  	 myChart.setOption(option);
//                  },1000);
                    echarts=ec;
                    timeChart=ec.init(document.getElementById("timeChart"));
                    var option1=setOption1(timeData);
                    timeChart.setOption(option1);
//                  volumeChart=ec.init(document.getElementById("volumeChart"));
//                  var volumeChartOption=volumeChartSetOption();
//                  volumeChart.setOption(volumeChartOption);;
//                  ec.connect("group1");
                }
        );
		
    }

    function sendMessage(method,parameters){
        socket.send('{"Method":"'+method+'","Parameters":'+parameters+'}');
    }
 	var time1;
    var url = "ws://192.168.0.213:9006";
    var socket = new WebSocket(url);
    var setIntval = null;
    socket.onopen = function(evt){
        sendMessage('Login','{"serName":"13677622344","PassWord":"a123456"}');
    };
    socket.onclose = function(evt){
    	setIntval.clear();
    };
    socket.onmessage = function(evt){
        var data = evt.data;
        var jsonData = JSON.parse(data);
        var method = jsonData.Method;
        if(method=="OnRspLogin"){
		var date=new Date();
   sendMessage('QryHistory','{"ExchangeNo":"'+bb.name[3]+'","CommodityNo":"'+bb.name[2]+'","ContractNo":"'+bb.name[1]+'"}');
   sendMessage('Subscribe','{"ExchangeNo":"'+bb.name[3]+'","CommodityNo":"'+bb.name[2]+'","ContractNo":"'+bb.name[1]+'"}');
   		
        setIntval = setInterval(function(){
            sendMessage('QryHistory','{"ExchangeNo":"'+bb.name[3]+'","CommodityNo":"'+bb.name[2]+'","ContractNo":"'+bb.name[1]+'","BeginTime":"'+time1+'","HisQuoteType":1}');
        },30000);
        }else if(method == "OnRspQryHistory"){
            var jsonData=JSON.parse(evt.data);
            processingData(jsonData);
            handleTime(jsonData);
            handleVolumeChartData(jsonData);
        }else if(method == "OnRtnQuote"){
        	var DATA=JSON.parse(evt.data);
        	insertDATA(DATA);
        }
    };
    socket.onerror = function(evt){

    };
    var domnRange=document.getElementById("domnRange");
    var freshPrices=document.getElementById("freshPrices");
    var volumePricesNumber=document.getElementById("volumePricesNumber");
    var buyPrices=document.getElementById("buyPrices");
    	var buyPricesNumber=document.getElementById("buyPricesNumber");
    	var sellPrices=document.getElementById("sellPrices");
    	var sellPricesNumber=document.getElementById("sellPricesNumber");
    function insertDATA(DATA){
    	buyPrices.innerHTML=DATA.Parameters.AskPrice1.toFixed(bb.name[4]);
    	buyPricesNumber.innerHTML=DATA.Parameters.AskQty1;
    	sellPrices.innerHTML=DATA.Parameters.BidPrice1.toFixed(bb.name[4]);
    	sellPricesNumber.innerHTML=DATA.Parameters.BidQty1;
    	volumePricesNumber.innerHTML=DATA.Parameters.LastVolume;
    	freshPrices.innerHTML=DATA.Parameters.LastPrice.toFixed(bb.name[4]);
    	domnRange.innerHTML=DATA.Parameters.ChangeValue.toFixed(bb.name[4])+" / "+DATA.Parameters.ChangeRate.toFixed(2)+"%";
    }
    var ww=1;
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
    backgroundColor: '#21202D',
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
//  dataZoom: [{
//      textStyle: {
//          color: '#8392A5'
//      },
//      handleIcon: 'M10.7,11.9v-1.3H9.3v1.3c-4.9,0.3-8.8,4.4-8.8,9.4c0,5,3.9,9.1,8.8,9.4v1.3h1.3v-1.3c4.9-0.3,8.8-4.4,8.8-9.4C19.5,16.3,15.6,12.2,10.7,11.9z M13.3,24.4H6.7V23h6.6V24.4z M13.3,19.6H6.7v-1.4h6.6V19.6z',
//      handleSize: '80%',
//      dataBackground: {
//          areaStyle: {
//              color: '#8392A5'
//          },
//          lineStyle: {
//              opacity: 0.8,
//              color: '#8392A5'
//          }
//      },
//      handleStyle: {
//          color: '#fff',
//          shadowBlur: 3,
//          shadowColor: 'rgba(0, 0, 0, 0.6)',
//          shadowOffsetX: 2,
//          shadowOffsetY: 2
//      }
//  }, {
//      type: 'inside'
//  }],
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
       	backgroundColor: '#21202D',
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
//           xAxis: {
//               show:false,
//               type : 'category',
//               splitLine: {
//                   show: true
//               },
////               splitNumber: 2,
//               data : data1.time
//           },
//         xAxis:[
//             {
//                 show: true,
//                 type : 'category',
//                 position:'bottom',
//                 boundaryGap : true,
//                 axisTick: {onGap:false},
//                 splitLine: {show:false},
//                 axisLine: { lineStyle: { color: '#8392A5' } },
//                 data :volumeChartData.time
//             }
//         ],
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
//          volumeChart.setOption(option);
//         volumeChart.group="group1";
        }
    }
    function volumeChartSetOption(data) {
        var  dataVolume=volumeChartData;
      var  option = {
      	backgroundColor: '#21202D',
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
//			grid: {
//             x: 80,
//             y:5,
//             x2:20,
//             y2:30
//        },
			grid: {
               x: 50,
               y:30,
               x2:20,
               y2:30
           },
//grid: {
//          top: '12%',
//          left: '2%',
//          right: '10%',
//          containLabel: true
//      },
//        dataZoom: [{
//            textStyle: {
//                color: '#8392A5'
//            },
//            handleIcon: 'M10.7,11.9v-1.3H9.3v1.3c-4.9,0.3-8.8,4.4-8.8,9.4c0,5,3.9,9.1,8.8,9.4v1.3h1.3v-1.3c4.9-0.3,8.8-4.4,8.8-9.4C19.5,16.3,15.6,12.2,10.7,11.9z M13.3,24.4H6.7V23h6.6V24.4z M13.3,19.6H6.7v-1.4h6.6V19.6z',
//            handleSize: '80%',
//            dataBackground: {
//                areaStyle: {
//                    color: '#8392A5'
//                },
//                lineStyle: {
//                    opacity: 0.8,
//                    color: '#8392A5'
//                }
//            },
//            handleStyle: {
//                color: '#fff',
//                shadowBlur: 3,
//                shadowColor: 'rgba(0, 0, 0, 0.6)',
//                shadowOffsetX: 2,
//                shadowOffsetY: 2
//            }
//        }, {
//            type: 'inside'
//        }],
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
//        yAxis: [
//            {
//                type: 'value',
//                scale: false,
////                  axisLabel:{
////                      
////                  },
//                  axisLabel: {
//                  	show:true,
//                      formatter: function (a) {
//                          console.log(a);
//                          a = +a;
//                          return isFinite(a)
//                                  ? echarts.format.addCommas(+a / 100)
//                                  : '';
//                      }
//                  },
//                name: '成交量(万)',
//            }
//        ],
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
    
//if (mui.cacheUser.){
//				mui.openWindow({
//				url:"trade.html",
//				id:"trade.html",
//				extras:{
//					traderBond:traderBond,
//					businessType:8
//				}
//				});
//			}
//			else
//			{
//				mui.openWindow({url:"../login/login.html",id:"login",extras:{backpageID:"cp"}});
//			}
	});