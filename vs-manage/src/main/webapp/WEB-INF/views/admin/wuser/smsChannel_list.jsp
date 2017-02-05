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
	<%@include file="../../common/import-easyui-js.jspf"%>
	<script type="text/javascript" src="${ctx}/static/script/wuser/smsChannel_list.js"></script>
	<link rel="stylesheet" type="text/css" href="${ctx}/static/css/dataStyle.css">
</head>
<body>
	<div id="tb" style="padding: 5px; height: auto">
		<div style="margin-bottom: 5px">
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="baseUtils.openEditwin(500,180,'修改数据','smsChannel')">修改</a>
		</div>
	</div>
	<table id="edatagrid"></table>
	<!-- ADD window -->
	<div id="addWin" style="padding:10px;top: 20px;"></div>
</body>
</html>