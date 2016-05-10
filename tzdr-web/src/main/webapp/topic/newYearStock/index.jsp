<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../../WEB-INF/views/common/common.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"  />
<title>春节重磅福利 - 投资达人</title>
<link rel="stylesheet" type="text/css" href="css/index.css">
</head>
 
<body>
<!--顶部 -->
<%@ include file="../../WEB-INF/views/common/personheader.jsp"%>
<div class="tp_main1"><img src="image/img_01.jpg" ></div>
<div class="tp_main1"><img src="image/img_02.jpg" ></div>
<div class="tp_main1"><img src="image/img_03.jpg" ></div>
<div class="tp_main1"><img src="image/img_04.jpg" ></div>
<div class="tp_main1"><img src="image/img_05.jpg" ></div>
<div class="tp_main1"><img src="image/img_06.jpg" ></div>
<div class="tp_main2">
	<div class="tp_main2_ctn">
		<h3>春节福利第一波：开门红包领！领！领！</h3>
		<p>2月1日起新开股票方案，并持有至2月15日，2月16日起即可领取现金红包。人人有份！金额大！大！大！</p>
		<a href="javascript:void();" onclick="sendEmail()"><img src="image/hongbao.jpg" /></a>
	</div>
</div>
<div class="tp_main3">
	<div class="tp_main3_ctn">
		<h3>春节福利第二波：实盘资金送！送！送！最高百万</h3>
		<p>2月1日起新开方案，并持有至2月15日，此方案在2月16日即可获得免费赠送实盘资金资格，所赠金额为申请方案时操盘保证金，最高百万！<br/><span>一个涨停，多赚10%！所赠资金完全免费，不收取任何费用。</span></p>
		<a href="javascript:void();" onclick="sendEmail(1)"><img src="image/linqu.jpg" /></a>
	</div>
</div>
<div class="tp_main4">
	<div class="tp_main4_ctn">
		<h3>赠送实盘资金规则：</h3>
		<p class="p1">只有当用户保证金大于或等于申请方案时保证金金额，才能申请成功</p>
		<p class="p2">所赠实盘资金使用期限为2月16日-2月26日</p>
		<p class="p3">获赠实盘资金后，补仓线与平仓线将按赠送后的倍数进行调整</p>
	</div>
</div>
<div class="tp_foot">Copyright © 2016 上海信闳投资管理有限公司 版权所有 沪ICP备14048395号-1</div>

</body> 
<script type="text/javascript">
function sendEmail(type){
	var url = "${ctx}/nyActivity/stock";
	if (type){
		url = "${ctx}/nyActivity/stockReceive";
	}
	$.post(url,{},function(data){
		if("success"==data){
			alert("恭喜你，领取成功！！！");
		}else if("login"==data){
			window.location.href="${ctx}/toNewYearStockSSO";
		}else
		{
			alert(data);
		}
	},"text");
}
</script>
</html>