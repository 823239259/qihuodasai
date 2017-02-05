
$(function(){
	queryAccountInfo();
	balanceAction();
	detailsAction();
	switchTab();
	convertAction();
	stockDetailsAction();
	//设置当前停留顶菜单位置
	$(".navlist li a").removeClass("on");
	$("#nav_my").addClass("on");
	
	//设置当前停留左菜单位置
	$("#oAccount").find("a").addClass("on");
//	$("#accountInfo").parent().addClass("on");
	

	//帐号展示、隐藏、点击事件
	$('.uc_tlbtn').each(function() {
		var btn=$(this);
		btn.hover(function() {
			btn.children('span').show();
		}, function() {
			btn.children('span').hide();
		});
		btn.children('span').click(function() {
			if(btn.parent().next('.uc_ucpromt').is(":hidden")){
				$(".uc_ucpromt").hide();
				btn.parent().next('.uc_ucpromt').show();
			}else{
				btn.parent().next('.uc_ucpromt').hide();
			}
		});
		btn.children("a").click(function(){
			var groupId = $(this).attr("data-id");
			window.location.href=basepath+"trade/detail/"+groupId;
		});
	});
	
	$('#arrearage_close').live('click',function() {
		var $this =$(this);
		$this.parent().parent().css({display: "none"});
	});
	
	//补费-方案欠费
	$('#late_fee').live('click',function(){
		var $this = $(this);
		var tradeId = $this.attr('data_id');
		var status = $this.attr('status');
		if(status == 'true'){
			$this.attr('status',false);
			$this.text('正在补费');
			$this.css({background:'#D3D3D3'});
			$.post(basepath+"user/lateTradeFee",{tradeId:tradeId,ajax:1},function(data){
				if(data.success){
					$this.text('补费');
					$this.css({background:'#4c8efb'});
					$this.attr('status',true);
					if(data.message!="" && data.message!=null){
						if(data.message == 'balanceLack'){
							showMsgDialog("提示",'您的余额不足，请充值后再补费!');
						}else{
							showMsgDialog("提示",'补费失败，未找到该方案的欠费信息!');
						}
					}else{
						winSucessRefresh('提示','补费成功！',basepath+'user/account');
					}
				}else{
					$this.text('补费');
					$this.css({background:'#4c8efb'});
					$this.attr('status',true);
					showMsgDialog("提示",'系统繁忙，请重试......');
				}
			},"json");
		}
	});
	
	//提现
	$('#drawmoney').live('click',function(){
		$.post(basepath+"draw/isHaveArrearage",{},function(data){
			if(data.success){
				if(data.data.isHaveArrearage){
					//showMsgDialog("提示",'您有未结算的账目，您的账号已被限制提现，请联系客服解除限制！');
					showMsgDialog("提示",'您的账户已被限制提现，具体原因为“'+data.data.reason+'”，请联系客服解除限制！');
				}else{
					window.location.href=basepath+'draw/drawmoney';
				}
			}else{
				showMsgDialog("提示",'系统繁忙，请重试......');
			}
		},"json");
	});
	
	//方案盈亏
	$("#tradeAccrual").live('click',function(){
		var $this = $(this);
		var groupId = $this.attr('data-id');
		$this.parent().find('img').css({display: ''});
		$this.css({display:"none"});
		$.post(basepath+"user/getTradeAccrual",{groupId:groupId},function(data){
			if(data.success){
				if((data.message!="" && data.message!=null) || data.data.tradeAccrual == null){
					$this.parent().find('img').css({display: 'none'});
					$this.css({display:'block'});
					showMsgDialog("提示",'系统繁忙，请重试......');
				}else{
					var totalAccrual = data.data.tradeAccrual == 0 ? '0.00' : $.formatMoney(data.data.tradeAccrual,2);
					var accrual = '';
					if(Number(data.data.tradeAccrual) >= 0){
						accrual += "<i class='color4'>"+totalAccrual+"</i>元";
					}else{
						accrual += "<i class='color3'>"+totalAccrual+"</i>元";
					}
					$this.parent().html(accrual);
				}
			}else{
				$this.parent().find('img').css({display: 'none'});
				$this.css({display:'block'});
				showMsgDialog("提示",'系统繁忙，请重试......');
			}
		},"json");
	});
	$('.fl_uc_cancelbtn').each(function() {
		$(this).click(function() {
			$('.fl_box').hide();
			$('#fl_mask').hide();
		});
	});
	$('.fl_uc_trade .fl_navtitle .close').click(function() {
		$('.fl_box').hide();
		$('#fl_mask').hide();
	});
	
	/* 添加保证金 */
	var addBond= function(addMoney,groupId){
		$('#addBond').html('添加中......');
		$.post(basepath + "trade/addBond.json", {addMoney:addMoney,groupId:groupId,ajax:1}, function(result) {
			if (result.success) {
				$('.fl_box').hide();
				$('#fl_mask').hide();
				clickCloseRefresh("提示","添加保证金成功。");
			} else {
				$('#addBond').css("color","").html('确定').bind('click', addBondFun);
				showMsgDialog("提示",result.message);
			}
		}, "json");	
		
	}
	
	var addBondFun= function(){
		var minAddMoney=Number($('#minAddMoney').val());
		var addMoney=Number($('#addMoney').val());
		var maxMoney=Number($('#balance').val());
		var groupId=$('#group_id').val();
		if(!addMoney){
			showMsgDialog("提示","请输入追加保证金金额。");
			return false;
		}
		if(!$.isNumeric(addMoney)){
			showMsgDialog("提示","请输入正确的金额。");
			return false;
		}
		if(addMoney<minAddMoney){
			showMsgDialog("提示","追加金额最少为总操盘资金的1%。");
			$('#addMoney').val(minAddMoney);
			return false;
		}
		if(addMoney>maxMoney){
			showMsgDialog("提示","追加金额最大为"+maxMoney+"元。");
			$('#addMoney').val("");
			return false;
		}
		 var moneyreg=/^[0-9]+([.]{1}[0-9]{1,2})?$/;
		 if(!moneyreg.test(addMoney)){
			 showMsgDialog("提示","请输入正确的金额,最小金额为分。"); 
				return false; 
		 }
		$('#addBond').css("color","#CCC").unbind("click");
		addBond(addMoney,groupId);
	}
	
	$('#addBond').bind('click', addBondFun);
});

