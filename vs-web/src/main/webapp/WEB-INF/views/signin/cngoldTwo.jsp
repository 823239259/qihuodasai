<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp"%>
<%@ include file="../common/import-artDialog-js.jspf"%>
<%
	String casServerLoginUrl=ConfUtil.getContext("SSO.casServer.loginUrl");
%>
<c:set var="casServerLoginUrl" value="<%=casServerLoginUrl%>"></c:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="zh">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="keywords" content="维胜，国际期货，期货，投资达人，金勺子，高盛，都城，南华期货，配资，期货配资，期货开户，外盘，富时A50，国际原油，恒指期货，期货公司，期货平台，炒期货，模拟盘，赚钱，头寸，持仓，成都盈透科技有限公司"/>
<meta name="description" content="维胜（www.vs.com）-致力于成为中国领先的国际期货及衍生品互联网交易平台，提供恒指期货、国际原油、富时A50等主流国际期货产品，开户操盘快捷方便，交易费用全网最低。"/>
<meta name="viewport" content="width=1280">
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/registrationBar.css?v=${v}">
<link href="${ctx}/static/css/tzdr.css?v=${v}" rel="stylesheet" type="text/css">
<script language="javascript" src="${ctx}/static/script/signin/signinExtensions.js?v=${v}"></script>
<script language="javascript" src="${ctx}/static/script/tzdr.js?v=${v}"></script>
<title>注册 - 维胜金融-中国领先的国际期货及衍生品互联网交易平台</title>
<script type="text/javascript">
	var casServerLoginUrl="${casServerLoginUrl}";
