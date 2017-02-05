<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../common/common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="Content-Language" content="zh-CN" />
<!-- X-UA-Compatible 设置ie8/ie9 的兼容模式 -->
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<%@include file="../../common/import-easyui-js.jspf"%>
<title>补录充值表单</title>
<script src="${ctx}/static/plugins/my97DatePicker/WdatePicker.js"
	type="text/javascript"></script>
<script type="text/javascript" src="${ctx}/static/script/wuser/public.js"></script>

<script type="text/javascript">
	function saveInfo(type){
		var mobile=$("#mobile").val();
		if ($("#addForm").form('validate') == false) {
			return;
		}
		$.post($.easyui.path()+"/admin/drawBlackList/checkData",{"mobile":mobile},function(data){  
			if(data.success){
				baseUtils.saveOrUpdate('admin/drawBlackList/doSave', 'iframe');		
			}else{
					alert(data.message);
					return;
			}
		},"json"); 
		}
	
	function editInfo(type){
		
		if ($("#addForm").form('validate') == false) {
			return;
		}
				baseUtils.saveOrUpdate('admin/drawBlackList/doEdit', 'iframe');		
			
		}
</script>
</head>
<body>
<input type="hidden" value="${fromType}">

		<form method="post" id="addForm" style="padding-left:10%;">
		<!-- missingMessage:'不能为空' 可以修改为空时的提示语 -->
		
					<input type="hidden" id="e_id" name="e_id" value="${id }">
					
					<table class="formTable">
					<c:if test="${fromType eq 'edit'}">
						<tr>
							<td>手机号:</td>
							<td>
								<input  id="mobile" readonly="readonly" class="easyui-validatebox" data-options="required:true"  name="mobile" value="${mobile }">
							</td>
						</tr>
						<tr>
							<td>原因:</td>
							<td>
							<textarea class="easyui-validatebox" data-options="required:true" rows="5" cols="60" id="reason" name="reason">${reason }</textarea>
								
							</td>
						</tr>
						<tr>
								<td colspan="2" align="center">
										<a href="javascript:void(0)" id="saveOrUpdate" class="easyui-linkbutton" data-options="iconCls:'icon-edit'"  onclick="editInfo(1)">修改</a>
								</td>
						</tr>
						</c:if>
					<c:if test="${fromType eq 'add'}">
						<tr>
							<td>手机号:</td>
							<td>
								<input  id="mobile" class="easyui-validatebox" data-options="required:true"  name="mobile" >
							</td>
						</tr>
						<tr>
							<td>原因:</td>
							<td>
							<textarea class="easyui-validatebox" data-options="required:true" rows="5" cols="60" id="reason" name="reason"></textarea>
								
							</td>
						</tr>
						<tr>
								<td colspan="2" align="center">
										<a href="javascript:void(0)" id="saveOrUpdate" class="easyui-linkbutton" data-options="iconCls:'icon-save'"  onclick="saveInfo(1)">保存</a>
								</td>
						</tr>
						</c:if>
					</table>
					
		</form>
</body>
</html>