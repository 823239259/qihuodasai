<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp"%>
<%@ include file="../common/import-artDialog-js.jspf"%>
<%
	String casServerLoginUrl=ConfUtil.getContext("SSO.casServer.loginUrl");
%>
<c:set var="casServerLoginUrl" value="<%=casServerLoginUrl%>"></c:set>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta content="投资达人提供股票操盘、P2P贷款、投资理财、股指期货等交易服务，更高的收益，更加安全可靠；投资达人致力于打造中国领先的互联网金融交易平台." name="description">
<link rel="stylesheet" href="${ctx}/static/css/stock_fastsignin.css?v=${v}">
<link href="${ctx}/static/css/tzdr.css?v=${v}" rel="stylesheet" type="text/css">
<script type="text/javascript" src="${ctx}/static/script/signin/stocksignin.js?v=${v}"></script>
<script language="javascript" src="${ctx}/static/script/tzdr.js?v=${v}"></script>
<title>快速注册_投资达人-中国领先的互联网金融交易平台</title>
<script type="text/javascript">
	var casServerLoginUrl="${casServerLoginUrl}";
</script>
</head>
<body>
<input type="hidden" id="backData" value="${backData}"/>
<input type="hidden" id="source" value="${source}"/>
<div class="floatlayer" style="z-index:100;">
	<div class="fl_mask yzm_fl_mask"  style="display: none;"></div>
    <!-- 填写验证码 -->
    <div class="fl_box fl_uc_trade yzm_box" id="yzm_box" name="yzm_box" style="display:none;">
        <div class="fl_navtitle">
            <h3>请先输入图形验证码</h3>
            <a href="javascript:void(0);"  class="close yzm_box_close"></a>
        </div>
        <div class="fl_uc_main">
            <ul class="fl_uc_list" style="padding:10px 0;">
                <li>
                    <input type="text" class="yzm" id="yzm" name="yzm" maxlength="5" style="width:120px;">
                    <img src="${ctx}/validate.code" id="refresh_code"  class="refresh_code" name="refresh_code" width="100px" height="34px">
                	<a href="javascript:void(0);" class="refresh_code">点击换一张</a>
                </li>
            </ul>
        </div>
        <div class="fl_uc_btn">
            <a href="javascript:void(0);" status="true" id="getSMSCode" name="getSMSCode" class="fl_uc_surebtn getSMSCode">确定</a>
        </div>
    </div>
</div>
<div class="banner">
    <img src="${ctx}/static/images/stockfastsignin/banner.jpg" alt="飞奔吧money"> 
    <form  class="form" action="" method="post">
	    <div class="regist">
	        <h2>快速注册</h2>
	        <ul class="rg_list">
	            <li>
	                <label>手机号</label>
	                <input type="text" class="rg_l_ip mobile" maxlength="11">
	                <p><i>*</i>请填写手机号码</p>
	            </li>
	            <li>
	                <label>密码</label>
	                <input type="password" class="rg_l_ip password" maxlength="16">
	                <p><i>*</i>6-16位数字和字母组成</p>
	            </li>
	            <li>
	                <label>验证码</label>
	                <input type="text" class="rg_l_ip2 code" maxlength="6">
	                <button type="button" data_type="0" class="openYZMBox">发送验证码</button>
	                <p><i>*</i>请填写验证码</p>
	            </li>
	            <li class="last">
	                <label></label>
	                <input class="agreement" type="checkbox" checked="checked">
	                <span>我同意<a href="javascript:showAgreement();">《投资达人服务协议》</a></span>
	            </li>
	        </ul>
	        <div status="true" class="rg_btn fastsignin">免费注册</div>
	        <div class="rg_link">已注册？请<a href="${ctx}/login">登录</a></div>
	    </div>
    </form>
