<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../../common/common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="Content-Language" content="zh-CN" />
<!-- X-UA-Compatible 设置ie8/ie9 的兼容模式 -->
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title></title>
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/css/dataStyle.css">
<script type="text/javascript"
	src="${ctx}/static/script/common/public.js"></script>
</head>
<body>
	<form method="post" id="addForm">
		<input type="hidden" id="id" name="id" value="${parentAccount.id}">
		<!-- missingMessage:'不能为空' 可以修改为空时的提示语 -->
		<table class="conn" border="0" width="100%" cellpadding="0"
			cellspacing="0" class="conn">
			<tr>
				<td class="label right">账户编号:</td>
				<td><input class="easyui-validatebox"
					value="${parentAccount.accountNo}" id="accountNo"
					name="accountNo" data-options="required:true"></td>
			</tr>
			<tr>
				<td class="label right">账户名称:</td>
				<td><input class="easyui-validatebox"
					value="${parentAccount.name}" id="name" name="name"
					data-options="required:true"></td>
			</tr>
			<tr>
				<td class="label right">账户交易通道:</td>
				<td><input class="easyui-combobox" id="tradeChannel"
					name="tradeChannel" value="${parentAccount.tradeChannel}"
					data-options="url:'${ctx}/admin/recharge/dataMapCombobox?key=hktradechannel',
					valueField:'id',panelHeight:50,textField:'text',required:true">
			</tr>
			<tr>
				<td colspan="2" align="center"><a href="javascript:void(0)"
					class="easyui-linkbutton" data-options="iconCls:'icon-save'"
					onclick="baseUtils.saveOrUpdate('admin/hkstock/hkParentAccount/edit')">保存</a>
				</td>
			</tr>
		</table>
	</form>
</body>
</html>