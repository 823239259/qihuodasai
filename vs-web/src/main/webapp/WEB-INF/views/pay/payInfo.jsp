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
<meta name="keywords" content="维胜，国际期货，期货，投资达人，金勺子，高盛，都城，南华期货，配资，期货配资，期货开户，外盘，富时A50，国际原油，恒指期货，期货公司，期货平台，炒期货，模拟盘，赚钱，头寸，持仓，成都盈透科技有限公司"/>
<meta name="description" content="维胜（www.vs.com）-致力于成为中国领先的国际期货及衍生品互联网交易平台，提供恒指期货、国际原油、富时A50等主流国际期货产品，开户操盘快捷方便，交易费用全网最低。"/>
<title>账户充值 - 维胜金融-中国领先的国际期货及衍生品互联网交易平台</title>
 <link rel="stylesheet" href="${ctx}/static/css/uc.css?version=20150721"  type="text/css">
 <link href="${ctx}/static/css/public.css?v=${v}" rel="stylesheet" type="text/css">	
 <link href="${ctx}/static/css/pagination.css?v=${v}" rel="stylesheet" type="text/css" /> 
 <script src="${ctx}/static/script/common/jquery.pagination.js?v=${v}" type="text/javascript"></script>
 <script type='text/javascript' src="${ctx}/static/script/pay/pay.js?v=${v}"></script>
  <script type='text/javascript' src="${ctx}/static/script/pingpp.js?v=${v}"></script>
 <script type='text/javascript' src="${ctx}/static/script/common/dateUtils.js?v=${v}"></script>
 <script type='text/javascript' src="${ctx}/static/script/common/ZeroClipboard.min.js?v=${v}"></script>
<script type="text/javascript">
	var tab=<%=tab%>;
</script>
<style>
	#nav_my {color: #ffcc33; border-bottom:2px solid #ffcc33; padding-bottom: 26px;}
	.uc_bank {width: auto; padding: 0 20px 0 20px;}
	.uc_b_money {width: auto; margin: 20px 0;}
	.uc_b_list {float:left; width: 693px; position: relative; left: 20px;}
	.uc_b_list ol {width: 700px; padding: 0; height: 70px;}
	.uc_b_l_info{padding-left: 10px; width: 475px; height: 48px; padding-top: 10px; padding-bottom: 10px;}
	.uc_b_money li label {width: 70px; }
	.uc_b_l_bank {height: 50px;padding: 10px 10px 0 10px;}
