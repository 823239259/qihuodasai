<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="../common/common.jsp"%>
<%@include file="../common/import-artDialog-js.jspf"%>
<html xmlns="http://www.w3.org/1999/xhtml" lang="zh">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
	<meta name="viewport" content="width=1010">
	<title>国际期货开户_成都期货入门_期货公司排名_期货交易时间_外盘期货是什么_什么是期货交易规则_维胜金融-中国领先的国际期货及衍生品互联网交易平台 </title>
	<meta name="keywords" content="期货入门,期货开户,期货模拟交易,期货交易,成都期货,外盘期货,国际期货开户,期货公司排名,期货交易规则,期货交易时间,外盘期货是什么,什么是期货交易,期货怎么炒"/>
	<meta name="description" content="维胜金融致力于成为中国领先的国际期货及衍生品互联网交易平台,提供期货模拟交易、期货入门、国际期货开户、成都期货、外盘期货、期货公司排名、恒指期货、国际原油、富时A50等期货入门服务,分享期货交易时间、期货是什么、期货怎么炒及什么是期货交易规则,维胜金融期货开户操盘快捷方便，交易费用全网最低。"/>
	<meta name="sogou_site_verification" content="rM6RBuyJcq"/>
	<meta name="360-site-verification" content="130a887517d7b56c6f630e2188d9d027" />
	<%
		String casServerLoginUrl=ConfUtil.getContext("SSO.casServer.loginUrl");
		String imgPreURL = ConfUtil.getContext("banner.url");
	%>
	<c:set var="imgPreURL" value="<%=imgPreURL %>"></c:set>
	<link href="${ctx }/static/css/trade.css?v=${v}" rel="stylesheet" type="text/css" />
	<link href="${ctx }/static/css/homepage.css?v=${v}" rel="stylesheet" type="text/css" />
    <script src="${ctx }/static/script/homepage/gundongtiao.js?v=${v}"></script>
	<!-- common js -->
	<script type="text/javascript">
		var basepath = "<%=basePath%>" + "/";
		var casServerLoginUrl = "<%=casServerLoginUrl%>";
	</script>
	<script src="${ctx }/static/script/esl.js?v=${v}"></script>
    <script src="${ctx }/static/script/slide-box.js?v=${v}"></script>
    <link href="${ctx}/static/css/gybf.css" rel="stylesheet" type="text/css">
    <%-- <script type='text/javascript' src="${ctx}/static/script/securityInfo/securityInfo.js?version=20150724"></script> --%>
</head>
<body>
<!-- header -->
<%@include file="../common/header.jsp"%>
<!-- 弹出层 -->
<div id="div_Mask"  style="display:none;"></div>
<div class="div_loading">
	<div class="tck01" id="skbt" style="display: none;">
		<div class="navtitle">
			<a class="nava">好消息！</a><a class="close" onclick="javascript:closeDiv('skbt')"></a>
		</div>
		<div class="smain">
			<div>恭喜您获得<span class="luckNum"></span>次首亏抽奖机会！</div>
		</div>
		<div class="anniu">
	 		<a href="${ctx}/extendsion/sign/luck/view" onclick="javascript:closeDiv('skbt')">立即抽奖</a>
	 	</div> 
	</div>
	<div class="tck01" id="skmsbt" style="display: none;">
		<div class="navtitle">
			<a class="nava">好消息！</a><a class="close" onclick="javascript:closeDiv('skmsbt')"></a>
		</div>
		<div class="smain">
			<div>恭喜您获得<span class="luckNum"></span>次首亏抽奖机会！</div>
			<div>并获得免损金牌活动<span class="subsidyMoney"></span>元现金红包奖励</div>
			<div>系统已经通过现金红包的方式存入您的账户！</div>	
		</div>
		<div class="anniu">
	 		<a href="${ctx}/extendsion/sign/luck/view" onclick="javascript:closeDiv('skmsbt')">立即抽奖</a>
	 		<a href="${ctx}/user/account" onclick="javascript:closeDiv('skmsbt')">查看账户</a>
	 	</div> 
	</div>
	<div class="tck01" id="sk_bt" style="display: none;">
		<div class="navtitle">
			<a class="nava">好消息！</a><a class="close" onclick="javascript:closeDiv('sk_bt')"></a>
		</div>
		<div class="smain">
			<div>并获得免损金牌活动<span class="subsidyMoney"></span>元现金红包奖励</div>
			<div>系统已经通过现金红包的方式存入您的账户！</div>	
		</div>
		<div class="anniu">
	 		<a href="${ctx}/user/account" onclick="javascript:closeDiv('sk_bt')">查看账户</a>
	 	</div> 
	</div>
