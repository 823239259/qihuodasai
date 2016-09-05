<%@page
	import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="org.springframework.context.ApplicationContext"%>
<%@page import="com.tzdr.domain.entity.DataMap"%>
<%@page import="com.tzdr.business.service.datamap.DataMapService"%>
<%@page import="com.umpay.api.log.SysOutLogger"%>
<%@ page language="java" import="java.util.*" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions" %> 
<%@ page import="com.tzdr.common.utils.ConfUtil"%> 

<%@ include file="../../WEB-INF/views/common/common.jsp"%>
<html>
<%
int num =0;
ApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(getServletContext());  
DataMapService data = context.getBean(DataMapService.class);
if (data != null) {
	List<DataMap> datas =  data.findByTypeKey("crudeActive");
	DataMap mapObj = datas.get(0);
	 num= Integer.parseInt(mapObj.getValueKey())*10;
} 
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"  />
<title>维胜百万现金大派送 - 维胜</title>
<meta content="维胜百万现金大派送,国际原油实盘资金免费领" name="description">
<link rel="stylesheet" type="text/css" href="css/index.css">
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/common.css">


<script type="text/javascript">

function apply(){ 
	if(!isLoginSSO){
		window.location.href="${ctx}/topic/crudeFreetrial.sso"; 
	}else{ 
		var surplus =<%=num%>; 
		if (surplus==0){
			alert("今日活动名额已满，请明天再来");
			return false;
		}
		var currentTime = new Date();
		var currentTimeSec =  currentTime.getHours()*60*60+currentTime.getMinutes()*60+currentTime.getSeconds();
		var startSec = 17*60*60;
		var endSec = 17*60*60+30*60;
		if (currentTimeSec>=startSec && currentTimeSec <=endSec){
			var num = Math.floor(Math.random()*10+1);
			if (num<=8){
				alert("申请活动的人数太多，请稍后重试 ！");
				return false;
			}
		}
		 
		$.post("${ctx}/crudeActive/apply",{},function(data){
			if(data.success){
				if(data.message!="" && data.message!=null){
					if(data.message == "over"){
						alert("今日活动名额已满，请明天再来");
						return;
					}else if(data.message == "again"){
						alert("您已拥有名额，请勿重复申请");
						window.location.href="${ctx}/crudeActive/index";
					}else if(data.message == "old"){
						alert("您已申请过国际原油，不能参加本次活动");
						return;
					}else if(data.message == "wrong"){
						alert("新手体验活动是2015.11.20-2015.11.25(除周末)的17点-24点，敬请期待");
						return;
					}else if(data.message == "ftse"){
						alert("非常抱歉，您已经体验过富时A50活动");
						return;
					}else if(data.message == "success") {
						window.location.href="${ctx}/crudeActive/index";
					}
				}
			}	
		},"json");
	} 
	
	return true;
}
</script>

</head>
 
<body>
<!--顶部 -->
	<%@ include file="../../WEB-INF/views/common/personheader.jsp"%>
<div class="tp_main"><img src="images/img_01.jpg" alt="维胜百万现金大派送" ></div>
<div class="tp_main"><img src="images/img_02.jpg" alt="41800元国际原油实盘资金免费领"></div>
<div class="tp_main"><img src="images/img_03.jpg" alt="盈利可提现,亏损算我们"></div>
<div class="tp_main"><img src="images/img_04.jpg" alt="仅此一次,机不可失"></div>
<div class="tp_main"><img src="images/img_05.jpg" alt="申请免费体验,加入实战直播间"></div>
<div class="tp_main">
	<img src="images/img_06.jpg" alt="交易国际原油,盈利全归你">
	<a href="javascript:void(0);" onclick="apply()" class="tp_join">立即申请</a>
	<a href="http://www.vs.com/help?tab=newbie&leftMenu=6" target="_blank" class="tp_link">加入原油实战直播间</a>
</div>
<div class="tp_main">
	<img src="images/img_07.jpg">
	<span class="tp_num"><%=num%></span>
</div>
<div class="tp_mainlist">
	<img src="images/img_09.jpg">
	<div class="tp_ml_list">
		<ul>
			<ol class="title">
				<li>用户名</li>
				<li>操盘金额</li>
				<li>投入本金</li>
				<li>盈利金额</li>
			</ol>			
			<ol>
				<li>158****9443</li>
				<li>41800</li>
				<li>1</li>
				<li><span>4502</span></li>
			</ol>
			<ol>
				<li>131****9774</li>
				<li>41800</li>
				<li>1</li>
				<li><span>2127</span></li>
			</ol>
			<ol>
				<li>136****8053</li>
				<li>41800</li>
				<li>1</li>
				<li><span>1457</span></li>
			</ol>
			<ol>
				<li>182****9958</li>
				<li>41800</li>
				<li>1</li>
				<li><span>1350</span></li>
			</ol>
			<ol>
				<li>182****7664</li>
				<li>41800</li>
				<li>1</li>
				<li><span>1286</span></li>
			</ol>
			<ol>
				<li>139****6569</li>
				<li>41800</li>
				<li>1</li>
				<li><span>1198</span></li>
			</ol>
			<ol>
				<li>130****9999</li>
				<li>41800</li>
				<li>1</li>
				<li><span>1173</span></li>
			</ol>	
			<ol>
				<li>177****9099</li>
				<li>41800</li>
				<li>1</li>
				<li><span>1158</span></li>
			</ol>
			<ol>
				<li>138****2588</li>
				<li>41800</li>
				<li>1</li>
				<li><span>1054</span></li>
			</ol>	
			<ol>
				<li>138****6102</li>
				<li>41800</li>
				<li>1</li>
				<li><span>977</span></li>
			</ol>
		</ul>
	</div>
</div>
<div class="tp_main"><img src="images/img_10.jpg" alt=""></div>
<div class="tp_main"><img src="images/img_11.jpg" alt=""></div>
<div class="tp_main"><img src="images/img_12.jpg" alt=""></div>	
<div class="tp_main">
	<img src="images/img_13.jpg" alt="">
	<a href="http://www.vs.com/ftse/index" target="_blank" class="tp_l_btn">点击详细</a>
	<a href="http://www.vs.com/hsi/index" target="_blank" class="tp_l_btn2">点击详细</a>
	<a href="http://www.vs.com/product/gold_index" target="_blank" class="tp_l_btn3">点击详细</a>
</div>	
<div class="foot">Copyright © 2015 上海信闳投资管理有限公司 版权所有 沪ICP备14048395号-1</div>
<!-- <span style="display:none"><script src='http://w.cnzz.com/q_stat.php?id=1256807294&l=3' language='JavaScript'></script></span> -->
<span style="display:none"><script src="https://s95.cnzz.com/z_stat.php?id=1259839078&web_id=1259839078" language="JavaScript"></script></span>
</body> 
</html>