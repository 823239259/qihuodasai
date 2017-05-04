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
<meta name="description" content="维胜（www.mzkqh.com）-致力于成为中国领先的国际期货及衍生品互联网交易平台，提供恒指期货、国际原油、富时A50等主流国际期货产品，开户操盘快捷方便，交易费用全网最低。"/>
<meta name="viewport" content="width=1000">
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/newregists.css?v=${v}">
<link href="${ctx}/static/css/tzdr.css?v=${v}" rel="stylesheet" type="text/css">
<script language="javascript" src="${ctx}/static/script/signin/signinExtensions.js?v=${v}"></script>
<script language="javascript" src="${ctx}/static/script/tzdr.js?v=${v}"></script>
<title>注册 - 维胜金融-中国领先的国际期货及衍生品互联网交易平台</title>
<script type="text/javascript">
	var casServerLoginUrl="${casServerLoginUrl}";
</script>
<script>
	$(function() {
		var url = document.referrer;
		if(url.indexOf("baidu") >= 0 ){
			$(".channelRoute").html("<img src='${ctx}/static/images/login/baidu.png'>");
		}else if(url.indexOf("360") >= 0 ) {
			$(".channelRoute").html("<img src='${ctx}/static/images/login/360.png'>");
		}else if(url.indexOf("sogou") >= 0 ) {
			$(".channelRoute").html("<img src='${ctx}/static/images/login/sogou.png'>");
		}else {
			$(".channelRoute").html("<img src='${ctx}/static/images/login/ld-Android.png'>");
		}
	})
</script>
</head>
<body>
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
<div class="ks_header">
	<div class="ks_content">
		<a class="logo" href="http://www.mzkqh.com">
			<img src="${ctx}/static/images/common-new/new_logo.png" title="维胜" alt="维胜">
		</a>
		<p>
			全国客服热线<span>400</span>-<span>852</span>-<span>8008</span>
			<%-- <span class="denglu"><span>已有账号?</span><a href="${ctx}/user/account">立即登录</a></span> --%>
		</p>
	</div>
</div>
<div class="ks_zuce">
	<%-- <div class="ks_zuce_content">
		<div class="rg_ctn">
			<p class="tt"></p>  
			<input type = "hidden" value="${channelCode}" id = "channelCode"/>
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
		                <a href="javascript:void(0);" status="true" id="openYZMBox" name="openYZMBox" class="rg_l_codebtn">获取验证码</a><i style="height: 34px; width: 2px; background: #fff8bb; display: inline-block; position: absolute; right: 117px;"></i>
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
    	cas ajax登录
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
	</div> --%>
</div>
<div class="ks_center">
	<p class="ks_ld"><img src="${ctx}/static/images/login/ld-title.png"></p>
	<div class="ks_all">
		<div class="ks_fl">
			<p style="margin-top: 185px;"><span>自动化智能化交易</span><br>支持金字塔、支持Matlab，<br>量化交易策略全面智能化；</p>
			<p style="margin-top: 136px;"><span>客观化数据</span><br>全球市场突发事件实时推送、<br>全面的财经日历、<br>全球各大交易所核心交易数据；</p>
		</div>
		<div class="ks_fc"><img src="${ctx}/static/images/login/ld-ys.png"></div>
		<div class="ks_fr">
			<p style="margin-top: 185px;"><span>秒速充值</span><br>第三方支付充值——即时到帐、<br>资金秒充，不再错过任何行情；</p>
			<p style="margin-top: 136px;"><span>极速开户</span><br>全球市场突发事件实时推送、<br>五分钟内交易账户即可操盘，<br>快人一步；</p>
		</div>
	</div>
	<p class="ks_sshq" style="margin-top: 30px;"><span>实时行情</span><br>各种类交易终端行情超低延迟，<br>领跑同行；</p>
	<div class="ks_lxfs">
		<ul style="margin-left: 140px;">
			<li class="channelRoute"><img src="${ctx}/static/images/login/ld-Ios.png"></li>
			<li class="xz">APP下载</li>
		</ul>
		<%-- <ul>
			<li><img src="${ctx}/static/images/login/ld-Ios.png"></li>
			<li class="xz">iOS下载</li>
		</ul> --%>
		<ul>
			<li><img src="${ctx}/static/images/login/ld-gzh.png"></li>
			<li class="xz">关注微信公众号</li>
		</ul>
		<ul class="call">
			<li style="font-size: 24px; color: #333; padding-bottom: 10px;">客服热线</li>
			<li>400-180-1860</li>
			<li>工作日 08:30-24:00 </li>
			<li>周末 09:00-17:00</li>
			<li>官方QQ群：469441280</li>
		</ul>
	</div>
</div>
<div class="footer">Copyright  2016  成都盈透科技有限公司  版权所有  蜀ICP备16018768号-1</div>
<%@ include file="../common/dsp.jsp"%>
</body>
</html>