/**
 * 行情初始化
 */
initQuote();
/**
 * 全局缓存品种
 */
var localCacheCommodity = {};
/**
 * 保存全局缓存品种到数据
 */
var localCacheCommodityArray = new Array();
/**
 * 全局缓存实时行情的最新数据
 */
var localCacheQuote = {}
/**
 * 全局缓存当前选中的品种合约
 */
var localCacheSelect = null;
/**
 * 处理行情数据
 * @param evt
 */
function quoteHandleData(evt){
	var data = evt.data;
	var jsonData = JSON.parse(data);
	var method = jsonData.Method;
	if(method == "OnRspLogin"){
		Quote.doAllQryCommodity();
	}else if(method == "OnRspQryCommodity"){
		generateRealTimeQuote(jsonData);
	}else if(method = "OnRtnQuote"){
		quotePush(jsonData);
	}
}
/**
 * 行情列表索引
 */
var quoteIndex = 0;
/**
 * 生成实时行情列表
 * @param param
 */
function generateRealTimeQuote(obj){
	var param = obj.Parameters;
	var paramSize = param.length;
	for(var i = 0 ; i < paramSize ; i ++){
		var data = param[i];
		var commodityName = data.CommodityName;
		var commodityNo = data.CommodityNo;
		var mainContract = data.MainContract;
		var exchangeNo = data.ExchangeNo;
		var cls = "quote-index"+quoteIndex;
		var html = '<ul class="';
					if(i == 0){
						html+=' on ';
						var contractCode  = commodityNo+mainContract;
						setLocalCacheSelect(contractCode);
						$("#commodity_title").text(commodityName+"  "+contractCode);
					}
					html+= '  left_xiangmu '+cls+'" data-tion-com = "'+(commodityNo+mainContract)+'" data="'+commodityNo+'&amp;'+mainContract+'&amp;'+exchangeNo+'">'+
					'	<li class="futures_name">'+
					'    	<span class="futures_mz">'+commodityName+'</span>'+
					'    	<span class="futures_bm">'+commodityNo+mainContract+'</span>'+
					'    </li>'+
					'    <li class="qlast" style="color: #30bf30;"></li>'+
					'    <li class="futures_number"></li>'+
					'   <li class="scal" style="color: #30bf30;"></li>'+
					'</ul>';
		$(".futuresList").append(html);
		updataQuoteIndex();
		addQuoteListBindClick(cls);
	}
	subscribe(param);
	//添加品种到全局缓存
	setLocalCacheCommodity(param);
	loadSelectData(param);
	setTradeInitData(param);
}
/**
 * 行情搜索
 */
function searchQuote(){
	var length = localCacheCommodityArray.length;
	var inputValue = $("#quotation_futures").val();
	console.log(inputValue.length);
	if(inputValue.length == 0){
		$(".left_xiangmu").css("display","block");
		return;
	}
	for (var i = 0; i < length; i++) {
		var data = localCacheCommodityArray[i];
		var commodityName = data.CommodityName;
		var commodityNo = data.CommodityNo;
		var mainContract = data.MainContract;
		var contractCode  = commodityNo+mainContract;
		var dom = $("ul[data-tion-com='"+contractCode+"']");
		if((commodityName.toLowerCase()).indexOf(inputValue.toLowerCase()) != -1 || (contractCode.toLowerCase()).indexOf(inputValue.toLowerCase()) != -1){
			dom.css("display","block");
		}else{
			dom.css("display","none");
		}
	}
}
/**
 * 更新左边实时行情列表
 * @param param
 */
function updateRealTimeQuote(param){
	var commodityNo = param.CommodityNo;
	var contractNo = param.ContractNo;
	var contractCode = commodityNo + contractNo;
	var localCommodity = getLocalCacheCommodity(contractCode);
	if(localCommodity == undefined){
		return ;
	}
	var doSize = localCommodity.DotSize;
	var lastPrice = parseFloat(param.LastPrice).toFixed(doSize);
	var changeRate = parseFloat(param.ChangeRate).toFixed(doSize);
	var qChangeValue = parseFloat(param.ChangeValue).toFixed(doSize);
	var color = " #ff5500";
	var bs = "↑";
	var jj = "+";
	if(param.ChangeRate < 0){
		 bs = "↓";
		 jj = "";
		 color = "#0bffa4";
	 }
	var qlast = $("ul[data-tion-com='"+contractCode+"'] li[class = 'qlast']");
	var futuresNumber = $("ul[data-tion-com='"+contractCode+"'] li[class = 'futures_number']");
	var scal = $("ul[data-tion-com='"+contractCode+"'] li[class = 'scal']");
	qlast.text(lastPrice+" "+bs);
	futuresNumber.text(jj + qChangeValue);
	scal.text(jj + changeRate + "%");
	qlast.css("color",color);
	futuresNumber.css("color",color);
	scal.css("color",color);
	
}
/**
 * 行情推送处理
 * @param obj
 */
