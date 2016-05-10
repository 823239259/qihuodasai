
$(document).ready(function() {
	
	//顶部菜单位置
	$('#navlist a').removeClass('cur');
	$("#nav_my").addClass("cur");
	
	//底部菜单位置
	$('.uc_sidebar').find("div.uc_nav ul a").each(function(){
		$(this).removeClass('on');
 	});
	$("#hkstock").parent().addClass('on');
	
	$('.ul_td_stopbtn').hover(function() {
		$('.uc_tdfl_stop').show();
	}, function() {
		$('.uc_tdfl_stop').hide();
	});
	
	$('.fl_box .fl_navtitle a').each(function() {
		$(this).click(function() {
			$('.fl_box').hide();
			$('.fl_mask').hide();
		});
	});
	
	$('.fl_uc_cancelbtn').each(function() {
		$(this).click(function() {
			$('.fl_box').hide();
			$('.fl_mask').hide();
		});
	});
	
	/* 方案列表 */
	var program= function(type,groupId){
		$.post(basepath + "uhkstock/detail.json", {type:type,groupId:groupId,ajax:1}, function(result) {
			if (result.success) {			
				var data=result.obj;			
				var table="";
				$.each(data, function(i,dataobj){
					var tr=' <tr>';
					tr+='<td>{0}</td>';
					tr+='<td>{1}港元</td>';
					tr+='<td>{2}元</td>';
					tr+='<td><i>{3}</i>元/交易日</td>';
					tr+='<td><i>{4}</i>元</td>';
					tr+='<td>{5}</td>';
					var gramNo="'"+this.programNo+"'";
					if($.trim(this.saveid)!=""){
					   tr+='<td><a href="javascript:void(0)" onclick="javascript:showContractsaveInfo('+gramNo+')">操盘协议</a></td>';
					}else{
					tr+='<td></td>';	
					}
					tr+='</tr>';
					tr+='<tr id="'+this.programNo+'" style="display:none;">';
				    tr+='<td class="uc_td_safe" colspan="8">';
					tr+='<a href="javascript:void(0)" onclick="javascript:hideContractsaveInfo('+gramNo+')" class="uc_td_safeup">收起</a> ';
					tr+='<h2>本协议已由<a href="'+basepath+'help?tab=safety&leftMenu=7" target="_blank">电子数据保全中心</a>保全</h2>';
					tr+='<ul>';
					tr+='<li>';
					tr+='<label>方案ID：</label>';
					tr+='<span>{0}</span>';
					tr+='</li>';
					tr+='<li>';
					tr+='<label>协议内容：</label>';
					tr+='<a href="'+basepath+'upload/tradeContract/{6}.html" target="_blank">操盘协议</a>';
					tr+='</li>';
					tr+='<li>';
					tr+='<label>保全ID：</label>';
					tr+='<span>'+this.saveid+'</span>';
					tr+='</li>';
					tr+='<li>';
					tr+='<label>保全时间：</label>';
					tr+='<span>'+this.savedate+'</span>';
					tr+='</li>';
					tr+='<li>';
					tr+='<label>数字指纹：</label>';
					tr+='<span>'+this.seckey+'</span>';
					tr+='</li>';
					tr+='<li>';
					tr+='<label>协议保全：</label>';
					
					tr+='<a href="javascript:viewContract('+this.saveid+')">查看保全</a>';
					tr+='</li>';
					tr+='</ul>';
					tr+='</td>';
					tr+='</tr>';
					var starttime=getFormatDateByLong(this.starttime,'yyyy-MM-dd');					
					table+=$.format(tr,this.programNo,$.formatMoney(this.totalLeverMoney),$.formatMoney(this.leverMoney),this.feeDay,this.apr,starttime,this.programNo);					
				});
				$('#program_table').html("<tbody>"+table+"</tbody>");
			} else {
				showMsgDialog("系统错误",result.message);
			}
		}, "json");	
	}
	
	/* 账户管理费*/
	var fee= function(type,groupId){
		$.post(basepath + "uhkstock/detail.json", {type:type,groupId:groupId,ajax:1}, function(result) {
			if (result.success) {	
				var data=result.obj;
				var sumManage=result.data.sumManage.toFixed(2);
				var sumInterest=result.data.sumInterest.toFixed(2);
				
				var head='<ol class="uc_tdit_title">';
				head+='<li>支付时间</li>';
				head+='<li>账户管理费(￥)</li>';
				head+='<li>总操盘利息(￥)</li>';
				head+='<li>支付状态</li>';
				head+='</ol>';
					
				var tr='<ol>';
				tr+='<li>{0}</li>';
				tr+='<li>{1}</li>';
				tr+='<li>{2}</li>';
				tr+='<li>{3}</li>';
				tr+='</ol>';
				var table="";
				$.each(data, function(){
					var manage="&nbsp;";
					var interest="&nbsp;";
					if(this.type==11){
						interest=-this.money+"元";
					}else if(this.type==12){
						manage=-this.money+"元";
					}
					var paytime=getFormatDateByLong(this.uptime,'yyyy-MM-dd');
					table+=$.format(tr,paytime,manage,interest,this.payStatusValue);				
				});
				$('#fee').empty();
				$('#fee').append(head+table);
				$('#sumManage').text(-sumManage);
				$('#sumInterest').text(-sumInterest);
			} else {
				showMsgDialog("系统错误",result.message);
			}
		}, "json");	
	}
	
	//列表切换【子方案列表、管理费列表、交易账户】
	$('.uc_paynav li a').each(function() {
		$(this).click(function() {
			$('.uc_paynav li a').removeClass('on');
			$(this).addClass('on');
			$('.box').hide();
			var value=$(this).attr('data');
			$('.box'+value).show();
			var groupId=$('#group_id').text();
			if(value==1){
				program(value,groupId);
			}else if(value==3){
				fee(value,groupId);	
			}
		});
	});
	
	//终结方案按钮点击事件
	var stopBtnFun= function() {
		var groupId=$('#group_id').text();
		//检测是否已经提交过终结方案申请
		$.ajax({url : basepath+"uhkstock/checkEndOfProgram.json",async : false,data:{groupId:groupId,ajax:1}, type : "POST", dataType : "json",  
	        success : function(result) {
	        	if(result.success) {
	        		if(result.message == 'nofinddata'){  //未找到该方案
	        			showMsgDialog("提示",'未找到该方案信息，无法终结该方案！');
	        		} else if(result.message == 'finalized') { //已终结
	        			clickCloseRefresh("提示","该方案已终结成功，不能重复终结！");
	        		} else if(result.message == 'auditing') {  //终结中
	        			clickCloseRefresh("提示",'该方案正在“终止操盘”的审核状态，请耐心等待！');
	        		} else {  //操盘中
	        			var tds = result.data.tradeDays;
	        			var parities = result.data.parities;
	        			showApplyHKEndTradeBox(groupId, tds,parities);   //打开终结方案窗口
	        		}
				}else{
					showMsgDialog("提示",result.message);
				}
	        }
		});
	};
	//追加保证金按钮点击事件
	var addBtnFun= function() {
		var groupId=$('#group_id').text();
		showRemarginBox(groupId);
	};
	//终结按钮绑定事件
	$('.ul_td_stopbtn').bind('click', stopBtnFun);	
	
	//追加保证金按钮绑定事件
	$('.ul_td_addBondbtn').bind('click', addBtnFun);
	
	
	//默认点击列表切换
	$('#program').click();
	
	$('.fl_uc_trade .fl_navtitle .close').click(function() {
		$('.fl_box').hide();
		$('.fl_mask').hide();
	});
	
	/* 添加保证金 */
	var addBond= function(addMoney,groupId){
		$('#addBond').html('添加中......');
		$.post(basepath + "uhkstock/addBond.json", {addMoney:addMoney,groupId:groupId,ajax:1}, function(result) {
			if (result.success) {
				$('.fl_box').hide();
				$('.fl_mask').hide();
				clickCloseRefresh("提示","追加保证金申请成功，系统会尽快处理！");
			} else {
				$('#addBond').css("color","").html('确定').bind('click', addBondFun);
				showMsgDialog("提示",result.message);
			}
		}, "json");	
		
	}
	var addBondFun= function(){
		var addMoney=Number($('#addMoney').val());
		var groupId=$('#group_id').val();
		$('#addBond').css("color","#CCC").unbind("click");
		addBond(addMoney,groupId);
	}
	var addBondNext= function(){
		var minAddMoney=Number($('#minAddMoney').val());
		var maxAddMoney=Number($('#maxAddMoney').val());
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
			showMsgDialog("提示","追加金额最少为"+minAddMoney+"元。");
			$('#addMoney').val(minAddMoney);
			var dollar = Math.floor(minAddMoney * rate*100)/100;
			$("#convertDollar").text(dollar);
			return false;
		}
		if(addMoney>maxMoney){
			showMsgDialog("提示","您的余额为"+maxMoney+"元。");
			$('#addMoney').val(maxMoney);
			var dollar = Math.floor(maxMoney * rate*100)/100;
			$("#convertDollar").text(dollar);
			return false;
		}
		if(addMoney>maxAddMoney){
			showMsgDialog("提示","追加金额最大为"+maxAddMoney+"元。");
			$('#addMoney').val(maxAddMoney);
			var dollar = Math.floor(maxAddMoney * rate*100)/100;
			$("#convertDollar").text(dollar);
			return false;
		}
		 var moneyreg=/^[0-9]+([.]{1}[0-9]{1,2})?$/;
		 if(!moneyreg.test(addMoney)){
			 showMsgDialog("提示","请输入正确的金额,最小金额为分。"); 
				return false; 
		 }
			$('.fl_uc_trade').hide();
			$('.fl_uc_trade2').show();
		
	}
	
	
	// 追加保证金跳转下一步
	$('#addBondNext').bind('click', addBondNext);
	// 追加保证金
	$('#addBond').bind('click', addBondFun);
	
	
	
});
//打开追加保证金窗口
function showRemarginBox(gid) {
	$.ajax({
		type:'post',
		url:basepath + 'uhkstock/getRemarginInfo',
		data:{
			'gid' : gid,
			'ajax': 1
		},
		dataType:'json',
		success:function(result) {
			if(result.success) {
				var _balance = result.data.balance,_minAddMoney = result.data.minAddMoney,_maxAddMoney = result.data.maxAddMoney,_rate = result.data.rate;
				rate = Math.floor(_rate*10000)/10000;
				var boxStr = '<li>' +
						'<label>账户资金：</label>' +
						'<input type="hidden" id="balance" value="'+_balance+'"/>' +
						'<input type="hidden" id="group_id" value="'+gid+'"/>' +
						'<span><i>'+_balance.toFixed(2)+'</i>元<a href="'+basepath+'pay/payinfo" target="_blank">&nbsp;&nbsp;去充值&gt;&gt;</a></span>' +
					'</li>' +
					'<li>' +
						'<label>追加金额：</label>' +
						'<input id="minAddMoney" type="hidden" value="'+_minAddMoney+'"> ' +
						'<input id="maxAddMoney" type="hidden" value="'+_maxAddMoney+'"> ' +
						'<input id="addMoney" type="text" maxlength="8" value="0">' + 
						'<span>元</span>'+
					'</li>'+
					'<li>' +
						'<label></label>' +
						'<span><i id="convertDollar">0.00</i>港元</span>' +						
					'</li>'+
					'<li style="border-top: 1px dotted #ccc;margin: 10px 0 -40px;">' +
					'<label style="margin-left:60px;">当前结算汇率：</label>' +
					'<span>1人民币元='+rate+'港元</span>' +
				'</li>';
				$('.fl_uc_trade .fl_uc_list').html(boxStr);
				$('.fl_uc_trade').show();
				$('.fl_mask').show();
				$("#addMoney").bind('input propertychange',function(){
					var rmb = $(this).val();
					var dollar = Math.floor(rmb * rate*100)/100;
					$("#convertDollar").text(dollar);
				});
			}else{
				showMsgDialog("提示",result.message);
			}
		}
	});
}




