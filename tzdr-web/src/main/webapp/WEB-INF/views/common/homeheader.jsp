<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.tzdr.common.utils.ConfUtil"%>
<div class="top">
    <div class="topctn">
        <div class="top_tel">全国客服热线：400-852-8008</div>
        <%
       		if(request.getSession().getAttribute("userName")!=null){
       	%>
       	<ul>
            <li class="download"><em>你好，<a href="${ctx}/user/account" class="top_myt"><%=request.getSession().getAttribute("userName").toString() %></a></em><a href="${ctx}/logout">退出</a></li>
            <li class="top_myc"><a href="${ctx}/user/account" class="on">我的账户</a></li>
            <li><a href="${ctx}/help?tab=software&leftMenu=1" target="_blank">交易软件下载</a></li>
            <li><a href="${ctx}/topic/app/" target="_blank" style="color:#f57c00;">手机APP</a></li>
            <li><a href="http://zhibo.tzdr.com" target="_blank" style="color:#f57c00;">喊单直播间</a></li>
            <li><a href="${ctx}/help?tab=newbie&leftMenu=1" target="_blank">新手指南</a></li>
        </ul>
       	<%
       		}else{
       	%>
       	<ul>
            <li><a href="${ctx}/user/account">登录</a></li>
            <li><a href="${ctx}/signin">注册</a></li>
            <li><a href="${ctx}/help?tab=software&leftMenu=1" target="_blank">交易软件下载</a></li>
            <li><a href="${ctx}/topic/app/" target="_blank" style="color:#f57c00;">手机APP</a></li>
            <li><a href="http://zhibo.tzdr.com" target="_blank" style="color:#f57c00;">喊单直播间</a></li>
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
        </div>
        <!-- 我的账户 -->
        <div class="top_mynav top_myname" style="display:none; top:1px;">
            <h2>我的账户</h2>
            <a href="${ctx}/user/account">操盘账户</a>
        </div>
    </div>
</div>
<div class="navbox">
    <div class="nav">
        <div class="navlogo"><a href="http://www.vs.com"><img src="${ctx}/static/images/common-new/logo.png" title="维胜" alt="维胜"></a></div>
        <ul class="navlist">
            <li><a href="${ctx}/" class="on">首页</a></li>
            <li><a id="togetherli" href="${ctx}/together/index">股票合买</a><!-- <i></i> --></li> 
            <li><a id="hkstockli" href="${ctx}/hkstock/index">港股操盘</a><img src="${ctx}/static/images/common/hkicon.gif" class="hkicon"></li>
            <li><a id="internationalFutures" href="${ctx}/ftse/index" class="nav_l_future">国际期货</a><i></i></li>
            <li><a id="productli" href="${ctx}/commodity/index">商品期货</a></li>
           	<li><a id="fxspot" href="${ctx}/topic/fxspot/">外汇现货</a></li>
            <%-- <li><a id="future-index" href="${ctx}/future/index" class="nav_l_sif">股指期货</a><i></i></li> --%>
            
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
        <p class="fl_sv_tent"><a href="http://wpa.b.qq.com/cgi/wpa.php?ln=1&amp;key=XzkzODA2MTY0MV8zMDUwODdfNDAwMDIwMDE1OF8yXw" target='_blank'></a></p>
        <div class="fl_sv_code">            
            <a href="${ctx}/topic/app/" target="_blank"></a> 
            <%-- <div class="fl_sv_codetk" style="display: none;"><img src="${ctx}/static/images/common-new/code.png"><p>扫二维码访问<br>维胜手机站</p></div> --%>
        </div>
        <p class="fl_sv_up"><a href="javascript: scrollTop();"></a></p>
    </div>
</div>
<script type="text/javascript">
	$(document).ready(function() {
		 /* $('.top_myc').hover(function() {
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
	   /*  $('.top_myc').hover(function() {
	        $('.top_myname').show();
	    });   $('.top_myt').hover(function() {
	        $('.top_mytel').show();
	    }); */
	    $('.top_mynav').hover(function() {
		}, function() {
		    $(this).hide();
		});
	});
	// 平滑滚动到顶部
	function scrollTop() {
		$('html, body').animate({scrollTop: '0px'}, 800);
		//$('html, body').animate({scrollTop:$('.bottom').offset().top}, 800);
	}
</script>