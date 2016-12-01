$(function() {
	var url = document.referrer;
	if(url.indexOf("simulateOperate/operate") >= 0 ){
		if(username == null && password == null){
			$("#signLogin").css("display","block");
			$("#div_Mask").show();
			var popupHeight = $("#signLogin").outerHeight()/2;
			var popupWidth = $("#signLogin").outerWidth()/2;    
			$("#signLogin").css({
				top:"50%",
				left:"50%",
				marginTop: -(popupHeight+15),
				marginLeft: -(popupWidth)
			});
			$(".signLogin_firm").css("display","none");
			$(".signLogin_simulation").css("display","block");
			$("#signLogin .p1 span.signLogin_span").css("color","#ffb319");
			$("#signLogin .p1 span.on").css("color","#333");
			tradeWebSocketIsMock = 1;
		}
	}
})

$(document).ready(
	function () {
	    $("#show_user_info .quotation_xl").click(function (e) {
	        $("#show_user_info .show_user_ul").toggle();
	        /*阻止冒泡事件*/
	        e = window.event || e;
	        if (e.stopPropagation) {
	            e.stopPropagation();
	        } else {
	            e.cancelBubble = true;
	        }
	    });
	    $("#show_user_info .show_user_ul li").click(function (e) {
	        $("#show_user_info .show_user_ul").hide();
	        /*阻止冒泡事件*/
	        /*e = window.event || e;
	        if (e.stopPropagation) {
	            e.stopPropagation();
	        } else {
	            e.cancelBubble = true;
	        }*/
	    });
	    $(document).click(function () {
	        $("#show_user_info .show_user_ul").hide();
	    });
	}
);
		

