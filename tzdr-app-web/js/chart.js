var marketCommdity = {};
function setMarketCommdity (key,value){
	marketCommdity[key] = value;
}
var marketCommdityLastPrice = {};
function setMarketCommdityLastPrice(key,value){
	marketCommdityLastPrice[key] = value;
}
var marketNotSubCommdity = {};
function setMarketNotSubCommdity(key,value){
	marketNotSubCommdity[key] = value;
}
var marketSubCommdity = {};
function setMarketSubCommdity(key,value){
	marketSubCommdity[key]=value;
}
var reconnect=null;
var marketSocket = null;
var firstTimeLength=1;
var commoditysData;
var OnRspQryCommodityDateL=1;
function subscribeHold(exchageNo,commodityNo,contractNo){
		masendMessage('Subscribe','{"ExchangeNo":"'+exchageNo+'","CommodityNo":"'+commodityNo+'","ContractNo":"'+contractNo+'"}');
}
mui.plusReady(function(){
    	var Transfer=plus.webview.currentWebview();
		var CommodityNo=document.getElementById("CommodityNo");
		var mainTitleFirst=document.getElementsByClassName("mainTitleFirst")[0];
		mainTitleFirst.innerHTML=Transfer.name[0];
		CommodityNo.innerHTML=Transfer.name[2]+Transfer.name[1];
		$("#tradeTitle").append("<option value='"+Transfer.name[2]+"'>"+Transfer.name[0]+Transfer.name[2]+Transfer.name[1]+"</option>");
//		$("#tradeContractTitle").text(Transfer.name[2]+Transfer.name[1]);
		init(Transfer.name);
    	setTimeout(function(){
					muiSpinner[0].style.display="none";
			},200);
	var url = MarketUrl.SocketUrl;
	marketSocket = new WebSocket(url);
    var setIntvalTime = null;
    var marketLoadParam = {}
	marketSocket.onopen = function(evt){
       masendMessage('Login','{"UserName":"'+marketUserName+'","PassWord":"'+marketPassword+'"}');
    };
    marketSocket.onclose = function(evt){
    	if(setIntvalTime != null)
    		clearInterval(setIntvalTime);
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
        	sendHistoryMessage();
	       masendMessage('QryCommodity',null);
        }else if(method == "OnRspQryHistory"){
            var historyParam = jsonData;
//          console.log(JSON.stringify(historyParam));
			if(historyParam.Parameters==null){
				return
			};
			if(firstTimeLength==1){
				getSubscript(historyParam.Parameters.ColumNames);
				firstTimeLength=2;
			}
			if(historyParam.Parameters.HisQuoteType==0){
				handleTime(historyParam);
				processingData(historyParam);
	            handleVolumeChartData(historyParam);
	            processingCandlestickVolumeData(historyParam);
			}else if(historyParam.Parameters.HisQuoteType==1){
				handleTime(historyParam);
				processingData(historyParam);
	            handleVolumeChartData(historyParam);
	            processingCandlestickVolumeData(historyParam);
			}else if(historyParam.Parameters.HisQuoteType==1440){
				console.log(JSON.stringify(historyParam));
				processingDayCandlestickData(historyParam)
				processingDayCandlestickVolumeData(historyParam);
			}
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
        	if(OnRspQryCommodityDateL==1){
        		commoditysData=jsonData.Parameters;
        	}
        	
        	var commoditys = jsonData.Parameters;
			if(commoditys == null)return;
			var size = commoditys.length;
			var tradeTitleHtml=document.getElementById("tradeTitle");
			console.log(JSON.stringify(commoditys));
			for(var i = 0 ; i < size ; i++){
				var comm = commoditys[i];
				var newCommdityNo = comm.CommodityNo;
				var newContractNo = comm.MainContract;
				var newExchangeNo = comm.ExchangeNo;
				//如果是当前合约与品种更新乘数
				if (valiationIsPresent(newCommdityNo, newContractNo)) {
					$("#contractSize").val(comm.ContractSize);
				} 
				var commdityAndContract = newCommdityNo+newContractNo;
				setMarketCommdity(commdityAndContract,comm);
				//验证在执行交易请求数据时，是否还有未订阅的持仓信息，
				var comContract = marketNotSubCommdity[commdityAndContract];
				if(comContract != undefined){ 
					console.log(marketSubCommdity[commdityAndContract]);
					if(marketSubCommdity[commdityAndContract] == undefined){
						console.log("订阅"); 
						subscribeHold(newExchangeNo,commdityNo,contractNo);
						setMarketSubCommdity(commdityAndContract,commdityAndContract);
					}
				}
				//masendMessage('Subscribe','{"ExchangeNo":"'+newExchangeNo+'","CommodityNo":"'+newCommdityNo+'","ContractNo":"'+newContractNo+'"}');
				if(Transfer.name[2]==newCommdityNo){
   					
	   			}else{
	   				
	   				tradeTitleHtml.innerHTML+="<option value="+newCommdityNo+">"+comm.CommodityName+comm.CommodityNo+comm.MainContract+"</option>"
//	   				tradeTitleHtml.innerHTML+="<option value='"+newCommdityNo+"'>"+comm.CommodityName+comm.CommodityNo+comm.MainContract+"</option>"
	   			}
			}
        }
    };
    marketSocket.onerror = function(evt){
    };
    function sendHistoryMessage(){
    		 var exchangeNo = $("#exchangeNo").val();
		    var commodityNo = $("#commodeityNo").val();
		    var contractNo = $("#contractNo").val();
		    //masendMessage('QryHistory','{"ExchangeNo":"'+exchangeNo+'","CommodityNo":"'+commodityNo+'","ContractNo":"'+contractNo+'"}');
		    masendMessage('QryHistory','{"ExchangeNo":"'+exchangeNo+'","CommodityNo":"'+commodityNo+'","ContractNo":"'+contractNo+'"}');
		    masendMessage('QryHistory','{"ExchangeNo":"'+exchangeNo+'","CommodityNo":"'+commodityNo+'","ContractNo":"'+contractNo+'","HisQuoteType":1440}');
		    masendMessage('Subscribe','{"ExchangeNo":"'+exchangeNo+'","CommodityNo":"'+commodityNo+'","ContractNo":"'+contractNo+'"}');
	        setIntvalTime = setInterval(function(){
	            masendMessage('QryHistory','{"ExchangeNo":"'+exchangeNo+'","CommodityNo":"'+commodityNo+'","ContractNo":"'+contractNo+'","Count":1,"HisQuoteType":1}');
	        },3000);
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
    var changeValue=document.getElementById("changeValue");
     var rose=document.getElementById("rose");
    var freshPrices=document.getElementById("freshPrices");
    var volumePricesNumber=document.getElementById("volumePricesNumber");
    var buyPrices=document.getElementById("buyPrices");
    var buyPricesNumber=document.getElementById("buyPricesNumber");
    var sellPrices=document.getElementById("sellPrices");
    var sellPricesNumber=document.getElementById("sellPricesNumber");
    /*
     * 切换合约
     * */
    
    $("#tradeTitle").change(function(){
    	var commoditysDataP=commoditysData.Parameters;
    	var valSelect=$("#tradeTitle").val();
    	console.log(JSON.stringify(commoditysData));
    	var exchangeNo= $("#exchangeNo").val();
    	var commodityNo= $("#commodeityNo").val();
    	var contractNo=  $("#contractNo").val();
    	masendMessage('UnSubscribe','{"ExchangeNo":"'+exchangeNo+'","CommodityNo":"'+commodityNo+'","ContractNo":"'+contractNo+'"}');
    	for(var i=0;i<commoditysData.length;i++){
    		if(commoditysData[i].CommodityNo==valSelect){
    			 	 $("#exchangeNo").val(commoditysData[i].ExchangeNo);
				    $("#commodeityNo").val(commoditysData[i].CommodityNo);
				   $("#contractNo").val(commoditysData[i].MainContract);
				   $("#CommodityName").val(commoditysData[i].CommodityName);
				   $("#doSize").val(commoditysData[i].DotSize);
				   $("#contractSize").val(commoditysData[i].ContractSize);
				  	$("#miniTikeSize").val(commoditysData[i].MiniTikeSize)
    		}
    	}
    	clearInterval(setIntvalTime);
    	var CommodityNo=document.getElementById("CommodityNo");
		var mainTitleFirst=document.getElementsByClassName("mainTitleFirst")[0];
		mainTitleFirst.innerHTML= $("#CommodityName").val();
		CommodityNo.innerHTML=$("#commodeityNo").val()+$("#contractNo").val();
   		rawData=[];
   		dayCandlestickChartData=[];
   		timeData.timeLabel=[];
   		timeData.prices=[];
   		volumeChartData.time=[];
   		volumeChartData.volume=[];
   		volumeTime=[];
   		volumeV=[];
// 		 CandlestickVolumeChartTime=[];
// 		 CandlestickVolumeChartVolume=[];
   		 CandlestickVolumeData={
		    	time:[],
		    	volume:[]
		    };
		    
   		  dayCandlestickVolumeData={
		    	time:[],
		    	volume:[]
		    };
//		    processingDayCandlestickVolumeData(null);
    	console.log("更新后的"+timeData.timeLabel.length);
    	sendHistoryMessage();
    	
    })
   
    function insertDATA(DATA){
    	 var doSize=$("#doSize").val();
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
function masendMessage(method,parameters){
	 marketSocket.send('{"Method":"'+method+'","Parameters":'+parameters+'}');
}
 function getSubscript(data){
	for(var i=0;i<=data.length-1;i++){
		if(data[i]=="DateTimeStamp"){
			DateTimeStampSubscript=i;
		}else if(data[i]=="LastPrice"){
			LastPriceSubscript=i;
		}else if(data[i]=="OpenPrice"){
			OpenPriceSubscript=i;
		}else if(data[i]=="LowPrice"){
			LowPriceSubscript=i
		}else if(data[i]=="HighPrice"){
			HighPriceSubscript=i
		}else if(data[i]=="Volume"){
			VolumeSubscript=i;
		}
	}
}
