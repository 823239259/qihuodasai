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
<meta content="美原油,极速开户,开户零手续费,零管理费,维胜" name="description">
<link rel="stylesheet" type="text/css" href="css/index.css">
<script src="js/jquery-1.8.2.min.js"></script>
<script type="text/javascript">
$(document).ready(function() {
	$('.m1 .step a').click(function() {
		$('.m1 .main_steppic').hide().eq($(this).index()).show(500);
		$('.m1 .step a').removeClass('on');
		$(this).addClass('on');
	});

	$('.m1 .mian_step .setp_up').click(function() {
		var index = $('.m1 .step .on').index();
		if(index == 0) {
			index = 3;
		} else {
			index--;
		}
		$('.m1 .step a').removeClass('on').eq(index).addClass('on');
		$('.m1 .main_steppic').hide().eq(index).show(500);
	});


	$('.m1 .mian_step .setp_down').click(function() {
		var index = $('.m1 .step .on').index();
		if(index == 3) {
			index = 0;
		} else {
			index++;
		}
		$('.m1 .step a').removeClass('on').eq(index).addClass('on');
		$('.m1 .main_steppic').hide().eq(index).show(500);
	});
	$('.m2 .step a').click(function() {
		$('.m2 .main_steppic').hide().eq($(this).index()).show(500);
		$('.m2 .step a').removeClass('on');
		$(this).addClass('on');
	});

	$('.m2 .mian_step .setp_up').click(function() {
		var index = $('.m2 .step .on').index();
		if(index == 0) {
			index = 3;
		} else {
			index--;
		}
		$('.m2 .step a').removeClass('on').eq(index).addClass('on');
		$('.m2 .main_steppic').hide().eq(index).show(500);
	});

	$('.m2 .mian_step .setp_down').click(function() {
		var index = $('.m2 .step .on').index();
		if(index == 3) {
			index = 0;
		} else {
			index++;
		}
		$('.m2 .step a').removeClass('on').eq(index).addClass('on');
		$('.m2 .main_steppic').hide().eq(index).show(500);
	});
});
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

	//手机号码blur校验
	$("#mobile").blur(function(){
		var $this = $(this);
		var mobile =  $.trim($this.val());
		//verifyMobile($this);
	});
});
	
//手机号码格式检验
function verifyMobile(type){ 
	var obj = $("#mobile");
	var mobile =  $.trim(obj.val());
	if(!mobile.match(/^(((13[0-9])|(14[7])|(15[0-9])|(17[0-9])|(18[0-9]))+\d{8})$/)){  //判断手机号码是否正确
		obj.parent().find("em").css({display:"block"});
		obj.parent().find("p").css({display:"block"}).text("请输入正确的手机号码");
		return false;
	}
	obj.parent().find("em").css({display:"none"});
	obj.parent().find("p").css({display:"none"});
	var mobile = $("#mobile").val();
	$.post("${ctx}/ftsespread/sentEmail",{mobile:mobile,type:type},function(data){
		alert("登记成功！我们的投资顾问会尽快联系您！");
	},"json");
	return true;
}
</script>
</head>
 
<body>
<div class="top">
	<p class="top_logo"><a href="http://www.vs.com/" target="_blank"><img src="images/logo.png"></a></p>
	<p class="top_tel">客服热线：400-020-158</p>
	<div class="top_nav">
		<a href="http://www.vs.com/crudeoil/index" target="_blank">原油期货申请入口</a>
		<i></i>
		<a href="http://www.vs.com/signin" target="_blank">免费注册</a>
		<i></i>
		<a href="http://www.vs.com/cas/login" target="_blank">登录</a>
	</div>
</div>
<div class="banner">
	<div class="banner_pic"><span><img src="images/banner.jpg"></span></div>
	<div class="banner_login">
		<ul class="bannber_l_list">
			<li style="margin-bottom:15px;">
			<i></i>
 				<input name="mobile" id="mobile" type="text" focucmsg="请输入手机号"  />
                <em style="display: none;"></em>
                <p style="display: block; font-size:12px; color:#ff9c00;"></p>
			</li>
				<li><a status="true" id="signinA" name="signinA" onclick="verifyMobile(3)">申请模拟开户</a></li>
			    <li><a status="true" id="signinB" name="signinB" onclick="verifyMobile(4)">申请实盘开户</a></li>
		</ul>
		<div class="banner_l_list2">
			<a href="http://wpa.b.qq.com/cgi/wpa.php?ln=1&amp;key=XzkzODA2MTY0MV8zMDUwODdfNDAwMDIwMDE1OF8yXw" target="_blank">领取行情软件</a> 
			<a href="http://wpa.b.qq.com/cgi/wpa.php?ln=1&amp;key=XzkzODA2MTY0MV8zMDUwODdfNDAwMDIwMDE1OF8yXw" target="_blank">联系在线客服</a> 
			<a target="_blank" href="http://shang.qq.com/wpa/qunwpa?idkey=bf56780683a5e571c52ae6fdf1dca308944e0435b8503a0b4d51a83115c9c9ac">加入喊单QQ群</a>
		</div>
	</div>
