<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp"%>
<%@include file="../common/import-fileupload-js.jspf"%>
<%@include file="../common/import-artDialog-js.jspf"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="../common/commonkeyword.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/kxhl.css">
	
</head>
<body>
<!--顶部 -->
<%@ include file="../common/personheader.jsp"%>
<input type="hidden" id="activityTimeStart" value="${activityTimeStart}">
<input type="hidden" id="activityTimeEnd" value="${activityTimeEnd}">
<div class="tp_main"><img src="${ctx}/static/images/kxhl/pic_01.jpg" alt="新年high翻天" ></div>
<div class="tp_main"><img src="${ctx}/static/images/kxhl/pic_02.jpg" alt="开箱有壕礼"></div>
<div class="tp_main"><img src="${ctx}/static/images/kxhl/pic_03.jpg" alt="2016年1月18日—2016年2月5日"></div>
<div class="tp_main tp_main2"><img src="${ctx}/static/images/kxhl/pic_04.jpg"></div>
<div class="tp_floatlayer">
	<div class="tp_flmask" style="display:none;"></div>
	<!-- 我的宝箱 -->
	<div class="tp_mybox" style="display:none;" id="mybox">
		<a href="javascript: void(0);" class="tp_mb_close" onclick="KXHL.closeWindow('#mybox',null);"></a>
		<h3>我的宝箱</h3>
		<ul class="tp_myboxlist" id="myboxlist">
			<ol id="myboxlistTitle">				
				<li>获得日期</li>
				<li>获奖名称</li>
				<li class="last">状态</li>	
			</ol>
			<c:forEach var="ActivityKudoWebVo" items="${ActivityKudoWebVoList}" varStatus="status">
				<ol>				
					<li>${ActivityKudoWebVo.kudoGetTime }</li>
					<li>${ActivityKudoWebVo.kudoName }</li>
					<li class="last">${ActivityKudoWebVo.kudoStatusStr }</li>	
				</ol>
			</c:forEach>
		</ul>
	</div>
	<!-- 奖品信息  -->
	<div class="tp_myprize" style="display:none;" id="myprize">
		<p>获得5000元现金红包</p>
		<a href="javascript: void(0);" onclick="KXHL.closeWindow('#myprize',null);">确定</a>
	</div>
