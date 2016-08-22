//var href = window.location.href;
    // 路径配置
    var aa=require.config({
        paths:{
            'echarts' :'../../js/echarts',
            'echarts/chart/pie' :'../../js/echarts'
        }
    });
    var rawData = [];
    var loadCount = 0;
    var loadKcount = 0 ;
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
                    setTimeout(function(){
                    	 myChart.setOption(option);
                    },1000);
                   
                    echarts=ec;
                    timeChart=ec.init(document.getElementById("timeChart"));
                    var option1=setOption1(timeData);
                    timeChart.setOption(option1);
                    volumeChart=ec.init(document.getElementById("volumeChart"));
                    var volumeChartOption=volumeChartSetOption();
                    volumeChart.setOption(volumeChartOption);;
                    ec.connect("group1");
                }
        );

    }

    function sendMessage(method,parameters){
        socket.send('{"Method":"'+method+'","Parameters":'+parameters+'}');
    }

    var url = "ws://socket.dktai.com:9002";
    var socket = new WebSocket(url);
    socket.onopen = function(evt){
        sendMessage('Login','{"serName":"13677622344","PassWord":"a123456"}');
    };
    socket.onclose = function(evt){
    };
    socket.onmessage = function(evt){
        var data = evt.data;
        var jsonData = JSON.parse(data);
        var method = jsonData.Method;
        if(method=="OnRspLogin"){
            sendMessage('QryCommodity','""')
        }else if(method == "OnRspQryCommodity"){
            var commoditys = jsonData.Parameters;
            var size = commoditys.length;
            var setTime=null;
            sendMessage('QryHistory','{"CommodityNo":"CL","ContractNo":"'+1609+'"}');
        setTimeout(function(){
            setInterval(function(){
                sendMessage('QryHistory','{"CommodityNo":"CL","ContractNo":"'+1609+'"}');
					
            },30000);
        },30000);

        }else if(method == "OnRspQryHistory"){
            var jsonData=JSON.parse(evt.data);
            processingData(jsonData);
            handleTime(jsonData);
            handleVolumeChartData(jsonData);
        }
    };
    socket.onerror = function(evt){

    };
    function processingData(jsonData){
        var Len=jsonData.Parameters.length;
        for(var i=0;i<Len;i++){
            var openPrice = jsonData.Parameters[i].OpenPrice;
            var closePrice = jsonData.Parameters[i].LastPrice;
            var chaPrice = closePrice - openPrice;
            var sgData = [jsonData.Parameters[i].DateTime,openPrice,closePrice,chaPrice,"",jsonData.Parameters[i].LowPrice,jsonData.Parameters[i].HighPrice,"","","-"];
            rawData[i] = sgData;
        };
        var option = setOption(rawData);
        
        if(myChart != null){
				setTimeout(function(){
					myChart.setOption(option);
					 var CandlestickChart=document.getElementById("CandlestickChart");
					 setInterval(function(){
					 	if(CandlestickChart.offsetWidth==0){
					 	}else{
					 		myChart.resize();
					 	}
					 },1000)
				},1000)
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
               tooltip: [{
                        trigger: 'axis',
                axisPointer: {
                    animation: false,
                    lineStyle: {
                   color: '#376df4',
                   width: 2,
                  opacity: 1
            }
                }}
                       ],
            grid: {
                left: '5px',
                right: '5px',
                top: '10px',
                bottom: '10px',
                containLabel: true
            },
//          title: {
//              text: '1分钟K线',
//              textStyle: {
//                  color : "#ffcc33"
//              }
//          },
            backgroundColor: '#fffff',
            xAxis:[
                {
                    type: 'category',
//                    data: dates,
                    data:volumeChartData.time,
                    scale: true,
                    boundaryGap : true,
                    axisLabel: {
                        show: true,
                        margin: 8
                    },
                    axisTick: {
                        show: false
                    },
                    axisLine: {
                        show:false,
                        onZero: true,
                        lineStyle: {
                            color: "black",
                            width: 1
                        }
                    },
                    splitLine: {
                        show: true,
                        lineStyle: {
                            color:"#eee"
                        }
                    },
                    splitNumber: 2,
                }
            ],
            yAxis:
                    [
            {
                type: 'value',
                scale: true,
                splitArea: {
                    show: false
                },
                axisLabel: {
                    inside: true,
                    margin: 4
                },
                axisTick: {
                    show: false
                },
                axisLine: {
                    show: false,
                    lineStyle: {
                        color: "black"
                    }
                },
                splitNumber: 2,
                splitLine: {
                    show: true,
                    lineStyle: {
                        color: "#eee"
                    }
                }
            }
                    ],
            dataZoom: [{
                textStyle: {
                    color: '#8392A5'
                },
                handleIcon: 'M10.7,11.9v-1.3H9.3v1.3c-4.9,0.3-8.8,4.4-8.8,9.4c0,5,3.9,9.1,8.8,9.4v1.3h1.3v-1.3c4.9-0.3,8.8-4.4,8.8-9.4C19.5,16.3,15.6,12.2,10.7,11.9z M13.3,24.4H6.7V23h6.6V24.4z M13.3,19.6H6.7v-1.4h6.6V19.6z',
                handleSize: '80%',
                dataBackground: {
                    areaStyle: {
                        color: '#8392A5'
                    },
                    lineStyle: {
                        opacity: 0.8,
                        color: '#8392A5'
                    }
                },
                handleStyle: {
                    color: '#fff',
                    shadowBlur: 3,
                    shadowColor: 'rgba(0, 0, 0, 0.6)',
                    shadowOffsetX: 2,
                    shadowOffsetY: 2
                }
            }, {
                type: 'inside'
            }],
            animation: true,
            series: [
                {
                    type: 'candlestick',
                    name:"1分钟K线",
                    data: data,
                    itemStyle: {
                        normal: {
                            color: 'rgb(246, 100, 70)',
                            color0: 'rgb(192, 231, 140)',
                            borderColor: 'rgb(246, 100, 70)',
                            borderColor0: 'rgb(192, 231, 140)'
                        }
                    },
                    label: {
                        normal: {
                            show: true,
                            position: 'inside'
                        }
                    },
                    symbolSize: 2
                }
            ]
        };
        return option;
    }
    function handleTime(json){
        var Len=json.Parameters.length;
        for(var i=0;i<Len;i++){
            timeData.time[i]=json.Parameters[i].DateTime;
            timeData.prices[i]=json.Parameters[i].LastPrice;
        }
        var option = setOption1();
        if(timeChart != null){
            timeChart.setOption(option);
            timeChart.group="group1";
            setTimeout(function(){

            },1000)

        }
    }
    function setOption1(){
        var  data1=timeData;
        console.log(data1);
       var  option = {
//         title: {
//             text: '分时线'
//         },
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
           dataZoom: [{
               show:false,
               textStyle: {
                   color: '#8392A5'
               },
               handleIcon: 'M10.7,11.9v-1.3H9.3v1.3c-4.9,0.3-8.8,4.4-8.8,9.4c0,5,3.9,9.1,8.8,9.4v1.3h1.3v-1.3c4.9-0.3,8.8-4.4,8.8-9.4C19.5,16.3,15.6,12.2,10.7,11.9z M13.3,24.4H6.7V23h6.6V24.4z M13.3,19.6H6.7v-1.4h6.6V19.6z',
               handleSize: '80%',
               dataBackground: {
                   areaStyle: {
                       color: '#8392A5'
                   },
                   lineStyle: {
                       opacity: 0.8,
                       color: '#8392A5'
                   }
               },
               handleStyle: {
                   color: '#fff',
                   shadowBlur: 3,
                   shadowColor: 'rgba(0, 0, 0, 0.6)',
                   shadowOffsetX: 2,
                   shadowOffsetY: 2
               }
           }, {
               type: 'inside'
           }],
//           xAxis: {
//               show:false,
//               type : 'category',
//               splitLine: {
//                   show: true
//               },
////               splitNumber: 2,
//               data : data1.time
//           },
           xAxis:[
               {
                   show: false,
                   type : 'category',
                   position:'bottom',
                   boundaryGap : true,
                   axisTick: {onGap:false},
                   splitLine: {show:false},
                   data :volumeChartData.time
               }
           ],
           yAxis:  [
               {
                   type: 'value',
                   scale: true,
                   position:"left",
                   splitArea: {
                       show: false
                   },
               }
           ],
//           grid: {
//               left: '1.5%',
//               right: '15px',
//               top: '10px',
//               bottom: '10px',
//               containLabel: true
//           },
           grid: {
               x: 80,
               y:5,
               x2:20,
               y2:30
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
        for(var i=0;i<Len;i++){
            volumeChartData.time[i]=json.Parameters[i].DateTime;
            volumeChartData.prices[i]=json.Parameters[i].TotalVolume;
        };
        console.log(volumeChartData);
        var option =volumeChartSetOption(volumeChartData);
        if(volumeChart != null){
            volumeChart.setOption(option);
//            volumeChart.connect([timeChart])
           volumeChart.group="group1";
        }
    }
    function volumeChartSetOption(data) {
        var  dataVolume=volumeChartData;
      var  option = {
//        title: {
//            text: '成交量',
//            position:"center"
////                subtext: '纯属虚构'
//        },
          tooltip: {
              trigger: 'axis'
          },
          legend: {
              data:['最新成交价']
          },
            toolbox: {
                show: false,
            },
//          grid: {
//              left: '1%',
//              right: '27px',
//              top: '10px',
//              bottom: '10px',
//              containLabel: true
//          },
			grid: {
               x: 80,
               y:5,
               x2:20,
               y2:30
           },
//        grid: {
//            x: 80,
//            y:5,
//            x2:55,
//            y2:30
//        },
//            dataZoom : {
//                y:500,
//                show : true,
//                realtime: true,
//                start : 50,
//                end : 100
//            },
          dataZoom: [{
              textStyle: {
                  color: '#8392A5'
              },
              handleIcon: 'M10.7,11.9v-1.3H9.3v1.3c-4.9,0.3-8.8,4.4-8.8,9.4c0,5,3.9,9.1,8.8,9.4v1.3h1.3v-1.3c4.9-0.3,8.8-4.4,8.8-9.4C19.5,16.3,15.6,12.2,10.7,11.9z M13.3,24.4H6.7V23h6.6V24.4z M13.3,19.6H6.7v-1.4h6.6V19.6z',
              handleSize: '80%',
              dataBackground: {
                  areaStyle: {
                      color: '#8392A5'
                  },
                  lineStyle: {
                      opacity: 0.8,
                      color: '#8392A5'
                  }
              },
              handleStyle: {
                  color: '#fff',
                  shadowBlur: 3,
                  shadowColor: 'rgba(0, 0, 0, 0.6)',
                  shadowOffsetX: 2,
                  shadowOffsetY: 2
              }
          }, {
              type: 'inside'
          }],
//          xAxis: [
//              {
//                  type: 'category',
//                  boundaryGap: true,
//                  data:dataVolume.time
//              }
//          ],
          xAxis:[
              {
                  type : 'category',
                  position:'bottom',
                  boundaryGap : true,
                  axisTick: {onGap:false},
                  splitLine: {show:false},
                  data : dataVolume.time
              }
          ],
          yAxis: [
              {
                  type: 'value',
                  scale: false,
//                  axisLabel:{
//                      show:true,
//                      rotate:45
//                  },
//                  axisLabel: {
//                      formatter: function (a) {
//                          console.log(a);
//                          a = +a;
//                          return isFinite(a)
//                                  ? echarts.format.addCommas(+a / 100)
//                                  : '';
//                      }
//                  },
                  name: '成交量',
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