<#include "/common.ftl">
<#include "/import-fileupload-js.ftl">
<#include "/import-artDialog-js.ftl">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>投资达人</title>
<link rel="stylesheet" type="text/css" href="${base}/static/css/common.css">
<link rel="stylesheet" href="${base}/static/css/login.css">

<link rel="stylesheet" type="text/css" href="${base}/static/css/tzdr.css">
<link rel="stylesheet" type="text/css" href="${base}/static/css/home.css">
<script type='text/javascript' src="http://hq.sinajs.cn/list=sz399006"></script>
<script type='text/javascript' src="http://hq.sinajs.cn/list=sz399001"></script>
<script type='text/javascript' src="http://hq.sinajs.cn/list=sh000001"></script>
<script type='text/javascript' src="http://hq.sinajs.cn/list=sz399005"></script>
<script type='text/javascript' src="${base}/static/script/tzdr.js"></script>
<script type='text/javascript' src="${base}/static/script/common/slider3.1.js"></script>
<script type='text/javascript' src="${base}/static/script/homepage/homepage.js"></script>
<style type="text/css">
.ddw_list{ position:absolute; top:-60px;}

</style>
</head>
<body>
<!-- 顶部 -->
<#--
<%@ include file="../common/homeheader.jsp"%>
-->
<#include "/homeheader.ftl">

<!-- 浮动层 -->
<div class="floatlayer">
    <div class="fl_server">
        <a href="javascript:void(0)" id="BizQQWPA" onclick="window.open('http://wpa.qq.com/msgrd?v=3&uin=4000200158&site=qq&menu=yes','QQ在线','height=405,width=500,top=200,left=200,toolbar=no,menubar=no,scrollbars=yes, resizable=no,location=no, status=no')";>
        <img src="${base}/static/images/common/fix_01.gif"></a>
        <a href="#" ><img src="${base}/static/images/common/fix_02.gif"></a>
    </div>

</div>
<!-- 广告,登录块 -->
<div class="login" style="background-image:url(${base}/static/images/home/bannerbg0.gif);">        
    <div class="loginbox" >
       <#--
    	<c:choose>
    		<c:when test="${user!=null }">
    	-->
    			<div class="lgctn" id="lgdiv" style="display:none">
		            <div class="lg_user">您好，<i>${mobile}</i><a href="${base}/logout">【安全退出】</a></div>
		            <ul class="lg_info">
		                <li>
		                    <label>账户余额：</label>
		                    <span>
		                    	
		                    </span>
		                </li>
		                <li>
		                    <label>融资金额：</label>
		                    <span>
		                   
		                    </span>
		                </li>
		            </ul>
		            <ul class="lg_status">
		                <li class="user2">
		                  
		                </li>
		                <li class="mail">
			                   
		                </li>
		            </ul>
		            <div class="lg_btn"><a href="${base}/user/account">进入我的账户</a></div>
		            <p class="lg_time">上次登录时间：<i>${lastLoginTime }</i></p>

        </div>
            <#--
    		</c:when>
    		<c:otherwise>
    		-->
		    		<div class="lgctn" id="loginctn">
		        	<form id="loginForm" name="loginForm" action="" method="post">
		            <h3>手机号登录</h3>
		            <div class="lg_ip">
		                <div class="lg_ipctn">
		                    <i class="user"></i>
		                    <input type="text" name="userName" id="userName" value="请输入您的手机号" />
		                </div>
		                <div class="lg_ipctn">
		                    <i class="password"></i>
		                    <input type="password" name="password" id="password" value="" />
		               		<i id='pwd-txt' name="pwd-txt" style="position: absolute;width:130px;left: 42px;_top: 0;color: #999;font-size: 14px;display: block" >请输入您的密码</i>
		                </div>
		                <#if isNeedCode?exists>
		                    <div class="lg_ipcode codeLi" style="display:block">
				                    <input type="text" id="code" name="code" value="输入验证码"/>
				                    <img src="validate.code"/>
				            </div>
		                <#else>
		                
		                    <div class="lg_ipcode" id="codeLi" name="codeLi" style="display:none">
				                    <input type="text" id="code" name="code" value="输入验证码"/>
				                    <img id="validateCode" name="validateCode" src="validate.code"/>
				            </div>
		                
		                </#if>
		                
		                <#--
		                <c:choose>
		                	<c:when test="${isNeedCode}">
		                		<div class="lg_ipcode codeLi" style="display:block">
				                    <input type="text" id="code" name="code" value="输入验证码"/>
				                    <img src="validate.code"/>
				                </div>
		                	</c:when>
		                	<c:otherwise>
		                		<div class="lg_ipcode" id="codeLi" name="codeLi" style="display:none">
				                    <input type="text" id="code" name="code" value="输入验证码"/>
				                    <img id="validateCode" name="validateCode" src="validate.code"/>
				                </div>
		                	</c:otherwise>
		                </c:choose>
		                -->
		                <div class="lg_btn"><button id="login" name="login" type="button">立即登录</button></div>
		            </div>
		            <div class="lg_link">
		                <a href="${base}/forgetpw" class="on">忘记密码?</a>
		                <i></i>
		                <a href="${base}/signin">免费注册</a>
		            </div>
		            </form>
		        </div>
		<#--
    		</c:otherwise>
    	</c:choose>
        </#if>
        -->
        
        
        <!-- 广告切换 -->
        <div class="ad_slider">
            <#list banners as banner>
               <a href="javascript:void(0)" alt="${banner.name!}"></a>
            </#list>
            <#--
        	<c:forEach  items="${banners}" var="banner" varStatus="status">
	            <a href="javascript:void(0)" alt="${banner.name}"></a>
	            
	       </c:forEach>
	       -->
        </div>
        <div id="outerdiv">
            <div class="bannerbox">
		        <#list banners as banner>
		           <div class="banner"  style="background-image: url(${base}/getRemoteContent?contentUrl=${banner.imgPath!});"></div>
		        </#list>
            
            <#--
            	<c:forEach  items="${banners}" var="banner" varStatus="status">
            		<div class="banner"  style="background-image: url(${base}/getRemoteContent?contentUrl=${banner.imgPath!});"></div>
            	</c:forEach>
                -->
            </div>  
        </div>

    </div>
