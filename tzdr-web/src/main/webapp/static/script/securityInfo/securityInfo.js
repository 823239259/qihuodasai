var waittime=60;
var  flag = true;
var timeid;
$(document).ready(function(){
	$(".fr a").removeClass("dja");
	$(".account").addClass("dja");
	$("#oAccount").find("a").removeClass("on");
	$('.navlist li a').removeClass('on');
	$("#nav_my").addClass("on");
	$("#security").parent().addClass("on");
	
	
	 $("#validateemailcode").click(function(){
		
		 if(!flag){
			 return;
		 }
		  var email=$("#email").val();
		  if(email==""){
			  $.alertTip($("#email"),"请输入邮箱 ");
			  return;
		  }
		  if(!isEmail(email)){
			  $.alertTip($("#email"),"邮箱格式不对 ");
			  return;
		  }
		  $.post(basepath+"securityInfo/sendEmailCode",{"email":email},function(data){  
				if(data.success){
					if(data.message!="" && data.message!=null){
						if(data.message=="success"){
							countReset($("#validateemailcode"),60,60);
						}else if(data.message=="exsit"){
							$.alertTip($("#email"),"邮箱存在 ");
							
						}
				    }	
				}
			},"json"); 
		}); 
	 
	 //重新发送邮箱验证码
	 $("#oldemailvalidatecode").click(function(){
		 if(!flag){
			 return;
		 }
		 toupdateEmail();
		}); 
	 
	 $("#fogetvalidatecode").click(function(){
		 if(!flag){
			 return;
		 }
		  $.post(basepath+"securityInfo/sendPhoneCode?type=draw",function(data){  
				if(data.success){
					if(data.message!="" && data.message!=null){
						if(data.message=="success"){
							countReset($("#fogetvalidatecode"),60,60);
						}
				    }	
				}
			},"json"); 
		}); 
	 
	 //忘记提现密码
	 $("#forgetdrawPwd").click(function(){
		 //showPageDialog(basepath+"securityInfo/toForgetdrawPwd","忘记密码",600,300);
		 $("#updatefogetcode").css("display","block"); 
		 $("#div_Mask").css("display","block");
		 clearCount();
		  $.post(basepath+"securityInfo/sendPhoneCode?type=draw",function(data){  
				if(data.success){
					if(data.message!="" && data.message!=null){
						if(data.message=="success"){
							countReset($("#fogetvalidatecode"),60,60);
						}
				    }	
				}
			},"json"); 
		}); 
	 
	  $("#nextfogetdrawpwdBtn").click(function(){
		  var code=$("#drawcode").val();
		  if(code==""){
			  $.alertTip($("#drawcode"),"请输入验证码 ");
			  return;
		  }
		  
		  $.post(basepath+"securityInfo/checkBingphone",{"code":code},function(data){  
				if(data.success){
					if(data.message!="" && data.message!=null){
						if(data.message=="success"){
							 $("#updatefogetcode").css("display","none"); 
							 $("#forgetmoneypwddiv").css("display","block"); 
						}else if(data.message=="diffcode"){
							 $.alertTip($("#drawcode"),"输入的验证码错误 ");
							  return;
						}else if(data.message=="timeout"){
							 $.alertTip($("#drawcode"),"验证码已失效，请重新获取 ");
							  return;
						}
				    }	
				}
			},"json"); 
	  });
	 
	  $("#updateforgetmoneypwdbtn").click(function(){
		   var moneypwd=$("#forgetdrawmoneypwd").val();
			 var aginmoneypwd=$("#forgetaginmoneypwd").val();
			 if(moneypwd==""){
				 $.alertTip($("#forgetdrawmoneypwd"),"提现密码不能为空 ");
				 return;
			 }else if(aginmoneypwd==""){
				 $.alertTip($("#forgetaginmoneypwd"),"确认密码不能为空 ");
				 return;
			 }else  if(!checkpwd(moneypwd)){
				 $.alertTip($("#forgetdrawmoneypwd"),"请输入6-16位数字和字母组成的密码 ");
				 return;
			 }else if(!checkpwd(aginmoneypwd)){
				 $.alertTip($("#forgetaginmoneypwd"),"请输入6-16位数字和字母组成的密码 ");
				 return;
			 }else if(moneypwd!=aginmoneypwd){
				 $.alertTip($("#forgetdrawmoneypwd"),"提现密码和确认密码不一致");
				 return;
			 }
			 $.post(basepath+"securityInfo/updateMoneyPwd",{"moneypassword":moneypwd,"aginmoneypassword":aginmoneypwd},function(data){  
					if(data.success){
						if(data.message=="diffpwd"){
							 $.alertTip($("#forgetdrawmoneypwd"),"提现和确认密码不一致");
						}else if(data.message=="loginpwd"){
							 $.alertTip($("#forgetdrawmoneypwd"),"提现密码不能和登陆密码相同");
						}else if(data.message=="success"){
							alert("提现密码修改成功");
							window.location.href=basepath+"securityInfo/secInfo";
							//$("#forgetmoneypwddiv").css("display","none");
						}
						
					}
				},"json"); 
	  });
	  
	  
	 //重新发送邮箱验证码
	 $("#randomemailcodes").click(function(){
		 var newemail=$("#newemail").val();
		 if(newemail==""){
			 $.alertTip($("#newemail"),"邮箱不能为空 ");
			 return;
		 }else if(!isEmail(newemail)){
			 $.alertTip($("#newemail"),"邮箱格式不对 ");
			 return;
			 
		 }
		
		 if(!emailflag){
			 return;
		 }
		//发送邮件验证码
  	   $.post(basepath+"securityInfo/sendEmailCode",{"email":newemail},function(data){  
  			if(data.success){
  				if(data.message!="" && data.message!=null){
  					if(data.message=="success"){
  						emailcountReset($("#randomemailcodes"),60,60);
  					}else if(data.message=="exsit"){
  						 $.alertTip($("newemail"),"邮箱存在 ");
  						 return;
  						
  					}
  			    }	
  			}
  		 },"json"); 
		}); 

	  //再次发送验证码
	  $("#validatecode").click(function(){
		  if(!flag){
			  return;
		  }
		  $.post(basepath+"securityInfo/sendPhoneCode?type=phone",function(data){  
				if(data.success){
					if(data.message!="" && data.message!=null){
						if(data.message=="success"){
							countReset($("#validatecode"),60,60);
					}
				  }	
				}
			},"json"); 
		});
	  
	  
	  $("#newvalidatecode").click(function(){
		  var $this = $(this);
		  var newphone=$("#newphone").val();
		  var yzmCodeObject = $("#yzmStrCode");
		  var yzmCode = yzmCodeObject == null || yzmCodeObject.length <= 0? null : $.trim(yzmCodeObject.val());
		  if(newphone==""){
			  $.alertTip($("#newphone"),"请输入手机号 ");
			  if(yzmCodeObject != null && yzmCodeObject.length > 0){
					$("#yzmStrCode").parent().find("a img").attr("src",basepath+"validate.code?1=" + Math.random()*10000);
			  }
			  return;
		  }
		  if(!isMobile(newphone)){
			  $.alertTip($("#newphone"),"请输入正确的手机号 ");
			  if(yzmCodeObject != null && yzmCodeObject.length > 0){
				  $("#yzmStrCode").parent().find("a img").attr("src",basepath+"validate.code?1=" + Math.random()*10000);
			  }
			  return;
		  }
		  
		  if(yzmCodeObject != null && yzmCodeObject.length > 0 && (yzmCode == null || yzmCode.length <= 0)){
			  $.alertTip($("#yzmStrCode"),"校验码错误 ");
			  $("#yzmStrCode").parent().find("a img").attr("src",basepath+"validate.code?1=" + Math.random()*10000);
			  return;
		  }
		  
		  if(!phoneflag){
			  if(yzmCodeObject != null && yzmCodeObject.length > 0){
				  $("#yzmStrCode").parent().find("a img").attr("src",basepath+"validate.code?1=" + Math.random()*10000);
			  }
			  return;
		  }
		  $.post(basepath+"securityInfo/sendPhoneCode?type=phone",{"mobile":newphone,"yzmCode":yzmCode},function(data){  
				if(data.success){
					var yzmStrLiObject = $("#yzmStrLi");
					if(data.message!="" && data.message!=null){
						if(data.message=="success"){
							phonecountReset($("#newvalidatecode"),60,60);
							if(yzmStrLiObject != null && yzmStrLiObject.length > 0){
								yzmStrLiObject.remove();
							}
					   }else if(data.message=="exsit"){
						   $.alertTip($("#newphone"),"手机号码已存在 ");
							return;
					   }else if(data.message=="yzmStrError"){
						   if(data.data.isNeedSendCode && yzmStrLiObject != null && yzmStrLiObject.length <= 0){
							   var yzmStr = "<div class='srk' id='yzmStrLi'>";
								yzmStr += "<span class='label'>校验码：</span><input class='au-ipt mglt30'  id='yzmStrCode' maxlength='5' name='yzmStrCode' type='text' style='width:85px'>";
								yzmStr += "&nbsp;&nbsp;&nbsp;&nbsp;";
								yzmStr += "<a href='javascript:void(0);'><img src='validate.code' id='refresh_code' style='width: 85px;height: 29px;' class='rg_l_codeimg'></a>";
								yzmStr += "</div>";
								$this.parent().after(yzmStr);
								$.alertTip($("#yzmStrCode"),"校验码错误，请输入校验码 ");
								return;
						   }else{
							   $.alertTip($("#yzmStrCode"),"校验码错误 ");
							   $("#yzmStrCode").parent().find("a img").attr("src",basepath+"validate.code?1=" + Math.random()*10000);
								return;
						   }
					   }
				  }	
				}
			},"json"); 
		});
	  
	  
	//刷新注册码
		$("#refresh_code").live("click",function(){
			var $this = $(this);
			$this.attr("src",basepath+"validate.code?1=" + Math.random()*10000);
		});
	  
	  //弹出修改密码
	 $("#toupdatepwd").click(function(){
		 $("#div_Mask").show();
		 $("#oldpwd").val("");
		 $("#newpwd").val("");
		 $("#cnewpwd").val("");
		 //showPageDialog(basepath+"/securityInfo/toUpdatePwd","修改密码",500,300)
		 $("#updatepwddiv").css("display","block"); 
		});
	 
	 
	  //修改密码
	 $("#doupdatepwd").click(function(){
		 var pwd=$("#oldpwd").val();
		 var newpwd=$("#newpwd").val();
		 var cnewpwd=$("#cnewpwd").val();
		 if(pwd==""){
			 $.alertTip($("#oldpwd"),"请输入原始密码");
			  return; 
		 }else if(newpwd==""){
			 $.alertTip($("#newpwd"),"请输入新密码");
			  return; 
		 }else if(cnewpwd==""){
			 $.alertTip($("#cnewpwd"),"请输入确认密码");
			  return; 
		 }
		 if(!checkpwd(pwd)){
				$.alertTip($("#oldpwd"),"请输入6-16位数字和字母组成的密码");
				return;
		 }else if(!checkpwd(newpwd)){
			  $.alertTip($("#newpwd"),"请输入6-16位数字和字母组成的新密码");
				return;
		 }else  if(!checkpwd(cnewpwd)){
			  $.alertTip($("#cnewpwd"),"请输入6-16位数字和字母组成的确认密码");
				return;
		 }else if(cnewpwd!=newpwd){
			   $.alertTip($("#cnewpwd"),"两次密码输入不一致");
				return;
		 }else{
			 	$(".cnewpwd").text("");
				$(".cnewpwd").removeClass("color999");
				$(".cnewpwd").removeClass("colorf60");
		 }
		 
		 $.post(basepath+"securityInfo/updatePwd",{"password":pwd,"newpassword":newpwd,"againNewpassword":cnewpwd},function(data){  
				if(data.success){
					if(data.message=="diffpwd"){
						$.alertTip($("#oldpwd"),"密码错误");
					}else if(data.message=="samepwd"){
						$.alertTip($("#newpwd"),"新密码不能和提现密码相同");
					}else if(data.message=="diffagainpwd"){
						$.alertTip($("#newpwd"),"新密码和确认密码不一致");
					}else if(data.message=="true"){
						updateMockAccount(newpwd);
						alert("密码修改成功");
						$("#updatepwddiv").css("display","none");
						$("#div_Mask").css("display","none");
					}else{
						$(".newpwd").text("");
						$(".newpwd").removeClass("colorf60");
					}
				}
			},"json"); 
	});
	  //重新设置取款密码
	 $("#resetmoneypwdbtn").click(function(){
		 var pwd=$("#olddrawmoneypwd").val();
		 var newpwd=$("#newdrawmoneypwd").val();
		 var cnewpwd=$("#agindrawmoneypwd").val();
		 if(pwd==""){
			 $.alertTip($("#olddrawmoneypwd"),"原提现密码不能为空");
				return;
		 }else if(newpwd==""){
			 $.alertTip($("#newdrawmoneypwd"),"新提现密码不能为空");
				return;
		 }else if(cnewpwd==""){
			 $.alertTip($("#agindrawmoneypwd"),"确认提现密码不能为空");
				return;
		 }
		 if(!checkpwd(pwd)){
				$.alertTip($("#olddrawmoneypwd"),"请输入6-16位字母和数字组成的密码");
				return;
		 }else if(!checkpwd(newpwd)){
				$.alertTip($("#newdrawmoneypwd"),"请输入6-16位字母和数字组成的新密码");
				return;
		 }else if(!checkpwd(cnewpwd)){
			    $.alertTip($("#agindrawmoneypwd"),"请输入6-16位字母和数字组成的确认密码");
				return;
		 }else if(cnewpwd!=newpwd){
			   $.alertTip($("#agindrawmoneypwd"),"新密码和确认密码不一致");
				return;
		 }
		 
		 $.post(basepath+"securityInfo/resetMoneyPwd",{"moneypassword":newpwd,"aginmoneypassword":cnewpwd,"olddrawmoneypwd":pwd},function(data){  
				if(data.success){
					if(data.message=="diffpwd"){
						 $.alertTip($("#olddrawmoneypwd"),"原提现密码错误");
						return;
					}else if(data.message=="diffaginpwd"){
						 $.alertTip($("#olddrawmoneypwd"),"新密码和确认密码不一致");
						return;
					}else if(data.message=="loginpwd"){
						$.alertTip($("#olddrawmoneypwd"),"提现密码不能和登陆密码相同");
						return;
						
					}else if(data.message=="success"){
						alert("密码修改成功");
						//$("#updatemoneypwddiv").css("display","none");
						window.location.href=basepath+"securityInfo/secInfo";
					}
				}
			},"json"); 
	});
	 
	 
	 //到修改提现密码
	 $("#moneypwd").click(function(){
		 $("#moneypwddiv").css("display","block"); 
		 $("#div_Mask").show();
		 $("#drawmoneypwd").val("");
		 $("#aginmoneypwd").val("");
	 });
	 
	 //设置提现密码
	 $("#moneypwdbtn").click(function(){
		 var moneypwd=$("#drawmoneypwd").val();
		 var aginmoneypwd=$("#aginmoneypwd").val();
		 if(moneypwd==""){
			 $.alertTip($("#drawmoneypwd"),"请输入提现秘密");
			 return;
		 }else if(aginmoneypwd==""){
				 $.alertTip($("#aginmoneypwd"),"确认密码不能为空");
				 return;
		 }
		 if(!checkpwd(moneypwd)){
			 $.alertTip($("#drawmoneypwd"),"请输入6-16位数字和字母组成的密码");
			 return;
		 }else if(!checkpwd(aginmoneypwd)){
			 $.alertTip($("#aginmoneypwd"),"请输入6-16位数字和字母组成的密码");
			 return;
		 }else if(moneypwd!=aginmoneypwd){
			 $.alertTip($("#aginmoneypwd"),"确认密码和提现密码不一致");
			 return;
		 }
		 
		 $.post(basepath+"securityInfo/updateMoneyPwd",{"moneypassword":moneypwd,"aginmoneypassword":aginmoneypwd},function(data){  
				if(data.success){
					if(data.message=="diffpwd"){
						 $.alertTip($("#aginmoneypwd"),"密码不一致");
						 return;
						
					}else if(data.message=="loginpwd"){
						 $.alertTip($("#aginmoneypwd"),"提现密码不能和登陆密码相同");
						 return;
						
					}
					if(data.message=="success"){
						/*var html="<a class='color34b3e0 font14 '>已设置</a>"+
							"<a class='color34b3e0 font14 mglt20' id='resetmoneypwd' href='javascript:resetMoneyPwd();'>立即修改</a>";
						$("#moneypwdset").html(html);
						showMsgDialog("提示","提现密码修改成功");*/
						window.location.href=basepath+"securityInfo/secInfo";
						$("#moneypwddiv").css("display","none");
					}
					
				}
			},"json"); 
	 });
	  
	 $("#toupdatephone").live("click",function(event){
		 event.stopPropagation();
		   updateMobile();
		});
	 
		$('.uc_sidebar').find("div.uc_nav ul a").each(function(){
			$(this).removeClass('on');
	 	});
		$("#security").addClass('on');
	 
 });

