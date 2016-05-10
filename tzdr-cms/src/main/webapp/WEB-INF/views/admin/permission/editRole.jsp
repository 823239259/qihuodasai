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
		<link rel="stylesheet" type="text/css" href="${ctx}/static/css/permission/editRole.css">
		<%@include file="../../common/import-easyui-js.jspf"%>
		<script type="text/javascript" src="${ctx}/static/script/permission/editRole.js"></script>
	</head>
	<body style="padding-top:5px;">
		<form action="" id="permissionForm">
			<div title="新增用户授权" data-options="tools:'#p-tools'" style="padding:5px;">
				    <div id="userp" class="easyui-panel" title="角色信息"  style="padding: 10px;height: 100px;" data-options="" border="false">
					   <input id="roleId" value="${role.id}" type="hidden">
					    <table>
					    	<tr>
					    		<td>角色名称：<input class="easyui-validatebox" id="name" value="${role.name}"  name="name" data-options="required:true,validType:['length[2,20]']"></td>
					    		<td class="tdleft">详细描述：<input class="easyui-validatebox"  value="${role.description}" id="description"  name="description" data-options="required:true,validType:['length[2,20]'],invalidMessage:'请输入2-20个字符，并且全是中文'"></td>
					    	</tr>
					    	<tr>
					    		<td>角色标志：<input class="easyui-validatebox" id="role"  value="${role.role}" name="role" data-options="required:true,validType:['length[2,20]','letter']"></td>
					    		<td class="tdleft">是否显示：
						    		<c:choose>
									<c:when test="${organization.show==false}">
						    			<input type="radio" name="show" value="true" ><span>是</span></input>
										<input type="radio" name="show" value="false" checked="checked"><span>否</span></input>
									</c:when>
									<c:otherwise>
										<input type="radio" name="show" value="true" checked="checked"><span>是</span></input>
										<input type="radio" name="show" value="false"><span>否</span></input>
									</c:otherwise>
									</c:choose>
								</td>
					    	</tr>
					    </table>
				    </div>
				    
				    <div id="rolep" class="easyui-panel" title="授权信息"  style="padding: 10px;" data-options="" border="false">
				    	<p>请选择授权角色</p>
				    	<div>
				    		<table>
				    			<tr>
				    				<td>资源</td>
				    				<td>
										<input id="resource"> 
				    				</td>
				    				<td>权限</td>
				    				<td>
				    					 <div id="permission" class="easyui-panel" style="padding:5px;top: 10px;" data-options="width:200,height:100,noheader:true" border="true">
								  			<c:forEach items="${permissions}" var="permission" varStatus="status">
								  				<p id="permission-${permission.id}">${permission.name}(${permission.permission})</p>
								  			</c:forEach>
							  			</div>
				    				</td>
				    				<td><a id="add" href="javascript:void(0)" onclick="permission.addResource()" class="easyui-linkbutton" data-options="iconCls:'icon-add'">添加</a></td>
				    			</tr>
				    		</table>				    		
				    	</div>
				    	<p>注意：添加的数据是临时的，还需要点击页面下方的新增保存该数据</p>
				    	<p>已选择的资源和权限 <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" plain="true" onclick="permission.removeResource()">删除选中</a></p>
				    	<table id="edatagrid"></table>
				    	<div id="noDataInfo" style="color:red;padding:5px; display:none;">请选择资和源权限！</div>
				    </div>
				    <c:choose>
							<c:when test="${fromType=='add'}">
				   				 <a href="javascript:void(0);" onclick="permission.saveRolePermission('admin/role/saveRole','add')" style="margin-left: 10px;" class="easyui-linkbutton" data-options="iconCls:'icon-save'">保存</a>
								<a href="javascript:void(0);" class="easyui-linkbutton" onclick="permission.backList('add')"  style="margin-left: 10px;"  data-options="iconCls:'icon-back'">返回</a>
							</c:when>
							<c:otherwise>
								 <a href="javascript:void(0);" onclick="permission.saveRolePermission('admin/role/updateRole','edit')" style="margin-left: 10px;" class="easyui-linkbutton" data-options="iconCls:'icon-edit'">修改</a>
								<a href="javascript:void(0);" class="easyui-linkbutton" onclick="permission.backList('edit')"  style="margin-left: 10px;"  data-options="iconCls:'icon-back'">返回</a>
							</c:otherwise>
					</c:choose>
		</div>
		</form>
	</body>
</html>