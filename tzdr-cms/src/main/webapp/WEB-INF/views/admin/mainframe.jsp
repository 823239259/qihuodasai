<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
		<meta http-equiv="Content-Language" content="zh-CN"/> 
		<!-- X-UA-Compatible 设置ie8/ie9 的兼容模式 -->
		<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
		<title>投资达人管理系统主页</title>
		<%@include file="../common/import-easyui-js.jspf"%>
		<link  rel="stylesheet" type="text/css" href="${ctx}/static/css/mainframe.css">
		<script type="text/javascript" src="${ctx}/static/script/mainframe.js"></script>
	</head>
	<body class="easyui-layout">
	<div class="northCls" data-options="region:'north',border:false" style="height:50px;">
		<h3 >投资达人管理系统</h3>
		<div>
			<span class="welcome">欢迎您：${user.username}</span>
			<a id="updatePwd" href="javascript:void(0);" onclick="main.openUpdatePwdWin()" class="easyui-linkbutton" data-options="">修改密码</a>
			<a id="logout" href="${ctx}/admin/login" class="easyui-linkbutton" data-options="">退出</a>
<%-- 			<c:if test="${user.username=='admin'}"><a id="refreshCache" onclick="main.refreshCache()" href="javascript:void(0);" class="easyui-linkbutton" data-options="">刷新缓存</a></c:if>
 --%>		
 		</div>
	</div>
	
	<div data-options="region:'west',split:true,title:'操作列表'" style="width:155px;">
		<div id="left" class="easyui-accordion" data-options="fit:true,border:false">
			<c:forEach items="${menus}" var="menu">
				<div title="${menu.name}" style="padding-top: 5px;padding-left: 4px;"  >
					<c:forEach items="${menu.children}" var="chlid">
			    		<a href="javascript:void(0)" onclick="tabUtils.addNewTab('tabPanel','${chlid.name}','${chlid.url}')" class="easyui-linkbutton" data-options="" style="margin-bottom: 5px;width:122px;">${chlid.name}</a>
					</c:forEach>
		   		 </div>
			</c:forEach>
		    <!-- <div title=" 用户管理" style="padding-top: 5px;padding-left: 4px;"  >
		    	<a href="javascript:void(0)" onclick="tabUtils.addNewTab('tabPanel','用户列表','admin/user/list')" class="easyui-linkbutton" data-options="" style="margin-bottom: 5px;width:122px;">用户列表</a>
		   		<a href="javascript:void(0)" onclick="tabUtils.addNewTab('tabPanel','组织机构','admin/org/list')" class="easyui-linkbutton" data-options="" style="margin-bottom: 5px;width:122px;">组织机构</a>
		   	
		    </div>
		    <div title=" 权限管理"  style="padding-top: 5px;padding-left: 4px;" selected="true">
		  		 <a href="javascript:void(0)" class="easyui-linkbutton" data-options="" style="margin-bottom: 5px;width:122px;" onclick="tabUtils.addNewTab('tabPanel','权限列表','admin/permission/list')">权限列表</a>
		  		 <a href="javascript:void(0)" class="easyui-linkbutton" data-options="" style="margin-bottom: 5px;width:122px;" onclick="tabUtils.addNewTab('tabPanel','角色列表','admin/role/list')" >角色列表</a>
		  		 <a href="javascript:void(0)" class="easyui-linkbutton" data-options="" style="margin-bottom: 5px;width:122px;" onclick="tabUtils.addNewTab('tabPanel','授权角色给用户','admin/permission/toGrantRole')">授权角色给用户</a>
		    </div>
		    <div title=" 资源管理" style="padding-top: 5px;padding-left: 4px;">
				<a href="javascript:void(0)"  onclick="tabUtils.addNewTab('tabPanel','资源列表','admin/resource')" class="easyui-linkbutton" data-options="" style="margin-bottom: 5px;width:122px;">资源列表</a>
		    </div> -->
		</div>
	</div>
	<div data-options="region:'south',border:false" style="height:30px;padding:3px;text-align: center;font-weight: bold;margin-top: 4px;">
		Copyright©2015 上海信闳投资管理有限公司 版权所有
	</div>
	
	<div id="main" data-options="region:'center',title:''">
		<div id="tabPanel" class="easyui-tabs" data-options="tools:'#tab-tools',border:false,fit:true">
			<div title="首页" data-options="tools:'#p-tools'" style="padding:20px;">
					
			</div>
		</div>
	</div>
	
	
	<!-- 修改密码弹出框-->
		<div id="updatePwdWin" style="padding:10px;">
			<form method="post" id="updateForm" style="padding-left: 22%;">
			<input type="hidden" id="userid" value="${user.id}">
					<table class="formTable">
						<tr>
							<td>原密码:</td>
							<td>
								<input class="easyui-validatebox" id="password" type="password"   name="password" data-options="required:true,validType:'safepass'">
							</td>
						</tr>
						<tr>
							<td>新密码:</td>
							<td>
								<input class="easyui-validatebox" id="password1" type="password"   name="password1" data-options="required:true,validType:'safepass'">
							</td>
						</tr>
						<tr>
							<td>确认密码:</td>
							<td>
								<input class="easyui-validatebox" id="password2" type="password"    name="password2" data-options="required:true,validType:['safepass','equalTo[\'#password1\']']">
							</td>
						</tr>
						<tr>
								<td colspan="2" align="center">
									<a href="javascript:void(0)" id="saveOrUpdate" class="easyui-linkbutton" data-options="iconCls:'icon-edit'"  onclick="main.updatePasswod()">确认修改</a>
								</td>
						</tr>
					</table>
			</form>
		</div>
		<!-- 修改密码弹出框-->
</body>
</html>