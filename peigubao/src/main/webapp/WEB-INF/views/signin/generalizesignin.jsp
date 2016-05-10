<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp"%>
<%@ include file="../common/import-artDialog-js.jspf"%>
<%
	String casServerLoginUrl=ConfUtil.getContext("SSO.casServer.loginUrl");
%>
<c:set var="casServerLoginUrl" value="<%=casServerLoginUrl%>"></c:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta content="配股宝提供股票操盘、P2P贷款、投资理财、股指期货等交易服务，更高的收益，更加安全可靠；配股宝致力于打造中国领先的互联网金融交易平台." name="description">
<link rel="stylesheet" href="${ctx}/static/css/generalize_signin.css?v=${v}">
<link href="${ctx}/static/css/tzdr.css?v=${v}" rel="stylesheet" type="text/css">
<script language="javascript" src="${ctx}/static/script/tzdr.js?v=${v}"></script>
<title>免费注册 - 配股宝</title>
<style type="text/css">
	.register .main .wrong{display: none;}
</style>
<script type="text/javascript">
	var casServerLoginUrl="${casServerLoginUrl}";
	$(document).ready(function() {
		 $('.qqbutton').hover( function(){			
			$('.qq_main').show();
		 });	
		 $('.qq_main').hover(function() {		
			}, function() {
				$(this).hide();
		 });
	});
	// 平滑滚动到顶部
	function scrollTop() {
		$('html, body').animate({scrollTop: '0px'}, 800);
	}
</script>
</head>
<body>
<input type="hidden" id="backData" value="${backData}"/>
<input type="hidden" id="source" value="${source}"/>
<div class="floatlayer">
	<div class="fl_mask yzm_fl_mask" style="display: none;"></div>
    <!-- 填写验证码 -->
    <div class="fl_box fl_uc_trade yzm_box" id="yzm_box" name="yzm_box" style="display:none;">
        <div class="fl_navtitle">
            <h3>请先输入图形验证码</h3>
            <a href="javascript:void(0);"  class="close yzm_box_close"></a>
        </div>
        <div class="fl_uc_main">
            <ul class="fl_uc_list" style="padding:10px 0;">
                <li>
                    <input type="text" id="yzm" name="yzm" maxlength="5" style="width:100px;">
                    <a href="javascript:void(0);" class="refresh"><img src="${ctx}/validate.code" id="refresh_code" class="refresh_code" name="refresh_code" width="100px" height="40px"></a>
                	<a href="javascript:void(0);" class="refresh_code refresh">点击换一张</a>
                </li>
            </ul>
        </div>
        <div class="fl_uc_btn">
            <a href="javascript:void(0);" status="true" id="getSMSCode" name="getSMSCode" class="fl_uc_surebtn getSMSCode">确定</a>
        </div>
    </div>
</div>
<div class="qq">
	<div class="qq_main" style="display:none;">
		<img src="${ctx}/static/images/generalizesignin/bg_sanjiao.png" class="sanjiao">
		<h3>联系客服QQ</h3>
		<p>白天</p>
		<a target="_blank" href="http://wpa.qq.com/msgrd?v=3&uin=2167073552&site=qq&menu=yes"><img border="0" src="http://wpa.qq.com/pa?p=2:2167073552:41" alt="点击这里给我发消息" title="点 击这里给我发消息"/></a> 
		<a target="_blank" href="http://wpa.qq.com/msgrd?v=3&uin=3118550377&site=qq&menu=yes"><img border="0" src="http://wpa.qq.com/pa?p=2:3118550377:41" alt="点击这里给我发消息" title="点击这里给我发消息"/></a> 
		<a target="_blank" href="http://wpa.qq.com/msgrd?v=3&uin=3048437716&site=qq&menu=yes"><img border="0" src="http://wpa.qq.com/pa?p=2:3048437716:41" alt="点击这里给我发消息" title="点击这里给我发消息"/></a>   
		<p>晚上</p>
		<a target="_blank" href="http://wpa.qq.com/msgrd?v=3&uin=3234979643&site=qq&menu=yes"><img border="0" src="http://wpa.qq.com/pa?p=2:3234979643:41" alt="点击这里给我发消息" title="点击这里给我发消息"/></a>  
	</div>
	<div class="qqbutton"><img src="${ctx}/static/images/generalizesignin/QQ.png"></div>
