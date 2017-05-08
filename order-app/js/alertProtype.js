function alertProtypeBind(data) {
	$("body").append("<script id='alertProtype' type='text/html'>" +
		"<div class='mui-backdrop chioce' id='chioceStopLoss'>" +
		"<div id='alertContent2' class='alertContent'>" +
		"<div  class='alert-content-inside'>" +
		"<span id='closeCirlcleStopLoss'  class='mui-icon mui-icon-closeempty popupButton'></span>" +
		"<ul id='buttonListLiStopLoss' class='buttonListLi'>" +
		"{{#  layui.each(d.list, function(index, item){ }}" +
		"<li>" +
		" {{item}}" +
		"</li>" +
		"{{#  }); }}" +
		"</ul>" +
		"<a class='mui-col-xs-6 mui-col-sm-6 mui-pull-left'><button id='confirmStopLossBotton' class='confirmBottonProfit'>确认</button></a>" +
		"<a class='mui-col-xs-6 mui-col-sm-6 mui-pull-left'><button id='cancelStopLossBotton' class='cancelBottonProfit'>取消</button></a>" +
		"</div>" +
		"</div>" +
		"</div>" +
		"</script>");
	var getTpl = alertProtype.innerHTML;
	layui.use('laytpl', function(laytpl) {
		laytpl(getTpl).render(data, function(html) {
			test.innerHTML = html;
			$("#buttonListLiStopLoss li").eq(0).addClass("on");
			document.getElementById("closeCirlcleStopLoss").addEventListener("tap", function() {
				$("#chioceStopLoss").css("display", "none");
			});
			document.getElementById("cancelStopLossBotton").addEventListener("tap", function() {
				$("#chioceStopLoss").css("display", "none");
			});
			mui("#buttonListLiStopLoss").on("tap", "li", function() {
				var _li = document.getElementById("buttonListLiStopLoss").getElementsByTagName("li");
				mui.each(_li, function(i, item) {
					if(item.getAttribute("class") == "on") {
						item.setAttribute("class", "");
					}
				});
				this.setAttribute("class", "on");
			});
		});
	})
}