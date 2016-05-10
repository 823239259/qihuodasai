<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="../common/common.jsp"%>
<%@include file="../common/import-artDialog-js.jspf"%>
<html xmlns="http://www.w3.org/1999/xhtml" lang="zh">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="../common/commonkeyword.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/public.css"/>
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/uc.css?version=20150721"  />
<script type="text/javascript" src="${ctx}/static/script/common/dateUtils.js"></script>
<script type="text/javascript" src="${ctx}/static/script/userVolume/userVolume.js?version=20150721"></script>
<script language="javascript" src="${ctx}/static/script/tzdr.js"></script>
<title>个人中心-抵扣券</title>
</head>
<body>
<!--顶部 -->
<%@ include file="../common/personheader.jsp"%>
<div class="uc">
	<!--个人中心导航 -->
	<%@ include file="../common/leftnav.jsp"%>
	<!--网银支付-->
	<div class="uc_mianbar">
		<div class="uc_pay">			
			<ul class="uc_paynav">
				<li id="ticketTab"><a href="javascript:void(0);" id="all" data-volumeType='0' class="on">全部抵扣券</a></li>
				<li id="ticketTab"><a href="javascript:void(0);" id="unused" data-volumeType='-1'>未使用</a></li>
				<li id="ticketTab"><a href="javascript:void(0);" id="used" data-volumeType='1'>已使用</a></li>
				<li id="ticketTab"><a href="javascript:void(0);" id="Invalid" data-volumeType='2'>已过期</a></li>
			</ul>
			<div class="uc_ticket" id="ticketList">
			<c:if test="${!empty userVolumeDetailVoList}">
				<c:forEach items="${userVolumeDetailVoList}" var="userVolumeDetailVo">
					<c:choose>
						<c:when test="${userVolumeDetailVo.useState == -1}">
							<div class="uc_tcone">
								<h3><i><fmt:formatNumber value="${userVolumeDetailVo.money} " pattern="#,###.##" minFractionDigits="0" ></fmt:formatNumber></i><em>${userVolumeDetailVo.useTypeName}</em></h3>
								<p>编号：${userVolumeDetailVo.code}</p>
								<p>获得日期：
									<script type="text/javascript">
											document.write(getFormatDateByLong('${userVolumeDetailVo.timeValueOfGetVolume}',"yyyy-MM-dd"));
									</script>
								</p>
								<p>有效日期：
									<script type="text/javascript">
											document.write(getFormatDateByLong('${userVolumeDetailVo.endDateValue}',"yyyy-MM-dd"));
									</script>
								</p>
								<p>备注：${userVolumeDetailVo.name}</p>
							</div>
						</c:when>
						<c:otherwise>
							<div class="uc_tcone uc_tcbg">
								<h3><i><fmt:formatNumber value="${userVolumeDetailVo.money} " pattern="#,###.##" minFractionDigits="0" ></fmt:formatNumber></i><em>${userVolumeDetailVo.useTypeName}</em></h3>
								<p>编号：${userVolumeDetailVo.code}</p>
								<p>获得日期：
									<script type="text/javascript">
											document.write(getFormatDateByLong('${userVolumeDetailVo.timeValueOfGetVolume}',"yyyy-MM-dd"));
									</script>
								</p>
								<c:choose>
									<c:when test="${userVolumeDetailVo.useState == 1}">
										<p>使用日期：
											<script type="text/javascript">
													document.write(getFormatDateByLong('${userVolumeDetailVo.userDateValue}',"yyyy-MM-dd"));
											</script>
										</p>
									</c:when>
									<c:otherwise>
										<p>有效日期：
											<script type="text/javascript">
													document.write(getFormatDateByLong('${userVolumeDetailVo.endDateValue}',"yyyy-MM-dd"));
											</script>
										</p>
									</c:otherwise>
								</c:choose>
								<p>备注：${userVolumeDetailVo.name}</p>
								<c:choose>
									<c:when test="${userVolumeDetailVo.useState == 1}">
										<span class="uc_tcuse"></span>
									</c:when>
									<c:otherwise>
										<span class="uc_tctime"></span>
									</c:otherwise>
								</c:choose>
							</div>
						</c:otherwise>
					</c:choose>
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