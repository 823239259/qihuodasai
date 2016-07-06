<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../../WEB-INF/views/common/common.jsp"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"  />
<title>无红包不狂欢现金大红包送不停 - 投资达人</title>
<meta content="无红包,不狂欢,现金大红包送不停" name="description">
<link rel="stylesheet" type="text/css" href="css/index.css">

</head>
 
<body>
<!--顶部 -->
	<%@ include file="../../WEB-INF/views/common/personheader.jsp"%>
<div class="tp_main"><img src="images/img_01.jpg" alt="无红包,不狂欢" ></div>
<div class="tp_main"><img src="images/img_02.jpg" alt="富时A50,恒指期货,国际原油"></div>
<div class="tp_main"><img src="images/img_03.jpg" alt="现金大红包送不停"></div>
<div class="tp_main"><img src="images/img_04.jpg" alt="活动时间12月7日至12月31日"></div>
<div class="main bg">
	<div class="mainctn">
		<img src="images/title_01.gif" title="投资达人年终大回馈,国际期货红包发发发">
		<img src="images/img_05.gif" title="现金红包详情说明">
		<img src="images/img_06.gif" title="举个栗子">
	</div>	
</div>
<div class="main bg2">
	<div class="mainctn">
		<img src="images/title_02.gif" title="现金红包怎么拿">
		<img src="images/img_07.gif" title="点击报名,开户操盘,拿到红包">
		<a href="javascript:void(0);" onclick="sendEmail()" class="tp_btn">抢红包</a>
	</div>
</div>
<div class="main bg">
	<div class="mainctn">
		<img src="images/title_03.gif" title="现金红包使用说明">
		<img src="images/img_08.gif" >
	</div>	
</div>
<div class="main bg2">
	<div class="mainctn">
		<img src="images/title_04.gif" title="抢现金红包">
		<a href="javascript:void(0);" onclick="sendEmail()" class="tp_btn2"><img src="images/img_09.gif" title="快来抢"></a>
		<div class="tp_mainlist">
			<h2>现金红包榜</h2>
			<div>
				<span>中奖用户</span>
				<span>红包金额</span>
			</div>
			<ul id="h_scroll">
				<li>
					<span>185****6978</span>
					<span>900元</span>					
				</li>
				<li class="other">
					<span>131****4687</span>
					<span>750元</span>	
				</li>
				<li class="other">
					<span>150****1337</span>
					<span>600元</span>	
				</li>
				<li>
					<span>151****2669</span>
					<span>600元</span>	
				</li>
				<li class="other">
					<span>186****0713</span>
					<span>5元</span>	
				</li>
				<li>
					<span>189****5486</span>
					<span>5元</span>	
				</li>
				<li class="other">
					<span>134****1684</span>
					<span>575元</span>	
				</li>
				<li>
					<span>156****5459</span>
					<span>450元</span>	
				</li>
				<li class="other">
					<span>150****3118</span>
					<span>450元</span>	
				</li>
				<li>
					<span>157****0264</span>
					<span>400元</span>	
				</li>
				<li class="other">
					<span>138****9290</span>
					<span>300元</span>	
				</li>
				<li>
					<span>155****4691</span>
					<span>300元</span>	
				</li>
				<li class="other">
					<span>155****0643</span>
					<span>300元</span>	
				</li>
				<li>
					<span>155****5687</span>
					<span>300元</span>	
				</li>
				<li class="other">
					<span>135****6481</span>
					<span>200元</span>	
				</li>
				<li>
					<span>136****0775</span>
					<span>155元</span>	
				</li>
				<li class="other">
					<span>182****8189</span>
					<span>150元</span>	
				</li>
			</ul>
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
function sendEmail(){
	$.post("${ctx}/ifredenvelope/email",{},function(data){
		if("success"==data){
			alert("恭喜你，领取成功！！！");
		}else if("login"==data){
			window.location.href="${ctx}/toIfredEnvelopSSO";
		}
	},"text");
	}
	
	$(document).ready(function(){
		var $a=$(".navlist li").find("a").eq(0);
		$($a).removeClass("on");
	})
	
		
		setTimeout(function(){
			
			// 最新公告滚动效果
			var box=document.getElementById("h_scroll"),can=true; 
			box.innerHTML+=box.innerHTML; 
			box.onmouseover=function(){can=false}; 
			box.onmouseout=function(){can=true}; 
			new function (){ 
			var stop=box.scrollTop%18==0&&!can; 
			if(!stop)box.scrollTop==parseInt(box.scrollHeight/2)?box.scrollTop=0:box.scrollTop++; 
			setTimeout(arguments.callee,box.scrollTop%44?20:1000); 
			};
		},1000);
		

	
</script>


</html>