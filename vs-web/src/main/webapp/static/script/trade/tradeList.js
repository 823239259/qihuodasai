var countOfCurrentPage = 5;
var currentPage = 0;
var endProject = function(groupId,feeType){
	
	if(feeType == 0 || feeType == 1){
		tentative();
		return;
	}
	
	//检测是否已经提交过终结方案申请
	$.ajax({url : basepath+"trade/checkEndOfProgram.json",async : false,data:{groupId:groupId,type:feeType,ajax:1}, type : "POST", dataType : "json",  
        success : function(result) {
        	if(result.success) {
        		var tds = result.data.tradeDays;
        		if(2 == feeType) {
	        		if(result.data.tradeStatus == 'finalized') { //已终结
	        			clickCloseRefresh("提示","该方案已终结成功，不能重复终结！");
	        		} else if(result.data.tradeStatus == 'auditing') {  //终结中
	        			showMsgDialog("提示",'您已提交过终结方案申请不能再次提交！');
	        		} else {  //操盘中
	        			endOfProgram(feeType, groupId, tds);
	        		}
	        	} else {
	        		endOfProgram(feeType, groupId, tds);
	        	}
			}else{
				showMsgDialog("提示",result.message);
			}
        }
	});
}

function endOfProgram(feeType, groupId, tds){
	if(confirm(tds ? '您还有'+ tds +'天利息，中途终止操盘，剩余利息将不予退还，请问是否确认终止操盘？' 
			   : '你确认要终结方案么？')) {
		$.post(basepath + "trade/endOfProgram.json", {groupId:groupId,ajax:1}, function(result) {
			if (result.success) {
				clickCloseRefresh("提示","终结方案成功。");
			} else {
				showMsgDialog("无法终结方案",result.message);
			}
		}, "json");	
	}
}

