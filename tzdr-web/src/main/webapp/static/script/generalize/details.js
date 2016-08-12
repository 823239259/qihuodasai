var countOfCurrentPage = 10;
var currentPage = 0;
var items_per_page = 10;
var page_index = 0;
$(document).ready(function(){
	//设置当前停留顶菜单位置
	$(".navlist li a").removeClass("on");
	$("#nav_my").addClass("on");
	//设置当前停留左菜单位置
	$("#toGeneralize").parent().addClass("on");
	
	iBShare.init();    //初始化分享控件  
	
	//切换tab
	$(".generalize").live("click",function(){
		var $this = $(this);
		$("#oAccount").find("a").removeClass("on");
		$this.parent().find("a").removeClass("on");
		$this.find("a").addClass("on");
		$(".generalizeSubtab").css({display:"none"});
		var id = $.trim($this.attr("id"));
		
		if (id=="detail") {  //推广详情
			$("#detailDiv").css({display:""});
		}
		else if (id == "subordinates") {  //我的下级
			$("#subordinatesDiv").css({display:""});
			getSubordinatesDataList();
		}
		else if (id == "visitors") { //访问记录
			$("#visitorsDiv").css({display:""});
			getVisitorsDataList();
		}
		else if (id == "generalizemoney") { //推广赚钱
			$("#generalizemoneyDiv").css({display:""});
			getDataList(page_index,"13","","","unionSearchresult","unionPagination","unioin");
		}
		else if (id == "incomeQuery") {
			$("#incomeQueryDiv").show();
			agentReturnData("unionSearchresult_agent","PagVisitorsInation_agent");
		}
		else {  //推广链接
			$("#generalizeDiv").css({display:""});
		}
	});
	
	//复制链接
	var clip = new ZeroClipboard($(".copy"), {
		moviePath: basepath+"static/script/common/ZeroClipboard.swf"
	});
	clip.on("complete", function(client, args){
		/*alert("已复制到粘贴板");*/
		var $this = $(this);
        var $target = $this.prev(),
                $copy = $this.closest('.uc_spl_link'),
                $str = $('<div class="pop">已复制到粘贴板</div>');
        var a =$target.select();
        $copy.find('.pop').stop(true).remove();
        $str.appendTo($copy).fadeIn();
        $(".pop").css({
            position: "absolute",
            top: "-22px",
            right: "56px",
        	color: "#fc3"
        });
        setTimeout(function() {
            $copy.find('.pop').fadeOut();
        }, 3000);
	});	
	
	//设置默认返点
	$(".updateDefaultRebate").live("click",function(){
		var $this = $(this);
		var rebate = $(".rebate");
		$(".updateRebateTitle").text("设置下级默认返点");
		rebate.val($.trim($(".subordinateDefaultRebate").val()));
		rebate.attr("data-id","");
		$(".updateRebateDiv").css({display:""});
	});
	
	//修改下级返点
	$(".updateSubordinatesRebate").live("click",function(){
		var $this = $(this);
		var rebate = $(".rebate");
		$(".updateRebateTitle").text("修改下级返点");
		rebate.val($this.find("a").attr("data-value"));
		rebate.attr("data-id",$this.find("a").attr("data-id"));
		rebate.attr("data-value",$this.find("a").attr("data-value"));
		$(".updateRebateDiv").css({display:""});
	});
	
	//关闭、取消设置/修改返点
	$(".close").live("click",function(){
		var $this = $(this);
		var rebate = $(".rebate");
		$(".updateRebateTitle").text("设置下级默认返点");
		rebate.val("");
		rebate.attr("data-id","");
		rebate.attr("data-value","");
		$(".updateRebateDiv").css({display:"none"});
	});
	
	//不允许返点为非数据类型
	$(".rebate").keypress(T.numKeyPress).keyup(function() {
		$(this).focus();
	});
	
	//更新返点
	$(".updateRebate").live("click",function(){
		var $this = $(this);
		var rebate = $(".rebate");
		var rebateValue = $.trim(rebate.val());   //新返点
		var myRebateValue = $.trim($(".myRebate").val());   //当前用户返点
		var subordinateDefaultRebate = $.trim($(".subordinateDefaultRebate").val());  //当前用户默认返点
		var uid = $.trim(rebate.attr("data-id"));          //修改返点用户ID
		var uRebate = $.trim(rebate.attr("data-value"));   //修改返点用户返点
		if(rebateValue == null || rebateValue == undefined || rebateValue.length <= 0 ||  rebateValue == 'undefined'){
			$.alertTip($(".rebate"),"请输返点值 !");
			return; 
		}
		var data={},url;
		if(uid == null || uid == undefined || uid.length <= 0 ||  uid == 'undefined'){
			var   reg=/^[1-9]*[1-9][0-9]*$/; 
			if(!(rebateValue.length == 1 && rebateValue == 0) && reg.test(rebateValue) == false){
				$.alertTip($(".rebate"),"返点值必须是正整数 !");
				return;
			}/*else if(rebateValue.length == 1 && rebateValue == 0){
				$.alertTip($this.parent(),"你的返点为零，不能设置下级返点，请联系你的上级为你增加返点 !");
				return;
			}*/
			url = "generalize/updateDefaultRebate";
			data = {rebate : rebateValue,ajax:1};
		}else{
			var   reg=/^[1-9]*[1-9][0-9]*$/; 
			if(reg.test(rebateValue) == false){
				$.alertTip($(".rebate"),"返点值必须是正整数 !");
				return;
			}else if(Number(rebateValue) < Number(uRebate)){
				$.alertTip($(".rebate"),"返点值只能升不能降 !");
				return;
			}
			url = "generalize/updateSubordinate";
			data = {uid : uid,rebate : rebateValue,ajax:1};
		}
		
		if(Number(rebateValue) > Number(myRebateValue)){
			$.alertTip($(".rebate"),"返点值不能大于自己的返点值 !");
			return;
		}else if(Number(rebateValue) > 100){
			$.alertTip($(".rebate"),"返点值必须小于100 !");
			return;
		}
		//更新返点
		$.post(basepath+url,data,function(data){
			if(data.success){
				if(data.message != null && data.message != ""){
					if(data.message == "rebateIsNull"){
						$.alertTip($(".rebate"),"请输返点值 !");
					}else if(data.message == "rebateNotPositiveInt"){
						$.alertTip($(".rebate"),"返点值必须是正整数 !");
					}else{
						$.alertTip($this,"返点值只能升不能降或不能大于自己的返点值 !");
					}
				}else{
					getSubordinatesDataList();
					$(".updateRebateTitle").text("设置下级默认返点");
					rebate.val("");
					rebate.attr("data-id","");
					rebate.attr("data-value","");
					if(uid == null || uid == undefined || uid.length <= 0 ||  uid == 'undefined'){
						$(".subordinateDefaultRebate").val(rebateValue);
						$("#mySubordinateDefaultRebate").text("下级默认返点："+rebateValue+"%");
					}
					$(".updateRebateDiv").css({display:"none"});
				}
			}else if(!data.success && data.errorCode=='errorCode'){
				$.alertTip($this,"系统错误...... ");
			}
		},"json");
	});
	
	//设置/修改返点表单对象
	var form = $(".updateRebateDiv");
	
	// 回车事件
	form.on("keypress", "input", function(event){
		if(event.keyCode == 13){
			$(".updateRebate").trigger("click");
			return false;
		}
	})
});

