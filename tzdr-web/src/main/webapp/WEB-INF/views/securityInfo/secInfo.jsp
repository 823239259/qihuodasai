<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ include file="../common/common.jsp"%>
   <%@include file="../common/import-fileupload-js.jspf"%>
   <%@include file="../common/import-artDialog-js.jspf"%>
<html xmlns="http://www.w3.org/1999/xhtml" lang="zh">
<head>
   <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
   <title>安全信息 - 维胜</title>
	 <link rel="stylesheet" href="${ctx}/static/css/uc.css?version=20150721"  type="text/css">
	 <link href="${ctx}/static/css/public.css" rel="stylesheet" type="text/css">	
	 <link rel="stylesheet" href="${ctx}/static/css/tzdr.css">
	 <link rel="stylesheet" href="${ctx}/static/css/base.css"  type="text/css">
    <link href="${ctx}/static/css/gybf.css" rel="stylesheet" type="text/css">	
    <script type='text/javascript' src="${ctx}/static/script/securityInfo/securityInfo.js?version=20150724"></script>
     <script src="${ctx}/static/script/tzdr.js" type="text/javascript"></script>
<script src="${ctx}/static/script/common/tzdr.user.js?version=20150724" type="text/javascript"></script>		
    <!--[if lte IE 9]><script src="js/html5.js"></script><![endif]-->

</head>


<script>

/**
蒙版信息控件
用法：
1.引用 mask.css
2.引用 mask.js
3.调用方法
var obj=new MaskControl();
//显示蒙版提示信息
obj.show("显示的提示信息");
//隐藏蒙版提示信息
obj.hide();
//显示提示信息，并隔timeOut(1000代表1秒)自动关闭
obj.autoDelayHide=function(html,timeOut)
*/
function MaskControl(){
this.show=function(html){
var loader=$("#div_maskContainer");
if(loader.length==0){
loader=$("<div id='div_maskContainer'><div id='div_Mask' ></div><div id='div_loading' ></div></div>");
$("body").append(loader);
}
self.loader=loader;
var w=$(window).width();
var h=$(window).height();
var divMask=$("#div_Mask");
divMask.css("top",0).css("left",0).css("width",w).css("height",h);
var tipDiv=$("#div_loading");
if(html==undefined)
html="";
tipDiv.html(html);
loader.show();
var x=(w-tipDiv.width())/2;
var y=(h-tipDiv.height())/2;
tipDiv.css("left",x);
tipDiv.css("top",y);
},
this.hide=function(){
var loader=$("#div_maskContainer");
if(loader.length==0) return ;
loader.remove();
},
this.autoDelayHide=function(html,timeOut){
var loader=$("#div_maskContainer");
if(loader.length==0) {
this.show(html);
}
else{
var tipDiv=$("#div_loading");
tipDiv.html(html);
}
if(timeOut==undefined) timeOut=3000;
window.setTimeout(this.hide,timeOut);
}

} 


function bandcard(){
	var idcard='${requestScope.idcard}';
	if(idcard!=""){
		window.location.href=basepath+"/draw/drawmoney?tab=1";
	}else{
		showMsgDialog("提示","请先进行实名认证");
	}
}


</script>

<body>
<div id="div_Mask"  style="display:none;"></div>
	<%@ include file="../common/personheader.jsp"%>
<!--弹出框-->
<div id="div_loading">
<!--001-->

<div class="tck01" id="idcardDiv" style="display:none;" >
<div class="navtitle"><a class="nava" style="width:90px;" >实名认证</a><a class="close" onclick="javascript:closeDiv('idcardDiv')"></a></div>
<div class=" wzms"> 实名信息提交后不可修改，请务必认真填写真实资料

 一个身份证只能绑定一个帐号。