</div>
<div class="money"><img src="images/money.gif"></div>
<div class="main main2">	
	<div class="main_nav"><span>零基础<em>三步教你照样赚大钱</em></span></div>
	<div class="main_step">
		<img src="images/step_01.gif">
		<img src="images/step_02.gif" class="main_step_sp">
		<img src="images/step_03.gif">
	</div>
</div>
<div class="main m1" id="open">
	<div class="main_nav">
		<span>原油期货如何开户？</span>
	</div>
	<div class="step">
		<a href="javascript:void(0);" class="on"><i>1</i>免费注册网站</a>
		<a href="javascript:void(0);" data="2"><i>2</i>账户充值</a>
		<a href="javascript:void(0);" data="3"><i>3</i>操盘申请</a>
		<a href="javascript:void(0);" data="4"><i>4</i>获取交易账号</a>
	</div>
	<div class="mian_step">
		<a href="javascript:void(0);" class="setp_up"><img src="images/page_up.gif"></a>
		<a href="javascript:void(0);" class="setp_down"><img src="images/page_down.gif"></a>
		<!-- 注册 -->
		<div class="main_steppic" style="display:block;">
			<div class="main_stpetitle"><a href="http://www.vs.com/signin" target="_blank">点击注册，进入免费注册页面</a></div>
			<img src="images/pic_01.jpg">
		</div>
		<!-- 充值 -->
		<div class="main_steppic" style="display:none;">
			<div class="main_stpetitle"><a href="http://www.vs.com/pay/payinfo" target="_blank">进入我的账户→账户充值页，选择支付渠道</a></div>
			<img src="images/pic_02.gif">
			<p>网银充值，及时到账；支付宝转账，5分钟内到账。<br>若您是原油期货新手，想试玩原油期货，最低充值2600元；原油期货高手充值没有上限</p>
		</div>
		<!-- 申请 -->
		<div class="main_steppic" style="display:none;">
			<div class="main_stpetitle"><a href="http://www.vs.com/crudeoil/index" target="_blank">进入原油期货操盘申请页面，申请方案</a></div>
			<img src="images/pic_03.gif">
			<p>原油期货新手，申请1手的方案吧；原油期货高手请随意选择；然后确认支付！</p>
		</div>
		<!-- 获取交易账号 -->
		<div class="main_steppic" style="display:none;">
			<div class="main_stpetitle"><a href="http://www.vs.com/usercrudeoil/trade_list" target="_blank">进入我的账户→原油期货，查看操盘方案里的交易账号</a></div>
			<img src="images/pic_04.gif">
			<p>交易账户开户成功后，我们会以短信的方式通知您</p>
		</div>
	</div>	

</div>
<div class="main m2" id="soft">
	<div class="main_nav">
		<span>原油期货如何交易？</span>
	</div>
	<div class="step">
		<a href="javascript:void(0);" class="on" date="1"><i>1</i>登录操作软件</a>
		<a href="javascript:void(0);" date="2"><i>2</i>选择原油合约</a>
		<a href="javascript:void(0);" date="3"><i>3</i>买入卖出</a>
		<a href="javascript:void(0);" date="4"><i>4</i>终结操盘方案</a>
	</div>
	<div class="mian_step">
		<a href="javascript:void(0);" class="setp_up"><img src="images/page_up.gif"></a>
		<a href="javascript:void(0);" class="setp_down"><img src="images/page_down.gif"></a>
		<!-- 登录 -->
		<div class="main_steppic" style="display:block;">
			<div class="main_stpetitle"><a href="http://www.vs.com/help?tab=software&leftMenu=8" target="_blank">点击这里，下载操盘软件-易盛系统</a></div>
			<img src="images/pic_05.jpg">
			<p>打开软件切换到交易服务器，使用我们为您开好的交易账号进行登录</p>
		</div>
		<!-- 选择合约 -->
		<div class="main_steppic" style="display:none;">
			<img src="images/pic_06.gif">
			<p>找到原油期货合约主力合约，双击“合约名”进入合约行情图<br>双击“买价”或“卖价”，横向下单区域会关联上该合约</p>
		</div>
		<!-- 买入卖出 -->
		<div class="main_steppic" style="display:none;">
			<img src="images/pic_07.gif">
			<p>按图操作进行下单交易，交易时间：9:05-22:55</p>
		</div>
		<!-- 终结操盘方案 -->
		<div class="main_steppic" style="display:none;">
			<div class="main_stpetitle"><a href="http://www.vs.com/usercrudeoil/trade_list" target="_blank">进入我的账户→原油期货合约，查看操盘方案里的终结方案</a></div>
			<img src="images/pic_08.gif">
			<p>当您赚钱后或行情不好时，随时可终结方案，盈亏金额转入您的账户余额！</p>
		</div>
	</div>

</div>
<div class="foot">Copyright © 2015 上海信闳投资管理有限公司 版权所有 沪ICP备14048395号-1</div>
<!-- <span style="display:none"><script src='http://w.cnzz.com/q_stat.php?id=1256807294&l=3' language='JavaScript'></script></span> -->
<span style="display:none"><script src="https://s95.cnzz.com/z_stat.php?id=1259839078&web_id=1259839078" language="JavaScript"></script></span>
<%@ include file="/WEB-INF/views/common/count.jsp"%>
</body> 
</html>