</div>
<!-- 安全保证 -->
<div class="h_safe">
    <h2><img src="${base}/static/images/home/title.gif"></h2>
    <ul class="h_safelist">
        <li>
            <i class="h_safe1"></i>
            <h3>资金安全</h3>
            <p>资金托管招行<br>封闭管理专款专用</p>
        </li>
        <li class="h_sf_space">
            <i class="h_safe2"></i>
            <h3>交易安全</h3>
            <p>券商监管与恒生<br>合作保障您的交易安全</p>
        </li>
        <li class="h_sf_space">
            <i class="h_safe3"></i>
            <h3>专业风控</h3>
            <p>投资达人多级风控<br>监管体系降低投资风险</p>
        </li>
        <li class="last">
            <i class="h_safe4"></i>
            <h3>新手课堂</h3>
            <p>全新模式<br>不一样的投资体验</p>
        </li>
    </ul>
</div>
<!-- 实盘申请 -->
<div class="home_trade">
    <div class="home_tradectn">
        <div class="home_td">
            <div class="home_td_title">
                <a href="${base}/day?enter=0" target="_self"></a>
            </div>
            <div class="td_money"><img src="${base}/static/images/home/td_font.png"></div>
            <div class="td_btn">
                <div class="td_btn_icon"><img src="${base}/static/images/home/td_icon.gif"></div>
                <div class="td_pm">
                    <ul>
                        <li>融资金额<br>10万</li>
                        <li>融资倍数<br>5倍</li>
                        <li>融资时间<br>8天</li>
                    </ul>
                    <a href="${base}/oday?lever=5&capitalMargin=20000&borrowPeriod=8&tradeStart=1">确定方案</a>
                </div>
                <div class="td_pmone td_pmye">
                    <h3>投资本金</h3>
                    <p>1-5万</p>
                    <a href="${base}/day?enter=1" target="_self">我要融资 ></a>
                </div>
                <div class="td_pmone td_pmbl">
                    <h3>投资本金</h3>
                    <p>5-30万</p>
                    <a href="${base}/day?enter=2" target="_self">我要融资 ></a>
                </div>
                <div class="td_pmone td_pmrd">
                    <h3>投资本金</h3>
                    <p>30万以上</p>
                    <a href="${base}/day?enter=3" target="_self">我要融资 ></a>
                </div>
            </div>
        </div>
    </div>

