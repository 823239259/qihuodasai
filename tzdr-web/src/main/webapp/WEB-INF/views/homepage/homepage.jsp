<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
	<title>投资达人 - 中国领先的互联网普惠金融平台 </title>
	<meta name="description" content="投资达人投身普惠金融互联网服务，以网络平台为A股、港股、美股、富时A50、恒指期货、国际原油等金融产品的操盘提供便利条件。" />
	<%
		String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
		String casServerLoginUrl=ConfUtil.getContext("SSO.casServer.loginUrl");
		String imgPreURL = ConfUtil.getContext("banner.url");
	%>
	<c:set var="ctx" value="<%=basePath%>"></c:set>
	<c:set var="v" value="20151127"></c:set>
	<c:set var="imgPreURL" value="<%=imgPreURL %>"></c:set>

	<!-- common css -->
	<link rel="shortcut icon" href="${ctx}/static/ico/tzdr.ico">
	<link rel="stylesheet" href="${ctx}/static/css/common.css?v=${v}">
	<!-- custom css -->
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

<!-- 广告,登录块 -->
<div class="login">
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
			
            <h3>欢迎登录投资达人</h3>
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
        <div class="ad_slider">
        	<c:forEach var="b" items="${banners }" varStatus="status">
            	<a href="javascript: void(0);" title="${status.count }" <c:if test="${status.index }==0">class="on"</c:if>>${status.count }</a>
        	</c:forEach>
        </div>
        <div class="bannerbox" id="bannersm" style="display:block;">
        	<c:forEach var="b" items="${banners }" varStatus="status">
            	<a href="${b.linkUrl }" target="_blank" style="display: none;"><img src="${imgPreURL }${b.imgPath }" title="banner" alt="banner"></a>
        	</c:forEach>
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
    <h2 class="h_chosetitle">炒股、炒期货为什么选投资达人？</h2>
    <ul class="hc_box fl">
        <li class="title"><label>自己炒股</label><span>在投资达人炒股</span></li>
        <li><label>1万本金</label><span>1万保证金+<i>融资4万操盘金</i></span></li>
        <li><label>买股票10手</label><span>买股票<i>50手</i></span></li>
        <li><label>一个涨停</label><span>一个涨停</span></li>
        <li><label>赚：1000元</label><span>赚：<i>5000元</i>（付少许管理费+利息）</span></li>
    </ul>
    <ul class="hc_box fr">
        <li class="title"><label>自己炒期货</label><span>在投资达人炒期货</span></li>
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
