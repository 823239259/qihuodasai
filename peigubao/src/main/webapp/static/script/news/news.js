
var items_per_page = 10;
var page_index = 0;
$(document).ready(function(){
	 setcols();
	 $('.news_siderbar a').each(function() {
		    $(this).click(function() {
		    	var title=$(this).attr("title");
		        $('.news_siderbar a').removeClass('on');
		        $(this).removeClass('on');
		        $(this).addClass('on');
		        var id=$(this).attr("iddata");
		        var title=$(this).attr("title");
		        $("#newstitle").html(title);
		        $("#pagebtndiv").children("div").each(function() {
		    		$(this).html("");
		   	    });
		        window.location.href=basepath+"news/newsdata?colname="+id;
		        //getDataList(page_index,id);
		    });
		});  	
});


//分页查询充值记录
function getDataList(index,colid){
		var pageIndex = index;
	    $.ajax({   
	        type: "POST",   
	        url: basepath+"news/datapage",   
	        data: {"pageIndex":pageIndex,'perPage':items_per_page,"colid":colid},   
	        dataType: 'json',   
	        contentType: "application/x-www-form-urlencoded",   
	        success: function(msg){  
	            var total =msg.totalCount;  
	            var html = '';   
	            $.each(msg.pageResults,function(i,n){
	            	if(colid==""){
	            		html+="<li><a  href='"+basepath+"news/shownews/"+n.id+"?type=all'>"+"【"+n.parentConfig.name+"】"+n.name+"</a><i>"+n.defineReleaseTime.substring(0,10)+"</i></li>";
	    	        }else{
	    	        	
	            		html+="<li><a  href='"+basepath+"news/shownews/"+n.id+"'>"+n.name+"</a><i>"+n.defineReleaseTime.substring(0,10)+"</i></li>";
		    	     }
	            	}); 
	            $('.news_newslist').html(html); 
	           //分页-只初始化一次   
	            if($("#Pagination"+colid).html()== ''){
	                $("#Pagination"+colid).pagination(total, {   
	                    'items_per_page'      : items_per_page,
	                    'num_edge_entries'    : 2,   
	                    'prev_text'           : "上一页",   
	                    'next_text'           : "下一页",   
	                    'callback'            :function pageCallback(page_index){
	                    	
	                    	getDataList(page_index,colid);
	                    }  
	                });   
	            }   
	        }   
	    });   
	}   
	  



function setcols(){
	var ts=$(".news_siderbar").find('a');
	var id="";
	if($("#colnameon").attr("iddata")){
		 id=$("#colnameon").attr("iddata");
	}else{
		ts.eq(0).addClass("on");
		id=ts.eq(0).attr("iddata");
	}
	
	getDataList(page_index,id);
}



	