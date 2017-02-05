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
<title>终结方案</title>
<script src="${ctx}/static/plugins/my97DatePicker/WdatePicker.js"
	type="text/javascript"></script>

<script type="text/javascript">
	function saveInfo(){
		var id=$("#id").val();
		if ($("#addForm").form('validate') == false) {
			return;
		}
			$.post(basepath+"/admin/freediff/checkData",{"account":account,"money":money,"addtime":createdate,"type":accounttype,"id":id},function(data){  
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
							<td>方案结算金额:</td>
							<td>
								<input  id="money" class="easyui-validatebox" name="money" data-options="required:true,validType:['money']" >
							</td>
						</tr>
						
						<tr>
								<td colspan="2" align="center">
								  <a href="javascript:void(0)" id="saveOrUpdate" class="easyui-linkbutton" data-options="iconCls:'icon-save'"  onclick="saveInfo()">保存</a>
								</td>
						</tr>
					</table>
		</form>
</body>
</html>