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
	
	//委托页面title
	$('#entrustCommodityName').text(CacheQuoteBase.getCacheContractAttribute(SuperCommodityNo, "CommodityName"));
	$('#entrustContract').text(CacheQuoteBase.getCacheContractAttribute(SuperCommodityNo, "CommodityNo")
		+CacheQuoteSubscribe.getCacheContractQuote(CommodityNoContractNo, "LastQuotation", "ContractNo"));
	
	//闪电设置页面title
	$('#flashSetingName').text(CacheQuoteBase.getCacheContractAttribute(SuperCommodityNo, "CommodityName"));
	$('#flashSetingCon').text(CacheQuoteBase.getCacheContractAttribute(SuperCommodityNo, "CommodityNo")
		+CacheQuoteSubscribe.getCacheContractQuote(CommodityNoContractNo, "LastQuotation", "ContractNo"));
	
	
	//分时图
	clearCache();
	is_fenshi=true;
	Quote.doQryHistory(exchangeNo, SuperCommodityNo, contractNo, 0, '', '', '');
	
	
	//为select赋值
	$('#contract').val(CommodityNoContractNo);
	console.log(mui.cacheData.getFlash(phone+SuperCommodityNo+'zfzje'));
	//判断是否存在闪电设置
	if(mui.cacheData.getFlash(phone+SuperCommodityNo+'zfzje')!=null){
//		$('#placeOrderBidPrice1').unbind('tap');
		$('#placeOrder00').attr('href','#');
	}
	
	
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
	
	//委托页面title
	$('#entrustCommodityName').text(CacheQuoteBase.getCacheContractAttribute(SuperCommodityNo, "CommodityName"));
	$('#entrustContract').text(CacheQuoteBase.getCacheContractAttribute(SuperCommodityNo, "CommodityNo")
		+CacheQuoteSubscribe.getCacheContractQuote(CommodityNoContractNo, "LastQuotation", "ContractNo"));
	
	//闪电设置页面title
	$('#flashSetingName').text(CacheQuoteBase.getCacheContractAttribute(SuperCommodityNo, "CommodityName"));
	$('#flashSetingCon').text(CacheQuoteBase.getCacheContractAttribute(SuperCommodityNo, "CommodityNo")
		+CacheQuoteSubscribe.getCacheContractQuote(CommodityNoContractNo, "LastQuotation", "ContractNo"));
	
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

function dealDrection(value){
	if(value==0){
		return '买入';
	}else{
		return '卖出';
	}
}



