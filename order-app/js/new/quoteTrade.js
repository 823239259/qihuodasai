
var SuperCommodityNo;
var contractNo;
var exchangeNo;
$("#list").on('tap','li',function(){
	
	var index = $(this).index();
	SuperCommodityNo = $('#list li').eq(index).children().eq(1).text();
	var CommodityNoContractNo = $('#list li').eq(index).children().eq(2).text();
	contractNo = CacheQuoteBase.getCacheContractAttribute(SuperCommodityNo, "MainContract");
	exchangeNo=CacheQuoteBase.getCacheContractAttribute(SuperCommodityNo, "ExchangeNo");
	$("#totalVolume").val('');
	$("#commodityNo").val(SuperCommodityNo);
	$("#contractNo").val( CacheQuoteBase.getCacheContractAttribute(SuperCommodityNo, "MainContract"));
	
	//初始化模拟下单页面的值
	$('#lastPrices').text(fixedPriceByContract(CacheQuoteSubscribe.getCacheContractQuote(CommodityNoContractNo, "LastQuotation", "LastPrice"),SuperCommodityNo));
	$('#riseAndFall').text(fixedPriceByContract(CacheQuoteSubscribe.getCacheContractQuote(CommodityNoContractNo, "LastQuotation", "ChangeValue"),SuperCommodityNo));
	$('#riseAndFallRange').text(parseFloat(CacheQuoteSubscribe.getCacheContractQuote(CommodityNoContractNo, "LastQuotation", "ChangeRate")).toFixed(2) + "%");
	//买一价
	$('#askPrice1').text(fixedPriceByContract(CacheQuoteSubscribe.getCacheContractQuote(CommodityNoContractNo, "LastQuotation", "BidPrice1"),SuperCommodityNo)+'/');
	$('#askQty1').text(CacheQuoteSubscribe.getCacheContractQuote(CommodityNoContractNo, "LastQuotation", "BidQty1"));
	//卖一价
	$('#bidPrice1').text(fixedPriceByContract(CacheQuoteSubscribe.getCacheContractQuote(CommodityNoContractNo, "LastQuotation", "AskPrice1"),SuperCommodityNo)+'/');
	$('#bidQty1').text(CacheQuoteSubscribe.getCacheContractQuote(CommodityNoContractNo, "LastQuotation", "AskQty1"));
	//卖一价
	$('#bidPrice1Button').text(fixedPriceByContract(CacheQuoteSubscribe.getCacheContractQuote(CommodityNoContractNo, "LastQuotation", "AskPrice1"),SuperCommodityNo));
	//买一价
	$('#askPrice1Button').text(fixedPriceByContract(CacheQuoteSubscribe.getCacheContractQuote(CommodityNoContractNo, "LastQuotation", "BidPrice1"),SuperCommodityNo));
	
	pankou(CommodityNoContractNo);
	
	
	
	
	
	//分时图
	clearCache();
	is_fenshi=true;
	Quote.doQryHistory(exchangeNo, SuperCommodityNo, contractNo, 0, '', '', '');
	
	
	//为select赋值
	$('#contract').val(CommodityNoContractNo);
});

