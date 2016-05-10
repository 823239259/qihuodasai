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
<title>补仓提醒</title>
<%@include file="../../common/import-easyui-js.jspf"%>
<script type="text/javascript" src="${ctx}/static/script/userTrade/notEnoghBalanceLlist.js"></script>
</head>
<body>
	<shiro:hasPermission name="sys:customerService:notEnoughBalance:view">
	<div id="tb" style="padding: 5px; height: auto">
		<div>
			<table>
				<tr>
					<td>
						<span>Name:</span> <input id="name">
					<td>
					<td>
						<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="user.list.doSearch()">Search</a>
					</td>
				</tr>
			</table>				
		</div>
		<div style="margin-bottom: 5px">
			<shiro:hasPermission name="sys:customerService:notEnoughBalance:notConnected">  
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-no" plain="true" onclick="notEnoghBalance.changeNoticeStatus(2)">未接通</a>
			</shiro:hasPermission>
			<shiro:hasPermission name="sys:customerService:notEnoughBalance:notified">  
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" plain="true" onclick="notEnoghBalance.changeNoticeStatus(1)">已通知</a>
			</shiro:hasPermission>
			<shiro:hasPermission name="sys:customerService:notEnoughBalance:view">  
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload" plain="true" onclick="notEnoghBalance.reloadData()">刷新</a>
			</shiro:hasPermission>
			<shiro:hasPermission name="sys:customerService:notEnoughBalance:sendSms">  
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" plain="true" onclick="notEnoghBalance.sendSms()">短信通知</a>
			</shiro:hasPermission>
		</div>
	</div>
	<table id="edatagrid"></table>

	<!-- ADD window -->
	<div id="addWin" style="padding:10px;top: 20px;"></div>
	<!-- window -->
	</shiro:hasPermission>
	<shiro:lacksPermission name="sys:customerService:notEnoughBalance:view">
		<%@ include file="../../common/noPermission.jsp"%>
	</shiro:lacksPermission>
</body>
</html>