//到重新设置取款密码页面
function resetMoneyPwd(){
	 $("#updatemoneypwddiv").css("display","block"); 
	 $("#div_Mask").show();
	 $("#olddrawmoneypwd").val("");
	 $("#newdrawmoneypwd").val("");
	 $("#agindrawmoneypwd").val("");
}


//立即开始实名认证
function authcard(){
	$("#cardname").val("");
	$("#idcard").val("");
	var obj=new MaskControl();
	var cardcontent=$("#idcardDiv").html(); 
	$("#idcardDiv").attr("display","block"); 
	$("#cardname").val("");
	$("#idcard").val("");
	$("#idcardDiv").css("display","block");
	$("#div_Mask").show();
	//var url=basepath+"static/toBindingPhone.jsp";
	//showPageDialog(url,'rrr',677,477);
}
function updateMockAccount(password){
	$.ajax({
		url:basepath+"/mock/trade/updateMock",
		type:"post",
		data:{
			password:password
		},
		success:function(){}
	});
}



//验证身份证
function validateCard(){
	
	var name=$("#cardname").val();
	var card=$("#idcard").val();
	if(name==""){
		$("#cardname").focus(); 
		 $.alertTip($("#cardname"),"姓名不能为空");
		
		return;
	}
	if(card==""){
		$("#idcard").focus(); 
		 $.alertTip($("#idcard"),"身份证号码不能为空");
			
		return;
	}
	if(checkCard(card)==false){
		$("#idcard").focus(); 
		 $.alertTip($("#idcard"),"身份证号码数据不对");
		
		return;
	}
	 $("#validating").text("正在验证");
	 $.post(basepath+"securityInfo/validateCard",{"name":name,"card":card},function(data){  
			if(data.success){
				$("#validating").text("提交");
				if(data.message!="" && data.message!=null){
					 if(data.message=="false"){
						 $.alertTip($("#idcard"),"身份证信息错误");
					
					}else if(data.message=="maxnum"){
						$.alertTip($("#idcard"),"身份证验证失败超过3次,请与客服联系");
					}else if(data.message=="exsit"){
						 $.alertTip($("#idcard"),"身份证已经存在");
						
					}else if(data.message=="haveRealName"){
						 $.alertTip($("#idcard"),"已是实名状态,不能重复实名");
						
					}else{
						/*$("#idcardDiv").css("display","none");
						var hml="<a class='colorred font14 fontarail '>"+data.message+
						"</a><a class='color34b3e0 font14 mglt10' href='javascript:toupdateCardFile();'>上传照片</a>";
						$("#cardInfo").html(hml);*/
						$("#div_Mask").hide();
						alert('验证成功');
						window.location.href=basepath+"securityInfo/secInfo";
						
					}
				}
				
			}
		},"json"); 

}

