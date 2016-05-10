<%@ page language="java" pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>商户提交数据给币币</title>
</head>

<body onload="load()">
	发送安全支付请求给币币
	<h4>${errorMsg}</h4>
	<input type="hidden" id="errorMsg" value="${errorMsg}">
	<form id="payform" action="${mobilePayUrl}" method="post">
		<input type="hidden" id="merchantaccount" name="merchantaccount" value="${merchantaccount}" /> 
		<input type="hidden" id="data" name="data" value="${data}" /> 
		<input type="hidden" id="encryptkey" name="encryptkey" value="${encryptkey}" /> 
	</form>
</body>

<script type="text/javascript">
	function load() {
		var errorMsg = document.getElementById("errorMsg").value;
		if (null !=errorMsg && "undefined" !=errorMsg && errorMsg.length==0)
		{   
			document.getElementById("payform").submit();
		}
	};
</script>
</html>