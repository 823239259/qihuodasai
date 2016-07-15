<%-- <%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.umpay.api.log.SysOutLogger"%>
<%@page import="com.tzdr.common.utils.ConfUtil"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="zh-cmn-Hans">
<head>
	<meta charset="utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
	<meta name="renderer" content="webkit" />	
	<meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
	<meta name="HandheldFriendly" content="true" />
	<meta name="MobileOptimized" content="320" />
	<meta name="apple-mobile-web-app-title" content="html5" />
	<meta name="format-detection" content="telephone=no,email=no" />
	<meta name="apple-mobile-web-app-capable" content="yes" />
	<meta name="apple-mobile-web-app-status-bar-style" content="black" />
	<title>维胜 - 中国领先的互联网普惠金融平台 </title>
	<meta name="description" content="维胜投身普惠金融互联网服务，以网络平台为A股、港股、美股、富时A50、恒指期货、国际原油等金融产品的操盘提供便利条件。" />
	<%
		String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
		String casServerLoginUrl=ConfUtil.getContext("SSO.casServer.loginUrl");
		String imgPreURL = ConfUtil.getContext("banner.url");
	%>
	<c:set var="ctx" value="<%=basePath%>"></c:set>
	<c:set var="v" value="20151127"></c:set>
	<c:set var="imgPreURL" value="<%=imgPreURL %>"></c:set>
	
	<link rel="stylesheet" href="${ctx }/static/css/new_index.css">
    <script src="${ctx }/static/script/jquery-1.8.3.js"></script>
   
    
	<!-- common css -->
	<link rel="shortcut icon" href="${ctx}/static/ico/icon.png">
	<link rel="stylesheet" href="${ctx}/static/css/common.css?v=${v}">
	<!-- custom css -->
	<link rel="stylesheet" href="${ctx}/static/css/home.css?v=${v}">

	<!-- common js -->
	<script src="${ctx}/static/script/common/jquery-1.8.0.min.js"></script>
	<script type="text/javascript">
		var basepath = "<%=basePath%>" + "/";
		var casServerLoginUrl = "<%=casServerLoginUrl%>";
	</script>
 <script src="${ctx }/static/script/esl.js"></script>
    <script src="${ctx }/static/script/slide-box.js"></script>
</head>
<body>
<!-- header -->
<%@include file="../common/header.jsp"%>

<!-- 广告,登录块 -->
<!-- <div class="login"> -->
    <div class="loginbox">
    	<%
       		if(request.getSession().getAttribute("userName")!=null){
       	%>
		<div class="lgctn" id="logondiv">
            <div class="lg_user">您好，<i>${mobile}</i><a href="${ctx}/logout">【安全退出】</a></div>
            <ul class="lg_info">
                <li>
                    <label>账户余额：</label>
                    <span>${usermap.user_avlbal }元</span>
                </li>
                <li>
                    <label>A股融资：</label>
                    <span>${usermap.user_money }元</span>
                </li>
                <li>
                    <label>港股融资：</label>
                    <span>${usermap.hk_money }港元</span>
                </li>
                <li style="display:none;">
                    <label>代理等级：</label>
                    <span>${usermap.user_level }级</span>
                </li>
            </ul>
            <div class="lg_btn"><a href="${ctx}/user/account" style="margin-bottom:10px;">操盘账户</a><a href="<%=ConfUtil.getContext("p2p.user.account") %>" style="display:none;">投资账户</a></div>
            <p class="lg_time" style="display:block;">上次登录时间：<i>${lastLoginTime}</i></p>
        </div>
       	<%
       		}else{
       	%>
       	<div class="lgctn" id="logindiv">
        	<form id="loginForm" name="loginForm" action="<%=casServerLoginUrl%>" onsubmit="return loginValidate();" method="post" target="ssoLoginFrame">
            <input type="hidden" name="isajax" value="true" />
			<input type="hidden" name="isframe" value="true" />
			<input type="hidden" name="lt" value="" id="J_LoginTicket">
			<input type="hidden" name="execution" value="" id="J_FlowExecutionKey">
			<input type="hidden" name="_eventId" value="submit" />
			
            <h3>欢迎登录维胜</h3>
            <div class="lg_ip">
                <div class="lg_ipctn">
                    <i class="user"></i>
                    <input type="tel" id="username" name="username" value="" placeholder="请输入手机号码" />
                    <!-- 请输入手机号码 -->
                    <div id="uname-txt" style="display: none;">
	                    <span style="position: absolute; width: 150px; left: 32px; font-size: 14px; top: -40px; padding: 4px; background-color: rgb(255, 255, 0); border: 1px solid rgb(255, 255, 0); color: rgb(255, 0, 0); text-align: center;" >请输入手机号码</span>
	                    <i style="position: absolute;left: 32px;top: -11px;width: 0;height: 0;border-width: 6px 20px 20px;border-style: solid;border-color: #ff0 transparent transparent;" ></i>
                	</div>
                </div>
                <div class="lg_ipctn">
                    <i class="password"></i>
                    <input type="password" id="password" name="password" value="" placeholder="请输入登录密码" />
                    <!-- 请输入登录密码 -->
                    <div id="pwd-txt" style="display: none;">
	                    <span style="position: absolute; width: 150px; left: 32px; font-size: 14px; top: -40px; padding: 4px; background-color: rgb(255, 255, 0); border: 1px solid rgb(255, 255, 0); color: rgb(255, 0, 0); text-align: center;" >请输入登录密码</span>
	                    <i style="position: absolute;left: 32px;top: -11px;width: 0;height: 0;border-width: 6px 20px 20px;border-style: solid;border-color: #ff0 transparent transparent;" ></i>
                	</div>
                </div>
                <c:choose>
                	<c:when test="${isNeedCode}">
                		<div class="lg_ipcode" id="codeLi" style="display: block;">
		                    <input type="text" id="code" name="code" value="请输入验证码" placeholder="请输入验证码" />
		                    <img id="validateCode" src="validate.code"/>
		                </div>
                	</c:when>
                	<c:otherwise>
                		<div class="lg_ipcode" id="codeLi" style="display: none;">
		                    <input type="text" id="code" name="code" value="请输入验证码" placeholder="请输入验证码" />
		                    <img id="validateCode" src="validate.code"/>
		                </div>
                	</c:otherwise>
                </c:choose>
                <div class="lg_btn"><button id="login" type="button">立即登录</button></div>
            </div>
            <div class="lg_link">
                <a href="${ctx}/forgetpw" class="left">忘记密码?</a>
                <a href="${ctx}/signin" class="right">免费注册</a>
            </div>
            
            </form>
        </div>
       	<%
       		}
       	%>
    </div>
	<div class="bannerlist">        
        <!-- 广告切换 -->
        <div class="ad_slider"  id="ad-slider">
        	<c:forEach var="b" items="${banners }" varStatus="status">
            	<a href="javascript: void(0);" title="${status.count }" <c:if test="${status.index }==0">class="on"</c:if>>${status.count }</a>
        	</c:forEach>
        </div>
        <div class="slide_box" id="slide-box" style="display:block;">
        	<div class="slide_banner">
	        	<c:forEach var="b" items="${banners }" varStatus="status">
	            	<a href="${b.linkUrl }" target="_blank" style="display: none;"><img src="${imgPreURL }${b.imgPath }" title="banner" alt="banner"></a>
	        	</c:forEach>
        	</div>
        </div>
         <div class="login">
	        <div class="loginbox">
	            <h3>欢迎登陆维胜</h3>
	            <form id="loginForm" name="loginForm" action="" onsubmit="return" method="post" target="">
	                <input type="hidden" name="isajax" value="true">
	                <input type="hidden" name="isframe" value="true">
	                <input type="hidden" name="lt" value="" id="LoginTicket">
	                <input type="hidden" name="execution" value="e3s1" id="J_FlowExecutionKey">
	                <input type="hidden" name="_eventId" value="submit">
	                <div class="lg_ip">
	                    <div class="lg_ipctn">
	                        <i class="user"></i>
	                        <input type="tel" id="username" name="username" value="" placeholder="请输入手机号码">
	                        <!-- 请输入手机号码 -->
	                    </div>
	                    <div class="lg_ipctn">
	                        <i class="password"></i>
	                        <input type="password" id="password" name="password" value="" placeholder="请输入登录密码">
	                        <!-- 请输入登录密码 -->
	                    </div>
	                    <div class="lg_btn"><button id="login" type="button">立即登录</button></div>
	                    <div class="lg_link">
	                        <a href="#" class="left">忘记密码?</a>
	                        <a href="#" class="right">免费注册</a>
	                    </div>
	                </div>
	            </form>
	            <div class="lg_bottom"></div>
	        </div>
    	</div>
   </div>
