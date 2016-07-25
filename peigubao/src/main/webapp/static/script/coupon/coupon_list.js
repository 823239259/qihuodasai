var items_per_page = 10;
var page_index = 0; 
var isOut = false; 
var index="${index}";

$(document).ready(function(){
	$("#oAccount").find("a").removeClass("on");
    $('.uc_sidebar').find("div.uc_nav ul a").each(function(){$(this).removeClass('on');});
    $('.navlist li a').removeClass('on');
	$("#nav_my").addClass("on");
    $("#coupon").parent().addClass("on");
    $.easyui.rechangeSetValue(".tzdr-tab",null,function(tag){
    	$("#fSimpleCouponPage").html("");
    	$(".tzdr-data01").hide();
    	var tabId = $(tag).attr("id");
    	var id = tabId + "Data";
    	var type = "";
    	if(id == "fSimpleCouponData"){  //优惠劵列表信息
    		getCouponDataList(page_index,tabId,"fSimpleCouponPage","pay");
    	}
    	$("#" + id).show();
    },null);
	$("#fSimpleCoupon").trigger("click");
});
//分页查询充值记录
function getCouponDataList(index,divid,pagediv,title){  
	
     pagediv=pagediv||'Pagination';
 
 divid=divid||'Searchresult';
    var pageIndex = index; 
  
    $.ajax({
        type: "POST",
        url: basepath+"user/coupon/findData",   
        data: {"pageIndex":pageIndex,'perPage':items_per_page},   
        dataType: 'json',   
        contentType: "application/x-www-form-urlencoded",   
        success: function(msg){  
            var total =msg.totalCount;   
            var html = '';   
            var tbody = $("#" + divid + "Data").find("div");
            $.each(msg.pageResults,function(i,n){
            	
            	var scope = n.scope;
            	var scopeStr = "";
            	if (null != scope && "" != scope) {
            		var scopeArr = scope.split(",");
            		for (var i = 0; i < scopeArr.length; i++) {
            			// 富士A50
            			if ("0" == scopeArr[i]) {
            				scopeStr += ",富士A50";
            			}
            			// 商品期货
            			if ("5" == scopeArr[i]) {
            				scopeStr += ",商品期货";
            			}
            			// 国际原油
            			if ("6" == scopeArr[i]) {
            				scopeStr += ",国际原油";
            			}
            			// 恒指期货
            			if ("7" == scopeArr[i]) {
            				scopeStr += ",恒指期货";
            			}
            			// 国际综合
            			if ("8" == scopeArr[i]) {
            				scopeStr += ",国际综合";
            			}
            			// 港股操盘 
            			if ("10" == scopeArr[i]) {
            				scopeStr += ",港股操盘 ";
            			}
            			// A股操盘 
            			if ("11" == scopeArr[i]) {
            				scopeStr += ",A股操盘 ";
            			}
            			// 股票合买
            			if ("12" == scopeArr[i]) {
            				scopeStr += ",股票合买 ";
            			}
            		}
            		// 去掉头部逗号
            		scopeStr = scopeStr.substring(1, scopeStr.length);
            	}

            	html += "<ul class='uc_cul_list'>";
            	html += (n.status == 3 || n.status == 4 ? "<ol class='use'>" : "<ol>");
            	var money = n.money;
            	var deadlineStr = n.deadlineStr;
            	var grantTimeStr = n.grantTimeStr;
            	var name = n.name;
            	if(n.type == 1){
            		html += "<li><span class='"+(n.status == 2 ? "uc_cul_red" : "uc_cul_usred")+"'>"+money+"</span></li>";
    				html += "<li class='type'>红包</li>";
    				html += "<li class='come'><i>"+scopeStr+"</i></li>";
    				html += "<li>"+grantTimeStr+"</li>";
    				html += "<li class='come'>"+deadlineStr+"</li>";
    				html += "<li title="+name+" class='come'>"+name+"</li>";
    				var handleHtml = n.status == 2 ? "<a href='javascript:void(0);' status='true' onclick=\"useRedPackage(this,'"+n.id+"',"+money+");\">立即使用</a>" : n.status == 3 ? "已使用" : "已过期";    				
    				html += "<li>"+handleHtml+"</li>";
            	}else if(n.type == 2){
            		html += "<li><span class='"+(n.status == 2 ? "uc_cul_money" : "uc_cul_usmoney")+"'>￥"+money+"</span></li>";
    				html += "<li class='type'>代金券</li>";
    				html += "<li class='come'><i>"+scopeStr+"</i></li>";
    				html += "<li>"+grantTimeStr+"</li>";
    				html += "<li class='come'>"+deadlineStr+"</li>";
    				html += "<li title="+name+" class='come'>"+name+"</li>";
    				var handleHtml = n.status == 2 ? "未使用" : n.status == 3 ? "已使用" : "已过期";    	
    				html += "<li >"+handleHtml+"</li>";
            	}else if(n.type == 6){
            		html += "<li><span class='"+(n.status == 2 ? "uc_cul_rebate" : "uc_cul_usrebate")+"'>￥"+money+"</span></li>";
    				html += "<li class='type'>抵扣券</li>";
    				html += "<li class='come'><i>"+scopeStr+"</i></li>";
    				html += "<li>"+grantTimeStr+"</li>";
    				html += "<li class='come'>"+deadlineStr+"</li>";
    				html += "<li title="+name+" class='come'>"+name+"</li>";
    				var handleHtml = n.status == 2 ? "未使用" : n.status == 3 ? "已使用" : "已过期";    	
    				html += "<li >"+handleHtml+"</li>";
            	}else{
            		html += "<li><span class='"+(n.status == 2 ? "uc_cul_ticket" : "uc_cul_usticket")+"'>"+money+"<b>折</b></span></li>";
    				html += "<li class='type'>折扣券</li>";
    				html += "<li class='come'><i>"+scopeStr+"</i></li>";
    				html += "<li>"+grantTimeStr+"</li>";
    				html += "<li class='come'>"+deadlineStr+"</li>";
    				html += "<li title="+name+" class='come'>"+name+"</li>";
    				var handleHtml = n.status == 2 ? "未使用" : n.status == 3 ? "已使用" : "已过期";  
    				html += "<li>"+handleHtml+"</li>";
            	}
				html += "</ol>";
            	html += "</ul>";
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
                	getCouponDataList(page_index,divid,pagediv,title);
                }
            });  
            }   
        }   
    });
};

