
/**
 *引入echart配置 **/
	var requireEchart=require.config({
	    paths:{
	        'echarts' :'./../static/script/qutrade/echarts.min',
	        'echarts/chart/pie' :'./../static/script/qutrade/echarts.min',
	    }
	});
	var timeChart=null;
	//webSocket链接/名字/密码
	var url = "ws://quote.vs.com:9002";
    var username="13677622344";
    var password="a123456";
    function loadK(){
        require(
                [
                    'echarts',
                    'echarts/chart/pie', // 使用柱状图就加载bar模块，按需加载
                ],
                function (ec) {
                    // 基于准备好的dom，初始化echarts图表
                    timeChart = ec.init(document.getElementById('timeChart'));
                    echarts=ec;
                    volumeChart=ec.init(document.getElementById("volumeChart"));
                   	 ec.connect("group1");
                   	 timeChart.setOption(option)
						setInterval(function () {
						
						    for (var i = 0; i < 5; i++) {
						        data.shift();
						        data.push(randomData());
						    }
						
						    timeChart.setOption({
						        series: [{
						            data: data
						        }]
						    });
						}, 1000);
                }
        );
		
    };
    //发送方法
	function masendMessage(method,parameters){
        marketSocket.send('{"Method":"'+method+'","Parameters":'+parameters+'}');
    }
	marketSocket = new WebSocket(url);
    marketSocket.onopen = function(evt){
        masendMessage('Login','{"UserName":"'+username+'","PassWord":"'+password+'"}');
    };
    marketSocket.onclose = function(evt){
    };
	marketSocket.onerror = function(evt){
    };
    //发送方法
	marketSocket.onmessage = function(evt){
		
	}












function randomData() {
    now = new Date(+now + oneDay);
    value = value + Math.random() * 21 - 10;
    return {
        name: now.toString(),
        value: [
            [now.getFullYear(), now.getMonth() + 1, now.getDate()].join('/'),
            Math.round(value)
        ]
    }
}

var data = [];
var now = +new Date(1997, 9, 3);
var oneDay = 24 * 3600 * 1000;
var value = Math.random() * 1000;
for (var i = 0; i < 1000; i++) {
    data.push(randomData());
}

var option = {
    title: {
        text: '动态数据 + 时间坐标轴'
    },
    tooltip: {
        trigger: 'axis',
        formatter: function (params) {
            params = params[0];
            var date = new Date(params.name);
            return date.getDate() + '/' + (date.getMonth() + 1) + '/' + date.getFullYear() + ' : ' + params.value[1];
        },
        axisPointer: {
            animation: false
        }
    },
    xAxis: {
        type: 'time',
        splitLine: {
            show: false
        }
    },
    yAxis: {
        type: 'value',
        boundaryGap: [0, '100%'],
        splitLine: {
            show: false
        }
    },
    series: [{
        name: '模拟数据',
        type: 'line',
        showSymbol: false,
        hoverAnimation: false,
        data: data
    }]
};












