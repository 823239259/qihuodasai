var countOfCurrentPage = 5;
var currentPage = 0;


function tentative(){
	showMsgDialog("提示","系统升级中，该功能暂停使用。");
	return;
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
		$.post(basepath + "usertogether/list.json", {type:type,countOfCurrentPage:countOfCurrentPage,currentPage:currentPage}, function(result) {
			if (result.success) {	
				$('#content').empty();
				var data=result.obj.pageResults;
				var total = result.obj.totalCount;   
				var table='<div class="uc_tlisttitle">';
				table+='<span class="fl">方案编号：{0}<i>{10}</i></span>';
				table+='<span class="fr">开始时间：{1}<i>结束时间：{2}</i></span>';	
				table+='</div>';
				table+='<ul class="uc_tlist">';
				table+='<li class="uc_tl120">';
				table+='	<h4 class="uc_tlist_title">总操盘资金</h4>';
				table+='	<p class="uc_tlist_con">{3}元</p>';
				table+='</li>';
				
			    table+='<li class="uc_tl120">';
				table+='	<h4 class="uc_tlist_title">操盘者出资</h4>';
				table+='	<p class="uc_tlist_con">{5}元</p>';
				table+='</li>';
				table+='<li class="uc_tl120">';
				table+='	<h4 class="uc_tlist_title">追加保证金</h4>';
				table+='	<p class="uc_tlist_con">{6}元</p>';
				table+='</li>';
				table+='<li class="uc_tl120">';
				table+='	<h4 class="uc_tlist_title">方案盈亏</h4>';
				table+='	<p class="uc_tlist_con">{7}</p>';
				table+='</li>';
				
				table+='<li class="uc_tl120">';
				table+='	<h4 class="uc_tlist_title">方案状态</h4>';
				table+='	<p class="uc_tlist_con">{8}</p>';
				table+='</li>';
				table+='<li class="uc_tlbtn">';
				table+='{11}<div class="ut_tlbtn_detail"><a href="'+basepath+'usertogether/detail/{0}" class="uc_tlbtn_btn" id="opt_btn{0}">操盘详细</a>';
				$.each(data, function(){				
					var totalAccrual="——元";
					if(this.status != 1 && 2 != this.feeType){
						totalAccrual=$.format('<i class="color4">{0}</i>元',this.totalAccrual == 0 ? '0.00' : $.formatMoney(this.totalAccrual,2));
						if(this.totalAccrual<0){
							totalAccrual=$.format('<i class="color3">{0}</i>元',this.totalAccrual == 0 ? '0.00' : $.formatMoney(this.totalAccrual,2));
						}
					}
					
					var tradeStatus = this.tradeStatus;
					var _iscNo = this.insuranceNo ? '保险单号：<a href="#" target="_balnk">'+ this.insuranceNo +'</a>':'';
					if(0 == this.status){ //开户    
						tradeStatus = '合买中';  
					}else if(1 == this.status){  //操盘  
						tradeStatus = '操盘中';//this.auditEndStatus == 0 ? '终结中' : this.auditEndStatus == 2 ? '终结失败':'操盘中';
					}else{  //终结    
						tradeStatus = this.auditStatus == 2 ?'合买失败':'已完结'; 
						totalAccrual = this.auditStatus == 2 ?'——':$.format('<i class="color'+(this.totalAccrual<0 ? 3 : 4)+'">{0}</i>元',(this.totalAccrual == 0 ? '0.00' : $.formatMoney(this.totalAccrual,2))); 
					} 
					
					var head=$.format(table,this.groupId,getFormatDateByLong(this.starttime,'yyyy-MM-dd'),getFormatDateByLong(this.endtime,'yyyy-MM-dd'),$.formatMoney(this.totalOperateMoney),$.formatMoney(this.totalLending),$.formatMoney(this.totalLeverMoney),$.formatMoney(this.totalAppendLeverMoney),totalAccrual,tradeStatus,"",_iscNo, '');
					$('#content').append('<div class="uc_tdone">'+head+'</div>');	
					$('#opt_btn'+this.groupId).removeClass('uc_tlbtn_btn').addClass('uc_tlbtn_btn_no');
					
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
	
	$('.uc_paynav li').click(function() {
		$('.uc_paynav li a.on').removeClass('on');
		$(this).find('a').addClass('on');
		var type=$(this).attr('data');
		if(type == 'all'){
			$("#Pagination").show();
			$("#Pagination").html("");
			list(type,countOfCurrentPage,1);	
		}else{
			$("#content").html("<div class='uc_bl_promt'>目前股票合买仅对邀请用户开放，全面开放时间待定，敬请期待！</div>");
			$("#Pagination").hide();
			
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
	$("#together").parent().addClass('on');
	
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
	
	
});