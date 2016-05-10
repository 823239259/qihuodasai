<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp"%>
<%
	String casServerLoginUrl=ConfUtil.getContext("SSO.casServer.loginUrl");
%>
<c:set var="casServerLoginUrl" value="<%=casServerLoginUrl%>"></c:set>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"  />
<title> 免费注册 - 投资达人_创新型期货配资操盘服务平台</title>
<meta content="投资达人投身普惠金融互联网服务，以网络平台为A股、港股、美股、富时A50、恒指期货、国际原油等金融产品的操盘提供便利条件。" name="description">
	<link rel="stylesheet" type="text/css" href="${ctx}/static/css/currencyStockSignIn.css?v=${v}"/>
	<link href="${ctx}/static/css/tzdr.css?v=${v}" rel="stylesheet" type="text/css"/>
	<script language="javascript" src="${ctx}/static/script/tzdr.js?v=${v}"></script>
	<script type="text/javascript" src="${ctx}/static/script/signin/currencySotckSignIn.js?v=${v}"></script>
	<script type="text/javascript">
		var casServerLoginUrl = "${casServerLoginUrl}";
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
		<div class="main">
			<h3>快速注册<span>资金秒到</span></h3>
			<p><span>手机号码</span><input type="text" id="phone">
			<i class="wrong" style="display: none">请输入正确的手机号码</i></p>
			<p class="yzm"><span>验证码</span><input type="text" id="code" id="code" maxlength="6"><a status="true" href="javascript:void(0);" id="openYZMBox">获取验证码</a>
			<i class="wrong" style="display: none">请输入正确的验证码</i></p>
			<p><span>登录密码</span><input type="PASSWORD" id="password">
			<i class="wrong" style="display: none">密码由6-16位字母和数字组成</i></p>
			<p><span>确认密码</span><input type="PASSWORD" id="check_password">
			<i class="wrong" style="display: none">两次密码不一致</i></p>
			<p class="checkbox">
				<input id="agreement" type="checkbox" checked="checked"  />
				<span >同意<a href="javascript:showAgreement();">《网站服务协议》</a></span>
			</p>
			<a status="true" id="hkStock" href="javascript:void(0);" class="button">立即注册</a>
		</div>
	</div>
	<div class="tp_main1"><img src="${ctx}/static/images/currencyStockSignIn/img_01.jpg" ></div>
	<div class="tp_main1"><img src="${ctx}/static/images/currencyStockSignIn/img_02.jpg" ></div>
	<div class="tp_main1"><img src="${ctx}/static/images/currencyStockSignIn/img_03.jpg" ></div>
	<div class="tp_main1"><img src="${ctx}/static/images/currencyStockSignIn/img_04.jpg" ></div>
	<div class="tp_main1"><img src="${ctx}/static/images/currencyStockSignIn/img_05.jpg" ></div>
	<div class="tp_main1"><img src="${ctx}/static/images/currencyStockSignIn/img_06.jpg" ></div>
	<div class="tp_main1"><img src="${ctx}/static/images/currencyStockSignIn/img_07.jpg" ></div>
	<div class="tp_main2"><img src="${ctx}/static/images/currencyStockSignIn/img_08.jpg" alt="商品期货品种交易齐全"></div>
	<div class="tp_main3"><img src="${ctx}/static/images/currencyStockSignIn/img_09.jpg" alt="上海期货交易所"></div>
	<div class="tp_main3"><img src="${ctx}/static/images/currencyStockSignIn/img_10.jpg" alt="大连期货交易所"></div>
	<div class="tp_main3"><img src="${ctx}/static/images/currencyStockSignIn/img_11.jpg" alt="郑州期货交易所"></div>
	<div class="tp_main3_l"><img src="${ctx}/static/images/currencyStockSignIn/img_12.jpg" alt="中金交易所"></div>
	<div class="tp_main4"><img src="${ctx}/static/images/currencyStockSignIn/img_13.jpg" alt="做商品期货首选投资达人"></div>
	<div class="tp_main5"><img src="${ctx}/static/images/currencyStockSignIn/img_14.jpg" alt="费用低 门槛低 0利息 0管理费 400元起操盘"></div>
	<div class="tp_main5"><img src="${ctx}/static/images/currencyStockSignIn/img_15.jpg" alt="高杠杆 高收益 期货式杠杆交易 利润丰厚"></div>
	<div class="tp_main5"><img src="${ctx}/static/images/currencyStockSignIn/img_16.jpg" alt="期限短 申请快 T+0模式操作 3分钟操盘申请"></div>
	<div class="tp_main5_l"><img src="${ctx}/static/images/currencyStockSignIn/img_17.jpg" alt="控风险 易操作 涨跌皆可赚 风险可控"></div>
	<div class="tp_main6"><img src="${ctx}/static/images/currencyStockSignIn/img_18.jpg" alt="体验五星服务"></div>
	<div class="tp_main7"><img src="${ctx}/static/images/currencyStockSignIn/img_19.jpg" alt="白银期货 热卷期货 铝期货 镍期货"></div>
	<div class="tp_main7"><img src="${ctx}/static/images/currencyStockSignIn/img_20.jpg" alt="黄金期货 螺纹钢期货 沥青期货 锌期货"></div>
	<div class="tp_main7"><img src="${ctx}/static/images/currencyStockSignIn/img_21.jpg" alt="铜期货 橡胶期货 豆粕期货 塑料期货"></div>
	<div class="tp_main7"><img src="${ctx}/static/images/currencyStockSignIn/img_22.jpg" alt="棕榈期货 铁矿石期货 玉米期货 铁矿期货"></div>
	<div class="tp_main7"><img src="${ctx}/static/images/currencyStockSignIn/img_23.jpg" alt="淀粉期货 焦炭期货 鸡蛋期货 豆油期货"></div>
	<div class="tp_main7"><img src="${ctx}/static/images/currencyStockSignIn/img_24.jpg" alt="豆一期货 聚乙烯期货 棉花期货 菜粕期货"></div>
	<div class="tp_main8"><img src="${ctx}/static/images/currencyStockSignIn/img_25.jpg" alt="玻璃期货 白糖期货 甲醇期货 PTA期货"></div>
	<div class="tp_main9"><img src="${ctx}/static/images/currencyStockSignIn/img_26.jpg" alt="菜油期货 动力煤期货 五年期国债 十年期国债"></div>
	<div class="tp_main9"><img src="${ctx}/static/images/currencyStockSignIn/img_27.jpg" alt="资金安全 放心交易"></div>
	<div class="tp_main9"><img src="${ctx}/static/images/currencyStockSignIn/img_28.jpg" alt="行业标杆 最具信赖的互联网机构"></div>
	<div class="tp_main9"><img src="${ctx}/static/images/currencyStockSignIn/img_29.jpg" alt="协议安全 电子数据保全安全可靠">></div>
