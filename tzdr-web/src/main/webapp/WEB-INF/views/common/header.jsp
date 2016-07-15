<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.tzdr.common.utils.ConfUtil"%>
<div class="top-title">
    <div class="topctn">
        <div class="top_tel"><!--<i></i>-->全国客服热线：400-852-8008</div>
        <%
       		if(request.getSession().getAttribute("userName")!=null){
       	%>
       	<ul>
            <li class="download" style = "color:#999; font-size: 12px;"><em >你好，<a href="${ctx}/user/account" class="top_myt" style = "color:#fc3;"><%=request.getSession().getAttribute("userName").toString() %></a></em><a style="margin-left: 36px;" href="${ctx}/logout">退出</a></li>
            <li class="top_myc"><a href="${ctx}/user/account" class="on" target="_blank">我的账户</a></li>
            <li><a href="${ctx}/help?tab=software&leftMenu=1" target="_blank">交易软件下载</a></li>
           <%--  <li><a href="${ctx}/topic/app/" target="_blank" style="color:#f57c00;">手机APP</a></li>
            <li><a href="http://zhibo.tzdr.com" target="_blank" style="color:#f57c00;">喊单直播间</a></li> --%>
            <li><a href="${ctx}/help?tab=newbie&leftMenu=1" target="_blank">新手指南</a></li>
        </ul>
       	<%
       		}else{
       	%>
       	<ul>
       		<li class="sign"><a href="${ctx}/user/account">登录</a><span class="sign_span"> | </span><a href="${ctx}/signin">注册</a></li>
            <li><a href="${ctx}/help?tab=software&leftMenu=1" target="_blank">交易软件下载</a></li>
           <%--  <li><a href="${ctx}/topic/app/" target="_blank" style="color:#f57c00;">手机APP</a></li>
            <li><a href="http://zhibo.tzdr.com" target="_blank" style="color:#f57c00;">喊单直播间</a></li> --%>
            <li><a href="${ctx}/help?tab=newbie&leftMenu=1" target="_blank">新手指南</a></li>
        </ul>
       	<%
       		}
       	%>
       	<!-- 我的账户 -->
       	<div class="top_mynav top_mytel" style="display: none; top:1px;">
       		<% if(request.getSession().getAttribute("userName") != null){%>
            <h2><%=request.getSession().getAttribute("userName").toString() %></h2>
            <%} %>
            <a href="${ctx}/user/account">操盘账户</a>
            <a href="<%=ConfUtil.getContext("p2p.user.account")%>">投资账户</a>
        </div>
        <!-- 我的账户 -->
        <div class="top_mynav top_myname" style="display:none; top:1px;">
            <h2>我的账户</h2>
            <a href="${ctx}/user/account">操盘账户</a>
            <a href="<%=ConfUtil.getContext("p2p.user.account")%>">投资账户</a>
        </div>
    </div>
