<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp"%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<%@ include file="../common/commonkeyword.jsp"%>
	<link rel="stylesheet" type="text/css" href="${ctx}/static/css/anniversary.css">
</head>
<body>
<!--顶部 -->
<%@ include file="../common/personheader.jsp"%>

<div class="tp_main"><img src="${ctx}/static/images/anniversary/pic_01.jpg" ></div>
<div class="tp_main"><img src="${ctx}/static/images/anniversary/pic_02.jpg"></div>
<div class="tp_main"><img src="${ctx}/static/images/anniversary/pic_03.jpg"></div>
<div class="tp_main"><img src="${ctx}/static/images/anniversary/pic_04.jpg"></div>
<div class="tp_floatlayer" style="display: none;">
	<div class="tp_flmask"></div>
	<!-- 奖品信息  -->
	<div class="tp_myprize">
		<p>获得精美礼品军刀</p>
		<a href="javascript:;" onclick="closeTips()">确定</a>
	</div>
</div>
<div class="tp_box">
	<!-- 开箱壕礼 -->
	<div class="tp_one">
		<img src="${ctx}/static/images/anniversary/gift.jpg"  class="gift"/>
		<h2>凡投资达人网注册会员，均拥有一次开箱抽奖机会。</h2>
		<c:choose>
			<c:when test="${islogin and isunpack }">
				<p class="tp_o_login">尊敬的<i>${mobile }</i>用户：你已获得${mykudo } </p>
			</c:when>
			<c:when test="${islogin and isunpack==false }">
				<p class="tp_o_login">尊敬的<i>${mobile }</i>用户：你有1次抽奖机会 </p>
			</c:when>
			<c:otherwise>
				<p class="tp_o_login"><a href="${ctx}/activity/anniversary/indexSSO">请登录</a></p>
			</c:otherwise>
		</c:choose>
		<div class="tp_o_gift">
			<c:choose>
				<c:when test="${islogin and isunpack }">
					<div class="tp_o_lotter"><a href="javascript:;"><img src="${ctx}/static/images/anniversary/am.gif"></a></div>
				</c:when>
				<c:when test="${islogin and isunpack==false }">
					<div class="tp_o_lotter"><a href="javascript:;" onclick="unpack()"><img src="${ctx}/static/images/anniversary/am.gif"></a></div>
				</c:when>
				<c:otherwise>
					<div class="tp_o_lotter"><a href="${ctx}/activity/anniversary/indexSSO"><img src="${ctx}/static/images/anniversary/am.gif"></a></div>
				</c:otherwise>
			</c:choose>
			<ul class="tp_o_giftlist">
				<li>
					<h3>精美礼品</h3>
					<p class="money">	
						<em></em>
						<span>投资达人网<br/>精心准备的小礼品 </span>
					</p>
				</li>
				<li>
					<h3>88元代金券</h3>
					<p class="ticket">	
						<em></em>
						<span>可代替用户<br/>操盘保证金<br/>交易国际期货 </span>
					</p>
				</li>
				<li>
					<h3>两倍赠送券</h3>
					<p class="packet">	
						<em></em>
						<span>赠送用户<br/>两倍的交易手数</span>
					</p>
				</li>
			</ul>
		</div>
	</div>
</div>
<div class="tp_box2">
	<!-- 奖励动态 -->
	<div class="tp_two">
		<div id="h_scroll">
			<img src="${ctx}/static/images/anniversary/dongtai.jpg" class="dongtai"/>
			<div id="slide" style="display:block;">
				<ul>
					<li>
						<span class="ticket"><i></i></span>
						<p>158****6482获得<br>88元代金券</p>
					</li>
					<li>
						<span class="upan"><i></i></span>
						<p>188****8203获得<br>精美礼品U盘</p>
					</li>
					<li>
						<span class="zpg"></span>
						<p>183****4810获得<br>精美礼品自拍杆</p>
					</li>
					<li>
						<span class="cdb"><i></i></span>
						<p>153****3611获得<br>精美礼品充电宝</p>
					</li>
					<li>
						<span class="dao"><i></i></span>
						<p>188****7950获得<br>精美礼品军刀</p>
					</li>
					<li>
						<span class="song"><i></i></span>
						<p>152****6423获得<br>双倍优惠券</p>
					</li>
					<li>
						<span class="ticket"><i></i></span>
						<p>187****6278获得<br>88元代金券</p>
					</li>
					<li>
						<span class="upan"><i></i></span>
						<p>180****1061获得<br>精美礼品U盘</p>
					</li>					
					<li>
						<span class="zpg"></span>
						<p>138****0833获得<br>精美礼品自拍杆</p>
					</li>
					<li>
						<span class="cdb"><i></i></span>
						<p>152****2265获得<br>精美礼品充电宝</p>
					</li>
					<li>
						<span class="dao"><i></i></span>
						<p>152****2265获得<br>精美礼品军刀</p>
					</li>
					<li>
						<span class="song"><i></i></span>
						<p>159****1932获得<br>双倍优惠券</p>
					</li>
				</ul>
			</div>
		</div>
	</div>
