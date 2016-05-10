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
<%@include file="../../common/import-easyui-js.jspf"%>

<script type="text/javascript" src="${ctx}/static/script/common/dateUtils.js"></script>
<script type="text/javascript" src="${ctx}/static/script/wuser/public.js"></script>
<script type="text/javascript" src="${ctx}/static/plugins/my97DatePicker/WdatePicker.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/dataStyle.css">
</head>
<body>
	<shiro:hasPermission name="sys:operationalConfig:spot:view">
	<div class="easyui-tabs" style="margin: auto;">
		<div title="开户报名" style="padding: 0px;">
			<div id="tb1">
				<div>
					<form id="queryForm" method="post">
					</form>
				</div>
				<div style="margin-bottom: 5px">
					<shiro:hasPermission name="sys:operationalConfig:spot:export">
						<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-excel" plain="true" onclick="$.easyui.exportExcel('edatagrid0','queryForm')">导出</a>
					</shiro:hasPermission>
				</div>
			</div>
			<table id="edatagrid0" class="easyui-datagrid" width="100%" toolbar="#tb1"
				             url="${ctx}/admin/spotBooking/getData" pagination="true"
				            rownumbers="true" fitColumns="true" singleSelect="true" showFooter="true">
			     <thead>
			         <tr>
						<th field="createTime" width="150">预约时间</th>
						<th field="autonym" width="150">真实姓名</th>
						<th field="mobile" width="150">手机号码</th>
						<th field="salesmanName" width="150">销售姓名</th>
						<th field="salesmanMobile" width="150">销售电话</th>
			         </tr>
			     </thead>
			</table>
		</div>
		<div title="销售人员管理" style="padding: 0px;">
			<div id="tb2">
				<div style="margin-bottom: 5px">
					<shiro:hasPermission name="sys:operationalConfig:spot:create">
						<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="baseUtils.openAddwin(500,300,'新增数据','spotSalesman')">新增</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="sys:operationalConfig:spot:update">
						<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="baseUtils.openEditwin(500,300,'修改数据','spotSalesman')">修改</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="sys:operationalConfig:spot:delete">
						<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="baseUtils.deleteData('spotSalesman')">删除</a>
					</shiro:hasPermission>
				</div>
			</div>
			<table id="edatagrid" class="easyui-datagrid" width="100%" toolbar="#tb2"
				             url="${ctx}/admin/spotSalesman/getData" pagination="true"
				            rownumbers="true" fitColumns="true" singleSelect="true">
			     <thead>
			         <tr>
			            <th field="id" data-options="checkbox:true"></th>
						<th field="name" width="150">销售姓名</th>
						<th field="mobile" width="150">销售电话</th>
			         </tr>
			     </thead>
			</table>
		</div>
	</div>
	<!-- ADD window -->
	<div id="addWin" style="padding:10px;top: 20px;"></div>
	<!-- window -->
	</shiro:hasPermission>
	<shiro:lacksPermission name="sys:operationalConfig:spot:view">
		<%@ include file="../../common/noPermission.jsp"%>
	</shiro:lacksPermission>
</body>
</html>