//$(function() {
//  
//  
//  var firstTimeLength = 0;
//  var cc = null;
//
//
//
// 
//      //加载K线图数据模型
//      function loadKData(currenCommodityNo,currenContractNo,currenExchangeNo,hisQuoteType){
//          /* CL 1612 NYMEX*/
//          var  carbon_time = document.getElementsByClassName("carbon_time");
//          for(var i = 0;i<carbon_time.length;i++){
//              carbon_time[i].onclick = function(){
//                  clearInterval(cc);
//                  var obj = this;
//                  var hisQuoteTypes = obj.getAttribute("data");
//                  var hisQuoteType =  hisQuoteTypes;
//                  loadKData(currenCommodityNo,currenContractNo,currenExchangeNo,hisQuoteType);
//                  $("#lightButton").css("color","#999999");
//			        $("#container1").css("z-index","997");
//			        $("#container").css("z-index","998");
//              }
//          }
//          masendMessage('QryHistory','{"ExchangeNo":"'+currenExchangeNo+'","CommodityNo":"'+currenCommodityNo+'","ContractNo":"'+currenContractNo+'","Count":400,"HisQuoteType":'+hisQuoteType+'}');
//          masendMessage('Subscribe','{"ExchangeNo":"'+currenExchangeNo+'","CommodityNo":"'+currenCommodityNo+'","ContractNo":"'+currenContractNo+'"}');
//          cc = setInterval(function(){
//              masendMessage('QryHistory','{"ExchangeNo":"'+currenExchangeNo+'","CommodityNo":"'+currenCommodityNo+'","ContractNo":"'+currenContractNo+'","Count":400,"HisQuoteType":'+hisQuoteType+'}');
//          },60000);
//      }
//
//      function click(div){
//          for(var i = 0;i<div.length;i++){
//              div[i].onclick = function(){
//                  clearInterval(cc);
//                  var obj = this;
//                  dataLight=[];
//                  var da = obj.getAttribute("data");
//                  if(da != null){
//                      var daArray = da.split("&");
//                      var currenCommodityNo = daArray[0];
//                      var currenContractNo = daArray[1];
//                      var currenExchangeNo = daArray[2];
//                      var hisQuoteType =  firstTimeLength;
//                      loadKData(currenCommodityNo,currenContractNo,currenExchangeNo,hisQuoteType);
//                  }
//                  /*切换品种  换回1分钟视图*/
//                  var  minute4 = document.getElementsByClassName("carbon_time")[0];
//                  var  minute = document.getElementsByClassName("carbon_time");
//                  for(var i= 0; i< minute.length; i++){
//                      minute[i].style.color = '#999';
//                  }
//                  minute4.style.color = '#ffb319';
//              }
//          }
//      }
////      function chechDataK(data,type){
////          var dataC=[];
////          var volume=[];
////          var dataall=data.Data;
////          //console.log(dataall);
////          for (var i=0; i < dataall.length; i++) {
////              var timestamp = Math.round(new Date(dataall[i][0]).getTime()/1000);
////              /*var exs = 3600*8;*/
////              //var zhi = parseInt((parseInt(timestamp)+ parseInt(exs)));
//////              timestamp = timestamp+"000";
////              timestamp = parseInt(timestamp);
////              dataC.push([
////                  timestamp,
////                  parseFloat(dataall[i][2].toFixed(4)),
////                  parseFloat(dataall[i][4].toFixed(4)),
////                  parseFloat(dataall[i][3].toFixed(4)),
////                  parseFloat(dataall[i][1].toFixed(4))
////              ]);
////              volume.push([
////                  timestamp,
////                  dataall[i][7]
////              ]);
////          }
////          for(var i=0;i<volume.length-1;i++){
////              if(volume[i][0]==volume[i+1][0]){
////                  volume.splice(i,1);
////              }
////          }
////          for(var i=0;i<dataC.length-1;i++){
////              if(dataC[i][0]==dataC[i+1][0]){
////                  dataC.splice(i,1);
////              }
////          }
////			var text=$("#commodity_title").text();
////			var length=$(".tab_content .position0").length;
////			var positionValue=0
////			if(length!=0){
////				for(var i=0;i<length;i++){
////					var text1=$(".tab_content .position0").eq(i).text();
////					if(text.indexOf(text1)>=0){
////						positionValue=$(".tab_content .position3").eq(i).text();
////					}
////				}
////			}
////          $('#container').highcharts('StockChart', {
////              chart: {
////                  backgroundColor:'#333333'
////              },
////              title : {
////                  text : ''
////              },
////              subtitle: {
////                  text: ''
////              },
////              yAxis: [{
////                  title: {
////                      text: 'Price'
////                  },
////                  labels: {
////                      style: {
////                          color: '#ffffff'
////                      }
////                  },
////                  height: 120,
//// 					 plotLines: [{
////                      value: positionValue,
////                      color: 'red',
////                      dashStyle: 'shortdash',
////                      width: 1,
////                      label: {
////                          style: {
////                              color: '#ffffff'
////                          },
////                          text: '持仓均线 '+positionValue
////                      }
////                  }]
////              },{
////                  title: {
////                      text: '成交量'
////                  },
////                  labels: {
////                      style: {
////                          color: '#ffffff'
////                      }
////                  },
////                  top: 166,
////                  height: 80,
////                  offset: 0,
////                  lineWidth: 2
////              },{
////                  title: {
////                      text: 'MACD'
////                  },
////                  labels: {
////                      style: {
////                          color: '#ffffff'
////                      }
////                  },
////                  top: 247,
////                  height: 100,
////                  offset: 0,
////                  lineWidth: 2
////              }],
////              tooltip: {
////                  crosshairs: true,
////                  shared: true
////              },
////              rangeSelector: {
////                  // 缩放选择按钮，是一个数组。
////                  // 其中type可以是： 'millisecond', 'second', 'minute', 'day', 'week', 'month', 'ytd' (year to date), 'year' 和 'all'。
////                  // 其中count是指多少个单位type。
////                  // 其中text是配置显示在按钮上的文字
////                  buttons: [{
////                      type: 'minute',
////                      count: 60*type,
////                      text: '分钟'
////                  }],
////                  // 默认选择域：0（缩放按钮中的第一个）、1（缩放按钮中的第二个）……
////                  selected: 0,
////                  // 是否允许input标签选框
////                  inputEnabled: false
////              },
////              /* legend: {
////               enabled: true,
////               layout: 'vertical',
////               align: 'right',
////               verticalAlign: 'middle',
////               borderWidth: 0
////               },
////               plotOptions: {
////               series: {
////               marker: {
////               enabled: false
////               }
////               }
////               }, */
////              plotOptions: {
////                  candlestick: {
////                      color: '#30bf30',
////                      upColor: '#ff4040',
////                      lineColor: '#30bf30',
////                      upLineColor: '#ff4040'
////                  }
////              },
////              series : [{
////                  name: 'K线',
////                  type : 'line', /*candlestick line*/
////                  id: 'primary',
////                  animation: false,
////                  data : dataC/*,
////                   visible: false*/
////              },{
////                  name: '',
////                  type : 'candlestick',
////                  id: 'primary',
////                  animation: false,
////                  data : dataC/*,
////                   visible: false*/
////              },{
////                  type: 'column',
////                  name: '成交量',
////                  animation: false,
////                  data: volume,
////                  yAxis: 1
////              },{
////                  name : 'MACD',
////                  linkedTo: 'primary',
////                  yAxis: 2,
////                  showInLegend: true,
////                  type: 'trendline',
////                  animation: false,
////                  algorithm: 'MACD'
////              }, {
////                  name : 'DEA',
////                  linkedTo: 'primary',
////                  yAxis: 2,
////                  showInLegend: true,
////                  type: 'trendline',
////                  animation: false,
////                  algorithm: 'signalLine'
////              }, {
////                  name: 'DIF',
////                  linkedTo: 'primary',
////                  yAxis: 2,
////                  showInLegend: true,
////                  animation: false,
////                  type: 'histogram'
////              }]
////          });
////          $(".highcharts-series-0").hide();
//      }
//      function chechData(data){
//          var dataC=[];
//          var volume=[];
//          var dataall=data.Data;
//          for (var i=0; i < dataall.length; i++) {
//              var timestamp = Math.round(new Date(dataall[i][0]).getTime()/1000);
//              /*var exs = 3600*8;
//              var zhi = parseInt((parseInt(timestamp)+ parseInt(exs)));*/
//              timestamp = timestamp+"000";
//              timestamp = parseInt(timestamp);
//              dataC.push([
//                  timestamp,
//                  parseFloat(dataall[i][2].toFixed(4)),
//                  parseFloat(dataall[i][4].toFixed(4)),
//                  parseFloat(dataall[i][3].toFixed(4)),
//                  parseFloat(dataall[i][1].toFixed(4))
//              ]);
//              volume.push([
//                  timestamp,
//                  dataall[i][7]
//              ]);
//          }
//          for(var i=0;i<volume.length-1;i++){
//              if(volume[i][0]==volume[i+1][0]){
//                  volume.splice(i,1);
//              }
//          }
//          for(var i=0;i<dataC.length-1;i++){
//              if(dataC[i][0]==dataC[i+1][0]){
//                  dataC.splice(i,1);
//              }
//          }
//			var text=$("#commodity_title").text();
//			var length=$(".tab_content .position0" ).length;
//			var positionValue=0
//			if(length!=0){
//				for(var i=0;i<length;i++){
//					var text1=$(".tab_content .position0").eq(i).text();
//					if(text.indexOf(text1)>=0){
//						positionValue=$(".tab_content .position3").eq(i).text();
//					}
//				}
//			}
//			console.log(positionValue);
////          $('#container').highcharts('StockChart', {
////
////              chart: {
////                  backgroundColor:'#333333'
////              },
////              title : {
////                  text : ''
////              },
////              subtitle: {
////                  text: ''
////              },
////              yAxis: [{
////                  title: {
////                      text: 'Price'
////                  },
////                  labels: {
////                      style: {
////                          color: '#ffffff'
////                      }
////                  },
////                  height: 120,
//// 					 plotLines: [{
////                      value: positionValue,
////                      color: 'red',
////                      dashStyle: 'shortdash',
////                      width: 1,
////                      label: {
////                          style: {
////                              color: '#ffffff'
////                          },
////                          text: '持仓均线 '+positionValue
////                      }
////                  }]
////              },{
////                  title: {
////                      text: '成交量'
////                  },
////                  labels: {
////                      style: {
////                          color: '#ffffff'
////                      }
////                  },
////                  top: 166,
////                  height: 80,
////                  offset: 0,
////                  lineWidth: 2
////              },{
////                  title: {
////                      text: 'MACD'
////                  },
////                  labels: {
////                      style: {
////                          color: '#ffffff'
////                      }
////                  },
////                  top: 247,
////                  height: 100,
////                  offset: 0,
////                  lineWidth: 2
////              }],
////              tooltip: {
////                  crosshairs: true,
////                  shared: true
////              },
////              rangeSelector: {
////                  buttons: [{
////                      type: 'minute',
////                      count: 120,
////                      text: '分钟'
////                  }],
////                  selected: 0
////              },
////              series : [{
////                  name: '分时线',
////                  type : 'line', /*candlestick line*/
////                  id: 'primary',
////                  animation: false,
////                  data : dataC
////              },{
////                  name: '',
////                  type : 'candlestick',
////                  id: 'primary',
////                  animation: false,
////                  data : dataC
////              },{
////                  type: 'column',
////                  name: '成交量',
////                  animation: false,
////                  data: volume,
////                  yAxis: 1
////              },{
////                  name : 'MACD',
////                  linkedTo: 'primary',
////                  yAxis: 2,
////                  showInLegend: true,
////                  type: 'trendline',
////                  animation: false,
////                  algorithm: 'MACD'
////              }, {
////                  name : 'DEA',
////                  linkedTo: 'primary',
////                  yAxis: 2,
////                  showInLegend: true,
////                  type: 'trendline',
////                  animation: false,
////                  algorithm: 'signalLine'
////              }, {
////                  name: 'DIF',
////                  linkedTo: 'primary',
////                  yAxis: 2,
////                  showInLegend: true,
////                  animation: false,
////                  type: 'histogram'
////              }]
////          });
////          $(".highcharts-series-1").hide();
////      }
////
////  };
//              /**************************闪电图********************************/
//  var dataLight=[];
//      Highcharts.setOptions({
//      global : {
//          useUTC : false
//      }
//  });
//  function dealLightData(data){
////  	 var dataLight=[];
//      var dataall=data.Parameters;
//      var text=$("#commodity_title").text();
//      if(text.indexOf(dataall.CommodityNo)>=0){
//          var timestamp = Math.round(new Date().getTime()/1000)
////          var exs = 3600*8;
////          var zhi = parseInt((parseInt(timestamp)+ parseInt(exs)));
////          timestamp = zhi+"000";
////          timestamp = parseInt(timestamp);
//          dataLight.push([timestamp,dataall.LastPrice]);
//          dataLight=dataLight.slice(-500);
//      }
//      
//      
//      $('#container1').highcharts('StockChart', {
//          chart: {
//              backgroundColor:'#333333'
//          },
//          yAxis: [{
//              title: {
//                  text: '闪电图'
//              },
//              labels: {
//                  style: {
//                      color: '#ffffff'
//                  }
//              },
//              height: 330,
////              lineWidth: 2
//          }],
//          navigator : {
//              enabled : false
//          },
//          tooltip: {
//              enabled : false,
//              crosshairs: false,
//              shared: false
//          },
//          series : [{
//              name: '闪电图',
//              type : 'line', /*candlestick line*/
////              id: 'primary',
//              animation: false,
//              data : dataLight
//          }]
//      });
//  }
//  $("#lightButton").click(function(){
//      $("#lightButton").css("color","rgb(255, 179, 25)");
//      $(".carbon_time").css("color","#999999");
//      $("#container1").css("z-index","998");
//      $("#container").css("z-index","997");
//  })
//});