<!-- </div> -->

<!-- 广告切换 -->
<div class="bannerlist">
    <div class="ad_slider" id="ad-slider">
        <a href="javascript: void(0);" title="1" class="on">1</a>
        <a href="javascript: void(0);" title="2" class="">2</a>
        <a href="javascript: void(0);" title="3" class="">3</a>
        <a href="javascript: void(0);" title="4" class="">4</a>
    </div>
    <div class="slide_box" id="slide-box">
        <div class="slide_banner">
        	<a href="javascript: void(0);" title="1" class="on">1</a>
	        <a href="javascript: void(0);" title="2" class="">2</a>
	        <a href="javascript: void(0);" title="3" class="">3</a>
	        <a href="javascript: void(0);" title="4" class="">4</a>
            <a href="#"><img src="https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1003704465,1400426357&fm=116&gp=0.jpg"></a>
            <a href="#"><img src="https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1003704465,1400426357&fm=116&gp=0.jpg"></a>
            <a href="#"><img src="https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1003704465,1400426357&fm=116&gp=0.jpg"></a>
            <a href="#"><img src="https://ss2.bdstatic.com/70cFvnSh_Q1YnxGkpoWK1HF6hhy/it/u=1003704465,1400426357&fm=116&gp=0.jpg"></a>
        </div>
    </div>
    <div class="login">
        <div class="loginbox">
            <h3>欢迎登陆维胜</h3>
            <form id="loginForm" name="loginForm" action="" onsubmit="return" method="post" target="">
                <input type="hidden" name="isajax" value="true">
                <input type="hidden" name="isframe" value="true">
                <input type="hidden" name="lt" value="" id="LoginTicket">
                <input type="hidden" name="execution" value="e3s1" id="J_FlowExecutionKey">
                <input type="hidden" name="_eventId" value="submit">
                <div class="lg_ip">
                    <div class="lg_ipctn">
                        <i class="user"></i>
                        <input type="tel" id="username" name="username" value="" placeholder="请输入手机号码">
                        <!-- 请输入手机号码 -->
                    </div>
                    <div class="lg_ipctn">
                        <i class="password"></i>
                        <input type="password" id="password" name="password" value="" placeholder="请输入登录密码">
                        <!-- 请输入登录密码 -->
                    </div>
                    <div class="lg_btn"><button id="login" type="button">立即登录</button></div>
                    <div class="lg_link">
                        <a href="#" class="left">忘记密码?</a>
                        <a href="#" class="right">免费注册</a>
                    </div>
                </div>
            </form>
            <div class="lg_bottom"></div>
        </div>
    </div>
</div>
<!-- 最新公告 -->
<div class="h_notic notice-scroll">
    <h2>最新动态：</h2>
    <ul class="h_noticlist" id="h_scroll">
        
    </ul>
    <a href="${ctx}/news/newsdata" class="h_n_more" target="_blank">更多&gt;&gt;</a>
</div>
<!-- 最新公告 -->
<div class="notice h_notic">
    <div class="notice_scroll">
        <h2><i></i>最新动态：</h2>
        <ul class="h_noticlist" id="h_scroll">
           <!--  <li><i></i><a href="#" target="_blank">测试平台初测结果</a><em>2015-11-24</em></li>
            <li><i></i><a href="#" target="_blank">配资最新详情</a><em>2015-11-24</em></li>
            <li><i></i><a href="#" target="_blank">好运</a><em>2015-11-24</em></li>
            <li><i></i><a href="#" target="_blank">配资</a><em>2015-11-24</em></li>
            <li><i></i><a href="#" target="_blank">股票</a><em>2015-11-24</em></li>
            <li><i></i><a href="#" target="_blank">龙胜</a><em>2015-11-24</em></li>
            <li><i></i><a href="#" target="_blank">投资</a><em>2015-11-24</em></li>
            <li><i></i><a href="#" target="_blank">测试</a><em>2015-11-24</em></li>
            <li><i></i><a href="#" target="_blank">好运</a><em>2015-11-24</em></li>
            <li><i></i><a href="#" target="_blank">配资</a><em>2015-11-24</em></li>
            <li><i></i><a href="#" target="_blank">股票</a><em>2015-11-24</em></li>
            <li><i></i><a href="#" target="_blank">龙胜</a><em>2015-11-24</em></li> -->
        </ul>
         <a href="${ctx}/news/newsdata" class="h_n_more" target="_blank">更多</a>
    </div>
</div>
<!-- 安全保证 -->
<div class="h_safe">
    <ul class="h_safelist">
        <li>
            <img src="static/images/home-new/safe_04.png">
            <h3>模式创新</h3>
            <p>光速开户<br>降低交易门槛</p>
        </li>
        <li>
            <img src="static/images/home-new/safe_01.png">
            <h3>资金安全</h3>
            <p>专款专用<br>资金封闭管理</p>
        </li>
        <li>
            <img src="static/images/home-new/safe_02.png">
            <h3>交易安全</h3>
            <p>杜绝对赌<br>保障您的交易安全</p>
        </li>
        <li>
            <img src="static/images/home-new/safe_03.png">
            <h3>专业指导</h3>
            <p>投资管家<br>提供指导策略</p>
        </li>
    </ul>
</div>

<!-- 国际期货 -->
<div class="h_main h_future">
     <div class="h_m_left">
        <p class="hm_l_mainpic"><img src="static/images/home-new/title_02.gif"></p>
        <ul class="hm_l_font">
            <li><img src="static/images/home-new/icon_01.png"><i>交人民币保证金操盘美元账户</i></li>
            <li><img src="static/images/home-new/icon_01.png"><i>保证金交易 以小博大</i></li>
            <li><img src="static/images/home-new/icon_01.png"><i>白天晚上都可以交易</i></li>
            <li><img src="static/images/home-new/icon_01.png"><i>极速开户  T+0结算到账</i></li>
        </ul>
        <h3>操盘软件使用直达快抢手：</h3>
        <div class="hm_l_btn">
            <a href="${ctx}/help?tab=rule&leftMenu=5" target="_blank">操盘细则</a>
            <a href="${ctx}/help?tab=software&leftMenu=8" target="_blank">实盘系统下载</a>
        </div>
    </div>
    <div class="h_m_main">
        <div class="hm_future">      
         <h3>恒指期货</h3>
            <h4><i>${showmap.hsi[0].valueData }元</i>/单边</h4>
            <p class="hm_f_font">• 港股指数  金融市场更加成熟<br>• 交易灵活  技术分析更有效<br>• 一天12小时可交易  盈利时间长<br>• T+0交易  随时锁定利润</p>
            <p class="hm_f_money">总共操盘: <em class="space">${showmap.hsi[1].valueData }人</em>总共交易: <em>${showmap.hsi[2].valueData }手</em></p>
            <a href="${ctx}/hsi/index" target="_blank">申请操盘</a>            
        </div>         
        <div class="hm_future">
            <h3>富时A50</h3>
            <h4><i>${showmap.ffa50[0].valueData }元</i>/单边</h4>
            <p class="hm_f_font">• A股精准风向标  免庄家操控<br>• 门槛低 超短线 交易灵活<br>• 国际版“IF指数” 操盘人数多<br>• 来自新加坡交易所  透明公正</p>
            <p class="hm_f_money">总共操盘: <em class="space">${showmap.ffa50[1].valueData }人</em>总共交易: <em>${showmap.ffa50[2].valueData }手</em></p>
            <a href="${ctx}/ftse/index" target="_blank">申请操盘</a>
        </div>    
        <div class="hm_future hm_lastfuture">
            <h3>国际原油</h3>
            <h4><i>${showmap.fco[0].valueData }元</i>/单边</h4>
            <p class="hm_f_font">• 全球玩家用户量最大 涨跌迅猛<br>• 高透明度便于基本面分析<br>• 全球交易市场无人操控<br>• 来自纽约商业交易所  行业标准</p>
            <p class="hm_f_money">总共操盘: <em class="space">${showmap.fco[1].valueData }人</em>总共交易: <em>${showmap.fco[2].valueData }手</em></p>
            <a href="${ctx}/crudeoil/index" target="_blank">申请操盘</a>
        </div>
    </div>
