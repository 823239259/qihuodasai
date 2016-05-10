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
<title>冻结失败信息</title>
<%@include file="../../common/import-easyui-js.jspf"%>
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/css/dataStyle.css">
</head>
<body>
	<!-- toolbar="#toolbar" -->
	<div id="tb" style="padding: 5px; height: auto">
		<div>
			<form id="searchForm">
				<table border="0" style="font-size: 12px;" class="conn" width="100%"
					cellpadding="0" cellspacing="0">
					<tr>
						<td class="label right"><span>恒生账号：</span></td>
						<td><input class="easyui-validatebox" id="account"
							name="account"> &nbsp;&nbsp;&nbsp; <a
							href="javascript:void(0)" class="easyui-linkbutton"
							iconCls="icon-search"
							onclick="datagridUtils.datagridQuery('dg','searchForm')">查询</a></td>
					</tr>
				</table>
			</form>
		</div>
	</div>
	<table id="dg" title="冻结失败信息" class="easyui-datagrid" width="100%"
		style="height: auto;" url="${ctx}/admin/freezeFailInfo/listData"
		pagination="true" toolbar="#tb" rownumbers="true" fitColumns="true"
		singleSelect="true">
		<thead>
			<tr>
				<th field="account" width="150">账户</th>
				<th field="typeInfo" width="150">类型</th>
				<th field="messageText" width="150">异常信息</th>
				<th field="crDatetimeStr" width="150">失败时间</th>
			</tr>
		</thead>
	</table>
	<!-- <div id="toolbar">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newUser()">添加</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editUser()">修改</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="destroyUser()">删除</a>
    </div> -->
</body>
</html>