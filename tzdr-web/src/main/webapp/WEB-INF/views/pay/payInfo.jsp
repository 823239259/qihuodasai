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
				<li><a href="javascript:void(0);" class="on">网银充值</a></li> 
				<li><a href="javascript:void(0);">支付宝充值</a></li>
				<li><a href="javascript:void(0);">银行转账</a></li>
				<li><a href="javascript:void(0);">充值记录</a></li>
			</ul>
			 <div class="tabcon">
				 <div class="subtab">
					<form id="goNetbank" action="" method="get" target="_blank"  >
					<div class="uc_olmoney">
						<!-- <h3 class="uc_pbtitle">填写充值金额</h3> -->
						<ul class="uc_pblist">
							<li><label>账户余额：</label><span><i>
							<fmt:formatNumber value="${requestScope.user.avlBal}" type="currency" pattern="0.00#"/>
							</i>元</span></li>
							<li><label>充值金额：</label><input type="hidden" value="7" name = "gopayWay" id= "gopayWay"/><input type="text" name="gopaymoney" id="gopaymoney"    class="uc_p_ip3" ><span>元</span></li>
						</ul><!-- onKeyUp="javascript:clearNoNumber(event,this)" -->
						<div class="uc_paybtn uc_olbtn"><a href="javascript:void(0);" onclick="doGopay();">立即充值</a></div>
					</div>
					
					<div class="uc_olpromt">
						<h3>温馨提示：</h3>
						<p>1、为了您的资金安全，您的账户资金将由第三方银行托管；</br>2、充值前请注意您的银行卡充值限制，以免造成不便；</br>3、禁止洗钱、信用卡套现，虚假交易等行为，一经发现并确认，将终止该账户的使用；</br>4、为了您的资金安全，建议充值前进行实名认证，手机绑定、设置提现密码；</br>5、如果充值遇到任何问题，请联系客服：400-852-8008.</p>
					</div>
				</form>
			</div> 
			<div class="subtab">
					<form id="netbank" action="" method="post" target="_blank"  >
					<div class="uc_olmoney">
						<ul class="uc_pblist">
							<li><label>账户余额：</label><span><i>
							<fmt:formatNumber value="${requestScope.user.avlBal}" type="currency" pattern="0.00#"/>
								
							</i>元</span></li>
							<li><label>充值金额：</label><input type="hidden" value="6" name = "payWay" id= "payWay"/><input type="text" name="money" id="money"  onKeyUp="javascript:clearNoNumber(event,this)"  class="uc_p_ip3" ><span>元</span></li>
						</ul>
						<div class="uc_paybtn uc_olbtn"><a href="javascript:void(0);" onclick="doNetPayment()">立即充值</a></div>
					</div>
					<div class="uc_olpromt">
						<h3>温馨提示：</h3>
						<p>1、为了您的资金安全，您的账户资金将由第三方银行托管；</br>2、充值前请注意您的银行卡充值限制，以免造成不便；</br>3、禁止洗钱、信用卡套现，虚假交易等行为，一经发现并确认，将终止该账户的使用；</br>4、为了您的资金安全，建议充值前进行实名认证，手机绑定、设置提现密码；</br>5、如果充值遇到任何问题，请联系客服：400-852-8008.</p>
					</div>
				</form>
			</div>
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

