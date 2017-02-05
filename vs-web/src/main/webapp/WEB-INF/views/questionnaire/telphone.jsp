<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp"%>
<%@include file="../common/import-fileupload-js.jspf"%>
<%@include file="../common/import-artDialog-js.jspf"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<%@ include file="../common/commonkeyword.jsp"%>
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/common.css">
<link rel="stylesheet" href="${ctx}/static/activies/css/sp.css"/>
<link rel="stylesheet" href="${ctx}/static/script/valForm/css/style.css">

<link rel="stylesheet" href="${ctx}/static/css/tzdr.css">
 <script src="${ctx}/static/script/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
 <script src="${ctx}/static/script/questionnaire/questionnaire.js" type="text/javascript"></script>
 <script src="${ctx}/static/script/tzdr.js" type="text/javascript"></script>
<script src="${ctx}/static/script/common/tzdr.user.js?version=20150720" type="text/javascript"></script>		
<script src="${ctx}/static/script/jquery.validate.min.js" type="text/javascript"></script>		
<script type="text/javascript">
$(document).ready(function(){
	var id=$("#id").val();
	  $.post(basepath+"/sendQuestionPhoneCode",{"id":id},function(data){  
			if(data.success){
				if(data.message!="" && data.message!=null){
					if(data.message=="success"){
						countReset($("#validatecode"),60,60);
					}
			    }	
			}
		},"json");
	
});

</script>
	
</head>
<body>
<form action="${ctx}/sendAccount" id="" method="post">
<input type="hidden" name="id" id="id" value="${entity.id}">
<div class="question">
    <div class="qbanner1"></div>
    <div class="qbanner2"></div>
 
    <div class="qstep">
        <img src="${ctx}/static/activies/images/qtitle_01.png">
    </div>
    <div class="qs_bg">
        <div class="tp_main">
            <h3>已向您的手机${mobile}发送效验码</h3>
           <div class="tp_m_main">
            <label>效验码：</label>
            <input type="text" maxlength="6" onKeyUp="javascript:clearCodeNum(event,this)" name="code" id="code">
            <a  href="javascript:void(0)"  id="validatecode" nam="validatecode" style="width:160px;">重新发送</a>
        </div>
            <div class="tp_m_main" style="display:none">
                <label></label>
                <span>效验码错误,请重新发送效验码.</span>
            </div>
            <div class="tp_m_main">
                <label></label>
                <a href="javascript:void(0)"  id="submitForm">确认</a>
            </div>
            <p>提示：如果你的手机号填写错误，请<a href="javascript:void(0)" onclick="history.back()">返回</a>修改。</p>
        </div>


    </div>
</div>

</form>


<!-- 公司简介 -->
<!-- DINGBU -->
<%@ include file="../common/dsp.jsp"%>
</body>
</html>
