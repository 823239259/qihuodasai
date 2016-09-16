<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp"%>
<%@include file="../common/import-fileupload-js.jspf"%>
<%@include file="../common/import-artDialog-js.jspf"%>
<%
	String tab = request.getParameter("tab");
%>
<html xmlns="http://www.w3.org/1999/xhtml" lang="zh">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="keywords" content="维胜，国际期货，期货，投资达人，金勺子，高盛，都城，南华期货，配资，期货配资，期货开户，外盘，富时A50，国际原油，恒指期货，期货公司，期货平台，炒期货，模拟盘，赚钱，头寸，持仓，成都盈透科技有限公司"/>
<meta name="description" content="维胜（www.vs.com）-致力于成为中国领先的国际期货及衍生品互联网交易平台，提供恒指期货、国际原油、富时A50等主流国际期货产品，开户操盘快捷方便，交易费用全网最低。"/>
<title>资金明细 - 维胜金融-中国领先的国际期货及衍生品互联网交易平台</title>
<link rel="stylesheet" href="${ctx}/static/css/uc.css?v=${v}" type="text/css">
<link href="${ctx}/static/css/public.css?v=${v}" rel="stylesheet" type="text/css">
<link href="${ctx}/static/css/pagination.css?v=${v}" rel="stylesheet" type="text/css" />
<script src="${ctx}/static/script/common/jquery.pagination.js?v=${v}" type="text/javascript"></script>
<script src="${ctx}/static/script/My97DatePicker/WdatePicker.js?v=${v}" type="text/javascript"></script>
<script type='text/javascript' src="${ctx}/static/script/fund/fundDeatail.js?v=${v}"></script>
<script type='text/javascript' src="${ctx}/static/script/common/dateUtils.js?v=${v}"></script>
<script type='text/javascript' src="${ctx}/static/script/common/ZeroClipboard.min.js?v=${v}"></script>
<script type="text/javascript" src="${ctx}/static/script/tzdr.js?v=${v}"></script>
<script type="text/javascript">
	var tab =<%=tab%>;
</script>
<style>
	#nav_my {color: #ffcc33; border-bottom:2px solid #ffcc33; padding-bottom: 26px;}
