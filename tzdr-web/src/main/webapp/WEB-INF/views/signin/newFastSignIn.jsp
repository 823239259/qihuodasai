<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp"%>
<%@ include file="../common/import-artDialog-js.jspf"%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta content="投资达人提供股票操盘、P2P贷款、投资理财、股指期货等交易服务，更高的收益，更加安全可靠；投资达人致力于打造中国领先的互联网金融交易平台." name="description">
<link rel="stylesheet" href="${ctx}/static/css/new_fastsignin.css?v=${v}">
<link href="${ctx}/static/css/tzdr.css?v=${v}" rel="stylesheet" type="text/css">
<script type="text/javascript" src="${ctx}/static/script/signin/fastSignIn.js?v=${v}"></script>
<script type="text/javascript" src="${ctx}/static/script/tzdr.js?v=${v}"></script>
<title>快速注册_投资达人-中国领先的互联网金融交易平台</title>
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
                    <img src="${ctx}/validate.code" id="refresh_code" class="refresh_code" name="refresh_code" width="100px" height="34px">
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
    <div class="bannerlist">
        <img src="${ctx}/static/images/fastsignin/banner_01.jpg">
        <img src="${ctx}/static/images/fastsignin/banner_02.jpg">
        <img src="${ctx}/static/images/fastsignin/banner_03.jpg">
        <img src="${ctx}/static/images/fastsignin/banner_04.jpg">        
    </div>
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
                <button id="openYZMBox" name="openYZMBox"  type="button">发送验证码</button>
                <p id="codeErro"><i>*</i>请填写验证码</p>
            </li>
            <li class="last">
                <label></label>
                <input id="agreement" type="checkbox" checked="checked">
                <span>我同意<a href="javascript:showAgreement();">《投资达人服务协议》</a></span>
            </li>
        </ul>
        <div id="fastsignin"  status="true" class="rg_btn">免费注册</div>
        <div class="rg_link">已注册？请<a href="${ctx}/login">登录</a></div>
    </div>
    </form>
</div>
<div class="main">
    <h1><img src="${ctx}/static/images/fastsignin/title_01.gif"></h1>
    <p><img src="${ctx}/static/images/fastsignin/main_pic.jpg"></p>
</div>
<div class="main_rule">
    <h1><img src="${ctx}/static/images/fastsignin/title_02.gif"></h1>
    <ul class="rule_font">
        <li><i>1</i><em>活动时间：2015.05.11-2015.05.15，现金红包使用时间：2015.05.11-2015.05.31；</em></li>
        <li><i>2</i><em>每个新用户注册，即可获得88元利息抵扣券，并可以在个人中心查看抵扣券及说明；</em></li>
        <li><i>3</i><em>用户操盘时，选择使用抵扣券，则可以使用抵扣券金额抵扣操盘利息；</em></li>
        <li><i>4</i><em>用户实际利息金额高于利息抵扣券金额，则用户需补足差额。实际利息金额低于利息抵扣券金额，利息抵扣券一次性使用，差额不补；</em></li>
        <li><i>5</i><em>咨询电话：400-020-0158;</em></li>
        <li><i>6</i><em>本次活动最终解释权归投资达人所有。</em></li>
    </ul>
</div>
<%@ include file="../common/dsp.jsp"%>
</body> 
</html>