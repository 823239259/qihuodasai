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
<title>我要提现 - 维胜</title>
<link rel="stylesheet" href="${ctx}/static/css/uc.css?v=20150918" type="text/css">
<link href="${ctx}/static/css/public.css" rel="stylesheet"
	type="text/css">
<link href="${ctx}/static/css/pagination.css" rel="stylesheet"
	type="text/css" />
<script src="${ctx}/static/script/common/jquery.pagination.js"
	type="text/javascript"></script>
<script type='text/javascript'
	src="${ctx}/static/script/drawmoney/drawmoney.js?v=20150918"></script>
<script type='text/javascript'
	src="${ctx}/static/script/common/dateUtils.js"></script>
<script type='text/javascript'
	src="${ctx}/static/script/common/ZeroClipboard.min.js"></script>
<title>维胜</title>
<script type="text/javascript">
	var tab =
<%=tab%>
	;

	var isOut = false;
	$(document).ready(function() {
		addClickEvent();
		//addHoverEvent();
	});

	function addClickEvent() {
		$(".uc_bcbdown").click(function(event) {
			event.stopPropagation();
			$('.uc_uc_option').hide();
			$(".uc_bc_bank").show();
		});

		$(document).click(function() {
			if (isOut == false) {
				$(".uc_bc_bank").hide();
			}

		});

		$(".uc_bc_bank a").each(function() {
			$(this).click(function() {
				var value = $(this).html();
				var bankname = $(this).attr("data-id");
				$('#bankname').val(value);
				$('#bankname').attr("data-id", $(this).attr("data-id"));
				$(".uc_bc_slbank").val(value);
				$(".uc_bc_bank").hide();
				$('.uc_bc_slbank').css('color', '#666');
			});
		});

		$('.uc_bcbanks li').each(function() {

			$('.uc_bcbanks li p').hide();

		});
		$('.uc_bcbanks li').each(function() {
			$(this).hover(function() {
				$(this).children('p').show();
			}, function() {
				$('.uc_bcbanks li p').hide();
			});

		});

	};
	function clearBankNoNumber(event,obj){ 
	    //响应鼠标事件，允许左右方向键移动 
	    event = window.event||event; 
	    if(event.keyCode == 37 || event.keyCode == 39){ 
	        return; 
	    } 
	    //先把非数字的都替换掉，除了数字和. 
	    obj.value = obj.value.replace(/[^\d]/g,""); 
	    if(obj.value.length==1){
	    	obj.value=obj.value.replace(/[^0-9]/g,'')
	    }else{
	    	obj.value=obj.value.replace(/\D/g,'')
	    }
	  }
</script>
</head>
<body>

	<!--顶部 -->
	<%@include file="../common/header.jsp"%>
	<!-- 浮动层 -->
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
							<div class="uc_wd">

								<c:choose>

									<c:when test="${ empty requestScope.userverified.idcard}">
										<div class="uc_wdtitle">
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

										</em>元

									</span></li>
									<li><label>提现金额：</label> <input type="text"
										class="uc_wdip" onKeyUp="javascript:clearNoDouble(event,this)"
										name="money" id="money" style="width:310px;"> <span>元</span></li>
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
								<div class="uc_paypromt">
									<ul>
										<li class="first">
											<h4>最快<i>10分钟</i>到账</h4>
											<p>
											1、工作日/时间：最快10分钟到账，最慢6小时到账。<br>
											2、非工作日/时间：所有提现下一工作日到账。
											</p>	
											

											
										</li>
										<li>
											<h4>支持银行多达<i>15</i>家</h4>
											<p>推荐您使用工商银行、建设银行、招商银行、农业银行提现，到账最快</p>	
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

