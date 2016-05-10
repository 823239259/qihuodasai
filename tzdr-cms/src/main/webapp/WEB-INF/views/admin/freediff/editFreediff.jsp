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
		var account=$("#account").val();
		var money=$("#money").val();
		var createdate=$("#createdate").val();
		var accounttype=$("#type").val();
		var id=$("#id").val();
		if ($("#addForm").form('validate') == false) {
			
			return;
		}
			$.post($.easyui.path()+"/admin/freediff/checkData",{"account":account,"money":money,"addtime":createdate,"type":accounttype,"id":id},function(data){  
				if(data.success){
					if (id){
							baseUtils.saveOrUpdate('admin/freediff/update', 'iframe');
						}else{
							baseUtils.saveOrUpdate('admin/freediff/create', 'iframe');	
						
						 }
				}else{
						alert(data.message);
						return;
				}
			},"json"); 
		}
</script>
</head>
<body>
		<form method="post" id="addForm" style="padding-left:10%;">
		<input type="hidden" id="id" name="id" value="${entity.id}">
		<!-- missingMessage:'不能为空' 可以修改为空时的提示语 -->
					<table class="formTable">
						<tr>
							<td>类型:</td>
							<td>
								<select name="type" id="type" style="width:150px">
				                 	<option value="1" ${entity.type eq '1' ? 'selected' : ''}>佣金差</option>
				                 	<option value="2" ${entity.type eq '2' ? 'selected' : ''}>过户费差</option>
				                 </select>
							</td>
						</tr>
						
						<tr>
							<td>恒生子账户:</td>
							<td>
								<input  id="account" class="easyui-validatebox" data-options="required:true"  name="account" value="${entity.account }">
							</td>
						</tr>
						
						<tr>
							<td>金额:</td>
							<td>
								<input  id="money" class="easyui-validatebox" name="money" data-options="required:true,validType:['money']" value="${entity.money }">
							</td>
						</tr>
						
						<tr>
							<td>产生日期:</td>
							<td>
							 <input type="text" name="createdate" readonly="readonly" class="easyui-validatebox" data-options="required:true"
										onclick="WdatePicker();" id="createdate" value="${time}">
							</td>
						</tr>
						<tr>
								<td colspan="2" align="center">
								<c:choose>
									<c:when test="${fromType=='add'}">
										<a href="javascript:void(0)" id="saveOrUpdate" class="easyui-linkbutton" data-options="iconCls:'icon-save'"  onclick="saveInfo(1)">保存</a>
									</c:when>
									<c:otherwise>
										<a href="javascript:void(0)" id="saveOrUpdate" class="easyui-linkbutton" data-options="iconCls:'icon-edit'"  onclick="saveInfo(2)">修改</a>
									</c:otherwise>
								</c:choose>
								</td>
						</tr>
					</table>
		</form>
</body>
</html>