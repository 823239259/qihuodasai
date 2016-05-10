function Tzdr(){
	
};

/**
 * 
 * @param jqueryFind jquery exception 
 * @param valueTag target Element Tag Object
 */
Tzdr.rechangeSetValue = function rechangeSetValue(jqueryFind,valueTag,clickCallback,initCallback) {
//	var isFirst = true;
	$(jqueryFind).each(function(){
    	$(this).click(function(){
		    $(jqueryFind).each(function(){
		    	$(this).removeClass("on");
		    });
    		$(this).addClass("on");
    		try {
    			var value = $(this).find(".defaultVal").val();
    			$(valueTag).val(value);
    		}
    		catch (ex) {}
    		if (clickCallback) {
    			clickCallback(this);
    		}
    		
    	});
//    	if (isFirst) {
//    		$(this).click();
//    		isFirst = false;
//    	}
    });
	try{
		initCallback();
	}
	catch(e) {}
};

Tzdr.submitForm = function submitForm(id) {
	setTimeout(function(){
		$("#" + id).submit();
	},100);
};

/**
 * 查询对象
 */
$.extend({easyui:{}});
$.extend($.easyui,{
	submitForm:function(id){
		Tzdr.submitForm(id);
	},
	rechangeSetValue:function rechangeSetValue(jqueryFind,valueTag,clickCallback,initCallback) {
		Tzdr.rechangeSetValue(jqueryFind,valueTag,clickCallback,initCallback);
	}
});

$(function(){
	$("#notPay_close").click(function(){
		$("#notPay").hide();
	});
}); 

function init() {
	$("#pay_button").on("click",function(){
		var showAvl = $("input[name='showAvl']").val();
		var loginStatus = user.isLogin();
//		var loginSucceedCallback = loginStatus ? function(){} : function(){window.location.reload();};
		if(user.checkAndShowLogin(function(){})){
			if(showAvl=='1'){
				$("#notPay").show();
				return false;
			}else{
				$("#confirmPayDiv").show();
				return false;
			}
		}
	});
};

/**
 * 
 * @param jqueryFind
 */
function closeWindow(jqueryFind) {
	$(".fl_mask").hide();
	$(jqueryFind).hide();
};

/**
 * @param jqueryFind
 * @param callback
 */
function closeWindow(jqueryFind,callback) {
	$(".fl_mask").hide();
	$(jqueryFind).hide();
	if(callback){
		callback();
	}
};

function colseConfirmPayDiv() {
	$("#confirmPayDiv").hide();
};

$('#confirmPay').live('click',function(){
	var status =  $(this).attr("status");
	$(this).attr("status",false);
	if(status == "true"){
		$("#confirmPayDiv").hide();
		$(this).attr("status",true);
		$('#forPaySubmit').click();
	}
});

/**
 * 
 * @param jqueryFind
 */
function openWindow(jqueryFind,callback) {
	try {
		callback(this);
	}
	catch (e){}
	$(".fl_mask").show();
	$(jqueryFind).show();
};
//交易明细窗口
function settingEndInfo(traderBond,appendTraderBond,tranProfitLoss,tranFeesTotal,endAmount) {
	$("#window_detail_endInfo").find("p").html("");
	var i = 0;
	$("#window_detail_endInfo").find("p").each(function(){
		++i;
		if (i == 1) {
			$(this).html($.formatMoney(Number(traderBond)));
		}else if (i == 2){
			$(this).html($.formatMoney(Number(appendTraderBond)));
		}
		else if (i == 3) {
			$(this).html($.formatMoney(Number(tranProfitLoss)));
		}
		else if (i == 4) {
			$(this).html($.formatMoney(Number(tranFeesTotal)));
		}
		else if (i == 5) {
			$(this).html($.formatMoney(Number(endAmount)));
		}
		

	});
};