如遇到问题，请联系客服 400-020-0158
</div>
<div class="smain" >
<div class="srk">
<span class="label">真实姓名：</span><input class="au-ipt" name="cardname" id="cardname" type="text">
</div>
<div class="srk"> <span class="label">身份证号：</span>
    <input class="au-ipt" name="input" name="idcard" id="idcard" type="text">
  </div>
 </div>
<!--001-1-->  
 <div class="anniu">
 <a class="btn-h01" id="validating" onclick="javascript:validateCard();">提&nbsp;交</a><a class="btn-h02" onclick="javascript:closeDiv('idcardDiv')">取&nbsp;消</a>
</div>
</div>

<div  id="maxcarderrorNum" class="tck01" style="display:none;" >
<div class="navtitle"><a class="nava" style="width:90px;" >实名认证</a><a class="close"></a></div>
<div class=" pdmain" style="padding:30px;">
  <p><img src="${ctx}/static/images/wrong.png" class="mglt30" width="48" height="48"><span class="font16">您已3次验证错误，请联系客服进行实名认证！</span></p>
</div>
  
 <div class="anniu">
 <a class="btn-h0101"  onclick="javascript:closeDiv('maxcarderrorNum');">提&nbsp;交</a></div>
</div>




<!--002-->
<div class="tck01" id="updateMobile" style="display:none;">
<div class="navtitle"><a class="nava" >修改绑定手机
</a><a class="close" onclick="javascript:closeDiv('updateMobile')"></a></div>
<div class="smain">
<div class="srk">
  <span class="label">原手机号：</span><a class="fontarail" >${mobile }</a>
</div>
<div class="srk">
  <input class="au-ipt mglt30"  id="phonecode" name="phonecode" type="text">
  &nbsp;&nbsp;&nbsp;&nbsp;
  <!--  
    <input type="button" id="validatecode" value="">-->
<a href="javascript:void(0)"  name="validatecode" id="validatecode" class=" colorf60 mglt10" style="text-decoration:none">获取验证码
</a>
  </div>
</div>

<div class="anniu">
 <a class="btn-h01"  id="nextsmsBtn" onclick="javascript:tonextvalidatephonecode();">下一步</a><a class="btn-h02" onclick="javascript:closeDiv('updateMobile')">取&nbsp;消</a>
  </div>
</div>

<!--002-->
<div class="tck01" id="updatenextMobile" style="display:none;">

<div class="navtitle"><a class="nava" >修改绑定手机
</a><a class="close" onclick="javascript:closeDiv('updatenextMobile')"></a></div>
<div class="smain">

<div class="srk">
 <span class="label">手机号：</span><input class="au-ipt mglt30"  id="newphone" name="newphone" type="text">
  &nbsp;&nbsp;&nbsp;&nbsp;
	
</div>
<div class="srk">
 <span class="label">验证码：</span><input class="au-ipt mglt30"  id="newcode" name="newcode" type="text">
  &nbsp;&nbsp;&nbsp;&nbsp;
   <a href="javascript:void(0)"  name="newvalidatecode" id="newvalidatecode" class=" colorf60 mglt10" style="text-decoration:none">获取验证码
  <!--  
    <input type="button" id="newvalidatecode" value="发送验证码">
    -->
    </a>
  </div>
<!--   <div class="srk" id="yzmStrLi">
  	<span class="label">校验码：</span><input class="au-ipt mglt30"  id="yzmCode" name="yzmCode" type="text" style="width:85px">
  	&nbsp;&nbsp;&nbsp;&nbsp;
  	<a href='javascript:void(0);'><img src='validate.code' id='refresh_code' style="width: 85px;height: 29px;" class='rg_l_codeimg'></a>
  </div> -->
</div>

<div class="anniu">
 <a class="btn-h01"  id="nextsmsBtn" onclick="javascript:updatephone();">下一步</a><a class="btn-h02" onclick="javascript:closeDiv('updatenextMobile')">取&nbsp;消</a>
  </div>
</div>



