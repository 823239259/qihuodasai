<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!-- 修改密码弹出框-->
<div id="updatePwdWin" style="padding: 10px;">
	<form method="post" id="updateForm" style="padding-left: 22%;">
		<input type="hidden" id="accountid" value="${id}">
		<table class="formTable">
			<tr>
				<td>原密码:</td>
				<td><input class="easyui-validatebox" id="password"
					type="password" name="password"
					data-options="required:true,validType:'safepass'"></td>
			</tr>
			<tr>
				<td>新密码:</td>
				<td><input class="easyui-validatebox" id="password1"
					type="password" name="password1"
					data-options="required:true,validType:'safepass'"></td>
			</tr>
			<tr>
				<td>确认密码:</td>
				<td><input class="easyui-validatebox" id="password2"
					type="password" name="password2"
					data-options="required:true,validType:['safepass','equalTo[\'#password1\']']">
				</td>
			</tr>
			<tr>
				<td colspan="2" align="center"><a href="javascript:void(0)"
					id="saveOrUpdate" class="easyui-linkbutton"
					data-options="iconCls:'icon-edit'" onclick="parentAccount.updatePasswod()">确认修改</a>
				</td>
			</tr>
		</table>
	</form>
</div>