</div>

<!-- 商品期货 -->
<div class="h_main h_goods">
    <div class="h_m_left">
        <p class="hm_l_mainpic"><img src="static/images/home-new/title_03.gif"></p>
        <ul class="hm_l_font">
            <li><img src="static/images/home-new/icon_01.png"><i>最高20倍高杠杆  高收益</i></li>
            <li><img src="static/images/home-new/icon_01.png"><i>可双向持仓  涨跌都赚钱</i></li>
            <li><img src="static/images/home-new/icon_01.png"><i>白天晚上都可以交易</i></li>
            <li><img src="static/images/home-new/icon_01.png"><i>极速开户  T+0结算到账</i></li>
        </ul>
        <h3>操盘软件使用博易大师：</h3>
        <div class="hm_l_btn">
            <a href="${ctx}/help?tab=rule&leftMenu=7" target="_blank">操盘细则</a>
            <a href="${ctx}/help?tab=software&leftMenu=9" target="_blank">实盘系统下载</a>
        </div>
    </div>
    <div class="h_m_main">
        <div class="hm_goods">
            <!-- 沪金 -->
            <div class="hm_g_list hm_g_au">
                <p class="hm_g_pic"><img src="static/images/home-new/good_01.png"></p>
                <ul class="hm_g_font">
                    <li><i>${showmap.fhsgolden[0].valueData }</i>元/单边</li>
                    <li><i>${showmap.fhsgolden[1].valueData }</i>元/手  最低本金</li>  
                    <li class="hm_gf_num">总共操盘：<i>${showmap.fhsgolden[2].valueData }人</i></li>
                    <li class="hm_gf_num">总共交易：<i>${showmap.fhsgolden[3].valueData }手</i></li>
                </ul>
                <a href="${ctx}/commodity/index" target="_blank">申请操盘</a>
            </div>
            <!-- 沪银 -->
            <div class="hm_g_list hm_g_ag">
                <p class="hm_g_pic"><img src="static/images/home-new/good_02.png"></p>
                <ul class="hm_g_font">
                    <li><i>${showmap.fhssilver[0].valueData }</i>元/单边</li>
                    <li><i>${showmap.fhssilver[1].valueData }</i>元/手  最低本金</li>  
                    <li class="hm_gf_num">总共操盘：<i>${showmap.fhssilver[2].valueData }人</i></li>
                    <li class="hm_gf_num">总共交易：<i>${showmap.fhssilver[3].valueData }手</i></li>
                </ul>
                <a href="${ctx}/commodity/index" target="_blank">申请操盘</a>
            </div>
            <!-- 沪铜 -->
            <div class="hm_g_list hm_g_cu">
                <p class="hm_g_pic"><img src="static/images/home-new/good_03.png"></p>
                <ul class="hm_g_font">
                    <li><i>${showmap.fhscopper[0].valueData }</i>元/单边</li>
                    <li><i>${showmap.fhscopper[1].valueData }</i>元/手  最低本金</li>
                    <li class="hm_gf_num">总共操盘：<i>${showmap.fhscopper[2].valueData }人</i></li>
                    <li class="hm_gf_num">总共交易：<i>${showmap.fhscopper[3].valueData }手</i></li>
                </ul>
                <a href="${ctx}/commodity/index" target="_blank">申请操盘</a>
            </div>
            <!-- 橡胶 -->
            <div class="hm_g_list hm_g_ru">
                <p class="hm_g_pic"><img src="static/images/home-new/good_04.png"></p>
                <ul class="hm_g_font">
                    <li><i>${showmap.frubber[0].valueData }</i>元/单边</li>
                    <li><i>${showmap.frubber[1].valueData }</i>元/手  最低本金</li>
                    <li class="hm_gf_num">总共操盘：<i>${showmap.frubber[2].valueData }人</i></li>
                    <li class="hm_gf_num">总共交易：<i>${showmap.frubber[3].valueData }手</i></li>
                </ul>
                <a href="${ctx}/commodity/index" target="_blank">申请操盘</a>
            </div>
        </div>
        <!-- 排行榜 -->
        <div class="hm_g_rank">
            <h3>期货操盘盈利排行榜</h3>
            <ul class="hm_g_ranklist">
            	<li  class="first">
                    <i>1</i>
                    <span>${fptop1[0] }</span>
                    <em>${fptop[0].valueData }元</em>
                </li>
                <li class="second">
                    <i>2</i>
                    <span>${fptop1[1] }</span>
                    <em>${fptop[1].valueData }元</em>
                </li>
                <li class="third">
                    <i>3</i>
                    <span>${fptop1[2] }</span>
                    <em>${fptop[2].valueData }元</em>
                </li>
                <li>
                    <i>4</i>
                    <span>${fptop1[3] }</span>
                    <em>${fptop[3].valueData }元</em>
                </li>
                <li>
                    <i>5</i>
                    <span>${fptop1[4] }</span>
                    <em>${fptop[4].valueData }元</em>
                </li>
                <li>
                    <i>6</i>
                    <span>${fptop1[5]}</span>
                    <em>${fptop[5].valueData }元</em>
                </li>
                <li>
                    <i>7</i>
                    <span>${fptop1[6] }</span>
                    <em>${fptop[6].valueData }元</em>
                </li>
                <li>
                    <i>8</i>
                    <span>${fptop1[7] }</span>
                    <em>${fptop[7].valueData }元</em>
                </li>
                <li>
                    <i>9</i>
                    <span>${fptop1[8] }</span>
                    <em>${fptop[8].valueData }元</em>
                </li>
                <li>
                    <i>10</i>
                    <span>${fptop1[9] }</span>
                    <em>${fptop[9].valueData }元</em>
                </li>
            </ul>
        </div>
    </div>
</div>

<!-- 港股操盘 -->
<div class="h_main hm_hk">
    <div class="h_m_left">
        <p class="hm_l_mainpic"><img src="static/images/home-new/title_01.gif"></p>
        <ul class="hm_l_font">
            <li><img src="static/images/home-new/icon_01.png"><i>交人民币保证金操盘港币账户</i></li>
            <li><img src="static/images/home-new/icon_01.png"><i>操盘港股 享T+0操盘方式</i></li>
            <li><img src="static/images/home-new/icon_01.png"><i>我出资 您炒股 盈利全归您</i></li>
            <li><img src="static/images/home-new/icon_01.png"><i>极速开户 按实时汇率结算</i></li>
        </ul>
        <h3>操盘软件使用钱隆TTS：</h3>
        <div class="hm_l_btn">
            <a href="${ctx}/help?tab=configuration&leftMenu=1" target="_blank">操盘指南</a>
            <a href="${ctx}/help?tab=software&leftMenu=1" target="_blank">实盘系统下载</a>
        </div>
    </div>
    <div class="h_m_main">
        <ul class="hm_hkpic">
            <li>
                <img src="static/images/home-new/hk_01.png">
                <h3>投资保证金</h3>
                <p>1万元人民币起</p>
            </li>
            <li>
                <img src="static/images/home-new/hk_02.png">
                <h3>融资倍数</h3>
                <p>1倍 ~ 3倍</p>
            </li>
            <li>
                <img src="static/images/home-new/hk_03.png">
                <h3>总操盘资金</h3>
                <p>最高50万港元</p>
            </li>
            <li>
                <img src="static/images/home-new/hk_04.png">
                <h3>操盘天数</h3>
                <p>3天 ~ 30天</p>
            </li>
        </ul>
        <div class="hm_hksteptitle">操作流程</div>
        <div class="hm_hkstep">
            <img src="static/images/home-new/hk_05.png">
            <i>申请方案</i>
            <img src="static/images/home-new/hk_09.png">
            <img src="static/images/home-new/hk_06.png">
            <i>支付本金</i>
            <img src="static/images/home-new/hk_09.png">
            <img src="static/images/home-new/hk_07.png">
            <i>获取账号</i>
            <img src="static/images/home-new/hk_09.png">
            <img src="static/images/home-new/hk_08.png">
            <i>操盘盈利</i>
        </div>
        <div class="hm_hkbtn"><a href="${ctx}/hkstock/index" target="_blank">申请操盘</a></div>
        <p class="hm_hkpromt" style="display:none;">即将上线敬请期待！</p>
    </div>
