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
<title>登录</title>
<%@include file="../../common/import-easyui-js.jspf"%>
<script type="text/javascript" src="${ctx}/static/plugins/my97DatePicker/WdatePicker.js"></script>
<script type="text/javascript" src="${ctx}/static/script/common/dateUtils.js"></script>
<script type="text/javascript" src="${ctx}/static/script/investor/list.js"></script>
</head>
<body>
	<shiro:hasPermission name="sys:riskmanager:investor:view">
	<div id="tb" style="padding: 5px; height: auto">
		<div>
			<table>
				<tr>
					<td>
						<span>姓名:</span>
						<input id="startTime" class="" type="text"/>
					</td>
					<td>
						<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="tradeDay.doSearch()">Search</a>
					</td>
				</tr>
			</table>				
		</div>
		<div style="margin-bottom: 5px">
			<shiro:hasPermission name="sys:riskmanager:investor:create">  
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="baseUtils.openAddwin(500,200,'新增在出资人','investor')">新增</a>
			</shiro:hasPermission>
			<shiro:hasPermission name="sys:riskmanager:investor:delete"> 
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="baseUtils.deleteData('investor')">删除</a>
			</shiro:hasPermission>
			<shiro:hasPermission name="sys:riskmanager:investor:update"> 
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="baseUtils.openEditwin(500,200,'修改出资人','investor')">修改</a>
			</shiro:hasPermission>
		</div>
	</div>
	<table id="edatagrid"></table>
	<!-- ADD window -->
	<div id="addWin" style="padding:10px;top: 20px;"></div>
	<!-- window -->
	</shiro:hasPermission>
	<shiro:lacksPermission name="sys:riskmanager:investor:view">
		<%@ include file="../../common/noPermission.jsp"%>
	</shiro:lacksPermission>
</body>
</html>