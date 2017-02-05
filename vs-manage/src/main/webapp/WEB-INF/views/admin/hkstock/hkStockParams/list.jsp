<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../../../common/common.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="Content-Language" content="zh-CN" />
<!-- X-UA-Compatible 设置ie8/ie9 的兼容模式 -->
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7">
<title></title>
<%@include file="../../../common/import-easyui-js.jspf"%>
<link rel="stylesheet" type="text/css"
	href="${ctx}/static/css/dataStyle.css">
<script type="text/javascript" src="${ctx}/static/script/hkstock/hkStockParams/list.js"></script>
</head>
<body>
	<shiro:hasPermission name="sys:settingParams:hkStockParams:view">
		<div>
			<shiro:hasPermission name="sys:settingParams:hkStockParams:update">
				<a href="javascript:void(0)" class="easyui-linkbutton"
					data-options="iconCls:'icon-save'"
					onclick="hkStockParamsOptions.update('admin/hkstock/hkStockParams/updateParams')">保存</a>
			</shiro:hasPermission>
		</div>
		<div>
			<form method="post" id="addForm">
				<input type="hidden" value="${hkStockParams.id}" id="id" name="id" />
				<input type="hidden" value="${hkStockParams.version}" id="version" name="version"/>
				<input type="hidden" value="${hkStockParams.createUser}" id="createUser" name="createUser" />
				<input type="hidden" value="${hkStockParams.createUserId}" id="createUserId" name="createUserId" />
				<input type="hidden" value="${hkStockParams.createTime}" id="createTime" name="createTime" />
				<!-- missingMessage:'不能为空' 可以修改为空时的提示语 -->
				<table class="conn" border="0" width="100%" cellpadding="0"
					cellspacing="0" class="conn">
					<tr>
						<td class="label right">最小总操盘资金（港元）:</td>
						<td><input class="easyui-numberbox"
							value="${hkStockParams.minTotalMoney}" id="minTotalMoney"
							name="minTotalMoney"
							data-options="required:true,precision:2,min:0" /></td>
						<td class="label right">最大总操盘资金（港元）:</td>
						<td><input class="easyui-numberbox"
							value="${hkStockParams.maxTotalMoney}" id="maxTotalMoney"
							name="maxTotalMoney"
							data-options="required:true,precision:2,min:0" /></td>
						<td class="label right">推荐操盘金额（港元）:</td>
						<td><input class="easyui-validatebox"
							value="${hkStockParams.recommendHoldMoney}"
							id="recommendHoldMoney" name="recommendHoldMoney"
							data-options="required:true" /><br /><span style="color: red;">请使用英文分隔符“;”</span></td>
					</tr>
					<tr>
						<td class="label right">港股配资倍数:</td>
						<td><input class="easyui-validatebox"
							value="${hkStockParams.leverConfig}" id="leverConfig"
							name="leverConfig" data-options="required:true" /><br /><span style="color: red;">请使用英文分隔符“;”</span></td>
						<td class="label right">警戒线风控系数:</td>
						<td><input class="easyui-validatebox"
							value="${hkStockParams.warningCoefficient}"
							id="warningCoefficient" name="warningCoefficient"
							data-options="required:true" /><br /><span style="color: red;">请使用英文分隔符“;”</span></td>
						<td class="label right">平仓线风控系数:</td>
						<td><input class="easyui-validatebox"
							value="${hkStockParams.openCoefficient}" id="openCoefficient"
							name="openCoefficient" data-options="required:true" /><br /><span style="color: red;">请使用英文分隔符“;”</span></td>
					</tr>
					<tr>
						<td class="label right">最小超盘天数:</td>
						<td><input class="easyui-numberbox"
							value="${hkStockParams.minHoldDays}" id="minHoldDays"
							name="minHoldDays" data-options="required:true,min:0" /></td>
						<td class="label right">最大超盘天数:</td>
						<td><input class="easyui-numberbox"
							value="${hkStockParams.maxHoldDays}" id="maxHoldDays"
							name="maxHoldDays" data-options="required:true,min:0" /></td>
						<td class="label right">推荐操盘天数:</td>
						<td><input class="easyui-validatebox"
							value="${hkStockParams.recommendHoldDays}" id="recommendHoldDays"
							name="recommendHoldDays" data-options="required:true" /><br /><span style="color: red;">请使用英文分隔符“;”</span></td>
					</tr>
					<tr>
						<td class="label right">保证金计算系数:</td>
						<td><input class="easyui-numberbox"
							value="${hkStockParams.bailCoefficient}" id="bailCoefficient"
							name="bailCoefficient"
							data-options="required:true,precision:2,min:0" /></td>
						<td class="label right">港股利息系数:</td>
						<td><input class="easyui-numberbox"
							value="${hkStockParams.interestCoefficient}"
							id="interestCoefficient" name="interestCoefficient"
							data-options="required:true,precision:2,min:0" /></td>
						<td class="label right">港股管理费系数:</td>
						<td><input class="easyui-numberbox"
							value="${hkStockParams.manageFeeCoefficient}"
							id="manageFeeCoefficient" name="manageFeeCoefficient"
							data-options="required:true,precision:2,min:0" /></td>
					</tr>
				</table>
			</form>
		</div>
	</shiro:hasPermission>
	<!-- window -->
	<shiro:lacksPermission name="sys:settingParams:hkStockParams:view">
		<%@ include file="../../../common/noPermission.jsp"%>
	</shiro:lacksPermission>
</body>
</html>