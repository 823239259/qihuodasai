<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../common/common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
		<meta http-equiv="Content-Language" content="zh-CN"/> 
		<!-- X-UA-Compatible 设置ie8/ie9 的兼容模式 -->
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
		<title>登录</title>
		<script type="text/javascript">var orgID = '${nodeID}';</script>	
		<%@include file="../../common/import-easyui-js.jspf"%>
		<script type="text/javascript" src="${ctx}/static/script/organization/nodeList.js"></script>	
		<script type="text/javascript" src="${ctx}/static/script/user/list.js"></script>
	
	</head>
	<body style="padding: 10px;">
		 <div id="toolBar" style="padding:5px;height:auto">
		    <div style="margin-bottom:5px">
		    	<shiro:hasPermission name="sys:system:orglist:create">  
		    		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="organization.openAddOrgWin()">新增组织</a>
		   		</shiro:hasPermission>
		   		<shiro:hasPermission name="sys:system:orglist:update">  
		   		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="organization.openEditOrgWin()">修改组织</a>
		    	</shiro:hasPermission>
		    	<%-- <shiro:hasPermission name="sys:system:orglist:delete">  
		   		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-cancel" plain="true" onclick="organization.deleteOrg('${nodeID}')">删除组织</a>
		    	</shiro:hasPermission> --%>
		    	<shiro:hasPermission name="sys:system:userlist:create">  
		    	<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="organization.openAddUserWin()">新增用户</a>
		   		</shiro:hasPermission>
		    	<%-- <shiro:hasPermission name="sys:system:orglist:view"> 
		    	<a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-reload'" onclick="organization.refreshTreeGrid('${nodeID}')">刷新</a>
		    	</shiro:hasPermission> --%>
		    </div>
		</div>
		<table  id="eytreegrid" style="width:700px;height:250px"
			data-options="
				url: '${ctx}/admin/org/getNodeData?parentId=${nodeID}',
				idField: 'id',
				treeField: 'name',
				fit:true,
				fitColumns:true
			">
			<thead>
				<tr>
					<th data-options="field:'name'" width="150">部门/姓名</th>
					<th data-options="field:'orgShow'" width="100">部门是否显示</th>
					<th data-options="field:'email'" width="100" align="right">电子邮箱</th>
					<th data-options="field:'phone'" width="150">手机号</th>
				</tr>
			</thead>
		</table>
	</body>
	
	<!-- ADDUser window -->
	<div id="addWin" style="width:500px;height:300px;padding:10px;top:30%;"></div>
	<!-- window -->
	
	<!-- add organization window -->
	<div id="addOrgWin"  style="width:500px;height:220px;padding:10px;top:20%;"></div>
</html>