<!--003-->
<div class="tck01" id="bandingEmail" style="display:none;">
<div class="navtitle"><a class="nava" >绑定邮箱</a><a class="close" onclick="javascript:closeDiv('bandingEmail')"></a></div>
<div class="smain">
<div class="srk">
  <span class="label">邮箱：</span>
  <input class="au-ipt" name="email" id="email" type="text">
</div>
<div class="srk"> <span class="label">验证码：</span>
    <input class="au-ipt" name="emailcode" id="emailcode" type="text">
    &nbsp;&nbsp;&nbsp;&nbsp;
    <!--  
    <input type="button" id="validateemailcode" value="获取验证码">
    -->
   	 <a href="javascript:void(0)"  name="validateemailcode" id="validateemailcode" class=" colorf60 mglt10" style="text-decoration:none">获取验证码
    </a>
   
   
  </div>
</div>
  
 <div class="anniu">
 <a class="btn-h01" onclick="javascript:bindEmail();">提&nbsp;交</a><a class="btn-h02" onclick="javascript:closeDiv('bandingEmail')">取&nbsp;消</a>
  </div> 
</div>

<!--002-->
<div class="tck01" id="sendoldMobile" style="display:none;">
<div class="navtitle"><a class="nava" >修改绑定邮箱
</a><a class="close" onclick="javascript:closeDiv('sendoldMobile')"></a></div>
<div class="smain">
<div class="srk">
  <span class="label">原邮箱：</span><a class="fontarail" >${email }</a>
</div>
<div class="srk">
  <input class="au-ipt mglt30"  id="oldemailcode" name="oldemailcode" type="text">
  &nbsp;&nbsp;&nbsp;&nbsp;
  <!--  
    <input type="button" id="oldemailvalidatecode" value="">
    -->
    <a href="javascript:void(0)"  name="oldemailvalidatecode" id="oldemailvalidatecode" class=" colorf60 mglt10" style="text-decoration:none">获取验证码
</a>

  </div>
</div>

<div class="anniu">
 <a class="btn-h01"  id="nextsmsBtn" onclick="javascript:donextupdateEmail();">下一步</a><a class="btn-h02" onclick="javascript:closeDiv('sendoldMobile')">取&nbsp;消</a>
  </div>
</div>

<!--003-->
<div class="tck01" id="updatebandingEmail" style="display:none;">
<div class="navtitle"><a class="nava" >绑定邮箱</a><a class="close" onclick="javascript:closeDiv('updatebandingEmail')"></a></div>
<div class="smain">
<div class="srk">
  <span class="label">邮箱：</span>
  <input class="au-ipt" name="newemail" id="newemail" type="text">
</div>
<div class="srk"> <span class="label">验证码：</span>
    <input class="au-ipt" name="newemailcode" id="newemailcode" type="text">
    &nbsp;&nbsp;&nbsp;&nbsp;
    <td>
    <!--  
    <input type="button" id="randomemailcodes" value="获取验证码">
    -->
    <a href="javascript:void(0)"  name="randomemailcodes" id="randomemailcodes" class=" colorf60 mglt10" style="text-decoration:none">获取验证码
</a>
    </td>
   
  </div>
</div>
  
 <div class="anniu">
 <a class="btn-h01" onclick="javascript:updateEmail();">提&nbsp;交</a><a class="btn-h02" onclick="javascript:closeDiv('updatebandingEmail')">取&nbsp;消</a>
  </div> 
</div>


<!--004-->
<div class="tck01" id="moneypwddiv" style="display:none;">
<div class="navtitle"><a class="nava" >设置提现密码</a><a class="close" onclick="javascript:closeDiv('moneypwddiv')"></a></div>
<div class=" smain">
<div class="srk">
  <span class="label">提现密码：</span>
  <input class="au-ipt" name="drawmoneypwd" id="drawmoneypwd"  type="password">
  <font class="meneypwderror" ></font>
