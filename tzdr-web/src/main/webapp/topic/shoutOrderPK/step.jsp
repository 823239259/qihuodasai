<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../../WEB-INF/views/common/common.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"  />
<title>喊单PK赛火热进行 - 投资达人</title>
<meta content="无红包,不狂欢,现金大红包送不停" name="description">
<link rel="stylesheet" type="text/css" href="css/index.css">
</head>
 
<body>
<!--顶部 -->
	<%@ include file="../../WEB-INF/views/common/personheader.jsp"%>
<div class="tp_floatlayer" style="display: none">
	<div class="tp_flmask"></div>
	<div class="tp_fl">
		<a href="javascript:void(0)" class="close" onclick="closewin()"></a>
		<table border="0" cellspacing="0" cellpadding="0">
		    <thead>		  	
			    <tr>
				    <td>分析师</td>
				    <td>商品名称</td>
				    <td>开仓时间</td>
				    <td>类型</td>
				    <td>开仓价</td>
				    <td>止损价</td>
				    <td>止盈价</td>
				    <td>平仓时间</td>
				    <td>平仓价</td>
				    <td>获利点数</td>
			    </tr>
		    </thead>		    
		    <tbody> 	
			    <tr>
				    <td>傅老师</td>
				    <td>恒指期货</td>
				    <td>9:32</td>
				    <td>限价买入</td>
				    <td>21847</td>
				    <td>21840</td>
				    <td>21840</td>
				    <td></td>
				    <td>21840</td>
				    <td>168</td>
			    </tr>
			    <tr>
				    <td>傅老师</td>
				    <td>恒指期货</td>
				    <td>9:46</td>
				    <td>限价买入</td>
				    <td>22040</td>
				    <td>22015</td>
				    <td>22100</td>
				    <td></td>
				    <td>22100</td>
				    <td>60</td>
			    </tr>	    
			    <tr>
				    <td>傅老师</td>
				    <td>恒指期货</td>
				    <td>10:21</td>
				    <td>限价买入</td>
				    <td>22060</td>
				    <td>22035</td>
				    <td>22090</td>
				    <td></td>
				    <td>22090</td>
				    <td>30</td>
			    </tr>			    		    
			    <tr>
				    <td>傅老师</td>
				    <td>恒指期货</td>
				    <td>13:48</td>
				    <td>限价买入</td>
				    <td>22040</td>
				    <td>22020</td>
				    <td>22065</td>
				    <td>14:08</td>
				    <td>22055</td>
				    <td>15</td>
			    </tr>    			   
		    </tbody>
		</table>
	</div>
</div>
<div class="tp_main">
	<img src="images/img_01.jpg" alt="喊单PK赛火热进行中">
</div>
<div class="tp_main">
	<img src="images/img_02.jpg" alt="盈利大于喊单师就有现金拿">
	<p class="tp_font">胜利者:180****6293<i>300</i>元</p>
	<a href="javascript:void(0)" class="tp_btn" onclick="enter()">立即报名参赛</a>
</div>
<div class="tp_main"><img src="images/img_03.jpg" alt="比赛时间:12月23日至12月31日"></div>
<div class="tp_main">
	<img src="images/img_04.jpg" alt="喊单师战绩展示">
    <p class="tp_record">4手<i>盈利：11533元</i></p>
	<a href="javascript:void(0)" class="tp_btn2" onclick="showshoutOrder()">点击查看喊单记录</a>
	<a href="http://zhibo.tzdr.com" class="tp_btn3" target="_blank">立即加入直播间</a>
</div>
<div class="tp_nav">
	<div class="tp_navctn">
		<a href="index.jsp">大赛排行榜</a>
		<a href="javascript:void(0)" class="on">参赛流程</a>
		<a href="rule.jsp">大赛规则</a>
		<a href="prize.jsp">奖品设置</a>
	</div>
</div>


<div class="tp_ctn">	
	<!-- 参赛流程 -->
<div class="tp_rank—process">
	<div class="tp_rank_main" id="tp_rank_main1"><img src="images/process1.jpg" ></div>
	<div class="tp_rank_main"><img src="images/process2.jpg" ></div>
	<div class="tp_rank_main"><img src="images/process3.jpg" ></div>
	<div class="tp_rank_main"><img src="images/process4.jpg" ></div>
	<div class="tp_rank_main"><img src="images/process5.jpg" ></div>
	<div class="tp_rank_main"><img src="images/process6.jpg" ></div>
		<div class="processButton"><a href="http://www.tzdr.com/signin" style="margin-left:-380px" target='_blank'>注册投资达人会员</a><a href="javascript:void(0)" style="margin-left:-83px" onclick="enter()">立即报名参赛</a><a href="http://zhibo.tzdr.com" style="margin-left:217px" target="_blank">开始喊单比赛</a></div>
</div>
</div>


<div class="tp_foot">Copyright © 2015 上海信闳投资管理有限公司 版权所有 沪ICP备14048395号-1</div>
<span style="display:none">
<!-- <span style="display:none"><script src='http://w.cnzz.com/q_stat.php?id=1256807294&l=3' language='JavaScript'></script></span> -->
<span style="display:none"><script src="https://s95.cnzz.com/z_stat.php?id=1259839078&web_id=1259839078" language="JavaScript"></script></span>
</body> 
<script type="text/javascript">
function enter(){
	$.post("${ctx}/shoutOrderPK/email",{},function(data){
		if("success"==data){
			alert("恭喜你，报名成功！！！");
		}else if("login"==data){
			window.location.href="${ctx}/toshoutOrderPKSSO";
		}
	},"text");
}

function showshoutOrder(){
	$(".tp_floatlayer").css("display","block");
}

function closewin(){
	$(".tp_floatlayer").css("display","none");
}

$(document).ready(function(){
	var $a=$(".navlist li").find("a").eq(0);
	$($a).removeClass("on");
})

</script>
</html>