$('#placeOrderBidPrice1').on('tap',function(){
	
	$('#flashMore').text('看多价:');
	$('#flashMorePrice').text($('#bidPrice1Button').text());
	$('#orderListButton').text('看多买入');
	
	mui.app_request(
		'game/order/contractParam',
		{
			'contractNo':SuperCommodityNo
			
		},function(result){
			
			$('#_TradeNum_ .chioce-button').remove();
			$('#_exchange_rate_string_').text('');
			$('#_exchange_rate_').text('');
			$('#_poundage_').text('');
			$('#_performance_margin_').text('');
			
			//汇率设置
			var currencyNo = CacheQuoteBase.getCacheContractAttribute(SuperCommodityNo, "CurrencyNo");//币种  USD
			var CNYExchangeRate;//汇率
			
			$.each(currencyArray, function(index,value) {
				if(currencyArray[index].CurrencyNo ==currencyNo){
					$('#_exchange_rate_string_').text('汇率 - '+currencyArray[index].CurrencyName+'人民币');
					$('#_exchange_rate_').text('1'+currencyArray[index].CurrencyName+'='+currencyArray[index].CNYExchangeRate+'元人民币');
					CNYExchangeRate = currencyArray[index].CNYExchangeRate;
				}
			});
			
			$.each(result.data,function(index, obj){
				tplFillData("_TradeNum_", "tp_TradeNum_", obj, FillType.after);
				mui.cacheData.removezy(obj.lever+SuperCommodityNo);
				mui.cacheData.savezy(obj.lever+SuperCommodityNo,obj.stopProfit);
				
				mui.cacheData.removezs(obj.lever+SuperCommodityNo);
				mui.cacheData.savezs(obj.lever+SuperCommodityNo,obj.stopLoss);
				
				mui.cacheData.removesxf(obj.lever+SuperCommodityNo);
				mui.cacheData.savesxf(obj.lever+SuperCommodityNo,obj.tradeFee);//手续费
				
				mui.cacheData.removeSlipBond(obj.lever+SuperCommodityNo);
				mui.cacheData.saveSlipBond(obj.lever+SuperCommodityNo,obj.slipBond);//滑点保证金
			});
			
			$("#_TradeNum_ button:first-child").addClass('on');
			var num0=$("#_TradeNum_ button:first-child").text().substr(0,1);
			
			//初始化 手续费
			var initsxf = mui.cacheData.getsxf(num0+SuperCommodityNo+'sxf');
			$('#_poundage_').text('('+currencyNo+(toFixedFloatNumber(initsxf*num0/CNYExchangeRate,4))+')'+' '+initsxf*num0+'元');
			
			
			var arrayCFZY0= mui.cacheData.getzy(num0+SuperCommodityNo+'zy').split(',');//触发止盈数组
			$('#_stopWin010_ .chioce-button').remove();
			
			$.each(arrayCFZY0,function(index,value){
					$('#_stopWin010_').append('<button class="chioce-button">'+value+'</button>');
			});
			
			$('#_stopWin010_ button:first-child').addClass('on');
			
			$("#_stopWin010_ .chioce-button").on('tap',function(){
				
				$(this).addClass('on'); // 设置被点击元素为黄色
				$(this).siblings(".chioce-button").removeClass('on'); // 去除所有同胞元素的黄色样式
			});
			
			var arrayCFZS0= mui.cacheData.getzy(num0+SuperCommodityNo+'zs').split(',');//触发止盈数组
			$('#_stopLoss010_ .chioce-button').remove();
			$.each(arrayCFZS0,function(index,value){
					$('#_stopLoss010_').append('<button class="chioce-button">'+value+'</button>');
			});
			$('#_stopLoss010_ button:first-child').addClass('on');
			
			//初始化 履约保证金
			var initHdbzj = mui.cacheData.getSlipBond(num0+SuperCommodityNo+'hdbzj');//滑点保证金
			var initzsj=$('#_stopLoss010_ button:first-child').text();//止损价
			var initlybzj = (initHdbzj*num0)+Number(initzsj);//滑点保证金*手数+止损价=履约保证金
			$('#_performance_margin_').text(initlybzj+'元');
			
			//初始化支付金额
			$('#payment_amount').text(initsxf*num0+initlybzj);
			var payment_amount = initsxf*num0+initlybzj;
			//初始化余额不足
			if(Number($('#difference').text())-payment_amount<0){
				$('#differenceNotFull').text('余额不足');
			}
			
			
			$("#_stopLoss010_ .chioce-button").on('tap',function(){
					
				$(this).addClass('on'); // 设置被点击元素为黄色
				$(this).siblings(".chioce-button").removeClass('on'); // 去除所有同胞元素的黄色样式
				
				//履约保证金
				var zsj=$(this).text();//止损价
				var lybzj = (initHdbzj*num0)+Number(zsj);//滑点保证金*手数+止损价=履约保证金
				$('#_performance_margin_').text(lybzj+'元');
				
				
				
			});
			
			$("#_TradeNum_ .chioce-button").on('tap',function(){
				
				$(this).addClass('on'); // 设置被点击元素为黄色
				$(this).siblings(".chioce-button").removeClass('on'); // 去除所有同胞元素的黄色样式
				
				var num;
				if($(this).text().length<=2){
					 num = $(this).text().substr(0,1);//手数
				}else{
					num = $(this).text().substr(0,2);//手数
				}
				
				var arrayCFZY= mui.cacheData.getzy(num+SuperCommodityNo+'zy').split(',');//触发止盈数组
				$('#_stopWin010_ .chioce-button').remove();
				$.each(arrayCFZY,function(index,value){
					$('#_stopWin010_').append('<button class="chioce-button">'+value+'</button>');
				});
				
				var arrayCFZS= mui.cacheData.getzs(num+SuperCommodityNo+'zs').split(',');//触发止损数组
				$('#_stopLoss010_ .chioce-button').remove();
				$.each(arrayCFZS,function(index,value){
					$('#_stopLoss010_').append('<button class="chioce-button">'+value+'</button>');
				});
				
				$("#_stopWin010_ .chioce-button").on('tap',function(){
				
					$(this).addClass('on'); // 设置被点击元素为黄色
					$(this).siblings(".chioce-button").removeClass('on'); // 去除所有同胞元素的黄色样式
				});
				
				$("#_stopLoss010_ .chioce-button").on('tap',function(){
					
					$(this).addClass('on'); // 设置被点击元素为黄色
					$(this).siblings(".chioce-button").removeClass('on'); // 去除所有同胞元素的黄色样式
					
					//履约保证金
					var hdbzj = mui.cacheData.getSlipBond(num+SuperCommodityNo+'hdbzj');//滑点保证金
					var zsj= $(this).text();//止损价
					var lybzj = hdbzj*num + Number(zsj);//滑点保证金*手数+止损价=履约保证金
					$('#_performance_margin_').text(lybzj+'元');
					var poundage= mui.cacheData.getzs(num+SuperCommodityNo+'sxf');
					$('#payment_amount').text(poundage*num+lybzj);
					
					if(Number($('#difference').text())-Number($('#payment_amount').text())<0){
						$('#differenceNotFull').text('余额不足');
					}
				});
				
				//综合手续费
				var poundage= mui.cacheData.getzs(num+SuperCommodityNo+'sxf');
				$('#_poundage_').text('('+currencyNo+(toFixedFloatNumber(poundage*num/CNYExchangeRate,4))+')'+' '+poundage*num+'元');
				
				//履约保证金
				var _hdbzj_ = mui.cacheData.getSlipBond(num+SuperCommodityNo+'hdbzj');//滑点保证金
				var _zsj_= 0;//止损价
				var _lybzj_ = _hdbzj_*num;//滑点保证金*手数+止损价=履约保证金
				$('#_performance_margin_').text(_lybzj_+'元');
				
				$('#payment_amount').text(poundage*num+_lybzj_);
				
				if(Number($('#difference').text())-Number($('#payment_amount').text())<0){
					$('#differenceNotFull').text('余额不足');
				}
				
			});
			
			
		}
		
	);
	
});


