mui.plusReady(function(){
		var muiSpinner=document.getElementsByClassName("mui-spinner");
    	var Transfer=plus.webview.currentWebview();
		var CommodityNo=document.getElementById("CommodityNo");
		var mainTitleFirst=document.getElementsByClassName("mainTitleFirst")[0];
		mainTitleFirst.innerHTML=Transfer.name[0];
		CommodityNo.innerHTML=Transfer.name[2]+Transfer.name[1];
		$("#tradeTitle").text(Transfer.name[0]);
		$("#tradeContractTitle").text(Transfer.name[2]+Transfer.name[1]);
		init(Transfer.name);
		var aa=require.config({
	    paths:{
	        'echarts' :'../../js/echarts',
	        'echarts/chart/pie' :'../../js/echarts',
	    }
    	});
    	setTimeout(function(){
					muiSpinner[0].style.display="none";
			},200);
	var time1;
    var url = MarketUrl.SocketUrl;
    var marketSocket = new WebSocket(url);
    var setIntvalTime = null;
    var marketLoadParam = {}
    
    marketSocket.onopen = function(evt){
       masendMessage('Login','{"UserName":"13677622344","PassWord":"a123456"}');
    };
    marketSocket.onclose = function(evt){
    	if(setIntvalTime != null)
    		clearInterval(setIntvalTime);
    		console.log("断开" + JSON.stringify(evt));
    };
    marketSocket.onmessage = function(evt){
        var data = evt.data;
        var jsonData = JSON.parse(data);
        var method = jsonData.Method;
        if(method=="OnRspLogin"){
		    var date=new Date();
		    var exchangeNo = $("#exchangeNo").val();
		    var commodityNo = $("#commodeityNo").val();
		    var contractNo = $("#contractNo").val();
		    masendMessage('QryHistory','{"ExchangeNo":"'+exchangeNo+'","CommodityNo":"'+commodityNo+'","ContractNo":"'+contractNo+'"}');
		    masendMessage('Subscribe','{"ExchangeNo":"'+exchangeNo+'","CommodityNo":"'+commodityNo+'","ContractNo":"'+contractNo+'"}');
	        setIntvalTime = setInterval(function(){
	            masendMessage('QryHistory','{"ExchangeNo":"'+exchangeNo+'","CommodityNo":"'+commodityNo+'","ContractNo":"'+contractNo+'","BeginTime":"'+time1+'","HisQuoteType":1}');
	        },3000);
	       masendMessage('QryCommodity','{"ExchangeNo":"'+exchangeNo+'"}');
        }else if(method == "OnRspQryHistory"){
            var historyParam = jsonData;
            processingData(historyParam);
            handleTime(historyParam);
            handleVolumeChartData(historyParam);
        }else if(method == "OnRtnQuote"){
//      	console.log("订阅成功"); 
        	var quoteParam = jsonData;
        	if(quoteParam.Parameters == null)return;
        	insertDATA(quoteParam);
        	var subscribeParam = quoteParam.Parameters;
			var newCommdityNo = subscribeParam.CommodityNo;
			var newContractNo = subscribeParam.ContractNo;
			marketLoadParam[newCommdityNo] = subscribeParam;
			//如果是当前合约与品种更新行情数据，和浮动盈亏
			if (valiationIsPresent(newCommdityNo, newContractNo)) {
				updateLoadWebParam(subscribeParam);
				updateFloatProfit();
			}
        }else if(method == "OnRspQryCommodity"){
        	var commoditys = jsonData.Parameters;
        	if(commoditys.Parameters == null)return;
        	var newCommdityNo = commoditys.CommodityNo;
			var newContractNo = commoditys.ContractNo;
			//如果是当前合约与品种更新乘数
			if (valiationIsPresent(newCommdityNo, newContractNo)) {
				$("#contractSize").val(commoditys.ContractSize);
			}
        }
    };
    marketSocket.onerror = function(evt){
    };
    function masendMessage(method,parameters){
        marketSocket.send('{"Method":"'+method+'","Parameters":'+parameters+'}');
    }
 	
    /**
	 * 更新行情数据 
	 * @param {Object} param
	 */
	function updateLoadWebParam(param) {
		$("#lastPrice").text(param.LastPrice);
		$("#sellLastPrice").text(param.AskPrice1);
		$("#buyLastPrice").text(param.BidPrice1);
		$("#totalVolume").text(param.TotalVolume);
		$("#askQty").text(param.AskQty1);
		$("#bidQty").text(param.BidQty1);
		$("#buyBtn_P").text(param.BidPrice1);
		$("#sellBtn_P").text(param.AskPrice1);
	}
    /**
	 * 更新浮动盈亏 
	 */
	function updateFloatProfit() {
		for (var i = 0; i < positionsIndex; i++) {
			var $thisFloat = $("li[contract-code-position = " + positionContractCode[i] + "] span[class = 'position4 dateTimeL']");
			var $thisAvgPrice = $("li[contract-code-position = " + positionContractCode[i] + "] span[class = 'position3']");
			var $thisHoldNum = $("li[contract-code-position = " + positionContractCode[i] + "] span[class = 'position2']");
			//验证该平仓数据是否存在列表中
			if ($thisAvgPrice.text() == undefined) {
				continue; 
			}
			var floatProfit = doGetFloatingProfit(parseFloat($("#lastPrice").text()), parseFloat($thisAvgPrice.text()) , $("#contractSize").val(),$("#miniTikeSize").val(),parseInt($thisHoldNum.text()));
			$thisFloat.text(floatProfit);
		}
	}
    /**
	 * 验证是否是当前品种和合约 
	 * @param {Object} commodeityNo 品种
	 * @param {Object} contractNo 合约
	 */
	function valiationIsPresent(commodeityNo, contractNo) {
		if (commodeityNo == $("#commodeityNo").val() && contractNo == $("#contractNo").val()) {
			return true;
		} else {
			return false;
		}
	}
//var href = window.location.href;
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
    var timeLabel=[]
    var timeData={
        "time":time,
        "prices":prices,
        "timeLabel":timeLabel
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
                    echarts=ec;
                    timeChart=ec.init(document.getElementById("timeChart"));
                    var option1=setOption1(timeData);
//                  volumeChart=ec.init(document.getElementById("volumeChart"));
//                  var volumeChartOption=volumeChartSetOption();
//                  volumeChart.setOption(volumeChartOption);;
//                  ec.connect("group1");
                }
        );
		
    }
    var changeValue=document.getElementById("changeValue");
     var rose=document.getElementById("rose");
    var freshPrices=document.getElementById("freshPrices");
    var volumePricesNumber=document.getElementById("volumePricesNumber");
    var buyPrices=document.getElementById("buyPrices");
    var buyPricesNumber=document.getElementById("buyPricesNumber");
    var sellPrices=document.getElementById("sellPrices");
    var sellPricesNumber=document.getElementById("sellPricesNumber");
    var doSize=$("#doSize").val();
    function insertDATA(DATA){
    	buyPrices.innerHTML=DATA.Parameters.AskPrice1.toFixed(doSize);
    	buyPricesNumber.innerHTML=DATA.Parameters.AskQty1;
    	sellPrices.innerHTML=DATA.Parameters.BidPrice1.toFixed(doSize);
    	sellPricesNumber.innerHTML=DATA.Parameters.BidQty1;
    	volumePricesNumber.innerHTML=DATA.Parameters.TotalVolume;
    	freshPrices.innerText = DATA.Parameters.LastPrice.toFixed(doSize);
    	if (Number(DATA.Parameters.LastPrice) - Number(DATA.Parameters.PreSettlePrice) < 0) {
			 freshPrices.className = "greenFont";
			 }else {
			freshPrices.className = "redFont";
		}
		changeValue.innerHTML=DATA.Parameters.ChangeRate.toFixed(2)+"%";
		if(Number(DATA.Parameters.ChangeRate)==0){
				 changeValue.className = "whiteFont";
			 }else if(Number(DATA.Parameters.ChangeRate)>0){
				changeValue.className = "redFont";
			}else if(Number(DATA.Parameters.ChangeRate)<0){
				 changeValue.className = "greenFont";
			}
	    rose.innerHTML=DATA.Parameters.ChangeValue.toFixed(doSize)+"/";
		if(Number(DATA.Parameters.ChangeValue)==0){
				 rose.className = "whiteFont";
			 }else if(Number(DATA.Parameters.ChangeValue)>0){
				rose.className = "redFont";
			}else if(Number(DATA.Parameters.ChangeValue)<0){
				 rose.className = "greenFont";
			}
		}
    var timeNumber=0;
    var num=0;
    var firstTimeNumber=0;
    var CandlestickChartOption=null;
    function processingData(jsonData){
    		var addRawData=[];
    		var newChartData=null;
    		var parameters = jsonData.Parameters;
    		var Len=parameters.length;
    		parameters1=parameters;
    		if(parameters == null)return;
    	    var lent=rawData.length;
    	    var newData=null;
    	    if(Len>60){
    	    	newData=parameters.slice(-60);
    	    }else{
    	    	newData=parameters;
    	    }
    	    var newDataLength=newData.length;
        	for(var i=0;i<newDataLength;i++){
        		var time2=newData[i].DateTimeStamp.split(" ");
		        	var str1=time2[1].split(":");
		        	var str2=str1[0]+":"+str1[1]
        			var openPrice = parseFloat(newData[i].OpenPrice).toFixed(doSize);
		            var closePrice = parseFloat(newData[i].LastPrice).toFixed(doSize);
		            var chaPrice = closePrice - openPrice;
		            time1=newData[i].DateTime;
		            var sgData = [str2,parseFloat(openPrice).toFixed(doSize),parseFloat(closePrice).toFixed(doSize),parseFloat(chaPrice).toFixed(doSize),"",parseFloat(newData[i].LowPrice).toFixed(doSize),parseFloat(newData[i].HighPrice).toFixed(doSize),"","","-"];
			         rawData[lent+i] = sgData; 
       		};
		   time1=jsonData.Parameters[Len-1].DateTimeStamp;
		   var lent1=rawData.length;
		   for(var i=0;i<lent1-2;i++){
		   		if(rawData[i][0]==rawData[i+1][0]){
		   			addRawData[addRawData.length-1]=rawData[i+1];
		   		}else{
		   			addRawData.push(rawData[i]);
		   		}
		   }
		   newChartData=addRawData.slice(-60);
        	CandlestickChartOption = setOption(newChartData);
        	if(firstTimeNumber==0){
		  			
		  	}else{
		  		myChart.setOption(CandlestickChartOption);
		  	}
		  	document.getElementById("Candlestick").addEventListener("tap",function(){
				 if(myChart != null){
				 	setTimeout(function(){
				 		muiSpinner[1].style.display="none";
				 	},200)
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
		            var res = params[0].name;
//		            console.log(JSON.stringify(params));
		            res += '<br/>' + params[0].seriesName;
		            res += '<br/>  开盘 : ' + params[0].value[0] + '  最高 : ' + params[0].value[3];
		            res += '<br/>  收盘 : ' + params[0].value[1] + '  最低 : ' + params[0].value[2];
		            return res;
		        }
		    },
		    grid: {
		               x: 43,
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
    var timeChartNumber=0
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
//      	timeLabel[leng+i]=str2;
//      	timePrice[leng+i]=parseFloat(Parameters[i].LastPrice).toFixed(doSize);
			timeData.timeLabel[TimeLength+i]=str2;
        	timeData.prices[TimeLength+i]=parseFloat(Parameters[i].LastPrice).toFixed(doSize); 	
        }
		for(var i=0;i<timeData.timeLabel.length-2;i++){
			if(timeData.timeLabel[i]==timeData.timeLabel[i+1]){
//				console.log("35");
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
		        data: data1.timeLabel,
		        axisLine: { lineStyle: { color: '#8392A5' } }
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
               y2:20
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
	document.getElementById("backClose").addEventListener("tap",function(){
		plus.webview.getWebviewById("quotationMain.html").reload();
		marketSocket.close();
		if(username != null){ 
			socket.close();
		};
	})
});
