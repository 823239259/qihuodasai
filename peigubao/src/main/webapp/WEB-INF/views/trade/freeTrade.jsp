<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp"%>
<%@include file="../common/import-artDialog-js.jspf"%>
<html xmlns="http://www.w3.org/1999/xhtml" lang="zh">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>免息操盘,配股宝</title>
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/free.css">
<script language="javascript" src="${ctx}/static/script/trade/freeTrade.js?v=${v}"></script>
<script type='text/javascript' src="${ctx}/static/script/tzdr.js?v=${v}"></script>
</head>
<body>
	<!--顶部 -->
	<%@ include file="../common/personheader.jsp"%>
	
	<!-- 免息操盘 -->
	<form method="POST" id="bespokeTradeForm" name="bespokeTradeForm" action="${ctx}/toBespokeTrade">
		<input type="hidden" id="tradeConfigId" name="tradeConfigId"/>
		<input type="hidden" id="tradeStart" name="tradeStart"/>
		<input type="hidden" id="lever" name="lever"/>
		<input type="hidden" id="capitalMargin" name="capitalMargin"/>
		<input type="hidden" id="borrowPeriod" name="borrowPeriod"/>
		<input type="hidden" id="interest" name="interest"/>
		<input type="hidden" id="expenese" name="expenese"/>
		 <c:forEach items="${trades}" var="trade">
		    <input type="hidden" id="interest${trade.id}" value="${trade.interest}"/>
		    <input type="hidden" id="expenese${trade.id}" value="${trade.expenese}"/>
		    <input type="hidden" id="min_lever${trade.id}" />
		   	<input type="hidden" id="max_lever${trade.id}" />
		   	<div class="free">
			    <div class="free_title">
			        <i></i>
			        <h2>${trade.name}</h2>
			    </div>
			    <div class="free_chose">
			        <ul class="free_c_list">
			            <li>
			                <h3>操盘保证金</h3>
			                <span>
			                   <!--  <input type="text"/>元<b>50万—600万</b> -->
			                    
			                    <c:choose>
					                <c:when test="${ empty  trade.minmoneystr}">
						                 <input type="text" class="inputno color" id="money${trade.id}" 
						                 onfocus="moneyValueFocus(this,'${trade.id}','<fmt:formatNumber value="${trade.minBond}" pattern="#" />','<fmt:formatNumber value="${trade.maxBond}" pattern="#" />');" 
						                 onblur="resetmoeny(this,${trade.minBond},${trade.maxBond},'${trade.id}','','')"
						                 minmoney="${trade.minBond}"  maxmoney="${trade.maxBond}"  idstr="${trade.id}"  
						                 minMultiple="${trade.minMultiple}"  maxMultiple="${trade.maxMultiple}"  
						                 minDuration="${trade.minDuration}"  maxDuration="${trade.maxDuration}"  
						                 interest="${trade.interest}"  expenese="${trade.expenese}"  
						                 value="<fmt:formatNumber value="${trade.minBond}" pattern="#" />—<fmt:formatNumber value="${trade.maxBond}" pattern="#" />"> 元
					                </c:when>
					                <c:otherwise>
						                 <input type="text" class="inputno color" id="money${trade.id}" 
						                 onfocus="moneyValueFocus(this,'${trade.id}','<fmt:formatNumber value="${trade.minBond}" pattern="#" />','<fmt:formatNumber value="${trade.maxBond}" pattern="#" />');" 
						                 onblur="resetmoeny(this,${trade.minBond},${trade.maxBond},'${trade.id}','${trade.minmoneystr}','${trade.maxmoneystr}')"
						                 minmoney="${trade.minBond}"  maxmoney="${trade.maxBond}"  idstr="${trade.id}"  
						                 minMultiple="${trade.minMultiple}"  maxMultiple="${trade.maxMultiple}"  
						                 minDuration="${trade.minDuration}"  maxDuration="${trade.maxDuration}"  
						                 interest="${trade.interest}"  expenese="${trade.expenese}"  
						                 value="${trade.minmoneystr}—${trade.maxmoneystr}"> 元
					                 </c:otherwise>
				                </c:choose>
			                </span>
			            </li>
			            <li>
			                <h3>操盘倍数</h3>
			                <span>
			                	<c:choose>
			                		<c:when test="${trade.minMultiple==trade.maxMultiple}">
			                			<span><i>${trade.maxMultiple}倍</i></span>
			                		</c:when>
			                		<c:otherwise>
			                			<select id="pzmultiple${trade.id}" class="pzmultiple_select" iddata="${trade.id}"
				                			minBond="${trade.minBond}" maxBond="${trade.maxBond}" 
				                			minMultiple="${trade.minMultiple}" maxMultiple="${trade.maxMultiple}" 
				                			minDuration="${trade.minDuration}" maxDuration="${trade.maxDuration}" 
				                			interest="${trade.interest}" expenese="${trade.expenese}" type="1">
			                				<option value="请选择">请选择</option>
					                    	<c:forEach var="multiple"  begin="${trade.minMultiple}" end="${trade.maxMultiple}">
					                    		<option value="${multiple}">${multiple}</option>
					                    	</c:forEach>
					                    </select> 倍
			                		</c:otherwise>
			                	</c:choose>
			                </span>
			            </li>
			            <li>
			                <h3>最长操盘时间</h3>
			                <span>
			                	<c:choose>
			                		<c:when test="${trade.minDuration==trade.maxDuration}">
			                			<span><i>${trade.minDuration}天</i></span>
			                		</c:when>
			                		<c:otherwise>
			                			<select id="daymultiple${trade.id}" class="daymultiple_select" iddata="${trade.id}" 
				                			minBond="${trade.minBond}" maxBond="${trade.maxBond}" 
				                			minMultiple="${trade.minMultiple}" maxMultiple="${trade.maxMultiple}" 
				                			minDuration="${trade.minDuration}" maxDuration="${trade.maxDuration}" 
				                			interest="${trade.interest}" expenese="${trade.expenese}" type="2">
			                				<option value="请选择">请选择</option>
			                				<c:forEach var="days"  begin="${trade.minDuration}" end="${trade.maxDuration}">
												<option value="${days}">${days}</option>
											</c:forEach>
					                    </select> 天
			                		</c:otherwise>
			                	</c:choose>
			                 </span>
			            </li>
			            <li>
			                <h3>最短操盘时间</h3>
			                <span><i>${trade.shortestDuration}</i>个交易日</span>
			            </li>
			            <li>
			                <h3>总操盘资金</h3>
			                <span id="zcpj${trade.id}">--元</span>
			            </li>
			            <li>
			                <h3>操盘利息</h3>
			                <c:choose>
		                		<c:when test="${trade.interest==0}">
		                			<span><i>免费</i></span>
		                		</c:when>
		                		<c:otherwise>
		                			<span id="lx${trade.id}">--元</span>
		                		</c:otherwise>
			                </c:choose>
			            </li>
			            <li class="last">
			                <h3>账户管理费</h3>
			                <c:choose>
		                		<c:when test="${trade.expenese==0}">
		                			<span><i>免费</i></span>
		                		</c:when>
		                		<c:otherwise>
		                			<span id="pzglf${trade.id}">--元/交易日<em id="pzglfyj${trade.id}"><c:if test="${trade.expenese lt 100}">(原 -- 元/交易日)</c:if></em></span>
		                		</c:otherwise>
		                	</c:choose>
			            </li>
			        </ul>    
			        <div class="free_c_agree">
			            <input type="checkbox" id="cpxy${trade.id}"/><span>我已阅读并同<a href="javascript:tradeContract();">《股票操盘合作协议》</a></span>
			            <a href="javascript:void(0)" class="btn" onclick="javascript:toBespokeTrade('${trade.id}',${trade.minBond},${trade.maxBond},${trade.minMultiple},${trade.maxMultiple},${trade.minDuration},${trade.maxDuration})">提交操盘申请</a>
			        </div> 
			    </div>
			</div>
		 </c:forEach>
	</form>
	<%@ include file="../common/personfooter.jsp"%>
</body>
</html>