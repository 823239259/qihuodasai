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
<%@include file="../../../common/import-easyui-js.jspf"%>
<script type="text/javascript"
	src="${ctx}/static/script/wuser/public.js"></script>
<script type="text/javascript"
	src="${ctx}/static/script/common/dateUtils.js"></script>
<script type="text/javascript" src="${ctx}/static/script/commodityFuture/futureParam/list.js"></script>
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/css/dataStyle.css">

</head>
<body>
	<shiro:hasPermission
		name="sys:settingParams:commodityFutureParam:view">
		<div id="tb" style="padding: 5px; height: auto">
			<%--- 功能按钮--%>
			<div style="margin-bottom: 5px">
				<shiro:hasPermission
					name="sys:settingParams:commodityFutureParam:update">
					<a href="javascript:void(0)" class="easyui-linkbutton"
						iconCls="icon-edit"
						onclick="futureParamOptions.auditParam(450, 550, '编辑')">修改</a>
				</shiro:hasPermission>
			</div>
		</div>
		<%-- 数据表格 --%>
		<table id="edatagrid" width="100%"></table>
		<!-- ADD window -->
		<div id="addWin" style="padding: 10px; top: 20px;"></div>
		<!-- window -->
	</shiro:hasPermission>
	<!-- window -->
	<shiro:lacksPermission name="sys:settingParams:commodityFutureParam:view">
		<%@ include file="../../../common/noPermission.jsp"%>
	</shiro:lacksPermission>
</body>
</html>