function quotePush(obj){
	var param = obj.Parameters;
	//更新实时数据
	updateRealTimeQuote(param);
	//添加行情到全局缓存
	setLocalCacheQuote(param);
	//更新浮动盈亏
	updateFloatingfit(param);
	//更新资金明细中的浮动盈亏
	sumListfloatingProfit();
	//更新右边边行情
	updateRight(param);
	//初始化设置最新价格
	setTradeLastPrice(param);
	//设置买卖的浮动价格
	setBuyAndSellFloatPrice(param);
}
/**
 * 订阅行情
 */
function subscribe(param){
	var paramSize = param.length;
	for (var i = 0; i < paramSize; i++) {
		var data = param[i];
		var commodityNo = data.CommodityNo;
		var mainContract = data.MainContract;
		var exchangeNo = data.ExchangeNo;
		Quote.doSubscribe(exchangeNo, commodityNo, mainContract);
	}
}
/**
 * 加载下拉框品种数据
 * @param param
 */
function loadSelectData(param){
	var paramSize = param.length;
	for(var i = 0 ; i < paramSize; i++){
		var data = param[i];
		var commodityName  = data.CommodityName;
		var commodityNo = data.CommodityNo;
		var mainContract = data.MainContract;
		var contractCode = commodityNo+mainContract;
		var html = "<option value='"+contractCode+"'>"+commodityName+"     "+commodityNo+mainContract+"</option>";
		$("#select_commodity").append(html);
	}
}
/**
 * 初始化交易需要的数据
 * @param param
 */
function setTradeInitData(param){
	var selectContractCode = $("#select_commodity").val();
	var paramSize = param.length;
	for(var i  = 0 ; i < paramSize ; i++){
		var data = param[i];
		var newCommdityNo = data.CommodityNo;
		var newContractNo = data.MainContract;
		var contractCode = newCommdityNo+newContractNo;
		if(selectContractCode == contractCode){
			var localCommodity = localCacheCommodity[contractCode];
			$("#trade_data #miniTikeSize").val(localCommodity.MiniTikeSize);
			$("#trade_data #contractSize").val(localCommodity.ContractSize);
			$("#trade_data #exchangeNo").val(localCommodity.ExchangeNo);
			$("#trade_data #commodeityNo").val(localCommodity.CommodityNo);
			$("#trade_data #contractNo").val(localCommodity.MainContract);
			$("#trade_data #doSize").val(localCommodity.DotSize);
			break;
		}
	}
}
/**
 * 委托价格输入框的设置次数
 */
var moneyNumberIndex = 0 ;
/**
 * 设置最新价格
 * @param price
 */
function setTradeLastPrice(param){
	var selectContractCode = $("#select_commodity").val();
	var newCommdityNo = param.CommodityNo;
	var newContractNo = param.ContractNo; 
	var contractCode = newCommdityNo+newContractNo;
	if(selectContractCode == contractCode){
		var lastPrice = parseFloat(param.LastPrice).toFixed(2);
		if(isNaN(lastPrice)){return;}
		$("#trade_data #lastPrice").val(lastPrice);
		if(getMoneyNumberIndex() == 0){
			$("#money_number").val(lastPrice);
			setMoneyNumberIndex(1);
		}
	}
}
/**
 * 设置买卖的浮动价格
 * @param
 */
function setBuyAndSellFloatPrice(param){
	var newCommdityNo = param.CommodityNo;
	var newContractNo = param.ContractNo; 
	var contractCode = newCommdityNo+newContractNo;
	var localCommodity = localCacheCommodity[contractCode];
	if(localCommodity != undefined){
		var selectContractCode = $("#select_commodity").val();
		if(selectContractCode == contractCode){
			var miniTikeSize = localCommodity.MiniTikeSize;
			var lastPrice = param.LastPrice;
			$("#float_buy").text(doGetMarketPrice(lastPrice, miniTikeSize, 0));
			$("#float_sell").text(doGetMarketPrice(lastPrice, miniTikeSize, 1));
		}
	}
};
/**
 * 行情列表绑定点击事件
 * @param cls
 * @returns
 */
