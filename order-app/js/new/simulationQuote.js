//document.getElementById("closeStopTable").addEventListener("tap", function() {
//	mui("#popoverConditoion").popover("toggle");
//});
$("#topPopover li").on("tap", function() {
	$(".mui-backdrop").remove();
	mui("#topPopover").popover("toggle");
});
//$("#modifiyConditioListPopover").on("tap", function() {
//	mui("#popoverConditoion").popover("toggle");
//});
$(".close_propove_Loss").on("tap", function() {
	mui("#popoverLoss").popover("toggle");
});
$(".close_propove_conditoion").on("tap", function() {
	mui("#popoverConditoion").popover("toggle");
});
//$("#stopLoss").on("tap", function() {
//	
//});
//$("#modifiystopOrderPopover").on("tap", function() {
//	mui("#popoverLoss").popover("toggle");
//});
$("#conditionButton").on("tap",function(){
	mui("#popoverConditoion").popover("toggle");
});
$("#add,#clsoeAdd").on("tap",function(){
	mui("#popover").popover("toggle");
});
vs.online();
//绑定切换按钮的事件
var height = $(window).height();
$("#checkPriceButton").on("tap", function() {
	var val = $(this).val();
	if(val == 1) {
		$("#checkPriceButton").css({ "background": "url(../../img/checked.png) center center no-repeat", "background-size": "25px 40px" });
		$(this).val(0);
		$("#marketPrice").val($("#xin0").text()).removeAttr("readonly");
		$('#sumbuyPrice').text($("#xin0").text());
		$('#sumsellPrice').text($("#xin0").text());
	} else {
		$("#checkPriceButton").css({ "background": "url(../../img/check.png) center center no-repeat", "background-size": "25px 40px" });
		$(this).val(1);
		$("#marketPrice").val("市价").attr("readonly","readonly");
		$('#sumbuyPrice').text("市价");
		$('#sumsellPrice').text("市价");
	}
});
$("#marketPrice").on("input",function(){
	var value=$(this).val();
	$('#sumbuyPrice').text(value);
	$('#sumsellPrice').text(value);
});
$("#tradeDivButton,#HandicapDivButton,#timeChartButton,#lightChartButton").on("tap", function() {
	$(".bottom_buy_div,.candlestick_data").addClass("display_none");
	$("#candlestickNav").addClass("display_none");
});
$("#timeChartButton,#lightChartButton,#chiocecandlestickButton").on("tap", function() {
	$(".bottom_buy_div,.candlestick_data").removeClass("display_none");
});
$("#chiocecandlestickButton ").on("tap", function() {
	$("#candlestickNav").removeClass("display_none");
});
$("#candlestickNav").on("tap", "a", function() {
	$("#candlestickNav").addClass("display_none");
});
/*年月日-日历*/
(function($) {
	$.init();
	document.getElementById("timechioce").addEventListener('tap', function() {
		var optionsJson = this.getAttribute('data-options') || '{}';
		var options = JSON.parse(optionsJson);
		var id = this.getAttribute('id');
		var picker = new $.DtPicker(options);
		picker.show(function(rs) {
			var calendar = rs.y.text + '-' + rs.m.text + '-' + rs.d.text;
			document.getElementById("finance").value = calendar;
			picker.dispose();
		});
	}, false);
	document.getElementById("timechioce1").addEventListener('tap', function() {
		var optionsJson = this.getAttribute('data-options') || '{}';
		var options = JSON.parse(optionsJson);
		var id = this.getAttribute('id');
		var picker = new $.DtPicker(options);
		picker.show(function(rs) {
			var calendar = rs.y.text + '-' + rs.m.text + '-' + rs.d.text;
			document.getElementById("finance1").value = calendar;
			picker.dispose();
		});
	}, false);
})(mui);
$("#positionListOrder table,#guandanListOrder table,#notConditioListTable table").on("tap", "tr", function() {
	$(this).addClass('clickBg'); // 设置被点击元素为黑色
	$(this).siblings('tr').removeClass('clickBg'); // 去除所有同胞元素的黑色样式
});
$("#notstopOrderListTable table").on("tap", "tr", function() {
	$(this).addClass('clickBg'); // 设置被点击元素为黑色
	$(this).siblings('tr').removeClass('clickBg'); // 去除所有同胞元素的黑色样式
	//0:运行中   1:暂停
	var Status= $(this).children().eq(17).text();
	if(Status==0){
		$('#zhanting').text('暂停');
	}
	if(Status==1){
		$('#zhanting').text('启动');
	}
	
});

$('#notConditioListTable table').on('tap','tr',function(){
	$(this).addClass('clickBg'); // 设置被点击元素为黑色
	$(this).siblings('tr').removeClass('clickBg'); // 去除所有同胞元素的黑色样式
	//0:运行中   1:暂停
	var Status= $(this).children().eq(22).text();
	if(Status==0){
		$('#suspendConditionList').text('暂停');
	}
	if(Status==1){
		$('#suspendConditionList').text('启动');
	}
});

var height=window.innerHeight;
$("#lightChart").css("height",height-370+"px");
$("#timeChart,#candlestickChartDiv").css("height",height-480+"px");
$("#positionListOrder,#guandanListOrder,#dealtListOrder,#entrustListOrder").css("height",height-378+"px");
$("#notstopOrderListTable,#alreadystopOrderTable").css("height",height-66+"px");
$("#notstopOrderListTable,#alreadystopOrderTable").css("height",height-66+"px");
$("#alreadyConditioListTable,#notConditioListTable").css("height",height-66+"px");

$("#notstopOrderListTable .button_list").css("top",height-155+"px");
$("#notConditioListTable .button_list").css("top",height-155+"px");
//$("#positionListOrder .button_list").css({"top":height-45+"px","z-index":"999"});
//$("#volumeChart,#candlestickVolumeChart").css("height",height-370+"px"+"px");





$('#allOpen').on('click',function(){
	
		if($('#holdList').children().length>0){
			layer.confirm(
					"确认全部平仓?", 
					{
						icon: 3,
						title: '确认全部平仓？'
					},
					function(index) { // 确认
						
						allPingCang();
						layer.close(index);
					},
					function(index) { // 取消
						layer.close(index);
					}
				);
		}else{
			
			layer.msg('持仓列表为空!');
		}
function allPingCang(){			
		$('#holdList').children().each(function(){
			var _this = $(this);
			//合约
		  	var contract =  _this.children().eq(1).text();
		  	// 多/空
		  	var moreOrnull =  _this.children().eq(2).text();
		  	//0:买	1:卖
		  	var drection;
		  	if(moreOrnull=='多'){
		  		drection=1;
		  	}else{
		  		drection=0;
		  	}
			//	 手数 		
		  	var orderNum =  _this.children().eq(3).text();
		  	//开仓均价
		  	var openAvgPrice = _this.children().eq(4).text();
		  	//1：市价
		  	var priceType = 1;
		
		  	
			Trade.doInsertOrder(contract,orderNum, drection,priceType, "", 0, buildOrderRef());
		});
	}
});


$('#Open').on('click',function(){
	var hasClass = false;
	if($('#holdList').children().length>0){
		$('#holdList').children().each(function(){
			var _this = $(this);
			if(_this.hasClass('clickBg')){
				hasClass = true;
			}
			
		});
		if(hasClass){
			open();
		}else{
			layer.msg('请选择一条记录!');
			return;
		}
		
		
	}else{
		layer.msg('持仓列表为空!');
	}
	
	
	function open(){
		$('#holdList').children().each(function(){
		  var _this = $(this);
		  if(_this.hasClass('clickBg')){
		  		//合约
		  		var contract =  _this.children().eq(1).text();
		  		// 多/空
		  		var moreOrnull =  _this.children().eq(2).text();
		  		//0:买	1:卖
		  		var drection;
		  		if(moreOrnull=='多'){
		  			drection=1;
		  		}else{
		  			drection=0;
		  		}
				//	 手数 		
		  		var orderNum =  _this.children().eq(3).text();
		  		//开仓均价
		  		var openAvgPrice = _this.children().eq(4).text();
		  		//1：市价
		  		var priceType = 1;
		  		var LimitPrice = 0.0;
		  		var TriggerPrice = 0.0;
		  		layer.confirm(
					"确认平仓:合约【" + contract + "】,价格:【" + (priceType == 1 ? "市价" : "") + "】,手数:【" + orderNum+ "】,买卖方向:【" + getDrectionName(drection) + "】", 
					{
						icon: 3,
						title: '确认平仓？'
					},
					function(index) { // 确认
						
						Trade.doInsertOrder(contract,orderNum, drection,priceType, LimitPrice, TriggerPrice, buildOrderRef());
						layer.close(index);
					},
					function(index) { // 取消
						layer.close(index);
					}
				);
		  }
		});
	
	}
});

var StopCommodityNo;
var zhiyinExchangeNo;
var zhiyinContractNo;
var zhiyinStopLossType;
var zhiyinOpenAvgPrice;
var zhiyinDrection;
var zhiyinOrderType;
$('#stopLoss').on('tap',function(){
	
	if($('#holdList').children().length>0){
		var isClass0 = false;
		$('#holdList').children().each(function(){
			var _this = $(this);
			if(_this.hasClass('clickBg')){
				isClass0 = true;
			}
		});
		if(isClass0){
			mui("#popoverLoss").popover("toggle");
			$('#stopAdd00').text('添加');
			$('#zhiyinAdd').text('添加');
			selectHoldList();
		}else{
			layer.msg('请选择一条记录!');
		}
		
	}else{
		
		layer.msg('持仓列表为空!');
	}
	
function selectHoldList(){
	$('#holdList').children().each(function(){
	  var _this = $(this);
	  if(_this.hasClass('clickBg')){
	  	
	  		//CL
	  		StopCommodityNo =  _this.children().eq(0).val();
	  		//合约 CL1706
	  		var contract =  _this.children().eq(1).text();
	  		// 多/空
	  		var moreOrnull =  _this.children().eq(2).text();
	  		//0:买	1:卖
	  		if(moreOrnull=='多'){
	  			zhiyinDrection=0;
	  		}else{
	  			zhiyinDrection=1;
	  		}
			//	 手数 		
	  		var orderNum =  _this.children().eq(3).text();
	  		//开仓均价
	  		zhiyinOpenAvgPrice = _this.children().eq(4).text();
	  		
	  		zhiyinContractNo = _this.children().eq(7).text();
	  		zhiyinExchangeNo = _this.children().eq(8).text();
	  		//1：市价
	  		var priceType = 1;
	  		var name =CacheQuoteBase.getCacheContractAttribute(StopCommodityNo, "CommodityName")
	  		
	  		$('#stopContract').text(name);
	  		$('#stopMoreOrNull').text(moreOrnull);
	  		//从缓存中取得最新价
	  		$('#stopLast').text(fixedPriceByContract(CacheQuoteSubscribe.getCacheContractQuote(contract, 
	  			"LastQuotation", "LastPrice"),StopCommodityNo));
	  			
	  		if($('#stopSecOption').val()==1){	
		  		$('#stopPrice').val(fixedPriceByContract(CacheQuoteSubscribe.getCacheContractQuote(contract, 
		  			"LastQuotation", "LastPrice"),StopCommodityNo));
		  		var price = $('#stopPrice').val();
		  		var num = 100*(price-zhiyinOpenAvgPrice)/zhiyinOpenAvgPrice;
				$('#stopPercent').text(toFixedFloatNumber(Math.abs(num),2));	
	  		}else{
	  			$('#stopPrice').val(CacheQuoteBase.getCacheContractAttribute(StopCommodityNo, "MiniTikeSize"));
	  		}
	  			
	  		
	  		if($('#stopSecOption').children('option:selected').text()=='止损价'){
	  			$('#stopPrice').keyup(function(){
		  			var price = $('#stopPrice').val();
		  			var num = 100*(price-zhiyinOpenAvgPrice)/zhiyinOpenAvgPrice;
					$('#stopPercent').text(toFixedFloatNumber(Math.abs(num),2));
		  		});
		  		//0-限价触发止损
		  		zhiyinStopLossType=0;
	  		}
	  		
	  		$('#stopSecOption').change(function(){
		  			var p1 =$(this).children('option:selected').text();
		  			if(p1=='止损价'){
		  				$('#stopPrice').val(fixedPriceByContract(CacheQuoteSubscribe.getCacheContractQuote(contract, 
	  					"LastQuotation", "LastPrice"),StopCommodityNo));
		  				var price = $('#stopPrice').val();
				  		var num = 100*(price-zhiyinOpenAvgPrice)/zhiyinOpenAvgPrice;
				  		$('#stopPercent').text(toFixedFloatNumber(Math.abs(num),2));
		  				$('#stopPrice').keyup(function(){
				  			var price = $('#stopPrice').val();
				  			var num = 100*(price-zhiyinOpenAvgPrice)/zhiyinOpenAvgPrice;
				  			$('#stopPercent').text(toFixedFloatNumber(Math.abs(num),2));
				  		});
				  		//0-限价触发止损
		  				zhiyinStopLossType=0;
		  			}else{
		  				$('#stopPrice').val(CacheQuoteBase.getCacheContractAttribute(StopCommodityNo, "MiniTikeSize"));
		  				var price = $('#stopPrice').val();
				  		var num = 100*price/zhiyinOpenAvgPrice;
				  		$('#stopPercent').text(toFixedFloatNumber(Math.abs(num),2));
		  				$('#stopPrice').keyup(function(){
				  			var price = $('#stopPrice').val();
				  			var num = 100*price/zhiyinOpenAvgPrice;
				  			$('#stopPercent').text(toFixedFloatNumber(Math.abs(num),2));
				  		});
				  		//2-浮动止损
				  		zhiyinStopLossType=2;
		  			}
		  	});
		  	zhiyinOrderType = $('#zhiyinOrderType').val();
		  	$('#zhiyinOrderType').change(function(){
		  		var p2 =$(this).children('option:selected').val();
		  		if(p2==1){
		  			zhiyinOrderType=1;
		  		}
		  		
		  		if(p2==2){
		  			zhiyinOrderType=2;
		  		}
		  	});
		  	
		  	//---------------设置止盈--------------------------
		  	$('#zhiyin00heyue').text(name);
		  	$('#zhiyin00moreOrNull').text(moreOrnull);
		  	
		  	//最新价
		  	$('#zhiyin00Last').text(fixedPriceByContract(CacheQuoteSubscribe.getCacheContractQuote(contract, "LastQuotation", "LastPrice"),StopCommodityNo));
		  	//止盈价
		  	$('#zhiyinjia').val(zhiyinOpenAvgPrice);
		  	$('#zhiyinjia').keyup(function(){
		  		
		  		var num1 = 100*($('#zhiyinjia').val()-zhiyinOpenAvgPrice)/zhiyinOpenAvgPrice;
		  		$('#zhiyinPercent00').text(toFixedFloatNumber(Math.abs(num1),2));
		  	});
		  	
	  		//mui("#popoverLoss").popover("toggle");
	  }
	});
	
}
	
	
	
});

/**
 * 提交止盈单
 */
$('#zhiyinAdd').on('tap',function(){
	$('#holdList').children().each(function(){
		var _this = $(this);
	  	if(_this.hasClass('clickBg')){
	  		var orderSysId = "";
			var ExchangeNo	=_this.children().eq(8).text();
			//CL
			var CommodityNo = _this.children().eq(0).val();
			//1705
			var ContractNo = _this.children().eq(7).text();
			//手数
			var Num = $('#zhiyinshoushu').val();
			//	1-限价触发止盈
			var StopLossType = 1;
			var StopLossPrice = $('#zhiyinjia').val();
			var StopLossDiff = 0;
			var HoldAvgPrice = _this.children().eq(4).text();
			//0:买	1:卖
			var HoldDirection = _this.children().eq(9).text();
			var OrderType=$('#zhiyinweituojia').val();
			var miniTikeSize=CacheQuoteBase.getCacheContractAttribute(CommodityNo, "MiniTikeSize");
			if($('#zhiyinAdd').text()=='添加'){
				if((StopLossPrice*10000)%(miniTikeSize*10000)==0 ){
					
					mui.confirm("确认止盈:合约【" + CommodityNo+ContractNo + "】,止盈价格:【"+StopLossPrice+"】,手数:【"+Num+"】,止盈委托价:【"+(OrderType==1?"市价":"对手价")+"】",function(e){
						if(e.index==1){
							Trade.doInsertStopLoss(ExchangeNo,CommodityNo,ContractNo,
								Num,StopLossType,StopLossDiff,HoldAvgPrice,
								HoldDirection,OrderType,StopLossPrice);
						}
					});
					
					
				}else{
					layer.msg('动态价不符合最小变动价，请重新设置！');
					mui("#popoverLoss").popover("toggle");
				}
			}
	  	}
	});
	
});

/**
 * 提交止损单
 */
