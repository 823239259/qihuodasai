<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="../common/common.jsp"%>
<%@include file="../common/import-artDialog-js.jspf"%>
<html xmlns="http://www.w3.org/1999/xhtml" lang="zh">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
	<meta name="viewport" content="width=1010">
	<!-- <meta name="viewport" content="user-scalable=no" /> -->
	<title>维胜-中国领先的国际期货及衍生品互联网交易平台</title>
	<meta name="keywords" content="维胜，国际期货，期货，投资达人，金勺子，高盛，都城，南华期货，配资，期货配资，期货开户，外盘，富时A50，国际原油，恒指期货，期货公司，期货平台，炒期货，模拟盘，赚钱，头寸，持仓，成都盈透科技有限公司"/>
	<meta name="description" content="维胜（www.vs.com）-致力于成为中国领先的国际期货及衍生品互联网交易平台，提供恒指期货、国际原油、富时A50等主流国际期货产品，开户操盘快捷方便，交易费用全网最低。"/>
	<%
		//String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
		String casServerLoginUrl=ConfUtil.getContext("SSO.casServer.loginUrl");
		String imgPreURL = ConfUtil.getContext("banner.url");
	%>
	<c:set var="ctx" value="<%=basePath%>"></c:set>
	<c:set var="v" value="20151127"></c:set>
	<c:set var="imgPreURL" value="<%=imgPreURL %>"></c:set>
	<%-- <link rel="stylesheet" href="${ctx}/static/css/common.css?v=${v}">
	<!-- custom css -->
	<link rel="stylesheet" href="${ctx}/static/css/home.css?v=${v}"> --%>
	<link rel="stylesheet" href="${ctx }/static/css/new_index.css?v=${v}">
	<link href="${ctx }/static/css/trade.css?v=${v}" rel="stylesheet" type="text/css" />
    <script src="${ctx }/static/script/homepage/gundongtiao.js?v=${v}"></script>
	<!-- common css -->
	<link rel="shortcut icon" href="${ctx}/static/ico/icon.png">
	<!-- common js -->
	<script src="${ctx}/static/script/common/jquery-1.8.0.min.js?v=${v}"></script>
	<script type="text/javascript">
		var basepath = "<%=basePath%>" + "/";
		var casServerLoginUrl = "<%=casServerLoginUrl%>";
	</script>
	<script src="${ctx }/static/script/esl.js?v=${v}"></script>
    <script src="${ctx }/static/script/slide-box.js?v=${v}"></script>
    <link href="${ctx}/static/css/gybf.css" rel="stylesheet" type="text/css">		
    <script type='text/javascript' src="${ctx}/static/script/securityInfo/securityInfo.js?version=20150724"></script>
	<style type="text/css">
	.ft_wx a:hover { background: url(../static/images/common-new/wxon.png) no-repeat; }
	.ft_wx a { display: block; width: 50px; height: 50px; background: url(../static/images/common-new/wx.png) no-repeat;}
	#shouye {color: #ffcc33; border-bottom:2px solid #ffcc33; padding-bottom: 26px;}
	.left_hidden{float: left; width: 195px; }
	#left_xiangqing{background: #333; float: left;height: 508px;  width: 195px; position: relative;overflow: hidden;}
    #left_xiangqing .w_content .left_hidden{float: left; width: 195px;}
    #left_xiangqing .scroll_y{background: #fc3;position: absolute; right: 0;width: 5px;}
    #left_xiangqing .scroll_ymove{background: #292929; height: 508px;position: absolute; right: 0;width: 5px;z-index: 2;}
	.div_loading {width: 285px;height: 100%;text-align: left;z-index: 10000;margin: 0 auto;}
		.anniu {text-align: center;}
		.smain {text-align: center;}
		.anniu a {margin-left: 30px;background: #fc3;color: #333;text-decoration: none;height: 40px;line-height: 40px;display: inline-block; width: 100px;}
		.money {color:#fc3;}
		.navtitle .nava {width: 550px; text-align: center;}
		.tck01 {width: 350px;}
		#shouye {color: #ffcc33;border-bottom: 2px solid #ffcc33;padding-bottom: 26px;}
	</style>
	<script type="text/javascript">
	function skbt(luckNum){
		$(".luckNum").html(luckNum);
    	$("#skbt").css("display","block");
    	$("#div_Mask").show();
		var windowWidth = document.documentElement.clientWidth;   
		var windowHeight = document.documentElement.clientHeight;   
		var popupHeight = $(".tck01").height();   
		var popupWidth = $(".tck01").width();    
		$(".tck01").css({     
		 "top": (windowHeight-popupHeight)/2+$(document).scrollTop(),   
		 "left": (windowWidth-popupWidth)/2   
		});  
    		
    }
	function sk_bt(subsidyMoney){
		$(".subsidyMoney").html(subsidyMoney);
    	$("#sk_bt").css("display","block");
    	$("#div_Mask").show();
		var windowWidth = document.documentElement.clientWidth;   
		var windowHeight = document.documentElement.clientHeight;   
		var popupHeight = $(".tck01").height();   
		var popupWidth = $(".tck01").width();    
		$(".tck01").css({     
		 "top": (windowHeight-popupHeight)/2+$(document).scrollTop(),   
		 "left": (windowWidth-popupWidth)/2   
		});  
    		
    }
    function skmsbt(luckNum,subsidyMoney){
    	$(".luckNum").html(luckNum);
    	$(".subsidyMoney").html(subsidyMoney);
    	$("#skmsbt").css("display","block");
    	$("#div_Mask").show();
		var windowWidth = document.documentElement.clientWidth;   
		var windowHeight = document.documentElement.clientHeight;   
		var popupHeight = $(".tck01").height();   
		var popupWidth = $(".tck01").width();    
		$(".tck01").css({     
		 "top": (windowHeight-popupHeight)/2+$(document).scrollTop(),   
		 "left": (windowWidth-popupWidth)/2   
		});  
    }
    $(function(){
    	$.post(basepath+"/extendsion/sign/validationTip",function(data){
			if(data.success){
				if(data.data.islogin){
					var luckNum = data.data.luckNum;
					var subsidyMoney = data.data.subsidyMoney;
					if(data.data.lucktip == 1 && data.data.subsidytip == 1){
						skmsbt(luckNum,subsidyMoney);
						return;
					}else if(data.data.lucktip == 1  && data.data.subsidytip == 0){
						skbt(luckNum);
						return;
					}else if(data.data.subsidytip == 1 && data.data.lucktip == 0){
						sk_bt(subsidyMoney);
						return;
					}
				}else{
					
				}
			}else{
				showMsgDialog("提示","系统繁忙，请稍候重试......");
			}
		},"json");
    })
    window.onload=function(){
    	$(function(){
    		$(".bannerList1").each(function(){
    		   var $this =	$(this);
    		   var href =  $this.attr("href");
    		   if(href.indexOf("sign/luck/view") > 0){
    			   $this.attr("target","_self");
    		   }
    		});
    	});
    }
	</script>
</head>
<body>
<!-- header -->
<%@include file="../common/header.jsp"%>
<!-- 广告切换 -->
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
	            		<%-- <a href="<%=ConfUtil.getContext("p2p.user.account") %>" style="display:none;">投资账户</a> --%>
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
		    <!--<div class="scroll_xmove">
		        	<div class="scroll_x" unorbind="unbind"></div>
		    	</div>-->
    			<input type="hidden" id="whichscro">
            </div>
            <div style="width: 5px; height: 510px; background: #292929; float: left; position: relative; right: 5px;"></div>
            <div class="right_xiangqing">
                <div id="main" style="height:500px; width: 800px;"></div>
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
                <h4><i>${showmap.hsi[0].valueData }</i>元/双边</h4>
                <ul>
                    <li>• 港股指数 金融市场更加成熟</li>
                    <li>• 交易灵活 技术分析更有效</li>
                    <li>• 一天12个小时可以交易 盈利时间长</li>
                    <li>• T+0交易 随时锁定利润</li>
                </ul>
                <div class="money">
                    <p>总共操盘: ${showmap.hsi[1].valueData }人</p>
                    <p>总共交易: ${showmap.hsi[2].valueData }手</p>
                </div>
                <p class="chaopan"><a href="${ctx}/hsi/index" target="_blank">申请操盘</a></p>
            </div>
            <div class="w-guopjiqihuo">
                <h3>【国际原油】</h3>
                <h4><i>${showmap.fco[0].valueData }</i>元/双边</h4>
                <ul>
                    <li>• 全球玩家用户量最大 涨跌迅猛</li>
                    <li>• 高透明度便于基本面分析</li>
                    <li>• 全球交易市场无人操控</li>
                    <li>• 来自纽约商业交易所 行业标准</li>
                </ul>
                <div class="money">
                    <p>总共操盘: ${showmap.fco[1].valueData }人</p>
                    <p>总共交易: ${showmap.fco[2].valueData }手</p>
                </div>
                <p class="chaopan"><a href="${ctx}/crudeoil/index" target="_blank">申请操盘</a></p>
            </div>
            <div class="w-guopjiqihuo" style="border-right: 10px solid #292929;">
                <h3>【富时A50】</h3>
                <h4><i>${showmap.ffa50[0].valueData }</i>元/双边</h4>
                <ul>
                    <li>• A股精准风向标 免庄家操控</li>
                    <li>• 门槛低 超短线 交易灵活</li>
                    <li>• 国际版“IF指数” 操盘人数多</li>
                    <li>• 来自新加坡交易所 透明公正</li>
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
<input type= "hidden" id = "dqCommodNo"/>
<!-- footer -->
<%@include file="../common/footer.jsp"%>
<!-- custom js -->
<script src="static/script/homepage/homepage.js?version=20151127"></script>
<%@ include file="/WEB-INF/views/common/count.jsp"%>
</body>
<script type="text/javascript" src = "static/script/homepage/homepage.trade.js"></script> 
</html>