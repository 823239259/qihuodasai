<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>充值跳转</title>
</head>
<body>	
	<script type='text/javascript' src="${ctx}/static/script/jquery-1.8.3.js"></script>
	<script type="text/javascript">
		$(function(){
			$.ajax({
				url:"gopay",
				type:"post",
				data:{
					paymoney:$("#money").val(),
					gopayWay:$("#payWay").val(),
					isFlag:$("#isFlag").val()
				},
				success:function(result){
					 if(result.success){
						var $data = result.data.data;	
						var data = JSON.parse($data);
						$("input[name = version]").val(data.Version);
						$("input[name = charset]").val(data.Charset);
						$("input[name = language]").val(data.Language);
						$("input[name = signType]").val(data.signType);
						$("input[name = tranCode]").val(data.tranCode);
						$("input[name = merchantID]").val(data.merchantID);
						$("input[name = merOrderNum]").val(data.merOrderNum);
						$("input[name = tranAmt]").val(data.tranAmt);
						$("input[name = feeAmt]").val(data.feeAmt);
						$("input[name = currencyType]").val(data.currencyType);
						$("input[name = frontMerUrl]").val(data.frontMerUrl);
						$("input[name = backgroundMerUrl]").val(data.backgroundMerUrl);
						$("input[name = tranDateTime]").val(data.tranDateTime);
						$("input[name = goodsDetail]").val(data.goodsDetail);
						$("input[name = virCardNoIn]").val(data.virCardNoIn);
						$("input[name = goodsName]").val(data.goodsName);
						$("input[name = tranIP]").val(data.tranIP);
						$("input[name = buyerContact]").val(data.buyerContact);
						$("input[name = buyerName]").val(data.buyerName);
						$("input[name = isRepeatSubmit]").val(data.isRepeatSubmit);
						$("input[name = signValue]").val(data.signValue);
						$("#form").submit(); 
					 }else{
						 alert("支付跳转失败,请重试");
					 }
				}
			});
		}); 
	</script>
	<form action="https://gateway.gopay.com.cn/Trans/WebClientAction.do" method="POST" id = "form" style="display: none;"> 
		 版本号:  <input type="text"  name="version" /> 
		  字符集:  <input type="text"  name="charset" />  
		  语言种类:<input type="text"  name="language" />  
		  签名类型:<input type="text"  name="signType" /> 
		   交易代码:<input type="text"  name="tranCode" />  
		   商户代码:  <input type="text"  name="merchantID" /> 
		    订单号:  <input type="text"  name="merOrderNum"/>  
		    交易金额:<input type="text"  name="tranAmt"/> 
		     手续费:  <input type="text"  name="feeAmt" />  
		     币种:    <input type="text"  name="currencyType"/> 
		      前台通知地址:<input type="text"  name="frontMerUrl"/> 
		 后台通知地址:<input type="text"  name="backgroundMerUrl"/> 
		 交易时间:<input type="text"  name="tranDateTime"/> 
		  转入账户:<input type="text"  name="virCardNoIn"/> 
		 用户IP:  <input type="text"  name="tranIP"/> 
		  商品名称:<input type="text"  name="goodsName"/>  
		  商品描述:<input type="text"  name="goodsDetail"/>  
		  买方姓名:<input type="text"  name="buyerName" />  
		  买方联系方式:<input type="text" name="buyerContact"/> 
		   商户备用字段1:<input type="text" name="merRemark1" />
		     商户备用字段2:<input type="text" name="merRemark2"/> 
		      系统备用信息: <input type="text" name="remark"/>  
		      重复提交标志:<input type="text"  name="isRepeatSubmit"/>
		       密文串:<input type="text"  name="signValue" /> 
		上送手机号:<input type="text"  name="buyerRealMobile" />
		 上送姓名:<input type="text"  name="buyerRealName" /> 
		 上送身份证:<input type="text"  name="buyerRealCertNo" /> 
		 上送银行卡号:<input type="text"  name="buyerRealBankAcctNum"/>  
		 <input type="submit" >
	</form>
	<input type="hidden" value="${money }" id = "money"/>
	<input type="hidden" value="${payway }" id = "payWay">
	<input type="hidden" value="${isFlag }" id = "isFlag">
</body>
</html>