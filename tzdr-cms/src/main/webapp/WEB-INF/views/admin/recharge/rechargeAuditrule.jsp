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
<title>补录充值表单</title>
<%@include file="../../common/import-easyui-js.jspf"%>
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/dataStyle.css">
</head>
<body>
	<shiro:hasPermission name="sys:finance:rechargeAuditRule:view">
	<div id="tb" style="padding: 5px; height: auto">
		<div>
			<form id="searchForm">
				<table border="0" style="font-size:12px;" class="conn"  width="100%" cellpadding="0" cellspacing="0">
					<tr>
						<td class="label right">
							<span>审核人员：</span>
						</td>
						<td>
							<input class="easyui-validatebox" id="search_LIKE_auditorName" name="search_LIKE_auditorName">					
						</td>
						<td class="label right">
							<span>创建人：</span>
						</td>
						<td>
							<input class="easyui-validatebox" id="search_LIKE_createUser" name="search_LIKE_createUser">					
						</td>
					</tr>
					<tr>
					<td class="label right">
						<span></span>
					</td>
					<td colspan="3">
						<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="datagridUtils.datagridQuery('edatagrid','searchForm')">查询</a>
					</td>
				</tr>
				</table>	
			</form>	
		</div>
		<div style="margin-bottom: 5px">
			<shiro:hasPermission name="sys:finance:rechargeAuditRule:create">  
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="baseUtils.openAddIframeWin(700,250,'设置审核规则','rechargeAuditRule')">设置审核规则</a>
			</shiro:hasPermission>
			<shiro:hasPermission name="sys:finance:rechargeAuditRule:update"> 
				<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="baseUtils.openEditIframeWin(700,250,'修改审核规则','rechargeAuditRule')">修改审核规则</a>
			</shiro:hasPermission>
		</div>
	</div>
	
	<table id="edatagrid" class="easyui-datagrid"
            width="100%"
            url="${ctx}/admin/rechargeAuditRule/easyuiPage" pagination="true"
            data-options="checkOnSelect:true,toolbar:'#tb',
            frozenColumns:[[
		            {field:'ck',checkbox:true}
			]],
            onLoadSuccess:function(data){
				datagridUtils.loadSuccess(data,'edatagrid');
			}"
            rownumbers="true" fitColumns="true" singleSelect="true">
	        <thead>
	            <tr>
	            	<th field="id" width="150" hidden="true"></th>
					<th field="auditorName" width="150" sortable="true">审核人员 </th>
					<th field="viewMoney" width="150">充值审核金额</th>
					<th field="createUser" width="150">创建人</th>
					<th field="viewCreateTime" width="150">创建时间</th>
					<th field="updateUser" width="150">最终修改人 </th>
					<th field="viewUpdateTime" width="150">最终修改时间</th>
	            </tr>
	        </thead>
   </table>

	<!-- ADD window -->
	<div id="addWin" style="padding:10px;top: 20px;"></div>
	<!-- window -->
	</shiro:hasPermission>
	<shiro:lacksPermission name="sys:finance:rechargeAuditRule:view">
		<%@ include file="../../common/noPermission.jsp"%>
	</shiro:lacksPermission>
</body>
</html>