var marketCommdity = {};
function setMarketCommdity (key,value){
	marketCommdity[key] = value;
}
var marketCommdityLastPrice = {};
function setMarketCommdityLastPrice(key,value){
	marketCommdityLastPrice[key] = value;
}
var reconnect=null;
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
       masendMessage('Login','{"UserName":"'+marketUserName+'","PassWord":"'+marketPassword+'"}');
    };
    marketSocket.onclose = function(evt){
    	if(setIntvalTime != null)
    		clearInterval(setIntvalTime);
    		//console.log("断开" + JSON.stringify(evt));
    	if(reconnect != false){
    		if(username==null){
    			alertProtype("行情服务器连接超时,点击确定重新连接","提示",Btn.confirmed(),null,reconnectPage);
    		}
    	}
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
	            masendMessage('QryHistory','{"ExchangeNo":"'+exchangeNo+'","CommodityNo":"'+commodityNo+'","ContractNo":"'+contractNo+'","Count":1,"HisQuoteType":1}');
	        },3000);
	       //masendMessage('QryCommodity','{"ExchangeNo":"'+exchangeNo+'"}');
	       masendMessage('QryCommodity',null);
        }else if(method == "OnRspQryHistory"){
            var historyParam = jsonData;
              handleTime(historyParam);
            processingData(historyParam);
            handleVolumeChartData(historyParam);
        }else if(method == "OnRtnQuote"){
        	var quoteParam = jsonData;
        	if(quoteParam.Parameters == null)return;
        	var subscribeParam = quoteParam.Parameters;
			var newCommdityNo = subscribeParam.CommodityNo;
			var newContractNo = subscribeParam.ContractNo;
			marketLoadParam[newCommdityNo] = subscribeParam;
			//如果是当前合约与品种更新行情数据，和浮动盈亏
			if (valiationIsPresent(newCommdityNo, newContractNo)) {
				updateLoadWebParam(subscribeParam); 
				insertDATA(quoteParam);
			} 
			updateFloatProfit(subscribeParam);
			setMarketCommdityLastPrice(newCommdityNo+newContractNo,subscribeParam.LastPrice);
        }else if(method == "OnRspQryCommodity"){
        	var commoditys = jsonData.Parameters;
			if(commoditys == null)return;
			var size = commoditys.length;
			for(var i = 0 ; i < size ; i++){
				var comm = commoditys[i];
				var newCommdityNo = comm.CommodityNo;
				var newContractNo = comm.MainContract;
				var newExchangeNo = comm.ExchangeNo;
				//如果是当前合约与品种更新乘数
				if (valiationIsPresent(newCommdityNo, newContractNo)) {
					$("#contractSize").val(comm.ContractSize);
				}
				setMarketCommdity(newCommdityNo+newContractNo,comm);
				
				masendMessage('Subscribe','{"ExchangeNo":"'+newExchangeNo+'","CommodityNo":"'+newCommdityNo+'","ContractNo":"'+newContractNo+'"}');
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
		var doSize = 2;
		var size = $("#doSize").val();
		if(size != undefined || size.length > 0){
			doSize = size;
		}
		$("#lastPrice").text(parseFloat(param.LastPrice).toFixed(doSize));
		$("#sellLastPrice").text(parseFloat(param.AskPrice1).toFixed(doSize));
		$("#buyLastPrice").text(parseFloat(param.BidPrice1).toFixed(doSize));
		$("#totalVolume").text(param.TotalVolume);
		$("#askQty").text(param.AskQty1);
		$("#bidQty").text(param.BidQty1);
		var newCommdityNo = param.CommodityNo;
		var newContractNo = param.ContractNo;
		var comm = marketCommdity[newCommdityNo+newContractNo];
		if(comm != undefined && $("input[type = 'radio']:checked").val() == 1){ 
			$("#buyBtn_P").text(parseFloat(doGetMarketPrice(param.LastPrice,comm.MiniTikeSize,0)).toFixed(doSize));
			$("#sellBtn_P").text(parseFloat(doGetMarketPrice(param.LastPrice,comm.MiniTikeSize,1)).toFixed(doSize));
		}
	}
    /**
	 * 更新浮动盈亏 
	 */
	function updateFloatProfit(param) {
		var isFlag = false;
		var newContract = param.CommodityNo+param.ContractNo;
		for (var i = 0; i < positionsIndex; i++) {
			if(newContract == positionContractCode[i]){
				isFlag =  true;
				break;
			}
		}
		if(isFlag){
			var lastPrice = param.LastPrice;
			var comm = marketCommdity[newContract];
			if(comm == undefined)return;
			var $thisFloat = $("#floatValue"+newContract);//$("li[contract-code-position = " + newContract + "] span[class = 'position4 dateTimeL']");
			var $thisAvgPrice = $("li[contract-code-position = " + newContract + "] span[class = 'position3']");
			var $thisHoldNum = $("li[contract-code-position = " + newContract + "] span[class = 'position2']");
			var $thisDrection = $("li[contract-code-position = " + newContract + "] span[class = 'position1']");
			var drection = $thisDrection.attr("data-drection");
			//验证该平仓数据是否存在列表中
			if ($thisAvgPrice.text() == undefined) { 
				return;
			}
			var floatP = doGetFloatingProfit(parseFloat(lastPrice), parseFloat($thisAvgPrice.text()) , comm.ContractSize,comm.MiniTikeSize,parseInt($thisHoldNum.text()),drection);
			var floatProfit = floatP +":"+ comm.CurrencyNo;
			$thisFloat.val(floatProfit); 
			if(parseFloat(floatP) < 0 ){
				$thisFloat.css("color","green");
			}else if(parseFloat(floatP) > 0){
				$thisFloat.css("color","red");
			}else{
				$thisFloat.css("color","white");
			}
		}
		
	}
    /**
	 * 验证是否是当前品种和合约 
	 * @param {Object} commodeityNo 品种
	 * @param {Object} contractNo 合约
	 */
	function valiationIsPresent(commodeityNo, contractNo) {
		if(commodeityNo == $("#commodeityNo").val() && contractNo == $("#contractNo").val()) {
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
    	if(Number(DATA.Parameters.AskPrice1) - Number(DATA.Parameters.PreSettlePrice) < 0){
    		buyPrices.className = "PricesLeft greenFont";
    	}else if(Number(DATA.Parameters.AskPrice1) - Number(DATA.Parameters.PreSettlePrice) > 0){
    		buyPrices.className = "PricesLeft redFont";
    	}else{
    		buyPrices.className = "PricesLeft whiteFont";
    	};
    	if(Number(DATA.Parameters.BidPrice1) - Number(DATA.Parameters.PreSettlePrice) < 0){
    		sellPrices.className = "PricesLeft greenFont";
    	}else if(Number(DATA.Parameters.BidPrice1) - Number(DATA.Parameters.PreSettlePrice) > 0){
    		sellPrices.className = "PricesLeft redFont";
    	}else{
    		sellPrices.className = "PricesLeft whiteFont";
    	};
    	if (Number(DATA.Parameters.LastPrice) - Number(DATA.Parameters.PreSettlePrice) < 0) {
			 freshPrices.className = "greenFont";
		 }else {
			freshPrices.className = "redFont";
		}
		if(Number(DATA.Parameters.ChangeRate)==0){
				 changeValue.className = "whiteFont";
		 }else if(Number(DATA.Parameters.ChangeRate)>0){
				changeValue.className = "redFont";
		}else if(Number(DATA.Parameters.ChangeRate)<0){
				 changeValue.className = "greenFont";
		}
	    rose.innerHTML=DATA.Parameters.ChangeValue.toFixed(doSize)+"/";
	    changeValue.innerHTML=DATA.Parameters.ChangeRate.toFixed(2)+"%";
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
    		var parameters = jsonData.Parameters;
    		var Len=parameters.length;
    		if(parameters == null)return;
    	    var lent=rawData.length;
        	for(var i=0;i<Len;i++){
        		var time2=parameters[i].DateTimeStamp.split(" ");
		        	var str1=time2[1].split(":");
		        	var str2=str1[0]+":"+str1[1]
        			var openPrice = parseFloat(parameters[i].OpenPrice).toFixed(doSize);
		            var closePrice = parseFloat(parameters[i].LastPrice).toFixed(doSize);
		            var chaPrice = closePrice - openPrice;
		            time1=parameters[i].DateTime;
		            var sgData = [str2,parseFloat(openPrice).toFixed(doSize),parseFloat(closePrice).toFixed(doSize),parseFloat(chaPrice).toFixed(doSize),"",parseFloat(parameters[i].LowPrice).toFixed(doSize),parseFloat(parameters[i].HighPrice).toFixed(doSize),"","","-"];
			         rawData[lent+i] = sgData; 
       		};
		   time1=jsonData.Parameters[Len-1].DateTimeStamp;
        	for(var i=0;i<rawData.length-1;i++){
        		if(rawData[i][0]==rawData[i+1][0]){
        			rawData.splice(i,1);
        		}
        	}
        	var newData=rawData.slice(-60);
        		CandlestickChartOption = setOption(newData);
        	if(firstTimeNumber==0){
		  			
		  	}else{
//		  		myChart.resize();
		  		myChart.setOption(CandlestickChartOption);
		  	}
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
        console.log(Len);
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
		var re = plus.webview.getWebviewById("quotationMain");
		if(re != null || re != undefined){
			re.reload(); 
		}
		masendMessage('Logout','{"UserName":"'+marketUserName+'"}');
		marketSocket.close();
		reconnect=false;
		if(username != null){
			Trade.doLoginOut(username);
			socket.close();
			loginOutFlag = true;
		};
		mui.back();
	});
	function reconnectPage(){
		plus.webview.getWebviewById("transactionDetails").reload();
	}
});
