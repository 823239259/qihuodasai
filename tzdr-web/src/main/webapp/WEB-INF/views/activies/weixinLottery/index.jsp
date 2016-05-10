<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.tzdr.web.utils.LotteryTimesUtil"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="zh-cmn-Hans">
<head>
	<meta charset="utf-8" />
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
	<meta http-equiv="Cache-Control" content="no-siteapp" />
	<meta name="renderer" content="webkit" />
	<meta name="robots" content="all" />
	<meta name="keywords" content="股票、期货" />
	<meta name="description" content="投资达人投身普惠金融互联网服务，以网络平台为A股、港股、美股、富时A50、恒指期货、国际原油等金融产品的操盘提供便利条件。" />
	<meta name="author" content="www.tzdr.com" />
	<meta name="viewport" content="width=device-width,initial-scale=1.0,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no">
	<meta name="HandheldFriendly" content="true">
	<meta name="MobileOptimized" content="320">
	<meta name="apple-mobile-web-app-title" content="html5" />
	<meta name="format-detection" content="telephone=no,email=no" />
	<meta name="apple-mobile-web-app-capable" content="yes" />
	<meta name="apple-mobile-web-app-status-bar-style" content="black" />
	<title>微信抽奖 - 投资达人 </title>
	<%
		String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
	%>
	<c:set var="ctx" value="<%=basePath%>"></c:set>
	<c:set var="v" value="20160113"></c:set>

	<!-- common css -->
	<link rel="shortcut icon" href="${ctx}/static/ico/tzdr.ico">
	<!-- custom css -->
	<link rel="stylesheet" href="${ctx}/static/css/weixin-lottery.css?v=${v}">

	<!-- common js -->
	<script src="${ctx}/static/script/common/jquery-1.8.0.min.js"></script>
	<script src="${ctx}/static/script/tzdr.js"></script>
	<script type="text/javascript">
		var basepath = "<%=basePath%>" + "/";
		$(document).ready(function() {
	        $('.mainbtn').click(function() {
	            $('#home').css('display', 'block');
	            $('#prize').css('display', 'none');
	            $('#rule').css('display', 'none');
	        });
	        $('.pzbtn').click(function() {
	            $('#home').css('display', 'none');
	            $('#prize').css('display', 'block');
	            $('#rule').css('display', 'none');
	        });
	        $('.rubtn').click(function() {
	            $('#home').css('display', 'none');
	            $('#prize').css('display', 'none');
	            $('#rule').css('display', 'block');
	        });
	    });
	</script>

</head>
<body>
<div class="floatlayer" style="display: none;">
</div>

<div id="home">
	<div class="pz">
	    <img src="${ctx}/static/images/weixinLottery/main_01.jpg">
	    <div class="main_box">
	        <img src="${ctx}/static/images/weixinLottery/main_02.jpg">
	        <%
	        	if(request.getSession().getAttribute("userName")==null || "".equals(request.getSession().getAttribute("userName"))) {
	        %>
	        <a href="${ctx}/weixin/lottery/login" class="main_lg">立即登录，查看我的开箱机会</a>
	        <%
	        	} else {
	        %>
	        <p class="main_id">欢迎您：<%=request.getSession().getAttribute("userName").toString() %></p>
	        <%
	        	}
	        %>
	    </div>
	    
	    <div class="main_box">
			<img src="${ctx}/static/images/weixinLottery/main_03.jpg">
	        <div class="wrapper">
	        <%
	        	if(request.getSession().getAttribute("userName")!=null && !"".equals(request.getSession().getAttribute("userName")) && LotteryTimesUtil.isLotteryTime()) {
	        %>
	        <a href="javascript: unpacking();"><img src="${ctx}/static/images/weixinLottery/gift.png"></a>
	        <%
	        	} else {
	        %>
	        <a href="javascript: void(0);"><img src="${ctx}/static/images/weixinLottery/gift.png"></a>
	        <%
	        	}
	        %>
	        </div>
	    </div>
	    
	    <div class="main_box">
	        <img src="${ctx}/static/images/weixinLottery/main_04.jpg">
	        <%
	        	if(!LotteryTimesUtil.isLotteryTime()) {
	        %>
	        <p class="main_time">下次开箱时间：<br><%=LotteryTimesUtil.getNextLotteryTimes() %></p>
	        <%
	        	}
	        %>
	    </div>
	    
	    <div class="main_box">
	        <img src="${ctx}/static/images/weixinLottery/main_05.jpg">
	        <ul class="main_pz" style="display: none;">
	        </ul>
	    </div>
	    
	    <div class="main_box">
	        <img src="${ctx}/static/images/weixinLottery/main_06.jpg">
	        <p class="main_tel">使用奖品，拨打热线：<a href="tel:400-020-0158">400-020-0158</a></p>
	    </div>
	</div>
	
	<footer>
	    <a href="javascript:void(0);" class="mainbtn"><img src="${ctx}/static/images/weixinLottery/navon_01.jpg"></a>
	    <a href="javascript:void(0);" class="pzbtn"><img src="${ctx}/static/images/weixinLottery/nav_02.gif"></a>
	    <a href="javascript:void(0);" class="rubtn"><img src="${ctx}/static/images/weixinLottery/nav_03.gif"></a>
	</footer>
