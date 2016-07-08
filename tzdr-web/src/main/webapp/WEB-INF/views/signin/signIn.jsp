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
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/newregist.css?v=${v}">
<link href="${ctx}/static/css/tzdr.css?v=${v}" rel="stylesheet" type="text/css">
<script language="javascript" src="${ctx}/static/script/signin/signIn.js?v=${v}"></script>
<script language="javascript" src="${ctx}/static/script/tzdr.js?v=${v}"></script>
<title>注册 - 维胜 </title>
<script type="text/javascript">
	var casServerLoginUrl="${casServerLoginUrl}";
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
            <a href="javascript:void(0);"  class="close yzm_box_close"  onclick="_hmt.push(['_trackEvent','signin','close','code','codelayer']);" ></a>
        </div>
        <div class="fl_uc_main">
            <ul class="fl_uc_list" style="padding:10px 0;">
                <li>
                    <input type="text" id="yzm" name="yzm" maxlength="5" style="width:100px;">
                    <a href="javascript:void(0);" onclick="_hmt.push(['_trackEvent','sginin','click','othercodeimg','codelayer']);"><img src="${ctx}/validate.code" id="refresh_code" class="refresh_code" name="refresh_code" width="100px" height="40px"></a>
                	<a href="javascript:void(0);" class="refresh_code" onclick="_hmt.push(['_trackEvent','signin','click','othercode','codelayer']);">点击换一张</a>
                </li>
            </ul>
        </div>
        <div class="fl_uc_btn">
            <a href="javascript:void(0);" status="true" id="getSMSCode" name="getSMSCode" class="fl_uc_surebtn"  onclick="_hmt.push(['_trackEvent','signin','submit','code','codelayer']);">确定</a>
        </div>
    </div>
</div>

<div class="header">
	<ul>
		<li><a href="${ctx}/" id="a1"></a></li>
	</ul>
	<p id="rightP"><a href="${ctx}/">返回首页</a></p>
</div>
<div class="register">
    <div class="rg_ctn">
        <h3>用户注册<p id="rightP" style="margin-top:-15px; *margin-top:-34px;">已有账号？<a href="${ctx}/user/account">立即登录</a></p></h3>
        <form  class="form" action="" method="post">        
        <ul class="rg_list">
            <li>
                <i>*</i><label>手机号码</label>               
                <input type="text" class="phone rg_l_ip rg_l_iperror" id="mobile" name="mobile" maxlength="11">
                <span class="rg_l_promt" style="display: none">以后用该手机号码登录平台</span>               
                <p style="display: none;" class="rg_l_error">该手机号码已经存在</p>
            </li>
            <li>
                <i>*</i><label>手机验证</label>   
                <input type="text" class="rg_l_codeip rg_l_tlcode rg_l_ip" id="code" name="code" maxlength="6">
                <span class="rg_l_promt" style="display: none">请先获取验证码</span>
                <!-- <a href="javascript:void(0);" status="true" id="getCode" name="getCode" class="rg_l_codebtn">获取验证码</a> -->
                <a href="javascript:void(0);" status="true" id="openYZMBox" name="openYZMBox" class="rg_l_codebtn" onclick="_hmt.push(['_trackEvent','signin','click','sendcode']);">获取验证码</a>
                <p style="display: none;" class="rg_l_error">输入验证码有误！</p>
            </li>
            <li>
                <i>*</i><label>登录密码</label> 
                <input type="password" class="rg_l_password rg_l_ip" id="password" name="password" maxlength="16">
                <span class="rg_l_promt" style="display: none">6-16位数字和字母组成</span>
                <p style="display: none;" class="rg_l_error">密码必须由6-16位数字和字母组成</p>
            </li>
            <li>
                <i>*</i><label>确认密码</label> 
                <input type="password" class="rg_l_password rg_l_ip" id="affirmpassword" name="affirmpassword" maxlength="16">
                <span class="rg_l_promt" style="display: none">请再输入一次您设置的密码</span>
                <p style="display: none;" class="rg_l_error">两次密码不一致</p>
            </li>
            <li>
                <i></i><label>邀请码</label> 
                <c:choose>
					<c:when test="${!empty generalizeUid}">
			 			<input type="text" class="rg_l_code rg_l_ip" id="generalizeId" name="generalizeId" maxlength="6" readonly="readonly" value="${generalizeUid}" style="width:151px;" />
					</c:when>
					<c:otherwise>
						<input type="text" class="rg_l_code rg_l_ip" id="generalizeId" name="generalizeId" maxlength="6" />
					</c:otherwise>
				</c:choose>
                <span class="rg_l_promt" style="display: none;">向邀请人索取，可不填</span>
                <p style="display: none;">推广码有误！</p>
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
<div class="footer"><p>Copyright © 2016 成都盈透科技有限公司 版权所有 蜀ICP备16018768号-1</p></div>
<%@ include file="../common/dsp.jsp"%>
</body>
</html>