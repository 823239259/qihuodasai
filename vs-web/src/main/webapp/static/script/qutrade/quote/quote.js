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
/**
 * 第一次获取数据数组的下表
 * */
var firstTimeLength=1
function quoteHandleData(evt){
	var data = evt.data;
	var jsonData = JSON.parse(data);
	var method = jsonData.Method;
	if(method == "OnRspLogin"){
		Quote.doAllQryCommodity();
	}else if(method == "OnRspQryCommodity"){
		generateRealTimeQuote(jsonData);
		sendHistory(0);
	}else if(method == "OnRtnQuote"){
		quotePush(jsonData);
 		var commodityNo = $("#commodeityNo").val();
		 var totalVolume=$("#totalVolume").val();
		 if(commodityNo==jsonData.Parameters.CommodityNo){
		 	dealOnRtnQuoteData(jsonData,totalVolume);
		 	lightChartData(jsonData);
		 }
	}else if(method == "OnRspQryHistory"){
		if(firstTimeLength==1){
				getSubscript(jsonData.Parameters.ColumNames);
				firstTimeLength=2;
			}
		handleTimeChartData(jsonData);
		processingData(jsonData);
	}else if(method == "OnRspSubscribe"){
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
					html+= '  left_xiangmu '+cls+'" data-index-com = "'+quoteIndex+'" data-tion-com = "'+(commodityNo+mainContract)+'" data="'+commodityNo+'&amp;'+mainContract+'&amp;'+exchangeNo+'">'+
					'	<li class="futures_name">'+
					'    	<span class="futures_mz">'+commodityName+'</span>'+
					'    	<span class="futures_bm">'+commodityNo+mainContract+'</span>'+
					'    </li>'+
					'    <li class="qlast" style="color: #666666;">-</li>'+
					'    <li class="futures_number">-</li>'+
					'   <li class="scal" style="color: #666666;">-</li>'+
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
			$("#gdt").css("background","#333");
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
	var changeRate = parseFloat(param.ChangeRate).toFixed(2);
	var qChangeValue = parseFloat(param.ChangeValue).toFixed(doSize);
	var color = " #ff5500";
	var bs = "↑";
	var jj = "+";
	if(param.ChangeRate < 0){
		 bs = "↓";
		 jj = "";
		 color = "#0bffa4";
	 }
	var oldObj = $("ul[data-tion-com='"+contractCode+"']");
	var qlast = $("ul[data-tion-com='"+contractCode+"'] li[class = 'qlast']");
	var futuresNumber = $("ul[data-tion-com='"+contractCode+"'] li[class = 'futures_number']");
	var scal = $("ul[data-tion-com='"+contractCode+"'] li[class = 'scal']");
	var oldLastPrice = parseFloat(qlast.text()).toFixed(doSize);
	qlast.text(lastPrice+" "+bs);
	futuresNumber.text(jj + qChangeValue);
	scal.text(jj + changeRate + "%");
	qlast.css("color",color);
	futuresNumber.css("color",color);
	scal.css("color",color);
	var cls = "quote-index"+oldObj.attr("data-index-com");
	if(lastPrice > oldLastPrice){
		rise(cls);
	}else if(lastPrice < oldLastPrice){
		fall(cls);
	}
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
	//根据行情更新持仓列表
	updatePositionByQuote(param);
	//根据行情更新挂单列表 
	updateDesignateByQuote(param);
	//更新浮动盈亏
	updateFloatingfit(param);
	//更新资金明细中的浮动盈亏
	sumListfloatingProfit();
	//更新持仓浮动盈亏总和
	updateHoldProfit();
	//更新账户资产
	updateAccountBalance();
	//更新右边边行情
	updateRight(param);
	//初始化设置最新价格
	setTradeLastPrice(param);
	//设置买卖的浮动价格
	setBuyAndSellFloatPrice(param);
	//设置盘口
	setHandicap(param);
	//更新止损/止盈最新价格
	updateStopLossLastPrice(param);
	//更新条件单弹出框最新价格
	setConditionLastPrice(param);
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
		var cls = "select_option"+i;
		var html = "<option class = '"+cls+"' value='"+contractCode+"'>"+commodityName+"     "+commodityNo+mainContract+"</option>";
		$("#select_commodity").append(html);
		$("#contion_time_contractCode").append(html);
		$("#condition_price_contractCode").append(html);
		if(i == 0){
			$("#trade_data #lastPrice").val(data.lastPrice);
			$("#trade_data #miniTikeSize").val(data.miniTikeSize);
			$("#trade_data #contractSize").val(data.ContractSize);
			$("#trade_data #exchangeNo").val(data.ExchangeNo);
			$("#trade_data #commodeityNo").val(commodityNo);
			$("#trade_data #contractNo").val(mainContract);
			$("#trade_data #doSize").val(data.DotSize);
		}
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
		var localCommodity = getLocalCacheCommodity(contractCode);
		var dotSize = 2;
		if(localCommodity != undefined){
			dotSize = localCommodity.DotSize;
		}
		var lastPrice = parseFloat(param.LastPrice).toFixed(dotSize);
		if(isNaN(lastPrice)){return;}
		$("#trade_data #lastPrice").val(lastPrice);
		if(getMoneyNumberIndex() == 0){
			$("#money_number").val(lastPrice);
			setMoneyNumberIndex(1);
		}
	}
}
/**
 * 更新止损止盈最新价格
 * @param param
 */