//使用优惠劵红包
function useRedPackage(obj,data_no,money){
	//使用优惠劵红包
 	var $this = $(obj);
 	if($this.attr("status") == "true"){
 		$this.attr("status",false);
 		$this.text("正在提交中...");
 		$.post(basepath + "user/coupon/employ", {id:data_no,ajax:1}, function(data) {
 			if (data.success) {
 				$this.attr("status",true);
 				$this.text("立即使用");
 				if(data.message!="" && data.message!=null){
 					if(data.message == "notFindData"){
 						showMsgDialog("提示","未找到该优惠劵数据！");
 						return;
 					}else if(data.message == "notUse"){
 						clickCloseRefresh("提示","该优惠劵为非红包类型，不能当红包使用！");
 						return;
 					}else if(data.message == "isUse"){
 						showMsgDialog("提示","该优惠劵已经使用过，不能重复使用！");
 						return;
 					}else{
 						showMsgDialog("提示","该优惠劵已经使过期，无法使用！");
 						return;
 					}
 				}else{
 					openWindow("#redDev",function(){$('#redMoney').html('获得'+money+'元现金红包')});
 				}
 			} else {
 				showMsgDialog("提示","系统繁忙，请重试......");
 			}
 		}, "json");
 	}
}

//关闭红包使用提示框
function closeUseRedPackageDev(){
	closeWindow("#redDev",function(){window.location.reload();});
}