<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@page import="com.tzdr.common.utils.ConfUtil"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>配股宝 - 专注股票操盘服务</title>
<meta name="description" content="中国最专业的股票金融服务平台，致力于为广大股票投资者提供股票配资，港股配资与美股配资等一站式服务，打造一流、创新型的互联网普惠金融模式。" />
<%
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
	String casServerLoginUrl=ConfUtil.getContext("SSO.casServer.loginUrl");
	String imgPreURL = ConfUtil.getContext("banner.url");
%>
<c:set var="ctx" value="<%=basePath%>"></c:set>
<c:set var="v" value="20151127"></c:set>
<c:set var="imgPreURL" value="<%=imgPreURL %>"></c:set>
<!-- common css -->
<link rel="shortcut icon" href="${ctx}/static/ico/pgb.ico">
<link rel="stylesheet" href="${ctx}/static/css/home.css?v=${v}">
<!-- common js -->
<script src="${ctx}/static/script/common/jquery-1.8.0.min.js"></script>
<script type="text/javascript">
	var basepath = "<%=basePath%>" + "/";
	var casServerLoginUrl = "<%=casServerLoginUrl%>";
</script>
</head>
<body>
<!-- header -->
<%@include file="../common/header.jsp"%>
<div class="p1">
	<div class="pic">
        <div class="shu bannernumber">
        	<c:forEach var="b" items="${banners}" varStatus="status">
        		<span><a href="javascript: void(0);" title="${status.count }" <c:if test="${status.index }==0">class="cur"</c:if>>${status.count}</a></span>
        	</c:forEach>
        </div>
        <div class="tu bannerbox" style="display:block;">
    		<c:forEach var="b" items="${banners}" varStatus="status">
        		<a href="${b.linkUrl}" target="_blank" style="display: none;"><img src="${imgPreURL}${b.imgPath}" title="banner" alt="banner" /></a>
        	</c:forEach>
        </div>
        <%
       		if(request.getSession().getAttribute("userName")!=null){
       	%>
        <div class="afterlogin" style="display: block;" id="logondiv">
            <div class="main">
                <p class="t1">您好,${mobile}<a href="${ctx}/logout">【安全退出】</a></p>
				<div class="mainCenter">
					<p class="t2">账户余额：<fmt:formatNumber value="${avlbal}" pattern="#,###.##" minFractionDigits="2" ></fmt:formatNumber>元</p>
					<p class="t2">A股方案：${tradeCount}个</p>
					<!-- 
					<p class="t2">港股方案：${hkTradeCount}个</p>
					 -->
					<p class="t3"><a href="${ctx}/user/account">进入账户首页</a></p>
				</div>
				
            </div>
        </div>
       	<%
       		}else{
       	%>
        <div class="login" id="logindiv">
            <div class="main">
	            <form id="loginForm" name="loginForm" action="<%=casServerLoginUrl%>" onsubmit="return loginValidate();" method="post" target="ssoLoginFrame">
		            <input type="hidden" name="isajax" value="true" />
					<input type="hidden" name="isframe" value="true" />
					<input type="hidden" name="lt" value="" id="J_LoginTicket">
					<input type="hidden" name="execution" value="" id="J_FlowExecutionKey">
					<input type="hidden" name="_eventId" value="submit" />
	                <p class="t1">用户登录</p>
	                <input class="uname" id="username" name="username" placeholder="请输入手机号码" value="" />
	                <input class="key" type="password" id="password" name="password" placeholder="请输入登录密码" value=""/>	                
	                <input class="bot" id="login" type="button" value="登录" />
	                <p class="fr"><span><a href="${ctx}/forgetpw">忘记密码？</a></span><font><a href="${ctx}/signin">免费注册</a></font></p>
                </form>
            </div>
        </div>
        <%
       		}
       	%>
    </div>
</div>