/**
 * 切换选项卡
 */
function switchTab(){
	$(".uc_sl_nav a").each(function(){
		var name=$(this).attr("name");
		$(this).click(function(){
			if($(this).hasClass("on")){
				return;
			}
			$(this).addClass("on");
			$("#"+name).show();
			if(name=="uc_stock"){
				$(".uc_sl_nav a[name='uc_futures']").removeClass("on");
				$("#uc_futures").hide();
			}else{
				$(".uc_sl_nav a[name='uc_stock']").removeClass("on");
				$("#uc_stock").hide();
			}
		})
	});
}


/**
 * 余额处理
 */
function balanceAction(){
	format($(".uc_sm_overmoney span"));
	format($(".uc_sm_money span"));
	prompt($("uc_sm_money a"));
	prompt($(".uc_sm_money a"));
	//安全认证
	$("#secInfo").live("click",function(){
		window.location.href=basepath+"securityInfo/secInfo";
	});
}

/**
 * 明细处理
 */
function detailsAction(){
	format($(".uc_sl_moneyinfo span"));
	format($(".uc_sl_moneylist p"));
	prompt($(".uc_sl_moneyinfo a"));
	//股票
	changeColor("green", $("#totalAccrual"), true, null);
	changeColor("green", $("#totalAccrual"), false, $("#totalAccrualInterest"));
	//股指
	changeColor("green", $("#cumulativeProfit"), true, null);
	changeColor("green", $("#cumulativeTotal"), true, null);
	changeColor("green", $("#actualProfit"), true, null);
//	changeColor("green", $("#actualProfit"), false, $("#profitRate"));
}

/**
 * 配资详情处理
 */