//为盘口赋值
function pankou(CommodityNoContractNo){
	
	//盘口--最新价
	$('#pklastparice').text(fixedPriceByContract(CacheQuoteSubscribe.getCacheContractQuote(CommodityNoContractNo, "LastQuotation", "LastPrice"),SuperCommodityNo));
	//盘口--卖五
	$('#sell_8').text(fixedPriceByContract(CacheQuoteSubscribe.getCacheContractQuote(CommodityNoContractNo, "LastQuotation", "AskPrice5"),SuperCommodityNo));
	//盘口--卖五-人数
	$('#sell_9').text(CacheQuoteSubscribe.getCacheContractQuote(CommodityNoContractNo, "LastQuotation", "AskQty5"));
	//盘口--开盘
	$('#pkopenprice').text(fixedPriceByContract(CacheQuoteSubscribe.getCacheContractQuote(CommodityNoContractNo, "LastQuotation", "OpenPrice"),SuperCommodityNo));
	//盘口--卖四
	$('#sell_6').text(fixedPriceByContract(CacheQuoteSubscribe.getCacheContractQuote(CommodityNoContractNo, "LastQuotation", "AskPrice4"),SuperCommodityNo));
	//盘口--卖四--人数
	$('#sell_7').text(CacheQuoteSubscribe.getCacheContractQuote(CommodityNoContractNo, "LastQuotation", "AskQty4"));
	//盘口--最高
	$('#pkhightprice').text(fixedPriceByContract(CacheQuoteSubscribe.getCacheContractQuote(CommodityNoContractNo, "LastQuotation", "HighPrice"),SuperCommodityNo));
	//盘口--卖三
	$('#sell_4').text(fixedPriceByContract(CacheQuoteSubscribe.getCacheContractQuote(CommodityNoContractNo, "LastQuotation", "AskPrice3"),SuperCommodityNo));
	//盘口--卖三--人数
	$('#sell_5').text(CacheQuoteSubscribe.getCacheContractQuote(CommodityNoContractNo, "LastQuotation", "AskQty3"));
	//盘口--最低
	$('#pklowprice').text(fixedPriceByContract(CacheQuoteSubscribe.getCacheContractQuote(CommodityNoContractNo, "LastQuotation", "LowPrice"),SuperCommodityNo));
	//盘口--卖二
	$('#sell_2').text(fixedPriceByContract(CacheQuoteSubscribe.getCacheContractQuote(CommodityNoContractNo, "LastQuotation", "AskPrice2"),SuperCommodityNo));
	//盘口--卖二--人数
	$('#sell_3').text(CacheQuoteSubscribe.getCacheContractQuote(CommodityNoContractNo, "LastQuotation", "AskQty2"));
	//盘口--涨跌
	$('#pkzd').text(fixedPriceByContract(CacheQuoteSubscribe.getCacheContractQuote(CommodityNoContractNo, "LastQuotation", "ChangeValue"),SuperCommodityNo));
	//盘口--卖一
	$('#sell_0').text(fixedPriceByContract(CacheQuoteSubscribe.getCacheContractQuote(CommodityNoContractNo, "LastQuotation", "AskPrice1"),SuperCommodityNo));
	//盘口--卖一--人数
	$('#sell_1').text(CacheQuoteSubscribe.getCacheContractQuote(CommodityNoContractNo, "LastQuotation", "AskQty1"));
	//盘口--成交量
	$('#pktrademl').text(CacheQuoteSubscribe.getCacheContractQuote(CommodityNoContractNo, "LastQuotation", "TotalVolume"));
	//盘口--买一
	$('#buy_0').text(fixedPriceByContract(CacheQuoteSubscribe.getCacheContractQuote(CommodityNoContractNo, "LastQuotation", "BidPrice1"),SuperCommodityNo));
	//盘口--买一--人数
	$('#buy_1').text(CacheQuoteSubscribe.getCacheContractQuote(CommodityNoContractNo, "LastQuotation", "BidQty1"));
	//盘口--持仓量
	$('#pkccml').text(CacheQuoteSubscribe.getCacheContractQuote(CommodityNoContractNo, "LastQuotation", "Position"));
	//盘口--买二
	$('#buy_2').text(fixedPriceByContract(CacheQuoteSubscribe.getCacheContractQuote(CommodityNoContractNo, "LastQuotation", "BidPrice2"),SuperCommodityNo));
	//盘口--买二--人数
	$('#buy_3').text(CacheQuoteSubscribe.getCacheContractQuote(CommodityNoContractNo, "LastQuotation", "BidQty2"));
	//盘口--昨结
	$('#pkzj').text(fixedPriceByContract(CacheQuoteSubscribe.getCacheContractQuote(CommodityNoContractNo, "LastQuotation", "PreSettlePrice"),SuperCommodityNo));
	//盘口--买三
	$('#buy_4').text(fixedPriceByContract(CacheQuoteSubscribe.getCacheContractQuote(CommodityNoContractNo, "LastQuotation", "BidPrice3"),SuperCommodityNo));
	//盘口--买三--人数
	$('#buy_5').text(CacheQuoteSubscribe.getCacheContractQuote(CommodityNoContractNo, "LastQuotation", "BidQty3"));
	//盘口--买四
	$('#buy_6').text(fixedPriceByContract(CacheQuoteSubscribe.getCacheContractQuote(CommodityNoContractNo, "LastQuotation", "BidPrice4"),SuperCommodityNo));
	//盘口--买四--人数
	$('#buy_7').text(CacheQuoteSubscribe.getCacheContractQuote(CommodityNoContractNo, "LastQuotation", "BidQty4"));
	//盘口--买五
	$('#buy_8').text(fixedPriceByContract(CacheQuoteSubscribe.getCacheContractQuote(CommodityNoContractNo, "LastQuotation", "BidPrice5"),SuperCommodityNo));
	//盘口--买五--人数
	$('#buy_9').text(CacheQuoteSubscribe.getCacheContractQuote(CommodityNoContractNo, "LastQuotation", "BidQty5"));
}



