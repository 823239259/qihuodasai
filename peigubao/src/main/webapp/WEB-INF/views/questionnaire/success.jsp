<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp"%>
<%@include file="../common/import-fileupload-js.jspf"%>
<%@include file="../common/import-artDialog-js.jspf"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="../common/commonkeyword.jsp"%>
<link rel="stylesheet" href="${ctx}/static/activies/css/sp.css"/>
<link rel="stylesheet" href="${ctx}/static/script/valForm/css/style.css">

<link rel="stylesheet" href="${ctx}/static/css/tzdr.css">
 <script src="${ctx}/static/script/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
 <script src="${ctx}/static/script/questionnaire/questionnaire.js" type="text/javascript"></script>
 <script src="${ctx}/static/script/tzdr.js" type="text/javascript"></script>
<script src="${ctx}/static/script/common/tzdr.user.js?version=20150720" type="text/javascript"></script>		
<script src="${ctx}/static/script/jquery.validate.min.js" type="text/javascript"></script>		
<script type="text/javascript">
$(document).ready(function() {
	var loginName=$("#loginName").val();
	var password=$("#password").val();	
	$.post(basepath+"login_operation",{loginName:loginName,password:password,ajax:1},function(data){
		if(data.success){
			$("#loginhead").show();
			$("#lg_username").html('<i><a href="'+basepath+'user/account">您好，${mobile }</a></i>');
			
			$("#tologin").hide();
		}else{
			
			
		}
	},"json");
});

</script>
	
</head>
<body>
<%@ include file="../common/homeheader.jsp"%>
<div class="question">
<form action="">
	<input type="hidden" name="loginName" id="loginName" value="${usename}"/>
	<input type="hidden" name="password" id="password" value="${pwd}">
    <div class="qbanner1"></div>
    <div class="qbanner2"></div>
  
    <div class="qstep">
        <img src="${ctx}/static/activies/images/qtitle_03.PNG">
    </div>
    <div class="qs_bg">
        <div class="tp_main">
            <h3>恭喜您！达人红包8800活动加推报名成功！</h3>
            <ul class="tp_user">
                <li>
                    <label>达人账号：</label>
                    <span>${mobile }</span>
                </li>
                <li>
                    <label>初始密码：</label>
                    <span>${pwd }</span>
                </li>

            </ul>
            <div class="tp_font tp_fontspace">我们将在<i>3月30日</i>公布加推名单，届时客服将与您联系确认。<br>如您的加推报名审核通过，即可领取8800红包，并参与炒股比赛赢取iPhone 6 plus大奖！</div>
            <div class="tp_font">我们已将账号密码同步发送至您的手机${mobile }。<br>请妥善保存您的账号密码，如遗失可致电客服400-633-9257寻求帮助。<br>现在您可以立即<a href="${ctx }/day?enter=0">操盘炒股</a>，开始您的投资达人财富之路。</div>
        </div>


    </div>
    
    
    </form>
</div>
<!-- 公司简介 -->
<%@ include file="../common/personfooter.jsp"%>
<%@ include file="../common/dsp.jsp"%>
</body>
<script>
!function(w,d,e){
var _orderno='${usename}';  //替换此处!;
var b=location.href,c=d.referrer,f,s,g=d.cookie,h=g.match(/(^|;)\s*ipycookie=([^;]*)/),i=g.match(/(^|;)\s*ipysession=([^;]*)/);if (w.parent!=w){f=b;b=c;c=f;};u='//stats.ipinyou.com/cvt?a='+e('BJ.Hq.girWCGH8nV_wz8wlUdUwu_')+'&c='+e(h?h[2]:'')+'&s='+e(i?i[2].match(/jump\%3D(\d+)/)[1]:'')+'&u='+e(b)+'&r='+e(c)+'&rd='+(new Date()).getTime()+'&OrderNo='+e(_orderno)+'&e=';
function _(){if(!d.body){setTimeout(_(),100);}else{s= d.createElement('script');s.src = u;d.body.insertBefore(s,d.body.firstChild);}}_();
}(window,document,encodeURIComponent);


</script>
<script>
!function(w,d,e){
var _orderno='${usename}';  //替换此处!;
var b=location.href,c=d.referrer,f,s,g=d.cookie,h=g.match(/(^|;)\s*ipycookie=([^;]*)/),i=g.match(/(^|;)\s*ipysession=([^;]*)/);if (w.parent!=w){f=b;b=c;c=f;};u='//stats.ipinyou.com/cvt?a='+e('BJ.Rq.NIW54BcjwnKqGxCOmbF310')+'&c='+e(h?h[2]:'')+'&s='+e(i?i[2].match(/jump\%3D(\d+)/)[1]:'')+'&u='+e(b)+'&r='+e(c)+'&rd='+(new Date()).getTime()+'&OrderNo='+e(_orderno)+'&e=';
function _(){if(!d.body){setTimeout(_(),100);}else{s= d.createElement('script');s.src = u;d.body.insertBefore(s,d.body.firstChild);}}_();
}(window,document,encodeURIComponent);
</script>
</html>