$('#stopAdd00').on('tap',function(){
	
		var orderSysId = "";
		//FDAX
		var commodityNo = StopCommodityNo;
		//XEurex
		var exchangeNo	=zhiyinExchangeNo;
		//1706
		var contractNo = zhiyinContractNo;
		var num = $('#zhiyinNum').val();
		/**
		 * 止损类型：
			0-限价触发止损
			1-限价触发止盈
			2-浮动止损
	
		 */
		var StopLossType = zhiyinStopLossType;
		var StopLossPrice;
		var StopLossDiff;
		if(StopLossType==0){
			StopLossPrice = $('#stopPrice').val();
		}
		
		if(StopLossType==2){
			StopLossDiff = $('#stopPrice').val();
		}
		var holdAvgPrice = zhiyinOpenAvgPrice;
		//0:买	1:卖
		var holdDrection = zhiyinDrection;
		var orderType = zhiyinOrderType;
		var lastprice = $('#stopLast').text();
		
		var miniTikeSize=CacheQuoteBase.getCacheContractAttribute(commodityNo, "MiniTikeSize");
		//当方式为动态价时，StopLossPrice=0
		if(typeof(StopLossPrice)=='undefined'){
				StopLossPrice = 0;
				if($('#stopAdd00').text()=='添加'){
					if((StopLossDiff*10000)%(miniTikeSize*10000)==0 && $('#stopPrice').val()!=0){
						
						mui.confirm("确认止损:合约【" + commodityNo+contractNo + "】,方式【动态价】,价格:【"+StopLossDiff+"】,手数:【"+num+"】,止损委托价:【"+(orderType==1?"市价":"对手价")+"】",function(e){
						if(e.index==1){
							//确认
							Trade.doInsertStopLoss(exchangeNo,commodityNo,contractNo,
								num,StopLossType,StopLossDiff,holdAvgPrice,holdDrection,orderType,StopLossPrice);
						}
					
						});
					}else{
						layer.msg('动态价不符合最小变动价，请重新设置！');
						mui("#popoverLoss").popover("toggle");
					}
					
				}
			}
		//当方式为止损价时，StopLossDiff=0
		if(typeof(StopLossDiff)=='undefined'){
		
			StopLossDiff = 0;
			if($('#stopAdd00').text()=='添加'){
				if((StopLossPrice*10000)%(miniTikeSize*10000)==0 && $('#stopPrice').val()!=0){
					mui.confirm("确认止损:合约【" + commodityNo+contractNo + "】,方式【止损价】,价格:【"+StopLossPrice+"】,手数:【"+num+"】,止损委托价:【"+(orderType==1?"市价":"对手价")+"】",function(e){
					if(e.index==1){
						//确认
						Trade.doInsertStopLoss(exchangeNo,commodityNo,contractNo,
							num,StopLossType,StopLossDiff,holdAvgPrice,holdDrection,orderType,StopLossPrice);
					}
				
					});
				}else{
					layer.msg('止损价不符合最小变动价，请重新设置！');
					mui("#popoverLoss").popover("toggle");
				}
			}
		}
	
/**
	 * 增加止损单
	 * @param {Object} exchangeNo
	 * @param {Object} commodityNo
	 * @param {Object} contractNo
	 * @param {Object} num
	 * @param {Object} stopLossType
	 * @param {Object} stopLossDiff
	 * @param {Object} holdAvgPrice
	 * @param {Object} holdDrection
	 * @param {Object} orderType
	 * @param {Object} StopLossPrice
	
	doInsertStopLoss: function(exchangeNo, commodityNo, contractNo, num, stopLossType, 
		stopLossDiff, holdAvgPrice, holdDrection, orderType, stopLossPrice)
	 */
});



$('#simBuy').on('tap',function(){
	
	//手数
	var orderNum =$('#orderNum0').val();
	//合约
	var contract = commodityNo+contractNo;
	//1：市价
	var priceType = 1;
	//0:买	1:卖
	var drection=0;
	
	layer.confirm(
				"确认提交订单:合约【" + contract + "】,价格:【" + (priceType == 1 ? "市价" : "") + "】,手数:【" + orderNum+ "】,买卖方向:【" + getDrectionName(drection) + "】", 
				{
					icon: 3,
					title: '确认下单？'
				},
				function(index) { // 确认
					
					Trade.doInsertOrder(contract,orderNum, drection,priceType, "", 0, buildOrderRef());
					layer.close(index);
				},
				function(index) { // 取消
					layer.close(index);
				}
			);
	
});

$('#simSell').on('tap',function(){
	
	//手数
	var orderNum =$('#orderNum0').val();
	//合约
	var contract = commodityNo+contractNo;
	//1：市价
	var priceType = 1;
	//0:买	1:卖
	var drection=1;
	
	layer.confirm(
				"确认提交订单:合约【" + contract + "】,价格:【" + (priceType == 1 ? "市价" : "") + "】,手数:【" + orderNum+ "】,买卖方向:【" + getDrectionName(drection) + "】", 
				{
					icon: 3,
					title: '确认下单？'
				},
				function(index) { // 确认
					
					Trade.doInsertOrder(contract,orderNum, drection,priceType, "", 0, buildOrderRef());
					layer.close(index);
				},
				function(index) { // 取消
					layer.close(index);
				}
			);
});

/**
 * 挂单	撤单
 */
/**
	 * 撤单请求 doCancelOrder
	 * @param {Object} orderSysId 系统编号
	 * @param {Object} orderId 订单号
	 * @param {Object} exchangeNo 交易所代码
	 * @param {Object} commodityNo 品种代码
	 * @param {Object} contractNo 合约代码
	 * @param {Object} orderNum 订单数量
	 * @param {Object} drection 买卖方向（0：买，1：卖）
	 * @param {Object} orderPrice 订单价格
	 */
$('#kilAnorder').on('tap',function(){
		var hasClass = false;
	  	if($('#applyOrderList').children().length>0){
	  		
	  		$('#applyOrderList').children().each(function(){
	  			var _this = $(this);
	  			if(_this.hasClass('clickBg')){
	  				hasClass = true;
	  			}
	  		});
	  		if(hasClass){
	  			chedan();
	  		}else{
	  			layer.msg('请选择一条记录!');
	  			return;
	  		}
	  	}else{
	  		
	  		layer.msg('挂单列表为空!');
	  	}
	  	
	});
	function chedan(){
		$('#applyOrderList').children().each(function(){
			
			var _this = $(this);
	  	if(_this.hasClass('clickBg')){
	  		//合约
	  		var contract = _this.children().eq(0).text();
	  		// 多/空
	  		var buyOrSell =  _this.children().eq(1).text();
	  		//0:买	1:卖
	  		var drection;
	  		if(buyOrSell=='买'){
	  			drection=0;
	  		}else{
	  			drection=1;
	  		}
	  		//委托价
	  		var orderPrice = _this.children().eq(2).text();
	  		//委托数量
	  		var orderNum = _this.children().eq(3).text();
	  		//挂单量 = 委托量-已成交
	  		var applyOrderNum = _this.children().eq(4).text();
	  		//订单号
	  		var orderId = _this.children().eq(6).text();
	  		//交易所代码
	  		var exchangeNo = _this.children().eq(7).text();
	  		//品种代码
	  		var commodityNo = _this.children().eq(8).text();
	  		//合约代码
	  		var contractNo = _this.children().eq(9).text();
	  		var orderSysId = "";
			layer.confirm(
				"确认撤单合约【" + contract + "】,手数:【" + orderNum+ "】", 
				{
					icon: 3,
					title: '确认撤单？'
				},
				function(index) { // 确认
					
					Trade.doCancelOrder(orderSysId, orderId, exchangeNo, commodityNo, contractNo, orderNum, drection, orderPrice);
					layer.close(index);
				},
				function(index) { // 取消
					
					layer.close(index);
				}
			);
			}
		});
	}	
	

/**
 * 挂单	全撤
 */
$('#fullWithdrawal').on('click',function(){
	
	
	if($('#applyOrderList').children().length>0){
		layer.confirm(
					"此操作将撤销挂单中所有合约，请您慎重选择，确认将所有合约全部撤销?", 
					{
						icon: 3,
						title: '确认全部撤单？'
					},
					function(index) { // 确认
						
						allquanche();
						layer.close(index);
					},
					function(index) { // 取消
						layer.close(index);
					}
				);
		
	}else{
		
		layer.msg('挂单列表为空!');
	}
	
	function allquanche(){
		$('#applyOrderList').children().each(function(){
			var _this = $(this);
			//合约
		  		var contract = _this.children().eq(0).text();
		  		// 多/空
		  		var buyOrSell =  _this.children().eq(1).text();
		  		//0:买	1:卖
		  		var drection;
		  		if(buyOrSell=='买'){
		  			drection=0;
		  		}else{
		  			drection=1;
		  		}
		  		//委托价
		  		var orderPrice = _this.children().eq(2).text();
		  		//委托数量
		  		var orderNum = _this.children().eq(3).text();
		  		//挂单量 = 委托量-已成交
		  		var applyOrderNum = _this.children().eq(4).text();
		  		//订单号
		  		var orderId = _this.children().eq(6).text();
		  		//交易所代码
		  		var exchangeNo = _this.children().eq(7).text();
		  		//品种代码
		  		var commodityNo = _this.children().eq(8).text();
		  		//合约代码
		  		var contractNo = _this.children().eq(9).text();
		  		var orderSysId = "";
		  		Trade.doCancelOrder(orderSysId, orderId, exchangeNo, commodityNo, contractNo, orderNum, drection, orderPrice);
		});
	}
	
	
	
});

$("#changeSingle").on('tap',function(){
	$('#col1').val('');
	$('#col2').val('');
	var hasClass=false;
	if($('#applyOrderList').children().length>0){
		
//		changgeee();
		$('#applyOrderList').children().each(function(){
			var _this = $(this);
			if(_this.hasClass('clickBg')){
				hasClass = true;
			}
		});
		if(hasClass){
			mui("#popover").popover("toggle");
			changgeee();
		}else{
			layer.msg('请选择一条记录!');
		}
		
		
	}else{
		layer.msg('挂单列表为空!');
		
	}
	
function changgeee(){	
	$('#applyOrderList').children().each(function(){
		var _this = $(this);
	  	if(_this.hasClass('clickBg')){
	  		//合约
	  		var contract = _this.children().eq(0).text();
	  		// 多/空
	  		var buyOrSell =  _this.children().eq(1).text();
	  		//0:买	1:卖
	  		if(buyOrSell=='买'){
	  			_drection=0;
	  		}else{
	  			_drection=1;
	  		}
	  		
	  		
	  		//委托价
	  		_orderPrice = _this.children().eq(2).text();
	  		orderPriceId = _this.children().eq(2).attr('id');
	  		//委托数量
	  		_orderNum = _this.children().eq(3).text();
	  		orderNumId = _this.children().eq(3).attr('id');
	  		
	  		//挂单量 = 委托量-已成交
	  		_applyOrderNum = _this.children().eq(4).text();
	  		//订单号
	  		_orderId = _this.children().eq(6).text();
	  		//交易所代码
	  		_exchangeNo = _this.children().eq(7).text();
	  		//品种代码
	  		_commodityNo = _this.children().eq(8).text();
	  		//合约代码
	  		_contractNo = _this.children().eq(9).text();
	  		_orderSysId = "";
	  		
	  		$('#col1').val(_orderPrice);
	  		$('#col2').val(_orderNum);
	  	}
		
	});
	
	}
	
});

var orderPriceId;
var orderNumId;

//委托价
var _orderPrice;
//委托数量
var _orderNum;
//挂单量
var _applyOrderNum;
//订单号
var _orderId;
//交易所代码
var _exchangeNo;
//品种代码
var _commodityNo;
//合约代码
var _contractNo;
//系统编号
var _orderSysId="";
var _drection;

$('#add').on('tap',function(){
	
		_orderPrice= $('#col1').val();
	  	_orderNum=$('#col2').val();
		
		Trade.doModifyOrder(_orderSysId,_orderId,_exchangeNo,_commodityNo,_contractNo,_orderNum,_drection,_orderPrice,0);
//doModifyOrder: function(orderSysId, orderId, exchangeNo, commodityNo,
//			contractNo, orderNum, drection, orderPrice, triggerPrice)
/**
	 * 改单请求
	 * @param {Object} orderSysId 系统编号
	 * @param {Object} orderId 订单号
	 * @param {Object} exchangeNo 交易所代码
	 * @param {Object} commodityNo 品种代码
	 * @param {Object} contractNo 合约代码
	 * @param {Object} orderNum 订单数量
	 * @param {Object} drection 买卖方向（0：买，1：卖）
	 * @param {Object} orderPrice 订单价格
	 * @param {Object} triggerPrice 触发价格
	 */
				
});


$('#sumbuyPrice').text($('#marketPrice').val());
$('#sumsellPrice').text($('#marketPrice').val());



var exchangeNo;
var commodityNo;
var contractNo;