</div>
<div class="tp_main">
	<div class="register">
		<form  id="form" action="" method="post">
		<div class="main">
			<h3>快速注册<span>资金秒到</span></h3>
			<p><span>手机号码</span><input type="text" id="phone" maxlength="11">
			<i class="wrong">请输入正确的手机号码</i></p>
			<p class="yzm"><span>验证码</span><input type="text" id="code" id="code" maxlength="6"><a status="true"  href="javascript:void(0);" id="openYZMBox" >获取验证码</a>
			<i class="wrong">请输入正确的验证码</i></p>
			<p><span>登录密码</span><input type="password" id="password" maxlength="16">
			<i class="wrong">密码由6-16位字母和数字组成</i></p>
			<p><span>确认密码</span><input type="password" id="check_password" maxlength="16">
			<i class="wrong">两次密码不一致</i></p>
			<p class="checkbox">
				<input id="agreement" type="checkbox" checked="checked" />
				<span>同意<a href="javascript:showAgreement();">《网站服务协议》</a></span>
			</p>
			<a status="true" id="generalizesignin" href="javascript:void(0);" class="button">立即注册</a>
		</div>
		</form>
	</div>
	<div class="tp_main1"><img src="${ctx}/static/images/generalizesignin/img_01.jpg" ></div>
	<div class="tp_main1"><img src="${ctx}/static/images/generalizesignin/img_02.jpg" ></div>
	<div class="tp_main1"><img src="${ctx}/static/images/generalizesignin/img_03.jpg" ></div>
	<div class="tp_main1"><img src="${ctx}/static/images/generalizesignin/img_04.jpg" ></div>
	<div class="tp_main1_l"><img src="${ctx}/static/images/generalizesignin/img_05.jpg" ></div>
</div>
<div class="tp_main2"><img src="${ctx}/static/images/generalizesignin/img_06.jpg" ></div>
<div class="tp_main2"><img src="${ctx}/static/images/generalizesignin/img_07.jpg" ></div>
<div class="tp_main2 tp_main3"><img src="${ctx}/static/images/generalizesignin/img_08.jpg" ></div>
<div class="tp_main2 tp_main4" style="background:#fafafa;"><img src="${ctx}/static/images/generalizesignin/img_14.jpg"></div>
<div class="tp_main2" style="background:#fafafa;"><img src="${ctx}/static/images/generalizesignin/img_09.jpg" ></div>
<div class="tp_main2_m" style="background:#fafafa;"><img src="${ctx}/static/images/generalizesignin/img_10.jpg" ></div>
<div class="tp_main2 tp_main6"><div class="button"><a href="javascript:scrollTop();"><img src="${ctx}/static/images/generalizesignin/button.jpg"></a></div></div>
<div class="tp_main2 tp_main5"><img src="${ctx}/static/images/generalizesignin/img_11.jpg" ></div>
<div class="tp_main2"><img src="${ctx}/static/images/generalizesignin/img_12.jpg" ></div>
<div class="tp_main2_l"><img src="${ctx}/static/images/generalizesignin/img_13.jpg" ></div>
<%-- cas ajax登录 --%>
<div style="display: none;">
	<form id="loginForm" action="${casServerLoginUrl}" onsubmit="return loginValidate();" method="post" target="ssoLoginFrame">
  	<input type="hidden" name="isajax" value="true" />
	<input type="hidden" name="isframe" value="true" />
	<input type="hidden" name="lt" value="" id="J_LoginTicket">
	<input type="hidden" name="execution" value="" id="J_FlowExecutionKey">
	<input type="hidden" name="_eventId" value="submit" />
	<input type="hidden" name="username" id="loginUsername">
	<input type="hidden" name="password" id="loginPassword">
	</form>
</div>
<%@ include file="../common/personfooter.jsp"%>
<%@ include file="../common/dsp.jsp"%>
<!-- <span style="display: none"><script src='http://w.cnzz.com/q_stat.php?id=1256807294&l=3' language='JavaScript'></script></span> -->
<script type="text/javascript" src="${ctx}/static/script/signin/generalizesignin.js?v=${v}"></script>
<%@ include file="/WEB-INF/views/common/count.jsp"%>
</body>
</html>