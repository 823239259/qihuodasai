$(document).ready(function(){

	//校验手机号码
	$("#mobile").blur(function(){
		var $this = $(this);
		var mobileValue =  $.trim($this.val());
		verifyMobile($this);  //检验手机号码
	});

	//检验手机号码格式
	function verifyMobile(obj){
		var mobileValue =  $.trim(obj.val());
		if(mobileValue == null || mobileValue.length <= 0){  //判断手机号码是否为空  
			obj.parent().find("p").addClass("error");
			obj.parent().find("p").html("<i>*</i>请填写手机号码");
			return false;
		}else if(!mobileValue.match(/^(((13[0-9])|(14[7])|(15[0-9])|(17[0-9])|(18[0-9]))+\d{8})$/)){  //判断手机号码是否正确
			obj.parent().find("p").addClass("error");
			obj.parent().find("p").html("<i>*</i>手机号码输入有误");
			return false;
		}
		obj.parent().find("p").removeClass("error");
		obj.parent().find("p").html("<i>*</i>您将用该手机号登录");
		return true;
	}

	//打开短信验证码框
	$("#openYZMBox").live("click",function(){
		var $this = $(this);
		if(verifyMobile($("#mobile"))){
			$(".fl_mask").show();
			$("#yzm_box").show();
		}
	});
	
	//关闭短信验证码框
	function colseYZMBox(){
		$(".fl_mask").hide();
		$("#yzm_box").hide();
		$("#yzm").val('');
	}
	
	//关闭短信验证码框
	$(".yzm_box_close").live("click",function(){
		colseYZMBox();
	});
	
	//刷新注册码
	$(".refresh_code").live("click",function(){
		var $this = $(this);
		$("#refresh_code").attr("src",basepath+"validate.code?1=" + Math.random()*10000);
	});

	//验证码倒计时
	var wait=60;
	
	//获取短信验证码
	$("#getSMSCode").live("click",function(){
		var $this = $(this);
		var status =  $(this).attr("status");  
		if(status == "true"){
			$this.attr("status",false);
			$this.css({background:'#D3D3D3'});
			var yzm = $("#yzm").val();
			var mobile = $("#mobile").val();
			if(yzm == null || yzm.length <= 0){
				$this.attr("status",true);
				$this.css({background:'#ff9c00'});
				$.alertTip($(this),"验证码不能空 ！");
				return;
			}
			$.post(basepath+"send_mobile_code",{mobile:mobile,yzmCode:yzm,ajax:1},function(data){  //获取短信验证码信息
				$("#refresh_code").attr("src",basepath+"validate.code?1=" + Math.random()*10000);
				if(data.success){
					if(data.message!="" && data.message!=null){
						if(data.message == "mobileIsExist"){
							$this.attr("status",true);
							$this.css({background:'#ff9c00'});
							$.alertTip($("#getSMSCode"),"该手机号码已经存在 ！");
							return;
						}else if(data.message == "mobileIsNull" || data.message == "mobileFormatError"){
							$this.attr("status",true);
							$this.css({background:'#ff9c00'});
							$.alertTip($("#getSMSCode"),"手机号码错误 ！");
							return;
						}else if(data.message == "yzmStrError"){
							$this.attr("status",true);
							$this.css({background:'#ff9c00'});
							$.alertTip($("#getSMSCode"),"验证码错误！");
							return;
						}else{
							colseYZMBox();
							$this.attr("status",true);
							$this.css({background:'#ff9c00'});
							$("#openYZMBox").attr("disabled",true);
							$("#openYZMBox").text("重新发送(" + wait + ")");
							$.alertTip($("#openYZMBox"),data.message == "highOperation" ? "操作过于频繁，请稍后再重试！" : "每天只能获取5次验证码 ！");
							countdown($("#openYZMBox"));  //按钮设置倒计时
						}
					}else{
						colseYZMBox();
						$this.attr("status",true);
						$this.css({background:'#ff9c00'});
						$("#openYZMBox").attr("disabled",true);
						$("#mobile").parent().find("p").removeClass("error");
						$("#mobile").parent().find("p").html("<i>*</i>√");
						$("#openYZMBox").text("重新发送(" + wait + ")");
						countdown($("#openYZMBox"));  //按钮设置倒计时
					}
				}else{
					$this.attr("status",true);
					$this.css({background:'#ff9c00'});
					$.alertTip($(this),"系统繁忙，请重试......");
				}
			},"json");
		}
	});
	
	//验证码倒计时
	function countdown(obj) {  
		if (wait <= 0) {  
			obj.attr("disabled",false);
			obj.text("发送验证码");  
			wait = 60;  
		} else {  
			obj.attr("disabled",true);
			obj.text("重新发送(" + wait + ")");  
			wait--;  
			setTimeout(function() {  
				countdown(obj);  
			},  
			1000);  
		}  
	} 
	
	//校验密码  
	$("#password").blur(function(){
		var $this = $(this);
		verifyPassword($this);  //校验密码格式
	});
	
	//校验密码格式
	function verifyPassword(obj){
		var passwordValue =  $.trim(obj.val());
		if(passwordValue == null || passwordValue.length <= 0 || !/^[0-9a-zA-Z]{6,16}$/.test(passwordValue) || passwordValue.length != $.trim(passwordValue).length ){
			obj.parent().find("p").addClass("error");
			return false;
		}else if(!/[0-9]{1,}/.test(passwordValue) || !/[a-zA-Z]{1,}/.test(passwordValue)){
			obj.parent().find("p").addClass("error");
			return false;
		}else if(passwordValue.length < 6 || passwordValue.length > 20){
			obj.parent().find("p").addClass("error");
			return false;
		}
		obj.parent().find("p").removeClass("error");
		return true;
	}
	
	//校验手机验证码格式
	function verifyCode(obj){
		var codeValue =  $.trim(obj.val());
		obj.parent().find("p").html("<i>*</i>请填写验证码");
		if(codeValue == null || codeValue.length <= 0){
			obj.parent().find("p").addClass("error");
			return false;
		}else{
			obj.parent().find("p").removeClass("error");
			return true;
		}
	}
	
	//协议选择操作
	$("#agreement").on("click",function(){
		var $this = $(this);
		var status = $this.attr("checked");
		if(status == null || status == "undefined"){
			$this.removeAttr("checked");
			$this.parent().parent().parent().find("#fastsignin").attr("status",false);
			$this.parent().parent().parent().find("#fastsignin").css({background:'#D3D3D3'});
		}else{
			$this.attr("checked","checked");
			$this.parent().parent().parent().find("#fastsignin").attr("status",true);
			$this.parent().parent().parent().find("#fastsignin").css({background:'url('+basepath+'static/images/btnbg_02.gif) repeat-x'});
		}
	});
	
	//注册
	$("#fastsignin").on("click", function(){
		/*art.dialog({
		    title: '提示',
		    content:'为响应政府整顿配资市场，本站已经暂时关闭注册功能，如有疑问请联系客服热线400-020-0158',
		    width:400,
		    cancelVal: '关闭',
		    background: '#BBBBBB', // 背景色 
		    opacity:0.87, 
		    zIndex:9900,
		    lock:true,
		    cancel: true //为true等价于function(){}
		});
		return;*/
		
		var $this = $(this);
		if($(this).attr("status") == "true"){
			$this.attr("status",false);
			$this.text("正在注册");
			var mobileObject =  $this.parent().find("#mobile");
			var mobileValue =  $.trim(mobileObject.val());
			var passwordObject =  $this.parent().find("#password");
			var passwordValue =  passwordObject.val();
			var codeObject =  $this.parent().find("#code");
			var codeValue =  $.trim(codeObject.val());
			//校验手机号码、验证码、密码
			if(!verifyMobile(mobileObject) || !verifyCode(codeObject) || !verifyPassword(passwordObject)){
				$this.attr("status",true);
				$this.text("免费注册");
				return;
			}
			
			$.post(basepath+"signin_operation",{ 'source':1,'mobile':mobileValue,'code':codeValue,'password':passwordValue,ajax:1},function(data){ //注册
				if(data.success){
					if(data.message!="" && data.message!=null){
						if(data.message=="mobileIsExist"){
							mobileObject.parent().find("p").addClass("error");
							mobileObject.parent().find("p").html("<i>*</i>该手机号码已经存在");
						}else if(data.message=="codeError"){
							codeObject.parent().find("p").addClass("error");
							codeObject.parent().find("p").html("<i>*</i>验证码错误,请重新获取");
						}else if(data.message=="codeTimeOut"){
							codeObject.parent().find("p").addClass("error");
							codeObject.parent().find("p").html("<i>*</i>验证码已失效,请重新获取");
						}
						$this.attr("status",true);
						$this.text("免费注册");
					}else{
						//达人论股系统登录
						discussStockLogin(data.data.key,function(){
							var form = $("<form action='"+basepath+"signinsucess"+"' method='post'><input type='hidden' name='volumeNum' value='" + data.data.volumeNum + "'/><input type='hidden' name='volumePrice' value='" + data.data.volumePrice + "'/><input type='hidden' name='type' value='1'/></form>");
							form.appendTo(document.body).submit();
						});
					}
				}else{
					$this.attr("status",true);
					$this.text("免费注册");
					showMsgDialog("提示","系统繁忙，请重试......");
				}
			},"json");
		}
	});
	
	var form = $(".form");
	
	// 回车事件
	form.on("keypress", "input", function(event){
		if(event.keyCode == 13){
			$("#fastsignin").trigger("click");
			return false;
		}
	});
	
	//初始化定义光标位置
	$("#mobile")[0].focus();
});

//显示协议
function showAgreement(){
	var htmlHeight = 685;  //网页高度
	var htmlWidth = 1221;  //网页宽度
	var iLeft = (window.screen.width-10-htmlWidth)/2;  //获得窗口的水平位置;  
	window.open(basepath+'websiteAgreement','投资达人网站服务协议','height='+htmlHeight+',innerHeight='+htmlHeight+',width='+htmlWidth+',innerWidth='+htmlWidth+',top=0,left='+iLeft+',toolbar=no,menubar=no,scrollbars=yes,resizeable=no,location=no,status=no');  
}