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
<script type="text/javascript"
	src="${ctx}/static/script/hkstock/hkArrearsEnd/list.js"></script>
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/css/dataStyle.css">

</head>
<body>
	<shiro:hasPermission name="sys:riskmanager:hkArrearsEnd:view">
		<div id="tb" style="padding: 5px; height: auto">
			<%-- 查询功能 --%>
			<form id="searchForm" method="post">
				<table border="0" style="font-size: 12px;" class="conn" width="100%"
					cellpadding="0" cellspacing="0">
					<tr>
						<td class="label right"><span>手机号码：</span></td>
						<td><input class="easyui-validatebox" id="search_LIKE_mobile"
							name="search_LIKE_mobile"></td>
						<td class="label right"><span>交易账号：</span></td>
						<td><input class="easyui-validatebox"
							id="search_EQ_accountNo" name="search_EQ_accountNo"></td>
					</tr>
					<tr>
						<td class="label right"><span> </span></td>
						<td colspan="3" class="label"><a href="javascript:void(0)"
							class="easyui-linkbutton" iconCls="icon-search"
							onclick="datagridUtils.datagridQuery('edatagrid','searchForm')">查询</a>
						</td>
					</tr>
				</table>
			</form>
			<%--- 功能按钮--%>
			<div style="margin-bottom: 5px">
				<shiro:hasPermission name="sys:riskmanager:hkArrearsEnd:end">
					<a href="javascript:void(0)" class="easyui-linkbutton"
						iconCls="icon-add" plain="true"
						onclick="arrearsEndOptions.endPlan()">终结方案</a>
				</shiro:hasPermission>
				<a href="javascript:void(0)" class="easyui-linkbutton"
					iconCls="icon-search" plain="true" onclick="arrearsEndOptions.openMoneyDetail()">查询欠费明细</a>
			</div>
		</div>
		<%-- 数据表格 --%>
		<table id="edatagrid" width="100%"></table>
		<!-- ADD window -->
		<div id="addWin" style="padding: 10px; top: 20px;"></div>
		<!-- window -->
	</shiro:hasPermission>
	<!-- window -->
	<shiro:lacksPermission name="sys:riskmanager:hkArrearsEnd:view">
		<%@ include file="../../../common/noPermission.jsp"%>
	</shiro:lacksPermission>
	<div id="moneyDetail" class="easyui-window" title="欠费明细"
		data-options="iconCls:'icon-save',modal:true,closed:true"
		style="width: 800px; height: 400px;">
		<form id="queryFormDetail" method="post">
			<input type="hidden" name="search_EQ_groupId"
				id="search_EQ_groupId" /> <a id="detailBtn"
				style="display: none;" href="javascript:void(0)"
				onclick="datagridUtils.datagridQuery('dgdetail','queryFormDetail')"
				class="easyui-linkbutton"
				data-options="iconCls:'icon-search'">查询</a>
		</form>
		<table id="dgdetail" width="100%"></table>
	</div>
</body>
</html>