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
					addNum = loca.ContractSize;
					doSize = loca.DotSize;
				}
				for(var i  = 0 ; i < addNum ; i ++){
					num--;
				}
				$("#money_number").val(parseFloat(num).toFixed(doSize));
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
					addNum = parseFloat(loca.ContractSize).toFixed(doSize);
				}
				for(var i  = 0 ; i < addNum ; i ++){
					num++;
				}
				$("#money_number").val(parseFloat(num).toFixed(doSize));
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
		
		/*隐藏*/
		$(".close").click(function() {
			$(".tck01").css("display","none");
			$("#div_Mask").css("display","none");
		})
/* 弹出层结束 */
		/*滚动条*/
		$(function(){
			$("#gdt").panel({iWheelStep:32});
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
});
/*渐变涨*/
function rise(cls){
	$("."+cls+"").addClass("rise");
    $("."+cls+"").animate({
        opacity: '0'
    },500);
    setTimeout(function() {
        $("."+cls+"").animate({ opacity: '1' },0);
        $("."+cls+"").removeClass("rise");
    },500);
}
/*渐变跌*/
function fall(cls){
	$("."+cls+"").addClass("fall");
    $("."+cls+"").animate({
        opacity: '0'
    },500);
    setTimeout(function() {
        $("."+cls+"").animate({ opacity: '1' },0);
        $("."+cls+"").removeClass("fall");
    },500);
}