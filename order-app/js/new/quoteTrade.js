
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
	//最新价
	$('#bidPrice1Button').text(fixedPriceByContract(CacheQuoteSubscribe.getCacheContractQuote(CommodityNoContractNo, "LastQuotation", "LastPrice"),SuperCommodityNo));
	$('#askPrice1Button').text(fixedPriceByContract(CacheQuoteSubscribe.getCacheContractQuote(CommodityNoContractNo, "LastQuotation", "LastPrice"),SuperCommodityNo));
	
	
	//分时图
	clearCache();
	is_fenshi=true;
	Quote.doQryHistory(exchangeNo, SuperCommodityNo, contractNo, 0, '', '', '');
	
	
	//为select赋值
	$('#contract').val(CommodityNoContractNo);
});


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
	
	//最新价
	$('#bidPrice1Button').text(fixedPriceByContract(CacheQuoteSubscribe.getCacheContractQuote(CommodityNoContractNo, "LastQuotation", "LastPrice"),SuperCommodityNo));
	$('#askPrice1Button').text(fixedPriceByContract(CacheQuoteSubscribe.getCacheContractQuote(CommodityNoContractNo, "LastQuotation", "LastPrice"),SuperCommodityNo));
	
	
	
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









