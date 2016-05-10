<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.tzdr.common.utils.ConfUtil"%> 
<%@ include file="../common/common.jsp"%>
   <%@include file="../common/import-artDialog-js.jspf"%>
<html xmlns="http://www.w3.org/1999/xhtml" lang="zh">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%-- <%@ include file="../common/commonkeyword.jsp"%> --%>
<title>港股方案详情 - 配股宝</title>
 <link rel="stylesheet" href="${ctx}/static/css/uc.css?version=${v}"  type="text/css">
 <link rel="stylesheet" type="text/css" href="${ctx}/static/css/public.css">
 <link href="${ctx}/static/css/public.css" rel="stylesheet" type="text/css">
 <link href="${ctx}/static/css/pagination.css" rel="stylesheet" type="text/css" /> 	
<script type="text/javascript" src="${ctx}/static/script/tzdr.js"></script>
 <script src="${ctx}/static/script/common/jquery.pagination.js" type="text/javascript"></script>
 <script type="text/javascript" src="${ctx}/static/script/common/dateUtils.js"></script> 
 <script type="text/javascript" src="${ctx}/static/script/hkstock/tradeDetail.js?v=${v}"></script>
</head>
<body>

<!--顶部 -->
<%@ include file="../common/personheader.jsp"%>
<!-- 浮动框 -->
<div class="floatlayer">
	
	<!-- 终止操盘 -->
	<div class="uc_tdfl_stop" style="display:none;top: 108px;left: 160px;">
		<p class="uc_tdfl_font">请将您的全部股票清仓后，进行终结方案！</p>
		<i class="uc_tdfl_icon"></i>
	</div>
	<!-- 终止操盘  提示框-->
	<div class="fl_box fl_uc_stops" style="display:none;" id="endOfProgramAlert">		
		<div class="fl_navtitle">
			<h3>终止操盘</h3>
			<a href="javascript:void(0)" class="close"></a>
		</div>
		<div class="fl_uc_main">
			<p class="fl_uc_stopfont">你确认要终结方案么？</p>
		</div>
		<div class="fl_uc_btn">
			<a href="javascript:void(0)" class="fl_uc_cancelbtn" style="margin:auto;float:none;">确认</a>
		</div>
	</div>	
	<!-- 终止操盘 确认框 -->
	<div class="fl_box fl_uc_trade sif_money_div" id="applyHKEndTrade" name="applyHKEndTrade" style="display:none;" >
		<div class="fl_navtitle">
			<h3>终止操盘</h3>
			<a href="javascript:void(0);" onclick="closeWindow('#applyHKEndTrade')" class="close"></a>
		</div>
		<div class="fl_uc_main">						
			<p style="font-size:14px;text-align: center;font-family: '微软雅黑'; margin-top:10px;">因港股结算时间为T+2，方案终结后的结算金额在两个港股交易日后返回到您的账户余额。</p>
			<p id="f_parities" style="font-size:14px;text-align: center;font-family: '微软雅黑'; margin-top:10px;color:#FF0000;" >当前汇率：1港元 = <i id="parities">0.8228</i>人民币</p>
			<p style="font-size:14px;text-align: center;font-family: '微软雅黑'; margin-top:10px;">若您的股票账户仍有股票未卖出，您的终结申请将不能通过审核。请问是否确认终止操盘？</p>
		</div>
		<div class="fl_uc_btn">
			<a href="javascript:void(0);" status="true" id="applyHKEndTradeBtn" data_no="" data_tds="" name="applyHKEndTradeBtn" class="fl_uc_surebtn">确定</a>
			<a href="javascript:void(0);" id="hkEndTrade_canceBtn" name="hkEndTrade_canceBtn" class="fl_uc_cancelbtn">取消</a>
		</div>
	</div>	
	
	<!-- 遮罩层 -->
		<div class="fl_mask" style="display:none;"></div>
		<!-- 追加保证金弹出框 -->
		<div class="fl_box fl_uc_trade" style="display:none;">
			<div class="fl_navtitle">
				<h3>追加保证金</h3>
				<a href="javascript:void(0);" class="close"></a>
			</div>
			<div class="fl_uc_main">
				<ul class="fl_uc_list">
					
				</ul>
			</div>
			<div class="fl_uc_btn">
				<a href="javascript:void(0);" class="fl_uc_cancelbtn">取消</a>
				<a id="addBondNext" href="javascript:void(0);" class="fl_uc_surebtn">下一步</a>
			</div>
		</div>
		<!-- 追加保证金弹出确认框 -->
		<div class="fl_box fl_uc_trade2" style="display: none;">
			<div class="fl_navtitle">
				<h3>追加保证金</h3>
				<a href="javascript:void(0);" class="close"></a>
			</div>
			<div class="fl_uc_main">
				<p style="padding: 8px 16px;font-size:14px;text-align: left;font-family: '宋体'; line-height: 24px;">一，系统会先扣账户余额，再追加到港股交易账户中，追加成功后会以短信提示您；</p>
				<p style="padding: 8px 16px;font-size:14px;text-align: left;font-family: '宋体'; line-height: 24px;">二，交易时间，10分钟内完成追加；非交易时间，下个交易日开盘前完成追加。</p>
				<p style="padding: 16px;font-size:14px;text-align: center;font-family: '宋体'; line-height: 24px;">请确认是否追加？</p>
			</div>
			<div class="fl_uc_btn">
				<a href="javascript:void(0);" class="fl_uc_cancelbtn">取消</a>
				<a id="addBond" href="javascript:void(0);" class="fl_uc_surebtn">确认</a>
			</div>
		</div>
