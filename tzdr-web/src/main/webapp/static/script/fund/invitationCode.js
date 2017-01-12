
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
	getDataList(page_index,"","","","","","");
//	getDataList(page_index,"1,2,3,4","","","payfundSearchresult","payfundPagination","pay");
//	getDataList(page_index,"11,12,10,15,16,17,18,25,26","","","loanSearchresult","loanPagination","loan");
//	
//	getDataList(page_index,"11,12,25,26","","","interestSearchresult","interestPagination","interest");
//	getDataList(page_index,"13","","","unionSearchresult","unionPagination","unioin");
	
	//点击左边菜单
	$('.uc_sidebar').find("div.uc_nav ul a").each(function(){
		$(this).removeClass('on');
 	});
	$("#invitationCode").parent().addClass('on');
});

//分页查询充值记录
function getDataList(index,type,starttime,endtime,divid,pagediv,iphone,title){   
     pagediv=pagediv||'Pagination';
	 divid=divid||'Searchresult';
	    var pageIndex = index; 
	  
	    var http6 ="http://192.168.2.197:8080/tzdr-app/activity/getOldAndNewInvitedList";
	    var http8= "http://localhost:63342/web/erweima2.json"
	    var http9 =	basepath+"fund/fundHistory";
	    
	    $.ajax({   
	        type: "POST",   
	        url: http6, 
	        //data: {"pageIndex":pageIndex,'perPage':items_per_page,"type":type,"starttime":starttime,"endtime":endtime,"iphone":iphone}, 
	        data: {"pageIndex":pageIndex,'perPage':items_per_page,"type":type,"starttime":starttime,"endtime":endtime,"fourMobile":iphone,"mobile":"18280302936"},
	        dataType: 'json',   
	        contentType: "application/x-www-form-urlencoded",   
	        success: function(msg){ 
	            var total =msg.totalCount;   
	            var html = '';   
	            $.each(msg.data.pageResults,function(i,n){
	            	/*var showid='"'+title+i+'"';
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
	            		statusvalue="";*/
	            	html +="<ol>";
/*	            	html+="<li class='uc_fsl165'>"+getFormatDateByLong(n.addtime,'yyyy-MM-dd hh:mm:ss')+"</li>"; //时间
	            	html+="<li class='uc_fsl200'>"+13558767652+"</li>";											//电话号码
	            	html+="<li class='uc_fsl100'>"+"小白"+"</li>";												//姓名
	            	html+="<li class='uc_fsl100'>"+"佣金收入"+"</li>";											//佣金类型
	            	html+="<li class='uc_fsl100'>"+100+""+"元"+"</li>";											//佣金金额
*/	            	n.mobile=n.mobile.substring(0,3)+"****"+n.mobile.substring(7,11);
					n.tname=n.tname.substring(0,1)+"**";
	            	html+="<li class='uc_fsl165' style='width: 150px;'>"+n.mobile+"</li>";                                                 //手机号码
	            	html+="<li class='uc_fsl200' style='width: 50px;'>"+n.tname+"</li>";											       //姓名
	            	html+="<li class='uc_fsl100' style='width: 200px;'>"+getFormatDateByLong(n.ctime,'yyyy-MM-dd hh:mm:ss')+"</li>";       //注册时间
	            	html+="<li class='uc_fsl100' style='width: 200px;'>"+getFormatDateByLong(n.endTime,'yyyy-MM-dd hh:mm:ss')+"</li>";     //交易时间
	            	html+="<li class='uc_fsl100' style='width: 100px;'>"+n.sumLever+""+"手"+"</li>";										   //交易手数
	            	
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
	 	tts.removeClass('on');
		var i = tts.index(this);
		var currTab = tts.eq(i);
		var currTabCon=tis.eq(i);
		if(currTab.hasClass('on'))return;
		tts.eq(i).addClass('on');
		currTabCon.show().siblings().hide();
	});		
}

//所有查询

function findAllData(divid,pagediv){
	
	$("#Pagination").html("");
	var iphone=$("#iphone").val();
	var starttime=$("#allstarttime").val();
	var endtime=$("#allendtime").val();
	var type=$("#fundtype").attr("data-id");
	if(starttime!="" && endtime!=""){
		if(starttime>endtime){
			showMsgDialog("提示","开始时间不能大于结束时间");
			return;
		}
	}
	
	//getTitleData(starttime,endtime,type,"incount","insummoney");
	getDataList(page_index,type,starttime,endtime,divid,pagediv,iphone,"");
}

//查询收入
/*function getTitleData(starttime,endtime,type,incount,insummoneydiv){
	 $.post(basepath+"fund/getTitleData",{"starttime":starttime,"endtime":endtime,"type":type,"isAjax":"1"},function(data){  
		if(data.success){
			$("#"+incount).html(data.obj.count);
			var summoney=data.obj.summoney==null?"0.00":data.obj.summoney;
			var smoney = parseFloat(summoney).toFixed(2);  
			$("#"+insummoneydiv).html(smoney);
		}
	},"json"); 
}*/