</div>

<div class="bannerlist">
    <div class="ad_slider" id="ad-slider">
        <c:forEach var="b" items="${banners }" varStatus="status">
        	<a  title="${status.count }" <c:if test="${status.index }==0">class="on"</c:if>><span></span></a>
        </c:forEach>
    </div>
    <div class="slide_box" id="slide-box">
       	<div class="slide_banner">
           <c:forEach var="b" items="${banners }" varStatus="status">
	           	<c:if test="${not empty b.linkUrl }">
	           		<c:if test="${fn:contains(b.linkUrl,\'sign/luck/view\')}">
	           			<a href="${b.linkUrl }"  target="_self" style="display: none;"><img src="${imgPreURL }${b.imgPath }" title="banner" alt="banner"></a>
	           		</c:if>
	           		<c:if test="${fn:contains(b.linkUrl,\'sign/luck/view\') == false}">
			    		<a href="${b.linkUrl }"  target="_blank" style="display: none;"><img src="${imgPreURL }${b.imgPath }" title="banner" alt="banner"></a>
	           		</c:if>
	           </c:if>
	           	<c:if test="${empty b.linkUrl }">
				    	<a href="javascript:void(0);"   style="display: none;"><img src="${imgPreURL }${b.imgPath }" title="banner" alt="banner"></a>
	           	</c:if>
	       </c:forEach>
    	</div>
    </div>
    <div class="login">
        <div class="loginbox">
        <%
       		if(request.getSession().getAttribute("userName")!=null){
       	%>
		<div class="lgctn" id="logondiv">
			<h3 class="lg_user">您好，<i>${mobile}</i><a href="${ctx}/logout">【安全退出】</a></h3>
			<div class="lg_info">
				<p class="yuei">账户余额：${usermap.user_avlbal }元</p>
				<div>
            		<a href="${ctx}/user/account" class="lg_chaopan">操盘账户</a>
            	</div>
            	<p class="lg_time" style="display:block;">上次登录时间：<br><i>${lastLoginTime}</i></p>
			</div>
        	<p style="border-top: 1px solid #4d4d4d"></p>
        </div>
       	<%
       		}else{
       	%>
        <div class="lgctn" id="logindiv">
			<h3>登录</h3>
            <input type="hidden" value="${islogin }" id = "islogin"> 
            <form id="loginForm" name="loginForm" action="<%=casServerLoginUrl%>" onsubmit="return loginValidate();" method="post" target="ssoLoginFrame">
				<input type="hidden" name="isajax" value="true">
	            <input type="hidden" name="isframe" value="true">
	            <input type="hidden" name="lt" value="" id="LoginTicket">
	            <input type="hidden" name="execution" value="e3s1" id="J_FlowExecutionKey">
	            <input type="hidden" name="_eventId" value="submit">
	            <div class="lg_ip">
		            <div class="lg_ipctn">
		                <i class="user"></i>
		                <input type="tel" id="username" name="username" value="${m }" placeholder="请输入手机号码">
		                <!-- 请输入手机号码 -->
		            </div>
	                <div class="lg_ipctn">
	                    <i class="password"></i>
	                    <input type="password" id="password" name="password" value="${p }" placeholder="请输入登录密码">
	                    <!-- 请输入登录密码 -->
	                </div>
	                <div class="lg_btn"><button id="login" type="button">立即登录</button></div>
	                <div class="lg_link">
	                    <a href="${ctx}/forgetpw" class="left">忘记密码?</a>
	           			 <a href="${ctx}/signin" class="right">免费注册</a>
	                </div>
	            </div>
            </form>
        </div>
        <% } %>
        <div class="lg_bottom"></div>
        </div>
    </div>