</div>
<div class="mainbg">
    <div class="main">
        <div class="title">
            <em>01</em>
            <span>想要赚钱就这么简单</span>
        </div>
        <div class="active">
            <a href="javascript:void(0);" class="showSigninBox" title="我要操盘"><img src="${ctx}/static/images/stockfastsignin/pic_01.gif" alt="我要申请"></a>
            <a href="javascript:void(0);" class="showSigninBox" title="进入达叔论股"><img src="${ctx}/static/images/stockfastsignin/pic_02.gif" alt="行情解读"></a>
        </div>    
        <div class="title">
            <em>02</em>
            <span>注册达人会员 尊享多重惊喜</span>
            <i></i>
        </div>
        <div class="m_regist">
            <img src="${ctx}/static/images/stockfastsignin/rg_01.gif" alt="保证有钱配">
            <img src="${ctx}/static/images/stockfastsignin/rg_02.gif" alt="光速开户炒股">
            <img src="${ctx}/static/images/stockfastsignin/rg_03.gif" alt="会员福利多多">
            <a href="javascript:void(0);" class="showSigninBox"><img src="${ctx}/static/images/stockfastsignin/rg_04.gif" class="m_rglink" title="立即注册"></a>
        </div>            
        <div class="title">
            <em>03</em>
            <span>实盘申请<b>创新型股票操盘产品</b></span>
        </div>
        <div class="trade">
            <p class="trade_mainpic"><img src="${ctx}/static/images/stockfastsignin/pei.gif" alt="实盘申请"></p>
            <div class="trade_ctn">
                <img src="${ctx}/static/images/stockfastsignin/pei_01.gif" alt="超低操盘起点">
                <img src="${ctx}/static/images/stockfastsignin/pei_02.gif" alt="倍数随心选">
                <img src="${ctx}/static/images/stockfastsignin/pei_03.gif" alt="资金随心配">
                <img src="${ctx}/static/images/stockfastsignin/pei_04.gif" alt="利润随心提">
            </div>
            <div class="trade_link">
                <img src="${ctx}/static/images/stockfastsignin/pei_05.gif" alt="全国独家收益随时提取，赚了钱立即提现" class="td_linkad">
                <a href="javascript:void(0);" class="showSigninBox" ><img src="${ctx}/static/images/stockfastsignin/pei_btn.gif" alt="我要操盘" class="td_linkbtn" title="我要申请"></a>
            </div>
        </div>
        <div class="title">
            <em>04</em>
            <span>资金安全有保障</span>
        </div>
        <div class="safe">
            <img src="${ctx}/static/images/stockfastsignin/safe_01.gif" alt="荣获最具信赖互联网金融机构,资金托管招商银行,电子协议保全">
            <img src="${ctx}/static/images/stockfastsignin/safe_02.gif" alt="银行三方存款,1秒钟到账,安全快捷,资金绝对保证安全">
            <p class="safe_bom"></p>
        </div>
    </div>
</div>
<!-- 弹出框 -->
<div class="floatlayer" style="display:none;">
    <div class="fl_mask"></div>
    <div class="fl_rgbox">
        <a href="javascript:void(0);" class="fl_rgclose bazarSigninClose"></a>
        <h2>快速注册</h2>
        <form  class="formBox" action="" method="post">
	        <div class="fl_regist">
	            <ul class="rg_list">
	                <li>
	                    <label>手机号</label>
	                    <input type="text" class="rg_l_ip mobile" maxlength="11" >
	                    <p><i>*</i>请填写手机号码</p>
	                </li>
	                <li>
	                    <label>密码</label>
	                    <input type="password" class="rg_l_ip password" maxlength="16">
	                    <p><i>*</i>6-16位数字和字母组成</p>
	                </li>
	                <li>
	                    <label>验证码</label>
	                    <input  type="text" class="rg_l_ip2 code" maxlength="6">
	                    <button class="openYZMBox" data_type="1" type="button">发送验证码</button>
	                    <p><i>*</i>请填写验证码</p>
	                </li>
	                <li class="last">
	                    <label></label>
	                    <input class="agreement" type="checkbox" checked="checked">
	                    <span>我同意<a href="javascript:showAgreement();">《投资达人服务协议》</a></span>
	                </li>
	            </ul>
	            <div status="true" class="rg_btn fastsignin">免费注册</div>
	            <div class="rg_link">已注册？请<a href="${ctx}/login">登录</a></div>
	        </div>
        </form>
    </div>
</div>
<%-- cas ajax登录 --%>
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
<%@ include file="../common/dsp.jsp"%>
</body> 
</html>