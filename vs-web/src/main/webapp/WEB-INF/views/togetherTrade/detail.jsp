<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp"%>
<%@include file="../common/import-artDialog-js.jspf"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>股票合买方案详情 - 股票合买</title>
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/uc.css?v=${v}">
<script language="javascript" src="${ctx}/static/script/tzdr.js"></script>
 <script type="text/javascript" src="${ctx}/static/script/common/dateUtils.js"></script> 
<script src="${ctx}/static/script/togetherTrade/detail.js?v=${v}" type="text/javascript"></script>
</head>

<body>
<!--顶部 -->
	<!-- DINGBU -->

<div class="uc">
	<!--个人中心导航 -->
	<%@ include file="../common/leftnav.jsp"%>
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
	<div class="fl_box fl_uc_stops" style="display:none;" id="endOfProgramConfirm">		
		<div class="fl_navtitle">
			<h3>终止操盘</h3>
			<a href="javascript:void(0)" class="close"></a>
		</div>
		<div class="fl_uc_main">
			<p class="fl_uc_stopfont">你确认要终结方案么？</p>
		</div>
		<div class="fl_uc_btn">
			<a id="endOfProgram" href="javascript:void(0)" class="fl_uc_surebtn">确定</a>
			<a href="javascript:void(0)" class="fl_uc_cancelbtn">取消</a>
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
				<li>
					<label>账户余额：</label>
					<input type="hidden" id="balance" value="${balance}" />
					<span>
						<i><fmt:formatNumber value="${balance}"  type="number" minFractionDigits="2"  ></fmt:formatNumber></i>元
						<a href="${ctx}/pay/payinfo" target="_blank">&nbsp;&nbsp;去充值&gt;&gt;</a>
					</span>
				</li>
				<li>
					<label>追加金额：</label>
					<input id="additionalMargin" type="hidden" value="${trade.activityType}" />
					<input id="minAddMoney" type="hidden" value="${minAddMoney}" />
					<input id="maxAddMoney" type="hidden" value="${maxAddMoney}" />
					<input id="addMoney" type="text" maxlength="9" value="${minAddMoney}" />
					<span>元</span>
				</li>
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
			<p style="padding: 8px 16px;font-size:14px;text-align: left;font-family: '宋体'; line-height: 24px;">一，系统会先扣账户余额，再追加到A股交易账户中，追加成功后会以短信提示您；</p>
			<p style="padding: 8px 16px;font-size:14px;text-align: left;font-family: '宋体'; line-height: 24px;">二，交易时间，10分钟内完成追加；非交易时间，下个交易日开盘前完成追加。</p>
			<p style="padding: 16px;font-size:14px;text-align: center;font-family: '宋体'; line-height: 24px;">请确认是否追加？</p>
		</div>
		<div class="fl_uc_btn">
			<a href="javascript:void(0);" class="fl_uc_cancelbtn">取消</a>
			<a id="addBond" href="javascript:void(0);" class="fl_uc_surebtn">确认</a>
		</div>
	</div>
	<div class="uc_mianbar">
		<c:if test="${trade.status eq 0}">
		<!-- 合买进行中 -->
		<div class="uc_bd_status">
			<h3>合买进行中 . . .</h3>
			<div class="uc_bds_ctn">
				<p class="firstP1">合买进度：合买中</p>
				<p class="secondP1">合买者需出资：<i><fmt:formatNumber value="${trade.totalLending}"  type="number" minFractionDigits="2"  ></fmt:formatNumber></i>元</p>
			</div>
		</div>	
		</c:if>
		
		<c:if test="${trade.status eq 2}">
		<c:choose>
			<c:when test="${trade.auditStatus eq 2}">
					<!-- 合买失败 -->
					<div class="uc_bd_status" >
						<h3>合买失败</h3>
						<div class="uc_bds_ctn">
							<div class="uc_bds_fbtn">
								<a href="javascript:void(0);" class="colorA1">追加保证金</a>
								<a href="javascript:void(0);" class="colorA2">终止操盘</a>
							</div>
							<ul class="uc_bds_flist">
								<li>交易账户：<i>*******</i></li>
								<li>交易密码：<i>*******</i>(为了您的资金安全，请妥善保管好密码)</li>
								<li>交易软件：<a href="${ctx}/help?tab=software&leftMenu=1">请点击这里按照说明安装交易软件</a></li>
							</ul>
						
						</div>
					</div>
			</c:when>
		<c:otherwise>	
		<!-- 方案已终结 -->
		<div class="uc_bd_status" >
			<h3>方案已终结</h3>
			<div class="uc_bds_ctn">
				<p class="firstP2">结算金额：<i><fmt:formatNumber value="${trade.finishedMoney}" type="number" minFractionDigits="2"  ></fmt:formatNumber></i>元</p>
				<c:if test="${trade.totalAccrual ge 0}">
					<p class="secondP2">投资盈利：<i>+<fmt:formatNumber value="${trade.totalAccrual}"  type="number" minFractionDigits="2"  ></fmt:formatNumber></i>元</p>
				</c:if>
				<c:if test="${trade.totalAccrual lt 0}">
					<p class="secondP2">投资亏损：<em><fmt:formatNumber value="${trade.totalAccrual}"  type="number" minFractionDigits="2"  ></fmt:formatNumber></em>元</p>
				</c:if>
			</div>
		</div>		
			</c:otherwise>
		</c:choose>
		</c:if>
		<c:if test="${trade.status eq 1}">
			<!-- 正在操盘中 -->
			<div class="uc_bd_status" >
			<h3>正在操盘中 . . .</h3>
			<div class="uc_bds_ctn">
				<div class="uc_bds_fbtn">
					<a href="javascript:void(0);" class="uc_bdsb_add">追加保证金</a>
					<a href="javascript:void(0);" class="uc_bdsb_end">终止操盘</a>
				</div>
				<ul class="uc_bds_flist">
					<li>交易账户：<em>${trade.account}</em></li>
					<li>交易密码：<em>${trade.password}</em>(为了您的资金安全，请妥善保管好密码)</li>
					<li>交易软件：<a href="${ctx}/help?tab=software&leftMenu=1">请点击这里按照说明安装交易软件</a></li>
				</ul>
			</div>
		</div>
		
		</c:if>
		<div class="uc_bd_info">
			<h3>操盘资金</h3>
			<table  border="0" cellspacing="0" cellpadding="0">
	            <thead>         
	                <tr>
	                    <td>总操盘资金</td>
	                    <td>操盘者出资</td>
	                    <td>追加保证金</td>
	                    <td>合买者出资</td>
	                </tr>
	            </thead>        
	            <tbody>
	                <tr>
	                    <td><i><fmt:formatNumber value="${trade.totalOperateMoney}"  type="number" minFractionDigits="2"  ></fmt:formatNumber></i>元</td>
	                    <td><i><fmt:formatNumber value="${trade.totalLeverMoney}"  type="number" minFractionDigits="2"  ></fmt:formatNumber></i>元</td>
	                    <td><i><fmt:formatNumber value="${trade.totalAppendLeverMoney}"  type="number" minFractionDigits="2"  ></fmt:formatNumber></i>元</td>
	                    <td><i><fmt:formatNumber value="${trade.totalLending}"  type="number" minFractionDigits="2"  ></fmt:formatNumber></i>元</td>
	                </tr>
	            </tbody>
	        </table>
			<h3>操盘规则<i id="group_id"  style="display:none" >${trade.groupId}</i><em>${operatorsInfo}</em></h3>
			<table  border="0" cellspacing="0" cellpadding="0">
	            <thead>         
	                <tr>
	                    <td>亏损补仓线</td>
	                    <td>亏损平仓线</td>
	                    <td>合买者权益</td>
	                    <td>账户管理费</td>
	                </tr>
	            </thead>        
	            <tbody>
	                <tr>
	                    <td><i><fmt:formatNumber value="${trade.warning}"  type="number" minFractionDigits="2"  ></fmt:formatNumber></i>元</td>
	                    <td><i><fmt:formatNumber value="${trade.open}"  type="number" minFractionDigits="2"  ></fmt:formatNumber></i>元</td>
	                    <td><i><fmt:formatNumber value="${trade.apr}"  type="number" minFractionDigits="2"  ></fmt:formatNumber></i>元</td>
	                    <td><i><fmt:formatNumber value="${trade.feeDay}"  type="number" minFractionDigits="2"  ></fmt:formatNumber></i>元/天</td>
	                </tr>
	            </tbody>
	        </table>	        
			<table  border="0" cellspacing="0" cellpadding="0" class="topTable">
	            <thead>         
	                <tr>
	                    <td>申请操盘天数</td>
	                    <td>已操盘天数</td>
	                    <td>操盘开始日期</td>
	                    <td>预计结束日期</td>
	                </tr>
	            </thead>        
	            <tbody>
	                <tr>
	                    <td><i>${trade.naturalDays}</i>天</td>
	                    <td><i>${trade.tradingDays}</i>天</td>
	                    <td><i>${trade.starttimeString}</i></td>
	                    <td><i>${trade.estimateEndtimeString}</i></td>
	                </tr>
	            </tbody>
	        </table>
			<h3>成本支出</h3>
			<table  border="0" cellspacing="0" cellpadding="0" >
				<thead>  
					<tr>
		                <td>支付日期</td>
		                <td>合买者权益</td>
		                <td>账户管理费</td>
		                <td>支付状态</td>
		            </tr>
          	  </thead>  
			  <tbody id="fee">
	                
	          </tbody>
			</table>
		</div>
	</div>
</div>
<!-- DINGBU -->
</body>
</html>