</div>
<div class="home_ctn">
    <!-- 用户动态 -->
    <div class="h_usernum" style="display:none;" >
        <div class="h_untitle">
            <h3>用户动态</h3>
            <div class="h_unnum">
                <span>当前在线人数</span>
                <i class="h_unnum_mr">${activeUserCount }人</i>
                <span>已有</span>
                <i>${usercountstr }位</i>
                <span>投资人累计配资</span>
                <i>${moneystr }元</i>
            </div>
        </div>
       <div class="h_unlist" id="FontScroll" style="position: relative;" >   
        </div>
    </div>
    <!--  -->
    <div class="h_box">
        <!-- 最新动态 -->
        <div class="h_news">
            <div class="h_nwestitle">
                <i>最新动态</i>
                <a href="${base}/news/newsdata" target="_blank">更多动态</a>
            </div>
            <ul class="h_newslist">
            <#list newsdata as news>
                <#if news_index == 0 >
                   <li class="on"><a href="${base}/news/shownews?id=${news.id!}" style="text-decoration:none;" target="_blank" >${news.name!}</a><span>${news.addtime!}</span></li>
                <#else>
                   <li><a href="${base}/news/shownews?id=${news.id!}"  style="text-decoration:none;"  target="_blank" >${news.name!}</a><span>${news.addtime!}</span></li>
                </#if>
            </#list>
            
            <#--
            <c:forEach  items="${newsdata}" var="news" varStatus="status">
            	<c:choose>
            		<c:when test="${ status.index==0}">
            		 <li class="on"><a href="${base}/news/shownews?id=${news.id}" style="text-decoration:none;" target="_blank" >${news.name }</a><span>${news.addtime }</span></li>
            		</c:when>
            		<c:otherwise>
            			 <li><a href="${base}/news/shownews?id=${news.id}"  style="text-decoration:none;"  target="_blank" >${news.name }</a><span>${news.addtime }</span></li>
            		</c:otherwise>
            	</c:choose>
            </c:forEach>
            -->
            </ul>   
        </div>
        <!-- 上证指数 -->
        <div class="h_uplistctn">
            <div class="h_uptitle">
                <i>上证指数</i>
                
            </div>
            <ul class="h_upmain">
                <li class="h_upm_num">
                    <p id="szzs" ></p>
                   
                </li>
                 <li class="h_upm_list">
                    <p><span id="szprice"></span><i id="increaseszzs"></i></p> 
                </li>
                
                <!--  
                <li class="h_upm_list">
                    <p><span>券商</span><i>+3.15%</i></p>
                    <p><span>券商</span><i>+3.15%</i></p>
                    <p><span>券商</span><i>+3.15%</i></p>
                    <p><span>券商</span><i>+3.15%</i></p>
                </li>
                -->
            </ul>
            <table width="318" border="0" cellspacing="0" cellpadding="0" class="h_up_talbe">
              <tr>
                <td width="65" class="color">上证指数</td>
                <td width="240"><i id="szzs1"></i><i id="szprice1"></i><i id="increaseszzs1"></i></td>
              </tr>
              <tr>
                <td width="65" class="color">创业板</td>
                <td width="240"><i id="cybdata"></i><i id="cybprice"></i><i id="increasecyb"></i></td>
              </tr>
              <tr>
                <td width="65" class="color">深圳成指</td>
                <td width="240"><i id="szczdata"></i><i id="szczprice"></i><i id="increaseszcz"></i></td>
              </tr>
              <tr>
                <td width="65" class="color">中小板</td>
                <td width="240"><i id="zxbdata"></i><i id="zxbprice"></i><i id="increasezxb"></i></td>
              </tr>
            </table>
        </div>
    </div>
    <div class="h_box">
        <!-- 合作伙伴 -->
        <div class="h_cplist">
            <h3>合作伙伴</h3>
               
               <#list partners as partner>
                   <a href="${partner.linkUrl!}" alt="${partner.name!}" target="_blank">
           	         <img src="${base}/getRemoteContent?contentUrl=${partner.imgPath!}" alt="${partner.name!}">
               		</a>
               </#list>
               
               <#--
               <c:forEach  items="${partners}" var="partner" varStatus="status">
               	    <a href="${partner.linkUrl}" alt="${partner.name}" target="_blank">
           	         <img src="${base}/getRemoteContent?contentUrl=${partner.imgPath}" alt="${banner.name}">
               		</a>
               </c:forEach>
               -->
        </div>
        <div class="h_cpline">
        </div>
        <div class="h_cpbank">
            <h3>资金托管银行</h3>
           	  <img src="${base}/static/images/home/bank.jpg">
        </div>
    </div>
</div>
<#--
<%@ include file="../common/homefooter.jsp"%>
-->
<#include "/homefooter.ftl">
</body>
<script type="text/javascript">
var Slider = new slider();
Slider.initialize({
    speed: 200,
    ourterContainerId:'outerdiv',
    loop: true,
    fullSpeed: 200,
    elementWidth: 1400,
    jumpSpeed:50,
    startIdx:0
});
$('.ad_slider a').each(function() {
	
    $(this).click(function() {
        $('.ad_slider a').removeClass('on');
        $(this).removeClass('on');
        $(this).addClass('on');
        Slider.jumpTo($(this).index());
        var img=$(this).attr("data");
        
        //$('.login').css('background-image','url('+basepath+'getRemoteContent?contentUrl='+img+')');
        return false;
    });
});