//到实名相片上传
function toupdateCardFile(){
	$("#cardInfofile").css("display","block"); 
	$("#div_Mask").show();
	$("#idcard_front").val("");
	$("#idcardbackfile").val("");
	$("#idcardpathfile").val("");
	$("#idcardfrontfile").val("");
	$("#idcardbackfile").val("");
	$("#idcardpathfile").val("");
	$("#idcard_frontspan").html("");
	$("#idcard_backspan").html("");
	$("#idcardspan").html("");
}

//上传身份证图片
function uploadCardFile(){
	//var formdata=$("#uploadCard").serialize();
	var idcardFront=$("#idcardfrontfile").val();
	var idcardBack=$("#idcardbackfile").val();
	var idcardPath=$("#idcardpathfile").val();
	if(idcardFront==""){
		alert("身份证正面不能为空");
		return;
	}
	if(idcardBack==""){
		alert("身份证反面不能为空");
		return;
	}
	
	if(idcardPath==""){
		alert("手持身份证不能为空");
		return;
	}
	$.post(basepath+"securityInfo/realNameAuth",{"idcardFront":idcardFront,"idcardPath":idcardPath,"idcardBack":idcardBack},function(data){  
			if(data.success){
				$("#div_Mask").hide();
				if(data.message!="" && data.message!=null){
					alert("身份证上传成功");
					$("#cardInfofile").css("display","none");
					/*var hml="<a class='colorred font14 fontarail '>"+data.message+
					"</a><a class='color34b3e0 font14 mglt10'>待审核</a>";
					$("#cardInfo").html(hml);*/
					window.location.href=basepath+"securityInfo/secInfo";
			  }	
			}
		},"json"); 
}

