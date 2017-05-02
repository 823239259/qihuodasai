<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions" %> 
<%
	String appPath = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+appPath;
	%>
<c:set var="ctx" value="<%=basePath%>"></c:set>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>404</title>
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/public.css">
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/404.css">
<script charset="utf-8" src="http://wpa.b.qq.com/cgi/wpa.php"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/common.css">
</head>
<body>
<!--顶部 -->
<div class="top">
    <div class="topctn">
        <div class="top_tel"><em>客服热线：</em><i>400-020-0158</i></div>
        <ul id="loginhead" style="display:none">
			<li id="lg_username" ></li>
			<li><a href="${ctx}/help?tab=newbie" id="quick">新手指南</a></li>
            <li><a href="${ctx}/help">帮助中心</a></li>
            <li><a href="${ctx}/help?tab=software&leftMenu=2">交易软件下载</a></li>
            <li><a href="${ctx}/logout">安全退出</a></li>
            <!-- 
            <li><a href="${ctx}/quickToDesktop" id="quick">快捷</a></li>
            
            <li class="last"><a href="javascript:void(0)" id="favorites">收藏</a></li>
        -->
        </ul>
       <ul id="tologin">
			<li><a href="${ctx }/login" class="">登录</a></li>
			<li><a href="${ctx}/signin">免费注册</a></li>
				
            <li><a href="${ctx}/help?tab=newbie">新手指南</a></li>
            <!--  
            <li><a href="${ctx}/help?tab=software&leftMenu=2">交易软件下载</a></li>
            -->
            <li><a href="${ctx}/quickToDesktop" >快捷方式</a></li>
            <!-- 
            <li class="last"><a href="javascript:void(0)" id="favorites">收藏</a></li>
       	 -->
        </ul>
    </div>
</div>
<div class="navbox">
    <div class="nav">
        <div class="navlogo"><a href="${ctx}"><img src="${ctx}/static/images/common/logo.jpg"></a></div>
        <div class="navtel">
            <p>客服热线：<i>400-020-0158</i></p>
            <span>工作日  8：30-20：30</span>
        </div>
       <ul class="navlist">
	        <li><a href="${ctx}" id="homeli" class="on">首页</a></li>
	        <li><a href="${ctx}/day?enter=0"  id="stockli" >实盘申请</a></li>
	        <!--  <%if(request.getSession().getAttribute("userName") != null){%> 
	       		 <li id="homegeneralizehref"><a id="generalizeli" href="${ctx}/generalize/details?tab=generalize">代理赚钱</a></li>
		       <%}else{ %>
		        <li id="homegeneralizehref"><a id="generalizeli" href="${ctx}/homegeneralize">代理赚钱</a></li>
		       
		       <%} %> -->
	        <li><a href="${ctx}/user/account" id="account">我的账户</a></li>
	        <li><a href="http://www.10jqka.com.cn/flash/" target="_blank">行情中心</a></li>
	    </ul>
    </div>
</div>
<!--  
<div class="notic" id="advert">
	    <p>公告：部分用户参与8800活动时选择了“下一个交易日”开始操盘，登录操盘系统时显示“账号冻结”，明天再登录操盘系统即可正常操盘。新用户请留意系统默认是“次日生效”，如要立刻操盘，须改变选项。请注意页面提示。</p>
	    <a href="javascript:void(0);"></a>
   
</div>
-->
<!-- 浮动层 -->
<div class="floatlayer">
    <div class="fl_server">
    	
        <p class="fl_sv_tent"  id="BizQQWPA" ><a href="javascript:void(0);" ></a></p>
        <p class="fl_sv_up"><a href="javascript:scroll(0,0)"></a></p>
    </div>

</div>
<!-- 404 -->
<div class="error">
    <div class="errorbox">
        <div class="errorpic"><img src="${ctx}/static/images/404/404.jpg"></div>
        <div class="errorfontbox">
            <div class="errorpromtpic"><img src="${ctx}/static/images/404/font.gif"></div>
            <div class="errorfont">
                <div class="errorbtn">
                    <a href="${ctx}/" class="errorbtn1">返回首页</a>
                    <a href="javascript:void(0);" onclick="history.back();" class="errorbtn2 back">返回上一页</a>
                </div>
                <div class="errorpromt">
                    <p>没有发现你要找的页面，经过砖家仔细研究可能原因如下:</p>
                    <ul>
                        <li>您输入地址时可能存在键入错误</li>
                        <li>页面被程序猿无情的删除了</li>
                        <li>电信网通那头接口生锈</li>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</div>