</div>
<!-- 最新公告 -->
<div style="background: #333; height: 46px;">
<div class="notice  h_notic">
    <div class="notice_scroll">
        <h2><i></i>最新动态：</h2>
        <ul class="h_noticlist" id="h_scroll">
        </ul>
         <a href="${ctx}/news/newsdata" class="h_n_more" target="_blank">更多动态</a>
    </div>
</div>
</div>
<!--content-->
<div class="w_content_app">
<div class="w_content">
    <div class="w_center">
        <div class="w_center_border"></div>
        <div class="w_center_top">
            <div class="left-shangzheng">
                <p>实时行情</p>
            </div>
            <div class="right-gengxin">
            	<a href = "#" id = "mainSqcp" target="_blank">申请操盘</a>
                <p><span class = "jk"></span><span class = "fd"></span></p>
                <p><span class = "zs"></span><span class = "gxsj"></span></p>
            </div>
        </div>
        <div class="w_center_xiangqing">
            <div class="left_xiangqing" id="left_xiangqing">
                <div class="left_hidden"></div>
                <div class="scroll_ymove">
        			<div class="scroll_y" unorbind="unbind"></div>
    			</div>
    			<input type="hidden" id="whichscro">
            </div>
            <div style="width: 5px; height: 510px; background: #292929; float: left; position: relative; right: 5px;"></div>
            <div class="right_xiangqing">
                <div id="main" style="height:500px; width: 800px;"></div>
            </div>
        </div>
    </div>
    <div class="w_center_upgrade" style="display: none;">
    	<div class="w_center_upgrade_center">
    		<div class="w_center_upgrade_title">
    			<p class="w_upgrade_title">显示出错了！</p>
    			<p class="w_upgrade_js">您正在使用的浏览器版本过低。这意味着浏览器未升级前你无法观看实时行情！推荐使用以下浏览器的最新版本：</p>
    			<ul class="ul">
    				<li><a href="http://sw.bos.baidu.com/sw-search-sp/software/a7958cfcdbd6e/ChromeStandalone_53.0.2785.116_Setup.exe"><span class="Chrome"></span>谷歌浏览器</a></li>
    				<li><a href="http://download.firefox.com.cn/releases-sha2/stub/official/zh-CN/Firefox-latest.exe"><span class="Firefox"></span>火狐浏览器</a></li>
    				<li><a href="http://dlsw.baidu.com/sw-search-sp/gaosu/2015_08_31_13/bind2/12966/Safari_5.34.57.2_12966_BDdl.exe"><span class="Safari"></span>Safari浏览器</a></li>
    				<li><a href="http://down.360safe.com/se/360se8.1.1.226.exe"><span class="sll"></span>360浏览器</a></li>
    				<li><a href="http://sw.bos.baidu.com/sw-search-sp/software/8666749b3f7a7/IE10-Windows6.1-zh-cn.exe"><span class="IE"></span>IE浏览器</a></li>
    			</ul>
    			<div class="w_upgrade_xz">
    				<p class="xz_title">您可以一边升级浏览器版本，一边扫码下载维胜金融APP或客户端，随心，随时，随地进行交易</p>
    				<ul>
    					<li><a class="pc" href="http://socket.vs.com/download/%e7%bb%b4%e8%83%9c%e4%ba%a4%e6%98%93%e6%a0%87%e5%87%86%e7%89%88v1.0.zip"></a></li>
    					<li class="xz_title">PC标准版下载</li>
    				</ul>
    				<ul>
    					<li class="ios"></li>
    					<li class="xz_title">iOS下载</li>
    				</ul>
    				<ul>
    					<li class="android"></li>
    					<li class="xz_title">Android下载</li>
    				</ul>
    			</div>
    		</div>
    	</div>
    </div>
    <div class="w-qihuo">
        <div class="w_center_border"></div>
        <div class="w-qihuo-title">
            <h3>国际期货</h3>
            <a href="${ctx}/outDisk/index" target="_blank">查看更多</a>
        </div> 
        <div class="w-qihuo-content">
            <div class="w-qihuo-caopan">
                <div class="w-qihuo-img"><img src="static/images/image/qidai-1.png" alt=""/></div>
                <p><i class="gou"></i>交人民币保证金操盘美元账户</p>
                <p><i class="gou"></i>保证金交易 以小博大</p>
                <p><i class="gou"></i>白天晚上都可以交易</p>
                <p><i class="gou"></i>极速开户 T+0结算到账</p>
                <p style="padding-left: 0;"><a href="${ctx}/help?tab=rule&leftMenu=5" target="_blank">操盘细则</a><a href="${ctx}/help?tab=software&leftMenu=1" target="_blank">实盘系统下载</a></p>
            </div>
            <div class="w-guopjiqihuo">
                <h3>【恒指期货】</h3>
                <h4><i>${showmap.hsi[0].valueData }</i>元/单边</h4>
                <ul>
                    <li>• 监管严格，市场成熟</li>
                    <li>• 履约保证，更安全透明</li>
                    <li>• 高成本效益，更高收益</li>
                    <li>• 交易费低廉，双边低至69</li>
                </ul>
                <div class="money">
                    <p>总共操盘: ${showmap.hsi[1].valueData }人</p>
                    <p>总共交易: ${showmap.hsi[2].valueData }手</p>
                </div>
                <p class="chaopan"><a href="${ctx}/hsi/index" target="_blank">申请操盘</a></p>
            </div>
            <div class="w-guopjiqihuo">
                <h3>【国际原油】</h3>
                <h4><i>${showmap.fco[0].valueData }</i>元/单边</h4>
                <ul>
                    <li>• 轻原油，更优质</li>
                    <li>• 国际市场，更透明</li>
                    <li>• 交易时间长，更多盈利</li>
                    <li>• T+0双向，多头空头都有赚</li>
                </ul>
                <div class="money">
                    <p>总共操盘: ${showmap.fco[1].valueData }人</p>
                    <p>总共交易: ${showmap.fco[2].valueData }手</p>
                </div>
                <p class="chaopan"><a href="${ctx}/crudeoil/index" target="_blank">申请操盘</a></p>
            </div>
            <div class="w-guopjiqihuo" style="border-right: 10px solid #292929;">
                <h3>【富时A50】</h3>
                <h4><i>${showmap.ffa50[0].valueData }</i>元/单边</h4>
                <ul>
                    <li>• A股大盘，趋势投资</li>
                    <li>• 超低门槛，短线灵活</li>
                    <li>• 国际IF指数，主流品种</li>
                    <li>• 新加坡指数，更成熟透明</li>
                </ul>
                <div class="money">
                    <p>总共操盘: ${showmap.ffa50[1].valueData }人</p>
                    <p>总共交易: ${showmap.ffa50[2].valueData }手</p>
                </div>
                <p class="chaopan"><a href="${ctx}/ftse/index" target="_blank">申请操盘</a></p>
            </div>
        </div>
    </div>
