var CouponManage={
		add:function(){
			$('#name2').val("");
			$('#money2').val("");
			$('#cycle2').val("");
			$('#numToHave2').val("");
			$('#type2').combobox('setValue','');
			$('#deadline2').datetimebox('setValue','');
			$('input[name="scope"]:checked').attr("checked", false);
			$("#addWin").window('open');
		},
		edit:function(){
			var rowData=$('#edatagrid').datagrid('getSelected');
			if (null == rowData){
				eyWindow.walert("修改提示",'请选择要修改的行', 'info');
				return;
			}
			$("#edit_id").val(rowData.id);
			$("#name3").text(rowData.name);
			$("#scope3").text(numToStr(rowData.scope));
			$("#type3").text(typeToStr(rowData.type));
			$("#money3").text(addUnit(rowData.money,rowData));
			$("#numToHave3").val("");
			if(rowData.deadline!=null&&rowData.cycle==null){
				$("#editForm").find('.dateclass2').eq(0).attr('checked','true');
				$("#deadline_e").show();
				$("#cycle_e").hide();
			}else if(rowData.cycle!=null&&rowData.deadline!=null){
				$("#editForm").find('.dateclass2').eq(1).attr('checked','true');
				$("#deadline_e").hide();
				$("#cycle_e").show();
			}else if(rowData.cycle!=null&&rowData.deadline==null){
				$("#editForm").find('.dateclass2').eq(1).attr('checked','true');
				$("#deadline_e").hide();
				$("#cycle_e").show();
			}
			$("#deadline3").datetimebox('setValue','');
			$("#cycle3").val("");
			
			$('#editWin').window('open');
		},
		give:function(){
			$("#name4").combobox("reload");
			 $("#name4").combobox('setValue','');
			 $('#type4').text('');
			 $('#money4').text('');
			 $('#userName4').text('');
			 $('#userPhone4').val('');
			$("#giveWin").window('open');
		},
		close:function(WindowId){
			$("#"+WindowId).window('open');
			$("#"+WindowId).window('close');
		},use:function(){
			var rowData=$('#edatagrid2').datagrid('getSelected');
			if (null == rowData){
				eyWindow.walert("修改提示",'请选择要使用的数据！', 'info');
				return;
			}
			if(rowData.status!=2){
				 Check.messageBox("提示","必须是已发放的状态才能使用！","info");
				  return;
			}
			$.messager.confirm('使用','确定使用这个优惠券吗？',function(r){
			    if (r){
					$.post(Check.rootPath()+'/admin/couponUse/useCoupon',{"id":rowData.id},function(data){
						if(data=="true"){
							 Check.messageBox("提示","使用成功！","info");
							 $('#edatagrid2').datagrid('reload');
							  return;
						}else if(data=="false.null"){
							 Check.messageBox("提示","优惠券不存在！","error");
							  return;
						}else if(data=="false.status"){
							 Check.messageBox("提示","优惠券状态不是已发放！","error");
							  return;
						}else if(data=="false.deadline"){
							 Check.messageBox("提示","优惠券未发放或已过期！","error");
							  return;
						}else{
							 Check.messageBox("提示","使用失败！","error");
							  return;
						}
					},"text");
			    }
			});
			
		}
}


