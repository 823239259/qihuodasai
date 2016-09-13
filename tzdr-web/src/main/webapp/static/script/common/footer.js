$(document).ready(function() {
    $('.top_mynav').hover(function() {
	}, function() {
	    $(this).hide();
	});
    $('.fl_sv_code a').hover(function() {
        $('.fl_sv_codetk ').show();
    }, function() {
        $('.fl_sv_codetk ').hide();
    });    
    /*二维码*/
    $('.follow .erweima').hover(function() {
        $('.erweima-wxtk').show();
    }, function() {
        $('.erweima-wxtk').hide();
    });
    // 加载最新公告
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
    })
    
    $(window).scroll(function () {
    	if(showNotice) {
		    var scrollTop = $(this).scrollTop();//滚动条位置
		    var scrollHeight = $(document).height();//高度
		    var windowHeight = $(this).height();//整体高度
		    if (scrollTop + windowHeight >= scrollHeight-80) {
		    	$(".notice-fixed").fadeOut(1000);
		    	$(".notice-relative").fadeIn(1000);
			} else {
				$(".notice-fixed").fadeIn(1000);
				$(".notice-relative").fadeOut(1000);
			}
    	}
	});
    
});
//检测公告
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
	return ""
}
	// 平滑滚动到顶部
function scrollTop() {
	$('html, body').animate({scrollTop: '0px'}, 800);
}
/* function scrollBottom() {
	$('html,body').animate({scrollTop: $(document).height()}, 800);
} */