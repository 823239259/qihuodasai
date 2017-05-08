/**
 * 行情初始化
 */
initQuote();
/*
 全局缓存品种
 */
var localCacheCommodity = {};
/**
 * 保存全局缓存品种到数据
 */
var localCacheCommodityArray = new Array();
/*
 第一次获取数据的下标
 */
var historyLength=0;

/**
 * 处理行情数据
 * @param evt
 */
function quoteHandleData(evt) {
	var data = evt.data;
	var jsonData = JSON.parse(data);
	var method = jsonData.Method;
	if(method == "OnRspLogin"){
		Quote.doAllQryCommodity();
	}else if(method == "OnRspQryCommodity"){
		insertProductListData(jsonData);
		sendQueryHistory(0);
	}else if(method == "OnRtnQuote"){
		 var totalVolume=$("#totalVolume").val();
		var commodityNo = $("#commodeityNo").val();
		 if(commodityNo==jsonData.Parameters.CommodityNo){
		 	dealOnRtnQuoteData(jsonData,totalVolume);
		 	lightChartData(jsonData);
		 	updateData(jsonData);
		 }
	}else if(method == "OnRspQryHistory"){
		if(historyLength==0){
			getSubscript(jsonData.Parameters.ColumNames);
			historyLength=1;
		}
		handleTimeChartData(jsonData);
		processingData(jsonData);
	}else if(method == "OnRspSubscribe"){
	}
}
/*
 添加行情列表数据
 */
function insertProductListData(data) {
	var choiceProductList=document.getElementById("choiceProductList");
	var length=data.Parameters.length;
	for(var i=0;i<length;i++){
	var length=data.Parameters.length;
		var CommodityName=data.Parameters[i].CommodityName;
		var CommodityNo=data.Parameters[i].CommodityNo;
		var MainContract=data.Parameters[i].MainContract;
		choiceProductList.innerHTML+="<li><span>"+CommodityName+"</span><span>"+CommodityNo+MainContract+"</span></li>"
		if(i==0){
			$("#trade_data #lastPrice").val(data.Parameters[i].lastPrice);
			$("#trade_data #miniTikeSize").val(data.Parameters[i].miniTikeSize);
			$("#trade_data #contractSize").val(data.Parameters[i].ContractSize);
			$("#trade_data #exchangeNo").val(data.Parameters[i].ExchangeNo);
			$("#trade_data #commodeityNo").val(CommodityNo);
			$("#trade_data #contractNo").val(MainContract);
			$("#trade_data #doSize").val(data.Parameters[i].DotSize);
			 Quote.doSubscribe(data.Parameters[i].ExchangeNo,CommodityNo,MainContract);
			 $("#choiceProductButton p").eq(0).text(CommodityName);
			  $("#choiceProductButton p").eq(1).text(CommodityNo+MainContract);
		}
	}
	if(length%2!=0){
		choiceProductList.innerHTML+="<li><span></span><span></span></li>";
	}
	setLocalCacheCommodity(data.Parameters);
}
/**
 * 添加品种到全局缓存
 * @param param
 */
function setLocalCacheCommodity(param) {
	var paramSize = param.length;
	for (var i = 0; i < paramSize; i++) {
		var data = param[i];
		var commodityNo = data.CommodityNo;
		var contractNo = data.MainContract;
		var contractCode = commodityNo + contractNo;
		localCacheCommodity[contractCode]= data;
		localCacheCommodityArray[i] = data;
	}
}
/**
 * 全局缓存中取品种
 * @param obj
 */