</div>
<div class="srk"> <span class="label">确认提现密码：</span>
    <input class="au-ipt" name="aginmoneypwd" id="aginmoneypwd" type="password">
    <font class="aginmeneypwderror" ></font>
  </div>
 </div>
  
 <div class="anniu">
 <a class="btn-h01" id="moneypwdbtn">提&nbsp;交</a><a class="btn-h02" onclick="javascript:closeDiv('moneypwddiv')">取&nbsp;消</a>
  </div> 
</div>

<div class="tck01" id="updatefogetcode" style="display:none;">
	<div class="navtitle"><a class="nava" >修改提现密码
	</a><a class="close" onclick="javascript:closeDiv('updatefogetcode')"></a></div>
	<div class="smain">
	<div class="srk">
	  <span class="label">手机号：</span><a class="fontarail" >${mobile}</a>
	</div>
	
	<div class="srk">
	  <input class="au-ipt mglt30"  id="drawcode" name="drawcode" type="text">
	  &nbsp;&nbsp;&nbsp;&nbsp;
	  <!--   <input type="button" id="fogetvalidatecode" value="">  -->
        <a href="javascript:void(0)"  name="fogetvalidatecode" id="fogetvalidatecode" class=" colorf60 mglt10" style="text-decoration:none">获取验证码
		</a>
	  </div>
	</div>
	
	<div class="anniu">
	 <a class="btn-h01"  id="nextfogetdrawpwdBtn" >下一步</a><a class="btn-h02" onclick="javascript:closeDiv('updatefogetcode')">取&nbsp;消</a>
	  </div>
</div>


<!--004-->
<div class="tck01" id="forgetmoneypwddiv" style="display:none;">
<div class="navtitle"><a class="nava" >设置提现密码</a><a class="close" onclick="javascript:closeDiv('forgetmoneypwddiv')"></a></div>
<div class=" smain">
<div class="srk">
  <span class="label">提现密码：</span>
  <input class="au-ipt" name="forgetdrawmoneypwd" id="forgetdrawmoneypwd"  type="password">
  <font class="meneypwderror" ></font>
</div>
<div class="srk"> <span class="label">确认提现密码：</span>
    <input class="au-ipt" name="forgetaginmoneypwd" id="forgetaginmoneypwd" type="password">
    <font class="aginmeneypwderror" ></font>
  </div>
 </div>
  
 <div class="anniu">
 <a class="btn-h01" id="updateforgetmoneypwdbtn">提&nbsp;交</a><a class="btn-h02" onclick="javascript:closeDiv('forgetmoneypwddiv')">取&nbsp;消</a>
  </div> 
</div>


<!--004-->
<div class="tck01" id="updatemoneypwddiv" style="display:none;">
<div class="navtitle">
<a class="nava" >设置提现密码</a>
<a class="close" onclick="javascript:closeDiv('updatemoneypwddiv')"></a>
</div>
<div class=" smain">
<div class="srk">
  <span class="label">原提现密码：</span>
  <input class="au-ipt" name="olddrawmoneypwd" id="olddrawmoneypwd"  type="password">
  <font class="olddrawmoneypwderror" ></font>
</div>
<div class="srk">
  <span class="label">提现密码：</span>
  <input class="au-ipt" name="newdrawmoneypwd" id="newdrawmoneypwd"  type="password">
  <font class="newdrawmoneypwderror" ></font>
</div>
<div class="srk"> <span class="label">确认提现密码：</span>
    <input class="au-ipt" name="agindrawmoneypwd" id="agindrawmoneypwd" type="password">
    <font class="agindrawmoneypwderror" ></font>
  </div>
 </div>
  
 <div class="anniu">
 <a class="btn-h01" id="resetmoneypwdbtn">提&nbsp;交</a><a class="btn-h02" onclick="javascript:closeDiv('updatemoneypwddiv')">取&nbsp;消</a>
  </div> 
