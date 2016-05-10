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
</head>
<body>
		<form method="post" id="addForm" style="padding-left: 22%;">
		<input type="hidden" id="dataID" value="${user.id}">
		<!-- missingMessage:'不能为空' 可以修改为空时的提示语 -->
					<table class="formTable">
						<tr>
							<td>用户姓名:</td>
							<td>
							<c:choose>
								<c:when test="${fromType=='add' || fromType=='orgAddUser' }">
									<input class="easyui-validatebox" id="realname" value=""   name="realname" data-options="required:true,validType:['length[2,8]']"></td>
								</c:when>
								<c:otherwise>
									<input type="hidden" id="realname" value="${user.realname}">
									${user.realname}
								</c:otherwise>
							</c:choose>
						</tr>
						<c:if test="${fromType=='add' || fromType=='orgAddUser'}">
							<tr>
								<td>密码:</td>
								<td>
									<input class="easyui-validatebox" id="password" type="password" value=""   name="apassword" data-options="required:true,validType:'safepass'">
								</td>
							</tr>
						</c:if>
						<tr>
							<td>手机号:</td>
							<td><input class="easyui-validatebox" value="${user.mobilePhoneNumber}" id="mobilePhoneNumber"  name="mobilePhoneNumber" data-options="required:true,validType:'mobile'"></td>
						</tr>
						<tr>
							<td>电子邮箱:</td>
							<td><input class="easyui-validatebox" value="${user.email}" id="email"  name="email" data-options="required:true,validType:'email'"></td>
						</tr>
						<tr>
							<td>所属组织:</td>
							<td>
								<c:choose>
									<c:when test="${fromType=='orgAddUser'}">
										${organization.name}
									</c:when>
									<c:otherwise>
									<input class="easyui-combobox" id="organization" value="${user.organization.id}" name="language" data-options="
										url:'${ctx}/admin/org/getComboboxData',
										valueField:'id',
										panelHeight:100,
										required:true,
										textField:'name'">
									</c:otherwise>
								</c:choose>
							</td>
						</tr>
						<tr>
								<td colspan="2" align="center">
								<c:choose>
									<c:when test="${fromType=='add' || fromType=='orgAddUser'}">
										<a href="javascript:void(0)" id="saveOrUpdate" class="easyui-linkbutton" data-options="iconCls:'icon-save'"  onclick="user.list.saveOrUpdate('admin/user/create','${organization.id}')">保存</a>
									</c:when>
									<c:otherwise>
										<a href="javascript:void(0)" id="saveOrUpdate" class="easyui-linkbutton" data-options="iconCls:'icon-edit'"  onclick="user.list.saveOrUpdate('admin/user/update','')">修改</a>
									</c:otherwise>
								</c:choose>
								</td>
						</tr>
					</table>
		</form>
</body>
</html>