//到邮箱绑定页面
function toBandingPhone(){
	$("#bandingEmail").css("display","block");
	$("#div_Mask").show();
}

//到修改手机号码
function updateMobile(){
	$("#div_Mask").show();
	$("#updateMobile").css("display","block");
	var phonecode = $("#phonecode");
	if(phonecode != null && phonecode.length > 0){
		//初始化定义光标位置
		$("#phonecode").focus();
	}
	setbtnvalue("validatecode");
	clearCount();
	countReset($("#validatecode"),60,waittime);
	$.post(basepath+"securityInfo/sendPhoneCode?type=phone",function(data){  
		if(data.success){
			if(data.message!="" && data.message!=null){
				if(data.message=="success"){
					alert("已成功发送验证码");
			}
		  }	
		}
	},"json"); 
	
}

function setbtnvalue(id){
	$("#"+id).val("");
}

//到手机验证下一步
function tonextvalidatephonecode(){
	var code=$("#phonecode").val();
	if(code==""){
		$.alertTip($("#phonecode"),"请输入验证码 ");
		return;
	}
	$.post(basepath+"securityInfo/checkBingphone",{"code":code},function(data){  
		if(data.success){
			if(data.message!="" && data.message!=null){
				if(data.message=="success"){
					$("#updateMobile").css("display","none");
					$("#updatenextMobile").css("display","block");
			   }else if(data.message=="timeout"){
				   $.alertTip($("#phonecode"),"验证码已失效，请重新获取验证码");
				   
			   }else if(data.message=="diffcode"){
				   $.alertTip($("#phonecode"),"验证码错误 ");
				
				}
		    }	
		}
	},"json"); 
}