<!--新connner-->
<div class="pei_con">
	<div class="pei_sx left ">
    	<div class="pei_sx_bj">
        	<div class="quan_sx_con">
            	<div class="quan_top">
                	<p class="left">随心操盘</p>
                    <div class="quan_ss right"><a href="${ctx}/day?enter=0">我要申请</a></div>
                </div>
                <div class="xuxian"></div>
                <p class="quan_p">用户按天在配股宝平台融资交易A股，操盘更灵活。</p>
                <div class="quan_logo">
                	<div class="quan_logo_left left">
                    	<div class="quan_left_a left">
                        	<img src="${ctx}/static/images/home-new/yy_tian.png" />
                        </div>
                        <div class="quan_left_b left">
                        	<h2>操盘时长最短2天起</h2>
                            <p>适合放短线、短期投资</p>
                        </div>
                    </div>
                    <div class="quan_logo_right right">
                    	<div class="quan_left_a left">
                        	<img src="${ctx}/static/images/home-new/yy_chen.png" />
                        </div>
                        <div class="quan_left_b left">
                        	<h2 style=" color:#f98638;">融资1至5倍任意选</h2>
                            <p>本金最低仅需300元</p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="yy_sx left">
    	<div class="pei_sx_bj">
        	<div class="quan_sx_con">
            	<div class="quan_top">
                	<p class="left">月月操盘</p>
                    <div class="quan_ss right"><a href="${ctx}/monthTrade/index">我要申请</a></div>
                </div>
                <div class="xuxian"></div>
                <p class="quan_p">用户按月在配股宝平台融资交易A股，费用更低。</p>
                <div class="quan_logo">
                	<div class="quan_logo_left left">
                    	<div class="quan_left_a left">
                        	<img src="${ctx}/static/images/home-new/yy_yue.png" />
                        </div>
                        <div class="quan_left_b left">
                        	<h2>一次性可申请6个月</h2>
                            <p>适合资金量大，投资时间长</p>
                        </div>
                    </div>
                    <div class="quan_logo_right right">
                    	<div class="quan_left_a left">
                        	<img src="${ctx}/static/images/home-new/yy_shou.png" />
                        </div>
                        <div class="quan_left_b left">
                        	<h2 style=" color:#f98638;">融资操盘成本更低</h2>
                            <p>操盘周期越长、越优惠</p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    
    
    <div class="pei_sx left ">
    	<div class="pei_sx_bj pei_sx_bj2 ">
        	<div class="quan_sx_con">
            	<div class="quan_a">
                	<p class="left">海外操盘</p>
                    <div class="quan_ss right"><a target="_blank" href="${ctx}/topic/overseas/">立即了解</a></div>
                </div>
                <div class="xuxian"></div>
                <p class="quan_p">面向全球海外华人，便捷投资A股，融资放大收益。</p>
                
            </div>
        </div>
    </div>
    <div class="yy_sx left">
    	<div class="pei_sx_bj pei_sx_bj2">
        	<div class="quan_sx_con">
            	<div class="quan_b">
                	<p class="left">港股操盘</p>
                    <div class="quan_ss right"><a href="${ctx}/hkstock/index">立即体验</a></div>
                </div>
                <div class="xuxian"></div>
                <p class="quan_p">国内首家港股融资操盘，无涨跌幅限制，T+0快速获利。</p>
            </div>
        </div>
    </div>
</div>
<!--新connner end-->


<div class="p3">
	<ul class="main zj">
    	<li class="t1">资金安全</li>
        <li class="t2">资金托管银行</li>
        <li class="t2">封闭管理专款专用</li>
    </ul>
    <ul class="main jy">
    	<li class="t1">交易安全</li>
        <li class="t2">券商监管</li>
        <li class="t2">保障您的交易安全</li>
    </ul>
    <ul class="main fk">
    	<li class="t1">专业风控</li>
        <li class="t2">多级风控管理体系</li>
        <li class="t2">降级投资风险</li>
    </ul>
    <ul class="main ms">
    	<li class="t1">全新模式</li>
        <li class="t2">随借随还按天收费</li>
        <li class="t2">叠加操盘轻松超低</li>
    </ul>
</div>
<div class="flink"><span>合作伙伴</span>
<ul>
	<li><img src="${ctx}/static/images/home-new/partner1.png" /></li>
	<li><img src="${ctx}/static/images/home-new/partner2.png" /></li>
	<li><img src="${ctx}/static/images/home-new/partner3.png" /></li>
	<li><img src="${ctx}/static/images/home-new/partner4.png" /></li>
	<li><img src="${ctx}/static/images/home-new/partner5.png" /></li>
	<li><img src="${ctx}/static/images/home-new/partner6.png" /></li>
</ul>
</div>

<div class="flink flink1"><span>友情链接</span>
<ul>
	<li><a href="http://www.peizimenhu.com/archives-daohang.html" target="_blank">配资门户</a></li>
	<li><a href="http://www.tzdr.com" target="_blank">投资达人</a></li>
</ul>
</div>