function stockDetailsAction(){
	//鼠标经过事件
	$('.ut_tlbtn_detail').live('mouseover',function() {
		var dataStatus = $(this).children('a').attr('data-status');
		if(dataStatus != 0){
			$(this).children('span').show();
		}
	});
	
	//鼠标移除事件
	$('.ut_tlbtn_detail').live('mouseout',function() {
		$(this).children('span').hide();
	});
	
	//帐号展示、隐藏事件
	$('.ut_tlbtn_detail span').live('click',function() {
		var $this = $(this);
		if($this.parents('ul').next('.uc_ucpromt').is(":hidden")){
			$(".uc_ucpromt").hide();
			$this.parents('ul').next('.uc_ucpromt').show();
		}else{
			$this.parents('ul').next('.uc_ucpromt').hide();
		}
	});
	
	//方案详情
	$('.ut_tlbtn_detail a').live('click',function(){
		var groupId = $(this).attr("data-id");
		window.location.href=basepath+"trade/detail/"+groupId;
	});
	
	//随心融资
	$(".toTrade").live("click",function(){
		window.location.href=basepath+"day";
	});
}

/**
 * 内转处理
 */
function convertAction(){
	
	$("#convert").click(function(){
		$("#convertWin").show();
		$('.fl_mask').show();
	});
	
	$("#convertClose").click(function(){
		$("#convertWin").hide();
		$('.fl_mask').hide();
		$("#convertMoney").val('');
	});

	$("#convertCancel").click(function(){
		$("#convertWin").hide();
		$('.fl_mask').hide();
		$("#convertMoney").val('');
	});
	//资金划转，记录明细
	$("#convertSure").click(function(){
		var outAccount=$("#outAccountSelect").val();
		var intoAccount=$("#intoAccountSelect").val();
		var  money=$("#convertMoney").val();
		var regex=/^\d+(\.\d+)?$/;
		if(checkConverValueArr(outAccount, intoAccount, money)){	
			$.ajax({
			type : "POST",
			url : basepath + "user/convert",
			data : {"outAccount":outAccount,"intoAccount":intoAccount,"money":money},
			success : function(result) {
				if (result.success) {
					clickCloseRefresh("提示","内转成功");
				} else {
					showMsgDialog("提示",result.message+"!");
				}
			}
			});
		}
	});
	
	function checkConverValueArr(outAccount, intoAccount, money){
		console.log("outAccount: "+outAccount);
		console.log("intoAccount: "+intoAccount);
		console.log("money: "+money);
		if(outAccount ==intoAccount){
			showMsgDialog("提示",'转入账户与转出账户不能相同!');
			return false;
		}
		var moneyreg=/^[0-9]+([.]{1}[0-9]{1,2})?$/;
		if(!moneyreg.test(money)){
			showMsgDialog("提示","请输入正确的金额,最小金额为分！"); 
			return false; 
		}
		return true;
	}
}

/**
 * 格式化金额样式
 * @param $obj
 */
function format(obj){
	obj.each(function(){
		if($.trim($(this).text())==""){
			return true;
		}
		addFormatValue($(this), $.trim($(this).text()));
	})
}

function addFormatValue(obj, text){
	var money=text.split(".");
	obj.html(money[0]+".<i>"+money[1]+"</i>");
}

/**
 * 提示信息特效
 * @param obj
 */
function prompt(obj){
	obj.each(function(){
		//获取焦点
		$(this).hover(function(){
			$(this).closest("li").find(".uc_mlpromt").show();
		}, 
		//失去焦点
		function(){
			$(this).closest("li").find(".uc_mlpromt").hide();
		})
	});
}

/**
 * 判断obj的数值的正负，改变颜色，如果obj2的颜色变化同obj
 * @param color
 * @param obj
 * @param isChange 是否改变颜色
 * @param obj2
 */
function changeColor(color, obj, isChange, obj2){
	var text=obj.text();
	if(text.indexOf("-")>-1){
		if(isChange){
			obj.css("color",color);
			obj.find("i:first").css("color",color);
		}
		if(obj2){
			obj2.css("color",color);
			obj2.find("i:first").css("color",color);
		}
	}
}
/**
 * 使用ajax加载账户信息
 */