</style>
</head>
<body>
	<!--顶部 -->
	<!--个人中心导航 -->
	<%@include file="../common/header.jsp"%>
	<div class="uc">
		<!--个人中心导航 -->
		<%@ include file="../common/leftnav.jsp"%>
		<!--网银支付-->
		<div class="uc_mianbar">
			<!--  
		<ul class="uc_fsinfo">
			<li>
				<h2>账户总资产</h2>
				<p><i><fmt:formatNumber value="${totalAssets}" type="currency" pattern="0.00#"/>
								</i>元</p>
			</li>
			<li>
				<h2>操盘配额</h2>
				<p><i>
				<fmt:formatNumber value="${totalLending}" type="currency" pattern="0.00#"/>
				</i>元</p>
			</li>
			<li>
				<h2>风险保证金</h2>
				<p><i>
				<fmt:formatNumber value="${totalLeverMoney}" type="currency" pattern="0.00#"/>
				
				</i>元</p>
			</li>
			<li>
				<h2>冻结金额</h2>
				<p><i><fmt:formatNumber value="${user.frzBal}" type="currency" pattern="0.00#"/>
								</i>元</p>
			</li>
			<li>
				<h2>账户余额</h2>
				<p><i><fmt:formatNumber value="${user.avlBal}" type="currency" pattern="0.00#"/>
						</i>元</p>
			</li>
		</ul>
		-->
			<div class="uc_pay" id="fundtab">
				<ul class="uc_paynav">
					<li><a href="javascript:void(0)" class="on">资金明细</a></li>
					<!--  
					<li><a href="javascript:void(0)">充值提款</a></li>
					<li><a href="javascript:void(0)">操盘明细</a></li>
					<li><a href="javascript:void(0)">利息管理费明细</a></li>
					<li><a href="javascript:void(0)">推广佣金</a></li>
					-->
					<%-- <li><a href="${futureUrl}/fund/detail">期指点点乐模拟盘明细</a></li> --%>
				</ul>
				<div class="tabcon">
					<div class="subtab">
						<!-- 全部明细 -->
						<div class="uc_trade">
							<!-- 浮动层 -->
							<div class="uc_floatlayer">
								<!-- 类型 -->
								<div class="uc_bc_option uc_fs_type" id="alltypediv"
									style="display: none;">
									<a href="javascript:void(0)" data-id="">全部明细</a>
									<c:forEach items="${data}" var="type" varStatus="status">
										<a href="javascript:void(0)" data-id="${type['valueKey']}">${type['valueName']}</a>
									</c:forEach>
								</div>
							</div>
							<div class="uc_fsdetails">
								<div class="uc_fsmoney">
									<p>
										收入<i class="color1" id="incount"><c:choose><c:when test="${!empty infund.count}">${infund.count}</c:when><c:otherwise>0</c:otherwise></c:choose></i>笔，共<i class="color1" id="insummoney"><c:choose><c:when test="${!empty infund.summoney}"><fmt:formatNumber value="${infund.summoney}" pattern="##.##" minFractionDigits="2"></fmt:formatNumber></c:when><c:otherwise>0.00</c:otherwise></c:choose></i>元
									</p>
									<p>
										支出<i class="color5" id="outcount"><c:choose><c:when test="${!empty outfund.count}">${outfund.count}</c:when><c:otherwise>0</c:otherwise></c:choose></i>笔，共<i class="color5" id="outsummoney"><c:choose><c:when test="${!empty outfund.summoney}"><fmt:formatNumber value="${outfund.summoney}" pattern="##.##" minFractionDigits="2"></fmt:formatNumber></c:when><c:otherwise>0.00</c:otherwise></c:choose></i>元
									</p>
								</div>
								<div class="uc_fssearch">
									<em>收支类型：</em> <input type="text" class="uc_fs_ip"
										id="fundtype" name="fundtype" value="全部明细"> <a
										href="javascript:void(0)" id="alltype"
										class="uc_fssdown uc_fsiptype"></a> <em>时间：</em> <input
										type="text" name="allstarttime" readonly="readonly"
										onclick="WdatePicker();" id="allstarttime" class="uc_fsiptime">
									<i>—</i> <input type="text" name="allendtime" id="allendtime"
										readonly="readonly" onclick="WdatePicker();"
										class="uc_fsiptime"> <span><a
										href="javascript:void(0)"
										onclick="findAllData('Searchresult','Pagination');">查询</a></span>
								</div>
							</div>
							<ul class="uc_fslist">
								<ol class="uc_fsl_title">
									<li class="uc_fsl165">时间</li>
									<li class="uc_fsl200">类型</li>
									<li class="uc_fsl100">收入</li>
									<li class="uc_fsl100">支出</li>
									<li class="uc_fsl100">余额</li>
								</ol>
								<div>
									<div id="Searchresult"></div>
								</div>
							</ul>
							<div class="uc_tpage uc_fspage">
								<div id="Pagination"></div>
							</div>
						</div>
					</div>
					<div class="subtab">
						<!-- 充值取款明细 -->
						<div class="uc_trade">
							<div class="uc_floatlayer">
								<div class="uc_bc_option uc_fs_type" id="paytypediv"
									style="display: none;">
									<a href="javascript:void(0)" data-id="1,2,3,4">全部明细</a> <a
										href="javascript:void(0)" data-id="1">充值存入</a> <a
										href="javascript:void(0)" data-id="2">提现取出</a>
										<a href="javascript:void(0)" data-id="3">系统调账</a>
										<a href="javascript:void(0)" data-id="4">系统冲账</a>
								</div>
							</div>
							<div class="uc_fsdetails">
								<div class="uc_fsmoney">
									<p>
										收入<i class="color1" id="inpaycount"><c:choose><c:when test="${!empty inpayfund.count}">${inpayfund.count}</c:when><c:otherwise>0</c:otherwise></c:choose></i>笔，共<i class="color1" id="inpaysum"><c:choose><c:when test="${!empty inpayfund.summoney}"><fmt:formatNumber value="${inpayfund.summoney}"
														pattern="##.##" minFractionDigits="2"></fmt:formatNumber></c:when><c:otherwise>0.00</c:otherwise></c:choose></i>元</p>
									<p>
										支出<i class="color5" id="outpaycount"><c:choose><c:when test="${!empty outpayfund.count}">${outpayfund.count}</c:when><c:otherwise>0</c:otherwise></c:choose></i>笔，共<i class="color5" id="outpaysum"><c:choose><c:when test="${!empty outpayfund.summoney}"><fmt:formatNumber value="${outpayfund.summoney}" pattern="##.##" minFractionDigits="2"></fmt:formatNumber></c:when><c:otherwise>0.00</c:otherwise></c:choose></i>元</p>
								</div>
								<div class="uc_fssearch">
									<em>收支类型：</em> <input type="text" class="uc_fs_ip"
										id="changetype" data-id="1,2,3,4" name="changetype" value="全部明细">
									<a href="javascript:void(0)" id="paytype" name="paytype"
										class="uc_fssdown uc_fsiptype"></a> <em>时间：</em> <input
										type="text" class="uc_fsiptime" id="paystarttime"
										onclick="WdatePicker();" readonly="readonly"
										name="paystarttime"> <i>—</i> <input type="text"
										class="uc_fsiptime" id="payendtime" onclick="WdatePicker();"
										readonly="readonly" name="payendtime"> <span><a
										href="javascript:void(0)"
										onclick="findpayfund('payfundSearchresult','payfundPagination');">查询</a></span>
								</div>
							</div>
							<ul class="uc_fslist">
								<ol class="uc_fsl_title">
									<li class="uc_fsl165">时间</li>
									<li class="uc_fsl200">类型</li>
									<li class="uc_fsl100">收入</li>
									<li class="uc_fsl100">支出</li>
									<!--  
									<li class="uc_fsl100">余额</li>
									-->
								</ol>
								<div>
									<div id="payfundSearchresult"></div>
								</div>
							</ul>
							<div class="uc_tpage uc_fspage">
								<div id="payfundPagination"></div>
							</div>
						</div>
					</div>
					<div class="subtab">
						<!-- 操盘明细 -->
						<div class="uc_trade">
							<div class="uc_floatlayer">
								<!-- 类型 -->
								<div class="uc_bc_option uc_fs_type" id="fundLoantypediv"
									style="display: none;">
									<a href="javascript:void(0)" data-id="10,11,12,15,16,17,18,25,26,27">全部明细</a>
									<a href="javascript:void(0)" data-id="10">操盘支出</a> <a
										href="javascript:void(0)" data-id="15">操盘撤回</a> <a
										href="javascript:void(0)" data-id="11">利息支出</a> <a
										href="javascript:void(0)" data-id="12">管理费支出</a> <a
										href="javascript:void(0)" data-id="16">利润提取</a> <a
										href="javascript:void(0)" data-id="17">追加保证金</a> <a
										href="javascript:void(0)" data-id="18">操盘欠费</a>
										<a
										href="javascript:void(0)" data-id="25">操盘管理费撤回</a>
										<a
										href="javascript:void(0)" data-id="26">操盘利息撤回</a>
										<a
										href="javascript:void(0)" data-id="27">补仓欠费</a>
								</div>
							</div>
							<div class="uc_fsdetails">
								<div class="uc_fsmoney">
									<p>
										收入<i class="color1" id="inloanfundcount"><c:choose><c:when test="${!empty inloanfund.count}">${inloanfund.count}</c:when><c:otherwise>0</c:otherwise></c:choose></i>笔，共<i class="color1" id="inloanfundsum"><c:choose><c:when test="${!empty inloanfund.summoney}"><fmt:formatNumber value="${inloanfund.summoney}"
														pattern="##.##" minFractionDigits="2"></fmt:formatNumber></c:when><c:otherwise>0.00</c:otherwise></c:choose></i>元</p>
									<p>
										支出<i class="color5" id="outloanfundcount"><c:choose><c:when test="${!empty outloanfund.count}">${outloanfund.count}</c:when><c:otherwise>0</c:otherwise></c:choose></i>笔，共<i class="color5" id="outloanfundsum"><c:choose><c:when test="${!empty outloanfund.summoney}"><fmt:formatNumber value="${outloanfund.summoney}"
														pattern="##.##" minFractionDigits="2"></fmt:formatNumber></c:when><c:otherwise>0.00</c:otherwise></c:choose></i>元</p>
								</div>
								<div class="uc_fssearch">
									<em>收支类型：</em> <input type="text" class="uc_fs_ip"
										id="fundLoan" data-id="10,11,12,15,16,17,18,25,26,27" name="fundLoan"
										value="全部明细"> <a href="javascript:void(0)"
										id="fundLoantype" name="fundLoantype"
										class="uc_fssdown uc_fsiptype"></a> <em>时间：</em> <input
										type="text" class="uc_fsiptime" id="loanstarttime"
										name="loanstarttime" readonly="readonly"
										onclick="WdatePicker();"> <i>—</i> <input type="text"
										class="uc_fsiptime" id="loanendtime" name="loanendtime"
										readonly="readonly" onclick="WdatePicker();"> <span><a
										href="javascript:void(0)"
										onclick="findfundLoan('loanSearchresult','loanPagination');">查询</a></span>
								</div>
							</div>
							<ul class="uc_fslist">
								<ol class="uc_fsl_title">
									<li class="uc_fsl165">时间</li>
									<li class="uc_fsl200">类型</li>
									<li class="uc_fsl100">收入</li>
									<li class="uc_fsl100">支出</li>
									<!--  
									<li class="uc_fsl100">余额</li>
									-->
								</ol>
								<div>
									<div id="loanSearchresult"></div>
								</div>
							</ul>
							<div class="uc_tpage uc_fspage">
								<div id="loanPagination"></div>
							</div>
						</div>
					</div>
					<div class="subtab">
						<!-- 利息管理费明细 -->
						<div class="uc_trade">
							<div class="uc_floatlayer">
								<div class="uc_bc_option uc_fs_type" id="interestdiv"
									style="display: none;">
									<a href="javascript:void(0)" data-id="11,12,25,26">全部明细</a> <a
										href="javascript:void(0)" data-id="11">利息支出</a> <a
										href="javascript:void(0)" data-id="12">管理费支出</a>
										<a href="javascript:void(0)" data-id="25">操盘管理费撤回</a>
										 <a href="javascript:void(0)" data-id="26">操盘利息撤回</a>
								</div>
							</div>
							<div class="uc_fsdetails">
								<div class="uc_fsmoney">
									<p>
										收入<i class="color1" id="interestIncount"><c:choose><c:when test="${!empty ininterestfund.count}">${ininterestfund.count}</c:when><c:otherwise>0</c:otherwise></c:choose></i>笔，共<i class="color1" id="interestInsum"><c:choose><c:when test="${!empty ininterestfund.summoney}"><fmt:formatNumber value="${ininterestfund.summoney}"
														pattern="##.##" minFractionDigits="2"></fmt:formatNumber></c:when><c:otherwise>0.00</c:otherwise></c:choose></i>元</p>
									<p>支出<i class="color5" id="interestcount"><c:choose><c:when test="${!empty outinterestfund.count}">${outinterestfund.count}</c:when><c:otherwise>0</c:otherwise></c:choose></i>笔，共<i class="color5" id="interestsum"><c:choose><c:when test="${!empty outinterestfund.summoney}"><fmt:formatNumber value="${outinterestfund.summoney}"
														pattern="##.##" minFractionDigits="2"></fmt:formatNumber></c:when><c:otherwise>0.00</c:otherwise></c:choose></i>元</p>
								</div>
								<div class="uc_fssearch">
									<em>收支类型：</em> <input type="text" class="uc_fs_ip"
										id="interest" name="interest" data-id="11,12,25,26" value="全部明细">
									<a href="javascript:void(0)" id="interesttype"
										name="interesttype" class="uc_fssdown uc_fsiptype"></a> <em>时间：</em>
									<input type="text" class="uc_fsiptime" id="intereststarttime"
										onclick="WdatePicker();" readonly="readonly"
										name="intereststarttime"> <i>—</i> <input type="text"
										class="uc_fsiptime" id="interestendtime"
										onclick="WdatePicker();" readonly="readonly"
										name="interestendtime"> <span><a
										href="javascript:void(0)"
										onclick="findinterestdata('interestSearchresult','interestPagination');">查询</a></span>
								</div>
							</div>
							<ul class="uc_fslist">
								<ol class="uc_fsl_title">
									<li class="uc_fsl165">时间</li>
									<li class="uc_fsl200">类型</li>
									<li class="uc_fsl100">收入</li>
									<li class="uc_fsl100">支出</li>
									<!--  
									<li class="uc_fsl100">余额</li>
									-->
								</ol>
								<div>
									<div id="interestSearchresult"></div>
								</div>
							</ul>
							<div class="uc_tpage uc_fspage">
								<div id="interestPagination"></div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<%@include file="../common/footer.jsp"%>
	<%@ include file="../common/dsp.jsp"%>
</body>
</html>