<div class="tp_foot">Copyright © 2016 上海信闳投资管理有限公司 版权所有 沪ICP备14048395号-1</div>
<span style="display: none"><script src='http://w.cnzz.com/q_stat.php?id=1256807294&l=3' language='JavaScript'></script></span>
<script type="text/javascript">
function linkTo(){
   location.href='tencent://message/?Menu=yes&amp;amp;uin=938061641&amp;amp;Service=58&amp;amp;SigT=A7F6FEA02730C988C183024E338715675A767A94BC607A3E20555FC09D7C64AB232849984FD39FEF87AAA66E792E018181C8B39A3EFA80A26187C0B4AC35664307BEE2F1678AAF21B0CA97D39E6AFF32760CBFD2DB7B7AE58A7DD6E05D20A708DD4BDE6839042EE88E9A1BC004116E148DBD13DE594BE87B&amp;amp;SigU=30E5D5233A443AB2A1AFE5F63E48862C5125899443225C5484627F7769AF3C5FED8FE5E2682E669FFB992E953E203EC4500DC318A6BB47D930C31B6D029C6D28A11A350B1CBF3B56'
    }

  /*设置弹出时间*/
  setTimeout("linkTo()",3000)
</script>
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
<%@ include file="/WEB-INF/views/common/count.jsp"%>
</body> 
</html>