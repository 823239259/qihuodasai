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
<title>现货</title>
</head>
<body>
	<form method="post" id="addForm" style="padding-left: 22%;">
	<input type="hidden" id="dataID" name="id"  value="${dataMap.id}">
	<!-- missingMessage:'不能为空' 可以修改为空时的提示语 -->
		<table class="formTable">
			<tr>
				<td>销售姓名:</td>
				<td>
					<input class="easyui-validatebox" value="${dataMap.name}" id="name"  name="name" data-options="required:true">
				</td>
			</tr>
			<tr>
				<td>销售电话:</td>
				<td>
					<input class="easyui-validatebox" value="${dataMap.mobile}" id="mobile"  name="mobile" data-options="required:true">
				</td>
			</tr>
			<tr>
				<td colspan="2" align="center">
				<c:choose>
					<c:when test="${fromType=='add'}">
						<a href="javascript:void(0)" id="saveOrUpdate" class="easyui-linkbutton" data-options="iconCls:'icon-save'"  onclick="baseUtils.saveOrUpdate('admin/spotSalesman/create')">保存</a>
					</c:when>
					<c:otherwise>
						<a href="javascript:void(0)" id="saveOrUpdate" class="easyui-linkbutton" data-options="iconCls:'icon-edit'"  onclick="baseUtils.saveOrUpdate('admin/spotSalesman/update')">修改</a>
					</c:otherwise>
				</c:choose>
				</td>
			</tr>
		</table>
	</form>
</body>
</html>