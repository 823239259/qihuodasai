<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.tzdr.common.utils.ConfUtil"%>
<link href="${ctx }/static/css/trade.css?v=2017-2-7" rel="stylesheet" type="text/css" />
<style>
#header {
    height: 60px;
    line-height: 60px;
    background: #333333;
    position: fixed;
    z-index: 9999;
    width: 100%;
}
#customerTel {
    height: 60px;
    line-height: 60px;
    font-size: 14px;
    display: inline-block;
}
#customerTel img {
    margin-left: 30px;
    margin-top: 10px;
    float: left;
}
#customerTel ul {
    float: left;
    margin-left: 60px;
    height: 80px;
    overflow: hidden;
}
#customerTel ul li {
    float: left;
    margin: 0 20px;
}
#customerTel ul li a:hover {
	color: #ffb319;
	text-decoration: none;
    cursor: pointer;
}
#customerTel ul li a {
    display: inline-block;
    line-height: 60px;
    height: 60px;
    font-size: 14px;
    color: #fff;
    text-align: center;
}
#headerTopRight {
    float: right;
    margin-right: 30px;
    height: 60px;
}
#headerTopRight ul li a {
    font-size: 14px;
    color: #fff;
}
#headerTopRight ul li a:hover {
	color: #fc3;
	text-decoration: none;
    cursor: pointer;
}
#headerTopRight ul li {
    float: left;
}
@media screen and (max-width: 1280px) {
    #header {width: 1280px}
}
</style>
<div id="header">
    <div id="customerTel">
        <a href=""><img src="http://www.vs.com/templets/style/html/images/image/logo.png"></a>
        <ul>    
            <li><a href="${ctx}/" id="shouye" class="on">首页</a></li>
            <li><a target="_blank" href="${ctx}/html/qutrade/quoteTrade.html">行情交易</a></li>
            <li><a id="guojiqihuo" href="${ctx}/hsi/index">国际期货</a></li>
 			<li><a href="http://www.vs.com/vsnews/about/" title="" >下载中心</a></li>
 			<li><a href="http://www.vs.com/vsnews/zimeiti/" title="" >7X24直播</a></li>
 			<li><a href="http://www.vs.com/vsnews/rili/" title="" >财经日历</a></li>
            <li><a href="http://www.vs.com/vsnews/xuetang/" title="" >期货学堂</a></li>
            <li><a href="http://www.vs.com/vsnews/news/" title="" >全球资讯</a></li>
        </ul>   
    </div>  
    <div id="headerTopRight" class="">
    <div class="">
        <%
       		if(request.getSession().getAttribute("userName")!=null){
       	%>
       	<ul>
            <li class="download" style = "color:#999; font-size: 12px;"><em style="    font-size: 14px;color: #fff;">欢迎您，<a href="${ctx}/user/account" class="top_myt" style = "color:#fc3;"><%=request.getSession().getAttribute("userName").toString() %></a></em><a style="margin-left: 15px;" href="${ctx}/logout">退出</a></li>
            <%-- <li class="top_myc"><a style="margin-left: 15px;" href="${ctx}/user/account" class="on" target="_blank">我的账户</a></li> --%>
        </ul>
       	<%
       		}else{
       	%>
       	<ul>
       		<li class="sign"><a href="${ctx}/user/account">登录</a><span class="sign_span" style="color: #333;position: relative; top: 1px; margin: 0 15px; height: 12px; border-left: 1px solid #fff; display: inline-block;"></span><a href="${ctx}/signin">注册</a></li>
        </ul>
       	<%
       		}
       	%>
    </div>
</div>
</div>
<div style="height: 80px;"></div>
<!-- 浮动层 -->
<div class="floatlayer">
    <!-- 联系客服、返回顶部 -->
    <div class="fl_server">
    	<p class="fl_sv_tent"><a href="http://crm2.qq.com/page/portalpage/wpa.php?uin=4008528008&aty=0&a=0&curl=&ty=1" target='_blank'></a></p>
    	<div class="fl_sv_code">
        	<a href="javascript:void(0)" target="_blank">
        		<div class="fl_sv_codetk" style="display: none;">
        			<img src="${ctx}/static/images/common-new/ld-Android.png">
        		</div>
        	</a>
        </div>
        <p class="fl_sv_up"><a href="javascript: scrollTop();"></a></p>   
    </div>
</div>