<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp"%>
<%@ include file="../common/import-artDialog-js.jspf"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="../common/commonkeyword.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/uc.css?version=20150804">
<%-- <link rel="stylesheet" type="text/css" href="${ctx}/static/css/public.css"> --%>
<%-- <link rel="stylesheet" type="text/css" href="${ctx}/static/css/capital.css"> --%>
<script type="text/javascript" src="${ctx}/static/script/common/dateUtils.js"></script>
<script language="javascript" src="${ctx}/static/script/account/account.js?version=20150804"></script>
<script language="javascript" src="${ctx}/static/script/tzdr.js"></script>
<style type="text/css">
	#secInfo{
		cursor: pointer;
	}
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
			<div class="uc_mainbanner" style="display:none;"><a href="http://www.tzdr.com/news/shownews/8af5e81853413bf90153593cf80c7273" target="_blank"><img src="${ctx}/static/images/uc/banner.png" alt="现金红包送不停"></a></div>
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
							<%---
							<a id="convert" href="javascript:void(0);">内转</a>
							 --%>
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
				<%-- 股票操盘 --%>
				<%-- <div class="uc_sl_siflist uc_sl_siflist2" style="display:block;">
					<div class="uc_sl_sifmoney">
						<ul class="uc_sl_moneylist uc_sl_moneylist2">
							<li>
								<h3>累计支出保证金</h3>
								<p>
								<c:choose>
									<c:when test="${!empty wUser.totalDeposit}">
										<fmt:formatNumber value="${wUser.totalDeposit}" pattern="#,###.##" minFractionDigits="2" ></fmt:formatNumber>元
									</c:when>
									<c:otherwise>
										0.<i>00元</i>
									</c:otherwise>
								</c:choose>
								</p>		
							</li>
							<li>
								<h3>累计融资金额</h3>
								<p>
								<c:choose>
									<c:when test="${!empty wUser.totalLending}">
										<fmt:formatNumber value="${wUser.totalLending}" pattern="#,###.##" minFractionDigits="2" ></fmt:formatNumber>元
									</c:when>
									<c:otherwise>
										0.<i>00元</i>
									</c:otherwise>
								</c:choose>	
								</p>	
							</li>
							<li>
								<h3>累计支出利息</h3>
								<p>
								<c:choose>
									<c:when test="${!empty wUser.totalInterestMo}">
										<fmt:formatNumber value="${wUser.totalInterestMo}" pattern="#,###.##" minFractionDigits="2" ></fmt:formatNumber>元
									</c:when>
									<c:otherwise>
										0.<i>00元</i>
									</c:otherwise>
								</c:choose>	
								</p>	
							</li>
							<li>
								<h3>累计支出管理费</h3>
								<p>
								<c:choose>
									<c:when test="${!empty wUser.totalManagerMo}">
										<fmt:formatNumber value="${wUser.totalManagerMo}" pattern="#,###.##" minFractionDigits="2" ></fmt:formatNumber>元
									</c:when>
									<c:otherwise>
										0.<i>00元</i>
									</c:otherwise>
								</c:choose>	
								</p>
							</li>
							<li>
								<h3>实现盈亏</h3>
								<p id="totalAccrual"><fmt:formatNumber value="${totalAccrual}" pattern="#,###.##" minFractionDigits="2" ></fmt:formatNumber>元</p>
							</li>
							<li>
								<h3>盈亏率</h3>
								<p id="totalAccrualInterest"><fmt:formatNumber value="${totalAccrualInterest}" pattern="#,###.##" minFractionDigits="2" ></fmt:formatNumber>%</p>	
							</li>
						</ul>	
					</div>		
				</div> --%>
			</div>
			<%--< div class="uc_siflist">
				<!-- 股指期货 -->
				<div class="uc_sl_nav">
					<a href="" class="on">期指点点乐模拟账户</a>
				</div>
				<div class="uc_sifmoney" style="border:none;">
					<ul class="uc_sm_over" style="float:left; width:250px;">
						<li class="uc_sm_overmoney">
							<h2>账户余额：</h2>
							<c:choose>
								<c:when test="${!empty futuresAccount.balanceMoney}">
									<p><span><fmt:formatNumber value="${futuresAccount.balanceMoney}" pattern="#,###.##" minFractionDigits="2" ></fmt:formatNumber></span>元</p>
								</c:when>
								<c:otherwise>
									<p><span>0.<i>00</i></span>元</p>
								</c:otherwise>
							</c:choose>
						</li>
					</ul>
					<ul class="uc_sm_money uc_sm_money2" style="display:none;">
						<li>
							<h3>账户总资产<a href="javascript:void(0);"><img src="${ctx}/static/images/uc/pay_02.gif"></a></h3>
							<c:choose>
								<c:when test="${!empty futuresAccount.totalMoney}">
									<p><fmt:formatNumber value="${futuresAccount.totalMoney}" pattern="#,###.##" minFractionDigits="2" ></fmt:formatNumber>元</p>
								</c:when>
								<c:otherwise>
									<p>0.<i>00元</i></p>
								</c:otherwise>
							</c:choose>
							<div class="uc_mlpromt" style="display:none;">
								<p>股指期货子账户总资产</p>
								<i></i>
							</div>
						</li>				
						<li>
							<h3>股指保证金<a href="javascript:void(0);"><img src="${ctx}/static/images/uc/pay_02.gif"></a></h3>
							<c:choose>
								<c:when test="${!empty futuresAccount.cautionMoney}">
									<p><fmt:formatNumber value="${futuresAccount.cautionMoney}" pattern="#,###.##" minFractionDigits="2" ></fmt:formatNumber>元</p>
								</c:when>
								<c:otherwise>
									<p>0.<i>00元</i></p>
								</c:otherwise>
							</c:choose>
							<div class="uc_mlpromt" style="display:none;">
								<p>您交易时扣除的保证金</p>
								<i></i>
							</div>
						</li>
					</ul>
				</div>
				<div class="uc_sl_sifmoney uc_sl_siflist2">
					<ul class="uc_sl_moneylist">
						<li>
							<h3>累计盈亏</h3>
							<c:choose>
								<c:when test="${!empty futuresAccount.cumulativeProfit}">
									<p id="cumulativeProfit"><fmt:formatNumber value="${futuresAccount.cumulativeProfit}" pattern="#,###.##" minFractionDigits="2" ></fmt:formatNumber>元</p>
								</c:when>
								<c:otherwise>
									<p id="cumulativeProfit">0.<i>00元</i></p>
								</c:otherwise>
							</c:choose>
						</li>
						<li>
							<h3>累计支出交易费</h3>
							<c:choose>
								<c:when test="${!empty futuresAccount.cumulativeTrans}">
									<p><fmt:formatNumber value="${futuresAccount.cumulativeTrans}" pattern="#,###.##" minFractionDigits="2" ></fmt:formatNumber>元</p>
								</c:when>
								<c:otherwise>
									<p>0.<i>00元</i></p>
								</c:otherwise>
							</c:choose>
						</li>
						<li>
							<h3>累计获得利润</h3>
							<c:choose>
								<c:when test="${!empty futuresAccount.cumulativeTotal}">
									<p id="cumulativeTotal"><fmt:formatNumber value="${futuresAccount.cumulativeTotal}" pattern="#,###.##" minFractionDigits="2" ></fmt:formatNumber>元</p>
								</c:when>
								<c:otherwise>
									<p id="cumulativeTotal">0.<i>00元</i></p>
								</c:otherwise>
							</c:choose>
						</li>
						<li>
							<h3>实际盈亏</h3>
							<c:choose>
								<c:when test="${!empty futuresAccount.actualProfit}">
									<p id="actualProfit"><fmt:formatNumber value="${futuresAccount.actualProfit}" pattern="#,###.##" minFractionDigits="2" ></fmt:formatNumber>元</p>
								</c:when>
								<c:otherwise>
									<p id="actualProfit">0.<i>00元</i></p>
								</c:otherwise>
							</c:choose>
						</li>
						
						<li>
							<h3>盈亏率</h3>
							<c:choose>
								<c:when test="${!empty futuresAccount.profitRate}">
									<p id="profitRate"><fmt:formatNumber value="${futuresAccount.profitRate}"  pattern="#,###.##" minFractionDigits="2" ></fmt:formatNumber>%</p>
								</c:when>
								<c:otherwise>
									<p id="profitRate">0.<i>00%</i></p>
								</c:otherwise>
							</c:choose>
						</li>
						
					</ul>
				</div>
			</div> --%>
		</div>
		<div class="clear"></div>
	</div>
	<!--底部 -->
<%@include file="../common/footer.jsp"%>
<!-- footer -->
<%@ include file="../common/dsp.jsp"%>
</body>
</html>