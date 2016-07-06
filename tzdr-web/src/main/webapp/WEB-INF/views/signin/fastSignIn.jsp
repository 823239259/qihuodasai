<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp"%>
<%@ include file="../common/import-artDialog-js.jspf"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="${ctx}/static/css/index.css?v=${v}">
<link href="${ctx}/static/css/tzdr.css?v=${v}" rel="stylesheet" type="text/css">
<script type="text/javascript" src="${ctx}/static/script/signin/fastSignIn.js?v=${v}"></script>
<script language="javascript" src="${ctx}/static/script/tzdr.js?v=${v}"></script>
<title>快速注册 - 维胜</title>
</head>
<body>
<!-- banner -->
<div class="floatlayer" style="z-index:100;">
	<div class="fl_mask yzm_fl_mask"  style="display: none;"></div>
    <!-- 填写验证码 -->
    <div class="fl_box fl_uc_trade" id="yzm_box" name="yzm_box" style="display:none;">
        <div class="fl_navtitle">
            <h3>请先输入图形验证码</h3>
            <a href="javascript:void(0);" class="close yzm_box_close" onclick="_hmt.push(['_trackEvent','cpRegist','close','code','codelayer']);" ></a>
        </div>
        <div class="fl_uc_main">
            <ul class="fl_uc_list" style="padding:10px 0;">
                <li>
                    <input type="text" id="yzm" name="yzm" maxlength="5" style="width:120px;">
                    <a href="" onclick="_hmt.push(['_trackEvent','cpRegist','click','othercodeimg','codelayer']);"><img src="${ctx}/validate.code" id="refresh_code" name="refresh_code" class="refresh_code" width="100px" height="40px"></a>
                	<a href="javascript:void(0);" class="refresh_code" onclick="_hmt.push(['_trackEvent','cpRegist','click','othercode','codelayer']);">点击换一张</a>
                </li>
            </ul>
        </div>
        <div class="fl_uc_btn">
            <a href="javascript:void(0);" status="true" id="getSMSCode" name="getSMSCode" class="fl_uc_surebtn"  onclick="_hmt.push(['_trackEvent','cpRegist','submit','code','codelayer']);">>确定</a>
        </div>
    </div>
</div>
<div class="banner">
    <img src="${ctx}/static/images/cpregist/banner.jpg" alt="飞奔吧money"> 
    <form  class="form" action="" method="post">
    <div class="regist">
        <h2>快速注册</h2>
        <ul class="rg_list">
            <li>
                <label>手机号</label>
                <input id="mobile" type="text" class="rg_l_ip" maxlength="11">
                <p id="mobileErro"><i>*</i>请填写手机号码</p>
            </li>
            <li>
                <label>密码</label>
                <input id="password" type="password" class="rg_l_ip">
                <p id="passwordErro"><i>*</i>6-16位数字和字母组成</p>
            </li>
            <li>
                <label>验证码</label>
                <input id="code" type="text" class="rg_l_ip2" maxlength="6">
                <a class="rg_l_yzmbtn" id="getCode" onclick="_hmt.push(['_trackEvent','cpRegist','click','sendcode']);">发送验证码</a>
                <p id="codeErro"><i>*</i>请填写验证码</p>
            </li>
            <li class="last">
                <label></label>
                <input id="agreement" type="checkbox" checked="checked">
                <span>我同意<a href="javascript:showAgreement();"  onclick="_hmt.push(['_trackEvent','cpRegist','click','agree']);">《投资达人服务协议》</a></span>
            </li>
        </ul>
        <div id="fastsignin"  status="true" class="rg_btn" onclick="_hmt.push(['_trackEvent','cpRegist','submit','regist']);">免费注册</div>
        <div class="rg_link">已注册？请<a href="${ctx}/login" onclick="_hmt.push(['_trackEvent','cpRegist','click','loginlink']);">登录</a></div>
    </div>
    </form>
</div>
<div class="freepei">
    <div class="fp_box">
        <img src="${ctx}/static/images/cpregist/free.gif">
    </div>
</div>
<div class="rgbox">
    <div class="rg_ctn">
        <a href="javascript:void(0);" onclick="_hmt.push(['_trackEvent','cpRegist','click','regist']);" class="showSigninBox"><em>立即注册</em></a>
    </div>
</div>
<div class="peibox">
    <div class="pei_ctn">
        <h2 class="rgb_title"><em>随心操盘</em>创新型股票融资产品</h2>
        <div class="pei_mainpic"><img src="${ctx}/static/images/cpregist/pei.gif"></div>
        <div class="pei_link">
            <img src="${ctx}/static/images/cpregist/pei_link.gif">
            <a href="javascript:void(0);" onclick="_hmt.push(['_trackEvent','cpRegist','click','regist']);" class="showSigninBox"><em>我要融资</em></a>
        </div>
    </div>
</div>
<div class="safebox">
    <div class="safe_ctn">
        <h2 class="rgb_title"><em>资金安全有保障</em></h2>
        <div class="safe_mainpic"><img src="${ctx}/static/images/cpregist/safe.gif"></div>
    </div>
</div>
<div class="rg_bom"></div>
<!-- 弹出框 -->
<div class="floatlayer" style="display:none;">
    <div class="fl_mask"></div>
    <div class="fl_rgbox">
        <a href="javascript:void(0);" onclick="_hmt.push(['_trackEvent','cpRegist','close','regist','floatlayer']);" class="fl_rgclose bazarSigninClose"></a>
        <h2>快速注册</h2>
        <form  class="formBox" action="" method="post">
	        <div class="fl_regist">
	            <ul class="rg_list">
	                <li>
	                    <label>手机号</label>
	                    <input type="text" class="rg_l_ip mobile" maxlength="11" >
	                    <p><i>*</i>请填写手机号码</p>
	                </li>
	                <li>
	                    <label>密码</label>
	                    <input type="password" class="rg_l_ip password" maxlength="16">
	                    <p><i>*</i>6-16位数字和字母组成</p>
	                </li>
	                <li>
	                    <label>验证码</label>
	                    <input  type="text" class="rg_l_ip2 code" maxlength="6">
	                    <a href="javascript:void(0);" class="openYZMBox" data_type="1" class="rg_l_yzmbtn" onclick="_hmt.push(['_trackEvent','cpRegist','click','sendcode','floatlayer']);">发送验证码</a>
	                    <p><i>*</i>请填写验证码</p>
	                </li>
	                <li class="last">
	                    <label></label>
	                    <input class="agreement" type="checkbox" checked="checked">
	                    <span>我同意<a href="javascript:showAgreement();" onclick="_hmt.push(['_trackEvent','cpRegist','click','agree','floatlayer']);">《投资达人服务协议》</a></span>
	                </li>
	            </ul>
	            <div status="true" class="rg_btn fastsignin"><a href="javascript:void(0);" onclick="_hmt.push(['_trackEvent','cpRegist','submit','regist','floatlayer']);">免费注册</a></div>
	            <div class="rg_link">已注册？请<a href="${ctx}/login" onclick="_hmt.push(['_trackEvent','cpRegist','click','loginlink','floatlayer']);">登录</a></div>
	        </div>
        </form>
    </div>
</div>
<span style="display:none"><script src='http://w.cnzz.com/q_stat.php?id=1256807294&l=3' language='JavaScript'></script></span>
<%@ include file="../common/dsp.jsp"%>
</body> 
</html>