</div>

<!-- 选择我们 -->
<div class="h_box">
    <h2 class="h_chosetitle">炒股、炒期货为什么选维胜？</h2>
    <ul class="hc_box fl">
        <li class="title"><label>自己炒股</label><span>在维胜炒股</span></li>
        <li><label>1万本金</label><span>1万保证金+<i>融资4万操盘金</i></span></li>
        <li><label>买股票10手</label><span>买股票<i>50手</i></span></li>
        <li><label>一个涨停</label><span>一个涨停</span></li>
        <li><label>赚：1000元</label><span>赚：<i>5000元</i>（付少许管理费+利息）</span></li>
    </ul>
    <ul class="hc_box fr">
        <li class="title"><label>自己炒期货</label><span>在维胜炒期货</span></li>
        <li><label>11万（本金+保证金）</label><span>1万保证金+<i>融资20万操盘金</i></span></li>
        <li><label>只能买1手恒指</label><span>可买<i>2手</i>恒指</span></li>
        <li><label>涨10%</label><span>涨10%</span></li>
        <li><label>赚：9000元</label><span>赚：<i>18000元</i>（管理费+利息全免）</span></li>
    </ul>
</div>

<!-- 合作伙伴 -->
<div class="h_box">
    <h2><em>合作伙伴 &amp; 媒体报道</em><i></i></h2>
    <div class="h_m_partner">
        <img src="static/images/home-new/partner/pa_15.gif">
    </div>
</div>

<!-- footer -->
<%@include file="../common/footer.jsp"%>

<!-- custom js -->
<script src="static/script/homepage/homepage.js?version=20151127"></script>
<%@ include file="/WEB-INF/views/common/count.jsp"%>
</body>
</html>
 --%>
 <%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.umpay.api.log.SysOutLogger"%>
<%@page import="com.tzdr.common.utils.ConfUtil"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 <!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
	<meta name="renderer" content="webkit" />	
	<meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
	<meta name="HandheldFriendly" content="true" />
	<meta name="MobileOptimized" content="320" />
	<meta name="apple-mobile-web-app-title" content="html5" />
	<meta name="format-detection" content="telephone=no,email=no" />
	<meta name="apple-mobile-web-app-capable" content="yes" />
	<meta name="apple-mobile-web-app-status-bar-style" content="black" />
	<title>维胜 - 中国领先的互联网普惠金融平台 </title>
	<meta name="description" content="维胜投身普惠金融互联网服务，以网络平台为A股、港股、美股、富时A50、恒指期货、国际原油等金融产品的操盘提供便利条件。" />
	<%
		String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
		String casServerLoginUrl=ConfUtil.getContext("SSO.casServer.loginUrl");
		String imgPreURL = ConfUtil.getContext("banner.url");
	%>
	<c:set var="ctx" value="<%=basePath%>"></c:set>
	<c:set var="v" value="20151127"></c:set>
	<c:set var="imgPreURL" value="<%=imgPreURL %>"></c:set>
	<%-- <link rel="stylesheet" href="${ctx}/static/css/common.css?v=${v}">
	<!-- custom css -->
	<link rel="stylesheet" href="${ctx}/static/css/home.css?v=${v}"> --%>
	<link rel="stylesheet" href="${ctx }/static/css/new_index.css">
	<link href="${ctx }/static/css/trade.css?v=20151127" rel="stylesheet" type="text/css" />
    <script src="${ctx }/static/script/jquery-1.8.3.js"></script>
    <script src="${ctx }/static/script/homepage/gundongtiao.js"></script>
    
	<!-- common css -->
	<link rel="shortcut icon" href="${ctx}/static/ico/icon.png">


	<!-- common js -->
	<script src="${ctx}/static/script/common/jquery-1.8.0.min.js"></script>
	<script type="text/javascript">
		 var basepath = "<%=basePath%>" + "/";
		var casServerLoginUrl = "<%=casServerLoginUrl%>";
	</script>
 <script src="${ctx }/static/script/esl.js"></script>
    <script src="${ctx }/static/script/slide-box.js"></script>
