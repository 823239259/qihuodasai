<%@page import="com.umpay.api.log.SysOutLogger"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ include file="../common/common.jsp"%>
<%@include file="../common/import-artDialog-js.jspf"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>忘记密码 - 维胜</title>
<link rel="stylesheet" type="text/css" href="${ctx }/static/css/loginrepwd.css?v=${v}" />
 <script language="javascript" src="${ctx}/static/script/forget/forget.js?v=${v}"></script>
</head>
<body style="background:#fff;">
<div class="header" style="background:none;">
	<ul>
		<li><a href="${ctx}/" id="a1"></a></li>
	</ul>
	<p id="rightP"><a href="${ctx}/">返回首页</a></p>
</div>
<div class="resetpassword">
			<h3>密码重置<p id="rightP" style="margin-top:-15px; *margin-top:-34px;width: 150px; text-align: right;">没有账号？<a href="${ctx }/signin">立即注册</a></p></h3>
			<form id="resetForm">
			<input type="hidden" class="token"  value=""> 
				<p><em>*</em>手机号码<input type="text" id="phone" maxlength="11"/><span id="firstSpan"  style="display:none">请输入注册时填写的手机号</span></span></p>
				<p><em>*</em>手机确认<input type="text" id="checkPhone" /><a href="javascript:void(0)" id="huoqu1">获取确认码</a><span id="checkback2" style="display:none"></span></p>
				<p id="shortP"><em>*</em>新密码<input type="password" id="password" /><span id="secondSpan"  style="display:none">6-10位数字和字母组成</span></p>
				<p><em>*</em>确认密码<input type="password" id="checkPassword" /><span id="checkback4" style="display:none"></span></p>
				<a href="javascript:void(0)" id="resetpassword" class="resetbtn">确认重置</a>
				
			</form>
			
</div>
<div class="footer"><p>Copyright © 2016 成都盈透科技有限公司 版权所有 蜀ICP备16018768号-1</p></div>
</body>
</html>
