<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="../common/import-artDialog-js.jspf"%>
 <%@ include file="../common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="zh">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<%@ include file="../common/commonkeyword.jsp"%> 
	<link rel="stylesheet" href="${ctx}/static/css/regist.css?version=20150507">
	<script language="javascript" src="${ctx}/static/script/signin/signInSucess.js?version=20150618"></script> 
</head>
<body>
<% 
 String username = "";
 if(userSessionBean == null){
	 userSessionBean = (UserSessionBean) request.getSession().getAttribute(Constants.TZDR_USER_SESSION);
 }
 if(userSessionBean != null){
	 username=userSessionBean.getMobile();
 }
 %>
<%@ include file="../common/header.jsp"%>
<input type="hidden" id="type" name="type" value="${type}"/>
<input type="hidden" id="backUrl" value="${backUrl}"/>
<input type="hidden" id="source" value="${source}"/>
<c:if test="${!empty volumeNum && !empty volumePrice}">
<div class="floatlayer" id="rolldialog" name="rolldialog">
	<div class="fl_mask"></div>
	<div class="fl_box">
		<div class="fl_navtitle"><a href="javascript:void(0)" class="close" id="rollclose" name="rollclose"></a></div>
		<div class="fl_uc_main">
			<p class="fl_uc_tptitle">恭喜您，注册成功!</p>
			<p>您获得<i id="price" name="price">${volumePrice}</i>元抵扣券<i id="num" name="num">${volumeNum}</i>张！</p>
		</div>
		<div class="fl_uc_btn">
			<a href="javascript:void(0);" id="rollaffirm" id="rollaffirm" class="fl_uc_surebtn">确定</a>
			<a href="${ctx}/uservolume/volumelist" class="fl_rgtk">去查看您的抵扣券</a>
		</div>
	</div>
</div>
</c:if>
<div class="regist">
    <div class="rg_step">
        <em class="out">用户信息</em><img src="${ctx}/static/images/rg/regist_08.gif"><img src="${ctx}/static/images/rg/regist_09.gif"  class="space">
        <em class="on">注册成功</em>
    </div>
    <div class="rgs_ctn">
        <div class="rgs_title"> <%-- <%=username%>, --%>恭喜您注册成功！</div>
        <p>恭喜您已完成注册，系统将在<i id="time" name="time">3</i>秒后跳转到<a href="${ctx}">首页</a>页面</br>你也可以点击<a href="${ctx}/user/account">个人中心</a>跳转。</p>
        <p><a href="${ctx}" class="rgs_backhome">返回首页</a></p>
    </div>
</div>
<%@ include file="../common/footer.jsp"%>
<script>
!function(w,d,e){
var _orderno='<%=username%>';  //替换此处!;
var b=location.href,c=d.referrer,f,s,g=d.cookie,h=g.match(/(^|;)\s*ipycookie=([^;]*)/),i=g.match(/(^|;)\s*ipysession=([^;]*)/);if (w.parent!=w){f=b;b=c;c=f;};u='//stats.ipinyou.com/cvt?a='+e('BJ.Hq.girWCGH8nV_wz8wlUdUwu_')+'&c='+e(h?h[2]:'')+'&s='+e(i?i[2].match(/jump\%3D(\d+)/)[1]:'')+'&u='+e(b)+'&r='+e(c)+'&rd='+(new Date()).getTime()+'&OrderNo='+e(_orderno)+'&e=';
function _(){if(!d.body){setTimeout(_(),100);}else{s= d.createElement('script');s.src = u;d.body.insertBefore(s,d.body.firstChild);}}_();
}(window,document,encodeURIComponent);
</script>
<script>
!function(w,d,e){
var _orderno='<%=username%>';  //替换此处!;
var b=location.href,c=d.referrer,f,s,g=d.cookie,h=g.match(/(^|;)\s*ipycookie=([^;]*)/),i=g.match(/(^|;)\s*ipysession=([^;]*)/);if (w.parent!=w){f=b;b=c;c=f;};u='//stats.ipinyou.com/cvt?a='+e('BJ.Rq.NIW54BcjwnKqGxCOmbF310')+'&c='+e(h?h[2]:'')+'&s='+e(i?i[2].match(/jump\%3D(\d+)/)[1]:'')+'&u='+e(b)+'&r='+e(c)+'&rd='+(new Date()).getTime()+'&OrderNo='+e(_orderno)+'&e=';
function _(){if(!d.body){setTimeout(_(),100);}else{s= d.createElement('script');s.src = u;d.body.insertBefore(s,d.body.firstChild);}}_();
}(window,document,encodeURIComponent);
</script>
<script>
!function(w,d,e){
var _orderno='${tzdrUser.id}';
var b=location.href,c=d.referrer,f,s,g=d.cookie,h=g.match(/(^|;)\s*ipycookie=([^;]*)/),i=g.match(/(^|;)\s*ipysession=([^;]*)/);if (w.parent!=w){f=b;b=c;c=f;};u='//stats.ipinyou.com/cvt?a='+e('BJ.ga.SUo2xRgMiNvhrAY2-R67L_')+'&c='+e(h?h[2]:'')+'&s='+e(i?i[2].match(/jump\%3D(\d+)/)[1]:'')+'&u='+e(b)+'&r='+e(c)+'&rd='+(new Date()).getTime()+'&OrderNo='+e(_orderno)+'&e=';
function _(){if(!d.body){setTimeout(_(),100);}else{s= d.createElement('script');s.src = u;d.body.insertBefore(s,d.body.firstChild);}}_();
}(window,document,encodeURIComponent);
</script>
<script>
!function(w,d,e){
var _orderno='${tzdrUser.id}';
var b=location.href,c=d.referrer,f,s,g=d.cookie,h=g.match(/(^|;)\s*ipycookie=([^;]*)/),i=g.match(/(^|;)\s*ipysession=([^;]*)/);if (w.parent!=w){f=b;b=c;c=f;};u='//stats.ipinyou.com/cvt?a='+e('BJ._F.3ag4T-uuz-3qugl7_8Ab6_')+'&c='+e(h?h[2]:'')+'&s='+e(i?i[2].match(/jump\%3D(\d+)/)[1]:'')+'&u='+e(b)+'&r='+e(c)+'&rd='+(new Date()).getTime()+'&OrderNo='+e(_orderno)+'&e=';
function _(){if(!d.body){setTimeout(_(),100);}else{s= d.createElement('script');s.src = u;d.body.insertBefore(s,d.body.firstChild);}}_();
}(window,document,encodeURIComponent);
</script>
<%@ include file="../common/dsp.jsp"%>
</body>
</html>