</div>

<div id="prize" style="display:none;">    
    <div class="pz">
        <img src="${ctx}/static/images/weixinLottery/pz_01.jpg">
        <img src="${ctx}/static/images/weixinLottery/pz_02.jpg">
        <img src="${ctx}/static/images/weixinLottery/pz_03.jpg">
        <img src="${ctx}/static/images/weixinLottery/pz_04.jpg">
        <div class="main_box">
            <img src="${ctx}/static/images/weixinLottery/pz_05.jpg">
            <ul class="pz_list" style="display:none;">
                <li><label>186****9087</label><span>获得实物礼品 充电宝</span></li>
                <li><label>186****9087</label><span>获得实物礼品 充电宝</span></li>
                <li><label>186****9087</label><span>获得实物礼品 充电宝</span></li>
                <li><label>186****9087</label><span>获得实物礼品 充电宝</span></li>
                <li><label>186****9087</label><span>获得实物礼品 充电宝</span></li>
            </ul>
        </div>
    </div>
    <footer>
        <a href="javascript:void(0);" class="mainbtn"><img src="${ctx}/static/images/weixinLottery/nav_01.gif"></a>
        <a href="javascript:void(0);" class="pzbtn"><img src="${ctx}/static/images/weixinLottery/navon_02.jpg"></a>
        <a href="javascript:void(0);" class="rubtn"><img src="${ctx}/static/images/weixinLottery/nav_03.gif"></a>
    </footer>
</div>

<div id="rule" style="display:none;">    
    <div class="pz">
        <img src="${ctx}/static/images/weixinLottery/ru_01.jpg">
        <img src="${ctx}/static/images/weixinLottery/ru_02.jpg">
        <img src="${ctx}/static/images/weixinLottery/ru_03.jpg">
        <img src="${ctx}/static/images/weixinLottery/ru_04.jpg">
        <img src="${ctx}/static/images/weixinLottery/ru_05.jpg">
        <img src="${ctx}/static/images/weixinLottery/ru_06.jpg">
    </div>
    <footer>
        <a href="javascript:void(0);" class="mainbtn"><img src="${ctx}/static/images/weixinLottery/nav_01.gif"></a>
        <a href="javascript:void(0);" class="pzbtn"><img src="${ctx}/static/images/weixinLottery/nav_02.gif"></a>
        <a href="javascript:void(0);" class="rubtn"><img src="${ctx}/static/images/weixinLottery/navon_03.jpg"></a>
    </footer>
</div>

<!-- custom js -->
<script>
	<%
	if(request.getSession().getAttribute("userName")!=null && !"".equals(request.getSession().getAttribute("userName"))) {
	%>
	// 我的奖品
	function getMyKudo() {
		var $content = $(".main_pz");
		$.post(basepath + "weixin/lottery/myKudo", function(result) {
			if (result.success && result.code == 1) {
				$content.empty();
				var $data = result.obj;
				var item = "<li><label>{0}</label><span>{1}</span><em>{2}</em></li>";
				
				$.each($data, function(idx, elem){
					var $item = $.format(item, elem.kudoGetTime, elem.kudoName, elem.kudoStatusStr);
					
					$content.append($item).show("slow");
				});
			} else {
				//alert(result.message);
			}
		}, "json");
	}
	getMyKudo();
	<%
		}
	%>
	
	<%
	if(request.getSession().getAttribute("userName")!=null && !"".equals(request.getSession().getAttribute("userName")) && LotteryTimesUtil.isLotteryTime()) {
	%>
	var flag = false;
	
	function closetk() {
		$(".close").parent().parent().hide();
		flag = false;
		location.href = location.href;
	}
	
	// 开箱操作
	function unpacking() {
		if(flag) {
			return;
		}
		
		var $content = $(".floatlayer");
		$.post(basepath + "weixin/lottery/unpacking", {"ajax":"1"}, function(result) {
			flag = true;
			
			if (result.success && result.code > 0) {
				$content.empty();
				
				var message, code=parseInt(result.code);
				switch(code) {
				case 1:
					message = '恭喜您开箱获得，<br>' + result.obj.kudoName + '！';
					break;
				case 3:
					message = '您在本周日未持有操盘方案，<br>没有开箱机会！<a href="http://m.peigubao.com/home/capital/index.html">请立即去开方案</a>';
					break;
				case 4:
					message = '您在本周一没有新开操盘方案，<br>没有开箱机会！<a href="http://m.peigubao.com/home/capital/index.html">请立即去开方案</a>';
					break;
				case 5:
					message = '本次开启宝箱机会已使用，改日再来！';
					break;
				case 6:
					message = '奖品已送完，敬请期待下次活动！';
					break;
				default:
					message = "开箱时间未到，请等待下次抽奖！";
				}
				
				var item = '<div class="fl_tk">' +
					'<a href="javascript: closetk();" class="close"><img src="' + basepath + 'static/images/weixinLottery/close.jpg"></a>' +
			        '<p>' + message + '</p>' +
			        '<a href="javascript: closetk();" class="fl_btn">确定</a>' +
			    	'</div>';
				
				$content.append(item).show("slow");
			} else {
				alert(result.message);
			}
		}, "json");
	}
	<%
		}
	%>
</script>
</body>
</html>