</div>
</div>
<div style="background: #333; height: 580px;">
<div class="safe">
    <div class="safelist">
        <ul>
            <li>
                <p class="safelist-img"><img src="static/images/image/chuangxin.png"></p>
                <h2>模式创新</h2>
                <p class="safelist-lis">光速开户，降低交易门槛</p>
            </li>
            <li>
                <p class="safelist-img"><img src="static/images/image/zijin.png"></p>
                <h2>资金安全</h2>
                <p class="safelist-lis">专款专用，资金封闭管理</p>
            </li>
            <li>
                <p class="safelist-img"><img src="static/images/image/jiaoyi.png"></p>
                <h2>交易安全</h2>
                <p class="safelist-lis">杜绝对赌，保障您的交易安全</p>
            </li>
            <li>
                <p class="safelist-img"><img src="static/images/image/zhuanye.png"></p>
                <h2>专业指导</h2>
                <p class="safelist-lis">投资管家，提供指导策略</p>
            </li>
        </ul>
    </div>
</div>
<div class="xuanzhe">
    <div class="xuanzhelist">
        <p class="xuanzhe-title">炒期货为什么选择维胜</p>
        <p style="width: 850px;margin: 0 auto;">
            <img src="static/images/image/zjichao.png" alt=""/>
            <img src="static/images/image/vs.png" alt="" class="vs-img"/>
            <img src="static/images/image/weishengchao.png" alt=""/>
        </p>
    </div>
