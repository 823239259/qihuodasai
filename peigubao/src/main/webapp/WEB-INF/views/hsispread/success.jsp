<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%com.tzdr.web.utils.UserSessionBean userSessionBean=
 (com.tzdr.web.utils.UserSessionBean) request.getSession().getAttribute(com.tzdr.web.constants.Constants.TZDR_USER_SESSION);
 String username=userSessionBean.getMobile();
 %>
<%@ include file="../common/common.jsp"%>
<%@include file="../common/import-artDialog-js.jspf"%>
<html xmlns="http://www.w3.org/1999/xhtml" lang="zh">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>恒生指数期货急速交易 - 投资达人</title>
<script language="javascript" src="${ctx}/static/script/tzdr.js?v=${v}"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/hsispreadindex.css?v=${v}">

<meta content="24倍杠杆助力恒指,低门槛,高收益" name="description">

</head>
 
<body>
	<%@ include file="../common/personheader.jsp"%>

<div class="suc_main">
    <div class="suc_mainfont">恭喜您注册成功</div>
    <h2><i>您已经注册成为投资达人用户</i></h2>
    <div class="suc_user">
        <label>用户名：</label><span><%=username%></span><i>用于行情软件登录</i>
    </div>
    <div class="suc_user">
        <label>密码：</label><span>a<%=username%></span>
    </div>
    <h2><i>您已经注册成为投资达人用户</i><a href="http://update.tzdr.com/Future/download/模拟盘交易软件.zip" class="suc_link1">下载行情&模拟软件</a><a href="http://www.vs.com/pay/payinfo" class="suc_link2">充值开通业务</a></h2>
    <h2><i style="color:#0086f5;">注意事项</i></h2>
    <p>1. 请您修改密码，输入原服务密码和新服务密码，即可进行密码修改</p>
    <p>2. 如果您有任何疑问！请联系QQ：4000200158</p>
    <p>3. 如果您有任何疑问！请拨打客服热线：400-633-9257</p>
    <p>4. 想赚大钱，有大神助阵！请加入喊单QT群：40139352</p>
</div>
<span style="display:none"><script src='http://w.cnzz.com/q_stat.php?id=1256807294&l=3' language='JavaScript'></script></span>
</body> 
</html> 