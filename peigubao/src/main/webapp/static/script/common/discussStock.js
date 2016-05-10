
//登录同步论股系统
function discussStockLogin(key,callback){
	isOpanStockForum='false';
	if(isOpanStockForum!='true'){
		if(callback != null){
			callback();
		}
	}else{
		$.ajax({
			async: false,  
			type:"GET",
	        url: stockForumBasePath+'home/home/checklogin?key='+key,
	        dataType: "jsonp",
	        jsonp: "callback",
	        success: function (data) {
	            console.log(data);
	            if(callback != null){
	            	callback();
				}
	        }, 
	        error: function (XMLHttpRequest, textStatus, errorThrown) { 
	        	if(callback != null){
	            	callback();
				}
	        }
	    });
	}
}