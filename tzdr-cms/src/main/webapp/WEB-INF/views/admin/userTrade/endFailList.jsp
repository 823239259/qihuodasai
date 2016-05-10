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
<title>终结划账失败列表</title>
<%@include file="../../common/import-easyui-js.jspf"%>
<script type="text/javascript" src="${ctx}/static/script/common/dateUtils.js"></script>
<script type="text/javascript" src="${ctx}/static/script/userTrade/endFailList.js"></script>
</head>
<body>
	<shiro:hasPermission name="sys:riskmanager:endFailTrades:view">
	<div id="tb" style="padding: 5px; height: auto">
		<!-- <div>
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
		</div> -->
		<div style="margin-bottom: 5px;margin-top: 10px;">
			<shiro:hasPermission name="sys:riskmanager:endFailTrades:update">  
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="endFailTrade.endSuccess()">已完结</a>
			</shiro:hasPermission>
		</div>
	</div>
	<table id="edatagrid"></table>

	<!-- ADD window -->
	<div id="addWin" style="padding:10px;top: 20px;"></div>
	<!-- window -->
	</shiro:hasPermission>
	<shiro:lacksPermission name="sys:riskmanager:endFailTrades:view">
		<%@ include file="../../common/noPermission.jsp"%>
	</shiro:lacksPermission>
</body>
</html>