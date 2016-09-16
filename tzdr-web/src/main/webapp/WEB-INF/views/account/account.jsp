<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp"%>
<%@ include file="../common/import-artDialog-js.jspf"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="../common/commonkeyword.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/uc.css?v=${v}">
<script type="text/javascript" src="${ctx}/static/script/common/dateUtils.js?v=${v}"></script>
<script language="javascript" src="${ctx}/static/script/account/account.js?v=${v}"></script>
<script language="javascript" src="${ctx}/static/script/tzdr.js?v=${v}"></script>
<style type="text/css">
	#secInfo{cursor: pointer;}
	.uc_sidebar h2 {font-weight: normal;}
	#nav_my {color: #ffcc33; border-bottom:2px solid #ffcc33; padding-bottom: 26px;}
</style>
</head>
<body>
<!--顶部 -->
	<%@include file="../common/header.jsp"%>
	<c:if test="${isActivityType eq 1}">
		<!-- 浮动层 -->
		<div class="floatlayer">
    		<div class="fl_cpstep" style="display:block;">
       			<div class="fl_mask"></div>
        		<div class="fl_cpstep1">
            		<a class="peiz_jine_next" href="${ctx}/day?enter=0" title="实盘申请"></a>
        		</div>
    		</div>
		</div>
	</c:if>	
	<!-- 遮罩层 -->
	<div class="fl_mask" id="fl_mask" style="display:none;"></div>
	<!-- 追加保证金弹出框 -->
	<div id="deposit" class="fl_box fl_uc_trade" style="display:none;">
		<div class="fl_navtitle">
			<h3>追加保证金</h3>
			<a href="javascript:void(0);" class="close"></a>
		</div>
		<div class="fl_uc_main">
			<ul class="fl_uc_list">
			</ul>
		</div>
		<div class="fl_uc_btn">
			<a id="addBond" href="javascript:void(0);" class="fl_uc_surebtn">确定</a>
			<a href="javascript:void(0);" class="fl_uc_cancelbtn">取消</a>
		</div>
	</div>
	<%-- 欠费方案 --%>
	<div class="uc_floatlayer" style="display: none;">
		<div class="fl_mask"></div>
		<div class="fl_ucmoney">
			<a href="javascript:void(0);" class="fl_ucmoneyclose" id="arrearage_close"></a>
			<h3>您有<i id="arrearageTotalNum">0</i>个欠费方案</h3>
			<ul class="fl_ucmlist" id="arrearageList"></ul>
		</div>
	</div>
	<%-- 内转弹出框 --%>
	<div id="convertWin" class="fl_box fl_uc_trade" style="display: none;">
		<div class="fl_navtitle">
			<h3>内转</h3>
			<a href="javascript:void(0);" id="convertClose" class="close"></a>
		</div>
		<ul class="fl_uc_list">
			<li>
				<label>转出账户：</label>
				<select id="outAccountSelect">
					<option value="1" selected="selected">股票账户</option>
					<option value="2">股指账户</option>
				</select>
			</li>
			<li>
				<label>转入账户：</label>
				<select id="intoAccountSelect">
					<option value="1">股票账户</option>
					<option value="2" selected="selected">股指账户</option>
				</select>
			</li>
			<li>
				<label>划拨金额：</label>
				<input type="text" id="convertMoney"><span>元</span>
			</li>
		</ul>
		<div class="fl_uc_btn">
			<a href="javascript:void(0);" id="convertCancel" class="fl_uc_cancelbtn">取消</a>
			<a href="javascript:void(0);" id="convertSure" class="fl_uc_surebtn">确定</a>
		</div>
	</div>
	<div class="uc">
		<!-- 个人中心导航 -->
		<%@ include file="../common/leftnav.jsp"%>
		<!--网银支付-->
		<div class="uc_mianbar">
			<div class="uc_siflist" style="margin-bottom:20px;">
				<div class="uc_sl_nav">
					<a href="javascript:void(0);" class="on">账户概况</a>
				</div>
				<div class="uc_sifmoney" style="border:none;">
					<ul class="uc_sm_over">
						<li class="uc_sm_overmoney">
							<h2>账户余额：</h2>
							<p>
								<c:choose>
									<c:when test="${!empty wUser.avlBal}">
										<span id="avlBal"><fmt:formatNumber value="${wUser.avlBal}" pattern="#,###.##" minFractionDigits="2" ></fmt:formatNumber></span>元
									</c:when>
									<c:otherwise>
										<span id="avlBal">0.<i>00</i></span>元
									</c:otherwise>
								</c:choose>
							</p>
						</li>
						<!-- 按钮组 -->
						<li class="uc_sm_overbtn">
							<a href="${ctx}/pay/payinfo">充值</a>
							<a id="drawmoney" href="javascript:void(0);" class="other">提现</a>
						</li>
					</ul>
					<div class="uc_sm_info">
						<h2>你好！${userName}</h2>
						<p>
							<%-- 安全等级 --%>
							<c:choose>
								<c:when test="${safetyCount == 0}">
									<span>安全等级：低</span>
								</c:when>
								<c:when test="${safetyCount == 1}">
									<span>安全等级：低</span>
								</c:when>
								<c:when test="${safetyCount == 2}">
									<span>安全等级：中</span>
								</c:when>
								<c:otherwise>
									<span>安全等级：高</span>
								</c:otherwise>
							</c:choose>
							<%--银行卡 --%>
							<c:choose>
								<c:when test="${isbank}">
									<a id="secInfo" href="javascript:void(0);" class="yhkon" title="银行卡"></a>
								</c:when>
								<c:otherwise>
									<a id="secInfo" href="javascript:void(0);" class="yhk" title="银行卡"></a>
								</c:otherwise>
							</c:choose>
							<%-- 实名 --%> 
							<c:choose>
								<c:when test="${!empty userVerified && !empty userVerified.idcard}">
									<a id="secInfo" href="javascript:void(0);" class="sfzon" title="实名"></a>
								</c:when>
								<c:otherwise>
									<a id="secInfo" href="javascript:void(0);" class="sfz" title="实名"></a>
								</c:otherwise>
							</c:choose>
							<!-- 提取密码 -->
							<c:choose>
								<c:when test="${!empty userVerified && !empty userVerified.drawMoneyPwd}">
									<a id="secInfo" href="javascript:void(0);" class="mmon" title="提取密码"></a>
								</c:when>
								<c:otherwise>
									<a id="secInfo" href="javascript:void(0);" class="mm" title="提取密码"></a>
								</c:otherwise>
							</c:choose>
							<%-- 邮箱 --%>
							<c:choose>
								<c:when test="${!empty userVerified && userVerified.emailActive == 1 }">
									<a id="secInfo" href="javascript:void(0);" class="yxon" title="邮箱"></a>
								</c:when>
								<c:otherwise>
									<a id="secInfo" href="javascript:void(0);o" class="yx" title="邮箱"></a>
								</c:otherwise>
							</c:choose>
						</p>
					</div>
					<ul class="uc_sm_money">
						<li>
							<h3>资产总值<a href="javascript:void(0);"><img src="${ctx}/static/images/uc/pay_02.gif"></a></h3>
							<p id="totalAssets"></p>
							<div class="uc_mlpromt" style="display:none;">
								<p>账户余额+融资保证金+冻结金额</p>
								<i></i>
							</div>
						</li>				
						<%-- <li>
							<h3>融资金额<a href="javascript:void(0);"><img src="${ctx}/static/images/uc/pay_02.gif"></a></h3>
							<p id="totalLending"></p>
							<div class="uc_mlpromt" style="display:none;">
								<p>维胜提供您的融资总额</p>
								<i></i>
							</div>
						</li>
						<li>
							<h3>融资保证金<a href="javascript:void(0);"><img src="${ctx}/static/images/uc/pay_02.gif"></a></h3>
							<p id="totalLeverMoney"></p>
							<div class="uc_mlpromt" style="display:none;">
								<p>您融资时缴纳的保证金</p>
								<i></i>
							</div>
						</li> --%>
						<li>
							<h3>冻结金额<a href="javascript:void(0);"><img src="${ctx}/static/images/uc/pay_02.gif"></a></h3>
							<p id="frzBal"></p>
							<div class="uc_mlpromt" style="display:none;">
								<p>您提现时被冻结的金额</p>
								<i></i>
							</div>
						</li>
					</ul>
				</div>
			</div>	
		</div>
		<div class="clear"></div>
	</div>
	<!--底部 -->
<%@include file="../common/footer.jsp"%>
</body>
</html>