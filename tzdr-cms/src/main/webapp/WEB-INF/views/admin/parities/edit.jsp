<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../common/common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="Content-Language" content="zh-CN" />
<!-- X-UA-Compatible 设置ie8/ie9 的兼容模式 -->
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title>汇率维护-edit</title>
<%@include file="../../common/import-easyui-js.jspf"%>
</head>
<body>
	<form method="post" id="addForm" style="padding-left: 20%;">
	<!-- missingMessage:'不能为空' 可以修改为空时的提示语 -->
	<table class="formTable">
		<tr>
			<td>汇率类型:</td>
			<td>
				<c:if test="${empty simpleParities}">
				<select id="type" name="type" class="easyui-combobox" style="width:100px;">
					<option value="1">美元</option>
					<option value="2">港元</option>
				</select>
				</c:if>
				<c:if test="${not empty simpleParities}">
				<input type="hidden" name="id" value="${simpleParities.id}">
				<input type="hidden" id="type" name="type" value="${simpleParities.type }"/>
				<input class="easyui-validatebox"  id="typeName" value="${simpleParities.typeName}"  name="typeName" data-options="required:true" readonly="readonly">
				</c:if>
			</td>
		</tr>
		<tr>
			<td>汇率:</td>
			<td><input type="number" value="${simpleParities.parities}" class="easyui-validatebox"  id="parities"  name="parities" data-options="required:true"></td>
		</tr>
		<tr>
			<td colspan="2" align="center">
				<a href="javascript:void(0)" id="saveOrUpdate" class="easyui-linkbutton" data-options="iconCls:'icon-edit'"  onclick="baseUtils.saveOrUpdate('admin/parities/createParities','iframe');">保存</a>
			</td>
		</tr>
	</table>
	</form>
</body>
</html>