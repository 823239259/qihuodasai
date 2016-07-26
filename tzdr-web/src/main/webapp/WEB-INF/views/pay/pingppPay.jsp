<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp"%>
<%@include file="../common/import-fileupload-js.jspf"%>
<%@include file="../common/import-artDialog-js.jspf"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>维胜充值</title>
</head>
  <script type='text/javascript' src="${ctx}/static/script/pingpp.js?version=20150721"></script>
<body>
		<span style = "display: none;" id= "charge">${charge }</span>
		<script type="text/javascript">
			$(function(){
				 var charge = JSON.parse($("#charge").text());
				pingpp.createPayment(charge , function(result, err){
					  if (result == "success") {
					    // 只有微信公众账号 wx_pub 支付成功的结果会在这里返回，其他的支付结果都会跳转到 extra 中对应的 URL。
					  } else if (result == "fail") {
					    // charge 不正确或者微信公众账号支付失败时会在此处返回
					  } else if (result == "cancel") {
					    // 微信公众账号支付取消支付
					  }
					});
			});
			
		</script>
</body>
</html>