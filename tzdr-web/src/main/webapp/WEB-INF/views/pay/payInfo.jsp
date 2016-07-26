<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp"%>
   <%@include file="../common/import-fileupload-js.jspf"%>
   <%@include file="../common/import-artDialog-js.jspf"%>
<html xmlns="http://www.w3.org/1999/xhtml" lang="zh">
   <%
   		String tab=request.getParameter("tab");
   
   %>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>账户充值 - 维胜</title>
 <link rel="stylesheet" href="${ctx}/static/css/uc.css?version=20150721"  type="text/css">
 <link href="${ctx}/static/css/public.css" rel="stylesheet" type="text/css">	
 <link href="${ctx}/static/css/pagination.css" rel="stylesheet" type="text/css" /> 
 <script src="${ctx}/static/script/common/jquery.pagination.js" type="text/javascript"></script>
 
 <script type='text/javascript' src="${ctx}/static/script/pay/pay.js?version=20150721"></script>
  <script type='text/javascript' src="${ctx}/static/script/pingpp.js?version=20150721"></script>
 <script type='text/javascript' src="${ctx}/static/script/common/dateUtils.js"></script>
 <script type='text/javascript' src="${ctx}/static/script/common/ZeroClipboard.min.js"></script>

<script type="text/javascript">
var tab=<%=tab%>;
</script>
<style>
	#nav_my {color: #ffcc33; border-bottom:2px solid #ffcc33; padding-bottom: 26px;}
</style>
</head>
<body>

<!--顶部 -->
	<%@include file="../common/header.jsp"%>
		<!-- 浮动层 -->
	<!-- 浮动层 -->
		<div class="floatlayer">
		<div class="uc_pay_promt" style="display:none;">
			<i class="uc_pp_arrow"></i>
			<p>	
				充值手续费由第三方支付平台收取：</br>
				快捷支付：手续费0.7%</br>
				网银充值：免手续费</br>
				支付宝转账：免手续费
			</p>
		</div>
	<div class="fl_mask" style="display:none;"></div>
	<div class="fl_box fl_uc_banks" id="issucessdiv"  style="display:none;">
		<div class="fl_navtitle">
			<h3>充值提示</h3>
			<a href="javascript:void(0)" class="close"></a>
		</div>
		<div class="fl_uc_main">
			<p class="fl_uc_charge">请在新打开的网上银行<br>页面完成付款<br>充值遇到问题请咨询客服 <br>400-852-8008</p>
			
		</div>
		<div class="fl_uc_btn">
			<a href="javascript:void(0)" class="fl_uc_cancelbtn">返回修改</a>
			<a href="javascript:void(0)" class="fl_uc_surebtn">已完成支付</a>
		</div>
	</div>
	</div>
	