function addQuoteListBindClick(cls){
	$("."+cls+"").bind("click",function(){
		 var obj = $(this);
		 var left_xiangmu   = $(".futuresList .left_xiangmu");
		 left_xiangmu.each(function(){
			 left_xiangmu.removeClass('on');
		 });
		 obj.addClass('on');
		 var contractCode = obj.attr("data-tion-com");
		 setSelectOption(contractCode);
		 setLocalCacheSelect(contractCode);
		 clearRightData();
		 setMoneyNumberIndex(0);
		 
	});
}
/**
 * 点击左边切换下拉框选中项
 * @param contractCode
 */
function setSelectOption(contractCode){
	$("#select_commodity").val(contractCode);
	var localCommodity = localCacheCommodity[contractCode];
	var localQoute = localCacheQuote[contractCode];
	var miniTikeSize = localCommodity.MiniTikeSize;
	var lastPrice = localQoute.LastPrice;
	$("#trade_data #lastPrice").val(lastPrice);
	$("#trade_data #miniTikeSize").val(miniTikeSize);
	$("#trade_data #contractSize").val(localCommodity.ContractSize);
	$("#trade_data #exchangeNo").val(localCommodity.ExchangeNo);
	$("#trade_data #commodeityNo").val(localCommodity.CommodityNo);
	$("#trade_data #contractNo").val(localCommodity.MainContract);
	$("#trade_data #doSize").val(localCommodity.DotSize);
	$("#money_number").val(localQoute.LastPrice);
	$("#commodity_title").text(localCommodity.CommodityName+"  "+contractCode);
	$("#float_buy").text(doGetMarketPrice(lastPrice, miniTikeSize, 0));
	$("#float_sell").text(doGetMarketPrice(lastPrice, miniTikeSize, 1));
}
/**
 * 更新行情列表索引
 * @returns
 */
function updataQuoteIndex(){
	quoteIndex++;
}
/**
 * 添加行情到全局缓存
 * @param param
 */
function setLocalCacheQuote(param){
	var commodityNo = param.CommodityNo;
	var contractNo = param.ContractNo;
	var contractCode = commodityNo + contractNo;
	localCacheQuote[contractCode] = param;
}
/**
 * 全局缓存中取行情最新数据
 * @param obj
 */
function getLocalCacheQuote(obj){
	return localCacheQuote[obj];
}
/**
 * 添加品种到全局缓存
 * @param param
 */
function setLocalCacheCommodity(param){
	var paramSize = param.length;
	for (var i = 0; i < paramSize; i++) {
		var data = param[i];
		var commodityNo = data.CommodityNo;
		var contractNo = data.MainContract;
		var contractCode = commodityNo + contractNo;
		localCacheCommodity[contractCode] = data;
		localCacheCommodityArray[i] = data;
	}
}
/**
 * 全局缓存中取品种
 * @param obj
 */
function getLocalCacheCommodity(obj){
	return localCacheCommodity[obj];
}
/**
 * 更新浮动盈亏
 * @param param
 */
function updateFloatingfit(param){
	var lastPrice = param.LastPrice;
	var newCommdityNo = param.CommodityNo;
	var newContractNo = param.ContractNo;
	var contractCode = newCommdityNo + newContractNo;
	var $float = $("ul[data-tion-position = '"+contractCode+"'] li[class = 'position4']");
	var $openAvgPrice = $("ul[data-tion-position = '"+contractCode+"'] li[class = 'position9']").text();
	var $floatP = $("ul[data-tion-position = '"+contractCode+"'] li[class = 'position10']");
	var $holdNum = $("ul[data-tion-position = '"+contractCode+"'] li[class = 'position1']").text();
	var drection = $("ul[data-tion-position = '"+contractCode+"'] li[class = 'position2']").attr("data-drection");
	//判断该行情对应是否在列表中
	if($openAvgPrice == undefined){
		return;
	}
	var localCommodity = localCacheCommodity[contractCode];
	if(localCommodity == undefined){
		return;
	}
	var contractSize = localCommodity.ContractSize;
	var miniTikeSize = localCommodity.MiniTikeSize;
	var floatP = doGetFloatingProfit(parseFloat(lastPrice), parseFloat($openAvgPrice) , contractSize,miniTikeSize,parseInt($holdNum),drection);
	var floatProfit = floatP +":"+ localCommodity.CurrencyNo;
	$float.text(floatProfit);
	$floatP.text(floatP);
	$float.css("width","160px");
	if(parseFloat(floatP) < 0 ){
		$float.css("color","green");
	}else if(parseFloat(floatP) > 0){
		$float.css("color","red");
	}else{
		$float.css("color","white");
	}
}
/**
 * 设置当前选中的合约项
 * @param param
 */
