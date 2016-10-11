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
				num--;
				$("#money_number").val(num);
			}
		});
		/* 增加金额 */
		$(".money_js .add").click(function() {
			var  num =  $("#money_number").val();
			if(num >999999999){
				alert("已达到最大金额限度！");
			}else{
				num++;
				$("#money_number").val(num);
			}
		});
		/* 光标离开输入框时 */
		$("#money_number").blur(function() {
			var  num =  $("#money_number").val();
			if(num<1){
				$("#money_number").val(1);
			}
		});
		/*交易ul li  odd even odd  li:nth-of-type(even)*/
		$(".quotation_detailed .quotation_detailed_title .tab_content:even").addClass("even");
		$(".quotation_detailed .quotation_detailed_title .tab_content").click(function() {
			var _this = $(this);
			$(".quotation_detailed .quotation_detailed_title .tab_content").removeClass("on");
			_this.addClass("on");
		});
/* 弹出层 开始 */
		$(function() {
			$("#trade_login").click(function() {
				weidenglu();
				//mairu();
				//revoke();
				//maichu();
			});
		});
		/*是否登录*/
		function weidenglu(){
			$("#weidenglu").css("display","block");
			$("#div_Mask").show();
			var windowWidth = document.documentElement.clientWidth;   
			var windowHeight = document.documentElement.clientHeight;   
			var popupHeight = $(".tck01").height();   
			var popupWidth = $(".tck01").width();    
			$(".tck01").css({     
			 "top": (windowHeight-popupHeight)/2+$(document).scrollTop(),   
			 "left": (windowWidth-popupWidth)/2   
			});
		}
		/*买入*/
		function mairu(){
			$("#mairu").css("display","block");
			$("#div_Mask").show();
			var windowWidth = document.documentElement.clientWidth;   
			var windowHeight = document.documentElement.clientHeight;   
			var popupHeight = $(".tck01").height();   
			var popupWidth = $(".tck01").width();    
			$(".tck01").css({     
			 "top": (windowHeight-popupHeight)/2+$(document).scrollTop(),   
			 "left": (windowWidth-popupWidth)/2   
			});
		}
		/*卖出*/
		function maichu(){
			$("#maichu").css("display","block");
			$("#div_Mask").show();
			var windowWidth = document.documentElement.clientWidth;   
			var windowHeight = document.documentElement.clientHeight;   
			var popupHeight = $(".tck01").height();   
			var popupWidth = $(".tck01").width();    
			$(".tck01").css({     
			 "top": (windowHeight-popupHeight)/2+$(document).scrollTop(),   
			 "left": (windowWidth-popupWidth)/2   
			});
		}
		/*撤单*/
		function revoke(){
			$("#revoke").css("display","block");
			$("#div_Mask").show();
			var windowWidth = document.documentElement.clientWidth;   
			var windowHeight = document.documentElement.clientHeight;   
			var popupHeight = $(".tck01").height();   
			var popupWidth = $(".tck01").width();    
			$(".tck01").css({     
			 "top": (windowHeight-popupHeight)/2+$(document).scrollTop(),   
			 "left": (windowWidth-popupWidth)/2   
			});
		}
		/*隐藏*/
		$(".close").click(function() {
			$(".tck01").css("display","none");
			$("#div_Mask").css("display","none");
		})
/* 弹出层结束 */
});