var username = "Q517029969";
var password = $.base64.encode("123456");
socket.onopen = function(){
	Trade.doLogin(username,password);
}
socket.onclose = function(){
	alert("链接关闭");
}
socket.onmessage = function(evt){
	
}
$(".buy").click(function(){
	var content = "你确定要提交订单吗?";
	var isFlag = alertProtype(content,"确认下单?",Btn.confirmedAndCancle(),buy,null,$(this));
});
function buy(param){
	var $this = param;
	var lastPrice = $("#freshPrices").text();
	if(lastPrice <= 0 || lastPrice == undefined || lastPrice == null || isNaN(lastPrice)){
		alertProtype("交易错误","提示",Btn.confirmed());
		return;
	}
	var exchangeNo = $("#exchangeNo").val();
	var commodityNo = $("#commodityNo").val();
	var contractNo = $("#contractNo").val();
	var contractSize = $("#contractSize").val();
	var orderNum = $("#orderNum").val();
	var drection = $this.attr("data-tion-buy");
	var limitPrice = doGetMarketPrice(lastPrice,contractSize,drection);
	var priceType = 0;
	var tradeParam = createSellingParam(exchangeNo,commodityNo,contractNo,orderNum,drection,priceType,limitPrice,0,doGetOrderRef());
	var param = new Array();
	param[0] = tradeParam;
	selling(param);
}
