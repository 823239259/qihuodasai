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
<title>权限列表</title>
<%@include file="../../common/import-easyui-js.jspf"%>
<script type="text/javascript" src="${ctx}/static/script/permission/roleList.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/dataStyle.css">
</head>
<body>
	<shiro:hasPermission name="sys:rolemanager:rolelist:view">
	<div id="tb" style="padding: 5px; height: auto">
		<div>
		<table border="0" style="font-size:12px;" class="conn"  width="100%" cellpadding="0" cellspacing="0">
				<tr>
					<td class="label right">
						<span>角色名称：</span>
					</td>
					<td>
					<input class="easyui-validatebox" id="rolename">
					&nbsp;&nbsp;
					<a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-search" onclick="cmsrole.doSearch()">查询</a>
					</td>
				</tr>
		</table>	
		</div>
		<div style="margin-bottom: 5px">
			<shiro:hasPermission name="sys:rolemanager:rolelist:create">  
				<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="tabUtils.addTopTab('tabPanel','新增角色','admin/role/editRole?fromType=add')">新增</a>
			</shiro:hasPermission>
			<shiro:hasPermission name="sys:rolemanager:rolelist:delete">  
				<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="cmsrole.deleteRole()">删除</a>
			</shiro:hasPermission>
			<shiro:hasPermission name="sys:rolemanager:rolelist:update">  
				<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="cmsrole.openEditTab()">修改</a>
			</shiro:hasPermission>
		</div> 
	</div>
	<table id="edatagrid"></table>
	</shiro:hasPermission>
		<shiro:lacksPermission name="sys:rolemanager:rolelist:view">
			<%@ include file="../../common/noPermission.jsp"%>
	</shiro:lacksPermission>
</body>
</html>