</div>
<div class="tp_box">
	<!-- 开箱壕礼 -->
	<div class="tp_one">
		<h2>开箱次数以前一交易日国际期货交易手数为标准，每10手可抽一次。</h2>
		 <%
       		if(request.getSession().getAttribute("userName")!=null){
       	 %>
       	 	<p class="tp_o_login">尊敬的<i><%=request.getSession().getAttribute("userName").toString() %></i>用户：前一日交易手数：<i id="yesterdayTradNum">${yesterdayTradNum }</i>手，可开箱次数：<em id="unpackingNum">${unpackingNum }</em>次<a href="javascript: void(0);" onclick="KXHL.openWindow('#mybox',null);">查看我的宝箱</a></p>
       	 <%}else{ %>
			<p class="tp_o_login">尊敬的用户，请<a href="${ctx}/tokxhlSSO">登录</a></p>
       	 <% } %>
		<div class="tp_o_gift">
			<div class="tp_o_lotter"><a href="javascript: void(0);" status="true" id="getkxhlPrize"><img src="${ctx}/static/images/kxhl/am.gif"></a></div>
			<ul class="tp_o_giftlist">
				<li>
					<h3>5000元现金大奖</h3>
					<p class="money">	
						<em></em>
						<span>每日一个名额<br>返还到平台用户的余额<br>可直接提现</span>
					</p>
				</li>
				<li>
					<h3>7-9.5折扣券</h3>
					<p class="ticket">	
						<em>￥7-9.5折</em>
						<span>7折、8折、8.5折、9折、9.5折用于期货方案交易手续费打折</span>
					</p>
				</li>
				<li>
					<h3>5-18元现金红包</h3>
					<p class="packet">	
						<em></em>
						<span>5元、8元、10元、18元返还到平台用户的余额可直接提现</span>
					</p>
				</li>

			</ul>
		</div>
	</div>
	<!-- 奖励动态 -->
	<div class="tp_two">
		<div id="h_scroll">
			<ul>
				<li>
					<span class="ticket"><i>￥7折</i></span>
					<p>185****0825获得<br>7折抵扣券</p>
				</li>
				<li>
					<span class="packet"><i>8元</i></span>
					<p>189****4906获得<br>8元现金红包</p>
				</li>
				<li>
					<span class="money"></span>
					<p>158****3772获得<br>5000元现金</p>
				</li>
				<li>
					<span class="ticket"><i>8折</i></span>
					<p>137****7164获得<br>8折抵扣券</p>
				</li>
				<li>
					<span class="packet"><i>8元</i></span>
					<p>156****7475获得<br>8元现金红包</p>
				</li>
				<li>
					<span class="ticket"><i>￥7折</i></span>
					<p>185****0825获得<br>7折抵扣券</p>
				</li>
				<li>
					<span class="ticket"><i>￥8折</i></span>
					<p>183****1608获得<br>8折抵扣券</p>
				</li>
				<li>
					<span class="ticket"><i>8折</i></span>
					<p>137****7164获得<br>8折抵扣券</p>
				</li>
				<li>
					<span class="packet"><i>18元</i></span>
					<p>183****1321获得<br>18元现金红包</p>
				</li>
				<li>
					<span class="ticket"><i>8.5折</i></span>
					<p>137****7164获得<br>8.5折抵扣券</p>
				</li>
				<li>
					<span class="ticket"><i>8.5折</i></span>
					<p>137****7003获得<br>8.5折抵扣券</p>
				</li>
				<li>
					<span class="packet"><i>18元</i></span>
					<p>151****3021获得<br>18元现金红包</p>
				</li>
				<li>
					<span class="ticket"><i>8.5折</i></span>
					<p>151****3167获得<br>8.5折抵扣券</p>
				</li>
				<li>
					<span class="packet"><i>18元</i></span>
					<p>158****3525获得<br>18元现金红包</p>
				</li>
				<li>
					<span class="ticket"><i>￥7折</i></span>
					<p>185****0015获得<br>7折抵扣券</p>
				</li>
				<li>
					<span class="packet"><i>15元</i></span>
					<p>189****5031获得<br>15元现金红包</p>
				</li>	
				<li>
					<span class="packet"><i>18元</i></span>
					<p>158****3668获得<br>18元现金红包</p>
				</li>	
				<li>
					<span class="ticket"><i>￥7折</i></span>
					<p>132****2922获得<br>7折抵扣券</p>
				</li>	
				<li>
					<span class="ticket"><i>￥7折</i></span>
					<p>185****0005获得<br>7折抵扣券</p>
				</li>			
				<li>
					<span class="packet"><i>18元</i></span>
					<p>151****4578获得<br>18元现金红包</p>
				</li>	
				<li>
					<span class="ticket"><i>￥7折</i></span>
					<p>137****7114获得<br>7折抵扣券</p>
				</li>		
				<li>
					<span class="ticket"><i>￥7折</i></span>
					<p>133****7257获得<br>7折抵扣券</p>
				</li>			
				<li>
					<span class="packet"><i>18元</i></span>
					<p>159****8674获得<br>18元现金红包</p>
				</li>		
				<li>
					<span class="packet"><i>15元</i></span>
					<p>189****6657获得<br>15元现金红包</p>
				</li>
				<li>
					<span class="ticket"><i>￥8.5折</i></span>
					<p>151****7553获得<br>8.5折抵扣券</p>
				</li>
				<li>
					<span class="packet"><i>18元</i></span>
					<p>158****9321获得<br>18元现金红包</p>
				</li>
				<li>
					<span class="money"></span>
					<p>189****2621获得<br>5000元现金</p>
				</li>
			</ul>
		</div>
	</div>
	<!-- 活动规则 -->
	<div class="tp_rule">
		<ul class="tp_ru_list">
			<li><i>1</i><p>用户登录投资达人网后即可参与开箱抽奖，可抽奖次数以上一交易日国际期货交易总手数为计算依据，每10次可抽一次。一个用户有多个方案（含不同品种）时，以总手数为计算依据。所以为了大奖，可以多交易哦！</p></li>
			<li><i>2</i><p>活动第一天（即1月18日），用户可抽奖次数将以上一周（1.11-1.15）国际期货交易总手数为计算依据，每10手可抽一次。</p></li>
			<li><i>3</i><p>因交易所结算时间原因，每日抽奖时间自14点开始，当天24点截止，周六、周日除外。</p></li>
			<li><i>4</i><p>当您抽中5000元现金大奖后，将由客服人员与您联系，并在3个工作日内直接充入您在投资达人平台的账户余额中，可直接提现。</p></li>
			<li><i>5</i><p>当您抽中其它金额的现金红包后，3个工作日内将金额充入您在投资达人平台的账户余额中，可直接提现。</p></li>
			<li><i>6</i><p>当您抽中折扣券后，可向客服申请使用折扣券，并指明使用到您所操盘的具体方案(需在终结方案前或终结方案当日)。只要在活动期间，无论申请前后，操盘手数均可享受折扣优惠(住：如未通知客服，终结方案达一日以上者，该方案将无法使用抵扣券)。</p></li>
			<li><i>7</i><p>一个方案只能使用一张折扣券，折扣券使用期间截止到2016年2月29日。</p></li>
		</ul>
	</div>
</div>
<div class="tp_foot">Copyright © 2015 上海信闳投资管理有限公司 版权所有 沪ICP备14048395号-1</div>
<span style="display:none">
	<script src='http://w.cnzz.com/q_stat.php?id=1256807294&l=3' language='JavaScript'></script>
</span>
<%@ include file="../common/dsp.jsp"%>
<script src="${ctx}/static/script/activies/kxhl.js?version=${v}"></script>
</body>
</html>