$(document).ready(function(){
	
	//倒计时
	var wait=3;
	//执行倒计时方法
	
	//注册渠道
	var type = $("#type").val();
	
	//关闭对话框
	$("#rollclose").on("click", function(){
		$("#rolldialog").css({display:"none"});
		time();
	});
	
	//确认操作
	$("#rollaffirm").on("click", function(){
		$("#rolldialog").css({display:"none"});
		time();
	});
	
	//判断是否取到卷
	if($("#rolldialog").length <= 0){
		time();
	}
	
	//倒计时方法
	function time() {  
		if (wait <= 0) {  
			wait = 3; 
			var backUrl=$.trim($("#backUrl").val());
			if(backUrl){
				window.location.href= backUrl;
			}else{
				window.location.href=basepath+'user/account';
			}
			/*if(type==0){
				window.location.href=basepath;
			}else{
				window.location.href=basepath+'day?enter=0';
			}*/
			
		} else {  
			$("#time").text(wait);
			wait--;  
			setTimeout(function() {  
				time()  
			},  
			1000)  
		}  
	} 
});