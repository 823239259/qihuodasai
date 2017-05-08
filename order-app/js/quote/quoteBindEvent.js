var data = {
	"list": ["1", "1", "1", "1", "1", "1", "1", "1", "1"]
};

$(function() {
	alertProtypeBind(data);
})
window.onload = function() {
	document.getElementById("confirmStopLossBotton").addEventListener("tap", function() {
		$("#chioceStopLoss").css("display", "none");
		var text = $("#buttonListLiStopLoss li.on").text();
		console.log(text)
		var id = Number($("#chioceStopLoss").attr("data-id"));;
		console.log(id);
		switch(id) {
			case 1:
				$("#closeProfitTable").text(text);
				break;
			case 2:
				$("#closeLossTable").text(text);
				break;
			case 3:
				$("#closeLossTableSeting").text(text);
			case 4:
				$("#closeProfitTableSeting").text(text);
				break;
			default:
		};
	});
	document.getElementById("closeProfitTable").addEventListener("tap", function() {
		$("#chioceStopLoss").css("display", "block").attr("data-id", "1");
		var buttonListLiStopLoss = document.getElementById("buttonListLiStopLoss");
		var height = buttonListLiStopLoss.offsetHeight;
		$("#alertContent2").css({ "height": height + 75 + "px", "margin-top": -(height + 75) / 2 + "px" });
	});
	document.getElementById("closeLossTable").addEventListener("tap", function() {
		$("#chioceStopLoss").css("display", "block").attr("data-id", "2");
		var buttonListLiStopLoss = document.getElementById("buttonListLiStopLoss");
		var height = buttonListLiStopLoss.offsetHeight;
		$("#alertContent2").css({ "height": height + 75 + "px", "margin-top": -(height + 75) / 2 + "px" });
	});
	document.getElementById("closeLossTableSeting").addEventListener("tap", function() {
		$("#chioceStopLoss").css("display", "block").attr("data-id", "3");
		var buttonListLiStopLoss = document.getElementById("buttonListLiStopLoss");
		var height = buttonListLiStopLoss.offsetHeight;
		$("#alertContent2").css({ "height": height + 75 + "px", "margin-top": -(height + 75) / 2 + "px" });
	});
	document.getElementById("closeProfitTableSeting").addEventListener("tap", function() {
		$("#chioceStopLoss").css("display", "block").attr("data-id", "4");
		var buttonListLiStopLoss = document.getElementById("buttonListLiStopLoss");
		var height = buttonListLiStopLoss.offsetHeight;
		$("#alertContent2").css({ "height": height + 75 + "px", "margin-top": -(height + 75) / 2 + "px" });
	});
	$("#flashSeting .chioce-button").on("tap", function() {
		$(this).addClass('on'); // 设置被点击元素为黄色
		$(this).siblings(".chioce-button").removeClass('on'); // 去除所有同胞元素的黄色样式
	});
	$("#placeOrder .chioce-button").on("tap", function() {
		$(this).addClass('on'); // 设置被点击元素为黄色
		$(this).siblings(".chioce-button").removeClass('on'); // 去除所有同胞元素的黄色样式
	});
	var checkboxButtonClickNum = 0;
	document.getElementById("checkboxButton").addEventListener("click", function() {
		if(checkboxButtonClickNum % 2 == 0) {
			console.log("544")
			$("#checkboxButton").css({ "background": "url('../../img/checkboxBg.png') no-repeat center", "background-size": "15px 15px" });
		} else {
			$("#checkboxButton").css({ "background": "", "background-size": "15px 15px" });
		}
		checkboxButtonClickNum++;
	});
}