function passCreate(){
	var $name2=$('#name2').val();
	  var scope_value ="";  
	   var $len=$('input[name="scope"]:checked').length;
	  $('input[name="scope"]:checked').each(function(index){ 
		  if($len-1==index){
			  scope_value+=$(this).val()
		  }else{
			  scope_value+=$(this).val()+",";
		  }
		     
	  });
	  if(scope_value.length==0){
		  Check.messageBox("提示","至少选择一个范围！","info");
		  return;
	  }
	
	  var $type2=$('#type2').combobox('getValue');
	  if($type2==-1){
		  Check.messageBox("提示","请选择一个类型！","info");
		  return;
	  }
	  var $money2=$('#money2').val();
	  if($type2!=null&&$type2==3){
		  if($money2<0||$money2>10){
			  Check.messageBox("提示","折扣券面值只能为0-10折！","info");
			  return; 
		  }
	  }
	  
	  var $numToHave2=$('#numToHave2').val();
	  if($numToHave2<=0){
		  Check.messageBox("提示","请输入正确的个数！","info");
		  return;
	  }
	  var dateval=$("#addForm").find('.dateclass').filter(':checked').val();
	  var $deadline2="";
	  var $cycle2=0;
	  var $deadlineLong=null;
	  if(dateval==0){
		  $deadline2= $('#deadline2').datetimebox('getValue');
		  if($deadline2==null){
			  Check.messageBox("提示","截止日期不能为空！","info");
			  return;
		  }
		  $deadlineLong = convertStartTime($deadline2);
	  }else if(dateval==1){
		  $cycle2=$('#cycle2').val();
		  if($cycle2<=0){
			  Check.messageBox("提示","使用周期至少是1天！","info");
			  return;
		  }
	  }
	  
	 
				$.post(basepath+'admin/couponManage/add',{"name":$name2,"scope":scope_value,"type":$type2,"money":$money2,"numToHave":$numToHave2,"deadline":$deadlineLong,"cycle":$cycle2},
						function(data){
						if(data.success&&data.message==""){
							 Check.messageBox("提示","新增成功！","info");
							 CouponManage.close('addWin');
							 $("#edatagrid").datagrid('reload');
						}else if(data.message=="参数不能为空"){
							 Check.messageBox("提示","请填写完整后提交！","info");
							 return;
						}else if(data.message=="名称不能为空"){
							Check.messageBox("提示","请填写名称后提交！","info");
							 return;
						}else if(data.message=="名称必须唯一"){
							Check.messageBox("提示","名称错误(已有)！","error");
							 return;
						}else{
							 Check.messageBox("提示","新增失败，请检查后重新提交！","error");
							 return;
						}
						
					},"json")
}


function passUpdate(){
	var $name3=$("#name3").text();
	if($name3==null){
		  Check.messageBox("提示","名称不能为空！","info");
		  return;
	}
	var $numToHave3=$("#numToHave3").val();
	  if($numToHave3<0){
		  Check.messageBox("提示","新增个数不能小于0！","info");
		  return;
	  }
	  var rowData=$('#edatagrid').datagrid('getSelected');
	  var $deadline3="";
	  var $cycle3=0;
	  var $deadlineLong=null;
	  if(rowData.deadline!=null&&rowData.cycle==null){
			 $deadline3=$("#deadline3").datetimebox('getValue');
			 if($deadline3==null){
				  Check.messageBox("提示","截止日期不能为空！","info");
				  return;
			 }
			 $deadlineLong = convertStartTime($deadline3);
		}else if(rowData.cycle!=null){
			 $cycle3=$("#cycle3").val();
			  if($cycle3<=0){
				  Check.messageBox("提示","使用周期至少是1天！","info");
				  return;
			  }
		}
	  

	  $.post(basepath+'admin/couponManage/edit',{"name":$name3,"numToHave":$numToHave3,"deadline":$deadlineLong,"cycle":$cycle3},function(data){
		  
		  if(data.success&&data.message==""){
			  Check.messageBox("提示","修改成功！","info");
				 CouponManage.close('editWin');
				 $("#edatagrid").datagrid('reload');
		  }else if(data.message=="请选择数据！"){
			  Check.messageBox("提示","请选择数据！","error");
				 return;
		  }else if(data.message=="新增个数有误！"){
			  Check.messageBox("提示","新增个数有误！","error");
				 return;
		  }else{
			  Check.messageBox("提示","出错了！","error");
				 return;
		  }
	  },"json")
	  
	
}


function findById(obj){
	$.post(basepath+'admin/couponUse/findById',{"id":obj.id},function(data){
		if(data.success){
			if(data.data.type==3){
				$('#money4').text(data.data.money+"折");
				$("#type4").text("折扣券");
			}else{
				$('#money4').text(data.data.money+"元");
				if(data.data.type==1){
					$("#type4").text("现金红包");
				}else if(data.data.type==2){
					$("#type4").text("代金券");
				}else if(data.data.type==4){
					$("#type4").text("实物");
				}else if(data.data.type==5){
					$("#type4").text("优惠券");
				}
			}
		}else{
			  Check.messageBox("提示","优惠券不存在！","error");
				 return;
		}

	},"json");
	
}


function changeToStr(value,row,index){
	return getFormatDateByLong(value,"yyyy-MM-dd hh:mm:ss");
}

function addUnit(value,row,index){
	if(row.type==3){
		return value+"折";
	}else{
		return value+"元";
	}
}

function cycleaddStr(value,row,index){
	if(value!=0&&value!=null){
		return value+"天";
	}
}