</div>

<!--005-->
<div class="tck01" id="updatepwddiv" style="display:none;" >
<div class="navtitle"><a class="nava" >修改登录密码</a><a class="close" onclick="javascript:closeDiv('updatepwddiv')"></a></div>

<div class="smain">
<div class="srk">
<span class="label">原登录密码：</span>
<input class="au-ipt" id="oldpwd" name="oldpwd" maxlength="16" type="password">
	<font class=" color999 mglt10 oldpwdErro" ></font>
</div>
<div class="srk">
<span class="label">新登录密码：</span>
<input class="au-ipt" id="newpwd" name="newpwd" maxlength="16" type="password">
<font class=" color999 mglt10 newpwd" ></font>
</div>
<div class="srk"> <span class="label">确认登录密码：</span>
    <input class="au-ipt" id="cnewpwd" name="cnewpwd" maxlength="16" type="password">
  <font class=" color999 mglt10 cnewpwd" ></font>
  </div>
</div>
  
 <div class="anniu">
 <a class="btn-h01" id="doupdatepwd">提&nbsp;交</a><a class="btn-h02" onclick="javascript:closeDiv('updatepwddiv')">取&nbsp;消</a>
</div>
</div>

<!--006-->
<div class="tck01" id="cardInfofile" style="display:none;" >
<div class="navtitle"><a class="nava" >身份证信息上传</a><a class="close" onclick="javascript:closeDiv('cardInfofile')"></a></div>

<div class="smain">
<div class="srk">
<span class="label">身份证正面照片：</span>
 <input type="file" id="idcard_front" ENCTYPE="multipart/form-data" name="files[]">
 <input type="hidden" name="idcardfrontfile" id="idcardfrontfile">
<span id="idcard_frontspan"></span>
</div>
<div class="srk">
<span class="label">身份证背面照片：</span>
<input type="file" id="idcard_back" name="files[]">
<input type="hidden" name="idcardbackfile" id="idcardbackfile">
 <span id="idcard_backspan"></span>
</div>
<div class="srk">
<span class="label">手持身份证正面照片：</span>
<input type="file" id="idcard_path" name="files[]">
<input type="hidden" name="idcardpathfile" id="idcardpathfile">
<span id="idcardspan"></span>
</div>
<div class="flmain">
  <img src="${ctx}/static/images/fanl001.png" width="150" height="115">
  <span>
  <p class="fontwtbd">手持身份证照片示例</p>
  <p class="colorf60 font13">本人的脸部和身份证文字必须在照片内清晰可见</p>
  <p></p>
  </span>
  </div>
 </div>
  
 <div class="anniu">
 <a class="btn-h01" id="cardinfobtn" onclick="javascript:uploadCardFile();">上&nbsp;传</a><a class="btn-h02" onclick="javascript:closeDiv('cardInfofile')">暂不上传</a>
</div>
</div>


</div>
<!--弹出框-->
<div class="centmain">

<%@ include file="../common/leftnav.jsp"%>
<div class="rightmain">
<div class="nav">安全信息</div>
<div class="centmain">
<div class="ulmain">
<div class="pmain">
<img src="${ctx}/static/images/sgrz01.png" width="60" height="55">
<p><a class="font15 ftwtb">实名认证</a>
</p>
<p id="cardInfo">
<c:choose> 
<c:when test="${not empty requestScope.userverified.idcard && requestScope.userverified.status=='2'}">  
	<a class="colorred font14 fontarail ">${requestScope.idcard }</a><a class="color34b3e0 font14 mglt10">照片认证通过</a>