<!--底部 -->
<!--底部 -->
<!-- 公司简介 -->
<div class="copyright">
    <div class="cpctn">
         <ul class="cp_list">
            <li><a href="${ctx}/news/newsdata?colname=<%=java.net.URLEncoder.encode(java.net.URLEncoder.encode("今日股市行情","utf-8"),"utf-8")%>"  >股市行情</a></li>
            <li><a href="${ctx}/news/newsdata?colname=<%=java.net.URLEncoder.encode(java.net.URLEncoder.encode("股市热点","utf-8"),"utf-8")%>"  >股市热点</a></li>
            <li><a href="${ctx}/news/newsdata?colname=<%=java.net.URLEncoder.encode(java.net.URLEncoder.encode("操盘必读","utf-8"),"utf-8")%>"  >操盘必读</a></li>
            <li><a href="${ctx}/news/newsdata?colname=<%=java.net.URLEncoder.encode(java.net.URLEncoder.encode("达人动态","utf-8"),"utf-8")%>"  >达人动态</a></li>
            <li><a href="${ctx}/news/newsdata?colname=<%=java.net.URLEncoder.encode(java.net.URLEncoder.encode("媒体报道","utf-8"),"utf-8")%>" >媒体报道</a></li>
        </ul>
        <ul class="cp_list">
            <li><a href="${ctx}/help?tab=newbie" >新手指南</a></li>
          	<li><a href="${ctx}/news/newsdata?colname=<%=java.net.URLEncoder.encode(java.net.URLEncoder.encode("网站公告","utf-8"),"utf-8")%>" >网站公告</a></li>
            <li><a href="${ctx}/abort" >关于我们</a></li>
            <li><a href="${ctx}/contact" >联系我们</a></li>
            <li><a href="${ctx}/help" >帮助中心</a></li>
        </ul>
        <div class="cp_sina">
            <h3>新浪微博</h3>
            <img src="${ctx}/static/images/common/sinacode.jpg">
        </div>
        <div class="cp_wx">
            <h3>关注微信</h3>
            <img src="${ctx}/static/images/common/wxcode.jpg">
        </div>
        <div class="cp_hot">
            <h3>客服热线</h3>
            <p class="cp_hotphone">400-020-0158</p>
            <p class="cp_hotqq">QQ服务群196113230<br>QQ交流群201182159</p>
        </div>
    </div>
    <div class="cp_copyright">
        <div id="footerlinks">友情链接：
          <c:forEach  items="${links}" var="link" varStatus="status">
             <a href="${link.linkUrl}" target="_blank">${link.name}</a>
           </c:forEach>
        </div>
        <p>Copyright © 2016 成都盈透科技有限公司 版权所有 蜀ICP备16018768号-1 投资有风险，入市需谨慎</p>
    </div>
      <div class="cp_img" style="text-align:center;">
    	<a href="http://webscan.360.cn/index/checkwebsite/url/www.qdjinsida.com"  target="_blank" >
    	<img border="0" src="http://img.webscan.360.cn/status/pai/hash/0d18c9d17eecdc1debceaeabb359922e"/>
    	</a>
    	<a href="http://www.anquan.org/authenticate/cert/?site=www.qdjinsida.com&at=realname" target="_blank" >
    		<img src="${ctx}/static/images/common/anquan.png">
    	</a>
    	<!-- 
    	 <a  key ="54a0e6eac274e76dc1035501" logo_style="fixed"  href="http://www.anquan.org" >
	    	 <script src="http://static.anquan.org/static/outer/js/aq_auth.js">
	    	 </script>
    	 </a>
 -->
    </div>
    <script charset="utf-8" type="text/javascript" src="http://wpa.b.qq.com/cgi/wpa.php?key=XzkzODA2MTY0MV8yMTQyNjFfNDAwMDIwMDE1OF8"></script>

</div>

</body>
</html>