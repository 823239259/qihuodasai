
function colseConfirmPayDiv() {
	$("#confirmPayDiv").hide();
};

$('#confirmPay').live('click',function(){
	var status =  $(this).attr("status");
	$(this).attr("status",false);
	if(status == "true"){
		$("#confirmPayDiv").hide();
		$(this).attr("status",true);
		$('#forPaySubmit').click();
	}
});