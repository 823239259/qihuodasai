<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp"%>
<%@include file="../common/import-artDialog-js.jspf"%>
<html xmlns="http://www.w3.org/1999/xhtml" lang="zh">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="../common/commonkeyword.jsp"%>
<link href="${ctx}/static/css/public.css" rel="stylesheet"
	type="text/css">
<link href="${ctx}/static/css/trade.css?v=20150528" rel="stylesheet"
	type="text/css" />


<script type="text/javascript">
	
</script>

</head>
<body>

	<!--顶部 -->
	<%@ include file="../common/personheader.jsp"%>

	<div class="capital">
	
	<div class="cp_suc">
	<c:set var="isTradeSuccess" value="false" scope="page"/>
	<c:choose>
		<c:when test="${feeType==0}">
			<c:if test="${!isActive}">
				<h3>申请方案验资中......</h3>
				<%-- <span>您现在可以<a href="${ctx}/help?tab=software&leftMenu=2" target="_blank">下载安装</a>交易软件！</span> --%>
				<span>申请成功后我们会向您的手机${mobile}发送短信提醒您！</span>
				<span>如果您有什么疑问，请联系客服：400-020-0158，客服将第一时间解决您的问题。</span>			
			</c:if>	
			<c:if test="${isActive}">
				<c:if test="${activityType}">
					<h3>恭喜您！达人红包8800活动的申请成功！</h3>
					<p>您的8800交易账户功能已开放，请使用下列交易账号密码登陆交易软件进行股票投资。</p>
					<span>交易账号：${account}</span>
					<span>交易密码：${password}</span>
					<span>您可以先<a href="${ctx}/help?tab=software&leftMenu=2">下载安装</a>交易软件</span>		
					<span class="red">您也可以自由体验网站其他功能，新开操盘方案炒股赚钱！</span>		
				</c:if>	
				<c:if test="${!activityType}">
					<c:if test="${!isMore}">
					<c:set var="isTradeSuccess" value="true" scope="page"/>
					<h3>恭喜您！申请成功!</h3>
					<span>你的交易账户功能已开放，享受你在投资达人的欢乐时光吧。</span>
					<span>交易账号：${account}</span>
					<span>交易密码：${password}</span>
					<%--
					<p class="cp_succolor1">* 平安保险保障交易账号资金安全！</p>
					 --%>
					<span>您现在可以<a href="${ctx}/help?tab=software&leftMenu=2" target="_blank">下载安装</a>交易软件！</span>	
					</c:if>	
					<c:if test="${isMore}">
					<h3>恭喜您！追加操盘方案成功!</h3>
					<p>操盘资金已划转到您的操盘方案【<a href="${ctx}/trade/detail/${groupId}">${groupId}</a>】中。如您有疑问，请联系客服：400-020-0158</p>
					</c:if>	
				</c:if>	
			</c:if>	
		</c:when>
		<c:when test="${feeType==2}">
			<c:set var="isTradeSuccess" value="true" scope="page"/>
			<h3>恭喜您！申请成功!您的方案开户中...</h3>
			<span>工作时间30分钟内开通股票交易账户，其他时间下个交易日09:15前开通。</span>
			<span>交易账户开户成功后我们将短信通知您。</span>
			<%--
			<p class="cp_succolor1">* 平安保险保障交易账号资金安全！</p>
			 --%>
			<%-- <span>您现在可以<a href="${ctx}/help?tab=software&leftMenu=5" target="_blank">下载安装</a>交易软件！</span>	 --%>
		</c:when>
		<c:when test="${feeType==3}">
			<c:set var="isTradeSuccess" value="true" scope="page"/>
			<h3>恭喜您！申请成功!您的方案开户中...</h3>
			<span>工作时间30分钟内开通股票交易账户，其他时间下个交易日09:15前开通。</span>
			<span>交易账户开户成功后我们将短信通知您。</span>
			<%--
			<p class="cp_succolor1">* 平安保险保障交易账号资金安全！</p>
			 --%>
			<%-- <span>您现在可以<a href="${ctx}/help?tab=software&leftMenu=6" target="_blank">下载安装同花顺</a>交易软件！</span>	 --%>
		</c:when>
	</c:choose>
		<div class="cp_paybtn">
			<div class="cp_pb_font">您现在可以进入：</div>
			<div class="cp_btnback"><a href="${ctx}">返回首页</a></div>		
			<div class="uc_paybtn cp_btnsuc"><a href="${ctx}/user/account">个人中心</a></div>
			<div class="uc_paybtn cp_btnsuc"><a href="${ctx}/trade/detail/${groupId}">方案详情</a></div>	
		</div>
		<c:if test="${isTradeSuccess }">
		<div class="cp_sucpromt">
			<h4>提示：</h4>
			<div class="cp_sucpromtfont">
			1.停牌股处理规则，<a href="${ctx}/help?tab=rule&leftMenu=3">点击查看</a><br>2.平仓后方案会继续保留并产生费用，停止产生费用请终结方案。<br>
			<c:if test="${!empty parentEndDate}">
			3.方案最长可延期至：${parentEndDate }
			</c:if>
			</div>
		</div>
		</c:if>
	</div>
</div>
	<%@ include file="../common/personfooter.jsp"%>
<%@ include file="../common/dsp.jsp"%>
</body>
</html>