function settingAccountInfo(tranAccount,tranPassword,businessType) {
	$("#window_detail_accountInfo").find("span").html("");
	var i = 0;
	$("#window_detail_accountInfo").find("span").each(function(){
		++i;
		if (i == 1) {
			$(this).html(tranAccount);
		}
		else if (i == 2) {
			$(this).html(tranPassword);
		}

	});
	if (businessType==20){
		$("#lookExplain").attr("href",basepath+"help?tab=rule&leftMenu=2");
	}else
	{
		$("#lookExplain").attr("href",basepath+"help?tab=rule&leftMenu=7");
	}
};



function init_table_web() {
	$('table.data_web').find('tr.tzdr-data-rows').unbind('mouseenter mouseleave');
	$('table.data_web').find("tr.tzdr-data-rows").hover (  
	        function() {  
	            $(this).find('td').addClass('tdhover');  
	        },  
	        function() {  
	            $(this).find('td').removeClass('tdhover');  
	        }  
	 );
	$("table.data_web").find("tr.tzdr-data-rows").click(function(){
		$(this).find("td").removeClass("tdhover");
		$(this).find("td").toggleClass("selected");
		
	});
}

var items_per_page = 10;
var page_index = 0;
//分页查询充值记录
function getDataList(index,type,
		  divid,pagediv,title){  
	
     pagediv=pagediv||'Pagination';
     
	 divid=divid||'Searchresult';
	    var pageIndex = index; 
	  
	    $.ajax({
	        type: "POST",
	        url: basepath+"userproduct/findData",   
	        data: {"pageIndex":pageIndex,'perPage':items_per_page,"type":type},   
	        dataType: 'json',   
	        contentType: "application/x-www-form-urlencoded",   
	        success: function(msg){  
	            var total =msg.totalCount;   
	            var html = '';   
	            var tbody = $("#" + divid + "Data").find("tbody");
	            $.each(msg.pageResults,function(i,n){
	            	var showid='"'+title+i+'"';
	            	html += "<tr class='tzdr-data-rows'>";
	            	var _tranLever = n.tranLever == null ? 0:Number(n.tranLever);
	            	html = html + "<td>" + n.businessTypeStr + "</td>";
//	        		html = html + "<td>" + (n.businessType == 8 ? '' : n.tranLever) + "</td>";
	            	html = html + "<td>" + n.appTimeStr + "</td>";
	            	html = html + "<td> <font color='#FF0000'>" + n.useTradeDay + "交易日</font></td>";
	            	html = html + "<td>" +(n.businessType == 20 ? '' : n.tranLever) + "</td>";
	            	html = html + "<td>" + n.stateTypeStr + "</td>";
	            	html = html + "<td>" + $.formatMoney(Number(n.traderTotal)) + "元</td>";
	            	html = html + "<td>" + $.formatMoney(Number(n.traderBond)) + "元</td>";
	            	html = html + "<td>" + $.formatMoney(Number(n.appendTraderBond)) + "元</td>";
	            	html = html + "<td>" + $.formatMoney(Number(n.lineLoss)) + "元</td>";
	            	if(n.stateType == 2 ||n.stateType == 3|| n.stateType == 4){
	            		html = html + "<td></td>";
	            		html = html + "<td>";
	            		html = html + "<a href=\"#\" onclick=\"openWindow('#accountDetailInfo',settingAccountInfo('"+ n.tranAccount + "','" + n.tranPassword +"','"+n.businessType+"'));\"  class='uc_tlbtn_btn_see' style='color:#34b3e0;'>查看交易账户</a>";
	            		if(n.stateType == 2){
	            			html = html + "<a href=\"#\" class='uc_tlbtn_btn_no' style='color:#fff;margin: 5px auto 0;background:#D3D3D3;'>已申请终结</a>";
	            		}else if(n.stateType == 4){
	            			html = html + "<a href=\"#\" data_no="+n.id+" onclick=\"showAppendMoneyBox('"+n.id+"')\" class='uc_tllist_add' style='color:#fff;margin: 5px auto 0;'>补充保证金</a>";
	            			html = html + "<a href=\"#\" data_no="+n.id+" onclick=\"showApplyEndTradeBox('"+n.id+"')\" class='uc_tlbtn_btn_no' style='color:#fff;margin: 5px auto 0;'>终结方案</a>";
	            		}
	            		html = html + "</td>";
	            	}else if(n.stateType == 6){
	            		html = html + "<td>" + $.formatMoney(Number(n.endAmount)) + "</td>";
	            		html = html + "<td><a href=\"#\" onclick=\"openWindow('#detailInfo',settingEndInfo(" +
		            	n.traderBond + "," 
		            	+n.appendTraderBond+","
		            	+ n.tranProfitLoss + ","
		            	+ n.tranFeesTotal +"," + n.endAmount + "));\" " +
		            	"style=\"cursor:pointer;color:#34b3e0;\" class='uc_tlbtn_btn_see' >账单详细</a></td>";
	            	}else{
	            		html = html + "<td></td>";
	            		html = html + "<td></td>";
	            	}
            	
            	html += "</tr>";
	            }); 
	            $(tbody).html(html);
	           //分页-只初始化一次   
	            if($("#"+pagediv).html()== ''){ 
        		   $("#"+pagediv).pagination(total, {   
                    'items_per_page'      : items_per_page,
                    'num_edge_entries'    : 2,   
                    'prev_text'           : "上一页",   
                    'next_text'           : "下一页",   
                    'callback'            : function pageCallback(page_index){
                    	getDataList(page_index,type,divid,pagediv,title);
                    }
                });  
	            }   
	            init_table_web();
	        }   
	    });
	};
	//打开追加保证金
	function showAppendMoneyBox(no) {
		$("#f_appendMoney").attr("status",true);
		$("#f_appendMoney").attr("data_no",no);
		$("#f_appendMoney").text("确定");
		$("#appendMoney").val('');
		$("#append_avlBal").val('');
		$("#append_avlBal").next('span').find('i').html('0.00');
		$.post(basepath+"userproduct/getAppendMoneyInfo",{id:no,ajax:1},function(data){  //获取短信验证码信息
			if(data.success){
				if(data.message!="" && data.message!=null && data.message == "notFindData"){
					showMsgDialog("提示","未找到该数据信息");
					return;
				}
				$("#append_avlBal").val(data.data.avlBal);
				$("#append_avlBal").next('span').find('i').html($.formatMoney(data.data.avlBal,2));
				$('#appendMoneyDetailInfo').show();
				$('.fl_mask').show();
			}else{
				showMsgDialog("提示","系统繁忙，请重试......");
			}
		},"json");
	};
	
	//追加保证金
	$("#f_appendMoney").live('click',function(){
		var $this = $(this);
		if($(this).attr("status") == "true"){
			$this.attr("status",false);
			$this.text("正在添加中...");
			var appendMoney=Number($('#appendMoney').val());
			var append_minAppendMoney=Number($('#append_minAppendMoney').val());
			var append_avlBal=Number($('#append_avlBal').val());
			var data_no=$this.attr('data_no');
			if(!appendMoney){
				$this.attr("status",true);
				$this.text("确定");
				showMsgDialog("提示","请输入补充保证金金额！");
				return;
			}
			if(!$.isNumeric(appendMoney)){
				$this.attr("status",true);
				$this.text("确定");
				showMsgDialog("提示","请输入正确的金额！");
				return;
			}
			if(appendMoney<append_minAppendMoney){
				$this.attr("status",true);
				$this.text("确定");
				showMsgDialog("提示","最少补充金额为"+append_minAppendMoney+"！");
				$('#appendMoney').val(append_minAppendMoney);
				return;
			}
			if(appendMoney>append_avlBal){
				$this.attr("status",true);
				$this.text("确定");  
				showMsgDialog("提示","账户资金不足，余额为"+append_avlBal+"元！");
				$('#appendMoney').val("");
				return;
			}
			 var moneyreg=/^[0-9]+([.]{1}[0-9]{1,2})?$/;
			 if(!moneyreg.test(appendMoney)){
				 $this.attr("status",true);
				 $this.text("确定");
				 showMsgDialog("提示","请输入正确的金额,最小金额为分！"); 
				 return; 
			 }
			$.post(basepath + "userproduct/appendMoney", {appendMoney:appendMoney,id:data_no,ajax:1}, function(data) {
				if (data.success) {
					if(data.message!="" && data.message!=null){
						if(data.message == "underDefaultMinAppendMoney"){
							showMsgDialog("提示","补充保证金金额不能大于帐号余额！");
							return;
						}else if(data.message == "insufficientBalance"){
							showMsgDialog("提示","最少补充金额为"+append_minAppendMoney+"！");
							return;
						}else if(data.message == "notFindData"){
							showMsgDialog("提示","未找到该方案数据！");
							return;
						}else{
							showMsgDialog("提示","无法对已完结方案进行补充保证金！");
							return;
						}
					}else{
						$this.attr("status",true);
						$this.text("确定");
						closeWindow('#appendMoneyDetailInfo');
						clickCloseRefresh("提示","补充保证金成功。");
					}
				} else {
					$this.attr("status",true);
					$this.text("确定");
					showMsgDialog("提示","系统繁忙，请重试......");
				}
			}, "json");	
		}
	}); 
	
	//取消追加保证金
	$("#f_appendMoney_cance").live('click',function(){
		if($("#f_appendMoney").attr("status") == "true"){
			closeWindow('#appendMoneyDetailInfo');
		}
	});

	//打开终结方案
	function showApplyEndTradeBox(no) {
		$("#f_applyEndTrade").attr("status",true);
		$("#f_applyEndTrade").attr("data_no",no);
		$("#f_applyEndTrade").text("确定");
		// 获取折扣券
		$.post(basepath + "userproduct/getCoupon", {ajax:1,businessType:5}, function(data) {
			if (data.success) {
				// 显示折扣券
				var discount = data.data.discount;
				if(discount.length > 0) {
					$("#discount").empty();
					var option = "";
					$.each(discount, function(n, value) {
						option += "<option value='"+value.id+"'>"+value.money+"折折扣券</option>"
					});
					option += "<option value=''>不使用折扣券</option>"
					$("#discount").append(option);
				}
				$("#f_parities").hide();
				$('#applyEndTrade').show();
				$('.fl_mask').show();
			} else {
				showMsgDialog("提示","系统繁忙，请重试......");
			}
		}, "json");
	};
	
	//终结方案
	$("#f_applyEndTrade").live('click',function(){
		var $this = $(this);
		var data_no=$this.attr('data_no');
		// 折扣券
		var discount_id = $("#discount").val();
		if($(this).attr("status") == "true"){
			$this.attr("status",false);
			$this.text("正在提交中...");
				$.post(basepath + "userproduct/apply_end_trade", {id:data_no,businessType:5,discountId:discount_id,ajax:1}, function(data) {
					if (data.success) {
						$this.attr("status",true);
						$this.text("确定");
						if(data.message!="" && data.message!=null){
							closeWindow('#applyEndTrade');
							if(data.message == "notFindData"){
								showMsgDialog("提示","未找到该方案数据！");
								return;
							}else if(data.message == "notRepetitionApply"){
								clickCloseRefresh("提示","该方案已经提交过，不能重复提交！");
								return;
							}else if(data.message == "notUpApplyEndTradeTime"){
								showMsgDialog("提示","申请当天不能终结方案，请下一个交易日提交终结申请！");
								return;
							}else{
								showMsgDialog("提示","该方案提交终结申请失败！");
								return;
							}
						}else{
							$this.attr("status",false);
							$this.text("已申请终结");
							closeWindow('#applyEndTrade');
							clickCloseRefresh("提示","申请成功。");
						}
					} else {
						showMsgDialog("提示","系统繁忙，请重试......");
					}
				}, "json");
		}
	});
	
	//取消终结方案
	$("#f_applyEndTrade_cance").live('click',function(){
		if($("#f_applyEndTrade").attr("status") == "true"){
			closeWindow('#applyEndTrade');
		}
	});
