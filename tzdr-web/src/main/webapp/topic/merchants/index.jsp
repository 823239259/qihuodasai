<%@page import="com.umpay.api.log.SysOutLogger"%>
<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions" %> 
<%@ page import="com.tzdr.common.utils.ConfUtil"%> 
<%@ include file="../../WEB-INF/views/common/common.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"  />
<html xmlns="http://www.w3.org/1999/xhtml" lang="zh">
<title>维胜网斥上亿巨资,诚招战略合作伙伴加盟 - 维胜</title>
<meta content="维胜网斥上亿巨资,诚招战略合作伙伴加盟,你出人脉我除资金,年入百万倍轻松" name="description">
<link rel="stylesheet" type="text/css" href="css/index.css">
<script src="js/jquery-1.8.2.min.js"></script>
<script type="text/javascript">

$(document).ready(function(){
	$('.navlist li a').removeClass('on');
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

	$("#name").val($("#name").attr("focucmsg"));
	$("#name").focus(function(){
	if($("#name").val() == $("#name").attr("focucmsg"))
		{
			$("#name").val('');
			$("#name").val('').css("color","#6b6969");
		}
	});
	$("#name").blur(function(){
		if(!$("#name").val()){
			$("#name").val($("#name").attr("focucmsg"));
			$("#name").val($("#name").attr("focucmsg")).css("color","#979393");
		}
	});
	
	$("#name").val($("#name").attr("focucmsg"));
	$("#name").focus(function(){
	if($("#name").val() == $("#name").attr("focucmsg"))
		{
			$("#name").val('');
			$("#name").val('').css("color","#6b6969");
		}
	});
	$("#name").blur(function(){
		if(!$("#name").val()){
			$("#name").val($("#name").attr("focucmsg"));
			$("#name").val($("#name").attr("focucmsg")).css("color","#979393");
		}
	});

	$("#message").val($("#message").attr("focucmsg"));
	$("#message").focus(function(){
	if($("#message").val() == $("#message").attr("focucmsg"))
		{
			$("#message").val('');
			$("#message").val('').css("color","#6b6969");
		}
	});
	$("#message").blur(function(){
		if(!$("#message").val()){
			$("#message").val($("#message").attr("focucmsg"));
			$("#message").val($("#message").attr("focucmsg")).css("color","#979393");
		}
	});
	
	$("#message").val($("#message").attr("focucmsg"));
	$("#message").focus(function(){
	if($("#message").val() == $("#message").attr("focucmsg"))
		{
			$("#message").val('');
			$("#message").val('').css("color","#6b6969");
		}
	});
	$("#message").blur(function(){
		if(!$("#message").val()){
			$("#message").val($("#message").attr("focucmsg"));
			$("#message").val($("#message").attr("focucmsg")).css("color","#979393");
		}
	});
	
});
	
//手机号码格式检验
function submit(){ 
	
	var name = $("#name").val();
	if(name == $("#name").attr("focucmsg")){
		alert("请输入您的姓名");
		return false;
	}
	var mobile = $("#mobile").val();
	if(!mobile.match(/^(((13[0-9])|(14[7])|(15[0-9])|(17[0-9])|(18[0-9]))+\d{8})$/)){  //判断手机号码是否正确
		alert("请输入正确的手机号码");
		return false;
	}
	var message = $("#message").val();
	if(message == $("#message").attr("focucmsg")){
		alert("请输入您想对我们说的话");
		return false;
	}
	
	$.post("${ctx}/merchants/sentEmail",{mobile:mobile,name:name,message:message},function(data){
		alert("您的留言已提交！我们会尽快联系您！");
	},"json");
	return true;
}

</script>
<script>
var _czc = _czc || [];
_czc.push(["_setAccount", "1256807294"]);
</script>
</head>
 
<body>
	<%@ include file="../../WEB-INF/views/common/personheader.jsp"%>