//分页查询访问记录
function getVisitorsDataList(index){   
	$.post(basepath+"generalize/visitors",{currentPage:index == null || index == undefined || index == 'undefined' ? 1:index+1,countOfCurrentPage:countOfCurrentPage,ajax:1},function(data){
		if(data.success){
			if(data.message == null || data.message == ""){
				$(".totalVisitClieantIp").text(data.data.totalVisitClieantIp);
				$(".totalVisit").text(data.data.totalVisit);
				var total = data.data.pageInfo.totalCount;   
				var visitorsListHtml = '';   
				$(data.data.pageInfo.pageResults).each(function(){
					visitorsListHtml +="<ol>";
					visitorsListHtml+="<li class='uc_tl120'>"+this.clieantIp+"</li>";
					visitorsListHtml+="<li class='uc_tl160'>"+getFormatDateByLong(this.createdate,'yyyy-MM-dd hh:mm:ss')+"</li>";
					visitorsListHtml +="</ol>";
				});
				var activeElement = $(".visitorsList");
				activeElement.empty();
				$('.visitorsList').html(visitorsListHtml); 
				//分页-只初始化一次   
				if($("#PagVisitorsInation").html().length == ''){   
					$("#PagVisitorsInation").pagination(total, {   
						'items_per_page'      : countOfCurrentPage,
						'num_edge_entries'    : 2,   
						'prev_text'           : "上一页",   
						'next_text'           : "下一页",   
						'callback'            : pageVisitorsSelectCallback   
					});   
				} 
			}  
		}else if(!data.success && data.errorCode=='errorCode'){
			showMsgDialog("提示","系统错误......");
		}
	},"json");
}   