</div>
<div class="uc">
	<!--个人中心导航 -->
	<%@ include file="../common/leftnav.jsp"%>
	<div class="uc_mianbar" style="background:#fff;">
		<div class="uc_td_main">
			<!-- 开户中 -->
			<c:if test="${trade.status eq 0}">   
				<div class="uc_td_trade" style="display:block;">
					<h3>正在开户中</h3>
				</div>
			</c:if>
			<!-- 操盘中  -->
			<c:if test="${trade.status eq 1}">   
				<div class="uc_td_trade" style="display:block;">
					<h3>正在操盘中</h3>
					<div class="uc_td_tdbtn">
						<a href="javascript:void(0)"  class="ul_td_addBondbtn" style="display:block; float:none; margin:80px auto;">追加保证金</a>
						<a href="javascript:void(0)"  class="ul_td_stopbtn" style="display:block; float:none; margin:80px auto;">终止操盘</a>
					</div>
				</div>
			</c:if>
			<!-- 已完结  -->
			<c:if test="${trade.status eq 2}">
				<div class="uc_td_tradestop">
					<div class="uc_tds_title" style="height:235px">
						<h3>方案已完结</h3>
						<span><i>结算金额</i></span>
						<p><em><fmt:formatNumber value="${trade.finishedMoney}" type="number" minFractionDigits="2"  ></fmt:formatNumber></em>港元</p>
						<span><i>操盘盈亏</i></span>
						<p>
						  <em>
							  <c:choose>
							  	<c:when test="${trade.finishedMoney - trade.totalOperateMoney ge 0}">
							  		<i>+<fmt:formatNumber value="${trade.finishedMoney-trade.totalOperateMoney}" type="number" minFractionDigits="2"  ></fmt:formatNumber></i>
							  	</c:when>
							  	<c:otherwise>
							  		<i><fmt:formatNumber value="${trade.finishedMoney-trade.totalOperateMoney}" type="number" minFractionDigits="2"  ></fmt:formatNumber></i>
							  	</c:otherwise>
							  </c:choose>
						  </em>港元
						</p>
						<span><i>结算汇率</i></span>
						<p><em>1港元=${trade.endExchangeRate}人民币元</em></p>
					</div>
					<c:if test="${trade.totalAccrual ge 0}">
						<div class="uc_tds_money" style="margin-top:-20px">
							<h3>投资盈利</h3>
							<p><i>+<fmt:formatNumber value="${trade.totalAccrual}"  type="number" minFractionDigits="2"  ></fmt:formatNumber></i>元</p>
						</div>
					</c:if>
					<c:if test="${trade.totalAccrual lt 0}">
						<div class="uc_tds_money_kui" style="margin-top:-20px">
							<h3>投资亏损</h3>
							<p><i><fmt:formatNumber value="${trade.totalAccrual}"  type="number" minFractionDigits="2"  ></fmt:formatNumber></i>元</p>
						</div>
					</c:if>
				</div>
			</c:if>
			
			<!-- 操盘规则 -->
			<div class="uc_td_rule">
				<h3><em>操盘规则</em><i id="group_id">${trade.groupId}</i></h3>
				<ul class="ul_td_rulenum">
					<li class="ul_td_ruletotal" style="width:170px;">
						<span>总操盘资金(HK$)</span>
						<p><i><fmt:formatNumber value="${trade.totalOperateMoney}" type="number" minFractionDigits="0"></fmt:formatNumber></i>港元</p>
					</li>
					<li class="ul_td_ruletotal" style="width:170px;">
						<span>操盘保证金(￥)</span>	
						<p><i><fmt:formatNumber value="${trade.totalLeverMoney}" type="number" minFractionDigits="0"></fmt:formatNumber></i>元</p>
					</li>
					<li class="ul_td_ruletotal" style="width:170px;">
						<span>追加保证金(￥)</span>	
						<p><i><fmt:formatNumber value="${trade.totalAppendLeverMoney}" type="number" minFractionDigits="0"></fmt:formatNumber></i>元</p>
					</li>
				</ul>	
				<ul class="ul_td_rulelist">
					<ol>
						<li>
							<label>亏损警戒线(HK$)</label>
							<span><i><fmt:formatNumber value="${trade.warning}" type="number" minFractionDigits="0"></fmt:formatNumber></i>港元</span>
						</li>
						<li class="ul_tdrl_space">
							<label>亏损平仓线(HK$)</label>
							<span><i><fmt:formatNumber value="${trade.open}" type="number" minFractionDigits="0"></fmt:formatNumber></i>港元</span>
						</li>
					</ol>
					<ol>
						<li>
							<label>账户管理费(￥)</label>
							<span><em><fmt:formatNumber value="${trade.feeDay}" type="number" minFractionDigits="2"></fmt:formatNumber></em>元/交易日</span>
						</li>
						<li class="ul_tdrl_space">
							<label>总操盘利息(￥)</label>
							<span>
							<em><fmt:formatNumber value="${trade.apr}" type="number" minFractionDigits="2"></fmt:formatNumber></em>元
							</span>
						</li>
					</ol>
					<ol>
						<li>
							<label>申请操盘天数</label>
							<span><em>${trade.naturalDays} </em>天</span>
						</li>
						<li class="ul_tdrl_space">
							<label>已操盘天数</label>
							<span><em>${trade.tradingDays} </em>天</span>
						</li>
					</ol>
					<ol>
						<li>
							<label>操盘开始日期</label>
							<span>${trade.starttimeString}</span>
						</li>
						<li class="ul_tdrl_space">
							<label>操盘结束日期</label>
							<span>${trade.estimateEndtimeString}</span>
						</li>
					</ol>
					<ol class="ul_td_rulefont">
						<li>
							<label>操盘须知</label>
							<span>${operatorsInfo}</span>
						</li>
					</ol>
				</ul>	
			</div>
		</div>
		<div class="uc_td_live" style="display:none;">
			<ul class="uc_td_livelist">
				<ol></ol>
			</ul>
		</div>
		<div class="uc_td_list">
			<ul class="uc_paynav uc_paynav2">
				<li><a href="javascript:void(0);"  class="on" data="1" id="program">方案列表</a></li>
				<li><a href="javascript:void(0);"  data="3">账户管理费</a></li>
				<li><a href="javascript:void(0);"  data="4">港股交易账户</a></li>
			</ul>
			<!-- 方案列表 -->
			<div class="uc_td_program box box1" id="program_list">
				<ul class="uc_td_pgtitle">
					<li style="width:112px;">方案ID</li>
					<li style="width:113px;">总操盘资金(HK$)</li>
					<li style="width:113px;">操盘保证金(￥)</li>
					<li style="width:113px;">账户管理费(￥)</li>
					<li style="width:113px;">总操盘利息(￥)</li>
					<li style="width:113px;">操盘时间</li>
					<li style="width:113px;"></li>
				</ul>
				<table width="790" border="0" cellspacing="0" cellpadding="0" id="program_table">
				 
				</table>
			</div>
			
			<!-- 账户管理费/利息 -->
			<div class="uc_td_interest box box3" style="display:none;">				
				<ul class="uc_tdit_list" id="fee">
					
				</ul>
				<ul class="ud_tdit_info">
					<li>
						<p>已支付管理费(￥)</p>
						<span><i id="sumManage"></i>元</span>
					</li>
					<li>
						<p>已支付利息(￥)</p>
						<span><i id="sumInterest"></i>元
						
						</span>
					</li>
					<li class="last">						
						<p>已支付天数</p>
						<span><i>${trade.tradingDays} </i>天</span>
					</li>
				</ul>
			</div>
			
			<!-- 股票交易账户-->
			<div class="uc_td_interest box box4">
				<c:if test="${trade.status ne 0}">			
					<div class="uc_tlpromt_account">					
						<p>资金风控：亏损警戒线<i style="color: red;"><fmt:formatNumber value="${trade.warning}" type="number" minFractionDigits="0"></fmt:formatNumber></i> 港元，亏损平仓线<i style="color: red;"><fmt:formatNumber value="${trade.open} " type="number" minFractionDigits="0"></fmt:formatNumber></i>港元</p>
						<p>交易账户：<span style="color: red;">${trade.accountNo}</span></p>
						<p>交易密码：<span style="color: red;">${trade.password}</span>(为了您的资金安全，请妥善保管好密码)</p>
						<c:if test="${!empty trade.policyNo}">
							<p>保险单号：<a href="#">${trade.policyNo }</a></p>
						</c:if>
						<p> 交易软件：<a href="${ctx}/help?status=3" target='_blank'>请点击这里按照说明安装交易软件</a></p>
					</div>
				</c:if>	
			</div>
		</div>
	</div>
   <form id="showContract" name="showContract" action="" method="post" target="_blank"/>
</div>

<%@ include file="../common/personfooter.jsp"%>
<%@ include file="../common/dsp.jsp"%>
</body>
</html>

