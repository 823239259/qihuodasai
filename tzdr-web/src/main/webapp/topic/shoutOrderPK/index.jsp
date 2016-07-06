<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../../WEB-INF/views/common/common.jsp"%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"  />
<title>喊单PK赛火热进行 - 投资达人</title>
<meta content="喊单pk赛火热进行中,当日盈利大于喊单师就有现金拿" name="description">
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
		<a href="javascript:void(0)" class="on">大赛排行榜</a>
		<a href="step.jsp">参赛流程</a>
		<a href="rule.jsp">大赛规则</a>
		<a href="prize.jsp">奖品设置</a>
	</div>
</div>
<div class="tp_ctn">	
	<div class="tp_rank">
		<h3><i>今日排行榜</i><em>大赛火热进行中，参赛就有机会获奖！</em></h3>
		<div class="tp_rankctn">
			<ul class="tp_ranklist">
				<ol class="tp_ranktitle today_title">
					<li>排名</li>
					<li>用户名</li>
					<li>手数</li>
					<li>盈利</li>
					<li>胜率</li>				
				</ol>
				<ol class="tp_rankol">
					<li>1</li>
					<li>180****6293</li>
					<li>38</li>
					<li>12900</li>
					<li>1胜0败</li>				
				</ol>
				<ol class="tp_rankol">
					<li>2</li>
					<li>188****5950</li>
					<li>18</li>
					<li>11575</li>
					<li>1胜0败</li>				
				</ol>
				<ol class="tp_rankol">
					<li>3</li>
					<li>186****1780</li>
					<li>24</li>
					<li>9868</li>
					<li>0败</li>				
				</ol>
				<ol>
					<li>4</li>
					<li>132****6823</li>
					<li>20</li>
					<li>9651</li>
					<li>0败</li>				
				</ol>
				<ol>
					<li>5</li>
					<li>134****1061</li>
					<li>26</li>
					<li>9321</li>
					<li>0败</li>				
				</ol>
				<ol>
					<li>6</li>
					<li>135****5364</li>
					<li>23</li>
					<li>8951</li>
					<li>0败</li>				
				</ol>
				<ol>
					<li>7</li>
					<li>189****2529</li>
					<li>15</li>
					<li>7892</li>
					<li>0败</li>				
				</ol>
				<ol>
					<li>8</li>
					<li>185****0226</li>
					<li>16</li>
					<li>7752</li>
					<li>0败</li>				
				</ol>
				<ol>
					<li>9</li>
					<li>155****8753</li>
					<li>21</li>
					<li>6982</li>
					<li>0败</li>				
				</ol>
				<ol>
					<li>10</li>
					<li>138****1229</li>
					<li>19</li>
					<li>6648</li>
					<li>0败</li>				
				</ol>
				
			</ul>
			<p><a href="javascript:void(0)" onclick="showmore(this)">查看更多</a></p>
		</div>
		<h3><i>累计排行榜</i><em>大赛火热进行中，参赛就有机会获奖</em></h3>
		<div class="tp_rankctn">
			<ul class="tp_ranklist">
				<ol class="tp_ranktitle leiji_title">
					<li>排名</li>
					<li>用户名</li>
					<li>手数</li>
					<li>盈利</li>
					<li>胜率</li>				
				</ol>
			</ul>		
			<p><a href="javascript:void(0)" onclick="showmore(this)">查看更多</a></p>
		</div>
	</div>

</div>
<div class="tp_foot">Copyright © 2015 上海信闳投资管理有限公司 版权所有 沪ICP备14048395号-1</div>
<span style="display:none">
<!-- <span style="display:none"><script src='http://w.cnzz.com/q_stat.php?id=1256807294&l=3' language='JavaScript'></script></span> -->
<span style="display:none"><script src="https://s95.cnzz.com/z_stat.php?id=1259839078&web_id=1259839078" language="JavaScript"></script></span>
</span>
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

function showmore(obj){
	var ols=$(obj).closest(".tp_rankctn").find("ol");
	$(ols).each(function(i){
		if(i>10){
				$(this).toggle();
		}
	})
	if($(obj).text()=="查看更多"){
		$(obj).text("收起");
	}else if($(obj).text()=="收起"){
		$(obj).text("查看更多");
	}
}

$(document).ready(function(){
	var $a=$(".navlist li").find("a").eq(0);
	$($a).removeClass("on");
	
	var ols=$(".today_title").nextAll();
	var ols2=$(".leiji_title").nextAll();
	$(ols).each(function(i){
		if(i>=10){
				$(this).hide();
		}
	})
	 $(ols2).each(function(i){
		if(i>=10){
				$(this).hide();
		}
	}) 
})

</script>
</html>