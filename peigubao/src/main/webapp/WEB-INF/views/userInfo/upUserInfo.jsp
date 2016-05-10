<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp"%>
<%@include file="../common/import-artDialog-js.jspf"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="../common/commonkeyword.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/public.css">
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/uc.css?version=20150721"  />
<%-- <script language="javascript" src="${ctx}/static/script/common/jquery-1.8.0.min.js"></script> --%>
<script language="javascript" src="${ctx}/static/script/userInfo/userInfo.js?version=20150721"></script>
<%-- <script type="text/javascript"  src="${ctx}/static/js/uc.js"></script> --%>
</head>

<body>

<!--顶部 -->
<%@ include file="../common/personheader.jsp"%>

<!-- 浮动层 -->
<div class="floatlayer">
	<!-- 省市 -->
	<div class="uc_i_option uc_i_pov" style="display:none;">
		<a href="javascript:void(0)" data-id="-1">请选择省市</a>
		<c:if test="${!empty provinceList}">
			<c:forEach  items="${provinceList}" var="province" >
				<a href="javascript:void(0)" data-id="${province.id }">${province.title }</a>
			</c:forEach>
		</c:if>
	</div>
	<!-- 城市 -->
	<div class="uc_i_option uc_i_city" style="display:none;">
		<a href="javascript:void(0)" data-id="-1">请选择城市</a>
		<c:if test="${!empty cityList}">
			<c:forEach  items="${cityList}" var="city" >
				<a href="javascript:void(0)" data-id="${city.id }">${city.title }</a>
			</c:forEach>
		</c:if>
	</div>
	<!-- 最高学历 -->
	<div class="uc_i_option uc_i_edu" style="display:none;">
		<a href="javascript:void(0)" data-id="-1">请选择</a>
		<c:if test="${!empty educationList}">
			<c:forEach  items="${educationList}" var="education" >
				<a href="javascript:void(0)" data-id="${education.valueKey }">${education.valueName }</a>
			</c:forEach>
		</c:if>
	</div>
	<!-- 所属行业 -->
	<div class="uc_i_option uc_i_ids" style="display:none;">
		<a href="javascript:void(0)" data-id="-1">请选择</a>
		<c:if test="${!empty industryList}">
			<c:forEach  items="${industryList}" var="industry" >
				<a href="javascript:void(0)" data-id="${industry.valueKey }">${industry.valueName }</a>
			</c:forEach>
		</c:if>
	</div>
	<!-- 职位 -->
	<div class="uc_i_option uc_i_off" style="display:none;"> 
		<a href="javascript:void(0)" data-id="-1">请选择</a>
		<c:if test="${!empty positionList}">
			<c:forEach  items="${positionList}" var="position" >
				<a href="javascript:void(0)" data-id="${position.valueKey }">${position.valueName }</a>
			</c:forEach>
		</c:if>
	</div>
	<!-- 月收入 -->
	<div class="uc_i_option uc_i_income" style="display:none;">
		<a href="javascript:void(0)" data-id="-1">请选择</a>
		<c:if test="${!empty incomeList}">
			<c:forEach  items="${incomeList}" var="income" >
				<a href="javascript:void(0)" data-id="${income.valueKey }">${income.valueName }</a>
			</c:forEach>
		</c:if>
	</div>
