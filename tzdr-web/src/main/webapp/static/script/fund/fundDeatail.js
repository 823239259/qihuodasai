
var items_per_page = 10;
var page_index = 0;
var isOut = false; 
$(document).ready(function(){
	$(".fr a").removeClass("dja");
	$(".account").addClass("dja");
	$("#oAccount").find("a").removeClass("on");
	$('.navlist li a').removeClass('on');
	$("#nav_my").addClass("on");
	inittab();
	getDataList(page_index,"","","","","","all");
	getDataList(page_index,"1,2,3,4","","","payfundSearchresult","payfundPagination","pay");
	getDataList(page_index,"11,12,10,15,16,17,18,25,26","","","loanSearchresult","loanPagination","loan");
	
	getDataList(page_index,"11,12,25,26","","","interestSearchresult","interestPagination","interest");
	getDataList(page_index,"13","","","unionSearchresult","unionPagination","unioin");
	
	//点击左边菜单
	$('.uc_sidebar').find("div.uc_nav ul a").each(function(){
		$(this).removeClass('on');
 	});
	$("#fund").parent().addClass('on');
});


//分页查询充值记录
function getDataList(index,type,starttime,endtime,divid,pagediv,title){   
     pagediv=pagediv||'Pagination';
	 divid=divid||'Searchresult';
	    var pageIndex = index; 
	  
	    $.ajax({   
	        type: "POST",   
	        url: basepath+"fund/fundHistory",   
	        data: {"pageIndex":pageIndex,'perPage':items_per_page,"type":type,"starttime":starttime,"endtime":endtime},   
	        dataType: 'json',   
	        contentType: "application/x-www-form-urlencoded",   
	        success: function(msg){  
	        	
	            var total =msg.totalCount;   
	            var html = '';   
	            
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
	            	if(statusvalue==null)
	            		statusvalue="";
	            	html +="<ol>";
	            	html+="<li class='uc_fsl165'>"+getFormatDateByLong(n.addtime,'yyyy-MM-dd hh:mm:ss')+"</li>";
	            	html+="<li class='uc_fsl200'>"+n.typevalue+"</li>";
	            	html+="<li class='uc_fsl100'>"+inmoney+"</li>";
	            	html+="<li class='uc_fsl100'>"+outmoney+"</li>";
	            	if(title=="all"){
	            		html+="<li class='uc_fsl100'>"+$.formatMoney(n.amount,2)+"元</li>";
	            	}
	            	html+='<li class="uc_fsl100 uc_fslcon" id="fslcon'+title+i+'">'+
					"<a href='javascript:void(0);' onclick='javascript:showdetail("+showid+")' class='uc_fsllink'>详情</a>"+
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
	var detailUrl  = "trade/detail/";
	var lidStr = new String(obj.lid);
	if ((8==lidStr.length) && ("HK"==lidStr.substring(0,2))){
		detailUrl = "uhkstock/detail/";
	}else if((9==lidStr.length) && ("HM"==lidStr.substring(0,2))){
		detailUrl = "usertogether/detail/";
	}
	
	if((9==lidStr.length) && ("HM"==lidStr.substring(0,2))){
		if(type=="15"||type=="10"||type=="16"||type=="18" || type=="27"){
			detail="合买方案["+obj.lid+"]</a>"+obj.remark;
		}else if(type=="11" || type=="12"){
			detail="合买方案["+obj.lid+"]-["+obj.rid+"]</a>"+obj.remark;
		}else if(type=="17"){
			detail="合买方案["+obj.lid+"]</a>"+obj.remark;
		}else{
			detail=obj.remark;
		}
	}else{
		if(type=="15"||type=="10"||type=="16"||type=="18" || type=="27"){
			detail="方案<a href='"+basepath+detailUrl+obj.lid+"'>["+obj.lid+"]</a>"+obj.remark;
		}else if(type=="11" || type=="12"){
			detail="方案<a href='"+basepath+detailUrl+obj.lid+"'>["+obj.lid+"]-["+obj.rid+"]</a>"+obj.remark;
		}else if(type=="17"){
			detail="方案["+obj.lid+"]</a>"+obj.remark;
		}else{
			detail=obj.remark;
		}
	}
	
	
	
	return detail;
}

function showdetail(titleid){
	$('#detaildiv'+titleid).show();
	$("#fslcon"+titleid).css('z-index', '5');
}

function closedetail(titleid){
	$('#detaildiv'+titleid).hide();
	$("#fslcon"+titleid).css('z-index', '0');
}

//初始化tab页面
inittab=function(id){
			id=id||'fundtab';
			var tts=$("#"+id).find('ul.uc_paynav a');
			var tis=$("#"+id).find('div.tabcon div.subtab');
			tts.first().addClass('on');
			tis.first().show().siblings().hide();
			tts.click(function(){
				
				//tts.removeClass('on');
				var i = tts.index(this);
				if(i>0){
					return;
				}
				var currTab = tts.eq(i);
				var currTabCon=tis.eq(i);
				if(currTab.hasClass('on'))return;
				$(this).addClass('on').parent().siblings().children().removeClass('on');
				var text=currTab.text();
				 currTabCon.show().siblings().hide();
			});
}

//所有查询
function findAllData(divid,pagediv){
	
	
	$("#Pagination").html("");
	var starttime=$("#allstarttime").val();
	var endtime=$("#allendtime").val();
	var type=$("#fundtype").attr("data-id");
	if(starttime!="" && endtime!=""){
		if(starttime>endtime){
			showMsgDialog("提示","开始时间不能大于结束时间");
			return;
		}
	}
	
	getTitleData(starttime,endtime,type,"incount","insummoney","outcount","outsummoney");
	getDataList(page_index,type,starttime,endtime,divid,pagediv,"all");
}

//查询收入和支出
function getTitleData(starttime,endtime,type,incount,insummoneydiv,outcountdiv,outsummoneydiv){
	 $.post(basepath+"fund/getTitleData",{"starttime":starttime,"endtime":endtime,"type":type,"isAjax":"1"},function(data){  
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


//充值提现查询
function findpayfund(divid,pagediv){
	var starttime=$("#paystarttime").val();
	var endtime=$("#payendtime").val();
	var type=$("#changetype").attr("data-id");
	if(starttime!="" && endtime!=""){
		if(starttime>endtime){
			showMsgDialog("提示","开始时间不能大于结束时间");
			return;
		}
	}
	
	getTitleData(starttime,endtime,type,"inpaycount","inpaysum","outpaycount","outpaysum");
	
	getDataList(page_index,type,starttime,endtime,divid,pagediv,"pay");
}

//融资查询
function findfundLoan(divid,pagediv){
	$("#"+pagediv).html("");
	var starttime=$("#loanstarttime").val();
	var endtime=$("#loanendtime").val();
	var type=$("#fundLoan").attr("data-id");
	if(starttime!="" && endtime!=""){
		if(starttime>endtime){
			showMsgDialog("提示","开始时间不能大于结束时间");
			return;
		}
	}
	getTitleData(starttime,endtime,type,"inloanfundcount","inloanfundsum","outloanfundcount","outloanfundsum");
	
	getDataList(page_index,type,starttime,endtime,divid,pagediv,"loan");
}



//利润和管理费查询
function findinterestdata(divid,pagediv){
	$("#"+pagediv).html("");
	var starttime=$("#intereststarttime").val();
	var endtime=$("#interestendtime").val();
	var type=$("#interest").attr("data-id");
	if(starttime!="" && endtime!=""){
		if(starttime>endtime){
			showMsgDialog("提示","开始时间不能大于结束时间");
			return;
		}
	}
	getTitleData(starttime,endtime,type,"interestIncount","interestInsum","interestcount","interestsum");
	getDataList(page_index,type,starttime,endtime,divid,pagediv,"interest");
}

//佣金
function finduniondata(divid,pagediv){
	$("#"+pagediv).html("");
	var starttime=$("#unionstarttime").val();
	var endtime=$("#unionendtime").val();
	var type=$("#union").attr("data-id");
	if(starttime!="" && endtime!=""){
		if(starttime>endtime){
			showMsgDialog("提示","开始时间不能大于结束时间");
			return;
		}
	}
	
	getTitleData(starttime,endtime,type,"inunioncount","inunionsum","","");
	getDataList(page_index,type,starttime,endtime,divid,pagediv,"union");
}

showtab=function(id,tabnum){
	id=id||'banktab';
	var tts=$("#"+id).find('ul.uc_paynav a');
	var tis=$("#"+id).find('div.tabcon div.subtab');
	tts.removeClass('on');
	var currTab = tts.eq(tabnum);
	var currTabCon=tis.eq(tabnum);
	if(currTab.hasClass('on'))return;
	var text=currTab.text();
	if(text=="提现记录"){//充值记录查询数据库
		getDataList(page_index);
	}
	 tts.eq(tabnum).addClass('on');
	 currTabCon.show().siblings().hide();
}
	 
	