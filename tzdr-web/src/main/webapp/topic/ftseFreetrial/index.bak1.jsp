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
	List<DataMap> datas =  data.findByTypeKey("ftseActive");
	DataMap mapObj = datas.get(0);
	 num= Integer.parseInt(mapObj.getValueKey())*3;
} 
%>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"  />
<title>投资达人百万现金大派送 - 投资达人</title>
<meta content="投资达人百万现金大派送,18000元A50实盘资金免费领" name="description">
<link rel="stylesheet" type="text/css" href="css/index.css">
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/common.css">


<script type="text/javascript">

function apply(){ 
	if(!isLoginSSO){
		window.location.href="http://www.tzdr.com:80/topic/ftseFreetrial.sso"; 
	}else{ 
		$.post("${ctx}/ftseActive/apply",{},function(data){
			if(data.success){
				if(data.message!="" && data.message!=null){
					if(data.message == "over"){
						alert("今日活动名额已满，请明天再来");
						return;
					}else if(data.message == "again"){
						alert("您已拥有名额，请勿重复申请");
						window.location.href="${ctx}/ftseActive/index";
					}else if(data.message == "old"){
						alert("您已申请过富时A50，不能参加本次活动");
						return;
					}else if(data.message == "wrong"){
						alert("新手体验活动是2015.11.16-2015.11.20的9点-15点，敬请期待");
						return;
					}else if(data.message == "success") {
						window.location.href="${ctx}/ftseActive/index";
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
<div class="tp_main"><img src="images/img_01.jpg" alt="投资达人百万现金大派送" ></div>
<div class="tp_main"><img src="images/img_02.jpg" alt="18000元A50实盘资金免费领"></div>
<div class="tp_main"><img src="images/img_03.jpg" alt="盈利可提现,亏损算我们"></div>
<div class="tp_main"><img src="images/img_04.jpg" alt="仅此一次,机不可失"></div>
<div class="tp_main"><img src="images/img_05.jpg" alt="申请免费体验,加入实战直播间"></div>
<div class="tp_main">
	<img src="images/img_06.jpg" alt="交易富时A50,盈利全归你">
	<a href="javascript:void(0);" onclick="apply()" class="tp_join">立即申请</a>
	<a href="http://www.tzdr.com/help?tab=newbie&leftMenu=6" target="_blank" class="tp_link">加入A50实战直播间</a>
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
				<li>153****9647</li>
				<li>18000</li>
				<li>1</li>
				<li><span>275</span></li>
			</ol>		
			<ol>
				<li>133****3075</li>
				<li>18000</li>
				<li>1</li>
				<li><span>918</span></li>
			</ol>		
			<ol>
				<li>139****0097</li>
				<li>18000</li>
				<li>1</li>
				<li><span>906</span></li>
			</ol>		
			<ol>
				<li>185****0910</li>
				<li>18000</li>
				<li>1</li>
				<li><span>84</span></li>
			</ol>		
			<ol>
				<li>139****4482</li>
				<li>18000</li>
				<li>1</li>
				<li><span>1136</span></li>
			</ol>		
			<ol>
				<li>186****3738</li>
				<li>18000</li>
				<li>1</li>
				<li><span>614</span></li>
			</ol>		
			<ol>
				<li>137****8678</li>
				<li>18000</li>
				<li>1</li>
				<li><span>595</span></li>
			</ol>		
			<ol>
				<li>189****3677</li>
				<li>18000</li>
				<li>1</li>
				<li><span>559</span></li>
			</ol>		
			<ol>
				<li>135****5419</li>
				<li>18000</li>
				<li>1</li>
				<li><span>1283</span></li>
			</ol>		
			<ol>
				<li>139****0126</li>
				<li>18000</li>
				<li>1</li>
				<li><span>1407</span></li>
			</ol>
		</ul>
	</div>
</div>
<div class="tp_main"><img src="images/img_10.jpg" alt=""></div>
<div class="tp_main"><img src="images/img_11.jpg" alt=""></div>
<div class="tp_main"><img src="images/img_12.jpg" alt=""></div>	
<div class="tp_main">
	<img src="images/img_13.jpg" alt="">
	<a href="http://www.tzdr.com/ftse/index" target="_blank" class="tp_l_btn">点击详细</a>
	<a href="http://www.tzdr.com/hsi/index" target="_blank" class="tp_l_btn2">点击详细</a>
	<a href="http://www.tzdr.com/product/gold_index" target="_blank" class="tp_l_btn3">点击详细</a>
</div>	
<div class="foot">Copyright © 2015 上海信闳投资管理有限公司 版权所有 沪ICP备14048395号-1</div>
<span style="display:none"><script src='http://w.cnzz.com/q_stat.php?id=1256807294&l=3' language='JavaScript'></script></span>
</body> 
</html>