
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
/*<!-- start 吉鹏代码 -->*/
var _zzsiteid="g2CiQ0pbhOF";
var _zzid = "g2CiQ0pbhOE";
(function() {
  var zz = document.createElement('script');
  zz.type = 'text/javascript';
  zz.async = true;
  zz.src = 'https:' == document.location.protocol ? 'https://daima.adpengshun.com/api/trace.js' : 'http://daima.adpengshun.com/trace/api/trace.js';
  var s = document.getElementsByTagName('script')[0];
  s.parentNode.insertBefore(zz, s);
})();
/*<!-- end 吉鹏代码 -->*/


$(document).ready(function(e) {
	$.ajaxSetup({ 
    	contentType : "application/x-www-form-urlencoded;charset=utf-8", 
    	complete : function(XMLHttpRequest, textStatus) { 
    	var sessionstatus = XMLHttpRequest.getResponseHeader("sessionstatus"); // 通过XMLHttpRequest取得响应头，sessionstatus， 
    	if (sessionstatus == "timeout") { 
    	// 如果超时就处理 ，指定要跳转的页面 
    	 window.location.replace(basepath+"login"); 
    	} 
      } 
    }); 
	$('.notic a').click(function() {
        $('.notic').slideUp();
    });
});	