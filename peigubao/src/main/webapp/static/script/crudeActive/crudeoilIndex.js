$(document).ready(function(){
	$('.navlist li a').removeClass('on');
	$("#ftseli").addClass("on");
});

function submitSetting() {
	if ($("#checkbox_agree").attr("checked")) {
		if(!isLoginSSO){
			window.location.href=basepath+"/toCrudeOilIndexSSO"; 
		}else{ 
    		$.easyui.submitForm("settingForm");
		} 
	}
	else {
		showMsgDialog("提示","请勾选\"我已阅读并同意《国际原油操盘合作协议》\"");
		return false;
	}
};

function tradeContract(){
	var htmlHeight = 800;  //网页高度
	var htmlWidth = 1221;  //网页宽度
	var iTop = (window.screen.height-30-htmlHeight)/2; //获得窗口的垂直位置;  
	var iLeft = (window.screen.width-10-htmlWidth)/2;  //获得窗口的水平位置;  
	window.open(basepath+'tradeContract','国际原油操盘合作协议','height='+htmlHeight+',innerHeight='+htmlHeight+',width='+htmlWidth+',innerWidth='+htmlWidth+',top='+iTop+',left='+iLeft+',toolbar=no,menubar=no,scrollbars=yes,resizeable=no,location=no,status=no');  
};