<div class="tp_main"><img src="images/img_01.jpg" alt="维胜网斥上亿资金" ></div>
<div class="tp_main"><img src="images/img_02.jpg" alt="诚招战略合作伙伴加盟"></div>
<div class="tp_main"><img src="images/img_03.jpg" alt="你除人脉我除资金,年入百万倍轻松"></div>
<div class="tp_main"><img src="images/img_04.jpg" alt="期货投资者,有资源有人脉"></div>
<div class="tp_main tp_main2"><img src="images/img_05.jpg" alt="投资门槛低,有成熟的培训和风控机制"></div>
<div class="tp_ctn">
	<div class="tp_prd">
		<img src="images/img_06.gif">
		<a href="http://www.vs.com/hsi/index" style="left:72px;" target="_blank" onclick="_czc.push(['_trackEvent','招商','查看详细','恒指']);">查看详细</a>
		<a href="http://www.vs.com/crudeoil/index" style="left:265px;" target="_blank" onclick="_czc.push(['_trackEvent','招商','查看详细','原油']);">查看详细</a>
		<a href="http://www.vs.com/ftse/index" style="left:456px;" target="_blank" onclick="_czc.push(['_trackEvent','招商','查看详细','A50']);">查看详细</a>
		<a href="http://www.vs.com/product/gold_index" style="left:650px;" target="_blank" onclick="_czc.push(['_trackEvent','招商','查看详细','商品期货']);">查看详细</a>
		<a href="#" style="left:842px;" target="_blank" onclick="_czc.push(['_trackEvent','招商','查看详细','股指期货']);">查看详细</a>
	</div>
</div>
<div class="tp_main4"><img src="images/img_07.jpg"></div>
<div class="tp_main5"><img src="images/img_09.jpg"></div>
<div class="tp_main6" style="background:#f4f4f4;"><img src="images/img_10.gif"></div>
<div class="tp_main7"><img src="images/img_11.gif"></div>
<div class="tp_main6"><img src="images/img_12.gif"></div>
<div class="tp_main8"><img src="images/img_13.gif"></div>
<div class="tp_main9"><img src="images/img_14.gif"></div>

<div class="tp_main10">
	<div class="message">
		<div class="message1">
			<h3>您好</h3>
			<p class="p1">感谢您来到维胜，若您有加盟意向，请您为我们留言或使用以下方式联系我们，我们将尽快给您回复，并为您提供最真诚的投资服务，谢谢！</p>
			<h2>上 海 信 闳 投 资 管 理 有 限 公 司</h2>
			<p class="p21">地&nbsp;&nbsp;&nbsp;址：中国（上海）自由贸易实验区加太路29号</p>
			<p class="p22">1幢东部404-A21室</p>
			<p class="p23">联系人：商先生</p>
			<p class="p24">电&nbsp;&nbsp;&nbsp;话：13618386824</p>
			<p class="p25">Q&nbsp;&nbsp;&nbsp;&nbsp;Q：4000200158</p>
			<p class="p26">邮&nbsp;&nbsp;&nbsp;箱：shangyanxi@tzdr.com</p>
		</div>
		<div class="message2"><img src="images/map.jpg"></div>
		<div style="clear:both"></div>
		<div class="message3">
				<input type="text" class="name" id="name" focucmsg="姓名" />
				<input type="text" class="phone" id = "mobile" focucmsg="电话" />
				<div style="clear:both"></div>
				<textarea class="words"  id="message" focucmsg="留言" ></textarea>
				<input type="button" class="submit" onclick ="submit()" value="提交"/>
		</div>
	</div>
</div>
<div class="tp_main11">
	<p>Copyright © 2015 上海信闳投资管理有限公司 版权所有 泸ICP备14048395号-1</p>
</div>
<span style="display:none">
<!-- <span style="display:none"><script src='http://w.cnzz.com/q_stat.php?id=1256807294&l=3' language='JavaScript'></script></span> -->
<span style="display:none"><script src="https://s95.cnzz.com/z_stat.php?id=1259839078&web_id=1259839078" language="JavaScript"></script></span>
</span>
</body> 
</html>