<!--  <div class="p21">
	<div class="try">
    	<p>用户通过少量资金做保证金向配股宝平台借入一定倍数资金，形成较大的总操盘资金去炒股，盈利全归用户。</p>
        <p>本金要求极低，用户可根据资金需求灵活选择操盘倍数和操盘时间。</p>
        <a href="${ctx}/day?enter=0"></a>
    </div>
    <div class="main">
    	<div class="youshi">
        	<ul>
            	<li class="tu"><img src="${ctx}/static/images/home-new/qian.gif" /></li>
                <li class="hei">300元起</li>
                <li class="xiao">投资本金低</li>
            </ul>
            <ul>
            	<li class="tu"><img src="${ctx}/static/images/home-new/gang.gif" /></li>
                <li class="hei">1-5倍</li>
                <li class="xiao">高杠杆 高收益</li>
            </ul>
            <ul>
            	<li class="tu"><img src="${ctx}/static/images/home-new/zong.gif" /></li>
                <li class="hei">600万</li>
                <li class="xiao">最高融资金额600万</li>
            </ul>
            <ul>
            	<li class="tu"><img src="${ctx}/static/images/home-new/tian.gif" /></li>
                <li class="hei">2天起</li>
                <li class="xiao">操盘天数任意选</li>
            </ul>
        </div>
        <div class="ruijian">
        	<p class="tui">推荐操盘方案<img src="${ctx}/static/images/home-new/tui.gif" /></p>
            <p class="shengqin"><span class="cao">操盘资金</span><em>10</em><font>万元</font><span class="bei">操盘倍数</span><em>5</em><font>倍</font><span class="tian">操盘天数</span><em>8</em><font>天</font> <a href="${ctx}/day?lever=5&capitalMargin=100000.0&borrowPeriod=8&enter=0"></a></p>
        </div>
    </div>
	
</div>
<div class="p22">
	<div class="try">
    	<p id="p1">港股操盘产品由配股宝的战略合作伙伴投资达人推出。</p>
        <p>用户通过少量人民币做本金，投资达人提供一定数额港币做操盘金，港股操盘盈利全归用户！</p>
    </div>
    <div class="main">
    	<div class="youshi">
        	<ul>
            	<li class="tu"><img src="${ctx}/static/images/home-new/hkqian.png" /></li>
                <li class="hei">600万港元</li>
                <li class="xiao">最高可配600万港元</li>
            </ul>
            <ul>
            	<li class="tu"><img src="${ctx}/static/images/home-new/qian2.png" /></li>
                <li class="hei">21250元起</li>
                <li class="xiao">投资本金低</li>
            </ul>
            <ul id="noBorderUl">
            	<li class="tu"><img src="${ctx}/static/images/home-new/tian2.png" /></li>
                <li class="hei">3天起</li>
                <li class="xiao">操盘天数任意选</li>
            </ul>
			<ul>
				<a href="http://www.tzdr.com/hkstock/index" target="_blank"></a>
			</ul>
        </div>
    </div>

</div>
<div class="p3">
	<ul class="main zj">
    	<li class="t1">资金安全</li>
        <li class="t2">资金托管银行</li>
        <li class="t2">封闭管理专款专用</li>
    </ul>
    <ul class="main jy">
    	<li class="t1">交易安全</li>
        <li class="t2">券商监管</li>
        <li class="t2">保障您的交易安全</li>
    </ul>
    <ul class="main fk">
    	<li class="t1">专业风控</li>
        <li class="t2">多级风控管理体系</li>
        <li class="t2">降级投资风险</li>
    </ul>
    <ul class="main ms">
    	<li class="t1">全新模式</li>
        <li class="t2">随借随还按天收费</li>
        <li class="t2">叠加操盘轻松抄底</li>
    </ul>
</div>
<div class="flink"><span>合作伙伴</span>
<ul>
	<li><a href="javascript: void(0);"><img src="${ctx}/static/images/home-new/partner1.png" /></a></li>
	<li><a href="javascript: void(0);"><img src="${ctx}/static/images/home-new/partner2.png" /></a></li>
	<li><a href="javascript: void(0);"><img src="${ctx}/static/images/home-new/partner3.png" /></a></li>
	<li><a href="javascript: void(0);"><img src="${ctx}/static/images/home-new/partner4.png" /></a></li>
	<li><a href="javascript: void(0);"><img src="${ctx}/static/images/home-new/partner5.png" /></a></li>
	<li><a href="javascript: void(0);"><img src="${ctx}/static/images/home-new/partner6.png" /></a></li>
</ul>
</div>
-->
<!--  <div class="main_link"><span>友情链接：</span>--><!-- <a href="http://www.peizimenhu.com/portal.php" target="_blank">配资门户</a> --><!--<a href="http://www.tzdr.com" target="_blank">投资达人</a></div>-->



<!-- footer -->
<%@include file="../common/footer.jsp"%>
<!-- custom js -->
<script src="${ctx}/static/script/homepage/homepage.js?version=20151127"></script>
<span style="display:none"><script src='http://w.cnzz.com/q_stat.php?id=1257376699&l=3' language='JavaScript'></script></span>
<%@ include file="/WEB-INF/views/common/count.jsp"%>
</body>
</html>