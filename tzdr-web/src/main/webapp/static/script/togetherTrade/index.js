$(document).ready(function(e) {
	//顶部菜单位置
	$('.navlist li a').removeClass('on');
	$("#togetherli").addClass("on");
	
	var  minMoney = Number($("#minMoney").val()); // 最小总操盘资金
	var  maxMoney = Number($("#maxMoney").val()); // 最大总操盘资金
	
	$("#otherRecommendMoney").val($("#otherRecommendMoney").attr("focucmsg"));
	/**
	 * 操盘者输入框只能输入数字
	 */
	$("#otherRecommendMoney").keypress(T.numKeyPress).keyup(function() {
		
	});
	
	$("#otherRecommendMoney").focus(function(){
		if($("#otherRecommendMoney").val() == $("#otherRecommendMoney").attr("focucmsg"))
			{
				$("#otherRecommendMoney").val('');
				$("#otherRecommendMoney").val('').css("color","#6b6969");
			}
		$('#margin ul li.on').removeClass('on');
		$(this).parent().parent().addClass('on');
	
	});
	$("#otherRecommendMoney").blur(function(){
		
		var otherRecommendMoney =$.trim($("#otherRecommendMoney").val()).replace(/\,/g,"");
		if(otherRecommendMoney<minMoney){
			otherRecommendMoney = minMoney;
			showMsgDialog("提示","总操盘资金最低"+minMoney+"元,最高"+maxMoney+"元");
		}else if(otherRecommendMoney>maxMoney){
			otherRecommendMoney = maxMoney;
			showMsgDialog("提示","总操盘资金最低"+minMoney+"元,最高"+maxMoney+"元");
		}
		$("#recommendMoney").val(otherRecommendMoney);
		
		$("#otherRecommendMoney").val($.formatMoney(otherRecommendMoney));
		var li = $("#otherRecommendMoney").parent().parent();
		li.attr("data",otherRecommendMoney);
		$('#bailMoney_div ul li').each(function(){
			var lever = Number($(this).attr("data"))+1;
			var pHtml = $.formatMoney(Math.round(otherRecommendMoney/lever))+"元";
			$(this).find("p").html(pHtml);
		});
		getDeductData(getTotalMoney(),getLever(),getUseDay());
		li.bind("click",function(){
			$('#margin ul li.on').removeClass('on');
			$(this).addClass('on');
			var money = $(this).attr("data");
			$("#recommendMoney").val(money);
			$('#bailMoney_div ul li').each(function(){
				var lever = Number($(this).attr("data"))+1;
				var pHtml = $.formatMoney(Math.round(money/lever))+"元";
				$(this).find("p").html(pHtml);
			});
			getDeductData(getTotalMoney(),getLever(),getUseDay());
		});
		
		
	});
	
	
	
	/**
	 * 默认进入时 当前选中的操盘者金额
	 */
	$('#margin ul li').each(function(){
		if ($(this).hasClass("on")){
			$("#recommendMoney").val(Number($(this).attr('data')));	
		}
	});
	
	/**
	 *  操盘者出资点击事件 
	 */
	$('#margin ul li').click(function() {
		$('#margin ul li.on').removeClass('on');
		$(this).addClass('on');
		var money = $(this).attr("data");
		var lever = Number($("#lever").val())+1;
		$("#recommendMoney").val(money);
		$('#bailMoney_div ul li').each(function(){
			var lever = Number($(this).attr("data"))+1;
			var pHtml = $.formatMoney(Math.round(money/lever))+"元";
			$(this).find("p").html(pHtml);
		});
		getDeductData(getTotalMoney(),getLever(),getUseDay());
	});
	$("#otherRecommendMoney").parent().parent().unbind();
	
	
	
	
	/**
	 * 绑定合买者点击事件
	 */
	$('#bailMoney_div ul li').live("click",function() { 
		$("#baildata").removeClass("font_size_15").addClass("font_size_22");	
		$('#bailMoney_div ul li.on').removeClass('on');
		$(this).addClass('on');
		$("#baildata").html($.formatMoney(Number($(this).attr('data')))); 
		$("#lever").val(Number($(this).attr('data')));
		getDeductData(getTotalMoney(),getLever(),getUseDay());
	});
	
	/**
	 * 推荐天数 点击事件
	 */
	$('#match_days ul li').click(function() {
		$("#days").removeClass("font_size_15").addClass("font_size_22");	
		$('#match_days ul li.on').removeClass('on');
		$(this).addClass('on');
		$("#days").val(Number($(this).attr('data')));
		$("#daysShow").html(Number($(this).attr('data')));
		getDeductData(getTotalMoney(),getLever(),getUseDay());
	});
	/**
	 * 获取总操盘资金
	 */
	function getTotalMoney(){
		return $.trim($("#recommendMoney").val()).replace(/\,/g,"");
	}
	/**
	 * 获取当前保证金对应的倍数
	 */
	function getLever(){
		return $("#lever").val();
	}
	/**
	 * 获取合买者资金
	 */
	function getBailMoney(){
		return $.trim($("#baildata").html()).replace(/\,/g,"");
	}
	
	
	/**
	 * 获取使用天数
	 */
	function getUseDay(){
		return $.trim($("#days").val());
	}
	
	/**
	 * 根据保证金，倍数，天数 获取平仓线，补仓线，利息，管理费等信息
	 */
	function getDeductData(recommendMoney,lever,days){
			$.post(basepath + "together/deduct.data.json", {
				recommendMoney : recommendMoney,
				lever : lever,
				days : days
			}, function(result) {
				if (result.success) {
					// 设置总操盘资金、仓线、补仓线显示
					
					$("#totalMoney").html($.formatMoney(result.obj.totalMoney));
					$("#hemaiMoney").html($.formatMoney(result.obj.capitalAmount));
					$("#shortLine").html($.formatMoney(result.obj.shortLine));
					$("#openLine").html($.formatMoney(result.obj.openLine));
					$("#operatorsInfo").html(result.obj.operatorsInfo);
					// 总利息小于等于0 则显示免费
					if (Number(result.obj.totalInterestFee)>0 ){
						$("#totalInterestFee").html("<i>"+($.formatMoney(Number(result.obj.totalInterestFee)))+"</i>元");						
					}else					{
						$("#totalInterestFee").html("<i>免费</i>");
					}
					
					$("#manageFee").html($.formatMoney(Number(result.obj.manageFee)));
				} else {
					showMsgDialog("提示",result.message);
				}
			}, "json");
		}	
	
	
	/**
	 * 保存提交申请
	 */
	$("#submit").on("click",function(){
		if(!$("#agree").attr("checked")){
			showMsgDialog("提示","请阅读并同意《股票合买操盘合作协议》。");
			return;
		}
		var borrowPeriod = Number(getUseDay()); // 借用天数
		var inputTotalMoney = Number(getTotalMoney());
		var lever = Number(getLever()); // 倍数
		if (Number(inputTotalMoney)<minMoney || Number(inputTotalMoney)>maxMoney){
			showMsgDialog("提示","请输入您的操盘保证金，最少"+$.formatMoney(minMoney)+"元，最多"+$.formatMoney(maxMoney)+"元。");
			return;
		}
			if(!isLoginSSO){
    			window.location.href=basepath+"/toTogetherSSO"; 
			}else{
				$("form").submit();		
			}
		
	});
//	/**
//	 * 是否从确认页面返回。如果不为空则填充数据显示
//	 */
//	var backlever = $("#backlever").val();
//	var backtradeStart =  $("#backtradeStart").val();
//	var backbailMoney = $("#backbailMoney").val();
//	var backborrowPeriod = $("#backborrowPeriod").val();
//	var backtotalMoney= $("#backtotalMoney").val();
//	var lever =$("#lever").val();
//	
//	if (validateIsNull(backlever) || validateIsNull(backtradeStart) 
//			|| validateIsNull(backbailMoney) || validateIsNull(backborrowPeriod)
//			|| validateIsNull(backtotalMoney))
//	{
		/**
		 * 页面初始化
		 */
		getDeductData(getTotalMoney(),getLever(),getUseDay());
//		
//	}else
//	{
//		//设置totalMoney
//		$("#totalMoney").val($.formatMoney(backtotalMoney));	
//		onTotalMoney();		
//		// 重新获取保证金值
//		queryBailMony(getTotalMoney(),1);
//		//设置保证金和倍数
//		$("#baildata").html($.formatMoney(backbailMoney)); 	
//		$("#lever").val(backlever);
//		// 重新获取保证金值
//		queryBailMony(getTotalMoney(),getLever(),getBailMoney()); 
//		
//		// 设置操盘天数
//		$("#days").val(backborrowPeriod);
//		onUseDay();
		//点击选中天数之后，在处理点击开始交易时间
//		getDeductData();
//	}
});