</div>
<!-- 内容区 -->
<div class="uc">
	<!--个人中心导航 -->
	<%@ include file="../common/leftnav.jsp"%>
	<!--个人信息-->
	<div class="uc_mianbar">
		<div class="uc_info">	
			<c:choose>
				<c:when test="${!empty userInfo}">
					<input type="hidden" class=userInfoId  value="${userInfo.id}"> 	
				</c:when>
				<c:otherwise>
					<input type="hidden" class=userInfoId  value=""> 	
				</c:otherwise>
			</c:choose>	
			<h3><i>个人信息</i></h3>
			<div class="uc_i_list2">
				<ul>
					<li>
						<label>用户名：</label>
						<span>${mobile}</span>
					</li>
					<li>
						<label>婚姻状况：</label>
						<c:if test="${!empty marriageList}">
							<c:forEach  items="${marriageList}" var="marriage" >
								<c:choose>
									<c:when test="${!empty userInfo && !empty userInfo.marriage && marriage.valueKey == userInfo.marriage}">
										<input name="marriage" type="radio" class="marriage" checked="checked" data-value="${marriage.valueKey }">
										<i>${marriage.valueName }</i>
									</c:when>
									<c:otherwise>
										<c:choose>
											<c:when test="${(empty userInfo || empty userInfo.marriage) && marriage.valueKey == 0}">
												<input name="marriage"  type="radio" class="marriage" checked="checked" data-value="${marriage.valueKey }">
											</c:when>
											<c:otherwise>
												<input name="marriage"  type="radio" class="marriage"  data-value="${marriage.valueKey }">
											</c:otherwise>
										</c:choose>
										<i>${marriage.valueName }</i>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</c:if>
					</li>
					<li>
						<label>居住地址：</label>
						<c:choose>
							<c:when test="${!empty userInfo && !empty userInfo.province && !empty provinceList}">
								<c:forEach items="${provinceList}" var="province" varStatus="status">
									<c:if test="${province.id == userInfo.province}">
										<input type="text" class="uc_i_sel uc_i_slpov" data-id="${province.id }" value="${province.title }" disabled=true />
									</c:if>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<input type="text" class="uc_i_sel uc_i_slpov" data-id="-1" value="请选择省市" disabled=true />
							</c:otherwise>
						</c:choose>
						<a href="javascript:void(0)" class="uc_i_ipdown uc_i_ippov"></a>
						<c:choose>
							<c:when test="${!empty userInfo && !empty userInfo.city && !empty cityList}">
								<c:forEach items="${cityList}" var="city" varStatus="status">
									<c:if test="${city.id == userInfo.city}">
										<input type="text" class="uc_i_sel uc_i_slcity" data-id="${city.id }" value="${city.title }" disabled=true />
									</c:if>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<input type="text" class="uc_i_sel uc_i_slcity" data-id="-1" value="请选择城市" disabled=true  />
							</c:otherwise>
						</c:choose>
						<a href="javascript:void(0)" class="uc_i_ipdown uc_i_ipcity"></a>
						<c:choose>
							<c:when test="${!empty userInfo && !empty userInfo.address}">
								<input type="text" class="uc_i_ip address" value="${userInfo.address}">
							</c:when>
							<c:otherwise><input type="text" class="uc_i_ip address"></c:otherwise>
						</c:choose>
						<a href="javascript:void(0)" class="uc_i_close cleanAddress"></a>
					</li>
					<li>
						<label>最高学历：</label>
						<c:choose>
							<c:when test="${!empty userInfo && !empty userInfo.industry && !empty educationList}">
								<c:forEach items="${educationList}" var="education" varStatus="status">
									<c:if test="${education.valueKey == userInfo.education}">
										<input type="text" class="uc_i_sel uc_i_sel2 uc_i_sledu" data-id="${education.valueKey }" value="${education.valueName }" disabled=true />
									</c:if>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<input type="text" class="uc_i_sel uc_i_sel2 uc_i_sledu " data-id="-1" value="请选择" disabled=true   />
							</c:otherwise>
						</c:choose>
						<a href="javascript:void(0)" class="uc_i_ipdown uc_i_ipedu"></a>
					</li>
					<li>
						<label>所属行业：</label>
						<c:choose>
							<c:when test="${!empty userInfo && !empty userInfo.industry && !empty industryList}">
								<c:forEach items="${industryList}" var="industry" varStatus="status">
									<c:if test="${industry.valueKey == userInfo.industry}">
										<input type="text" class="uc_i_sel uc_i_sel2 uc_i_slids" data-id="${industry.valueKey }" value="${industry.valueName }" disabled=true />
									</c:if>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<input type="text" class="uc_i_sel uc_i_sel2 uc_i_slids" data-id="-1"  value="请选择"  disabled=true    />
							</c:otherwise>
						</c:choose>
						<a href="javascript:void(0)" class="uc_i_ipdown uc_i_ipids"></a>
					</li>
					<li>
						<label>职位：</label>
						<c:choose>
							<c:when test="${!empty userInfo && !empty userInfo.industry && !empty positionList}">
								<c:forEach items="${positionList}" var="position" varStatus="status">
									<c:if test="${position.valueKey == userInfo.position}">
										<input type="text" class="uc_i_sel uc_i_sel2 uc_i_sloff" data-id="${position.valueKey }" value="${position.valueName }" disabled=true />
									</c:if>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<input type="text" class="uc_i_sel uc_i_sel2 uc_i_sloff" data-id="-1" value="请选择"  disabled=true    />
							</c:otherwise>
						</c:choose>
						<a href="javascript:void(0)" class="uc_i_ipdown uc_i_ipoff"></a>
					</li>
					<li>
						<label>月收入：</label>
						<c:choose>
							<c:when test="${!empty userInfo && !empty userInfo.income && !empty incomeList}">
								<c:forEach items="${incomeList}" var="income" varStatus="status">
									<c:if test="${income.valueKey == userInfo.income}">
										<input type="text" class="uc_i_sel uc_i_sel2 uc_i_slicm" data-id="${income.valueKey }" value="${income.valueName }" disabled=true />
									</c:if>
								</c:forEach>
							</c:when>
							<c:otherwise>
								<input type="text" class="uc_i_sel uc_i_sel2 uc_i_slicm" data-id="-1" value="请选择"  disabled=true   />
							</c:otherwise>
						</c:choose>
						<a href="javascript:void(0)" class="uc_i_ipdown uc_i_ipicm"></a>
					</li>
				</ul>
				<div class="uc_i_btn">
					<div class="uc_paybtn uc_ib2_space"><a status="true" href="javascript:void(0)" class="upUserInfo">修改信息</a></div>
					<div class="uc_i_btn2" id="cancel"><a href="${ctx}/userinfo/info">取消</a></div>
				</div>
			</div>
		</div>
	</div>
</div>
<!--底部 -->
<%@ include file="../common/personfooter.jsp"%>
<%@ include file="../common/dsp.jsp"%>
</body>
</html>