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
<title>我要提现 - 维胜金融-中国领先的国际期货及衍生品互联网交易平台</title>
<link rel="stylesheet" href="${ctx}/static/css/uc.css?v=${v}" type="text/css">
<link href="${ctx}/static/css/public.css?v=${v}" rel="stylesheet" type="text/css">
<link href="${ctx}/static/css/pagination.css?v=${v}" rel="stylesheet" type="text/css" />
<script src="${ctx}/static/script/common/jquery.pagination.js?v=${v}" type="text/javascript"></script>
<script type='text/javascript' src="${ctx}/static/script/drawmoney/drawmoney.js?v=${v}"></script>
<script type='text/javascript' src="${ctx}/static/script/drawmoney/citySelect.js?v=${v}"></script>
<script type='text/javascript' src="${ctx}/static/script/common/dateUtils.js?v=${v}"></script>
<script type='text/javascript' src="${ctx}/static/script/common/ZeroClipboard.min.js?v=${v}"></script>
<script src="${ctx}/static/script/drawmoney/citySelect.js?v=${v}"></script>
<script type="text/javascript">
	var tab = <%=tab%>;
</script>
<style>
	#nav_my {color: #ffcc33; border-bottom:2px solid #ffcc33; padding-bottom: 26px;}
	#demo{display: inline-block; float: left;}
    #demo h3{height:32px; line-height:32px}
    #demo p{line-height:24px}
    pre{margin-top:10px; padding:6px; background:#f7f7f7}
    .address label{float: left;display: inline-block;width: 110px;text-align: right;height: 35px;line-height: 35px;vertical-align: middle;font-size: 14px;color: #666;margin-right: 15px;}
	.address {height: 35px; margin-bottom: 20px;}
	.address select {color: #666666; border: 1px solid #e5e5e5; padding: 8px 0px;}
	.address input {font-size: 14px; color: #666;width: 160px; height: 33px; border: 1px solid #e5e5e5; border-top: 1px solid #d3d3d3; outline: none; padding: 0 15px; vertical-align: middle; line-height: 33px\9;}    
	.address .addressname{height: 35px; line-height: 35px; vertical-align: middle; font-size: 14px; color: #666; margin-left: 10px;}
</style>
</head>
<body>
	<!--顶部 -->
	<%@include file="../common/header.jsp"%>
	<!-- 浮动层 -->
	<input type="hidden" id="withdrawSetting" value="${withdrawSetting}">
	<input type="hidden" id="handleFee" value="${handleFee}">
	<div class="uc">
		<!--个人中心导航 -->
		<%@ include file="../common/leftnav.jsp"%>
		<div class="uc_mianbar">
			<div class="uc_pay" id="banktab">
				<ul class="uc_paynav">
					<li><a href="javascript:void(0)" class="on">我要提现</a></li>
					<c:if test="${!empty requestScope.userverified.idcard}">
						<li><a href="javascript:void(0)">银行卡管理</a></li>
						<li><a href="javascript:void(0)">提现记录</a></li>
					</c:if>
				</ul>
				<div class="tabcon">
					<div class="subtab">
						<form id="quickPayForm" action="${ctx}/pay/quickPayment"
							method="post" target="_blank">
							<!-- 我要提款 -->
							<div class="uc_wd" style="width: 700px; margin-left: 104px;">
								<c:choose>
									<c:when test="${ empty requestScope.userverified.idcard}">
										<div class="uc_wdtitle" style="margin-left: 62px;">
											<i id="warnidcard"> 首次提现，请先实名认证，请前往 <a
												href="${ctx}/securityInfo/secInfo" class="a1"> 安全信息</a>
											</i>
										</div>
									</c:when>
									<c:when test="${ fn:length(requestScope.banks) == 0}">
										<div class="uc_wdtitle">
											<i id="warncard">未绑定银行卡，请先绑定银行卡。前往<a
												href="javascript:void(0)" onclick="bankmanage();">银行卡管理</a>进行绑定
											</i>
										</div>
									</c:when>
									<c:when test="${ empty requestScope.userverified.drawMoneyPwd}">
										<div class="uc_wdtitle">
											<i id="warnidcard"> 首次提现，请设置提现密码，请前往 <a
												href="${ctx}/securityInfo/secInfo" class="a1"> 安全信息</a>
											</i>
										</div>
									</c:when>
								</c:choose>
								<div class="floatlayer">
									<!-- 提现银行 -->
									<div class="uc_i_option uc_wd_bank" style="display: none;">
										<c:if test="${!empty requestScope.banks}">
											<c:forEach items="${requestScope.banks}" var="bankcard">
												<a href="javascript:void(0)" data-id="${bankcard.card }"
													data-name="${bankcard.bank }">${bankcard.bankvalue}:${bankcard.card }</a>
											</c:forEach>
										</c:if>
										<c:if test="${!empty requestScope.userverified.idcard}">
											<a href="javascript:void(0)" data-id="addcard">添加银行卡</a>
										</c:if>
									</div>
								</div>
								<ul class="uc_wdlist">
									<li><label>账户余额：</label> <span> <em id="balance">
												<fmt:formatNumber value="${requestScope.user.avlBal}"
													type="currency" pattern="0.00#" />
										</em>元&nbsp;<b>(累计免提现手续费金额:<c:if test="${user.countOperateMoney  == null || user.countOperateMoney == ''}">
																				<em>0元</em>
																		</c:if>
																		<c:if test="${user.countOperateMoney  != null && user.countOperateMoney != ''}">
																				<em>${user.countOperateMoney }元</em>
																		</c:if>)</b>
									</span></li>
									<li><label>提现金额：</label> <input type="text"
										class="uc_wdip" onKeyUp="javascript:clearNoDouble(event,this)"
										name="money" id="money" style="width:310px;"> <span>元&nbsp;<b id = "moneyTip" class = "uc_wdpassword"></b></span></li>
									<li><label>提现银行：</label> <c:choose>
											<c:when test="${fn:length(requestScope.banks) >= 1}">
												<input type="text" id="bankcard" name="bankcard"
													data-name="${banks[0].bank}"
													data-id="${banks[0].card}"
													value="${banks[0].bankvalue}:${banks[0].card}"
													disabled="true" class="uc_wdbanksel">
											</c:when>
											<c:otherwise>
												<input type="text" id="bankcard" name="bankcard"
													disabled="true" class="uc_wdbanksel">
											</c:otherwise>
										</c:choose> <a href="javascript:void(0);" class="uc_wdbank"></a></li>
									<li>
										<label>提现手续费：</label>
										<span>
											<em id="handle-fee">0.00</em>元
										</span>
									</li>
									<li>
										<label>实际到账金额：</label>
										<span>
											<em id="actual-money">0.00</em>元
										</span>
									</li>
									<li><label>提现密码：</label>
									 <input type="password"
										class="uc_wdip" id="drawpwd" name="drawpwd"  style="width:310px;">
										 <input type="password"
										class="uc_wdip22" id="pwds" name="pwds"  style="display:none;">
										 <a
										href="${ctx}/securityInfo/secInfo" class="uc_wdpassword">
											<c:choose>
												<c:when test="${empty requestScope.userverified.drawMoneyPwd}">
											  	 设置密码
											</c:when>
												<c:otherwise>
												忘记密码
											</c:otherwise>
											</c:choose>
									</a></li>
									  <c:choose>
		                				<c:when test="${isNeedCode}">
											<li id="randcode">
											<label>验证码：</label> 
											<input type="text"
												class="uc_wdip" maxlength="6"
												name="code" id="code" style="width:150px;"> 
												<span><img class="validateCode" onclick="createcode();" id="validateCode" src="validate.code" style=" position:absolute; margin-left:5px;" width="115" height="35"></span>
											</li>
									    </c:when>
									    <c:otherwise>
						                		<li id="randcode" style="display:none">
													<label>验证码：</label> 
													<input type="text"
														class="uc_wdip" maxlength="6"
														name="code" id="code" style="width:150px;"> 
														<span><img class="validateCode" id="validateCode" onclick="createcode();" src="validate.code" style=" position:absolute; margin-left:5px;" width="115" height="35"></span>
												</li>
						                	</c:otherwise>
						                </c:choose>
								</ul>
								<div class="uc_paybtn uc_wdbtn">
									<a href="javascript:void(0)" onclick="drawmoney();">下一步</a>
								</div>
								<div class="uc_paypromt" style="left: -86px;">
									<ul>
										<li class="first" style="border-right: 1px solid #e9e9e9;">
											<h4>最快<i>10分钟</i>到账</h4>
											<p style="line-height: 20px;">
											1、到账时间：工作日09：00——16:30申请，24小时内到账，最快5分钟到账。其余时间申请，将在下一个工作日到账。<br>
											2、提现手续费 :</br><span style="margin-left: 66px;">1%（适用于充值后，未实际操盘金额）</span></br><span style="margin-left: 66px;">0元（适用于操盘用户提现）。</span></span>
											</p>
										</li>
										<li>
											<h4>支持银行多达<i>15</i>家</h4>
											<p style="line-height: 20px;">推荐您使用工商银行、建设银行、招商银行、农业银行提现，到账最快</p>	
										</li>
									</ul>
									<div class="uc_paypromtbom">温馨提示：禁止洗钱、信用卡套现、虚假交易等行为，一经发现并确认，将终止该账户的使用。</div>
								</div>
							</div>
						</form>
					</div>
					<div class="subtab">
						<form id="netbank" action="${ctx}/pay/quickPayment" method="post"
							target="_blank">
							<div class="uc_bc">
								<!-- 未绑定的时候，这个div隐藏 -->
								<c:if test="${!empty requestScope.banks}">
									<div class="uc_bc_binding" style="">
										<div class="uc_bctitle">
											<i class="uc_bcadd">已绑定银行卡</i>
										</div>
										<ul class="uc_bcbanks">
											<c:forEach items="${requestScope.banks}" var="bankcard">
												<c:choose>
													<c:when test="${bankcard.isdefault=='1'}">
														<li class="on"><span>${bankcard.card}</span> <a
															href="javascript:void(0)"><img
																src="${ctx}/static/${bankcard.bankimgpath}"></a> <i></i>
															<p>
																<a href="javascript:void(0)"
																	onclick="delcard('${bankcard.id}')" class="uc_ucdele">删除</a>
															</p></li>
													</c:when>
													<c:otherwise>
														<li><span>${bankcard.card}</span> <a
															href="javascript:void(0)"><img
																src="${ctx}/static/${bankcard.bankimgpath}"></a>
															<p>
																<a href="javascript:void(0)"
																	onclick="setdefaulcard('${bankcard.id}')"
																	class="uc_ucdele">默认</a> <a href="javascript:void(0)"
																	onclick="delcard('${bankcard.id}')" class="uc_ucdele">删除</a>
															</p></li>
													</c:otherwise>
												</c:choose>
											</c:forEach>
										</ul>
									</div>
								</c:if>
								<div class="uc_bctitle">
									<i>添加银行卡</i>
								</div>
								<!-- 弹出层 -->
								<div class="uc_floatlayer">
									<!-- 开户银行 -->
									<div class="uc_bc_option uc_bc_bank" style="display: none;">
									   <c:forEach varStatus="status" items="${supportBanks}" var="supportBank">
									   		<a href="javascript:void(0)" data-id="${supportBank.abbreviation}">${supportBank.bankName}</a>
									   </c:forEach>
									</div>
									<!-- 所在地 -->
								</div>
								<ul class="uc_bcinfo">
									<li><label>开户名：</label> <span>${userverified.tname }</span>
									</li>
									<li><label>开户银行：</label> <input type="text" readonly="readonly"
										class="uc_bcsel uc_bc_slbank" id="bankname" value="请选择">
										<a href="javascript:void(0);" class="uc_bcbdown"></a></li>
									<div class="address">
										<label>开户地址：</label>
										<div id="demo">
								            <div id="city"> 
											    <select class="prov"></select>  
											    <select class="city" disabled="disabled"></select>
											</div>
	      								</div>
	      								<span class="addressname">具体地址：</span><input type="text" name="address">
									</div>
									<li><label>银行卡号：</label> <input type="text" name="card"
										maxlength="19" id="card"
										onKeyUp="javascript:clearBankNoNumber(event,this)" class="uc_bcip">
										<em>请输入借记卡卡号，不支持存折</em>
									</li>
									<li><label>确认卡号：</label> <input type="text"
										name="agincard" maxlength="19" id="agincard"
										onKeyUp="javascript:clearBankNoNumber(event,this)" class="uc_bcip">
									</li>

								</ul>
								<div class="uc_paybtn uc_bcbtn">
									<div class="uc_paybtn uc_bcbtn" style="margin:22px auto;">
										<a href="javascript:void(0)" id="savecard">保存</a>
									</div>
								</div>
							</div>
						</form>

					</div>

					<div class="subtab">
						<div class="uc_recharge">
							<ul class="uc_retitle">
								<li class="uc_re_num">时间</li>
								<li class="uc_re_num">提现银行</li>
								<li class="uc_re_num2">提现金额</li>
								<li class="uc_re_num2">状态</li>
							</ul>
							<ul class="uc_relist">
								<div id="Searchresult"></div>
							</ul>
							<div id="Pagination"></div>
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