function numToStr(value,row,index){
	var strs= new Array();
	var newStr="";
	
	strs=value.split(","); 
	for(i=0;i<strs.length ;i++ ){
		if(strs[i]=='0'){
			newStr+="富时A50"+"、"
		}else if(strs[i]=='5'){
			newStr+="商品期货"+"、"
		}else if(strs[i]=='6'){
			newStr+="国际原油"+"、"
		}else if(strs[i]=='7'){
			newStr+="恒指期货"+"、"
		}else if(strs[i]=='8'){
			newStr+="国际综合"+"、"
		}
	}
	
	newStr=newStr.substring(0,newStr.length-1)
	
	return newStr;
}

function statusToStr(value,row,index){
	if(value==1){
		return "未发放";
	}else if(value==2){
		return "已发放";
	}else if(value==4){
		return "已过期";
	}else{
		return "已使用";
	}
}


function typeToStr(value,row,index){
	if(value==1){
		return "现金红包";
	}else if(value==2){
		return "代金券";
	}else if(value==4){
		return "实物";
	}else if(value==5){
		return "优惠券";
	}else{
		return "折扣券";
	}
}



function passGive(){
	var $name=$("#name4").combobox('getText');
	if($name==null||$name==''){
		  Check.messageBox("提示","请选择优惠券！","info");
		  return;
	}
	var $userPhone=$("#userPhone4").val();
	if($userPhone==null||$userPhone==''||!$userPhone.match(mobileForm)){
		  Check.messageBox("提示","请输入正确的手机号码！","info");
		  return;
	}
	var $userName=$("#userName4").text();
	$.post(basepath+'admin/couponUse/passGive',{"name":$name,"userPhone":$userPhone,"userName":$userName},function(data){
		if(data=="true"){
			  Check.messageBox("提示","发放成功！","info");
			  CouponManage.close("giveWin");
			 
			  $("#edatagrid2").datagrid('reload');
			  return;
		}else if(data=="false.list.null"){
			  Check.messageBox("提示","优惠券未存在或已被使用！","error");
			  return;
		}else if(data=="false.status"){
			  Check.messageBox("提示","优惠券已被使用或发放！","error");
			  return;
		}else if(data=="false.deadline"){
			  Check.messageBox("提示","优惠券已过期！","error");
			  return;
		}else if(data=="false.user"){
			  Check.messageBox("提示","用户不存在！","error");
			  return;
		}else{
			  Check.messageBox("提示","发放失败，请检查后重新发放！","error");
			  return;
		}
	},"text");
	
}


var mobileForm =/^(((13[0-9])|(14[5|7])|(15[0-3|5-9])|(17[0|6-8])|(18[0-9]))+\d{8})$/;

$(document).ready(function(){	
	$("#addForm").find('.dateclass').click(function(){
		var val=$(this).val();
		if(val==0){
			$("#deadline").show();
			$("#cycle").hide();
		}else{
			$("#deadline").hide();
			$("#cycle").show();
		}
	})
	
	$("#editForm").find('.dateclass2').click(function(){
		var val=$(this).val();
		if(val==0){
			  Check.messageBox("提示","只能做使用周期的修改！","info");
			  $("#editForm").find('.dateclass2').eq(1).attr('checked','true');
				 return;
		}else{
			Check.messageBox("提示","只能做截止日期的修改！","info");
			$("#editForm").find('.dateclass2').eq(0).attr('checked','true');
			 return;
		}
	})
	
	
	$('#type2').combobox({
		onSelect:function(){
		var $type=$(this).combobox('getValue');
		if($type==1||$type==2||$type==4||$type==5){
			$('#unit').text('元');
		}else if($type==3){
			$('#unit').text('折');
		}else{
			$('#unit').text('');
		}
		}
	})

	$('#money2').blur(function(){
	var $money2=$(this).val();
	var $unit=$('#unit').text();
	if($unit=='折'){
		
		if($money2<0||$money2>10){
			Check.messageBox("提示","折扣券数值不能大于10！","error");
		}
	}
	
	
})


	$("#userPhone4").blur(function(){
		var $userPhone4=$(this).val();
		if(!$userPhone4.match(mobileForm)){
			Check.messageBox("提示","请输入正确的手机号码！","error");
			return;
		}
		if($userPhone4==''||$userPhone4==null){
			Check.messageBox("提示","手机号码不能为空！","error");
			return;
		}
		$.post(basepath+'admin/couponUse/findByPhone',{"userPhone":$userPhone4},
		function(data){
			if(data.success){
				if(data.data.tname=='null'||data.data.tname==null){
					$("#userName4").text('');
				}else{
					$("#userName4").text(data.data.tname);
				}
				
			}else{
				Check.messageBox("提示","手机号码未注册或不存在！","error");
				$("#userName4").text("");
				return;
			}
		},"json");
	})
	
})