//访问记录分页回调
function pageVisitorsSelectCallback(page_index, jq){
	getVisitorsDataList(page_index); 
}

//分页查询我的下级
function getSubordinatesDataList(index){   
	$.post(basepath+"generalize/subordinates",{currentPage:index == null || index == undefined || index == 'undefined' ? 1:index+1,countOfCurrentPage:countOfCurrentPage,ajax:1},function(data){
		if(data.success){
			if(data.message == null || data.message == ""){
				var total = data.data.pageInfo.totalCount;   
				var subordinatesListHtml = '';   
				$(data.data.pageInfo.pageResults).each(function(){
					subordinatesListHtml +="<ol>";
					subordinatesListHtml+="<li class='uc_tl120'>"+this.mobile+"</li>";
					subordinatesListHtml+="<li class='uc_tl50'>"+this.tname+"</li>";
					subordinatesListHtml+="<li class='uc_tl50'>"+(this.userGrade == null || this.userGrade == 0 ? "股民1级" : this.userGrade == 1 ? "股师": "股神")+"</li>";
					/*subordinatesListHtml+="<li class='uc_tl50'>"+this.rebate+"</li>";*/
					subordinatesListHtml+="<li class='uc_tl90'>"+this.totalChild+"</li>";
					subordinatesListHtml+="<li class='uc_tl90'>"+this.allChildNumber+"</li>";
					//subordinatesListHtml+="<li class='uc_tl110'><em>"+(this.totalReturnCommission).toFixed(2)+"</em></li>";
					//subordinatesListHtml+="<li class='uc_tl139'>"+getFormatDateByLong(this.ctime,'yyyy-MM-dd hh:mm:ss')+"</li>";
					//subordinatesListHtml+="<li class='uc_lowerbtn updateSubordinatesRebate'><a href='javascript:void(0);' data-id="+this.id+" data-value='"+this.rebate+"'>修改</a></li>";
					subordinatesListHtml +="</ol>";
				});
				$('.subordinatesList').html(subordinatesListHtml); 
				//分页-只初始化一次   
				if($("#PagSubordinatesInation").html().length == ''){   
					$("#PagSubordinatesInation").pagination(total, {   
						'items_per_page'      : countOfCurrentPage,
						'num_edge_entries'    : 2,   
						'prev_text'           : "上一页",   
						'next_text'           : "下一页",   
						'callback'            : pageSubordinatesSelectCallback   
					});   
				} 
			}  
		}else if(!data.success && data.errorCode=='errorCode'){
			showMsgDialog("提示","系统错误......");
		}
	},"json");
}   

//我的下级分页回调
function pageSubordinatesSelectCallback(page_index, jq){
	getSubordinatesDataList(page_index); 
}

//佣金
function finduniondata(divid,pagediv){
	$("#"+pagediv).html("");
	var starttime=$("#unionstarttime").val();
	var endtime=$("#unionendtime").val();
	var type=$("#union").attr("data-id");
	var fourMobile = $("#fourthMobile").val();
	//alert(fourMobile + "---");
	if(starttime!="" && endtime!=""){
		if(starttime>endtime){
			showMsgDialog("提示","开始时间不能大于结束时间");
			return;
		}
	}
	
	//getTitleData(starttime,endtime,type,fourMobile,"inunioncount","inunionsum","","");
	getDataList(page_index,type,starttime,endtime,fourMobile,divid,pagediv,"union");

}

//佣金
function agentReturnData(divid,pagediv){
	$("#"+pagediv).html("");
	var starttime=$("#unionstarttime_a").val();
	var endtime=$("#unionendtime_a").val();
	//var type=$("#union").attr("data-id");
	//var fourMobile = $("#fourthMobile").val();
	//alert(fourMobile + "---");
	if (starttime!="" && endtime!="") {
		if(starttime>endtime){
			showMsgDialog("提示","开始时间不能大于结束时间");
			return;
		}
	}
	$("#"+pagediv).html("");
	//getTitleData(starttime,endtime,type,fourMobile,"inunioncount","inunionsum","","");
	getAgentDataList(page_index,starttime,endtime,divid,pagediv,"union");

}