function updateStopLossLastPrice(param){
	var commodityNo = param.CommodityNo;
	var contractNo = param.ContractNo;
	var contractCode = commodityNo + contractNo;
	var oldContractCode = $("#stopHoldContractCode").val();
	if(contractCode == oldContractCode){
		var lastPrice = param.LastPrice;
		$("#stop_lastPrice").text(lastPrice);
		$("#loss_lastPrice").text(lastPrice);
		var localCommodity = localCacheCommodity[contractCode];
		var contractSize = localCommodity.ContractSize;
		var miniTikeSize = localCommodity.MiniTikeSize;
		var stopDrection = $("#stopHoldDrection").val();
		var stopInputprice = $("#stop_inputprice").val();
		if(stopInputprice.length != 0){
			var stopInputnum = $("#stop_inputnum").val();
			if(stopInputnum.length != 0){
				var  price =  doGetFloatingProfit(lastPrice,stopInputprice,contractSize,miniTikeSize,stopInputnum,stopDrection);
				$("#stop_yjks").text(price);
			}
		}
		var lossInputprice = $("#loss_inputprice").val();
		if(lossInputprice.length != 0){
			var lossInputnum = $("#loss_inputnum").val();
			if(lossInputnum.length != 0){
				var  price =  doGetFloatingProfit(lastPrice,lossInputprice,contractSize,miniTikeSize,lossInputnum,stopDrection);
				$("#loss_yjks").text(price);
			}
		}
		
	}
}
/**
 * 设置条件单弹出框最新价
 * @param param
 */
function setConditionLastPrice(param){
	var commodityNo = param.CommodityNo;
	var contractNo  = param.ContractNo;
	var contractCode = commodityNo + contractNo;
	var conditionPriceContractCode = $("#condition_price_contractCode").val();
	var conditionTimeContractCode = $("#contion_time_contractCode").val();
	var lastPrice = param.LastPrice;
	if(conditionPriceContractCode == contractCode){
		$("#condition_price_lastPrice").text(lastPrice);
	}
	if(conditionTimeContractCode == contractCode){
		$("#condition_time_lastPrice").text(lastPrice);
	}
}
/**
 * 设置买卖的浮动价格
 * @param
 */
function setBuyAndSellFloatPrice(param){
	return;
	var priceType = $("input[type='radio']:checked").val();
	//如果限价就不需要修改
	if(priceType == 0)return;
	var newCommdityNo = param.CommodityNo;
	var newContractNo = param.ContractNo; 
	var contractCode = newCommdityNo+newContractNo;
	var localCommodity = localCacheCommodity[contractCode];
	if(localCommodity != undefined){
		var selectContractCode = $("#select_commodity").val();
		if(selectContractCode == contractCode){
			var miniTikeSize = localCommodity.MiniTikeSize;
			var dotSize = localCommodity.DotSize;
			var lastPrice = param.LastPrice;
			$("#float_buy").text(doGetMarketPrice(lastPrice, miniTikeSize, 0,dotSize));
			$("#float_sell").text(doGetMarketPrice(lastPrice, miniTikeSize, 1,dotSize));
		}
	}
};
/**
 * 行情列表绑定点击事件
 * @param cls
 * @returns
 */
