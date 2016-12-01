
//交易明细窗口
function settingEndInfoFtse(traderBond,appendTraderBond,tranProfitLoss,parities,tranCommission,endAmount,businessType,tranActualLever,crudeTranActualLever,hsiTranActualLever,
		mdtranActualLever,mntranActualLever,mbtranActualLever,daxtranActualLever,nikkeiTranActualLever,lhsiTranActualLever,agTranActualLever,heIndexActualLever,xheIndexActualLever,ameCopperActualLever,ameSilverActualLever,smaActualLever,daxtranMinActualLever) {
	$(".fl_navtitle h3").removeClass("on").eq(0).addClass("on");
	$("#window_detail_tab .window_detail_lis").hide().eq(0).show();
	
	$("#window_detail_endInfoFtse").find("p").html("");
	var i = 0;
	$("#window_detail_endInfoFtse").find("p").each(function(){
		++i;
		if (i == 1) {
			$(this).html($.formatMoney(Number(traderBond))+'元');
		}else if (i == 2) {
			$(this).html($.formatMoney(Number(appendTraderBond))+'元');
		}
		else if (i == 3) {
			$(this).html($.formatMoney(Number(tranProfitLoss)*parities,2)+'元');//（'+$.formatMoney(Number(tranProfitLoss)*parities,2)+'人民币）
		}
		else if (i == 4){
			$(this).html('1:'+Number(parities).toFixed(6));
		}
		else if (i == 5) {
			$(this).html($.formatMoney(Number(tranCommission))+'元');
		}
		else if (i == 6) {
			$(this).html($.formatMoney(Number(endAmount))+'元');
		}
	});
	var window_detail_details_fee ="<p>"+endAmount+"元="+traderBond+"元+"+appendTraderBond+"元+"+$.formatMoney(Number(tranProfitLoss)*parities,2)+"元 - "+tranCommission+"元</p>" +
    "<p>（结算金额=操盘保证金+追加保证金+交易盈亏-交易手续费）</p>" +
    "<p>注意：交易手续费=合约手续费×手数</p>";
	$("#window_detail_details_fee").html(window_detail_details_fee);
	var detailInfoFtseHeight  = $("#detailInfoFtse").outerHeight()/2;
    $(".sif_money").css({
        top:"50%",
        marginTop: -detailInfoFtseHeight

    })
	$("#window_detail_endInfoFtse_trade").html('');
	if(businessType == 8){
		var tradeDev = '<p style="text-align: center; font-size: 20px; padding-top: 20px;">交易手数</p>';
		tradeDev+= '<ul><li style="width:186px;">';
		tradeDev+= '<h3>A50交易手数</h3>';
		tradeDev+= '<p style= "border-bottom: 1px solid #e7e7e7;">'+tranActualLever+'手</p>';
		tradeDev+= '</li>';
		tradeDev+= '<li style="width:186px;">';
		tradeDev+= '<h3>期指期货交易手</h3>';
		tradeDev+= '<p style= "border-bottom: 1px solid #e7e7e7;">'+hsiTranActualLever+'手</p>';
		tradeDev+= '</li>';
		tradeDev+= '<li style="width:186px;">';
		tradeDev+= '<h3>国际原油交易手数</h3>';
		tradeDev+= '<p style= "border-bottom: 1px solid #e7e7e7;">'+crudeTranActualLever+'手</p>';
		tradeDev+= '</li>';
		tradeDev+= '<li style="width:186px;">';
		tradeDev+= '<h3>迷你道指交易手数</h3>';
		tradeDev+= '<p style= "border-bottom: 1px solid #e7e7e7;">'+mdtranActualLever+'手</p>';
		tradeDev+= '</li>';
		tradeDev+= '<li style="width:186px;">';
		tradeDev+= '<h3>迷你纳指交易手数</h3>';
		tradeDev+= '<p style= "border-bottom: 1px solid #e7e7e7;">'+mntranActualLever+'手</p>';
		tradeDev+= '</li>';
		tradeDev+= '<li style="width:186px;">';
		tradeDev+= '<h3>迷你标普交易手数</h3>';
		tradeDev+= '<p style= "border-bottom: 1px solid #e7e7e7;">'+mbtranActualLever+'手</p>';
		tradeDev+= '</li>';
		tradeDev+= '<li style="width:186px;">';
		tradeDev+= '<h3>德国DAX交易手数</h3>';
		tradeDev+= '<p style= "border-bottom: 1px solid #e7e7e7;">'+daxtranActualLever+'手</p>';
		tradeDev+= '</li>';
		tradeDev+= '<li style="width:186px;">';
		tradeDev+= '<h3>日经225交易手数</h3>';
		tradeDev+= '<p style= "border-bottom: 1px solid #e7e7e7;">'+nikkeiTranActualLever+'手</p>';
		tradeDev+= '</li>';
		tradeDev+= '<li style="width:186px;">';
		tradeDev+= '<h3>小恒指交易手数</h3>';
		tradeDev+= '<p style= "border-bottom: 1px solid #e7e7e7;">'+lhsiTranActualLever+'手</p>';
		tradeDev+= '</li>';
		tradeDev+= '<li style="width:186px;">';
		tradeDev+= '<h3>美黄金交易手数</h3>';
		tradeDev+= '<p style= "border-bottom: 1px solid #e7e7e7;">'+agTranActualLever+'手</p>';
		tradeDev+= '</li>';
		tradeDev+= '<li style="width:186px;">';
		tradeDev+= '<h3>H股指数</h3>';
		tradeDev+= '<p style= "border-bottom: 1px solid #e7e7e7;">'+heIndexActualLever+'手</p>';
		tradeDev+= '</li>';
		tradeDev+= '<li style="width:186px;">';
		tradeDev+= '<h3>小H股指数</h3>';
		tradeDev+= '<p style= "border-bottom: 1px solid #e7e7e7;">'+xheIndexActualLever+'手</p>';
		tradeDev+= '</li>'; 
		tradeDev+= '<li style="width:186px;">';
		tradeDev+= '<h3>美铜</h3>';
		tradeDev+= '<p style= "border-bottom: 1px solid #e7e7e7;">'+ameCopperActualLever+'手</p>';
		tradeDev+= '</li>';
		tradeDev+= '<li style="width:186px;">';
		tradeDev+= '<h3>美白银</h3>';
		tradeDev+= '<p style= "border-bottom: 1px solid #e7e7e7;">'+ameSilverActualLever+'手</p>';
		tradeDev+= '</li>';
		tradeDev+= '<li style="width:186px;">';
		tradeDev+= '<h3>小原油</h3>';
		tradeDev+= '<p style= "border-bottom: 1px solid #e7e7e7;">'+smaActualLever+'手</p>';
		tradeDev+= '</li>';
		tradeDev+= '<li style="width:186px;">';
		tradeDev+= '<h3>迷你德国DAX指数</h3>';
		tradeDev+= '<p style= "border-bottom: 1px solid #e7e7e7;">'+(daxtranMinActualLever == undefined ? 0 : daxtranMinActualLever)+'手</p>';
		tradeDev+= '</li>';
		tradeDev+= '</ul>';
		$("#window_detail_endInfoFtse_trade").html(tradeDev);
		var detailInfoFtseHeight  = $("#detailInfoFtse").outerHeight()/2;
	    $(".sif_money").css({
	        top:"50%",
	        marginTop: -detailInfoFtseHeight

	    })
	}
	$(".fl_navtitle h3").click(function() {
		var _this = $(this);
		$(".fl_navtitle h3").removeClass("on").eq(_this.index()).addClass("on");
		$("#window_detail_tab .window_detail_lis").hide().eq(_this.index()).show();
	});
};
function bindEndOfFtse(cls){
	$("."+cls).bind("click",function(){
		var fastId = $(this).attr("data-fastId");
		$.ajax({
			url:basepath+"/userftse/getFstTradeDetail",
			type:"get",
			data:{
				id:fastId
			},
			success:function(result){
				var window_detail_title ='<tr id="window_detail_title" style="color: #333;">'+
											'<td style="width: 40px;">序号</td>'+
											'<td style="width: 120px;">合约名称</td>'+
											'<td style="width: 60px;">交易盈亏</td>'+
											'<td style="width: 60px;">交易手数</td>'+
											'<td style="width: 60px;">手续费</td>'+
											'<td style="width: 60px;">成交价</td>'+
											'<td style="width: 40px;">买卖</td>'+
											'<td style="width: 80px;">买入/卖出</td>'+
											'<td style="width: 60px;">订单类型</td>'+
											'<td style="width: 100px;">结算时间</td>'+
										'</tr>';
				var data = result.data.data;
				for(var i = 0 ; i < data.length;i++){
					var _data = data[i];
				    window_detail_title += '<tr><td>'+i+'</td>' +
				    '<td>'+_data.commodityNo+_data.contractNo+'</td>' +
				    '<td>'+_data.flat+'</td>' +
				    '<td>'+_data.tradeNum+'</td>' +
				    '<td>'+_data.free+'</td>' +
				    '<td>'+_data.tradePrice+'</td>' +
				    '<td>'+_data.drection+'</td>' +
				    '<td>'+_data.orderPrice+'</td>' +
				    '<td>'+_data.orderType+'</td>' +
				    '<td>'+_data.marketDate+'</td><tr>';
				}
				$("#tradeDetail").after(window_detail_title);
				/*$("#tradeDetail").css({
				    "height": "500px",
			        "overflow": "hidden"
	            });*/
			}
		});
	});
}
//交易帐号明细窗口
function settingAccountInfoFtse(tranAccount,tranPassword,businessType) {
	
	$("#window_detail_accountInfoFtse").html('');
	
	//交易软件下载地址
	var softwareLeftMenu = (businessType == 0||businessType == 7||businessType == 8) ? 8 : 10;
	
	//交易说明
	var tradeExplain = businessType == 0 ? 0 : businessType == 6 ? 8 : businessType == 7 ? 9 : 5;
	//提示信息
	var businessName = businessType == 0 ? '富时A50' : businessType == 6 ? "国际原油" : businessType == 7 ? "恒指期货" : "国际综合";

	//账号明细
	var tranAccountTable = '<table style="font-size:13px;" border="1">'; 
	tranAccountTable+= '<tr>';       
	tranAccountTable+= '<td>交易账号：</td>';
	tranAccountTable+= '<td><span>'+tranAccount+'</span></td>';
	tranAccountTable+= '</tr>';
	tranAccountTable+= '<tr>';
	tranAccountTable+= '<td>交易密码：</td>';
	tranAccountTable+= '<td><span>'+tranPassword+'</span></td>';
	tranAccountTable+= '</tr>';
//	if(businessType == 6){
//		tranAccountTable+= '</tr>';
//		tranAccountTable+= '<td>行情账户：</td>';
//		tranAccountTable+= '<td>50728</td>';
//		tranAccountTable+= '</tr>';
//		tranAccountTable+= '<tr>';
//		tranAccountTable+= '<td>行情密码：</td>';
//		tranAccountTable+= '<td>315975</td>';
//		tranAccountTable+= '</tr>';
//	}
	tranAccountTable+= '<tr>';
	tranAccountTable+= '<td>交易软件：</td>';
	tranAccountTable+= '<td><a target="_blank" href="'+basepath+'help?tab=software&leftMenu=1">交易软件下载</a></td>';
	tranAccountTable+= '</tr>';
	tranAccountTable+= '<tr>';
	tranAccountTable+= '<td></td>';
	tranAccountTable+= '<td><a target="_blank" href="'+basepath+'help?tab=rule&leftMenu='+tradeExplain+'">'+businessName+'操盘细则</a></td>';
	tranAccountTable+= '</tr>';
	tranAccountTable+= '</table>';
	$("#window_detail_accountInfoFtse").html(tranAccountTable);
	$("#hismsg").remove();
	//注意：从2016.03.23 18:00开始，我们的操盘软件已更换成直达快抢手快速下单软件，请新申请操盘方案的用户务必下载新软件！
		var hsimsg="<p class='fl_uc_userpromt' id='hismsg'></p>";
		$("#window_detail_accountInfoFtse").after(hsimsg);
	
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
function getFtseDataList(index,type,
		  divid,pagediv,title){  
	
     pagediv=pagediv||'Pagination';
 
     divid=divid||'Searchresult';
     
     var pageIndex = index; 
  
    $.ajax({
        type: "POST",
        url: basepath+"userftse/findData",   
        data: {"pageIndex":pageIndex,'perPage':items_per_page},   
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
        		html = html + "<td>" + (n.businessType == 8 ? '' : n.tranLever) + "</td>";
            	html = html + "<td>" + n.appTimeStr + "</td>";
            	html = html + "<td> <font color='#fc3'>" + n.useTradeDay + "交易日</font></td>";
            	html = html + "<td>" + n.stateTypeStr + "</td>";
            	html = html + "<td>" + $.formatMoney(Number(n.traderTotal)) + "美元</td>";
            	html = html + "<td>" + $.formatMoney(Number(n.lineLoss)) + "美元</td>";
            	html = html + "<td>" + $.formatMoney(Number(n.traderBond)) + "元</td>";
            	html = html + "<td>" + $.formatMoney(Number(n.appendTraderBond)) + "元</td>";
            	if(n.stateType == 2 || n.stateType == 4){
            		html = html + "<td></td>";
            		html = html + "<td>";
            		html = html + "<a href='javascript:void(0);' onclick=\"openWindow('#accountDetailInfoFtse',settingAccountInfoFtse('"+ n.tranAccount + "','" + n.tranPassword +"','"+n.businessType+"'));\"  class='uc_tlbtn_btn_see' style='color:#34b3e0;'>查看交易账户</a>";
            		if(n.stateType == 2){
            			html = html + "<a href='javascript:void(0);' class='uc_tlbtn_btn_no' style='color:#fff;margin: 5px auto 0;background:#D3D3D3;'>已申请终结</a>";
            		}else if(n.stateType == 4){
            			html = html + "<a href='javascript:void(0);' data_no="+n.id+" onclick=\"showAppendMoneyBox('"+n.id+"')\" class='uc_tllist_add' style='color:#333;margin: 5px auto 0;'>补充保证金</a>";
            			html = html + "<a href='javascript:void(0);' data_no="+n.id+" onclick=\"showApplyEndTradeBox("+n.businessType+",'"+n.id+"')\" class='uc_tlbtn_btn_no' style='color:#fff;margin: 5px auto 0;'>终结方案</a>";
            		}
            		html = html + "</td>";
            	}else if(n.stateType == 6){
            		html = html + "<td>" + $.formatMoney(Number(n.endAmount)) + "元</td>";
            		html = html + "<td><a href='javascript:void(0);' class = 'dataFastId' data-fastId="+n.id+"  onclick=\"openWindow('#detailInfoFtse',settingEndInfoFtse(" +
            		n.traderBond + "," 
	            	+ n.appendTraderBond + ","
	            	+ n.tranProfitLoss + ","
	            	+n.endParities+","
	             	+ n.tranFeesTotal +"," + n.endAmount +","+n.businessType+","+n.tranActualLever +","+n.crudeTranActualLever +","+n.hsiTranActualLever+ ","+n.mdtranActualLever+","
	            	+n.mntranActualLever+","+n.mbtranActualLever+","+n.daxtranActualLever+","+n.nikkeiTranActualLever+","+n.lhsiTranActualLever+","+n.agTranActualLever+","+n.heIndexActualLever+","
	            	+n.xheIndexActualLever+","+n.ameCopperActualLever+","+n.ameSilverActualLever+","+n.smaActualLever+","+n.daxtranMinActualLever+"));\" " +
	            	"style=\"cursor:pointer;color:#fc3;\" class='uc_tlbtn_btn_see' >账单详细</a></td>";
            	}else{
            		html = html + "<td></td>";
            		html = html + "<td></td>";
            	}
            	html += "</tr>";
            }); 
            $(tbody).html(html);
            bindEndOfFtse("dataFastId");
           //分页-只初始化一次   
            if($("#"+pagediv).html()== ''){ 
    		   $("#"+pagediv).pagination(total, {   
                'items_per_page'      : items_per_page,
                'num_edge_entries'    : 2,   
                'prev_text'           : "上一页",   
                'next_text'           : "下一页",   
                'callback'            : function pageCallback(page_index){
                	getFtseDataList(page_index,type,divid,pagediv,title);
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
	$.post(basepath+"userftse/getAppendMoneyInfo",{id:no,ajax:1},function(data){
		if(data.success){
			if(data.message!="" && data.message!=null && data.message == "notFindData"){
				showMsgDialog("提示","未找到该数据信息");
				return;
			}
			$("#append_avlBal").val(data.data.avlBal);
			var rate = data.data.exchangeRate;
			$('#rate').html("保证金采用固定汇率"+rate+","+rate+"元  = 1美元");
			$('#rateValue').val(rate);
			$("#append_avlBal").next('span').find('i').html($.formatMoney(data.data.avlBal,2));
			$('#appendMoneyDetailInfo').show();
			$('.fl_mask').show();
			// TODO兑换美元
			$("#appendMoney").bind('input propertychange',function(){
				var rmb = $(this).val();
				var dollar = $.formatMoney(Math.floor((rmb / rate)*100)/100,2);
				$("#convertDollar").text(dollar);
			});
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
		var rmb = $("#appendMoney").val();
		var rate = $('#rateValue').val();
		var dollar = Math.floor((rmb / rate)*100)/100;
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
			$("#convertDollar").text($.formatMoney(Math.floor((append_minAppendMoney / rate)*100)/100,2));
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
		$.post(basepath + "userftse/appendMoney", {appendMoney:appendMoney,rate:rate,dollar:dollar,id:data_no,ajax:1}, function(data) {
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
function showApplyEndTradeBox(business_type,no) {
	$("#f_applyEndTrade").attr("status",true);
	$("#f_applyEndTrade").attr("data_no",no);
	$("#f_applyEndTrade").attr("business_type",business_type);
	$("#f_applyEndTrade").text("确定");
	//if(business_type == 0){ //富时A50
	if(true){ //所有类型
		$.post(basepath + "userftse/getparities", {ajax:1,businessType:business_type}, function(data) {
			if (data.success) {
				$("#parities").html(data.data.parities);
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
				$("#f_parities").show();
				$('#applyEndTrade').show();
				$('.fl_mask').show();
			} else {
				showMsgDialog("提示","系统繁忙，请重试......");
			}
		}, "json");
	}else{
		$("#f_parities").hide();
		$('#applyEndTrade').show();
		$('.fl_mask').show();
	}
};

//终结方案
$("#f_applyEndTrade").live('click',function(){
	var $this = $(this);
	var data_no=$this.attr('data_no');
	var business_type = $this.attr('business_type');
	// 折扣券
	var discount_id = $("#discount").val();
	if($(this).attr("status") == "true"){
		$this.attr("status",false);
		$this.text("正在提交中...");
		$.post(basepath + "userftse/apply_end_trade", {id:data_no,businessType:business_type,discountId:discount_id,ajax:1}, function(data) {
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

var isOut = false; 
var index="${index}";
/* 访问记录  */
$(document).ready(function(){
	$("#oAccount").find("a").removeClass("on");
    $('.uc_sidebar').find("div.uc_nav ul a").each(function(){
	$(this).removeClass('on');
	});
    $('.navlist li a').removeClass('on');
	$("#nav_my").addClass("on");
    $("#ftse").parent().addClass("on");
    $.easyui.rechangeSetValue(".tzdr-tab",null,function(tag){
    	$("#appStateFtsePage").html("");
    	$(".tzdr-data01").hide();
    	var tabId = $(tag).attr("id");
    	var id = tabId + "Data";
    	var type = "";
    	if(id == "appStateFtseData"){  //国际期货
    		type = "0";
    		getFtseDataList(page_index,type,
		    		tabId,"appStateFtsePage","pay");
    	}
    	$("#" + id).show();
    },null);
    
	$("#appStateFtse").trigger("click");
});