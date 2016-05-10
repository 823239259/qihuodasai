
//bShare分享  
	var iBShare = {  
		//初始化  
		init: function() {  
			var $shareBox = $(".bshare-custom");  
			//加载分享工具  
			var tools = '<a title="分享到新浪微博" class="bshare-sinaminiblog"></a>';  
			tools += '<a title="分享到微信" class="bshare-weixin" href="javascript:void(0);"></a>';
			tools += '<a title="分享到QQ好友" class="bshare-qqim" href="javascript:void(0);"></a>'; 
			//tools += '<a title="分享到腾讯微博" class="bshare-qqmb"></a>';    
			tools += '<a title="分享到QQ空间" class="bshare-qzone"></a>';  
			tools += '<a title="更多平台" class="bshare-more bshare-more-icon more-style-addthis"></a>';  
			
			$shareBox.append(tools);  
			//绑定分享事件  
			$shareBox.children("a").click(function() {  
				var parents = $(this).parent();  
				bShare.addEntry({  
					title: parents.attr("bshareTitle"),  
					url: parents.attr("bshareUrl"),  
					summary: parents.attr("bshareSummary"),  
					pic: parents.attr("bsharePic")  
				});  
			});  
		}
	};

$(document).ready(function() {
	
	var AddProgram=function(){
//		showMsgDialog("提示","追加操盘方案暂时关闭。");
		showMsgDialog("提示","系统升级中，该功能暂停使用。");
		return false;
	}
	
	$('.ul_td_tradebtn').css("color","#CCC").bind('click',AddProgram);	
	
	iBShare.init();    //初始化分享控件  
	
	
	$('.navlist li a').removeClass('on');
	$("#nav_my").addClass("on");
	$('.ul_td_tradebtn').hover(function() {
		$('.uc_tffl_trade').show();
	}, function() {
		$('.uc_tffl_trade').hide();
	});
	$('.ul_td_bailbtn').hover(function() {
		$('.uc_tdfl_bail').show();
	}, function() {
		$('.uc_tdfl_bail').hide();
	});
	$('.ul_td_stopbtn').hover(function() {
		$('.uc_tdfl_stop').show();
	}, function() {
		$('.uc_tdfl_stop').hide();
	});
	$('.ul_td_probtn').hover(function() {
		$('.uc_tdfl_pro').show();
	}, function() {
		$('.uc_tdfl_pro').hide();
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
		
	$('.ul_td_bailbtn').click(function() {
		var type = $(this).attr("data-feeType");
		if(type == 0 || type == 1){
			showMsgDialog("提示","系统升级中，该功能暂停使用。");
			return;
		}
		$('.fl_uc_trade').show();
		$('.fl_mask').show();
	});
	
	$('.ul_td_probtn').click(function() {
		showMsgDialog("提示","功能升级中，暂停提取利润。");
		// getTradingUserInfo(true); 暂停提取利润 modify by wuchaoliang 20150911
	});
	
	/* 方案列表 */
	var program= function(type,groupId){
		$.post(basepath + "trade/detail.json", {type:type,groupId:groupId,ajax:1}, function(result) {
			if (result.success) {			
				var data=result.obj;			
				var table="";
				$.each(data, function(i,dataobj){
					var tr=' <tr>';
					tr+='<td>{8}</td>';
					tr+='<td>{0}元</td>';
					tr+='<td>{1}元</td>';
					tr+='<td>{2}</td>';
					//tr+='<td>{3}元</td>';
					//tr+='<td>{4}元</td>';
					tr+='<td><i>{5}</i>元/天</td>';
					tr+='<td><i>{6}</i>元</td>';
					tr+='<td>{7}</td>';
					var gramNo="'"+this.programNo+"'";
					if((this.activityType==0||this.activityType==3) && $.trim(this.saveid)!=""){
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
					tr+='<span>{8}</span>';
					tr+='</li>';
					tr+='<li>';
					tr+='<label>协议内容：</label>';
					tr+='<a href="'+basepath+'upload/tradeContract/{8}.html" target="_blank">操盘协议</a>';
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
					table+=$.format(tr,$.formatMoney(this.totalLeverMoney),$.formatMoney(this.money),$.formatMoney(this.leverMoney),$.formatMoney(this.warning),$.formatMoney(this.open),this.feeDay,this.apr,starttime,this.programNo);					
				});
				$('#program_table').html("<tbody>"+table+"</tbody>");
			} else {
				showMsgDialog("系统错误",result.message);
			}
		}, "json");	
		
	}
	

	
	/* 当前持仓明细  */
	var positionDetails=function(type,groupId){
		$.post(basepath + "trade/detail.json", {type:type,groupId:groupId,ajax:1}, function(result) {
			if (result.success) {
				var today = new Date();
				var data=result.obj;
				var tr=' <tr>';
				tr+='<td>{0}</td>';
				tr+='<td>{1}</td>';
				tr+='<td>{2}</td>';
				tr+='<td>{3}</td>';
				tr+='<td class="{4}">{5}</td>';
				tr+='<td class="{4}">{6}%</td>';
				tr+='<td>{7}</td>';
				tr+='</tr>';
				var table="";
			$.each(data, function(){				
				var color="color4";
				if(this.profitAndLoss<0){
					color="color3";
				}
				table+=$.format(tr,this.stockCode,$.formatMoney(this.stockCost),this.currentAmount,$.formatMoney(this.marketValue),color,$.formatMoney(this.profitAndLoss),this.profitAndLossPer,getSmpFormatDate(today,false));				
			});
			$('#positionDetails_table').html("<tbody>"+table+"</tbody>");
			} else {
				showMsgDialog("系统错误",result.message);
			}
		}, "json");	
		
	}
	
	/* 历史持仓明细  */
	var historyDetails=function(type,groupId){
		$.post(basepath + "trade/detail.json", {type:type,groupId:groupId,ajax:1}, function(result) {
			if (result.success) {
				var data=result.obj;
				var tr=' <tr>';
				tr+='<td>{0}</td>';
				tr+='<td>{1}</td>';
				tr+='<td>{2}</td>';
				tr+='<td>{3}</td>';
				tr+='<td>{4}</td>';
				tr+='<td>{5}</td>';
				tr+='<td>{6}</td>';
				tr+='</tr>';
				var table="";
			$.each(data, function(){
				var exchangeTypeString="上交所";
				if(this.exchangeType=='1'){
					exchangeTypeString="上交所";
				}else if(this.exchangeType=='2'){
					exchangeTypeString="深交所";
				}
				var entrustDirectionString="股票买入";
				if(this.entrustDirection=='1'){
					entrustDirectionString="股票买入";
				}else if(this.entrustDirection=='2'){
					entrustDirectionString="股票卖出";
				}
				table+=$.format(tr,this.stockCode,exchangeTypeString,entrustDirectionString,this.businessPrice,this.businessAmount,this.businessBalance,this.initDate);				
			});
			$('#historyDetails_table').html("<tbody>"+table+"</tbody>");
			} else {
				showMsgDialog("系统错误",result.message);
			}
		}, "json");	
		
	}
	
	/* 账户管理费*/
	var fee= function(type,groupId){
		$.post(basepath + "trade/detail.json", {type:type,groupId:groupId,ajax:1}, function(result) {
			if (result.success) {	
				var data=result.obj;
				var sumManage=result.data.sumManage.toFixed(2);
				var sumInterest=result.data.sumInterest.toFixed(2);
				
				var head='<ol class="uc_tdit_title">';
				head+='<li>支付时间</li>';
				head+='<li>账户管理费</li>';
				head+='<li>操盘利息</li>';
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
	
	/* 利润列表 */
	var profits= function(type,groupId){
		
		$.post(basepath + "trade/detail.json", {type:type,groupId:groupId,ajax:1}, function(result) {
			if (result.success) {			
				var data=result.obj;
				var total=result.data.total;
				var sum=result.data.sum;
	
				var head='<ol class="uc_td_pfltitle">';
				head+='<li>提现时间</li>';
				head+='<li class="uc_td_pfmoney">提现金额</li>';
				head+='<li>状态</li>';
				head+='</ol>';
					
				var tr='<ol>';
				tr+='<li>{0}</li>';
				tr+='<li class="uc_td_pfmoney">{1}元</li>';
				tr+='<li>{2}</li>';
				tr+='</ol>';
				var table="";
				$.each(data, function(){
					var paytime=getFormatDateByLong(this.uptime,'yyyy-MM-dd');
					table+=$.format(tr,paytime,this.money,this.payStatusValue);				
				});
				$('#profits').empty();
				$('#profits').append(head+table);	
				$('#profit_count').text(total);
				$('#profit_money').text(sum.toFixed(2));
			} else {
				showMsgDialog("系统错误",result.message);
			}
		}, "json");	
		
	}
	
	/* 添加保证金列表 */
	var add= function(type,groupId){
		
		$.post(basepath + "trade/detail.json", {type:type,groupId:groupId,ajax:1}, function(result) {
			if (result.success) {			
				var data=result.obj;
				var total=result.data.total;
				var sum=result.data.sum;
	
				var head='<ol class="uc_td_pfltitle">';
				head+='<li>追加时间</li>';
				head+='<li class="uc_td_pfmoney">追加金额</li>';
				head+='<li>状态</li>';
				head+='</ol>';
					
				var tr='<ol>';
				tr+='<li>{0}</li>';
				tr+='<li class="uc_td_pfmoney">{1}元</li>';
				tr+='<li>{2}</li>';
				tr+='</ol>';
				var table="";
				$.each(data, function(){
					var paytime=getFormatDateByLong(this.uptime,'yyyy-MM-dd');
					table+=$.format(tr,paytime,-this.money,this.payStatusValue);				
				});
				$('#add').empty();
				$('#add').append(head+table);
				$('#bail_count').text(total);
				//要改为正数
				$('#bail_money').text(-sum.toFixed(2));
			} else {
				showMsgDialog("系统错误",result.message);
			}
		}, "json");	
		
	}
	
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
			}else if(value==2){
				positionDetails(value,groupId);	
			}else if(value==3){
				fee(value,groupId);	
			}else if(value==4){
				
			}else if(value==5){
				profits(value,groupId);	
			}else if(value==6){
				add(value,groupId);	
			}else if(value==7){
				historyDetails(value,groupId);	
			}
			
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
	
	// 追加保证金跳转下一步
	$('#addBondNext').bind('click', addBondNext);
	// 确认追加保证金
	$('#addBond').bind('click', addBondFun);
	/*============== 追加保证金结束 ==============*/
	
	/* 提取利润 */
	var extractable=function(extractableProfitMoney,groupId){
		$('#extractableProfitBtn').html('正在提取......');
		$.post(basepath + "trade/extractableProfit.json", {extractableProfitMoney:extractableProfitMoney,groupId:groupId,ajax:1}, function(result) {
			if (result.success) {
				$('.fl_box').hide();
				$('.fl_mask').hide();
				clickCloseRefresh("提示","提取利润成功。");
			} else {
				$('#extractableProfitBtn').css("color","").html('确定').bind('click',extractableFun);	
				showMsgDialog("提示",result.message);
			}
		}, "json");			
	}
	var extractableFun= function (){
		var maxExtractableProfit=Number($('#extractableProfit').val());
		var extractMoney=Number($('#extractMoney').val());
		var groupId=$('#group_id').text();
		if(!extractMoney){
			showMsgDialog("提示","请输入提取利润金额。");
			return false;
		}
		if(!$.isNumeric(extractMoney)){
			showMsgDialog("提示","请输入正确的金额。");
			return false;
		}
		if(extractMoney<=0){
			showMsgDialog("提示","请输入正的金额,输入不能为负。");
			return false;
		}
		if(extractMoney>maxExtractableProfit){
			alert("最大提取利润金额为"+maxExtractableProfit);
			$('#extractMoney').val(maxExtractableProfit);
			return false;
		}
		 var moneyreg=/^[0-9]+([.]{1}[0-9]{1,2})?$/;
		 if(!moneyreg.test(extractMoney)){
			 showMsgDialog("提示","请输入正确的金额,最小金额为分。"); 
			 $('#extractMoney').val(maxExtractableProfit);
				return false; 
		 }
		$('#extractableProfitBtn').css("color","#CCC").unbind("click");
		extractable(extractMoney,groupId);
	}
	
	$('#extractableProfitBtn').bind('click',extractableFun);	
	
	//有没有追加保证金的功能 1是8800活动
	if(Number($('#additionalMargin').val())==1){	
		$('.ul_td_bailbtn').css("color","#CCC").unbind("click");
	}
	
	//有没有提取利润的功能
	if(Number($('#isExtractableProfit').val())==0){
		$('.ul_td_probtn').css("color","#CCC").unbind("click").attr('href','javascript:showMsgDialog("提示","您没有可提取的利润或这个时间段不可以提取利润。");');
	}
	
	//有没有追加方案的功能
	if(Number($('#isAddProgram').val())==0){
		$('.ul_td_tradebtn').attr('href','javascript:void(0);');
		//$('.ul_td_tradebtn').css("color","#CCC").unbind("click");	
	}
	
	var stopBtnFun= function() {
		var type = $(this).attr("data-feeType");
		if(type == 0 || type == 1){
			showMsgDialog("提示","系统升级中，该功能暂停使用。");
			return;
		}
		var groupId=$('#group_id').text();
		var feeType = $(this).attr('data-feeType');
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
	
	$('.ul_td_stopbtn').bind('click', stopBtnFun);	
	
	$('#program').click();
	
	$('.uc_sidebar').find("div.uc_nav ul a").each(function(){
		$(this).removeClass('on');
 	});
	$("#stock").parent().addClass('on');
	
	function endOfProgram(feeType, groupId, tds){
		if(confirm(tds ? '您还有'+ tds +'天利息，中途终止操盘，剩余利息将不予退还，请问是否确认终止操盘？' 
					   : '你确认要终结方案么？')) {
			$('.ul_td_stopbtn').css("color","#CCC").unbind("click");
			$.post(basepath + "trade/endOfProgram.json", {groupId:groupId,ajax:1}, function(result) {
				if (result.success) {
					clickCloseRefresh("提示","终结方案成功。");
				} else {
					$('.ul_td_stopbtn').css("color","").bind('click', stopBtnFun);
					showMsgDialog("提示",result.message);
				}
			}, "json");	
		}
	}
	
	$('#uc_qj_trading_info li a').live("click",function(){
		$(this).parent().parent().find('a').css('display', 'none');
		$(this).parent().parent().find('img').css('display', '');
		getTradingUserInfo(false);
	});
	
//	$('#uc_qj_trading_info').find('li a').each(function() {
//		$(this).click(function() {
//		});
//	});
	function getTradingUserInfo(flag) {
		var _gid = $('#group_id').text();
		var _ep = $('#extractableProfit').val();
		if(!_ep) {
			return;
		}
		$.ajax({
			type:'post',
			url: basepath + 'trade/tradingUserInfo',
			data: {
				groupId : _gid
			},
			dataType:'json',
			success:function(result) {
				var _b = result.success;
				if(_b) {
					var _o = result.obj, _ta = _o.totalAccrual, _cb = _o.cashBalance, _ep = _o.extractableProfit, _atv = _o.assetTotalValue, _sa = _o.stockAssets;
					var data_totalLending = Number($('.ul_td_probtn').attr('data-totalLending')),data_totalLeverMoney=Number($('.ul_td_probtn').attr('data-totalLeverMoney'));
					if(flag) {
						if(_atv <= (data_totalLending+data_totalLeverMoney)*1.10){
							showMsgDialog("提示","您的股票建议账户资产总值>方案总操盘资金*110%时才能进行利润提取。");
							$('.ul_td_probtn').css("color","#CCC").unbind("click").attr('href','javascript:showMsgDialog("提示","您的股票建议账户资产总值>方案总操盘资金*110%时才能进行利润提取。");');
						}else{
							$('.fl_uc_pro').show();
							$('.fl_mask').show();
						}
					}
					
					$('#uc_qj_trading_info').find('li').each(function() {
						if($(this).index() === 0) {
							$(this).children('span').html('<i class="'+(_ta > 0 ? 'uc_red' : 'uc_green')+'">'+_ta.toFixed(2)+'</i>元');
						} else if($(this).index() === 1) {
							$(this).children('span').text(_cb.toFixed(2) + '元');
						} else if($(this).index() === 2) {
							$(this).children('span').text(_ep.toFixed(2) + '元');
						} else if($(this).index() === 3) {
							$(this).children('span').text(_atv.toFixed(2) + '元');
						} else if($(this).index() === 4) {
							$(this).children('span').text(_sa.toFixed(2) + '元');
						}
						$(this).children('img').css('display', 'none');
						$(this).children('a').css('display', 'none');
						$(this).children('span').css('display', '');
						$('#isExtractableProfit').val(_ep > 0 ? 1 : 0);
						$('#extractableProfit').val(_ep).parent().find('i').text(_ep);
						$('#extractMoney').val(_ep);
					});
				} else {
					showMsgDialog("提示","系统繁忙, 请稍后再试..");
					$('#uc_qj_trading_info').find('li').each(function() {
						$(this).children('img').css('display', 'none');
						$(this).children('span').css('display', 'none');
						$(this).children('a').css('display', '');
					});
				}
			},
			error: function() {
				showMsgDialog("提示","系统繁忙, 请稍后再试..");
				$('#uc_qj_trading_info').find('li').each(function() {
					$(this).children('img').css('display', 'none');
					$(this).children('span').css('display', 'none');
					$(this).children('a').css('display', '');
				});
			}
		});
	}
	// addd by wuchaoliang 20150911 
	$('.ul_td_probtn').css("color","#CCC").unbind("click").attr('href','javascript:showMsgDialog("提示","功能升级中，暂停提取利润。");');
});

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


