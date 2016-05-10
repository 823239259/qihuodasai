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
<script type="text/javascript" src="${ctx}/static/script/common/dateUtils.js"></script>
<script type="text/javascript" src="${ctx}/static/script/hundsunAccount/subList.js"></script>
<script type="text/javascript" src="${ctx}/static/plugins/my97DatePicker/WdatePicker.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/dataStyle.css">

</head>
<body>
	<shiro:hasPermission name="sys:accountmanager:subaccount:view">
	<div id="tb" style="padding: 5px; height: auto">
		<div>
			<table border="0" style="font-size:12px;" class="conn"  width="100%" cellpadding="0" cellspacing="0">
				<tr>
					<td class="label right">
						<span>恒生帐号:</span> 
					</td>
					<td><input id="search_account"></td>
					<td class="label right">
						<span>使用状态:</span>
					</td>
					<td>
					<input class="easyui-combobox" id="userstatus" name="userstatus" data-options="
										url:'${ctx}/admin/dataDic/getDicDatas?typeKey=subAccountStatus',
										valueField:'valueField',
										panelHeight:100,
										textField:'textField'">
					</td>
					<td class="label right">
					<span>创建时间:</span>
					</td>
					<td>
						<input id="startTime" class="Wdate" type="text" onFocus="var endTime=$dp.$('endTime');WdatePicker({onpicked:function(){endTime.focus();},maxDate:'#F{$dp.$D(\'endTime\')}'})"/>
					      至  <input id="endTime" class="Wdate" type="text" onFocus="WdatePicker({minDate:'#F{$dp.$D(\'startTime\')}'})"/>
					</td>
				</tr>
				<tr>
					<td class="label right">
						<span>母账户名称:</span>
					</td>
					<td><input id="search_parentAccountName"></td>
					<td class="label right">
						<span> </span>
					</td>
					<td colspan="3">
						<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="subAccount.doSearch()">查询</a>
					</td>
				</tr>
			</table>				
		</div>
		<div style="margin-bottom: 5px">
			<shiro:hasPermission name="sys:accountmanager:subaccount:create">  
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="baseUtils.openAddwin(500,300,'新增子账户','subAccount')">新增</a>		
			</shiro:hasPermission>
			<shiro:hasPermission name="sys:accountmanager:subaccount:import">  
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="baseUtils.openIframeWin(510,230,'批量导入账户','admin/subAccount/toImport')">批量导入</a>
			</shiro:hasPermission>
			<shiro:hasPermission name="sys:accountmanager:subaccount:delete"> 
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="baseUtils.deleteData('subAccount')">删除</a>
			</shiro:hasPermission>
			<shiro:hasPermission name="sys:accountmanager:subaccount:update"> 
			<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="baseUtils.openEditwin(500,300,'修改子账户','subAccount')">修改</a>
 			</shiro:hasPermission>
 			<a href="${ctx}/admin/subAccount/downloadTemplate"class="easyui-linkbutton" iconCls="icon-remove" plain="true">下载子账户导入模版</a>
 			<%-- <shiro:hasPermission name="sys:accountmanager:subaccount:refresh"> 
 			 	<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-reload" plain="true" onclick="subAccount.refreshCombine()">刷新组合信息</a>
 			</shiro:hasPermission> --%>
		</div>
	</div>
	<table id="edatagrid"></table>

	<!-- ADD window -->
	<div id="addWin" style="padding:10px;top: 20px;"></div>
	<!-- window -->
	</shiro:hasPermission>
	<shiro:lacksPermission name="sys:accountmanager:subaccount:view">
		<%@ include file="../../common/noPermission.jsp"%>
	</shiro:lacksPermission>
</body>
</html>