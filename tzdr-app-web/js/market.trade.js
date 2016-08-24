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
$(function(){
	$(".buy").click(function(){
		var $this = $(this);
		var lastPrice = $("#freshPrices").val();
		var exchangeNo = $("#exchangeNo").val();
		var commodityNo = $("#commodityNo").val();
		var contractNo = $("#contractNo").val();
		var contractSize = $("#contractSize").val();
		var orderNum = $("#orderNum").val();
		var drection = $this.attr("data-tion-buy");
		var limitPrice = doGetMarketPrice(lastPrice,contractSize,direction);
		var priceType = 0;
		var tradeParam = createSellingParam(exchangeNo,commodityNo,contractNo,orderNum,drection,priceType,limitPrice,0,doGetOrderRef());
		selling(tradeParam);
		
	});
	localStorage.setItem("name","hedaoqing");
});
