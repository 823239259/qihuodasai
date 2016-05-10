<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<input id="noticeid" type="hidden" value="123456" />
<div class="tips_out site-notice notice-fixed" style="display: none;">
    <div class="tips">
        <div class="close" onclick="javascript: closeNotice();"></div>
        <div class="main">尊敬的用户：
           <p class="notice-content"></p>
        </div>
    </div>
</div>
<script type="text/javascript">
	$(document).ready(function() {
	    var showNotice = false;
	    var content="";
	    $.ajax({
	    	url:basepath+"findnewData",
	    	data:{},
	    	type:'POST',
	    	success:function(nitives){
	    		var reg1=new RegExp("&lt;","g"); 
	    		var reg2=new RegExp("&gt;","g"); 
	    		$(nitives).each(function(){
	    			content = $(this).attr("content");
	    			content=content.replace(reg1,"<");
	    			content=content.replace(reg2,">");
	    			$('.notice-content').html(content);
	    			$('#noticeid').val($(this).attr("version"));
	    		    // 检查公告
	    		    checkNotice();
	    		    showNotice = true;
	    		})
	    	},dataType:'json'
	    });
	});
	// 检测公告
	function checkNotice() {
		var noticeid = getCookie("noticeid");
		var loaclNoticeid = $("#noticeid").val();
		if(noticeid === loaclNoticeid) {
			$(".site-notice").remove();
		} else {
			$(".notice-fixed").fadeIn("slow");
		}
	}
	// 关闭公告
	function closeNotice() {
		$(".site-notice").remove();
		// cookie记录公告已删除
		addCookie("noticeid", $("#noticeid").val());
	}
	//添加cookie 
	function addCookie(objName, objValue){
		if(objValue==""){
			var Num="";
			for(var i=0;i<6;i++){ 
				Num+=Math.floor(Math.random()*10); 
			} 
			objValue=Num;
		}
		var days = 365; 
	    var exp = new Date(); 
	    exp.setTime(exp.getTime() + days*24*60*60*1000); 
	    document.cookie = objName+"="+ escape (objValue)+";path=/;expires="+exp.toGMTString(); 
	}
	//获取指定名称的cookie的值 
	function getCookie(c_name) {
		if (document.cookie.length > 0) {
			var c_start = document.cookie.indexOf(c_name + "=");
			if(c_start != -1) {
				c_start = c_start + c_name.length + 1; 
				c_end = document.cookie.indexOf(";", c_start)
				if(c_end == -1) {
					c_end = document.cookie.length;
				}
			    return unescape(document.cookie.substring(c_start, c_end))
			}
		}
		return "";
	}
</script>