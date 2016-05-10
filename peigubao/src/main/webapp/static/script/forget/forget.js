$(document).ready(function(){
	var changecolor={
		message_foucs:function(obj,text,dis){
			$(obj).text(text);
			$(obj).css("display",dis);
			$(obj).css("border","1px solid #178E30");
		 	$(obj).css("background","#D7EEDB");
		 	$(obj).css("color","green");
		 	$(obj).closest("p").find("input").css("border","1px solid #178E30");
		} ,
		message_blus:function(obj,text,dis){
			 $(obj).closest("p").find("input").css("border","1px solid #C5C5C5");
			 $(obj).text(text);
			$(obj).css("color","green");
			$(obj).css("display",dis);
		},
		error:function(obj,text,dis){
			 $(obj).text(text);
			 $(obj).css("color","red");
			 $(obj).closest("p").find("input").css("border","1px solid #FF4D4D");
			 $(obj).css("border","1px solid #FF4D4D");
			 $(obj).css("background","#FFE4E5");
			 $(obj).css("display",dis);
		}
	}
	
	//设置标题
	//$(".headerTitle").text("用户登录");
	//手机号码规则
	var mobileForm =/^(((13[0-9])|(14[5|7])|(15[0-3|5-9])|(17[0|6-8])|(18[0-9]))+\d{8})$/;

	//初始化验证手机验证码页面的文本提示信息
	var initMobileText = "请输入注册时填写的手机号";
	
	var initMobileCodeText = "短信确认码";
	
	//不允许返点为非数据类型
//	$(".mobile").keypress(T.numKeyPress).keyup(function() {
//		$(this).focus();
//	});
	
	$('#phone').focus(function(){
//		 var $this = $(this);
//		if($.trim($this.val()) == initMobileText){
//			$this.val("");
//		}
		//$(this).val("");
		changecolor.message_foucs("#firstSpan",initMobileText,"block");
	});
	
	$('#phone').blur(function(){
		 var $this = $(this).val();
//		if(!$.trim($this.val())){
//			$this.val(initMobileText);
//		}
		 if($this==""){
			 changecolor.error("#firstSpan","手机号码不能为空","block");
		 }
		 else if(!$this.match(mobileForm)){
			 changecolor.error("#firstSpan","请输入正确的手机号码","block");
		 }else{
			 changecolor.message_blus("#firstSpan","","none");
		 }
		 
	});
	
	$('#checkPhone').focus(function(){
		 var $this = $(this);
		 //$this.val("");
		 //changecolor.message_foucs("#checkback2","","none");
		// $(".code").val("");
//		if($.trim($this.val()) == initMobileCodeText){
//			$this.val("");
//		}
		//$this.css("display","none");
		
	});
	
//	$('#checkPhone').blur(function(){
//		 var $this = $(this);
////		if(!$.trim($this.val())){
////			$this.val(initMobileCodeText);
////		}
//		 $this.val();
//	});
	
	//验证码倒计时
	var wait=60;
	
	//获取短信验证码信息
	$("#huoqu1").on("click", function(){
		
		yz=false;
		var $this = $(this);
		if($this.text().indexOf("重新获取")>-1){
			changecolor.error("#checkback2","短信已发送，请不要重复获取确认码 !","block");
			return;
		}
//		var status = $this.attr("status",true);  
//		if(status == "true"){
			
			//$this.attr("status",false);
			
			 
			var mobile = $("#phone").val();
			if(mobile == null || mobile.length <= 0){  //判断手机号码是否为空
				$("#phone").blur();
				return;
			}else if(!mobile.match(mobileForm)){  //判断手机号码是否正确
				$("#phone").blur();
				return;
			}else{
				$this.css({background:'#D3D3D3'});
				$this.text(wait+"秒后重新获取");
				time($this);  //按钮设置倒计时
			$.post(basepath+"send_password_mobile_code",{mobile:mobile,ajax:1},function(data){
				if(data.success){
					if(data.message!="" && data.message!=null){
						if(data.message == "mobileNotExist"){
							//$.alertTip($("#phone"),"请输入正确的手机号码 !");
							$("#phone").focus();
						}else if(data.message == "sendMobileCodeFail"){
							//$.alertTip($this,"下发验证码失败，请重新获取验证码 !");
							
							 changecolor.error("#checkback2","下发确认码失败，请重新获取确认码 !","block");
						}
						//$this.attr("status",true);
						wait = 0;
					}else{
						$(".token").val(data.data.token);
						$('#checkPhone').focus();
						//$(".code").focus();
					}
				}else{
					wait = 0;
					//$this.attr("status",true);
//					$.alertTip($this,"系统错误...... ");
					showMsgDialog("提示","系统错误.....");
				}
			},"json");
			}
//		}
	});
	
	//验证码倒计时
	function time(obj) {  
		if (wait <= 0) {  
			//obj.attr("status",true);
			obj.text("获取确认码");  
			obj.css({background:'#34B3E0'});
			wait = 60;  
		} else {  
			//obj.attr("status",false);
			obj.text("重新获取(" + wait + ")"); 
			wait--;  
			setTimeout(function() {  
				time(obj)  
			},  
			1000)  
		}  
	} 
	
	var yz=false;
	//验证短信验证码
	$('#checkPhone').blur(function(){
		var $this = $(this);
		//changecolor.message_blus("#checkback2","","none");
		if(yz!=null&&yz==true){
			return;
		}
		if($this.val()==null||$this.val()==""){
			return;
		}
		//var status = $this.attr("status");  
		var mobile = $.trim($("#phone").val());
		var code =  $.trim($("#checkPhone").val());
		var token = $.trim($(".token").val());
		if(token==null||token==""){
			changecolor.error("#checkback2","请先获取确认码!","block");
			  return;
		}
//		if(status == "true"){
			//$this.attr("status",false);
			$this.text("正在处理中");
//			if(mobile == null || mobile.length <= 0 || mobile == initMobileText){
//				$this.attr("status",true);
//				$this.text("下一步");
//				$.alertTip($(".mobile"),"请输入手机号码 !");
//				$(".mobile").focus();
//				return ;
//			}else if(!mobile.match(mobileForm)){  //判断手机号码是否正确
//				$this.attr("status",true);
//				$this.text("下一步");
//				$.alertTip($(".mobile"),"请输入正确的手机号码 !");
//				$(".mobile").focus();
//				return;
//			}else if(code ==  null || code.length <= 0 || code == initMobileCodeText){
//				$this.attr("status",true);
//				$this.text("下一步");
//				$.alertTip($(".code"),"请输入手机号码短信验证码 !");
//				$(".code").focus();
//				return ;
//			}
			$.post(basepath+"verify_mobile_code",{token:token,mobile:mobile,code:code,ajax:1},function(data){
				if(data.success){
					if(data.message!="" && data.message!=null){
					   if(data.message == "mobileNotExist"){
						  // $.alertTip($(".mobile"),"请输入正确的手机号码 !");
						   $("#phone").focus();
					   }else if(data.message == "codeError"){
							//$.alertTip($(".code"),"验证码错误,请重新获取验证码 !");
						   changecolor.error("#checkback2","确认码错误，请重新获取 !","block");
						}else{
							//$.alertTip($(".code"),"验证码已失效,请重新获取验证码 !");
							changecolor.error("#checkback2","确认码已失效,请重新获取确认码 !","block");
						}
					}else{
//						var form = $("<form action='"+basepath+"forgetpw?step=2' method='post'></form>");
//						form.append("<input type=hidden name=token value='" + data.data.token + "'/>");
//						form.append("<input type=hidden name=mobile value='" + mobile + "'/>");
//						form.appendTo(document.body).submit();
						yz=true;
						changecolor.message_blus("#checkback2",yz,"none");
						$(".token").val(data.data.token);
					}
				}else{
//					$.alertTip($this,"系统错误...... ");
					showMsgDialog("提示","系统错误.....");
				}
//				$this.text("下一步");
//				$this.attr("status",true);
				wait = 0;
			},"json");
//		}
	});
	
//	//获取找回密码检验验证form
//	var checkoutMobile = $(".checkoutMobile");
//	
//	//回车事件
//	checkoutMobile.on("keypress", "input", function(event){
//		if(event.keyCode == 13){
//			$(".verifyMobileCode").trigger("click");
//			return false;
//		}
//	});
	
	//初始化定义检验验证码页面的光标位置
	//$(".mobile").focus();
	
	//初始化设置密码页面的文本提示信息
	var initNewPasswordText = "6-16位数字和字母组成";
	
	var initAffirmPasswordText = "请再输入一次您的新密码";
	//密码规则
	var passwordcheck=/^(?![0-9]+$)(?![a-zA-Z]+$)[0-9a-zA-Z]{6,16}$/;
	
//	$('#password').on("click",function(){
//		$('#password').attr("type","password");
//	})
	
	$('#password').focus(function(){
//		 var $this = $(this);
		//if($.trim($(".newPwdText").text()) == initNewPasswordText){
//			$(".newPwdText").text("");
		//}
		changecolor.message_foucs("#secondSpan",initNewPasswordText,"block");
	});
	
	
	
	$('#password').blur(function(){
		 var $this = $(this);
//		if(!$.trim($(".newPwdText").text()) && !$.trim($(".password").val())){
//			$(".newPwdText").text(initNewPasswordText);
//		}
		if($this.val()==null||$this.val()==""){
			changecolor.error("#secondSpan","密码不能为空","block");
		}else if(!$this.val().match(passwordcheck)){
			changecolor.error("#secondSpan","密码必须由6-16位数字和字母组成","block");
		}else{
		changecolor.message_blus("#secondSpan","","none");
		}
	});
	
//	$(".newPwdText").live("click",function(){
//		$(".password").focus();
//	});
	
	
//	$('#checkPassword').on("click",function(){
//		$('#checkPassword').attr("type","password");
//	})
	
	$('#checkPassword').focus(function(){
		 var $this = $(this);
//		if($.trim($(".affirmPwdText").text()) == initAffirmPasswordText){
//			$(".affirmPwdText").text("");
//		}
		 changecolor.message_foucs("#checkback4",initAffirmPasswordText,"block");
	});
	
	$('#checkPassword').blur(function(){
		 var $this = $(this);
//		 if(!$.trim($(".affirmPwdText").text()) && !$.trim($(".affirmPassword").val())){
//			$(".affirmPwdText").text(initAffirmPasswordText);
//		 }
		 if($this.val()==null||$this.val()==""){
			 changecolor.error("#checkback4","确认密码不能为空","block");
		 }else if($('#password').val()==null||$('#password').val()==""){
			 changecolor.error("#checkback4","请先填写新密码","block");
		 }else if($('#password').val()!=$this.val()){
			 changecolor.error("#checkback4","两次密码不一致","block");
		 }else{
			 changecolor.message_blus("#checkback4","","none");
		 }
	});
	
//	$(".affirmPwdText").live("click",function(){
//		$(".affirmPassword").focus();
//	});
	
	//设置密码
	$("#resetpassword").on("click",function(){
		var $this = $(this);
		//var status = $this.attr("status");  
		var password = $.trim($("#password").val());
//		var affirmPassword = $.trim($("#checkPassword").val());
//		var checkPhone = $.trim($("#checkPhone").val());
		var token = $.trim($(".token").val());
		var mobile = $.trim($("#phone").val());
		//if(status == "true"){
//			$this.attr("status",false);
//			$this.text("正在处理中");
//			if(password == null || password.length <= 0){
//				$.alertTip($(".password"),"请输入新密码 !");
//				$this.attr("status",true);
//				$this.text("确定");
//				$(".password").focus();
//				return ;
//			}else if(!/^[0-9a-zA-Z]{6,16}$/.test(password) || password.length != $.trim(password).length || !/[0-9]{1,}/.test(password) || !/[a-zA-Z]{1,}/.test(password) || password.length < 6 || password.length > 20){
//				$.alertTip($(".password"),"密码必须是6-16位数字和字母组成 !");
//				$this.attr("status",true);
//				$this.text("确定");
//				$(".password").focus();
//				return ;
//			}else if(initAffirmPasswordText == affirmPassword || affirmPassword == null || affirmPassword.length <= 0){
//				$.alertTip($(".affirmPassword"),"请输入确认密码 !");
//				$this.attr("status",true);
//				$this.text("确定");
//				$(".affirmPassword").focus();
//				return ;
//			}else if(password != affirmPassword){
//				$.alertTip($(".affirmPassword"),"确认密码与新密码不一致 !");
//				$this.attr("status",true);
//				$this.text("确定");
//				$(".affirmPassword").focus();
//				return ;
//			}
		if($("#firstSpan").text()==""&&$("#checkback2").text()=="true"&&$("#secondSpan").text()==""&&$("#password").text()==$("#checkPassword").text()){
			$.post(basepath+"update_password",{password:password,mobile:mobile,token:token,ajax:1},function(data){
				if(data.success){
					if(data.message!="" && data.message!=null){
						//$.alertTip($("#password"),"密码设置失败 !");
						showMsgDialog("提示","密码设置失败 !");
					}else{
						//window.location.href=basepath+"forgetpw?step=3";
						showMsgDialog("提示","恭喜你，密码重置成功 !");
						window.location.href=basepath+"/user/account";
					}
				}else{
					//$.alertTip($(this),"系统错误......");
					showMsgDialog("提示","系统错误.....");
				}
				//$this.attr("status",true);
				//$this.text("确定");
			},"json");
		}else{
			showMsgDialog("提示","请按提示输入正确格式的信息！");
		}
//		}
	});
	
	//获取设置密码页面form
	var setPasswordForm = $("#resetForm");
	
	//回车事件
	setPasswordForm.on("keypress", "input", function(event){
		if(event.keyCode == 13){
			$("#resetpassword").click();
			return false;
		}
	});
	
	//初始化定义设置密码页面的光标位置
	//$(".password").focus();
});