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
</head>
<body>
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/dataStyle.css">
<script type="text/javascript" src="${ctx}/static/script/common/public.js"></script>
	<form method="post" id="addForm">
		<input type="hidden" id="id" name="id" value="${fSimpleFtseUserTrade.id}">
		<input type="hidden" id="type" name="type" value="${type}">
		<table class="conn" border="0" width="100%" cellpadding="0" cellspacing="0"  class="conn" >
			<tr>
				<td class="label right">账号:</td>
				<td>
					<input class="easyui-validatebox" value="${fSimpleFtseUserTrade.tranAccount}" id="tranAccount" name="tranAccount" data-options="required:true">
				</td>
			</tr>
			<tr>
				<td class="label right">密码:</td>
				<td>
					<input class="easyui-validatebox" value="${fSimpleFtseUserTrade.tranPassword}" id="tranPassword" name="tranPassword" data-options="required:true">
				</td>
			</tr>
			<tr>
				<td colspan="2" align="center">
					<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-cancel'"  onclick="futureRiskOptions.cancel()">取消</a>
					<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save'"  onclick="futureRiskOptions.saveOrUpdate('admin/commodityFutureRisk/editAccount', 'applyGrid')">提交</a>
				</td>
			</tr>
		</table>
	</form>
</body>
</html>