var editRecharge={
		changeType:function(typeVal){
			if (typeVal==3){
				$("#trtradeAccount").hide();
				$("#trbankCard").hide();
				$("#trwechatNo").hide();
				$("#tralipayNo").show();
				$("#tradeAccount").combobox({required:false});
				$("#bankCard").validatebox({required:false});
				$("#wechatNo").validatebox({required:false});
				$("#alipayNo").validatebox({required:true});
			}else if(typeVal == 9){
				$("#trtradeAccount").hide();
				$("#trbankCard").hide();
				$("#tralipayNo").hide();
				$("#trwechatNo").show();
				$("#tradeAccount").combobox({required:false});
				$("#bankCard").validatebox({required:false});
				$("#alipayNo").validatebox({required:false});
				$("#wechatNo").validatebox({required:true});
			}else
			{
				$("#tralipayNo").hide();
				$("#trwechatNo").hide();
				$("#trtradeAccount").show();
				$("#trbankCard").show();
				$("#alipayNo").validatebox({required:false});
				$("#wechatNo").validatebox({required:false});
				$("#tradeAccount").combobox({required:true});
				$("#bankCard").validatebox({required:true});
			}
		}
}

$(function(){
	var typeVal=$("#retype option:selected").val();
	editRecharge.changeType(typeVal);
	var tempsource = $("#tempsource").val();
	if (tempsource){
		$("#source").combobox("select",tempsource);
	}

});

