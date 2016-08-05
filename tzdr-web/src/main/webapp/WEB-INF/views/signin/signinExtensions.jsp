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
<meta content="维胜提供股票操盘、P2P贷款、投资理财、股指期货等交易服务，更高的收益，更加安全可靠；维胜致力于打造中国领先的互联网金融交易平台." name="description">
<meta name="viewport" content="width=1000">
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/newregist.css?v=${v}">
<link href="${ctx}/static/css/tzdr.css?v=${v}" rel="stylesheet" type="text/css">
<script language="javascript" src="${ctx}/static/script/signin/signinExtensions.js?v=${v}"></script>
<script language="javascript" src="${ctx}/static/script/tzdr.js?v=${v}"></script>
<title>注册 - 维胜 </title>
<script type="text/javascript">
	var casServerLoginUrl="${casServerLoginUrl}";
</script>
<script type="text/javascript">
	function scrollTop() {
		$('html, body').animate({scrollTop: '0px'}, 800);
		//$('html, body').animate({scrollTop:$('.bottom').offset().top}, 800);
	}
</script>
<style>
	.rg_l_error { display: block;width: 220px;height: 20px;line-height: 20px;background: none; font-size: 12px;color: #fff;border: none;line-height: 20px;padding: 0 ; position: relative;left: 0px;top: 0;text-align: left;margin-left: 20px;}
	.rg_l_promt {display: block;width: 220px;height: 20px;line-height: 20px;background: none; font-size: 12px;color: #fff;border: none;line-height: 20px;padding: 0 ;position: relative;left: 0px;top: 0;text-align: left;margin-left: 20px;}
	.rg_agree {margin-left: 23px; font-size: 12px; color: #fff8bb; height: 16px; line-height: 16px;}
	.rg_agree input {position: relative; top: 3px;}
	.rg_btn a {height: 34px; line-height: 34px; margin-left: 20px; margin-top: 20px; width: 220px;}
</style>
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
		<a class="logo" href="http://www.vs.com">
			<img src="${ctx}/static/images/common-new/new_logo.png" title="维胜" alt="维胜">
		</a>
		<p>
			全国客服热线<span>400</span>-<span>852</span>-<span>8008</span>
			<span class="denglu"><span>已有账号?</span><a href="${ctx}/user/account">立即登录</a></span>
		</p>
	</div>
</div>
<div class="ks_zuce">
	<div class="ks_zuce_content">
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
		                <input type="text" id="code" name="code" maxlength="6" placeholder="请输入您的手机验证码">
		                 <a href="javascript:void(0);" status="true" id="openYZMBox" name="openYZMBox" class="rg_l_codebtn">获取验证码</a>
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
<div class="ks_why">
	<div class="ks_why_content">
		<p class="ks_why_js_1" style="">免损秘籍-如何获得3000元损失补贴！</p>
		<div class="ks_why_bt">
			<div class="ks_why_btleft">
				<h2>秘籍一</h2>
				<p>首次出现亏损，即可获得1次免损机会，最高1000元！<p>
			</div>
			<div class="ks_why_btright">
				<h2>秘籍二</h2>
				<p>单日操盘达到一定手数，累计亏损即可获得对应亏损补贴，最高2000元！<p>
			</div>
		</div>
		<p class="ks_why_js_1">为什么选择国际期货？-四大优势秒杀其他投资方式！</p>
		<div class="ks_why_ys">
			<div style="margin-left:0;">
				<h2>实时</h2>
				<p style="margin-top: 17px;">客观化实时行情<p>
				<p>维胜网为用户提供客观化实时</p>
				<p>交易行情，即时直观</p>
			</div>
			<div>
				<h2>客观</h2>
				<p style="margin-top: 17px;">全球交易市场，行情不受操控<p>
				<p>全球最大交易所交易-</p>
				<p>CME、LME、HKEX、SGX</p>
			</div>
			<div>
				<h2>安全</h2>
				<p style="margin-top: 17px;">资金三方监管<p>
				<p>资金-银行监管，交易-券商监管，</p>
				<p>风控-维胜监管</p>
			</div>
			<div style="margin-right:0;">
				<h2>低价</h2>
				<p style="margin-top: 17px;">交易手续费极低<p>
				<p>充值、提现、开户、账户管理均零</p>
				<p>收费，维胜网双边交易低至49元/手</p>
			</div>
		</div>
		<p class="ks_why_js_1">为什么选择VS？-五大特点助您引爆财富！</p>
		<div class="ks_why_td">
			<div style="margin-left:0;">
				<h2>专业</h2>
				<p style="margin-top: 17px;">团队成员来自金融、<p>
				<p>互联网等领域</p>
				<p>团队成员具备相关领域</p>
				<p>多年从业经验</p>
			</div>
			<div>
				<h2>实力</h2>
				<p style="margin-top: 17px;">国际专业投资机构与国内<p>
				<p>领先互联网运营商共同</p>
				<p>出资设立</p>
				<p>前身系知名金融交易平台</p>
			</div>
			<div>
				<h2>创新</h2>
				<p style="margin-top: 17px;">多终端交易系统<p>
				<p>打破信息不对称，</p>
				<p>信息实时透明</p>
				<p>杠杆机制，以小博大</p>
			</div>
			<div>
				<h2>全面</h2>
				<p style="margin-top: 17px;">交易+数据+行情，<p>
				<p>助力操盘</p>
				<p>注册-交易-充值提现，</p>
				<p>一站式服务</p>
			</div>
			<div style="margin-right:0;">
				<h2>精细</h2>
				<p style="margin-top: 42px;">客观化数据即时掌握<p>
				<p>行情资讯24小时推送</p>
			</div>
		</div>
		<p class="ks_zp"><a href="javascript:scrollTop();">点我立即注册</a><p>
	</div>
</div>
<div class="ks_footer">
	<p>
		<span>Copyright © 2016 成都盈透科技有限公司 版权所有 蜀ICP备16018768号-1</span>
		<img src="${ctx}/static/images/image/chengxing.png" style="margin-right: 0;">
    	<img src="${ctx}/static/images/image/anquan.png">
    	<img src="${ctx}/static/images/image/shiming.png">
    </p>
</div>
<%@ include file="../common/dsp.jsp"%>
</body>
</html>