var loopSlider = self.setInterval(function(){
	Slider.moveForward(1, '', function(){
    var index = Slider.startIdx + 1;
    var imgs=$('.ad_slider').find("a");
	var length=imgs.length;
	if(Slider.startIdx>length){
		Slider.startIdx=1;
	}
	var imgobj=imgs.eq(Slider.startIdx);
	var imgpath=imgobj.attr("data");
	

    //$('.login').css('background-image','url('+imgpath+')');

    //$('.login').css('background-image','url('+basepath+'getRemoteContent?contentUrl='+imgpath+')');

    $('.ad_slider a').removeClass('on').eq(index-1).addClass('on');
    /*$('.main_picfont > a').each(function(){
        if($(this).html() == index )$(this).addClass('point');
        else $(this).removeClass('point');
    });*/
})},3000);

$('#outerdiv').hover(function(){
    clearInterval(loopSlider);
},function(){
    loopSlider = self.setInterval(function(){Slider.moveForward(1, '', function(){
        var index = Slider.startIdx + 1;
        var imgs=$('.ad_slider').find("a");
    	var length=imgs.length;
    	if(Slider.startIdx>length){
    		Slider.startIdx=1;
    	}
    	var imgobj=imgs.eq(Slider.startIdx);
    	var imgpath=imgobj.attr("data");

        //$('.login').css('background-image','url('+imgpath+')');

        //$('.login').css('background-image','url('+basepath+'getRemoteContent?contentUrl='+imgpath+')');

        $('.ad_slider a').removeClass('on').eq(index-1).addClass('on');
    })},3000);
});

$(document).ready(function() {
    $('.h_newslist li').hover(function() {
        $('.h_newslist li').removeClass('on');
        $(this).addClass('on');
    });
	
   
});
</script>
<script type="text/javascript">
	function SZ(n){
		var hq_str=eval("hq_str_"+n); 
		var elements=hq_str.split(",");
		var data1=(elements[3]*1).toFixed(2); 
		var data2=(elements[3]-elements[2]).toFixed(2);
		var data3=(elements[3]-elements[2])*100/(elements[2]*1);
		return processDataArray(data1,data2,data3);
	}
	
	function processDataArray(data1,data2,data3){ 
		 data3=isNaN(data3*1)?"--":(data3*1).toFixed(2);
		 return[data1,data2,data3];
    }
    function displayszData(result){
    	if(parseFloat(result[2])>0){
    		$("#szzs").addClass("fontred").html(result[0]+"↑");
    		$("#szprice").addClass("fontred").html(result[1]);
        	$("#increaseszzs").addClass("fontred").html(result[2]+"%");
    	}else{
    		$("#szzs").addClass("fontgreen").html(result[0]+"↓");
    		$("#szprice").addClass("fontgreen").html(result[1]);
        	$("#increaseszzs").addClass("fontgreen").html(result[2]+"%");
    	}
    	
    	$("#szzs1").html(result[0]);
    	$("#szprice1").html(result[1]);
    	$("#increaseszzs1").html(result[2]+"%");
	} 
    function displaycybData(result){
    	$("#cybdata").html(result[0]);
    	$("#cybprice").html(parseFloat(result[1])>0?"+"+result[1]:result[1]);
    	$("#increasecyb").html(parseFloat(result[2])>0?"+"+result[2]+"%":result[2]+"%");
	} 
    function displayszczData(result){
    	$("#szczdata").html(result[0]);
    	$("#szczprice").html(parseFloat(result[1])>0?"+"+result[1]:result[1]);
    	$("#increaseszcz").html(parseFloat(result[2])>0?"+"+result[2]+"%":result[2]+"%");
	} 
    function displayzxbData(result){
    	$("#zxbdata").html(result[0]);
    	$("#zxbprice").html(parseFloat(result[1])>0?"+"+result[1]:result[1]);
    	$("#increasezxb").html(parseFloat(result[2])>0?"+"+result[2]+"%":result[2]+"%");
	} 

		//创业板块
		displaycybData(SZ("sz399006"));
		//上证指数
		displayszData(SZ("sh000001"));
		//深圳成指 
		displayszczData(SZ("sz399001"));
		//中小板
		displayzxbData(SZ("sz399005"));
		
</script>
</html>




