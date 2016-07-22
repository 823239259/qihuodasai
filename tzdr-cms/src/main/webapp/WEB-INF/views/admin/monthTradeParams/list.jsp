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

<title></title>
<%@include file="../../common/import-easyui-js.jspf"%>
<script type="text/javascript" src="${ctx}/static/script/monthTrade/list.js"></script>
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/css/dataStyle.css">
</head>
<body>
	<shiro:hasPermission name="sys:settingParams:monthTrade:view">
		<div>
			<shiro:hasPermission name="sys:settingParams:monthTrade:update">
				<a href="javascript:void(0)" class="easyui-linkbutton"
					data-options="iconCls:'icon-save'"
					onclick="ParamsOptions.update('admin/monthTrade/updateParams')">保存</a>
			</shiro:hasPermission>
		</div>
		<div>
			<form method="post" id="addForm" >
				<!-- missingMessage:'不能为空' 可以修改为空时的提示语 -->
				<table class="conn" border="0" width="100%" cellpadding="0"
					cellspacing="0" class="conn">
					<tr>
						<td class="label right">最小操盘资金:</td>
						<td><input class="easyui-numberbox"
							value="${params.minTotalMoney}" id="minTotalMoney"
							name="minTotalMoney"
							data-options="required:true,precision:2,min:0" /></td>
						<td class="label right">最大操盘资金:</td>
						<td><input class="easyui-numberbox"
							value="${params.maxTotalMoney}" id="maxTotalMoney"
							name="maxTotalMoney"
							data-options="required:true,precision:2,min:0" /></td>
						<td class="label right">推荐操盘金额:</td>
						<td><input class="easyui-validatebox"
							value="${params.recommendHoldMoney}"
							id="recommendHoldMoney" name="recommendHoldMoney"
							data-options="required:true" /><br /><span style="color: red;">请使用英文分隔符“;”</span></td>
					</tr>
					<tr>
						<td class="label right">月月配配资倍数:</td>
						<td><input class="easyui-validatebox"
							value="${params.leverConfig}" id="leverConfig"
							name="leverConfig" data-options="required:true" /><br /><span style="color: red;">请使用英文分隔符“;”</span></td>
						<td class="label right">月月配利息系数:</td>
						<td><input class="easyui-numberbox"
							value="${params.interestCoefficient}"
							id="interestCoefficient" name="interestCoefficient"
							data-options="required:true,precision:2,min:0" /></td>
						<td class="label right">月月配管理费系数:</td>
						<td><input class="easyui-numberbox"
							value="${params.manageFeeCoefficient}"
							id="manageFeeCoefficient" name="manageFeeCoefficient"
							data-options="required:true,precision:2,min:0" /></td>
	
					</tr>
					<tr>
						<td class="label right">操盘月数:</td>
						<td><input class="easyui-validatebox"
							value="${params.recommendHoldMonths}" id="recommendHoldMonths"
							name="recommendHoldMonths" data-options="required:true" /><br /><span style="color: red;">请使用英文分隔符“;”</span></td>
					</tr>
					<tr>
						
					</tr>
				</table>
			</form>
		</div>
	</shiro:hasPermission>
	<!-- window -->
	<shiro:lacksPermission name="sys:settingParams:monthTrade:view">
			<%@ include file="../../common/noPermission.jsp"%>
	</shiro:lacksPermission>
</body>
</html>