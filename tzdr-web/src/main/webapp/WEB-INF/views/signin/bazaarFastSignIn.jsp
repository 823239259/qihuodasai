<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp"%>
<%@ include file="../common/import-artDialog-js.jspf"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta content="维胜提供股票操盘、P2P贷款、投资理财、股指期货等交易服务，更高的收益，更加安全可靠；维胜致力于打造中国领先的互联网金融交易平台." name="description">
<link rel="stylesheet" href="${ctx}/static/css/index.css?v=${v}">
<link href="${ctx}/static/css/tzdr.css?v=${v}" rel="stylesheet" type="text/css">
<script type="text/javascript" src="${ctx}/static/script/signin/bazaarFastSignIn.js?v=${v}"></script>
<script type="text/javascript" src="${ctx}/static/script/tzdr.js?v=${v}"></script>
<title>快速注册_维胜-中国领先的互联网金融交易平台</title>
</head>
<body>
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
                    <input type="text" id="yzm" name="yzm" maxlength="5" style="width:120px;">
                    <img src="${ctx}/validate.code" id="refresh_code" name="refresh_code" class="refresh_code" width="100px" height="34px">
                    <a href="javascript:void(0);" class="refresh_code">点击换一张</a>
                </li>
            </ul>
        </div>
        <div class="fl_uc_btn">
            <a href="javascript:void(0);" status="true" id="getSMSCode" name="getSMSCode" class="fl_uc_surebtn">确定</a>
        </div>
    </div>
</div>
<!-- banner -->
<div class="banner">
    <img src="${ctx}/static/images/banner.jpg" alt="达人红包8800大派送"> 
    <form  class="form" action="" method="post">
	    <div class="regist">
	        <h2>快速注册</h2>
	        <ul class="rg_list">
	            <li>
	                <label>手机号</label>
	                <input type="text" class="rg_l_ip" id="mobile" name="mobile" maxlength="11">
	                <p><i>*</i>请填写手机号码</p>
	            </li>
	            <li>
	                <label>密码</label>
	                <input type="password" class="rg_l_ip" id="password" name="password" maxlength="16">
	                <p><i>*</i>6-16位数字和字母组成</p>
	            </li>
	            <li>
	                <label>验证码</label>
	                <input type="text" class="rg_l_ip2 code" id="code" name="code" maxlength="6">
	               	<button id="openYZMBox" name="openYZMBox"  type="button">发送验证码</button>
	                <p><i>*</i>请填写验证码</p>
	            </li>
	            <li class="last">
	                <label></label>
	                <input class="agreement" type="checkbox" id="agreement" name="agreement" checked="checked">
	                <span>我同意<a href="javascript:showAgreement();">《维胜服务协议》</a></span>
	            </li>
	        </ul>
	        <div status="true" class="rg_btn" id="fastsignin" name="fastsignin">免费注册</div>
	        <div class="rg_link">已注册？请<a href="${ctx}/login">登录</a></div>
	    </div>
    </form>
</div>
<div class="main"><img src="${ctx}/static/images/img_01.gif?version=2015071501"></div>
<div class="main"><img src="${ctx}/static/images/img_02.gif?version=2015071501"></div>
<div class="main"><img src="${ctx}/static/images/img_03.gif?version=2015071501"></div>
<div class="main"><img src="${ctx}/static/images/img_04.gif?version=2015071501"></div>
<div class="main"><img src="${ctx}/static/images/img_05.gif?version=2015071501"></div>
<div class="main"><img src="${ctx}/static/images/img_06.gif?version=2015071501"></div>
<div class="main main2"><img src="${ctx}/static/images/img_07.gif?version=2015071501"></div>
</body> 
</html>