</div>
<div class="navbox">
    <div class="nav">
        <div class="navlogo"><a href="http://www.dktai.com"><img src="${ctx}/static/images/common-new/new_logo.png" title="维胜" alt="维胜"></a></div>
        <ul class="navlist">
            <li><a href="${ctx}/" class="on" style="padding: 0 16px 26px 16px;">首页</a></li>
           <%--  <li><a id="togetherli" href="${ctx}/together/index">股票合买</a><!-- <i></i> --></li>
            <li><a id="hkstockli" href="${ctx}/hkstock/index">港股操盘</a><img src="${ctx}/static/images/common/hkicon.gif" class="hkicon"></li>
            <li><a id="internationalFutures" href="${ctx}/ftse/index" class="nav_l_future">国际期货</a><i></i></li>
            <li><a id="productli" href="${ctx}/commodity/index">商品期货</a></li>
            <li><a id="future-index" href="${ctx}/future/index" class="nav_l_sif">股指期货</a><i></i></li>
            <li><a id="fxspot" href="${ctx}/topic/fxspot/">外汇现货</a></li>
            <li><a class="on"  href="#" style="padding: 27px 16px 26px 16px;">首页</a></li> --%>
            <li><a id="hengzhiqidai" href="${ctx}/hsi/index">恒指期货</a></li>
            <li><a id="guojiyuanyou" href="${ctx}/crudeoil/index">国际原油</a></li>
            <li><a id="fushia50" href="${ctx}/ftse/index">富时A50</a></li>
            <li><a id="guojizonghe" href="${ctx}/outDisk/index">国际综合</a></li>
           
            <%
        		if(request.getSession().getAttribute("userName") !=null){
        	%>
           		<li><a id="nav_my" href="${ctx}/user/account" class="nav_l_mc">我的账户</a></li>
        	<%
        		}else{
        	%>
            	<li><a id="nav_my" href="${ctx}/user/account" class="nav_l_mcnot">我的账户</a></li>
        	<%
        		}
        	%>
        	
        </ul>
        <!-- 股票合买 -->
        <div style="display: none;" class="nav_tknav nav_together">
            <h2><a href="${ctx}/together/index">股票合买</a></h2>
            <a href="${ctx}/together/index">发起合买</a>
            <a href="${ctx}/together/list">参与合买</a>
        </div>
        <!-- 国际期货 -->
        <div style="display: none;" class="nav_tknav nav_profur">
            <h2><a href="${ctx}/outDisk/index">国际期货</a></h2>
            <a href="${ctx}/outDisk/index">国际综合</a>
            <a href="${ctx}/ftse/index">富时A50</a>
            <a href="${ctx}/hsi/index">恒指期货</a>
            <a href="${ctx}/crudeoil/index">国际原油</a>
        </div>
        <!-- 商品期货 -->
        <div style="display: none;" class="nav_tknav nav_pronav">
            <h2><a href="${ctx}/commodity/index">商品期货</a></h2>
            <a href="${ctx}/commodity/index">商品综合</a>
            <a href="${ctx}/product/gold_index">沪金</a>
            <a href="${ctx}/product/sliver_index">沪银</a>
            <a href="${ctx}/product/copper_index">沪铜</a>
            <a href="${ctx}/product/rubber_index">橡胶</a>
        </div>
        <!-- 我的账户 -->
        <div style="display: none;" class="nav_tknav nav_mcnav">
            <h2><a href="${ctx}/user/account">我的账户</a></h2>
            <a href="${ctx}/user/account">操盘账户</a>
            <a href="<%=ConfUtil.getContext("p2p.user.account")%>">投资账户</a>
        </div>
        <!-- 股指期货 -->
        <div style="display: none;" class="nav_tknav nav_prosif">
            <h2><a href="${ctx}/future/index">股指期货</a></h2>
            <a href="${ctx}/future/index">期指随心乐</a>
            <a href="${ctx}/future/day_index">期指天天乐</a>
            <%-- <%
           		String futUrl = ConfUtil.getContext("tzdr.future.url");
        		if(request.getRemoteUser()!=null){
        	%>
        		<a href="<%=futUrl%>/login/virtual.jsp">期指点点乐</a>
        	<%
        		}else{
        	%>
        		<a href="<%=futUrl%>/virtualFuserTrade/open">期指点点乐</a> 
        	<%
        		}
        	%> --%>
        </div>
    </div>
</div>
<!-- 浮动层 -->
<div class="floatlayer">
    <!-- 联系客服、返回顶部 -->
    <div class="fl_server">
       <!--  <p class="fl_sv_tent"><a href="http://wpa.b.qq.com/cgi/wpa.php?ln=1&amp;key=XzkzODA2MTY0MV8zMDUwODdfNDAwMDIwMDE1OF8yXw" target='_blank'></a></p> -->
        <div class="fl_sv_code">
           <%--  <a href="${ctx}/topic/app/" target="_blank"></a>  --%>
            <%--  <div class="fl_sv_codetk" style="display: none;"><img src="${ctx}/static/images/common-new/code.png"><p>扫二维码访问<br>维胜手机站</p></div> --%>
        </div>
        <p class="fl_sv_up"><a href="javascript: scrollTop();"></a></p>
    </div>
</div>
<script type="text/javascript">
$(document).ready(function() {
	/*  $('.top_myc').hover(function() {
	        $('.top_myname').show();
	    }); 
	    $('.top_myt').hover(function() {
	        $('.top_mytel').show();
	    });*/
	 
	    $('.top_mynav').hover(function() {
		}, function() {
		    $(this).hide();
		});
    $('.nav_l_together').hover(function() {
		$('.nav_together').show();
	});
	$('.nav_l_future').hover(function() {
		$('.nav_profur').show();
	});
	$('.nav_l_pro').hover(function() {
		$('.nav_pronav').show();
	});
	$('.nav_l_sif').hover(function() {
		$('.nav_prosif').show();
	});
	/* $('.nav_l_mc').hover(function() {
		$('.nav_mcnav').show();
	}); */
	
	$('.nav_tknav').hover(function() {
	}, function() {
	    $(this).hide();
	});
	
    $('.fl_sv_code a').hover(function() {
        $('.fl_sv_codetk ').show();
    }, function() {
        $('.fl_sv_codetk ').hide();
    }); 
    /* $('.top_myc').hover(function() {
        $('.top_myname').show();
    });
    $('.top_myt').hover(function() {
        $('.top_mytel').show();
    }); */
    $('.top_mynav').hover(function() {
	}, function() {
	    $(this).hide();
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
	//$('html, body').animate({scrollTop:$('.bottom').offset().top}, 800);
}
</script>