$('#contract').change(function(){
	var CommodityNoContractNo = $(this).children('option:selected').val();
	SuperCommodityNo = CommodityNoContractNo.substr(0,CommodityNoContractNo.length-4);
	contractNo = CacheQuoteBase.getCacheContractAttribute(SuperCommodityNo, "MainContract");
	exchangeNo=CacheQuoteBase.getCacheContractAttribute(SuperCommodityNo, "ExchangeNo");
	
	$("#totalVolume").val('');
	$("#commodityNo").val(SuperCommodityNo);
	$("#contractNo").val( CacheQuoteBase.getCacheContractAttribute(SuperCommodityNo, "MainContract"));
	
	//初始化模拟下单页面的值
	$('#lastPrices').text(fixedPriceByContract(CacheQuoteSubscribe.getCacheContractQuote(CommodityNoContractNo, "LastQuotation", "LastPrice"),SuperCommodityNo));
	$('#riseAndFall').text(fixedPriceByContract(CacheQuoteSubscribe.getCacheContractQuote(CommodityNoContractNo, "LastQuotation", "ChangeValue"),SuperCommodityNo));
	$('#riseAndFallRange').text(parseFloat(CacheQuoteSubscribe.getCacheContractQuote(CommodityNoContractNo, "LastQuotation", "ChangeRate")).toFixed(2) + "%");
	//买一价
	$('#askPrice1').text(fixedPriceByContract(CacheQuoteSubscribe.getCacheContractQuote(CommodityNoContractNo, "LastQuotation", "BidPrice1"),SuperCommodityNo)+'/');
	$('#askQty1').text(CacheQuoteSubscribe.getCacheContractQuote(CommodityNoContractNo, "LastQuotation", "BidQty1"));
	//卖一价
	$('#bidPrice1').text(fixedPriceByContract(CacheQuoteSubscribe.getCacheContractQuote(CommodityNoContractNo, "LastQuotation", "AskPrice1"),SuperCommodityNo)+'/');
	$('#bidQty1').text(CacheQuoteSubscribe.getCacheContractQuote(CommodityNoContractNo, "LastQuotation", "AskQty1"));
	
	//卖一价
	$('#bidPrice1Button').text(fixedPriceByContract(CacheQuoteSubscribe.getCacheContractQuote(CommodityNoContractNo, "LastQuotation", "AskPrice1"),SuperCommodityNo));
	//买一价
	$('#askPrice1Button').text(fixedPriceByContract(CacheQuoteSubscribe.getCacheContractQuote(CommodityNoContractNo, "LastQuotation", "BidPrice1"),SuperCommodityNo));
	
	pankou(CommodityNoContractNo);
	
	
	
	
	
	
	//分时图
	clearCache();
	is_fenshi=true;
	Quote.doQryHistory(exchangeNo, SuperCommodityNo, contractNo, 0, '', '', '');
	
	
});







$("#lightChartButton").on("tap", function() {
	lightChart00();
});

$("#timeChartButton").on("tap",function(){
	timeChart00();
});
document.getElementById("chiocecandlestickButton").addEventListener("tap",function(){
	click1k();
});


function click1k(){
		clearCache();
		is_k = true;
		is_fenshi=false;
		is_shandian=false;
		console.log('exchangeNo:'+exchangeNo+'SuperCommodityNo:'+SuperCommodityNo+'contractNo:'+contractNo);
		Quote.doQryHistory(exchangeNo, SuperCommodityNo, contractNo, 1, '', '', '');
	}
function click5k(){
		clearCache();
		is_k = true;
		is_fenshi=false;
		is_shandian=false;
		Quote.doQryHistory(exchangeNo, SuperCommodityNo, contractNo, 5, '', '', '');
	}
function click15k(){
	clearCache();
	is_k = true;
	is_fenshi=false;
	is_shandian=false;
	Quote.doQryHistory(exchangeNo, SuperCommodityNo, contractNo, 15, '', '', '');
}

function click30k(){
	clearCache();
	is_k = true;
	is_fenshi=false;
	is_shandian=false;
	Quote.doQryHistory(exchangeNo, SuperCommodityNo, contractNo, 30, '', '', '');
}
function click1440k(){
	clearCache();
	is_k = true;
	is_fenshi=false;
	is_shandian=false;
	Quote.doQryHistory(exchangeNo, SuperCommodityNo, contractNo, 1440, '', '', '');
}

function timeChart00(){
	clearCache();
	is_k = false;
	is_fenshi=true;
	is_shandian=false;
	Quote.doQryHistory(exchangeNo, SuperCommodityNo, contractNo, 0, '', '', '');
}

function lightChart00(){
	is_k = false;
	is_fenshi=false;
	is_shandian=true;
}


var is_k = false;
var is_fenshi=false;
var is_shandian=false;

function clearCache(){
	timeData={
	"timeLabel":[],
	"prices":[],
	"time":[]
	}
	volumeChartData={
		"time":[],
		"volume":[]
	}
	rawData=[];
	CandlestickVolumeData={
		"time":[],
		"volume":[]
	}
	CandlestickData={
		"categoryData":[],
		"values":[]
	};
	lightChartTime={
		"time":[],
		"price":[]
	}
}

$("#candlestickNav").on("tap","a", function() {
	var data = $(this).attr("data");
	data=Number(data);
	switch(data) {
		case 1:
			click1k();
			break;
		case 5:
			click5k()
			break;
		case 15:
			click15k()
			break;
		case 30:
			click30k()
			break;
		case 1440:
			click1440k()
			break;
		default:
	}
});