//分页查询
function getAgentDataList(index,starttime,endtime,divid,pagediv,title){   
     pagediv=pagediv||'Pagination';
	 divid=divid||'Searchresult';
	    var pageIndex = index; 
	    $.ajax({   
	        type: "POST",   
	        url: basepath+"fund/fundAgentReturn",   
	        data: {"pageIndex":pageIndex,'perPage':items_per_page,"addtime_start":starttime,"addtime_end":endtime},   
	        dataType: 'json',   
	        contentType: "application/x-www-form-urlencoded",   
	        success: function(msg){  
	            var total =msg.totalCount;   
	            var html = '';
	            //$("#inunioncount").html(msg.totalCount);
	            $.each(msg.pageResults,function(i,n){
	            	var showid='"'+title+i+'"';
	            	var outmoney="";
	            	var inmoney="";
	            	if(n.money<0){
	            		outmoney=Math.abs(n.money)+"元";
	            	}else{
	            		inmoney=n.money+"元";
	            	}
	            	var detail=getdetail(n);
	            	var statusvalue=n.typevale;
		            //$("#inunionsum").html(n.totalAmount);
	            	
	            	if(statusvalue==null)
	            		statusvalue="";
	            	html +="<ol>";
	            	html+="<li class='uc_fsl165'>"+n.addDateStr+"</li>";
	            	html+="<li class='uc_fsl100'>"+n.childNumber+"</li>";
	            	html+="<li class='uc_fsl100'>"+n.allChildNumber+"</li>";
	            	html+="<li class='uc_fsl100'>"+n.totalAmountStr+"</li>";
	            	html+="<li class='uc_fsl200'>"+n.returnAmountStr+"</li>";
	            	//html+="<li class='uc_fsl100'>"+outmoney+"</li>";
	            	if(title=="all"){
	            		html+="<li class='uc_fsl100'>"+n.amount+"元</li>";
	            	}
	            	/**
	            	html+='<li class="uc_fsl100 uc_fslcon" id="fslcon'+title+i+'">'+
					"<a href='javascript:void(0);' onclick='javascript:showdetail("+showid+")' class='uc_fsllink'>详情</a>"+
					'<div class="uc_fslpromt" id="detaildiv'+title+i+'"  style="display:none;">'+
						'<i class="uc_ucp_icon"></i>'+
						'<p class="uc_flspormtbox">详情：'+detail+'</p>'+
						"<a href='javascript:void(0);' onclick='javascript:closedetail("+showid+")' class='uc_fslclose'></a>"+
					'</div>'+
				'</li>';
				**/
	            	html +="</ol>";
	            	
	            }); 
	        	//var endtime=$("#unionendtime_a").val();
	            $('#'+divid).html(html); 
	           //分页-只初始化一次   
	            if($("#"+pagediv).html()== ''){ 
        		   $("#"+pagediv).pagination(total, {   
                    'items_per_page'      : items_per_page,
                    'num_edge_entries'    : 2,   
                    'prev_text'           : "上一页",   
                    'next_text'           : "下一页",   
                    'callback'            : function pageCallback(page_index){
                    	getAgentDataList(page_index,starttime,endtime,divid,pagediv,title);
                    }
                });  
	            }   
	        }   
	    });   
	}







//查询收入和支出
function getTitleData(starttime,endtime,type,fourMobile,incount,insummoneydiv,outcountdiv,outsummoneydiv){
	 $.post(basepath+"fund/getTitleData",{"addtimeStr_start":starttime,"addtimeStr_end":endtime,"type":type,"isAjax":"1","mobile":fourMobile},function(data){  
			if(data.success){
				$("#"+incount).html(data.obj.count);
				$("#"+outcountdiv).html(data.obj.outcount);
				var summoney=data.obj.summoney==null?"0.00":data.obj.summoney;
				var outsummoney=data.obj.outsummoney==null?"0.00":data.obj.outsummoney;
				
				var smoney = parseFloat(summoney).toFixed(2);  
				var omoney=parseFloat(outsummoney).toFixed(2);
				$("#"+insummoneydiv).html(smoney);
				$("#"+outsummoneydiv).html(omoney);
			}
		},"json"); 
}