</style>
</head>
<body>
<!--顶部 -->
	<%@include file="../common/header.jsp"%>
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
			<p class="fl_uc_charge">请在新打开的网上银行<br>页面完成付款<br>充值遇到问题请咨询客服 <br>400-180-1860</p>
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
                <li><a href="javascript:void(0);" class="on">支付宝转账</a></li>
                <li><a href="javascript:void(0);" >网银充值</a></li> 
                <!-- <li><a href="javascript:void(0);">微信充值</a></li> -->
                <li><a href="javascript:void(0);">银行转账</a></li>
                <li style="float:right;"><a href="javascript:void(0);">充值记录</a></li>
            </ul>
            
             <div class="tabcon">
             <div class="subtab">
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
                                <%--
                                <li>
                                    <label>充值金额：</label>
                                    <input type="text" id="alimoney" name="alimoney" maxlength="7" onKeyUp="javascript:clearNoNumber(event,this)">
                                </li>
                                 --%>
                            </ul>
                            <%--
                            <div class="uc_paybtn uc_apbtn"><a id="aliSubmitBtn" href="javascript:void(0)" onclick="doAliCharge();">立即充值</a></div>
                             --%>
                             <div class="uc_paybtn uc_apbtn"><a id="aliSubmitBtn" href="javascript:void(0)" onclick="doAliCharge();">立即绑定</a></div>
                        </div>
                        <div class="uc_banktime uc_aptime">
                            <h3>温馨提示：<i></i></h3>
                            <p>1，为了能使您的支付宝转账能及时到账，请绑定您自己的支付宝账号；</p>
                            <p>2，如果支付宝绑定遇到任何问题，请致电客服：400-180-1860。</p>
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
                            <li><label>收款人支付宝账户：</label><span>weisheng@vs.com</span>
                            <a id="popBtn" href="javascript:;" class="ml15" data-clipboard-target="popText">
                            <input type="hidden" id="popText" value="weisheng@vs.com">
                            </a>
                            </li>           
                        </ol>
                        <ol>
                            <li><label>收款人账号名称：</label><span>成都维胜智慧科技有限公司</span></li>
                        </ol>
                    </ul>
                    <%--
                    <ul class="uc_ap_info uc_ap_info1">
                        <li><label>收款人支付宝账户：</label><span>xinhong1@tzdr.com</span>
                        <a id="popBtn" href="javascript:;" class="ml15" data-clipboard-target="popText">复制
                        <input type="hidden" id="popText" value="xinhong1@tzdr.com">
                        </a>
                        </li>
                        <li><label>账户名称：</label><span>上海信闳投资管理有限公司</span></li>
                    </ul>
                    <ul class="uc_ap_info">
                        <li><label>您的支付宝账户：</label><span id="payaliaccount"></span></li>
                        <li><label>转入金额：</label><span><i id="payalimoney"></i>元</span><a href="javascript:void(0)" onClick="toAliCharge();">修改金额</a></li>
                    </ul>
                    --%>
                    <div class="uc_ap_link">
                            <div class="uc_ap_code">
                                
                                <img src="${ctx}/static/images/uc/code.png?v=20170105">
                                <p>手机支付宝扫一扫，快速转账，<i>0手续费</i></p>
                            </div>
                            <div class="uc_ap_aplink">
                                <div class="uc_ap_aplink">
                                    <span><a href="https://shenghuo.alipay.com/send/payment/fill.htm?_tosheet=true&_pdType=afcabecbafgggffdhjch" target="_blank"><img src="${ctx}/static/images/uc/alipay.gif"></a></span>
                                    <a href="https://shenghuo.alipay.com/send/payment/fill.htm?_tosheet=true&_pdType=afcabecbafgggffdhjch" target="_blank" class="uc_ap_apbtn">去支付宝网站转账>></a>
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
            </div>
                 <div class="subtab">
                    <form id="goNetbank" action="" method="get" target="_blank"  >
                    <div class="uc_olmoney">
                        <!-- <h3 class="uc_pbtitle">填写充值金额</h3> -->
                        <ul class="uc_pblist">
                            <li><label>账户余额：</label><span><i>
                            <fmt:formatNumber value="${requestScope.user.avlBal}" type="currency" pattern="0.00#"/>
                            </i>元</span></li>
                            <li><label>充值金额：</label>
                            <input type = "hidden" id = "isFlag" name = "isFlag" value ="${isFlag }" />
                            <input type = "hidden" id = "isFlagMoney" name = "isFlagMoney" value ="${money }" />
                            <input type="hidden" value="7" name = "gopayWay" id= "gopayWay"/><!--  -->
                            <input type="text" value="${money}" name="gopaymoney" id="gopaymoney" onKeyUp="javascript:clearNoNumber(event,this)"   class="uc_p_ip3" ><span>元</span></li>
                        </ul><!--  -->
                        <div class="uc_paybtn uc_olbtn"><a id = "payButton" href="javascript:void(0);" onclick="doGopay();">立即充值</a></div>
                        <div id = "fatip" style="margin-left: 65px;font-size: 14px; color: #fc3;">注意：选择确认，自动付款并提交操盘申请。</div>
                    </div>
                    
                    <div class="uc_olpromt">
                        <h3>温馨提示：</h3>
                        <p>1、为了您的资金安全，您的账户资金将由第三方银行托管；</br>2、充值前请注意您的银行卡充值限制，以免造成不便；</br>3、禁止洗钱、信用卡套现，虚假交易等行为，一经发现并确认，将终止该账户的使用；</br>4、为了您的资金安全，建议充值前进行实名认证，手机绑定、设置提现密码；</br>5、如果充值遇到任何问题，请联系客服：400-180-1860.</p>
                    </div>
                </form>
            </div> 
            <%-- <div class="subtab">
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
                        <p>1、为了您的资金安全，您的账户资金将由第三方银行托管；</br>2、充值前请注意您的银行卡充值限制，以免造成不便；</br>3、禁止洗钱、信用卡套现，虚假交易等行为，一经发现并确认，将终止该账户的使用；</br>4、为了您的资金安全，建议充值前进行实名认证，手机绑定、设置提现密码；</br>5、如果充值遇到任何问题，请联系客服：400-180-1860.</p>
                    </div>
                </form>
            </div> --%>
            <%-- <div class="subtab">
                <form id="weixinbank" action="" method="post" target="_blank">
                    <div class="uc_olmoney" style="padding-bottom: 10px; border-bottom: 1px solid #eee;">
                        <p style="color: #fc3;font-size: 14px;">温馨提示：如您已开通网银，请尽量选择网银充值，如您是柜台转账请尽量选择相同银行转账，这样可以快速及时到账！</p>
                        <ul class="uc_pblist">
                            <li><label style="text-align: left; font-size: 16px; margin-left: 25px;"><img src="${ctx}/static/images/weixin.png" style="position: relative; top: 5px; margin-right: 5px;">微信支付</label></li>
                            <li id="weixin_bind"><label>微信账号：</label><input type="text" name="weixn" value = "" id="weixin" class="uc_p_ip3"><span class="weixin_bind">立即绑定</span></li>
                            <li id="weixin_update"><label>微信账号：</label><span style="width: 192px;" id = "userAccount">${userverified.wxAccount }</span><span class="weixin_update">修改绑定</span></li>
                            <li><label style="color: #fc3; font-size: 17px;">第一步：</label><span style="color: #fc3; font-size: 16px;">核实收款方信息，输入支付金额！</span></li>
                            <li><label>微信账号：</label><span>1369866402@1369866402</span></li>
                            <li><label>账号名称：</label><span>成都盈透科技有限公司</span></li>
                            <li><label>支付金额：</label><input type="text" name="money" value = "" id="pay_money" class="uc_p_ip3" onkeyup="javascript:clearNoNumber(event,this)"></li>
                            <li style="margin-top: 30px;"><label style="color: #fc3; font-size: 17px;">第二步：</label><span style="color: #fc3; font-size: 16px;">手机微信扫一扫，安全、快速到账！</span></li>
                            <li style="height: 130px;"><label>&nbsp;</label><span><img src="${ctx}/static/images/weixinpay.png"></span></li>
                            <li style="margin-top: 30px;"><label style="color: #fc3; font-size: 17px;">第三步：</label><span style="color: #fc3; font-size: 16px;">输入交易单号，确认交易。</span></li>
                            <li><label>交易单号：</label><input type="text" name="billing" value = "" id="billing" class="uc_p_ip3"></li>
                            <input type="hidden" name="default_value" value = "${money }" id="default_value">
                        </ul>
                        <div class="uc_paybtn uc_b_btn" style="margin-left: 165px;">
                            <a href="javascript:void(0)" class="pay_confirm">确认充值</a>
                        </div>
                    </div>
                    <div class="uc_olpromt" style="width: 746px; height: 230px; margin: 0 30px;margin-top: 40px;">
                        <div class="uc_b_promt"><i>注意事项</i></div>
                        <p class="uc_b_promtfont">转账成功后，请务必在上方填写 <i>转账银行</i>、<i>转账金额</i>、<i>转账流水号</i>，以便我们及时帮您处理</p>
                        <div class="uc_banktime">
                            <h3>到账时间:<i>如需马上到账或长时间未到账，可拨打客服电话：400-180-1860</i></h3>
                            <p><i></i>工作日17:30前转账的在资金到账后半小时内完成维胜网充值</p>
                            <p><i></i>工作日17:30以后转账的在第二个工作日早上09:15前完成维胜网充值</p>
                            <p><i></i>非工作日转账的将在下一个工作日早上09:15前完成维胜网充值</p>
                        </div>
                    </div>
                </form>
            </div> --%>
            <div class="subtab">
                    <div class="uc_bank">
                        <p class="uc_b_title">温馨提示：如您已开通网银，请尽量选择网银充值，如您是柜台转账请尽量选择相同银行转账，这样可以快速及时到账！</p>
                        <ul class="uc_b_money">
                            <li>
                                <label>转账金额</label>
                                <input type="text" id="transmoney" name="transmoney" onKeyUp="javascript:clearNoNumber(event,this)">
                            </li>
                        </ul>
                        <div style="height: 360px;">
                            <ul class="uc_b_money" style="float: left; width: 70px; margin-top: 30px; ">
                                <li><label>转账到</label></li>
                            </ul>
                            <ul class="uc_b_list" id="uc_bank_radio">
                                <ol class="first">
                                    <li><input type="radio" name="back_icon" value="cmb" style="margin-top: 29px;"></li>
                                    <li class="uc_b_l_bank"><img src="${ctx}/static/images/banks/bank_19.jpg"></li>
                                    <li class="uc_b_l_info">
                                        <span>账号：<i>1289 0715 5110 501</i></span>
                                        <span>户名：<i>成都盈透科技有限公司  </i>    开户行：<i>招商银行股份有限公司成都天府大道支行</i></span>
                                        <!-- <span>开户行：<i>招商银行股份有限公司成都天府大道支行</i></span> -->
                                    </li>
                                </ol>
                                <ol>
                                    <li><input type="radio" name="back_icon" value="icbc" style="margin-top: 29px;"></li>
                                    <li class="uc_b_l_bank"><img src="${ctx}/static/images/banks/bank_01.jpg"></li>
                                    <li class="uc_b_l_info">
                                        <span>帐号：<i>4402 9391 1910 0042 543</i></span>
                                        <span>户名：<i>成都盈透科技有限公司  </i>    开户行：<i>中国工商银行府河音乐花园支行</i></span>
                                        <!-- <span>开户行：<i>中国工商银行府河音乐花园支行</i></span> -->
                                    </li>
                                </ol>
                                <ol>
                                    <li><input type="radio" name="back_icon" value="boc" style="margin-top: 29px;"></li>
                                    <li class="uc_b_l_bank" ><img src="${ctx}/static/images/banks/bank_10.jpg"></li>
                                    <li class="uc_b_l_info">
                                        <span>帐号：<i>1158 4367 7712</i></span>
                                        <span>户名：<i>成都盈透科技有限公司  </i>    开户行：<i>中国银行天府新区华阳支行</i></span>
                                        <!-- <span>开户行：<i>中国银行天府新区华阳支行</i></span> -->
                                    </li>
                                </ol>
                                <ol class="first">
                                    <li><input type="radio" name="back_icon" value="ccb" style="margin-top: 29px;"></li>
                                    <li class="uc_b_l_bank"><img src="${ctx}/static/images/banks/bank_04.jpg"></li>
                                    <li class="uc_b_l_info">
                                        <span>帐号：<i>5105 0140 6137 0000 0421</i></span>
                                        <span>户名：<i>成都盈透科技有限公司  </i>    开户行：<i>中国建设银行成都世纪城新会展支行</i></span>
                                        <!-- <span>开户行：<i>中国建设银行成都世纪城新会展支行</i></span> -->
                                    </li>
                                </ol>
                                <ol>
                                    <li><input type="radio" name="back_icon" value="abc" style="margin-top: 29px;"></li>
                                    <li class="uc_b_l_bank"><img src="${ctx}/static/images/banks/bank_03.jpg"></li>
                                    <li class="uc_b_l_info">
                                        <span>帐号：<i>2280 8201 0400 0559 5</i></span>
                                        <span>户名：<i>成都盈透科技有限公司   </i>   开户行：<i>中国农业银行成都新希望国际支行</i></span>
                                        <!-- <span>开户行：<i>中国农业银行成都新希望国际支行</i></span> -->
                                    </li>
                                </ol>
                            </ul>
                        </div>
                        <ul class="uc_b_money">
                            <li>
                                <label>转账流水</label>
                                <input type="text" id="serialnum" name="serialnum">
                            </li>
                        </ul>
                        <!-- 弹出框 -->
                <div class="uc_floatlayer">
                    <div class="uc_bc_option uc_b_bank" style="display:none;">
                        <a data-id="cmb" href="javascript:void(0)">中国招商银行</a>
                        <a data-id="icbc" href="javascript:void(0)">中国工商银行</a>
                        <a data-id="boc" href="javascript:void(0)">中国银行</a>
                        <a data-id="ccb" href="javascript:void(0)">中国建设银行</a>
                        <a data-id="abc" href="javascript:void(0)">中国农业银行</a>
                    </div>
                </div>
                <div class="uc_paybtn uc_b_btn" style="margin-left: 90px;">
                    <a href="javascript:void(0)" onclick="doTransmany();">提交</a>
                </div>
                    <div class="uc_b_promt"><i>注意事项</i></div>
                    <p class="uc_b_promtfont" style="display:none;">1、转账时请增加转账零头（如100.85）</br>2、转账备注中请务必填写<i>注册手机号和注册用户名</i>，方便我们确认是您的汇款</br>3、转账成功后，<i>请拨打客服热线400-180-1860<!-- ，或将回单发给QQ客服 -->，</i>以便我们及时帮您处理</p>
                    <p class="uc_b_promtfont">转账成功后，请务必在上方填写 <i>转账银行</i>、<i>转账金额</i>、<i>转账流水号</i>，以便我们及时帮您处理</p>
                    <!-- <div class="uc_paybtn">
                    <a href="javascript:void(0)" id="BizQQWPA" onclick="window.open('http://wpa.qq.com/msgrd?v=3&uin=4008528008&site=qq&menu=yes','QQ在线','height=405,width=500,top=200,left=200,toolbar=no,menubar=no,scrollbars=yes, resizable=no,location=no, status=no');">
                    发给QQ客服</a></div> -->
                </div>
                <div class="uc_banktime">
                    <h3>到账时间:<i>如需马上到账或长时间未到账，可拨打客服电话：400-180-1860</i></h3>
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

