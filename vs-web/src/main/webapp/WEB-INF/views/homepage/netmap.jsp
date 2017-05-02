<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp"%>
<%@include file="../common/import-fileupload-js.jspf"%>
<%@include file="../common/import-artDialog-js.jspf"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>网站地图 - 维胜</title>
<link rel="stylesheet" href="${ctx}/static/css/login.css">
<link rel="stylesheet" href="${ctx}/static/css/news.css">
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/tzdr.css">
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/home.css">
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/map.css">
<script type='text/javascript' src="${ctx}/static/script/tzdr.js"></script>
<script type="text/javascript">
$(document).ready(function() {
    $('.news_cpimglist li a').each(function() {
        $(this).click(function() {
            $('.fl_conimg').hide();
            $('.fl_mask').show();
            var value = $(this).attr('data');
            $('.fl_box'+value).show();
        });
    });
    $('.fl_conimg .fl_navtitle a').each(function() {
        $(this).click(function() {
            $('.fl_conimg').hide();
            $('.fl_mask').hide();
        });
    });

});
</script>
</head>
<body>
<!-- 顶部 -->
<!-- DINGBU -->
<div class="map_link">
    <a href="http://www.qdjinsida.com/" target="_blank">首页</a>
    <i>></i>
    <i>网站地图</i>
</div>
<div class="map">
    <dl class="map_one">
        <dt>成为维胜会员</dt>
        <dd><a href="${ctx}/signin" target="_blank">注册</a></dd>
        <dd><a href="${ctx}/login" target="_blank">登录</a></dd>
    </dl>
    <dl class="map_one">
        <dt>我要操盘</dt>
        <dd><a href="${ctx}/day?enter=0" target="_blank">股票操盘</a></dd>
    </dl>
    <dl class="map_one">
        <dt>我要代理</dt>
        <dd><a href="${ctx}/homegeneralize" target="_blank">代理赚钱</a></dd>
    </dl>
    <dl class="map_two">
        <dt>账号管理</dt>
        <dd><a href="${ctx}/user/account" target="_blank">账户信息</a></dd>
        <dd><a href="${ctx}/userinfo/info" target="_blank">个人信息</a></dd>
        <dd><a href="${ctx}/securityInfo/secInfo" target="_blank">安全信息</a></dd>
        <dd><a href="${ctx}/fund/fundDetail" target="_blank">资金明细</a></dd>
        <dd><a href="${ctx}/message/usermessage" target="_blank">在线留言</a></dd>
        <dd><a href="${ctx}/trade/list" target="_blank">方案列表</a></dd>
    </dl>
    <dl class="map_two">
        <dt>充值提现</dt>
        <dd><a href="${ctx}/pay/payinfo?tab=0" target="_blank">网银充值</a></dd>
        <dd><a href="${ctx}/pay/payinfo?tab=1" target="_blank">支付宝转账</a></dd>
        <dd><a href="${ctx}/pay/payinfo?tab=2" target="_blank">银行转账</a></dd>
        <dd><a href="${ctx}/pay/payinfo?tab=3" target="_blank">充值记录</a></dd>
        <dd><a href="${ctx}/draw/drawmoney?tab=1" target="_blank">银行卡管理</a></dd>
        <dd><a href="${ctx}/draw/drawmoney?tab=0" target="_blank">我要提现</a></dd>
        <dd><a href="${ctx}/draw/drawmoney?tab=2" target="_blank">提现记录</a></dd>
    </dl>
    <dl class="map_two">
        <dt>达人帮助</dt>
        <dd><a href="${ctx}/forgetpw" target="_blank">忘记密码</a></dd>
        <dd><a href="${ctx}/websiteAgreement" target="_blank">服务协议</a></dd>
        <dd><a href="${ctx}/tradeContract" target="_blank">操盘协议</a></dd>
        <dd><a href="${ctx}/help?tab=software" target="_blank">软件下载</a></dd>
        <dd><a href="http://www.10jqka.com.cn/flash/" target="_blank">行情中心</a></dd>
    </dl>
    <dl class="map_two last">
        <dt>新闻中心</dt>
        <dd><a href="${ctx}/news/newsdata" target="_blank">新闻中心</a></dd>
        <dd><a href="${ctx}/news/newsdata?colname=8a2868ab4be94f15014be9a11ba303de" target="_blank">操盘必读</a></dd>
        <dd><a href="${ctx}/news/newsdata?colname=8a2868ab4be94f15014be9a17d1303df" target="_blank">股市热点</a></dd>
        <dd><a href="${ctx}/news/newsdata?colname=8a2868ab4be94f15014be9a25cff03e0" target="_blank">达人动态</a></dd>
        <dd><a href="${ctx}/news/newsdata?colname=8a2868ab4be94f15014be9a2f24103e1" target="_blank">媒体报道</a></dd>
        <dd><a href="${ctx}/news/newsdata?colname=8a2868ab4beea111014c07c0fabd0406" target="_blank">股市行情</a></dd>
        <dd><a href="${ctx}/news/newsdata?colname=8a2868ab4beea111014c07c3554d0407" target="_blank">网站公告</a></dd>
    </dl>
    <dl class="map_two last">
        <dt>新手指南</dt>
        <dd><a href="${ctx}/help?tab=newbie" target="_blank">新手指南</a></dd>
        <dd><a href="${ctx}/help?tab=software" target="_blank">交易软件下载</a></dd>
        <dd><a href="${ctx}/help?tab=safety" target="_blank">安全保障</a></dd>
        <dd><a href="${ctx}/help?tab=rule" target="_blank">操盘规则</a></dd>
        <dd><a href="${ctx}/help?tab=configuration" target="_blank">实盘申请介绍</a></dd>
    </dl>
    <dl class="map_two last">
        <dd><a href="${ctx}/abort" target="_blank">公司简介</a></dd>
        <dd><a href="${ctx}/contact" target="_blank">联系我们</a></dd>
    </dl>
</div>


<!-- DINGBU -->
<%@ include file="../common/dsp.jsp"%>
</body>


</html>