$("#quote-list").on("tap", ".simulation_optional_list_href", function() {
	var index = $(this).index();
	commodityNo = $("#quote-list .simulation_optional_list_href").eq(index).attr("data").split("-")[0];
	contractNo = CacheQuoteBase.getCacheContractAttribute(commodityNo, "MainContract");
	exchangeNo=CacheQuoteBase.getCacheContractAttribute(commodityNo, "ExchangeNo");
	$("#totalVolume").val("");
	$("#commodityNo").val(commodityNo);
     $("#contractNo").val(contractNo);
     var id=$(this).attr("id");
     $('#candlestick_data_id div:eq(0)').attr('id',id+'-candlestick-price');
//	$('#candlestick_data_id div:eq(0)').text($("#quote-list ul").eq(index).find("li").eq(1).text());
	$('#candlestick_data_id div:eq(1) p:eq(0)').attr('id',id+'-candlestick-time');
	$('#candlestick_data_id div:eq(1) p:eq(1) span:eq(0)').attr('id',id+'-candlestick-zd');
	$('#candlestick_data_id div:eq(1) p:eq(1) span:eq(1)').attr('id',id+'-candlestick-zdf');
	$('#candlestick_data_id ul:eq(0) li:eq(1)').attr('id',id+'-candlestick-openPrice');
	$('#candlestick_data_id ul:eq(0) li:eq(3)').attr('id',id+'-candlestick-highPrice');
	$('#candlestick_data_id ul:eq(0) li:eq(5)').attr('id',id+'-candlestick-totalVolume');
	$('#candlestick_data_id ul:eq(0) li:eq(7)').attr('id',id+'-candlestick-lowPrice');
	
	//根据CommodityNo为最高，最低等值赋初值，即使没有行情的情况下，也不会错误的显示值
	//最新价  PreSettlePrice
	var LastPrice = CacheQuoteSubscribe.getCacheContractQuote(commodityNo+contractNo, "LastQuotation", "LastPrice");
	$('#candlestick_data_id div:eq(0)').text(fixedPriceByContract(LastPrice,commodityNo));
	if(LastPrice-PreSettlePrice>0){
		$('#candlestick_data_id div:eq(0)').css('color','red');
	}
	if(LastPrice-PreSettlePrice<0){
		$('#candlestick_data_id div:eq(0)').css('color','forestgreen');
	}
	
	//时间
	var DateTimeStamp = CacheQuoteSubscribe.getCacheContractQuote(commodityNo+contractNo, "LastQuotation", "DateTimeStamp");
	if(DateTimeStamp!=''){
		$('#candlestick_data_id div:eq(1) p:eq(0)').text(DateTimeStamp);
	}else{
		$('#candlestick_data_id div:eq(1) p:eq(0)').text('0000-00-00 00:00');
	}
	//涨跌
	var ChangeValue = CacheQuoteSubscribe.getCacheContractQuote(commodityNo+contractNo, "LastQuotation", "ChangeValue");
	$('#candlestick_data_id div:eq(1) p:eq(1) span:eq(0)').text(fixedPriceByContract(ChangeValue,commodityNo));
	if(fixedPriceByContract(ChangeValue,commodityNo)>0){
		$('#candlestick_data_id div:eq(1) p:eq(1) span:eq(0)').css('color','red');
		$('#candlestick_data_id div:eq(1) p:eq(1) span:eq(0)').text('+'+fixedPriceByContract(ChangeValue,commodityNo));
	}
	if(fixedPriceByContract(ChangeValue,commodityNo)<0){
		$('#candlestick_data_id div:eq(1) p:eq(1) span:eq(0)').css('color','forestgreen');
	}
	
	//涨跌幅
	var ChangeRate = CacheQuoteSubscribe.getCacheContractQuote(commodityNo+contractNo, "LastQuotation", "ChangeRate");
	$('#candlestick_data_id div:eq(1) p:eq(1) span:eq(1)').text(parseFloat(ChangeRate).toFixed(2) + "%");
	if(ChangeRate>0){
		$('#candlestick_data_id div:eq(1) p:eq(1) span:eq(1)').css('color','red');
		$('#candlestick_data_id div:eq(1) p:eq(1) span:eq(1)').text('+'+parseFloat(ChangeRate).toFixed(2) + "%");
	}
	if(ChangeRate<0){
		$('#candlestick_data_id div:eq(1) p:eq(1) span:eq(1)').css('color','forestgreen');
	}
	//开盘
	var OpenPrice = CacheQuoteSubscribe.getCacheContractQuote(commodityNo+contractNo, "LastQuotation", "OpenPrice");
	$('#candlestick_data_id ul:eq(0) li:eq(1)').text(fixedPriceByContract(OpenPrice,commodityNo));
	if(fixedPriceByContract(OpenPrice,commodityNo)-fixedPriceByContract(PreSettlePrice,commodityNo)>0){
		$('#candlestick_data_id ul:eq(0) li:eq(1)').css('color','red');
	}
	if(fixedPriceByContract(OpenPrice,commodityNo)-fixedPriceByContract(PreSettlePrice,commodityNo)<0){
		$('#candlestick_data_id ul:eq(0) li:eq(1)').css('color','forestgreen');
	}
	//最高
	var HighPrice = CacheQuoteSubscribe.getCacheContractQuote(commodityNo+contractNo, "LastQuotation", "HighPrice");
	$('#candlestick_data_id ul:eq(0) li:eq(3)').text(fixedPriceByContract(HighPrice,commodityNo));
	if(fixedPriceByContract(HighPrice,commodityNo)-fixedPriceByContract(PreSettlePrice,commodityNo)>0){
		$('#candlestick_data_id ul:eq(0) li:eq(3)').css('color','red');
	}
	if(fixedPriceByContract(HighPrice,commodityNo)-fixedPriceByContract(PreSettlePrice,commodityNo)<0){
		$('#candlestick_data_id ul:eq(0) li:eq(3)').css('color','forestgreen');
	}
	//成交量
	var TotalVolume = CacheQuoteSubscribe.getCacheContractQuote(commodityNo+contractNo, "LastQuotation", "TotalVolume");
	$('#candlestick_data_id ul:eq(0) li:eq(5)').text(TotalVolume);
	//最低
	var LowPrice = CacheQuoteSubscribe.getCacheContractQuote(commodityNo+contractNo, "LastQuotation", "LowPrice");
	$('#candlestick_data_id ul:eq(0) li:eq(7)').text(fixedPriceByContract(LowPrice,commodityNo));
	if(fixedPriceByContract(LowPrice,commodityNo)-fixedPriceByContract(PreSettlePrice,commodityNo)>0){
		$('#candlestick_data_id ul:eq(0) li:eq(7)').css('color','red');
	}
	if(fixedPriceByContract(LowPrice,commodityNo)-fixedPriceByContract(PreSettlePrice,commodityNo)<0){
		$('#candlestick_data_id ul:eq(0) li:eq(7)').css('color','forestgreen');
	}
	
	//滚动条处卖一 AskPrice1
	var AskPrice1 = CacheQuoteSubscribe.getCacheContractQuote(commodityNo+contractNo, "LastQuotation", "AskPrice1");
	$('#_sell00_').text(fixedPriceByContract(AskPrice1,commodityNo));
	if(fixedPriceByContract(AskPrice1,commodityNo)-fixedPriceByContract(PreSettlePrice,commodityNo)>0){
		$('#_sell00_').css('color','red');
	}
	if(fixedPriceByContract(AskPrice1,commodityNo)-fixedPriceByContract(PreSettlePrice,commodityNo)<0){
		$('#_sell00_').css('color','forestgreen');
	}
	//滚动条处卖一成交量 AskQty1
	var AskQty1 = CacheQuoteSubscribe.getCacheContractQuote(commodityNo+contractNo, "LastQuotation", "AskQty1");
	$('#_sell01_').text(AskQty1);
	//滚动条处买一 BidPrice1
	var BidPrice1 = CacheQuoteSubscribe.getCacheContractQuote(commodityNo+contractNo, "LastQuotation", "BidPrice1");
	$('#_buy00_').text(fixedPriceByContract(BidPrice1,commodityNo));
	if(fixedPriceByContract(BidPrice1,commodityNo)-fixedPriceByContract(PreSettlePrice,commodityNo)>0){
		$('#_buy00_').css('color','red');
	}
	if(fixedPriceByContract(BidPrice1,commodityNo)-fixedPriceByContract(PreSettlePrice,commodityNo)<0){
		$('#_buy00_').css('color','forestgreen');
	}
	//滚动条处买一成交量 BidQty1
	var BidQty1 = CacheQuoteSubscribe.getCacheContractQuote(commodityNo+contractNo, "LastQuotation", "BidQty1");
	$('#_buy01_').text(BidQty1);
	
	//盘口中的数据
	//盘口中的--最新
	$('#pklastparice').text(fixedPriceByContract(LastPrice,commodityNo));
	if(fixedPriceByContract(LastPrice,commodityNo)-fixedPriceByContract(PreSettlePrice,commodityNo)>0){
		$('#pklastparice').css('color','red');
	}
	if(fixedPriceByContract(LastPrice,commodityNo)-fixedPriceByContract(PreSettlePrice,commodityNo)<0){
		$('#pklastparice').css('color','forestgreen');
	}
	//盘口中的--卖五 AskPrice5
	var AskPrice5 = CacheQuoteSubscribe.getCacheContractQuote(commodityNo+contractNo, "LastQuotation", "AskPrice5");
	$('#sell_8').text(fixedPriceByContract(AskPrice5,commodityNo));
	if(fixedPriceByContract(AskPrice5,commodityNo)-fixedPriceByContract(PreSettlePrice,commodityNo)>0){
		$('#sell_8').css('color','red');
	}
	if(fixedPriceByContract(AskPrice5,commodityNo)-fixedPriceByContract(PreSettlePrice,commodityNo)<0){
		$('#sell_8').css('color','forestgreen');
	}
	//盘口中的--卖五 人数
	var AskQty5 = CacheQuoteSubscribe.getCacheContractQuote(commodityNo+contractNo, "LastQuotation", "AskQty5");
	$('#sell_9').text(AskQty5);
	//盘口中的--开盘
	$('#pkopenprice').text(fixedPriceByContract(OpenPrice,commodityNo));
	if(OpenPrice-PreSettlePrice>0){
		$('#pkopenprice').css('color','red');
	}
	if(OpenPrice-PreSettlePrice<0){
		$('#pkopenprice').css('color','forestgreen');
	}
	//盘口中的--卖四  AskPrice4
	var AskPrice4 = CacheQuoteSubscribe.getCacheContractQuote(commodityNo+contractNo, "LastQuotation", "AskPrice4");
	$('#sell_6').text(fixedPriceByContract(AskPrice4,commodityNo));
	if(AskPrice4-PreSettlePrice>0){
		$('#sell_6').css('color','red');
	}
	if(AskPrice4-PreSettlePrice<0){
		$('#sell_6').css('color','forestgreen');
	}
	//盘口中的--卖四 人数
	var AskQty4 = CacheQuoteSubscribe.getCacheContractQuote(commodityNo+contractNo, "LastQuotation", "AskQty4");
	$('#sell_7').text(AskQty4);
	//盘口中的--最高
	$('#pkhightprice').text(fixedPriceByContract(HighPrice,commodityNo));
	if(HighPrice-PreSettlePrice>0){
		$('#pkhightprice').css('color','red');
	}
	if(HighPrice-PreSettlePrice<0){
		$('#pkhightprice').css('color','forestgreen');
	}
	//盘口中的--卖三 AskPrice3
	var AskPrice3 = CacheQuoteSubscribe.getCacheContractQuote(commodityNo+contractNo, "LastQuotation", "AskPrice3");
	$('#sell_4').text(fixedPriceByContract(AskPrice3,commodityNo));
	if(AskPrice3-PreSettlePrice>0){
		$('#sell_4').css('color','red');
	}
	if(AskPrice3-PreSettlePrice<0){
		$('#sell_4').css('color','forestgreen');
	}
	//盘口中的--卖三 人数
	var AskQty3 = CacheQuoteSubscribe.getCacheContractQuote(commodityNo+contractNo, "LastQuotation", "AskQty3");
	$('#sell_5').text(AskQty3);
	//盘口中的--最低
	$('#pklowprice').text(fixedPriceByContract(LowPrice,commodityNo));
	if(LowPrice-PreSettlePrice>0){
		$('#pklowprice').css('color','red');
	}
	if(LowPrice-PreSettlePrice<0){
		$('#pklowprice').css('color','forestgreen');
	}
	//盘口中的--卖二
	var AskPrice2 = CacheQuoteSubscribe.getCacheContractQuote(commodityNo+contractNo, "LastQuotation", "AskPrice2");
	$('#sell_2').text(fixedPriceByContract(AskPrice2,commodityNo));
	if(AskPrice2-PreSettlePrice>0){
		$('#sell_2').css('color','red');
	}
	if(AskPrice2-PreSettlePrice<0){
		$('#sell_2').css('color','forestgreen');
	}
	//盘口中的--卖二 人数
	var AskQty2 = CacheQuoteSubscribe.getCacheContractQuote(commodityNo+contractNo, "LastQuotation", "AskQty2");
	$('#sell_3').text(AskQty2);
	//盘口中的--涨跌
	$('#pkzd').text(fixedPriceByContract(ChangeValue,commodityNo));
	if(ChangeValue>0){
		$('#pkzd').css('color','red');
	}
	if(ChangeValue<0){
		$('#pkzd').css('color','forestgreen');
	}
	//盘口中的--卖一
	$('#sell_0').text(fixedPriceByContract(AskPrice1,commodityNo));
	if(AskPrice1-PreSettlePrice>0){
		$('#sell_0').css('color','red');
	}
	if(AskPrice1-PreSettlePrice<0){
		$('#sell_0').css('color','forestgreen');
	}
	//盘口中的--卖一 人数
	$('#sell_1').text(AskQty1);
	//盘口中的--成交量
	$('#pktrademl').text(TotalVolume);
	//盘口中的--买一
	$('#buy_0').text(fixedPriceByContract(BidPrice1,commodityNo));
	if(BidPrice1-PreSettlePrice>0){
		$('#buy_0').css('color','red');
	}
	if(BidPrice1-PreSettlePrice<0){
		$('#buy_0').css('color','forestgreen');
	}
	//盘口中的--买一 人数
	$('#buy_1').text(BidQty1);
	//盘口中的--持仓量
	var Position = CacheQuoteSubscribe.getCacheContractQuote(commodityNo+contractNo, "LastQuotation", "Position");
	$('#pkccml').text(Position);
	//盘口中的--买二
	var BidPrice2 = CacheQuoteSubscribe.getCacheContractQuote(commodityNo+contractNo, "LastQuotation", "BidPrice2");
	$('#buy_2').text(fixedPriceByContract(BidPrice2,commodityNo));
	if(BidPrice2-PreSettlePrice>0){
		$('#buy_2').css('color','red');
	}
	if(BidPrice2-PreSettlePrice<0){
		$('#buy_2').css('color','forestgreen');
	}
	//盘口中的--买二 人数
	var BidQty2 = CacheQuoteSubscribe.getCacheContractQuote(commodityNo+contractNo, "LastQuotation", "BidQty2");
	$('#buy_3').text(BidQty2);
	//盘口中的--昨结
	var PreSettlePrice = CacheQuoteSubscribe.getCacheContractQuote(commodityNo+contractNo, "LastQuotation", "PreSettlePrice");
	$('#pkzj').text(fixedPriceByContract(PreSettlePrice,commodityNo));
	//盘口中的--买三
	var BidPrice3 = CacheQuoteSubscribe.getCacheContractQuote(commodityNo+contractNo, "LastQuotation", "BidPrice3");
	$('#buy_4').text(fixedPriceByContract(BidPrice3,commodityNo));
	if(BidPrice3-PreSettlePrice>0){
		$('#buy_4').css('color','red');
	}
	if(BidPrice3-PreSettlePrice<0){
		$('#buy_4').css('color','forestgreen');
	}
	//盘口中的--买三 人数
	var BidQty3 = CacheQuoteSubscribe.getCacheContractQuote(commodityNo+contractNo, "LastQuotation", "BidQty3");
	$('#buy_5').text(BidQty3);
	//盘口中的--买四
	var BidPrice4 = CacheQuoteSubscribe.getCacheContractQuote(commodityNo+contractNo, "LastQuotation", "BidPrice4");
	$('#buy_6').text(fixedPriceByContract(BidPrice4,commodityNo));
	if(BidPrice4-PreSettlePrice>0){
		$('#buy_6').css('color','red');
	}
	if(BidPrice4-PreSettlePrice<0){
		$('#buy_6').css('color','forestgreen');
	}
	//盘口中的--买四 人数
	var BidQty4 = CacheQuoteSubscribe.getCacheContractQuote(commodityNo+contractNo, "LastQuotation", "BidQty4");
	$('#buy_7').text(BidQty4);
	//盘口中的--买五
	var BidPrice5 = CacheQuoteSubscribe.getCacheContractQuote(commodityNo+contractNo, "LastQuotation", "BidPrice5");
	$('#buy_8').text(fixedPriceByContract(BidPrice5,commodityNo));
	if(BidPrice5-PreSettlePrice>0){
		$('#buy_8').css('color','red');
	}
	if(BidPrice5-PreSettlePrice<0){
		$('#buy_8').css('color','forestgreen');
	}
	//盘口中的--买五 人数
	var BidQty5 = CacheQuoteSubscribe.getCacheContractQuote(commodityNo+contractNo, "LastQuotation", "BidQty5");
	$('#buy_9').text(BidQty5);
	
	//交易页面 最新价
	$('#xin0').text(fixedPriceByContract(LastPrice,commodityNo));
	if(LastPrice-PreSettlePrice>0){
		$('#xin0').css('color','red');
	}
	if(LastPrice-PreSettlePrice<0){
		$('#xin0').css('color','forestgreen');
	}
	//交易页面 成交量
	$('#xin1').text(TotalVolume);
	
	//交易页面 卖一 
	$('#sell00').text(fixedPriceByContract(AskPrice1,commodityNo));
	if(AskPrice1-PreSettlePrice>0){
		$('#sell00').css('color','red');
	}
	if(AskPrice1-PreSettlePrice<0){
		$('#sell00').css('color','forestgreen');
	}
	//交易页面 卖一 人数
	$('#sell11').text(AskQty1);
	//交易页面 买一
	$('#buy00').text(fixedPriceByContract(BidPrice1,commodityNo));
	if(BidPrice1-PreSettlePrice>0){
		$('#buy00').css('color','red');
	}
	
	if(BidPrice1-PreSettlePrice<0){
		$('#buy00').css('color','forestgreen');
	}
	//交易页面 买一 人数
	$('#buy11').text(BidQty1);
	
	
	$('#select000 option').eq(0).text($("#quote-list ul").eq(index).find("li").eq(0).find("span").eq(0).text());
	$('#p00').text($("#quote-list ul").eq(index).find("li").eq(0).find("span").eq(1).text());
	$('#select666 option').eq(0).text($("#quote-list ul").eq(index).find("li").eq(0).find("span").eq(0).text());
	$('#contract').val(commodityNo+contractNo);
	$("#contractNoSelect").text(commodityNo+contractNo)
	$('#p66').text($("#quote-list ul").eq(index).find("li").eq(0).find("span").eq(1).text());
	$("#contract option").eq(index).attr("selected",true);
//	$('#select00Commodity').text($("#quote-list ul").eq(index).find("li").eq(0).find("span").eq(0).text()+' '+$("#quote-list ul").eq(index).find("li").eq(0).find("span").eq(1).text());
	clearCache();
	is_fenshi=true;
	Quote.doQryHistory(exchangeNo, commodityNo, contractNo, 0, '', '', '');
	if($("#checkPriceButton").val()==0){
		$('#sumbuyPrice').text($("#quote-list ul").eq(index).find("li").eq(1).text());
		$('#marketPrice').val($("#quote-list ul").eq(index).find("li").eq(1).text());
		$('#sumsellPrice').text($("#quote-list ul").eq(index).find("li").eq(1).text());
	}
	
	//设置条件单中的合约
	$('#pricesContract').val(commodityNo+contractNo);
	$('#timeContract').val(commodityNo+contractNo);
	
	
});
$("#myQuote-list").on("tap",".simulation_optional_list_href", function(){
	var index = $(this).index();
	commodityNo = $("#myQuote-list .simulation_optional_list_href").eq(index).attr("data").split("-")[0];
	contractNo = CacheQuoteBase.getCacheContractAttribute(commodityNo, "MainContract");
	exchangeNo=CacheQuoteBase.getCacheContractAttribute(commodityNo, "ExchangeNo");
	$("#totalVolume").val("");
	$("#commodityNo").val(commodityNo);
     $("#contractNo").val(contractNo);
	var contract=commodityNo+contractNo;
    var id=$(this).attr("id");//id=CL1704-line 在更新的时候使用
     $('#candlestick_data_id div:eq(0)').attr('id',id+'-candlestick-price');
	$('#candlestick_data_id div:eq(0)').text($('#'+contract+'-price').text());
	$('#candlestick_data_id div:eq(1) p:eq(0)').attr('id',id+'-candlestick-time');
	$('#candlestick_data_id div:eq(1) p:eq(1) span:eq(0)').attr('id',id+'-candlestick-zd');
	$('#candlestick_data_id div:eq(1) p:eq(1) span:eq(1)').attr('id',id+'-candlestick-zdf');
	$('#candlestick_data_id ul:eq(0) li:eq(1)').attr('id',id+'-candlestick-openPrice');
	$('#candlestick_data_id ul:eq(0) li:eq(3)').attr('id',id+'-candlestick-highPrice');
	$('#candlestick_data_id ul:eq(0) li:eq(5)').attr('id',id+'-candlestick-totalVolume');
	$('#candlestick_data_id ul:eq(0) li:eq(7)').attr('id',id+'-candlestick-lowPrice');	
	$('#select000 option').eq(0).text($("#myQuote-list ul").eq(index).find("li").eq(0).find("span").eq(0).text());
	$('#p00').text($("#myQuote-list ul").eq(index).find("li").eq(0).find("span").eq(1).text());
	$('#select666 option').eq(0).text($("#myQuote-list ul").eq(index).find("li").eq(0).find("span").eq(0).text());
	$('#p66').text($("#myQuote-list ul").eq(index).find("li").eq(0).find("span").eq(1).text());
	$('#'+contract+'-option-contract').attr("selected",true);
	var length=$('#contract option').length;
	for(var i=0;i<length;i++){
		var selectVal=$('#contract option').eq(i).val();
		selectVal=selectVal.substr(0,selectVal.length-4);
		if(commodityNo==selectVal){
			$('#contract option').eq(i).attr("selected",true);
			$("#contractNoSelect").text($("#myQuote-list ul").eq(index).find("li").eq(0).find("span").eq(1).text())
			$("#contractNoSelect").text($('#contract option').eq(i).val())
		}else{
			$('#contract option').eq(i).removeAttr("selected",true);
		}
	}
	$('#pricesContract').val(commodityNo+contractNo);
	$('#timeContract').val(commodityNo+contractNo);
//	$('#select00Commodity').text($("#quote-list ul").eq(index).find("li").eq(0).find("span").eq(0).text()+' '+$("#quote-list ul").eq(index).find("li").eq(0).find("span").eq(1).text());
var LastPrice = CacheQuoteSubscribe.getCacheContractQuote(commodityNo+contractNo, "LastQuotation", "LastPrice");
	$('#candlestick_data_id div:eq(0)').text(fixedPriceByContract(LastPrice,commodityNo));
	if(LastPrice-PreSettlePrice>0){
		$('#candlestick_data_id div:eq(0)').css('color','red');
	}
	if(LastPrice-PreSettlePrice<0){
		$('#candlestick_data_id div:eq(0)').css('color','forestgreen');
	}
	
	//时间
	var DateTimeStamp = CacheQuoteSubscribe.getCacheContractQuote(commodityNo+contractNo, "LastQuotation", "DateTimeStamp");
	if(DateTimeStamp!=''){
		$('#candlestick_data_id div:eq(1) p:eq(0)').text(DateTimeStamp);
	}else{
		$('#candlestick_data_id div:eq(1) p:eq(0)').text('0000-00-00 00:00');
	}
	//涨跌
	var ChangeValue = CacheQuoteSubscribe.getCacheContractQuote(commodityNo+contractNo, "LastQuotation", "ChangeValue");
	$('#candlestick_data_id div:eq(1) p:eq(1) span:eq(0)').text(fixedPriceByContract(ChangeValue,commodityNo));
	if(fixedPriceByContract(ChangeValue,commodityNo)>0){
		$('#candlestick_data_id div:eq(1) p:eq(1) span:eq(0)').css('color','red');
		$('#candlestick_data_id div:eq(1) p:eq(1) span:eq(0)').text('+'+fixedPriceByContract(ChangeValue,commodityNo));
	}
	if(fixedPriceByContract(ChangeValue,commodityNo)<0){
		$('#candlestick_data_id div:eq(1) p:eq(1) span:eq(0)').css('color','forestgreen');
	}
	
	//涨跌幅
	var ChangeRate = CacheQuoteSubscribe.getCacheContractQuote(commodityNo+contractNo, "LastQuotation", "ChangeRate");
	$('#candlestick_data_id div:eq(1) p:eq(1) span:eq(1)').text(parseFloat(ChangeRate).toFixed(2) + "%");
	if(ChangeRate>0){
		$('#candlestick_data_id div:eq(1) p:eq(1) span:eq(1)').css('color','red');
		$('#candlestick_data_id div:eq(1) p:eq(1) span:eq(1)').text('+'+parseFloat(ChangeRate).toFixed(2) + "%");
	}
	if(ChangeRate<0){
		$('#candlestick_data_id div:eq(1) p:eq(1) span:eq(1)').css('color','forestgreen');
	}
	//开盘
	var OpenPrice = CacheQuoteSubscribe.getCacheContractQuote(commodityNo+contractNo, "LastQuotation", "OpenPrice");
	$('#candlestick_data_id ul:eq(0) li:eq(1)').text(fixedPriceByContract(OpenPrice,commodityNo));
	if(fixedPriceByContract(OpenPrice,commodityNo)-fixedPriceByContract(PreSettlePrice,commodityNo)>0){
		$('#candlestick_data_id ul:eq(0) li:eq(1)').css('color','red');
	}
	if(fixedPriceByContract(OpenPrice,commodityNo)-fixedPriceByContract(PreSettlePrice,commodityNo)<0){
		$('#candlestick_data_id ul:eq(0) li:eq(1)').css('color','forestgreen');
	}
	//最高
	var HighPrice = CacheQuoteSubscribe.getCacheContractQuote(commodityNo+contractNo, "LastQuotation", "HighPrice");
	$('#candlestick_data_id ul:eq(0) li:eq(3)').text(fixedPriceByContract(HighPrice,commodityNo));
	if(fixedPriceByContract(HighPrice,commodityNo)-fixedPriceByContract(PreSettlePrice,commodityNo)>0){
		$('#candlestick_data_id ul:eq(0) li:eq(3)').css('color','red');
	}
	if(fixedPriceByContract(HighPrice,commodityNo)-fixedPriceByContract(PreSettlePrice,commodityNo)<0){
		$('#candlestick_data_id ul:eq(0) li:eq(3)').css('color','forestgreen');
	}
	//成交量
	var TotalVolume = CacheQuoteSubscribe.getCacheContractQuote(commodityNo+contractNo, "LastQuotation", "TotalVolume");
	$('#candlestick_data_id ul:eq(0) li:eq(5)').text(TotalVolume);
	//最低
	var LowPrice = CacheQuoteSubscribe.getCacheContractQuote(commodityNo+contractNo, "LastQuotation", "LowPrice");
	$('#candlestick_data_id ul:eq(0) li:eq(7)').text(fixedPriceByContract(LowPrice,commodityNo));
	if(fixedPriceByContract(LowPrice,commodityNo)-fixedPriceByContract(PreSettlePrice,commodityNo)>0){
		$('#candlestick_data_id ul:eq(0) li:eq(7)').css('color','red');
	}
	if(fixedPriceByContract(LowPrice,commodityNo)-fixedPriceByContract(PreSettlePrice,commodityNo)<0){
		$('#candlestick_data_id ul:eq(0) li:eq(7)').css('color','forestgreen');
	}
	
	//滚动条处卖一 AskPrice1
	var AskPrice1 = CacheQuoteSubscribe.getCacheContractQuote(commodityNo+contractNo, "LastQuotation", "AskPrice1");
	$('#_sell00_').text(fixedPriceByContract(AskPrice1,commodityNo));
	if(fixedPriceByContract(AskPrice1,commodityNo)-fixedPriceByContract(PreSettlePrice,commodityNo)>0){
		$('#_sell00_').css('color','red');
	}
	if(fixedPriceByContract(AskPrice1,commodityNo)-fixedPriceByContract(PreSettlePrice,commodityNo)<0){
		$('#_sell00_').css('color','forestgreen');
	}
	//滚动条处卖一成交量 AskQty1
	var AskQty1 = CacheQuoteSubscribe.getCacheContractQuote(commodityNo+contractNo, "LastQuotation", "AskQty1");
	$('#_sell01_').text(AskQty1);
	//滚动条处买一 BidPrice1
	var BidPrice1 = CacheQuoteSubscribe.getCacheContractQuote(commodityNo+contractNo, "LastQuotation", "BidPrice1");
	$('#_buy00_').text(fixedPriceByContract(BidPrice1,commodityNo));
	if(fixedPriceByContract(BidPrice1,commodityNo)-fixedPriceByContract(PreSettlePrice,commodityNo)>0){
		$('#_buy00_').css('color','red');
	}
	if(fixedPriceByContract(BidPrice1,commodityNo)-fixedPriceByContract(PreSettlePrice,commodityNo)<0){
		$('#_buy00_').css('color','forestgreen');
	}
	//滚动条处买一成交量 BidQty1
	var BidQty1 = CacheQuoteSubscribe.getCacheContractQuote(commodityNo+contractNo, "LastQuotation", "BidQty1");
	$('#_buy01_').text(BidQty1);
	
	//盘口中的数据
	//盘口中的--最新
	$('#pklastparice').text(fixedPriceByContract(LastPrice,commodityNo));
	if(fixedPriceByContract(LastPrice,commodityNo)-fixedPriceByContract(PreSettlePrice,commodityNo)>0){
		$('#pklastparice').css('color','red');
	}
	if(fixedPriceByContract(LastPrice,commodityNo)-fixedPriceByContract(PreSettlePrice,commodityNo)<0){
		$('#pklastparice').css('color','forestgreen');
	}
	//盘口中的--卖五 AskPrice5
	var AskPrice5 = CacheQuoteSubscribe.getCacheContractQuote(commodityNo+contractNo, "LastQuotation", "AskPrice5");
	$('#sell_8').text(fixedPriceByContract(AskPrice5,commodityNo));
	if(fixedPriceByContract(AskPrice5,commodityNo)-fixedPriceByContract(PreSettlePrice,commodityNo)>0){
		$('#sell_8').css('color','red');
	}
	if(fixedPriceByContract(AskPrice5,commodityNo)-fixedPriceByContract(PreSettlePrice,commodityNo)<0){
		$('#sell_8').css('color','forestgreen');
	}
	//盘口中的--卖五 人数
	var AskQty5 = CacheQuoteSubscribe.getCacheContractQuote(commodityNo+contractNo, "LastQuotation", "AskQty5");
	$('#sell_9').text(AskQty5);
	//盘口中的--开盘
	$('#pkopenprice').text(fixedPriceByContract(OpenPrice,commodityNo));
	if(OpenPrice-PreSettlePrice>0){
		$('#pkopenprice').css('color','red');
	}
	if(OpenPrice-PreSettlePrice<0){
		$('#pkopenprice').css('color','forestgreen');
	}
	//盘口中的--卖四  AskPrice4
	var AskPrice4 = CacheQuoteSubscribe.getCacheContractQuote(commodityNo+contractNo, "LastQuotation", "AskPrice4");
	$('#sell_6').text(fixedPriceByContract(AskPrice4,commodityNo));
	if(AskPrice4-PreSettlePrice>0){
		$('#sell_6').css('color','red');
	}
	if(AskPrice4-PreSettlePrice<0){
		$('#sell_6').css('color','forestgreen');
	}
	//盘口中的--卖四 人数
	var AskQty4 = CacheQuoteSubscribe.getCacheContractQuote(commodityNo+contractNo, "LastQuotation", "AskQty4");
	$('#sell_7').text(AskQty4);
	//盘口中的--最高
	$('#pkhightprice').text(fixedPriceByContract(HighPrice,commodityNo));
	if(HighPrice-PreSettlePrice>0){
		$('#pkhightprice').css('color','red');
	}
	if(HighPrice-PreSettlePrice<0){
		$('#pkhightprice').css('color','forestgreen');
	}
	//盘口中的--卖三 AskPrice3
	var AskPrice3 = CacheQuoteSubscribe.getCacheContractQuote(commodityNo+contractNo, "LastQuotation", "AskPrice3");
	$('#sell_4').text(fixedPriceByContract(AskPrice3,commodityNo));
	if(AskPrice3-PreSettlePrice>0){
		$('#sell_4').css('color','red');
	}
	if(AskPrice3-PreSettlePrice<0){
		$('#sell_4').css('color','forestgreen');
	}
	//盘口中的--卖三 人数
	var AskQty3 = CacheQuoteSubscribe.getCacheContractQuote(commodityNo+contractNo, "LastQuotation", "AskQty3");
	$('#sell_5').text(AskQty3);
	//盘口中的--最低
	$('#pklowprice').text(fixedPriceByContract(LowPrice,commodityNo));
	if(LowPrice-PreSettlePrice>0){
		$('#pklowprice').css('color','red');
	}
	if(LowPrice-PreSettlePrice<0){
		$('#pklowprice').css('color','forestgreen');
	}
	//盘口中的--卖二
	var AskPrice2 = CacheQuoteSubscribe.getCacheContractQuote(commodityNo+contractNo, "LastQuotation", "AskPrice2");
	$('#sell_2').text(fixedPriceByContract(AskPrice2,commodityNo));
	if(AskPrice2-PreSettlePrice>0){
		$('#sell_2').css('color','red');
	}
	if(AskPrice2-PreSettlePrice<0){
		$('#sell_2').css('color','forestgreen');
	}
	//盘口中的--卖二 人数
	var AskQty2 = CacheQuoteSubscribe.getCacheContractQuote(commodityNo+contractNo, "LastQuotation", "AskQty2");
	$('#sell_3').text(AskQty2);
	//盘口中的--涨跌
	$('#pkzd').text(fixedPriceByContract(ChangeValue,commodityNo));
	if(ChangeValue>0){
		$('#pkzd').css('color','red');
	}
	if(ChangeValue<0){
		$('#pkzd').css('color','forestgreen');
	}
	//盘口中的--卖一
	$('#sell_0').text(fixedPriceByContract(AskPrice1,commodityNo));
	if(AskPrice1-PreSettlePrice>0){
		$('#sell_0').css('color','red');
	}
	if(AskPrice1-PreSettlePrice<0){
		$('#sell_0').css('color','forestgreen');
	}
	//盘口中的--卖一 人数
	$('#sell_1').text(AskQty1);
	//盘口中的--成交量
	$('#pktrademl').text(TotalVolume);
	//盘口中的--买一
	$('#buy_0').text(fixedPriceByContract(BidPrice1,commodityNo));
	if(BidPrice1-PreSettlePrice>0){
		$('#buy_0').css('color','red');
	}
	if(BidPrice1-PreSettlePrice<0){
		$('#buy_0').css('color','forestgreen');
	}
	//盘口中的--买一 人数
	$('#buy_1').text(BidQty1);
	//盘口中的--持仓量
	var Position = CacheQuoteSubscribe.getCacheContractQuote(commodityNo+contractNo, "LastQuotation", "Position");
	$('#pkccml').text(Position);
	//盘口中的--买二
	var BidPrice2 = CacheQuoteSubscribe.getCacheContractQuote(commodityNo+contractNo, "LastQuotation", "BidPrice2");
	$('#buy_2').text(fixedPriceByContract(BidPrice2,commodityNo));
	if(BidPrice2-PreSettlePrice>0){
		$('#buy_2').css('color','red');
	}
	if(BidPrice2-PreSettlePrice<0){
		$('#buy_2').css('color','forestgreen');
	}
	//盘口中的--买二 人数
	var BidQty2 = CacheQuoteSubscribe.getCacheContractQuote(commodityNo+contractNo, "LastQuotation", "BidQty2");
	$('#buy_3').text(BidQty2);
	//盘口中的--昨结
	var PreSettlePrice = CacheQuoteSubscribe.getCacheContractQuote(commodityNo+contractNo, "LastQuotation", "PreSettlePrice");
	$('#pkzj').text(fixedPriceByContract(PreSettlePrice,commodityNo));
	//盘口中的--买三
	var BidPrice3 = CacheQuoteSubscribe.getCacheContractQuote(commodityNo+contractNo, "LastQuotation", "BidPrice3");
	$('#buy_4').text(fixedPriceByContract(BidPrice3,commodityNo));
	if(BidPrice3-PreSettlePrice>0){
		$('#buy_4').css('color','red');
	}
	if(BidPrice3-PreSettlePrice<0){
		$('#buy_4').css('color','forestgreen');
	}
	//盘口中的--买三 人数
	var BidQty3 = CacheQuoteSubscribe.getCacheContractQuote(commodityNo+contractNo, "LastQuotation", "BidQty3");
	$('#buy_5').text(BidQty3);
	//盘口中的--买四
	var BidPrice4 = CacheQuoteSubscribe.getCacheContractQuote(commodityNo+contractNo, "LastQuotation", "BidPrice4");
	$('#buy_6').text(fixedPriceByContract(BidPrice4,commodityNo));
	if(BidPrice4-PreSettlePrice>0){
		$('#buy_6').css('color','red');
	}
	if(BidPrice4-PreSettlePrice<0){
		$('#buy_6').css('color','forestgreen');
	}
	//盘口中的--买四 人数
	var BidQty4 = CacheQuoteSubscribe.getCacheContractQuote(commodityNo+contractNo, "LastQuotation", "BidQty4");
	$('#buy_7').text(BidQty4);
	//盘口中的--买五
	var BidPrice5 = CacheQuoteSubscribe.getCacheContractQuote(commodityNo+contractNo, "LastQuotation", "BidPrice5");
	$('#buy_8').text(fixedPriceByContract(BidPrice5,commodityNo));
	if(BidPrice5-PreSettlePrice>0){
		$('#buy_8').css('color','red');
	}
	if(BidPrice5-PreSettlePrice<0){
		$('#buy_8').css('color','forestgreen');
	}
	//盘口中的--买五 人数
	var BidQty5 = CacheQuoteSubscribe.getCacheContractQuote(commodityNo+contractNo, "LastQuotation", "BidQty5");
	$('#buy_9').text(BidQty5);
	
	//交易页面 最新价
	$('#xin0').text(fixedPriceByContract(LastPrice,commodityNo));
	if(LastPrice-PreSettlePrice>0){
		$('#xin0').css('color','red');
	}
	if(LastPrice-PreSettlePrice<0){
		$('#xin0').css('color','forestgreen');
	}
	//交易页面 成交量
	$('#xin1').text(TotalVolume);
	
	//交易页面 卖一 
	$('#sell00').text(fixedPriceByContract(AskPrice1,commodityNo));
	if(AskPrice1-PreSettlePrice>0){
		$('#sell00').css('color','red');
	}
	if(AskPrice1-PreSettlePrice<0){
		$('#sell00').css('color','forestgreen');
	}
	//交易页面 卖一 人数
	$('#sell11').text(AskQty1);
	//交易页面 买一
	$('#buy00').text(fixedPriceByContract(BidPrice1,commodityNo));
	if(BidPrice1-PreSettlePrice>0){
		$('#buy00').css('color','red');
	}
	
	if(BidPrice1-PreSettlePrice<0){
		$('#buy00').css('color','forestgreen');
	}
	//交易页面 买一 人数
	$('#buy11').text(BidQty1);
	clearCache();
	is_fenshi=true;
	Quote.doQryHistory(exchangeNo, commodityNo, contractNo, 0, '', '', '');
	if($("#checkPriceButton").val()==0){
		$('#sumbuyPrice').text($("#myQuote-list ul").eq(index).find("li").eq(1).text());
		$('#marketPrice').val($("#myQuote-list ul").eq(index).find("li").eq(1).text());
		$('#sumsellPrice').text($("#myQuote-list ul").eq(index).find("li").eq(1).text());
	}
	
})
$("#lightChartButton").on("tap",function(){
	lightChart00();
});
$("#timeChartButton").on("tap",function(){
	timeChart00();
});
document.getElementById("chiocecandlestickButton").addEventListener("tap",function(){
	click1k();
});
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


