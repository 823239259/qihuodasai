$(document).ready(function() {
	//顶部菜单位置
	$('.navlist li a').removeClass('on');
	$("#togetherli").addClass("on");
	
	$('.bd_is_btn').bind("click",function(){
		if(!isLoginSSO){
			window.location.href=basepath+"toPublicTogetherSSO"; 
		}else{
			showMsgDialog("提示","目前股票合买仅对邀请用户开放，全面开放时间待定，敬请期待！");
		}
	})
})