//绑定邮箱
function bindEmail(){
	 var email=$("#email").val();
	  var emailcode=$("#emailcode").val();
	  if(email==""){
		  $.alertTip($("#email"),"邮箱不能为空 ");
		  return;
	  }
	  if(!isEmail(email)){
		  $.alertTip($("#email"),"邮箱格式错误 ");
		  return;
	  }
	 
	  if(emailcode==""){
		  $.alertTip($("#emailcode"),"邮箱验证码不能为空 ");
		 
		  return;
	  }
		$.post(basepath+"securityInfo/bindingEmail",{"email":email,"emailcode":emailcode},function(data){  
			if(data.success){
				if(data.message!="" && data.message!=null){
					if(data.message=="exsit"){
						alert("邮箱存在");
				   }else if(data.message=="timeout"){
					   $.alertTip($("#emailcode"),"邮箱验证码已失效，请重新获取验证码");
				   }else if(data.message=="diffcode"){
					   $.alertTip($("#emailcode"),"邮箱验证码错误");
					}else if(data.message=="emailerror"){
					   $.alertTip($("#emailcode"),"邮箱输入错误");
					}else {
						$("#div_Mask").hide();
						$("#bandingEmail").css("display","none");
						/*var html="<a class='colorred fontarail '>"+data.message+"</a><a class='color34b3e0 font14 mglt20' href='javascript:toupdateEmail();'>修改</a>";
						$("#emaildivinfo").html(html);*/
						window.location.href=basepath+"securityInfo/secInfo";
					}
			    }else {
					$("#div_Mask").hide();
					$("#bandingEmail").css("display","none");
					/*var html="<a class='colorred fontarail '>"+data.message+"</a><a class='color34b3e0 font14 mglt20' href='javascript:toupdateEmail();'>修改</a>";
					$("#emaildivinfo").html(html);*/
					window.location.href=basepath+"securityInfo/secInfo";
				}	
			}
		},"json"); 
}


//修改邮箱到下一步
function donextupdateEmail(){
	var url=basepath+"securityInfo/checkEmailCode";
	var code=$("#oldemailcode").val();
	if(code==""){
		 $.alertTip($("#oldemailcode"),"请输入验证码");
		
		return;
	}
	clearCount();
	$.ajaxSetup({ async :false});
	$.post(url,{"emailcode":code}, function(data,status){  
		if(data.success){
			if(data.message!="" && data.message!=null){
			  if(data.message=="timeout"){
				  $.alertTip($("#oldemailcode"),"验证码已失效，请重新获取验证码");
					
			   }else if(data.message=="diffcode"){
				   $.alertTip($("#oldemailcode"),"验证码错误");
					
				}else if(data.message=="success"){
					$("#sendoldMobile").css("display","none");
					$("#updatebandingEmail").css("display","block");
					$("#randomemailcodes").removeAttr("disabled");
					$("#randomemailcodes").html("获取验证码");
					flag=true;
					
				}
		    }	
		}
	},"json"); 
}




