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
		<%@include file="../../common/import-easyui-js.jspf"%>
		<script type="text/javascript" src="${ctx}/static/script/organization/list.js"></script>
	</head>
	<body class="easyui-layout" style="padding: 10px;">
		<shiro:hasPermission name="sys:system:orglist:view">
			<div data-options="region:'west',split:true,title:'资源树'" style="width:200px;">
			<a href="javascript:void(0)" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-reload'" onclick="reloadTree()" style="margin-bottom: 5px;">刷新组织</a>
			 <ul id="orgTree" data-options="animate:true">
			 </ul>
			
			</div>
			<div id="main" data-options="region:'center',title:''">
				<div id="tabPanel" class="easyui-tabs" data-options="tools:'#tab-tools',border:false,fit:true">
				</div>
			</div>
			</div>
		</shiro:hasPermission>
		<shiro:lacksPermission name="sys:system:orglist:view">
			<%@ include file="../../common/noPermission.jsp"%>
		</shiro:lacksPermission>
</body>
</html>