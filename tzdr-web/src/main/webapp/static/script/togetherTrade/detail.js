$(document).ready(function() {
	$('.navlist li a').removeClass('on');
	$("#nav_my").addClass("on");
	
	$('.uc_bdsb_add').click(function() {
		$('.fl_uc_trade').show();
		$('.fl_mask').show();
	});
	// 追加保证金跳转下一步
	$('#addBondNext').bind('click', addBondNext);
	// 确认追加保证金
	$('#addBond').bind('click', addBondFun);
	var groupId=$('#group_id').text();	

	fee(groupId);	
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
	
	$('.uc_bdsb_end').bind('click', stopBtnFun);	
	
	$('#program').click();
	
	$('.uc_sidebar').find("div.uc_nav ul a").each(function(){
		$(this).removeClass('on');
 	});
	$("#together").parent().addClass('on');
});
	
	
	/*============== 追加保证金开始 ==============*/
	var addBond= function(addMoney,groupId){
		$('#addBond').html('添加中......');
		$.post(basepath + "usertogether/addBond.json", {addMoney:addMoney,groupId:groupId,ajax:1}, function(result) {
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
	/* 账户管理费*/
	var fee= function(groupId){
		$.post(basepath + "usertogether/detail.json", {groupId:groupId,ajax:1}, function(result) {
			if (result.success) {	
				var data=result.obj;
//				var sumManage=result.data.sumManage.toFixed(2);
//				var sumInterest=result.data.sumInterest.toFixed(2);
					
				var tr='<tr>';
				tr+='<td>{0}</td>';
				tr+='<td>{1}</td>';
				tr+='<td>{2}</td>';
				tr+='<td>{3}</td>';
				tr+='</tr>';
				var table="";
				$.each(data, function(){
					var manage="0元";
					var interest="0元";
					if(this.type==11){
						interest=-this.money+"元";
					}else if(this.type==12){
						manage=-this.money+"元";
					}
					var paytime=getFormatDateByLong(this.uptime,'yyyy-MM-dd');
					table+=$.format(tr,paytime,interest,manage,this.payStatusValue);				
				});
				$('#fee').empty();
				$('#fee').append(table);
			} else {
				showMsgDialog("系统错误",result.message);
			}
		}, "json");	
	}
	var addBondFun= function(){
		var addMoney=Number($('#addMoney').val());
		var groupId=$('#group_id').text();
		$('#addBond').css("color","#CCC").unbind("click");
		addBond(addMoney,groupId);
	}
	
	var addBondNext = function() {
		var minAddMoney=Number($('#minAddMoney').val());
		var maxAddMoney=Number($('#maxAddMoney').val());
		var addMoney=Number($('#addMoney').val());
		var acountbalance=Number($('#balance').val());
		if(!addMoney){
			showMsgDialog("提示","请输入追加保证金金额。");
			return false;
		}
		if(!$.isNumeric(addMoney)){
			showMsgDialog("提示","请输入正确的金额。");
			return false;
		}
		var moneyreg=/^[0-9]+([.]{1}[0-9]{1,2})?$/;
		if(!moneyreg.test(addMoney)){
			showMsgDialog("提示","请输入正确的金额,最小金额为分。"); 
			return false; 
		}
		if(addMoney<minAddMoney){
			// showMsgDialog("提示","追加金额最少为总操盘资金的1%。");
			showMsgDialog("提示","追加金额最低"+minAddMoney+"元");
			$('#addMoney').val(minAddMoney);
			return false;
		}
		if(addMoney>maxAddMoney){
			showMsgDialog("提示","追加金额最高100万元。");
			$('#addMoney').val("");
			return false;
		}
		if(addMoney>acountbalance){
			showMsgDialog("提示","余额不足，请立即充值。");
			$('#addMoney').val("");
			return false;
		}
		
		$('.fl_uc_trade').hide();
		$('.fl_uc_trade2').show();
	}
	
	
	/*============== 追加保证金结束 ==============*/
	
	
	
	var stopBtnFun= function() {
		var groupId=$('#group_id').text();
		//检测是否已经提交过终结方案申请
		$.ajax({url : basepath+"usertogether/checkEndOfProgram.json",async : false,data:{groupId:groupId,type:2,ajax:1}, type : "POST", dataType : "json",  
	        success : function(result) {  
	        	if(result.success) {
	        		var tds = result.data.tradeDays;
	        		if(result.data.tradeStatus == 'finalized') { //已终结
		        			clickCloseRefresh("提示","该方案已终结成功，不能重复终结！");
		        		} else if(result.data.tradeStatus == 'auditing') {  //终结中
		        			showMsgDialog("提示",'该方案正在“终止操盘”的审核状态，请耐心等待！');
		        		} else {  //操盘中
		        			endOfProgram(2, groupId, tds);
		        		}
				}else{
					showMsgDialog("提示",result.message);
				}
	        }
		});
	}
	
	
	
	function endOfProgram(feeType, groupId, tds){
		if(confirm(tds ? '1.您还有'+ tds +'天利息，中途终止操盘，剩余利息将不予退还。2.若您的股票账户仍有股票未卖出，您的终结申请将不能通过审核。请问是否确认终止操盘？' 
					   : '若您的股票账户仍有股票未卖出，您的终结申请将不能通过审核。请确认终止操盘？')) {
			$('.ul_td_stopbtn').css("color","#CCC").unbind("click");
			$.post(basepath + "usertogether/endOfProgram.json", {groupId:groupId,ajax:1}, function(result) {
				if (result.success) {
					clickCloseRefresh("提示","申请成功，我们会尽快处理。");
				} else {
					$('.ul_td_stopbtn').css("color","").bind('click', stopBtnFun);
					showMsgDialog("提示",result.message);
				}
			}, "json");	
		}
	}

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


