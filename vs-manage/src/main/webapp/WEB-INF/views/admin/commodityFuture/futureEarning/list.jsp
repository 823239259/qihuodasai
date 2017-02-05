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
	src="${ctx}/static/plugins/my97DatePicker/WdatePicker.js"></script>
<script type="text/javascript"
	src="${ctx}/static/script/commodityFuture/futureEarning/list.js"></script>
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/css/dataStyle.css">

</head>
<body>
	<shiro:hasPermission name="sys:accountant:commodityFutureEarning:view">
		<div id="tb" style="padding: 5px; height: auto">
			<form id="dataSearchForm" method="post">
				<table border="0" style="font-size: 12px;" class="conn" width="100%"
					cellpadding="0" cellspacing="0">
					<tr>
						<td class="label right"><span>手机号码：</span></td>
						<td><input class="easyui-validatebox" id="search_LIKE_mobile"
							name="search_LIKE_mobile"></td>
						<td class="label right"><span>客户姓名：</span></td>
						<td><input class="easyui-validatebox"
							id="search_LIKE_username" name="search_LIKE_username"></td>
						<td class="label right"><span>结算状态：</span></td>
						<td><select id="search_IN_stateType" class="easyui-combobox"
							name="search_IN_stateType" style="width: 100px; height: 25px;">
								<option value="">全部</option>
								<option value="1">开户中</option>
								<option value="2">申请结算</option>
								<option value="3">待结算</option>
								<option value="4">操盘中</option>
								<option value="5">申请已拒绝</option>
								<option value="6">已结算</option>
						</select></td>
					</tr>
					<tr>
						<td class="label right"><span>申请时间：</span></td>
						<td><input id="startAppTime"
							name="search_date_GTE_appTime" class="Wdate" type="text"
							onFocus="var endDate=$dp.$('endAppTime');WdatePicker({onpicked:function(){endDate.focus();},maxDate:'#F{$dp.$D(\'endAppTime\')}'})" />
							至 <input id="endAppTime" name="search_date_LTE_appTime"
							class="Wdate" type="text"
							onFocus="WdatePicker({minDate:'#F{$dp.$D(\'startAppTime\')}'})" />
						</td>
						<td class="label right"><span>结算时间：</span></td>
						<td><input id="startEndTime"
							name="search_date_GTE_endTime" class="Wdate" type="text"
							onFocus="var endDate=$dp.$('endEndTime');WdatePicker({onpicked:function(){endDate.focus();},maxDate:'#F{$dp.$D(\'endEndTime\')}'})" />
							至 <input id="endEndTime" name="search_date_LTE_endTime"
							class="Wdate" type="text"
							onFocus="WdatePicker({minDate:'#F{$dp.$D(\'startEndTime\')}'})" />
						</td>
						<td class="label right"><span>交易品种：</span></td>
						<td><select id="search_IN_businessType"
							class="easyui-combobox" name="search_IN_businessType"
							style="width: 100px; height: 25px;">
								<option value="1,2,3,4,20">全部</option>
								<option value="1">沪金</option>
								<option value="2">沪银</option>
								<option value="3">沪铜</option>
								<option value="4">橡胶</option>
								<option value="20">商品综合</option>
						</select></td>
					</tr>
					<tr>
						<td class="label right"><span> </span></td>
						<td colspan="5" class="label"><a href="javascript:void(0)"
							class="easyui-linkbutton" iconCls="icon-search"
							onclick="datagridUtils.datagridQuery('edatagrid','dataSearchForm')">查询</a>
						</td>
					</tr>
				</table>
			</form>
			<%--- 功能按钮--%>
			<div style="margin-bottom: 5px">
				<shiro:hasPermission
					name="sys:accountant:commodityFutureEarning:export">
					<a href="javascript:void(0)" class="easyui-linkbutton"
						iconCls="icon-excel"
						onclick="$.easyui.exportExcel('edatagrid','dataSearchForm')">导出</a>
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
	<shiro:lacksPermission name="sys:accountant:commodityFutureEarning:view">
		<%@ include file="../../../common/noPermission.jsp"%>
	</shiro:lacksPermission>
</body>
</html>