$(function() {
		// 持仓 委托 挂单 tab 切换
		$(".quotation_detailed_lis a").click(function() {
			var index = $(this);
            index.addClass('on');
            index.siblings().removeClass('on');
            $(".quotation_detailed_title").hide();
            $(".quotation_detailed_title").eq(index.index()).show();
		});
		// 股市tab切换
		$(".futuresList ul").click(function() {
			$(".futuresList ul").removeClass('on');
			$(this).addClass('on');
		});
		/* 买入卖出 */
		$(".xdfs_sj").click(function() {
			$(".money_sj").show();
			$(".money_js").hide();
		})
		$(".xdfs_xj").click(function() {
			$(".money_sj").hide();
			$(".money_js").show();
		})
		/* 减数量 */
		$(".money_sjsl .del").click(function() {
			var  num =  $("#entrust_number").val();
			if(num <=2){
				$("#entrust_number").val(1);
			}else{
				num--;
				$("#entrust_number").val(num);
			}
		});
		/* 加数量 */
		$(".money_sjsl .add").click(function() {
			var  num =  $("#entrust_number").val();
			if(num >999999999){
				alert("已达到最大数量限度！");
			}else{
				num++;
				$("#entrust_number").val(num);
			}
		});
		/* 光标离开输入框时 */
		$("#entrust_number").blur(function() {
			var  num =  $("#entrust_number").val();
			if(num<1){
				$("#entrust_number").val(1);
			}else if((/^(\+|-)?\d+$/.test(num))&&num>0){
				return true;
			}else{  
		        alert("数量中请输入正整数！");  
		        $("#entrust_number").val(1);
		        return false;  
			}
		});
		/* 减少金额 */
		$(".money_js .del").click(function() {
			var  num =  $("#money_number").val();
			if(num <=2){
				$("#money_number").val(1);
			}else{
				var contractCode  =  $("#select_commodity").val();
				var loca = localCacheCommodity[contractCode];
				var addNum = 1;
				var doSize = 0;
				if(loca != undefined){
					addNum = loca.MiniTikeSize;
					doSize = loca.DotSize;
				}
				num = num - addNum;
				$("#money_number").val(parseFloat(num).toFixed(doSize));
				$("#float_buy").text(parseFloat(num).toFixed(doSize));
				$("#float_sell").text(parseFloat(num).toFixed(doSize));
			}
		});
		/* 增加金额 */
		$(".money_js .add").click(function() {
			var  num =  $("#money_number").val();
			if(num >999999999){
				alert("已达到最大金额限度！");
			}else{
				var contractCode  =  $("#select_commodity").val();
				var loca = localCacheCommodity[contractCode];
				var addNum = 1;
				var doSize = 0;
				if(loca != undefined){
					doSize = loca.DotSize;
					addNum = parseFloat(loca.MiniTikeSize);
				}
				$("#money_number").val(parseFloat(parseFloat(num) + addNum*1).toFixed(doSize));
				$("#float_buy").text(parseFloat(parseFloat(num) + addNum*1).toFixed(doSize));
				$("#float_sell").text(parseFloat(parseFloat(num) + addNum*1).toFixed(doSize));
			}
		});
		/* 光标离开输入框时 */
		$("#money_number").blur(function() {
			var  num =  $("#money_number").val();
			if(num<1){
				$("#money_number").val(1);
			}
		});
		/*是否登录*/
		function weidenglu(){
			$("#weidenglu").css("display","block");
			$("#div_Mask").show();
			var popupHeight = $(".tck01").outerHeight()/2;
			var popupWidth = $(".tck01").outerWidth()/2;    
			$(".tck01").css({
				top:"50%",
				left:"50%",
				marginTop: -(popupHeight+15),
				marginLeft: -(popupWidth)
			});
		}
		/*买入*/
		function mairu(){
			$("#mairu").css("display","block");
			$("#div_Mask").show();
			var popupHeight = $(".tck01").outerHeight()/2;
			var popupWidth = $(".tck01").outerWidth()/2;    
			$(".tck01").css({
				top:"50%",
				left:"50%",
				marginTop: -(popupHeight+15),
				marginLeft: -(popupWidth)
			});
		}
		/*卖出*/
		function maichu(){
			$("#maichu").css("display","block");
			$("#div_Mask").show();
			var popupHeight = $(".tck01").outerHeight()/2;
			var popupWidth = $(".tck01").outerWidth()/2;    
			$(".tck01").css({
				top:"50%",
				left:"50%",
				marginTop: -(popupHeight+15),
				marginLeft: -(popupWidth)
			});
		}
		/*撤单*/
		function revoke(){
			$("#change").css("display","block");
			$("#div_Mask").show();
			var popupHeight = $(".tck01").outerHeight()/2;
			var popupWidth = $(".tck01").outerWidth()/2;    
			$(".tck01").css({
				top:"50%",
				left:"50%",
				marginTop: -(popupHeight+15),
				marginLeft: -(popupWidth)
			});
		}
		/*改单*/
		function change(){
			$("#change").css("display","block");
			$("#div_Mask").show();
			var popupHeight = $(".tck01").outerHeight()/2;
			var popupWidth = $(".tck01").outerWidth()/2;    
			$(".tck01").css({
				top:"50%",
				left:"50%",
				marginTop: -(popupHeight+15),
				marginLeft: -(popupWidth)
			});
		}
		
		function sign(){
			$("#sign_login").css("display","block");
			$("#div_Mask").show();
			var popupHeight = $(".tck01").outerHeight()/2;
			var popupWidth = $(".tck01").outerWidth()/2;    
			$(".tck01").css({
				top:"50%",
				left:"50%",
				marginTop: -(popupHeight+15),
				marginLeft: -(popupWidth)
			});
		}
		/*找回密码*/
		/*$(".backPassword").click(function() {
			$("#back_passwork").css("display","block");
			$("#div_Mask").show();
			var popupHeight = $(".tck01").outerHeight()/2;
			var popupWidth = $(".tck01").outerWidth()/2;    
			$(".tck01").css({
				top:"50%",
				left:"50%",
				marginTop: -(popupHeight+15),
				marginLeft: -(popupWidth)
			});
		});*/
		
		$("#sign_login").click(function() {
			$("#signLogin").css("display","block");
			$("#div_Mask").show();
			var popupHeight = $("#signLogin").outerHeight()/2;
			var popupWidth = $("#signLogin").outerWidth()/2;    
			$("#signLogin").css({
				top:"50%",
				left:"50%",
				marginTop: -(popupHeight+15),
				marginLeft: -(popupWidth)
			});
		});
		$(".signLogin_close").click(function() {
			$("#div_Mask").css("display","none");
			$("#signLogin").css("display","none");
		})
		/*隐藏*/
		$(".close").click(function() {
			$(".tck01").css("display","none");
			$("#div_Mask").css("display","none");
		})
		
		$("#signLogin .signLogin_span").click(function(){
	        var _this = $(this);
	        $("#signLogin .signLogin_span").css("color","#333").eq(_this.index()).css("color","#ffb319");
	        $("#signLogin .signLogin_mode .signLogin_all").hide().eq(_this.index()).show();
        });
		/*盘口*/
		$("#handicap").click(function(){
			$("#handicapDetails").css("display","block");
			$("#div_Mask").show();
			var popupHeight = $("#handicapDetails").outerHeight()/2;
			var popupWidth = $("#handicapDetails").outerWidth()/2;    
			$("#handicapDetails").css({
				top:"50%",
				left:"50%",
				marginTop: -(popupHeight+15),
				marginLeft: -(popupWidth)
			});
		});
/* 弹出层结束 */
		/*滚动条*/
		$(function(){
			$("#gdt").panel({iWheelStep:32});
		});
		$(function(){
			$(".gdt1").panel({iWheelStep:32});
		});
		/*商品隐藏显示*/
		$("#content .stretch").click(function(){
		    $("#content .stretch").css("display","none");
		    $("#content .stretch1").css("display","block");
		    $("#content").animate({marginLeft:'205px'});
		});
		$("#content .stretch1").click(function(){
		    $("#content .stretch1").css("display","none");
		    $("#content .stretch").css("display","block");
		    $("#content").animate({marginLeft:'0px'});
		});
		$(".carbon_time").click(function() {
			var _this =$(this);
			$(".carbon_time").css("color","#999");
			_this.css("color","#ffb319");
		});
		/*底部自适应高度*/
		var windowHeight = document.body.scrollHeight;
		var height = windowHeight-799;
		if(windowHeight >836){
			$("#footer").css({
				height: height+"px",
				lineHeight: height+"px"
			});
		}
		/*window.onresize = function () {
			var windowHeight = document.body.scrollHeight;
	 		if(windowHeight >836){
				$("#footer").css({
					height: height+"px",
					lineHeight: height+"px"
				});
			}
		};*/
		$(window).resize(function(){
	        var windowHeight = $("body").height();
	        var height = windowHeight-799;
			if(windowHeight >836){
				$("#footer").css({
					height: height+"px",
					lineHeight: height+"px"
				});
			}
	    });
});
/*渐变涨*/
function rise(cls){
	$("."+cls+"").addClass("rise");
   /* $("."+cls+"").animate({
        opacity: '0'
    },500);*/
    setTimeout(function() {
        //$("."+cls+"").animate({ opacity: '1' },0);
        $("."+cls+"").removeClass("rise");
    },1000);
}
/*渐变跌*/
function fall(cls){
	$("."+cls+"").addClass("fall");
    /*$("."+cls+"").animate({
        opacity: '0'
    },500);*/
    setTimeout(function() {
        //$("."+cls+"").animate({ opacity: '1' },0);
        $("."+cls+"").removeClass("fall");
    },1000);
}