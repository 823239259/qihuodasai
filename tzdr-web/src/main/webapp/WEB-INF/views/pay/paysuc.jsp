<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp"%>
 <%@include file="../common/import-artDialog-js.jspf"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="UTF-8">
    <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no" />
    <title>充值成功</title>
    <style>
    	body {
    	  width: 100%;
		  height: 100%;
		  margin: 0px;
		  padding: 0px;
		  background-color: #f5f5f5;
    	}
    	.mui-content {
		  height: 100%;
		  background: none;
		  padding-bottom: 50px;
		}
		.pay_suc img {
		  width: 60px;
		  display: block;
		  margin: 15% auto 0;
		}
		.mui-content-padded {
		  margin: 25px 10px 10px;
		}
		.pay_suc .mui-content-padded h4 {
		  text-align: center;
		  font-weight: normal;
		  color: #333;
		  margin-bottom: 40px;
		  font-size: 18px;
		}
    </style>
</head>
<script type="text/javascript" src="${ctx}/static/script/Utils.js?v=${v}"></script>

<script type="text/javascript">
var time = 5; 
var intervalTime = null;
intervalTime =  setInterval(function(){
		time = time - 1;
		$("#time").text(time);
		if(time == 0){
			var payUrl = getCookie("payurl");
			if(payUrl != null && payUrl != "null" && payUrl != undefined && payUrl.length > 0){
				//验证订单是否支付成功
				$.ajax({
					url:"${ctx}/userOutDisk/validation/order/pay",
					type:"get",
					success:function(result){
						if(result.success){
								$("input[name = 'inputTraderBond']").val(getCookie("bond"));
								$("input[name = 'inputTranLever']").val(getCookie("lever"));
								$("input[name = 'voucherId']").val(getCookie("vocherid"));
								$("input[name = 'tokenTzdr']").val(getCookie("tokenTzdr"));
								$("#payableForm").attr("action","${ctx}/" + payUrl);
								$.ajax({
									url:"${ctx}/userOutDisk/clearPayCookie",
									type:"post",
									success:function(result){
										
									}
								})
								$("input[type = 'submit']").click();
								return ;
						}else{
							showMsgDialog("提示", "购买方案失败，请重新购买");
							 setTimeout(function(){
								 $("input[type = 'submit']").click();
							 }, 3000);
							 return;
						}
					}
				});
			}else{
				$("input[type = 'submit']").click();
			}
			clearInterval(intervalTime);
		}
	}, 1000);
</script>
<body>
<div class="pay_suc">
	<div class="mui-content-padded">
		<h4>已充值成功,<b id = "time">5</b>秒后自动跳转...</h4>
		
	</div>
	<form action="${ctx}/user/account" style = "display: none;" id="payableForm" method="post">
		<input type="hidden" name="inputTraderBond" value="0" />
		<input type="hidden" name="inputTranLever" value="0" />
		<input type="hidden" name="tokenTzdr" value="0" />
		<input type="hidden" name="voucherId" value="" />
		<input type="submit"/>
	</form>
</div>
</body>
</html>