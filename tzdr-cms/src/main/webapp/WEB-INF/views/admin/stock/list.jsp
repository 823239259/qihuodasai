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
<script type="text/javascript"
	src="${ctx}/static/plugins/my97DatePicker/WdatePicker.js"></script>
<script type="text/javascript"
	src="${ctx}/static/script/common/dateUtils.js"></script>
<script type="text/javascript"
	src="${ctx}/static/script/stock/list.js?20150528"></script>
<script type="text/javascript"
	src="${ctx}/static/script/wuser/public.js"></script>
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/css/dataStyle.css">

</head>
<body>
	<input type="hidden" value="${type}" id="stockType">
	<c:if test="${type==1}">
		<shiro:hasPermission name="sys:riskmanager:restrictStock:view">
			<div id="tb" style="padding: 5px; height: auto">
				<div>
					<form id="searchForm">
						<input type="hidden" name="search_EQ_deleted" value="false">
						<input type="hidden" name="search_EQ_type" value="1">
						<table border="0" style="font-size: 12px;" class="conn"
							width="100%" cellpadding="0" cellspacing="0">
							<tr>
								<td class="label right"><span>股票名称:</span></td>
								<td><input class="easyui-validatebox" id="search_LIKE_name"
									name="search_LIKE_name"></td>
								<td class="label right"><span>股票代码:</span></td>
								<td><input class="easyui-validatebox" id="search_LIKE_code"
									name="search_LIKE_code"></td>
							</tr>
							<tr>
								<td class="label right"><span></span></td>
								<td colspan="3"><a href="javascript:void(0)"
									class="easyui-linkbutton" iconCls="icon-search"
									onclick="datagridUtils.datagridQuery('edatagrid','searchForm')">查询</a>
								</td>
							</tr>
						</table>
					</form>
				</div>
				<div style="margin-bottom: 5px">
					<shiro:hasPermission name="sys:riskmanager:restrictStock:create">
						<a href="javascript:void(0)" class="easyui-linkbutton"
							iconCls="icon-add" plain="true"
							onclick="baseUtils.openAddwin(500,230,'新增限制股','stock/restrict')">新增</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="sys:riskmanager:restrictStock:delete">
						<a href="javascript:void(0)" class="easyui-linkbutton"
							iconCls="icon-remove" plain="true"
							onclick="baseUtils.deleteData('stock/restrict')">删除</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="sys:riskmanager:restrictStock:update">
						<a href="javascript:void(0)" class="easyui-linkbutton"
							iconCls="icon-edit" plain="true"
							onclick="baseUtils.openEditwin(500,230,'修改限制股','stock/restrict')">修改</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="sys:riskmanager:restrictStock:import">
						<a href="javascript:void(0)" class="easyui-linkbutton"
							iconCls="icon-add" plain="true"
							onclick="stock.openImportWindow()">导入</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="sys:riskmanager:restrictStock:export">
						<a id="excelBtn" href="javascript:void(0)"
							onclick="stock.exportTempExcel('/admin/stock/restrict');"
							iconCls="icon-excel" plain="true" class="easyui-linkbutton">导出模板</a>
					</shiro:hasPermission>
				</div>
			</div>
			<table id="edatagrid"></table>
			<!-- ADD window -->
			<div id="addWin" style="padding: 10px; top: 20px;"></div>
			<!-- import window -->
			<div id="excelImport" class="easyui-dialog" title="数据导入"
				style="width: 400px; height: 200px;"
				data-options="iconCls:'icon-save',closed:true,resizable:true,modal:true">
				<form method="post" action="${ctx}/admin/stock/restrict/uploadFile"
					target="target_upload" enctype="multipart/form-data">
					<table class="conn" cellpadding="0" cellspacing="0">
						<tr>
							<td class="label right">上传文件:</td>
							<td colspan="2"><input type="file" id="uploadFileInput"
								name="uploadFile"></td>
						</tr>
						<tr>
							<td>&nbsp;</td>
							<td><a href="#" class="easyui-linkbutton"
								onclick="stock.importExcelData()">上传</a></td>
						</tr>
					</table>
					<iframe name="target_upload" style="display: none;"></iframe>
				</form>
			</div>
			<!-- window -->
		</shiro:hasPermission>
		<shiro:lacksPermission name="sys:riskmanager:restrictStock:view">
			<%@ include file="../../common/noPermission.jsp"%>
		</shiro:lacksPermission>
	</c:if>

	<c:if test="${type==2}">
		<shiro:hasPermission name="sys:riskmanager:suspendedStock:view">
			<div id="tb" style="padding: 5px; height: auto">
				<div>
					<form id="searchForm">
						<input type="hidden" name="search_EQ_deleted" value="false">
						<input type="hidden" name="search_EQ_type" value="2">
						<table border="0" style="font-size: 12px;" class="conn"
							width="100%" cellpadding="0" cellspacing="0">
							<tr>
								<td class="label right"><span>股票名称:</span></td>
								<td><input class="easyui-validatebox" id="search_LIKE_name"
									name="search_LIKE_name"></td>
								<td class="label right"><span>股票代码:</span></td>
								<td><input class="easyui-validatebox" id="search_LIKE_code"
									name="search_LIKE_code"></td>
							</tr>
							<tr>
								<td class="label right"><span></span></td>
								<td colspan="3"><a href="javascript:void(0)"
									class="easyui-linkbutton" iconCls="icon-search"
									onclick="datagridUtils.datagridQuery('edatagrid','searchForm')">查询</a>
								</td>
							</tr>
						</table>
					</form>
				</div>
				<div style="margin-bottom: 5px">
					<shiro:hasPermission name="sys:riskmanager:suspendedStock:create">
						<a href="javascript:void(0)" class="easyui-linkbutton"
							iconCls="icon-add" plain="true"
							onclick="baseUtils.openAddwin(500,230,'新增停牌股','stock/suspended')">新增</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="sys:riskmanager:suspendedStock:delete">
						<a href="javascript:void(0)" class="easyui-linkbutton"
							iconCls="icon-remove" plain="true"
							onclick="baseUtils.deleteData('stock/suspended')">删除</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="sys:riskmanager:suspendedStock:update">
						<a href="javascript:void(0)" class="easyui-linkbutton"
							iconCls="icon-edit" plain="true"
							onclick="baseUtils.openEditwin(500,230,'修改停牌股','stock/suspended')">修改</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="sys:riskmanager:suspendedStock:import">
						<a href="javascript:void(0)" class="easyui-linkbutton"
							iconCls="icon-add" plain="true"
							onclick="stock.openImportWindow()">导入</a>
					</shiro:hasPermission>
					<shiro:hasPermission name="sys:riskmanager:suspendedStock:export">
						<a id="excelBtn" href="javascript:void(0)"
							onclick="stock.exportTempExcel('/admin/stock/suspended');"
							iconCls="icon-excel" plain="true" class="easyui-linkbutton">导出模板</a>
					</shiro:hasPermission>
				</div>
			</div>
			<table id="edatagrid"></table>
			<!-- ADD window -->
			<div id="addWin" style="padding: 10px; top: 20px;"></div>
			<div id="excelImport" class="easyui-dialog" title="数据导入"
				style="width: 400px; height: 200px;"
				data-options="iconCls:'icon-save',closed:true,resizable:true,modal:true">
				<form method="post" action="${ctx}/admin/stock/suspended/uploadFile"
					target="target_upload" enctype="multipart/form-data">
					<table class="conn" cellpadding="0" cellspacing="0">
						<tr>
							<td class="label right">上传文件:</td>
							<td colspan="2"><input type="file" id="uploadFileInput"
								name="uploadFile"></td>
						</tr>
						<tr>
							<td>&nbsp;</td>
							<td><a href="#" class="easyui-linkbutton"
								onclick="stock.importExcelData()">上传</a></td>
						</tr>
					</table>
					<iframe name="target_upload" style="display: none;"></iframe>
				</form>
			</div>
			<!-- window -->

		</shiro:hasPermission>
		<shiro:lacksPermission name="sys:riskmanager:suspendedStock:view">
			<%@ include file="../../common/noPermission.jsp"%>
		</shiro:lacksPermission>
	</c:if>
</body>
</html>