//关闭div
function closeDiv(id){
	$("#div_Mask").hide();
	$("#"+id).css("display","none"); 
}






//更新手机号
function updatephone(){
	 var newmobile=$("#newphone").val();
	 if(newmobile==""){
		 $.alertTip($("#newphone"),"手机号码不能为空 ");
		  return;
	 }
	 
	  if(!isMobile(newmobile)){
		  $.alertTip($("#newphone"),"手机号码错误 ");
		  return;
	  }
	var url=basepath+"securityInfo/updatePhone";
	var newmobile=$("#newphone").val();
	var mobilecode=$("#newcode").val();
	if(mobilecode==""){
		 $.alertTip($("#newcode"),"验证码不能为空 ");
		 return;
	}
	if(newmobile==""){
		 $.alertTip($("#newphone"),"手机号码不能为空 ");
		 return;
	}
	$.post(url,{"newmobile":newmobile,"mobilecode":mobilecode},function(data){ 
		
		if(data.success){
			if(data.message!="" && data.message!=null){
				if(data.message=="exsit"){
					 $.alertTip($("#newphone"),"手机号码已经存在 ");
			   }else if(data.message=="timeout"){
				   $.alertTip($("#newcode"),"验证码已失效，请重新获取验证码 ");
			   }else if(data.message=="diffcode"){
				   $.alertTip($("#newcode"),"验证码错误 ");
				}else if(data.message=="success"){
					alert("成功更新手机号");
					/*$("#div_Mask").hide();
					$("#updatenextMobile").css("display","none");
					*/
					window.location.href=basepath+"securityInfo/secInfo";
				}
		    }	
		}
	},"json"); 
}




//到邮箱修改页面
function toupdateEmail(){
	$("#div_Mask").css("display","block");
	$("#sendoldMobile").css("display","block");
	clearCount();
	//countReset($("#validatecode"),60,waittime);
	sendEmailcode("","oldemailvalidatecode");
}

//发送邮件验证码
function sendEmailcode(email,id){
	  $.post(basepath+"securityInfo/sendEmailCode",{"email":email},function(data){  
			if(data.success){
				if(data.message!="" && data.message!=null){
					if(data.message=="success"){
						countReset($("#"+id),60,60);
					}else if(data.message=="exsit"){
						alert("邮箱存在");
					}
			    }	
			}
		},"json"); 
}



//修改邮箱
function updateEmail(){
	var url=basepath+"securityInfo/bindingEmail";
	 var email=$("#newemail").val();
	  var emailcode=$("#newemailcode").val();
	  if(email==""){
		  $.alertTip($("#newemail"),"邮箱不能为空 ");
		  return;
	  }
	  if(!isEmail(email)){
		  $.alertTip($("#newemail"),"邮箱格式错误 ");
		 
		  return;
	  }
	  if(emailcode==""){
		  $.alertTip($("#newemailcode"),"邮箱验证码不能为空 ");
		  
		  return;
	  }
	  
	  $.post(url,{"email":email,"emailcode":emailcode},function(data){  
			if(data.success){
				if(data.message!="" && data.message!=null){
					if(data.message=="exsit"){
						  $.alertTip($("#newemail"),"邮箱存在 ");
						 
					}else if(data.message=="timout"){
						 $.alertTip($("#newemailcode"),"邮箱验证码已失效，请重新获取验证码");
						
					}else if(data.message=="diffcode"){
						 $.alertTip($("#newemailcode"),"邮箱验证码错误 ");
						
					}else if(data.message=="emailerror"){
					   $.alertTip($("#newemailcode"),"邮箱输入错误");
					}else {
						//$("#updatebandingEmail").css("display","block");
						alert("修改成功");
						window.location.href=basepath+"/securityInfo/secInfo";
					}
			    }else {
					//$("#updatebandingEmail").css("display","block");
					alert("修改成功");
					window.location.href=basepath+"/securityInfo/secInfo";
				}	
			}
		},"json"); 

	
}




checkpwd=function(password){
	if(password == null || password.length <= 0 || !/^[0-9a-zA-Z]{6,16}$/.test(password) || password.length != $.trim(password).length ){
		
		return false;
	}else if(!/[0-9]{1,}/.test(password) || !/[a-zA-Z]{1,}/.test(password)){
		
		return false;
	}else if(password.length < 6 || password.length > 20){
	
		return false;
	}
	return true;
}



isEmail=function(email){
	return email&&/^[0-9a-zA-Z_\-]+@[0-9a-zA-Z_\-]+\.\w{1,5}(\.\w{1,5})?$/.test(email);
}

