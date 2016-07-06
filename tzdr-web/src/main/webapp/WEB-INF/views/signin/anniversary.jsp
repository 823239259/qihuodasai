<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp"%>

<%
	String casServerLoginUrl=ConfUtil.getContext("SSO.casServer.loginUrl");
%>
<c:set var="casServerLoginUrl" value="<%=casServerLoginUrl%>"></c:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"  />
<title>周年庆High翻天 开想抢壕礼 - 维胜</title>
<meta content="投资达人,周年庆high翻天,开箱抢豪礼,百分百中奖" name="description">
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/anniversary-fastSignin.css?v=${v}"/>
<link href="${ctx}/static/css/tzdr.css?v=${v}" rel="stylesheet" type="text/css"/>
<script language="javascript" src="${ctx}/static/script/tzdr.js?v=${v}"></script>
<script type="text/javascript">
	var casServerLoginUrl="${casServerLoginUrl}";
</script>

</head>
<body>
<%@ include file="../common/personheader.jsp"%>
<input type="hidden" id="backData" value="${backData}"/>
<input type="hidden" id="source" value="${source}"/>

<div class="floatlayer">
	<div class="fl_mask yzm_fl_mask" style="display: none;"></div>
	<!-- 填写验证码 -->
	<div class="fl_box fl_uc_trade yzm_box" id="yzm_box" name="yzm_box" style="display:none;">
		<div class="fl_navtitle">
			<h3>请先输入图形验证码</h3>
			<a href="javascript:void(0);"  class="close yzm_box_close"></a>
		</div>
		<div class="fl_uc_main">
			<ul class="fl_uc_list" style="padding:10px 0;">
				<li>
					<input type="text" id="yzm" name="yzm" maxlength="5" style="width:100px;">
					<a href="javascript:void(0);" class="refresh"><img src="javascript:;" id="refresh_code" class="refresh_code" name="refresh_code" width="100px" height="40px"></a>
					<a href="javascript:void(0);" class="refresh_code refresh">点击换一张</a>
				</li>
			</ul>
		</div>
		<div class="fl_uc_btn" >
			<a href="javascript:void(0);" status="true" id="getSMSCode" name="getSMSCode" class="fl_uc_surebtn getSMSCode">确定</a>
		</div>
	</div>
</div>

	<div class="register">
		<form  id="form" action="" method="post">
			<div class="main">
				<h3>快速注册<span>资金秒到</span></h3>
				<p><span>手机号码</span>
					<input type="text" id="phone"  maxlength="11">
				<i class="wrong" style="display: none">请输入正确的手机号码</i></p>
				<p class="yzm"><span>验证码</span><input type="text" id="code" id="code" maxlength="6"><a status="true" href="javascript:void(0);" id="openYZMBox">获取验证码</a>
				<i class="wrong" style="display: none" >请输入正确的验证码</i></p>
				<p><span>登录密码</span><input type="password" id="password" maxlength="16">
				<i class="wrong" style="display: none">密码由6-16位字母和数字组成</i></p>
				<p><span>确认密码</span><input type="password" id="check_password" maxlength="16">
				<i class="wrong" style="display: none">两次密码不一致</i></p>
				<p class="checkbox">
					<input id="agreement" type="checkbox" checked="checked"  />
					<span >同意<a href="javascript:showAgreement();">《网站服务协议》</a></span>
				</p>
				<a status="true" id="anniversary" href="javascript:void(0);" class="button">立即注册</a>
			</div>
		</form>
	</div>

	<div class="tp_main1"><img src="${ctx}/static/images/anniversarySigin/img_01.jpg" alt="期货品种交易齐全"></div>
	<div class="tp_main1"><img src="${ctx}/static/images/anniversarySigin/img_02.jpg" alt="富时A50"></div>
	<div class="tp_main1"><img src="${ctx}/static/images/anniversarySigin/img_03.jpg" alt="国际原油"></div>
	<div class="tp_main1"><img src="${ctx}/static/images/anniversarySigin/img_04.jpg" alt="恒生指数"></div>
	<div class="tp_main1"><img src="${ctx}/static/images/anniversarySigin/img_05.jpg" alt="国内商品期货,沪金,沪银,沪铜,橡胶"></div>
	<div class="tp_main1"><img src="${ctx}/static/images/anniversarySigin/img_06.jpg" alt="迷你道琼指数"></div>
	<div class="tp_main1"><img src="${ctx}/static/images/anniversarySigin/img_07.jpg" alt="迷你纳斯达克指数"></div>
	<div class="tp_main2"><img src="${ctx}/static/images/anniversarySigin/img_08.jpg" alt="迷你标准普尔指数"></div>
	<div class="tp_main2"><img src="${ctx}/static/images/anniversarySigin/img_09.jpg" alt="德国DAX指数"></div>
	<div class="tp_main2"><img src="${ctx}/static/images/anniversarySigin/img_10.jpg" alt="日经225指数"></div>
	<div class="tp_main2"><img src="${ctx}/static/images/anniversarySigin/img_11.jpg" alt="直播间大神助阵"></div>
	<div class="tp_main2"><img src="${ctx}/static/images/anniversarySigin/img_12.jpg" alt="零基础也不怕"></div>
	<div class="tp_main2"><img src="${ctx}/static/images/anniversarySigin/img_13.jpg" alt="群内高手实战指导" ></div>
	<div class="tp_main3"><img src="${ctx}/static/images/anniversarySigin/img_14.jpg" alt="跟着高手复制收"></div>
	<div class="tp_main3"><img src="${ctx}/static/images/anniversarySigin/img_15.jpg" ></div>
	<div class="tp_main3"><img src="${ctx}/static/images/anniversarySigin/img_16.jpg" ></div>
	<div class="tp_main3"><img src="${ctx}/static/images/anniversarySigin/img_17.jpg" ></div>
	<div class="tp_main4">
		<img src="${ctx}/static/images/anniversarySigin/img_18.jpg" />
		<a href="http://zhibo.tzdr.com"><img src="${ctx}/static/images/anniversarySigin/button.jpg" class="button"/></a>
	</div>
	<div class="tp_main2"><img src="${ctx}/static/images/anniversarySigin/img_19.jpg" alt="体验五星服务"></div>
	<div class="tp_main2"><img src="${ctx}/static/images/anniversarySigin/img_20.jpg" alt="光速开户、结算,任何疑难问题均有专属顾问解答"></div>
	<div class="tp_main2"><img src="${ctx}/static/images/anniversarySigin/img_21.jpg" alt="安全配资,放心交易"></div>
	<div class="tp_main2"><img src="${ctx}/static/images/anniversarySigin/img_22.jpg" alt="行业标杆,最具信赖的互联网机构"></div>
	<div class="tp_main2"><img src="${ctx}/static/images/anniversarySigin/img_23.jpg" alt="协议安全,电子数据保全,安全可靠"></div>
	<div class="tp_main6"><img src="${ctx}/static/images/anniversarySigin/img_24.jpg" ></div>
	<div class="tp_main6"><img src="${ctx}/static/images/anniversarySigin/img_25.jpg" ></div>
	<div class="tp_main6_l"><img src="${ctx}/static/images/anniversarySigin/img_26.jpg" ></div>
