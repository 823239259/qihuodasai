var waittime=60;
$(document).ready(function(){
	$('#mobile').blur(function(){
		 var mobile = $("#mobile").val();
		 if(mobile==""){
			 $.alertTip($("#mobile"),"请输入手机号 ");
				return;
		 }else if(!isMobile(mobile)){
			 $.alertTip($("#mobile"),"请输入正确的手机号 ");
				return;
			}
		 checkMobile(mobile);
	});
	
	
	  //再次发送验证码
	  $("#validatecode").click(function(){
		  if(!flag){
			  return;
		  }
		  var id=$("#id").val();
		  $.post(basepath+"sendQuestionPhoneCode",{"id":id},function(data){  
				if(data.success){
					if(data.message!="" && data.message!=null){
						if(data.message=="success"){
							countReset($("#validatecode"),60,60);
					}
				  }	
				}
			},"json"); 
		});
	  var flag=true;
	  //再次发送验证码
	  $("#submitForm").click(function(){
		  var code=$("#code").val();
		  if(code==""){
			  $.alertTip($("#code"),"请输入验证码");
			  return;
		  }
		  var id=$("#id").val();
		  if (!flag){
			  return;
		  }
		  flag=false;
		  $.post(basepath+"validateCode",{"id":id,"code":code},function(data){ 
				if(data.success){
					if(data.data.fail){
						window.location.href= basepath+"sendAccount";
					}else{
						if(data.message!="" && data.message!=null){
							 $.alertTip($("#submitForm"),data.message);
							 flag=true;
						}else{
							if(data.obj){
								var action=basepath+"sendAccount?pwd="+data.obj.password;
								document.forms[0].action=action;
							}
							document.forms[0].submit();
						}
				   }
				  }	
			},"json"); 
		});
});	
	
	function submitForm() {
		//if(!$("#questionFrom").valid());
		 var sex= $('input:radio[name="sex"]:checked').val(); 
		 var question1= $('input:radio[name="question1"]:checked').val(); 
		 var question2= $('input:radio[name="question2"]:checked').val(); 
		 var question4= $('input:radio[name="question4"]:checked').val(); 
		 var question5= $('input:radio[name="question5"]:checked').val(); 
		 var question6= $('input:radio[name="question6"]:checked').val(); 
		 var question7= $('input:radio[name="question7"]:checked').val(); 
		 var question3="";
		 $('input[name="question03"]:checked').each(function(){ 
			 question3+=$(this).val() + ',';
			 }
		 );  
		 
		 if(question3!=""){
			 question3=question3.substring(0, question3.length-1);
		 }
		
		 if($("#name").val()==""){
				$("#name").focus();
				$.alertTip($("#name"),"请输入姓名 ");
				return;
		}else if($("#mobile").val()==""){
			$("#mobile").focus();
			$.alertTip($("#mobile"),"请输入手机号 ");
			return;
		}else if(!isMobile($("#mobile").val())){
			$.alertTip($("#mobile"),"请输入正确的手机号 ");
			$("#mobile").focus();
			return;
		}else if(sex==""||sex==undefined){
			//$.alertTip($("#sexdiv"),"请输入性别 ");
			showMsgDialog("提示","请输入性别");
			return;
		}else if($("#birthday").val()==""){
			$.alertTip($("#birthday"),"请选择生日 ");
			$("#birthday").focus();
			return;
		}else if(question1==""||question1==undefined){
			showMsgDialog("提示","选择问题1");
			return;
		}else if(question2==""||question2==undefined){
			showMsgDialog("提示","选择问题2");
			return;
		}else if(question3==""||question3==undefined){
			showMsgDialog("提示","选择问题3");
			return;
		}else if(question4==""||question4==undefined){
			showMsgDialog("提示","选择问题4");
			return;
		}else if(question5==""||question5==undefined){
			showMsgDialog("提示","选择问题5");
			return;
		}else if(question6==""||question6==undefined){
			showMsgDialog("提示","选择问题6");
			return;
		}else if(question7==""||question7==undefined){
			showMsgDialog("提示","选择问题7");
			return;
		}else if($("#place").val()==""){
			showMsgDialog("提示","请选择省份");
			return;
		}else if($("#city").val()==""){
			showMsgDialog("提示","请选择城市");
			return;
		}
		$("#question3").val(question3);
		var questionFromData=$("#questionFrom").serialize();
		
		$.post(basepath+"check_exist_mobile",{mobile:$("#mobile").val(),ajax:1},function(data){  //检验手机号码是否已注册
			if(data.success){
				if(data.data.isExist){
					showMsgDialog("提示","您的手机号码已经注册");
					return;
				}else if(data.data.ischongqing){
					window.location.href= basepath+"sendAccount";
				}else if(data.data.fail){
					window.location.href= basepath+"sendAccount";
				}else{
				  $.ajax({
						url : basepath+"doQuestionnaire",
						type : "post",
						dataType : "json",
						data : questionFromData,
						success : function(data) {
							if(data.success){
								if(data.obj.id!=null && data.obj.id!="" && data.obj.id!=undefined){
									$("#id").val(data.obj.id);
									//window.location.href=basepath+"/telphone?id="+data.obj.id;
									document.forms[0].submit();
								}else{
									showMsgDialog("提示","保存错误请返回重新填写");
								}
							}else{
								showMsgDialog("提示",data.message);
							}
							
						},
						error:function() {
							alert("添加失败！请刷新重试！");
						}
					});
					
				}
			}else{
				showMsgDialog("提示","系统错误......");
			}
		},"json"); 
		
		
	};
	
	
	
function showage(){
	var birthday=$("#birthday").val();
	if(birthday!=""){
		var birthdays=birthday.split(",");
		var year=birthdays[0];
		var nowDate = new Date();
		var nowyear=nowDate.getFullYear();
		var age=parseInt(nowyear)-parseInt(year);
		$("#agenum").html(age);
		$("#age").val(age);
	}
}

function getcitydata(){
	var id=$("#place ").val();
	var temp_html="";
	$.post(basepath+"/queryareas/"+id,{ajax:1},function(data){  //获取二级数据(城市)
		if(data.success){
			$(data.data.areaList).each(function(){
				 temp_html+="<option value='"+this.id+"'>"+this.title+"</option>";

			});
		}else{
			showMsgDialog("提示","系统错误......");
		}
		 $("#city").html(temp_html);
	},"json"); 
	
}


//检查手机号码是否存在
function checkMobile(mobile){
	$.post(basepath+"/check_exist_mobile",{mobile:mobile,ajax:1},function(data){  //检验手机号码是否已注册
		if(data.success){
			if(data.data.isExist){
				$(".qs_telp_error").show();
			}else{
				$(".qs_telp_error").hide();
			}
		}else{
			showMsgDialog("提示","系统错误......");
		}
	},"json"); 
}

isMobile=function(mobile){
	return mobile&&/^1[3-9]\d{9}$/.test(mobile);
}



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

var flag=true;
//倒数计时间
function countReset(obj,tt,wait){
	obj.html(tt+"秒后重新获取");
	obj.attr("disabled","disabled");
	time(obj,wait);
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


