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
<%@include file="../../common/import-easyui-js.jspf"%>
<title>母账户编辑</title>
</head>
<body>
		<form method="post" id="addForm" style="padding-left: 22%;">
		<!-- missingMessage:'不能为空' 可以修改为空时的提示语 -->
					<table class="formTable">
						<tr>
							<td>手机号:</td>
							<td>
								<input class="easyui-validatebox" id="mobile"   name="mobile" data-options="required:true,validType:['mobile']">
							</td>
						</tr>
						
						<tr>
							<td>配资保证金:</td>
							<td>
								<input class="easyui-validatebox"  id="leverMoney"    name="leverMoney" data-options="required:true,validType:'money'">
							</td>
						</tr>
						
					
						<tr>
							<td>配资杠杆:</td>
							<td><input class="easyui-numberbox"   id="lever"  name="lever" data-options="min:1,max:15,required:true" missingMessage="必须填写，配资杠杆范围1~15倍。"></td>
						</tr>
						<tr>
							<td>借款期限:</td>
							<td><input class="easyui-numberbox"  id="naturalDays"  name="naturalDays" data-options="min:2,max:180,required:true" missingMessage="必须填写，借款期限范围2~180天。"></td>
						</tr>
						
						<tr>
							<td>恒生账户名:</td>
							<td><input class="easyui-validatebox"  id="accountName"  name="accountName" data-options=""></td>
						</tr>
						<tr>
							<td>交易帐号:</td>
							<td><input class="easyui-validatebox"  id="account"  name="account" data-options=""></td>
						</tr>
				
						<tr>
							<td>交易密码:</td>
							<td><input class="easyui-validatebox"  id="password"  name="password" data-options=""></td>
						</tr>
						<tr>
							<td>单元序号:</td>
							<td><input class="easyui-validatebox"  id="assetId"  name="assetId" data-options=""></td>
						</tr>
						<tr>
							<td>母账户名称:</td>
							<td>
									<input class="easyui-combobox" id="parentAccountId"  name="parentAccountId" data-options="
										url:'${ctx}/admin/parentAccount/getComboboxData',
										valueField:'id',
										panelHeight:100,
										textField:'accountName'">
							</td>
						</tr>
						<tr>
							<td>保险单号:</td>
							<td><input class="easyui-validatebox" value="" id="insuranceNo"  name="insuranceNo" data-options=""></td>
						</tr>
						<tr>
							<td colspan="2" align="center">
								<a href="javascript:void(0)" id="saveOrUpdate" class="easyui-linkbutton" data-options="iconCls:'icon-save'"  onclick="baseUtils.saveOrUpdate('admin/handTrade/saveInfo','iframe')">保存</a>
							</td>
						</tr>
					</table>
		</form>
</body>
</html>