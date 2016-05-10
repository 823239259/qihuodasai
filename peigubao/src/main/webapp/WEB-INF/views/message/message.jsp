<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp"%>
<%@include file="../common/import-artDialog-js.jspf"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="../common/commonkeyword.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/uc.css?version=20150721" />
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/public.css">
<script type="text/javascript" src="${ctx}/static/script/common/dateUtils.js"></script>
<script type="text/javascript" src="${ctx}/static/script/message/message.js?version=20150721"></script>
</head>
<body>
<!--顶部 -->
<%@ include file="../common/personheader.jsp"%>
<!-- 弹出层 -->
<div class="floatlayer">
	<div class="uc_m_select" style="display:none;">
		<c:if test="${!empty messageTypeList}">
			<c:forEach items="${messageTypeList}" var="messageType" varStatus="status">
				<c:choose>
					<c:when test="${status.index == 0}">
						<a href="javascript:void(0);" class="on" data-id="${messageType.valueKey }">${messageType.valueName }</a>
					</c:when>
					<c:otherwise>
						<a href="javascript:void(0);" data-id="${messageType.valueKey }">${messageType.valueName }</a>
					</c:otherwise>
				</c:choose>
			</c:forEach>
		</c:if>
	</div>
</div>
<!-- 在线留言 -->
<div class="uc">
	<!--个人中心导航 -->
	<%@ include file="../common/leftnav.jsp"%>
	<!--快捷支付-->
	<div class="uc_mianbar">
		<div class="uc_message">
			<h2>在线留言</h2>
			<div class="uc_m_fill">
				<p>类型：</p>
				<c:choose>
					<c:when test="${!empty messageTypeList}">
						<c:forEach items="${messageTypeList}" var="messageType" varStatus="status">
							<c:if test="${status.index==0}">
								<input type="text" class="uc_m_ip messageTypeValue" disabled="true" data-id="${messageType.valueKey }" value="${messageType.valueName }">
							</c:if>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<input type="text" class="uc_m_ip messageTypeValue" disabled="true" value="">
					</c:otherwise>
				</c:choose>
				<a href="javascript:void(0);" class="uc_m_down"></a>
			</div>
			<div class="uc_m_fill content">
				<p>内容：</p>
				<textarea maxlength="3000"></textarea>				
				<div class="uc_paybtn uc_msbtn msbtn"><a href="javascript:void(0);" >提交</a></div>
			</div>
			
			<div class="messageList">
				<c:if test="${!empty messageList}">
					<c:forEach items="${messageList}" var="message" varStatus="status">
						<div class="uc_ms">
							<p class="uc_mstitle">
								<em>${message.type}</em>
								<a href="javascript:void(0)" data-id="${message.id}" class="delMessage">删除</a>
								<i>
								<script type="text/javascript">
									document.write(getFormatDateByLong('${message.addtime}',"yyyy-MM-dd"));
								</script>
								</i>
							</p>
							<ul class="uc_mslist">
								<ol>
									<li><i class="uc_mscl">内容：</i><p>${message.content}</p></li>
									<li><i>回复：</i><p>${message.recontent}</p></li>
								</ol>
							</ul>
						</div>
					</c:forEach>
				</c:if>
			</div>
		</div>
	</div>
</div>
<!--底部 -->
<%@ include file="../common/personfooter.jsp"%>
<%@ include file="../common/dsp.jsp"%>
</body>
</html>