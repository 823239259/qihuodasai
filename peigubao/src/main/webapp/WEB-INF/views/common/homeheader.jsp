<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.tzdr.common.utils.ConfUtil"%>
<link rel="stylesheet" href="${ctx}/static/css/common.css?v=${v}">
<!-- notice -->
<%@include file="../common/notice.jsp"%>
<div class="header">
	<div class="top">
    	<div class="salogan"><a href="${ctx}/topic/overseas/" target="_blank" style="color:#808080;">海外用户入口</a></div>
        <p>
        	<%
       			if(request.getSession().getAttribute("userName")!=null){
       		%>
       		<em>你好，<a href="${ctx}/user/account"><%=request.getSession().getAttribute("userName").toString() %></a></em><a href="${ctx}/logout">退出</a>|<a href="${ctx}/user/account" style="color: #f60;">我的账户</a>|<a href="${ctx}/help?status=3" target="_blank">交易软件下载</a>
        	<%
	       		}else{
	       	%>
	       	<a href="${ctx}/user/account">登录</a>|<a href="${ctx}/signin">免费注册</a>|<a href="${ctx}/help?status=3" target="_blank">交易软件下载</a>
	       	<%
	       		}
	       	%>
        	<span>客服热线：</span><font>400-633-9257</font>
        </p>
    </div>
    <div class="main">
    	<div class="logo"><a href="${ctx}/"></a></div>
    	<p style="float: left;margin-top: 40px;color: #f00; height: 20px;line-height: 20px; font-size:16px;">股市有风险，投资须谨慎！</p>
        <div class="nav" id="navlist">
        	<a id="nav_my" href="${ctx}/user/account">我的账户</a> 
        	<a id="hkstockli" href="${ctx}/hkstock/index">港股操盘</a>           	
			<a id="monthli" href="${ctx}/monthTrade/index">月月操盘</a>
			<a id="stockli" href="${ctx}/day?enter=0">随心操盘</a>
            <a href="${ctx}/" class="cur">首页</a>
        </div>
    </div>
</div>

<!-- 浮动层 -->
<div class="floatlayer">
    <!-- 联系客服、返回顶部 -->
    <div class="fl_server">
        <div class="fl_sv_tent">
        	<a href="javascript:void(0);" target='_blank' class="fl_sv_tentbg"></a>
        	<div class="fl_sv_tenttk" style="display:none;">
        		<h3>联系客服QQ</h3>
        		<div class="fl_sv_tentlist">
        			<h4>白天8:30-18:00</h4>
        			<p>
        				<a target="_blank" href="http://wpa.qq.com/msgrd?v=3&uin=2167073552&site=qq&menu=yes"><img border="0" src="http://wpa.qq.com/pa?p=2:251802955:52" alt="点击这里给我发消息" title="点击这里给我发消息"/></a>
        				<a target="_blank" href="http://wpa.qq.com/msgrd?v=3&uin=3118550377&site=qq&menu=yes"><img border="0" src="http://wpa.qq.com/pa?p=2:251802955:52" alt="点击这里给我发消息" title="点击这里给我发消息"/></a>
        				<a target="_blank" href="http://wpa.qq.com/msgrd?v=3&uin=3048437716&site=qq&menu=yes"><img border="0" src="http://wpa.qq.com/pa?p=2:251802955:52" alt="点击这里给我发消息" title="点击这里给我发消息"/></a>
        			</p>
        		</div>        		
        		<div class="fl_sv_tentlist">
        			<h4>晚上18:00-24:00</h4>
        			<h4>周末全天在线</h4>
        			<p><a target="_blank" href="http://wpa.qq.com/msgrd?v=3&uin=3234979643&site=qq&menu=yes"><img border="0" src="http://wpa.qq.com/pa?p=2:251802955:52" alt="点击这里给我发消息" title="点击这里给我发消息"/></a></p>
        		</div>
        	</div>	
        </div>
        <div class="fl_sv_code">
            <a href="javascript: void(0);"></a> 
            <div class="fl_sv_codetk" style="display: none;"><img src="${ctx}/static/images/common-new/code.png"><p>扫二维码访问<br>配股宝手机站</p></div>
        </div>
        <p class="fl_sv_up"><a href="javascript: scrollTop();"></a></p>
    </div>
</div>

<script type="text/javascript">
	$(document).ready(function() {
		 $('.fl_sv_code a').hover(function() {
		        $('.fl_sv_codetk ').show();
		    }, function() {
		        $('.fl_sv_codetk ').hide();
		    }); 
			
		 $('.fl_sv_tentbg').hover( function(){			
				$('.fl_sv_tenttk').show();
		 });	
		 $('.fl_sv_tenttk').hover(function() {		
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