</c:when>
<c:when test="${not empty requestScope.userverified.idcard && requestScope.userverified.status=='1'}">  
           <a class="colorred font14 fontarail ">${requestScope.idcard }</a><a class="color34b3e0 font14 mglt10" href="javascript:toupdateCardFile();" title="请再次上次照片">照片未认证通过</a>
 </c:when> 
 <c:when test="${not empty requestScope.userverified.idcard && requestScope.userverified.status=='6'}">  
          <a class="colorred font14 fontarail ">${requestScope.idcard }</a><a class="color34b3e0 font14 mglt10">照片审核中</a>
 </c:when> 
  <c:when test="${not empty requestScope.userverified.idcard && requestScope.userverified.status=='3'}">  
       <a class='colorred font14 fontarail '>${requestScope.idcard }</a><a class='color34b3e0 font14 mglt10' href='javascript:toupdateCardFile();'>上传照片</a>
</c:when> 
<c:when test="${ empty requestScope.userverified.idcard && requestScope.userverified.validatenum>='3'}">   
<a class="colorred font14 ">认证失败(请联系客服处理)</a>
</c:when>
  <c:when test="${not empty requestScope.userverified.idcard && not empty requestScope.userverified.status}">  
       <a class=' colorred font14 fontarail '>${requestScope.idcard }</a><a class='color34b3e0 font14 mglt10' href='javascript:toupdateCardFile();'>上传照片</a>
</c:when> 
<c:otherwise>
<a class="colorred font14 ">未认证</a><a class="color34b3e0 font14 mglt20" href="javascript:authcard();">立即认证</a>
</c:otherwise>
</c:choose>
</p>
</div>
<div class="color999 font14 mgt10">保障安全,只有完成实名认证才能提现
</div>
</p>
</div>

<div class="ulmain mglt10">
<div class="pmain">
<img src="${ctx}/static/images/bdsj.png" width="60" height="55">
<p><a class="font15 ftwtb">绑定手机</a>
</p>
<p><a class="font14 fontarail colorred ">${mobile }</a><a class="color34b3e0 font14 mglt20" href="javascript:updateMobile();">修改</a>
</p>
</div>
<div class="color999 font14 mgt10">
  <p>手机号码是您在维胜的重要凭证</p>
</div>
</p>
</div>

<div class="ulmain mglt10">
<div class="pmain">
<img src="${ctx}/static/images/bdyx.png" width="60" height="55">
<p><a class="font15 ftwtb">绑定邮箱</a>
</p>
<p id="emaildivinfo">
<c:choose> 
<c:when test="${not empty requestScope.user.email }">  
	
	<a class="colorred fontarail ">${email }</a><a class='color34b3e0 font14 mglt20' href='javascript:toupdateEmail();'>修改</a>
</c:when>
<c:when test="${ empty requestScope.user.email}">  
	
	<a class="colorred font14 ">未绑定</a><a class="color34b3e0 font14 mglt20" href="javascript:toBandingPhone();">立即绑定</a>
</c:when>
</c:choose>
</p>
</div>
<div class="color999 font14 mgt10">
  <p>绑定邮箱后，可作登录时使用</p>
</div>
</p>
</div>

<div class="ulmain">
<div class="pmain">
<img src="${ctx}/static/images/dlmm.png" width="60" height="55">
<p><a class="font15 ftwtb">登录密码</a>
</p>
<p><a class="color34b3e0 font14 ">已设置</a><a class="color34b3e0 font14 mglt20" id="toupdatepwd" href="javascript:void(0)">修改</a>
</p>
</div>
<div class="color999 font14 mgt10">
  <p>登录维胜网站时需要输入的密码</p>
</div>
</p>
</div>

<div class="ulmain mglt10">
<div class="pmain">
<img src="${ctx}/static/images/txmm.png" width="60" height="55">
<p><a class="font15 ftwtb">提现密码</a>
</p>
<p id="moneypwdset">
<c:choose> 
<c:when test="${not empty requestScope.userverified.drawMoneyPwd }">  
<a class='color34b3e0 font14 '>已设置</a>
<a class='color34b3e0 font14 mglt10' id='resetmoneypwd' href='javascript:resetMoneyPwd();'>立即修改</a>
<a class='color34b3e0 font14 mglt10' id='forgetdrawPwd' href='javascript:void(0)'>忘记密码</a>

 </c:when>
 <c:otherwise>
  <a class="colorred font14 ">未设置</a>
 <a class="color34b3e0 font14 mglt20" id="moneypwd" href="javascript:void(0);">立即设置</a>
 </c:otherwise>
 </c:choose>
