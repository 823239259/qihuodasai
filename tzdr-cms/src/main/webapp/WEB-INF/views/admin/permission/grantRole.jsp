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
		<script type="text/javascript" src="${ctx}/static/script/permission/grantRole.js"></script>
		
	</head>
	<body style="padding-top:5px;">
	<shiro:hasPermission name="sys:rolemanager:grantuser:view">
		<form action="#" id="userRoleForm">
				    <div id="userp" class="easyui-panel" title="用户信息"  style="padding: 10px;height: 100px;" data-options="" border="false">
					    <p>选择用户 (可多选)<input  id="userTree">
					    </p>
				    </div>
				    
				    <div id="rolep" class="easyui-panel" title="角色信息"  style="padding: 10px;" data-options="" border="false">
				    	<div class="easyui-layout" style="height: 210px;">
					  		<div region="east"  style="width:800%;" border="false">
					  			<div id="rolep2" class="easyui-panel" title="已选择的角色列表"  style="padding: 10px;top: 10px;" data-options="width:300,height:200" border="true">
					  			</div>
					  		</div>
							<div region="west"  style="width:300%;" border="false">
								<div id="rolep1" cls="seletRole" class="easyui-panel" title="未选择的角色列表"  style="padding: 10px;" data-options="width:300,height:200" border="true">
					  				<c:forEach items="${roles}" var="role" varStatus="status">
					  					<a href="javascript:void(0)" id="${role.id}" onclick="grantRole.selectRole(this)"  class="easyui-linkbutton" data-options="" style="margin-bottom: 5px;width:200px;">${role.name}[${role.role}]</a>
					  				</c:forEach>
					  			</div> 
							</div>
							<div region="center" border="false" style="padding-top:70px;text-align: center;">
								<img alt="" src="${ctx}/static/images/chooseRole.png">
							</div>
					  	</div>	
				    </div>
				    <shiro:hasPermission name="sys:rolemanager:grantuser:create"> 
				    <a href="javascript:void(0);" style="margin-left: 8px;" class="easyui-linkbutton" onclick="grantRole.saveUserRole('admin/auth/saveAuth')" data-options="iconCls:'icon-save'">保存</a>
					</shiro:hasPermission>
			</div>
		</form>
		
		</shiro:hasPermission>
		<shiro:lacksPermission name="sys:rolemanager:grantuser:view">
			<%@ include file="../../common/noPermission.jsp"%>
		</shiro:lacksPermission>
		
	</body>
	<script type="text/javascript">
	</script>
</html>