isMobile=function(mobile){
	return mobile&&/^1[3-9]\d{9}$/.test(mobile);
},
//验证身份证
checkCard = function(card) {
    //是否为空
    if(card === '') {
        return false;
    }
    //校验长度，类型
    if(isCardNo(card) === false) {
        return false;
    }

    //校验生日
    if(checkBirthday(card) === false) {
        return false;
    }
    //检验位的检测
    if(checkParity(card) === false)
    {
        return false;
    }
    return true;
};


//检查号码是否符合规范，包括长度，类型
isCardNo = function(card) {
    //身份证号码为15位或者18位，15位时全为数字，18位前17位为数字，最后一位是校验位，可能为数字或字符X
    var reg = /(^\d{15}$)|(^\d{17}(\d|X)$)/;
    if(reg.test(card) === false) {
        return false;
    }
    return true;
};



//检查生日是否正确
checkBirthday = function(card) {
    var len = card.length;
    //身份证15位时，次序为省（3位）市（3位）年（2位）月（2位）日（2位）校验位（3位），皆为数字
    if(len == '15') {
        var re_fifteen = /^(\d{6})(\d{2})(\d{2})(\d{2})(\d{3})$/; 
        var arr_data = card.match(re_fifteen);
        var year = arr_data[2];
        var month = arr_data[3];
        var day = arr_data[4];
        var birthday = new Date('19'+year+'/'+month+'/'+day);
        return verifyBirthday('19'+year,month,day,birthday);
    }
    //身份证18位时，次序为省（3位）市（3位）年（4位）月（2位）日（2位）校验位（4位），校验位末尾可能为X
    if(len == '18') {
        var re_eighteen = /^(\d{6})(\d{4})(\d{2})(\d{2})(\d{3})([0-9]|X)$/;
        var arr_data = card.match(re_eighteen);
        var year = arr_data[2];
        var month = arr_data[3];
        var day = arr_data[4];
        var birthday = new Date(year+'/'+month+'/'+day);
        return verifyBirthday(year,month,day,birthday);
    }
    return false;
};

//校验日期
verifyBirthday = function(year,month,day,birthday) {
    var now = new Date();
    var now_year = now.getFullYear();
    //年月日是否合理
    if(birthday.getFullYear() == year && (birthday.getMonth() + 1) == month && birthday.getDate() == day) {
        //判断年份的范围（3岁到100岁之间)
        var time = now_year - year;
        if(time >= 3 && time <= 100) {
            return true;
        }
        return false;
    }
    return false;
};

//校验位的检测
checkParity = function(card) {
    //15位转18位
    card = changeFivteenToEighteen(card);
    var len = card.length;
    if(len == '18') {
        var arrInt = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2); 
        var arrCh = new Array('1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'); 
        var cardTemp = 0, i, valnum; 
        for(i = 0; i < 17; i ++) { 
            cardTemp += card.substr(i, 1) * arrInt[i]; 
        } 
        valnum = arrCh[cardTemp % 11]; 
        if (valnum == card.substr(17, 1)) {
            return true;
        }
        return false;
    }
    return false;
};


//倒数计时间
function emailcountReset(obj,tt,wait){
	obj.html(tt+"秒后重新获取");
	obj.attr("disabled","disabled");
	emailtime(obj,wait);
}

//倒数计时间
function phonecountReset(obj,tt,wait){
	obj.html(tt+"秒后重新获取");
	obj.attr("disabled","disabled");
	phonetime(obj,wait);
}


//倒数计时间
function countReset(obj,tt,wait){
	obj.html(tt+"秒后重新获取");
	obj.attr("disabled","disabled");
	time(obj,wait);
}
var emailflag=true;
var phoneflag=true;
var emailtimeid;
var phonetimeid;
//到时计时开始
function emailtime(o,wait) {  
    if (wait == 0) {  
       o.removeAttr("disabled"); 
       o.html("免费获取验证码");  
       emailflag=true;
        wait = 60;  
    } else {  
         o.attr("disabled", "disabled");  
         emailflag=false;
         o.html(wait+"秒后重新发送");  
        wait--;  
        emailtimeid=setTimeout(function() {  
        	emailtime(o,wait)  
        },  
        1000)  
    }  
} 

//到时计时开始
function phonetime(o,wait) {  
    if (wait == 0) {  
       o.removeAttr("disabled"); 
       //o.bind("click");
       o.html("免费获取验证码");  
       phoneflag=true;
        wait = 60;  
    } else {  
         o.attr("disabled", "disabled");  
        // o.unbind("click");
         phoneflag=false;
         o.html(wait+"秒后重新发送");  
        wait--;  
        phonetimeid=setTimeout(function() {  
            phonetime(o,wait)  
        },  
        1000)  
    }  
} 

//到时计时开始
function time(o,wait) {  
    if (wait == 0) {  
       o.removeAttr("disabled"); 
       //o.bind("click");
       o.html("免费获取验证码");  
       flag=true;
        wait = 60;  
    } else {  
         o.attr("disabled", "disabled");  
        // o.unbind("click");
         flag=false;
         o.html(wait+"秒后重新发送");  
        wait--;  
        timeid=setTimeout(function() {  
            time(o,wait)  
        },  
        1000)  
    }  
} 




function clearCount(){
	 emailflag=true;
	 flag=true;
	clearTimeout(timeid);  
	clearTimeout(emailtimeid);  
}