function tentative(){
	showMsgDialog("提示","系统升级中，该功能暂停使用。");
	return;
}

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
				var _balance = result.data.balance, _o = result.data.trade, _totalOperateMoney = _o.totalOperateMoney, _activityType = _o.activityType, _minAddMoney = result.data.minAddMoney, _maxAddMoney = result.data.maxAddMoney;
				var boxStr = '<li>' +
						'<label>账户余额：</label>' +
						'<input type="hidden" id="balance" value="'+_balance+'" />' +
						'<input type="hidden" id="group_id" value="'+gid+'" />' +
						'<span><i>'+_balance.toFixed(2)+'</i>元<a href="'+basepath+'pay/payinfo" target="_blank">&nbsp;&nbsp;去充值&gt;&gt;</a></span>' +
					'</li>' +
					/*
					'<li>' +
						'<label>总操盘资金：</label>' +
						'<span><i>'+_totalOperateMoney.toFixed(2)+'</i>元</span>' +
					'</li>' +
					*/
					'<li>' +
						'<label>追加金额：</label>' +
						'<input type="hidden"  id="additionalMargin" value="'+_activityType+'" />' +
						'<input id="minAddMoney" type="hidden" value="'+_minAddMoney+'" /> ' +
						'<input id="maxAddMoney" type="hidden" value="'+_maxAddMoney+'" /> ' +
						'<input id="addMoney" type="text" maxlength="9" value="'+_minAddMoney+'" />' + 
						'<span>元</span>' +
					'</li>';
				$('.fl_uc_trade .fl_uc_list').html(boxStr);
				$('.fl_uc_trade').show();
				$('.fl_mask').show();
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
	
	var list=function(type,countOfCurrentPage,currentPage){
		$('#content').empty();
		$.post(basepath + "trade/list.json", {type:type,countOfCurrentPage:countOfCurrentPage,currentPage:currentPage}, function(result) {
			if (result.success) {	
				$('#content').empty();
				var data=result.obj.pageResults;
				var total = result.obj.totalCount;   
				var table='<div class="uc_tlisttitle">';
				table+='<span class="fl">方案编号：{0}<i>{10}</i></span>';
				table+='<span class="fr">开始时间：{1}<i>结束时间：{2}</i></span>';	
				table+='</div>';
				table+='<ul class="uc_tlist">';
				table+='<li class="uc_tl100">';
				table+='	<h4 class="uc_tlist_title">总操盘资金</h4>';
				table+='	<p class="uc_tlist_con">{3}元</p>';
				table+='</li>';
				table+='<li class="uc_tl90">';
				table+='	<h4 class="uc_tlist_title">操盘金额</h4>';
				table+='	<p class="uc_tlist_con">{4}元</p>';
				table+='</li>';
			    table+='<li class="uc_tl90">';
				table+='	<h4 class="uc_tlist_title">操盘保证金</h4>';
				table+='	<p class="uc_tlist_con">{5}元</p>';
				table+='</li>';
				table+='<li class="uc_tl90">';
				table+='	<h4 class="uc_tlist_title">追加保证金</h4>';
				table+='	<p class="uc_tlist_con">{6}元</p>';
				table+='</li>';
				table+='<li class="uc_tl100">';
				table+='	<h4 class="uc_tlist_title">方案盈亏</h4>';
				table+='	<p class="uc_tlist_con">{7}</p>';
				table+='</li>';
				
				table+='<li class="uc_tl80">';
				table+='	<h4 class="uc_tlist_title">类型</h4>';
				table+='	<p class="uc_tlist_con">{9}</p>';
				table+='</li>';
				
				table+='<li class="uc_tl80">';
				table+='	<h4 class="uc_tlist_title">方案状态</h4>';
				table+='	<p class="uc_tlist_con">{8}</p>';
				table+='</li>';
				table+='<li class="uc_tlbtn">';
				table+='{11}<div class="ut_tlbtn_detail"><a href="'+basepath+'trade/detail/{0}" class="uc_tlbtn_btn" id="opt_btn{0}">操盘详细</a>';
				
				var detail='	<span style="display:none;" class="uc_tlist_link">交易账户</span>';
				detail+='<span style="display:none;"><a href="javascript:endProject(\'{5}\',{6});" class="uc_tlist_stopbtn" >终结方案</a></span></div>';
				detail+='</li>';
				detail+='</ul>';
				
				var last='	<div class="uc_tlpromt" style="display:none;">	';
				last+='		<i class="uc_ucp_icon"></i>';
				last+='		<div class="uc_tlpromtbox">		';			
				last+='			<p>重点提醒：交易前请线阅读 <a href="'+basepath+'help?tab=configuration&leftMenu=1" target="_blank">《股票操盘规则说明》</a></p>';
				last+='			<p>资金风控：亏损补仓线<i>{0}</i> 元，亏损平仓线<i>{1}</i>元</p>';
				last+='			<p>开户券商：<span>{2}</span></p>';
				last+='			<p>交易账户：<span>{3}</span></p>';
				last+='			<p>交易密码：<span>{4}</span>(为了您的资金安全，请妥善保管好密码)</p>';
				last+='			<p>{8}</p>';
				//last+='			<p> 交易软件：<a href="'+basepath+'help?tab=software&leftMenu={6}" target="_blank">{7}</a></p>';
				last +="<p> 交易软件：1.若您的交易帐号以“<i style='color: red;'>6236</i>”开头，需使用钱江版交易软件，请“<a href='http://www.ihoms.com/file/xunlei/downloadxunlei/HOMS_QiJian/ZhengShi/DuLi/homs_duli.exe?fileCode=10&sourceType=4' target='_blank'>立即下载</a>”；<br><em class='uc_tlpromtsp2'>2.若您的交易帐号以“<i style='color: red;'>8709</i>”开头，需使用涌金版交易软件，请“<a href='http://www.mzkqh.com/static/apply/HOMS_YONGJINBAN_jiaoyiduan.exe' target='_blank'>立即下载</a>”；</em><em class='uc_tlpromtsp2' style='display:block;'>3.若您的交易帐号以“<i style='color: red;'>15703</i>”开头，需使用钱隆TTS股票交易系统，请“<a href='"+basepath+"+help?tab=software&leftMenu=1'>立即下载</a>”。</em></p>";
				//last+='			<p>&nbsp&nbsp提示：1.停牌股处理规则，<a href="'+basepath+'/help?tab=rule&leftMenu=3" target="_blank">点击查看</a><br>&nbsp&nbsp&nbsp&nbsp&nbsp2.平仓后方案会继续保留并产生费用，停止产生费用请终结方案。</p>';
				last+='			<p><em class="uc_tlpromtsp">提示：1.停牌股处理规则，<a href="'+basepath+'/help?tab=configuration&leftMenu=6" target="_blank">点击查看</a><br></em><em class="uc_tlpromtsp2">2.平仓后方案继续保留并产生费用，停止产品费用请终结方案</em></p>';
				last+='			<a href="javascript:void(0)" class="uc_tlp_up"></a>';
				last+='		</div>';
				last+='	</div>';
				
				$.each(data, function(){				
					
					var totalAccrual="<a style='display:block;' href='javascript:void(0);' id='tradeAccrual' data-id='"+this.groupId+"'>点击查看</a><img style='display:none;' src='"+basepath+"static/images/uc/loading.gif'/>";
					if(this.status != 1 && 2 != this.feeType){
						totalAccrual=$.format('<i class="color4">{0}</i>元',this.totalAccrual == 0 ? '0.00' : $.formatMoney(this.totalAccrual,2));
						if(this.totalAccrual<0){
							totalAccrual=$.format('<i class="color3">{0}</i>元',this.totalAccrual == 0 ? '0.00' : $.formatMoney(this.totalAccrual,2));
						}
					}
					
					var tradeStatus = this.tradeStatus;
					var feelTypeName = '钱江版';
					if(this.feeType==0 || this.feeType==1){
						feelTypeName = '钱江版';
					}else if(this.feeType==2){
						totalAccrual = '——';
						feelTypeName = '涌金版';
					}else if(this.feeType==3){
						totalAccrual = '——';
						feelTypeName = '同花顺';
					}
					
					var _iscNo = this.insuranceNo ? '保险单号：<a href="#" target="_balnk">'+ this.insuranceNo +'</a>':'';
					var softwareDownload = '独立委托版';
					if(2 == this.feeType || 3==this.feeType){
						softwareDownload = (3==this.feeType)?'同花顺软件下载':'涌金版软件下载';
					}
					
					if(0 == this.status){ //开户    
						feelTypeName = '';
						tradeStatus = '开户中';  
					}else if(1 == this.status){  //操盘  
						tradeStatus = '操盘中';//this.auditEndStatus == 0 ? '终结中' : this.auditEndStatus == 2 ? '终结失败':'操盘中';
					}else{  //终结    
						if(this.auditStatus == 2){feelTypeName = '';}
						tradeStatus = this.auditStatus == 2 ?'开户失败':'已完结'; 
						totalAccrual = this.auditStatus == 2 ?'——':$.format('<i class="color'+(this.totalAccrual<0 ? 3 : 4)+'">{0}</i>元',(this.totalAccrual == 0 ? '0.00' : $.formatMoney(this.totalAccrual,2))); 
					} 
					
					var _remarginbtn = 1 == this.status ? '<a href="javascript:;" onclick="'+(this.feeType == 0 || this.feeType == 1  ? 'tentative();' : 'showRemarginBox(\''+this.groupId+'\');')+'" class="uc_tllist_add">追加保证金</a>' : '';
					var head=$.format(table,this.groupId,getFormatDateByLong(this.starttime,'yyyy-MM-dd'),getFormatDateByLong(this.endtime,'yyyy-MM-dd'),$.formatMoney(this.totalOperateMoney),$.formatMoney(this.totalLending),$.formatMoney(this.totalLeverMoney),$.formatMoney(this.totalAppendLeverMoney),totalAccrual,tradeStatus,feelTypeName,_iscNo, _remarginbtn);
					var content=""
					if(this.status==1){
						content=$.format(detail,$.formatMoney(this.warning),$.formatMoney(this.open),this.hsBelongBroker,this.account,this.password,this.groupId,this.feeType);
					}
					var foot=""
					if(this.status==1){
						foot = $.format(last,$.formatMoney(this.warning),$.formatMoney(this.open),this.hsBelongBroker,this.account,this.password,this.groupId,(this.feeType == 2?5:this.feeType==3?6:2),softwareDownload, _iscNo);
					}
					$('#content').append('<div class="uc_tdone">'+head+content+foot+'</div>');	
					if(this.status!=1){
						$('#opt_btn'+this.groupId).removeClass('uc_tlbtn_btn').addClass('uc_tlbtn_btn_no');
					}
				});
				hover();
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
	var needlist=function(){
		$('#content').empty();
		$.post(basepath + "trade/needlist.json", {}, function(result) {
			if (result.success) {
				$('#content').empty();
				var data=result.obj;				
				var table='<div class="uc_tlisttitle">';
				table+='<span class="fl">方案编号：{0}<i>{10}</i></span>';
				table+='<span class="fr">开始时间：{1}<i>结束时间：{2}</i></span>';
				table+='</div>';
				table+='<ul class="uc_tlist">';
				table+='<li class="uc_tl90">';
				table+='	<h4 class="uc_tlist_title">总操盘资金</h4>';
				table+='	<p class="uc_tlist_con">{3}元</p>';
				table+='</li>';
				table+='<li class="uc_tl90">';
				table+='	<h4 class="uc_tlist_title">操盘金额</h4>';
				table+='	<p class="uc_tlist_con">{4}元</p>';
				table+='</li>';
			    table+='<li class="uc_tl90">';
				table+='	<h4 class="uc_tlist_title">操盘保证金</h4>';
				table+='	<p class="uc_tlist_con">{5}元</p>';
				table+='</li>';
				table+='<li class="uc_tl90">';
				table+='	<h4 class="uc_tlist_title">追加保证金</h4>';
				table+='	<p class="uc_tlist_con">{6}元</p>';
				table+='</li>';
				table+='<li class="uc_tl100">';
				table+='<h4 class="uc_tlist_title">亏损补仓线</h4>';			
				table+='<p class="uc_tlist_con"><i class="color4">{7}</i>元</p>';				
				table+='</li>	';		
				table+='<li class="uc_tl90">';
				table+='	<h4 class="uc_tlist_title">方案盈亏</h4>';
				table+='	<p class="uc_tlist_con">{8}</p>';
				table+='</li>';
				table+='<li class="uc_tl90">';
				table+='	<h4 class="uc_tlist_title">方案状态</h4>';
				table+='	<p class="uc_tlist_con">{9}</p>';
				table+='</li>';
				table+='<li class="uc_tlbtn">';
				table+='	<a href="'+basepath+'trade/detail/{0}" class="uc_tlbtn_btn">操盘详细</a>';
				
				var detail='	<span style="display:none;" class="uc_tlist_link">交易账户</span>';
				detail+='<span style="display:none;"><a href="javascript:endProject(\'{5}\',{6});" class="uc_tlist_stopbtn" >终结方案</a></span>';
				detail+='</li>';
				detail+='</ul>';
				
				var last='	<div class="uc_tlpromt" style="display:none;">	';
				last+='		<i class="uc_ucp_icon"></i>';
				last+='		<div class="uc_tlpromtbox">		';			
				last+='			<p>重点提醒：交易前请线阅读 <a href="'+basepath+'help" target="_blank">《股票操盘规则说明》</a></p>';
				last+='			<p>资金风控：亏损补仓线<i>{0}</i> 元，亏损平仓线<i>{1}</i>元</p>';
				last+='			<p>开户券商：<span>{2}</span></p>';
				last+='			<p>交易账户：<span>{3}</span></p>';
				last+='			<p>交易密码：<span>{4}</span>(为了您的资金安全，请妥善保管好密码)</p>';
				last+='			<p>{6}</p>';
				//last+='			<p> 交易软件：<a href="'+basepath+'help?tab=software&leftMenu=2" target="_blank">独立委托版</a></p>';
				last +="<p> 交易软件：1.若您的交易帐号以“<i style='color: red;'>6236</i>”开头，需使用钱江版交易软件，请“<a href='"+basepath+"help?tab=software&leftMenu=2' target='_blank'>立即下载</a>”；<br><em class='uc_tlpromtsp2'>2.若您的交易帐号以“<i style='color: red;'>8709</i>”开头，需使用涌金版交易软件，请“<a href='"+basepath+"help?tab=software&leftMenu=5'  target='_blank'>立即下载</a>”；</em><em class='uc_tlpromtsp2' style='display:block;'>3.若您的交易帐号以“<i style='color: red;'>15703</i>”开头，需使用钱隆版交易软件，请先下载安装“<a href='http://www.microsoft.com/zh-CN/download/details.aspx?id=21'  target='_blank'>.net framework3.5</a>”，再下载安装“<a href='http://www.etradepark.com/load/pdf/钱隆TTS.exe'  target='_blank'>钱隆TTS</a>”。</em></p>";
				//last+='			<p>&nbsp&nbsp提示：1.停牌股处理规则，<a href="'+basepath+'/help?tab=rule&leftMenu=3" target="_blank">点击查看</a><br>&nbsp&nbsp&nbsp&nbsp&nbsp2.平仓后方案会继续保留并产生费用，停止产生费用请终结方案。</p>';
				last+='			<p><em class="uc_tlpromtsp">提示：1.停牌股处理规则，<a href="'+basepath+'/help?tab=rule&leftMenu=3" target="_blank">点击查看</a><br></em><em class="uc_tlpromtsp2">2.平仓后方案继续保留并产生费用，停止产品费用请终结方案</em></p>';
				last+='			<a href="javascript:void(0)" class="uc_tlp_up"></a>';
				last+='		</div>';
				last+='	</div>';
				$.each(data, function(){
					
					var totalAccrual=$.format('<i class="color4">{0}</i>元',this.totalAccrual == 0 ? '0.00' : $.formatMoney(this.totalAccrual));
					if(this.totalAccrual<0){
						totalAccrual=$.format('<i class="color3">{0}</i>元',this.totalAccrual == 0 ? '0.00' : $.formatMoney(this.totalAccrual));
					}
					
					var content="", 
						foot="", 
						_iscNo = this.insuranceNo ? '保险单号：<a href="#" target="_balnk">'+ this.insuranceNo +'</a>':'',
						head=$.format(table,this.groupId,getFormatDateByLong(this.starttime,'yyyy-MM-dd'),getFormatDateByLong(this.endtime,'yyyy-MM-dd'),$.formatMoney(this.totalOperateMoney),$.formatMoney(this.totalLeverMoney),$.formatMoney(this.totalLending),$.formatMoney(this.totalAppendLeverMoney),$.formatMoney(this.warning),totalAccrual,this.tradeStatus, _iscNo);
					if(this.status==1){
						content=$.format(detail,$.formatMoney(this.warning),$.formatMoney(this.open),this.hsBelongBroker,this.account,this.password,this.groupId,this.feeType);
					}
					if(this.status==1){
						foot=$.format(last,$.formatMoney(this.warning),$.formatMoney(this.open),this.hsBelongBroker,this.account,this.password,this.groupId, _iscNo);
					}
					$('#content').append('<div class="uc_tdone">'+head+content+foot+'</div>');	
					if(this.status!=1){
						$('#opt_btn'+this.groupId).removeClass('uc_tlbtn_btn').addClass('uc_tlbtn_btn_no');
					}
				});
				hover();
				//没有分页
				$("#Pagination").html("");
			} else {
				showMsgDialog("提示",result.message);
			}
		}, "json");	
	}
	
	$('.uc_paynav li').click(function() {
		$('.uc_paynav li a.on').removeClass('on');
		$(this).find('a').addClass('on');
		var type=$(this).attr('data');
		if(type=="need"){
			$("#Pagination").html("");
			needlist();
		}else{
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
	
	$('.uc_sidebar').find("div.uc_nav ul a").each(function(){
		$(this).removeClass('on');
 	});
	$("#stock").parent().addClass('on');
	
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
			$('.fl_mask').hide();
		});
	});
	$('.fl_box .fl_navtitle .close').each(function() {
		$(this).click(function() {
			$('.fl_box').hide();
			$('.fl_mask').hide();
		});
	});
	
	/*============== 追加保证金开始 ==============*/
	var addBond= function(addMoney,groupId){
		$('#addBond').html('添加中......');
		$.post(basepath + "trade/addBond.json", {addMoney:addMoney,groupId:groupId,ajax:1}, function(result) {
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
	
	// 追加保证金跳转下一步
	$('#addBondNext').bind('click', addBondNext);
	// 确认追加保证金
	$('#addBond').bind('click', addBondFun);
	/*============== 追加保证金结束 ==============*/
	
});