//打开终结方案窗口
function showApplyHKEndTradeBox(groupId, tds,parities) {
	$("#applyHKEndTradeBtn").attr("status",true);
	$("#applyHKEndTradeBtn").attr("data_no",groupId);
	$("#applyHKEndTradeBtn").attr("data_tds",tds);
	$("#parities").html(parities);
	$("#applyHKEndTradeBtn").text("确定");
	$('#applyHKEndTrade').show();
	$('.fl_mask').show();
};

//终结方案
$("#applyHKEndTradeBtn").live('click',function(){
	var $this = $(this);
	var groupId=$this.attr('data_no');
	if($(this).attr("status") == "true"){
		$this.attr("status",false);
		$this.text("正在提交中...");
		$.post(basepath + "uhkstock/endOfProgram.json", {groupId:groupId,ajax:1}, function(data) {
			if (data.success) {
				$this.attr("status",true);
				$this.text("确定");
				if(data.message!="" && data.message!=null){
					closeWindow('#applyHKEndTrade');
					if(data.message == "notFindData"){
						showMsgDialog("提示","未找到该方案数据！");
						return;
					}else if(data.message == "auditing"){
						clickCloseRefresh("提示","该方案正在“终止操盘”的审核状态，请耐心等待。");
						return;
					}else if(data.message == "finalized"){
						clickCloseRefresh("提示","该方案已经完结，不能重复提交。");
						return;
					}else{
						showMsgDialog("提示","该方案提交终结申请失败！");
						return;
					}
				}else{
					$this.attr("status",false);
					closeWindow('#applyHKEndTrade');
					clickCloseRefresh("提示","申请成功。");
				}
			} else {
				$this.attr("status",true);
				$this.text("确定");
				showMsgDialog("提示","系统繁忙，请重试......");
			}
		}, "json");
	}
});
	
//关闭终结方案
$("#hkEndTrade_canceBtn").live('click',function(){
	if($("#applyHKEndTradeBtn").attr("status") == "true"){
		closeWindow('#applyHKEndTrade');
	}
});

/**
 * 
 * @param jqueryFind
 */
function closeWindow(jqueryFind) {
	$(".fl_mask").hide();
	$(jqueryFind).hide();
};

function showContractsaveInfo(programNo){
	$("#"+programNo).show();
}

function hideContractsaveInfo(programNo){
	$("#"+programNo).hide();
}

function viewContract(id){
	document.forms["showContract"].action=basepath+"contractsave/viewInfo?saveId="+id;
	document.forms["showContract"].submit();
}

/**
 * 
 * @param jqueryFind
 */
function closeWindow(jqueryFind) {
	$(".fl_mask").hide();
	$(jqueryFind).hide();
};
