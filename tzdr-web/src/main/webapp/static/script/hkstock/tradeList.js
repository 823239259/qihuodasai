var countOfCurrentPage = 6;
var currentPage = 0;
var rate = 0;
function tentative(){
	showMsgDialog("提示","系统升级中，该功能暂停使用。");
	return;
}

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
						''+
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
$(document).ready(function() {
	$("#oAccount").find("a").removeClass("on");
	$('.navlist li a').removeClass('on');
	$("#nav_my").addClass("on");
	var hover = function(){
		$('.ut_tlbtn_detail').each(function() {
			var btn=$(this);
			btn.hover(function() {
				btn.children('span').show();
			}, function() {
				btn.children('span').hide();
			});

			btn.children('.uc_tlist_link').toggle(function(){	
				btn.parents('ul').next('.uc_tlpromt').show();
				},function(){
				btn.parents('ul').next('.uc_tlpromt').hide();
				});
		});
	}
	
	$('.uc_sidebar').find("div.uc_nav ul a").each(function(){
		$(this).removeClass('on');
 	});
	$("#hkstock").parent().addClass('on');
	
	var list=function(type,countOfCurrentPage,currentPage){
		$('#content').empty();
		$.post(basepath + "uhkstock/list.json", {type:type,countOfCurrentPage:countOfCurrentPage,currentPage:currentPage}, function(result) {
			if (result.success) {	
				$('#content').empty();
				var data=result.obj.pageResults;
				var total = result.obj.totalCount;   
				var table='<div class="uc_tlisttitle">';
				table+='<span class="fl">方案编号：{0}<i></i></span>';
				table+='<span class="fr">开始时间：{1}<i>结束时间：{2}</i></span>';	
				table+='</div>';
				table+='<ul class="uc_tlist">';
				table+='<li class="uc_tl150">';
				table+='	<h4 class="uc_tlist_title">总操盘资金(HK$)</h4>';
				table+='	<p class="uc_tlist_con">{3}港元</p>';
				table+='</li>';
			    table+='<li class="uc_tl120">';
				table+='	<h4 class="uc_tlist_title">操盘保证金(￥)</h4>';
				table+='	<p class="uc_tlist_con">{4}元</p>';
				table+='</li>';
			    table+='<li class="uc_tl120">';
				table+='	<h4 class="uc_tlist_title">追加保证金(￥)</h4>';
				table+='	<p class="uc_tlist_con">{5}元</p>';
				table+='</li>';
				table+='<li class="uc_tl120">';
				table+='	<h4 class="uc_tlist_title">方案盈亏(￥)</h4>';
				table+='	<p class="uc_tlist_con">{6}元</p>';
				table+='</li>';
				
				table+='<li class="uc_tl120">';
				table+='	<h4 class="uc_tlist_title">方案状态</h4>';
				table+='	<p class="uc_tlist_con">{7}</p>';
				table+='</li>';
				table+='<li class="uc_tlbtn" style="margin-left:20px;">';
				table+='{8}<div class="ut_tlbtn_detail" style="margin:10px 0;"><a href="'+basepath+'uhkstock/detail/{0}" class="uc_tlbtn_btn" id="opt_btn{0}">操盘详细</a>';
				
				var detail='	<span style="display:none;" class="uc_tlist_link">交易账户</span>';
				detail+='<span style="display:none;"><a href="javascript:endProject(\'{0}\');" class="uc_tlist_stopbtn" >终结方案</a></span></div>';
				detail+='</li>';
				detail+='</ul>';
				
				var last='	<div class="uc_tlpromt" style="display:none;">	';
				last+='		<i class="uc_ucp_icon"></i>';
				last+='		<div class="uc_tlpromtbox">		';			
				last+='			<p>资金风控：亏损警戒线<i style="color: red;">{0}</i> 元，亏损平仓线<i style="color: red;">{1}</i>元</p>';
				last+='			<p>交易账户：<span style="color: red;">{2}</span></p>';
				last+='			<p>交易密码：<span style="color: red;">{3}</span>(为了您的资金安全，请妥善保管好密码)</p>';
				last+='			<p> 交易软件：<a href="'+basepath+'help?tab=software&leftMenu=1" target="_blank">请点击这里按照说明安装交易软件</a></p>';
				last+='		</div>';
				last+='	</div>';
				
				$.each(data, function(){				
					
					//方案盈亏
					var totalAccrual="——";
					if(this.status != 1 && this.auditEndStatus == 1){
						totalAccrual=$.format('<i class="color4">{0}</i>',this.totalAccrual == 0 ? '0.00' : $.formatMoney(this.totalAccrual,2));
						if(this.totalAccrual<0){
							totalAccrual=$.format('<i class="color3">{0}</i>',this.totalAccrual == 0 ? '0.00' : $.formatMoney(this.totalAccrual,2));
						}
					}
					
					//方案状态
					var tradeStatus = this.tradeStatus;
					if(0 == this.status){ //开户    
						tradeStatus = '开户中';  
					}else if(1 == this.status){  //操盘  
						tradeStatus = '操盘中';
					}else{  //终结    
						tradeStatus = this.auditStatus == 2 ?'开户失败':'已完结'; 
					} 
					var _remarginbtn = 1 == this.status ? '<a href="javascript:;" onclick="'+(this.feeType == 0 || this.feeType == 1  ? 'tentative();' : 'showRemarginBox(\''+this.groupId+'\');')+'" class="uc_tllist_add">追加保证金</a>' : '';
					var head=$.format(table,this.groupId,getFormatDateByLong(this.starttime,'yyyy-MM-dd'),getFormatDateByLong(this.endtime,'yyyy-MM-dd'),$.formatMoney(this.totalOperateMoney),$.formatMoney(this.totalLeverMoney),$.formatMoney(this.totalAppendLeverMoney),totalAccrual,tradeStatus,_remarginbtn);
					
					var content=""
					if(this.status == 1){
						content=$.format(detail,this.groupId);
					}
					
					var foot=""
					if(this.status == 1){
						foot = $.format(last,$.formatMoney(this.warning),$.formatMoney(this.open),this.accountNo,this.password);
					}
					
					$('#content').append('<div class="uc_tdone">'+head+content+foot+'</div>');
					
					if(this.status == 2 && this.auditStatus == 2){   //开户失败，去掉按钮
						$('#opt_btn'+this.groupId).parent().remove();
					}
					
					if(this.status != 1 && !(this.status == 2 && this.auditStatus == 2)){  //非操盘中，只能进入详情
						$('#opt_btn'+this.groupId).removeClass('uc_tlbtn_btn').addClass('uc_tlbtn_btn_no');
					}
				});
				
				hover();  //下拉按钮
				
				//分页
				if($("#Pagination").html() == ''){ 
					$("#Pagination").pagination(total, {   
						'items_per_page'      : countOfCurrentPage,
						'num_edge_entries'    : 2,   
						'prev_text'           : "上一页",   
						'next_text'           : "下一页",   
						'callback'            : pageselectCallback   
					});   
				} 
			} else {
				alert(result.message);
			}
		}, "json");	
	}
	
	$('.uc_paynav li').click(function() {
		$('.uc_paynav li a.on').removeClass('on');
		$(this).find('a').addClass('on');
		var type=$(this).attr('data');
		if(type=="all"){
			$("#Pagination").html("");
			list(type,countOfCurrentPage,1);	
		}
	});	
	
	function getDataList(index){ 
		var type=$('.uc_paynav li a.on').parent().attr('data');
		list(type,countOfCurrentPage,index+1);
	}
	
	$('.uc_paynav li a.on').click();
	
	//分页回调
	function pageselectCallback(page_index, jq){
		getDataList(page_index); 
	}
	$('.fl_uc_cancelbtn').each(function() {
		$(this).click(function() {
			$('.fl_box').hide();
			$('.fl_mask').hide();
		});
	});
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

//终结方案-检测该方案是否已经提交过
var endProject = function(groupId){
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