function getLocalCacheCommodity(obj) {
	return localCacheCommodity[obj];
}
/*
 获取数据的下标
 */
 function getSubscript(data) {
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
 /**
  * 发送查询的历史数据的方法
  * */
 	function sendQueryHistory(HisQuoteType) {
 		var exchangeNo = $("#exchangeNo").val();
        var commodityNo = $("#commodeityNo").val();
        var contractNo = $("#contractNo").val();
		timeData={
		"timeLabel":[],
		"prices":[],
		"time":[]
		}
		volumeChartData={
			"time":[],
			"volume":[]
		}
		 CandlestickData={
			"categoryData":[],
			"values":[]
		};
		rawData=[];
		CandlestickVolumeData={
			"time":[],
			"volume":[]
		}
        Quote.doQryHistory(exchangeNo,commodityNo,contractNo,HisQuoteType,"","",0);
 	}
 	/*
 	 获取持仓均线的值
 	 */
    function getPositionValue() {
//  	var text=$("#commodity_title").text();
//		var length=$(".tab_content .position0").length;
		var positionValue=0
//		if(length!=0){
//			for(var i=0;i<length;i++){
//				var text1=$(".tab_content .position0").eq(i).text();
//				if(text.indexOf(text1)>=0){
//					positionValue=$(".tab_content .position3").eq(i).text();
//				}
//			}
//		}
		return positionValue;
    }
    function dealOnRtnQuoteData(data,totalVolume) {
	var dosizeL=Number($("#doSize").val());
	var Parameters=data.Parameters;
	var range=checkRange();
	if(totalVolume==""){
		totalVolume=0;
		$("#totalVolume").val(Parameters.TotalVolume);
		return
	}
	if(range==1440){
		return;
	}
	var Volume=Number(Parameters.TotalVolume)-Number(totalVolume);
	var lastVolume=Number(volumeChartData.volume[volumeChartData.volume.length-1]);
	if(Volume<=0){
		Volume=0;
	}
	var freshVolume=lastVolume+Volume;
	var lastVolume1=0;
	var freshVolume1=0;
	if(CandlestickData != undefined){
		lastVolume1=Number(CandlestickVolumeData.volume[CandlestickVolumeData.volume.length-1]);
		freshVolume1=lastVolume1+Volume;
	}
	var lastPrices=Number(Parameters.LastPrice).toFixed(dosizeL);
	var DateTimeStamp=Parameters.DateTimeStamp.replace(/-/g, "/");
	if(timeData.timeLabel[timeData.timeLabel.length-1]==undefined){
		return
	}
	var oldTime1=(timeData.timeLabel[timeData.timeLabel.length-1]).replace(/-/g, "/");
	var oldTime=Math.round(new Date(oldTime1).getTime()/1000);
	var nowShjian=Math.round(new Date(DateTimeStamp).getTime()/1000)
	var time1=parseInt(nowShjian / (60*range));
	var newTime=Number(time1*60* range);
	var time5=new Date(parseInt(newTime) * 1000);
	var time6=getNowFormatDate(time5);
	var length=$("#positionList .position3").length;
	var text=$("#CommodityNo").text();
	var positionValue=getPositionValue();
	if(oldTime==newTime){
		var lastPrices1=lastPrices;
		if(timeData.prices[timeData.prices.length-1]==lastPrices1 && volumeChartData.volume[volumeChartData.volume.length-1]==freshVolume){
			
		}else{
			timeData.prices[timeData.prices.length-1]=lastPrices1;	
        	volumeChartData.volume[volumeChartData.volume.length-1]=freshVolume;
        	drawChartTime(positionValue);
		}
	}else{
			timeData.timeLabel.push(time6);
        	timeData.prices.push(lastPrices);
        	volumeChartData.time.push(time6);
	        volumeChartData.volume.push(0);
	        drawChartTime(positionValue);
	}
	if(CandlestickData != undefined){
		var oldTime2=(CandlestickData.categoryData[CandlestickData.categoryData.length-1]);
		var oldTime3=Math.round(new Date(oldTime2).getTime()/1000);
		if(oldTime3==newTime){
			var length=CandlestickData.values.length;
	      	var lowPrices=Number(CandlestickData.values[length-1][2]);
    		var highPrices=Number(CandlestickData.values[length-1][3]);
    		var closePrices=lastPrices;
    		if(lastPrices >=highPrices){
    			highPrices=lastPrices
    		}
    		if(lowPrices>=highPrices){
    			lowPrices=lastPrices
    		}
    		CandlestickData.values[length-1]=[CandlestickData.values[length-1][0],closePrices,lowPrices,highPrices];
    		CandlestickVolumeData.volume[CandlestickVolumeData.volume.length-1]=freshVolume1;
		}else{
			CandlestickData.categoryData.push(time6)
    		CandlestickData.values.push([lastPrices,lastPrices,lastPrices,lastPrices]);
    		CandlestickVolumeData.time.push(time6)
    		CandlestickVolumeData.volume.push(0);
	       	CandlestickData.categoryData=CandlestickData.categoryData.slice(-500);
	        CandlestickData.values=CandlestickData.values.slice(-500);
	        CandlestickVolumeData.time=CandlestickVolumeData.time.slice(-500);
	        CandlestickVolumeData.volume=CandlestickVolumeData.volume.slice(-500);
		}
	}
	$("#totalVolume").val(Parameters.TotalVolume)
	drawChartCandlestick(positionValue);
}
function drawChartTime(positionValue) {
//	var value=$(".carbon_time").eq(1).hasClass("active")
//	if(value){
//		if(CandlestickData.volume==null){
//	       		return
//	    }
		var option = setOptionTime(timeData,positionValue);
	    timeChart.setOption(option);
        timeChart.resize();
        timeChart.group="group1";
		var volumeChartOption=volumeChartSetOption(volumeChartData)
        volumeChart.setOption(volumeChartOption);
        volumeChart.resize();
       	volumeChart.group="group1";
//	}
}
function drawChartCandlestick(positionValue) {
//	var value=$(".carbon_time").eq(0).hasClass("active")
	if(CandlestickData != undefined){
//		if(!value){
	       	if(CandlestickData.categoryData==null){
	       		return
	       	}
			var option=setOptionCandlestick(CandlestickData,positionValue);
			var option1=volumeChartCandlestickSetOption(CandlestickVolumeData);
   			CandlestickChart.setOption(option);
	  		CandlestickChart.resize();
		  	CandlestickChart.group="group2";
		  	CandlestickVolumeChart.setOption(option1);
	  		CandlestickVolumeChart.resize();
		  	CandlestickVolumeChart.group="group2";
       	}
//	}
}
function checkRange() {
	var range=1;
	var length=$("#candlestickList a").length;
	for(var i=0;i<length;i++){
		if($("#candlestickList a").eq(i).hasClass("mui-active")==true){
			var data=$("#candlestickList a").eq(i).attr("data");
			if(data==0){
				range=1
			}else{
				range=data;
			}
		}
	}
	return range;
}
    function getNowFormatDate(date) {
        var seperator1 = "-";
        var seperator2 = ":";
        var month = date.getMonth() + 1;
        var strDate = date.getDate();
        var getMinutes=date.getMinutes();
        var getSeconds="00"
        if (month >= 1 && month <= 9) {
            month = "0" + month;
        }
        if (strDate >= 0 && strDate <= 9) {
            strDate = "0" + strDate;
        }
        if(Number(getMinutes)<10){
        	getMinutes="0"+getMinutes;
        }
        var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate
                + " " + date.getHours() + seperator2 + getMinutes
                + seperator2 + getSeconds;
        return currentdate;
    }
    //绑定选择k线的点击事件
   mui("#candlestickList").on("tap","a",function(){
	   	var data=this.getAttribute("data");
	   	sendQueryHistory(data);
		document.getElementById("candlestickNav").style.display="none";
		CandlestickVolumeChart.resize();
		CandlestickChart.resize();
	})
   //绑定选择品种的事件
	mui("#choiceProductList").on("tap","li",function(){
		var spanList=this.getElementsByTagName("span");
		if(spanList[0].innerHTML==null || spanList[0].innerHTML==""){
			return
		}
		var titleList=document.getElementById("choiceProductButton").getElementsByTagName("p");
		var contractCode1=titleList[1].innerHTML;
		var localCommodity1=getLocalCacheCommodity(contractCode1);
		Quote.doUnSubscribe(localCommodity1.ExchangeNo,localCommodity1.CommodityNo,localCommodity1.MainContract);
		titleList[0].innerHTML=spanList[0].innerHTML;
		titleList[1].innerHTML=spanList[1].innerHTML;
		var contractCode=spanList[1].innerHTML;
		var localCommodity=getLocalCacheCommodity(contractCode);
		initInputData(localCommodity);
		sendQueryHistory(0);
		$("#totalVolume").val("");
		document.getElementById("choiceProductList").style.display="none";
		Quote.doSubscribe(localCommodity.ExchangeNo,localCommodity.CommodityNo,localCommodity.MainContract);
	})
	//初始化input标签
	function initInputData(Parameters) {
		$("#trade_data #lastPrice").val(Parameters.lastPrice);
		$("#trade_data #miniTikeSize").val(Parameters.miniTikeSize);
		$("#trade_data #contractSize").val(Parameters.ContractSize);
		$("#trade_data #exchangeNo").val(Parameters.ExchangeNo);
		$("#trade_data #commodeityNo").val(Parameters.CommodityNo);
		$("#trade_data #contractNo").val(Parameters.MainContract);
		$("#trade_data #doSize").val(Parameters.DotSize);
		$("#trade_data #totalVolume").val(0);
	}
	function updateData(data) {
		var dosize=Number($("#doSize").val());
		var color="#333333";
//		console.log(JSON.stringify(data));
		var Parameters=data.Parameters;
		var LastPrice=Number(Parameters.LastPrice);
		var PreSettlePrice=Number(Parameters.PreSettlePrice);
		if(LastPrice>PreSettlePrice){
			color="#f25749"
		}else if(LastPrice<PreSettlePrice){
			color="#1f9947"
		}else{
			color="#333333";
		}
		$("#lastPrices").text(Parameters.LastPrice.toFixed(dosize)).css("color",color);
		var ChangeRate=Number(Parameters.ChangeRate);
		if(ChangeRate>0){
			color="#f25749"
		}else if(ChangeRate<0){
			color="#1f9947"
		}else{
			color="#333333";
		}
		$("#riseAndFallRange").text(Parameters.ChangeRate.toFixed(2)+"%").css("color",color);
		var ChangeValue=Number(Parameters.ChangeValue);
		if(ChangeValue>0){
			color="#f25749"
		}else if(ChangeValue<0){
			color="#1f9947"
		}else{
			color="#333333";
		}
		$("#riseAndFall").text(Parameters.ChangeValue.toFixed(2)).css("color",color);
		var AskPrice1=Number(Parameters.AskPrice1);
		var BidPrice1=Number(Parameters.BidPrice1);
		var AskQty1=Number(Parameters.AskQty1);
		var BidQty1=Number(Parameters.BidQty1);
		$("askPrice1").html(AskQty1+"/<i>"+AskPrice1+"</i>");
		$("#bidPrice1").html("<i>"+BidPrice1+"</i>/"+BidQty1+"");
		updateWidth(AskQty1,BidQty1);
		$("#bidPrice1Button").text(BidPrice1);
		$("#askPrice1Button").text(AskPrice1)
	}
	/*
	 修改柱子的长短
	 */
	function  updateWidth(AskQty1,BidQty1) {
		var buyRate=AskQty1/(AskQty1+BidQty1);
		$("#buySellNumLeft").css("width",parseInt(buyRate*50)+"px");
		var sellRate=BidQty1/(AskQty1+AskQty1);
		$("#buySellNumright").css("width",parseInt(sellRate*50)+"px");
	}