<style type="text/css">
	.ft_wx a:hover { background: url(../static/images/common-new/wxon.png) no-repeat; }
	.ft_wx a { display: block; width: 50px; height: 50px; background: url(../static/images/common-new/wx.png) no-repeat;}
	.navlist li a.on{color: #ffcc33; border-bottom:2px solid #ffcc33; padding-bottom: 26px;}
	.left_hidden{float: left; width: 195px; }
	#left_xiangqing{background: #333; float: left;height: 508px;  width: 195px; position: relative;overflow: hidden;}
    #left_xiangqing .w_content .left_hidden{float: left; width: 195px;}
    #left_xiangqing .scroll_y{background: #fc3;position: absolute; right: 0;width: 5px;}
    #left_xiangqing .scroll_ymove{background: #292929; height: 508px;position: absolute; right: 0;width: 5px;z-index: 2;}
</style>
</head>

<body>
<!-- header -->
<%@include file="../common/header.jsp"%>
<%-- <div class="top-title">
    <div class="topctn">
        <div class="top_tel"><!--<i></i>-->全国客服热线：400-852-8008$</div>
        <ul>
            <li class="sign"><a href="${ctx}/user/account">登录</a><span class="sign_span"> | </span><a href="${ctx}/signin">注册</a></li>
            <li><a href="${ctx}/help?tab=software&amp;leftMenu=1" target="_blank">交易软件下载</a></li>
            <!--<li><a href="http://test.www.dktai.com:80/topic/app/" target="_blank">手机APP</a></li>-->
           <!-- <li><a href="http://zhibo.tzdr.com" target="_blank">喊单直播间</a></li>-->
            <li><a href="${ctx}/help?tab=newbie&amp;leftMenu=1" target="_blank">新手指南</a></li>
        </ul>
    </div>
</div> --%>
<%-- <div class="navbox">
    <div class="nav">
        <div class="navlogo"><a href="#"><img src="${ctx}/static/images/common-new/new_logo.png" title="维胜" alt="维胜"></a></div>
        <ul class="navlist">
            <li><a class="on"  href="${ctx}/" style="padding: 27px 16px 26px 16px;">首页</a></li>
             <li><a href="${ctx}/hsi/index">恒指期货</a></li>
            <li><a href="${ctx}/crudeoil/index">国际原油</a></li>
            <li><a href="${ctx}/ftse/index">富时A50</a></li>
            <li><a href="${ctx}/outDisk/index">国际综合</a></li>
             <%
        		if(request.getSession().getAttribute("userName") !=null){
        	%>
           		<li><a id="nav_my" href="${ctx}/user/account" class="nav_l_mc">我的账户</a></li>
        	<%
        		}else{
        	%>
            	<li><a id="nav_my" href="${ctx}/user/account" class="nav_l_mcnot">我的账户</a></li>
        	<%
        		}
        	%>
        </ul>
    </div>
</div> --%>
<!-- 广告切换 -->
<div class="bannerlist">
    <div class="ad_slider" id="ad-slider">
        <c:forEach var="b" items="${banners }" varStatus="status">
        	<a  title="${status.count }" <c:if test="${status.index }==0">class="on"</c:if>></a>
        </c:forEach>
    </div>
    <div class="slide_box" id="slide-box">
       	<div class="slide_banner">
           <c:forEach var="b" items="${banners }" varStatus="status">
	    	<a href="${b.linkUrl }" target="_blank" style="display: none;"><img src="${imgPreURL }${b.imgPath }" title="banner" alt="banner"></a>
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
            		<%-- <a href="<%=ConfUtil.getContext("p2p.user.account") %>" style="display:none;">投资账户</a> --%>
            	</div>
            	<p class="lg_time" style="display:block;">上次登录时间：<br><i>${lastLoginTime}</i></p>
			</div>
        	<p style="border-top: 1px solid #4d4d4d"></p>
                <%-- <li>
                    <label>A股融资：</label>
                    <span>${usermap.user_money }元</span>
                </li>
                <li>
                    <label>港股融资：</label>
                    <span>${usermap.hk_money }港元</span>
                </li>
                <li style="display:none;">
                    <label>代理等级：</label>
                    <span>${usermap.user_level }级</span>
                </li> --%>
        </div>
       	<%
       		}else{
       	%>
        <div class="lgctn" id="logindiv">
            <h3>登录</h3>
             <form id="loginForm" name="loginForm" action="<%=casServerLoginUrl%>" onsubmit="return loginValidate();" method="post" target="ssoLoginFrame">
                <input type="hidden" name="isajax" value="true">
                <input type="hidden" name="isframe" value="true">
                <input type="hidden" name="lt" value="" id="LoginTicket">
                <input type="hidden" name="execution" value="e3s1" id="J_FlowExecutionKey">
                <input type="hidden" name="_eventId" value="submit">
                <div class="lg_ip">
                    <div class="lg_ipctn">
                        <i class="user"></i>
                        <input type="tel" id="username" name="username" value="" placeholder="请输入手机号码">
                        <!-- 请输入手机号码 -->
                    </div>
                    <div class="lg_ipctn">
                        <i class="password"></i>
                        <input type="password" id="password" name="password" value="" placeholder="请输入登录密码">
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
<div class="notice  h_notic">
    <div class="notice_scroll">
        <h2><i></i>最新动态：</h2>
        <ul class="h_noticlist" id="h_scroll">
        </ul>
         <a href="${ctx}/news/newsdata" class="h_n_more" target="_blank">更多动态</a>
    </div>
</div>
<!--content-->
<div class="w_content">
    <div class="w_center">
        <div class="w_center_border"></div>
        <div class="w_center_top">
            <div class="left-shangzheng">
                <p>实时行情</p>
            </div>
            <div class="right-gengxin">
                <p><span class = "jk"></span><span class = "fd"></span></p>
                <p><span class = "zs"></span><span class = "gxsj"></span></p>
                <a href = "#" id = "mainSqcp" target="_blank">申请操盘</a>
            </div>
        </div>
        <div class="w_center_xiangqing">
            <div class="left_xiangqing" id="left_xiangqing">
                <div class="left_hidden">
                   
                   
                </div>
                <div class="scroll_ymove">
        			<div class="scroll_y" unorbind="unbind"></div>
    			</div>
		    <!--<div class="scroll_xmove">
		        	<div class="scroll_x" unorbind="unbind"></div>
		    	</div>-->
    			<input type="hidden" id="whichscro">
            </div>
            <div style="width: 5px; height: 510px; background: #292929; float: left; position: relative; right: 5px;
            "></div>
            <div class="right_xiangqing">
                <div id="main" style="height:500px; width: 800px;"></div>
            </div>
        </div>
    </div>
    <script type="text/javascript">
        	
        </script>
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
                <h4><i>40</i>元/单边</h4>
                <ul>
                    <li>• 港股指数 金融市场更加成熟</li>
                    <li>• 交易灵活 技术分析更有效</li>
                    <li>• 一天12个小时可以交易 盈利时间长</li>
                    <li>• T+0交易 随时锁定利润</li>
                </ul>
                <div class="money">
                    <p>总共操盘: 4795人</p>
                    <p>总共交易: 41733手</p>
                </div>
                <p class="chaopan"><a href="${ctx}/hsi/index" target="_blank">申请操盘</a></p>
            </div>
            <div class="w-guopjiqihuo">
                <h3>【国际原油】</h3>
                <h4><i>45</i>元/单边</h4>
                <ul>
                    <li>• 全球玩家用户量最大 涨跌迅猛</li>
                    <li>• 高透明度便于基本面分析</li>
                    <li>• 全球交易市场无人操控</li>
                    <li>• 全球交易市场无人操控</li>
                </ul>
                <div class="money">
                    <p>总共操盘: 4795人</p>
                    <p>总共交易: 41733手</p>
                </div>
                <p class="chaopan"><a href="${ctx}/crudeoil/index" target="_blank">申请操盘</a></p>
            </div>
            <div class="w-guopjiqihuo" style="border-right: 10px solid #292929;">
                <h3>【富时A50】</h3>
                <h4><i>39</i>元/单边</h4>
                <ul>
                    <li>• A股精准风向标 免庄家操控</li>
                    <li>• 门槛低 超短线 交易灵活</li>
                    <li>• 国际版“IF指数” 操盘人数多</li>
                    <li>• 国际版“IF指数” 操盘人数多</li>
                </ul>
                <div class="money">
                    <p>总共操盘: 4795人</p>
                    <p>总共交易: 41733手</p>
                </div>
                <p class="chaopan"><a href="${ctx}/ftse/index" target="_blank">申请操盘</a></p>
            </div>
        </div>
    </div>
</div>
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
        <p>
            <img src="static/images/image/zjichao.png" alt=""/>
            <img src="static/images/image/vs.png" alt="" class="vs-img"/>
            <img src="static/images/image/weishengchao.png" alt=""/>
        </p>
    </div>
</div>
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
<input type= "hidden" id = "dqCommodNo"/>
<!-- footer -->
<%@include file="../common/footer.jsp"%>
<!-- custom js -->
<script src="static/script/homepage/homepage.js?version=20151127"></script>
<%@ include file="/WEB-INF/views/common/count.jsp"%>
</body>
<script type="text/javascript">
    /* var href = window.location.href;
 	// 路径配置
    require.config({
        paths:{//${ctx }/static/script
            'echarts' :href + '/static/script/echarts',
            'echarts/chart/pie' :href + '/static/script/echarts'
        }
    });
 	
    var rawData = [];
    var loadCount = 0;
    var loadKcount = 0 ;
    var	 myChart = null;
    //加载K线图数据模型
    function loadKData(commodity,contract){
    	$(function(){
    		var rawDataLength = rawData.length - 1;
            var url = "Quotation/doGetQk?commodity="+commodity+"&contract="+contract;
            if(rawDataLength > 0){
            	var beginTime = rawData[rawDataLength][0];
            	url +="&beginTime="+beginTime;
            }
            $.ajax({
	           	 url : url,
	   			 type:"get",
	   			 dateType:"json",
	   			 success:function(data){
	   				 var resultData = data.data;
	   				 var addKData = [];
	   					$.each(resultData,function (i,item){
	   						var openPrice = item.OpenPrice;
	   						var closePrice = item.LastPrice;
	   						var chaPrice = closePrice - openPrice;
	   						var sgData = [item.DateTime,openPrice,closePrice,chaPrice,"",item.LowPrice,item.HighPrice,"","","-"];
	   						addKData[i] = sgData;
	   					});
	   					//追加到容器中
	   					var option = setOption(addKData);
	   					myChart.setOption(option);
	   				 }
            });
    	});
    }
    //设置数据参数（为画图做准备）
    function setOption(rawData){
    	var dates = rawData.map(function (item) {
            return item[0];
        });

        var data = rawData.map(function (item) {
            return [+item[1], +item[2], +item[5], +item[6]];
        });
    	var option = {
                title: {
                    text: '1分钟K线',
                    textStyle: {
                    	color : "#ffcc33"
                    }
                },
                backgroundColor: '#333',
                tooltip: {
                    trigger: 'axis',
                    axisPointer: {
                        animation: false,
                        lineStyle: {
                            color: '#376df4',
                            width: 2,
                            opacity: 1
                        }
                    }
                },
                xAxis: {
                    type: 'category',
                    data: dates,
                    axisLine: { lineStyle: { color: '#8392A5' } }
                },
                yAxis: {
                    scale: true,
                    axisLine: { lineStyle: { color: '#8392A5' } },
                    splitLine: { show: false }
                },
                dataZoom: [{
                    textStyle: {
                        color: '#8392A5'
                    },
                    handleIcon: 'M10.7,11.9v-1.3H9.3v1.3c-4.9,0.3-8.8,4.4-8.8,9.4c0,5,3.9,9.1,8.8,9.4v1.3h1.3v-1.3c4.9-0.3,8.8-4.4,8.8-9.4C19.5,16.3,15.6,12.2,10.7,11.9z M13.3,24.4H6.7V23h6.6V24.4z M13.3,19.6H6.7v-1.4h6.6V19.6z',
                    handleSize: '80%',
                    dataBackground: {
                        areaStyle: {
                            color: '#8392A5'
                        },
                        lineStyle: {
                            opacity: 0.8,
                            color: '#8392A5'
                        }
                    },
                    handleStyle: {
                        color: '#fff',
                        shadowBlur: 3,
                        shadowColor: 'rgba(0, 0, 0, 0.6)',
                        shadowOffsetX: 2,
                        shadowOffsetY: 2
                    }
                }, {
                    type: 'inside'
                }],
                animation: false,
                series: [
                    {
                        type: 'candlestick',
                        name:"1分钟K线",
                        data: data,
                        itemStyle: {
                            normal: {
                                color: '#FD1050',
                                color0: '#0CF49B',
                                borderColor: '#FD1050',
                                borderColor0: '#0CF49B'
                            }
                        }
                    }
                ]
            };
    	return option;
    }
    function calculateMA(dayCount, data) {
        var result = [];
        for (var i = 0, len = data.length; i < len; i++) {
            if (i < dayCount) {
                result.push('-');
                continue;
            }
            var sum = 0;
            for (var j = 0; j < dayCount; j++) {
                sum += data[i - j][1];
            }
            result.push(sum / dayCount);
        }
        return result;
    } 
    
    //生成一个K线图容器
    function loadK(){
    	 // 使用
        require(
                [
                    'echarts',
                    'echarts/chart/pie' // 使用柱状图就加载bar模块，按需加载
                ],
                function (ec) {
                	
                    // 基于准备好的dom，初始化echarts图表
                    myChart = ec.init(document.getElementById('main'));
                	var option = setOption(rawData);
    			    // 为echarts对象加载数据
	    			myChart.setOption(option);
                }
        );
    }
    var interValData = null;
    function bindLoadKDataInterValData(CommodityNo,contract){
    	interValData = window.setInterval(function(){loadKData(CommodityNo,contract)}, 3000);
 		loadCount ++;
    }
    function clearLoadKDataInterVal(){
    	if(interValData != null){
	    	window.clearInterval(interValData);
    	}
    }
    function exctionLoadK(CommodityNo,contract){
	 	clearLoadKDataInterVal();
	 	bindLoadKDataInterValData(CommodityNo,contract);
    }
    function propHrefCP(commod){
    	$(function(){
	    	if(commod == "HSI"){
		 		 $("#mainSqcp").prop("href",href + "hsi/index");
		 	 }else if(commod == "CL"){
		 		 $("#mainSqcp").prop("href",href + "crudeoil/index");
		 	 }else if(commod == "CN"){
		 		 $("#mainSqcp").prop("href",href + "ftse/index");
		 	 }else{
		 		 $("#mainSqcp").prop("href",href + "outDisk/index");
		 	 }
    	});
    }
   $(function(){
	 		//初始化K线图容器
	   		loadK();
    		$(".w_center_xiangqing .left_xiangqing .left_hidden").html("");
			$.ajax({
				url:"Quotation/doGetCommodity",
				type:"get",
				dateType:"json",
				success:function(result){
					 var data = result.data;
					 for(var i = 0 ; i < data.length; i++){
						 var html = '';
						 var item = data[i];
						 var size = item.DotSize;
						 var _data = item.data;
						 var commodityName = item.commodityName;
						 var qlastPrice = (parseFloat(_data.QLastPrice)).toFixed(size);
						 var qpreCloseingPrice = (parseFloat(_data.QPreClosingPrice)).toFixed(size);
						 var qLowPrice = (parseFloat(_data.QLowPrice)).toFixed(size);
						 var qHighPrice = (parseFloat(_data.QHighPrice)).toFixed(size);
						 var qOpenPrice = (parseFloat(_data.QOpenPrice)).toFixed(size);
						 var scal = (parseFloat(_data.QChangeRate)).toFixed(size);
						 var bs = "↑";
						 var jj = "+";
						 var color = " #ff5500";
						 if(scal < 0){
							 bs = "↓";
							 jj = "";
							 color = "#0bffa4";
						 }
						 html += '<div topData = "'+qpreCloseingPrice+'&'+qLowPrice+'&'+qHighPrice+'&'+qOpenPrice+'&'+_data.TimeStamp+'" data = "'+_data.CommodityNo+'&'+item.contract+'"  class="left_xiangmu left_x'+i+'';
						 if(loadCount  == 0){
						 	 html += ' on';
						 	loadKData(_data.CommodityNo,item.contract);
						 	exctionLoadK(_data.CommodityNo,item.contract);
						 	propHrefCP(_data.CommodityNo);
						 	$(".zs").text(qpreCloseingPrice);	
						    $(".fd").text(qLowPrice+' - '+qHighPrice);
						    $(".jk").text(qOpenPrice);
						    $(".gxsj").text(_data.TimeStamp);
						    $("#dqCommodNo").val(_data.CommodityNo);
						 }
						 html +=  ' "> <p><em style="" class = "right">'+commodityName+'</em>'
						 	  + '<span class = "qlast'+i+' left" style="color: '+color+';">'+ jj  + qlastPrice + " " + bs +'</span>'
						 	  + '<span class = "qchange'+i+' right" style="color: '+color+';">'+ jj + (parseFloat(_data.QChangeValue)).toFixed(size)+'</span>'
						 	  + '<span class = "scal'+i+' left" style="color: '+color+';">'+ jj + scal  + "%" +'</span>';
						 $(".w_center_xiangqing .left_xiangqing .left_hidden").append(html);
						 $(".left_x"+i+"").bind("click",function(){
							 loadK();
							 var obj = $(this);
							 var da = obj.attr("data");
							 if(da != null){
								  rawData=[];
							 	  var daArray = da.split("&");
							 	  var commod = daArray[0];
							 	  var contract = daArray[1];
							 	  loadKData(commod,contract);
							 	  exctionLoadK(commod,contract);
							 	  $("#dqCommodNo").val(commod);
							 	  propHrefCP(commod);
							 }
							 var topData = obj.attr("topData");
							 if(topData != null){
								 var topDataArray = topData.split("&");	
								 $(".zs").text("昨收:"+topDataArray[0]);	
								 $(".fd").text("每日幅度:"+topDataArray[1]+' - '+topDataArray[2]);
								 $(".jk").text("今开:"+topDataArray[3]);
								 $(".gxsj").text("更新时间:"+_data.TimeStamp);
							 }
							 var left_xiangmu   = $(".w_content .w_center_xiangqing .left_xiangmu");
							 left_xiangmu.each(function(){
								 left_xiangmu.removeClass('on');
							 });
							  obj.addClass('on');
						 });
					 }
				}
			});
		});
	   window.setInterval(function(){ $(function(){
		   	$.ajax({
		   		url:"Quotation/doGetCommodity",
					type:"get",
					dateType:"json",
					success:function(result){
						 var data = result.data;
						 for(var i = 0 ; i < data.length; i++){
							 var html = '';
							 var item = data[i];
							 var size = item.DotSize;
							 var _data = item.data;
							 var commodityName = item.commodityName;
							 var qlastPrice = (parseFloat(_data.QLastPrice)).toFixed(size);
							 var qpreCloseingPrice = (parseFloat(_data.QPreClosingPrice)).toFixed(size);
							 var qLowPrice = (parseFloat(_data.QLowPrice)).toFixed(size);
							 var qHighPrice = (parseFloat(_data.QHighPrice)).toFixed(size);
							 var qOpenPrice = (parseFloat(_data.QOpenPrice)).toFixed(size);
							 var scal = (parseFloat(_data.QChangeRate)).toFixed(size);
							 var qChangeValue = (parseFloat(_data.QChangeValue)).toFixed(size);
							 var bs = "↑";
							 var jj = "+";
							 var color = " #ff5500";
							 if(scal < 0){
								 bs = "↓";
								 jj = "";
								 color = "#0bffa4";
							 }
							 $(".qlast"+i+"").text(jj  + qlastPrice + " " + bs);
							 $(".qchange"+i+"").text(jj + qChangeValue);
							 $(".scal"+i+"").text(jj + scal + "%");
							 $(".qlast"+i+"").css(color);
							 $(".qchange"+i+"").css(color);
							 $(".scal"+i+"").css(color);
							 var contractNo = $("#dqCommodNo").val();
							 if(contractNo == _data.CommodityNo){
								 $(".zs").text("昨收:"+qpreCloseingPrice);	
								 $(".fd").text("每日幅度:"+qLowPrice+' - '+qHighPrice);
								 $(".jk").text("今开:"+qOpenPrice);
								 $(".gxsj").text("更新时间:"+_data.TimeStamp);
							 }
						 }
					}
		   	});
		   });
	   },1000);
	   
	   var webSocketUrl = "ws://192.168.0.213:9002";
	   var socket = new WebSocket(webSocketUrl);
	   socket.onopen = function(evt){
		   
	   };
	   socket.onclose = function(evt){
		   
	   };
	   socket.onmessage = function(evt){
		   
	   };
	   socket.onerror = function(evt){
		   
	   } */
	   var href = window.location.href;
	 	// 路径配置
	    require.config({
	        paths:{//${ctx }/static/script
	            'echarts' :href + '/static/script/echarts',
	            'echarts/chart/pie' :href + '/static/script/echarts'
	        }
	    });
	 	
	    var rawData = [];
	    var loadCount = 0;
	    var loadKcount = 0 ;
	    var	 myChart = null;
	    //加载K线图数据模型
	    function loadKData(commodity,contract){
	    	$(function(){
	    		var rawDataLength = rawData.length - 1;
	            var url = "Quotation/doGetQk?commodity="+commodity+"&contract="+contract;
	            if(rawDataLength > 0){
	            	var beginTime = rawData[rawDataLength][0];
	            	url +="&beginTime="+beginTime;
	            }
	            $.ajax({
		           	 url : url,
		   			 type:"get",
		   			 dateType:"json",
		   			 success:function(data){
		   				 var resultData = data.data;
		   				 /* var addKData = []; */
		   					$.each(resultData,function (i,item){
		   						var j = rawDataLength + i;
		   						var openPrice = item.OpenPrice;
		   						var closePrice = item.LastPrice;
		   						var chaPrice = closePrice - openPrice;
		   						var sgData = [item.DateTime,openPrice,closePrice,chaPrice,"",item.LowPrice,item.HighPrice,"","","-"];
		   						rawData[j] = sgData;
		   					});
		   					//追加到容器中
		   					var option = setOption(rawData);
		   					if(myChart != null){
			   					myChart.setOption(option);
		   					}
		   				 }
	            });
	    	});
	    }
	    //设置数据参数（为画图做准备）
	    function setOption(rawData){
	    	var dates = rawData.map(function (item) {
	            return item[0];
	        });

	        var data = rawData.map(function (item) {
	            return [+item[1], +item[2], +item[5], +item[6]];
	        });
	    	var option = {
	                title: {
	                    text: '1分钟K线',
	                    textStyle: {
	                    	color : "#ffcc33"
	                    }
	                },
	                backgroundColor: '#333',
	                tooltip: {
	                    trigger: 'axis',
	                    axisPointer: {
	                        animation: false,
	                        lineStyle: {
	                            color: '#376df4',
	                            width: 2,
	                            opacity: 1
	                        }
	                    }
	                },
	                xAxis: {
	                    type: 'category',
	                    data: dates,
	                    axisLine: { lineStyle: { color: '#8392A5' } }
	                },
	                yAxis: {
	                    scale: true,
	                    axisLine: { lineStyle: { color: '#8392A5' } },
	                    splitLine: { show: false }
	                },
	                dataZoom: [{
	                    textStyle: {
	                        color: '#8392A5'
	                    },
	                    handleIcon: 'M10.7,11.9v-1.3H9.3v1.3c-4.9,0.3-8.8,4.4-8.8,9.4c0,5,3.9,9.1,8.8,9.4v1.3h1.3v-1.3c4.9-0.3,8.8-4.4,8.8-9.4C19.5,16.3,15.6,12.2,10.7,11.9z M13.3,24.4H6.7V23h6.6V24.4z M13.3,19.6H6.7v-1.4h6.6V19.6z',
	                    handleSize: '80%',
	                    dataBackground: {
	                        areaStyle: {
	                            color: '#8392A5'
	                        },
	                        lineStyle: {
	                            opacity: 0.8,
	                            color: '#8392A5'
	                        }
	                    },
	                    handleStyle: {
	                        color: '#fff',
	                        shadowBlur: 3,
	                        shadowColor: 'rgba(0, 0, 0, 0.6)',
	                        shadowOffsetX: 2,
	                        shadowOffsetY: 2
	                    }
	                }, {
	                    type: 'inside'
	                }],
	                animation: false,
	                series: [
	                    {
	                        type: 'candlestick',
	                        name:"1分钟K线",
	                        data: data,
	                        itemStyle: {
	                            normal: {
	                                color: '#FD1050',
	                                color0: '#0CF49B',
	                                borderColor: '#FD1050',
	                                borderColor0: '#0CF49B'
	                            }
	                        }
	                    }
	                ]
	            };
	    	return option;
	    }
	    function calculateMA(dayCount, data) {
	        var result = [];
	        for (var i = 0, len = data.length; i < len; i++) {
	            if (i < dayCount) {
	                result.push('-');
	                continue;
	            }
	            var sum = 0;
	            for (var j = 0; j < dayCount; j++) {
	                sum += data[i - j][1];
	            }
	            result.push(sum / dayCount);
	        }
	        return result;
	    } 
	    
	    //生成一个K线图容器
	    function loadK(){
	    	 // 使用
	        require(
	                [
	                    'echarts',
	                    'echarts/chart/pie' // 使用柱状图就加载bar模块，按需加载
	                ],
	                function (ec) {
	                	/* document.getElementById('main').innerHTML = ""; */
	                    // 基于准备好的dom，初始化echarts图表
	                    myChart = ec.init(document.getElementById('main'));
	                	var option = setOption(rawData);
	    			    // 为echarts对象加载数据
		    			myChart.setOption(option);
	                }
	        );
	    }
	    var interValData = null;
	    function bindLoadKDataInterValData(CommodityNo,contract){
	    	interValData = window.setInterval(function(){loadKData(CommodityNo,contract)}, 3000);
	    }
	    function clearLoadKDataInterVal(){
	    	if(interValData != null){
		    	window.clearInterval(interValData);
	    	}
	    }
	    function exctionLoadK(CommodityNo,contract){
		 	clearLoadKDataInterVal();
		 	bindLoadKDataInterValData(CommodityNo,contract);
	    }
	    function propHrefCP(commod){
	    	$(function(){
		    	if(commod == "HSI"){
			 		 $("#mainSqcp").prop("href",href + "hsi/index");
			 	 }else if(commod == "CL"){
			 		 $("#mainSqcp").prop("href",href + "crudeoil/index");
			 	 }else if(commod == "CN"){
			 		 $("#mainSqcp").prop("href",href + "ftse/index");
			 	 }else{
			 		 $("#mainSqcp").prop("href",href + "outDisk/index");
			 	 }
	    	});
	    }
	    var contractData = [];
	    function sendMessage(method,parameters){
	    	socket.send('{"Method":"'+method+'","Parameters":'+parameters+'}');
	    }
	    
	    var url = "ws://socket.dktai.com:9002";
	    var socket = new WebSocket(url);
	    socket.onopen = function(evt){
			sendMessage('Login','{"serName":"13677622344","PassWord":"a123456"}');
	     };
	    socket.onclose = function(evt){
	    };
	    socket.onmessage = function(evt){
	    	$(function(){
		    	var data = evt.data;
		    	var jsonData = JSON.parse(data);
		    	var method = jsonData.Method;
		    	//用户登录
		    	if(method == "OnRspLogin"){
		    		//发送消息获取品种
		    		sendMessage('QryCommodity','""')
		    	}else if(method == "OnRspQryCommodity"){
		    		var commoditys = jsonData.Parameters;
		    		console.log(jsonData);
		    		var size = commoditys.length;
		    		//生成实时行情html
		    		console.log(commoditys);
		    		for(var i = 0 ; i < size ; i++){
		    			var _data = commoditys[i];
		    			var html ="";
		    			var doSize = _data.DotSize;
		    			html += '<input type="hidden" class="doSize'+i+'" value = "'+doSize+'" /><div   class="left_xiangmu left_x'+i+'';
						 if(i  == 0){
						 	 html += ' on';
						 }
						 html +=  '"> <p><em  class = "right"></em>'
						 	  + '<span class = "qlast'+i+' left"></span>'
						 	  + '<span class = "qchange'+i+' right"></span>'
						 	  + '<span class = "scal'+i+' left"></span>';
						 $(".w_center_xiangqing .left_xiangqing .left_hidden").append(html);
						 contractData[i] = {'cname':_data.CommodityName,'Cmno':_data.CommodityNo,'doSize':doSize};
						 $(".left_x"+i+"").bind("click",function(){
							 rawData=[];
							 loadK();
							 var obj = $(this);
							 var da = obj.attr("data");
							 if(da != null){
							 	  var daArray = da.split("&");
							 	  var commod = daArray[0];
							 	  var contract = daArray[1];
							 	  loadKData(commod,contract);
							 	  exctionLoadK(commod,contract);
							 	  $("#dqCommodNo").val(commod);
							 	  propHrefCP(commod);
							 }
							 var topData = obj.attr("topData");
							 if(topData != null){
								 var topDataArray = topData.split("&");	
								 $(".zs").text("昨收:"+topDataArray[0]);	
								 $(".fd").text("每日幅度:"+topDataArray[1]+' - '+topDataArray[2]);
								 $(".jk").text("今开:"+topDataArray[3]);
								 $(".gxsj").text("更新时间:"+_data.TimeStamp);
							 }
							 var left_xiangmu   = $(".w_content .w_center_xiangqing .left_xiangmu");
							 left_xiangmu.each(function(){
								 left_xiangmu.removeClass('on');
							 });
							  obj.addClass('on');
						 });
		    		}
		    		for(var i = 0 ; i < size ; i++){
		    			var comm = commoditys[i];
		    			//sendMessage('QryContract','{"CommodityNo":"'+comm.CommodityNo+'"}');
		    			sendMessage('Subscribe','{"CommodityNo":"'+comm.CommodityNo+'","ContractNo":"'+comm.MainContract+'"}');
		    		}
		    	}else if(method == "OnRspQryContract"){
		    		 var catracts = jsonData.Parameters;
		    		 var size = catracts.length;
		    		 var mincatract = null;
		    		 var minContractNo = null;
		    		 if(size > 0){
		    			 mincatract = catracts[0];
		    			 minContractNo = parseInt(mincatract.ContractNo);
		    		 }
		    		 for(var i = 0 ; i < size ; i ++){
		    			 var contr = catracts[i];
		    			 var conNo = parseInt(contr.ContractNo);
		    			 if(minContractNo > conNo){
		    				 mincatract = contr;
		    				 minContractNo = conNo;
		    			 }
		    		 }
		    		 if(mincatract != null){
			    		 sendMessage('Subscribe', '{"CommodityNo":"'+mincatract.CommodityNo+'","ContractNo":"'+mincatract.ContractNo+'"}');
		    		 }
		    	}else if(method == "OnRspSubscribe"){
		    	}else if(method == "OnRtnQuote"){
		    		var size = contractData.length;
		    		var newCommNo = jsonData.Parameters.CommodityNo;
		    		var flag = false;
		    		for(var i = 0 ;i < size ; i ++){
		    			var con = contractData[i].Cmno;
		    			if(newCommNo == con){
		    				contractData[i].Parameters = jsonData;
				    		break;
		    			}
		    		}
		    		var contractLength = contractData.length;
		    		var loadInitCommod = null;
		    		if(contractLength > 0){
		    			loadInitCommod = contractData[0].Cmno;
		    		}
		    	   for(var i = 0 ; i < contractLength; i++){
						 var _data_ = contractData[i];
						 if(_data_.hasOwnProperty("Parameters")){
							 var _data = _data_.Parameters.Parameters;
							 var size = $(".doSize"+i+"").val();
							 var qlastPrice = (parseFloat(_data.QLastPrice)).toFixed(size);
							 var qpreCloseingPrice = (parseFloat(_data.QPreClosingPrice)).toFixed(size);
							 var qLowPrice = (parseFloat(_data.QLowPrice)).toFixed(size);
							 var qHighPrice = (parseFloat(_data.QHighPrice)).toFixed(size);
							 var qOpenPrice = (parseFloat(_data.QOpenPrice)).toFixed(size);
							 var scal = (parseFloat(_data.QChangeRate)).toFixed(size);
							 var qChangeValue = (parseFloat(_data.QChangeValue)).toFixed(size);
							 var qBidPrice1 = (parseFloat(_data.QBidPrice1)).toFixed(size);
							 var qAskPrice1 =  (parseFloat(_data.QAskPrice1)).toFixed(size);
							 var bs = "↑";
							 var jj = "+";
							 var color = " #ff5500";
							 if(_data.QChangeRate < 0){
								 bs = "↓";
								 jj = "";
								 color = "#0bffa4";
							 }
							 $(".left_x"+i+"").attr("data",""+_data.CommodityNo+"&"+_data.ContractNo+"");
							 $(".left_x"+i+" .right").text(_data_.cname);
							 $(".qlast"+i+"").text(qlastPrice + " " + bs);
							 $(".qchange"+i+"").text(jj + qChangeValue);
							 $(".scal"+i+"").text(jj + scal + "%");
							 $(".qlast"+i+"").css("color",color);
							 $(".qchange"+i+"").css("color",color);
							 $(".scal"+i+"").css("color",color);
							 var CNo = $("#dqCommodNo").val();
							 if(CNo == _data.CommodityNo){
								 $(".zs").text("最新买价:"+qBidPrice1);	
								 $(".fd").text("最新价:"+qlastPrice);
								 $(".jk").text("最新卖价:"+qAskPrice1);
								 $(".gxsj").text("更新时间:"+_data.TimeStamp);
							 }
							 if(loadInitCommod != null && loadInitCommod == _data.CommodityNo && loadCount == 0){
								  loadK();
								  rawData=[];
							 	  var cd = _data.CommodityNo;
							 	  var ct = _data.ContractNo;
							 	  loadKData(cd,ct);
							 	  exctionLoadK(cd,ct);
							 	  $("#dqCommodNo").val(cd);
							 	  propHrefCP(cd);
								 loadCount++;
							 }
					 }
		    	   }
		    	}
	    	});
	    };
	    socket.onerror = function(evt){
		   
	    }
	    
	    $(".left_hidden").mouseover(function(){
	        $("#whichscro").val($.trim($(this).parent().attr("id")))
	        if ((navigator.userAgent.match(/(iPhone|Android|iPad)/i))){
	            var scrollfathter1=document.getElementById($.trim($(this).parent().attr("id")));
	            scrollfathter1.addEventListener("touchstart", touchStart, false);
	            scrollfathter1.addEventListener("touchmove", touchMove, false);
	            scrollfathter1.addEventListener("touchend", touchEnd, false);
	        }
	    });
	    scroll_y("left_xiangqing","left_hidden","scroll_y","scroll_ymove","scroll_x","scroll_xmove","","wheely","")
</script>
</html>