function queryAccountInfo(){
	$.post(basepath+"user/queryAccount",{},function(data){
		if(data.success){
			var avlBal = data.data.avlBal == null || data.data.avlBal == 0 ? '0.00' : $.formatMoney(data.data.avlBal,2);
			addFormatValue($("#avlBal"), avlBal);
			var totalAssets = data.data.totalAssets == null || data.data.totalAssets == 0 ? '0.00' : $.formatMoney(data.data.totalAssets,2);
			addFormatValue($("#totalAssets"), totalAssets+"元");
			var totalLending = data.data.totalLending == null || data.data.totalLending == 0 ? '0.00' : $.formatMoney(data.data.totalLending,2);
			addFormatValue($("#totalLending"), totalLending+"元")
			var totalLeverMoney = data.data.totalLeverMoney == null || data.data.totalLeverMoney == 0 ? '0.00' : $.formatMoney(data.data.totalLeverMoney,2);
			addFormatValue($("#totalLeverMoney"), totalLeverMoney+"元")
			var frzBal = data.data.frzBal == null || data.data.frzBal == 0 ? '0.00' : $.formatMoney(data.data.frzBal,2);
			addFormatValue($("#frzBal"), frzBal+"元")
			var avlBal2 = data.data.avlBal == null || data.data.avlBal == 0 ? '0.00' : $.formatMoney(data.data.avlBal,2);
			addFormatValue($("#avlBal2"), avlBal2+"元")
			var tradeTotalNum = data.data.userTradeVoList != null ? data.data.userTradeVoList.length : 0;
			$("#tradeTotalNum").text(tradeTotalNum);
			var trade = null;
			if(Number(tradeTotalNum) > 0){
				$(data.data.userTradeVoList).each(function(){
					//标题
					trade = trade == null ? "<div class='uc_uctitl'>" : trade + "<div class='uc_uctitl'>";
					trade += "<h4>方案编号："+this.groupId+"</h4>";
					trade += "<div class='uc_ucttime'>开始时间：<i>"+getFormatDateByLong(this.starttime,"yyyy-MM-dd")+"</i></div>";
					trade += "</div>";
					
					trade += "<ul class='uc_tlist uc_uclist'>";
					
					trade += "<li class='uc_tl110'>";
					trade += "<h4 class='uc_tlist_title'>总操盘资金</h4>";
					var totalAccountLeverMoney = this.totalAccountLeverMoney == null || this.totalAccountLeverMoney == 0 ? '0.00' : $.formatMoney(this.totalAccountLeverMoney,2);
					trade += "<p class='uc_tlist_con'>"+totalAccountLeverMoney+"元</p>";
					trade += "</li>";
					
					trade += "<li class='uc_tl110'>";
					trade += "<h4 class='uc_tlist_title'>融资金额</h4>";
					var totalMoney = this.totalMoney == null || this.totalMoney == 0 ? '0.00' : $.formatMoney(this.totalMoney,2);
					trade += "<p class='uc_tlist_con'>"+totalMoney+"</p>";
					trade += "</li>";
					
					trade += "<li class='uc_tl100'>";
					trade += "<h4 class='uc_tlist_title'>融资保证金</h4>";
					var totalLeverMoney = this.totalLeverMoney == null || this.totalLeverMoney == 0 ? '0.00' : $.formatMoney(this.totalLeverMoney,2);
					trade += "<p class='uc_tlist_con'>"+totalLeverMoney+"元</p>";
					trade += "</li>";
					
					trade += "<li class='uc_tl120'>";
					trade += "<h4 class='uc_tlist_title'>追加保证金</h4>";
					var totalAppendLeverMoney = this.totalAppendLeverMoney == null || this.totalAppendLeverMoney == 0 ? '0.00' : $.formatMoney(this.totalAppendLeverMoney,2);
					trade += "<p class='uc_tlist_con'>"+totalAppendLeverMoney+"元</p>";
					trade += "</li>";
					
					if(2 == this.feeType || 3 == this.feeType){
						trade += "<li class='uc_tl110'>";
						trade += "<h4 class='uc_tlist_title'>类型</h4>";
						var totalAccrual = this.totalAccrual == null || this.totalAccrual == 0 ? '0.00' : $.formatMoney(this.totalAccrual,2);
						trade += "<p class='uc_tlist_con'>";
						trade += (2 == this.feeType?"涌金版":"同花顺");
						trade += "</p>";
						trade += "</li>";
					}else{
						trade += "<li class='uc_tl110'>";
						trade += "<h4 class='uc_tlist_title'>方案盈亏</h4>";
						var totalAccrual = this.totalAccrual == null || this.totalAccrual == 0 ? '0.00' : $.formatMoney(this.totalAccrual,2);
						totalAccrual = this.status == 0 ? '0.00' : totalAccrual;
						trade += "<p class='uc_tlist_con'>";
						trade += "<a style='display:block;' href='javascript:void(0);' id='tradeAccrual' data-id='"+this.groupId+"'>点击查看</a><img style='display:none;' src='"+basepath+"static/images/uc/loading.gif'/>";
						/*if(this.status == 0 || Number(this.totalAccrual) >= 0){
							trade += "<i class='color4'>"+totalAccrual+"</i>元";
						}else{
							trade += "<i class='color3'>"+totalAccrual+"</i>元";
						}*/
						trade += "</p>";
						trade += "</li>";
					}
					
					var tradeStatusName = null;
					var tradeStatus = null;
					if(2 == this.feeType || 3 == this.feeType){
						tradeStatusName = this.auditStatus == 0 ? '开户中' : '操盘中'; 
						tradeStatus = this.auditStatus == 1 ? 1 : 0; 
					}else{
						tradeStatusName = this.status == 0 ? '验资中' : '操盘中';
						tradeStatus = this.status == 0 ? 0 : 1; 
					}
					
					trade += "<li class='uc_tl90'>";
					trade += "<h4 class='uc_tlist_title'>方案状态</h4>";
					trade += "<p class='uc_tlist_con'>"+tradeStatusName+"</p>";
					trade += "</li>";
					
					trade += "<li class='uc_tlbtn'>";
					if(1 == tradeStatus) {
						trade += '<a href="javascript:void(0)" onclick="showRemarginBox(\''+this.groupId+'\');" class="uc_tllist_add">追加保证金</a>';
					}
					trade += "<div class='ut_tlbtn_detail'><a href='javascript:void(0);' class='"+(tradeStatus == 0 ? 'uc_tlbtn_btn_no' : 'uc_tlbtn_btn')+"' data-status="+tradeStatus+" data-id='"+this.groupId+"'>操盘详细</a>";
					trade += "<span style='display:none;' class='uc_tlist_link'>交易账户</span></div>";
					trade += "</li>";
					
					trade += "</ul>";
					
					trade += "<div class='uc_ucpromt' style='display:none;'>";
					trade += "<i class='uc_ucp_icon'></i>";
					trade += "<div class='uc_ucpromtbox'>";
					trade += "<p>重点提醒：交易前请线阅读 <a href='"+basepath+"help'>《股票融资规则说明》</a></p>";
					trade += "<p>资金风控：亏损警戒线<i>"+$.formatMoney(this.totalWarning,2)+"</i> 元，亏损平仓线<i>"+$.formatMoney(this.totalOpen,2)+"</i>元</p>";
					trade += "<p>开户券商：<span>"+this.hsBelongBroker+"</span></p>";
					trade += "<p>交易账户：<span>"+this.account+"</span></p>";
					trade += "<p>交易密码：<span>"+this.password+"</span>(为了您的资金安全，请妥善保管好密码)</p>";
					trade += this.insuranceNo ? '<p>保险单号：<a href="">'+ this.insuranceNo +'</a></p>' : '';
					//trade += "<p> 交易软件：<a href='"+basepath+"help?tab=software&leftMenu="+(this.feeType==2 ? 5:this.feeType==3?6:2)+"'>"+(this.feeType==2 ? '涌金版软件下载':this.feeType==3?'同花顺软件下载':'独立委托版')+"</a></p>";
					trade += "<p> 交易软件：1.若您的交易帐号以“<i style='color: red;'>6236</i>”开头，需使用钱江版交易软件，请“<a href='"+basepath+"help?tab=software&leftMenu=2'>立即下载</a>”；<br><em class='uc_tlpromtmsp2'>2.若您的交易帐号以“<i style='color: red;'>8709</i>”开头，需使用涌金版交易软件，请“<a href='"+basepath+"help?tab=software&leftMenu=5'>立即下载</a>”；</em><br><em class='uc_tlpromtmsp2'>3.若您的交易帐号以“<i style='color: red;'>15703</i>”开头，需使用钱隆版交易软件，请“<a href='http://www.etradepark.com/load/pdf/钱隆TTS.exe'>立即下载</a>”；</em></p>";
					//trade += '<p>&nbsp&nbsp提示：1.停牌股处理规则，<a href="'+basepath+'/help?tab=rule&leftMenu=3" target="_blank">点击查看</a><br>&nbsp&nbsp&nbsp&nbsp&nbsp2.平仓后方案会继续保留并产生费用，停止产生费用请终结方案。</p>';
					trade += '<p><em class="uc_tlpromtmsp">提示：1.停牌股处理规则，<a href="'+basepath+'/help?tab=rule&leftMenu=3" target="_blank">点击查看</a><br></em><em class="uc_tlpromtmsp2">2.平仓后方案继续保留并产生费用，停止产品费用请终结方案</em></p>';
					trade += "</div>";	
					trade += "</div>";
					
				});
			}else{
				trade = "<div class='uc_sl_noplan toTrade'>";
				trade += "<a href='javascript:void(0);'><em>我要申请</em></a>";
				trade += "</div>";
			}
			$("#tradeTotalNum").closest('.uc_sl_sifplan').after(trade);
			
			$('#arrearageList').empty();
			
			//欠费方案列表信息
			var arrearageTitle = "<ol class='title'>";
			arrearageTitle += "<li class='fl_ucm125'>方案编号</li>";
			arrearageTitle += "<li class='fl_ucm135'>欠费金额</li>";
			arrearageTitle += "<li class='fl_ucm125'>欠费天数</li>";
			arrearageTitle += "<li class='fl_ucm90'>操作</li>";
			arrearageTitle += "</ol>";
			
			var arrearageList = "<ol>";
			arrearageList += "<li class='fl_ucm125'>{0}</li>";
			arrearageList += "<li class='fl_ucm135'>{1}</li>";
			arrearageList += "<li class='fl_ucm125'>{2}</li>";
			arrearageList += "<li class='fl_ucm90'><a href='javascript:void(0);' status=true id='late_fee' data_id='{0}'>补费</a></li>";
			arrearageList += "</ol>";
			
			var arrearageTotalNum = data.data.userTradeArrearageVos != null ? data.data.userTradeArrearageVos.length : 0;
			if(Number(arrearageTotalNum) > 0){
				var arrearageTotalMoney = 0.00;
				$('#arrearageTotalNum').text(arrearageTotalNum);
				$('#arrearageList').append(arrearageTitle);	
				$(data.data.userTradeArrearageVos).each(function(){
					var list=$.format(arrearageList,this.tradeId,$.formatMoney(this.money,2),this.days);
					arrearageTotalMoney += this.money;
					$('#arrearageList').append(list);
				});
				$('#accountArrearage i').text(arrearageTotalMoney == 0 ? '0.00': $.formatMoney(arrearageTotalMoney,2));
				$('#accountArrearage').css({display: ""});
				$('#arrearageList').parent().parent().css({display: "block"});
			}
		}
	},"json");
}

