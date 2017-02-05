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
	src="${ctx}/static/script/common/dateUtils.js"></script>
<script type="text/javascript"
	src="${ctx}/static/script/wuser/public.js"></script>
<script type="text/javascript"
	src="${ctx}/static/script/hundsunAccount/notCancelList.js?v=20150625"></script>
<script type="text/javascript"
	src="${ctx}/static/plugins/my97DatePicker/WdatePicker.js"></script>
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/css/dataStyle.css">

</head>
<body>
	<shiro:hasPermission name="sys:riskmanager:notCancelAccounts:view">
		<div id="tb" style="padding: 5px; height: auto">
			<div>
				<form id="searchForm" method="post">
					<table border="0" style="font-size: 12px;" class="conn"
						width="100%" cellpadding="0" cellspacing="0">
						<tr>
							<td class="label right"><span>恒生帐号：</span></td>
							<td><input class="easyui-validatebox" id="search_account">
							</td>
							<td class="label right"><span>终结时间：</span></td>
							<td><input id="startTime" class="" type="text"
								onFocus="var endTime=$dp.$('endTime');WdatePicker({onpicked:function(){endTime.focus();},maxDate:'#F{$dp.$D(\'endTime\')}'})" />
								至 <input id="endTime" class="" type="text"
								onFocus="WdatePicker({minDate:'#F{$dp.$D(\'startTime\')}'})" />
							</td>
						</tr>
						<tr>
							<td class="label right"><span>交易账号类型：</span></td>
							<td colspan="3"><input class="easyui-combobox" id="feeType"
								name="search_IN_feeType"
								data-options="
										url:'${ctx}/admin/dataDic/getDicDatas?typeKey=feetype',
										valueField:'valueField',
										panelHeight:50,
										textField:'textField'">
							</td>
						</tr>
						<tr>
							<td class="label">&nbsp;</td>
							<td class="label" colspan="3"><a href="javascript:void(0)" class="easyui-linkbutton"
								iconCls="icon-search" onclick="subAccount.doSearch()">查询</a></td>
						</tr>
					</table>
				</form>
			</div>
			<div style="margin-bottom: 5px">
				<shiro:hasPermission name="sys:riskmanager:notCancelAccounts:update">
					<a href="javascript:void(0)" class="easyui-linkbutton"
						iconCls="icon-edit" plain="true"
						onclick="subAccount.cancelAccount()">注销账户</a>
					<a href="javascript:void(0)" class="easyui-linkbutton"
						iconCls="icon-excel" plain="true"
						onclick="$.easyui.exportExcel('edatagrid','searchForm')">导出</a>
				</shiro:hasPermission>
			</div>
		</div>
		<table id="edatagrid"></table>

		<!-- ADD window -->
		<div id="addWin" style="padding: 10px; top: 20px;"></div>
		<!-- window -->
	</shiro:hasPermission>
	<shiro:lacksPermission name="sys:riskmanager:notCancelAccounts:view">
		<%@ include file="../../common/noPermission.jsp"%>
	</shiro:lacksPermission>
</body>
</html>