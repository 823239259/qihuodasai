<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="../../common/common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta http-equiv="Content-Language" content="zh-CN" />
	<!-- X-UA-Compatible 设置ie8/ie9 的兼容模式 -->
	<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
	<title>短信通道切换</title>
	<link rel="stylesheet" type="text/css" href="${ctx}/static/css/dataStyle.css">
	<script type="text/javascript" src="${ctx}/static/script/wuser/public.js"></script>
</head>
<body>
	<form method="post" id="addForm" style="padding-left: 22%;">
		<input type="hidden" id="dataID" name="id"  value="${dataMap.id}">
		<table class="formTable">
			<tr>
				<td align="right">内容:&nbsp;&nbsp;</td>
				<td>${dataMap.typeName}</td>
			</tr>
			<tr>
				<td align="right">短信通道:&nbsp;&nbsp;</td>
				<td>
					<input type="hidden" name="typeKey" value="${dataMap.typeKey}" />
					<input type="hidden" name="typeName" value="${dataMap.typeName}" />
					<input class="easyui-combobox" id="valueKey" name="valueKey" value="${dataMap.valueKey}" 
							data-options="url:'${ctx}/admin/smsChannel/getSmsChannel?typeKey=smsChannel',valueField:'valueKey',textField:'valueName',panelHeight:'auto',required:true" />
					<input type="hidden" id="valueName" name="valueName" value="${dataMap.valueName}" />
					<input type="hidden" name="weight" value="${dataMap.weight}" />
				</td>
			</tr>
			<tr>
				<td colspan="2" align="center">
					<a href="javascript:void(0)" id="saveOrUpdate" class="easyui-linkbutton" data-options="iconCls:'icon-edit'" onclick="baseUtils.saveOrUpdate('admin/smsChannel/update')">修改</a>
				</td>
			</tr>
		</table>
	</form>
	<script type="text/javascript">
		$(document).ready(function() {
			$("#valueKey").combobox({
				onSelect: function(n, o) {
					$("#valueName").val($("#valueKey").combobox("getText"));
				}
			});
		});
	</script>
</body>
</html>