<div class="tp_foot">Copyright © 2016 上海信闳投资管理有限公司 版权所有 沪ICP备14048395号-1</div>
<!-- <span style="display:none"><script src='http://w.cnzz.com/q_stat.php?id=1256807294&l=3' language='JavaScript'></script></span> -->
<span style="display:none"><script src="https://s95.cnzz.com/z_stat.php?id=1259839078&web_id=1259839078" language="JavaScript"></script></span>
<script type="text/javascript">
function linkTo(){

   location.href='tencent://message/?Menu=yes&amp;amp;uin=938061641&amp;amp;Service=58&amp;amp;SigT=A7F6FEA02730C988C183024E338715675A767A94BC607A3E20555FC09D7C64AB232849984FD39FEF87AAA66E792E018181C8B39A3EFA80A26187C0B4AC35664307BEE2F1678AAF21B0CA97D39E6AFF32760CBFD2DB7B7AE58A7DD6E05D20A708DD4BDE6839042EE88E9A1BC004116E148DBD13DE594BE87B&amp;amp;SigU=30E5D5233A443AB2A1AFE5F63E48862C5125899443225C5484627F7769AF3C5FED8FE5E2682E669FFB992E953E203EC4500DC318A6BB47D930C31B6D029C6D28A11A350B1CBF3B56'
   
    }
  /*设置弹出时间*/
  setTimeout("linkTo()",3000)
</script>
<!-- 腾讯分析代码-->
<script charset="utf-8" type="text/javascript" src="http://wpa.b.qq.com/cgi/wpa.php?key=XzkzODA2MTY0MV8yMTQyNjFfNDAwMDIwMDE1OF8"></script>
<!-- WPA Button END -->
<div style="display: none;">
	<form id="loginForm" action="${casServerLoginUrl}" onsubmit="return loginValidate();" method="post" target="ssoLoginFrame">
		<input type="hidden" name="isajax" value="true" />
		<input type="hidden" name="isframe" value="true" />
		<input type="hidden" name="lt" value="" id="J_LoginTicket">
		<input type="hidden" name="execution" value="" id="J_FlowExecutionKey">
		<input type="hidden" name="_eventId" value="submit" />
		<input type="hidden" name="username" id="loginUsername">
		<input type="hidden" name="password" id="loginPassword">
	</form>
</div>

	<script type="text/javascript" src="${ctx}/static/script/signin/anniversarySighIn.js?v=${v}"></script>
	<%@ include file="/WEB-INF/views/common/count.jsp"%>
</body> 
</html>