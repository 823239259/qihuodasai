<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../../WEB-INF/views/common/common.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"  />
<title>春节感恩回馈 - 投资达人</title>
<link rel="stylesheet" type="text/css" href="css/index.css">
</head>
 
<body>
<!--顶部 -->
<%@ include file="../../WEB-INF/views/common/personheader.jsp"%>
<div class="tp_main1"><img src="image/img_01.jpg" ></div>
<div class="tp_main1"><img src="image/img_02.jpg" ></div>
<div class="tp_main1"><img src="image/img_03.jpg" ></div>
<div class="tp_main1"><img src="image/img_04.jpg" ></div>
<div class="tp_main2">
	<div class="tp_main2_ctn">
		<a href="javascript:void();" onclick="sendEmail()"><img src="image/button.jpg"></a>
	</div>
</div>
<div class="tp_main3">
	<div class="tp_main3_ctn">
		<div class="tp_main3_ctn_mge">
			<p class="p1">2月8日—2月12日期间，任意操盘即可享受做一手返一手的优惠。</p>
			<p class="p2">每日免费赠送上限为16手，同一种交易品种对应同种交易手数。</p>
			<p class="p3">2月22日-2月26日起依次返还，每日最多返还16手。</p>
			<p class="p4">代理用户暂不享受本次活动。</p>
			<p class="p5">对活动如有任何疑问，敬请咨询投资达人客服电话400-020-0158。</p>
			<p class="p6">本次活动只针对恒指期货、国际原油，最终解释权归投资达人所有。</p>
			<p class="p7">（举个栗子：小明2月8日交易了16手恒指期货。那么对应2月22日如再次交易16手将免除手续费，超过16手的将正常收取手续费。手续费先扣后返。）</p>
		</div>
	</div>
</div>
<div class="tp_main4"><img src="image/img_05.jpg" ></div>


<div class="tp_foot">Copyright © 2016 上海信闳投资管理有限公司 版权所有 沪ICP备14048395号-1</div>

</body> 
<script type="text/javascript">
function sendEmail(){
	$.post("${ctx}/nyActivity/future",{},function(data){
		if("success"==data){
			alert("恭喜你，领取成功！！！");
		}else if("login"==data){
			window.location.href="${ctx}/toNewYearFutureSSO";
		}
	},"text");
}
</script>
</html>