</p>
</div>
<div class="color999 font14 mgt10">
  <p>保障资金安全，提现需要设置提现密码</p>
</div>
</p>
</div>
<div class="ulmain mglt10">
  <div class="pmain">
      <img src="${ctx }/static/images/yhk.png" width="60" height="55">
      
      <p><a class="font15 ftwtb">绑定银行卡</a></p>
      
           <c:choose> 
			<c:when test="${not empty requestScope.bank}">
			 <p><a class="colorred font14 ">${requestScope.bank }</a>
			 <a class="color34b3e0 font14 mglt20" href="javascript:bandcard()" >修改</a>
			 </p>
			</c:when>
			<c:otherwise>
			 <p><a class="colorred font14 ">未绑定</a>
		      <a class="color34b3e0 font14 mglt20" href="javascript:bandcard()" >立即绑定</a>
		      </p>
			</c:otherwise>
		</c:choose>
  </div>
  
  <div class="color999 font14 mgt10">
    <p>绑定银行卡后，充值提现更方便</p>
  </div>

</div>
</div>
</div>
</div>
</div>
</div>
<%@ include file="../common/personfooter.jsp"%>
<script type="text/javascript">
$("#idcard_front").fileupload({
    url:basepath+"fileUpload",//文件上传地址，当然也可以直接写在input的data-url属性内
    dataType:'json',
    formData:{dir:"upload/idcard",fileType:"img",limitSize:2},//如果需要额外添加参数可以在这里添加
    done:function(e,result){
    	result = result.result;
    	var imgurl = result.url;
    	if(result.error==""){
    		alert("上传成功");
    		$("#idcardfrontfile").val(imgurl);
    		showfilename(imgurl,"idcard_frontspan");
    	}else{
    		alert(result.error);
    	}
    	
    	//document.getElementById("idcardfrontfile").value=url;
    	
    }
});

$("#idcard_back").fileupload({
    url:basepath+"fileUpload",//文件上传地址，当然也可以直接写在input的data-url属性内
    dataType:'json',
    formData:{dir:"upload/idcard",fileType:"img",limitSize:2},//如果需要额外添加参数可以在这里添加
    done:function(e,result){
    	result = result.result;
    	var imgurl = result.url;
    	//document.getElementById("idcardfrontfile").value=url;
    	
    	if(result.error==""){
    		alert("上传成功");
    		$("#idcardbackfile").val(imgurl);
    		showfilename(imgurl,"idcard_backspan");
    	}else{
    		alert(result.error);
    	}
    }
});

$("#idcard_path").fileupload({
    url:basepath+"fileUpload",//文件上传地址，当然也可以直接写在input的data-url属性内
    dataType:'json',
    formData:{dir:"upload/idcard",fileType:"img",limitSize:2},//如果需要额外添加参数可以在这里添加
    done:function(e,result){
    	result = result.result;
    	var url = result.url;
    	//document.getElementById("idcardfrontfile").value=url;
    	if(result.error==""){
    		alert("上传成功");
    		$("#idcardpathfile").val(url);
    		showfilename(url,"idcardspan");
    	}else{
    		alert(result.error);
    	}
    }
});

function showfilename(fileurl,id){
	var length=fileurl.lastIndexOf("/");
	var filename=fileurl.substring(length+1,fileurl.length);
	var name=filename.substring(filename.length-8,filename.length);
	$("#"+id).html(name);
}
</script>
<%@ include file="../common/dsp.jsp"%>
</body>
</html>