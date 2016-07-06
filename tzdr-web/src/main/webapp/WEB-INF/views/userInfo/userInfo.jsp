<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp"%>
<%@include file="../common/import-artDialog-js.jspf"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>个人信息 - 维胜</title>
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/public.css">
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/uc.css?version=20150721"  />
<!-- <script src="js/jquery-1.8.2.min.js" type="text/javascript"></script> -->
<%-- <script type="text/javascript"  src="${ctx}/static/js/uc.js"></script> --%>
<script language="javascript" src="${ctx}/static/script/userInfo/userInfo.js?version=20150721"></script>
</head>

<body>


<!--顶部 -->
<%@ include file="../common/personheader.jsp"%>

<div class="uc">
	<!--个人中心导航 -->
	<%@ include file="../common/leftnav.jsp"%>
	<!--个人信息-->
	<div class="uc_mianbar">
		<div class="uc_info">
			<h3><i>个人信息</i></h3>
			<div class="uc_i_list">
				<ul>
					<!-- <li>
						<label>用户名：</label>
						<span>Roger</span>
					</li> -->
					<li>
						<label>真实姓名：</label>
						<c:choose>
							<c:when test="${!empty userVerified and !empty userVerified.tname }"><span>${userVerified.tname}</span></c:when>
							<c:otherwise><span>未认证</span><a href="${ctx}/securityInfo/secInfo">认证</a></c:otherwise>
						</c:choose>
					</li>
					<li>
						<label>身份证号码：</label>
					    <c:choose>
							<c:when test="${!empty idcard}"><span>${idcard}</span></c:when>
							<c:otherwise><span>未认证</span><a href="${ctx}/securityInfo/secInfo">认证</a></c:otherwise>
						</c:choose>
					</li>
					<li>
						<label>手机号码：</label>
							<c:choose>
								<c:when test="${!empty userName }"><span>${userName}</span></c:when>
								<c:otherwise><span>未绑定</span><a href="${ctx}/securityInfo/secInfo">立即绑定</a></c:otherwise>
							</c:choose>
					</li>
					<li>
						<label>邮箱地址：</label>
						<c:choose>
								<c:when test="${!empty userVerified and !empty userVerified.emailActive and userVerified.emailActive == 1 }"><span>${email}</span></c:when>
								<c:otherwise><span>未绑定</span><a href="${ctx}/securityInfo/secInfo">立即绑定</a></c:otherwise>
						</c:choose>
					</li>
					<li>
						<label>婚姻状况：</label>
						<span>
						<c:choose>
								<c:when test="${!empty userInfo and !empty userInfo.marriage }">${userInfo.marriage}</c:when>
								<c:otherwise>未填写</c:otherwise>
						</c:choose>
						</span>
					</li>
					<li>
						<label>居住地址：</label>
						<span>
						<c:choose>
								<c:when test="${!empty userInfo and !empty userInfo.address }">${userInfo.address}</c:when>
								<c:otherwise>未填写</c:otherwise>
						</c:choose>
						</span>
					</li>
					<li>
						<label>最高学历：</label>
						<span>
						<c:choose>
								<c:when test="${!empty userInfo and !empty userInfo.education }">${userInfo.education}</c:when>
								<c:otherwise>未填写</c:otherwise>
						</c:choose>
						</span>
					</li>
					<li>
						<label>所属行业：</label>
						<span>
						<c:choose>
								<c:when test="${!empty userInfo and !empty userInfo.industry }">${userInfo.industry}</c:when>
								<c:otherwise>未填写</c:otherwise>
						</c:choose>
						</span>
					</li>
					<li>
						<label>职位：</label>
						<span>
						<c:choose>
								<c:when test="${!empty userInfo and !empty userInfo.position }">${userInfo.position}</c:when>
								<c:otherwise>未填写</c:otherwise>
						</c:choose>
						</span>
					</li>
					<li>
						<label>月收入：</label>
						<span>
						<c:choose>
								<c:when test="${!empty userInfo and !empty userInfo.income }">${userInfo.income}</c:when>
								<c:otherwise>未填写</c:otherwise>
						</c:choose>
						</span>
					</li>
				</ul>						
				<div class="uc_paybtn uc_ib_space"><a href="${ctx}/userinfo/upinfo">修改信息</a></div>
			</div>			
		</div>
	</div>
</div>
<!--底部 -->
<%@ include file="../common/personfooter.jsp"%>
<%@ include file="../common/dsp.jsp"%>
</body>
</html>