<div class="uc">
	<!--个人中心导航 -->
	<%@ include file="../common/leftnav.jsp"%>
	<div class="uc_mianbar">
		<div class="uc_pay" id="banktab">			
			<ul class="uc_paynav">
				<!--  
				<li><a href="javascript:void(0);" class="on">快捷支付</a></li>
				-->
				<li><a href="javascript:void(0);">支付宝充值</a></li>
				<!-- <li><a href="javascript:void(0);">网银充值</a></li> 
				<li><a href="javascript:void(0);">支付宝转账</a></li> -->
				<li><a href="javascript:void(0);">银行转账</a></li>
				<li><a href="javascript:void(0);">充值记录</a></li>
			</ul>
			 <div class="tabcon">
			 <!--  
			    <div class="subtab">
				 	<form id="quickPayForm" action="${ctx}/pay/quickPayment" method="post" target="_blank">
						<div class="uc_paytitle">
							<img src="${ctx }/static/images/uc/pay_01.gif">
							
							<c:choose> 
								<c:when test="${requestScope.userverified.status=='1'}">
								<i id="warnidcard">
									 首次快捷支付充值，请先实名认证，请前往
									 <a href="<%=basePath%>/securityInfo/secInfo" class="a1">
									 安全信息</a>
									 进行认证，或选择其他充值方式
								</i>
								</c:when>
								<c:when test="${ empty requestScope.userverified.idcard}">
								<i id="warnidcard">
									 首次快捷支付充值，请先实名认证，请前往
								 <a href="<%=basePath%>/securityInfo/secInfo" class="a1">
								   安全信息</a>
								   进行认证，或选择其他充值方式
								</i>
								</c:when>
								<c:otherwise>
								    <i id="warnidcard"></i>
									<i>无需开通网银，有银行卡就能支付(单笔单卡4万，每日累计20万)</i>
								</c:otherwise>
							</c:choose>
						</div>
						<div class="uc_paybank">
							<h3 class="uc_pbtitle">充值银行</h3>
							<ul class="uc_banklist">
								<li><input type="radio" name="banktype" value="boc" checked="checked"><a href="javascript:void(0);"><img src="${ctx }/static/images/banks/bank_10.jpg"></a><i></i></li>
								<li><input type="radio" name="banktype" value="ceb" ><a href="javascript:void(0);"><img src="${ctx }/static/images/banks/bank_11.jpg"></a></li>
								<li><input type="radio" name="banktype" value="abc"><a href="javascript:void(0);"><img src="${ctx }/static/images/banks/bank_03.jpg"></a></li>
								<li><input type="radio" name="banktype" value="ccb"><a href="javascript:void(0);"><img src="${ctx }/static/images/banks/bank_04.jpg"></a></li>
								<li><input type="radio" name="banktype" value="citic"><a href="javascript:void(0);"><img src="${ctx }/static/images/banks/bank_15.jpg"></a></li>
								<li><input type="radio" name="banktype" value="cib"><a href="javascript:void(0);"><img src="${ctx }/static/images/banks/bank_13.jpg"></a></li>
							</ul>
						</div>
						<div class="uc_paymoney">
							<h3 class="uc_pbtitle">填写充值金额</h3>
							<ul class="uc_pblist">
								<li><label>账户余额：</label><span><i>
								<fmt:formatNumber value="${requestScope.user.avlBal}" type="currency" pattern="0.00#"/>
								
								</i>元</span></li>
								<c:if test="${ not empty requestScope.userverified.idcard}">
									<li><label>持卡人姓名：</label><span>${requestScope.userverified.tname}</span></li>
								</c:if>
								<li><label>储蓄卡号：</label><input type="text" id="bankCard" name="bankCard" maxlength="30" class="uc_p_ip2" onKeyUp="javascript:clearNoNumber(event,this)"></li>
								<li><label>充值金额：</label><input type="text" id="paymoney" name="paymoney" maxlength="10"  class="uc_p_ip2"  onKeyUp="javascript:clearNoNumber(event,this)" ><em></em><span>元</span></li>
								<li><label>充值手续费：</label><span><b id="poundage">0</b>元</span>
									<a href="javascript:void(0)" class="uc_ppbtn"></a>
									<font>网银充值、支付宝转账，免手续费</font>
								</li>
								<li><label>实际支付金额：</label><span><b id="realpaymoney">0</b>元</span></li>
							</ul>
						</div>
						<div class="uc_paybtn">
						<a href="javascript:void(0);" onclick="doPayment();" target="_blank">立即充值</a></div>
					 </form>
				</div>
				-->
				<%-- <div class="subtab">
					<form id="netbank" action="" method="post" target="_blank"  >
						
						<div class="uc_olbank">
						<h3 class="uc_pbtitle">选择充值银行</h3>
							<ul class="uc_banklist" style="height:288px"> 
								<c:forEach items="${supportBanks}" var="supportBank" varStatus="status">
									<c:choose>
										<c:when test="${status.count==1}">
											<li><input type="radio" value="${supportBank.abbreviation}" name="banktype" checked="checked"><a href="javascript:void(0);"><img src="${ctx}/${supportBank.iconPath}"></a><i></i></li>							
										</c:when>
										<c:otherwise>
											<li><input type="radio" value="${supportBank.abbreviation}"  name="banktype"><a href="javascript:void(0);"><img src="${ctx }/${supportBank.iconPath}"></a></li>
										</c:otherwise>
									</c:choose>
								</c:forEach>
						  </ul>
						<div class="uc_olbankmore uc_oldown1" style="display:block;"><a href="javascript:void(0);"	class="uc_olbankdown"><img src="${ctx }/static/images/uc/pay_bank.gif"></a></div>
						<div class="uc_olbankmore uc_olup1" style="display:none;"><a href="javascript:void(0);" class="uc_olbankup"><img src="${ctx }/static/images/uc/pay_bankup.gif"></a></div>
					</div>
					<div class="uc_olmoney">
						<h3 class="uc_pbtitle">填写充值金额</h3>
						<ul class="uc_pblist">
							<li><label>账户余额：</label><span><i>
							<fmt:formatNumber value="${requestScope.user.avlBal}" type="currency" pattern="0.00#"/>
								
							</i>元</span></li>
							<li><label>充值金额：</label><input type="text" name="money" id="money"  onKeyUp="javascript:clearNoNumber(event,this)"  class="uc_p_ip3" ><span>元</span></li>
						</ul>
						<div class="uc_paybtn uc_olbtn"><a href="javascript:void(0);" onclick="doNetPayment();">立即充值</a></div>
					</div>
					 <div id="banks_desc">
							<table width="650" border="1" id="icbc_desc" cellpadding="0" cellspacing="0"  class="uc_oltable">
							  <tr>
							    <td colspan="4">工商银行支付额度表</td>
						      </tr>
							  <tr>
							    <td rowspan="2">支付方式</td>
							    <td colspan="2">支付额度</td>
							    <td rowspan="2">开通方式</td>
							  </tr>
							  <tr>
							    <td>每笔限额</td>
							    <td>每日限额</td>
						      </tr>
							  <tr>
							    <td>存量静态密码</td>
							    <td colspan="2">累计300元</td>
							    <td>已无法开通</td>
							  </tr>
							  <tr>
							    <td>电子银行口令卡</td>
							    <td>500</td>
							    <td>1000</td>
							    <td rowspan="4">柜台/网上自助开通</td>
							  </tr>
							  <tr>
							    <td>电子银行口令卡+手机短信验证</td>
							    <td>2000</td>
							    <td>5000</td>
						      </tr>
							  <tr>
							    <td>借记卡U盾</td>
							    <td>100万</td>
							    <td>100万</td>
						      </tr>
							  
							</table>
							  <table id="spdb_desc" width="650" border="1" cellpadding="0" cellspacing="0"  class="uc_oltable">
                                        	
                                        	<tr>
                                                <td colspan="4">上海浦发展银行借记卡支付额度表</td>
                                            </tr>
                                            <tr>
                                            	<td rowspan="2">支付方式</td>
                                                <td colspan="2">支付限额</td>
                                                <td rowspan="2">开通方式</td>
                                            </tr>
                                            <tr>
                                                <td>每笔限额</td>
                                                <td>每日限额</td>
                                            </tr>
                                            <tr>
                                            	<td>动态密码</td>
                                                <td>5万</td>
                                                <td>20万</td>
                                                <td rowspan="2">柜面开通</td>
                                            </tr>
                                            <tr>
                                            	<td>数字证书</td>
                                                <td>10万</td>
                                                <td>20万</td>
                                            </tr>
                                            
                                        </table>
							<table  id="abc_desc" width="650" border="1" cellpadding="0" cellspacing="0"  class="uc_oltable">
							 
                             	<tr>
                                     <td colspan="4">农业银行行借记卡支付额度表 </td>
                                 </tr>
                                 <tr>
                                 	<td rowspan="2">支付方式</td>
                                     <td colspan="2">支付限额</td>
                                     <td rowspan="2">开通方式</td>
                                 </tr>
                                 <tr>
                                     <td>每笔限额</td>
                                     <td>每日限额</td>
                                 </tr>
                                 <tr>
                                 	<td>浏览证书+动态口令卡</td>
                                     <td>1000</td>
                                     <td>3000</td>
                                     <td rowspan="3">柜面开通</td>
                                 </tr>
                                 <tr>
                                 	<td>Key宝总行证书</td>
                                 	<td>无限额</td>
                                     <td>无限额</td>
                                    
                                 </tr>
                                  <tr>
                                 	<td>K码</td>
                                     <td>1000</td>
                                     <td>1000</td>
                                    
                                 </tr>
                             </table>
                               <table id="boc_desc" width="650" border="1" cellpadding="0" cellspacing="0"  class="uc_oltable">
                                        	
                                        	<tr>
                                                <td colspan="4">中国银行借记卡支付额度表</td>
                                            </tr>
                                            <tr>
                                            	<td rowspan="2">支付方式</td>
                                                <td colspan="2">支付限额</td>
                                                <td rowspan="2">开通方式</td>
                                            </tr>
                                            <tr>
                                                <td>每笔限额</td>
                                                <td>每日限额</td>
                                            </tr>
                                            <tr>
                                            	<td>借记卡专业版</td>
                                                <td>5万</td>
                                                <td>50万</td>
                                                <td rowspan="2">柜面开通</td>
                                            </tr>
                                            <tr>
                                            	<td>借记卡网银快付</td>
                                                <td>1000</td>
                                                <td>5000</td>
                                            </tr>
                                             
                                        </table>
                                        <table id="ccb_desc" width="650" border="1" cellpadding="0" cellspacing="0"  class="uc_oltable">
                                        	<colgroup>
                                                <col style="width:230px">
                                                <col style="width:135px">
                                                <col style="width:135px">
                                                <col>
                                            </colgroup>
                                        	<tr>
                                                <td colspan="4">建设银行借记卡/准贷记卡支付额度表</td>
                                            </tr>
                                            <tr>
                                            	<td rowspan="2">支付方式</td>
                                                <td colspan="2">支付限额</td>
                                                <td rowspan="2">开通方式</td>
                                            </tr>
                                            <tr>
                                                <td>每笔限额</td>
                                                <td>每日限额</td>
                                            </tr>
                                            <tr>
                                            	<td>帐户支付</td>
                                                <td>1000</td>
                                                <td>1000</td>
                                                <td rowspan="4">柜面开通</td>
                                            </tr>
                                            <tr>
                                            	<td>动态口令卡</td>
                                                <td>5000</td>
                                                <td>5000</td>
                                              
                                            </tr>
                                            <tr>
                                            	<td>网银盾（U宝）</td>
                                                <td>50万</td>
                                                <td>50万</td>
                                               
                                            </tr>
                                           
                                        </table>
                                         <table id="comm_desc" width="650" border="1" cellpadding="0" cellspacing="0"  class="uc_oltable">
                                        	<colgroup>
                                                <col style="width:230px">
                                                <col style="width:135px">
                                                <col style="width:135px">
                                                <col>
                                            </colgroup>
                                        	<tr>
                                                <td colspan="4">交通银行借记卡支付额度表</td>
                                            </tr>
                                            <tr>
                                            	<td rowspan="2">支付方式</td>
                                                <td colspan="2">支付限额</td>
                                                <td rowspan="2">开通方式</td>
                                            </tr>
                                            <tr>
                                                <td>每笔限额</td>
                                                <td>每日限额</td>
                                            </tr>
                                            <tr>
                                            	<td>借记卡手机注册</td>
                                                <td>5000</td>
                                                <td>5000</td>
                                                <td rowspan="2">柜面开通</td>
                                            </tr>
                                            <tr>
                                            	<td>借记卡证书支付</td>
                                                <td>5万</td>
                                                <td>5万</td>
                                            </tr>
                                            
                                        </table>
                                        <table id="cmb_desc"  width="650" border="1" cellpadding="0" cellspacing="0"  class="uc_oltable">
                                        	<colgroup>
                                                <col style="width:230px">
                                                <col style="width:135px">
                                                <col style="width:135px">
                                                <col>
                                            </colgroup>
                                        	<tr>
                                                <td colspan="4">招商银行支付额度表</td>
                                            </tr>
                                            <tr>
                                            	<td rowspan="2">支付方式</td>
                                                <td colspan="2">支付限额</td>
                                                <td rowspan="2">开通方式</td>
                                            </tr>
                                            <tr>
                                                <td>每笔限额</td>
                                                <td>每日限额</td>
                                            </tr>
                                            <tr>
                                            	<td>大众版网上支付功能</td>
                                                <td>5000</td>
                                                <td>5000</td>
                                                <td>网上自助开通</td>
                                            </tr>
                                            <tr>
                                            	<td>专业版</td>
                                                <td>无限额</td>
                                                <td>无限额</td>
                                                <td rowspan="3">柜面开通</td>
                                            </tr>
                                            
                                        </table>
                                        <table id="cmbc_desc" width="650" border="1" cellpadding="0" cellspacing="0"  class="uc_oltable">
                                        	
                                        	<tr>
                                                <td colspan="4">民生银行借记卡/信用卡支付额度表 </td>
                                            </tr>
                                            <tr>
                                            	<td rowspan="2">支付方式</td>
                                                <td colspan="2">支付限额</td>
                                                <td rowspan="2">开通方式</td>
                                            </tr>
                                            <tr>
                                                <td>每笔限额</td>
                                                <td>每日限额</td>
                                            </tr>
                                            <tr>
                                            	<td>大众版</td>
                                                <td>300</td>
                                                <td>300</td>
                                                <td rowspan="3">柜面开通</td>
                                            </tr>
                                            <tr>
                                            	<td>贵宾版数字证书</td>
                                                <td>5000</td>
                                                <td>5000</td>
                                            </tr>
                                             <tr>
                                            	<td>贵宾版（U宝）</td>
                                                <td>2万</td>
                                                <td>10万</td>
                                            </tr>
                                        </table>
                                         <table id="ceb_desc" width="650" border="1" cellpadding="0" cellspacing="0"  class="uc_oltable">
                                        	<tr>
                                                <td colspan="4">中国光大银行借记卡支付额度表</td>
                                            </tr>
                                            <tr>
                                            	<td rowspan="2">支付方式</td>
                                                <td colspan="2">支付限额</td>
                                                <td rowspan="2">开通方式</td>
                                            </tr>
                                            <tr>
                                                <td>每笔限额</td>
                                                <td>每日限额</td>
                                            </tr>
                                            <tr>
                                            	<td>动态密码支付</td>
                                                <td>5000</td>
                                                <td>5000</td>
                                                <td rowspan="3">柜面开通</td>
                                            </tr>
                                            <tr>
                                            	<td>阳光网盾</td>
                                                <td>20万</td>
                                                <td>50万</td>
                                            </tr>
                                            <tr>
                                            	<td>动态口令牌</td>
                                                <td>50万</td>
                                                <td>50万</td>
                                            </tr>
                                        </table>
                                        <table id="cib_desc" width="650" border="1" cellpadding="0" cellspacing="0"  class="uc_oltable">
                                        	
                                        	<tr>
                                                <td colspan="4">兴业银行借记卡支付额度表 </td>
                                            </tr>
                                            <tr>
                                            	<td rowspan="2">支付方式</td>
                                                <td colspan="2">支付限额</td>
                                                <td rowspan="2">开通方式</td>
                                            </tr>
                                            <tr>
                                                <td>每笔限额</td>
                                                <td>每日限额</td>
                                            </tr>
                                            <tr>
                                            	<td>手机验证</td>
                                                <td>自选1000/5000</td>
                                                <td>自选1000/5000</td>
                                                <td rowspan="3">柜面开通</td>
                                            </tr>
                                            <tr>
                                            	<td>电子支付卡（e卡）</td>
                                                <td>5000</td>
                                                <td>5000</td>
                                            </tr>
                                            <tr>
                                            	<td>证书支付</td>
                                                <td>无限额</td>
                                                <td>无限额</td>
                                            </tr>
                                        </table>
                                         <table id="gdb_desc" width="650" border="1" cellpadding="0" cellspacing="0"  class="uc_oltable">
                                        	<tr>
                                                <td colspan="4">广东发展银行支付额度表</td>
                                            </tr>
                                            <tr>
                                            	<td rowspan="2">支付方式</td>
                                                <td colspan="2">支付限额</td>
                                                <td rowspan="2">开通方式</td>
                                            </tr>
                                            <tr>
                                                <td>每笔限额</td>
                                                <td>每日限额</td>
                                            </tr>
                                            <tr>
                                            	<td>借记卡手机动态验证码</td>
                                                <td>3000</td>
                                                <td>3000</td>
                                                <td>网上自助开通</td>
                                            </tr>
                                            <tr>
                                            	<td>借记卡KEY盾</td>
                                                <td>30万</td>
                                                <td>30万</td>
                                                <td>柜面开通</td>
                                            </tr>
                                             
                                        </table>
                                        <table id="hxb_desc" width="650" border="1" cellpadding="0" cellspacing="0"  class="uc_oltable">
                                        	
                                        	<tr>
                                                <td colspan="4">华夏银行借记卡支付额度表 </td>
                                            </tr>
                                            <tr>
                                            	<td rowspan="2">支付方式</td>
                                                <td colspan="2">支付限额</td>
                                                <td rowspan="2">开通方式</td>
                                            </tr>
                                            <tr>
                                                <td>每笔限额</td>
                                                <td>每日限额</td>
                                            </tr>
                                            <tr>
                                            	<td>直接支付</td>
                                                <td>300</td>
                                                <td>1000</td>
                                                <td rowspan="2">网上自助开通</td>
                                            </tr>
                                               <tr>
                                            	<td>电子钱包签约</td>
                                                <td>5万</td>
                                                <td>10万</td>
                                               
                                            </tr>
                                        </table>
                                         <table id="psbc_desc" width="650" border="1" cellpadding="0" cellspacing="0"  class="uc_oltable">
                                        	
                                        	<tr>
                                                <td colspan="4">中国邮政储蓄银行借记卡支付额度表 </td>
                                            </tr>
                                            <tr>
                                            	<td rowspan="2">支付方式</td>
                                                <td colspan="2">支付限额</td>
                                                <td rowspan="2">开通方式</td>
                                            </tr>
                                            <tr>
                                                <td>每笔限额</td>
                                                <td>每日限额</td>
                                            </tr>
                                            <tr>
                                            	<td>手机动态密码版</td>
                                                <td>3000</td>
                                                <td>3000</td>
                                                <td>柜面开通</td>
                                            </tr>
                                           
                                        </table>
                                         <table id="bea_desc" width="650" border="1" cellpadding="0" cellspacing="0"  class="uc_oltable">
                                        	<tr>
                                                <td colspan="4">东亚银行借记卡支付额度表 </td>
                                            </tr>
                                            <tr>
                                            	<td rowspan="2">支付方式</td>
                                                <td colspan="2">支付限额</td>
                                                <td rowspan="2">开通方式</td>
                                            </tr>
                                            <tr>
                                                <td>每笔限额</td>
                                                <td>每日限额</td>
                                            </tr>
                                            <tr>
                                            	<td>手机动态密码</td>
                                                <td>5000</td>
                                                <td>2万</td>
                                                <td rowspan="2">柜面开通</td>
                                            </tr>
                                            <tr>
                                            	<td>USB-KEY/USB-KEY+口令卡</td>
                                                <td colspan="2">100万</td>
                                            </tr>
                                        </table>
                                        <table id="cib_desc"  width="650" border="1" cellpadding="0" cellspacing="0"  class="uc_oltable" >
                                        	<tr>
                                                <td colspan="4">兴业银行借记卡支付额度表 </td>
                                            </tr>
                                            <tr>
                                            	<td rowspan="2">支付方式</td>
                                                <td colspan="2">支付限额</td>
                                                <td rowspan="2">开通方式</td>
                                            </tr>
                                            <tr>
                                                <td>每笔限额</td>
                                                <td>每日限额</td>
                                            </tr>
                                            <tr>
                                            	<td>手机验证</td>
                                                <td>自选1000/5000</td>
                                                <td>自选1000/5000</td>
                                                <td rowspan="3">柜面开通</td>
                                            </tr>
                                            <tr>
                                            	<td>电子支付卡（e卡）</td>
                                                <td>5000</td>
                                                <td>5000</td>
                                            </tr>
                                            <tr>
                                            	<td>证书支付</td>
                                                <td>无限额</td>
                                                <td>无限额</td>
                                            </tr>
                                        </table>
                                         <table id="bjb_desc" width="650" border="1" cellpadding="0" cellspacing="0"  class="uc_oltable">
                                        	
                                        	<tr>
                                                <td colspan="4">北京银行借记卡支付额度表</td>
                                            </tr>
                                            <tr>
                                            	<td rowspan="2">支付方式</td>
                                                <td colspan="2">支付限额</td>
                                                <td rowspan="2">开通方式</td>
                                            </tr>
                                            <tr>
                                                <td>每笔限额</td>
                                                <td>每日限额</td>
                                            </tr>
                                            <tr>
                                            	<td>普通版</td>
                                                <td colspan="">累计300</td>
                                                <td colspan="">累计300	</td>
                                                 <td rowspan="4">柜台开通</td>
                                            </tr>
                                            <tr>
                                            	<td>动态密码版</td>
                                                <td colspan="">1万</td>
                                                <td colspan="">1万	</td>
                                               
                                            </tr>
                                            
                                           
                                            <tr>
                                            	<td>证书版</td>
                                                <td colspan="2">100万</td>
                                            </tr>
                                        </table>
                                         <table id="citic_desc" width="650" border="1" cellpadding="0" cellspacing="0"  class="uc_oltable" >
                                        	
                                        	<tr>
                                                <td colspan="4">中信银行借记卡支付额度表 </td>
                                            </tr>
                                            <tr>
                                            	<td rowspan="2">支付方式</td>
                                                <td colspan="2">支付限额</td>
                                                <td rowspan="2">开通方式</td>
                                            </tr>
                                            <tr>
                                                <td>每笔限额</td>
                                                <td>每日限额</td>
                                            </tr>
                                            <tr>
                                            	<td>动态密码支付</td>
                                                <td>客户自行设定</td>
                                                <td>客户自行设定</td>
                                                <td rowspan="3">柜面开通</td>
                                            </tr>
                                            <tr>
                                            	<td>文件证书</td>
                                                <td>1000</td>
                                                <td>5000</td>
                                            </tr>
                                             <tr>
                                            	<td>移动证书</td>
                                                <td>100万</td>
                                                <td>100万</td>
                                            </tr>
                                        </table>
                                        
                                         <table id="shb_desc" width="650" border="1" cellpadding="0" cellspacing="0"  class="uc_oltable" >
                                        	
                                        	<tr>
                                                <td colspan="4">上海银行借记卡支付额度表 </td>
                                            </tr>
                                            <tr>
                                            	<td rowspan="2">支付方式</td>
                                                <td colspan="2">支付限额</td>
                                                <td rowspan="2">开通方式</td>
                                            </tr>
                                            <tr>
                                                <td>每笔限额</td>
                                                <td>每日限额</td>
                                            </tr>
                                             <tr>
                                            	<td>借记卡专业版文件证书</td>
                                                <td colspan="">5000	</td>
                                                <td colspan="">5000	</td>
                                                <td rowspan="5">柜台开通</td>
                                            </tr>
                                            <tr>
                                            	<td>借记卡专业版USB-KEY</td>
                                                <td colspan="">5万	</td>
                                                <td colspan="">5万	</td>
                                                
                                            </tr>
                                            <tr>
                                            	<td>信用卡大众版</td>
                                                <td colspan="">3000	</td>
                                                <td colspan="">6000	</td>
                                             
                                            </tr>
                                            <tr>
                                            	<td>信用卡专业版文件证书</td>
                                                <td colspan="">3000	</td>
                                                <td colspan="">6000	</td>
                                              
                                            </tr>
                                             <tr>
                                            	<td>信用卡专业版USB-KEY</td>
                                                <td colspan="">5000	</td>
                                                <td colspan="">10000</td>
                                               
                                            </tr>
                                            
                                            
                                        </table>
                                        <table id="spab_desc"  width="650" border="1" cellpadding="0" cellspacing="0"  class="uc_oltable" >
                                        	<tr>
                                                <td colspan="4">平安银行支付额度表 </td>
                                            </tr>
                                            <tr>
                                            	<td rowspan="2">支付方式</td>
                                                <td colspan="2">支付限额</td>
                                                <td rowspan="2">开通方式</td>
                                            </tr>
                                            <tr>
                                                <td>每笔限额</td>
                                                <td>每日限额</td>
                                            </tr>
                                            <tr>
                                            	<td>借记卡手机动态密码</td>
                                                <td>客户自行设置</td>
                                                <td>客户自行设置</td>
                                                <td rowspan="2">柜面开通</td>
                                            </tr>
                                             <tr>
                                            	<td>信用卡网上直接支付</td>
                                                <td>2500</td>
                                                <td>2500</td>
                                            </tr>
                                           
                                        </table>
                                         <table id="wzcb_desc"  width="650" border="1" cellpadding="0" cellspacing="0"  class="uc_oltable" >
                                        	<tr>
                                                <td colspan="4">温州银行借记卡支付额度表 </td>
                                            </tr>
                                            <tr>
                                            	<td rowspan="2">支付方式</td>
                                                <td colspan="2">支付限额</td>
                                                <td rowspan="2">开通方式</td>
                                            </tr>
                                            <tr>
                                                <td>每笔限额</td>
                                                <td>每日限额</td>
                                            </tr>
                                            <tr>
                                            	<td>大众版网银</td>
                                                <td>300</td>
                                                <td>300</td>
                                                <td rowspan="3">付款金额超过1000元，需要专业版网银证书进行验证。</td>
                                            </tr>
                                            <tr>
                                            	<td>大众版网银支付功能并办理手机验</td>
                                                <td>800</td>
                                                <td>800</td>
                                            </tr>
                                            <tr>
                                            	<td>专业版网银支付功能</td>
                                                <td>自行设置</td>
                                                <td>自行设置</td>
                                            </tr>
                                        </table>
                                         <table id="shrcb_desc"  width="650" border="1" cellpadding="0" cellspacing="0"  class="uc_oltable" >
                                        	<tr>
                                                <td colspan="4">上海农商银行借记卡支付额度表 </td>
                                            </tr>
                                            <tr>
                                            	<td rowspan="2">支付方式</td>
                                                <td colspan="2">支付限额</td>
                                                <td rowspan="2">开通方式</td>
                                            </tr>
                                            <tr>
                                                <td>每笔限额</td>
                                                <td>每日限额</td>
                                            </tr>
                                            <tr>
                                            	<td>卡号密码支付</td>
                                                <td>2000</td>
                                                <td>5000</td>
                                                <td rowspan="4">柜面开通</td>
                                            </tr>
                                            <tr>
                                            	<td>短信专业版支付</td>
                                                <td>1000</td>
                                                <td>5000</td>
                                            </tr>
                                            <tr>
                                            	<td>证书专业版支付</td>
                                                <td>50</td>
                                                <td>100</td>
                                            </tr>
                                             
                                             <tr>
                                            	<td>手机支付</td>
                                                <td>5000</td>
                                                <td>5000</td>
                                               
                                            </tr>
                                        </table>
					</div>
					<div class="uc_olpromt">
						<h3>温馨提示：</h3>
						<p>1、为了您的资金安全，您的账户资金将由第三方银行托管；</br>2、充值前请注意您的银行卡充值限制，以免造成不便；</br>3、禁止洗钱、信用卡套现，虚假交易等行为，一经发现并确认，将终止该账户的使用；</br>4、为了您的资金安全，建议充值前进行实名认证，手机绑定、设置提现密码；</br>5、如果充值遇到任何问题，请联系客服：400-852-8008.</p>
					</div>
				</form>
			</div> --%>
			<div class="subtab">
					<form id="netbank" action="" method="post" target="_blank"  >
					<div class="uc_olmoney">
						<ul class="uc_pblist">
							<li><label>账户余额：</label><span><i>
							<fmt:formatNumber value="${requestScope.user.avlBal}" type="currency" pattern="0.00#"/>
								
							</i>元</span></li>
							<li><label>充值金额：</label><input type="hidden" value="6" name = "payWay" id= "payWay"/><input type="text" name="money" id="money"  onKeyUp="javascript:clearNoNumber(event,this)"  class="uc_p_ip3" ><span>元</span></li>
						</ul>
						<div class="uc_paybtn uc_olbtn"><a href="javascript:void(0);" onclick="doNetPayment();">立即充值</a></div>
					</div>
					<div class="uc_olpromt">
						<h3>温馨提示：</h3>
						<p>1、为了您的资金安全，您的账户资金将由第三方银行托管；</br>2、充值前请注意您的银行卡充值限制，以免造成不便；</br>3、禁止洗钱、信用卡套现，虚假交易等行为，一经发现并确认，将终止该账户的使用；</br>4、为了您的资金安全，建议充值前进行实名认证，手机绑定、设置提现密码；</br>5、如果充值遇到任何问题，请联系客服：400-852-8008.</p>
					</div>
				</form>
			</div>
			<%-- <div class="subtab">
				<div id="alipay1" style="padding:20px 50px 50px;">
					<!-- 支付宝转账 -->
						<div class="uc_alipay">
							<div class="uc_apl_title">
								<img src="${ctx }/static/images/uc/alipay.gif">
								<i>支付宝转账（手机转账0手续费）</i>
							</div>
							<ul class="uc_ap_money">
								<li>
									<label>支付宝账号：</label>
									<input type="text" id="alipayaccount" name="alipayaccount" >
								</li>
								
								<li>
									<label>充值金额：</label>
									<input type="text" id="alimoney" name="alimoney" maxlength="7" onKeyUp="javascript:clearNoNumber(event,this)">
								</li>
								
							</ul>
							
							<div class="uc_paybtn uc_apbtn"><a id="aliSubmitBtn" href="javascript:void(0)" onclick="doAliCharge();">立即充值</a></div>
							
							 <div class="uc_paybtn uc_apbtn"><a id="aliSubmitBtn" href="javascript:void(0)" onclick="doAliCharge();">立即绑定</a></div>
						</div>
						<div class="uc_banktime uc_aptime">
							<h3>温馨提示：<i></i></h3>
							<p>1，为了能使您的支付宝转账能及时到账，请绑定您自己的支付宝账号；</p>
							<p>2，如果支付宝绑定遇到任何问题，请致电客服：400-852-8008。</p>
						</div>
				</div>
				<div id="alipay2" style="padding:40px 0 0; display:none;">
					<div class="uc_alipay">
					<div class="uc_apl_title uc_apl_title2">
						<img src="${ctx }/static/images/uc/alipay.gif">
						<i>支付宝转账（手机转账0手续费）</i>
					</div>
					<ul class="uc_apl_money">
						<ol id="ali_account_text">
							<li><label>支付宝账号：</label><span id="payaliaccount"></span><a href="javascript:;" class="edit">修改支付宝账号</a></li>
						</ol>
						<ol style="display: none;" id="ali_account_input">
							<li><label>支付宝账号：</label><input type="text"><a href="javascript:;" class="btn">立即绑定</a></li>
						</ol>
						<ol>
							<li><label>收款人支付宝账户：</label><span>vs@vs.com</span>
							<a id="popBtn" href="javascript:;" class="ml15" data-clipboard-target="popText">
							<input type="hidden" id="popText" value="vs@vs.com">
							</a>
	                        </li>			
						</ol>
						<ol>
							<li><label>收款人账号名称：</label><span>成都盈透科技有限公司</span></li>
						</ol>
					</ul>
					
					<ul class="uc_ap_info uc_ap_info1">
						<li><label>收款人支付宝账户：</label><span>vs@vs.com</span>
						<a id="popBtn" href="javascript:;" class="ml15" data-clipboard-target="popText">复制
						<input type="hidden" id="popText" value="vs@vs.com">
						</a>
                        </li>
						<li><label>账户名称：</label><span>上海信闳投资管理有限公司</span></li>
					</ul>
					<ul class="uc_ap_info">
						<li><label>您的支付宝账户：</label><span id="payaliaccount"></span></li>
						<li><label>转入金额：</label><span><i id="payalimoney"></i>元</span><a href="javascript:void(0)" onClick="toAliCharge();">修改金额</a></li>
					</ul>
					
					<div class="uc_ap_link">
							<div class="uc_ap_code">
								
								<img src="${ctx}/static/images/uc/new_code.png?v=20150717">
								<p>手机支付宝扫一扫，快速转账，<i>0手续费</i></p>
							</div>
							<div class="uc_ap_aplink">
								<div class="uc_ap_aplink">
									<span><a href="https://shenghuo.alipay.com/send/payment/fill.htm?_tosheet=true&_pdType=afcabecbafgggffdhjch" target="_blank"><img src="${ctx}/static/images/uc/alipay.gif"></a></span>
									<a href="https://shenghuo.alipay.com/send/payment/fill.htm?_tosheet=true&_pdType=afcabecbafgggffdhjch" target="_blank" class="uc_ap_apbtn">去支付宝网站转账</a>
									<i>*手续费将由支付宝收取，具体请咨询支付宝</i>
								</div>
								
							</div>
						</div>
					</div>
					<div class="uc_banktime">
						<h3>温馨提示:</h3>
						<p>1，请使用您绑定的支付宝账号进行转账付款；</p>
						<p>2，最快5分钟内到账，最晚下一个工作日09:15前到账；</p>
		
					</div>
				</div>
			</div> --%>
			<div class="subtab">
					<div class="uc_bank">
						<p class="uc_b_title">您可以通过网上银行或银行柜台向维胜转账（手续费最多一笔50元）</p>
						<ul class="uc_b_list">
							<ol class="first">
								<li class="uc_b_l_bank"><img src="${ctx}/static/images/banks/bank_19.jpg"></li>
								<li class="uc_b_l_info">
									<span>账号：<i>1289 0715 5110 501</i></span>
									<span>户名：<i>成都盈透科技有限公司</i></span>
									<span>开户行：<i>招商银行股份有限公司成都天府大道支行</i></span>
								</li>
							</ol>
							<%-- <ol class="first">
								<li class="uc_b_l_bank"><img src="${ctx}/static/images/banks/bank_04.jpg"></li>
								<li class="uc_b_l_info">
									<span>帐号：<i>3100 1518 0000 5003 1146</i></span>
									<span>户名：<i>上海信闳投资管理有限公司</i></span>
									<span>开户行：<i>中国建设银行上海黄浦支行</i></span>
								</li>
							</ol> --%>
							<%-- <ol>
								<li class="uc_b_l_bank"><img src="${ctx}/static/images/banks/bank_01.jpg"></li>
								<li class="uc_b_l_info">
									<span>帐号：<i>1001 2689 1901 6802 706</i></span>
									<span>户名：<i>上海信闳投资管理有限公司 </i></span>
									<span>开户行：<i>中国工商银行上海市金陵东路支行</i></span>
								</li>
							</ol>
							<ol>
								<li class="uc_b_l_bank"><img src="${ctx}/static/images/banks/bank_03.jpg"></li>
								<li class="uc_b_l_info">
									<span>帐号：<i>0335 7100 0400 11335</i></span>
									<span>户名：<i>上海信闳投资管理有限公司 </i></span>
									<span>开户行：<i>中国农业银行上海西藏南路支行 </i></span>
								</li>
							</ol>
							<ol>
								<li class="uc_b_l_bank" ><img src="${ctx}/static/images/banks/bank_10.jpg"></li>
								<li class="uc_b_l_info">
									<span>帐号：<i>4572 6756 9167</i></span>
									<span>户名：<i>上海信闳投资管理有限公司</i></span>
									<span>开户行：<i>中国银行上海市大世界支行</i></span>
								</li>
							</ol> --%>
						</ul>
						<!-- 弹出框 -->
				<div class="uc_floatlayer">
					<div class="uc_bc_option uc_b_bank" style="display:none;">
						<a data-id="cmb" href="javascript:void(0)">中国招商银行</a>
						<!-- <a data-id="ccb" href="javascript:void(0)">中国建设银行</a>
						<a data-id="icbc" href="javascript:void(0)">中国工商银行</a>
						<a data-id="abc" href="javascript:void(0)">中国农业银行</a>
						<a data-id="boc" href="javascript:void(0)">中国银行</a> -->
					</div>

				</div>
				<ul class="uc_b_money">
					<li>
						<label>转账银行</label>
						<input type="text" disabled="true" class="uc_b_selbank">
						<a href="javascript:void(0)" class="uc_i_ipdown uc_b_ipbank"></a>
					</li>
					<li>
						<label>转账金额</label>
						<input type="text" id="transmoney" name="transmoney" onKeyUp="javascript:clearNoNumber(event,this)">
					</li>
					<li>
						<label>流水号</label>
						<input type="text" id="serialnum" name="serialnum">
					</li>
				</ul>
					<div class="uc_paybtn uc_b_btn">
						<a href="javascript:void(0)" onclick="doTransmany();">提交</a>
					</div>

						<div class="uc_b_promt"><i>注意事项</i></div>
						<p class="uc_b_promtfont" style="display:none;">1、转账时请增加转账零头（如100.85）</br>2、转账备注中请务必填写<i>注册手机号和注册用户名</i>，方便我们确认是您的汇款</br>3、转账成功后，<i>请拨打客服热线400-852-8008<!-- ，或将回单发给QQ客服 -->，</i>以便我们及时帮您处理</p>
						<p class="uc_b_promtfont">转账成功后，请务必在上方填写 <i>转账银行</i>、<i>转账金额</i>、<i>转账流水号</i>，以便我们及时帮您处理</p>
						<!-- <div class="uc_paybtn">
						<a href="javascript:void(0)" id="BizQQWPA" onclick="window.open('http://wpa.qq.com/msgrd?v=3&uin=4008528008&site=qq&menu=yes','QQ在线','height=405,width=500,top=200,left=200,toolbar=no,menubar=no,scrollbars=yes, resizable=no,location=no, status=no');">
        				发给QQ客服</a></div> -->
					</div>
					<div class="uc_banktime">
						<h3>到账时间:<i>如需马上到账或长时间未到账，可拨打客服电话：400-852-8008</i></h3>
						<p><i></i>工作日17:30前转账的在资金到账后半小时内完成维胜网充值</p>
						<p><i></i>工作日17:30以后转账的在第二个工作日早上09:15前完成维胜网充值</p>
						<p><i></i>非工作日转账的将在下一个工作日早上09:15前完成维胜网充值</p>
					</div>
			</div>
			<div class="subtab">
				<div class="uc_recharge">
					<ul class="uc_retitle">
						<li class="uc_re_time">时间</li>
						<li class="uc_re_num120">订单号</li>
						<li class="uc_re_way80">充值方式</li>
						<li class="uc_re_money100">提交金额</li>
						<li class="uc_re_money100">实际到账金额</li>
						<li class="uc_re_ope100">状态</li>

					</ul>
					<!--  
					<iframe runat="server" src="${ctx}/pay/payHistorty" width="750" height="400" frameborder="no" border="0" marginwidth="0" marginheight="0" scrolling="no" allowtransparency="yes"></iframe>
					-->
					<ul class="uc_relist">	
						<div id="Searchresult">  
						</div> 
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