</div>
<div class="tp_box3">
	<!-- 活动规则 -->
	<div class="tp_rule">
		<img src="${ctx}/static/images/anniversary/rule.jpg" class="rule"/>
		<ul class="tp_ru_list">
			<li><i>1</i><p>凡投资达人网注册会员均有一次开箱抽奖机会，开箱时间为3月17日9点—18点。</p></li>
			<li><i>2</i><p>开箱抽中精美礼品的用户，将由专人在5个工作日内与您联系，并在确认身份后寄出。还请您保持电话畅通，方便接收我们的一份心意哦。</p></li>
			<li><i>3</i><p>抽中代金券的用户，可在“我的账户”——“我的优惠”下查看并使用。代金券在申请方案时可充抵操盘本金，可帮您获取更高收益。代金券在操盘结束后，将返还回平台。目前代金券只 支持期货产品，如您也想在股票操盘上使用，可联系客服咨询。代金劵的有效期至4月30号。</p></li>
			<li><i>4</i><p>抽中双倍优惠券的用户，可获得“每做一手就送两手”的特权。在3月18日，您交易国际期货（富时A50，恒指期货，国际原油）多少手，就赠送您两倍的交易手数！所赠送的手数，在3月 19日——4月30日期间，按每3手可使用赠送1手的规则进行兑现（即每4手返还1手的手续费）。5月1日零点所赠送的手数将失效。</p></li>
			<li><i>5</i><p>如您有疑问，欢迎咨询在线客服或拨打400-020-0158。</p></li>
			<li><i>6</i><p>投资达人对本活动拥有最终解释权。</p></li>
		</ul>
	</div>
</div>
<div class="tp_foot">Copyright © 2015 上海信闳投资管理有限公司 版权所有 沪ICP备14048395号-1</div>

<script type="text/javascript">
	var flag = false;
	function unpack() {
		if(flag) {
			return;
		}
		$.post(basepath+"activity/anniversary/unpack.json", {ajax:1}, function(data) {
			if(data.success) {
				flag = true;
				if("0" == data.code) {
					alert("提示：您未登录！");
					return;
				}
				if("1" == data.code) {
					$(".tp_myprize p").html("获得" + data.obj);
					$(".tp_floatlayer").show();
					return;
				}
				if("2" == data.code) {
					alert("奖品已抽完！");
					return;
				}
				if("3" == data.code) {
					alert("抽奖时间为3月17日9:00-18:00");
					return;
				}
				if("4" == data.code) {
					alert("活动已结束！");
					return;
				}
			} else {
				alert("提示","系统繁忙，请稍候重试......");
			}
		}, "json");
	}
	function closeTips() {
		$(".tp_floatlayer").hide();
		window.location.href = window.location.href;
	}
	function AutoScroll(){
		var _scroll = $("#slide>ul");
		_scroll.animate({marginLeft:"-143px"},1000,function(){
			_scroll.css({marginLeft:0}).find("li:first").appendTo(_scroll);
		});
	}
	$(function(){
		//两秒后调用
		var _scrolling=setInterval("AutoScroll()",2000);
		$("#slide>ul").hover(function(){
			//鼠标移动DIV上停止
			clearInterval(_scrolling);
		},function(){
			//离开继续调用
			_scrolling=setInterval("AutoScroll()",2000);
		});
	});
</script>
<!-- <span style="display:none"><script src='http://w.cnzz.com/q_stat.php?id=1256807294&l=3' language='JavaScript'></script></span> -->
<span style="display:none"><script src="https://s95.cnzz.com/z_stat.php?id=1259839078&web_id=1259839078" language="JavaScript"></script></span>
</body>
</html>