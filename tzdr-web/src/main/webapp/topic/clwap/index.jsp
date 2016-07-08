<%@page import="com.umpay.api.log.SysOutLogger"%>
<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions" %> 
<%@ page import="com.tzdr.common.utils.ConfUtil"%> 
<!DOCTYPE HTML>
<%
	String appPath = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+appPath;
%>
<c:set var="ctx" value="<%=basePath%>"></c:set>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"  />
    <title>美原油期货极速交易 - 维胜</title>
    <meta name="viewport" content="initial-scale=1, maximum-scale=1, user-scalable=no, minimal-ui">
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <link href="css/wapcn.css" rel="stylesheet">
        <script src="js/jquery-1.8.2.min.js"></script>
    <script type="text/javascript">
	$(document).ready(function(){
		$("#mobile").val($("#mobile").attr("focucmsg"));
		$("#mobile").focus(function(){
		if($("#mobile").val() == $("#mobile").attr("focucmsg"))
			{
				$("#mobile").val('');
				$("#mobile").val('').css("color","#6b6969");
			}
		});
		$("#mobile").blur(function(){
			if(!$("#mobile").val()){
				$("#mobile").val($("#mobile").attr("focucmsg"));
				$("#mobile").val($("#mobile").attr("focucmsg")).css("color","#979393");
			}
		});

		$('#mobile').focus(function(){
			var $this = $(this);
			if($this.parent().find("span").css("display") == "block"){
				$this.parent().find("span").css({display:"none"});
			}
		});

	
		$("#mobileA").val($("#mobileA").attr("focucmsg"));
		$("#mobileA").focus(function(){
		if($("#mobileA").val() == $("#mobileA").attr("focucmsg"))
			{
				$("#mobileA").val('');
				$("#mobileA").val('').css("color","#6b6969");
			}
		});
		$("#mobileA").blur(function(){
			if(!$("#mobileA").val()){
				$("#mobileA").val($("#mobileA").attr("focucmsg"));
				$("#mobileA").val($("#mobileA").attr("focucmsg")).css("color","#979393");
			}
		});
		
		$('#mobileA').focus(function(){
			var $this = $(this);
			if($this.parent().find("span").css("display") == "block"){
				$this.parent().find("span").css({display:"none"});
			}
		});
	});	
			
	//手机号码格式检验
	function verifyMobile(type,id){ 
		var obj = $("#"+id);
		var mobile =  $.trim(obj.val());
		if(!mobile.match(/^(((13[0-9])|(14[7])|(15[0-9])|(17[0-9])|(18[0-9]))+\d{8})$/)){  //判断手机号码是否正确
			obj.parent().find("em").css({display:"block"});
			obj.parent().find("p").css({display:"block"}).text("请输入正确的手机号码");
			return false;
		}
		obj.parent().find("em").css({display:"none"});
		obj.parent().find("p").css({display:"none"});
		$.post("${ctx}/ftsespread/sentEmail",{mobile:mobile,type:type},function(data){
			alert("登记成功！我们的投资顾问会尽快联系您！");
		},"json");
		return true;
	}
	
</script>
</head>
<body>
<header>
    <p class="hd_logo"><img src="images/wapcn/logo.gif"></p>
    <a class="hd_btn" href="tel:4000200158" target="_blank"></a>
</header>
<div class="banner"><img src="images/wapcn/c_banner.gif"></div>
<div class="content">
    <div class="cn_ip">
        <img src="images/wapcn/icon.gif" class="cn_ip_icon">
        <div >
 				<input name="mobile" id="mobile" type="text" focucmsg="请输入手机号"  />
                <em style="display: none;"></em>
                <p style="display: block; font-size:14px;margin-bottom:10px;text-align:center;color:red;"></p>
		</div>
        <a href="javascript:void(0);" onclick = "verifyMobile(3,'mobile')" class="space"><img src="images/wapcn/btn_01.gif"></a>
        <a href="javascript:void(0);" onclick = "verifyMobile(4,'mobile')"><img src="images/wapcn/btn_02.gif"></a> 
    </div>
    <div class="cn_pic"><img src="images/wapcn/cpic_01.gif"></div>
    <div class="cn_pic"><img src="images/wapcn/cpic_02.gif"></div>
    <div class="cn_pic"><img src="images/wapcn/cpic_03.gif"></div>
    <div class="cn_ip">
        <img src="images/wapcn/icon.gif" class="cn_ip_icon">
        <div >
 				<input name="mobileA" id="mobileA" type="text" focucmsg="请输入手机号"  />
                <em style="display: none;"></em>
                <p style="display: block; font-size:14px;margin-bottom:10px;text-align:center;color:red;"></p>
		</div>
        <a href="javascript:void(0);" onclick = "verifyMobile(3,'mobileA')" class="space"><img src="images/wapcn/btn_01.gif"></a>
        <a href="javascript:void(0);" onclick = "verifyMobile(4,'mobileA')"><img src="images/wapcn/btn_02.gif"></a> 
    </div>
</div>
<footer><a href="tel:4000200158" target="_blank"><img src="images/wapcn/btn_03.gif"></a></footer>
<%@ include file="/WEB-INF/views/common/count.jsp"%>
</body>
</html>