var leftSelectQuoteObj = 0;
function addQuoteListBindClick(cls){
	$("."+cls+"").bind("click",function(){
		 var obj = $(this);
		 var left_xiangmu   = $(".futuresList .left_xiangmu");
		 left_xiangmu.each(function(){
			 left_xiangmu.removeClass('on');
		 });
		 obj.addClass('on');
		 var contractCode = obj.attr("data-tion-com");
		 setLocalCacheSelect(contractCode);
		 clearRightData();
		 setSelectOption(contractCode);
		 setMoneyNumberIndex(0);
		 clearHandicapData();
		 var HisQuoteType=checkHisQuoteType();
		 sendHistory(HisQuoteType);
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
	var val = $('input:radio:checked').val();
	if(val == 0){
		var money = $("#money_number").val();
		$("#float_buy").text(money);
		$("#float_sell").text(money);
	}
	//$("#float_buy").text(doGetMarketPrice(lastPrice, miniTikeSize, 0));
	//$("#float_sell").text(doGetMarketPrice(lastPrice, miniTikeSize, 1));
	updateRight(localQoute);
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
	var $holdAvgPrice = $("ul[data-tion-position = '"+contractCode+"'] li[class = 'position3']").text();
	var $openAvgPrice = $("ul[data-tion-position = '"+contractCode+"'] li[class = 'position9']").text();
	var $floatP = $("ul[data-tion-position = '"+contractCode+"'] li[class = 'position10']");
	var $holdNum = $("ul[data-tion-position = '"+contractCode+"'] li[class = 'position1']").text();
	var drection = $("ul[data-tion-position = '"+contractCode+"'] li[class = 'position2']").attr("data-drection");
	//判断该行情对应是否在列表中
	if($openAvgPrice == undefined || $openAvgPrice.length <= 0){
		return;
	}
	var localCommodity = localCacheCommodity[contractCode];
	if(localCommodity == undefined){
		return;
	}
	var contractSize = localCommodity.ContractSize;
	var miniTikeSize = localCommodity.MiniTikeSize;
	var floatP = doGetFloatingProfit(parseFloat(lastPrice), parseFloat($holdAvgPrice) , contractSize,miniTikeSize,parseInt($holdNum),drection);
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
 * 设置盘口信息
 */
function setHandicap(param){
	var commodityNo = param.CommodityNo;
	var contractNo = param.ContractNo;
	var contractCode =commodityNo+contractNo ;
	if(contractCode != getLocalCacheSelect())return;
	var localCommodity = getLocalCacheCommodity(contractCode);
	var dotSize = 0;
	if(localCommodity != undefined){
		dotSize = localCommodity.DotSize;
	}
	//卖价
	var askPrice = param.AskPrice1;
	//买价
	var bidPrice1 = param.BidPrice1;
	//最新价
	var lastPrice = param.LastPrice;
	//开仓价
	var openPrice = param.OpenPrice;
	//最高价
	var highPrice = param.HighPrice;
	//最低价
	var lowPrice = param.LowPrice;
	//昨收
	var preClosingPrice = param.PreClosingPrice;
	//结算价
	var settlePrice = param.SettlePrice;
	//卖量
	var totalAskQty = param.AskQty1;
	//买量
	var totalBidQty = param.BidQty1;
	//涨幅
	var changeRate = param.ChangeRate;
	//涨跌值
	var changeValue = param.ChangeValue;
	//当日成交量
	var totalVolume = param.TotalVolume;
	//持仓量
	var position = param.Position;
	//昨结
	var preSettlePrice = param.PreSettlePrice;
	var color = "#FFFFFF";
	if(askPrice > preSettlePrice){
		color = "#ff5500";
	}else if (askPrice < preSettlePrice){
		color = "#0bffa4";
	}else{
		color = "#FFFFFF";
	}
	
	$("#pkmj_sell").text(parseFloat(askPrice).toFixed(dotSize));
	$("#pkmj_sell").css("color",color);
	if(bidPrice1 > preSettlePrice){
		color = "#ff5500";
	}else if (bidPrice1 < preSettlePrice){
		color = "#0bffa4";
	}else{
		color = "#FFFFFF";
	}
	$("#pkmj_buy").text(parseFloat(bidPrice1).toFixed(dotSize));
	$("#pkmj_buy").css("color",color);
	if(lastPrice > preSettlePrice){
		color = "#ff5500";
	}else if (lastPrice < preSettlePrice){
		color = "#0bffa4";
	}else{
		color = "#000000";
	}
	$("#pklastparice").text(parseFloat(lastPrice).toFixed(dotSize));
	$("#pklastparice").css("color",color);
	if(openPrice > preSettlePrice){
		color = "#ff5500";
	}else if (openPrice < preSettlePrice){
		color = "#0bffa4";
	}else{
		color = "#000000";
	}
	$("#pkopenprice").text(parseFloat(openPrice).toFixed(dotSize));
	$("#pkopenprice").css("color",color);
	if(highPrice > preSettlePrice){
		color = "#ff5500";
	}else if (highPrice < preSettlePrice){
		color = "#0bffa4";
	}else{
		color = "#000000";
	}
	$("#pkhightprice").text(parseFloat(highPrice).toFixed(dotSize));
	$("#pkhightprice").css("color",color);
	if(lowPrice > preSettlePrice){
		color = "#ff5500";
	}else if (lowPrice < preSettlePrice){
		color = "#0bffa4";
	}else{
		color = "#000000";
	}
	$("#pklowprice").text(parseFloat(lowPrice).toFixed(dotSize));
	$("#pklowprice").css("color",color);
	if(settlePrice > preSettlePrice){
		color = "#ff5500";
	}else if (settlePrice < preSettlePrice){
		color = "#0bffa4";
	}else{
		color = "#000000";
	}
	$("#pkjs").text(parseFloat(settlePrice).toFixed(dotSize));
	$("#pkjs").css("color",color);
	if(totalAskQty > preSettlePrice){
		color = "#ff5500";
	}else if (totalAskQty < preSettlePrice){
		color = "#0bffa4";
	}else{
		color = "#000000";
	}
	$("#pkml_sell").text(totalAskQty);
	$("#pkml_sell").css("color","#000000");
	if(totalBidQty > preSettlePrice){
		color = "#ff5500";
	}else if (totalBidQty < preSettlePrice){
		color = "#0bffa4";
	}else{
		color = "#000000";
	}
	$("#pkml_buy").text(totalBidQty);
	$("#pkml_buy").css("color","#000000");
	if(changeValue > 0){
		color = "#ff5500";
	}else if (changeValue < 0){
		color = "#0bffa4";
	}else{
		color = "#000000";
	}
	$("#pkzd").text(parseFloat(changeValue).toFixed(dotSize)+"/"+parseFloat(changeRate).toFixed(2)+"%");
	$("#pkzd").css("color",color);
	$("#pktrademl").text(totalVolume);
	$("#pktrademl").css("color","#000000");
	$("#pkccml").text(position);
	$("#pkccml").css("color","#000000");
	$("#pkzj").text(parseFloat(preSettlePrice).toFixed(dotSize));
	$("#pkccml").css("color","#000000");
}
/**
 * 清理盘口数据
 */
function clearHandicapData(){
	$("#pkzd").text(0);
	$("#pktrademl").text(0);
	$("#pkccml").text(0);
	$("#pkzj").text(0);
	$("#pkmj_sell").text(0);
	$("#pkmj_buy").text(0);
	$("#pklastparice").text(0);
	$("#pkopenprice").text(0);
	$("#pkhightprice").text(0);
	$("#pklowprice").text(0);
	$("#pkjs").text(0);
	$("#pkml_sell").text(0);
	$("#pkml_buy").text(0);
	$(".fr").css("color","#000000");
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
	$("#right_zj_0").text(parseFloat(preSettlePrice).toFixed(doSize));
	//涨跌
	$("#right_zd_1").text(parseFloat(param.ChangeValue).toFixed(doSize));
	//今开
	var openPrice = param.OpenPrice;
	if(openPrice > preSettlePrice){
		$("#right_jk_1").css("color","#ff5500");
	}else if(openPrice < preSettlePrice){
		$("#right_jk_1").css("color","#0bffa4");
	}
	$("#right_jk_1").text(parseFloat(openPrice).toFixed(doSize));
	//幅度
	var changeRate = parseFloat(param.ChangeRate).toFixed(2);
	var color = "#ff5500";
	var bs = "↑";
	var jj = "+";
	var rate = param.ChangeRate;
	if(rate < 0){
		 bs = "↓";
		 jj = "";
		 color = "#0bffa4";
	 }else if(rate == 0){
		 color = "#FFFFFF";
	 }
	$("#right_lastPrice_0").css("color",color);
	//$("#right_zj_0").css("color",color);
	$("#right_zd_1").css("color",color);
	//$("#right_zd_1").css("color",color);
	//$("#right_zl_3").css("color",color);
	$("#right_zd_3").css("color",color);
	$("#right_fd_2").css("color",color);
	$("#right_fd_2").text(jj+changeRate+"%");
	//最高
	var highPrice = param.HighPrice;
	if(highPrice > preSettlePrice){
		$("#right_zg_2").css("color","#ff5500");
	}else if(highPrice < preSettlePrice){
		$("#right_zg_2").css("color","#0bffa4");
	}
	$("#right_zg_2").text(parseFloat(param.HighPrice).toFixed(doSize));
	//总量
	$("#right_zl_3").text(parseFloat(param.TotalVolume).toFixed(0));
	//最低价
	var lowPrice = param.LowPrice;
	if(lowPrice > preSettlePrice){
		$("#right_zd_3").css("color","#ff5500");
	}else if(lowPrice < preSettlePrice){
		$("#right_zd_3").css("color","#0bffa4");
	}
	$("#right_zd_3").text(parseFloat(lowPrice).toFixed(doSize));
	
	//卖五-卖一
	var askPrice5 = param.AskPrice5;
	if(askPrice5 > preSettlePrice){
		$("#right_sell_0").css("color","#ff5500");
	}else if(askPrice5 < preSettlePrice){
		$("#right_sell_0").css("color","#0bffa4");
	}
	$("#right_sell_0").text(parseFloat(askPrice5).toFixed(doSize));
	$("#right_sell_1").text(param.AskQty5);
	var askPrice4 = param.AskPrice4;
	if(askPrice4 > preSettlePrice){
		$("#right_sell_2").css("color","#ff5500");
	}else if(askPrice4 < preSettlePrice){
		$("#right_sell_2").css("color","#0bffa4");
	}
	$("#right_sell_2").text(parseFloat(askPrice4).toFixed(doSize));
	$("#right_sell_3").text(param.AskQty4);
	var askPrice3 = param.AskPrice3;
	if(askPrice3 > preSettlePrice){
		$("#right_sell_4").css("color","#ff5500");
	}else if(askPrice3 < preSettlePrice){
		$("#right_sell_4").css("color","#0bffa4");
	}
	$("#right_sell_4").text(parseFloat(askPrice3).toFixed(doSize));
	$("#right_sell_5").text(param.AskQty3);
	var  askPrice2 = param.AskPrice2;
	if(askPrice2 > preSettlePrice){
		$("#right_sell_6").css("color","#ff5500");
	}else if(askPrice2 < preSettlePrice){
		$("#right_sell_6").css("color","#0bffa4");
	}
	$("#right_sell_6").text(parseFloat(askPrice2).toFixed(doSize));
	$("#right_sell_7").text(param.AskQty2);
	var  askPrice1 = param.AskPrice1;
	if(askPrice1 > preSettlePrice){
		$("#right_sell_8").css("color","#ff5500");
	}else if(askPrice1 < preSettlePrice){
		$("#right_sell_8").css("color","#0bffa4");
	}
	$("#right_sell_8").text(parseFloat(askPrice1).toFixed(doSize));
	$("#right_sell_9").text(param.AskQty1);
	//买五-买一
	var  bidPrice1 = param.BidPrice1;
	if(bidPrice1 > preSettlePrice){
		$("#right_buy_0").css("color","#ff5500");
	}else if(bidPrice1 < preSettlePrice){
		$("#right_buy_0").css("color","#0bffa4");
	}
	$("#right_buy_0").text(parseFloat(bidPrice1).toFixed(doSize));
	$("#right_buy_1").text(param.BidQty1);
	var  bidPrice2 = param.BidPrice2;
	if(bidPrice2 > preSettlePrice){
		$("#right_buy_2").css("color","#ff5500");
	}else if(bidPrice2 < preSettlePrice){
		$("#right_buy_2").css("color","#0bffa4");
	}
	$("#right_buy_2").text(parseFloat(bidPrice2).toFixed(doSize));
	$("#right_buy_3").text(param.BidQty2);
	var  bidPrice3 = param.BidPrice3;
	if(bidPrice3 > preSettlePrice){
		$("#right_buy_4").css("color","#ff5500");
	}else if(bidPrice3 < preSettlePrice){
		$("#right_buy_4").css("color","#0bffa4");
	}
	$("#right_buy_4").text(parseFloat(bidPrice3).toFixed(doSize));
	$("#right_buy_5").text(param.BidQty3);
	var  bidPrice4 = param.BidPrice4;
	if(bidPrice4 > preSettlePrice){
		$("#right_buy_6").css("color","#ff5500");
	}else if(bidPrice4 < preSettlePrice){
		$("#right_buy_6").css("color","#0bffa4");
	}
	$("#right_buy_6").text(parseFloat(bidPrice4).toFixed(doSize));
	$("#right_buy_7").text(param.BidQty4);
	var  bidPrice5 = param.BidPrice5;
	if(bidPrice5 > preSettlePrice){
		$("#right_buy_8").css("color","#ff5500");
	}else if(bidPrice5 < preSettlePrice){
		$("#right_buy_8").css("color","#0bffa4");
	}
	$("#right_buy_8").text(parseFloat(bidPrice5).toFixed(doSize));
	$("#right_buy_9").text(param.BidQty5);
}
/**
 * 清除右边数据内容
 */
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
 /**
  * 发送查询的历史数据的方法
  * */
 	function sendHistory(HisQuoteType){
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
function dealOnRtnQuoteData(data,totalVolume){
	var dosizeL=Number($("#doSize").val());
	var Parameters=data.Parameters;
	var range=checkRange();
	if(totalVolume=="0"){
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
	var DateTimeStamp=Parameters.DateTimeStamp;
	if(timeData.timeLabel[timeData.timeLabel.length-1]==undefined){
		return
	}
	var oldTime1=(timeData.timeLabel[timeData.timeLabel.length-1]);
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
function drawChartTime(positionValue){
	var value=$(".carbon_time").eq(1).hasClass("active");
	console.log(value);
	if(value){
		if(volumeChartData.volume==null){
	       		return
	    }
		console.log('454545')
		var option = setOptionTime(timeData,positionValue);
	    timeChart.setOption(option);
        timeChart.resize();
        timeChart.group="group1";
		var volumeChartOption=volumeChartSetOption(volumeChartData)
        volumeChart.setOption(volumeChartOption);
        volumeChart.resize();
       	volumeChart.group="group1";
	}
}
function drawChartCandlestick(positionValue){
	var value=$(".carbon_time").eq(0).hasClass("active")
	if(CandlestickData != undefined){
		if(!value){
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
	}
}
function checkRange(){
	var range=1;
	var length=$(".carbon_time").length;
	for(var i=0;i<length;i++){
		if($(".carbon_time").eq(i).hasClass("active")==true){
			var data=$(".carbon_time").eq(i).attr("data")
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
    function checkHisQuoteType(){
    	var length=$(".carbon_time").length;
		for(var i=0;i<length;i++){
			if($(".carbon_time").eq(i).hasClass("active")==true){
				var data=$(".carbon_time").eq(i).attr("data");
				HisQuoteType=data;
			}
		}
    	return HisQuoteType;
    	
    }