//15位转18位身份证号
changeFivteenToEighteen = function(card) {
    if(card.length == '15') {
        var arrInt = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2); 
        var arrCh = new Array('1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2'); 
        var cardTemp = 0, i;   
        card = card.substr(0, 6) + '19' + card.substr(6, card.length - 6);
        for(i = 0; i < 17; i ++) { 
            cardTemp += card.substr(i, 1) * arrInt[i]; 
        } 
        card += arrCh[cardTemp % 11]; 
        return card;
    }
    return card;
};

/* + */
/**
蒙版信息控件
用法：
1.引用 .css
2.引用 mask.js
3.调用方法
var obj=new MaskControl();
//显示蒙版提示信息
obj.show("显示的提示信息");
//隐藏蒙版提示信息
obj.hide();
//显示提示信息，并隔timeOut(1000代表1秒)自动关闭
obj.autoDelayHide=function(html,timeOut)
*/
function MaskControl(){
	this.show=function(html){
		var loader=$("#div_maskContainer");
		if(loader.length==0){
			loader=$("<div id='div_maskContainer'><div id='div_Mask' ></div><div id='div_loading' ></div></div>");
			$("body").append(loader);
		}
		self.loader=loader;
		var w=$(window).width();
		var h=$(window).height();
		var divMask=$("#div_Mask");
		divMask.css("top",0).css("left",0).css("width",w).css("height",h);
		var tipDiv=$("#div_loading");
		if(html==undefined)
		html="";
		tipDiv.html(html); 
		loader.show(); 
		var x=(w-tipDiv.width())/2;
		var y=(h-tipDiv.height())/2;
		tipDiv.css("left",x);
		tipDiv.css("top",y);
	},
	this.hide=function(){
		var loader=$("#div_maskContainer");
		if(loader.length==0) return ;
		loader.remove();
	},
	this.autoDelayHide=function(html,timeOut){
		var loader=$("#div_maskContainer");
		if(loader.length==0) {
			this.show(html);
		}
		else{
			var tipDiv=$("#div_loading");
			tipDiv.html(html);
		}
		if(timeOut==undefined) timeOut=3000;
		window.setTimeout(this.hide,timeOut);
	}
} 

function bandcard(){
	var idcard='${requestScope.idcard}';
	if(idcard!=""){
		window.location.href=basepath+"/draw/drawmoney?tab=1";
	}else{
		showMsgDialog("提示","请先进行实名认证");
	}
}
/* + */
$("#idcard_front").fileupload({
    url:basepath+"fileUpload",//文件上传地址，当然也可以直接写在input的data-url属性内
    dataType:'json',
    formData:{dir:"upload/idcard",fileType:"img",limitSize:2},//如果需要额外添加参数可以在这里添加
    done:function(e,result){
    	result = result.result;
    	var imgurl = result.url;
    	if(result.error==""){
    		alert("上传成功");
    		$("#idcardfrontfile").val(imgurl);
    		showfilename(imgurl,"idcard_frontspan");
    	}else{
    		alert(result.error);
    	}
    	
    	//document.getElementById("idcardfrontfile").value=url;
    	
    }
});

$("#idcard_back").fileupload({
    url:basepath+"fileUpload",//文件上传地址，当然也可以直接写在input的data-url属性内
    dataType:'json',
    formData:{dir:"upload/idcard",fileType:"img",limitSize:2},//如果需要额外添加参数可以在这里添加
    done:function(e,result){
    	result = result.result;
    	var imgurl = result.url;
    	//document.getElementById("idcardfrontfile").value=url;
    	
    	if(result.error==""){
    		alert("上传成功");
    		$("#idcardbackfile").val(imgurl);
    		showfilename(imgurl,"idcard_backspan");
    	}else{
    		alert(result.error);
    	}
    }
});

$("#idcard_path").fileupload({
    url:basepath+"fileUpload",//文件上传地址，当然也可以直接写在input的data-url属性内
    dataType:'json',
    formData:{dir:"upload/idcard",fileType:"img",limitSize:2},//如果需要额外添加参数可以在这里添加
    done:function(e,result){
    	result = result.result;
    	var url = result.url;
    	//document.getElementById("idcardfrontfile").value=url;
    	if(result.error==""){
    		alert("上传成功");
    		$("#idcardpathfile").val(url);
    		showfilename(url,"idcardspan");
    	}else{
    		alert(result.error);
    	}
    }
});

function showfilename(fileurl,id){
	var length=fileurl.lastIndexOf("/");
	var filename=fileurl.substring(length+1,fileurl.length);
	var name=filename.substring(filename.length-8,filename.length);
	$("#"+id).html(name);
}

function weChatAttention() {
	$("#div_Mask").css("display","block");
	$("#weChatAttention").css("display","block");
	$.get(basepath+"wechat/getWechatQrcodeTicket",function(data){
		if(data.success){
			var newUrl = data.data.url+'?ticket='+data.data.ticket;
			$('#weChatAttention_code').attr("src",newUrl);
			$("#weChatAttention .close").click(function(){
				window.location.href=basepath+"securityInfo/secInfo";
			});
			$("#weChatAttention .anniu a").click(function(){
				window.location.href=basepath+"securityInfo/secInfo";
			});
		}
	},"json"); 
}