/**
 * 显示追加保证金的弹出窗口
 * @param gid
 */
function showRemarginBox(gid) {
	$.ajax({
		type:'post',
		url:basepath + 'trade/getRemarginInfo',
		data:{
			'gid' : gid,
			'ajax': 1
		},
		dataType:'json',
		success:function(result) {
			if(result.success) {
				var _balance = result.data.balance, _o = result.data.trade, _totalOperateMoney = _o.totalOperateMoney, _activityType = _o.activityType, _minAddMoney = result.data.minAddMoney;
				var boxStr = '<li>' +
						'<label>账户资金：</label>' +
						'<input type="hidden" id="balance" value="'+_balance+'"/>' +
						'<input type="hidden" id="group_id" value="'+gid+'"/>' +
						'<span><i>'+_balance.toFixed(2)+'</i>元</span>' +
					'</li>' +
					'<li>' +
						'<label>总操盘资金：</label>' +
						'<span><i>'+_totalOperateMoney.toFixed(2)+'</i>元</span>' +
					'</li>' +
					'<li>' +
						'<label>追加保证金：</label>' +
						'<input type="hidden"  id="additionalMargin" value="'+_activityType+'"/>' +
						'<input id="minAddMoney" type="hidden" value="'+_minAddMoney+'"> ' +
						'<input id="addMoney" type="text" maxlength="8" value="'+_minAddMoney+'">' + 
						'<span>元</span>' +
					'</li>';
				$('#deposit .fl_uc_list').html(boxStr);
				$('#deposit').show();
				$('.fl_mask').show();
			}
		}
	});
}

