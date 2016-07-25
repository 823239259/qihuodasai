<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.tzdr.common.utils.ConfUtil"%>
<%@ include file="../common/common.jsp"%>
<%@include file="../common/import-artDialog-js.jspf"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%-- <%@ include file="../common/commonkeyword.jsp"%> --%>
<title>A股方案详情 - 配股宝</title>
<link href="${ctx}/static/css/pagination.css" rel="stylesheet" type="text/css" />
<link href="${ctx}/static/css/uc.css?v=${v}"rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${ctx}/static/script/tzdr.js"></script>
<script src="${ctx}/static/script/common/jquery.pagination.js" type="text/javascript"></script>
<script type="text/javascript" src="${ctx}/static/script/common/dateUtils.js"></script>
<script type="text/javascript" charset="utf-8" src="http://static.bshare.cn/b/buttonLite.js#style=-1&amp;uuid=fc88a5c2-48c9-4d9d-9c01-ec40b668977f&amp;pophcol=2&amp;lang=zh"></script>
<script type="text/javascript" charset="utf-8" src="http://static.bshare.cn/b/bshareC2P.js"></script>
<script type="text/javascript"></script>
</head>
<body>
	<!--顶部 -->
	<%@ include file="../common/personheader.jsp"%>
	<div class="floatlayer">
		<div class="fl_mask" style="display:none;"></div>
		<!-- 追加保证金 -->
		<div class="fl_layer" style="display:none;" id="addTradeMoneyBox">
			<div class="fl_navtitle">
				<h3>申请追加保证金</h3>
				<a href="javascript:void(0);" onclick="PGB.closeWindow('.fl_layer',null)" class="close"></a>
			</div>
			<div class="fl_l_main">
				<ul class="fl_l_list">
					<li><label>账户余额：</label>
					<input type="hidden" id="balance" value="${balance}" />
					<span><i><fmt:formatNumber value="${balance}"  type="number" minFractionDigits="2"  ></fmt:formatNumber></i>元</span>
					<a href="${ctx}/pay/payinfo" target="_blank">去充值>></a></li>
					<li>
						<input id="additionalMargin" type="hidden" value="${trade.activityType}" />
						<input id="minAddMoney" type="hidden" value="${minAddMoney}" />
						<input id="maxAddMoney" type="hidden" value="${maxAddMoney}" />
						<label>追加金额：<input id="addMoney" type="text" maxlength="9" value="${minAddMoney}" /><span>元</span></label>
					</li>
				</ul>
			</div>
			<div class="fl_l_btn"><a href="javascript:void(0);" onclick="PGB.closeWindow('.fl_layer',null)" class="cancel">取消</a><a href="javascript:void(0);" id="sure" class="sure">确定</a></div>
		</div>
		<!-- 申请追加保证金2 -->
		<div class="fl_layer" style="display:none;" id="addTradeMoneyAlert">
			<div class="fl_navtitle">
				<h3>申请追加保证金</h3>
				<a href="javascript:void(0);" onclick="PGB.closeWindow('.fl_layer',null)" class="close"></a>
			</div>
			<div class="fl_l_main">
				<p class="fl_l_promt" style="margin-bottom:20px;">一，系统会先扣账户余额，再追加到A股交易账户中，追加成功后会以短信提示您；</p>
				<p class="fl_l_promt">二，交易时间，10分钟内完成追加；非交易时间，下个交易日开盘前完成追加。</p>
				<p class="fl_l_surefont">请确认是否申请追加？</p>
			</div>
			<div class="fl_l_btn"><a href="javascript:void(0);" onclick="PGB.closeWindow('.fl_layer',null)" class="cancel">取消</a><a status="true" href="javascript:void(0);" id="sure" class="sure">确定</a></div>
		</div>
		<!-- 提示 -->
		<div class="fl_layer" style="display:none;" id="endOfProgramAlert">
			<div class="fl_navtitle">
				<h3>提示</h3>
				<a href="javascript:void(0);" onclick="PGB.closeWindow('.fl_layer',null)" class="close"></a>			
			</div>
			<div class="fl_l_main">
				<p class="fl_l_freefont" id="alertContent">您还有1天利息，中途终止操盘，剩余利息将不予退还</p>
				<p class="fl_l_surefont">请确认终止操盘？</p>
			</div>		
			<div class="fl_l_btn"><a href="javascript:void(0);" onclick="PGB.closeWindow('.fl_layer',null)" class="cancel">取消</a><a href="javascript:void(0);" status="true" id="sure" class="sure">确定</a></div>
		</div>
	</div>
	<div class="AGuDetail">
		<!--个人中心导航 -->
		<%@ include file="../common/leftnav.jsp"%>
		<input type="hidden" id="group_id" value="${trade.groupId}" />
		<div class="AGuDetailRight">
			<c:choose>
				<c:when test="${trade.status eq 0}">
					<!-- 正在开户中 -->
					<div class="AGuDetailRightTop">
						<h3>正在开户中...</h3>
						<div class="AGuDetailRightTop1">
							<p>
								<a href="javascript:void(0);" id="a1"><img src="${ctx }/static/images/agu/zhuajia2.png" /></a> 
								<a href="javascript:void(0);" id="a2"><img src="${ctx }/static/images/agu/zongzhi2.png" /></a>
							</p>
						</div>
						<div class="AGuDetailRightTop2">
							<p>
								交易账户：<em>********</em>
							</p>
							<p>
								交易密码：<em>******</em>(为了您的资金安全,请妥善保管好密码)
							</p>
							<p>
								交易软件：<a href="${ctx}/help?status=3">请点击这里按照说明安装交易软件</a>
							</p>
						</div>
					</div>
				</c:when>
				<c:when test="${trade.status eq 1}">
					<!-- 正在操盘中 -->
					<div class="AGuDetailRightTop">
						<h3>正在操盘中...</h3>
						<div class="AGuDetailRightTop1">
							<p>
								<a href="javascript:void(0);" id="a1" class="addTradeMoney" data-feeType="${trade.feeType}"><img src="${ctx }/static/images/agu/zhuijia.png" /></a> 
								<a href="javascript:void(0);" class="stopTradeBtn" onclick="stopBtnFun('${trade.feeType}','${trade.groupId}')" id="a2" data-feeType="${trade.feeType}"><img src="${ctx }/static/images/agu/zongzhi.png" /></a>
							</p>
						</div>
						<div class="AGuDetailRightTop2">
							<p>
								交易账户:<em>${trade.account}</em>
							</p>
							<p>
								交易密码:<em>${trade.password}</em>(为了您的资金安全,请妥善保管好密码)
							</p>
							<p>
								交易软件:<a href="${ctx}/help?status=3">请点击这里按照说明安装交易软件</a>
							</p>
						</div>
					</div>
				</c:when>
				<c:otherwise>
					<c:choose>
						<c:when test="${trade.auditStatus eq 2}">
							<!-- 开户失败 -->
							<div class="AGuDetailRightTop">
								<h3>开户失败！</h3>
								<div class="AGuDetailRightTop1">
									<p>
										<a href="javascript:void(0);" id="a1"><img src="${ctx }/static/images/agu/zhuajia2.png" /></a> 
								<a href="javascript:void(0);" id="a2"><img src="${ctx }/static/images/agu/zongzhi2.png" /></a>
									</p>
								</div>
								<div class="AGuDetailRightTop2">
									<p>
										交易账户:<em>********</em>
									</p>
									<p>
										交易密码:<em>******</em>(为了您的资金安全,请妥善保管好密码)
									</p>
									<p>
										交易软件:<a href="${ctx}/help?status=3">请点击这里按照说明安装交易软件</a>
									</p>
								</div>
							</div>
						</c:when>
						<c:otherwise>
							<!-- 方案已终结 -->
							<div class="AGuDetailRightTop">
								<h3>方案已终结</h3>
								<div class="AGuDetailRightTop1">
									<p>结算金额：<fmt:formatNumber value="${trade.finishedMoney}" type="number" minFractionDigits="2"  ></fmt:formatNumber>元</p>
								</div>
								<div class="AGuDetailRightTop2">
									<c:if test="${trade.totalAccrual ge 0}">
										<p style="height: 70px;line-height: 70px;">盈利金额：<i class="color4 ">+<fmt:formatNumber value="${trade.totalAccrual}"  type="number" minFractionDigits="2"  ></fmt:formatNumber></i>元</p>
									</c:if>
									<c:if test="${trade.totalAccrual lt 0}">
										<p style="height: 70px;line-height: 70px;">亏损金额：<i class="color3"><fmt:formatNumber value="${trade.totalAccrual}"  type="number" minFractionDigits="2"  ></fmt:formatNumber></i>元</p>
									</c:if>
								</div>
							</div>
						</c:otherwise>
					</c:choose>
				</c:otherwise>
			</c:choose>
			<div class="AGuDetailRightMain">
				<h4>操盘资金</h4>
				<table cellpadding="0" cellspacing="0">
					<tr>
						<td id="td1">总操盘资金</td>
						<td id="td2">操盘保证金</td>
						<td id="td3">追加保证金</td>
						<td id="td4">融资金额</td>
					</tr>
					<tr>
						<td id="td5"><em><fmt:formatNumber value="${trade.totalOperateMoney}" type="number" minFractionDigits="0"></fmt:formatNumber></em>元</td>
						<td id="td6"><em><fmt:formatNumber value="${trade.totalLeverMoney}" type="number" minFractionDigits="0"></fmt:formatNumber></em>元</td>
						<td id="td7"><em><fmt:formatNumber value="${trade.totalAppendLeverMoney}" type="number" minFractionDigits="0"></fmt:formatNumber></em>元</td>
						<td id="td8"><em><fmt:formatNumber value="${trade.totalLending}" type="number" minFractionDigits="0"></fmt:formatNumber></em>元</td>
					</tr>
				</table>
				<h4>
					操盘规则<span>${operatorsInfo}</span><span class="tdlimit"><a href="${ctx}/help?status=1#list" target="_blank">今日限制股</a></span>
				</h4>
				<table cellpadding="0" cellspacing="0">
					<tr>
						<td id="td1">亏损补仓线</td>
						<td id="td2">亏损平仓线</td>
						<td id="td3">总操盘费用</td>
					</tr>
					<tr>
						<td id="td5"><em><fmt:formatNumber value="${trade.warning}" type="number" minFractionDigits="0"></fmt:formatNumber></em>元</td>
						<td id="td6"><em><fmt:formatNumber value="${trade.open}" type="number" minFractionDigits="0"></fmt:formatNumber></em>元</td>
						<td id="td7"><em><fmt:formatNumber value="${money}" type="number" minFractionDigits="0"></fmt:formatNumber></em>元</td>
					</tr>
				</table>
				<table id="secondT" cellpadding="0" cellspacing="0">
					<tr>
						<td id="td1">申请操盘月数</td>
						<td id="td2">已操盘天数</td>
						<td id="td3">操盘开始日期</td>
						<td id="td4">预计结束日期</td>
					</tr>
					<tr>
						<td id="td5">${tradeMonth}个月</td>
						<td id="td6">${useDays}天</td>
						<td id="td7">${trade.starttimeString}</td>
						<td id="td8">${trade.estimateEndtimeString}</td>
					</tr>
				</table>
				
				
				<c:choose>
				<c:when test="${trade.status eq 01}">
				<table style="margin-top:-1px;"  cellpadding="0" cellspacing="0">
					<tr>
						<td id="td1">自动延期</td>
						<td id="td2">手动延期</td>

					</tr>
					<tr>
						<td id="td5" style="text-align:left;">
						<span style="font-size:12px; text-align:left; margin-left:8px;">
						1.方案到期日8:20，系统会自动延期并扣取相应操盘费用;
						</span>
						<br>
						<span style="font-size:12px; text-align:left; margin-left:8px;">
						2.若余额不足，自动延期失败，系统将在当天14:30开始平仓并终结方案。
						</span>
						</td>
						<td id="td6"> 
							<a href="javascript:void(0);" id="a1" class="addTradeMonth" data-feeType="${trade.feeType}"><img src="${ctx }/static/images/agu/zhuijiayue.png" /></a> 
						</td>
					</tr>
				</table>
				</c:when>
				<c:otherwise>
				<table  style="margin-top:-1px;"  cellpadding="0" cellspacing="0">
					<tr>
						<td id="td1">自动延期</td>
						<td id="td2">手动延期</td>

					</tr>
					<tr>
						<td id="td5" style="text-align:left;">
						<span style="font-size:12px; text-align:left; margin-left:8px;">
						1.方案到期日8:20，系统会自动延期并扣取相应操盘费用;
						</span>
						<br>
						<span style="font-size:12px; text-align:left; margin-left:8px;">
						2.若余额不足，自动延期失败，系统将在当天14:30开始平仓并终结方案。
						</span>
						</td>
						<td id="td6"> 
							<a href="javascript:void(0);" "><img src="${ctx }/static/images/agu/bunengzuijiayue.png" /></a> 
						</td>
					</tr>
				</table> 
				</c:otherwise>
				</c:choose>
				<h4>成本支出</h4>
				<div class="agufreelist">					
					<table id="fee" cellpadding="0" cellspacing="0">
					</table>
				</div>
			</div>
		</div>
	</div>
	
	
	
	
	
	
			<div class="fl_layer" style="display:none;" id="addTradeMonthBox">
			<div class="fl_navtitle">
				<h3>申请追加月数</h3>
				<a href="javascript:void(0);" onclick="PGB.closeWindow('.fl_layer',null)" class="close"></a>
			</div>
			<div class="fl_l_main">
				<ul class="fl_l_list">
					<li><label style="float:left; font-size:16px; margin-left:96px;">追加月数：</label>
					<select id="monthNum" name="monthNum" onchange="getMoney()" style="width:80px;line-height:26px; border:1px solid #e5e5e5;margin-top:8px; height:26px;" >
					<!-- 	<option var = "">请选择</option> -->
						<c:forEach var="month" items="${months}"  >
							<option value="${month}">${month}个月</option>
						</c:forEach>
					</select>
					</li>
					<li><label style="margin-left:96px; font-size:16px;">账户余额：</label>
					<input type="hidden" id="balance" value="${balance}" />
					<span style="font-size:16px;"><i><fmt:formatNumber value="${balance}"  type="number" minFractionDigits="2"  ></fmt:formatNumber></i>元</span>
					<li>
						<label style="margin-left:96px; font-size:16px;">支付金额：</label>
						<span style="font-size:16px;">
						<label style="font-size:16px;" id = "totalFee"></label>
						</span>
					</li>
				</ul>
			</div>
			<div class="fl_l_nomoney" id="notEnoughDiv" style="width:170px; text-align:center; height:26px; line-height:26px; font-size:12px; color:#fff; background-color:#ff7e00; margin:8px auto 15px; display:none">
					<span>余额不足，</span><a style="color:#fff;text-decoration:underline;" href="${ctx}/pay/payinfo">请立即充值！</a>
				</div>
			<div class="fl_l_btn"><a href="javascript:void(0);" onclick="PGB.closeWindow('.fl_layer',null)" class="cancel">取消</a><a href="javascript:void(0);" id="sure1" class="sure">确认支付</a></div>
		</div>
<%@ include file="../common/personfooter.jsp"%>
<%@ include file="../common/dsp.jsp"%>
<script type="text/javascript" src="${ctx}/static/script/monthTrade/tradeDetail.js?v=${v}"></script>
</body>
</html>