</script>
<style>
	.rg_l_error { display: block;width: 220px;height: 20px;line-height: 20px;background: none; font-size: 12px;color: #fff;border: none;line-height: 20px;padding: 0 ; position: relative;left: 0px;top: 0;text-align: left;margin-left: 30px;}
	.rg_l_promt {display: block;width: 220px;height: 20px;line-height: 20px;background: none; font-size: 12px;color: #fff;border: none;line-height: 20px;padding: 0 ;position: relative;left: 0px;top: 0;text-align: left;margin-left: 30px;}
	.rg_agree {margin-left: 23px; font-size: 12px; color: #fff8bb; height: 16px; line-height: 16px;}
	.rg_agree input {position: relative; top: 3px;}
	.rg_btn a {height: 34px; line-height: 34px; margin-left: 20px; margin-top: 20px; width: 220px;}
</style>
</head>
<body>
<div class="jtw">
<input type="hidden" id="backData" value="${backData}"/>
<input type="hidden" id="source" value="${source}"/>

<div class="floatlayer">
	<div class="fl_mask" style="display: none;"></div>
    <!-- 填写验证码 -->
    <div class="fl_box fl_uc_trade" id="yzm_box" name="yzm_box" style="display:none;">
        <div class="fl_navtitle">
            <h3>请先输入图形验证码</h3>
            <a href="javascript:void(0);"  class="close yzm_box_close"></a>
        </div>
        <div class="fl_uc_main">
            <ul class="fl_uc_list" style="padding:10px 0;">
                <li>
                    <input type="text" id="yzm" name="yzm" maxlength="5" style="width:100px;">
                    <a href="javascript:void(0);" onclick="_hmt.push(['_trackEvent','sginin','click','othercodeimg','codelayer']);"><img src="${ctx}/validate.code" id="refresh_code" class="refresh_code" name="refresh_code" width="100px" height="40px"></a>
                	<a href="javascript:void(0);" class="refresh_code">点击换一张</a>
                </li>
            </ul>
        </div>
        <div class="fl_uc_btn">
            <a href="javascript:void(0);" status="true" id="getSMSCode" name="getSMSCode" class="fl_uc_surebtn">确定</a>
        </div>
    </div>
</div>
<div class="ks_zuce">
	<div class="ks_zuce_content">
		<div class="rg_ctn">
			<p class="tt"></p>  
			<input type = "hidden" value="${channelCode }" id = "channelCode"/>
        	<form  class="form" action="" method="post">  
		        <ul class="rg_list">
		            <li>     
		                <input type="text" id="mobile" name="mobile" maxlength="11" placeholder="请输入您的手机号码">
		                <span class="rg_l_promt" style="display: none">以后用该手机号码登录平台！</span>               
		                <p style="display: none;" class="rg_l_error">该手机号码已经存在</p>
		                <i class="mobile-iphone"></i>
		            </li>
		            <li style="position: relative;">   
		                <input type="text" id="code" name="code" maxlength="6" placeholder="请输入手机验证码">
		                 <a href="javascript:void(0);" status="true" id="openYZMBox" name="openYZMBox" class="rg_l_codebtn">获取验证码</a><i style="height: 34px; width: 2px; background: #fff8bb; display: inline-block; position: absolute; right: 109px;"></i>
		                <span class="rg_l_promt" style="display: none">请先获取验证码！</span>
		                <p style="display: none;" class="rg_l_error">输入验证码有误！</p>
		            </li>
		            <li>
		                <input type="password" id="password" name="password" maxlength="16" placeholder="请设置6-16位数的字母与数字密码">
		                <span class="rg_l_promt" style="display: none">6-16位数字和字母组成</span>
		                <p style="display: none;" class="rg_l_error">密码必须由6-16位数字和字母组成</p>
		            </li>
		            <li>
		                <input type="password" id="affirmpassword" name="affirmpassword" maxlength="16"  placeholder="请确认您的密码">
		                <span class="rg_l_promt" style="display: none">请再输入一次您设置的密码</span>
		                <p style="display: none;" class="rg_l_error">两次密码不一致！</p>
		            </li>
		        </ul>
        		<div class="rg_agree"><input type="checkbox" checked="checked" id="agreement" name="agreement">我已阅读并同意<a href="javascript:showAgreement();">《维胜网站服务协议》</a></div>
        		<div class="rg_btn"><a status="true" id="signin" name="signin" href="javascript:void(0);">立即注册</a></div>
        	</form>
    	</div>
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
	</div>
</div>
<div class="tjw_activityTime">活动时间：2017年1月11日—2017年2月10日</div>	
<div style="width: 1280px; margin: 0 auto; height: 100px;"><img src="${ctx}/static/images/image/ws_jt_6.png"></div>
<div class="jtw_activityContent">
	<div class="jtw_img jtw_img_1"></div>
	<div class="jtw_center">
		<img src="${ctx}/static/images/image/ws_jt_7.png">
		<img src="${ctx}/static/images/image/ws_jt_8.png">
	</div>
</div>
<div class="jtw_activityContent">
	<div class="jtw_img jtw_img_2"></div>
	<div class="jtw_center">
		<img src="${ctx}/static/images/image/ws_jt_13.png" style="margin-top: 62px;">
		<div class="jtw_th">
			<ul style="margin-left: 0px;">
				<li>富时A50</li>
				<li>最低<span>350元</span>可玩</li>
				<li>手续费<span>39元</span>/双边</li>
			</ul>
			<ul>
				<li style="margin-top: 25px;">新手第一手双边</li>
				<li>手续费<span>全免</span></li>
			</ul>
			<ul style="margin-right: 0px;">
				<li style="margin-top: 25px;">原油超值活动</li>
				<li>手续费<span>89元</span>/双</li>
			</ul>
		</div>
	</div>
</div>
<div class="jtw_activityContent">
	<div class="jtw_img jtw_img_3"></div>
	<div class="jtw_center">
		<div class="jtw_xq">
			<ul>
				<li><a href="http://www.vs.com/" target="_blank"><img src="${ctx}/static/images/image/ws_jt_14.png"></a></li>
				<li>www.vs.com</li>
			</ul>
		</div>
	</div>
</div>
<p style="font-size: 12px; color: #ffd400; height: 60px; line-height: 60px; text-align: center; background: #771a1a;">以上所有活动最终解释权归维胜网所有，客服咨询：400-852-8008</p>
</div>
<%@ include file="../common/dsp.jsp"%>
</body>
</html>