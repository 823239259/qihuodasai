$(document).ready(function(e) {
	
	//顶部菜单位置
	$('#navlist a').removeClass('cur');
	$("#hkstockli").addClass("cur");
	
	var  minTotalMoney = $("#minTotalMoney").val(); // 最小总操盘资金
	var  maxTotalMoney = $("#maxTotalMoney").val(); // 最大总操盘资金
	var  minHoldDays = $("#minHoldDays").val(); // 最小使用天数
	var  maxHoldDays = $("#maxHoldDays").val();// 最大使用天数
	/**
	 * 默认进入时 当前选中的总操盘金额
	 */
	$('#margin ul li').each(function(){
		if ($(this).hasClass("on")){
			$("#totalMoney").val($.formatMoney(Number($(this).attr('data'))));	
		}
	});
	
	/**
	 * 默认进入时 当前选中的选择天数
	 */
	$('#match_days ul li').each(function(){
		if ($(this).hasClass("on")){
			$("#use_day").val(Number($(this).attr('data')));	
		}
	});
	
	/**
	 *  总操盘资金 点击事件 
	 */
	$('#margin ul li').click(function() {
		$("#totalMoney").removeClass("font_size_15").addClass("font_size_22");	
		$('#margin ul li.on').removeClass('on');
		$(this).addClass('on');
		$("#totalMoney").val($.formatMoney(Number($(this).attr('data'))));		
		// 重新获取保证金值
		queryBailMony(getTotalMoney(),getLever());
	});
	
	
	/**
	 * 获取总操盘资金
	 */
	function getTotalMoney(){
		return $.trim($("#totalMoney").val()).replace(/\,/g,"");
	}
	
	/**
	 * 获取操盘保证金
	 */
	function getBailMoney(){
		return $.trim($("#baildata").html()).replace(/\,/g,"");
	}
	
	
	/**
	 * 获取使用天数
	 */
	function getUseDay(){
		return $.trim($("#use_day").val());
	}
	
	
	
	/**
	 * 根据当前选中的总操盘资金 获取对应倍数的 保证金
	 */
	var  queryBailMony = function (selectTotalMoney,selectLever,selectBailMoney){
		if (!selectTotalMoney 
				||!selectLever
				|| Number(selectTotalMoney)<minTotalMoney  
				|| Number(selectTotalMoney)>maxTotalMoney){
			return;
		}
		//设置总操盘金额显示
		$("#show_total_money").html($.formatMoney(selectTotalMoney));
		//请求后台数据
		$.post(basepath + "hkstock/baildata.json", {
			totalMoney:selectTotalMoney,
			lever:selectLever
		}, function(result) {
			if (result.success) {
				// 当前保证金值存在，即点击的推荐保证金块
				if (validateIsNull(selectBailMoney)){
					var  dataLength = result.obj.bailArray.length;
					if (dataLength==0){
						return ;
					}
					var bailHtml = "";
					for (var i=0;i<dataLength;i++){
						if (i==0){
							//填充保证金显示值 
							$("#baildata").html($.formatMoney(result.obj.bailArray[i].bailMoney));
							$("#sublever").val(i+1);
							$("#show_bail_money").html($.formatMoney(result.obj.bailArray[i].bailMoney));
							
							bailHtml = bailHtml+"<li class='on' data='"+result.obj.bailArray[i].bailMoney+"' lever='"+(i+1)+"'> ";
							bailHtml = bailHtml+"<p><i>"+$.formatMoney(result.obj.bailArray[i].bailMoney)+"</i>元</p> ";
							bailHtml = bailHtml+"<span></span> ";
							bailHtml = bailHtml+"</li> ";
						}else
						{
							bailHtml = bailHtml+"<li data='"+result.obj.bailArray[i].bailMoney+"' lever='"+(i+1)+"' > ";
							bailHtml = bailHtml+"<p><i>"+$.formatMoney(result.obj.bailArray[i].bailMoney)+"</i>元</p> ";
							bailHtml = bailHtml+"<span></span> ";
							bailHtml = bailHtml+"</li> ";
						}
					}
					
					$("#bailMoney").html(bailHtml);
				}
				else
				{
					$("#baildata").html($.formatMoney(selectBailMoney));
					$("#sublever").val(selectLever);
					$("#show_bail_money").html($.formatMoney(selectBailMoney));
					// alert(selectBailMoney);
					onBailMoney();	// 设置选中值
				}
				// 设置平仓线、补仓线显示
				$("#show_waring_money").html($.formatMoney(result.obj.waringMoney));
				$("#show_open_money").html($.formatMoney(result.obj.openMoney));
				// 操盘规则显示
				$("#operatorsInfo").html(result.obj.operatorsInfo);
				// 获取管理费 利息
				getDeductData();

			} else {
				showMsgDialog("提示",result.message);
			}
		}, "json");
	}
	
	/**
	 * 获取当前保证金对应的倍数
	 */
	function getLever(){
		return $("#sublever").val();
	}

	
	/**
	 * 绑定保证金 点击事件
	 */
	$('#bailMoney_div ul li').live("click",function() { 
		$("#baildata").removeClass("font_size_15").addClass("font_size_22");	
		$('#bailMoney_div ul li.on').removeClass('on');
		$(this).addClass('on');
		$("#baildata").html($.formatMoney(Number($(this).attr('data')))); 
		$("#sublever").val(Number($(this).attr('lever')));

		// 重新获取保证金值
		queryBailMony(getTotalMoney(),getLever(),getBailMoney()); 
		getDeductData();
	});
	
	/**
	 * 推荐天数 点击事件
	 */
	$('#match_days ul li').click(function() {
		$("#use_day").removeClass("font_size_15").addClass("font_size_22");	
		$('#match_days ul li.on').removeClass('on');
		$(this).addClass('on');
		$("#use_day").val(Number($(this).attr('data')));
		//点击选中天数之后，在处理点击开始交易时间
		getDeductData();
	});

	
	/**
	 * 获取预计结束日期
	 * @param borrowPeriod 借用天数
	 * @param tradeStart 1：下个交易日 0：立即交易
	 */
	var endday=function(borrowPeriod,tradeStart){
		$.post(basepath + "hkstock/endDay.json", {borrowPeriod:borrowPeriod,tradeStart:tradeStart}, function(result) {
			if (result.success) {
				$("#end_day").html(result.data.endDay);
			} else {
				showMsgDialog("提示",result.message);
			}
		}, "json");
	}
	/**
	 * 点击下个交易日
	 */
	$('.nday').click(function() {
		if($.isNumeric($('#use_day').val())){
		var borrowPeriod = Number($('#use_day').val());
		endday(borrowPeriod,1);
		}
	});
	/**
	 * 点击立即交易日
	 */
	$('.tday').click(function() {
		if($.isNumeric($('#use_day').val())){
		var borrowPeriod = Number($('#use_day').val());
		endday(borrowPeriod,0);
		}
	});
	
	/**
	 * 初始化交易日方法  交易日当前时间是否交易日
	 */
	var initTradeStart= function(){
		$.post(basepath + "hkstock/isTrading.json", {}, function(result) {
		if (result.success) {
			var tday = $(".tday");
			var nday = $(".nday");
			if (result.data.isTradeDay) {
				tday.show();			
				nday.show();
				//如果是返回修改的,当前是交易日，但是之前选择的下个交易日
				if (!validateIsNull(backtradeStart) && backtradeStart==1){
					nday.click();
					return;
				}
				tday.click();
			} else {
				tday.hide();
				nday.show();
				nday.click();			
			}
		} else {
			showMsgDialog("提示",result.message);
		}
	}, "json");
	}
	
	/**
	 * 操盘天数输入框按下事件
	 */
	$("#use_day").keypress(T.numKeyPress).keyup(function() {
		onUseDay();
		getDeductData();
		$(this).focus();
	}).focus(function() {
		if($(this).val()=='请输入天数'){
			$("#use_day").removeClass("font_size_15").addClass("font_size_22");	
			$(this).val('');
			}
		$(this).css("ime-mode","disabled");
	});
	
	/**
	 * 总操盘资金 输入框按下事件
	 */
	$("#totalMoney").keypress(T.numKeyPress).keyup(function() {
		onTotalMoney();
		// 重新获取保证金值
		queryBailMony(getTotalMoney(),1);
		$(this).focus();
	}).focus(function() {
		if($(this).val()=='请输入总操盘资金'){
			$("#tz").removeClass("font_size_15").addClass("font_size_22");	
			$(this).val('');
			}
		$(this).css("ime-mode","disabled");
	});
	
	/**
	 * 比较输入的总操盘金额 与 推荐金额是否相等
	 */
	var onTotalMoney = function (){
		var totalMoney=Number(getTotalMoney());
		if (!validateIsNull(getTotalMoney())){
			/*if (totalMoney<minTotalMoney){
				$("#totalMoney").val($.formatMoney(minTotalMoney));		
			}*/
			if (totalMoney>maxTotalMoney){
				$("#totalMoney").val($.formatMoney(maxTotalMoney));		
			}
		}
		
		$('#margin ul li').each(function(i){
			if($(this).attr('data')==totalMoney){
				$(this).addClass('on');
			 }else{
				$(this).removeClass('on'); 
			 }
		});
	}
	
	/**
	 * 比较输入的保证金金额 与 推荐金额是否相等
	 */
	var onBailMoney = function (){
		var bailMoney=Number(getBailMoney());
		$('#bailMoney_div ul li').each(function(i){
			if($(this).attr('data')==bailMoney){
				$(this).addClass('on');
			 }else{
				$(this).removeClass('on'); 
			 }
		});
	}
	
	
	/**
	 * 比较输入的天数与 推荐天数是否相等
	 */
	var onUseDay = function (){
		
		var onDay=Number($("#use_day").val());
		if (!validateIsNull($("#use_day").val())){
			/*if (onDay<minHoldDays){
				$("#use_day").val(minHoldDays);		
			}*/
			if (onDay>maxHoldDays){
				$("#use_day").val(maxHoldDays);		
			}
		}
		
		$('#match_days ul li').each(function(i){
			if($(this).attr('data')==onDay){
				$(this).addClass('on');
			 }else{
				$(this).removeClass('on'); 
			 }
		});
	}
	
	/**
	 * 获取管理费、利息等信息
	 */
	function getDeductData() {
		var borrowPeriod = Number(getUseDay()); // 借用天数
		var inputTotalMoney = getTotalMoney();
		var bailMoney = Number(getBailMoney());    // 保证金
		var lever = Number(getLever()); // 倍数
		if (!borrowPeriod || !inputTotalMoney || !bailMoney || !lever){
			return;
		}
		
		var tradeStart = typeof($("input[name='tradeStart']:checked").val()) == "undefined"?1:$("input[name='tradeStart']:checked").val();
		if (Number(borrowPeriod)>=minHoldDays && Number(borrowPeriod)<=maxHoldDays
			&& Number(inputTotalMoney)>=minTotalMoney && Number(inputTotalMoney)<=maxTotalMoney){
			$.post(basepath + "hkstock/deduct.data.json", {
				borrowPeriod : borrowPeriod,
				lever : lever,
				bailMoney : bailMoney,
				tradeStart:tradeStart,
				totalMoney:inputTotalMoney
			}, function(result) {
				if (result.success) {
					$("#end_day").html(result.data.endDay);
					// 总利息小于等于0 则显示免费
					if (Number(result.data.totalInterestFee)>0 ){
						$("#totalInterestFee").html("<i>"+($.formatMoney(Number(result.data.totalInterestFee)))+"</i>元");						
					}
					else
					{
						$("#totalInterestFee").html("<i>免费</i>");
					}
					$("#manageFee").html($.formatMoney(Number(result.data.manageFee)));
				} else {
					showMsgDialog("提示",result.message);
				}
			}, "json");
		}	
	}
	
	/**
	 * 保存提交申请
	 */
	$("#submit").on("click",function(){
		//return false;
		if(!$("#agree").attr("checked")){
			showMsgDialog("提示","请阅读并同意《借款协议》。");
			return;
		}
		var borrowPeriod = Number(getUseDay()); // 借用天数
		var inputTotalMoney = getTotalMoney();
		var bailMoney = Number(getBailMoney());    // 保证金
		var lever = Number(getLever()); // 倍数
		
		if (borrowPeriod<minHoldDays || borrowPeriod>maxHoldDays){
			showMsgDialog("提示","请选择操盘天数："+minHoldDays+"-"+maxHoldDays+"天");
			return;
		}
		if (Number(inputTotalMoney)<minTotalMoney || Number(inputTotalMoney)>maxTotalMoney){
			showMsgDialog("提示","总操盘金最少"+$.formatMoney(minTotalMoney)+"港元，最多"+$.formatMoney(maxTotalMoney)+"港元。");
			return;
		}
		
		if(validateIsNull($("input[name='tradeStart']:checked").val())){
			showMsgDialog("提示","请选择开始交易时间！");	
			return;
		}
		// 设置需要提交的值
		$("#subTotalMoney").val(inputTotalMoney);
		$("#subBailMoney").val(bailMoney);
		//提交到下一个页面	
		if($("input[name='tradeStart']:checked").val()==1){
			showConfirmDialog("提示","请确认是否要在下个交易日开始操盘!",function(){
				if(!isLoginSSO){
	    			window.location.href=basepath+"/toHkStockSSO"; 
					}else{
						$("form").submit();		
					}		
				},"确定",function(){
					return true;		
			});
		}else{
			if(!isLoginSSO){
    			window.location.href=basepath+"/toHkStockSSO"; 
			}else{
				$("form").submit();		
			}
		}
	});
	
	/**
	 * 是否从确认页面返回。如果不为空则填充数据显示
	 */
	var backlever = $("#backlever").val();
	var backtradeStart =  $("#backtradeStart").val();
	var backbailMoney = $("#backbailMoney").val();
	var backborrowPeriod = $("#backborrowPeriod").val();
	var backtotalMoney= $("#backtotalMoney").val();
	if (validateIsNull(backlever) || validateIsNull(backtradeStart) 
			|| validateIsNull(backbailMoney) || validateIsNull(backborrowPeriod)
			|| validateIsNull(backtotalMoney))
	{
		/**
		 * 页面初始化
		 */
		queryBailMony(getTotalMoney(),1);
		//初始化交易日选择
		initTradeStart();
		
		//点击选中天数之后，在处理点击开始交易时间
		getDeductData();
	}else
	{
		//设置totalMoney
		$("#totalMoney").val($.formatMoney(backtotalMoney));	
		onTotalMoney();		
		// 重新获取保证金值
		queryBailMony(getTotalMoney(),1);
		//设置保证金和倍数
		$("#baildata").html($.formatMoney(backbailMoney)); 	
		$("#sublever").val(backlever);
		// 重新获取保证金值
		queryBailMony(getTotalMoney(),getLever(),getBailMoney()); 
		
		// 设置操盘天数
		$("#use_day").val(backborrowPeriod);
		onUseDay();
		// 设置 选择的交易日
		//初始化交易日选择
		initTradeStart();  
		
		//点击选中天数之后，在处理点击开始交易时间
		getDeductData();
	}
});

/**
 * 打开操盘协议
 */
function hkTradeContract(){
	var htmlHeight = 800;  //网页高度
	var htmlWidth = 1221;  //网页宽度
	var iTop = (window.screen.height-30-htmlHeight)/2; //获得窗口的垂直位置;  
	var iLeft = (window.screen.width-10-htmlWidth)/2;  //获得窗口的水平位置;  
	window.open(basepath+'hkstock/contract','港股申请合作操盘协议','height='+htmlHeight+',innerHeight='+htmlHeight+',width='+htmlWidth+',innerWidth='+htmlWidth+',top='+iTop+',left='+iLeft+',toolbar=no,menubar=no,scrollbars=yes,resizeable=no,location=no,status=no');  
}