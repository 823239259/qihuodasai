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
</head>
<body>
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/dataStyle.css">
<script type="text/javascript" src="${ctx}/static/script/common/public.js"></script>
	<form method="post" id="addForm">
		<input type="hidden" id="id" name="id" value="${fSimpleConfig.id}">
	<!-- missingMessage:'不能为空' 可以修改为空时的提示语 -->
		<table class="conn" border="0" width="100%" cellpadding="0" cellspacing="0"  class="conn" >
			<tr>
				<td class="label right">手续费:</td>
				<td>
					<input class="easyui-validatebox" value="${fSimpleConfig.tranFeesArray}" id="tranFeesArray" name="tranFeesArray" data-options="required:true">
				</td>
			</tr>
			<%--
			<tr>
				<td class="label right">管理费:</td>
				<td>
					<input class="easyui-numberbox" value="${fSimpleConfig.feeManage}" id="feeManage" name="feeManage" data-options="required:true,precision:2,min:0">
				</td>
			</tr>
			--%>
			<tr>
				<td class="label right">单手保证金:</td>
				<td>
					<input class="easyui-numberbox" value="${fSimpleConfig.traderBond}" id="traderBond" name="traderBond" data-options="required:true,precision:2,min:0">
				</td>
			</tr>
			<tr>
				<td class="label right">单手操盘金:</td>
				<td>
					<input class="easyui-numberbox" value="${fSimpleConfig.traderMoney}" id="traderMoney" name="traderMoney" data-options="required:true,precision:2,min:0">
				</td>
			</tr>
			<tr>
				<td class="label right">开仓保留金额:</td>
				<td>
					<input class="easyui-numberbox" value="${fSimpleConfig.lineLoss}" id="lineLoss" name="lineLoss" data-options="required:true,precision:2,min:0">
				</td>
			</tr>
			<tr>
				<td class="label right">开仓手数:</td>
				<td>
					<input class="easyui-validatebox" value="${fSimpleConfig.tranLever}" id="tranLever" name="tranLever" data-options="required:true">
				</td>
			</tr>
			<tr>
				<td colspan="2" align="center">
					<a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-save'"  onclick="baseUtils.saveOrUpdate('admin/commodityFutureParam/updateParam')">保存</a>
				</td>
			</tr>
		</table>
	</form>
</body>
</html>