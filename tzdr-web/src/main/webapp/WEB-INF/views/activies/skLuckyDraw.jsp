<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.umpay.api.log.SysOutLogger"%>
<%@page import="com.tzdr.common.utils.ConfUtil"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge"/>
	<meta name="viewport" content="width=1010">
	<title>维胜 - 抽奖</title>
	<meta name="description" content="维胜投身普惠金融互联网服务，以网络平台为A股、港股、美股、富时A50、恒指期货、国际原油等金融产品的操盘提供便利条件。" />
	<%
		String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
		String casServerLoginUrl=ConfUtil.getContext("SSO.casServer.loginUrl");
		String imgPreURL = ConfUtil.getContext("banner.url");
	%>
	<c:set var="ctx" value="<%=basePath%>"></c:set>
	<c:set var="v" value="20151127"></c:set>
	<c:set var="imgPreURL" value="<%=imgPreURL %>"></c:set>
	<!-- common css -->
	<link rel="shortcut icon" href="${ctx}/static/ico/icon.png">
	<!-- common js -->
	<script src="${ctx}/static/script/common/jquery-1.8.0.min.js?v=${v}"></script>
	<script src="${ctx}/static/script/activies/kszp.js?v=${v}"></script>
    <link href="${ctx}/static/css/gybf.css" rel="stylesheet" type="text/css">	
    <link href="${ctx}/static/css/skLuckyDraw.css" rel="stylesheet" type="text/css">	
    <script type='text/javascript' src="${ctx}/static/script/securityInfo/securityInfo.js?version=20150724"></script>
	<script type="text/javascript">
		var basepath = "<%=basePath%>" + "/";
		var casServerLoginUrl = "<%=casServerLoginUrl%>";
	</script>
	<script type="text/javascript">
    $(function (){
        var rotateTimeOut = function (){
            $('#rotate').rotate({
                angle:0,
                animateTo:2160,
                duration:8000,
                callback:function (){
                    alert('网络超时，请检查您的网络设置！');
                }
            });
        };
        var bRotate = false;

        var rotateFn = function (awards, angles, txt){
            bRotate = !bRotate;
            $('#rotate').stopRotate();
            $('#rotate').rotate({
                angle:0,
                animateTo:angles+1800,
                duration:8000,
                callback:function (){
                    $(".money").html(txt);
                    kszx();
                    bRotate = !bRotate;
                }
            })
        };
        $('.pointer').click(function (){
        	$.post(basepath+"/extendsion/sign/validationTip",function(data){
				if(data.success){
					if(data.data.islogin){
						if(data.data.luck == 1 && data.data.luckNum > 0){
							zhuanpan();
							return;
						}else if(data.data.luck == 0  && data.data.luckNum <= 0){
							kszxno();
							return;
						}
					}else{
						zhuce();
					}
				}else{
					showMsgDialog("提示","系统繁忙，请稍候重试......");
				}
			},"json");
        	
           /* console.log(item);*/
        });
    });
    function rnd(n, m){
        return Math.floor(Math.random()*(m-n+1)+n)
    }
    
    function zhuanpan(){
    	if(bRotate)return;
        var a= [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,2,2,2,2,2,2,2,2,2,2,3];
        var index = Math.floor((Math.random()*a.length));
        var item = rnd(a[index],a[index]);
        switch (item) {
            case 0:
                //var angle = [26, 88, 137, 185, 235, 287, 337];
                rotateFn(0,181,'2');
                break;
            case 1:
                //var angle = [88, 137, 185, 235, 287];
                rotateFn(1, 241, '5');
                break;
            case 2:
                //var angle = [137, 185, 235, 287];
                rotateFn(2, 120, '10');
                break;
            case 3:
                //var angle = [137, 185, 235, 287];
                rotateFn(3, 300, '50');
                break;
            case 4:
                //var angle = [185, 235, 287];
                rotateFn(4, 185, '免单4元');
                break;
        }
    }
    
    function kszx(){
    	$("#kszj").css("display","block");
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
    function kszxno(){
    	$("#zp").css("display","block");
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
    function zhuce(){
    	$("#zc").css("display","block");
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
	</script>
	<style type="text/css">
		.div_loading {width: 285px;height: 100%;text-align: left;z-index: 10000;margin: 0 auto;}
		.anniu {text-align: center;}
		.smain {text-align: center;}
		.anniu a {margin-left: 30px;background: #fc3;color: #333;text-decoration: none;height: 40px;line-height: 40px;display: inline-block; width: 100px;}
		.money {color:#fc3;}
		.navtitle .nava {width: 550px; text-align: center;}
	</style>
</head>
<body>
<div id="div_Mask"  style="display:none;"></div>
<div class="div_loading">
	<div class="tck01" id="kszj" style="display: none;">
		<div class="navtitle">
			<a class="nava">您中奖啦！</a><a class="close" onclick="javascript:closeDiv('kszj')"></a>
		</div>
		<div class="smain">
			<div>恭喜您获得<span class="money"></span>元现金红包！</div>
			<div>系统已经通过现金红包的方式存入您的账户！</div>
		</div>
		<div class="anniu">
	 		<a href="${ctx}tzdr-web/user/account" onclick="javascript:closeDiv('kszj')">查看账户</a>
	 	</div> 
	</div>
	<div class="tck01" id="zp" style="display: none;">
		<div class="navtitle">
			<a class="nava">不好意思啦！</a><a class="close" onclick="javascript:closeDiv('zp')"></a>
		</div>
		<div class="smain">
			<div>您暂时没有抽奖机会！赶紧去操盘吧</div>
		</div>
		<div class="anniu">
	 		<a href="${ctx}/ftse/index" onclick="javascript:closeDiv('zp')">立即操盘</a>
	 	</div> 
	</div>
	<div class="tck01" id="zc" style="display: none;">
		<div class="navtitle">
			<a class="nava">提示</a><a class="close" onclick="javascript:closeDiv('zc')"></a>
		</div>
		<div class="smain">
			<div>你还未登录，赶紧去登陆吧!</div>
		</div>
		<div class="anniu">
	 		<a href="${ctx}/user/account">立即登录</a>
	 		<a  onclick="javascript:closeDiv('zc')">取消</a>
	 	</div> 
	</div>
</div>
<div class="bannerlist">
	<div class="slide_box">
		<div class="slide_banner">
			<img src="${ctx}/static/images/anniversary/sk-title-banner1.png">
		</div>
	</div>
</div>
<div class="bannercenter">
	<div class="banner-center">
		<p class="ms-beijing"><img src="${ctx}/static/images/anniversary/sk-title-1.png"/>免损金牌-首亏有补贴，最高1000元</p>
		<div class="ks-center">
			<div class="hdjs">
				<p class="hdjs-title">活动期间，进行交易首次出现亏损，可获得一次补贴抽奖机会，白花花的现金，100%中奖哦~</p>
				<p class="hdjs-js" style="margin-top: 20px;">注：</p>
				<p class="hdjs-js">1，抽奖资格发放：用户结算方案后，首次出现亏损（手续费不计入）系统自动发放1次抽奖资格；</p>
				<p class="hdjs-js">2，奖品设置：1000元现金红包、500元现金红包、50元现金红包、10元现金红包 、5元现金红包、2元现金红包；</p>
				<p class="hdjs-js">3，奖品发放和使用：抽到的现金红包将直接发放到您的账户，您可直接使用；</p>
			</div>
			<div class="kszp">
				<div class="turntable-bg">
	    			<div class="pointer">
	    				<a href="javascript:(0);">
	    					<img src="${ctx}/static/images/anniversary/sk-zhixiang.png"/>
	    				</a>
	    			</div>
	    			<div class="rotate" ><img id="rotate" src="${ctx}/static/images/anniversary/sk-zhuanpan.png"/></div>
				</div>
			</div>
		</div>
		<p class="ms-beijing"><img src="${ctx}/static/images/anniversary/sk-title-2.png"/>免损金牌-达标直接补，免损最高2000元</p>
		<p class="hdjs-title" style="margin-top: 50px;">活动期间，每个交易日满足一定交易手数，累计亏损（手续费不计入）的用户可获得相应金额的亏损补贴；</p>
		<p class="hdjs-js" style="margin-top: 20px;">注：</p>
		<p class="hdjs-js">1，每个工作日统计并发放上个交易日补贴情况；</p>
		<p class="hdjs-js">2，补贴将以现金红包的方式直接发放到您的账户，您可直接使用；</p>
		<p class="hdjs-js">3，如实际亏损少于每个等级的补贴金额，补贴将以实际亏损金额发放；</p>
		<div class="ksbt">
			<ul class="ksbt-border">
				<li>单日交易满10手</li>
				<li>单日交易满20手</li>
				<li>单日交易满40手</li>
			</ul>
			<ul>
				<li><img src="${ctx}/static/images/anniversary/sk-title-3.png"/></li>
				<li><img src="${ctx}/static/images/anniversary/sk-title-3.png"/></li>
				<li><img src="${ctx}/static/images/anniversary/sk-title-3.png"/></li>
			</ul>
			<ul class="ksbt-border">
				<li>500元亏损补贴</li>
				<li>1000元亏损补贴</li>
				<li>2000元亏损补贴</li>
			</ul>
		</div>
	</div>
</div>
<p class="ks-footer">以上所有活动最终解释权归维胜网所有，客服咨询：400-852-8008</p>
</body>
</html>