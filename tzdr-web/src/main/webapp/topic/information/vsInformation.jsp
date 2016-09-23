<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../../WEB-INF/views/common/common.jsp"%>
<html>
<head>
<meta name="viewport" content="width=1010">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="keywords" content="维胜，国际期货，期货，投资达人，金勺子，高盛，都城，南华期货，配资，期货配资，期货开户，外盘，富时A50，国际原油，恒指期货，期货公司，期货平台，炒期货，模拟盘，赚钱，头寸，持仓，成都盈透科技有限公司"/>
<meta name="description" content="维胜（www.vs.com）-致力于成为中国领先的国际期货及衍生品互联网交易平台，提供恒指期货、国际原油、富时A50等主流国际期货产品，开户操盘快捷方便，交易费用全网最低。"/>
<link rel="stylesheet" href="../css/vsInformation.css?v=20151127">
<title>维胜资讯-维胜金融- 中国领先的国际期货及衍生品互联网交易平台</title>
</head>
<body>
	<div class="information-img"></div>
	<div class="information-content">
		<div class="information-center">
			<div class="fl">
				<p>7*24直播</p>
				<ul>
					<li>专业报道资讯全掌握</li>
					<li>深入浅出行情全明白</li>
					<li>实时放送信息全推送</li>
				</ul>
			</div>
			<div class="fl">
				<p>财经日历</p>
				<ul>
					<li>国内国外财经大事一线展示</li>
					<li>基金期货种种数据一手掌握</li>
					<li>行情走向各类趋势一目了然</li>
				</ul>
			</div>
			<div class="fl">
				<p>财经资讯</p>
				<ul>
					<li>财经大事深入解读</li>
					<li>新颖观点助力理解</li>
					<li>独特视角更新思维</li>
				</ul>
			</div>
			<div class="fl">
				<p>宏观数据</p>
				<ul>
					<li>海量数据完整收罗</li>
					<li>数据节点精准分析</li>
					<li>结构数据助力量化</li>
				</ul>
			</div>
		</div>
	</div>
	<div class="information-concern">
		<div class="concern-center">
			<ul>
				<li class="weixin"></li>
				<li>关注微信</li>
			</ul>
			<ul>
				<li class="app"></li>
				<li>APP下载 <span style="font-size: 14px;"> (iOS&Android)</span></li>
			</ul>
			<ul>
				<li class="furx">客服热线</li>
				<li>400-852-8008</li>
				<li>工作日：8:30 - 24:00</li>
				<li>周末：09:00 - 17:00</li>
			</ul>
			<ul>
				<li class="furx">官方QQ群</li>
				<li>469441280</li>
			</ul>
		</div>
	</div>
	<div class="information-footer"><p>Copyright &copy; 2016 成都盈透科技有限公司 版权所有 蜀ICP备16018768号-1</p></div>
	<div id="kefu">
		<span class="close"></span>
		<p><span class="ry"></span>维胜资讯欢迎您！如果您有跟期货相关的问题，我们可以在线为您解答。</p>
		<div class="lx"><input type="text" placeholder="您可以在这里直接与我们联系！"><span><a href="http://crm2.qq.com/page/portalpage/wpa.php?uin=4008528008&amp;aty=0&amp;a=0&amp;curl=&amp;ty=1" target="_blank" style="color: #dcefff;text-decoration: none;">发送</a></span></div>
	</div>
</body>
<script>
	window.onload = function(){ 
		$("#kefu").show();
	} 
	$(function() {
        $("#kefu .close").click(function(){
            $("#kefu").hide();
            setTimeout(function(){
                $("#kefu").show();
            },60000*2);
        });
    })
</script>
</html>