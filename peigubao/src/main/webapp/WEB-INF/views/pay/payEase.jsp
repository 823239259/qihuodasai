<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>商户提交数据给易支付平台</title>
</head>

<body onload="load()">
	<!-- 商户提交数据给易支付平台 -->
	<h4>${errorMsg}</h4>
	<input type="hidden" id="errorMsg" value="${errorMsg}">
	<c:choose>
		<c:when test="${empty payEaseParams}">
			<h4>提交数据验签失败！</h4>
		</c:when>
		<c:otherwise>
			<P align="center">正在提交数据到银行，请稍等......</P>
			<form id="payform" action="${payEaseParams.payurl}" method="post" style="display:none; ">
			<input type="text" name="v_mid" value="${payEaseParams.vmid}" id="v_mid">
			<input type="text" name="v_oid" value="${payEaseParams.vorid}" id="v_oid">
			<input type="text" name="v_rcvname" value="${payEaseParams.vrcvname}" id="v_rcvname">
			<input type="text" name="v_rcvaddr" value="${payEaseParams.vrcvaddr}">
			<input type="text" name="v_rcvtel" value="${payEaseParams.vrcvtel}">
			<input type="text" name="v_rcvpost" value="${payEaseParams.vrcvpost}">
			<input type="text" name="v_amount" value="${payEaseParams.vamount}" id="v_amount">
			<input type="text" name="v_ymd" value="${payEaseParams.vymd}" id="v_ymd">
			<input type="text" name="v_orderstatus" value="${payEaseParams.vorderstatus}">
			<input type="text" name="v_ordername" value="${payEaseParams.vordername}">
			<input type="text" name="v_moneytype" value="${payEaseParams.vmoneytype}" id="v_moneytype">
			<input type="text" name="v_url" value="${payEaseParams.vurl}" id="v_url">
			<input type="text" name="v_md5info" value="${payEaseParams.vmd5info}" id="v_md5info">
			<input type="text" name="v_pmode" value="${payEaseParams.vpmode}" >
			</form>
		</c:otherwise>
	</c:choose>
	
</body>

<script type="text/javascript">
 function load() {
	    var errorMsg = document.getElementById("errorMsg").value;
	    if (null !=errorMsg && "undefined" !=errorMsg && errorMsg.length>0)
		{
	    	return;
		}
		var payform = document.getElementById("payform");
		if (payform)
		{   
			document.getElementById("payform").submit();
		}
	}; 
</script>
</html>