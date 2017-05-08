mui.init();
//初始化单页view
var viewApi = mui('#app').view({
	defaultPage: '#home'
});
//初始化单页的区域滚动
mui('.mui-scroll-wrapper').scroll();
//分享操作
var items = document.getElementById("candlestickNavContainer").getElementsByClassName("mui-control-item");
mui("#candlestickNavContainer").on("tap", "a", function() {
	var text = this.innerHTML;
	if(text.indexOf("日线") > -1) {
		document.getElementById("candlestickNav").style.display = "block";
	} else {
		document.getElementById("candlestickNav").style.display = "none";
	}
	timeChart.resize();
	volumeChart.resize();
	lightChart.resize();

});
document.getElementById("choiceProductButton").addEventListener("tap", function() {
	document.getElementById("choiceProductList").style.display = "block";
});
(function($) {
	var deceleration = mui.os.ios ? 0.003 : 0.0009;
	$('.mui-scroll-wrapper').scroll({
		bounce: false,
		indicators: true, //是否显示滚动条
		deceleration: deceleration
	});
})(mui);
mui(document.querySelector('#positionList .mui-scroll')).pullToRefresh({
	up: {
		callback: function() {
			var self = this;
			var ul = self.element.querySelector('#positionListOrder');
			//					setTimeout(function(){
			//						ajaxQueryPageInfo(self);	
			//					},500)
		}
	},
	down: {
		callback: function() {
			var self = this;
			var ul = self.element.querySelector('#positionListOrder');
			//					setTimeout(function(){
			//						ajaxQueryPageInfo(self);	
			//					},500)
		}
	}
});

function ajaxQueryPageInfo() {

}
var view = viewApi.view;
(function($) {
	//处理view的后退与webview后退
	var oldBack = $.back;
	$.back = function() {
		if(viewApi.canBack()) { //如果view可以后退，则执行view的后退
			viewApi.back();
		} else { //执行webview后退
			oldBack();
		}
	};
	//监听页面切换事件方案1,通过view元素监听所有页面切换事件，目前提供pageBeforeShow|pageShow|pageBeforeBack|pageBack四种事件(before事件为动画开始前触发)
	//第一个参数为事件名称，第二个参数为事件回调，其中e.detail.page为当前页面的html对象
	view.addEventListener('pageBeforeShow', function(e) {
		//				console.log(e.detail.page.id + ' beforeShow');
	});
	view.addEventListener('pageShow', function(e) {
		//				console.log(e.detail.page.id + ' show');
	});
	view.addEventListener('pageBeforeBack', function(e) {
		//				console.log(e.detail.page.id + ' beforeBack');
	});
	view.addEventListener('pageBack', function(e) {
		//				console.log(e.detail.page.id + ' back');
	});
})(mui);