function setLocalCacheSelect(param){
	localCacheSelect = param;
}
/**
 *  取当前选中的合约项
 * @returns {___anonymous_localCacheSelect}
 */
function getLocalCacheSelect(){
	return localCacheSelect;
}
/**
 * 设置当前设置委托初始化价格的次数
 */
function setMoneyNumberIndex(count){
	moneyNumberIndex = count;
}
/**
 * 获取当前设置委托初始化价格的次数
 */
function getMoneyNumberIndex(){
	return moneyNumberIndex;
}
/**
 * 更新右边数据
 * @param param
 */
function updateRight(param){
	var commodityNo = param.CommodityNo;
	var contractNo = param.ContractNo;
	var contractCode =commodityNo+contractNo ;
	if(contractCode != getLocalCacheSelect())return;
	var localCommodity = localCacheCommodity[contractCode];
	var doSize = 2;
	if(localCommodity != undefined){
		doSize = localCommodity.DotSize;
	}
	var preSettlePrice = param.PreSettlePrice;
	//
	//最新价
	$("#right_lastPrice_0").text(parseFloat(param.LastPrice).toFixed(doSize));
	//昨结
	$("#right_zj_0").text(parseFloat(param.PreSettlePrice).toFixed(doSize));
	//涨跌
	$("#right_zd_1").text(parseFloat(param.ChangeValue).toFixed(doSize));
	//今开
	$("#right_jk_1").text(parseFloat(param.OpenPrice).toFixed(doSize));
	//幅度
	var changeRate = parseFloat(param.ChangeRate).toFixed(doSize);
	var color = " #ff5500";
	var bs = "↑";
	var jj = "+";
	if(param.ChangeRate < 0){
		 bs = "↓";
		 jj = "";
		 color = "#0bffa4";
	 }
	$("#right_zd_1").css("color",color);
	$("#right_fd_2").text(jj+changeRate);
	$("#right_fd_2").css("color",color);
	//最高
	$("#right_zg_2").text(parseFloat(param.HighPrice).toFixed(doSize));
	//总量
	$("#right_zl_3").text(parseFloat(param.TotalVolume).toFixed(doSize));
	//总量
	$("#right_zd_3").text(parseFloat(param.LowPrice).toFixed(doSize));
	//卖五-卖一
	var askPrice5 = param.AskPrice5;
	if(askPrice5 > preSettlePrice){
		$("#right_sell_0").css("color","red");
	}else{
		$("#right_sell_0").css("color","green");
	}
	$("#right_sell_0").text(parseFloat(askPrice5).toFixed(doSize));
	$("#right_sell_1").text(parseFloat(param.AskQty5).toFixed(doSize));
	var askPrice4 = param.AskPrice4;
	if(askPrice4 > preSettlePrice){
		$("#right_sell_2").css("color","red");
	}else{
		$("#right_sell_2").css("color","green");
	}
	$("#right_sell_2").text(parseFloat(askPrice4).toFixed(doSize));
	$("#right_sell_3").text(parseFloat(param.AskQty4).toFixed(doSize));
	var askPrice3 = param.AskPrice3;
	if(askPrice3 > preSettlePrice){
		$("#right_sell_4").css("color","red");
	}else{
		$("#right_sell_4").css("color","green");
	}
	$("#right_sell_4").text(parseFloat(askPrice3).toFixed(doSize));
	$("#right_sell_5").text(parseFloat(param.AskQty3).toFixed(doSize));
	var  askPrice2 = param.AskPrice2;
	if(askPrice2 > preSettlePrice){
		$("#right_sell_6").css("color","red");
	}else{
		$("#right_sell_6").css("color","green");
	}
	$("#right_sell_6").text(parseFloat(askPrice2).toFixed(doSize));
	$("#right_sell_7").text(parseFloat(param.AskQty2).toFixed(doSize));
	var  askPrice1 = param.AskPrice1;
	if(askPrice1 > preSettlePrice){
		$("#right_sell_8").css("color","red");
	}else{
		$("#right_sell_8").css("color","green");
	}
	$("#right_sell_8").text(parseFloat(askPrice1).toFixed(doSize));
	$("#right_sell_9").text(parseFloat(param.AskQty1).toFixed(doSize));
	//买五-买一
	var  bidPrice5 = param.BidPrice5;
	if(bidPrice5 > preSettlePrice){
		$("#right_buy_0").css("color","red");
	}else{
		$("#right_buy_0").css("color","green");
	}
	$("#right_buy_0").text(parseFloat(bidPrice5).toFixed(doSize));
	$("#right_buy_1").text(parseFloat(param.BidQty5).toFixed(doSize));
	var  bidPrice4 = param.BidPrice4;
	if(bidPrice4 > preSettlePrice){
		$("#right_buy_2").css("color","red");
	}else{
		$("#right_buy_2").css("color","green");
	}
	$("#right_buy_2").text(parseFloat(bidPrice4).toFixed(doSize));
	$("#right_buy_3").text(parseFloat(param.BidQty4).toFixed(doSize));
	var  bidPrice3 = param.BidPrice3;
	if(bidPrice3 > preSettlePrice){
		$("#right_buy_4").css("color","red");
	}else{
		$("#right_buy_4").css("color","green");
	}
	$("#right_buy_4").text(parseFloat(bidPrice3).toFixed(doSize));
	$("#right_buy_5").text(parseFloat(param.BidQty3).toFixed(doSize));
	var  bidPrice2 = param.BidPrice2;
	if(bidPrice2 > preSettlePrice){
		$("#right_buy_6").css("color","red");
	}else{
		$("#right_buy_6").css("color","green");
	}
	$("#right_buy_6").text(parseFloat(bidPrice2).toFixed(doSize));
	$("#right_buy_7").text(parseFloat(param.BidQty2).toFixed(doSize));
	var  bidPrice1 = param.BidPrice1;
	if(bidPrice1 > preSettlePrice){
		$("#right_buy_8").css("color","red");
	}else{
		$("#right_buy_8").css("color","green");
	}
	$("#right_buy_8").text(parseFloat(bidPrice1).toFixed(doSize));
	$("#right_buy_9").text(parseFloat(param.BidQty1).toFixed(doSize));
}
function clearRightData(){
	//最新价
	$("#right_lastPrice_0").text(0.00);
	//昨结
	$("#right_zj_0").text(0.00);
	//涨跌
	$("#right_zd_1").text(0.00);
	//今开
	$("#right_jk_1").text(0.00);
	//幅度
	$("#right_fd_2").text(0.00);
	//最高
	$("#right_zg_2").text(0.00);
	//总量
	$("#right_zl_3").text(0.00);
	//总量
	$("#right_zd_3").text(0.00);
	//卖五-卖一
	$("#right_sell_0").text(0.00);
	$("#right_sell_1").text(0.00);
	$("#right_sell_2").text(0.00);
	$("#right_sell_3").text(0.00);
	$("#right_sell_4").text(0.00);
	$("#right_sell_5").text(0.00);
	$("#right_sell_6").text(0.00);
	$("#right_sell_7").text(0.00);
	$("#right_sell_8").text(0.00);
	$("#right_sell_9").text(0.00);
	//买五-买一
	$("#right_buy_0").text(0.00);
	$("#right_buy_1").text(0.00);
	$("#right_buy_2").text(0.00);
	$("#right_buy_3").text(0.00);
	$("#right_buy_4").text(0.00);
	$("#right_buy_5").text(0.00);
	$("#right_buy_6").text(0.00);
	$("#right_buy_7").text(0.00);
	$("#right_buy_8").text(0.00);
	$("#right_buy_9").text(0.00);
}
/**
 * 行情加载历史数据
 * @param currenExchangeNo
 * @param currenCommodityNo
 * @param currenContractNo
 */
function loadHistory(currenExchangeNo, currenCommodityNo, currenContractNo){
	Quote.doQryFirstHistory(currenExchangeNo, currenCommodityNo, currenContractNo);
}
/**
 * 定时查询增量数据
 */
function openSetInterval(currenCommodityNo, currenContractNo, currenExchangeNo, hisQuoteType, beginTime, endTime, count){
	cc = setInterval(function(){
		loadAddData(currenCommodityNo, currenContractNo, currenExchangeNo, hisQuoteType, beginTime, endTime, count)
    },10000);
}
/**
 * 查询增量数据
 * @param currenCommodityNo
 * @param currenContractNo
 * @param currenExchangeNo
 * @param hisQuoteType
 * @param beginTime
 * @param endTime
 * @param count
 */
function loadAddData(currenCommodityNo, currenContractNo, currenExchangeNo, hisQuoteType, beginTime, endTime, count){
	Quote.doQryHistory(currenExchangeNo, currenCommodityNo, currenContractNo, hisQuoteType, beginTime, endTime, count);
}