</div>
</div>
<div class="h_partner_app">
<div class="h_partner">
    <div class="h_partner_content">
        <p class="h_chosetitle"><span>合作伙伴与媒体报道</span></p>
        <p class="h_img">
            <a href="javascript:void(0)"><img src="static/images/image/tengxun-bank.png" style="margin-left: 0px" alt=""/></a>
            <a href="javascript:void(0)"><img src="static/images/image/xinlang-bank.png" alt=""/></a>
            <a href="javascript:void(0)"><img src="static/images/image/huaxing-bank.png" alt=""/></a>
            <a href="javascript:void(0)"><img src="static/images/image/huanan-bank.png" alt=""/></a>
            <a href="javascript:void(0)"><img src="static/images/image/anjin-bank.png" alt=""/></a>
            <a href="javascript:void(0)"><img src="static/images/image/xiangcai-bank.png" alt=""/></a>
            <a href="javascript:void(0)"><img src="static/images/image/zhida-bank.png" alt=""/></a>
            <a href="javascript:void(0)"><img src="static/images/image/zhongdian-bank.png" style="margin-right: 0px" alt=""/></a>
        </p>
    </div>
</div>
</div>
<div style="display:none;">
	<a href="http://www.vs.com/" >都城投资</a><a href="http://www.vs.com/" >投资达人</a>
	<a href="http://www.vs.com/" >外盘期货公司</a><a href="http://www.vs.com/" >国际期货开户</a>
	<a href="http://www.vs.com/" >国际期货公司</a><a href="http://www.vs.com/" >外盘期货交易模拟软件</a>
	<a href="http://www.vs.com/" >外盘期货</a><a href="http://www.vs.com/" >期货</a>
	<a href="http://www.vs.com/" >期货交易</a><a href="http://www.vs.com/" >股指期货</a>
	<a href="http://www.vs.com/" >成都期货</a><a href="http://www.vs.com/" >维胜</a>
	<a href="http://www.vs.com/" >直达期货</a><a href="http://www.vs.com/" >横华期货</a>
	<a href="http://www.vs.com/" >高盛期货</a><a href="http://www.vs.com/" >期权</a>
	<a href="http://www.vs.com/" >恒指期货</a><a href="http://www.vs.com/" >富时a50</a>
	<a href="http://www.vs.com/" >国际原油</a><a href="http://www.vs.com/" >外盘期货行情</a>
	<a href="http://www.vs.com/" >黄金行情</a><a href="http://www.vs.com/" >期货行情</a>
	<a href="http://www.vs.com/" >股票资讯</a><a href="http://www.vs.com/" >期货开户</a>
	<a href="http://www.vs.com/" >期货模拟</a><a href="http://www.vs.com/" >期货开户云</a>
</div>
<input type= "hidden" id = "dqCommodNo"/>
<!-- footer -->
<%@include file="../common/footer.jsp"%>
<!-- custom  -->
<script src="static/script/homepage/homepage.js?version=20151127"></script>
<%@ include file="/WEB-INF/views/common/count.jsp"%>
</body>
<script type="text/javascript" src = "static/script/homepage/homepage.trade.js"></script> 
</html>