function click1k(){
		clearCache();
		is_k = true;
		is_fenshi=false;
		is_shandian=false;
		Quote.doQryHistory(exchangeNo, commodityNo, contractNo, 1, '', '', '');
	}
function click5k(){
		clearCache();
		is_k = true;
		is_fenshi=false;
		is_shandian=false;
		Quote.doQryHistory(exchangeNo, commodityNo, contractNo, 5, '', '', '');
	}
function click15k(){
	clearCache();
	is_k = true;
	is_fenshi=false;
	is_shandian=false;
	Quote.doQryHistory(exchangeNo, commodityNo, contractNo, 15, '', '', '');
}

function click30k(){
	clearCache();
	is_k = true;
	is_fenshi=false;
	is_shandian=false;
	Quote.doQryHistory(exchangeNo, commodityNo, contractNo, 30, '', '', '');
}

function click1440k(){
	clearCache();
	is_k = true;
	is_fenshi=false;
	is_shandian=false;
	Quote.doQryHistory(exchangeNo, commodityNo, contractNo, 1440, '', '', '');
}

function timeChart00(){
	clearCache();
	is_k = false;
	is_fenshi=true;
	is_shandian=false;
	Quote.doQryHistory(exchangeNo, commodityNo, contractNo, 0, '', '', '');
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

function  dealMoreOrNull(value){
	
	if($.trim(value)=='0'){
		return '多';
	}
	if($.trim(value)=='1'){
		return '空';
	}
}

function dealDrection(value){
	if(value==0){
		return '买';
	}else{
		return '卖';
	}
}


function dealStopLossType(value){
	
	var a = $.trim(value);
	if(a=='0'){
		return '限价止损';
	}
	if(a=='1'){
		return '限价止盈';
	}
	if(a=='2'){
		return '浮动止损';
	}
}

function dealOrderType(value){
	var a = $.trim(value);
	if(a=='1'){
		return '市价';
	}
	if(a=='2'){
		return  '对手价';
	}
}

function dealOrderType0(a){
	if(a==1){
		return '市价';
	}
	if(a==2){
		return  '对手价';
	}
}

/*
 * 点差是否为0，表示是否为限价
 */
function isZero(StopLossPrice,StopLossDiff){
	//点差为0表示，表示止损价不为0
	if(StopLossDiff==0){
		return true;
	}else{
		return false;
	}
}


function dealStopLossDiff(StopLossPrice,StopLossDiff,ContractNo,CommodityNo){
	
	if(StopLossPrice=='0'){
//		$('#'+(CommodityNo+ContractNo)+'-stopLoss-StopLossPrice').css('display','block');
		var a = fixedPriceByContract(StopLossDiff,CommodityNo)
		return a;
	}
}

function dealStatus(value){
	if(value==0){
		return '运行中';
	}
	
	if(value==1){
		return '暂停';
	}
	if(value==2){
		return '已触发';
	}
	if(value==3){
		return '已取消';
	}
	if(value==4){
		return '插入失败';
	}
	if(value==5){
		return '触发失败';
	}
}

//处理条件单类型
function dealConditionType(value){
	
	if(value==0){
		return "价格条件";
	}
	if(value==1){
		return "时间条件";
	}
}
//条件单列表--"条件"
function dealCompareType(CompareType,PriceTriggerPonit,AdditionFlag,AdditionType,AdditionPrice,CommodityNo){
		
	if(AdditionFlag==0){
		if(CompareType==0){
			return ">"+fixedPriceByContract(PriceTriggerPonit,CommodityNo);
		}
		else if(CompareType==1){
			return "<"+fixedPriceByContract(PriceTriggerPonit,CommodityNo);
		}
		else if(CompareType==2){
			return ">="+fixedPriceByContract(PriceTriggerPonit,CommodityNo);
		}
		else if(CompareType==3){
			return "<="+fixedPriceByContract(PriceTriggerPonit,CommodityNo);
		}else{
			return 0;
		}
	}
	
	if(AdditionFlag==1){
		
		
		if(CompareType==0 && AdditionType==1){
			return ">"+fixedPriceByContract(PriceTriggerPonit,CommodityNo)+" <"+fixedPriceByContract(AdditionPrice,CommodityNo);
		}
		
		else if(CompareType==0 && AdditionType==3){
			return ">"+fixedPriceByContract(PriceTriggerPonit,CommodityNo)+" <="+fixedPriceByContract(AdditionPrice,CommodityNo);
		}
		
		else if(CompareType==1 && AdditionType==0){
			return "<"+fixedPriceByContract(PriceTriggerPonit,CommodityNo)+" >"+fixedPriceByContract(AdditionPrice,CommodityNo);
		}
		
		else if(CompareType==1 && AdditionType==2){
			return "<"+fixedPriceByContract(PriceTriggerPonit,CommodityNo)+" >="+fixedPriceByContract(AdditionPrice,CommodityNo);
		}
		
		else if(CompareType==2 && AdditionType==1){
			return ">="+fixedPriceByContract(PriceTriggerPonit,CommodityNo)+" <"+fixedPriceByContract(AdditionPrice,CommodityNo);
		}
		
		else if(CompareType==2 && AdditionType==3){
			return ">="+fixedPriceByContract(PriceTriggerPonit,CommodityNo)+" <="+fixedPriceByContract(AdditionPrice,CommodityNo);
		}
		
		else if(CompareType==3 && AdditionType==0){
			return "<="+fixedPriceByContract(PriceTriggerPonit,CommodityNo)+" >"+fixedPriceByContract(AdditionPrice,CommodityNo);
		}
		
		else if(CompareType==3 && AdditionType==2){
			return "<="+fixedPriceByContract(PriceTriggerPonit,CommodityNo)+" >="+fixedPriceByContract(AdditionPrice,CommodityNo);
		}
		
		else if(CompareType==3 && AdditionType==0){
			return "<="+fixedPriceByContract(PriceTriggerPonit,CommodityNo)+" >"+fixedPriceByContract(AdditionPrice,CommodityNo);
		}
		//没有5这种状态，但是由于功能实现需要，写了一个
		else if(CompareType==5 && AdditionType==0){
			return ">"+fixedPriceByContract(AdditionPrice,CommodityNo);
		}
		else if(CompareType==5 && AdditionType==1){
			return "<"+fixedPriceByContract(AdditionPrice,CommodityNo);
		}
		else if(CompareType==5 && AdditionType==2){
			return ">="+fixedPriceByContract(AdditionPrice,CommodityNo);
		}
		else if(CompareType==5 && AdditionType==3){
			return "<="+fixedPriceByContract(AdditionPrice,CommodityNo);
		}
		else{
			
			return 0;
		}
		
		
		
		
	}
	
}


$('#contract').change(function(){
	
	//p1:CL1704
	var p1 =$(this).children('option:selected').val();
	//p2：美元兑人民币
	var p2 = $(this).children('option:selected').text();
	$('#candlestick_data_id div:eq(0)').attr('id',p1+'-line-candlestick-price');
//	$('#candlestick_data_id div:eq(0)').text();
	
	$('#candlestick_data_id div:eq(1) p:eq(0)').attr('id',p1+'-line-candlestick-time');
	$('#candlestick_data_id div:eq(1) p:eq(1) span:eq(0)').attr('id',p1+'-line-candlestick-zd');
	$('#candlestick_data_id div:eq(1) p:eq(1) span:eq(1)').attr('id',p1+'-line-candlestick-zdf');
	$('#candlestick_data_id ul:eq(0) li:eq(1)').attr('id',p1+'-line-candlestick-openPrice');
	$('#candlestick_data_id ul:eq(0) li:eq(3)').attr('id',p1+'-line-candlestick-highPrice');
	$('#candlestick_data_id ul:eq(0) li:eq(5)').attr('id',p1+'-line-candlestick-totalVolume');
	$('#candlestick_data_id ul:eq(0) li:eq(7)').attr('id',p1+'-line-candlestick-lowPrice');
//	var a = p2.split(' ');
	$('#simulation_trade_name p:eq(0) select option').text(p2);
	$('#simulation_trade_name p:eq(1)').text(p1);
	//subp1：CUS
	var subp1 = p1.substr(0,p1.length-4);
	//subp2:1705
	var subp2 = p1.substr(p1.length-4,p1.length);
	exchangeNo =CacheQuoteBase.getCacheContractAttribute(subp1, "ExchangeNo");
	commodityNo=CacheQuoteBase.getCacheContractAttribute(subp1, "CommodityNo");
	contractNo =subp2;
	$("#commodityNo").val(commodityNo);
    $("#contractNo").val(contractNo);
    $("#contractNoSelect").text(p1);
    $('#select666 option').eq(0).text($("#select000 option").eq(0).text());
	$('#p66').text(p1);
	
	//清除charts中原有的数据
	clearCache();
	//分时图请求
	is_fenshi=true;
	Quote.doQryHistory(exchangeNo, commodityNo, contractNo, 0, '', '', '');
	//根据CommodityNo为最高，最低等值赋初值，即使没有行情的情况下，也不会错误的显示值
	// commodityNo :CUS
//	var commodityNo = subp1;
	//contractNo :1705
//	var contractNo = subp2;
	//最新价  PreSettlePrice
	var LastPrice = CacheQuoteSubscribe.getCacheContractQuote(commodityNo+contractNo, "LastQuotation", "LastPrice");
	$('#candlestick_data_id div:eq(0)').text(fixedPriceByContract(LastPrice,commodityNo));
	if(LastPrice-PreSettlePrice>0){
		$('#candlestick_data_id div:eq(0)').css('color','red');
	}
	if(LastPrice-PreSettlePrice<0){
		$('#candlestick_data_id div:eq(0)').css('color','forestgreen');
	}
	
	//时间
	var DateTimeStamp = CacheQuoteSubscribe.getCacheContractQuote(commodityNo+contractNo, "LastQuotation", "DateTimeStamp");
	if(DateTimeStamp!=''){
		$('#candlestick_data_id div:eq(1) p:eq(0)').text(DateTimeStamp);
	}else{
		$('#candlestick_data_id div:eq(1) p:eq(0)').text('0000-00-00 00:00');
	}
	//涨跌
	var ChangeValue = CacheQuoteSubscribe.getCacheContractQuote(commodityNo+contractNo, "LastQuotation", "ChangeValue");
	$('#candlestick_data_id div:eq(1) p:eq(1) span:eq(0)').text(fixedPriceByContract(ChangeValue,commodityNo));
	if(fixedPriceByContract(ChangeValue,commodityNo)>0){
		$('#candlestick_data_id div:eq(1) p:eq(1) span:eq(0)').css('color','red');
		$('#candlestick_data_id div:eq(1) p:eq(1) span:eq(0)').text('+'+fixedPriceByContract(ChangeValue,commodityNo));
	}
	if(fixedPriceByContract(ChangeValue,commodityNo)<0){
		$('#candlestick_data_id div:eq(1) p:eq(1) span:eq(0)').css('color','forestgreen');
	}
	
	//涨跌幅
	var ChangeRate = CacheQuoteSubscribe.getCacheContractQuote(commodityNo+contractNo, "LastQuotation", "ChangeRate");
	$('#candlestick_data_id div:eq(1) p:eq(1) span:eq(1)').text(parseFloat(ChangeRate).toFixed(2) + "%");
	if(ChangeRate>0){
		$('#candlestick_data_id div:eq(1) p:eq(1) span:eq(1)').css('color','red');
		$('#candlestick_data_id div:eq(1) p:eq(1) span:eq(1)').text('+'+parseFloat(ChangeRate).toFixed(2) + "%");
	}
	if(ChangeRate<0){
		$('#candlestick_data_id div:eq(1) p:eq(1) span:eq(1)').css('color','forestgreen');
	}
	//开盘
	var OpenPrice = CacheQuoteSubscribe.getCacheContractQuote(commodityNo+contractNo, "LastQuotation", "OpenPrice");
	$('#candlestick_data_id ul:eq(0) li:eq(1)').text(fixedPriceByContract(OpenPrice,commodityNo));
	if(fixedPriceByContract(OpenPrice,commodityNo)-fixedPriceByContract(PreSettlePrice,commodityNo)>0){
		$('#candlestick_data_id ul:eq(0) li:eq(1)').css('color','red');
	}
	if(fixedPriceByContract(OpenPrice,commodityNo)-fixedPriceByContract(PreSettlePrice,commodityNo)<0){
		$('#candlestick_data_id ul:eq(0) li:eq(1)').css('color','forestgreen');
	}
	//最高
	var HighPrice = CacheQuoteSubscribe.getCacheContractQuote(commodityNo+contractNo, "LastQuotation", "HighPrice");
	$('#candlestick_data_id ul:eq(0) li:eq(3)').text(fixedPriceByContract(HighPrice,commodityNo));
	if(fixedPriceByContract(HighPrice,commodityNo)-fixedPriceByContract(PreSettlePrice,commodityNo)>0){
		$('#candlestick_data_id ul:eq(0) li:eq(3)').css('color','red');
	}
	if(fixedPriceByContract(HighPrice,commodityNo)-fixedPriceByContract(PreSettlePrice,commodityNo)<0){
		$('#candlestick_data_id ul:eq(0) li:eq(3)').css('color','forestgreen');
	}
	//成交量
	var TotalVolume = CacheQuoteSubscribe.getCacheContractQuote(commodityNo+contractNo, "LastQuotation", "TotalVolume");
	$('#candlestick_data_id ul:eq(0) li:eq(5)').text(TotalVolume);
	//最低
	var LowPrice = CacheQuoteSubscribe.getCacheContractQuote(commodityNo+contractNo, "LastQuotation", "LowPrice");
	$('#candlestick_data_id ul:eq(0) li:eq(7)').text(fixedPriceByContract(LowPrice,commodityNo));
	if(fixedPriceByContract(LowPrice,commodityNo)-fixedPriceByContract(PreSettlePrice,commodityNo)>0){
		$('#candlestick_data_id ul:eq(0) li:eq(7)').css('color','red');
	}
	if(fixedPriceByContract(LowPrice,commodityNo)-fixedPriceByContract(PreSettlePrice,commodityNo)<0){
		$('#candlestick_data_id ul:eq(0) li:eq(7)').css('color','forestgreen');
	}
	
	//滚动条处卖一 AskPrice1
	var AskPrice1 = CacheQuoteSubscribe.getCacheContractQuote(commodityNo+contractNo, "LastQuotation", "AskPrice1");
	$('#_sell00_').text(fixedPriceByContract(AskPrice1,commodityNo));
	if(fixedPriceByContract(AskPrice1,commodityNo)-fixedPriceByContract(PreSettlePrice,commodityNo)>0){
		$('#_sell00_').css('color','red');
	}
	if(fixedPriceByContract(AskPrice1,commodityNo)-fixedPriceByContract(PreSettlePrice,commodityNo)<0){
		$('#_sell00_').css('color','forestgreen');
	}
	//滚动条处卖一成交量 AskQty1
	var AskQty1 = CacheQuoteSubscribe.getCacheContractQuote(commodityNo+contractNo, "LastQuotation", "AskQty1");
	$('#_sell01_').text(AskQty1);
	//滚动条处买一 BidPrice1
	var BidPrice1 = CacheQuoteSubscribe.getCacheContractQuote(commodityNo+contractNo, "LastQuotation", "BidPrice1");
	$('#_buy00_').text(fixedPriceByContract(BidPrice1,commodityNo));
	if(fixedPriceByContract(BidPrice1,commodityNo)-fixedPriceByContract(PreSettlePrice,commodityNo)>0){
		$('#_buy00_').css('color','red');
	}
	if(fixedPriceByContract(BidPrice1,commodityNo)-fixedPriceByContract(PreSettlePrice,commodityNo)<0){
		$('#_buy00_').css('color','forestgreen');
	}
	//滚动条处买一成交量 BidQty1
	var BidQty1 = CacheQuoteSubscribe.getCacheContractQuote(commodityNo+contractNo, "LastQuotation", "BidQty1");
	$('#_buy01_').text(BidQty1);
	
	//盘口中的数据
	//盘口中的--最新
	$('#pklastparice').text(fixedPriceByContract(LastPrice,commodityNo));
	if(fixedPriceByContract(LastPrice,commodityNo)-fixedPriceByContract(PreSettlePrice,commodityNo)>0){
		$('#pklastparice').css('color','red');
	}
	if(fixedPriceByContract(LastPrice,commodityNo)-fixedPriceByContract(PreSettlePrice,commodityNo)<0){
		$('#pklastparice').css('color','forestgreen');
	}
	//盘口中的--卖五 AskPrice5
	var AskPrice5 = CacheQuoteSubscribe.getCacheContractQuote(commodityNo+contractNo, "LastQuotation", "AskPrice5");
	$('#sell_8').text(fixedPriceByContract(AskPrice5,commodityNo));
	if(fixedPriceByContract(AskPrice5,commodityNo)-fixedPriceByContract(PreSettlePrice,commodityNo)>0){
		$('#sell_8').css('color','red');
	}
	if(fixedPriceByContract(AskPrice5,commodityNo)-fixedPriceByContract(PreSettlePrice,commodityNo)<0){
		$('#sell_8').css('color','forestgreen');
	}
	//盘口中的--卖五 人数
	var AskQty5 = CacheQuoteSubscribe.getCacheContractQuote(commodityNo+contractNo, "LastQuotation", "AskQty5");
	$('#sell_9').text(AskQty5);
	//盘口中的--开盘
	$('#pkopenprice').text(fixedPriceByContract(OpenPrice,commodityNo));
	if(OpenPrice-PreSettlePrice>0){
		$('#pkopenprice').css('color','red');
	}
	if(OpenPrice-PreSettlePrice<0){
		$('#pkopenprice').css('color','forestgreen');
	}
	//盘口中的--卖四  AskPrice4
	var AskPrice4 = CacheQuoteSubscribe.getCacheContractQuote(commodityNo+contractNo, "LastQuotation", "AskPrice4");
	$('#sell_6').text(fixedPriceByContract(AskPrice4,commodityNo));
	if(AskPrice4-PreSettlePrice>0){
		$('#sell_6').css('color','red');
	}
	if(AskPrice4-PreSettlePrice<0){
		$('#sell_6').css('color','forestgreen');
	}
	//盘口中的--卖四 人数
	var AskQty4 = CacheQuoteSubscribe.getCacheContractQuote(commodityNo+contractNo, "LastQuotation", "AskQty4");
	$('#sell_7').text(AskQty4);
	//盘口中的--最高
	$('#pkhightprice').text(fixedPriceByContract(HighPrice,commodityNo));
	if(HighPrice-PreSettlePrice>0){
		$('#pkhightprice').css('color','red');
	}
	if(HighPrice-PreSettlePrice<0){
		$('#pkhightprice').css('color','forestgreen');
	}
	//盘口中的--卖三 AskPrice3
	var AskPrice3 = CacheQuoteSubscribe.getCacheContractQuote(commodityNo+contractNo, "LastQuotation", "AskPrice3");
	$('#sell_4').text(fixedPriceByContract(AskPrice3,commodityNo));
	if(AskPrice3-PreSettlePrice>0){
		$('#sell_4').css('color','red');
	}
	if(AskPrice3-PreSettlePrice<0){
		$('#sell_4').css('color','forestgreen');
	}
	//盘口中的--卖三 人数
	var AskQty3 = CacheQuoteSubscribe.getCacheContractQuote(commodityNo+contractNo, "LastQuotation", "AskQty3");
	$('#sell_5').text(AskQty3);
	//盘口中的--最低
	$('#pklowprice').text(fixedPriceByContract(LowPrice,commodityNo));
	if(LowPrice-PreSettlePrice>0){
		$('#pklowprice').css('color','red');
	}
	if(LowPrice-PreSettlePrice<0){
		$('#pklowprice').css('color','forestgreen');
	}
	//盘口中的--卖二
	var AskPrice2 = CacheQuoteSubscribe.getCacheContractQuote(commodityNo+contractNo, "LastQuotation", "AskPrice2");
	$('#sell_2').text(fixedPriceByContract(AskPrice2,commodityNo));
	if(AskPrice2-PreSettlePrice>0){
		$('#sell_2').css('color','red');
	}
	if(AskPrice2-PreSettlePrice<0){
		$('#sell_2').css('color','forestgreen');
	}
	//盘口中的--卖二 人数
	var AskQty2 = CacheQuoteSubscribe.getCacheContractQuote(commodityNo+contractNo, "LastQuotation", "AskQty2");
	$('#sell_3').text(AskQty2);
	//盘口中的--涨跌
	$('#pkzd').text(fixedPriceByContract(ChangeValue,commodityNo));
	if(ChangeValue>0){
		$('#pkzd').css('color','red');
	}
	if(ChangeValue<0){
		$('#pkzd').css('color','forestgreen');
	}
	//盘口中的--卖一
	$('#sell_0').text(fixedPriceByContract(AskPrice1,commodityNo));
	if(AskPrice1-PreSettlePrice>0){
		$('#sell_0').css('color','red');
	}
	if(AskPrice1-PreSettlePrice<0){
		$('#sell_0').css('color','forestgreen');
	}
	//盘口中的--卖一 人数
	$('#sell_1').text(AskQty1);
	//盘口中的--成交量
	$('#pktrademl').text(TotalVolume);
	//盘口中的--买一
	$('#buy_0').text(fixedPriceByContract(BidPrice1,commodityNo));
	if(BidPrice1-PreSettlePrice>0){
		$('#buy_0').css('color','red');
	}
	if(BidPrice1-PreSettlePrice<0){
		$('#buy_0').css('color','forestgreen');
	}
	//盘口中的--买一 人数
	$('#buy_1').text(BidQty1);
	//盘口中的--持仓量
	var Position = CacheQuoteSubscribe.getCacheContractQuote(commodityNo+contractNo, "LastQuotation", "Position");
	$('#pkccml').text(Position);
	//盘口中的--买二
	var BidPrice2 = CacheQuoteSubscribe.getCacheContractQuote(commodityNo+contractNo, "LastQuotation", "BidPrice2");
	$('#buy_2').text(fixedPriceByContract(BidPrice2,commodityNo));
	if(BidPrice2-PreSettlePrice>0){
		$('#buy_2').css('color','red');
	}
	if(BidPrice2-PreSettlePrice<0){
		$('#buy_2').css('color','forestgreen');
	}
	//盘口中的--买二 人数
	var BidQty2 = CacheQuoteSubscribe.getCacheContractQuote(commodityNo+contractNo, "LastQuotation", "BidQty2");
	$('#buy_3').text(BidQty2);
	//盘口中的--昨结
	var PreSettlePrice = CacheQuoteSubscribe.getCacheContractQuote(commodityNo+contractNo, "LastQuotation", "PreSettlePrice");
	$('#pkzj').text(fixedPriceByContract(PreSettlePrice,commodityNo));
	//盘口中的--买三
	var BidPrice3 = CacheQuoteSubscribe.getCacheContractQuote(commodityNo+contractNo, "LastQuotation", "BidPrice3");
	$('#buy_4').text(fixedPriceByContract(BidPrice3,commodityNo));
	if(BidPrice3-PreSettlePrice>0){
		$('#buy_4').css('color','red');
	}
	if(BidPrice3-PreSettlePrice<0){
		$('#buy_4').css('color','forestgreen');
	}
	//盘口中的--买三 人数
	var BidQty3 = CacheQuoteSubscribe.getCacheContractQuote(commodityNo+contractNo, "LastQuotation", "BidQty3");
	$('#buy_5').text(BidQty3);
	//盘口中的--买四
	var BidPrice4 = CacheQuoteSubscribe.getCacheContractQuote(commodityNo+contractNo, "LastQuotation", "BidPrice4");
	$('#buy_6').text(fixedPriceByContract(BidPrice4,commodityNo));
	if(BidPrice4-PreSettlePrice>0){
		$('#buy_6').css('color','red');
	}
	if(BidPrice4-PreSettlePrice<0){
		$('#buy_6').css('color','forestgreen');
	}
	//盘口中的--买四 人数
	var BidQty4 = CacheQuoteSubscribe.getCacheContractQuote(commodityNo+contractNo, "LastQuotation", "BidQty4");
	$('#buy_7').text(BidQty4);
	//盘口中的--买五
	var BidPrice5 = CacheQuoteSubscribe.getCacheContractQuote(commodityNo+contractNo, "LastQuotation", "BidPrice5");
	$('#buy_8').text(fixedPriceByContract(BidPrice5,commodityNo));
	if(BidPrice5-PreSettlePrice>0){
		$('#buy_8').css('color','red');
	}
	if(BidPrice5-PreSettlePrice<0){
		$('#buy_8').css('color','forestgreen');
	}
	//盘口中的--买五 人数
	var BidQty5 = CacheQuoteSubscribe.getCacheContractQuote(commodityNo+contractNo, "LastQuotation", "BidQty5");
	$('#buy_9').text(BidQty5);
	
	//交易页面 最新价
	$('#xin0').text(fixedPriceByContract(LastPrice,commodityNo));
	if(LastPrice-PreSettlePrice>0){
		$('#xin0').css('color','red');
	}
	if(LastPrice-PreSettlePrice<0){
		$('#xin0').css('color','forestgreen');
	}
	//交易页面 成交量
	$('#xin1').text(TotalVolume);
	
	//交易页面 卖一 
	$('#sell00').text(fixedPriceByContract(AskPrice1,commodityNo));
	if(AskPrice1-PreSettlePrice>0){
		$('#sell00').css('color','red');
	}
	if(AskPrice1-PreSettlePrice<0){
		$('#sell00').css('color','forestgreen');
	}
	//交易页面 卖一 人数
	$('#sell11').text(AskQty1);
	//交易页面 买一
	$('#buy00').text(fixedPriceByContract(BidPrice1,commodityNo));
	if(BidPrice1-PreSettlePrice>0){
		$('#buy00').css('color','red');
	}
	
	if(BidPrice1-PreSettlePrice<0){
		$('#buy00').css('color','forestgreen');
	}
	//交易页面 买一 人数
	$('#buy11').text(BidQty1);		
	
});


/**
 * 止损单--未触发列表--暂停
 */
$('#zhanting').on('tap',function(){
	if($('#stopLoss-list').children().length>0){
		var hasClass = false;
		$('#stopLoss-list').children().each(function(){
			var _this = $(this);
	  		if(_this.hasClass('clickBg')){
	  			hasClass = true;
	  			
	  		}
		});
		if(hasClass){
			suspendStopLoss();
		}else{
			layer.msg('请选择一条记录!');
		}
	}else{
		
		layer.msg('止损单列表无数据!');
	}
	
});

//	暂停/启动 功能
function suspendStopLoss(){
	
	if($('#stopLoss-list').children().length>0){
		$('#stopLoss-list').children().each(function(){
			var _this = $(this);
	  		if(_this.hasClass('clickBg')){
	  		//止损编号
			var StopLossNo =  _this.children().eq(9).text();
	  		//操作类型：0:修改 1：删除	2：暂停	3：启动
	  		//0:运行中   1:暂停
			var Status= $(this).children().eq(17).text();
			var ModifyFlag;
			//处于运行中：0，那么ModifyFlag=2
			if(Status==0){
				ModifyFlag = 2;
			}
			//处于暂停：1，那么ModifyFlag=3
			if(Status==1){
				ModifyFlag = 3;
			}
			//订单数量
			var Num = _this.children().eq(4).text();
			//止损类型	0-限价触发止损	1-限价触发止盈 	2-浮动止损
			var StopLossType = _this.children().eq(10).text();
			//触发下单价格类型:市价-1，对价-2 
			var OrderType = _this.children().eq(13).text();
			//止损(赢)价 
			var StopLossPrice = _this.children().eq(11).text();
			//动态止损的点差
			var StopLossDiff = _this.children().eq(12).text();
			
			//OnRtnStopLossState
//			Trade.doModifyStopLoss(StopLossNo,ModifyFlag,Num,StopLossType,OrderType,StopLossDiff,StopLossPrice);
	  		if(Status==0){
					layer.confirm(
						'确认暂停',
						{
							icon: 3,
							title: '确认暂停？'
						},
						function(index){
							Trade.doModifyStopLoss(StopLossNo,ModifyFlag,Num,StopLossType,OrderType,StopLossDiff,StopLossPrice);
							layer.close(index);
						},
						function(index) { // 取消
							layer.close(index);
						}
					
					);
			}
	  		
	  		if(Status==1){
				layer.confirm(
					'确认启动',
					{
						icon: 3,
						title: '确认启动？'
					},
					function(index){
						Trade.doModifyStopLoss(StopLossNo,ModifyFlag,Num,StopLossType,OrderType,StopLossDiff,StopLossPrice);
						layer.close(index);
					},
					function(index) { // 取消
						layer.close(index);
					}
				
				);
			}
	  		
	  		
	  		
	  		
	  		
	  		
	  		
	  		
	  		
	  		}
		});
	}
}



/**
 * 止损单--未触发列表--删除
 */
$('#shanchu').on('tap',function(){
	
	if($('#stopLoss-list').children().length>0){
		var hasClass = false;
		$('#stopLoss-list').children().each(function(){
			var _this = $(this);
			if(_this.hasClass('clickBg')){
				hasClass = true;
			}
		});
		
		if(hasClass){
			//删除止损单--未触发列表一行记录
			deleteStopLoss();
		}else{
			layer.msg('请选择一条记录!');
		}
		
		
	}else{
		
		layer.msg('止损单列表无数据!');
	}
});
//删除止损单--未触发列表一行记录
/**
* Trade.doModifyStopLoss: function(stopLossNo, modifyFlag, num, stopLossType, orderType, stopLossDiff, stopLossPrice)
**/
function deleteStopLoss(){
	$('#stopLoss-list').children().each(function(){
		var _this = $(this);
		if(_this.hasClass('clickBg')){
			//止损编号
			var StopLossNo =  _this.children().eq(9).text();
			//操作类型：0:修改 1：删除	2：暂停	3：启动
			var ModifyFlag = 1;
			//订单数量
			var Num = _this.children().eq(4).text();
			//止损类型	0-限价触发止损	1-限价触发止盈 	2-浮动止损
			var StopLossType = _this.children().eq(10).text();
			//触发下单价格类型:市价-1，对价-2 
			var OrderType = _this.children().eq(13).text();
			//止损(赢)价 
			var StopLossPrice = _this.children().eq(11).text();
			//动态止损的点差
			var StopLossDiff = _this.children().eq(12).text();
			
			//OnRtnStopLossState
			layer.confirm(
				"是否删除止损单？",
				{
					icon: 2,
					title: '确认删除？'
				},
				function(index){
					Trade.doModifyStopLoss(StopLossNo,ModifyFlag,Num,StopLossType,OrderType,StopLossDiff,StopLossPrice);
					_this.remove();
					layer.close(index);
				},
				function(index) { // 取消
					layer.close(index);
				}
				
			);
			
			
		}
	});
	
}
$("#modifiystopOrderPopover").on('tap',function(){
	if($('#stopLoss-list').children().length>0){
		var hasClass = false;
		
		$('#stopLoss-list').children().each(function(){
			var _this = $(this);
			if(_this.hasClass('clickBg')){
				hasClass = true;
				//1:限价止盈	2：限价止损
				var StopLossType = _this.children().eq(10).text();
				if(StopLossType==1){
					$("#stopAndLoss a").eq(1).addClass("mui-active");
					$("#stopAndLoss a").eq(0).removeClass('mui-active');
					$("#stopTitle").removeClass("mui-active");
					$("#lossTitle").addClass("mui-active");
				}else{
					$("#stopAndLoss a").eq(0).addClass("mui-active");
					$("#stopAndLoss a").eq(1).removeClass('mui-active');
					$("#stopTitle").addClass("mui-active");
					$("#lossTitle").removeClass("mui-active");
				}
			}
		});
		if(hasClass){
			mui("#popoverLoss").popover("toggle");
			//mui("#popoverLoss").popover("toggle");
			modifyStopLoss();
			//修改止损单--未触发列表一行记录
			$('#stopAdd00').text('修改');
			$('#zhiyinAdd').text('修改');
			
		}else{
			layer.msg('请选择一条记录!');
		}
	}else{
		
		layer.msg('止损单列表无数据!');
	}
});

//修改止损单--未触发列表一行记录
var StopLossNo_Stop;
var CommodityNoContractNo;
function modifyStopLoss(value){
	if($('#stopLoss-list').children().length>0){
		$('#stopLoss-list').children().each(function(){
			var _this = $(this);
			if(_this.hasClass('clickBg')){
				if(_this.children().eq(17).text()=='1'){
					CommodityNoContractNo = _this.children().eq(0).text();
					//运行中
					var Status = _this.children().eq(1).text();	
					//多 空
					var HoldDrection = _this.children().eq(2).text();	
					//类别： 限价止损
					var StopLossType = _this.children().eq(10).text();
					//手数
					var Num = _this.children().eq(4).text();
					//CL
					var CommodityNo = _this.children().eq(14).text();
					//
					var StopLossPrice = _this.children().eq(11).text();
					//
					var StopLossDiff = _this.children().eq(12).text();
					
					var HoldAvgPrice = _this.children().eq(16).text();
					var OrderType = _this.children().eq(13).text();
					StopLossNo_Stop = _this.children().eq(9).text();
					
					//缓存中的最新价
					var LastPrice = CacheQuoteSubscribe.getCacheContractQuote(CommodityNoContractNo, 
						"LastQuotation", "LastPrice");
					
					$('#stopContract').text(CacheQuoteBase.getCacheContractAttribute(CommodityNo, "CommodityName"));
					$('#stopMoreOrNull').text(HoldDrection);
					$('#stopLast').text('');
					$('#stopLast').text(fixedPriceByContract(LastPrice,CommodityNo));
					$('#stopSecOption').val(isZero(StopLossPrice,StopLossDiff)?1:2);
					if($('#stopSecOption').val()==1){
						$('#stopPrice').val(fixedPriceByContract(StopLossPrice,CommodityNo));
					}
					if($('#stopSecOption').val()==2){
						$('#stopPrice').val(fixedPriceByContract(StopLossDiff,CommodityNo));
					}
					
					var percent = (fixedPriceByContract(StopLossPrice,CommodityNo)-HoldAvgPrice)/HoldAvgPrice;
					$('#stopPercent').text(toFixedFloatNumber(percent,2));
					$('#zhiyinNum').val(Num);
					$('#zhiyinOrderType').val(OrderType);
					
					$('#stopSecOption').change(function(){
						$('#stopPrice').val(0);
						$('#stopPercent').text('0');
					});
					
					$('#stopPrice').keyup(function(){
							if($('#stopSecOption').val()==1){
								var price = $('#stopPrice').val();
								var num = (price-HoldAvgPrice)/HoldAvgPrice;
								$('#stopPercent').text(toFixedFloatNumber(num,2));
							}
							
							if($('#stopSecOption').val()==2){
								var price = $('#stopPrice').val();
								var num = price/HoldAvgPrice;
								$('#stopPercent').text(toFixedFloatNumber(num,2));
							}
					});
					
					//----------止损单-修改--止盈----------
					$('#zhiyin00heyue').text(CacheQuoteBase.getCacheContractAttribute(CommodityNo, "CommodityName"));
					$('#zhiyin00moreOrNull').text(HoldDrection);
					$('#zhiyin00Last').text(fixedPriceByContract(LastPrice,CommodityNo));
					$('#zhiyinjia').val(_this.children().eq(11).text());
					$('#zhiyinPercent00').text(toFixedFloatNumber(($('#zhiyinjia').val()-HoldAvgPrice)/HoldAvgPrice,2));
					$('#zhiyinshoushu').val(_this.children().eq(4).text());
					if(_this.children().eq(6).text()=='市价'){
						$('#zhiyinweituojia').val(1);
					}
					if(_this.children().eq(6).text()=='对手价'){
						$('#zhiyinweituojia').val(2);
					}
					
					$('#zhiyinjia').keyup(function(){
						
						$('#zhiyinPercent00').text(toFixedFloatNumber(($('#zhiyinjia').val()-HoldAvgPrice)/HoldAvgPrice,2));
					});
					
				
				}else{
					mui("#popoverLoss").popover("toggle");
					layer.msg('止损单支持暂停状态可修改!');
				}
			}
		});
		
	}
}

			$('#zhiyinAdd').on('tap',function(){
					//0：修改
					var ModifyFlag = 0;
					var Num = $('#zhiyinshoushu').val();
					//1-限价触发止盈
					var StopLossType = 1;
					var OrderType = $('#zhiyinweituojia').val();
					var StopLossPrice = $('#zhiyinjia').val();
					var StopLossDiff = 0;
					
					if($('#zhiyinAdd').text()=='修改'){
						mui.confirm("确认修改止盈:合约【" + CommodityNoContractNo+ "】,价格【"+StopLossPrice+"】,手数:【"+Num+"】,止盈委托价:【"+(OrderType==1?"市价":"对手价")+"】",function(e){
							if(e.index==1){
								//确认
								Trade.doModifyStopLoss(StopLossNo_Stop,ModifyFlag,Num,StopLossType,OrderType,StopLossDiff,StopLossPrice);
							}
						});
					}
				});




					$('#stopAdd00').on('tap',function(){
							//0：修改
							var ModifyFlag = 0;
							var Num = $('#zhiyinNum').val();
							//止损    1：止损价	2:动态价
							var stopSecOption = $('#stopSecOption').val();
							var StopLossType=0;
							var StopLossPrice=0;
							var StopLossDiff=0;
							if(stopSecOption==1){
								StopLossType = 0;
								StopLossPrice = $('#stopPrice').val();
							}
							if(stopSecOption==2){
								StopLossType = 2;
								StopLossDiff = $('#stopPrice').val();
							}
							//市价-1，对价-2 
							var OrderType = $('#zhiyinOrderType').val();
							if($('#stopAdd00').text()=='修改'){	
								mui.confirm("确认修改止损:合约【" + CommodityNoContractNo+ "】,方式【"+(stopSecOption==1?"止损价":"动态价")+"】,价格:【"+(stopSecOption==1?StopLossPrice:StopLossDiff)+"】,手数:【"+Num+"】,止损委托价:【"+(OrderType==1?"市价":"对手价")+"】",function(e){
									if(e.index==1){
										Trade.doModifyStopLoss(StopLossNo_Stop,ModifyFlag,Num,StopLossType,OrderType,StopLossDiff,StopLossPrice);
									}
									
								});
							}
								
						});




// 查询历史成交
function qryHisTrade(){
	indexhis=1;
	$("#hisTradeList").empty();
	var minTime=document.getElementById("finance").value;
	var maxTime=document.getElementById("finance1").value;
	if(minTime == "" || maxTime == ""){ 
		
		//默认前3天的数据
		var d = new Date();
		var year = d.getFullYear();
		var month = d.getMonth()+1;
		if(month < 10){
			month = "0" + month;
		}
		var str = year+"/"+month+"/"+d.getDate()+" "+"00:00:00";
		
		var timestamp1 = new Date(str).getTime()/1000;
		
		var timestamp2 = timestamp1 - 3*24*60*60;
		
		maxTime = vs.dateUtil.getFormatDataByLong(timestamp1,"yyyy/MM/dd");
		console.log(str);
		minTime = vs.dateUtil.getFormatDataByLong(timestamp2,"yyyy/MM/dd");
		document.getElementById("finance").value = minTime;
	    document.getElementById("finance1").value = maxTime;
	    maxTime = maxTime + " 00:00:00";
	    minTime = minTime + " 00:00:00";
		
	}else{
		minTime = minTime + " "+"00:00:00"; 
		maxTime = maxTime +" "+"23:59:59"; 
	}
	Trade.doHisTrade(TradeConfig.username,minTime,maxTime);
}
$('#qryHisTrade1').on('tap',function(){
	qryHisTrade();
});
$('#qryHisTrade2').on('tap',function(){
	qryHisTrade();
});
function doRevokeNum(OrderNum,TradeNum,OrderStatus){
	
	if(OrderStatus==4){
		return OrderNum-TradeNum;
	}else{
		
		return 0;
	}
}

/**
 * 价格条件--添加
 */
var AdditionFlag00=$("#selectAddition").find("option:selected").val();
if(AdditionFlag00==-1){
	$('#additionPriceNumber').attr("disabled","disabled");
}
$("#selectAddition").change(function(){
	
	//是否附加条件
	AdditionFlag00 = $("#selectAddition").find("option:selected").val();
	if(AdditionFlag00==-1){
		$('#additionPriceNumber').attr("disabled",true);
		$('#additionPriceNumber').val(0);
	}else{
		$('#additionPriceNumber').attr("disabled",false);
	}
});

$('#pricesAdd').on('tap',function(){
	if($('#pricesAdd').text()=='添加'){
		var commodityNocontractNo = $("#pricesContract").find("option:selected").val();
		var ExchangeNo = CacheQuoteSubscribe.getCacheContractQuote(commodityNocontractNo, "LastQuotation", "ExchangeNo");
		var CommodityNo = commodityNocontractNo.substr(0,commodityNocontractNo.length-4);
		var ContractNo = commodityNocontractNo.substr(commodityNocontractNo.length-4,commodityNocontractNo.length);
		var Num =$('#priceNumber').val();
		//0:价格条件
		var ConditionType = 0;
		//触发价
		var PriceTriggerPonit = $('#priceTriggerPonit').val();
		//触发价格方式
		var CompareType = $('#selectCompareType').val();
		//是否附加条件
		var AdditionFlag;
		var AdditionFlag00=$("#selectAddition").find("option:selected").val();
		if(AdditionFlag00==-1){
			AdditionFlag = 0;
		}else{
			AdditionFlag = 1;
		}
		//触发价格方式
		var AdditionType;
		if(AdditionFlag00==-1){
			AdditionType = 4;
		}else{
			AdditionType=AdditionFlag00;
		}
		//附加的价格
		var AdditionPrice = $('#additionPriceNumber').val();
		//买卖方向 0:买 1:卖
		var Direction = $('#priceBuyOrSellSelect').find("option:selected").val();
		//触发下单价格类型:市价-1，对价-2
		var OrderType =  $('#priceSelectOrderType').find("option:selected").val();
		var TimeTriggerPoint="";
		var AB_BuyPoint=0.0;
		var AB_SellPoint = 0.0;
		var StopLossType = 5;
		var StopLossDiff = 0.0;
		var StopWinDiff = 0.0;
		
		
			Trade.doInsertCondition(ExchangeNo,CommodityNo,
				ContractNo,Num,ConditionType,PriceTriggerPonit,
				CompareType,TimeTriggerPoint,
				AB_BuyPoint,
				AB_SellPoint,
				OrderType,Direction,
				StopLossType,
				StopLossDiff,
				StopWinDiff,
				AdditionFlag,AdditionType,AdditionPrice);
	}
	
	
	if($('#pricesAdd').text()=='修改'){
		var ConditionNo;
		var CommodityNoContractNo00;
		if($('#condition-list').children().length>0){
			$('#condition-list').children().each(function(){
				var _this = $(this);
				if(_this.hasClass('clickBg')){
					
					ConditionNo = _this.children().eq(7).text();
					CommodityNoContractNo00= _this.children().eq(0).text();
				}
			});
		}
		
		var title=$('#segmentedControlConditoion .mui-active').text();
		if(title =='价格条件'){
			var ModifyFlag = 0;
			var Num = $('#priceNumber').val();
			var ConditionType = 0;
			var PriceTriggerPonit = $('#priceTriggerPonit').val();
			var CompareType = $('#selectCompareType').val();
			var TimeTriggerPoint = "";
			var AB_BuyPoint = 0.0;
			var AB_SellPoint = 0.0;
			var OrderType = $('#priceSelectOrderType').val();
			var StopLossType = 5;
			var Direction = $('#priceBuyOrSellSelect').val();
			var StopLossDiff = 0.0;
			var StopWinDiff = 0.0;
			
			var AdditionFlag;
			var AdditionType;
			if($("#selectAddition").find("option:selected").val()==-1){
				AdditionFlag = 0;
				AdditionType=4;
			}else{
				AdditionFlag = 1;
				AdditionType=$("#selectAddition").find("option:selected").val();
			}
			var AdditionPrice=$('#additionPriceNumber').val();
			
			layer.confirm(
				'你确定要修改【'+CommodityNoContractNo00+'】条件单?',
				{
					icon: 3,
					title: '确认修改？'
				},
				function(index) { // 确认
					Trade.doUpdateModifyCondition(ConditionNo,
					ModifyFlag,
					Num,
					ConditionType,
					PriceTriggerPonit,
					CompareType,
					TimeTriggerPoint,
					AB_BuyPoint,
					AB_SellPoint,
					OrderType,
					Direction,
					StopLossType,
					StopLossDiff,
					StopWinDiff,
					AdditionFlag,
					AdditionType,
					AdditionPrice);
					layer.close(index);
				},
				function(index) { // 取消
					layer.close(index);
				}
				
			);
			
			
			
		}
		
		if(title =='时间条件'){
			var ModifyFlag = 0;
			var Num = $('#timeNumber').val();
			var ConditionType = 1;
			var PriceTriggerPonit = 0.0;
			var CompareType = 5;
			var TimeTriggerPoint = new Date().toLocaleDateString().replace('/','-').replace('/','-')+' '+$('#insertTimeInput').val();
			var AB_BuyPoint = 0.0;
			var AB_SellPoint = 0.0;
			var OrderType = $('#selectTimeDirection').val();
			var StopLossType = 5;
			var Direction = $('#selectTimeDirection').val();
			var StopLossDiff = 0.0;
			var StopWinDiff = 0.0;
			var AdditionFlag;
			var AdditionType;
			if($('#selectTimeAdditionType').val()==-1){
				AdditionFlag = 0;
				AdditionType = 4;
			}else{
				AdditionFlag = 1;
				AdditionType=$('#selectTimeAdditionType').val();
			}
			var AdditionPrice=$('#TimeAdditionPrice').val();
			
			layer.confirm(
				'你确定要修改【'+CommodityNoContractNo00+'】条件单?',
				{
					icon: 3,
					title: '确认修改？'
				},
				function(index) { // 确认
					Trade.doUpdateModifyCondition(ConditionNo,
					ModifyFlag,
					Num,
					ConditionType,
					PriceTriggerPonit,
					CompareType,
					TimeTriggerPoint,
					AB_BuyPoint,
					AB_SellPoint,
					OrderType,
					Direction,
					StopLossType,
					StopLossDiff,
					StopWinDiff,
					AdditionFlag,
					AdditionType,
					AdditionPrice);
					layer.close(index);
				},
				function(index) { // 取消
					layer.close(index);
				}
			);
			
			
			
					
		}
		
	}
});

/**
 * 查询条件单列表
 * 
 */
$('#conditioListTrade00').on('tap',function(){
	document.getElementById("condition-list").innerHTML="";
	document.getElementById("condition-notrigger-list").innerHTML="";
	//止损单查询请求
	Trade.doQryCondition(TradeConfig.username);
});

/**
 * 查询止损单列表
 */
$('#stopOrderListTrade00').on('tap',function(){
	
	document.getElementById("stopLoss-list00").innerHTML="";
	document.getElementById("stopLoss-list").innerHTML="";
	Trade.doQryStopLoss(TradeConfig.username);
});

$('#deleteCondition').on('tap',function(){
	
	if($('#condition-list').children().length>0){
		var hasClass = false;
		$('#condition-list').children().each(function(){
			var _this = $(this);
			if(_this.hasClass('clickBg')){
				hasClass = true;
			}
		});
		if(hasClass){
			deleteConditionListMsg();
		}else{
			layer.msg('请选择一条记录!');
		}
	}else{
		layer.msg('条件单列表无数据!');
	}
});

/**
 * 删除条件单 -- 未触发列表的一条记录
 */
function deleteConditionListMsg(){
	
	if($('#condition-list').children().length>0){
		var hasClass = false;
		$('#condition-list').children().each(function(){
			var _this = $(this);
			if(_this.hasClass('clickBg')){
				hasClass = true;
				var ConditionNo = _this.children().eq(7).text();
				//1：删除
				var ModifyFlag = 1;
				var Num= _this.children().eq(8).text();
				var ConditionType = _this.children().eq(9).text();
				var PriceTriggerPonit = _this.children().eq(10).text();
				var CompareType = _this.children().eq(11).text();
				var TimeTriggerPoint= _this.children().eq(12).text();
				var AB_BuyPoint = _this.children().eq(13).text();
				var AB_SellPoint = _this.children().eq(14).text();
				var OrderType = _this.children().eq(15).text();
				var StopLossType = _this.children().eq(16).text();
				var Direction = _this.children().eq(17).text();
				var StopLossDiff=_this.children().eq(18).text();
				var StopWinDiff = 0.0;
				var AdditionFlag = _this.children().eq(19).text();
				var AdditionType = _this.children().eq(20).text();
				var AdditionPrice = _this.children().eq(21).text();
				
				
				
				layer.confirm(
//					"删除:方向【"+(Direction==0?"买":"卖")+"】,价格类型:【"+(OrderType==1?"市价":"对手价")+"】,手数:【"+Num+"】",
					"是否要删除条件单？",
					{
						icon: 2,
						title: '删除'
					},
					function(index){
						Trade.doUpdateModifyCondition(ConditionNo,
							ModifyFlag,
							Num,
							ConditionType,
							PriceTriggerPonit,
							CompareType,
							TimeTriggerPoint,
							AB_BuyPoint,
							AB_SellPoint,
							OrderType,
							Direction,
							StopLossType,
							StopLossDiff,
							StopWinDiff,
							AdditionFlag,
							AdditionType,
							AdditionPrice);
						_this.remove();	
						layer.close(index);
					},
					function(index) { // 取消
						layer.close(index);
					}
					
				);
					
			}
			
		});
		
	}
}

//条件单未触发列表--暂停/启动
$('#suspendConditionList').on('tap',function(){
	if($('#condition-list').children().length>0){
		var hasClass = false;
		$('#condition-list').children().each(function(){
			var _this = $(this);
			if(_this.hasClass('clickBg')){
				hasClass = true;
			}
		});
		if(hasClass){
			suspendCondition();
		}else{
			layer.msg('请选择一条记录!');
		}
	}else{
		layer.msg('条件单列表无数据!');
	}
});
//条件单未触发列表--暂停/启动
function suspendCondition(){
	if($('#condition-list').children().length>0){
		$('#condition-list').children().each(function(){
			var _this = $(this);
			if(_this.hasClass('clickBg')){
				//条件单编号
				var ConditionNo = _this.children().eq(7).text();
				//状态：0-运行中  1-暂停
				var Status = _this.children().eq(22).text();
				//操作类型：2-暂停，3-启动
				var ModifyFlag;
				if(Status==0){
					ModifyFlag = 2;
				}
				if(Status==1){
					ModifyFlag = 3;
				}
				//订单数量
				var Num = _this.children().eq(8).text();
				//条件单类型：0-价格条件 1-时间条件  2-（双向价格）AB单
				var ConditionType =_this.children().eq(9).text();
				//触发价
				var PriceTriggerPonit = _this.children().eq(10).text();
				//价格触发方式：
				var CompareType = _this.children().eq(11).text();
				//触发时间
				var TimeTriggerPoint = _this.children().eq(12).text();
				var AB_BuyPoint = _this.children().eq(13).text();
				var AB_SellPoint = _this.children().eq(14).text();
				var OrderType = _this.children().eq(15).text();
				var StopLossType = _this.children().eq(16).text();
				var Direction = _this.children().eq(17).text();
				var StopLossDiff = _this.children().eq(18).text();
				var StopWinDiff = 0.0;
				var AdditionFlag = _this.children().eq(19).text();
				var AdditionType = _this.children().eq(20).text();
				var AdditionPrice = _this.children().eq(21).text();
				
				if(Status==0){
				
					layer.confirm(
							'是否要暂停条件单?',
							{
								icon: 3,
								title: '确认暂停？'
							},
							function(index){// 确认
								Trade.doUpdateModifyCondition(ConditionNo,
								ModifyFlag,
								Num,
								ConditionType,
								PriceTriggerPonit,
								CompareType,
								TimeTriggerPoint,
								AB_BuyPoint,
								AB_SellPoint,
								OrderType,
								Direction,
								StopLossType,
								StopLossDiff,
								StopWinDiff,
								AdditionFlag,
								AdditionType,
								AdditionPrice);
								layer.close(index);
							},
							function(index) { // 取消
								layer.close(index);
							}
						);
				}else{
					
					layer.confirm(
							'是否要启动条件单?',
							{
								icon: 3,
								title: '确认启动？'
							},
							function(index){// 确认
								Trade.doUpdateModifyCondition(ConditionNo,
								ModifyFlag,
								Num,
								ConditionType,
								PriceTriggerPonit,
								CompareType,
								TimeTriggerPoint,
								AB_BuyPoint,
								AB_SellPoint,
								OrderType,
								Direction,
								StopLossType,
								StopLossDiff,
								StopWinDiff,
								AdditionFlag,
								AdditionType,
								AdditionPrice);
								layer.close(index);
							},
							function(index) { // 取消
								layer.close(index);
							}
						);
				}
				
				
			}
		});
	}
	
}

/**
 * 时间条件--添加
 */
var _AdditionFlag_=$("#selectTimeAdditionType").find("option:selected").val();
if(_AdditionFlag_==-1){
	$('#TimeAdditionPrice').attr("disabled","disabled");
}
$("#selectTimeAdditionType").change(function(){
	
	//是否附加条件
	_AdditionFlag_=$("#selectTimeAdditionType").find("option:selected").val();
	if(_AdditionFlag_==-1){
		$('#TimeAdditionPrice').attr("disabled","disabled");
		$('#TimeAdditionPrice').val(0);
	}else{
		$('#TimeAdditionPrice').attr("disabled",false);
	}
});

//时间条件--添加
$('#timeAdd').on('tap',function(){
	
	if($('#timeAdd').text()=='添加'){
	
		var commodityNocontractNo = $("#timeContract").find("option:selected").val();
		var ExchangeNo = CacheQuoteSubscribe.getCacheContractQuote(commodityNocontractNo, "LastQuotation", "ExchangeNo");
		var CommodityNo = commodityNocontractNo.substr(0,commodityNocontractNo.length-4);
		var ContractNo = commodityNocontractNo.substr(commodityNocontractNo.length-4,commodityNocontractNo.length);
		var Num =$('#timeNumber').val();
		//1:时间条件
		var ConditionType = 1;
		//触发价
		var PriceTriggerPonit = 0.0;
		//触发价格方式
		var CompareType = 5;
		//触发时间
		var date00 = new Date();
//		var TimeTriggerPoint =date00.format("yyyy-MM-dd")+' '+$('#insertTimeInput').val();
		var TimeTriggerPoint = date00.format("yyyy-MM-dd")+' '+$('#insertTimeInput').val();
		
		var AB_BuyPoint = 0.0;
		var AB_SellPoint = 0.0;
		var OrderType = $('#selectTimeOrderTyep').find("option:selected").val();
		var Direction = $('#selectTimeDirection').find("option:selected").val();
		var StopLossType = 5;
		var StopLossDiff = 0.0;
		var StopWinDiff = 0.0;
		//是否附加条件
		var AdditionFlag;
		if(_AdditionFlag_==-1){
			AdditionFlag = 0;
		}else{
			AdditionFlag = 1;
		}
		//触发价格方式
		var AdditionType;
		if(_AdditionFlag_==-1){
			AdditionType = 4;
		}else{
			AdditionType=_AdditionFlag_;
		}
		
		//附加的价格
		var AdditionPrice = $('#TimeAdditionPrice').val();
		
		Trade.doInsertCondition(ExchangeNo,CommodityNo,
			ContractNo,Num,ConditionType,PriceTriggerPonit,
			CompareType,TimeTriggerPoint,
			AB_BuyPoint,
			AB_SellPoint,
			OrderType,Direction,
			StopLossType,
			StopLossDiff,
			StopWinDiff,
			AdditionFlag,AdditionType,AdditionPrice);
	}
	
	if($('#timeAdd').text()=='修改'){
		var commodityNocontractNo = $("#timeContract").find("option:selected").val();
//		var ConditionNo = $('#ConditionNo00').val();
		var ConditionNo =ConditionNo00_;
		var ModifyFlag = 0;
		var Num = $('#timeNumber').val();
		var ConditionType = 1;
		var PriceTriggerPonit = 0.0;
		var CompareType = 5;
		var date00 = new Date();
		var TimeTriggerPoint =date00.format("yyyy-MM-dd")+' '+$('#insertTimeInput').val();
		var AB_BuyPoint = 0.0;
		var AB_SellPoint = 0.0;
		var OrderType = $('#selectTimeOrderTyep').find("option:selected").val();
		var StopLossType = 5;
		var _Direction_ = $('#selectTimeDirection').find("option:selected").val();
		var StopLossDiff = 0.0;
		var StopWinDiff = 0.0;
		var selectTimeAdditionType = $('#selectTimeAdditionType').val();
		var AdditionFlag;
		var AdditionType;
		var AdditionPrice;
		if(selectTimeAdditionType==-1){
			AdditionFlag = 0;
			AdditionType = 4;
			AdditionPrice = 0.0;
		}else{
			AdditionFlag = 1;
			AdditionType = $("#selectTimeAdditionType").find("option:selected").val();
			AdditionPrice = $('#TimeAdditionPrice').val();
		}
		
		layer.confirm(
			'你确定要修改【'+commodityNocontractNo+'】条件单?',
			{
				icon: 3,
				title: '确认修改？'
			},
			function(index) {// 确认
				Trade.doUpdateModifyCondition(ConditionNo,
									ModifyFlag,
									Num,
									ConditionType,
									PriceTriggerPonit,
									CompareType,
									TimeTriggerPoint,
									AB_BuyPoint,
									AB_SellPoint,
									OrderType,
									_Direction_,
									StopLossType,
									StopLossDiff,
									StopWinDiff,
									AdditionFlag,
									AdditionType,
									AdditionPrice);
				layer.close(index);					
			},
			function(index) { // 取消
				layer.close(index);
			}
			
		);
		
	}
});

//条件单--修改
$('#modifiyConditionListPopover').on('tap',function(){
	$('#pricesAdd').text('修改');
	$('#timeAdd').text('修改');
	
	if($('#condition-list').children().length>0){
		var hasClass = false;
		$('#condition-list').children().each(function(){
			var _this = $(this);
			if(_this.hasClass('clickBg')){
				hasClass = true;
				//条件单类型：0-价格条件	1-时间条件
				var ConditionType =  _this.children().eq(9).text();
				if(ConditionType==0){
					$("#segmentedControlConditoion a").eq(0).addClass("mui-active");
					$("#segmentedControlConditoion a").eq(1).removeClass('mui-active');
					$("#ConditoionTitlePrices").addClass("mui-active");
					$("#ConditoionTitleTime").removeClass("mui-active");
				}
				
				if(ConditionType==1){
					$("#segmentedControlConditoion a").eq(0).removeClass("mui-active");
					$("#segmentedControlConditoion a").eq(1).addClass('mui-active');
					$("#ConditoionTitlePrices").removeClass("mui-active");
					$("#ConditoionTitleTime").addClass("mui-active");
				}
			}
			
		});
		if(hasClass){
			mui("#popoverConditoion").popover("toggle");
			modificationCondition();
		}else{
			layer.msg('请选择一条记录!');
		}
		
		
	}else{
		layer.msg('条件单列表无数据!');
	}
});

//条件单修改功能
var ConditionNo00_;
function modificationCondition(){
	if($('#condition-list').children().length>0){
		$('#condition-list').children().each(function(){
			var _this = $(this);
			if(_this.hasClass('clickBg')){
				
					var commodityNocontractNo = _this.children().eq(0).text();
					var CommodityNo = commodityNocontractNo.substr(0,commodityNocontractNo.length-4);
					var CommodityName = CacheQuoteBase.getCacheContractAttribute(CommodityNo, "CommodityName");
					ConditionNo00_ = _this.children().eq(7).text();
					var ConditionType =  _this.children().eq(9).text();
					
					//条件单类型：0-价格条件	1-时间条件
					if(ConditionType==0){
						$("#pricesContract").val(commodityNocontractNo).attr("disabled","disabled").css('color','black');
						$("#timeContract").val(commodityNocontractNo).attr("disabled","disabled").css('color','black');
						
						$('#selectCompareType').val(_this.children().eq(11).text());
						$('#priceTriggerPonit').val(fixedPriceByContract(_this.children().eq(10).text(),CommodityNo));
						
						var AdditionFlag = _this.children().eq(19).text();
						if(AdditionFlag==0){
							$('#selectAddition').val(-1);
							$('#additionPriceNumber').val(0);
						}
						
						if(AdditionFlag==1){
							$('#selectAddition').val(_this.children().eq(20).text());
							$('#additionPriceNumber').val(_this.children().eq(21).text());
						}
						
						$('#TimeAdditionPrice').val($('#priceTriggerPonit').val());
						$('#selectTimeAdditionType').val($('#selectCompareType').val());
						
						//买，卖
						$('#priceBuyOrSellSelect').val(_this.children().eq(17).text());
						//市价、对手价
						$('#priceSelectOrderType').val(_this.children().eq(15).text());
						
						$('#priceNumber').val(_this.children().eq(8).text());
						
						var LastPrice0 = CacheQuoteSubscribe.getCacheContractQuote($('#pricesContract').val(), "LastQuotation", "LastPrice");
						var CommodityNo0 = CacheQuoteSubscribe.getCacheContractQuote($('#pricesContract').val(), "LastQuotation", "CommodityNo");
						$('#jgtjLastPrice').text(fixedPriceByContract(LastPrice0,CommodityNo0));
						
						
						var LastPrice1 = CacheQuoteSubscribe.getCacheContractQuote($('#timeContract').val(), "LastQuotation", "LastPrice");
						var CommodityNo1 = CacheQuoteSubscribe.getCacheContractQuote($('#timeContract').val(), "LastQuotation", "CommodityNo");
						$('#ConditionNo00').val(fixedPriceByContract(LastPrice1,CommodityNo1));
						$('#_ConditionNo000_').text(fixedPriceByContract(LastPrice1,CommodityNo1));
						
						$('#TimeAdditionPrice').attr('disabled',false);
						
					}
					
					if(ConditionType==1){
						$("#pricesContract").val(commodityNocontractNo).attr("disabled","disabled").css('color','black');
						$("#timeContract").val(commodityNocontractNo).attr("disabled","disabled").css('color','black');
						var time = _this.children().eq(12).text();
						$('#insertTimeInput').val(time.substr(time.length-8,time.length));
						$('#_ConditionNo000_').text(_this.children().eq(23).text());
						$('#priceTriggerPonit').val($('#_ConditionNo000_').text());
						
						var AdditionFlag = _this.children().eq(19).text();
						var AdditionType = _this.children().eq(20).text();
						var AdditionPrice = _this.children().eq(21).text();
						if(AdditionFlag==0){
							$('#selectTimeAdditionType').val(-1);
							$('#TimeAdditionPrice').val(0);
						}
						
						if(AdditionFlag==1){
							$('#selectTimeAdditionType').val(AdditionType);
							$('#TimeAdditionPrice').val(fixedPriceByContract(AdditionPrice,CommodityNo));
						}
						
						$('#timeNumber').val(_this.children().eq(8).text());
						
						$('#selectTimeDirection').val(_this.children().eq(17).text());
						$('#selectTimeOrderTyep').val(_this.children().eq(15).text());
						
						$('#ConditionNo00').val(_this.children().eq(7).text());
						
						if($('#selectTimeAdditionType').find("option:selected").val()!=-1){
							$('#TimeAdditionPrice').attr('disabled',false);
						}
						
						var LastPrice0 = CacheQuoteSubscribe.getCacheContractQuote($('#pricesContract').val(), "LastQuotation", "LastPrice");
						var CommodityNo0 = CacheQuoteSubscribe.getCacheContractQuote($('#pricesContract').val(), "LastQuotation", "CommodityNo");
						$('#jgtjLastPrice').text(fixedPriceByContract(LastPrice0,CommodityNo0));
						
						
						var LastPrice1 = CacheQuoteSubscribe.getCacheContractQuote($('#timeContract').val(), "LastQuotation", "LastPrice");
						var CommodityNo1 = CacheQuoteSubscribe.getCacheContractQuote($('#timeContract').val(), "LastQuotation", "CommodityNo");
						$('#ConditionNo00').val(fixedPriceByContract(LastPrice1,CommodityNo1));
						$('#_ConditionNo000_').text(fixedPriceByContract(LastPrice1,CommodityNo1));
						
					}
				
			}
		});
		
	}
}


$('#conditionButton').on('tap',function(){
	
	$("#pricesContract").attr("disabled",false);
	$("#timeContract").attr("disabled",false);
	$('#pricesAdd').text('添加');
	$('#timeAdd').text('添加');
	
	
	//获取最新价格
	var LastPrice = CacheQuoteSubscribe.getCacheContractQuote($('#pricesContract').val(), "LastQuotation", "LastPrice");
	var CommodityNo = CacheQuoteSubscribe.getCacheContractQuote($('#pricesContract').val(), "LastQuotation", "CommodityNo");
	//价格条件
	$('#selectCompareType').val(0);
	$('#priceTriggerPonit').val(fixedPriceByContract(LastPrice,CommodityNo));
	$('#selectAddition').val(-1);
	$('#additionPriceNumber').val(0);
	$('#additionPriceNumber').attr("disabled",true);
	$('#priceBuyOrSellSelect').val(0);
	$('#priceSelectOrderType').val(1);
	$('#priceNumber').val(1);
	$('#jgtjLastPrice').text(fixedPriceByContract(LastPrice,CommodityNo));
	
	//时间条件
	$('#insertTimeInput').val('');
//	if($('#insertTimeInput').val()==0){
//		$('#insertTimeInput').val('');
//		
//	}
	$('#selectTimeAdditionType').val(-1);
	$('#TimeAdditionPrice').val(0);
	$('#selectTimeDirection').val(0);
	$('#selectTimeOrderTyep').val(1);
	$('#timeNumber').val(1);
	$('#_ConditionNo000_').text(fixedPriceByContract(LastPrice,CommodityNo));
	
	
});


$('#pricesContract').change(function(){
	var s = $(this).children('option:selected').val();
	var LastPrice = CacheQuoteSubscribe.getCacheContractQuote(s, "LastQuotation", "LastPrice");
	var CommodityNo = CacheQuoteSubscribe.getCacheContractQuote(s, "LastQuotation", "CommodityNo");
	$('#priceTriggerPonit').val(fixedPriceByContract(LastPrice,CommodityNo));
	$('#jgtjLastPrice').text(fixedPriceByContract(LastPrice,CommodityNo));
});

$('#timeContract').change(function(){
	var s = $(this).children('option:selected').val();
	var LastPrice = CacheQuoteSubscribe.getCacheContractQuote(s, "LastQuotation", "LastPrice");
	var CommodityNo = CacheQuoteSubscribe.getCacheContractQuote(s, "LastQuotation", "CommodityNo");
	$('#_ConditionNo000_').text(fixedPriceByContract(LastPrice,CommodityNo));
	
	
});

//selectTimeAdditionType
$('#selectTimeAdditionType').change(function(){
	
	if($('#selectTimeAdditionType').find("option:selected").val()!=-1){
		$('#TimeAdditionPrice').val($('#_ConditionNo000_').text());
	}
});




