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
	/*
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
	*/
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
/*
document.getElementById("novice").addEventListener("tap",function(){
	mui.openWindow({
					"url": "novice.html",
					"id": "novice"
				})
})*/
var imgId=[];
			mui.app_request('banner/list',{},
				function(result){
					var bannerLen = result.data.bannerList == null ? 0 : result.data.bannerList.length;
					if (bannerLen>0){ 
						var  banner_content='';
						var indicator_content='';
						for (var i=0;i<bannerLen;i++){ 
							
							if (i==0){
								banner_content +=addBanner(result.data.bannerList[bannerLen-1],true);
								indicator_content+='<div class="mui-indicator mui-active"></div>';
							}else
							{
								indicator_content+='<div class="mui-indicator"></div>';
							}  
							 
							banner_content +=addBanner(result.data.bannerList[i]); 
							
							if (i==(bannerLen-1)){ 
								banner_content +=addBanner(result.data.bannerList[0],true); 
							}
							
						} 
						//alert(banner_content);
						document.getElementById('bannerloop').innerHTML=banner_content;
						document.getElementById('indicatorid').innerHTML=indicator_content; 
					}
					var slider = mui("#slider");  
					slider.slider({interval: 3000});
					
				},
				function(){
					
				}
			);
						//新增banner 显示
			var timeNum=0;
			function addBanner(banner,firstend){
				var _html='';
				if (firstend){
					 _html='<div class="mui-slider-item mui-slider-item-duplicate" >';
				}
				else{ 
					 _html='<div class="mui-slider-item">';
				}
				_html +='<a class="id" href="javascript:void(0);" id="'+banner.newId+'" data-id="'+timeNum+'"><img src="'+vs.constants.base_images_url+banner.imgPath+'" title=""/>';
				_html +='</a></div>';
				imgId.push(banner.newId);
				timeNum++;
				return _html;
			}
			//监听banner点击事件
			var clickNum=0;
		mui("#bannerloop").on('tap','a',function(){		 
			//获取id
			var nid = this.getAttribute("id");
			if(clickNum<=imgId.length-1){
				var clickId = $(this).attr('data-id');
				nid=imgId[clickId];
				clickNum++;
			}
			var str="http";
			console.log(nid);
			if(nid == "" || nid == null || nid.length == 0 ){
			}else if(nid.indexOf(str)==-1){
				mui.openWindow({url:"../news/news.html",id:nid,extras:{nid:nid}});
			}else{
				mui.openWindow({url:"../news/active.html",id:"active",extras:{nid:nid}});
			}
		});