mui.app_request('/user/getbalancerate', {
				"businessType": 4
		}, function(result) {
			if(result.success == true) {
					
				$('#difference').text(result.data.balance);
			}
	}, function(res) {});
	



$('#placeOrderAskPrice1').on('tap',function(){
	
	$('#flashMore').text('看空价:');
	$('#flashMorePrice').text($('#askPrice1Button').text());
	$('#orderListButton').text('看空买入');
});

/**
 * 点击快闪按钮
 * 
 */
/* obj
 * {
    "id": null,
    "paramId": null,
    "contractNo": null,
    "contractName": "国际原油",
    "timeBucket": "4:00",
    "icon": null,
    "described": null,
    "tradeRule": null,
    "lever": 1,
    "stopProfit": "1000,2000,3000,5000,10000",
    "stopLoss": "-1000,-2000,-3000,-5000,-10000",
    "tradeFee": 40,
    "slipBond": 2000,
    "updateUserId": null,
    "updateUser": null,
    "updateTime": null,
    "updateUserOrg": null,
    "mainId": null
}
 */

var superPoundage;
var super_lybzj_;
$('#flashButton').on('tap',function(){
	
	mui.app_request(
		'game/order/contractParam',
		{
			'contractNo':SuperCommodityNo
			
		},function(result){
			$('#TradeNum .chioce-button').remove();
			$('#exchange_rate_string').text('');
			$('#exchange_rate').text('');
			$('#poundage').text('');
			$('#performance_margin').text('');
			//汇率设置
			var currencyNo = CacheQuoteBase.getCacheContractAttribute(SuperCommodityNo, "CurrencyNo");//币种  USD
			var CNYExchangeRate;//汇率
			$.each(currencyArray, function(index,value) {
				if(currencyArray[index].CurrencyNo ==currencyNo){
				$('#exchange_rate_string').text('汇率 - '+currencyArray[index].CurrencyName+'人民币');
				$('#exchange_rate').text('1'+currencyArray[index].CurrencyName+'='+currencyArray[index].CNYExchangeRate+'元人民币');
				CNYExchangeRate = currencyArray[index].CNYExchangeRate;
					}
				});
			$.each(result.data,function(index, obj){
				tplFillData("TradeNum", "tpTradeNum", obj, FillType.after);
//				tplFillData("_TradeNum_", "tp_TradeNum_", obj, FillType.after);
				
				
				mui.cacheData.removezy(obj.lever+SuperCommodityNo);
				mui.cacheData.savezy(obj.lever+SuperCommodityNo,obj.stopProfit);
				
				mui.cacheData.removezs(obj.lever+SuperCommodityNo);
				mui.cacheData.savezs(obj.lever+SuperCommodityNo,obj.stopLoss);
				
				mui.cacheData.removesxf(obj.lever+SuperCommodityNo);
				mui.cacheData.savesxf(obj.lever+SuperCommodityNo,obj.tradeFee);//手续费
				
				mui.cacheData.removeSlipBond(obj.lever+SuperCommodityNo);
				mui.cacheData.saveSlipBond(obj.lever+SuperCommodityNo,obj.slipBond);//滑点保证金
				
			});
			
			
			
			$("#TradeNum button:first-child").addClass('on');
			var num0=$("#TradeNum button:first-child").text().substr(0,1);
			
			//初始化 手续费
			var initsxf = mui.cacheData.getsxf(num0+SuperCommodityNo+'sxf');
			$('#poundage').text('('+currencyNo+(toFixedFloatNumber(initsxf*num0/CNYExchangeRate,4))+')'+' '+initsxf*num0+'元');
			superPoundage = initsxf*num0;
			
			
			var arrayCFZY0= mui.cacheData.getzy(num0+SuperCommodityNo+'zy').split(',');//触发止盈数组
			$('#stopWin010 .chioce-button').remove();
			$.each(arrayCFZY0,function(index,value){
					$('#stopWin010').append('<button class="chioce-button">'+value+'</button>');
			});
			$('#stopWin010 button:first-child').addClass('on');
			
			$("#stopWin010 .chioce-button").on('tap',function(){
				
				$(this).addClass('on'); // 设置被点击元素为黄色
				$(this).siblings(".chioce-button").removeClass('on'); // 去除所有同胞元素的黄色样式
			});
			
			var arrayCFZS0= mui.cacheData.getzy(num0+SuperCommodityNo+'zs').split(',');//触发止盈数组
			$('#stopLoss010 .chioce-button').remove();
			$.each(arrayCFZS0,function(index,value){
					$('#stopLoss010').append('<button class="chioce-button">'+value+'</button>');
			});
			$('#stopLoss010 button:first-child').addClass('on');
			
			//初始化 履约保证金
			var initHdbzj = mui.cacheData.getSlipBond(num0+SuperCommodityNo+'hdbzj');//滑点保证金
			var initzsj=$('#stopLoss010 button:first-child').text();//止损价
			var initlybzj = (initHdbzj*num0)+Number(initzsj);//滑点保证金*手数+止损价=履约保证金
			$('#performance_margin').text(initlybzj+'元');
			super_lybzj_ = initlybzj;
			
						
			
			$("#stopLoss010 .chioce-button").on('tap',function(){
					
				$(this).addClass('on'); // 设置被点击元素为黄色
				$(this).siblings(".chioce-button").removeClass('on'); // 去除所有同胞元素的黄色样式
				
				//履约保证金
				var zsj=$(this).text();//止损价
				var lybzj = (initHdbzj*num0)+Number(zsj);//滑点保证金*手数+止损价=履约保证金
				$('#performance_margin').text(lybzj+'元');
			});
			
			
			<!--------------------------------------->
			$("#TradeNum .chioce-button").on("tap", function() {
				$(this).addClass('on'); // 设置被点击元素为黄色
				$(this).siblings(".chioce-button").removeClass('on'); // 去除所有同胞元素的黄色样式
				
				var num;
				if($(this).text().length<=2){
					 num = $(this).text().substr(0,1);//手数
				}else{
					num = $(this).text().substr(0,2);//手数
				}
				var arrayCFZY= mui.cacheData.getzy(num+SuperCommodityNo+'zy').split(',');//触发止盈数组
				$('#stopWin010 .chioce-button').remove();
				$.each(arrayCFZY,function(index,value){
					$('#stopWin010').append('<button class="chioce-button">'+value+'</button>');
				});
				
				var arrayCFZS= mui.cacheData.getzs(num+SuperCommodityNo+'zs').split(',');//触发止损数组
				$('#stopLoss010 .chioce-button').remove();
				$.each(arrayCFZS,function(index,value){
					$('#stopLoss010').append('<button class="chioce-button">'+value+'</button>');
				});
				
				$("#stopWin010 .chioce-button").on('tap',function(){
				
					$(this).addClass('on'); // 设置被点击元素为黄色
					$(this).siblings(".chioce-button").removeClass('on'); // 去除所有同胞元素的黄色样式
				});
			
				$("#stopLoss010 .chioce-button").on('tap',function(){
					
					$(this).addClass('on'); // 设置被点击元素为黄色
					$(this).siblings(".chioce-button").removeClass('on'); // 去除所有同胞元素的黄色样式
					
					//履约保证金
					var hdbzj = mui.cacheData.getSlipBond(num+SuperCommodityNo+'hdbzj');//滑点保证金
					var zsj= $(this).text();//止损价
					var lybzj = hdbzj*num + Number(zsj);//滑点保证金*手数+止损价=履约保证金
					$('#performance_margin').text(lybzj+'元');
					
					var poundage= mui.cacheData.getzs(num+SuperCommodityNo+'sxf');
					superPoundage = poundage*num;
					super_lybzj_ = lybzj;
				});
				
				
				//手续费
				var poundage= mui.cacheData.getzs(num+SuperCommodityNo+'sxf');
				$('#poundage').text('('+currencyNo+(toFixedFloatNumber(poundage*num/CNYExchangeRate,4))+')'+' '+poundage*num+'元');
				
				//履约保证金
				var _hdbzj_ = mui.cacheData.getSlipBond(num+SuperCommodityNo+'hdbzj');//滑点保证金
				var _zsj_= 0;//止损价
				var _lybzj_ = _hdbzj_*num;//滑点保证金*手数+止损价=履约保证金
				$('#performance_margin').text(_lybzj_+'元');
				
			});
			<!---------------------------------------------------------------------------->
			$('#openFlash').on('tap',function(){
					var tradeNum=$('#TradeNum .on').text();
					var stopWin= $('#stopWin010 .on').text();
					var stopLoss= $('#stopLoss010 .on').text();
					var poundage = $('#poundage').text().substr($('#poundage').text().indexOf(')')+2, $('#poundage').text().indexOf('元'));
					poundage = poundage.substr(0,poundage.length-1);
					var lybzj =
					$('#performance_margin').text().substr(0,$('#performance_margin').text().length-1);//履约保证金
					var commodityNo = SuperCommodityNo;
					
					
					
					mui.cacheData.removeFlash(phone+commodityNo+'tradeNum');
					mui.cacheData.removeFlash(phone+commodityNo+'stopWin');
					mui.cacheData.removeFlash(phone+commodityNo+'stopLoss');
					mui.cacheData.removeFlash(phone+commodityNo+'lybzj');
					mui.cacheData.removeFlash(phone+commodityNo+'zfzje');
					
					//phone 变量在common.js最后一行
					mui.cacheData.saveFlash(phone+commodityNo+'tradeNum',tradeNum);//交易数量
					mui.cacheData.saveFlash(phone+commodityNo+'stopWin',stopWin);//止盈
					mui.cacheData.saveFlash(phone+commodityNo+'stopLoss',stopLoss);//止损
					mui.cacheData.saveFlash(phone+commodityNo+'lybzj',lybzj);//履约保证金
					mui.cacheData.saveFlash(phone+commodityNo+'zfzje',Number(poundage)+Number(lybzj));//支付总金额（不含下单费用）
					
			});
			
		}
	);
	
});


$('#orderListButton').on('tap',function(){
	
	Trade.doOpenOrderGW(TradeConfig.username,phone,'00001','CL','1707',2,0,80.25,2.36,5,2);
});

	
	
$("#placeOrder .chioce-button").on("tap", function() {
		$(this).addClass('on'); // 设置被点击元素为黄色
		$(this).siblings(".chioce-button").removeClass('on'); // 去除所有同胞元素的黄色样式
});

function dealContractNo(contractNo){
	
	return  contractNo.substr(0,contractNo.length-4);
}