//分页查询
function getDataList(index,type,starttime,endtime,fourMobile,divid,pagediv,title){   
     pagediv=pagediv||'Pagination';
	 divid=divid||'Searchresult';
	    var pageIndex = index; 
	    $.ajax({   
	        type: "POST",   
	        url: basepath+"fund/fundHistorySelfAndChild",   
	        data: {"pageIndex":pageIndex,'perPage':items_per_page,"type":type,"addtimeStr_start":starttime,"addtimeStr_end":endtime,"mobile":fourMobile},   
	        dataType: 'json',   
	        contentType: "application/x-www-form-urlencoded",   
	        success: function(msg){  
	        	
	            var total =msg.totalCount;   
	            var html = '';
	            $("#inunioncount").html(msg.totalCount);
	            $.each(msg.pageResults,function(i,n){
	            	var showid='"'+title+i+'"';
	            	var outmoney="";
	            	var inmoney="";
	            	if(n.money<0){
	            		outmoney=Math.abs(n.money)+"元";
	            	}else{
	            		inmoney=n.money+"元";
	            	}
	            	var detail=getdetail(n);
	            	var statusvalue=n.typevale;
		            $("#inunionsum").html(n.totalAmount);
	            	
	            	if(statusvalue==null)
	            		statusvalue="";
	            	html +="<ol>";
	            	html+="<li class='uc_fsl165'>"+n.addtimeStr+"</li>";
	            	html+="<li class='uc_fsl165'>"+n.mobile+"</li>";
	            	html+="<li class='uc_fsl100'>"+n.tname+"</li>";
	            	html+="<li class='uc_fsl100'>"+n.typeStr+"</li>";
	            	html+="<li class='uc_fsl100'>"+n.moneyStr+"</li>";
	            	//html+="<li class='uc_fsl100'>"+outmoney+"</li>";
	            	if(title=="all"){
	            		html+="<li class='uc_fsl100'>"+n.amount+"元</li>";
	            	}
	            	html+='<li class="uc_fsl100 uc_fslcon" id="fslcon'+title+i+'">'+
					"<a href='javascript:void(0);' onclick='javascript:showdetail("+showid+")' data-id='"+n.uid+"' data-date='"+n.addtimeStr+"' class='uc_fsllink'>详情</a>"+
					'<div class="uc_fslpromt" id="detaildiv'+title+i+'"  style="display:none;">'+
						'<i class="uc_ucp_icon"></i>'+
						'<p class="uc_flspormtbox">详情：'+detail+'</p>'+
						"<a href='javascript:void(0);' onclick='javascript:closedetail("+showid+")' class='uc_fslclose'></a>"+
					'</div>'+
				'</li>';
	            	html +="</ol>";
	            	
	            }); 
	            $('#'+divid).html(html); 
	           //分页-只初始化一次   
	            if($("#"+pagediv).html()== ''){ 
        		   $("#"+pagediv).pagination(total, {   
                    'items_per_page'      : items_per_page,
                    'num_edge_entries'    : 2,   
                    'prev_text'           : "上一页",   
                    'next_text'           : "下一页",   
                    'callback'            : function pageCallback(page_index){
                    	getDataList(page_index,type,starttime,endtime,divid,pagediv,title);
                    }
                });  
	            }   
	        }   
	    });   
	}


function getdetail(obj){
	var detail="";
	var type=obj.type;
	if(type=="15"||type=="10"||type=="16"||type=="18"){
		detail="方案<a href='"+basepath+"trade/detail/"+obj.lid+"'>["+obj.lid+"]</a>"+obj.remark;
	}else if(type=="11" || type=="12"){
		detail="方案<a href='"+basepath+"trade/detail/"+obj.lid+"'>["+obj.lid+"]-["+obj.rid+"]</a>"+obj.remark;
	}else if(type=="17"){
		detail="方案["+obj.lid+"]</a>"+obj.remark;
	}else{
		detail=obj.remark;
	}
	return detail;
}
function showdetail(titleid){
	var data_id = $('#fslcon'+titleid+' a').attr("data-id");
	var data_date = $('#fslcon'+titleid+' a').attr("data-date");
	data_date = data_date.split(" ")[0];
	$.post(basepath+"fund/getTotalTradeMoney",{"uid":data_id,"date":data_date},function(data){  
		if(data.success){
			var totalMoney = data.data.totalMoney == null || data.data.totalMoney == 0 ? '0.00' : $.formatMoney(data.data.totalMoney,2);
			$('#detaildiv'+titleid+' p').text("详情：操盘配额"+totalMoney+"。");
			$('#detaildiv'+titleid).show();
			$("#fslcon"+titleid).css('z-index', '5');
		}else if(!data.success){
			showMsgDialog("提示","获取信息失败！");
		}
	},"json"); 
}

function closedetail(titleid){
	$('#detaildiv'+titleid).hide();
	$("#fslcon"+titleid).css('z-index', '0');
}


//bShare分享  
var iBShare = {  
	//初始化  
	init: function() {  
		var $shareBox = $(".bshare-custom");  
		//加载分享工具  
		var tools = '<a title="分享到新浪微博" class="bshare-sinaminiblog"></a>';  
		tools += '<a title="分享到微信" class="bshare-weixin" href="javascript:void(0);"></a>';
		tools += '<a title="分享到QQ好友" class="bshare-qqim" href="javascript:void(0);"></a>'; 
		tools += '<a title="分享到腾讯微博" class="bshare-qqmb"></a>';    
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